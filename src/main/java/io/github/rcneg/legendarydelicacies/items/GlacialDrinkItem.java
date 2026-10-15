package io.github.rcneg.legendarydelicacies.items;

import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.BlockShapes;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;
import java.util.List;

public class GlacialDrinkItem extends DrinkableItem {
    public GlacialDrinkItem(Properties p_41383_) {
        super(p_41383_, false, true);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        BlockPos pos = consumer.getOnPos().above(2);
        int r = 1;
        for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r, r, r)) {
            if(level.getBlockState(tmpPos).canBeReplaced() && !tmpPos.equals(pos)){
                level.setBlockAndUpdate(tmpPos, BlockRegistry.GLACIAL_ICE.get().defaultBlockState());
            }
        }
    }
}
