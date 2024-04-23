package io.github.sylquivia.astrovia;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
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

public class OilHeaterBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
	private final DefaultedList <ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
	public final int maxProgress = 200;
	public int fluid, gas, burnTime, maxBurnTime, progress;
	protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
		@Override
		public int get(int index) {
			switch(index) {
				case 0:
					return OilHeaterBlockEntity.this.fluid;
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
					OilHeaterBlockEntity.this.fluid = value;
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
		fluid = nbt.getInt("fluid");
		gas = nbt.getInt("gas");
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		Inventories.writeNbt(nbt, inventory);
		nbt.putInt("fluid", fluid);
		nbt.putInt("gas", gas);
		super.writeNbt(nbt);
	}

	public static void tick(World world, BlockPos pos, BlockState state, OilHeaterBlockEntity blockEntity) {
		if (blockEntity.getStack(1).isOf(AstroviaFluids.OIL_BUCKET) && blockEntity.fluid < 3) {
			blockEntity.setStack(1, Items.BUCKET.getDefaultStack());
			blockEntity.fluid ++;
		}

		if (blockEntity.getStack(0).isOf(Items.LAVA_BUCKET) && blockEntity.burnTime <= 0 && blockEntity.fluid > 0 && blockEntity.gas < 3) {
			blockEntity.setStack(0, Items.BUCKET.getDefaultStack());
			blockEntity.maxBurnTime = 400;
			blockEntity.burnTime = 400;
		}

		if (blockEntity.progress >= blockEntity.maxProgress) {
			blockEntity.progress = 0;
			blockEntity.fluid --;
			blockEntity.gas ++;
		}

		if (blockEntity.burnTime > 0) {
			blockEntity.burnTime --;
			blockEntity.progress ++;
		} else {
			blockEntity.progress = 0;
		}

		if (blockEntity.getStack(2).isOf(Items.BUCKET) && blockEntity.gas > 0) {
			blockEntity.setStack(2, AstroviaItems.OXYGEN_BREAD.getDefaultStack());
			blockEntity.gas --;
		}
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
