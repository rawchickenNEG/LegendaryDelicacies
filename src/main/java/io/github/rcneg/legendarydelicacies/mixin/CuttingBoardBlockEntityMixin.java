package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

import static net.minecraft.world.inventory.AbstractContainerMenu.stillValid;

@Mixin(value = CuttingBoardBlockEntity.class)
public class CuttingBoardBlockEntityMixin {
    @Redirect(
            method = "lambda$processStoredItemUsingTool$2",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;" +
                            "getItemEnchantmentLevel(" +
                            "Lnet/minecraft/world/item/enchantment/Enchantment;" +
                            "Lnet/minecraft/world/item/ItemStack;" +
                            ")I",
                    remap = true
            ),
            remap = false
    )
    private int lmd$addCuttingBoardFortuneLevel(Enchantment enchantment, ItemStack toolStack) {
        int originalLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, toolStack);
        CuttingBoardBlockEntity blockEntity = (CuttingBoardBlockEntity)(Object)this;
        boolean flag = blockEntity.getBlockState().is(BlockRegistry.RECONSTRUCTING_BOARD.get());
        return flag ? originalLevel + 3 : originalLevel;
    }
}

