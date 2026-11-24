package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Lava_eaterEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ElectricityEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.FireBreath;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElectricityEntity.class)
public class ElectricityEntityMixin {
    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void lmd$electricityConvertBottle(CallbackInfo ci) {
        ElectricityEntity electricity = (ElectricityEntity)(Object)this;
        for(ItemEntity itemEntity : electricity.level().getEntitiesOfClass(ItemEntity.class, electricity.getBoundingBox().inflate((double)0.5F, (double)4.0F, (double)0.75F))) {
            if(itemEntity.getItem().is(Items.GLASS_BOTTLE)){
                ItemStack stack = new ItemStack(ItemRegistry.LIGHTNING_BOTTLE.get());
                stack.setCount(itemEntity.getItem().getCount());
                itemEntity.spawnAtLocation(stack);
                itemEntity.discard();
            }
        }
    }

}
