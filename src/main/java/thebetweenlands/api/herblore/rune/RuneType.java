package thebetweenlands.api.herblore.rune;

/**
 * The rune type determines how a rune in the chain is processed
 */
public enum RuneType {
	/**
	 * A catalyst rune serves as the trigger of a rune chain.
	 * If the rune chain is inactive and certain condition is met the catalyst rune will activate and run
	 * the rune chain
	 */
	CATALYST, 

	/**
	 * Mark runes can mark certain objects in the world (for example entities or blocks) and then
	 * provide those as targets to other runes
	 */
	MARK, 

	/**
	 * A predicate rune checks for a certain condition and cancels the current branch if that condition is not met.
	 * Predicate runes should not cause any side effects
	 */
	PREDICATE, 

	/**
	 * Effect runes apply the actual effect to marked target(s)
	 */
	EFFECT;
}