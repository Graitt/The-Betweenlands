package thebetweenlands.api.herblore.rune;

import net.minecraft.util.ITickable;

public interface IRuneEffect extends ITickable {
	/**
	 * Called when the rune effect should no longer affect the world
	 */
	public void cleanup();
	
	/**
	 * Updates the rune effect every tick until the chain finishes
	 */
	@Override
	public void update();
	
	/**
	 * Called when the effect is activated
	 */
	public void activate();
	
	/**
	 * Called when the rune chain is finished
	 */
	public void finishChain();
}
