package io.github.rcneg.legendarydelicacies.config;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LegendaryDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static ForgeConfigSpec COMMON_CONFIG;
    // COMMON
    public static ForgeConfigSpec.BooleanValue BOSS_FOOD_CANCEL_COOLDOWN;

    public static ForgeConfigSpec.IntValue POT_EXTRA;
    public static ForgeConfigSpec.IntValue BOARD_EXTRA;
    public static ForgeConfigSpec.IntValue STOVE_EXTRA;

    public static ForgeConfigSpec.IntValue PARRY_TIME;
    public static ForgeConfigSpec.IntValue PARRY_TIME_POSSESSED;
    public static ForgeConfigSpec.IntValue PARRY_CD;
    public static ForgeConfigSpec.BooleanValue SWORD_SKILL_CHANGE;
    public static ForgeConfigSpec.DoubleValue SOUL_BLADE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue SOUL_BLADE_DAMAGE_POSSESSED;
    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.push("Common");
        BOSS_FOOD_CANCEL_COOLDOWN = COMMON_BUILDER.comment("If it's true, consuming boss food will not be interrupted by bosses attack.")
                .define("FoodCDProtection", true);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("AnnihilationCookingSets");
        BOARD_EXTRA = COMMON_BUILDER.comment("Define the extra fortune level added by Annihilation Cutting Board (Particle Reconstructing Board)")
                .defineInRange("AnnihilationBoardExtra", 3, 0, 10);
        STOVE_EXTRA = COMMON_BUILDER.comment("Define the chance of increase output by 1 in Annihilation Stove (Nucleon Stove)")
                .defineInRange("AnnihilationStoveExtra", 25, 0, 100);
        POT_EXTRA = COMMON_BUILDER.comment("Define the chance of not consuming inputs in Annihilation Cooking Pot (Molecule Synthesising Pot)")
                .defineInRange("AnnihilationPotExtra", 45, 0, 100);
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
                .defineInRange("ParrySuccessCD", 20, 0, Integer.MAX_VALUE);
        SOUL_BLADE_DAMAGE = COMMON_BUILDER.comment("Define the damage of soul blades from Soul Great Sword and Soul Dagger.")
                .defineInRange("SoulBladeDamage", 10.0, 0.0, Double.MAX_VALUE);
        SOUL_BLADE_DAMAGE_POSSESSED = COMMON_BUILDER.comment("Define the damage of soul blades from Soul Great Sword and Soul Dagger in possessed status (red).")
                .defineInRange("SoulBladeDamagePossessed", 15.0, 0.0, Double.MAX_VALUE);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}