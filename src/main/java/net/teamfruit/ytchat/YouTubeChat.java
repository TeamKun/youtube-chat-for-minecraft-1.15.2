package net.teamfruit.ytchat;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.teamfruit.ytchat.service.ChatService;
import net.teamfruit.ytchat.service.YouTubeChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ytchat")
public class YouTubeChat {
    public static final String MODID = "ytchat";
    public static final String APPNAME = "YouTube Chat";

    // Directly reference a log4j logger.
    public static final Logger logger = LogManager.getLogger(APPNAME);

    private static ChatService service = new ChatService();

    public static YouTubeChatService getService() {
        return service;
    }

    public static ChatService getServiceInternal() {
        return service;
    }

    public YouTubeChat() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, YouTubeConfiguration.spec);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        YouTubeChatClient.initClient();
    }
}
