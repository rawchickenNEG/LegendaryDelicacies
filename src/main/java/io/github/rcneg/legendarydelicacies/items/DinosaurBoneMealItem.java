package io.github.rcneg.legendarydelicacies.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DinosaurBoneMealItem extends Item {
    public DinosaurBoneMealItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        if (!level.getBlockState(clickedPos).is(Blocks.GRASS_BLOCK)) {
            return InteractionResult.PASS;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }

        RandomSource random = level.getRandom();

        for (int i = 0; i < 16; i++) {

            int xOffset = random.nextInt(7) - 3;
            int zOffset = random.nextInt(7) - 3;
            BlockPos groundPos = clickedPos.offset(xOffset, 0, zOffset);
            BlockPos plantPos = groundPos.above();

            if (!level.getBlockState(groundPos).is(Blocks.GRASS_BLOCK)) {
                continue;
            }
            if (!level.getBlockState(plantPos).isAir()) {
                continue;
            }
            if (random.nextBoolean()) {
                BlockState torchflower = Blocks.TORCHFLOWER.defaultBlockState();
                if (torchflower.canSurvive(level, plantPos)) {
                    level.setBlock(plantPos, torchflower, 3);
                }

            } else {
                if (!level.getBlockState(plantPos).isAir() || !level.getBlockState(plantPos.above()).isAir()) {
                    continue;
                }
                BlockState pitcher = Blocks.PITCHER_PLANT.defaultBlockState();
                if (pitcher.canSurvive(level, plantPos)) {
                    ((DoublePlantBlock) Blocks.PITCHER_PLANT).placeAt(level, pitcher, plantPos, 3);
                }
            }
        }
        level.levelEvent(1505, clickedPos.above(), 0);
        level.playSound(null, clickedPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);

        if (!context.getPlayer().getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }
        return InteractionResult.CONSUME;
    }
}