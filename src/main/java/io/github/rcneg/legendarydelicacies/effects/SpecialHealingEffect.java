package io.github.rcneg.legendarydelicacies.effects;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class SpecialHealingEffect extends MobEffect {
    public SpecialHealingEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyEffectTick(LivingEntity living, int p_19468_) {
        if((this == EffectRegistry.FLAME_EATER.get() && living.isOnFire()) || (this == EffectRegistry.OVER_GROWING.get() && living.hasEffect(MobEffects.POISON))){
            if (living.getHealth() < living.getMaxHealth()) {
                living.heal(2.0F);
            }
        }
    }

    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        int i;
        i = 25 >> p_19456_;
        if (i > 0) {
            return p_19455_ % i == 0;
        } else {
            return true;
        }
    }
}
