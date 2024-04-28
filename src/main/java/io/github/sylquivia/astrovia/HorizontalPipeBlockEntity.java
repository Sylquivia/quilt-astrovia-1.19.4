package io.github.sylquivia.astrovia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static io.github.sylquivia.astrovia.HorizontalPipeBlock.*;

public class HorizontalPipeBlockEntity extends BlockEntity {
	public int oil, gas, naphtha, kerosene, fuel_oil;
	public boolean isInput;
	List<BlockState> inputs = new ArrayList<>();

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
		nbt.putBoolean("isInput", isInput);
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
		isInput = nbt.getBoolean("isInput");
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

	private boolean isConverter(BlockState state) {
		return state.isOf(AstroviaBlocks.OIL_HEATER_BLOCK)
			|| state.isOf(AstroviaBlocks.FRACTIONATING_COLUMN_BLOCK);
	}
}
