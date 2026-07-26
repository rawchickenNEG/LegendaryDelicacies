package io.github.rcneg.legendarydelicacies.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class ConsumablePopsicleItem extends ConsumableItem {
    public ConsumablePopsicleItem(Item.Properties properties) {
        super(properties);
    }

    public ConsumablePopsicleItem(Item.Properties properties, boolean hasFoodEffectTooltip) {
        super(properties, hasFoodEffectTooltip);
    }

    public ConsumablePopsicleItem(Item.Properties properties, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(properties, hasFoodEffectTooltip, hasCustomTooltip);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.clearFire();
    }
}
