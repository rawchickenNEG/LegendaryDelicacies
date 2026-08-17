package io.github.rcneg.legendarydelicacies.items;

import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.List;

public class SproutDrinkItem extends DrinkableItem {
    public SproutDrinkItem(Properties properties) {
        super(properties, false, true);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        List<MobEffectInstance> list = new ArrayList<>(consumer.getActiveEffects());
        int maxDur = 0;
        int maxAmp = 0;
        for (MobEffectInstance mei : list) {
            if (mei.getDuration() > maxDur) {
                maxDur = mei.getDuration();
            }
            if (mei.getAmplifier() > maxAmp) {
                maxAmp = mei.getAmplifier();
            }
        }
        consumer.addEffect(new MobEffectInstance(EffectRegistry.FLOURISH.get(), maxDur, maxAmp));
    }
}
