package io.github.rcneg.legendarydelicacies.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.List;

public class OasisDrinkItem extends DrinkableItem {
    public OasisDrinkItem(Item.Properties p_41383_) {
        super(p_41383_, false, true);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        removeAllEffects(consumer);
        final Vec3 center = new Vec3(consumer.getX(), consumer.getY(), consumer.getZ());
        AABB aabb = new AABB(center, center).inflate(16);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, aabb);
        for (LivingEntity target : targets) {
            removeAllEffects(target);
        }
    }

    public void removeAllEffects(LivingEntity living){
        List<MobEffectInstance> list = new ArrayList<>(living.getActiveEffects());
        for (MobEffectInstance ins : list) {
            living.removeEffect(ins.getEffect());
            if (living.hasEffect(ins.getEffect())) {
                living.getActiveEffectsMap().remove(ins.getEffect());
            }
        }
    }
}
