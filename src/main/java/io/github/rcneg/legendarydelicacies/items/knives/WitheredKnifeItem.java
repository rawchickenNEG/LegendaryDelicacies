package io.github.rcneg.legendarydelicacies.items.knives;

import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;

public class WitheredKnifeItem extends KnifeItem {

    public WitheredKnifeItem() {
        super(new Tier() {
            public int getUses() {
                return 700;
            }
            public float getSpeed() {
                return 6.0F;
            }
            public float getAttackDamageBonus() {
                return 0.0F;
            }
            public int getLevel() {
                return 3;
            }
            public int getEnchantmentValue() {
                return 10;
            }
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ModItems.WITHERED_HORN.get()));
            }
        }, 7.5F-1.0F, -2.4F, (new Properties()).rarity(Rarity.EPIC).fireResistant());
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("tooltip.legendary_delicacies.withered_knife").withStyle(ChatFormatting.GRAY));
    }
}
