package io.github.rcneg.legendarydelicacies.items.dressing;

import io.github.rcneg.legendarydelicacies.config.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class DripstoneSaltItem extends DressingItems{
    public DripstoneSaltItem(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack resultItem(ItemStack item){
        CompoundTag tag = item.getOrCreateTag();
        if(tag.getBoolean("LMDSalty")){
            return ItemStack.EMPTY;
        }
        ItemStack resultStack = item.copy();
        resultStack.setCount(1);
        resultStack.getOrCreateTag().putBoolean("LMDSalty", true);
        return resultStack;
    }
}
