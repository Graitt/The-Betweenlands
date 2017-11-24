package thebetweenlands.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thebetweenlands.common.lib.ModInfo;
import thebetweenlands.util.config.ConfigHandler;

public class GuiWorkingBetweenlands extends GuiScreenWorking {
	private GuiMainMenuBackground background = new GuiMainMenuBackground(new ResourceLocation(ModInfo.ID, "textures/gui/main/layer"), 4, false, false);

	@SubscribeEvent
	public static void onGuiOpened(GuiOpenEvent event) {
		if (event.getGui() instanceof GuiScreenWorking && 
				!(event.getGui() instanceof GuiWorkingBetweenlands)) {
			event.setGui(new GuiWorkingBetweenlands());
		}
	}

	@Override
	public void initGui() {
		this.background.mc = this.mc;
		this.background.width = this.width;
		this.background.height = this.height;
		this.background.initGui();

		super.initGui();
	}

	@Override
	public void onGuiClosed() {
		this.background.onGuiClosed();
	}

	@Override
	public void updateScreen() {
		this.background.updateScreen();
	}

	@Override
	public void drawDefaultBackground() {
		this.background.drawScreen(0, 0, 0);
	}
}
