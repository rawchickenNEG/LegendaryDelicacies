package io.github.rcneg.legendarydelicacies.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.state.CookingPotSupport;

public class AnnihilationPotBlock extends CookingPotBlock {
    protected static final VoxelShape SHAPE;
    protected static final VoxelShape SHAPE_WITH_TRAY;
    public AnnihilationPotBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(SUPPORT).equals(CookingPotSupport.TRAY) ? SHAPE_WITH_TRAY : SHAPE;
    }


    static {
        SHAPE = Block.box((double)1.0F, (double)2F, (double)1.0F, (double)15.0F, (double)10.0F, (double)15.0F);
        SHAPE_WITH_TRAY = Shapes.or(SHAPE, Block.box((double)0.0F, (double)-1.0F, (double)0.0F, (double)16.0F, (double)0.0F, (double)16.0F));
    }
}
