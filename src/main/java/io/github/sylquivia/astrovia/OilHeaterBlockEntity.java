package io.github.sylquivia.astrovia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.sylquivia.astrovia.OilHeaterBlock.*;

public class OilHeaterBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
	private final DefaultedList <ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
	public final int maxProgress = 200;
	public int oil, gas, burnTime, maxBurnTime, progress;
	protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
		@Override
		public int get(int index) {
			switch(index) {
				case 0:
					return OilHeaterBlockEntity.this.oil;
				case 1:
					return OilHeaterBlockEntity.this.gas;
				case 2:
					return OilHeaterBlockEntity.this.burnTime;
				case 3:
					return OilHeaterBlockEntity.this.maxBurnTime;
				case 4:
					return OilHeaterBlockEntity.this.progress;
				case 5:
					return OilHeaterBlockEntity.this.maxProgress;
				default:
					return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch(index) {
				case 0:
					OilHeaterBlockEntity.this.oil = value;
					break;
				case 1:
					OilHeaterBlockEntity.this.gas = value;
					break;
				case 2:
					OilHeaterBlockEntity.this.burnTime = value;
					break;
				case 3:
					OilHeaterBlockEntity.this.maxBurnTime = value;
					break;
				case 4:
					OilHeaterBlockEntity.this.progress = value;
			}
		}

		@Override
		public int size() {
			return 6;
		}
	};

	public OilHeaterBlockEntity(BlockPos pos, BlockState state) {
		super(AstroviaBlocks.OIL_HEATER_BLOCK_ENTITY, pos, state);
	}

	public DefaultedList <ItemStack> getItems() {
		return inventory;
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		Inventories.readNbt(nbt, inventory);
		oil = nbt.getInt("oil");
		gas = nbt.getInt("gas");
		burnTime = nbt.getInt("burnTime");
		maxBurnTime = nbt.getInt("maxBurnTime");
		progress = nbt.getInt("progress");
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		Inventories.writeNbt(nbt, inventory);
		nbt.putInt("oil", oil);
		nbt.putInt("gas", gas);
		nbt.putInt("burnTime", burnTime);
		nbt.putInt("maxBurnTime", maxBurnTime);
		nbt.putInt("progress", progress);
		super.writeNbt(nbt);
	}

	public static void tick(World world, BlockPos pos, BlockState state, OilHeaterBlockEntity blockEntity) {
		if (blockEntity.getStack(1).isOf(AstroviaItems.OIL_BUCKET) && state.get(OIL) < 3) {
			blockEntity.setStack(1, Items.BUCKET.getDefaultStack());

			world.setBlockState(pos, state.with(OIL, state.get(OIL) + 1), Block.NOTIFY_LISTENERS);
		}

		if (blockEntity.getStack(0).isOf(Items.LAVA_BUCKET) && blockEntity.burnTime <= 0 && state.get(OIL) > 0 && state.get(GAS) < 3) {
			blockEntity.setStack(0, Items.BUCKET.getDefaultStack());
			blockEntity.maxBurnTime = 400;
			blockEntity.burnTime = 400;

			world.setBlockState(pos, state.with(LIT, true));
		}

		if (blockEntity.burnTime > 0) {
			blockEntity.burnTime --;

			if (state.get(OIL) > 0 && state.get(GAS) < 3) {
				blockEntity.progress ++;
			}

			if (blockEntity.progress >= blockEntity.maxProgress) {
				blockEntity.progress = 0;

				if (blockEntity.burnTime <= 0 && !blockEntity.getStack(0).isOf(Items.LAVA_BUCKET)) {
					world.setBlockState(pos, state
							.with(OIL, state.get(OIL) - 1)
							.with(GAS, state.get(GAS) + 1)
							.with(LIT, false),
						Block.NOTIFY_LISTENERS);
				} else {
					world.setBlockState(pos, state
							.with(OIL, state.get(OIL) - 1)
							.with(GAS, state.get(GAS) + 1)
							.with(LIT, true),
						Block.NOTIFY_LISTENERS);
				}
			}
		}

		if (blockEntity.getStack(2).isOf(Items.BUCKET) && state.get(GAS) > 0) {
			blockEntity.setStack(2, AstroviaItems.OXYGEN_BREAD.getDefaultStack());

			world.setBlockState(pos, state.with(GAS, state.get(GAS) - 1), Block.NOTIFY_LISTENERS);
		}

		blockEntity.oil = state.get(OIL);
		blockEntity.gas = state.get(GAS);
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

	@Override
	public DefaultedList<ItemStack> getInventory() {
		return inventory;
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable(getCachedState().getBlock().getTranslationKey());
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new OilHeaterScreenHandler(i, playerInventory, this, this.propertyDelegate);
	}
}
