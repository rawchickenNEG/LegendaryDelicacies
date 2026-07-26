package io.github.rcneg.legendarydelicacies.events;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.damagetype.ModDamageTypes;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.CloudGolem.Cloud_GolemEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.IceSpikeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.LightningBoltEntity;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttackEvents {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        DamageSource damage = event.getSource();
        //降低爆炸伤害
        if(entity.hasEffect(EffectRegistry.DUNE_SHIELD.get()) && (damage.is(DamageTypeTags.IS_EXPLOSION))){
            float result = event.getAmount();
            int n = entity.getEffect(EffectRegistry.DUNE_SHIELD.get()).getAmplifier();
            for(int i = 0; i <= n; i++){
                result *= 0.5f;
            }
            event.setAmount(result);
        }
        //反伤
        if(entity.hasEffect(EffectRegistry.SPIKY_SKIN.get()) && !(damage.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) && damage.getEntity() instanceof LivingEntity attacker){
            int amp = entity.getEffect(EffectRegistry.SPIKY_SKIN.get()).getAmplifier() + 1;
            attacker.hurt(level.damageSources().thorns(entity), event.getAmount() * amp * 0.25f);
        }
        //不毁重构
        if(entity.hasEffect(EffectRegistry.RECONSTRUCTION.get())){
            int amp = entity.getEffect(EffectRegistry.RECONSTRUCTION.get()).getAmplifier() + 2;
            if(event.getAmount() >= entity.getMaxHealth() / amp){
                entity.playSound(ModSounds.TOTEM_USE, 1, 1);
                entity.addEffect(new MobEffectInstance(ModEffects.UNBREAKABLE.get(), 60, 0));
                event.setAmount(entity.getMaxHealth() / amp);
            }
        }
        if(damage.getEntity() instanceof LivingEntity attacker){
            //堕落狂魂增强
            if(attacker.hasEffect(EffectRegistry.POSSESSED_SOUL.get())){
                //灵魂剑施加碎魂
                if(damage.is(ModDamageTypes.GHOST)){
                    if(entity.getMaxHealth() > 100){
                        if(entity.hasEffect(EffectRegistry.LIFE_FRACTURE.get())){
                            entity.addEffect(new MobEffectInstance(EffectRegistry.LIFE_FRACTURE.get(), 200, entity.getEffect(EffectRegistry.LIFE_FRACTURE.get()).getAmplifier() + 1));
                        }else{
                            entity.addEffect(new MobEffectInstance(EffectRegistry.LIFE_FRACTURE.get(), 200, 0));
                        }
                    }else{
                        if(entity.hasEffect(ModEffects.SOUL_FRACTURE.get())){
                            entity.addEffect(new MobEffectInstance(ModEffects.SOUL_FRACTURE.get(), 200, entity.getEffect(ModEffects.SOUL_FRACTURE.get()).getAmplifier() + 1));
                        }else{
                            entity.addEffect(new MobEffectInstance(ModEffects.SOUL_FRACTURE.get(), 200, 0));
                        }
                    }
                }
                //骑士剑施加流血
                if(attacker.getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.KNIGHTS_SWORD.get()) || attacker.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemRegistry.KNIGHTS_KNIFE.get())){
                    if(attacker.getRandom().nextInt(100) < 20){
                        entity.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), 100, 0));
                    }
                }

            }
            if(attacker.hasEffect(EffectRegistry.LIGHTNING.get()) && damage.is(DamageTypes.INDIRECT_MAGIC)){
                //雷云百分比
                event.setAmount(event.getAmount() + entity.getMaxHealth() * 0.01F);
            }
            if(attacker instanceof Cloud_GolemEntity && damage.is(DamageTypes.INDIRECT_MAGIC)){
                if(entity instanceof Player player){
                    ItemStack result = new ItemStack(ItemRegistry.LIGHTNING_BOTTLE.get());
                    boolean flag = false;
                    if(player.getMainHandItem().is(Items.GLASS_BOTTLE)){
                        flag = true;
                        if (!player.getAbilities().instabuild) {
                            player.getMainHandItem().shrink(1);
                        }
                    }
                    if(player.getOffhandItem().is(Items.GLASS_BOTTLE)){
                        flag = true;
                        if (!player.getAbilities().instabuild) {
                            player.getOffhandItem().shrink(1);
                        }
                    }
                    if(flag){
                        if (!player.getInventory().add(result)) {
                            player.drop(result, false);
                        }
                    }
                }
            }
        }
        //堕落狂魂减伤
        if(entity.hasEffect(EffectRegistry.POSSESSED_SOUL.get())){
            //降低雷电伤害
            if(hasEquipment(entity, ModItems.ATMOSPHERIC_BOOTS.get()) && (damage.is(DamageTypes.LIGHTNING_BOLT))){
                event.setAmount(event.getAmount() * 0.6F);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        DamageSource damage = event.getSource();
        LivingEntity attacker = damage.getEntity() instanceof LivingEntity living ? living : null;
        //免疫摔落等伤害
        if(entity.hasEffect(EffectRegistry.FLAWLESS.get()) && (damage.is(DamageTypeTags.IS_FALL) || damage.is(DamageTypeTags.IS_FIRE) || damage.is(DamageTypeTags.IS_DROWNING) || damage.is(DamageTypes.IN_WALL) || damage.is(DamageTypes.CRAMMING))){
            event.setCanceled(true);
        }
        //免疫火焰
        if(entity.hasEffect(EffectRegistry.FLAME_EATER.get()) && damage.is(DamageTypeTags.IS_FIRE)){
            event.setCanceled(true);
        }
        //堕落狂魂减伤
        if(entity.hasEffect(EffectRegistry.POSSESSED_SOUL.get())){
            //免疫摔落
            if(hasEquipment(entity, ModItems.ATMOSPHERIC_BOOTS.get()) && damage.is(DamageTypeTags.IS_FALL)){
                event.setCanceled(true);
            }
            //免疫火焰
            if(hasEquipment(entity, ModItems.FIERY_BOOTS.get()) && damage.is(DamageTypeTags.IS_FIRE)){
                event.setCanceled(true);
            }
            //潜行时
            if(entity instanceof Player player && player.isCrouching()){
                //免疫爆炸
                if(hasEquipment(player, ModItems.BLASTPROOF_HELMET.get()) && damage.is(DamageTypeTags.IS_EXPLOSION)){
                    event.setCanceled(true);
                }
                //免疫弹射物
                if(hasEquipment(player, ModItems.SHULKER_HELMET.get()) && damage.is(DamageTypeTags.IS_PROJECTILE)){
                    event.setCanceled(true);
                }
            }

        }
        //生成冰刺
        if(attacker != null && attacker.hasEffect(EffectRegistry.FROST_AVATAR.get())){
            int amp = attacker.getEffect(EffectRegistry.FROST_AVATAR.get()).getAmplifier();
            if(attacker.getRandom().nextInt(5 + amp) > 1 + amp && entity.onGround() && !(damage.is(DamageTypes.INDIRECT_MAGIC))){
                level.addFreshEntity(new IceSpikeEntity(level, entity.getX(), entity.getOnPos().getY() + 1, entity.getZ(), 0, 0, attacker));
            }
        }
        //生成雷云
        if(attacker != null && attacker.hasEffect(EffectRegistry.LIGHTNING.get())){
            int amp = attacker.getEffect(EffectRegistry.LIGHTNING.get()).getAmplifier();
            if(attacker.getRandom().nextInt(5 + amp) > 1 + amp && !(damage.is(DamageTypes.INDIRECT_MAGIC))){
                entity.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
                level.addFreshEntity(new LightningBoltEntity(level, entity.getX(), entity.getOnPos().getY() + 1, entity.getZ(), 0, 0, attacker, 30, (float)(8.0 * (Double) ModConfig.MOB_CONFIG.AxeOfLightningBoltDamageMultiplier.get())));
            }
        }
    }

    public static boolean hasEquipment(LivingEntity living, Item equip){
        for(EquipmentSlot slot : EquipmentSlot.values()){
            if(living.getItemBySlot(slot).is(equip)){
                return true;
            }
        }
        return false;
    }
}

