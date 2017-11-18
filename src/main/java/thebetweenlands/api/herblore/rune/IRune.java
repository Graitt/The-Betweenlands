package thebetweenlands.api.herblore.rune;

import thebetweenlands.api.herblore.aspect.IAspectType;

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
	 * Infuses this rune with the specified aspect
	 * @param aspect
	 * @return
	 */
	public IInfusedRune infuse(IAspectType aspect);
}
