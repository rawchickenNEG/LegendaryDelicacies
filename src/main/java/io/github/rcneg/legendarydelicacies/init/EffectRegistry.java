package io.github.rcneg.legendarydelicacies.init;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import io.github.rcneg.legendarydelicacies.effects.*;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class EffectRegistry {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, LegendaryDelicacies.MODID);
    public static final DeferredRegister<Potion> POTION = DeferredRegister.create(ForgeRegistries.POTIONS, LegendaryDelicacies.MODID);
    public static final RegistryObject<MobEffect> PERSEVERING_SOUL = MOB_EFFECTS.register("persevering_soul", () -> new LMDImmunityMobEffect(MobEffectCategory.BENEFICIAL, 0x38BDC4, List.of(ModEffects.SOUL_FRACTURE.get())));
    public static final RegistryObject<MobEffect> ATOM_ANNIHILATION = MOB_EFFECTS.register("atom_annihilation", () -> new LMDImmunityMobEffect(MobEffectCategory.BENEFICIAL, 0xB3E35D, List.of(ModEffects.ANNIHILATION.get())));
    public static final RegistryObject<MobEffect> CHORUS_REPEL = MOB_EFFECTS.register("chorus_repel", () -> new LMDImmunityMobEffect(MobEffectCategory.BENEFICIAL, -9999028, List.of(ModEffects.CHORUSINFECTION.get())));
    public static final RegistryObject<MobEffect> WITHER_STRIKE = MOB_EFFECTS.register("wither_strike", () -> new LMDImmunityMobEffect(MobEffectCategory.BENEFICIAL, -9999028, List.of(MobEffects.WITHER)));
    public static final RegistryObject<MobEffect> FROST_AVATAR = MOB_EFFECTS.register("frost_avatar", () -> new LMDImmunityMobEffect(MobEffectCategory.BENEFICIAL, -9999028, List.of(MobEffects.MOVEMENT_SLOWDOWN, ModEffects.FREEZE.get())));
    public static final RegistryObject<MobEffect> MIMIC_SHELL = MOB_EFFECTS.register("mimic_shell", () -> new LMDImmunityMobEffect(MobEffectCategory.BENEFICIAL, -9999028, List.of(MobEffects.LEVITATION, ModEffects.GRAVITY_PULL.get())));
    public static final RegistryObject<MobEffect> DUNE_SHIELD = MOB_EFFECTS.register("dune_shield", () -> new LMDImmunityMobEffect(MobEffectCategory.BENEFICIAL, -9999028, List.of(ModEffects.PHARAONS_CURSE.get())));

    public static final RegistryObject<MobEffect> LIGHTNING = MOB_EFFECTS.register("thunderstruck", () -> new LMDMobEffect(MobEffectCategory.BENEFICIAL, 0xA6A6A6));
    public static final RegistryObject<MobEffect> FLAWLESS = MOB_EFFECTS.register("flawless", () -> new FlawlessEffect(MobEffectCategory.BENEFICIAL, 0xE3F6FF));
    public static final RegistryObject<MobEffect> FLAME_EATER = MOB_EFFECTS.register("flame_eater", () -> new SpecialHealingEffect(MobEffectCategory.BENEFICIAL, -9999028));
    public static final RegistryObject<MobEffect> OVER_GROWING = MOB_EFFECTS.register("over_growing", () -> new SpecialHealingEffect(MobEffectCategory.BENEFICIAL, -9999028));
    public static final RegistryObject<MobEffect> FLYING = MOB_EFFECTS.register("flying", () -> new FlyingEffect(MobEffectCategory.BENEFICIAL, 0xE3F6FF));
    public static final RegistryObject<MobEffect> SPIKY_SKIN = MOB_EFFECTS.register("spiky_skin", () -> new LMDMobEffect(MobEffectCategory.BENEFICIAL, -9999028));
    public static final RegistryObject<MobEffect> FLOURISH = MOB_EFFECTS.register("flourish", () -> new FlourishEffect(MobEffectCategory.BENEFICIAL, -9999028));
    public static final RegistryObject<MobEffect> POSSESSED_SOUL = MOB_EFFECTS.register("possessed_soul", () -> new PossessedSoulEffect(MobEffectCategory.NEUTRAL, 0xA02727));
    public static final RegistryObject<MobEffect> RECONSTRUCTION = MOB_EFFECTS.register("reconstruction", () -> new LMDMobEffect(MobEffectCategory.BENEFICIAL, 0x649A7D));
    public static final RegistryObject<MobEffect> LIFE_FRACTURE = MOB_EFFECTS.register("life_fracture", () -> new LifeFractureEffect(MobEffectCategory.HARMFUL, 0x601126));

}
