package net.teamfruit.ytchat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.extensions.IForgeKeybinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.teamfruit.ytchat.gui.GuiConfigYouTubeChat;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = YouTubeChat.MODID, value = Dist.CLIENT)
public class YouTubeChatClient {
    public static IForgeKeybinding openConfig;

    public static void initClient() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
                () -> (mc, parent) -> new GuiConfigYouTubeChat(parent));

        openConfig = new KeyBinding(
                Util.makeTranslationKey("key", new ResourceLocation(YouTubeChat.MODID, "config")),
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputMappings.Type.KEYSYM.getOrMakeInput(GLFW.GLFW_KEY_KP_9),
                YouTubeChat.APPNAME);

        ClientRegistry.registerKeyBinding(openConfig.getKeyBinding());
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (openConfig == null)
            return;

        while (openConfig.getKeyBinding().isPressed()) {
            Minecraft.getInstance().displayGuiScreen(new GuiConfigYouTubeChat(null));
        }
    }

}
