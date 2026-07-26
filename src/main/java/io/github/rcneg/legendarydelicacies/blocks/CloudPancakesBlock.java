package io.github.rcneg.legendarydelicacies.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.function.Supplier;

public class CloudPancakesBlock extends FeastBlock {
    protected static final VoxelShape FOOD_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 10, 13.0);
    protected static final VoxelShape PLATE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);

    public CloudPancakesBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(this.getServingsProperty()) == 0 ? PLATE_SHAPE : Shapes.joinUnoptimized(PLATE_SHAPE, FOOD_SHAPE, BooleanOp.OR);
    }
}