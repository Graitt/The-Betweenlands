package thebetweenlands.api.herblore.rune;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

/**
 * A container that has multiple slots that can store any number of rune marks
 */
public interface IRuneMarkContainer {
	/**
	 * Returns how many mark slots this container has
	 * @return
	 */
	public int getSlotCount();

	/**
	 * Returns how many marks there are in the specified slot
	 * @param slot
	 * @return
	 */
	public int getMarkCount(int slot);

	/**
	 * Returns true if all slots have exactly one rune mark
	 * @return
	 */
	public boolean isSingularContainer();

	/**
	 * Returns a mark from the specified slot
	 * @param slot
	 * @param mark
	 * @return
	 */
	@Nullable
	public IRuneMark<?> getMark(int slot, int mark);

	/**
	 * Returns whether the specified container has the exactly same amount of marks in each slot and 
	 * if all of those marks are applicable to the marks in this container
	 * @param container
	 * @return
	 */
	public boolean isApplicable(IRuneMarkContainer container);

	/**
	 * Returns an iterator that iterates through all possible rune mark combinations
	 * in this rune mark container
	 * @return
	 */
	public CombinatorialIterator getCombinations();

	public static abstract class CombinatorialIterator implements Iterator<IRuneMarkContainer> {
		protected final int count;
		protected final int[] markCounts;
		protected final int[] divs;
		protected final IRuneMarkContainer container;

		protected int index = 0;

		protected CombinatorialIterator(IRuneMarkContainer container) {
			int totalCombinations = 1;
			this.markCounts = new int[container.getSlotCount()];
			this.divs = new int[container.getSlotCount()];
			for(int slot = 0; slot < container.getSlotCount(); slot++) {
				totalCombinations *= (this.markCounts[slot] = container.getMarkCount(slot));
				int div = 1;
				if(slot > 0) {
					for(int divSlotIndex = slot - 1; divSlotIndex < this.markCounts.length - 1; divSlotIndex++) {
						div *= this.markCounts[divSlotIndex];
					}
				}
				this.divs[slot] = div;
			}
			this.count = totalCombinations;
			this.container = container;
		}

		/**
		 * Returns how many combinations there are in total
		 * @return
		 */
		public int getCount() {
			return this.count;
		}

		@Override
		public boolean hasNext() {
			return this.index < this.getCount();
		}

		@Override
		public IRuneMarkContainer next() {
			if(this.index >= this.getCount()) {
				throw new NoSuchElementException();
			}

			IRuneMark[] iterationRuneMarks = new IRuneMark[this.markCounts.length];

			for(int slot = 0; slot < this.markCounts.length; slot++) {
				iterationRuneMarks[slot] = this.container.getMark(slot, (this.index / this.divs[slot]) % this.markCounts[slot]);
			}
			
			this.index++;

			return this.createSingularContainer(iterationRuneMarks);
		}
		
		/**
		 * Creates a new rune mark container from the specified rune marks.
		 * The array indices correspond to the slots. The array may have length 0, the
		 * container should then be empty.
		 * @param marks
		 * @return
		 */
		protected abstract IRuneMarkContainer createSingularContainer(IRuneMark[] marks);
	}
}
