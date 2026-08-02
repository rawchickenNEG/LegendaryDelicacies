package io.github.rcneg.legendarydelicacies.items.knives;

import com.google.common.collect.Sets;
import io.github.rcneg.legendarydelicacies.entities.ThrownMonstrousKnifeEntity;
import io.github.rcneg.legendarydelicacies.entities.ThrownTesseractKnifeEntity;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;
import java.util.Set;

public class MonstrousKnifeItem extends KnifeItem {

    public MonstrousKnifeItem() {
        super(new Tier() {
            public int getUses() {
                return 1100;
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
        }, 7.0F-1.0F, -2.6F, (new Properties()));
    }

    public boolean canAttackBlock(BlockState p_43409_, Level p_43410_, BlockPos p_43411_, Player p_43412_) {
        return !p_43412_.isCreative();
    }

    public UseAnim getUseAnimation(ItemStack p_43417_) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack p_43419_) {
        return 72000;
    }

    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
        if (p_43396_ instanceof Player $$4) {
            int $$5 = this.getUseDuration(p_43394_) - p_43397_;
            if ($$5 >= 5) {
                if (!p_43395_.isClientSide) {
                    p_43394_.hurtAndBreak(1, $$4, (p_43388_) -> {
                        p_43388_.broadcastBreakEvent(p_43396_.getUsedItemHand());
                    });
                    ThrownMonstrousKnifeEntity $$7 = new ThrownMonstrousKnifeEntity(p_43395_, $$4, p_43394_);
                    $$7.shootFromRotation($$4, $$4.getXRot(), $$4.getYRot(), 0.0F, 2.5F, 1.2f);
                    if ($$4.getAbilities().instabuild) {
                        $$7.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    p_43395_.addFreshEntity($$7);
                    p_43395_.playSound((Player)null, $$7, ModSounds.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!$$4.getAbilities().instabuild) {
                        $$4.getInventory().removeItem(p_43394_);
                    }
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
        ItemStack $$3 = p_43406_.getItemInHand(p_43407_);

        p_43406_.startUsingItem(p_43407_);
        return InteractionResultHolder.consume($$3);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        Set<Enchantment> ALLOWED_ENCHANTMENTS = Sets.newHashSet(Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.MOB_LOOTING);
        if (ALLOWED_ENCHANTMENTS.contains(enchantment) || (enchantment.canEnchant(new ItemStack(Items.TRIDENT)) && enchantment != Enchantments.RIPTIDE && enchantment != Enchantments.CHANNELING)) {
            return true;
        } else {
            Set<Enchantment> DENIED_ENCHANTMENTS = Sets.newHashSet(Enchantments.BLOCK_FORTUNE);
            return !DENIED_ENCHANTMENTS.contains(enchantment) && enchantment.category.canEnchant(stack.getItem());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("tooltip.legendary_delicacies.monstrous_knife").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("tooltip.legendary_delicacies.monstrous_knife_1").withStyle(ChatFormatting.GRAY));
    }
}
