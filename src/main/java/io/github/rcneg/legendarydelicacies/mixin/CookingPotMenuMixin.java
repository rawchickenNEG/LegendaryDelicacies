package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotMenu;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import static net.minecraft.world.inventory.AbstractContainerMenu.stillValid;

@Mixin(value = CookingPotMenu.class)
public class CookingPotMenuMixin {
    @Shadow
    @Final
    public CookingPotBlockEntity blockEntity;

    @Inject(
            method = "stillValid",
            at = @At("HEAD"),
            cancellable = true)
    private void lmd$perseveringSoulEffect(Player playerIn, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerIn, BlockRegistry.SYNTHESISING_POT.get()));
    }
}
