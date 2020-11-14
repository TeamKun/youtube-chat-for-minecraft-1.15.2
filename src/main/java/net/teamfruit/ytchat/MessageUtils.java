package net.teamfruit.ytchat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.command.ICommandSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

public class MessageUtils {

    public static void showMessage(String message) {
        Minecraft mc = Minecraft.getInstance();
        ICommandSource sender = mc.player;
        if (sender != null)
            sender.sendMessage(new StringTextComponent(message));
    }

    public static void showErrorMessage(String message) {
        Minecraft mc = Minecraft.getInstance();
        ICommandSource sender = mc.player;
        if (sender != null)
            sender.sendMessage(new StringTextComponent(message));
        else
            mc.displayGuiScreen(new DisconnectedScreen(mc.currentScreen, Util.makeTranslationKey("gui", new ResourceLocation(YouTubeChat.MODID, "error")), new StringTextComponent(message)));
    }

}
