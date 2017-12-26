package thebetweenlands.api.herblore.rune;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * A rune chain consists of multiple runes that are activated one after another.
 * At least one catalyst rune is required for the rune chain to be able to activate itself.
 * <p>
 * Generally, the rune chain runs from the beginning to the end by activating one rune at a time.
 * However, if the next rune's {@link IRune#branch()} returns true, the chain can branch into
 * multiple new branches (ie the rune chain will branch off for each input mark combination). Each of those
 * new branches will contain exactly one of the input mark combinations (ie one mark per slot). 
 * All branches run at the same pace, so one rune activation per branch per rune chain step.
 * If a rune that does not branch off and has multiple marks available per input, then that rune is
 * activated multiple times, once for each possible input mark combination. If this occurs or multiple branches are 
 * updated in one step, no cooldown is applied until all marks or branches have been processed, and only then the highest cooldown 
 * that any of the activations in the branches would have produced is applied once to the rune chain after the step has ended.
 * If a rune produces marks then those marks are only added to the current branch.
 * If a rune's {@link IRune#canActivate(IRuneMarkContainer)} for a given rune mark combination returns false, only the current branch is stopped and removed, all
 * other branches will continue.
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
	 * Returns how many runes are linked to the specified rune slot and mark index.
	 * At most 1 for input runes.
	 * @param slot Rune Slot
	 * @param markIndex Mark Index
	 * @return
	 */
	public int getRuneLinkCount(int slot, int markIndex);

	/**
	 * Returns the rune link at the specified output rune slot and mark index
	 * @param slot Rune Slot
	 * @param markIndex Mark Index
	 * @param linkIndex Link Index, see {@link #getRuneLinkCount(int, int)}
	 * @return
	 */
	@Nullable
	public IRuneLink getRuneLink(int slot, int markIndex, int linkIndex);

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
