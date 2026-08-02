package io.github.rcneg.legendarydelicacies.init;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import io.github.rcneg.legendarydelicacies.entities.ThrownMonstrousKnifeEntity;
import io.github.rcneg.legendarydelicacies.entities.ThrownTesseractKnifeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.PossessedPaladin.PossessedPaladinEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.PosessedPaladinEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = LegendaryDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LegendaryDelicacies.MODID);

    public static final RegistryObject<EntityType<ThrownTesseractKnifeEntity>> THROWN_TESSERACT_KNIFE = abstractArrow("thrown_tesseract_knife", ThrownTesseractKnifeEntity::new);
    public static final RegistryObject<EntityType<ThrownMonstrousKnifeEntity>> THROWN_MONSTROUS_KNIFE = abstractArrow("thrown_monstrous_knife", ThrownMonstrousKnifeEntity::new);

    private static <T extends Entity> RegistryObject<EntityType<T>> abstractArrow(String name, EntityType.EntityFactory<T> factory) {
        return ENTITY_TYPES.register(name, () -> (EntityType.Builder.of(factory, MobCategory.MISC).sized(0.5F, 0.5F)
                .clientTrackingRange(4).updateInterval(20).build(name)));
    }
}
