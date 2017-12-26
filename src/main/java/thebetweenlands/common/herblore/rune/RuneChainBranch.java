package thebetweenlands.common.herblore.rune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import thebetweenlands.api.herblore.rune.IRune;
import thebetweenlands.api.herblore.rune.IRuneChain;
import thebetweenlands.api.herblore.rune.IRuneLink;
import thebetweenlands.api.herblore.rune.IRuneMark;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneMarkContainer;

public class RuneChainBranch {
	protected Map<Integer, List<IRuneMark>[]> availableMarks = new HashMap<>();

	protected final IRuneChain chain;
	protected final RuneChainBranch parent;

	public RuneChainBranch(IRuneChain chain) {
		this(chain, null);
	}

	public RuneChainBranch(IRuneChain chain, RuneChainBranch parent) {
		this.chain = chain;
		this.parent = parent;
	}

	@SuppressWarnings("unchecked")
	public void storeMarks(int runeSlot, IRuneMarkContainer generatedMarks) {
		List<IRuneMark>[] availableMarkLists = this.availableMarks.get(runeSlot);
		if(availableMarkLists == null) {
			this.availableMarks.put(runeSlot, availableMarkLists = new List[generatedMarks.getSlotCount()]);
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
	}

	@SuppressWarnings("unchecked")
	public void setAvailableMarks(int slot, IRuneMarkContainer container) {
		if(slot >= 0 && slot < this.chain.getRuneCount()) {
			for(int m = 0; m < container.getSlotCount(); m++) {
				IRuneLink link = this.chain.getRuneLink(slot, m, 0);

				List<IRuneMark>[] availableMarkLists = this.availableMarks.get(link.getOutputRuneSlot());
				if(availableMarkLists == null) {
					this.availableMarks.put(link.getOutputRuneSlot(), availableMarkLists = new List[this.chain.getRune(link.getOutputRuneSlot()).generateRuneMarks(Optional.empty()).getSlotCount()]);
				}
				List<IRuneMark> availableMarks = availableMarkLists[link.getOutputMarkIndex()] = new ArrayList<>(container.getMarkCount(m));
				for(int i = 0; i < container.getMarkCount(m); i++) {
					availableMarks.add(container.getMark(m, i));
				}
			}
		}
	}

	public IRuneMarkContainer getAvailableMarks(int slot) {
		if(slot >= 0 && slot < this.chain.getRuneCount()) {
			IRune rune = this.chain.getRune(slot);

			@SuppressWarnings("unchecked")
			List<IRuneMark>[] markLists = new List[rune.getRequiredRuneMarks().getSlotCount()];

			for(int m = 0; m < rune.getRequiredRuneMarks().getSlotCount(); m++) {
				IRuneLink link = this.chain.getRuneLink(slot, m, 0);

				List<IRuneMark>[] availableMarkLists = this.availableMarks.get(link.getOutputRuneSlot());
				if(availableMarkLists == null) {
					return this.getParentAvailableMarks(slot);
				} else if(availableMarkLists.length >= link.getOutputMarkIndex()) {
					List<IRuneMark> availableMarks = availableMarkLists[link.getOutputMarkIndex()];
					if(availableMarks != null) {
						markLists[m] = availableMarks;
					} else {
						markLists[m] = new ArrayList<IRuneMark>();
					}
				}
			}

			return new RuneMarkContainer(markLists);
		}

		return this.getParentAvailableMarks(slot);
	}

	protected IRuneMarkContainer getParentAvailableMarks(int slot) {
		return this.parent != null ? this.parent.getAvailableMarks(slot) : RuneMarkContainer.EMPTY;
	}
}
