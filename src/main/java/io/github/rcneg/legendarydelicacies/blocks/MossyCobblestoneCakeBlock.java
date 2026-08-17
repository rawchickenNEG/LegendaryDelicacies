package io.github.rcneg.legendarydelicacies.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeConfig;

import java.util.function.Supplier;

public class MossyCobblestoneCakeBlock extends CommonCakeBlock {
    protected static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D), Block.box(3.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D), Block.box(5.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D), Block.box(7.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D), Block.box(9.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D), Block.box(11.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D), Block.box(13.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D)};

    public MossyCobblestoneCakeBlock(Properties p_51184_, Supplier<Item> slice) {
        super(p_51184_, slice);
    }

    @Override
    public VoxelShape getShape(BlockState p_51222_, BlockGetter p_51223_, BlockPos p_51224_, CollisionContext p_51225_) {
        return SHAPE_BY_BITE[p_51222_.getValue(BITES)];
    }
}
