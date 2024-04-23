package io.github.sylquivia.astrovia;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class AstroviaFluids {
	public static FlowableFluid OIL = new OilFluid.Still();
	public static FlowableFluid FLOWING_OIL = new OilFluid.Flowing();
	public static Block OIL_BLOCK = new FluidBlock(AstroviaFluids.OIL, QuiltBlockSettings.copyOf(Blocks.WATER));
	public static Item OIL_BUCKET = new BucketItem(AstroviaFluids.OIL, new QuiltItemSettings().maxCount(1));

	public static void register(ModContainer mod) {
		Registry.register(Registries.FLUID, new Identifier(mod.metadata().id(), "oil"), OIL);
		Registry.register(Registries.FLUID, new Identifier(mod.metadata().id(), "flowing_oil"), FLOWING_OIL);
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "oil_block"), OIL_BLOCK);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "oil_bucket"), OIL_BUCKET);
	}
}
