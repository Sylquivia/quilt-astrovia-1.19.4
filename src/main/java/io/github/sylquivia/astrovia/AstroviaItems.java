package io.github.sylquivia.astrovia;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
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

		ItemGroup ASTROVIA_ITEMS = FabricItemGroup.builder(new Identifier("astrovia", "astrovia_items"))
			.icon(() -> new ItemStack(DANKIE_POO))
			.build();
		ItemGroup ASTROVIA_BLOCKS = FabricItemGroup.builder(new Identifier("astrovia", "astrovia_blocks"))
			.icon(() -> new ItemStack(PLEASE_WORK))
			.build();

		ItemGroupEvents.modifyEntriesEvent(ASTROVIA_ITEMS).register(entries -> {
			entries.addItem(DANKIE_POO);
		});

		ItemGroupEvents.modifyEntriesEvent(ASTROVIA_BLOCKS).register(entries -> {
			entries.addItem(PLEASE_WORK.asItem());
		});
	}
}
