package io.github.rcneg.legendarydelicacies.items;

import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.List;

public class AnnihilationDrinkItem extends DrinkableItem {
    public AnnihilationDrinkItem(Properties properties) {
        super(properties, false, true);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        List<MobEffectInstance> list = new ArrayList<>(consumer.getActiveEffects());
        int i = 0;
        for (MobEffectInstance ins : list) {
            if(ins.getEffect() != ModEffects.UNBREAKABLE.get()){
                i += ins.getAmplifier() + 1;
                consumer.removeEffect(ins.getEffect());
                if (consumer.hasEffect(ins.getEffect())) {
                    consumer.getActiveEffectsMap().remove(ins.getEffect());
                }
            }
        }
        consumer.addEffect(new MobEffectInstance(ModEffects.UNBREAKABLE.get(), i * 20));
    }
}
