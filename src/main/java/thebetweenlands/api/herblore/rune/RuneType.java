package thebetweenlands.api.herblore.rune;

public enum RuneType {
	/**
	 * A catalyst rune serves as the trigger of a rune chain.
	 * If the rune chain is inactive and certain condition is met the catalyst rune will activate and run
	 * the rune chain.
	 */
	CATALYST, 

	/**
	 * Mark runes can mark certain objects in the world (for example entities or blocks) and then
	 * provide those as targets to other runes.
	 */
	MARK, 

	/**
	 * Predicate runes check for a certain condition and cancel the rune chain if that condition is not met.
	 */
	PREDICATE, 

	/**
	 * Effect runes apply the actual effect to target(s)
	 */
	EFFECT;
}