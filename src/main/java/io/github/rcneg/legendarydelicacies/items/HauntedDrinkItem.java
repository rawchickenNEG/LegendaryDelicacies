package io.github.rcneg.legendarydelicacies.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HauntedDrinkItem extends DrinkableItem {
    public HauntedDrinkItem(Properties properties) {
        super(properties, false, true);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        List<MobEffectInstance> list = new ArrayList<>(consumer.getActiveEffects());
        List<MobEffectInstance> newEffects = new ArrayList<>();
        int totalDuration = 0;
        List<Integer> ampifierList = new ArrayList<>();
        if(!list.isEmpty()){
            for(MobEffectInstance effectInstance : list){
                if(effectInstance.getDuration() == -1){
                    totalDuration += 24000;
                }else{
                    totalDuration += effectInstance.getDuration();
                }
                ampifierList.add(effectInstance.getAmplifier());
            }
            for(int i = 0; i < list.size(); ++i){
                newEffects.add(new MobEffectInstance(list.get(i).getEffect(), totalDuration / list.size(), ampifierList.get(i)));
            }
            consumer.getActiveEffectsMap().clear();
            for(MobEffectInstance newEffect : newEffects){
                consumer.addEffect(newEffect);
            }
        }
    }
}
