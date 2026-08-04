package io.github.rcneg.legendarydelicacies.items;

import io.github.rcneg.legendarydelicacies.config.Config;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.List;

public class BloodyMarkItem extends DrinkableItem {
    public BloodyMarkItem(Properties properties) {
        super(properties, false, true);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        List<MobEffectInstance> list = new ArrayList<>(consumer.getActiveEffects());
        list.removeIf(effectInstance -> Config.bloodBlacklistEffects.contains(effectInstance.getEffect()));
        int maxDur = 0;
        int maxAmp = 0;
        MobEffectInstance me0 = null;
        MobEffectInstance me1 = null;
        for (MobEffectInstance mei : list) {
            if (mei.getDuration() > maxDur) {
                maxDur = mei.getDuration();
                me0 = mei;
            }
            if (mei.getAmplifier() > maxAmp) {
                maxAmp = mei.getAmplifier();
                me1 = mei;
            }
        }
        if(me0 != null && me1 != null){
            if(me0.getEffect() != me1.getEffect()){
                consumer.removeEffect(me1.getEffect());
                if (consumer.hasEffect(me1.getEffect())) {
                    consumer.getActiveEffectsMap().remove(me1.getEffect());
                }
                consumer.addEffect(new MobEffectInstance(me0.getEffect(), me0.getDuration(), me1.getAmplifier(), me0.isAmbient(), me0.isVisible(), me0.showIcon()));
            }
        }
    }
}
