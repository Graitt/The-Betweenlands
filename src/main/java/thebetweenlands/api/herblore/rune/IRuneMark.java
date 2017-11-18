package thebetweenlands.api.herblore.rune;

import net.minecraft.world.World;

public interface IRuneMark<T extends IRuneMark<T, F>, F> {
	/**
	 * Returns the unlocalized name of this rune mark
	 * @return
	 */
	public String getUnlocalizedName();
	
	/**
	 * Returns whether the rune mark is still valid at this point in time
	 * @return
	 */
	public boolean isValid();

	/**
	 * Creates a new rune mark for the given object
	 * @param object The object that should be marked
	 * @param world The world the object is in
	 * @param 
	 * @return
	 */
	public T create(F object, World world);

	/**
	 * Returns the object type
	 * @return
	 */
	public Class<F> getType();

	/**
	 * Returns the marked object
	 * @return
	 */
	public F get();

	/**
	 * Returns whether the specified rune mark can replace this rune mark
	 * @param mark
	 * @return
	 */
	public boolean isApplicable(IRuneMark<?, ?> mark);
}
