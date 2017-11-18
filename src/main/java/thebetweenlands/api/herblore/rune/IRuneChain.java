package thebetweenlands.api.herblore.rune;

import javax.annotation.Nullable;

import net.minecraft.util.ITickable;
import thebetweenlands.api.herblore.aspect.IAspectType;

/**
 * A rune chain consists of multiple runes that are activated one after another.
 * A rune chain requires at least one catalyst rune and one effect rune to activate itself
 */
public interface IRuneChain extends ITickable {
	/**
	 * Called when the rune chain should no longer affect the world
	 */
	public void cleanup();
	
	/**
	 * Updates the rune chain every tick
	 */
	@Override
	public void update();

	/**
	 * Activates the rune chain, excluding catalyst runes
	 */
	public void activate();

	/**
	 * Returns whether this rune chain is currently active
	 * @return
	 */
	public boolean isActive();

	/**
	 * Cancels the rune chain if it is currently running
	 * @param refund Whether the used up aspects should be refunded
	 */
	public void cancel(boolean refund);

	/**
	 * Returns the index of the pending rune that is executed next
	 * @return
	 */
	@Nullable
	public int getPendingRune();

	/**
	 * Returns whether the catalyst is active and the rune chain should be activated
	 */
	public void isCatalystActive();

	/**
	 * Adds a new rune to the chain
	 * @param rune The rune
	 * @param type The aspect type
	 * @return
	 */
	public boolean addRune(IRune rune, IAspectType type);

	/**
	 * Inserts a new rune at the specified slot and pushes all runes behind one further
	 * @param rune The rune
	 * @param type The aspect type
	 * @param slot The slot
	 * @return
	 */
	public boolean addRune(IRune rune, IAspectType type, int slot);

	/**
	 * Removes a rune from the chain
	 * @param slot The slot
	 * @return
	 */
	@Nullable
	public IInfusedRune removeRune(int slot);

	/**
	 * Returns all runes on this rune chain in order
	 * @return
	 */
	public IInfusedRune[] getRunes();

	/**
	 * Returns the rune in the specified slot
	 * @param slot
	 * @return
	 */
	@Nullable
	public IInfusedRune getRune(int slot);

	/**
	 * Returns all runes that are linked to the specified slot
	 * @param slot
	 * @return
	 */
	public IInfusedRune[] getLinkedRunes(int slot);

	/**
	 * Returns all rune marks that are currently available to the specified slot
	 * @param slot
	 * @return
	 */
	public IRuneMark[] getRuneMarks(int slot);
}
