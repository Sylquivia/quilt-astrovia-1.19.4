package io.github.sylquivia.astrovia;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class AstroviaBlocks {
	public static final Block FRACTIONATING_COLUMN = new GlassBlock((QuiltBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).nonOpaque()));
	public static final Block HORIZONTAL_PIPE = new HorizontalPipe(QuiltBlockSettings.copyOf(Blocks.GLASS).nonOpaque());
	public static final Block DIRECTIONAL_PIPE = new DirectionalPipe(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque());
	public static final Block GOO_BITTY = new GooBitty(QuiltBlockSettings.copyOf(Blocks.STONE));
	public static final Block OIL_HEATER_BLOCK = new OilHeaterBlock(QuiltBlockSettings.copyOf(Blocks.STONE));
	public static final BlockEntityType<OilHeaterBlockEntity> OIL_HEATER_BLOCK_ENTITY = QuiltBlockEntityTypeBuilder.create(OilHeaterBlockEntity::new, OIL_HEATER_BLOCK).build();
	public static void register(ModContainer mod) {
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "fractionating_column"), FRACTIONATING_COLUMN);
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "horizontal_pipe"), HORIZONTAL_PIPE);
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "directional_pipe"), DIRECTIONAL_PIPE);
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "goo_bitty"), GOO_BITTY);
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "oil_heater_block"), OIL_HEATER_BLOCK);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(mod.metadata().id(), "oil_heater_block_entity"), OIL_HEATER_BLOCK_ENTITY);
	}
}
