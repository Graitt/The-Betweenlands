package thebetweenlands.api.herblore.rune;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.ITickable;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * A rune chain consists of multiple runes that are activated one after another.
 * At least one catalyst rune is required for the rune chain to be able to activate itself.
 * 
 * Generally, the rune chain runs from the beginning to the end by activating one rune at a time.
 * However, if the next rune is a {@link RuneType#PREDICATE} type rune the chain can branch into
 * multiple new branches, see {@link RuneType#PREDICATE} for details. All branches run at the same pace, so
 * one rune activation per branch per rune chain step.
 * If a non-predicate rune has mark inputs that have multiple marks available per input, then that rune is
 * activated multiple times, once for each possible input mark combination. If this occurs or multiple branches are 
 * updated in one step, no cooldown is applied until all marks or branches have been processed, and only then the highest cooldown that any of the activations
 * in the branches would have produced is applied once to the rune chain after the step has ended.
 * If a {@link RuneType#MARK} type rune produces marks then those marks are only added to the current branch.
 */
public interface IRuneChain extends ITickable {
	/**
	 * Sets the entity that is currently using, or a position where the rune is being used at
	 * @param entity
	 */
	public void setUser(@Nullable Entity entity, @Nullable Vec3d position);
	
	/**
	 * Returns the user entity
	 * @return
	 */
	@Nullable
	public Entity getUserEntity();
	
	/**
	 * Returns the user position
	 * @return
	 */
	@Nullable
	public Vec3d getUserPosition();
	
	/**
	 * Returns the world
	 * @return
	 */
	public World getWorld();
	
	/**
	 * Called when the rune chain should no longer affect the world
	 */
	public void cleanup();

	/**
	 * Updates the rune chain every tick.
	 * Runs through the chain if the rune chain is active and activates the
	 * runes according to {@link RuneType}
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
	 * Stops the rune chain if it is currently running
	 */
	public void stop();

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
	 * @param rune Rune
	 * @return
	 */
	public boolean addRune(IRune rune);

	/**
	 * Inserts a new rune at the specified slot and pushes all runes behind one further
	 * @param rune Rune
	 * @param slot Rune slot
	 * @return
	 */
	public boolean addRune(IRune rune, int slot);

	/**
	 * Removes a rune from the chain
	 * @param slot Rune slot
	 * @return
	 */
	@Nullable
	public IRune removeRune(int slot);

	/**
	 * Returns the maximum rune count
	 * @return
	 */
	public int getMaximumRuneCount();

	/**
	 * Returns how many runes this rune chain has
	 * @return
	 */
	public int getRuneCount();

	/**
	 * Returns the rune in the specified slot
	 * @param slot Rune slot
	 * @return
	 */
	@Nullable
	public IRune getRune(int slot);

	/**
	 * Returns how many runes are linked to the specified slot and output mark index
	 * @param slot Rune slot
	 * @return
	 */
	public int getLinkedInputRuneCount(int slot, int outputMarkIndex);

	/**
	 * Returns the rune and output mark index that is linked to the specified slot and input mark index
	 * @param slot Rune slot
	 * @param mark Input mark index
	 * @return
	 */
	@Nullable
	public Tuple<Integer, Integer> getLinkedOutputRune(int slot, int inputMarkIndex);

	/**
	 * Returns the input rune and input rune mark index that are is to the specified slot, output mark index and index
	 * @param slot Rune slot
	 * @param outputMarkIndex Output mark index
	 * @param index Link index, see {@link #getLinkedInputRuneCount(int, int)}
	 * @return
	 */
	@Nullable
	public Tuple<Integer, Integer> getLinkedInputRune(int slot, int outputMarkIndex, int index);

	/**
	 * Returns all rune marks that are currently available to the specified slot
	 * @param slot Rune slot
	 * @return
	 */
	public IRuneMarkContainer getRuneMarks(int slot);

	/**
	 * Links a rune mark output with a rune mark input
	 * @param outputRuneSlot Output rune slot
	 * @param outputMarkIndex Output mark index
	 * @param inputRuneSlot Input rune slot
	 * @param inputMarkIndex Input mark index
	 * @return
	 */
	public boolean linkRune(int outputRuneSlot, int outputMarkIndex, int inputRuneSlot, int inputMarkIndex);

	/**
	 * Unlinks a rune mark output from a rune mark input
	 * @param outputRuneSlot Output rune slot
	 * @param outputMarkIndex Output mark index
	 * @param inputRuneSlot Input rune slot
	 * @param inputMarkIndex Input mark index
	 * @return
	 */
	public boolean unlinkRune(int outputRuneSlot, int outputMarkIndex, int inputRuneSlot, int inputMarkIndex);
}
