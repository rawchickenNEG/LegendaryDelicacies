package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.helper.LMDRecipeHelper;
import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static io.github.rcneg.legendarydelicacies.helper.LMDRecipeHelper.checkHasReverseRecipe;

@Mixin(value = CuttingBoardBlockEntity.class)
public class CuttingBoardBlockEntityMixin {
    /*
    @Redirect(
            method = "lambda$processStoredItemUsingTool$2",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getTagEnchantmentLevel(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/item/ItemStack;)I",
                    remap = true
            ),
            remap = false,
            require = 0
    )
    private int lmd$addCuttingBoardFortuneLevelNew(Enchantment enchantment, ItemStack toolStack) {
        int originalLevel = EnchantmentHelper.getTagEnchantmentLevel(enchantment, toolStack);
        CuttingBoardBlockEntity blockEntity = (CuttingBoardBlockEntity)(Object)this;
        boolean flag = blockEntity.getBlockState().is(BlockRegistry.RECONSTRUCTING_BOARD.get());
        return flag ? originalLevel + 3 : originalLevel;
    }
     */

    @Redirect(
            method = "lambda$processStoredItemUsingTool$2",
            at = @At(
                    value = "INVOKE",
                    target = "Lvectorwing/farmersdelight/common/crafting/CuttingBoardRecipe;rollResults(Lnet/minecraft/util/RandomSource;ILnet/minecraftforge/items/wrapper/RecipeWrapper;)Ljava/util/List;",
                    remap = true
            ),
            remap = false,
            require = 0
    )
    private List<ItemStack> lmd$addCuttingBoardResultNew(CuttingBoardRecipe instance, RandomSource random, int fortuneLevel, RecipeWrapper inventory) {
        CuttingBoardBlockEntity ent = (CuttingBoardBlockEntity)(Object)this;
        boolean flag = ent.getBlockState().is(BlockRegistry.RECONSTRUCTING_BOARD.get());

        ItemStack inputStack = inventory.getItem(0);
        List<ItemStack> initialStacks = instance.rollResults(random, fortuneLevel, inventory);
        if(!flag) return initialStacks;

        ArrayList<ItemStack> resultStacks = new ArrayList<>();
        Level level = ent.getLevel();
        for(ItemStack resultStack : initialStacks){
            if(resultStack.getItem() != inputStack.getItem()){
                ItemStack modifiedStack = resultStack.copy();
                if(level instanceof ServerLevel serverLevel && !LMDRecipeHelper.checkHasReverseRecipe(serverLevel, inputStack, modifiedStack, resultStack.getCount())){
                    modifiedStack.setCount(resultStack.getCount() + 1);
                    resultStacks.add(modifiedStack);
                }else{
                    resultStacks.add(resultStack);
                }
            }else{
                resultStacks.add(resultStack);
            }
        }
        return resultStacks;
    }
}

