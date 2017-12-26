package thebetweenlands.common.herblore.rune.test;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import thebetweenlands.api.herblore.aspect.IAspectType;
import thebetweenlands.api.herblore.rune.DefaultRuneMarks.BlockRuneMark;
import thebetweenlands.api.herblore.rune.IRuneEffect;
import thebetweenlands.api.herblore.rune.IRuneMark;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneType;
import thebetweenlands.common.herblore.rune.AbstractRune;

public class TestRune3 extends AbstractRune {
	public TestRune3(IAspectType type) {
		super(type);
	}

	@Override
	public String getUnlocalizedName() {
		return "test_rune_3";
	}

	@Override
	public RuneType getType() {
		return RuneType.PREDICATE;
	}

	@Override
	public IRuneMarkContainer getRequiredRuneMarks() {
		return new RuneMarkContainer(new IRuneMark[]{new BlockRuneMark()});
	}

	@Override
	public boolean canActivate(IRuneMarkContainer marks) {
		if(super.canActivate(marks)) {
			BlockPos pos = marks.getMark(0, 0).<BlockPos>getUnsafe();
			return this.chain.getWorld().getBlockState(pos).getBlock() == Blocks.GRASS;
			//System.out.println("CHECK TEST RUNE 3 with marks: " + pos);
			//return true;//pos.getY() >= 236;
		}
		return false;
	}

	@Override
	public IRuneEffect activate(IRuneMarkContainer marks) {
		return null;
	}

	@Override
	public int getBufferSize() {
		return 100000;
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
		return 1F;
	}

	@Override
	public float getChainCostMultiplier(IRuneMarkContainer marks) {
		return 0.1F;
	}
}
