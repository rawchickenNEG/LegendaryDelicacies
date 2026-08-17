package io.github.rcneg.legendarydelicacies.items.dressing;

import io.github.rcneg.legendarydelicacies.config.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class FissionDippingItem extends DressingItems{
    public FissionDippingItem(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack resultItem(ItemStack item){
        CompoundTag tag = item.getOrCreateTag();
        if(tag.getBoolean("LMDFission") || Config.fissionBlacklistItems.contains(item.getItem())){
            return ItemStack.EMPTY;
        }
        ItemStack resultStack = item.copy();
        resultStack.setCount(1);
        resultStack.getOrCreateTag().putBoolean("LMDFission", true);
        return resultStack;
    }
}
