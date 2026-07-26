package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEffect.class)
public class MobEffectMixin {
    @Inject(
            method = "applyEffectTick",
            at = @At("HEAD"),
            cancellable = true,
            remap = false)
    private void lmd$overGrowthEffect(LivingEntity living, int p_19468_, CallbackInfo ci) {
        MobEffect effect = (MobEffect)(Object)this;
        if(effect == MobEffects.POISON && living.hasEffect(EffectRegistry.OVER_GROWING.get())){
            ci.cancel();
        }
    }
}
