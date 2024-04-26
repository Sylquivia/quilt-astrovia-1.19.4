package io.github.sylquivia.astrovia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static io.github.sylquivia.astrovia.HorizontalPipeBlock.GAS;

public class HorizontalPipeBlockEntity extends BlockEntity {
	public int fluid = 0;
	public int gas = 0;

	public HorizontalPipeBlockEntity(BlockPos pos, BlockState state) {
		super(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		nbt.putInt("fluid", fluid);
		nbt.putInt("gas", gas);
		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		fluid = nbt.getInt("fluid");
		gas = nbt.getInt("gas");
	}

	public static void tick(World world, BlockPos blockPos, BlockState state, HorizontalPipeBlockEntity blockEntity) {
		BlockPos neighborPos = blockPos.north();
		BlockPos neighborPos1 = blockPos.east();
		BlockPos neighborPos2 = blockPos.south();
		BlockPos neighborPos3 = blockPos.west();
		BlockPos neighborPos4 = blockPos.up();
		BlockPos neighborPos5 = blockPos.down();

		BlockState neighborState = world.getBlockState(neighborPos);
		BlockState neighborState1 = world.getBlockState(neighborPos1);
		BlockState neighborState2 = world.getBlockState(neighborPos2);
		BlockState neighborState3 = world.getBlockState(neighborPos3);
		BlockState neighborState4 = world.getBlockState(neighborPos4);
		BlockState neighborState5 = world.getBlockState(neighborPos5);

		if (neighborState.contains(GAS)) {
			if (neighborState.get(GAS) > 0 && neighborState.get(GAS) < 3 - state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState.with(GAS, neighborState.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (neighborState1.contains(GAS)) {
			if (neighborState1.get(GAS) > 0 && neighborState1.get(GAS) < 3 - state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState1.with(GAS, neighborState1.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (neighborState2.contains(GAS)) {
			if (neighborState2.get(GAS) > 0 && neighborState2.get(GAS) < 3 - state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState2.with(GAS, neighborState2.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (neighborState3.contains(GAS)) {
			if (neighborState3.get(GAS) > 0 && neighborState3.get(GAS) < 3 - state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState3.with(GAS, neighborState3.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (neighborState4.contains(GAS)) {
			if (neighborState4.get(GAS) > 0 && neighborState4.get(GAS) < 3 - state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState4.with(GAS, neighborState4.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (neighborState5.contains(GAS)) {
			if (neighborState5.get(GAS) > 0 && neighborState5.get(GAS) < 3 - state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState5.with(GAS, neighborState5.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}
	}
}
