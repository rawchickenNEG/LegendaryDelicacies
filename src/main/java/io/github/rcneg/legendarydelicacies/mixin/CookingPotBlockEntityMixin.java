package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.registry.ModItems;

@Mixin(value = CookingPotBlockEntity.class)
public class CookingPotBlockEntityMixin {
    //概率不消耗材料（对于每格）
    @Redirect(
            method = "processCooking",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/items/ItemStackHandler;" +
                            "getStackInSlot(I)" +
                            "Lnet/minecraft/world/item/ItemStack;",
                    ordinal = 1,
                    remap = false
            ),
            remap = false
    )
    private ItemStack lmd$replaceIngredientWithEmpty(ItemStackHandler inventory, int slot) {
        ItemStack stack = inventory.getStackInSlot(slot);
        CookingPotBlockEntity blockEntity = (CookingPotBlockEntity)(Object)this;
        Level level = blockEntity.getLevel();
        boolean shouldKeep = level != null && !level.isClientSide() && level.getRandom().nextInt(100) < Config.POT_EXTRA.get();
        boolean flag = blockEntity.getBlockState().is(BlockRegistry.SYNTHESISING_POT.get());
        return flag && shouldKeep ? ItemStack.EMPTY : stack;
    }

    //概率增产(对厨锅)
    @ModifyVariable(
            method = "processCooking",
            at = @At("STORE"),
            remap = false,
            name = "resultStack")
    private ItemStack lmd$modifyResultStack(ItemStack originalStack) {
        ItemStack result = originalStack.copy();
        CookingPotBlockEntity blockEntity = (CookingPotBlockEntity)(Object)this;
        Level level = blockEntity.getLevel();
        boolean flag = level != null && level.getBlockState(blockEntity.getBlockPos().below()).is(BlockRegistry.NUCLEON_STOVE.get());
        boolean shouldAdd = level != null && !level.isClientSide() && level.getRandom().nextInt(100) < Config.STOVE_EXTRA.get();
        if(flag && shouldAdd){
            result.grow(1);
        }
        return result;
    }

    //改判断
    @Inject(
            method = "getMealFromItem",
            at = @At("HEAD"),
            remap = false,
            cancellable = true)
    private static void lmd$customPotGetMealFromItem(ItemStack cookingPotStack, CallbackInfoReturnable<ItemStack> cir) {
        if (cookingPotStack.is(ItemRegistry.SYNTHESISING_POT.get())) {
            CompoundTag compound = cookingPotStack.getTagElement("BlockEntityTag");
            if (compound != null) {
                CompoundTag inventoryTag = compound.getCompound("Inventory");
                if (inventoryTag.contains("Items", 9)) {
                    ItemStackHandler handler = new ItemStackHandler();
                    handler.deserializeNBT(inventoryTag);
                    cir.setReturnValue(handler.getStackInSlot(6));
                }
            }
        }
    }

    //还是改判断
    @Inject(
            method = "takeServingFromItem",
            at = @At("HEAD"),
            remap = false)
    private static void lmd$customPotTakeServingFromItem(ItemStack cookingPotStack, CallbackInfo ci) {
        if (cookingPotStack.is(ItemRegistry.SYNTHESISING_POT.get())) {
            CompoundTag compound = cookingPotStack.getTagElement("BlockEntityTag");
            if (compound != null) {
                CompoundTag inventoryTag = compound.getCompound("Inventory");
                if (inventoryTag.contains("Items", 9)) {
                    ItemStackHandler handler = new ItemStackHandler();
                    handler.deserializeNBT(inventoryTag);
                    ItemStack newMealStack = handler.getStackInSlot(6);
                    newMealStack.shrink(1);
                    compound.remove("Inventory");
                    compound.put("Inventory", handler.serializeNBT());
                }
            }
        }
    }

    //继续改判断
    @Inject(
            method = "getContainerFromItem",
            at = @At("HEAD"),
            cancellable = true,
            remap = false)
    private static void lmd$customPotGetContainerFromItem(ItemStack cookingPotStack, CallbackInfoReturnable<ItemStack> cir) {
        if (cookingPotStack.is(ItemRegistry.SYNTHESISING_POT.get())) {
            CompoundTag compound = cookingPotStack.getTagElement("BlockEntityTag");
            cir.setReturnValue(compound != null ? ItemStack.of(compound.getCompound("Container")) : ItemStack.EMPTY);
        }
    }

    //自定义锅的粒子
    @Inject(
            method = "animationTick",
            at = @At("HEAD"),
            remap = false,
            cancellable = true)
    private static void lmd$customPotAnimationTick(Level level, BlockPos pos, BlockState state, CookingPotBlockEntity cookingPot, CallbackInfo ci) {
        if(cookingPot.isHeated(level, pos)){
            RandomSource random = level.random;
            if(state.is(BlockRegistry.SYNTHESISING_POT.get())){
                double d0 = (double)pos.getX() + (double)0.5F + ((double)0.5F - random.nextDouble());
                double d1 = (double)pos.getY() + (double)0.8F;
                double d2 = (double)pos.getZ() + (double)0.5F + ((double)0.5F - random.nextDouble());
                double d3 = (double)random.nextFloat() * 0.04;
                level.addParticle(ModParticles.SMALL_ANNIHILATION_FLAME.get(), d0, d1, d2, (double)0.0F, d3, (double)0.0F);
                ci.cancel();
            }
        }
    }
}
