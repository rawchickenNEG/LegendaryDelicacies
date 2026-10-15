package io.github.rcneg.legendarydelicacies.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.function.Supplier;

public class DesertFriedRiceBlock extends FeastBlock {
    protected static final VoxelShape[] SHAPES_R;
    public DesertFriedRiceBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES_R[(Integer)state.getValue(SERVINGS)];
    }

    static {
        SHAPES_R = new VoxelShape[]{Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)2.0F, (double)14.0F), Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)3.0F, (double)14.0F), Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)6.0F, (double)14.0F), Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)8.0F, (double)14.0F), Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)10.0F, (double)14.0F)};
    }
}