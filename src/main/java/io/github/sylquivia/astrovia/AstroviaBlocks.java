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
	public static final Block GOO_BITTY = new GooBitty(QuiltBlockSettings.copyOf(Blocks.STONE));
	public static Identifier OIL_HEATER = new Identifier("astrovia", "oil_heater_block");
	public static final Block OIL_HEATER_BLOCK = new OilHeaterBlock(QuiltBlockSettings.copyOf(Blocks.FURNACE));
	public static final BlockEntityType<OilHeaterBlockEntity> OIL_HEATER_BLOCK_ENTITY = QuiltBlockEntityTypeBuilder.create(OilHeaterBlockEntity :: new, OIL_HEATER_BLOCK).build(null);
	public static Identifier DIRECTIONAL_PIPE = new Identifier("astrovia", "directional_pipe_block");
	public static final Block DIRECTIONAL_PIPE_BLOCK = new DirectionalPipeBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));
	public static Identifier HORIZONTAL_PIPE = new Identifier("astrovia", "horizontal_pipe_block");
	public static final Block HORIZONTAL_PIPE_BLOCK = new HorizontalPipeBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));
	public static final BlockEntityType<DirectionalPipeBlockEntity> DIRECTIONAL_PIPE_BLOCK_ENTITY = QuiltBlockEntityTypeBuilder.create(DirectionalPipeBlockEntity :: new, DIRECTIONAL_PIPE_BLOCK).build(null);
	public static final BlockEntityType<DirectionalPipeBlockEntity> HORIZONTAL_PIPE_BLOCK_ENTITY = QuiltBlockEntityTypeBuilder.create(DirectionalPipeBlockEntity :: new, HORIZONTAL_PIPE_BLOCK).build(null);
	public static void register(ModContainer mod) {

		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "fractionating_column"), FRACTIONATING_COLUMN);
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "goo_bitty"), GOO_BITTY);

		Registry.register(Registries.BLOCK, OIL_HEATER, OIL_HEATER_BLOCK);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, OIL_HEATER, OIL_HEATER_BLOCK_ENTITY);

		Registry.register(Registries.BLOCK, DIRECTIONAL_PIPE, DIRECTIONAL_PIPE_BLOCK);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, DIRECTIONAL_PIPE, DIRECTIONAL_PIPE_BLOCK_ENTITY);

		Registry.register(Registries.BLOCK, HORIZONTAL_PIPE, HORIZONTAL_PIPE_BLOCK);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, HORIZONTAL_PIPE, HORIZONTAL_PIPE_BLOCK_ENTITY);
	}
}
