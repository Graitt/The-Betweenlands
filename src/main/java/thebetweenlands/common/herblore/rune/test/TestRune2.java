package thebetweenlands.common.herblore.rune.test;

import thebetweenlands.api.herblore.aspect.IAspectType;
import thebetweenlands.api.herblore.rune.DefaultRuneMarks.BlockRuneMark;
import thebetweenlands.api.herblore.rune.IRuneEffect;
import thebetweenlands.api.herblore.rune.IRuneMark;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneType;
import thebetweenlands.common.herblore.rune.AbstractRune;

public class TestRune2 extends AbstractRune {
	public TestRune2(IAspectType type) {
		super(type);
	}

	@Override
	public String getUnlocalizedName() {
		return "test_rune_2";
	}

	@Override
	public RuneType getType() {
		return RuneType.EFFECT;
	}

	@Override
	public void update() { }

	@Override
	public IRuneMarkContainer getRequiredRuneMarks() {
		return new RuneMarkContainer(new IRuneMark[]{new BlockRuneMark(), new BlockRuneMark()});
	}

	@Override
	public IRuneEffect activate(IRuneMarkContainer marks) {
		System.out.println("ACTIVATE TEST RUNE 2 with marks: " + marks.getMark(0, 0).get() + " " + marks.getMark(1, 0).get());
		return null;
	}

	@Override
	public int getBufferSize() {
		return 20;
	}

	@Override
	public float getFillRatio() {
		return 1;
	}

	@Override
	public int getCost(IRuneMarkContainer marks) {
		return 1;
	}

	@Override
	public float getDuration(IRuneMarkContainer marks) {
		return 0.1F;
	}

	@Override
	public float getChainCostMultiplier(IRuneMarkContainer marks) {
		return 0.1F;
	}
}
