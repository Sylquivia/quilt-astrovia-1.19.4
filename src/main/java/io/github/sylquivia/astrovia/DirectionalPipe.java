package io.github.sylquivia.astrovia;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DirectionalPipe extends ConnectingBlock {
	public DirectionalPipe(Settings settings) {
		super(0.25f, settings);
		setDefaultState(
			getDefaultState()
				.with(NORTH, false)
				.with(EAST, false)
				.with(SOUTH, false)
				.with(WEST, false)
				.with(UP, false)
				.with(DOWN, false)
		);
	}

	private boolean canConnectHorizontally(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE)
			|| state.isOf(AstroviaBlocks.FRACTIONATING_COLUMN)
			|| state.isOf(AstroviaBlocks.OIL_HEATER_BLOCK);
	}

	private boolean canConnectVertically(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE)
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

		return (Objects.requireNonNull(super.getPlacementState(ctx)))
			.with(NORTH, canConnectHorizontally(blockState))
			.with(EAST, canConnectHorizontally(blockState2))
			.with(SOUTH, canConnectHorizontally(blockState3))
			.with(WEST, canConnectHorizontally(blockState4))
			.with(UP, canConnectVertically(blockState5))
			.with(DOWN, canConnectVertically(blockState6));
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

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(NORTH);
		builder.add(EAST);
		builder.add(SOUTH);
		builder.add(WEST);
		builder.add(UP);
		builder.add(DOWN);
	}
}
