package io.github.rcneg.legendarydelicacies.items.knives;

import io.github.rcneg.legendarydelicacies.config.Config;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.AnimatedEntity.FallingSoulBladeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.AnimatedEntity.SoulBladeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.util.BlockUtils;
import net.miauczel.legendary_monsters.util.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;

public class SoulKnifeItem extends KnifeItem {

    public SoulKnifeItem() {
        super(new Tier() {
            public int getUses() {
                return 2350;
            }
            public float getSpeed() {
                return 6.0F;
            }
            public float getAttackDamageBonus() {
                return 0.0F;
            }
            public int getLevel() {
                return 2;
            }
            public int getEnchantmentValue() {
                return 14;
            }
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.NETHERITE_INGOT));
            }
        }, 7.5F-1.0F, -2.0F, (new Item.Properties()).rarity(Rarity.EPIC));
    }

    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, -1);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        if(isPossessed(itemstack)){
            list.add(Component.translatable("tooltip.legendary_delicacies.possessed_soul").withStyle(ChatFormatting.DARK_RED));
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_dagger.possessed").withStyle(ChatFormatting.GRAY));
        }else{
            list.add(Component.translatable("tooltip.legendary_delicacies.soul_dagger").withStyle(ChatFormatting.GRAY));
        }
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.getItemInHand(hand);
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide && !player.getCooldowns().isOnCooldown(this) && player.onGround()) {
            boolean possessed = isPossessed(stack);
            int standingOnY = Mth.floor(player.getY());
            CameraShakeEntity.cameraShake(world, player.position(), 10.0F, 0.1F, 5, 5);
            world.playSound(null, BlockUtils.blockPosVec3(player.position()), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            float r = possessed ? 0.5F : 0.2F;
            float g = possessed ? 0.0F : 0.75F;
            float b = possessed ? 0.0F : 0.8F;
            ParticleUtils.sendParticlesToServer(player.level(), new Circle.RingData(0.0F, 1.5707964F, 20, r, g, b, 1.0F, 70.0F, false, Circle.EnumRingBehavior.GROW), player.getX(), player.getY() + 0.25, player.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            int maxCount = 3;
            float baseAngle = player.getYHeadRot() * Mth.DEG_TO_RAD + Mth.HALF_PI;
            for(int k = 0; k < 6; ++k) {
                float f3 = baseAngle + k * Mth.TWO_PI / 6.0F;
                for(int m = 0; m < maxCount; ++m){
                    if(possessed){
                        this.spawnIceSpikesAdvanced2(player.getX() + (double) Mth.cos(f3) * (1.5 + m), player.getZ() + (double) Mth.sin(f3) * (1.5 + m), (double) standingOnY, player.getY() + 1.0, f3, m, player, true);
                        this.spawnIceSpikesAdvanced(player.getX() + (double) Mth.cos(f3) * (1.5 + m), player.getZ() + (double) Mth.sin(f3) * (1.5 + m), (double) standingOnY, player.getY() + 1.0, f3, m + maxCount + 10, player, true);

                    }else {
                        this.spawnIceSpikesAdvanced(player.getX() + (double) Mth.cos(f3) * (1.5 + m), player.getZ() + (double) Mth.sin(f3) * (1.5 + m), (double) standingOnY, player.getY() + 1.0, f3, m, player, false);
                    }
                }
            }
            player.getCooldowns().addCooldown(this, 100);
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }

    public void spawnIceSpikesAdvanced(double x, double z, double minY, double maxY, float rotation, int delay, Player player, boolean isRed) {
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

    public void spawnIceSpikesAdvanced2(double x, double z, double minY, double maxY, float rotation, int delay, Player player, boolean isRed) {
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

    public boolean isPossessed(ItemStack weapon){
        CompoundTag tag = weapon.getTag();
        if (tag != null) {
            return tag.getBoolean("LMDPossessed");
        }
        return false;
    }
    
}
