package io.github.sylquivia.astrovia;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class OilHeaterScreen extends HandledScreen <OilHeaterScreenHandler> {
	private static final Identifier TEXTURE = new Identifier("astrovia", "textures/gui/container/oil_heater.png");

	public OilHeaterScreen(OilHeaterScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer :: getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, TEXTURE);

		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;

		drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

		switch (this.handler.getFluid()) {
			case 1:
				drawTexture(matrices, x + 48, y + 53, 176, 67, 8, 16);
				break;
			case 2:
				drawTexture(matrices, x + 48, y + 35, 176, 49, 8, 34);
				break;
			case 3:
				drawTexture(matrices, x + 48, y + 17, 176, 31, 8, 52);
				break;
		}

		switch (this.handler.getGas()) {
			case 1:
				drawTexture(matrices, x + 120, y + 17, 184, 31, 8, 17);
				break;
			case 2:
				drawTexture(matrices, x + 120, y + 17, 184, 31, 8, 35);
				break;
			case 3:
				drawTexture(matrices, x + 120, y + 17, 184, 31, 8, 52);
				break;
		}

		if (this.handler.getProgress() > 0 && this.handler.getBurnTime() > 0 && this.handler.getFluid() > 0 && this.handler.getGas() < 3) {
			int progressWidth = 24 * this.handler.getProgress() / this.handler.getMaxProgress();
			drawTexture(matrices, x + 76, y + 17, 176, 14, progressWidth, 17);
		}

		if (this.handler.getBurnTime() > 0) {
			int burnTimeHeight = 14 * this.handler.getBurnTime() / this.handler.getMaxBurnTime();
			drawTexture(matrices, x + 81, y + 37 + (12 - burnTimeHeight), 176, 12 - burnTimeHeight, 14, burnTimeHeight + 1);
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void init() {
		super.init();
		titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
	}


}
