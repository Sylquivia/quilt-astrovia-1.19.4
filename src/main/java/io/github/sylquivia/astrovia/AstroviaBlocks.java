package io.github.sylquivia.astrovia;

import net.minecraft.block.AmethystBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class AstroviaBlocks {
	public static final Block PLEASE_WORK = new Block(QuiltBlockSettings.copyOf(Blocks.AMETHYST_BLOCK));
	public static void register(ModContainer mod) {
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "please_work"), PLEASE_WORK);
	}
}
