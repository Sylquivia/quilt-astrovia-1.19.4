package io.github.sylquivia.astrovia;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class OilHeaterScreenHandler extends ScreenHandler {
	private final Inventory inventory;

	public OilHeaterScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(9));
	}

	public OilHeaterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(Astrovia.OIL_HEATER_SCREEN_HANDLER, syncId);
		checkSize(inventory, 9);
		this.inventory = inventory;

		inventory.onOpen(playerInventory.player);

		int i;
		int j;

		for (i = 0 ; i < 3 ; i ++) {
			for (j = 0 ; j < 3 ; j ++) {
				this.addSlot(new Slot(inventory, j + i * 3, 62 + j * 18, 17 + i * 18));
			}
		}

		for (i = 0 ; i < 3 ; i ++) {
			for (j = 0; j < 9; j++) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0 ; i < 9 ; i ++) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
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
}
