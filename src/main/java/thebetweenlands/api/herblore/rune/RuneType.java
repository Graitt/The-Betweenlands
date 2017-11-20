package thebetweenlands.api.herblore.rune;

/**
 * The rune type determines how a rune in the chain is processed
 */
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
	 * Before a prediate rune is activated the rune chain creates a new branch for each input mark combination.
	 * Each of those new branches will have exactly one of the input marks.
	 * Then in the new branch the predicate rune is activated, it checks for a certain condition and cancels the current branch if that condition is not met.
	 */
	PREDICATE, 

	/**
	 * Effect runes apply the actual effect to target(s)
	 */
	EFFECT;
}