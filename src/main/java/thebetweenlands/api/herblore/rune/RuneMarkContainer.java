package thebetweenlands.api.herblore.rune;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class RuneMarkContainer implements IRuneMarkContainer {
	public static final IRuneMarkContainer EMPTY = new RuneMarkContainer();

	private final List<IRuneMark>[] marks;

	@SuppressWarnings("unchecked")
	public RuneMarkContainer() {
		this.marks = new List[0];
	}

	@SuppressWarnings("unchecked")
	public RuneMarkContainer(List<IRuneMark>[] marks) {
		this.marks = new List[marks.length];
		for(int i = 0; i < marks.length; i++) {
			List<IRuneMark> lst = new ArrayList<IRuneMark>();
			if(marks[i] != null)
				lst.addAll(marks[i]);
			this.marks[i] = lst;
		}
	}

	@SuppressWarnings("unchecked")
	public RuneMarkContainer(IRuneMark[][] marks) {
		this.marks = new List[marks.length];
		for(int i = 0; i < marks.length; i++) {
			List<IRuneMark> lst = new ArrayList<IRuneMark>();
			if(marks[i] != null) {
				for(IRuneMark m : marks[i]) {
					lst.add(m);
				}
			}
			this.marks[i] = lst;
		}
	}

	@SuppressWarnings("unchecked")
	public RuneMarkContainer(List<IRuneMark> marks) {
		this.marks = new List[marks.size()];
		int i = 0;
		for(IRuneMark mark : marks) {
			List<IRuneMark> lst = new ArrayList<IRuneMark>();
			lst.add(mark);
			this.marks[i] = lst;
			i++;
		}
	}

	@SuppressWarnings("unchecked")
	public RuneMarkContainer(IRuneMark[] marks) {
		this.marks = new List[marks.length];
		int i = 0;
		for(IRuneMark mark : marks) {
			List<IRuneMark> lst = new ArrayList<IRuneMark>();
			lst.add(mark);
			this.marks[i] = lst;
			i++;
		}
	}

	@Override
	public int getSlotCount() {
		return this.marks.length;
	}

	@Override
	public int getMarkCount(int slot) {
		if(slot < 0 || slot >= this.getSlotCount()) return 0;
		return this.marks[slot].size();
	}

	@Override
	@Nullable
	public IRuneMark<?> getMark(int slot, int mark) {
		if(slot < 0 || slot >= this.getSlotCount()) return null;
		if(mark < 0 || mark >= this.getMarkCount(slot)) return null;
		return this.marks[slot].get(mark);
	}

	@Override
	public boolean isSingularContainer() {
		for(List<IRuneMark> lst : this.marks) {
			if(lst == null || lst.isEmpty() || lst.size() != 1) {
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
