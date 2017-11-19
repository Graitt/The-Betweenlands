package thebetweenlands.api.herblore.rune;

import javax.annotation.Nullable;

/**
 * An immutable (!) container that has multiple slots that can store any number of rune marks
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
}
