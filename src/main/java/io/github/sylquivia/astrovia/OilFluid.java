package io.github.sylquivia.astrovia;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class OilFluid extends FlowableFluid {
	@Override
	public Fluid getFlowing() {
		return AstroviaFluids.FLOWING_OIL;
	}

	@Override
	public Fluid getStill() {
		return AstroviaFluids.OIL;
	}

	@Override
	protected boolean isInfinite(World world) {
		return false;
	}

	@Override
	protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {

	}

	@Override
	protected int getFlowSpeed(WorldView world) {
		return 1;
	}

	@Override
	protected int getLevelDecreasePerBlock(WorldView world) {
		return 2;
	}

	@Override
	public Item getBucketItem() {
		return AstroviaFluids.OIL_BUCKET;
	}

	@Override
	protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
		return false;
	}

	@Override
	public int getTickRate(WorldView world) {
		return 10;
	}

	@Override
	protected float getBlastResistance() {
		return 100;
	}

	@Override
	protected BlockState toBlockState(FluidState state) {
		return AstroviaFluids.OIL_BLOCK.getDefaultState().with(FluidBlock.LEVEL, Integer.valueOf(getBlockStateLevel(state)));
	}

	@Override
	public boolean matchesType(Fluid fluid) {
		return fluid == getStill() || fluid == getFlowing();
	}

	public static class Flowing extends OilFluid {
		@Override
		protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.add(LEVEL);
		}

		@Override
		public boolean isSource(FluidState state) {
			return false;
		}

		@Override
		public int getLevel(FluidState state) {
			return state.get(LEVEL);
		}
	}

	public static class Still extends OilFluid {

		@Override
		public boolean isSource(FluidState state) {
			return true;
		}

		@Override
		public int getLevel(FluidState state) {
			return 8;
		}
	}
}
