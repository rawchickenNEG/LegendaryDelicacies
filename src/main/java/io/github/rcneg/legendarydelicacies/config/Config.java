package io.github.rcneg.legendarydelicacies.config;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = LegendaryDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static ForgeConfigSpec COMMON_CONFIG;
    // COMMON
    public static ForgeConfigSpec.BooleanValue BOSS_FOOD_CANCEL_COOLDOWN;

    public static ForgeConfigSpec.IntValue POT_EXTRA;
    public static ForgeConfigSpec.IntValue BOARD_EXTRA;
    public static ForgeConfigSpec.IntValue STOVE_EXTRA;
    public static ForgeConfigSpec.IntValue SOUL_STOVE_TIME;
    public static ForgeConfigSpec.DoubleValue ROYAL_STOVE_SPEED;
    public static ForgeConfigSpec.DoubleValue ANCIENT_POT_SPEED;
    public static ForgeConfigSpec.BooleanValue ANCIENT_POT_ABILITY;
    public static ForgeConfigSpec.BooleanValue ANCIENT_POT_DOUBLE;

    public static ForgeConfigSpec.IntValue PARRY_TIME;
    public static ForgeConfigSpec.IntValue PARRY_TIME_POSSESSED;
    public static ForgeConfigSpec.IntValue PARRY_CD;
    public static ForgeConfigSpec.IntValue PARRY_CD_POSSESSED;
    public static ForgeConfigSpec.IntValue PARRY_FAILED_CD;
    public static ForgeConfigSpec.IntValue PARRY_FAILED_CD_POSSESSED;
    public static ForgeConfigSpec.BooleanValue SWORD_SKILL_CHANGE;
    public static ForgeConfigSpec.DoubleValue SOUL_BLADE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue SOUL_BLADE_DAMAGE_POSSESSED;

    public static ForgeConfigSpec.IntValue SKILLET_PARRY_TIME;
    public static ForgeConfigSpec.IntValue SKILLET_PARRY_CD;
    public static ForgeConfigSpec.IntValue SKILLET_PARRY_FAILED_CD;

    private final static ForgeConfigSpec.ConfigValue<List<? extends String>> HAUNTED_DRINK_EFFECT_BLACKLIST;
    private final static ForgeConfigSpec.ConfigValue<List<? extends String>> BLOOD_DRINK_EFFECT_BLACKLIST;
    private final static ForgeConfigSpec.ConfigValue<List<? extends String>> FLAWLESS_EFFECT_BLACKLIST;
    private final static ForgeConfigSpec.ConfigValue<List<? extends String>> POT_EXTRA_BLACKLIST;
    private final static ForgeConfigSpec.ConfigValue<List<? extends String>> STOVE_EXTRA_BLACKLIST;
    private final static ForgeConfigSpec.ConfigValue<List<? extends String>> BREED_BLACKLIST;
    private final static ForgeConfigSpec.ConfigValue<List<? extends String>> FISSION_BLACKLIST;

    public static ForgeConfigSpec.IntValue FISSION_CHANCE;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.push("Common");
        BOSS_FOOD_CANCEL_COOLDOWN = COMMON_BUILDER.comment("If it's true, consuming boss food will not be interrupted by bosses attack.")
                .define("FoodCDProtection", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("CookingSets");
        BOARD_EXTRA = COMMON_BUILDER.comment("Define the extra fortune level added by Annihilation Cutting Board (Particle Reconstructing Board)")
                .defineInRange("AnnihilationBoardExtra", 3, 0, 10);
        STOVE_EXTRA = COMMON_BUILDER.comment("Define the chance of increase output by 1 in Annihilation Stove (Nucleon Stove)")
                .defineInRange("AnnihilationStoveExtra", 25, 0, 100);
        STOVE_EXTRA_BLACKLIST = COMMON_BUILDER
                .comment("Define which items can not apply the ability of increase output by 1 in Annihilation Stove")
                .defineListAllowEmpty("AnnihilationStoveBlacklist", List.of("legendary_delicacies:flying_food"), Config::validateItemName);
        POT_EXTRA = COMMON_BUILDER.comment("Define the chance of not consuming inputs in Annihilation Cooking Pot (Molecule Synthesising Pot)")
                .defineInRange("AnnihilationPotExtra", 45, 0, 100);
        POT_EXTRA_BLACKLIST = COMMON_BUILDER
                .comment("Define which items can not apply the ability of not consuming inputs in Annihilation Cooking Pot")
                .defineListAllowEmpty("AnnihilationPotBlacklist", List.of("minecraft:barrier"), Config::validateItemName);
        SOUL_STOVE_TIME = COMMON_BUILDER.comment("Define the cooking time of Indestructible Stove (in tick).")
                .defineInRange("IndestructibleStoveTime", 20, 0, Integer.MAX_VALUE);
        ROYAL_STOVE_SPEED = COMMON_BUILDER.comment("Define the cooking time multiplier of Royal Stove.")
                .defineInRange("RoyalStoveTimeMultiplier", 0.5, 0, Double.MAX_VALUE);
        ANCIENT_POT_SPEED = COMMON_BUILDER.comment("Define the cooking time multiplier of Ancient Cauldron.")
                .defineInRange("AncientPotTimeMultiplier", 5, 0, Double.MAX_VALUE);
        ANCIENT_POT_DOUBLE = COMMON_BUILDER.comment("If it's true, Ancient Cauldron will double the outputs.")
                .define("AncientPotDouble", true);
        ANCIENT_POT_ABILITY = COMMON_BUILDER.comment("If it's true, Ancient Cauldron will double the effect duration for foods with effect. That ability will replace the double outputs ability")
                .define("AncientPotAbility", true);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("SoulSwordChanges");
        SWORD_SKILL_CHANGE = COMMON_BUILDER.comment("If it's true, Legendary Delight will be able to change the Soul Great Sword abilities.")
                .comment("Including change throwing Soul Knives to launch Soul Blades, change parry CD and max parry time.")
                .define("SwordSkillChange", true);
        PARRY_TIME = COMMON_BUILDER.comment("Define the max parry time of Soul Great Sword (in tick).")
                .defineInRange("ParryTick", 6, 0, Integer.MAX_VALUE);
        PARRY_TIME_POSSESSED = COMMON_BUILDER.comment("Define the max parry time of Soul Great Sword in possessed status (red) (in tick).")
                .defineInRange("ParryTickPossessed", 15, 0, Integer.MAX_VALUE);
        PARRY_CD = COMMON_BUILDER.comment("Define the parry CD of Soul Great Sword when you successfully parried.")
                .defineInRange("ParrySuccessCD", 70, 0, Integer.MAX_VALUE);
        PARRY_CD_POSSESSED = COMMON_BUILDER.comment("Define the parry CD of Soul Great Sword when you successfully parried in possessed status (red).")
                .defineInRange("PossessedParrySuccessCD", 10, 0, Integer.MAX_VALUE);
        PARRY_FAILED_CD = COMMON_BUILDER.comment("Define the parry CD of Soul Great Sword when you failed parrying.")
                .defineInRange("ParryFailedCD", 20, 0, Integer.MAX_VALUE);
        PARRY_FAILED_CD_POSSESSED = COMMON_BUILDER.comment("Define the parry CD of Soul Great Sword when you failed parrying in possessed status (red).")
                .defineInRange("PossessedParryFailedCD", 100, 0, Integer.MAX_VALUE);
        SOUL_BLADE_DAMAGE = COMMON_BUILDER.comment("Define the damage of soul blades from Soul Great Sword and Soul Dagger.")
                .defineInRange("SoulBladeDamage", 10.0, 0.0, Double.MAX_VALUE);
        SOUL_BLADE_DAMAGE_POSSESSED = COMMON_BUILDER.comment("Define the damage of soul blades from Soul Great Sword and Soul Dagger in possessed status (red).")
                .defineInRange("SoulBladeDamagePossessed", 15.0, 0.0, Double.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("SoulSkillet");
        SKILLET_PARRY_TIME = COMMON_BUILDER.comment("Define the max parry time of Soul Skillet (in tick).")
                .defineInRange("SkilletParryTick", 10, 0, Integer.MAX_VALUE);
        SKILLET_PARRY_CD = COMMON_BUILDER.comment("Define the parry CD of Soul Skillet when you successfully parried.")
                .defineInRange("SkilletParrySuccessCD", 50, 0, Integer.MAX_VALUE);
        SKILLET_PARRY_FAILED_CD = COMMON_BUILDER.comment("Define the parry CD of Soul Skillet when you failed parrying.")
                .defineInRange("SkilletParryFailedCD", 20, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("Drinks");
        HAUNTED_DRINK_EFFECT_BLACKLIST = COMMON_BUILDER
                .comment("Define which effects can not be affected by Haunted Drink")
                .defineListAllowEmpty("HauntedDrinkBlacklist", List.of("legendary_monsters:unbreakable"), Config::validateEffectName);
        BLOOD_DRINK_EFFECT_BLACKLIST = COMMON_BUILDER
                .comment("Define which effects can not be affected by Blood Mark")
                .defineListAllowEmpty("BloodMarkBlacklist", List.of("legendary_monsters:unbreakable"), Config::validateEffectName);
        FLAWLESS_EFFECT_BLACKLIST = COMMON_BUILDER
                .comment("Define which effects can not be cleared by Flawless Effect")
                .defineListAllowEmpty("FlawlessBlacklist", List.of("legendary_monsters:soul_fracture"), Config::validateEffectName);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("FoodAbilities");
        FISSION_CHANCE = COMMON_BUILDER.comment("Define the chance of not consuming food when food has Fission ability")
                .defineInRange("FissionChance", 30, 0, 100);
        BREED_BLACKLIST = COMMON_BUILDER
                .comment("Define which foods can not apply Breed ability")
                .defineListAllowEmpty("BreedBlacklist", List.of("legendary_delicacies:flying_food"), Config::validateItemName);
        FISSION_BLACKLIST = COMMON_BUILDER
                .comment("Define which foods can not apply Fission ability")
                .defineListAllowEmpty("FissionBlacklist", List.of("legendary_delicacies:flying_food"), Config::validateItemName);

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static List<MobEffect> hauntedBlacklistEffects;
    public static List<MobEffect> bloodBlacklistEffects;
    public static List<MobEffect> flawlessBlacklistEffects;
    public static List<Item> potBlacklistItems;
    public static List<Item> stoveBlacklistItems;
    public static List<Item> breedBlacklistItems;
    public static List<Item> fissionBlacklistItems;

    private static boolean validateEffectName(final Object obj)
    {
        return obj instanceof final String name && ForgeRegistries.MOB_EFFECTS.containsKey(new ResourceLocation(name));
    }
    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String name && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(name));
    }

    private static List<MobEffect> blackListEffects(ForgeConfigSpec.ConfigValue<List<? extends String>> effectsString){
        Set<MobEffect> blacklistEffectsByID = effectsString.get().stream()
                .map(id -> ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(id)))
                .collect(Collectors.toSet());
        return BuiltInRegistries.MOB_EFFECT.stream().filter(blacklistEffectsByID::contains).toList();
    }
    private static List<Item> blackListItems(ForgeConfigSpec.ConfigValue<List<? extends String>> itemsString){
        Set<Item> blacklistItemsByID = itemsString.get().stream()
                .map(id -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(id)))
                .collect(Collectors.toSet());
        return BuiltInRegistries.ITEM.stream().filter(blacklistItemsByID::contains).toList();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        hauntedBlacklistEffects = blackListEffects(HAUNTED_DRINK_EFFECT_BLACKLIST);
        bloodBlacklistEffects = blackListEffects(BLOOD_DRINK_EFFECT_BLACKLIST);
        flawlessBlacklistEffects = blackListEffects(FLAWLESS_EFFECT_BLACKLIST);
        potBlacklistItems = blackListItems(POT_EXTRA_BLACKLIST);
        stoveBlacklistItems = blackListItems(STOVE_EXTRA_BLACKLIST);
        fissionBlacklistItems = blackListItems(FISSION_BLACKLIST);
        breedBlacklistItems = blackListItems(BREED_BLACKLIST);
    }
}