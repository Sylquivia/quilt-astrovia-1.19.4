package io.github.sylquivia.astrovia;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class OilHeaterScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	public final PropertyDelegate propertyDelegate;

	public OilHeaterScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(6));
	}

	public OilHeaterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(Astrovia.OIL_HEATER_SCREEN_HANDLER, syncId);
        checkSize(inventory, 3);
		this.inventory = inventory;
		this.propertyDelegate = propertyDelegate;

		inventory.onOpen(playerInventory.player);

		addSlot(new Slot(inventory, 0, 80, 53));
		addSlot(new Slot(inventory, 1, 26, 35));
		addSlot(new Slot(inventory, 2, 134, 35));

		int i, j;

		for (i = 0 ; i < 3 ; i ++) {
			for (j = 0 ; j < 9 ; j ++) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0 ; i < 9 ; i ++) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}

		this.addProperties(propertyDelegate);
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(fromIndex);

		if (slot != null && slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();

			if (fromIndex < this.inventory.size()) {
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		return newStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	public int getFluid() {
		return this.propertyDelegate.get(0);
	}

	public int getGas() {
		return this.propertyDelegate.get(1);
	}

	public int getBurnTime() {
		return this.propertyDelegate.get(2);
	}

	public int getMaxBurnTime() {
		return this.propertyDelegate.get(3);
	}

	public int getProgress() {
		return this.propertyDelegate.get(4);
	}

	public int getMaxProgress() {
		return this.propertyDelegate.get(5);
	}
}
