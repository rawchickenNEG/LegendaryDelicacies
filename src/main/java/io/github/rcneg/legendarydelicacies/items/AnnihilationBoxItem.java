package io.github.rcneg.legendarydelicacies.items;

import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;

public class AnnihilationBoxItem extends TippedItems{
    public AnnihilationBoxItem(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResult useOn(UseOnContext ctx) {
        BlockPos pos = ctx.getClickedPos().relative(ctx.getClickedFace());
        Level level = ctx.getLevel();
        //直接穷举了，，，
        List<BlockPos> poses = List.of(pos, pos.west(), pos.south(), pos.south().west(), pos.above(), pos.above().west(), pos.above().south(), pos.above().south().west());
        boolean flag = true;
        for(BlockPos pos0 : poses){
            if(!level.getBlockState(pos0).canBeReplaced()){
                flag = false;
            }
        }
        if(!level.isClientSide() && flag){
            level.setBlock(poses.get(0), BlockRegistry.ANNIHILATION_BOX.get().defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(poses.get(1), BlockRegistry.ANNIHILATION_BOX.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), Block.UPDATE_ALL);
            level.setBlock(poses.get(2), BlockRegistry.ANNIHILATION_BOX.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST), Block.UPDATE_ALL);
            level.setBlock(poses.get(3), BlockRegistry.ANNIHILATION_BOX.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH), Block.UPDATE_ALL);
            level.setBlock(poses.get(4), BlockRegistry.ANNIHILATION_BOX_TOP.get().defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(poses.get(5), BlockRegistry.ANNIHILATION_BOX_TOP.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), Block.UPDATE_ALL);
            level.setBlock(poses.get(6), BlockRegistry.ANNIHILATION_BOX_TOP.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST), Block.UPDATE_ALL);
            level.setBlock(poses.get(7), BlockRegistry.ANNIHILATION_BOX_TOP.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH), Block.UPDATE_ALL);
            level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ARMOR_EQUIP_NETHERITE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if(ctx.getPlayer() != null && !ctx.getPlayer().getAbilities().instabuild){
                ctx.getItemInHand().shrink(1);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
