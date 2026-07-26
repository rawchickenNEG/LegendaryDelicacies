package io.github.rcneg.legendarydelicacies.items.knives;

import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ElectricityEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.IceSpikeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.LightningBoltEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;

public class LightningKnifeItem extends KnifeItem {

    public LightningKnifeItem() {
        super(new Tier() {
            public int getUses() {
                return 750;
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
                return Ingredient.of(new ItemStack(Items.IRON_INGOT));
            }
        }, 4.5F-1.0F, -2.0F, (new Properties()).rarity(Rarity.EPIC));
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("tooltip.legendary_delicacies.knife_of_lightning").withStyle(ChatFormatting.GRAY));
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        int standingOnY = Mth.floor(player.getY()) - 1;
        double headY = player.getY() + 1.0;
        float yawRadians = (float)Math.toRadians((double)(90.0F + player.getYRot()));
        boolean hasSucceeded = false;
        player.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 3.0F, 1.0F);

        for(int l = 0; l < 6; ++l) {
            double d2 = 1.25 * (l + 1.2);
            if (this.spawnFangs(player.getX() + (double)Mth.cos(yawRadians) * d2 + Mth.lerp(player.getRandom().nextFloat(), -l, l) * 0.5F, headY, player.getZ() + (double)Mth.sin(yawRadians) * d2 + Mth.lerp(player.getRandom().nextFloat(), -l, l) * 0.5F, standingOnY, yawRadians, l, world, player)) {
                hasSucceeded = true;
            }
        }

        ItemStack stack = player.getItemInHand(hand);
        if (hasSucceeded) {
            player.getCooldowns().addCooldown(this, 40);
            return InteractionResultHolder.success(stack);
        } else {
            return InteractionResultHolder.pass(stack);
        }
    }

    private boolean spawnFangs(double x, double y, double z, int lowestYCheck, float yRot, int warmupDelayTicks, Level world, Player player) {
        BlockPos blockpos = BlockPos.containing(x, y, z);
        boolean flag = false;
        double d0 = 0.0;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = world.getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(world, blockpos1, Direction.UP)) {
                if (!world.isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(world, blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while(blockpos.getY() >= lowestYCheck);

        if (flag) {
            world.addFreshEntity(new LightningBoltEntity(world, x, (double)blockpos.getY() + d0, z, yRot, warmupDelayTicks, player, 30, (float)(8.0 * (Double) ModConfig.MOB_CONFIG.AxeOfLightningBoltDamageMultiplier.get())));
            return true;
        } else {
            return false;
        }
    }
}
