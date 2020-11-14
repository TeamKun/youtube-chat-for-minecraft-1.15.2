package net.teamfruit.ytchat.gui;

import com.google.api.services.youtube.model.LiveChatMessageAuthorDetails;
import com.google.api.services.youtube.model.LiveChatSuperChatDetails;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.teamfruit.ytchat.YouTubeChat;
import net.teamfruit.ytchat.YouTubeConfiguration;
import net.teamfruit.ytchat.gui.config.OptionsEntryButton;
import net.teamfruit.ytchat.gui.config.OptionsListWidget;
import net.teamfruit.ytchat.gui.config.value.OptionsEntryValueInput;
import net.teamfruit.ytchat.service.Auth;
import net.teamfruit.ytchat.service.ChatService;
import net.teamfruit.ytchat.service.YouTubeChatService;

import java.io.IOException;

import static net.teamfruit.ytchat.MessageUtils.showErrorMessage;
import static net.teamfruit.ytchat.MessageUtils.showMessage;

public class GuiConfigYouTubeChat extends GuiOptions {
    private final ChatService service;
    private static YouTubeChatService.YouTubeChatMessageListener listener = (author, superChatDetails, message) -> {
        showMessage(message);
        if (superChatDetails != null
                && superChatDetails.getAmountMicros() != null
                && superChatDetails.getAmountMicros().longValue() > 0) {
            showMessage("Received "
                    + superChatDetails.getAmountDisplayString()
                    + " from "
                    + author.getDisplayName());
        }
    };

    public GuiConfigYouTubeChat(Screen parent) {
        super(parent, new TranslationTextComponent(Util.makeTranslationKey("gui", new ResourceLocation(YouTubeChat.MODID, "configuration")), YouTubeChat.APPNAME), YouTubeConfiguration.spec::save, null);
        this.service = YouTubeChat.getServiceInternal();
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
                            new ResourceLocation(YouTubeChat.MODID, "secret.client_secret")),
                            YouTubeConfiguration.SECRET.clientSecret.get(),
                            YouTubeConfiguration.SECRET.clientSecret::set
                    ));
                    options.add(new OptionsEntryValueInput<String>(Util.makeTranslationKey(
                            "config",
                            new ResourceLocation(YouTubeChat.MODID, "secret.video_id")),
                            YouTubeConfiguration.SECRET.videoId.get(),
                            YouTubeConfiguration.SECRET.videoId::set
                    ));
                    return options;
                }
            });
        })));
        options.add(new OptionsEntryButton(Util.makeTranslationKey("gui", new ResourceLocation(YouTubeChat.MODID, "mock")), new Button(0, 0, 100, 20, "", w -> {
            minecraft.displayGuiScreen(new GuiOptions(GuiConfigYouTubeChat.this, new TranslationTextComponent(Util.makeTranslationKey("gui", new ResourceLocation(YouTubeChat.MODID, "mock")))) {
                String author = "author";
                String message = "message";

                @Override
                public OptionsListWidget getOptions() {
                    OptionsListWidget options = new OptionsListWidget(this, minecraft, width + 45, height, 32, height - 32, 30);
                    options.add(new OptionsEntryValueInput<String>(Util.makeTranslationKey(
                            "gui",
                            new ResourceLocation(YouTubeChat.MODID, "mock.author")),
                            author,
                            val -> author = val
                    ));
                    options.add(new OptionsEntryValueInput<String>(Util.makeTranslationKey(
                            "gui",
                            new ResourceLocation(YouTubeChat.MODID, "mock.message")),
                            message,
                            val -> message = val
                    ));
                    options.add(new OptionsEntryButton(Util.makeTranslationKey("gui", new ResourceLocation(YouTubeChat.MODID, "mock.send")), new Button(0, 0, 100, 20, "", w -> {
                        // Message
                        System.out.println(String.format("YouTubeChatMock received %1$s from %2$s", message, author));
                        LiveChatMessageAuthorDetails authorDetails = new LiveChatMessageAuthorDetails();
                        authorDetails.setDisplayName(author);
                        authorDetails.setChannelId(author);
                        YouTubeChat.getServiceInternal().broadcastMessage(
                                authorDetails, new LiveChatSuperChatDetails(), message);
                        minecraft.displayGuiScreen(null);
                    })));
                    return options;
                }
            });
        })));
        options.add(new OptionsEntryButton(Util.makeTranslationKey("gui", new ResourceLocation(YouTubeChat.MODID, "control.start")), new Button(0, 0, 100, 20, "", w -> {
            // Start
            String clientSecret = YouTubeConfiguration.SECRET.clientSecret.get();
            if (clientSecret == null || clientSecret.isEmpty()) {
                showErrorMessage("No client secret configurated");
                return;
            }
            service.start(YouTubeConfiguration.SECRET.videoId.get(), clientSecret);
            minecraft.displayGuiScreen(null);
        })));
        options.add(new OptionsEntryButton(Util.makeTranslationKey("gui", new ResourceLocation(YouTubeChat.MODID, "control.stop")), new Button(0, 0, 100, 20, "", w -> {
            // Stop
            service.stop();
            minecraft.displayGuiScreen(null);
        })));
        options.add(new OptionsEntryButton(Util.makeTranslationKey("gui", new ResourceLocation(YouTubeChat.MODID, "control.logout")), new Button(0, 0, 100, 20, "", w -> {
            // Logout
            service.stop();
            try {
                Auth.clearCredentials();
                minecraft.displayGuiScreen(null);
            } catch (IOException e) {
                showErrorMessage(e.getMessage());
            }
        })));
        options.add(new OptionsEntryButton(Util.makeTranslationKey("gui", new ResourceLocation(YouTubeChat.MODID, "control.echo.start")), new Button(0, 0, 100, 20, "", w -> {
            // Echo Start
            if (!service.isInitialized()) {
                showErrorMessage("Service is not initialized");
                return;
            }
            service.subscribe(listener);
            minecraft.displayGuiScreen(null);
        })));
        options.add(new OptionsEntryButton(Util.makeTranslationKey("gui", new ResourceLocation(YouTubeChat.MODID, "control.echo.stop")), new Button(0, 0, 100, 20, "", w -> {
            // Echo Stop
            service.unsubscribe(listener);
            minecraft.displayGuiScreen(null);
        })));
        return options;
    }

}
