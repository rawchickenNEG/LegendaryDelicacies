package io.github.rcneg.legendarydelicacies.items.knives;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class VoidExterminatorSword extends SwordItem {
    public VoidExterminatorSword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("tooltip.legendary_delicacies.void_exterminator").withStyle(ChatFormatting.DARK_PURPLE));
        list.add(Component.translatable("tooltip.legendary_delicacies.void_exterminator_1").withStyle(ChatFormatting.GRAY));
    }
}
