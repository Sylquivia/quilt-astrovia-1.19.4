package io.github.sylquivia.astrovia;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class AstroviaClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		BlockRenderLayerMap.put(RenderLayer.getTranslucent(), AstroviaBlocks.FRACTIONATING_COLUMN);

		FluidRenderHandlerRegistry.INSTANCE.register(AstroviaFluids.OIL, AstroviaFluids.FLOWING_OIL, new SimpleFluidRenderHandler(
			new Identifier("minecraft:block/water_still"),
			new Identifier("minecraft:block/water_flow"),
			0x1f1f1f
		));
	}
}
