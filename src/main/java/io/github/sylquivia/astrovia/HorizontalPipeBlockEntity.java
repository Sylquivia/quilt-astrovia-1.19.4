package io.github.sylquivia.astrovia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static io.github.sylquivia.astrovia.HorizontalPipeBlock.*;

public class HorizontalPipeBlockEntity extends BlockEntity {
	public int oil = 0;
	public int gas = 0;
	public int naphtha = 0;
	public int kerosene = 0;
	public int fuel_oil = 0;

	public HorizontalPipeBlockEntity(BlockPos pos, BlockState state) {
		super(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		nbt.putInt("fluid", oil);
		nbt.putInt("gas", gas);
		nbt.putInt("naphtha", naphtha);
		nbt.putInt("kerosene", kerosene);
		nbt.putInt("fuel_oil", fuel_oil);
		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		oil = nbt.getInt("fluid");
		gas = nbt.getInt("gas");
		naphtha = nbt.getInt("naphtha");
		kerosene = nbt.getInt("kerosene");
		fuel_oil = nbt.getInt("fuel_oil");
	}

	private boolean canTakeOilFrom(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK);
	}

	private boolean canTakeGasFrom(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.OIL_HEATER_BLOCK);
	}

	private boolean canTakeNaphthaFrom(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.FRACTIONATING_COLUMN_BLOCK);
	}

	private boolean canTakeKeroseneFrom(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.FRACTIONATING_COLUMN_BLOCK);
	}

	private boolean canTakeFuelOilFrom(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.FRACTIONATING_COLUMN_BLOCK);
	}

	public static void tick(World world, BlockPos blockPos, BlockState state, HorizontalPipeBlockEntity blockEntity) {
		BlockPos neighborPos = blockPos.north();
		BlockPos neighborPos1 = blockPos.east();
		BlockPos neighborPos2 = blockPos.south();
		BlockPos neighborPos3 = blockPos.west();

		BlockState neighborState = world.getBlockState(neighborPos);
		BlockState neighborState1 = world.getBlockState(neighborPos1);
		BlockState neighborState2 = world.getBlockState(neighborPos2);
		BlockState neighborState3 = world.getBlockState(neighborPos3);

		if (blockEntity.canTakeOilFrom(neighborState)) {
			if (neighborState.get(OIL) > 0 && neighborState.get(OIL) <= 3 - state.get(OIL)) {
				world.setBlockState(blockPos, state.with(OIL, neighborState.get(OIL)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState.with(OIL, state.get(OIL)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeOilFrom(neighborState1)) {
			if (neighborState1.get(OIL) > 0 && neighborState1.get(OIL) <= 3 - state.get(OIL)) {
				world.setBlockState(blockPos, state.with(OIL, neighborState1.get(OIL)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos1, neighborState1.with(OIL, state.get(OIL)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeOilFrom(neighborState2)) {
			if (neighborState2.get(OIL) > 0 && neighborState2.get(OIL) <= 3 - state.get(OIL)) {
				world.setBlockState(blockPos, state.with(OIL, neighborState2.get(OIL)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos2, neighborState2.with(OIL, state.get(OIL)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeOilFrom(neighborState3)) {
			if (neighborState3.get(OIL) > 0 && neighborState3.get(OIL) <= 3 - state.get(OIL)) {
				world.setBlockState(blockPos, state.with(OIL, neighborState3.get(OIL)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos3, neighborState3.with(OIL, state.get(OIL)), Block.NOTIFY_LISTENERS);
			}
		}


		if (blockEntity.canTakeGasFrom(neighborState)) {
			if (neighborState.get(GAS) > 0 && neighborState.get(GAS) <= 3 - state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, neighborState.get(GAS)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState.with(GAS, state.get(GAS)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeGasFrom(neighborState1)) {
			if (neighborState1.get(GAS) > 0 && neighborState1.get(GAS) <= 3 - state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, neighborState1.get(GAS)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos1, neighborState1.with(GAS, state.get(GAS)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeGasFrom(neighborState2)) {
			if (neighborState2.get(GAS) > 0 && neighborState2.get(GAS) <= 3 - state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, neighborState2.get(GAS)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos2, neighborState2.with(GAS, state.get(GAS)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeGasFrom(neighborState3)) {
			if (neighborState3.get(GAS) > 0 && neighborState3.get(GAS) <= 3 - state.get(GAS)) {
				world.setBlockState(blockPos, state.with(GAS, neighborState3.get(GAS)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos3, neighborState3.with(GAS, state.get(GAS)), Block.NOTIFY_LISTENERS);
			}
		}


		if (blockEntity.canTakeNaphthaFrom(neighborState)) {
			if (neighborState.get(NAPHTHA) > 0 && neighborState.get(NAPHTHA) <= 3 - state.get(NAPHTHA)) {
				world.setBlockState(blockPos, state.with(NAPHTHA, neighborState.get(NAPHTHA)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState.with(NAPHTHA, state.get(NAPHTHA)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeNaphthaFrom(neighborState1)) {
			if (neighborState1.get(NAPHTHA) > 0 && neighborState1.get(NAPHTHA) <= 3 - state.get(NAPHTHA)) {
				world.setBlockState(blockPos, state.with(NAPHTHA, neighborState1.get(NAPHTHA)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos1, neighborState1.with(NAPHTHA, state.get(NAPHTHA)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeNaphthaFrom(neighborState2)) {
			if (neighborState2.get(NAPHTHA) > 0 && neighborState2.get(NAPHTHA) <= 3 - state.get(NAPHTHA)) {
				world.setBlockState(blockPos, state.with(NAPHTHA, neighborState2.get(NAPHTHA)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos2, neighborState2.with(NAPHTHA, state.get(NAPHTHA)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeNaphthaFrom(neighborState3)) {
			if (neighborState3.get(NAPHTHA) > 0 && neighborState3.get(NAPHTHA) <= 3 - state.get(NAPHTHA)) {
				world.setBlockState(blockPos, state.with(NAPHTHA, neighborState3.get(NAPHTHA)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos3, neighborState3.with(NAPHTHA, state.get(NAPHTHA)), Block.NOTIFY_LISTENERS);
			}
		}


		if (blockEntity.canTakeKeroseneFrom(neighborState)) {
			if (neighborState.get(KEROSENE) > 0 && neighborState.get(KEROSENE) <= 3 - state.get(KEROSENE)) {
				world.setBlockState(blockPos, state.with(KEROSENE, neighborState.get(KEROSENE)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState.with(KEROSENE, state.get(KEROSENE)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeKeroseneFrom(neighborState1)) {
			if (neighborState1.get(KEROSENE) > 0 && neighborState1.get(KEROSENE) <= 3 - state.get(KEROSENE)) {
				world.setBlockState(blockPos, state.with(KEROSENE, neighborState1.get(KEROSENE)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos1, neighborState1.with(KEROSENE, state.get(KEROSENE)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeKeroseneFrom(neighborState2)) {
			if (neighborState2.get(KEROSENE) > 0 && neighborState2.get(KEROSENE) <= 3 - state.get(KEROSENE)) {
				world.setBlockState(blockPos, state.with(KEROSENE, neighborState2.get(KEROSENE)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos2, neighborState2.with(KEROSENE, state.get(KEROSENE)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeKeroseneFrom(neighborState3)) {
			if (neighborState3.get(KEROSENE) > 0 && neighborState3.get(KEROSENE) <= 3 - state.get(KEROSENE)) {
				world.setBlockState(blockPos, state.with(KEROSENE, neighborState3.get(KEROSENE)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos3, neighborState3.with(KEROSENE, state.get(KEROSENE)), Block.NOTIFY_LISTENERS);
			}
		}


		if (blockEntity.canTakeFuelOilFrom(neighborState)) {
			if (neighborState.get(FUEL_OIL) > 0 && neighborState.get(FUEL_OIL) <= 3 - state.get(FUEL_OIL)) {
				world.setBlockState(blockPos, state.with(FUEL_OIL, neighborState.get(FUEL_OIL)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos, neighborState.with(FUEL_OIL, state.get(FUEL_OIL)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeFuelOilFrom(neighborState1)) {
			if (neighborState1.get(FUEL_OIL) > 0 && neighborState1.get(FUEL_OIL) <= 3 - state.get(FUEL_OIL)) {
				world.setBlockState(blockPos, state.with(FUEL_OIL, neighborState1.get(FUEL_OIL)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos1, neighborState1.with(FUEL_OIL, state.get(FUEL_OIL)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeFuelOilFrom(neighborState2)) {
			if (neighborState2.get(FUEL_OIL) > 0 && neighborState2.get(FUEL_OIL) <= 3 - state.get(FUEL_OIL)) {
				world.setBlockState(blockPos, state.with(FUEL_OIL, neighborState2.get(FUEL_OIL)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos2, neighborState2.with(FUEL_OIL, state.get(FUEL_OIL)), Block.NOTIFY_LISTENERS);
			}
		}

		if (blockEntity.canTakeFuelOilFrom(neighborState3)) {
			if (neighborState3.get(FUEL_OIL) > 0 && neighborState3.get(FUEL_OIL) <= 3 - state.get(FUEL_OIL)) {
				world.setBlockState(blockPos, state.with(FUEL_OIL, neighborState3.get(FUEL_OIL)), Block.NOTIFY_LISTENERS);
				world.setBlockState(neighborPos3, neighborState3.with(FUEL_OIL, state.get(FUEL_OIL)), Block.NOTIFY_LISTENERS);
			}
		}
	}
}
