package io.github.rcneg.legendarydelicacies.effects;

import io.github.rcneg.legendarydelicacies.config.Config;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class FlawlessEffect extends LMDMobEffect {
    public FlawlessEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        List<MobEffectInstance> list = new ArrayList<>(entity.getActiveEffects());
        list.removeIf(effectInstance -> Config.flawlessBlacklistEffects.contains(effectInstance.getEffect()));
        for (MobEffectInstance ins : list) {
            if(ins.getEffect().getCategory() == MobEffectCategory.HARMFUL){
                entity.removeEffect(ins.getEffect());
                if (entity.hasEffect(ins.getEffect())) {
                    entity.getActiveEffectsMap().remove(ins.getEffect());
                }
            }
        }
    }

}
