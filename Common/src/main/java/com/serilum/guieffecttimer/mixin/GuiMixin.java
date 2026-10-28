package com.serilum.guieffecttimer.mixin;

import com.serilum.guieffecttimer.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
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

	@Unique private MobEffect guiEffectTimer$mobEffect = null;
	@Unique private int guiEffectTimer$slotX = 0;
	@Unique private int guiEffectTimer$slotY = 0;

	@ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"), index = 0)
	private ResourceLocation renderEffects_backgroundTexture(ResourceLocation texture, int x, int y, int uOffset, int vOffset, int width, int height) {
		guiEffectTimer$slotX = x;
		guiEffectTimer$slotY = y;

		return texture;
	}

	@ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/MobEffectTextureManager;get(Lnet/minecraft/world/effect/MobEffect;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"), index = 0)
	private MobEffect renderEffects_mobEffect(MobEffect mobEffect) {
		guiEffectTimer$mobEffect = mobEffect;
		return mobEffect;
	}

	@Inject(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/MobEffectTextureManager;get(Lnet/minecraft/world/effect/MobEffect;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", shift = At.Shift.AFTER))
	private void onRenderEffects(GuiGraphics guiGraphics, CallbackInfo ci) {
		Util.addEffectTimer(guiGraphics, this.minecraft, guiEffectTimer$mobEffect, guiEffectTimer$slotX, guiEffectTimer$slotY);
	}
}
