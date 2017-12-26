package thebetweenlands.common.herblore.rune;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import thebetweenlands.api.herblore.rune.IRune;
import thebetweenlands.api.herblore.rune.IRuneChain;
import thebetweenlands.api.herblore.rune.IRuneEffect;
import thebetweenlands.api.herblore.rune.IRuneLink;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer.CombinatorialIterator;
import thebetweenlands.api.herblore.rune.RuneType;

public class RuneChain implements IRuneChain {
	//TODO: Use arrays instead instead of lists/maps?
	protected List<IRune> catalystRunes = new ArrayList<>();
	protected List<IRune> effectRunes = new ArrayList<>();
	protected List<IRune> allRunes = new ArrayList<>();

	protected Map<Integer, List<Tuple<Integer, Integer>>[]> markLinksOutputs = new HashMap<>();
	protected Map<Integer, Tuple<Integer, Integer>[]> markLinksInputs = new HashMap<>();

	protected List<IRuneEffect> runeEffects = new ArrayList<>();

	protected int currentEffectRune = 0;

	protected boolean active = false;

	protected float runeActivationCooldown = 1;

	protected Entity userEntity;
	protected Vec3d userPosition;

	protected World world;

	protected RuneChainBranch currentBranch;
	protected Deque<RuneChainBranch> branches = new LinkedList<>();

	public RuneChain(World world) {
		this.world = world;
	}

	@Override
	public void setUser(Entity entity, Vec3d position) {
		this.userEntity = entity;
		this.userPosition = position;
	}

	@Override
	public Entity getUserEntity() {
		return this.userEntity;
	}

