package io.github.sylquivia.astrovia;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class HorizontalPipe extends ConnectingBlock {
	public HorizontalPipe(Settings settings) {
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

	private boolean canConnect(BlockState state) {
		return state.isOf(AstroviaBlocks.HORIZONTAL_PIPE) ||
			state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE) ||
			state.isOf(AstroviaBlocks.FRACTIONATING_COLUMN) ||
			state.isOf(Blocks.FURNACE);
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

		return (Objects.requireNonNull(super.getPlacementState(ctx)))
			.with(NORTH, canConnect(blockState))
			.with(EAST, canConnect(blockState2))
			.with(SOUTH, canConnect(blockState3))
			.with(WEST, canConnect(blockState4));
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (world.getBlockState(pos.north()).isOf(AstroviaBlocks.HORIZONTAL_PIPE) ||
			world.getBlockState(pos.north()).isOf(AstroviaBlocks.DIRECTIONAL_PIPE)) {
			if (!world.getBlockState(pos.north()).get(SOUTH)) {
				world.setBlockState(pos.north(), world.getBlockState(pos.north()).with(SOUTH, true));
			}
		}
		if (world.getBlockState(pos.east()).isOf(AstroviaBlocks.HORIZONTAL_PIPE) ||
			world.getBlockState(pos.east()).isOf(AstroviaBlocks.DIRECTIONAL_PIPE)) {
			if (!world.getBlockState(pos.east()).get(WEST)) {
				world.setBlockState(pos.east(), world.getBlockState(pos.east()).with(WEST, true));
			}
		}
		if (world.getBlockState(pos.south()).isOf(AstroviaBlocks.HORIZONTAL_PIPE) ||
			world.getBlockState(pos.south()).isOf(AstroviaBlocks.DIRECTIONAL_PIPE)) {
			if (!world.getBlockState(pos.south()).get(NORTH)) {
				world.setBlockState(pos.south(), world.getBlockState(pos.south()).with(NORTH, true));
			}
		}
		if (world.getBlockState(pos.west()).isOf(AstroviaBlocks.HORIZONTAL_PIPE) ||
			world.getBlockState(pos.west()).isOf(AstroviaBlocks.DIRECTIONAL_PIPE)) {
			if (!world.getBlockState(pos.west()).get(EAST)) {
				world.setBlockState(pos.west(), world.getBlockState(pos.west()).with(EAST, true));
			}
		}
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (world.getBlockState(pos.north()).isOf(AstroviaBlocks.HORIZONTAL_PIPE) ||
			world.getBlockState(pos.north()).isOf(AstroviaBlocks.DIRECTIONAL_PIPE)) {
			if (world.getBlockState(pos.north()).get(SOUTH)) {
				world.setBlockState(pos.north(), world.getBlockState(pos.north()).with(SOUTH, false));
			}
		}
		if (world.getBlockState(pos.east()).isOf(AstroviaBlocks.HORIZONTAL_PIPE) ||
			world.getBlockState(pos.east()).isOf(AstroviaBlocks.DIRECTIONAL_PIPE)) {
			if (world.getBlockState(pos.east()).get(WEST)) {
				world.setBlockState(pos.east(), world.getBlockState(pos.east()).with(WEST, false));
			}
		}
		if (world.getBlockState(pos.south()).isOf(AstroviaBlocks.HORIZONTAL_PIPE) ||
			world.getBlockState(pos.south()).isOf(AstroviaBlocks.DIRECTIONAL_PIPE)) {
			if (world.getBlockState(pos.south()).get(NORTH)) {
				world.setBlockState(pos.south(), world.getBlockState(pos.south()).with(NORTH, false));
			}
		}
		if (world.getBlockState(pos.west()).isOf(AstroviaBlocks.HORIZONTAL_PIPE) ||
			world.getBlockState(pos.west()).isOf(AstroviaBlocks.DIRECTIONAL_PIPE)) {
			if (world.getBlockState(pos.west()).get(EAST)) {
				world.setBlockState(pos.west(), world.getBlockState(pos.west()).with(EAST, false));
			}
		}
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
