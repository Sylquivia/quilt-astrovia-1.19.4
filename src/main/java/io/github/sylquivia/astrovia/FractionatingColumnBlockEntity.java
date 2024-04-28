package io.github.sylquivia.astrovia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static io.github.sylquivia.astrovia.FractionatingColumnBlock.*;

public class FractionatingColumnBlockEntity extends BlockEntity {
	public int gas, naphtha, kerosene, fuel_oil, progress;
	public final int maxProgress = 200;

	public FractionatingColumnBlockEntity(BlockPos pos, BlockState state) {
		super(AstroviaBlocks.FRACTIONATING_COLUMN_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		nbt.putInt("gas", gas);
		nbt.putInt("naphtha", naphtha);
		nbt.putInt("kerosene", kerosene);
		nbt.putInt("fuel_oil", fuel_oil);
		nbt.putInt("progress", progress);
		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		gas = nbt.getInt("gas");
		naphtha = nbt.getInt("naphtha");
		kerosene = nbt.getInt("kerosene");
		fuel_oil = nbt.getInt("fuel_oil");
		progress = nbt.getInt("progress");
	}

	private boolean canTakeGasFrom(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK);
	}

	public static void tick(World world, BlockPos blockPos, BlockState state, FractionatingColumnBlockEntity blockEntity) {
		BlockPos neighborPos = blockPos.north();
		BlockPos neighborPos1 = blockPos.east();
		BlockPos neighborPos2 = blockPos.south();
		BlockPos neighborPos3 = blockPos.west();
		BlockPos neighborPos4 = blockPos.up();
		BlockPos neighborPos5 = blockPos.down();
		BlockPos neighborPos6 = neighborPos5.down();

		BlockState neighborState = world.getBlockState(neighborPos);
		BlockState neighborState1 = world.getBlockState(neighborPos1);
		BlockState neighborState2 = world.getBlockState(neighborPos2);
		BlockState neighborState3 = world.getBlockState(neighborPos3);
		BlockState neighborState4 = world.getBlockState(neighborPos4);
		BlockState neighborState5 = world.getBlockState(neighborPos5);
		BlockState neighborState6 = world.getBlockState(neighborPos6);
		BlockState inputPipe;

		if (blockEntity.canTakeGasFrom(neighborState)) {
			if (neighborState.get(GAS) > 0 && neighborState.get(GAS) <= 3 - state.get(GAS)) {
				inputPipe = neighborState;

				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState.with(GAS, neighborState.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeGasFrom(neighborState1)) {
			if (neighborState1.get(GAS) > 0 && neighborState1.get(GAS) <= 3 - state.get(GAS)) {
				inputPipe = neighborState1;

				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos1, neighborState1.with(GAS, neighborState1.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeGasFrom(neighborState2)) {
			if (neighborState2.get(GAS) > 0 && neighborState2.get(GAS) <= 3 - state.get(GAS)) {
				inputPipe = neighborState2;

				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos2, neighborState2.with(GAS, neighborState2.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeGasFrom(neighborState3)) {
			if (neighborState3.get(GAS) > 0 && neighborState3.get(GAS) <= 3 - state.get(GAS)) {
				inputPipe = neighborState3;

				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos3, neighborState3.with(GAS, neighborState3.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeGasFrom(neighborState4)) {
			if (neighborState4.get(GAS) > 0 && neighborState4.get(GAS) <= 3 - state.get(GAS)) {
				inputPipe = neighborState4;

				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos4, neighborState4.with(GAS, neighborState4.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeGasFrom(neighborState5)) {
			if (neighborState5.get(GAS) > 0 && neighborState5.get(GAS) <= 3 - state.get(GAS)) {
				inputPipe = neighborState5;

				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos5, neighborState5.with(GAS, neighborState5.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}


		if (neighborState5.isOf(AstroviaBlocks.FRACTIONATING_COLUMN_BLOCK)) {
			if (neighborState5.get(GAS) > 0 && neighborState5.get(GAS) > state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, state.get(GAS) + 1), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos5, neighborState5.with(GAS, neighborState5.get(GAS) - 1), Block.NOTIFY_LISTENERS);
			}
		}

		if (state.contains(GAS) && neighborState5.contains(GAS) && neighborState6.contains(GAS)) {
			if (blockEntity.progress < blockEntity.maxProgress) {
				blockEntity.progress ++;
			}

			if (state.get(GAS) > 0 && neighborState5.get(GAS) > 0 && neighborState6.get(GAS) > 0 && state.get(GAS) < 3 && neighborState5.get(GAS) < 3 && neighborState6.get(GAS) < 3 && blockEntity.progress >= blockEntity.maxProgress) {
				blockEntity.progress = 0;

				world.setBlockState(blockPos, state
						.with(GAS, state.get(GAS) - 1)
						.with(NAPHTHA, state.get(NAPHTHA) + 1),
					Block.NOTIFY_LISTENERS);

				world.setBlockState(neighborPos5, neighborState5
						.with(GAS, neighborState5.get(GAS) - 1)
						.with(KEROSENE, neighborState5.get(KEROSENE) + 1),
					Block.NOTIFY_LISTENERS);

				world.setBlockState(neighborPos6, neighborState6
						.with(GAS, neighborState6.get(GAS) - 1)
						.with(FUEL_OIL, neighborState6.get(FUEL_OIL) + 1),
					Block.NOTIFY_LISTENERS);
			}
		}
	}
}
