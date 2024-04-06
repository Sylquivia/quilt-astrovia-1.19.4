package io.github.sylquivia.astrovia;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class OilHeaterBlockEntity extends BlockEntity {
	private int number = 19;
	public OilHeaterBlockEntity(BlockPos pos, BlockState state) {
		super(AstroviaBlocks.OIL_HEATER_BLOCK_ENTITY, pos, state);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		number = nbt.getInt("number");
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		nbt.putInt("number", number);
		super.writeNbt(nbt);
	}

	public static void tick(World world, BlockPos pos, BlockState state, OilHeaterBlockEntity blockEntity) {

	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.of(this);
	}

	@Override
	public NbtCompound toSyncedNbt() {
		return super.toSyncedNbt();
	}
}
