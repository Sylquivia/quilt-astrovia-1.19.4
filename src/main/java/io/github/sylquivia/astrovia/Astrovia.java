package io.github.sylquivia.astrovia;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class Astrovia implements ModInitializer {
	public static final ScreenHandlerType <OilHeaterScreenHandler> OIL_HEATER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(AstroviaBlocks.OIL_HEATER, OilHeaterScreenHandler :: new);

    @Override
	public void onInitialize(ModContainer mod) {
		AstroviaItems.register(mod);
		AstroviaBlocks.register(mod);
		AstroviaFluids.register(mod);

		for (int i = 0 ; i < 2147483647 ; i ++) {
			System.out.println("nice #" + i);
		}
	}
}
