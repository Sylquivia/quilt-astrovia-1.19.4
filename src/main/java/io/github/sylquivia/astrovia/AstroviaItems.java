package io.github.sylquivia.astrovia;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import static io.github.sylquivia.astrovia.AstroviaBlocks.PLEASE_WORK;

public class AstroviaItems {
	public static final Item DANKIE_POO = new Item(new QuiltItemSettings());

	public static void register(ModContainer mod) {
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "dankie_poo"), DANKIE_POO);

		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "please_work"), new BlockItem(PLEASE_WORK, new QuiltItemSettings()));

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addItem(DANKIE_POO);
			entries.addItem(PLEASE_WORK.asItem());
		});
	}
}
