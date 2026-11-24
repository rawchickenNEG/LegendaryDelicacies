package io.github.rcneg.legendarydelicacies.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WitherStrikeEffect extends LMDImmunityMobEffect{
    public WitherStrikeEffect(MobEffectCategory p_19451_, int p_19452_, List<MobEffect> immuneEffects) {
        super(p_19451_, p_19452_, immuneEffects);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if((entity instanceof Player player && player.isSprinting()) || !(entity instanceof Player)){
            Level level = entity.level();
            Vec3 movement = entity.getDeltaMovement();
            if(movement.horizontalDistance() < 0.02) return;
            AABB aabb = entity.getBoundingBox().inflate(1);
            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, aabb).stream().filter(target -> !target.is(entity) && !target.isAlliedTo(entity)).toList();
            for (LivingEntity target : targets) {
                DamageSource damageSource = entity.damageSources().mobAttack(entity);
                float damage = (float) (movement.horizontalDistance() * 10 * (amplifier * 0.5 + 1) * entity.getAttributeValue(Attributes.ATTACK_DAMAGE));
                target.hurt(damageSource, damage);
                target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, amplifier));
            }
        }
    }
}
