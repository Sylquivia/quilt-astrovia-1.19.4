package io.github.sylquivia.astrovia;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class Astrovia implements ModInitializer {
	@Override
	public void onInitialize(ModContainer mod) {
		AstroviaItems.register(mod);
		AstroviaBlocks.register(mod);
	}
}
