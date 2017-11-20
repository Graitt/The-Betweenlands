package thebetweenlands.common.herblore.rune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import thebetweenlands.api.herblore.rune.IRuneMark;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer.CombinatorialIterator;
import thebetweenlands.api.herblore.rune.RuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneType;

public class RuneChain implements IRuneChain {
	//TODO: Use arrays instead instead of lists/maps?
	protected List<IRune> catalystRunes = new ArrayList<>();
	protected List<IRune> effectRunes = new ArrayList<>();
	protected List<IRune> allRunes = new ArrayList<>();

	protected Map<Integer, List<Tuple<Integer, Integer>>[]> markLinksOutputs = new HashMap<>();
	protected Map<Integer, Tuple<Integer, Integer>[]> markLinksInputs = new HashMap<>();

	protected List<IRuneEffect> runeEffects = new ArrayList<>();

	protected Map<Integer, List<IRuneMark>[]> availableMarks = new HashMap<>();

	protected int currentEffectRune = 0;

	protected boolean active = false;

	protected float runeActivationCooldown = 1;

	protected Entity userEntity;
	protected Vec3d userPosition;

	protected World world;

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
		for(IRune rune : this.allRunes) {
			rune.cleanup();
		}
	}

	@Override
	public void update() {
		for(IRune rune : this.allRunes) {
			rune.update();
		}

		if(this.isActive()) {
			this.updateActiveChain();
		}
	}

	@Override
	public void activate() {
		if(this.effectRunes.size()== 0) {
			return;
		}
		this.availableMarks.clear();
		this.runeEffects.clear();
		this.runeActivationCooldown = 1;
		this.active = true;
		this.currentEffectRune = 0;
	}

	protected void updateActiveChain() {
		this.runeActivationCooldown--;

		while(this.isActive() && this.runeActivationCooldown <= 0.0001F) {
			IRune pendingRune = this.effectRunes.get(this.currentEffectRune);

			if(pendingRune.getType() == RuneType.PREDICATE) {
				//TODO Implement branching!
			}

			IRuneMarkContainer runeMarkContainer = this.getRuneMarks(this.catalystRunes.size() + this.currentEffectRune);

			boolean activated = false;

			float cooldown = 0.0F;

			float chainCostMultiplier = pendingRune.getChainCostMultiplier(runeMarkContainer);

			CombinatorialIterator it = runeMarkContainer.getCombinations();

			int i = 0;
			while(it.hasNext()) {
				IRuneMarkContainer iterationRuneMarkContainer = it.next();

				if(this.tryActivateRune(pendingRune, iterationRuneMarkContainer, i > 0 ? chainCostMultiplier : 1)) {
					//Use the maximum cooldown as cooldown until the next rune can be activated
					cooldown = Math.max(cooldown, pendingRune.getDuration(iterationRuneMarkContainer));
					activated = true;
				} else {
					//Chain can no longer execute
					this.stop();
					break;
				}

				i++;
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
	}

	@SuppressWarnings("unchecked")
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
				List<IRuneMark>[] availableMarkLists = this.availableMarks.get(this.catalystRunes.size() + this.currentEffectRune);
				if(availableMarkLists == null) {
					this.availableMarks.put(this.catalystRunes.size() + this.currentEffectRune, availableMarkLists = new List[generatedMarks.getSlotCount()]);
				}
				for(int m = 0; m < availableMarkLists.length; m++) {
					List<IRuneMark> availableMarkList = availableMarkLists[m];
					if(availableMarkList == null) {
						availableMarkLists[m] = availableMarkList = new ArrayList<>(generatedMarks.getMarkCount(m));
					}
					for(int m2 = 0; m2 < generatedMarks.getMarkCount(m); m2++) {
						availableMarkList.add(generatedMarks.getMark(m, m2));
					}
				}

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
	public int getLinkedInputRuneCount(int slot, int outputMarkIndex) {
		List<Tuple<Integer, Integer>>[] outputLinks = this.markLinksOutputs.get(slot);
		if(outputLinks != null && outputMarkIndex >= 0 && outputMarkIndex < outputLinks.length) {
			List<Tuple<Integer, Integer>> markOutputLinks = outputLinks[outputMarkIndex];
			if(markOutputLinks != null) {
				return markOutputLinks.size();
			}
		}
		return 0;
	}

	@Override
	@Nullable
	public Tuple<Integer, Integer> getLinkedInputRune(int slot, int outputMarkIndex, int index) {
		List<Tuple<Integer, Integer>>[] outputLinks = this.markLinksOutputs.get(slot);
		if(outputLinks != null && outputMarkIndex >= 0 && outputMarkIndex < outputLinks.length) {
			List<Tuple<Integer, Integer>> markOutputLinks = outputLinks[outputMarkIndex];
			if(markOutputLinks != null && index >= 0 && index < markOutputLinks.size()) {
				return markOutputLinks.get(index);
			}
		}
		return null;
	}

	@Override
	@Nullable
	public Tuple<Integer, Integer> getLinkedOutputRune(int slot, int inputMarkIndex) {
		Tuple<Integer, Integer>[] inputLinks = this.markLinksInputs.get(slot);
		if(inputLinks != null && inputMarkIndex >= 0 && inputMarkIndex < inputLinks.length) {
			return inputLinks[inputMarkIndex];
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

	@SuppressWarnings("unchecked")
	@Override
	public IRuneMarkContainer getRuneMarks(int slot) {
		if(slot >= 0 && slot < this.allRunes.size()) {

			IRune rune = this.allRunes.get(slot);

			Tuple<Integer, Integer>[] links = this.markLinksInputs.get(slot);

			if(links != null) {
				List<IRuneMark>[] markLists = new List[rune.getRequiredRuneMarks().getSlotCount()];

				for(int m = 0; m < links.length; m++) {
					List<IRuneMark>[] availableMarkLists = this.availableMarks.get(links[m].getFirst());
					if(availableMarkLists.length >= links[m].getSecond()) {
						List<IRuneMark> availableMarks = availableMarkLists[links[m].getSecond()];
						if(availableMarks != null) {
							markLists[m] = availableMarks;
						} else {
							markLists[m] = new ArrayList<IRuneMark>();
						}
					}
				}

				return new RuneMarkContainer(markLists);
			}
		}

		return new RuneMarkContainer();
	}
}
