package thebetweenlands.api.herblore.rune;

import java.util.Optional;

import javax.annotation.Nullable;

import thebetweenlands.api.herblore.aspect.Aspect;

public interface IRune {
	/**
	 * Returns the unlocalized name of this rune
	 * @return
	 */
	public String getUnlocalizedName();

	/**
	 * Returns the type of this rune
	 * @return
	 */
	public RuneType getType();

	/**
	 * Sets the rune chain and slot that this rune is in
	 * @param chain
	 * @param slot
	 */
	public void setChain(IRuneChain chain, int slot);

	/**
	 * Returns the rune chain
	 * @return
	 */
	public IRuneChain getChain();

	/**
	 * Returns the rune chain slot this rune is in
	 * @return
	 */
	public int getChainSlot();

	/**
	 * Returns the required rune marks in a rune mark container
	 * @return
	 */
	public IRuneMarkContainer getRequiredRuneMarks();

	/**
	 * Returns whether the rune can activate with the given rune mark combination.
	 * <p><b>Only accepts singular rune mark containers</b> ({@link IRuneMarkContainer#isSingularContainer()})
	 * @param marks Rune mark container with one rune mark combination (ie <b>exactly one</b> mark per slot)
	 * @return
	 */
	public boolean canActivate(IRuneMarkContainer marks);

	/**
	 * Returns whether this rune causes branching of the rune chain. See {@link IRuneChain} for details
	 * @return
	 */
	public boolean branch();

	/**
	 * Activates this rune and returns the rune effect that should be activated.
	 * The effect may be null
	 */
	@Nullable
	public IRuneEffect activate(IRuneMarkContainer marks);

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
	 * @param conversion Whether the amount should be multiplied by {@link #getFillRatio()} and then floored
	 * @return How much was drained
	 */
	public int drain(int amount, boolean conversion);

	/**
	 * Fills the aspect buffer
	 * @param amount The actual amount that was added
	 * @param conversion Whether the amount should be multiplied by {@link #getFillRatio()} and then floored
	 * @return How much was added
	 */
	public int fill(int amount, boolean conversion);

	/**
	 * Returns the activation cost of this rune with the given rune marks
	 * @param marks
	 * @return
	 */
	public int getCost(IRuneMarkContainer marks);

	/**
	 * Returns the aspect on this rune
	 * @return
	 */
	public Aspect getAspect();

	/**
	 * Returns the time in ticks that is required until the next rune in the chain can be activated.
	 * Can be a fraction of a tick
	 * @param marks
	 * @return
	 */
	public float getDuration(IRuneMarkContainer marks);

	/**
	 * If no value is present this returns what rune marks can be returned.
	 * If a value is present this creates rune mark instances for the given rune marks
	 * @param marks
	 * @return
	 */
	public IRuneMarkContainer generateRuneMarks(Optional<IRuneMarkContainer> marks);

	/**
	 * Returns the chain cost multiplier. 
	 * All immediately following rune activation costs of this rune will be multiplied by this multiplier and then ceiled
	 * @param marks
	 * @return
	 */
	public float getChainCostMultiplier(IRuneMarkContainer marks);
}
