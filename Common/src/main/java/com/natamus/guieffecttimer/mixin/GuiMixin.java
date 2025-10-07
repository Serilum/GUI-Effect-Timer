package com.natamus.guieffecttimer.mixin;

import com.natamus.guieffecttimer.util.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Iterator;

@Mixin(value = Gui.class, priority = 1001)
public class GuiMixin {
	@Shadow private @Final Minecraft minecraft;

    @Inject(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIII)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
	private void renderEffects(GuiGraphics guiGraphics, DeltaTracker arg1, CallbackInfo ci, Collection<?> collection, int i, int j, Iterator<?> var6, MobEffectInstance mobEffectInstance, Holder<MobEffect> mobEffectHolder, int k, int l, float f) {
		Util.addEffectTimer(guiGraphics, mobEffectInstance, k, l);
	}
}
