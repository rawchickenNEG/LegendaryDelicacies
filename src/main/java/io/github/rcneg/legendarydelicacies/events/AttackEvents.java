package io.github.rcneg.legendarydelicacies.events;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import io.github.rcneg.legendarydelicacies.items.SoulSkilletItem;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.damagetype.ModDamageTypes;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.CloudGolem.Cloud_GolemEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.PossessedPaladin.PossessedPaladinEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.IceSpikeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.LightningBoltEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SoulStrike;
import net.miauczel.legendary_monsters.event.ForgeEvents;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.item.custom.SoulGreatSwordItem;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.miauczel.legendary_monsters.util.BlockUtils;
import net.miauczel.legendary_monsters.util.MathUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.List;
import java.util.Objects;

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
                    if(attacker.getRandom().nextInt(100) < 40){
                        entity.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), 100, 0));
                    }
                }
                //霜冻剑施加冰冻并额外造成冰冻伤害
                if(attacker.getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.THE_GREAT_FROST.get()) || attacker.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemRegistry.FROST_KNIFE.get())){
                    if(attacker instanceof Player player && player.getAttackStrengthScale(0.5F) >= 1.0F && player.fallDistance > 0.0F){
                        entity.addEffect(new MobEffectInstance(ModEffects.FREEZE.get(), 100, 0));
                        if(entity.fireImmune() || entity.isSensitiveToWater()){
                            event.setAmount(event.getAmount() * 2);
                        }
                    }
                }
                //金斧枪暴击施加流血+概率眩晕
                if(attacker.getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.GOLDEN_HALBERT.get())){
                    if(attacker instanceof Player player && player.getAttackStrengthScale(0.5F) >= 1.0F && player.fallDistance > 0.0F){
                        if(player.getRandom().nextInt(100) < 40){
                            entity.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 30, 0));
                            entity.playSound(SoundEvents.ANVIL_PLACE, 0.5F, 1.5F);
                        }
                        entity.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), 100, 1));
                    }
                }
            }
            //凋零刀施加凋零
            if(attacker.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemRegistry.WITHERED_KNIFE.get())){
                entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0));
            }
            if(attacker.hasEffect(EffectRegistry.LIGHTNING.get()) && damage.is(DamageTypes.INDIRECT_MAGIC)){
                //雷云百分比
                event.setAmount(event.getAmount() + entity.getMaxHealth() * 0.01F);
            }
            //虚空剑秒boss
            if(attacker.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemRegistry.VOID_EXTERMINATOR.get()) || attacker.getItemBySlot(EquipmentSlot.OFFHAND).is(ItemRegistry.VOID_EXTERMINATOR.get())){
                if(entity instanceof PossessedPaladinEntity paladin){
                    paladin.parry_cooldown = 100;
                    paladin.hasParried = true;
                }
                entity.hurt(level.damageSources().fellOutOfWorld(), entity.getMaxHealth() * 2);
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

    //LM的格挡
    @SubscribeEvent
    public static void onLivingAttackedCustomParry(LivingAttackEvent event) {
        DamageSource damageSource = event.getSource();
        LivingEntity attackedEntity = event.getEntity();
        Entity attacker1 = damageSource.getEntity();
        float sweepSize = 1.0F;
        float sweepRot = 20.0F;
        float bigSweepHeight = 3.0F;
        float bigSweepAdditionalY = 1.0F;
        if (attackedEntity instanceof Player player) {
            ItemStack stack = player.getUseItem();
            AttributeInstance attributeInstance = player.getAttribute(Attributes.ATTACK_DAMAGE);

            assert attributeInstance != null;

            float attackAttributeValue = (float)attributeInstance.getValue();
            if (stack.is(ItemRegistry.SOUL_SKILLET.get()) && player.isUsingItem() && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !damageSource.is(DamageTypeTags.BYPASSES_ARMOR) && !player.isShiftKeyDown() && (Boolean)ModConfig.MOB_CONFIG.canSoulGreatSwordUseParry.get()) {
                Item var13 = player.getUseItem().getItem();
                if (var13 instanceof SoulSkilletItem) {
                    SoulSkilletItem item = (SoulSkilletItem)var13;
                    if (item.timeUsed > item.maxUseDuration()) {
                        return;
                    }

                    if (attacker1 instanceof LivingEntity) {
                        LivingEntity parriedMob = (LivingEntity)attacker1;
                        parriedMob.hurt(attacker1.damageSources().playerAttack(player), attackAttributeValue + MathUtils.percentValue(event.getAmount(), 30.0F));
                        AABB aabb = new AABB(BlockUtils.blockPosVec3(parriedMob.position()));

                        for(LivingEntity listEntity : player.level().getEntitiesOfClass(LivingEntity.class, aabb.inflate((double)2.0F, (double)2.5F, (double)2.0F))) {
                            if (listEntity != parriedMob && listEntity != player) {
                                if (listEntity instanceof TamableAnimal) {
                                    TamableAnimal tamableAnimal = (TamableAnimal)listEntity;
                                    if (tamableAnimal.getOwner() == player) {
                                        continue;
                                    }
                                }

                                if (listEntity != null) {
                                    listEntity.hurt(listEntity.damageSources().playerAttack(player), attackAttributeValue);
                                }
                            }
                        }

                        event.setCanceled(true);
                        item.parrySucced = true;
                        CameraShakeEntity.cameraShake(player.level(), player.position(), 10.0F, 0.2F, 2, 2);
                        player.level().playSound((Player)null, player.getX(), player.getY(), player.getZ(), (SoundEvent)ModSounds.BLOCK.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
                        player.level().playSound((Player)null, player.getX(), player.getY(), player.getZ(), (SoundEvent)ModSounds.SOUL_FLY.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
                        ForgeEvents.createSweep(player, 0.0F, 0.0F, bigSweepHeight, (double)bigSweepAdditionalY, true, sweepSize, sweepRot, false);
                        player.heal(MathUtils.percentValue(player.getMaxHealth(), 20.0F));
                        float f = Mth.cos(player.yBodyRot * ((float)Math.PI / 180F));
                        float f1 = Mth.sin(player.yBodyRot * ((float)Math.PI / 180F));
                        double theta = (double)player.yBodyRot * (Math.PI / 180D);
                        ++theta;
                        double vecX = Math.cos(theta);
                        double vecZ = Math.sin(theta);
                        float angle = 40.0F;

                        for(int i = 0; i < 9; ++i) {
                            SoulStrike peq = new SoulStrike(player.level(), player, false);
                            peq.setDamage(9.0F);
                            peq.shootFromRotation(player, 0.0F, angle * (float)i, 0.0F, 0.45F, 0.0F);
                            peq.setPos(player.getX() + (double)0.0F * vecX + (double)f * (double)1.5F, player.getY() + 0.3, player.getZ() + (double)0.0F * vecZ + (double)f1 * (double)1.5F);
                        }
                    }
                }
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
            if(attacker.getRandom().nextInt(5 + amp) < 1 + amp && entity.onGround() && !(damage.is(DamageTypes.INDIRECT_MAGIC))){
                level.addFreshEntity(new IceSpikeEntity(level, entity.getX(), entity.getOnPos().getY() + 1, entity.getZ(), 0, 0, attacker));
            }
        }
        //生成雷云
        if(attacker != null && attacker.hasEffect(EffectRegistry.LIGHTNING.get())){
            int amp = attacker.getEffect(EffectRegistry.LIGHTNING.get()).getAmplifier();
            if(attacker.getRandom().nextInt(5 + amp) < 1 + amp && !(damage.is(DamageTypes.INDIRECT_MAGIC))){
                entity.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
                level.addFreshEntity(new LightningBoltEntity(level, entity.getX(), entity.getOnPos().getY() + 1, entity.getZ(), 0, 0, attacker, 30, (float)(8.0 * (Double) ModConfig.MOB_CONFIG.AxeOfLightningBoltDamageMultiplier.get())));
            }
        }
        //生成毒云
        if(attacker != null && attacker.getMainHandItem().is(ItemRegistry.MOSSY_KNIFE.get())){
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(level, entity.getX(), entity.getY(), entity.getZ());
            areaeffectcloud.setOwner(attacker);
            areaeffectcloud.setRadius(1.5F);
            areaeffectcloud.setDuration(200);
            areaeffectcloud.setRadiusPerTick(- areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
            areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.POISON, 100));
            areaeffectcloud.setFixedColor(8889187);
            level.addFreshEntity(areaeffectcloud);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        DamageSource damage = event.getSource();
        LivingEntity attacker = damage.getEntity() instanceof LivingEntity living ? living : null;
        if(attacker != null && attacker.getMainHandItem().is(ItemRegistry.MOSSY_KNIFE.get()) && entity.getMobType() == MobType.UNDEAD){
            if(level.getBlockState(entity.getOnPos()).is(BlockTags.DIRT)){
                level.setBlock(entity.getOnPos(), ModBlocks.ORGANIC_COMPOST.get().defaultBlockState(), 3);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityLoot(LivingDropsEvent event) {
        if (event.getEntity().level() instanceof ServerLevel level) {
            if(event.getSource().getEntity() instanceof LivingEntity attacker){
                LivingEntity entity = event.getEntity();
                ResourceLocation lootId = entity.getLootTable();
                LootParams ctx = new LootParams.Builder(level)
                        .withParameter(LootContextParams.THIS_ENTITY, entity)
                        .withParameter(LootContextParams.ORIGIN, entity.position())
                        .withParameter(LootContextParams.DAMAGE_SOURCE, event.getSource())
                        .withOptionalParameter(LootContextParams.KILLER_ENTITY, attacker)
                        .create(LootContextParamSets.ENTITY);
                if(attacker.getMainHandItem().is(ItemRegistry.WITHERED_KNIFE.get()) && entity.hasEffect(MobEffects.WITHER)){
                    Objects.requireNonNull(level.getServer()).getLootData().getLootTable(lootId).getRandomItems(ctx, s -> entity.spawnAtLocation(s, 1.0F));
                }
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

