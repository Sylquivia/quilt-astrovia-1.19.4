package io.github.sylquivia.astrovia;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DirectionalPipeBlockEntity extends BlockEntity {
	public int fluid = 0;
	public int gas = 0;

	public DirectionalPipeBlockEntity(BlockPos pos, BlockState state) {
		super(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK_ENTITY, pos, state);
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
}
