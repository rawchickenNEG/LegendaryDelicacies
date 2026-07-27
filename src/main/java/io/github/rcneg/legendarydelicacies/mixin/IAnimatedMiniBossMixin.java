package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses.IAnimatedBoss;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses.IAnimatedMiniBoss;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IAnimatedMiniBoss.class)
public class IAnimatedMiniBossMixin {
    @Redirect(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;min(DD)D"
            )
    )
    //绕过限伤
    private double lmd$bypassDamageCapInHurt(double cap, double amount, DamageSource source, float originalAmount) {
        if (source.getEntity() instanceof LivingEntity living && living.hasEffect(EffectRegistry.ATOM_ANNIHILATION.get())) {
            return amount;
        }
        return Math.min(cap, amount);
    }
}
