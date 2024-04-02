package io.github.sylquivia.astrovia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalConnectingBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class NewPipe extends HorizontalConnectingBlock {
	public NewPipe(Settings settings) {
		super(4, 4, 12, 12, 12, settings);
		setDefaultState(
			getDefaultState()
				.with(NORTH, Boolean.FALSE)
				.with(EAST, Boolean.FALSE)
				.with(SOUTH, Boolean.FALSE)
				.with(WEST, Boolean.FALSE)
				.with(WATERLOGGED, Boolean.FALSE)
		);
	}

	private boolean canConnect(BlockState state) {
		return state.isOf(AstroviaBlocks.NEW_PIPE);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockView blockView = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		BlockState blockState = blockView.getBlockState(blockPos);
		BlockState blockState1 = blockView.getBlockState(blockPos.north());
		BlockState blockState2 = blockView.getBlockState(blockPos.east());
		BlockState blockState3 = blockView.getBlockState(blockPos.south());
		BlockState blockState4 = blockView.getBlockState(blockPos.west());

		return super.getPlacementState(ctx)
			.with(NORTH, canConnect(blockState1))
			.with(EAST, canConnect(blockState2))
			.with(SOUTH, canConnect(blockState3))
			.with(WEST, canConnect(blockState4));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(NORTH);
		builder.add(EAST);
		builder.add(SOUTH);
		builder.add(WEST);
		builder.add(WATERLOGGED);
	}
}
