package io.github.rcneg.legendarydelicacies.recipe;

import io.github.rcneg.legendarydelicacies.init.RecipeSerializerRegistry;
import io.github.rcneg.legendarydelicacies.items.dressing.DressingItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DressingApplyRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<DressingApplyRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(DressingApplyRecipe::new);

    public DressingApplyRecipe(ResourceLocation id, CraftingBookCategory ctg) {
        super(id, ctg);
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess access) {
        List<ItemStack> stackList = new ArrayList<ItemStack>();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack slotStack = inv.getItem(i);

            if (!slotStack.isEmpty()) {
                stackList.add(slotStack);
            }
        }

        if (stackList.size() == 2){
            if(getMagicItem(stackList) != null && getItemStack(stackList) != null && getMagicItem(stackList).getItem() instanceof DressingItems magicItem){
                if(!magicItem.resultItem(getItemStack(stackList).copy()).isEmpty()){
                    ItemStack appliedItem = stackList.get(0).getItem() instanceof DressingItems ? stackList.get(1).copy() : stackList.get(0).copy();
                    return magicItem.resultItem(appliedItem);
                }
            }
        }

        return ItemStack.EMPTY;
    }

    private static @Nullable ItemStack getItemStack(List<ItemStack> stackList) {
        ItemStack item = null;
        if(stackList.get(0).getItem() instanceof DressingItems && stackList.get(1) != null){
            item = stackList.get(1);
        }
        else if(stackList.get(1).getItem() instanceof DressingItems && stackList.get(0) != null){
            item = stackList.get(0);
        }
        return item;
    }

    private static @Nullable ItemStack getMagicItem(List<ItemStack> stackList) {
        ItemStack item = null;
        if(stackList.get(0).getItem() instanceof DressingItems && stackList.get(1) != null){
            item = stackList.get(0);
        }
        else if(stackList.get(1).getItem() instanceof DressingItems && stackList.get(0) != null){
            item = stackList.get(1);
        }
        return item;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level world) {
        List<ItemStack> stackList = new ArrayList<>();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack slotStack = inv.getItem(i);

            if (!slotStack.isEmpty()) {
                stackList.add(slotStack);
            }
        }

        if (stackList.size() == 2){
            if(getMagicItem(stackList) != null && getItemStack(stackList) != null && getMagicItem(stackList).getItem() instanceof DressingItems magicItem){
                return !magicItem.resultItem(getItemStack(stackList).copy()).isEmpty();
            }
        }
        return false;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> remainingItems = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof DressingItems) {
                if (stack.hasCraftingRemainingItem()) {
                    remainingItems.set(i, stack.getCraftingRemainingItem());
                }
            }
        }
        return remainingItems;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.MAGIC_ITEM_APPLY.get();
    }
}