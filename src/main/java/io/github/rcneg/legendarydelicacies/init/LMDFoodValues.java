package io.github.rcneg.legendarydelicacies.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class LMDFoodValues {
    public static final FoodProperties ANNIHILATION_MILKSHAKE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).effect(
            new MobEffectInstance(EffectRegistry.ATOM_ANNIHILATION.get(), 3600), 1F
    ).alwaysEat().build();
    public static final FoodProperties ANNIHILATION_CAKE_SLICE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).effect(
            new MobEffectInstance(EffectRegistry.ATOM_ANNIHILATION.get(), 1200), 1F
    ).alwaysEat().build();
    public static final FoodProperties ANNIHILATION_ICE_CREAM = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.4F).effect(
            new MobEffectInstance(EffectRegistry.ATOM_ANNIHILATION.get(), 3600), 1F
    ).alwaysEat().build();
    public static final FoodProperties HAUNTED_MILKSHAKE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).effect(
            new MobEffectInstance(EffectRegistry.PERSEVERING_SOUL.get(), 3600), 1F
    ).alwaysEat().build();
    public static final FoodProperties HAUNTED_CAKE_SLICE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).effect(
            new MobEffectInstance(EffectRegistry.PERSEVERING_SOUL.get(), 1200), 1F
    ).alwaysEat().build();
    public static final FoodProperties HAUNTED_ICE_CREAM = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.4F).effect(
            new MobEffectInstance(EffectRegistry.PERSEVERING_SOUL.get(), 3600), 1F
    ).alwaysEat().build();
    public static final FoodProperties BLOOD_MILKSHAKE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).effect(
            new MobEffectInstance(EffectRegistry.POSSESSED_SOUL.get(), 3600), 1F
    ).alwaysEat().build();
    public static final FoodProperties BLOOD_CAKE_SLICE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).effect(
            new MobEffectInstance(EffectRegistry.POSSESSED_SOUL.get(), 1200), 1F
    ).alwaysEat().build();
    public static final FoodProperties BLOOD_ICE_CREAM = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.4F).effect(
            new MobEffectInstance(EffectRegistry.POSSESSED_SOUL.get(), 3600), 1F
    ).alwaysEat().build();
    public static final FoodProperties CLOUD_MILKSHAKE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).effect(
            new MobEffectInstance(EffectRegistry.FLAWLESS.get(), 3600), 1F
    ).alwaysEat().build();
    public static final FoodProperties CLOUD_CAKE_SLICE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).effect(
            new MobEffectInstance(EffectRegistry.FLAWLESS.get(), 1200), 1F
    ).alwaysEat().build();
    public static final FoodProperties CLOUD_ICE_CREAM = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.4F).effect(
            new MobEffectInstance(EffectRegistry.FLAWLESS.get(), 3600), 1F
    ).alwaysEat().build();
    public static final FoodProperties CUMULONIMBUS_MILKSHAKE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).effect(
            new MobEffectInstance(EffectRegistry.LIGHTNING.get(), 3600), 1F
    ).alwaysEat().build();
    public static final FoodProperties CUMULONIMBUS_CAKE_SLICE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).effect(
            new MobEffectInstance(EffectRegistry.LIGHTNING.get(), 1200), 1F
    ).alwaysEat().build();
    public static final FoodProperties CUMULONIMBUS_ICE_CREAM = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.4F).effect(
            new MobEffectInstance(EffectRegistry.LIGHTNING.get(), 3600), 1F
    ).alwaysEat().build();

    public static final FoodProperties ANNIHILATION_SAUCE_EYES_ROD = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.65F)
            .effect(new MobEffectInstance(EffectRegistry.ATOM_ANNIHILATION.get(), 3600), 1F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600), 1F)
            .alwaysEat().build();
    public static final FoodProperties ANNIHILATION_EYE_SOUP = (new FoodProperties.Builder()).nutrition(10).saturationMod(0.65F)
            .effect(new MobEffectInstance(EffectRegistry.ATOM_ANNIHILATION.get(), 6000), 1F)
            .effect(new MobEffectInstance(EffectRegistry.RECONSTRUCTION.get(), 1800), 1F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1), 1F)
            .effect(new MobEffectInstance(ModEffects.COMFORT.get(), 6000), 1F)
            .alwaysEat().build();
    public static final FoodProperties ANNIHILATION_STEW = (new FoodProperties.Builder()).nutrition(14).saturationMod(0.85F)
            .effect(new MobEffectInstance(EffectRegistry.ATOM_ANNIHILATION.get(), 3600), 1F)
            .effect(new MobEffectInstance(EffectRegistry.RECONSTRUCTION.get(), 3600, 1), 1F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 600, 2), 1F)
            .effect(new MobEffectInstance(ModEffects.NOURISHMENT.get(), 9600), 1F)
            .alwaysEat().build();
    public static final FoodProperties ANNIHILATION_EYE_PIE_SLICE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.65F)
            .effect(new MobEffectInstance(EffectRegistry.ATOM_ANNIHILATION.get(), 1800), 1F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1), 1F)
            .alwaysEat().build();
    public static final FoodProperties ANNIHILATION_BLOOM_EYE = (new FoodProperties.Builder()).nutrition(20).saturationMod(0.85F)
            .effect(new MobEffectInstance(EffectRegistry.ATOM_ANNIHILATION.get(), 9600), 1F)
            .effect(new MobEffectInstance(EffectRegistry.RECONSTRUCTION.get(), 3600, 2), 1F)
            .effect(new MobEffectInstance(net.miauczel.legendary_monsters.effect.ModEffects.UNBREAKABLE.get(), 200), 1F)
            .effect(new MobEffectInstance(ModEffects.NOURISHMENT.get(), 9600), 1F)
            .alwaysEat().build();
    public static final FoodProperties ANNIHILATION_SUODIU = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.5F)
            .effect(new MobEffectInstance(EffectRegistry.ATOM_ANNIHILATION.get(), 200), 1F)
            .alwaysEat().build();

    public static final FoodProperties HAUNTED_STICK = (new FoodProperties.Builder()).nutrition(10).saturationMod(0.65F)
            .effect(new MobEffectInstance(net.miauczel.legendary_monsters.effect.ModEffects.SOUL_RAGE.get(), 1800, 1), 1F)
            .effect(new MobEffectInstance(EffectRegistry.PERSEVERING_SOUL.get(), 1800, 2), 1F)
            .alwaysEat().build();
    public static final FoodProperties HAUNTED_SOUP = (new FoodProperties.Builder()).nutrition(9).saturationMod(0.65F)
            .effect(new MobEffectInstance(net.miauczel.legendary_monsters.effect.ModEffects.SOUL_RAGE.get(), 3600, 0), 1F)
            .effect(new MobEffectInstance(ModEffects.COMFORT.get(), 6000), 1F)
            .alwaysEat().build();
    public static final FoodProperties HAUNTED_BREAD = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.65F)
            .effect(new MobEffectInstance(EffectRegistry.PERSEVERING_SOUL.get(), 1200, 1), 1F)
            .alwaysEat().build();
    public static final FoodProperties HAUNTED_SANDWICH = (new FoodProperties.Builder()).nutrition(12).saturationMod(0.65F)
            .effect(new MobEffectInstance(EffectRegistry.PERSEVERING_SOUL.get(), 3600, 1), 1F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 1), 1F)
            .alwaysEat().build();
    public static final FoodProperties PLATE_OF_HAUNTED_KNIGHT_STEAK = (new FoodProperties.Builder()).nutrition(16).saturationMod(0.85F)
            .effect(new MobEffectInstance(EffectRegistry.PERSEVERING_SOUL.get(), 600, 3), 1F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 3), 1F)
            .effect(new MobEffectInstance(net.miauczel.legendary_monsters.effect.ModEffects.SOUL_RAGE.get(), 1800, 1), 1F)
            .effect(new MobEffectInstance(ModEffects.NOURISHMENT.get(), 9600), 1F)
            .alwaysEat().build();

    public static final FoodProperties POSSESSED_BLOOD_BOTTLE = (new FoodProperties.Builder()).nutrition(0).saturationMod(0.0F)
            .effect(new MobEffectInstance(EffectRegistry.POSSESSED_SOUL.get(), 600, 0), 1F)
            .effect(new MobEffectInstance(MobEffects.WITHER, 200, 1), 1F)
            .effect(new MobEffectInstance(net.miauczel.legendary_monsters.effect.ModEffects.SOUL_FRACTURE.get(), 200, 3), 1F)
            .alwaysEat().build();
    public static final FoodProperties BLOOD_CLOT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.35F)
            .effect(new MobEffectInstance(net.miauczel.legendary_monsters.effect.ModEffects.SOUL_RAGE.get(), 600, 0), 1F)
            .effect(new MobEffectInstance(MobEffects.HUNGER, 600, 3), 1F)
            .alwaysEat().build();
    public static final FoodProperties BLOOD_POPSICLE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F)
            .effect(new MobEffectInstance(EffectRegistry.POSSESSED_SOUL.get(), 1200), 1F)
            .alwaysEat().build();
    public static final FoodProperties BLOODY_STEAMED_BUN = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.65F)
            .effect(new MobEffectInstance(EffectRegistry.POSSESSED_SOUL.get(), 1800), 1F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0), 1F)
            .alwaysEat().build();
    public static final FoodProperties BLOODBLOOM = (new FoodProperties.Builder()).nutrition(12).saturationMod(0.65F)
            .effect(new MobEffectInstance(EffectRegistry.POSSESSED_SOUL.get(), 3600), 1F)
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1F)
            .effect(new MobEffectInstance(ModEffects.COMFORT.get(), 6000), 1F)
            .alwaysEat().build();
    public static final FoodProperties BOWL_OF_BLOOD_FEAST = (new FoodProperties.Builder()).nutrition(12).saturationMod(0.65F)
            .effect(new MobEffectInstance(EffectRegistry.POSSESSED_SOUL.get(), 9600), 1F)
            .effect(new MobEffectInstance(ModEffects.NOURISHMENT.get(), 9600), 1F)
            .alwaysEat().build();

    public static final FoodProperties FLYING_FOOD = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.6F).effect(
            new MobEffectInstance(EffectRegistry.FLYING.get(), 3600), 1F
    ).alwaysEat().build();
    public static final FoodProperties CLOUD = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.4F).effect(
            new MobEffectInstance(MobEffects.SLOW_FALLING, 1200), 1F
    ).alwaysEat().build();
    public static final FoodProperties CLOUD_BREAD = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.6F)
            .effect(new MobEffectInstance(MobEffects.SLOW_FALLING, 1200), 1F)
            .effect(new MobEffectInstance(EffectRegistry.FLAWLESS.get(), 1200), 1F)
            .alwaysEat().build();
    public static final FoodProperties CLOUD_GOLEM_COOKIE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F)
            .effect(new MobEffectInstance(EffectRegistry.FLAWLESS.get(), 200), 1F)
            .alwaysEat().fast().build();
    public static final FoodProperties CLOUD_SAUCE_RICE_CAKE = (new FoodProperties.Builder()).nutrition(10).saturationMod(0.6F)
            .effect(new MobEffectInstance(EffectRegistry.FLAWLESS.get(), 3600), 1F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 1), 1F)
            .alwaysEat().build();
    public static final FoodProperties CLOUD_MARSHMALLOW = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F)
            .effect(new MobEffectInstance(EffectRegistry.FLAWLESS.get(), 600), 1F)
            .effect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600), 1F)
            .alwaysEat().build();
    public static final FoodProperties CLOUD_PANCAKE = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.6F)
            .effect(new MobEffectInstance(EffectRegistry.FLAWLESS.get(), 3600), 1F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 1), 1F)
            .alwaysEat().build();
    public static final FoodProperties CUMULONIMBUS = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.4F)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200), 1F)
            .effect(new MobEffectInstance(MobEffects.GLOWING, 1200), 1F)
            .alwaysEat().build();
    public static final FoodProperties CUMULONIMBUS_BREAD = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.6F)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200), 1F)
            .effect(new MobEffectInstance(MobEffects.GLOWING, 1200), 1F)
            .effect(new MobEffectInstance(EffectRegistry.LIGHTNING.get(), 1200), 1F)
            .alwaysEat().build();
    public static final FoodProperties CUMULONIMBUS_GOLEM_COOKIE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F)
            .effect(new MobEffectInstance(EffectRegistry.LIGHTNING.get(), 200), 1F)
            .effect(new MobEffectInstance(MobEffects.GLOWING, 200), 1F)
            .alwaysEat().fast().build();
    public static final FoodProperties CUMULONIMBUS_SAUCE_RICE_CAKE = (new FoodProperties.Builder()).nutrition(10).saturationMod(0.6F)
            .effect(new MobEffectInstance(EffectRegistry.LIGHTNING.get(), 1800, 1), 1F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 1), 1F)
            .effect(new MobEffectInstance(MobEffects.GLOWING, 3600), 1F)
            .alwaysEat().build();
    public static final FoodProperties CUMULONIMBUS_MARSHMALLOW = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F)
            .effect(new MobEffectInstance(EffectRegistry.LIGHTNING.get(), 600), 1F)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 600), 1F)
            .effect(new MobEffectInstance(MobEffects.GLOWING, 600), 1F)
            .alwaysEat().build();
    public static final FoodProperties CUMULONIMBUS_PANCAKE = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.6F)
            .effect(new MobEffectInstance(EffectRegistry.LIGHTNING.get(), 1800, 1), 1F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1800, 2), 1F)
            .effect(new MobEffectInstance(MobEffects.GLOWING, 1800), 1F)
            .alwaysEat().build();
}
