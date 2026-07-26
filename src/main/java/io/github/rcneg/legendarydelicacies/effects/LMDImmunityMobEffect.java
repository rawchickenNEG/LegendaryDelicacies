package io.github.rcneg.legendarydelicacies.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class LMDImmunityMobEffect extends LMDMobEffect {
    private final List<MobEffect> immuneEffects;
    public LMDImmunityMobEffect(MobEffectCategory p_19451_, int p_19452_, List<MobEffect> immuneEffects) {
        super(p_19451_, p_19452_);
        this.immuneEffects = immuneEffects;
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        List<MobEffectInstance> list = new ArrayList<>(entity.getActiveEffects());
        for (MobEffectInstance ins : list) {
            if(this.immuneEffects.contains(ins.getEffect())){
                entity.removeEffect(ins.getEffect());
                if (entity.hasEffect(ins.getEffect())) {
                    entity.getActiveEffectsMap().remove(ins.getEffect());
                }
            }
        }
    }

    public List<MobEffect> getImmuneEffects(){
        return immuneEffects;
    }
}
