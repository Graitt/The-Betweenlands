package thebetweenlands.api.herblore.rune;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DefaultRuneMarks {
	public static class BlockRuneMark implements IRuneMark<BlockRuneMark, BlockPos> {
		private final World world;
		private final IBlockState originalState;
		private final BlockPos pos;

		private BlockRuneMark() {
			this.pos = null;
			this.originalState = null;
			this.world = null;
		}

		private BlockRuneMark(BlockPos pos, IBlockState originalState, World world) {
			this.pos = pos;
			this.originalState = originalState;
			this.world = world;
		}

		@Override
		public boolean isValid() {
			return this.originalState == null || this.world == null || this.pos == null ? false : this.originalState.getBlock() == this.world.getBlockState(this.pos);
		}

		@Override
		public BlockRuneMark create(BlockPos object, World world) {
			return new BlockRuneMark(object, world.getBlockState(object), world);
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
		public boolean isApplicable(IRuneMark<?, ?> mark) {
			return mark.getType() == this.getType() && this.pos.equals(mark.get());
		}

		@Override
		public String getUnlocalizedName() {
			return "rune_mark.block";
		}
	}

	public static class EntityRuneMark implements IRuneMark<EntityRuneMark, Entity> {
		private final World world;
		private final Entity entity;

		private EntityRuneMark() {
			this.entity = null;
			this.world = null;
		}

		private EntityRuneMark(Entity entity, World world) {
			this.entity = entity;
			this.world = world;
		}

		@Override
		public boolean isValid() {
			return this.entity == null || this.world == null || !this.entity.isEntityAlive() ? false : true;
		}

		@Override
		public EntityRuneMark create(Entity object, World world) {
			return new EntityRuneMark(object, world);
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
		public boolean isApplicable(IRuneMark<?, ?> mark) {
			return mark.getType() == this.getType() && this.entity == mark.get();
		}

		@Override
		public String getUnlocalizedName() {
			return "rune_mark.entity";
		}
	}

	public static final BlockRuneMark BLOCK = new BlockRuneMark();
	public static final EntityRuneMark ENTITY = new EntityRuneMark();
}
