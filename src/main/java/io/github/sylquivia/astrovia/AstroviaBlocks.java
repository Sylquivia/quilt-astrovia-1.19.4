package io.github.sylquivia.astrovia;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlassBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class AstroviaBlocks {
	public static final Block FRACTIONATING_COLUMN = new GlassBlock((QuiltBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).nonOpaque()));
	public static final Block HORIZONTAL_PIPE = new HorizontalPipe(QuiltBlockSettings.copyOf(Blocks.GLASS).nonOpaque());
	public static final Block DIRECTIONAL_PIPE = new HorizontalPipe(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque());
	public static void register(ModContainer mod) {
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "fractionating_column"), FRACTIONATING_COLUMN);
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "horizontal_pipe"), HORIZONTAL_PIPE);
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "directional_pipe"), DIRECTIONAL_PIPE);
	}
}
