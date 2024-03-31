package io.github.sylquivia.astrovia;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import static io.github.sylquivia.astrovia.AstroviaBlocks.FRACTIONATING_COLUMN;
import static io.github.sylquivia.astrovia.AstroviaBlocks.PIPE;

public class AstroviaItems {
	public static final Item DANKIE_POO = new Item(new QuiltItemSettings());
	public static final Item OXYGEN_BREAD = new Item(new QuiltItemSettings().food(
		new FoodComponent.Builder()
			.hunger(5)
			.alwaysEdible()
			.meat()
			.build()
	));

	public static final ArmorMaterial SPACESUIT_MATERIAL = new SpacesuitMaterial();
	public static final Item SPACESUIT_HELMET = new ArmorItem(SPACESUIT_MATERIAL, ArmorItem.ArmorSlot.HELMET, new QuiltItemSettings());
	public static final Item SPACESUIT_CHEST = new ArmorItem(SPACESUIT_MATERIAL, ArmorItem.ArmorSlot.CHESTPLATE, new QuiltItemSettings());
	public static final Item SPACESUIT_LEGS = new ArmorItem(SPACESUIT_MATERIAL, ArmorItem.ArmorSlot.LEGGINGS, new QuiltItemSettings());
	public static final Item SPACESUIT_BOOTS = new ArmorItem(SPACESUIT_MATERIAL, ArmorItem.ArmorSlot.BOOTS, new QuiltItemSettings());

	public static void register(ModContainer mod) {
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "dankie_poo"), DANKIE_POO);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "oxygen_bread"), OXYGEN_BREAD);

		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "spacesuit_helmet"), SPACESUIT_HELMET);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "spacesuit_chest"), SPACESUIT_CHEST);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "spacesuit_legs"), SPACESUIT_LEGS);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "spacesuit_boots"), SPACESUIT_BOOTS);

		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "fractionating_column"), new BlockItem(FRACTIONATING_COLUMN, new QuiltItemSettings()));
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "pipe"), new BlockItem(PIPE, new QuiltItemSettings()));


		ItemGroup ASTROVIA_ITEMS = FabricItemGroup.builder(new Identifier("astrovia", "astrovia_items"))
			.icon(() -> new ItemStack(DANKIE_POO))
			.build();
		ItemGroup ASTROVIA_BLOCKS = FabricItemGroup.builder(new Identifier("astrovia", "astrovia_blocks"))
			.icon(() -> new ItemStack(FRACTIONATING_COLUMN))
			.build();
		ItemGroup ASTROVIA_ARMOUR = FabricItemGroup.builder(new Identifier("astrovia", "astrovia_armour"))
			.icon(() -> new ItemStack(SPACESUIT_HELMET))
			.build();

		ItemGroupEvents.modifyEntriesEvent(ASTROVIA_ITEMS).register(entries -> {
			entries.addItem(DANKIE_POO);
			entries.addItem(OXYGEN_BREAD);
		});

		ItemGroupEvents.modifyEntriesEvent(ASTROVIA_BLOCKS).register(entries -> {
			entries.addItem(FRACTIONATING_COLUMN.asItem());
			entries.addItem(PIPE.asItem());
		});

		ItemGroupEvents.modifyEntriesEvent(ASTROVIA_ARMOUR).register(entries -> {
			entries.addItem(SPACESUIT_HELMET);
			entries.addItem(SPACESUIT_CHEST);
			entries.addItem(SPACESUIT_LEGS);
			entries.addItem(SPACESUIT_BOOTS);
		});
	}
}
