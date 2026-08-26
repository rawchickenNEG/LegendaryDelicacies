package io.github.rcneg.legendarydelicacies.blocks;

import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class AncientKebabStandBlock extends FeastBlock {
    public static final IntegerProperty ROLL_SERVINGS = IntegerProperty.create("servings", 0, 5);
    protected static final VoxelShape SHAPE_BASE = Shapes.or(Block.box(1.0F, 0.0F, 1.0F, 15.0F, 1.0F, 15.0F), Block.box(6.5F, 0.0F, 6.5F, 9.5F, 32.0F, 9.5F));
    protected static final VoxelShape[] SHAPES;

    public final List<Supplier<Item>> riceRollServings;

    public AncientKebabStandBlock(BlockBehaviour.Properties properties) {
        super(properties, ItemRegistry.BOWL_OF_ANCIENT_KEBAB_MEAT, true);
        this.riceRollServings = Arrays.asList(ItemRegistry.BOWL_OF_ANCIENT_KEBAB_MEAT, ItemRegistry.BOWL_OF_ANCIENT_KEBAB_MEAT, ItemRegistry.BOWL_OF_ANCIENT_KEBAB_MEAT, ItemRegistry.BOWL_OF_ANCIENT_KEBAB_MEAT, ItemRegistry.BOWL_OF_ANCIENT_KEBAB_MEAT);
    }

    public IntegerProperty getServingsProperty() {
        return ROLL_SERVINGS;
    }

    public int getMaxServings() {
        return 5;
    }

    public ItemStack getServingItem(BlockState state) {
        return new ItemStack((ItemLike)((Supplier)this.riceRollServings.get((Integer)state.getValue(this.getServingsProperty()) - 1)).get());
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(SHAPES[5 - state.getValue(getServingsProperty())], SHAPE_BASE);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, ROLL_SERVINGS});
    }

    static {
        SHAPES = new VoxelShape[]{
                Block.box(2.0F, 9.0F, 2.0F, 14.0F, 29.0F, 14.0F),
                Block.box(3.0F, 9.0F, 3.0F, 13.0F, 29.0F, 13.0F),
                Block.box(4.0F, 9.0F, 4.0F, 12.0F, 29.0F, 12.0F),
                Block.box(5.0F, 9.0F, 5.0F, 11.0F, 29.0F, 11.0F),
                Block.box(6.0F, 9.0F, 6.0F, 10.0F, 29.0F, 10.0F),
                Block.box(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        };
    }
}
