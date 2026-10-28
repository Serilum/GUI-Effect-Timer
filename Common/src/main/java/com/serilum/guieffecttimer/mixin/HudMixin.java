package com.serilum.guieffecttimer.mixin;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.serilum.guieffecttimer.util.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Hud.class, priority = 1001)
public class HudMixin {
	@Shadow private @Final Minecraft minecraft;

	@Unique private Identifier guiEffectTimer$iconSprite = null;
	@Unique private int guiEffectTimer$iconX = 0;
	@Unique private int guiEffectTimer$iconY = 0;

	@ModifyArg(method = "extractEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V"), index = 1)
	private Identifier extractEffects_iconSprite(RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, int color) {
		guiEffectTimer$iconSprite = location;
		guiEffectTimer$iconX = x;
		guiEffectTimer$iconY = y;

		return location;
	}

	@Inject(method = "extractEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V", shift = At.Shift.AFTER))
	private void onExtractEffects(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		Util.addEffectTimer(graphics, this.minecraft, guiEffectTimer$iconSprite, guiEffectTimer$iconX, guiEffectTimer$iconY);
	}
}
