package io.github.rcneg.legendarydelicacies.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.item.CookingPotItem;

import javax.annotation.Nullable;
import java.util.List;

public class TippedPotItems extends CookingPotItem {
    public TippedPotItems(Block block, Properties properties) {
        super(block, properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.legendary_delicacies." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.GRAY));
    }
}
