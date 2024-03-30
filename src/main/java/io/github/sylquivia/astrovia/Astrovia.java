package io.github.sylquivia.astrovia;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.sylquivia.astrovia.AstroviaBlocks.PLEASE_WORK;

public class Astrovia implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Astrovia");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		AstroviaItems.register(mod);
		AstroviaBlocks.register(mod);
	}
}
