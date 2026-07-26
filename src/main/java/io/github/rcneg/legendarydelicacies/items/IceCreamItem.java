package io.github.rcneg.legendarydelicacies.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class IceCreamItem extends ConsumableItem {
    public IceCreamItem(Properties p_40682_) {
        super(p_40682_, true);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer){
        consumer.setTicksFrozen(consumer.getTicksFrozen() + 200);
        return super.finishUsingItem(stack, level, consumer);
    }
}
