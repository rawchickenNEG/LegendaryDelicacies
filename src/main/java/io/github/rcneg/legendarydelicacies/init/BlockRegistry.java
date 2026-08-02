package io.github.rcneg.legendarydelicacies.init;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import io.github.rcneg.legendarydelicacies.blocks.*;
import net.miauczel.legendary_monsters.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LegendaryDelicacies.MODID);

    public static final RegistryObject<Block> RECONSTRUCTING_BOARD = BLOCKS.register("reconstructing_board", () -> new CuttingBoardBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(15.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> SYNTHESISING_POT = BLOCKS.register("synthesising_pot", () -> new CookingPotBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(15.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> NUCLEON_STOVE = BLOCKS.register("nucleon_stove", () -> new NucleonStoveBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(15.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK).lightLevel((state) -> 14)));

    public static final RegistryObject<Block> ANNIHILATION_EYE_PIE_BLOCK = BLOCKS.register("annihilation_eye_pie", () -> new AnnihilationEyePieBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), ItemRegistry.ANNIHILATION_EYE_PIE_SLICE));
    public static final RegistryObject<Block> ANNIHILATION_CAKE = BLOCKS.register("annihilation_cake", () -> new CommonCakeBlock(Block.Properties.copy(Blocks.CAKE), ItemRegistry.ANNIHILATION_CAKE_SLICE));
    public static final RegistryObject<Block> ANNIHILATION_BOX = BLOCKS.register("annihilation_box", () -> new AnnihilationBoxBlock(Block.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public static final RegistryObject<Block> ANNIHILATION_BOX_TOP = BLOCKS.register("annihilation_box_top", () -> new AnnihilationBoxBlock(Block.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public static final RegistryObject<Block> BLOOD_CAKE = BLOCKS.register("blood_cake", () -> new CommonCakeBlock(Block.Properties.copy(Blocks.CAKE), ItemRegistry.BLOOD_CAKE_SLICE));
    public static final RegistryObject<Block> CLOUD_CAKE = BLOCKS.register("cloud_cake", () -> new CommonCakeBlock(Block.Properties.copy(Blocks.CAKE), ItemRegistry.CLOUD_CAKE_SLICE));
    public static final RegistryObject<Block> CUMULONIMBUS_CAKE = BLOCKS.register("cumulonimbus_cake", () -> new CommonCakeBlock(Block.Properties.copy(Blocks.CAKE), ItemRegistry.CUMULONIMBUS_CAKE_SLICE));
    public static final RegistryObject<Block> HAUNTED_CAKE = BLOCKS.register("haunted_cake", () -> new CommonCakeBlock(Block.Properties.copy(Blocks.CAKE), ItemRegistry.HAUNTED_CAKE_SLICE));
    public static final RegistryObject<Block> HAUNTED_KNIGHT_STEAK = BLOCKS.register("haunted_knight_steak", () -> new KnightSteakBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BLOOD_FEAST = BLOCKS.register("blood_feast", () -> new BloodFeastBlock(BlockBehaviour.Properties.copy(Blocks.WITHER_SKELETON_SKULL), ItemRegistry.BOWL_OF_BLOOD_FEAST, true));
    public static final RegistryObject<Block> CLOUD_PANCAKES = BLOCKS.register("cloud_pancakes", () -> new CloudPancakesBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), ItemRegistry.CLOUD_PANCAKE, true));
    public static final RegistryObject<Block> CUMULONIMBUS_PANCAKES = BLOCKS.register("cumulonimbus_pancakes", () -> new CloudPancakesBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), ItemRegistry.CUMULONIMBUS_PANCAKE, true));

}
