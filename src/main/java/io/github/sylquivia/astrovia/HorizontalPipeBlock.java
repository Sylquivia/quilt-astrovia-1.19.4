package io.github.sylquivia.astrovia;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.ConnectingBlock.*;

public class HorizontalPipeBlock extends BlockWithEntity implements Waterloggable, BlockEntityProvider {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final IntProperty OIL = AstroviaProperties.OIL_3;
	public static final IntProperty GAS = AstroviaProperties.GAS_3;
	public static final IntProperty NAPHTHA = AstroviaProperties.NAPHTHA_3;
	public static final IntProperty KEROSENE = AstroviaProperties.KEROSENE_3;
	public static final IntProperty FUEL_OIL = AstroviaProperties.FUEL_OIL_3;
	private BlockState input, output;

	public HorizontalPipeBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState()
			.with(NORTH, false)
			.with(EAST, false)
			.with(SOUTH, false)
			.with(WEST, false)
			.with(WATERLOGGED, false)
			.with(OIL, 0)
			.with(GAS, 0)
			.with(NAPHTHA, 0)
			.with(KEROSENE, 0)
			.with(FUEL_OIL, 0)
		);
	}

	private boolean canConnectHorizontally(BlockState state) {
		return state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.FRACTIONATING_COLUMN_BLOCK)
			|| state.isOf(AstroviaBlocks.OIL_HEATER_BLOCK);
	}

	private boolean canTakeGasFrom(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.OIL_HEATER_BLOCK);
	}

	private boolean canGiveGasTo(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.FRACTIONATING_COLUMN_BLOCK);
	}

	private int takeGas(ItemPlacementContext ctx,
						BlockState neighborState1, BlockState neighborState2, BlockState neighborState3, BlockState neighborState4,
						BlockPos neighborPos1, BlockPos neighborPos2, BlockPos neighborPos3, BlockPos neighborPos4) {
		if (canTakeGasFrom(neighborState1)) {
			if (neighborState1.get(GAS) > 0) {
				ctx.getWorld().setBlockState(neighborPos1, neighborState1.with(GAS, neighborState1.get(GAS) - 1));
				return 1;
			}
		}

		if (canTakeGasFrom(neighborState2)) {
			if (neighborState2.get(GAS) > 0) {
				ctx.getWorld().setBlockState(neighborPos2, neighborState2.with(GAS, neighborState2.get(GAS) - 1));
				return 1;
			}
		}

		if (canTakeGasFrom(neighborState3)) {
			if (neighborState3.get(GAS) > 0) {
				ctx.getWorld().setBlockState(neighborPos3, neighborState3.with(GAS, neighborState3.get(GAS) - 1));
				return 1;
			}
		}

		if (canTakeGasFrom(neighborState4)) {
			if (neighborState4.get(GAS) > 0) {
				ctx.getWorld().setBlockState(neighborPos4, neighborState4.with(GAS, neighborState4.get(GAS) - 1));
				return 1;
			}
		}

		return 0;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockView blockView = ctx.getWorld();

		BlockPos pos = ctx.getBlockPos();
		BlockPos pos1 = pos.north();
		BlockPos pos2 = pos.east();
		BlockPos pos3 = pos.south();
		BlockPos pos4 = pos.west();

		BlockState blockState = blockView.getBlockState(pos);
		BlockState blockState1 = blockView.getBlockState(pos1);
		BlockState blockState2 = blockView.getBlockState(pos2);
		BlockState blockState3 = blockView.getBlockState(pos3);
		BlockState blockState4 = blockView.getBlockState(pos4);

		FluidState fluidState = blockView.getFluidState(pos);

		return getDefaultState()
			.with(NORTH, canConnectHorizontally(blockState1))
			.with(EAST, canConnectHorizontally(blockState2))
			.with(SOUTH, canConnectHorizontally(blockState3))
			.with(WEST, canConnectHorizontally(blockState4))
			.with(WATERLOGGED, fluidState.isOf(Fluids.WATER))
			.with(OIL, 0)
			.with(GAS, takeGas(ctx, blockState1, blockState2, blockState3, blockState4, pos1, pos2, pos3, pos4))
			.with(NAPHTHA, 0)
			.with(KEROSENE, 0)
			.with(FUEL_OIL, 0);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction.getAxis().isHorizontal()) {
			world.setBlockState(pos, state.with(FACING_PROPERTIES.get(direction), canConnectHorizontally(neighborState)), Block.NOTIFY_LISTENERS);
		}

		if (canTakeGasFrom(neighborState)) {
			if (neighborState.get(GAS) > 0 && state.get(GAS) < 3 && neighborState != output) {
				world.setBlockState(pos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				input = neighborState;
			}
		}

		if (canGiveGasTo(neighborState)) {
			if (state.get(GAS) > 0 && neighborState.get(GAS) < 3 && neighborState != input) {
				world.setBlockState(pos, state.with(GAS, state.get(GAS) - 1), Block.NOTIFY_LISTENERS);
				output = neighborState;
			}
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
		builder.add(NORTH, EAST, SOUTH, WEST, WATERLOGGED, OIL, GAS, NAPHTHA, KEROSENE, FUEL_OIL);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new HorizontalPipeBlockEntity(pos, state);
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
