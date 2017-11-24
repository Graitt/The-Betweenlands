package thebetweenlands.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thebetweenlands.common.lib.ModInfo;

public class BetweenlandsLoadingScreen {
	@SubscribeEvent
	public static void onGuiOpened(GuiOpenEvent event) {
		if (event.getGui() instanceof GuiDownloadTerrain && 
				!(event.getGui() instanceof GuiDownloadTerrainBetweenlands)) {
			event.setGui(new GuiDownloadTerrainBetweenlands());
		} else if (event.getGui() instanceof GuiScreenWorking && 
				!(event.getGui() instanceof GuiWorkingBetweenlands)) {
			event.setGui(new GuiWorkingBetweenlands());
		}
	}

	public static void renderLoadingScreen() {
		Minecraft mc = Minecraft.getMinecraft();
		GuiMainMenuBackground background = new GuiMainMenuBackground(new ResourceLocation(ModInfo.ID, "textures/gui/main/layer"), 4, false, false);
		ScaledResolution res = new ScaledResolution(mc);
		background.mc = mc;
		background.width = res.getScaledWidth();
		background.height = res.getScaledHeight();
		background.initGui();

		background.drawScreen(0, 0, 0);

		background.delete();
	}
}
