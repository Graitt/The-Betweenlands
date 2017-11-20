package thebetweenlands.common.herblore.rune.test;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
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
		return new RuneMarkContainer(new IRuneMark[]{new BlockRuneMark()});
	}

	@Override
	public IRuneEffect activate(IRuneMarkContainer marks) {
		BlockPos pos = marks.getMark(0, 0).<BlockPos>getUnsafe();

		System.out.println("ACTIVATE TEST RUNE 2 with marks: " + pos);

		IBlockState state = this.getChain().getWorld().getBlockState(pos);
		TileEntity te = this.getChain().getWorld().getTileEntity(pos);
		if(state.getBlock().canHarvestBlock(this.getChain().getWorld(), pos, (EntityPlayer)this.getChain().getUserEntity())) {
			state.getBlock().harvestBlock(this.getChain().getWorld(), (EntityPlayer)this.getChain().getUserEntity(), pos, state, te, ItemStack.EMPTY);
			this.getChain().getWorld().setBlockToAir(pos);
		}
		return null;
	}

	@Override
	public int getBufferSize() {
		return 100;
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
