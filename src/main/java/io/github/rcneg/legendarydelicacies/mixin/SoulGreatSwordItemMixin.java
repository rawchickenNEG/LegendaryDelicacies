package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.AnimatedEntity.FallingSoulBladeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.AnimatedEntity.SoulBladeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.item.custom.SoulGreatSwordItem;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.miauczel.legendary_monsters.util.BlockUtils;
import net.miauczel.legendary_monsters.util.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SoulGreatSwordItem.class)
public class SoulGreatSwordItemMixin extends SwordItem {

    public SoulGreatSwordItemMixin(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }
    @Inject(
            method = "onUseTick",
            at = @At("HEAD"),
            cancellable = true)
    public void lmd$changeOnUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration, CallbackInfo ci) {
        if(!Config.SWORD_SKILL_CHANGE.get()) return;
        int cooldown;
        SoulGreatSwordItem sword = (SoulGreatSwordItem)(Object)this;
        boolean possessed = lmd$isPossessed(stack);
        if (entity instanceof Player player) {
            if (sword.timeUsed % 5 == 0 && entity.isShiftKeyDown()) {
                cooldown = 5;
                float v = 0.075F;
                float endY = 1.5F;
                ParticleOptions particles = possessed ? ModParticles.GHOSTLY_SOUL_RED.get() : ModParticles.GHOSTLY_SOUL.get();
                sword.attractParticles(player, particles, cooldown, 4, 0.0F, 0.0F, 5.0F, endY, v);
                sword.attractParticles(player, particles, cooldown, 4, 0.0F, 0.0F, 3.0F, endY, v);
                sword.attractParticles(player, particles, cooldown, 4, 0.0F, 0.0F, 2.0F, endY, v);
            }
        }

        if (!level.isClientSide) {
            if (sword.timeUsed == 16) {
                level.playSound((Player)null, entity.getOnPos(), (SoundEvent) ModSounds.OMINOUS_WIND_UP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            ++sword.timeUsed;
            if (entity instanceof Player player) {
                int maxUseDuration = possessed ? Config.PARRY_TIME_POSSESSED.get() : Config.PARRY_TIME.get();
                if (sword.timeUsed >= maxUseDuration && !player.isShiftKeyDown() && (Boolean) ModConfig.MOB_CONFIG.canSoulGreatSwordUseParry.get()) {
                    cooldown = sword.parrySucced
                            ? (possessed ? Config.PARRY_CD_POSSESSED.get() : Config.PARRY_CD.get())
                            : (possessed ? Config.PARRY_FAILED_CD_POSSESSED.get() : Config.PARRY_FAILED_CD.get());
                    player.getCooldowns().addCooldown(stack.getItem(), cooldown);
                    sword.parrySucced = false;
                    sword.timeUsed = 0;
                    player.stopUsingItem();
                }
            }

            super.onUseTick(level, entity, stack, remainingUseDuration);
        }
        ci.cancel();
    }

    @Inject(
            method = "releaseUsing",
            at = @At("HEAD"),
            cancellable = true)
    public void lmd$changeReleaseUsing(ItemStack pStack, Level level, LivingEntity pLivingEntity, int pTimeCharged, CallbackInfo ci) {
        if(!Config.SWORD_SKILL_CHANGE.get()) return;

        if (!level.isClientSide) {
            SoulGreatSwordItem sword = (SoulGreatSwordItem)(Object)this;
            boolean possessed = lmd$isPossessed(pStack);
            int cooldown = sword.parrySucced
                    ? (possessed ? Config.PARRY_CD_POSSESSED.get() : Config.PARRY_CD.get())
                    : (possessed ? Config.PARRY_FAILED_CD_POSSESSED.get() : Config.PARRY_FAILED_CD.get());
            if (pLivingEntity instanceof Player player) {
                if (player.isShiftKeyDown()) {
                    if (sword.timeUsed >= 20) {
                        player.getCooldowns().addCooldown(pStack.getItem(), 120);
                        sword.spreadDaggers(player, 6);
                        CameraShakeEntity.cameraShake(level, player.position(), 10.0F, 0.1F, 5, 5);
                        float r = possessed ? 0.5F : 0.2F;
                        float g = possessed ? 0.0F : 0.75F;
                        float b = possessed ? 0.0F : 0.8F;
                        ParticleUtils.sendParticlesToServer(player.level(), new Circle.RingData(0.0F, 1.5707964F, 20, r, g, b, 1.0F, 70.0F, false, Circle.EnumRingBehavior.GROW), player.getX(), player.getY() + 0.25, player.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                    }
                } else if ((Boolean)ModConfig.MOB_CONFIG.canSoulGreatSwordUseParry.get()) {
                    player.getCooldowns().addCooldown(pStack.getItem(), cooldown);
                }
            }

            sword.timeUsed = 0;
            sword.parrySucced = false;
            super.releaseUsing(pStack, level, pLivingEntity, pTimeCharged);
        }
        ci.cancel();
    }

    @Inject(
            method = "spreadDaggers",
            at = @At("HEAD"),
            remap = false, cancellable = true)
    public void lmd$changeToSummonBlade(Player player, int count, CallbackInfo ci) {
        if(!Config.SWORD_SKILL_CHANGE.get()) return;

        int standingOnY = Mth.floor(player.getY());
        boolean possessed = player.hasEffect(EffectRegistry.POSSESSED_SOUL.get());
        player.level().playSound((Player)null, BlockUtils.blockPosVec3(player.position()), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        if(!possessed){
            ParticleUtils.sendParticlesToServer(player.level(), new Circle.RingData(0.0F, 1.5707964F, 20, 0.2F, 0.75F, 0.8F, 1.0F, 70.0F, false, Circle.EnumRingBehavior.GROW), player.getX(), player.getY() + 0.25, player.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
        }else{
            ParticleUtils.sendParticlesToServer(player.level(), new Circle.RingData(0.0F, 1.5707964F, 20, 0.5F, 0.0F, 0.0F, 1.0F, 70.0F, false, Circle.EnumRingBehavior.GROW), player.getX(), player.getY() + 0.25, player.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
        }
        int maxCount = 9;
        float baseAngle = player.getYHeadRot() * Mth.DEG_TO_RAD + Mth.HALF_PI;
        for(int k = 0; k < 6; ++k) {
            float f3 = baseAngle + k * Mth.TWO_PI / 6.0F;
            for(int m = 0; m < maxCount; ++m){
                if(possessed){
                    this.lmd$spawnIceSpikesAdvanced2(player.getX() + (double)Mth.cos(f3) * (1.5 + m), player.getZ() + (double)Mth.sin(f3) * (1.5 + m), (double)standingOnY, player.getY() + 1.0, f3, m, player, true);
                    this.lmd$spawnIceSpikesAdvanced(player.getX() + (double)Mth.cos(f3) * (1.5 + m), player.getZ() + (double)Mth.sin(f3) * (1.5 + m), (double)standingOnY, player.getY() + 1.0, f3, m + maxCount + 10, player, true);
                }else{
                    this.lmd$spawnIceSpikesAdvanced(player.getX() + (double)Mth.cos(f3) * (1.5 + m), player.getZ() + (double)Mth.sin(f3) * (1.5 + m), (double)standingOnY, player.getY() + 1.0, f3, m, player, false);
                }
            }
        }
        ci.cancel();
    }

    @Inject(
            method = "appendHoverText",
            at = @At("HEAD"),
            cancellable = true)
    public void lmd$changeAppendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag, CallbackInfo ci) {
        if(!Config.SWORD_SKILL_CHANGE.get()) return;

        super.appendHoverText(itemstack, world, list, flag);
        if(lmd$isPossessed(itemstack)){
            list.add(Component.translatable("tooltip.legendary_delicacies.possessed_soul").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_great_sword_1.possessed").withStyle(ChatFormatting.GRAY));
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_great_sword_2.possessed").withStyle(ChatFormatting.GRAY));
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_great_sword_3.possessed").withStyle(ChatFormatting.GRAY));
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_great_sword_4.possessed").withStyle(ChatFormatting.GRAY));
        }else{
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_great_sword").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_great_sword_1").withStyle(ChatFormatting.GRAY));
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_great_sword_2").withStyle(ChatFormatting.GRAY));
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_great_sword_3").withStyle(ChatFormatting.GRAY));
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_great_sword_4").withStyle(ChatFormatting.GRAY));
        }

        list.add(Component.translatable("item.legendary_monsters.soul_great_sword4").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("item.legendary_monsters.soul_great_sword1").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("item.legendary_monsters.soul_great_sword2").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("item.legendary_monsters.soul_great_sword3").withStyle(ChatFormatting.GRAY));

        ci.cancel();
    }


    @Inject(
            method = "maxUseDuration",
            at = @At("HEAD"),
            remap = false,
            cancellable = true)
    public void lmd$changeMaxUseDuration(CallbackInfoReturnable<Integer> cir) {
        if(!Config.SWORD_SKILL_CHANGE.get()) return;
        cir.setReturnValue(72000);
    }

    @Unique
    public void lmd$spawnIceSpikesAdvanced(double x, double z, double minY, double maxY, float rotation, int delay, Player player, boolean isRed) {
        BlockPos blockpos = new BlockPos((int)x, (int)maxY, (int)z);
        boolean flag = false;
        double d0 = 0.0;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = player.level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(player.level(), blockpos1, Direction.UP)) {
                if (!player.level().isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = player.level().getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(player.level(), blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while(blockpos.getY() >= Mth.floor(minY) - 1);

        if (flag) {
            LivingEntity entity1 = player;
            player.level().addFreshEntity(new SoulBladeEntity(player.level(), x, (double)blockpos.getY() + d0, z, rotation, delay, entity1, (float) (isRed ? 1.0f * Config.SOUL_BLADE_DAMAGE_POSSESSED.get() : 1.0f * Config.SOUL_BLADE_DAMAGE.get()), isRed));
        }

    }

    @Unique
    public void lmd$spawnIceSpikesAdvanced2(double x, double z, double minY, double maxY, float rotation, int delay, Player player, boolean isRed) {
        BlockPos blockpos = new BlockPos((int)x, (int)maxY, (int)z);
        boolean flag = false;
        double d0 = 0.0;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = player.level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(player.level(), blockpos1, Direction.UP)) {
                if (!player.level().isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = player.level().getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(player.level(), blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while(blockpos.getY() >= Mth.floor(minY) - 1);

        if (flag) {
            LivingEntity entity1 = player;
            player.level().addFreshEntity(new FallingSoulBladeEntity(player.level(), x, (double)blockpos.getY() + d0, z, rotation, delay, entity1, (float) (isRed ? 1.0f * Config.SOUL_BLADE_DAMAGE_POSSESSED.get() : 1.0f * Config.SOUL_BLADE_DAMAGE.get()), isRed));
        }

    }


    @Unique
    public boolean lmd$isPossessed(ItemStack weapon){
        CompoundTag tag = weapon.getTag();
        if (tag != null) {
            return tag.getBoolean("LMDPossessed");
        }
        return false;
    }
}
