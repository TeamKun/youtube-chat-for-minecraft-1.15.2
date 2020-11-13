package com.google.youtube.gaming.chat.gui;

import com.google.youtube.gaming.chat.YouTubeChat;
import com.google.youtube.gaming.chat.YouTubeConfiguration;
import com.google.youtube.gaming.chat.gui.config.OptionsEntryButton;
import com.google.youtube.gaming.chat.gui.config.OptionsListWidget;
import com.google.youtube.gaming.chat.gui.config.value.OptionsEntryValueInput;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiConfigYouTubeChat extends GuiOptions {

    public GuiConfigYouTubeChat(Screen parent) {
        super(parent, new TranslationTextComponent("ytchat.gui.configuration", YouTubeChat.APPNAME), YouTubeConfiguration.spec::save, () -> {
        });
    }

    @Override
    public OptionsListWidget getOptions() {
        OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30, YouTubeConfiguration.spec::save);
        options.add(new OptionsEntryButton(Util.makeTranslationKey("config", new ResourceLocation(YouTubeChat.MODID, "secret")), new Button(0, 0, 100, 20, "", w -> {
            minecraft.displayGuiScreen(new GuiOptions(GuiConfigYouTubeChat.this, new TranslationTextComponent(Util.makeTranslationKey("config", new ResourceLocation(YouTubeChat.MODID, "secret")))) {
                @Override
                public OptionsListWidget getOptions() {
                    OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);
                    options.add(new OptionsEntryValueInput<String>(Util.makeTranslationKey(
                            "config",
                            new ResourceLocation(YouTubeChat.MODID, "client_secret")),
                            YouTubeConfiguration.SECRET.clientSecret.get(),
                            YouTubeConfiguration.SECRET.clientSecret::set
                    ));
                    options.add(new OptionsEntryValueInput<String>(Util.makeTranslationKey(
                            "config",
                            new ResourceLocation(YouTubeChat.MODID, "video_id")),
                            YouTubeConfiguration.SECRET.videoId.get(),
                            YouTubeConfiguration.SECRET.videoId::set
                    ));
                    return options;
                }
            });
        })));
        return options;
    }
}
