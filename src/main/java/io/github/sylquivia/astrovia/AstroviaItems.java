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
	public static final Item OXYGEN_BREAD = new Item(new QuiltItemSettings().food(
		new FoodComponent.Builder()
			.hunger(5)
			.alwaysEdible()
			.meat()
			.build()
	));

	public static final ArmorMaterial DOOBEE_ARMOUR_MATERIAL = new DoobeeArmourMaterial();
	public static final Item DOOBEE_HELMET = new ArmorItem(DOOBEE_ARMOUR_MATERIAL, ArmorItem.ArmorSlot.HELMET, new QuiltItemSettings());
	public static final Item DOOBEE_CHESTPLATE = new ArmorItem(DOOBEE_ARMOUR_MATERIAL, ArmorItem.ArmorSlot.CHESTPLATE, new QuiltItemSettings());
	public static final Item DOOBEE_LEGGINGS = new ArmorItem(DOOBEE_ARMOUR_MATERIAL, ArmorItem.ArmorSlot.LEGGINGS, new QuiltItemSettings());
	public static final Item DOOBEE_BOOTS = new ArmorItem(DOOBEE_ARMOUR_MATERIAL, ArmorItem.ArmorSlot.BOOTS, new QuiltItemSettings());

	public static void register(ModContainer mod) {
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "dankie_poo"), DANKIE_POO);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "oxygen_bread"), OXYGEN_BREAD);

		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "doobee_helmet"), DOOBEE_HELMET);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "doobee_chestplate"), DOOBEE_CHESTPLATE);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "doobee_leggings"), DOOBEE_LEGGINGS);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "doobee_boots"), DOOBEE_BOOTS);

		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "please_work"), new BlockItem(PLEASE_WORK, new QuiltItemSettings()));

		ItemGroup ASTROVIA_ITEMS = FabricItemGroup.builder(new Identifier("astrovia", "astrovia_items"))
			.icon(() -> new ItemStack(DANKIE_POO))
			.build();
		ItemGroup ASTROVIA_BLOCKS = FabricItemGroup.builder(new Identifier("astrovia", "astrovia_blocks"))
			.icon(() -> new ItemStack(PLEASE_WORK))
			.build();
		ItemGroup ASTROVIA_ARMOUR = FabricItemGroup.builder(new Identifier("astrovia", "astrovia_armour"))
			.icon(() -> new ItemStack(DOOBEE_CHESTPLATE))
			.build();

		ItemGroupEvents.modifyEntriesEvent(ASTROVIA_ITEMS).register(entries -> {
			entries.addItem(DANKIE_POO);
			entries.addItem(OXYGEN_BREAD);
		});

		ItemGroupEvents.modifyEntriesEvent(ASTROVIA_BLOCKS).register(entries -> {
			entries.addItem(PLEASE_WORK.asItem());
		});

		ItemGroupEvents.modifyEntriesEvent(ASTROVIA_ARMOUR).register(entries -> {
			entries.addItem(DOOBEE_HELMET);
			entries.addItem(DOOBEE_CHESTPLATE);
			entries.addItem(DOOBEE_LEGGINGS);
			entries.addItem(DOOBEE_BOOTS);
		});
	}
}