	@Override
	public Vec3d getUserPosition() {
		return this.userPosition;
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public int getMaximumRuneCount() {
		return 32;
	}

	@Override
	public void cleanup() {
		if(!this.runeEffects.isEmpty()) {
			for(IRuneEffect effect : this.runeEffects) {
				effect.cleanup();
			}
		}
	}

	@Override
	public void update() {
		if(this.isActive()) {
			this.updateActiveChain();
		}
	}

	@Override
	public void activate() {
		if(this.effectRunes.size()== 0) {
			return;
		}

		this.branches.clear();
		this.currentBranch = new RuneChainBranch(this);
		this.branches.push(this.currentBranch);

		this.runeEffects.clear();
		this.runeActivationCooldown = 1;
		this.active = true;
		this.currentEffectRune = 0;
	}

	protected void updateActiveChain() {
		long start = System.nanoTime();

		this.runeActivationCooldown--;

		while(this.isActive() && this.runeActivationCooldown <= 0.0001F) {
			IRune pendingRune = this.effectRunes.get(this.currentEffectRune);

			boolean doBranching = false;
			Deque<RuneChainBranch> newBranches = null;
			if(pendingRune.branch()) {
				doBranching = true;
				newBranches = new LinkedList<>();
			}

			Iterator<RuneChainBranch> branchIT = this.branches.iterator();

			boolean activated = false;

			float cooldown = 0.0F;

			int i = 0;

			System.out.println("BRANCH COUNT: " + this.branches.size());

			while(branchIT.hasNext()) {
				RuneChainBranch sourceBranch = this.currentBranch = branchIT.next();

				IRuneMarkContainer runeMarkContainer = this.getRuneMarks(this.catalystRunes.size() + this.currentEffectRune);

				float chainCostMultiplier = pendingRune.getChainCostMultiplier(runeMarkContainer);

				CombinatorialIterator it = runeMarkContainer.getCombinations();

				boolean removeBranch = false;

				while(it.hasNext()) {
					IRuneMarkContainer iterationRuneMarkContainer = it.next();

					if(doBranching) {
						//System.out.println("BRANCH OFF: " + iterationRuneMarkContainer.getMark(0, 0));
						this.currentBranch = new RuneChainBranch(this, sourceBranch);
						this.currentBranch.setAvailableMarks(this.catalystRunes.size() + this.currentEffectRune, iterationRuneMarkContainer);
						newBranches.push(this.currentBranch);
						removeBranch = true;
					}

					if(this.tryActivateRune(pendingRune, iterationRuneMarkContainer, i > 0 ? chainCostMultiplier : 1)) {
						//Use the maximum cooldown as cooldown until the next rune can be activated
						cooldown = Math.max(cooldown, pendingRune.getDuration(iterationRuneMarkContainer));
						activated = true;
					} else {
						//TODO: Cancel whole chain if a rune ran out of aspect since any following branch won't be able to activate either

						//Chain can no longer execute
						//this.stop();
						//break;
						if(doBranching) {
							newBranches.pop();
						} else {
							removeBranch = true;
						}
						continue;
					}

					i++;
				}

				if(removeBranch) {
					branchIT.remove();
				}
			}

			if(doBranching) {
				this.branches = newBranches;
			}

			if(!activated){
				//Chain can no longer execute
				this.stop();
			} else {
				this.runeActivationCooldown += cooldown;
				this.currentEffectRune++;

				if(this.currentEffectRune == this.getRuneCount()) {
					this.stop();
				}
			}
		}

		for(IRuneEffect effect : this.runeEffects) {
			effect.update();
		}

		System.out.println("T: " + (System.nanoTime() - start) / 1000000.0F);
	}

	protected boolean tryActivateRune(IRune rune, IRuneMarkContainer marks, float chainCostMultiplier) {
		if(rune.canActivate(marks)) {
			int cost = rune.getCost(marks);

			//Apply chain cost multiplier to all following activations
			cost = MathHelper.ceil(cost * chainCostMultiplier);

			if(rune.getAspect().amount >= cost) {
				//Activate rune and get effect
				IRuneEffect effect = rune.activate(marks);

				if(effect != null) {
					//Activate rune effect
					effect.activate();
					this.runeEffects.add(effect);
				}

				//Get provided marks
				IRuneMarkContainer generatedMarks = rune.generateRuneMarks(Optional.of(marks));

				//Store provided marks
				this.currentBranch.storeMarks(this.catalystRunes.size() + this.currentEffectRune, generatedMarks);

				rune.drain(cost, false);

				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public void stop() {
		this.active = false;

		for(IRuneEffect effect : this.runeEffects) {
			effect.finishChain();
		}
	}

	@Override
	public int getPendingRune() {
		return this.catalystRunes.size() + this.currentEffectRune;
	}

	@Override
	public void isCatalystActive() {
		//TODO: Catalyst logic
	}

	@Override
	public boolean addRune(IRune rune) {
		rune.setChain(this, this.allRunes.size());
		if(rune.getType() == RuneType.CATALYST) {
			this.catalystRunes.add(rune);
		} else {
			this.effectRunes.add(rune);
		}
		this.allRunes.add(rune);
		return true;
	}

	@Override
	public boolean addRune(IRune rune, int slot) {
		if(slot < 0 || slot >= this.allRunes.size()) {
			return false;
		}
		rune.setChain(this, slot);
		if(rune.getType() == RuneType.CATALYST) {
			this.catalystRunes.add(slot, rune);
		} else {
			this.effectRunes.add(slot - this.catalystRunes.size(), rune);
		}
		this.allRunes.add(slot, rune);
		return true;
	}

	@Override
	@Nullable
	public IRune removeRune(int slot) {
		if(slot < 0 || slot >= this.allRunes.size()) {
			return null;
		}
		IRune rune = this.getRune(slot);
		if(rune.getType() == RuneType.CATALYST) {
			this.catalystRunes.remove(slot);
		} else {
			this.effectRunes.remove(slot - this.catalystRunes.size());
		}
		this.allRunes.remove(slot);
		this.markLinksInputs.remove(slot);
		this.markLinksOutputs.remove(slot);
		rune.setChain(null, 0);
		return rune;
	}

	@Override
	public int getRuneCount() {
		return this.allRunes.size();
	}

	@Override
	@Nullable
	public IRune getRune(int slot) {
		if(slot < 0 || slot >= this.allRunes.size()) {
			return null;
		}
		return this.allRunes.get(slot);
	}

	@Override
	public int getRuneLinkCount(int slot, int markIndex) {
		List<Tuple<Integer, Integer>>[] outputLinks = this.markLinksOutputs.get(slot);
		if(outputLinks != null && markIndex >= 0 && markIndex < outputLinks.length) {
			List<Tuple<Integer, Integer>> markOutputLinks = outputLinks[markIndex];
			if(markOutputLinks != null) {
				return markOutputLinks.size();
			}
		}
		return 0;
	}

	@Override
	@Nullable
	public IRuneLink getRuneLink(int slot, int markIndex, int linkIndex) {
		List<Tuple<Integer, Integer>>[] outputLinks = this.markLinksOutputs.get(slot);
		if(outputLinks != null && markIndex >= 0 && markIndex < outputLinks.length) {
			List<Tuple<Integer, Integer>> markOutputLinks = outputLinks[markIndex];
			if(markOutputLinks != null && linkIndex >= 0 && linkIndex < markOutputLinks.size()) {
				Tuple<Integer, Integer> link = markOutputLinks.get(linkIndex);
				return new RuneLink(this, slot, markIndex, link.getFirst(), link.getSecond());
			}
		}
		Tuple<Integer, Integer>[] inputLinks = this.markLinksInputs.get(slot);
		if(inputLinks != null && markIndex >= 0 && markIndex < inputLinks.length) {
			Tuple<Integer, Integer> link = inputLinks[markIndex];
			return new RuneLink(this, link.getFirst(), link.getSecond(), slot, markIndex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean linkRune(int outputRuneSlot, int outputMarkIndex, int inputRuneSlot, int inputMarkIndex) {
		if(outputRuneSlot < 0 || outputRuneSlot >= this.allRunes.size()) return false;
		if(inputRuneSlot < 0 || inputRuneSlot >= this.allRunes.size()) return false;

		IRune outputRune = this.getRune(outputRuneSlot);
		IRuneMarkContainer outputRuneMarks = outputRune.generateRuneMarks(Optional.<IRuneMarkContainer>empty()); 
		IRune inputRune = this.getRune(inputRuneSlot);
		IRuneMarkContainer inputRuneMarks = inputRune.getRequiredRuneMarks();

		List<Tuple<Integer, Integer>>[] outputLinks = this.markLinksOutputs.get(outputRuneSlot);
		if(outputLinks == null) {
			this.markLinksOutputs.put(outputRuneSlot, outputLinks = new ArrayList[outputRuneMarks.getSlotCount()]);
		}

		if(outputMarkIndex < 0 || outputMarkIndex >= outputRuneMarks.getSlotCount()) return false;
		if(inputMarkIndex < 0 || inputMarkIndex >= inputRuneMarks.getSlotCount()) return false;

		if(!inputRuneMarks.getMark(inputMarkIndex, 0).isApplicable(outputRuneMarks.getMark(outputMarkIndex, 0))) return false;

		List<Tuple<Integer, Integer>> slotOutputLinks = outputLinks[outputMarkIndex];

		if(slotOutputLinks == null) {
			outputLinks[outputMarkIndex] = slotOutputLinks = new ArrayList<>();
		} else {
			for(Tuple<Integer, Integer> link : slotOutputLinks) {
				if(link.getFirst() == inputRuneSlot && link.getSecond() == inputMarkIndex) {
					//Already linked
					return false;
				}
			}
		}

		slotOutputLinks.add(new Tuple<>(inputRuneSlot, inputMarkIndex));

		Tuple<Integer, Integer>[] slotInputLinks = this.markLinksInputs.get(inputRuneSlot);
		if(slotInputLinks == null) {
			this.markLinksInputs.put(inputRuneSlot, slotInputLinks = new Tuple[inputRuneMarks.getSlotCount()]);
		}

		slotInputLinks[inputMarkIndex] = new Tuple<>(outputRuneSlot, outputMarkIndex);

		return true;
	}

	@Override
	public boolean unlinkRune(int outputRuneSlot, int outputMarkIndex, int inputRuneSlot, int inputMarkIndex) {
		if(outputRuneSlot < 0 || outputRuneSlot >= this.allRunes.size()) return false;
		if(inputRuneSlot < 0 || inputRuneSlot >= this.allRunes.size()) return false;

		IRune outputRune = this.getRune(outputRuneSlot);
		IRuneMarkContainer outputRuneMarks = outputRune.generateRuneMarks(Optional.<IRuneMarkContainer>empty()); 
		IRune inputRune = this.getRune(inputRuneSlot);
		IRuneMarkContainer inputRuneMarks = inputRune.getRequiredRuneMarks();

		if(outputMarkIndex < 0 || outputMarkIndex >= outputRuneMarks.getSlotCount()) return false;
		if(inputMarkIndex < 0 || inputMarkIndex >= inputRuneMarks.getSlotCount()) return false;

		boolean unlinked = false;

		List<Tuple<Integer, Integer>>[] outputLinks = this.markLinksOutputs.get(outputRuneSlot);
		if(outputLinks != null) {
			List<Tuple<Integer, Integer>> slotOutputLinks = outputLinks[outputMarkIndex];

			Iterator<Tuple<Integer, Integer>> it = slotOutputLinks.iterator();
			while(it.hasNext()) {
				Tuple<Integer, Integer> link = it.next();
				if(link.getFirst() == inputRuneSlot && link.getSecond() == inputMarkIndex) {
					it.remove();
					unlinked = true;
				}
			}
		}

		Tuple<Integer, Integer>[] slotInputLinks = this.markLinksInputs.get(inputRuneSlot);
		if(slotInputLinks != null) {
			Tuple<Integer, Integer> link = slotInputLinks[inputMarkIndex];
			if(link.getFirst() == outputRuneSlot && link.getSecond() == outputMarkIndex) {
				slotInputLinks[inputMarkIndex] = null;
				unlinked = true;
			}
		}

		return unlinked;
	}

	@Override
	public IRuneMarkContainer getRuneMarks(int slot) {
		return this.currentBranch.getAvailableMarks(slot);
	}
}
