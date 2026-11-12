package io.github.rcneg.legendarydelicacies.items;

import io.github.rcneg.legendarydelicacies.config.Config;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BloodyMarkItem extends DrinkableItem {
    public BloodyMarkItem(Properties properties) {
        super(properties, false, true);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        final Vec3 center = new Vec3(consumer.getX(), consumer.getY(), consumer.getZ());
        AABB aabb = new AABB(center, center).inflate(16);
        LivingEntity nearest = level.getEntitiesOfClass(
                LivingEntity.class,
                aabb,
                entity -> entity != consumer && entity.isAlive() && !entity.getActiveEffectsMap().isEmpty()
        ).stream().min(Comparator.comparingDouble(consumer::distanceToSqr)).orElse(null);

        if(nearest != null){
            List<MobEffectInstance> list = new ArrayList<>(nearest.getActiveEffects().stream().filter(effect -> !Config.bloodBlacklistEffects.contains(effect.getEffect())).toList());
            for(MobEffectInstance effectI : list){
                consumer.addEffect(effectI);
                nearest.removeEffect(effectI.getEffect());
                if (nearest.hasEffect(effectI.getEffect())) {
                    nearest.getActiveEffectsMap().remove(effectI.getEffect());
                }
            }

        }

    }
}
