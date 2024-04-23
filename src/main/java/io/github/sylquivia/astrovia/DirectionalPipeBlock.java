package io.github.sylquivia.astrovia;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.ConnectingBlock.*;

public class DirectionalPipeBlock extends BlockWithEntity implements Waterloggable, BlockEntityProvider {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public DirectionalPipeBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState()
			.with(NORTH, false)
			.with(EAST, false)
			.with(SOUTH, false)
			.with(WEST, false)
			.with(UP, false)
			.with(DOWN, false)
			.with(WATERLOGGED, false)
		);
	}

	private boolean canConnectHorizontally(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE)
			|| state.isOf(AstroviaBlocks.FRACTIONATING_COLUMN)
			|| state.isOf(AstroviaBlocks.OIL_HEATER_BLOCK);
	}

	private boolean canConnectVertically(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.FRACTIONATING_COLUMN)
			|| state.isOf(AstroviaBlocks.OIL_HEATER_BLOCK);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockView blockView = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		BlockState blockState = blockView.getBlockState(blockPos.north());
		BlockState blockState2 = blockView.getBlockState(blockPos.east());
		BlockState blockState3 = blockView.getBlockState(blockPos.south());
		BlockState blockState4 = blockView.getBlockState(blockPos.west());
		BlockState blockState5 = blockView.getBlockState(blockPos.up());
		BlockState blockState6 = blockView.getBlockState(blockPos.down());
		FluidState fluidState = blockView.getFluidState(blockPos);

		return getDefaultState()
			.with(NORTH, canConnectHorizontally(blockState))
			.with(EAST, canConnectHorizontally(blockState2))
			.with(SOUTH, canConnectHorizontally(blockState3))
			.with(WEST, canConnectHorizontally(blockState4))
			.with(UP, canConnectVertically(blockState5))
			.with(DOWN, canConnectVertically(blockState6))
			.with(WATERLOGGED, fluidState.isOf(Fluids.WATER));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (canConnectHorizontally(neighborState) && direction.getAxis().isHorizontal()) {
			world.setBlockState(pos, state.with(FACING_PROPERTIES.get(direction), true), Block.NOTIFY_LISTENERS);
		} else if (canConnectVertically(neighborState) && direction.getAxis().isVertical()) {
			world.setBlockState(pos, state.with(FACING_PROPERTIES.get(direction), true), Block.NOTIFY_LISTENERS);
		} else {
			world.setBlockState(pos, state.with(FACING_PROPERTIES.get(direction), false), Block.NOTIFY_LISTENERS);
		}

		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, WATERLOGGED);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DirectionalPipeBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0.25, 0.25, 0.25, 0.75, 0.75, 0.75);
	}
}
