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
	public static Identifier OIL_HEATER = new Identifier("astrovia", "oil_heater_block");
	public static final Block OIL_HEATER_BLOCK = new OilHeaterBlock(QuiltBlockSettings.copyOf(Blocks.FURNACE));
	public static Identifier DIRECTIONAL_PIPE = new Identifier("astrovia", "directional_pipe_block");
	public static final Block DIRECTIONAL_PIPE_BLOCK = new DirectionalPipeBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));
	public static Identifier HORIZONTAL_PIPE = new Identifier("astrovia", "horizontal_pipe_block");
	public static final Block HORIZONTAL_PIPE_BLOCK = new HorizontalPipeBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK));
	public static Identifier FRACTIONATING_COLUMN = new Identifier("astrovia", "fractionating_column_block");
	public static final Block FRACTIONATING_COLUMN_BLOCK = new FractionatingColumnBlock(QuiltBlockSettings.copyOf(Blocks.GLASS));
	public static final BlockEntityType<OilHeaterBlockEntity> OIL_HEATER_BLOCK_ENTITY = QuiltBlockEntityTypeBuilder.create(OilHeaterBlockEntity :: new, OIL_HEATER_BLOCK).build(null);
	public static final BlockEntityType<DirectionalPipeBlockEntity> DIRECTIONAL_PIPE_BLOCK_ENTITY = QuiltBlockEntityTypeBuilder.create(DirectionalPipeBlockEntity :: new, DIRECTIONAL_PIPE_BLOCK).build(null);
	public static final BlockEntityType<HorizontalPipeBlockEntity> HORIZONTAL_PIPE_BLOCK_ENTITY = QuiltBlockEntityTypeBuilder.create(HorizontalPipeBlockEntity :: new, HORIZONTAL_PIPE_BLOCK).build(null);
	public static final BlockEntityType<FractionatingColumnBlockEntity> FRACTIONATING_COLUMN_BLOCK_ENTITY = QuiltBlockEntityTypeBuilder.create(FractionatingColumnBlockEntity :: new, FRACTIONATING_COLUMN_BLOCK).build(null);
	public static void register(ModContainer mod) {
		Registry.register(Registries.BLOCK, OIL_HEATER, OIL_HEATER_BLOCK);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, OIL_HEATER, OIL_HEATER_BLOCK_ENTITY);

		Registry.register(Registries.BLOCK, DIRECTIONAL_PIPE, DIRECTIONAL_PIPE_BLOCK);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, DIRECTIONAL_PIPE, DIRECTIONAL_PIPE_BLOCK_ENTITY);

		Registry.register(Registries.BLOCK, HORIZONTAL_PIPE, HORIZONTAL_PIPE_BLOCK);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, HORIZONTAL_PIPE, HORIZONTAL_PIPE_BLOCK_ENTITY);

		Registry.register(Registries.BLOCK, FRACTIONATING_COLUMN, FRACTIONATING_COLUMN_BLOCK);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, FRACTIONATING_COLUMN, FRACTIONATING_COLUMN_BLOCK_ENTITY);
	}
}
