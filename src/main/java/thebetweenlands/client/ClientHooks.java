package thebetweenlands.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import thebetweenlands.api.event.PreRenderShadersEvent;
import thebetweenlands.client.gui.BetweenlandsLoadingScreen;
import thebetweenlands.client.gui.GuiMainMenuBackground;
import thebetweenlands.common.lib.ModInfo;

public final class ClientHooks {
	private ClientHooks() { }

	/**
	 * Called before the vanilla shaders are applied to the screen
	 */
	public static void onPreRenderShaders(float partialTicks) {
		MinecraftForge.EVENT_BUS.post(new PreRenderShadersEvent(partialTicks));
	}
	
	/**
	 * Called when the loading screen is rendered
	 */
	public static void onRenderLoadingScreen() {
		BetweenlandsLoadingScreen.renderLoadingScreen();
	}
}
