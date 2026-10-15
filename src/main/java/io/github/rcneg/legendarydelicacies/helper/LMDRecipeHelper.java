package io.github.rcneg.legendarydelicacies.helper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LMDRecipeHelper {
    //获取所有合成配方
    public static Map<List<Ingredient>, Integer> getCraftingIngredientsForResult(ServerLevel level, ItemStack resultStack) {
        Map<List<Ingredient>, Integer> recipeInputs = new LinkedHashMap<>();
        if (level == null || resultStack.isEmpty()) return recipeInputs;

        RecipeManager manager = level.getServer().getRecipeManager();
        List<CraftingRecipe> allRecipes = manager.getAllRecipesFor(RecipeType.CRAFTING);

        for (CraftingRecipe recipe : allRecipes) {
            ItemStack output = recipe.getResultItem(level.registryAccess());
            if (!ItemStack.isSameItem(output, resultStack)) continue;
            List<Ingredient> ingredients = recipe.getIngredients();
            recipeInputs.put(ingredients, output.getCount());
        }
        return recipeInputs;
    }

    public static boolean checkHasReverseRecipe(ServerLevel level, ItemStack resultStack, ItemStack inputStack, int count){
        Map<List<Ingredient>, Integer> recipes = getCraftingIngredientsForResult(level, resultStack);
        for (Map.Entry<List<Ingredient>, Integer> recipe : recipes.entrySet()) {
            if (recipe.getValue() != 1) continue;
            int matchedCount = 0;
            boolean valid = true;
            for (Ingredient ingredient : recipe.getKey()) {
                if (ingredient.isEmpty()) {
                    continue;
                }
                if (!ingredient.test(inputStack)) {
                    valid = false;
                    break;
                }
                matchedCount++;
            }
            if (valid && matchedCount == count) return true;
        }
        return false;
    }
}