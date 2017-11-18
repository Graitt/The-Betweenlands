package thebetweenlands.api.herblore.rune;

import net.minecraft.util.ITickable;
import thebetweenlands.api.herblore.aspect.Aspect;

public interface IInfusedRune extends IRune, ITickable {
	/**
	 * Called when the rune should no longer affect the world
	 */
	public void cleanup();
	
	/**
	 * Updates the rune every tick
	 */
	@Override
	public void update();

	/**
	 * Sets the rune chain and slot that this rune is in
	 * @param chain
	 * @param slot
	 */
	public void setChain(IRuneChain chain, int slot);

	/**
	 * Returns the required rune marks
	 * @return
	 */
	public IRuneMark<?, ?>[] getRequiredRuneMarks();

	/**
	 * Returns whether the rune can activate with the given rune marks
	 * @param marks
	 * @return
	 */
	public boolean canActivate(IRuneMark[] marks);

	/**
	 * Activates this rune
	 */
	public void activate(IRuneMark[] marks);

	/**
	 * Returns the aspect buffer size of this rune
	 * @return
	 */
	public int getBufferSize();

	/**
	 * Returns the aspect refill ratio
	 */
	public float getFillRatio();

	/**
	 * Drains from the aspect buffer
	 * @param amount The actual amount that was drained
	 * @param conversion Whether the amount should be multiplied by {@link #getFillRatio()} (floored)
	 * @return
	 */
	public int drain(int amount, boolean conversion);

	/**
	 * Fills the aspect buffer
	 * @param amount The actual amount that was added
	 * @param conversion Whether the amount should be multiplied by {@link #getFillRatio()} (floored)
	 * @return
	 */
	public int fill(int amount, boolean conversion);

	/**
	 * Returns the activation cost of this rune
	 * @return
	 */
	public int getCost();

	/**
	 * Returns the aspect on this rune
	 * @return
	 */
	public Aspect getAspect();
	
	/**
	 * Returns the time in ticks that is required until the next rune in the chain can be activated.
	 * Can be a fraction of a tick
	 * @return
	 */
	public float getDuration();
}
