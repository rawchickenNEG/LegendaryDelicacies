package io.github.rcneg.legendarydelicacies.init;


import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import io.github.rcneg.legendarydelicacies.recipe.DressingApplyRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializerRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LegendaryDelicacies.MODID);

    public static final RegistryObject<RecipeSerializer<DressingApplyRecipe>> MAGIC_ITEM_APPLY =
            SERIALIZERS.register("dressing_item_apply", () -> DressingApplyRecipe.SERIALIZER);
}