package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

@Mixin(value = CuttingBoardBlock.class)
public class CuttingBoardBlockMixin {
    @Inject(
            method = "use",
            at = @At("HEAD"),
            cancellable = true)
    public void lmd$tesseractKnifePerformCut(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        BlockEntity var8 = level.getBlockEntity(pos);
        if (var8 instanceof CuttingBoardBlockEntity cuttingBoard) {
            ItemStack mainHandStack = player.getMainHandItem();
            if (mainHandStack.is(ItemRegistry.TESSERACT_KNIFE.get()) && !cuttingBoard.canAddItem(mainHandStack)) {
                boolean stop = false;
                while(!stop){
                    stop = !cuttingBoard.processStoredItemUsingTool(mainHandStack, player);
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
