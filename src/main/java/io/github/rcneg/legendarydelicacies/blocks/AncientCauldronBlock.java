package io.github.rcneg.legendarydelicacies.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.state.CookingPotSupport;

public class AncientCauldronBlock extends CookingPotBlock {
    protected static final VoxelShape SHAPE_0;
    protected static final VoxelShape SHAPE_1;
    protected static final VoxelShape SHAPE_X;
    protected static final VoxelShape SHAPE;
    protected static final VoxelShape SHAPE_WITH_TRAY;

    public AncientCauldronBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(SUPPORT).equals(CookingPotSupport.TRAY) ? SHAPE_WITH_TRAY : SHAPE;
    }

    static {
        SHAPE_0 = Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)2.0F, (double)14.0F);
        SHAPE_1 = Block.box((double)0.0F, (double)2.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F);
        SHAPE_X = Block.box((double)2.0F, (double)2.0F, (double)2.0F, (double)14.0F, (double)16.0F, (double)14.0F);
        SHAPE = Shapes.or(SHAPE_0, Shapes.join(SHAPE_1, SHAPE_X, BooleanOp.ONLY_FIRST));
        SHAPE_WITH_TRAY = Shapes.or(SHAPE, Block.box((double)0.0F, (double)-1.0F, (double)0.0F, (double)16.0F, (double)0.0F, (double)16.0F));
    }
}
