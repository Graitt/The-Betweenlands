package thebetweenlands.api.herblore.rune;

import net.minecraft.world.World;

public interface IRuneMark<F> {
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
	 * Returns the marked object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public default <T> T getUnsafe() {
		return (T) this.get();
	}

	/**
	 * Returns whether the specified rune mark can replace this rune mark
	 * @param mark
	 * @return
	 */
	public boolean isApplicable(IRuneMark<?> mark);
}
