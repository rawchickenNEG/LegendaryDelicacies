package io.github.rcneg.legendarydelicacies.init;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import io.github.rcneg.legendarydelicacies.items.*;
import io.github.rcneg.legendarydelicacies.items.dressing.DripstoneSaltItem;
import io.github.rcneg.legendarydelicacies.items.dressing.FissionDippingItem;
import io.github.rcneg.legendarydelicacies.items.dressing.LifeFruitJamItem;
import io.github.rcneg.legendarydelicacies.items.knives.*;
import io.github.rcneg.legendarydelicacies.tier.LMDArmorTiers;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.item.ModToolTiers;
import net.miauczel.legendary_monsters.item.custom.ShatteredGreatSwordItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.PopsicleItem;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendaryDelicacies.MODID);
    //0
    public static final RegistryObject<Item> FLYING_FOOD = ITEMS.register("flying_food", () -> new ConsumableItem(foodBuilder(LMDFoodValues.FLYING_FOOD), true, true));
    public static final RegistryObject<Item> VOID_EXTERMINATOR = ITEMS.register("void_exterminator", () -> new VoidExterminatorSword(Tiers.NETHERITE, 5, -2.4F, defaultBuilder().rarity(Rarity.EPIC)));

    //材料
    public static final RegistryObject<Item> LIGHTNING_BOTTLE = ITEMS.register("lightning_bottle", () -> new TippedItems(defaultBuilder().craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> HAUNTED_NETHERITE_DEBRIS = ITEMS.register("haunted_netherite_debris", () -> new TippedItems(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BROKEN_CORRUPTED_SOUL = ITEMS.register("broken_corrupted_soul", () -> new TippedItems(defaultBuilder()));
    public static final RegistryObject<Item> SOMBER_METAL_CHUNK = ITEMS.register("somber_metal_chunk", () -> new Item(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DIAMOND_CHUNK = ITEMS.register("diamond_chunk", () -> new Item(defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LIFE_FRUIT_SEEDS = ITEMS.register("life_fruit_seeds", () -> new TippedBlockItems(BlockRegistry.LIFE_VINES.get(), defaultBuilder()));
    public static final RegistryObject<Item> PRIMAL_ICE_CHUNK = ITEMS.register("primal_ice_chunk", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> SAND_DUST = ITEMS.register("sand_dust", () -> new Item(defaultBuilder()));

    public static final RegistryObject<Item> KNIGHTS_CHALLENGE_SUMMONER_IRON = ITEMS.register("knights_challenge_summoner_iron", () -> new KnightsChallengeSummoner(defaultBuilder(), 0));
    public static final RegistryObject<Item> KNIGHTS_CHALLENGE_SUMMONER_GOLD = ITEMS.register("knights_challenge_summoner_gold", () -> new KnightsChallengeSummoner(defaultBuilder().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> KNIGHTS_CHALLENGE_SUMMONER_DIAMOND = ITEMS.register("knights_challenge_summoner_diamond", () -> new KnightsChallengeSummoner(defaultBuilder().rarity(Rarity.RARE), 2));

    //刀
    public static final RegistryObject<Item> SOUL_DAGGER = ITEMS.register("soul_dagger", SoulKnifeItem::new);
    public static final RegistryObject<Item> FROST_KNIFE = ITEMS.register("frost_knife", FrostKnifeItem::new);
    public static final RegistryObject<Item> KNIGHTS_KNIFE = ITEMS.register("knights_knife", KnightKnifeItem::new);
    public static final RegistryObject<Item> KNIFE_OF_LIGHTNING = ITEMS.register("knife_of_lightning", LightningKnifeItem::new);
    public static final RegistryObject<Item> TESSERACT_KNIFE = ITEMS.register("tesseract_knife", TesseractKnifeItem::new);
    public static final RegistryObject<Item> MONSTROUS_KNIFE = ITEMS.register("monstrous_knife", MonstrousKnifeItem::new);
    public static final RegistryObject<Item> WITHERED_KNIFE = ITEMS.register("withered_knife", WitheredKnifeItem::new);
    public static final RegistryObject<Item> MOSSY_KNIFE = ITEMS.register("mossy_knife", MossyKnifeItem::new);

    //厨具
    public static final RegistryObject<Item> RECONSTRUCTING_BOARD = ITEMS.register("reconstructing_board", () -> new TippedBlockItems(BlockRegistry.RECONSTRUCTING_BOARD.get(), defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SYNTHESISING_POT = ITEMS.register("synthesising_pot", () -> new TippedPotItems(BlockRegistry.SYNTHESISING_POT.get(), defaultBuilder().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NUCLEON_STOVE = ITEMS.register("nucleon_stove", () -> new TippedBlockItems(BlockRegistry.NUCLEON_STOVE.get(), defaultBuilder().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> INDESTRUCTIBLE_STOVE = ITEMS.register("indestructible_stove", () -> new TippedBlockItems(BlockRegistry.INDESTRUCTIBLE_STOVE.get(), defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ROYAL_STOVE = ITEMS.register("royal_stove", () -> new TippedBlockItems(BlockRegistry.ROYAL_STOVE.get(), defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ANCIENT_CAULDRON = ITEMS.register("ancient_cauldron", () -> new AncientPotItems(BlockRegistry.ANCIENT_CAULDRON.get(), defaultBuilder().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> SOUL_SKILLET = ITEMS.register("soul_skillet", () -> new SoulSkilletItem(BlockRegistry.SOUL_SKILLET.get(), defaultBuilder().stacksTo(1).rarity(Rarity.EPIC)));

    //装备
    public static final RegistryObject<Item> LIGHTNING_BOOTS = ITEMS.register("lightning_boots", () -> new LightningBootsItem(LMDArmorTiers.LIGHTNING, ArmorItem.Type.BOOTS, defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLD_SHATTERED_GREATSWORD = ITEMS.register("gold_shattered_greatsword", () -> new GoldShatteredGreatSwordItem(ModToolTiers.SHATTERED_GREATSWORD, 8, -2.8F, (new Item.Properties()).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLD_RESURRECTED_JAVELIN = ITEMS.register("gold_resurrected_javelin", () -> new GoldResurrectedJavelinItem(ModToolTiers.SHATTERED_GREATSWORD, 8, -2.8F, (new Item.Properties()).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DIAMOND_SHATTERED_GREATSWORD = ITEMS.register("diamond_shattered_greatsword", () -> new DiamondShatteredGreatSwordItem(ModToolTiers.SHATTERED_GREATSWORD, 8, -2.8F, (new Item.Properties()).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DIAMOND_RESURRECTED_JAVELIN = ITEMS.register("diamond_resurrected_javelin", () -> new DiamondResurrectedJavelinItem(ModToolTiers.SHATTERED_GREATSWORD, 8, -2.8F, (new Item.Properties()).rarity(Rarity.EPIC)));

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
    public static final RegistryObject<Item> FISSION_DIPPING = ITEMS.register("fission_dipping", () -> new FissionDippingItem(foodBuilder(LMDFoodValues.FISSION_DIPPING).craftRemainder(Items.GLASS_BOTTLE)));

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

    //蔓生系列
    public static final RegistryObject<Item> LIFE_FRUIT = ITEMS.register("life_fruit", () -> new ConsumableItem(foodBuilder(LMDFoodValues.LIFE_FRUIT), true));
    public static final RegistryObject<Item> LIFE_FRUIT_SLICE = ITEMS.register("life_fruit_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.LIFE_FRUIT_SLICE), true));
    public static final RegistryObject<Item> JUNGLE_MEDLEY = ITEMS.register("jungle_medley", () -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.JUNGLE_MEDLEY), true));
    public static final RegistryObject<Item> LIFE_FRUIT_PUDDING = ITEMS.register("life_fruit_pudding", () -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.LIFE_FRUIT_PUDDING), true));
    public static final RegistryObject<Item> LIFE_SOUP = ITEMS.register("life_soup", () -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.LIFE_SOUP), true));
    public static final RegistryObject<Item> OVERGROWN_WITH_WEEDS = ITEMS.register("overgrown_with_weeds", () -> new ConsumableItem(customContainerFoodBuilder(LMDFoodValues.OVERGROWN_WITH_WEEDS, Items.FLOWER_POT), true));
    public static final RegistryObject<Item> SPROUT_DRINK = ITEMS.register("sprout_drink", () -> new SproutDrinkItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final RegistryObject<Item> SPROUT_PASTA = ITEMS.register("sprout_pasta",() -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.SPROUT_PASTA), true));
    public static final RegistryObject<Item> BOWL_OF_HERB_SOUP = ITEMS.register("bowl_of_herb_soup", () -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.BOWL_OF_HERB_SOUP), true));
    public static final RegistryObject<Item> HERB_SOUP_POT = ITEMS.register("herb_soup_pot", () -> new BlockItem(BlockRegistry.HERB_SOUP_POT.get(), defaultBuilder().stacksTo(1)));
    public static final RegistryObject<Item> LIFE_FRUIT_JAM = ITEMS.register("life_fruit_jam", () -> new LifeFruitJamItem(foodBuilder(LMDFoodValues.LIFE_FRUIT_JAM).craftRemainder(Items.GLASS_BOTTLE)));

    //风味
    public static final RegistryObject<Item> MOSSY_COBBLESTONE_CAKE = ITEMS.register("mossy_cobblestone_cake", () -> new ItemNameBlockItem(BlockRegistry.MOSSY_COBBLESTONE_CAKE.get(), defaultBuilder()));
    public static final RegistryObject<Item> MOSSY_COBBLESTONE_CAKE_SLICE = ITEMS.register("mossy_cobblestone_cake_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.MOSSY_COBBLESTONE_CAKE_SLICE), true));
    public static final RegistryObject<Item> LIFE_FRUIT_ICE_CREAM = ITEMS.register("life_fruit_ice_cream", () -> new IceCreamItem(customContainerFoodBuilder(LMDFoodValues.LIFE_ICE_CREAM, Items.FLOWER_POT)));
    public static final RegistryObject<Item> LIFE_FRUIT_MILKSHAKE = ITEMS.register("life_fruit_milkshake", () -> new MilkShakeItem(drinkBuilder(LMDFoodValues.LIFE_MILKSHAKE)));

    //荒古系列
    //1
    public static final RegistryObject<Item> SPIKY_BUG = ITEMS.register("spiky_bug", () -> new ConsumableItem(foodBuilder(LMDFoodValues.SPIKY_BUG), true));
    public static final RegistryObject<Item> RAW_DRIPSTONE_MOBS_MEAT = ITEMS.register("raw_dripstone_mobs_meat", () -> new ConsumableItem(foodBuilder(LMDFoodValues.RAW_DRIPSTONE_MEAT), true));
    public static final RegistryObject<Item> COOKED_DRIPSTONE_MOBS_MEAT = ITEMS.register("cooked_dripstone_mobs_meat", () -> new ConsumableItem(foodBuilder(LMDFoodValues.COOKED_DRIPSTONE_MEAT), true));

    public static final RegistryObject<Item> SPIKY_BUG_ROLL = ITEMS.register("spiky_bug_roll", () -> new ConsumableItem(foodBuilder(LMDFoodValues.SPIKY_BUG_ROLL), true));
    public static final RegistryObject<Item> SPIKY_BUG_SANDWICH = ITEMS.register("spiky_bug_sandwich", () -> new ConsumableItem(foodBuilder(LMDFoodValues.SPIKY_BUG_SANDWICH), true));
    public static final RegistryObject<Item> SPIKY_BUG_KEBABS = ITEMS.register("spiky_bug_kebabs", () -> new ConsumableItem(customContainerFoodBuilder(LMDFoodValues.SPIKY_BUG_KEBABS, ModItems.ANCHOR_HANDLE.get()), true));
    public static final RegistryObject<Item> FRIED_SPIKY_BUG = ITEMS.register("fried_spiky_bug",() -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.FRIED_SPIKY_BUG), true));
    public static final RegistryObject<Item> DRIPSTONE_MOBS_MEAT_KEBABS = ITEMS.register("dripstone_mobs_meat_kebabs", () -> new ConsumableItem(customContainerFoodBuilder(LMDFoodValues.DRIPSTONE_MOBS_MEAT_KEBABS, ModItems.ANCHOR_HANDLE.get()), true));
    public static final RegistryObject<Item> DRIPSTONE_MOBS_MEAT_WRAP = ITEMS.register("dripstone_mobs_meat_wrap", () -> new ConsumableItem(foodBuilder(LMDFoodValues.DRIPSTONE_MOBS_MEAT_WRAP), true));
    public static final RegistryObject<Item> DRIPSTONE_MOBS_MEAT_STEW = ITEMS.register("dripstone_mobs_meat_stew",() -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.DRIPSTONE_MOBS_MEAT_STEW), true));
    public static final RegistryObject<Item> DRIPSTONE_MOBS_STEAK_WITH_MUSHROOM = ITEMS.register("dripstone_mobs_steak_with_mushroom",() -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.DRIPSTONE_MOBS_STEAK_WITH_MUSHROOM), true));
    public static final RegistryObject<Item> BOWL_OF_ANCIENT_KEBAB_MEAT = ITEMS.register("bowl_of_ancient_kebab_meat",() -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.BOWL_OF_ANCIENT_KEBAB_MEAT), true));
    public static final RegistryObject<Item> ANCIENT_KEBAB_STAND = ITEMS.register("ancient_kebab_stand", () -> new BlockItem(BlockRegistry.ANCIENT_KEBAB_STAND.get(), defaultBuilder().stacksTo(1)));
    public static final RegistryObject<Item> ANCIENT_DRINK = ITEMS.register("ancient_drink", () -> new AncientDrinkItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final RegistryObject<Item> DRIPSTONE_SALT = ITEMS.register("dripstone_salt", () -> new DripstoneSaltItem(foodBuilder(LMDFoodValues.DRIPSTONE_SALT).craftRemainder(Items.GLASS_BOTTLE)));

    //风味
    public static final RegistryObject<Item> SPIKY_CAKE = ITEMS.register("spiky_cake", () -> new ItemNameBlockItem(BlockRegistry.SPIKY_CAKE.get(), defaultBuilder()));
    public static final RegistryObject<Item> SPIKY_CAKE_SLICE = ITEMS.register("spiky_cake_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.ANCIENT_CAKE_SLICE), true));
    public static final RegistryObject<Item> ANCIENT_ICE_CREAM = ITEMS.register("ancient_ice_cream", () -> new IceCreamItem(bowlFoodBuilder(LMDFoodValues.ANCIENT_ICE_CREAM)));
    public static final RegistryObject<Item> ANCIENT_MILKSHAKE = ITEMS.register("ancient_milkshake", () -> new MilkShakeItem(drinkBuilder(LMDFoodValues.ANCIENT_MILKSHAKE)));

    //霜冻系列
    public static final RegistryObject<Item> XUELIAN_ICE_CUBES = ITEMS.register("xuelian_ice_cubes", () -> new ConsumableItem(foodBuilder(LMDFoodValues.XUELIAN_ICE_CUBES), true));
    public static final RegistryObject<Item> WINTER_MELON_SLICE = ITEMS.register("winter_melon_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.WINTER_MELON_SLICE), true));
    public static final RegistryObject<Item> PRIMAL_POPSICLE = ITEMS.register("primal_popsicle", () -> new ConsumablePopsicleItem(foodBuilder(LMDFoodValues.PRIMAL_POPSICLE)));
    public static final RegistryObject<Item> SWEET_SMOOTHIES = ITEMS.register("sweet_smoothies", () -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.SWEET_SMOOTHIES), true));
    public static final RegistryObject<Item> FROZEN_BUCKET = ITEMS.register("frozen_bucket", () -> new ConsumableItem(customContainerFoodBuilder(LMDFoodValues.FROZEN_BUCKET, Items.BUCKET), true));
    public static final RegistryObject<Item> ICEBERG_DRINK = ITEMS.register("iceberg_drink", () -> new ConsumableItem(drinkBuilder(LMDFoodValues.ICEBERG_DRINK), true));
    public static final RegistryObject<Item> GLACIAL_MELTWATER = ITEMS.register("glacial_meltwater", () -> new GlacialDrinkItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final RegistryObject<Item> BOWL_OF_ICED_RICE_MEDLEY = ITEMS.register("bowl_of_iced_rice_medley",() -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.BOWL_OF_ICED_RICE_MEDLEY), true));
    public static final RegistryObject<Item> ICED_RICE_MEDLEY = ITEMS.register("iced_rice_medley", () -> new BlockItem(BlockRegistry.ICED_RICE_MEDLEY.get(), defaultBuilder().stacksTo(1)));
    //风味
    public static final RegistryObject<Item> PRIMAL_ICE_CAKE = ITEMS.register("primal_ice_cake", () -> new ItemNameBlockItem(BlockRegistry.PRIMAL_ICE_CAKE.get(), defaultBuilder()));
    public static final RegistryObject<Item> PRIMAL_ICE_CAKE_SLICE = ITEMS.register("primal_ice_cake_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.PRIMAL_ICE_CAKE_SLICE), true));
    public static final RegistryObject<Item> PRIMAL_ICE_CREAM = ITEMS.register("primal_ice_cream", () -> new IceCreamItem(bowlFoodBuilder(LMDFoodValues.PRIMAL_ICE_CREAM)));
    public static final RegistryObject<Item> PRIMAL_ICE_MILKSHAKE = ITEMS.register("primal_ice_milkshake", () -> new MilkShakeItem(drinkBuilder(LMDFoodValues.PRIMAL_ICE_MILKSHAKE)));

    //沙丘系列
    public static final RegistryObject<Item> CACTUS_RABBIT_ROLL = ITEMS.register("cactus_rabbit_roll", () -> new ConsumableItem(foodBuilder(LMDFoodValues.CACTUS_RABBIT_ROLL), true));
    public static final RegistryObject<Item> DUNE_ROASTED_RABBIT = ITEMS.register("dune_roasted_rabbit", () -> new ConsumableItem(foodBuilder(LMDFoodValues.DUNE_ROASTED_RABBIT), true));
    public static final RegistryObject<Item> DUNE_STUFFED_POTATO = ITEMS.register("dune_stuffed_potato", () -> new ConsumableItem(foodBuilder(LMDFoodValues.DUNE_STUFFED_POTATO), true));
    public static final RegistryObject<Item> DUNE_MEAT_FLATBREAD = ITEMS.register("dune_meat_flatbread", () -> new ConsumableItem(foodBuilder(LMDFoodValues.DUNE_MEAT_FLATBREAD), true));
    public static final RegistryObject<Item> SANDWICH = ITEMS.register("sandwich", () -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.SANDWICH), true));
    public static final RegistryObject<Item> SANDSTORM_CRYSTAL_POPSICLE = ITEMS.register("sandstorm_crystal_popsicle", () -> new ConsumablePopsicleItem(drinkBuilder(LMDFoodValues.SANDSTORM_CRYSTAL_POPSICLE)));
    public static final RegistryObject<Item> OASIS_IN_THE_DUST = ITEMS.register("oasis_in_the_dust", () -> new OasisDrinkItem(new Item.Properties().craftRemainder(ModItems.ANCIENT_SANDSTONE_SHARD.get()).stacksTo(16)));
    public static final RegistryObject<Item> BOWL_OF_DESERT_FRIED_RICE = ITEMS.register("bowl_of_desert_fried_rice",() -> new ConsumableItem(bowlFoodBuilder(LMDFoodValues.BOWL_OF_DESERT_FRIED_RICE), true));
    public static final RegistryObject<Item> DESERT_FRIED_RICE = ITEMS.register("desert_fried_rice", () -> new BlockItem(BlockRegistry.DESERT_FRIED_RICE.get(), defaultBuilder().stacksTo(1)));
    //风味
    public static final RegistryObject<Item> DUNE_CAKE = ITEMS.register("dune_cake", () -> new ItemNameBlockItem(BlockRegistry.DUNE_CAKE.get(), defaultBuilder()));
    public static final RegistryObject<Item> DUNE_CAKE_SLICE = ITEMS.register("dune_cake_slice", () -> new ConsumableItem(foodBuilder(LMDFoodValues.DUNE_CAKE_SLICE), true));
    public static final RegistryObject<Item> SANDSTORM_ICE_CREAM = ITEMS.register("sandstorm_ice_cream", () -> new IceCreamItem(bowlFoodBuilder(LMDFoodValues.DUNE_ICE_CREAM)));
    public static final RegistryObject<Item> SANDSTORM_MILKSHAKE = ITEMS.register("sandstorm_milkshake", () -> new MilkShakeItem(drinkBuilder(LMDFoodValues.DUNE_MILKSHAKE)));


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
