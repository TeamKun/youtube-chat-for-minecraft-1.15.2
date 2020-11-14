/**
 * Copyright 2020 Kamesuta.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.teamfruit.ytchat;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Configuration settings for YouTube Chat.
 */
public class YouTubeConfiguration {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final Secret SECRET = new Secret(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    public static class Secret {
        public final ForgeConfigSpec.ConfigValue<String> clientSecret;
        public final ForgeConfigSpec.ConfigValue<String> videoId;

        public Secret(final ForgeConfigSpec.Builder builder) {
            builder
                    .comment("The client secrets from Google API console")
                    .push("Secret");
            this.clientSecret = builder
                    .comment("The client secret from Google API console")
                    .translation(Util.makeTranslationKey("config", new ResourceLocation(YouTubeChat.MODID, "secret.client_secret")))
                    .define("Client Secret", "");
            this.videoId = builder
                    .comment("The id of the live video")
                    .translation(Util.makeTranslationKey("config", new ResourceLocation(YouTubeChat.MODID, "secret.video_id")))
                    .define("Video ID", "");
            builder.pop();
        }
    }
}
