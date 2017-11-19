package thebetweenlands.api.herblore.rune;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DefaultRuneMarks {
	public static class BlockRuneMark implements IRuneMark<BlockPos> {
		private final World world;
		private final IBlockState originalState;
		private final BlockPos pos;

		public BlockRuneMark() {
			this.world = null;
			this.originalState = null;
			this.pos = null;
		}
		
		public BlockRuneMark(BlockPos pos, World world) {
			this.pos = pos;
			this.originalState = world.getBlockState(pos);
			this.world = world;
		}

		@Override
		public boolean isValid() {
			return this.originalState == null || this.world == null || this.pos == null ? false : this.originalState.getBlock() == this.world.getBlockState(this.pos);
		}

		@Override
		public BlockPos get() {
			return this.pos;
		}

		@Override
		public Class<BlockPos> getType() {
			return BlockPos.class;
		}

		@Override
		public boolean isApplicable(IRuneMark<?> mark) {
			return this.getType().isAssignableFrom(mark.getType()) && (this.isValid() == mark.isValid() || this.pos.equals(mark.get()));
		}

		@Override
		public String getUnlocalizedName() {
			return "rune_mark.block";
		}
	}

	public static class EntityRuneMark implements IRuneMark<Entity> {
		private final World world;
		private final Entity entity;

		public EntityRuneMark() {
			this.world = null;
			this.entity = null;
		}
		
		public EntityRuneMark(Entity entity, World world) {
			this.entity = entity;
			this.world = world;
		}

		@Override
		public boolean isValid() {
			return this.entity == null || this.world == null || !this.entity.isEntityAlive() ? false : true;
		}

		@Override
		public Entity get() {
			return this.entity;
		}

		@Override
		public Class<Entity> getType() {
			return Entity.class;
		}

		@Override
		public boolean isApplicable(IRuneMark<?> mark) {
			return mark.getType() == this.getType() && (this.isValid() == mark.isValid() || this.entity.equals(mark.get()));
		}

		@Override
		public String getUnlocalizedName() {
			return "rune_mark.entity";
		}
	}
}
