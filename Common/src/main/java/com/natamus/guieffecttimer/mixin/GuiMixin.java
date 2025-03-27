package com.natamus.guieffecttimer.mixin;

import com.natamus.guieffecttimer.util.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.MobEffectTextureManager;
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
import java.util.List;

@Mixin(value = Gui.class, priority = 1001)
public class GuiMixin {
	@Shadow private @Final Minecraft minecraft;

	@Inject(method = "renderEffects", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/effect/MobEffectInstance;getEffect()Lnet/minecraft/core/Holder;"), locals = LocalCapture.CAPTURE_FAILSOFT)
	private void renderEffects(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci, Collection<MobEffectInstance> mobEffectInstanceCollection, int i, int j, MobEffectTextureManager mobEffectTextureManager, List<Runnable> runnableList, Iterator<?> iterator, MobEffectInstance mobEffectInstance, Holder<MobEffect> mobEffectHolder) {
		int k = guiGraphics.guiWidth();
		int l = 1;
		if (this.minecraft.isDemo()) {
			l += 15;
		}

		if ((mobEffectHolder.value()).isBeneficial()) {
			++i;
			k -= 25 * i;
		} else {
			++j;
			k -= 25 * j;
			l += 26;
		}

		Util.addEffectTimer(guiGraphics, mobEffectInstance, k, l);
	}
}
