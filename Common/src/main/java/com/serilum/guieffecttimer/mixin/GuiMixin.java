package com.serilum.guieffecttimer.mixin;

import com.serilum.guieffecttimer.util.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Gui.class, priority = 1001)
public class GuiMixin {
	@Shadow private @Final Minecraft minecraft;

	@Unique private Holder<MobEffect> guiEffectTimer$mobEffectHolder = null;
	@Unique private int guiEffectTimer$slotX = 0;
	@Unique private int guiEffectTimer$slotY = 0;

	@ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"), index = 0)
	private ResourceLocation renderEffects_backgroundSprite(ResourceLocation sprite, int x, int y, int width, int height) {
		guiEffectTimer$slotX = x;
		guiEffectTimer$slotY = y;

		return sprite;
	}

	@ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/MobEffectTextureManager;get(Lnet/minecraft/core/Holder;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"), index = 0)
	private Holder<MobEffect> renderEffects_mobEffectHolder(Holder<MobEffect> mobEffectHolder) {
		guiEffectTimer$mobEffectHolder = mobEffectHolder;
		return mobEffectHolder;
	}

	@Inject(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/MobEffectTextureManager;get(Lnet/minecraft/core/Holder;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", shift = At.Shift.AFTER))
	private void onRenderEffects(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		Util.addEffectTimer(guiGraphics, this.minecraft, guiEffectTimer$mobEffectHolder, guiEffectTimer$slotX, guiEffectTimer$slotY);
	}
}
