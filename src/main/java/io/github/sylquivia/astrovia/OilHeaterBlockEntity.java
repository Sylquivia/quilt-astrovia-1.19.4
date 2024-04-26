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
	public int fluid, gas, burnTime, maxBurnTime, progress; // make fluid and gas properties not just nbt k thx
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
		burnTime = nbt.getInt("burnTime");
		maxBurnTime = nbt.getInt("maxBurnTime");
		progress = nbt.getInt("progress");
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		Inventories.writeNbt(nbt, inventory);
		nbt.putInt("fluid", fluid);
		nbt.putInt("gas", gas);
		nbt.putInt("burnTime", burnTime);
		nbt.putInt("maxBurnTime", maxBurnTime);
		nbt.putInt("progress", progress);
		super.writeNbt(nbt);
	}

	public static void tick(World world, BlockPos pos, BlockState state, OilHeaterBlockEntity blockEntity) {
		if (blockEntity.getStack(1).isOf(AstroviaItems.OIL_BUCKET) && blockEntity.fluid < 3) {
			blockEntity.setStack(1, Items.BUCKET.getDefaultStack());
			blockEntity.fluid ++;

			world.setBlockState(pos, state.with(FLUID, blockEntity.fluid), Block.NOTIFY_LISTENERS);
		}

		if (blockEntity.getStack(0).isOf(Items.LAVA_BUCKET) && blockEntity.burnTime <= 0 && blockEntity.fluid > 0 && blockEntity.gas < 3) {
			blockEntity.setStack(0, Items.BUCKET.getDefaultStack());
			blockEntity.maxBurnTime = 400;
			blockEntity.burnTime = 400;

			world.setBlockState(pos, state.with(LIT, true));
		}

		if (blockEntity.progress >= blockEntity.maxProgress) {
			blockEntity.progress = 0;
			blockEntity.fluid --;
			blockEntity.gas ++;

			world.setBlockState(pos, state
				.with(FLUID, blockEntity.fluid)
				.with(GAS, blockEntity.gas),
				Block.NOTIFY_LISTENERS
			);
		}

		if (blockEntity.burnTime > 0) {
			blockEntity.burnTime --;

			if (blockEntity.fluid > 0 && blockEntity.gas < 3) {
				blockEntity.progress ++;
			}

			if (blockEntity.burnTime <= 0) {
				blockEntity.progress = 0;

				world.setBlockState(pos, state.with(LIT, false));
			}
		}

		if (blockEntity.getStack(2).isOf(Items.BUCKET) && blockEntity.gas > 0) {
			blockEntity.setStack(2, AstroviaItems.OXYGEN_BREAD.getDefaultStack());
			blockEntity.gas --;

			world.setBlockState(pos, state.with(GAS, blockEntity.gas), Block.NOTIFY_LISTENERS);
		}

		if (blockEntity.fluid != state.get(FLUID)) {
			blockEntity.fluid = state.get(FLUID);
		}

		if (blockEntity.gas != state.get(GAS)) {
			blockEntity.gas = state.get(GAS);
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
