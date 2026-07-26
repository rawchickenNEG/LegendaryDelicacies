package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses.IAnimatedBoss;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IAnimatedBoss.class)
public class IAnimatedBossMixin {
    @Redirect(
            method = "addDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;min(FF)F"
            ),
            remap = false
    )
    //绕过限伤
    private float lmd$bypassDamageCapInAddDamage(float amount, float maxAllowed, float originalAmount, DamageSource source) {
        if (source.getEntity() instanceof LivingEntity living && living.hasEffect(EffectRegistry.ATOM_ANNIHILATION.get())) {
            return amount;
        }
        return Math.min(amount, maxAllowed);
    }

    @Redirect(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;min(DD)D"
            ),
            remap = false
    )
    //绕过限伤
    private double lmd$bypassDamageCapInHurt(double cap, double amount, DamageSource source, float originalAmount) {
        if (source.getEntity() instanceof LivingEntity living && living.hasEffect(EffectRegistry.ATOM_ANNIHILATION.get())) {
            return amount;
        }
        return Math.min(cap, amount);
    }

    @Redirect(
            method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/miauczel/legendary_monsters/entity/AnimatedMonster/OriginClasses/IAnimatedBoss;reducedDamage()Z"
            ),
            remap = false
    )
    //绕过适应减伤
    private boolean lmd$bypassReducedDamage(IAnimatedBoss boss, DamageSource source, float amount) {
        if (source.getEntity() instanceof LivingEntity attacker
                && attacker.hasEffect(EffectRegistry.ATOM_ANNIHILATION.get())) {
            return false;
        }
        return boss.reducedDamage();
    }
}
