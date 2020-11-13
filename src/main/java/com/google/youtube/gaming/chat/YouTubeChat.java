package com.google.youtube.gaming.chat;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ytchat")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class YouTubeChat {
    public static final String MODID = "ytchat";
    public static final String APPNAME = "YouTube Chat";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

/*
    private static YouTubeChatService service;

    public static synchronized YouTubeChatService getService() {
        if (service == null) {
            service = new ChatService();
        }

        return service;
    }
*/

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
        //ModList.get().getModContainerById(MODID).ifPresent(c -> c.registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, parent) -> new GuiConfigYouTubeChat(parent)));
    }
}
