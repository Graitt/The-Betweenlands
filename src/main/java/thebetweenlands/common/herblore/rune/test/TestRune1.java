package thebetweenlands.common.herblore.rune.test;

import java.util.Optional;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thebetweenlands.api.herblore.aspect.IAspectType;
import thebetweenlands.api.herblore.rune.DefaultRuneMarks.BlockRuneMark;
import thebetweenlands.api.herblore.rune.IRuneEffect;
import thebetweenlands.api.herblore.rune.IRuneMark;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneType;
import thebetweenlands.common.herblore.rune.AbstractRune;

public class TestRune1 extends AbstractRune {
	private int range;
	
	public TestRune1(IAspectType type, int range) {
		super(type);
		this.range = range;
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
	public IRuneMarkContainer getRequiredRuneMarks() {
		return RuneMarkContainer.EMPTY;
	}

	@Override
	public IRuneEffect activate(IRuneMarkContainer marks) {
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
	public boolean canActivate(IRuneMarkContainer marks) {
		return super.canActivate(marks) && this.getChain().getUserEntity() != null;
	}

	@Override
	protected IRuneMarkContainer provideRuneMarks(Optional<IRuneMarkContainer> marks) {
		if(marks.isPresent()) {
			BlockPos pos = this.getChain().getUserEntity().getPosition();
			World world = this.getChain().getWorld();
			
			long start = System.nanoTime();
			
			IRuneMark[][] generated = new IRuneMark[1][(range*2+1)*(range*2+1)*(range*2+1)];

			int i = 0;
			for(int xo = -range; xo <= range; xo++) {
				for(int yo = -range; yo <= range; yo++) {
					for(int zo = -range; zo <= range; zo++) {
						generated[0][i] = new BlockRuneMark(pos.add(xo, yo, zo), world);
						i++;
					}
				}
			}
			
			//System.out.println("test2");
			
			IRuneMarkContainer container = new RuneMarkContainer(generated);
			
			System.out.println("Gen positions: " + (System.nanoTime() - start) / 1000000F);
			
			return container;
		} else {
			return new RuneMarkContainer(new IRuneMark[][]{
				{
					new BlockRuneMark()
				}
			});
		}

		/*if(marks.isPresent()) {
			Vec3d start = this.getChain().getUserEntity().getPositionEyes(1);
			Vec3d end = start.add(this.getChain().getUserEntity().getLookVec().scale(20));
			RayTraceResult result = this.getChain().getWorld().rayTraceBlocks(start, end);
			if(result.typeOfHit == RayTraceResult.Type.BLOCK) {
				return new RuneMarkContainer(ImmutableList.of(new BlockRuneMark(result.getBlockPos(), this.getChain().getWorld())));
			} else {
				return RuneMarkContainer.EMPTY;
			}
		} else {
			return new RuneMarkContainer(new IRuneMark[][]{
				{
					new BlockRuneMark()
				}
			});
		}*/

		/*int i = 0;
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
		});*/
	}
}
