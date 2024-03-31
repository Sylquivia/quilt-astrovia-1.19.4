package io.github.sylquivia.astrovia;

import net.minecraft.client.render.RenderLayer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class AstroviaClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		BlockRenderLayerMap.put(RenderLayer.getTranslucent(), AstroviaBlocks.FRACTIONATING_COLUMN);
		BlockRenderLayerMap.put(RenderLayer.getCutout(), AstroviaBlocks.PIPE);
	}
}
