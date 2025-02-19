package com.natamus.guieffecttimer.util;

import com.natamus.guieffecttimer.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Util {
    public static void addEffectTimer(GuiGraphics guiGraphics, MobEffectInstance mobEffectInstance, int k, int l) {
        String timeText = getTimeText(mobEffectInstance);

        Font font = Minecraft.getInstance().font;
        float scale = 0.7F;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);
        guiGraphics.pose().translate(-0.5F / scale, 0.0F, 0.0F);

        guiGraphics.drawString(font, Component.literal(timeText), (int)(((k + 13) / scale) - (font.width(timeText) / 2.0F)), (int)(((l + 16) / scale)), new Color(ConfigHandler.timerColourRGB_R, ConfigHandler.timerColourRGB_G, ConfigHandler.timerColourRGB_B).getRGB(), true);

        guiGraphics.pose().popPose();
    }

    private static @NotNull String getTimeText(MobEffectInstance mobEffectInstance) {
        int ticks = mobEffectInstance.getDuration();
        if (ticks < 0) {
            return "∞";
        }

        double seconds = ticks / 20.0;

        if (seconds < 60) {
            return String.format("%.0fs", seconds);
        } else if (seconds < 3600) {
            return String.format("%dm", (int) (seconds / 60));
        } else if (seconds < 86400) {
            return String.format("%dh", (int) (seconds / 3600));
        } else {
            return String.format("%dd", (int) (seconds / 86400));
        }
    }
}
