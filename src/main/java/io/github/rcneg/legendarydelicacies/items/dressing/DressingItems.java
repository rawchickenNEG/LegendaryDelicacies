package io.github.rcneg.legendarydelicacies.items.dressing;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public abstract class DressingItems extends ConsumableItem {
    public DressingItems(Item.Properties p_41383_) {
        super(p_41383_, true, true);
    }

    public ItemStack resultItem(ItemStack item){
        return ItemStack.EMPTY;
    }

    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }
}
