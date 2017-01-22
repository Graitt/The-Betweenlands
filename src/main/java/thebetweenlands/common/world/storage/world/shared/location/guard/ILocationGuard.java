package thebetweenlands.common.world.storage.world.shared.location.guard;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public interface ILocationGuard {
	/**
	 * Sets whether the specified position is guarded
	 * @param world World
	 * @param pos Position
	 * @param guarded Whether the block is guarded
	 */
	public void setGuarded(World world, BlockPos pos, boolean guarded);

	/**
	 * Returns whether the location is guarded at the specified position.
	 * @param world World
	 * @param entity Entity that's checking for the guard
	 * @param pos Position
	 * @return
	 */
	public boolean isGuarded(World world, @Nullable Entity entity, BlockPos pos);

	/**
	 * Clears all guards
	 * @param world
	 * @param guarded
	 */
	public void clear(World world);

	/**
	 * Handles explosions that affect the location
	 * @param world
	 * @param explosion
	 */
	public void handleExplosion(World world, Explosion explosion);

	/**
	 * Writes the guard to NBT
	 * @param nbt
	 * @return
	 */
	public NBTTagCompound writeToNBT(NBTTagCompound nbt);

	/**
	 * Reads the guard from NBT
	 * @param nbt
	 */
	public void readFromNBT(NBTTagCompound nbt);
}
