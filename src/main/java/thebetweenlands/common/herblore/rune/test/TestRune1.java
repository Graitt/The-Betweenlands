package thebetweenlands.common.herblore.rune.test;

import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import thebetweenlands.api.herblore.aspect.IAspectType;
import thebetweenlands.api.herblore.rune.DefaultRuneMarks.BlockRuneMark;
import thebetweenlands.api.herblore.rune.IRuneEffect;
import thebetweenlands.api.herblore.rune.IRuneMark;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneType;
import thebetweenlands.common.herblore.rune.AbstractRune;

public class TestRune1 extends AbstractRune {
	public TestRune1(IAspectType type) {
		super(type);
	}

	@Override
	public String getUnlocalizedName() {
		return "test_rune_1";
	}

	@Override
	public RuneType getType() {
		return RuneType.MARK;
	}

	@Override
	public void update() { }

	@Override
	public IRuneMarkContainer getRequiredRuneMarks() {
		return RuneMarkContainer.EMPTY;
	}

	@Override
	public IRuneEffect activate(IRuneMarkContainer marks) {
		System.out.println("ACTIVATE TEST RUNE 1");
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
		return 1F;
	}

	@Override
	public float getChainCostMultiplier(IRuneMarkContainer marks) {
		return 1;
	}

	@Override
	protected IRuneMarkContainer provideRuneMarks(Optional<IRuneMarkContainer> marks) {
		int i = 0;
		int k = 0;
		return new RuneMarkContainer(new IRuneMark[][]{
			{
				new BlockRuneMark(new BlockPos(i++, 0, 0), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(i++, 0, 0), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(i++, 0, 0), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(i++, 0, 0), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(i++, 0, 0), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(i++, 0, 0), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(i++, 0, 0), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(i++, 0, 0), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(i++, 0, 0), Minecraft.getMinecraft().world),
			},
			{
				new BlockRuneMark(new BlockPos(0, 0, k++), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(0, 0, k++), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(0, 0, k++), Minecraft.getMinecraft().world),
				new BlockRuneMark(new BlockPos(0, 0, k++), Minecraft.getMinecraft().world)
			}
		});
	}
}
