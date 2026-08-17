package io.github.rcneg.legendarydelicacies;

import com.google.common.collect.ImmutableSet;
import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.init.*;
import io.github.rcneg.legendarydelicacies.mixin.BlockEntityTypeAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

import java.util.Set;

@Mod(LegendaryDelicacies.MODID)
public class LegendaryDelicacies
{
    public static final String MODID = "legendary_delicacies";

    public LegendaryDelicacies()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        EffectRegistry.MOB_EFFECTS.register(modEventBus);
        EntityTypeRegistry.ENTITY_TYPES.register(modEventBus);
        LootModifierRegistry.LOOT_MODIFIER.register(modEventBus);
        TabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        RecipeSerializerRegistry.SERIALIZERS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            applyBlockEntity(BlockRegistry.RECONSTRUCTING_BOARD.get(), ModBlockEntityTypes.CUTTING_BOARD.get());
            applyBlockEntity(BlockRegistry.SYNTHESISING_POT.get(), ModBlockEntityTypes.COOKING_POT.get());
            applyBlockEntity(BlockRegistry.ANCIENT_CAULDRON.get(), ModBlockEntityTypes.COOKING_POT.get());
            applyBlockEntity(BlockRegistry.INDESTRUCTIBLE_STOVE.get(), ModBlockEntityTypes.STOVE.get());
            applyBlockEntity(BlockRegistry.ROYAL_STOVE.get(), ModBlockEntityTypes.STOVE.get());
            applyBlockEntity(BlockRegistry.SOUL_SKILLET.get(), ModBlockEntityTypes.SKILLET.get());
        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
    }

    public void applyBlockEntity(Block block, BlockEntityType blockEntityType){
        BlockEntityTypeAccessor accessor = (BlockEntityTypeAccessor) blockEntityType;
        Set<Block> originalBlocks = accessor.lmd$getValidBlocks();
        Set<Block> newBlocks = ImmutableSet.<Block>builder().addAll(originalBlocks).add(block).build();
        accessor.lmd$setValidBlocks(newBlocks);
    }
}
