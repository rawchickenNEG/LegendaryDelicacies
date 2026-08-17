package io.github.rcneg.legendarydelicacies.items;

import io.github.rcneg.legendarydelicacies.config.Config;
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

public class AncientPotItems extends CookingPotItem {
    public AncientPotItems(Block block, Properties properties) {
        super(block, properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.legendary_delicacies." + stack.getItem();
        if(Config.ANCIENT_POT_DOUBLE.get()){
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.GRAY));
        }
        if(Config.ANCIENT_POT_ABILITY.get()){
            tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.GRAY));
        }
    }
}
