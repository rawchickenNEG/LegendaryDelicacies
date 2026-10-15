package io.github.rcneg.legendarydelicacies.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;

public class FlyingEffect extends LMDMobEffect {
    public FlyingEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity instanceof Player player){
            player.getAbilities().mayfly = true;
        }
    }
}
