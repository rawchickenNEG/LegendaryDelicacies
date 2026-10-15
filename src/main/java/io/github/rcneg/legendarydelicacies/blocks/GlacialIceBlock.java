package io.github.rcneg.legendarydelicacies.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class GlacialIceBlock extends FrostedIceBlock {
    public GlacialIceBlock(Properties p_53564_) {
        super(p_53564_);
    }

    @Override
    public void playerDestroy(Level p_54157_, Player p_54158_, BlockPos p_54159_, BlockState p_54160_, @Nullable BlockEntity p_54161_, ItemStack p_54162_) {
    }

    protected void melt(BlockState p_54169_, Level p_54170_, BlockPos p_54171_) {
        if (p_54170_.dimensionType().ultraWarm()) {
            p_54170_.removeBlock(p_54171_, false);
        } else {
            p_54170_.setBlockAndUpdate(p_54171_, Blocks.AIR.defaultBlockState());
            p_54170_.neighborChanged(p_54171_, Blocks.AIR, p_54171_);
        }
    }
}
