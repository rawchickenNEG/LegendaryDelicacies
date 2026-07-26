package io.github.rcneg.legendarydelicacies.init;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import io.github.rcneg.legendarydelicacies.items.*;
import io.github.rcneg.legendarydelicacies.items.knives.*;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendaryDelicacies.MODID);
    //0
    public static final RegistryObject<Item> FLYING_FOOD = ITEMS.register("flying_food", () -> new ConsumableItem(foodBuilder(LMDFoodValues.FLYING_FOOD), true, true));

    //材料
    public static final RegistryObject<Item> LIGHTNING_BOTTLE = ITEMS.register("lightning_bottle", () -> new TippedItems(defaultBuilder().craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> HAUNTED_NETHERITE_DEBRIS = ITEMS.register("haunted_netherite_debris", () -> new TippedItems(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BROKEN_CORRUPTED_SOUL = ITEMS.register("broken_corrupted_soul", () -> new TippedItems(defaultBuilder()));

    //刀
    public static final RegistryObject<Item> SOUL_DAGGER = ITEMS.register("soul_dagger", SoulKnifeItem::new);
    public static final RegistryObject<Item> FROST_KNIFE = ITEMS.register("frost_knife", FrostKnifeItem::new);
    public static final RegistryObject<Item> KNIGHTS_KNIFE = ITEMS.register("knights_knife", KnightKnifeItem::new);
    public static final RegistryObject<Item> KNIFE_OF_LIGHTNING = ITEMS.register("knife_of_lightning", LightningKnifeItem::new);
    public static final RegistryObject<Item> TESSERACT_KNIFE = ITEMS.register("tesseract_knife", TesseractKnifeItem::new);

    //云魔系列
    //1
    public static final RegistryObject<Item> CLOUD = ITEMS.register("cloud", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CLOUD), true));

    public static final RegistryObject<Item> CLOUD_BREAD = ITEMS.register("cloud_bread", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CLOUD_BREAD), true));
    public static final RegistryObject<Item> CLOUD_GOLEM_COOKIE = ITEMS.register("cloud_golem_cookie", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CLOUD_GOLEM_COOKIE), true));
    public static final RegistryObject<Item> CLOUD_SAUCE_RICE_CAKE = ITEMS.register("cloud_sauce_rice_cake", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CLOUD_SAUCE_RICE_CAKE), true));
    public static final RegistryObject<Item> CLOUD_MARSHMALLOW = ITEMS.register("cloud_marshmallow", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CLOUD_MARSHMALLOW), true));
    public static final RegistryObject<Item> CLOUD_PANCAKE = ITEMS.register("cloud_pancake", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CLOUD_PANCAKE), true));
    public static final RegistryObject<Item> CLOUD_PANCAKES = ITEMS.register("cloud_pancakes", () -> new BlockItem(BlockRegistry.CLOUD_PANCAKES.get(), defaultBuilder().stacksTo(1)));

    //风味
    public static final RegistryObject<Item> CLOUD_CAKE = ITEMS.register("cloud_cake", () -> new ItemNameBlockItem(BlockRegistry.CLOUD_CAKE.get(), defaultBuilder()));
    public static final RegistryObject<Item> CLOUD_CAKE_SLICE = ITEMS.register("cloud_cake_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CLOUD_CAKE_SLICE), true));
    public static final RegistryObject<Item> CLOUD_ICE_CREAM = ITEMS.register("cloud_ice_cream", () -> new IceCreamItem(bowlFoodBuilder(LMDFoodValues.CLOUD_ICE_CREAM)));
    public static final RegistryObject<Item> CLOUD_MILKSHAKE = ITEMS.register("cloud_milkshake", () -> new MilkShakeItem(drinkBuilder(LMDFoodValues.CLOUD_MILKSHAKE)));

    //2
    public static final RegistryObject<Item> CUMULONIMBUS = ITEMS.register("cumulonimbus", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CUMULONIMBUS), true));

    public static final RegistryObject<Item> CUMULONIMBUS_BREAD = ITEMS.register("cumulonimbus_bread", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CUMULONIMBUS_BREAD), true));
    public static final RegistryObject<Item> CUMULONIMBUS_GOLEM_COOKIE = ITEMS.register("cumulonimbus_golem_cookie", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CUMULONIMBUS_GOLEM_COOKIE), true));
    public static final RegistryObject<Item> CUMULONIMBUS_SAUCE_RICE_CAKE = ITEMS.register("cumulonimbus_sauce_rice_cake", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CUMULONIMBUS_SAUCE_RICE_CAKE), true));
    public static final RegistryObject<Item> CUMULONIMBUS_MARSHMALLOW = ITEMS.register("cumulonimbus_marshmallow", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CUMULONIMBUS_MARSHMALLOW), true));
    public static final RegistryObject<Item> CUMULONIMBUS_PANCAKE = ITEMS.register("cumulonimbus_pancake", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CUMULONIMBUS_PANCAKE), true));
    public static final RegistryObject<Item> CUMULONIMBUS_PANCAKES = ITEMS.register("cumulonimbus_pancakes", () -> new BlockItem(BlockRegistry.CUMULONIMBUS_PANCAKES.get(), defaultBuilder().stacksTo(1)));

    //风味
    public static final RegistryObject<Item> CUMULONIMBUS_CAKE = ITEMS.register("cumulonimbus_cake", () -> new ItemNameBlockItem(BlockRegistry.CUMULONIMBUS_CAKE.get(), defaultBuilder()));
    public static final RegistryObject<Item> CUMULONIMBUS_CAKE_SLICE = ITEMS.register("cumulonimbus_cake_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CUMULONIMBUS_CAKE_SLICE), true));
    public static final RegistryObject<Item> CUMULONIMBUS_ICE_CREAM = ITEMS.register("cumulonimbus_ice_cream", () -> new IceCreamItem(bowlFoodBuilder(LMDFoodValues.CUMULONIMBUS_ICE_CREAM)));
    public static final RegistryObject<Item> CUMULONIMBUS_MILKSHAKE = ITEMS.register("cumulonimbus_milkshake", () -> new MilkShakeItem(drinkBuilder(LMDFoodValues.CUMULONIMBUS_MILKSHAKE)));

    //湮灭系列
    public static final RegistryObject<Item> ANNIHILATION_SAUCE_EYES_ROD = ITEMS.register("annihilation_sauce_eyes_rod", () -> new ConsumableItem(foodBuilder(LMDFoodValues.ANNIHILATION_SAUCE_EYES_ROD), true));
    public static final RegistryObject<Item> ANNIHILATION_EYE_SOUP = ITEMS.register("annihilation_eye_soup", () -> new ConsumableItem(customContainerFoodBuilder(LMDFoodValues.ANNIHILATION_EYE_SOUP, ModItems.PORTAL_SHARD.get()), true));
    public static final RegistryObject<Item> ANNIHILATION_STEW = ITEMS.register("annihilation_stew", () -> new ConsumableItem(customContainerFoodBuilder(LMDFoodValues.ANNIHILATION_STEW, ModItems.PORTAL_SHARD.get()), true));
    public static final RegistryObject<Item> ANNIHILATION_EYE_PIE = ITEMS.register("annihilation_eye_pie", () -> new BlockItem(BlockRegistry.ANNIHILATION_EYE_PIE_BLOCK.get(), defaultBuilder().stacksTo(1)));
    public static final RegistryObject<Item> ANNIHILATION_EYE_PIE_SLICE = ITEMS.register("annihilation_eye_pie_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.ANNIHILATION_EYE_PIE_SLICE), true));
    public static final RegistryObject<Item> ANNIHILATION_DRINK = ITEMS.register("annihilation_drink", () -> new AnnihilationDrinkItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final RegistryObject<Item> ANNIHILATION_BLOOM_EYE = ITEMS.register("annihilation_bloom_eye", () -> new ConsumableItem(foodBuilder(LMDFoodValues.ANNIHILATION_BLOOM_EYE), true));
    public static final RegistryObject<Item> ANNIHILATION_BOX = ITEMS.register("annihilation_box", () -> new AnnihilationBoxItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ANNIHILATION_BOX_BLOCK = ITEMS.register("annihilation_box_block", () -> new ItemNameBlockItem(BlockRegistry.ANNIHILATION_BOX.get(), defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ANNIHILATION_BOX_BLOCK_TOP = ITEMS.register("annihilation_box_block_top", () -> new ItemNameBlockItem(BlockRegistry.ANNIHILATION_BOX_TOP.get(), defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ANNIHILATION_SUODIU = ITEMS.register("annihilation_suodiu", () -> new AnnihilationSuodiuItem(customContainerFoodBuilder(LMDFoodValues.ANNIHILATION_SUODIU, ModItems.PORTAL_SHARD.get()).durability(10)));

    //风味
    public static final RegistryObject<Item> ANNIHILATION_CAKE = ITEMS.register("annihilation_cake", () -> new ItemNameBlockItem(BlockRegistry.ANNIHILATION_CAKE.get(), defaultBuilder()));
    public static final RegistryObject<Item> ANNIHILATION_CAKE_SLICE = ITEMS.register("annihilation_cake_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.ANNIHILATION_CAKE_SLICE), true));
    public static final RegistryObject<Item> ANNIHILATION_ICE_CREAM = ITEMS.register("annihilation_ice_cream", () -> new IceCreamItem(customContainerFoodBuilder(LMDFoodValues.ANNIHILATION_ICE_CREAM, ModItems.PORTAL_SHARD.get())));
    public static final RegistryObject<Item> ANNIHILATION_MILKSHAKE = ITEMS.register("annihilation_milkshake", () -> new MilkShakeItem(drinkBuilder(LMDFoodValues.ANNIHILATION_MILKSHAKE)));

    //圣骑系列
    //1
    public static final RegistryObject<Item> HAUNTED_STICK = ITEMS.register("haunted_stick", () -> new ConsumableItem(foodBuilder(LMDFoodValues.HAUNTED_STICK),true));
    public static final RegistryObject<Item> HAUNTED_SOUP = ITEMS.register("haunted_soup", () -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.HAUNTED_SOUP), true));
    public static final RegistryObject<Item> HAUNTED_BREAD = ITEMS.register("haunted_bread", () -> new ConsumableItem(foodBuilder(LMDFoodValues.HAUNTED_BREAD),true));
    public static final RegistryObject<Item> HAUNTED_SANDWICH = ITEMS.register("haunted_sandwich", () -> new ConsumableItem(foodBuilder(LMDFoodValues.HAUNTED_SANDWICH),true));
    public static final RegistryObject<Item> HAUNTED_DRINK = ITEMS.register("haunted_drink", () -> new HauntedDrinkItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final RegistryObject<Item> PLATE_OF_HAUNTED_KNIGHT_STEAK = ITEMS.register("plate_of_haunted_knight_steak", () -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.PLATE_OF_HAUNTED_KNIGHT_STEAK), true));
    public static final RegistryObject<Item> HAUNTED_KNIGHT_STEAK = ITEMS.register("haunted_knight_steak", () -> new BlockItem(BlockRegistry.HAUNTED_KNIGHT_STEAK.get(), defaultBuilder().stacksTo(1)));

    //风味
    public static final RegistryObject<Item> HAUNTED_CAKE = ITEMS.register("haunted_cake", () -> new ItemNameBlockItem(BlockRegistry.HAUNTED_CAKE.get(), defaultBuilder()));
    public static final RegistryObject<Item> HAUNTED_CAKE_SLICE = ITEMS.register("haunted_cake_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.HAUNTED_CAKE_SLICE), true));
    public static final RegistryObject<Item> HAUNTED_ICE_CREAM = ITEMS.register("haunted_ice_cream", () -> new IceCreamItem(bowlFoodBuilder(LMDFoodValues.HAUNTED_ICE_CREAM)));
    public static final RegistryObject<Item> HAUNTED_MILKSHAKE = ITEMS.register("haunted_milkshake", () -> new MilkShakeItem(drinkBuilder(LMDFoodValues.HAUNTED_MILKSHAKE)));

    //2
    public static final RegistryObject<Item> POSSESSED_BLOOD_BOTTLE = ITEMS.register("possessed_blood_bottle", () -> new DrinkableItem(drinkBuilder(LMDFoodValues.POSSESSED_BLOOD_BOTTLE), true, true));
    public static final RegistryObject<Item> BLOOD_CLOT = ITEMS.register("blood_clot", () -> new ConsumableItem(foodBuilder(LMDFoodValues.BLOOD_CLOT), true));

    public static final RegistryObject<Item> BLOOD_POPSICLE = ITEMS.register("blood_popsicle", () -> new ConsumableItem(foodBuilder(LMDFoodValues.BLOOD_POPSICLE), true));
    public static final RegistryObject<Item> BLOODY_STEAMED_BUN = ITEMS.register("bloody_steamed_bun", () -> new ConsumableItem(foodBuilder(LMDFoodValues.BLOODY_STEAMED_BUN), true));
    public static final RegistryObject<Item> BLOODY_MARK = ITEMS.register("bloody_mark", () -> new BloodyMarkItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final RegistryObject<Item> BLOODBLOOM = ITEMS.register("bloodbloom", () -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.BLOODBLOOM), true));
    public static final RegistryObject<Item> BOWL_OF_BLOOD_FEAST = ITEMS.register("bowl_of_blood_feast", () -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.BOWL_OF_BLOOD_FEAST), true));
    public static final RegistryObject<Item> BLOOD_FEAST = ITEMS.register("blood_feast", () -> new BlockItem(BlockRegistry.BLOOD_FEAST.get(), defaultBuilder().stacksTo(1)));

    //风味
    public static final RegistryObject<Item> BLOOD_CAKE = ITEMS.register("blood_cake", () -> new ItemNameBlockItem(BlockRegistry.BLOOD_CAKE.get(), defaultBuilder()));
    public static final RegistryObject<Item> BLOOD_CAKE_SLICE = ITEMS.register("blood_cake_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.BLOOD_CAKE_SLICE), true));
    public static final RegistryObject<Item> BLOOD_ICE_CREAM = ITEMS.register("blood_ice_cream", () -> new IceCreamItem(bowlFoodBuilder(LMDFoodValues.BLOOD_ICE_CREAM)));
    public static final RegistryObject<Item> BLOOD_MILKSHAKE = ITEMS.register("blood_milkshake", () -> new MilkShakeItem(drinkBuilder(LMDFoodValues.BLOOD_MILKSHAKE)));

    private static Item.Properties defaultBuilder() {
        return new Item.Properties();
    }

    public static Item.Properties foodBuilder(FoodProperties food) {
        return defaultBuilder().food(food);
    }

    private static Item.Properties bowlFoodBuilder(FoodProperties food) {
        return defaultBuilder().craftRemainder(Items.BOWL).stacksTo(16).food(food);
    }

    private static Item.Properties drinkBuilder(FoodProperties food) {
        return defaultBuilder().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(food);
    }

    private static Item.Properties customContainerFoodBuilder(FoodProperties food, Item item) {
        return defaultBuilder().craftRemainder(item).stacksTo(16).food(food);
    }
}
