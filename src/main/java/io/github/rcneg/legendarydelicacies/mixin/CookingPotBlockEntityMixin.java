package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import io.github.rcneg.legendarydelicacies.tags.LMDTags;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

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
        if(Config.potBlacklistItems.contains(stack.getItem())){
            return stack;
        }
        return flag && shouldKeep ? ItemStack.EMPTY : stack;
    }

    @ModifyVariable(
            method = "processCooking",
            at = @At("STORE"),
            remap = false,
            name = "resultStack")
    private ItemStack lmd$modifyResultStack(ItemStack originalStack) {
        ItemStack result = originalStack.copy();
        CookingPotBlockEntity blockEntity = (CookingPotBlockEntity)(Object)this;
        Level level = blockEntity.getLevel();
        if(level != null){
            BlockState potBlock = level.getBlockState(blockEntity.getBlockPos());
            BlockState stoveBlock = level.getBlockState(blockEntity.getBlockPos().below());
            //核炉灶概率增产(对厨锅)
            if(stoveBlock.is(BlockRegistry.NUCLEON_STOVE.get()) && level.getRandom().nextInt(100) < Config.STOVE_EXTRA.get()){
                if(!Config.stoveBlacklistItems.contains(result.getItem())){
                    result.grow(1);
                }
            }
            //荒古锅倍产(对厨锅)
            if(potBlock.is(BlockRegistry.ANCIENT_CAULDRON.get()) && stoveBlock.is(LMDTags.PRIMITIVE_HEAT_SOURCES)){
                if(result.isEdible() && !result.getFoodProperties(null).getEffects().isEmpty() && Config.ANCIENT_POT_ABILITY.get()){
                    //效果持续时间翻倍
                    result.getOrCreateTag().putBoolean("LMDRich", true);
                }else if(Config.ANCIENT_POT_DOUBLE.get()){
                    result.grow(originalStack.getCount());
                }
            }
        }
        return result;
    }

    //修改烹饪时间
    @Redirect(
            method = "processCooking",
            at = @At(
                    value = "INVOKE",
                    target = "Lvectorwing/farmersdelight/common/crafting/CookingPotRecipe;getCookTime()I",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    private int lmd$changeCookingSpeed(CookingPotRecipe instance) {
        CookingPotBlockEntity blockEntity = (CookingPotBlockEntity)(Object)this;
        Level level = blockEntity.getLevel();
        if(level != null){
            BlockState potBlock = level.getBlockState(blockEntity.getBlockPos());
            BlockState stoveBlock = level.getBlockState(blockEntity.getBlockPos().below());
            //不破炉灶固定1秒烹饪
            if(stoveBlock.is(BlockRegistry.INDESTRUCTIBLE_STOVE.get())){
                return Config.SOUL_STOVE_TIME.get();
            }
            //皇家炉灶减半烹饪总时长
            if(stoveBlock.is(BlockRegistry.ROYAL_STOVE.get())){
                return (int) Math.round(instance.getCookTime() * Config.ROYAL_STOVE_SPEED.get());
            }
            //荒古锅大幅延长烹饪总时长
            if(potBlock.is(BlockRegistry.ANCIENT_CAULDRON.get()) && stoveBlock.is(LMDTags.PRIMITIVE_HEAT_SOURCES) && Config.ANCIENT_POT_DOUBLE.get()){
                return (int) Math.round(instance.getCookTime() * Config.ANCIENT_POT_SPEED.get());
            }
        }
        return instance.getCookTime();
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
