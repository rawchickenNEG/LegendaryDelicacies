package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(
            method = "getDamageAfterArmorAbsorb",
            at = @At("HEAD"),
            cancellable = true
    )
    private void lmd$atomAnnihilationEffectIgnoreArmor(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if(source.getEntity() instanceof LivingEntity living && living.hasEffect(EffectRegistry.ATOM_ANNIHILATION.get())){
            cir.setReturnValue(amount);
        }
    }

    @Inject(
            method = "getDamageAfterMagicAbsorb",
            at = @At("HEAD"),
            cancellable = true
    )
    private void lmd$atomAnnihilationEffectIgnoreMagic(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if(source.getEntity() instanceof LivingEntity living && living.hasEffect(EffectRegistry.ATOM_ANNIHILATION.get())){
            cir.setReturnValue(amount);
        }
    }
}
