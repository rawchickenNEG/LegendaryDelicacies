package io.github.rcneg.legendarydelicacies.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.List;

public class AncientDrinkItem extends DrinkableItem {
    public AncientDrinkItem(Item.Properties p_41383_) {
        super(p_41383_, false, true);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        List<MobEffectInstance> list = new ArrayList<>(consumer.getActiveEffects());
        for (MobEffectInstance ins : list) {
            int duration = ins.getDuration();
            if(duration > 0 && duration < 9600){
                duration = Math.min(duration * 2, 9600);
            }
            consumer.addEffect(new MobEffectInstance(ins.getEffect(), duration, ins.getAmplifier(), ins.isAmbient(), ins.isVisible(), ins.showIcon()));
        }
    }
}
