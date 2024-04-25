package io.github.sylquivia.astrovia;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class AstroviaClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		BlockRenderLayerMap.put(RenderLayer.getTranslucent(), AstroviaBlocks.FRACTIONATING_COLUMN);
		BlockRenderLayerMap.put(RenderLayer.getTranslucent(), AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK);
		BlockRenderLayerMap.put(RenderLayer.getTranslucent(), AstroviaBlocks.HORIZONTAL_PIPE_BLOCK);

		FluidRenderHandlerRegistry.INSTANCE.register(AstroviaFluids.OIL, AstroviaFluids.FLOWING_OIL, new SimpleFluidRenderHandler(
			new Identifier("astrovia:block/oil_still"),
			new Identifier("astrovia:block/oil_flow")
		));

		HandledScreens.register(Astrovia.OIL_HEATER_SCREEN_HANDLER, OilHeaterScreen :: new);
	}
}
