package io.github.rcneg.legendarydelicacies.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vectorwing.farmersdelight.common.item.ConsumableItem;

@Mixin(value = ConsumableItem.class)
public class ConsumableItemMixin {
    @Redirect(
            method = "finishUsingItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getCraftingRemainingItem()Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack lmd$redirectGetContainer(ItemStack instance, ItemStack stack, Level level, LivingEntity consumer) {
        if(stack.getTag() != null){
            if(stack.getTag().getBoolean("LMDShouldFission") || stack.getTag().getBoolean("LMDIsOffspring")){
                return ItemStack.EMPTY;
            }
        }
        return instance.getCraftingRemainingItem();
    }
}
