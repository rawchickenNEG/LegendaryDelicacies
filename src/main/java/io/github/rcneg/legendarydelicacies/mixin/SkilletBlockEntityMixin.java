package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.SkilletBlock;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

@Mixin(value = SkilletBlockEntity.class)
public class SkilletBlockEntityMixin {
    //概率增产(对煎锅)
    @ModifyVariable(
            method = "cookAndOutputItems",
            at = @At("STORE"),
            remap = false,
            name = "resultStack")
    private ItemStack lmd$modifyResultStackForSkillet(ItemStack originalStack) {
        ItemStack result = originalStack.copy();
        SkilletBlockEntity blockEntity = (SkilletBlockEntity)(Object)this;
        Level level = blockEntity.getLevel();
        boolean flag = level != null && level.getBlockState(blockEntity.getBlockPos().below()).is(BlockRegistry.NUCLEON_STOVE.get());
        boolean shouldAdd = level != null && !level.isClientSide() && level.getRandom().nextInt(100) < Config.STOVE_EXTRA.get();
        if(flag && shouldAdd){
            result.grow(1);
        }
        return result;
    }

    //修改烹饪时间
    @Redirect(
            method = "addItemToCook",
            at = @At(
                    value = "INVOKE",
                    target = "Lvectorwing/farmersdelight/common/block/SkilletBlock;getSkilletCookingTime(II)I",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    private int lmd$changeSkilletCookingSpeed(int originalCookingTime, int fireAspectLevel) {
        SkilletBlockEntity blockEntity = (SkilletBlockEntity)(Object)this;
        Level level = blockEntity.getLevel();
        if(level != null){
            BlockState skilletBlock = level.getBlockState(blockEntity.getBlockPos());
            BlockState stoveBlock = level.getBlockState(blockEntity.getBlockPos().below());
            //不破炉灶固定1秒烹饪
            if(stoveBlock.is(BlockRegistry.INDESTRUCTIBLE_STOVE.get())){
                return 20;
            }
            //皇家炉灶减半烹饪总时长
            if(stoveBlock.is(BlockRegistry.ROYAL_STOVE.get())){
                return SkilletBlock.getSkilletCookingTime(originalCookingTime / 2, fireAspectLevel);
            }
        }
        return SkilletBlock.getSkilletCookingTime(originalCookingTime, fireAspectLevel);
    }
}
