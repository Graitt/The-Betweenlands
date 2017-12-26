package thebetweenlands.api.herblore.rune;

public interface IRuneLink {
	/**
	 * Returns the rune chain that this link belongs to
	 * @return
	 */
	public IRuneChain getChain();

	/**
	 * Returns the slot of the rune that outputs the rune mark
	 * @return
	 */
	public int getOutputRuneSlot();

	/**
	 * Returns the rune mark index of the output rune
	 * @return
	 */
	public int getOutputMarkIndex();

	/**
	 * Returns the rune slot that uses the the rune mark
	 * @return
	 */
	public int getInputRuneSlot();

	/**
	 * Returns the rune mark index of the input rune
	 * @return
	 */
	public int getInputMarkIndex();
}
