package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.item.custom.MonstrousAnchorItem;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MonstrousAnchorItem.class)
public class MonstrousAnchorItemMixin{
    @Inject(
            method = "hurtEnemy",
            at = @At("HEAD")
    )
    private void lmd$hurtEnemyModify(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker, CallbackInfoReturnable<Boolean> cir) {
        if(pAttacker.hasEffect(EffectRegistry.POSSESSED_SOUL.get())){
            double dashRadius = 3.0F;
            Level level = pAttacker.level();
            AABB areaOfEffect = pTarget.getBoundingBox().inflate(dashRadius);
            
            if(pAttacker instanceof Player player && player.getAttackStrengthScale(0.5F) >= 1.0F && player.fallDistance != 0.0F){
                level.playSound(null, pTarget.getOnPos(), ModSounds.IRON_GOLEM_REPAIR, SoundSource.PLAYERS,1.0F, 1.0F);
                for(LivingEntity target : pTarget.level().getEntitiesOfClass(LivingEntity.class, areaOfEffect)) {
                    if (target != pAttacker && target != null) {
                        DamageSource damageSource = new DamageSource(pAttacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), pAttacker);
                        target.hurt(damageSource, (float)((double)12.0F * (Double) ModConfig.MOB_CONFIG.MonstrousAnchorAbilityDamageMultiplier.get()));
                    }
                }
                CameraShakeEntity.cameraShake(player.level(), player.position(), 10.0F, 0.1F, 0, 5);
                for(LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate((double)3.0F))) {
                    if (entity != player) {
                        if (entity instanceof Player player1) {
                            if (player1.getAbilities().invulnerable) {
                                continue;
                            }
                        }
                        if (entity instanceof TamableAnimal) {
                            TamableAnimal animal = (TamableAnimal)entity;
                            if (animal.isTame() && animal.getOwner() == player) {
                                continue;
                            }
                        }
                        entity.push((double)0.0F, (double)0.6F, (double)0.0F);
                        entity.addEffect(new MobEffectInstance((MobEffect) ModEffects.STUN.get(), 60, 0));
                    }
                }
                if(level instanceof ServerLevel serverLevel){
                    Vec3 vec3 = pTarget.getOnPos().getCenter();
                    serverLevel.sendParticles(ModParticles.LM_COSY_SMOKE.get(), pTarget.getX(), pTarget.getY(), pTarget.getZ(), 50, 0.0D, 0.0D, 0.0D, 0.3D);
                }
            }
        }
    }
}
