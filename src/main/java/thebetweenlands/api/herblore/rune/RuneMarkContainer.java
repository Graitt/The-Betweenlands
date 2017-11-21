package thebetweenlands.api.herblore.rune;

import java.util.List;

import javax.annotation.Nullable;

public class RuneMarkContainer implements IRuneMarkContainer {
	public static final IRuneMarkContainer EMPTY = new RuneMarkContainer(new IRuneMark[0][0]);

	private final IRuneMark[][] marks;

	//TODO This should go
	@Deprecated
	public RuneMarkContainer(List<IRuneMark>[] marks) {
		this.marks = new IRuneMark[marks.length][];
		for(int i = 0; i < marks.length; i++) {
			this.marks[i] = marks[i].toArray(new IRuneMark[0]);
		}
	}

	public RuneMarkContainer(IRuneMark[][] marks) {
		this.marks = marks;
	}

	//TODO This probably too
	@Deprecated
	public RuneMarkContainer(List<IRuneMark> marks) {
		this.marks = new IRuneMark[marks.size()][];
		int i = 0;
		for(IRuneMark mark : marks) {
			this.marks[i] = new IRuneMark[]{mark};
			i++;
		}
	}

	public RuneMarkContainer(IRuneMark[] marks) {
		this.marks = new IRuneMark[marks.length][];
		for(int i = 0; i < marks.length; i++) {
			this.marks[i] = new IRuneMark[]{marks[i]};
		}
	}

	@Override
	public int getSlotCount() {
		return this.marks.length;
	}

	@Override
	public int getMarkCount(int slot) {
		if(slot < 0 || slot >= this.getSlotCount()) return 0;
		return this.marks[slot].length;
	}

	@Override
	@Nullable
	public IRuneMark<?> getMark(int slot, int mark) {
		if(mark < 0 || mark >= this.getMarkCount(slot)) return null;
		return this.marks[slot][mark];
	}

	@Override
	public boolean isSingularContainer() {
		for(IRuneMark[] lst : this.marks) {
			if(lst == null || lst.length != 1) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isApplicable(IRuneMarkContainer container) {
		if(this.getSlotCount() != container.getSlotCount()) {
			return false;
		}
		for(int i = 0; i < this.getSlotCount(); i++) {
			int markCount = this.getMarkCount(i);
			if(markCount != container.getMarkCount(i)) {
				return false;
			}
			for(int m = 0; m < markCount; m++) {
				if(!this.getMark(i, m).isApplicable(container.getMark(i, m))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public CombinatorialIterator getCombinations() {
		return new CombinatorialIterator(this) {
			@Override
			protected IRuneMarkContainer createSingularContainer(IRuneMark[] marks) {
				return new RuneMarkContainer(marks);
			}
		};
	}
}
