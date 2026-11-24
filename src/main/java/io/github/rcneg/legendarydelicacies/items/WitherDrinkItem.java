package io.github.rcneg.legendarydelicacies.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.List;

public class WitherDrinkItem extends DrinkableItem {
    public WitherDrinkItem(Properties p_41383_) {
        super(p_41383_, false, true);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        List<MobEffectInstance> list = new ArrayList<>(consumer.getActiveEffects());
        final Vec3 center = new Vec3(consumer.getX(), consumer.getY(), consumer.getZ());
        AABB aabb = new AABB(center, center).inflate(8);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, aabb);
        for (LivingEntity target : targets) {
            for (MobEffectInstance ins : list) {
                target.addEffect(ins);
            }
        }
    }

}
