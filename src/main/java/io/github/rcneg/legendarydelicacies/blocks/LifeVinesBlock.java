package io.github.rcneg.legendarydelicacies.blocks;

import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class LifeVinesBlock extends CaveVinesPlantBlock {
    public LifeVinesBlock(Properties p_152959_) {
        super(p_152959_);
    }

    public ItemStack getCloneItemStack(BlockGetter p_152966_, BlockPos p_152967_, BlockState p_152968_) {
        return new ItemStack(ItemRegistry.LIFE_FRUIT_SEEDS.get());
    }

    @Override
    protected Block getBodyBlock() {
        return BlockRegistry.LIFE_VINES_PLANT.get();
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BlockRegistry.LIFE_VINES.get();
    }

    @Override
    public InteractionResult use(BlockState p_152980_, Level p_152981_, BlockPos p_152982_, Player p_152983_, InteractionHand p_152984_, BlockHitResult p_152985_) {
        return InteractionResult.PASS;
    }

    public boolean isValidBonemealTarget(LevelReader p_256221_, BlockPos p_255647_, BlockState p_256117_, boolean p_256504_) {
        Optional<BlockPos> optional = this.getHeadPos(p_256221_, p_255647_, p_256117_.getBlock());
        return optional.isPresent() && p_256221_.getBlockState(optional.get().relative(this.growthDirection)).is(Blocks.AIR);
    }

    public boolean isBonemealSuccess(Level p_221290_, RandomSource p_221291_, BlockPos p_221292_, BlockState p_221293_) {
        return true;
    }

    public void performBonemeal(ServerLevel p_221285_, RandomSource p_221286_, BlockPos p_221287_, BlockState p_221288_) {
        Optional<BlockPos> optional = this.getHeadPos(p_221285_, p_221287_, p_221288_.getBlock());
        if (optional.isPresent()) {
            BlockState blockstate = p_221285_.getBlockState(optional.get());
            ((GrowingPlantHeadBlock)blockstate.getBlock()).performBonemeal(p_221285_, p_221286_, optional.get(), blockstate);
        }
    }

    private Optional<BlockPos> getHeadPos(BlockGetter p_153323_, BlockPos p_153324_, Block p_153325_) {
        return BlockUtil.getTopConnectedBlock(p_153323_, p_153324_, p_153325_, this.growthDirection, this.getHeadBlock());
    }
}
