package io.github.rcneg.legendarydelicacies.blocks;

import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class KnightSteakBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    public static final IntegerProperty SERVINGS = IntegerProperty.create("servings", 0, 4);

    protected static final VoxelShape CONTAINER_SHAPE_STICK =
            Block.box(7.0, 0.0, 7.0, 9.0, 9.0, 9.0);
    protected static final VoxelShape CONTAINER_SHAPE_FLAME =
            Block.box(3.0, 0.0, 13.0, 3.0, 2.0, 13.0);

    protected static final VoxelShape CONTAINER_SHAPE_RIGHT_S =
            Block.box(0.0, 8.0, 4.0, 16.0, 9.0, 12.0);
    protected static final VoxelShape CONTAINER_SHAPE_RIGHT_W =
            Block.box(4.0, 8.0, 0.0, 12.0, 9.0, 16.0);
    protected static final VoxelShape CONTAINER_SHAPE_RIGHT_N =
            Block.box(0.0, 8.0, 4.0, 16.0, 9.0, 12.0);
    protected static final VoxelShape CONTAINER_SHAPE_RIGHT_E =
            Block.box(0.0, 8.0, 0.0, 12.0, 9.0, 16.0);

    protected static final VoxelShape CONTAINER_SHAPE_MIDDLE_S =
            Block.box(0.0, 8.0, 4.0, 16.0, 9.0, 12.0);
    protected static final VoxelShape CONTAINER_SHAPE_MIDDLE_W =
            Block.box(4.0, 8.0, 0.0, 12.0, 9.0, 16.0);
    protected static final VoxelShape CONTAINER_SHAPE_MIDDLE_N =
            Block.box(0.0, 8.0, 4.0, 16.0, 9.0, 12.0);
    protected static final VoxelShape CONTAINER_SHAPE_MIDDLE_E =
            Block.box(4.0, 8.0, 0.0, 12.0, 9.0, 16.0);

    protected static final VoxelShape CONTAINER_SHAPE_LEFT_S =
            Block.box(0.0, 8.0, 4.0, 16.0, 9.0, 12.0);
    protected static final VoxelShape CONTAINER_SHAPE_LEFT_W =
            Block.box(4.0, 8.0, 0.0, 12.0, 9.0, 16.0);
    protected static final VoxelShape CONTAINER_SHAPE_LEFT_N =
            Block.box(0.0, 8.0, 4.0, 16.0, 9.0, 12.0);
    protected static final VoxelShape CONTAINER_SHAPE_LEFT_E =
            Block.box(4.0, 8.0, 0.0, 12.0, 9.0, 16.0);

    public KnightSteakBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SERVINGS, 0)
                .setValue(PART, Part.MIDDLE));
    }

    public enum Part implements StringRepresentable {
        LEFT("left"),
        MIDDLE("middle"),
        RIGHT("right");

        private final String name;

        Part(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SERVINGS, PART);
    }

    private static Direction getLeftDirection(Direction facing) {
        return facing.getCounterClockWise();
    }

    private static Direction getRightDirection(Direction facing) {
        return facing.getClockWise();
    }

    private static BlockPos getPartPos(BlockPos middlePos, Direction facing, Part part) {
        return switch (part) {
            case LEFT -> middlePos.relative(getLeftDirection(facing));
            case MIDDLE -> middlePos;
            case RIGHT -> middlePos.relative(getRightDirection(facing));
        };
    }

    private static BlockPos getMiddlePos(BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);

        return switch (state.getValue(PART)) {
            case LEFT -> pos.relative(getRightDirection(facing));
            case MIDDLE -> pos;
            case RIGHT -> pos.relative(getLeftDirection(facing));
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos middlePos = ctx.getClickedPos();
        Direction facing = getFacingDirection(ctx);

        for (Part part : Part.values()) {
            BlockPos partPos = getPartPos(middlePos, facing, part);

            if (!level.getWorldBorder().isWithinBounds(partPos)) {
                return null;
            }

            if (!level.getBlockState(partPos).canBeReplaced(ctx)) {
                return null;
            }
        }

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(PART, Part.MIDDLE)
                .setValue(SERVINGS, 4);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (level.isClientSide) {
            return;
        }

        Direction facing = state.getValue(FACING);
        BlockPos leftPos = getPartPos(pos, facing, Part.LEFT);
        BlockPos rightPos = getPartPos(pos, facing, Part.RIGHT);

        int placementFlags = Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE;

        level.setBlock(leftPos, state.setValue(PART, Part.LEFT), placementFlags);

        level.setBlock(rightPos, state.setValue(PART, Part.RIGHT), placementFlags);

        level.updateNeighborsAt(leftPos, this);
        level.updateNeighborsAt(pos, this);
        level.updateNeighborsAt(rightPos, this);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockPos middlePos = getMiddlePos(pos, state);
            BlockState middleState = level.getBlockState(middlePos);
            if (middleState.is(this)) {
                Direction facing = middleState.getValue(FACING);
                Part brokenPart = state.getValue(PART);
                if (!player.getAbilities().instabuild && brokenPart != Part.MIDDLE) {
                    Block.dropResources(middleState, level, middlePos, null, player, player.getMainHandItem());
                }

                int removalFlags = Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE| Block.UPDATE_SUPPRESS_DROPS;

                for (Part part : Part.values()) {
                    BlockPos partPos = getPartPos(middlePos, facing, part);
                    if (partPos.equals(pos)) continue;

                    BlockState partState = level.getBlockState(partPos);

                    if (partState.is(this)) {
                        level.setBlock(partPos, Blocks.AIR.defaultBlockState(), removalFlags);
                    }
                }
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!isStructureValid(level, pos, state)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, dir, neighborState, level, pos, neighborPos);
    }

    private boolean isStructureValid(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockPos middlePos = getMiddlePos(pos, state);
        BlockState middleState = level.getBlockState(middlePos);

        if (!middleState.is(this)) {
            return false;
        }

        Direction facing = middleState.getValue(FACING);
        int servings = middleState.getValue(SERVINGS);

        return isPart(level, getPartPos(middlePos, facing, Part.LEFT), facing, Part.LEFT, servings)
                && isPart(level, middlePos, facing, Part.MIDDLE, servings)
                && isPart(level, getPartPos(middlePos, facing, Part.RIGHT), facing, Part.RIGHT, servings);
    }

    private boolean isPart(LevelAccessor level, BlockPos pos, Direction facing, Part part, int servings) {
        BlockState state = level.getBlockState(pos);

        return state.is(this) && state.getValue(FACING) == facing && state.getValue(PART) == part && state.getValue(SERVINGS) == servings;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return this.rotate(state, mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    private static Direction getFacingDirection(BlockPlaceContext ctx) {
        Player player = ctx.getPlayer();
        BlockPos pos = ctx.getClickedPos();

        if (player == null) {
            return ctx.getHorizontalDirection();
        }

        double dx = pos.getX() + 0.5D - player.getX();
        double dz = pos.getZ() + 0.5D - player.getZ();

        Direction direction = Direction.getNearest(dx, 0.0D, dz);

        if (direction.getAxis().isVertical()) {
            direction = ctx.getHorizontalDirection();
        }

        return direction;
    }

    public IntegerProperty getServingsProperty() {
        return SERVINGS;
    }

    public ItemStack getServingItem(BlockState state) {
        return new ItemStack(ItemRegistry.PLATE_OF_HAUNTED_KNIGHT_STEAK.get());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        return this.takeServing(level, pos, state, player, hand);
    }

    protected InteractionResult takeServing(LevelAccessor level, BlockPos pos, BlockState state, Player player, InteractionHand hand
    ) {
        BlockPos middlePos = getMiddlePos(pos, state);
        BlockState middleState = level.getBlockState(middlePos);

        if (!middleState.is(this)) {
            return InteractionResult.PASS;
        }

        int servings = middleState.getValue(this.getServingsProperty());

        if (servings == 0) {
            level.playSound(null, middlePos, SoundEvents.WOOD_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);

            Direction facing = middleState.getValue(FACING);

            level.destroyBlock(middlePos, true);

            int flags = Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS;

            for (Part part : new Part[]{Part.LEFT, Part.RIGHT}) {
                BlockPos partPos = getPartPos(middlePos, facing, part);

                if (level.getBlockState(partPos).is(this)) {
                    level.setBlock(partPos, Blocks.AIR.defaultBlockState(), flags);
                }
            }

            return InteractionResult.SUCCESS;
        }

        ItemStack serving = this.getServingItem(middleState);
        ItemStack heldStack = player.getItemInHand(hand);

        if (!serving.hasCraftingRemainingItem()
                || ItemStack.isSameItem(heldStack, serving.getCraftingRemainingItem())) {

            int nextServings = servings - 1;

            setServingsAll(level, middlePos, middleState, nextServings);

            if (!player.getAbilities().instabuild && serving.hasCraftingRemainingItem()) {
                heldStack.shrink(1);
            }

            if (!player.getInventory().add(serving)) {
                player.drop(serving, false);
            }

            level.playSound(null, middlePos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 1.0F, 1.0F);

            return InteractionResult.SUCCESS;
        }

        player.displayClientMessage(TextUtils.getTranslation("block.feast.use_container", serving.getCraftingRemainingItem().getHoverName()), true);
        return InteractionResult.PASS;
    }

    private void setServingsAll(LevelAccessor level, BlockPos anyPos, BlockState anyState, int servings) {
        BlockPos middlePos = getMiddlePos(anyPos, anyState);
        Direction facing = anyState.getValue(FACING);

        int flags = Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE;

        for (Part part : Part.values()) {
            BlockPos partPos = getPartPos(middlePos, facing, part);
            BlockState partState = level.getBlockState(partPos);

            if (partState.is(this)) {
                level.setBlock(partPos, partState.setValue(SERVINGS, servings), flags);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(PART) == Part.RIGHT) {
            return switch (state.getValue(FACING)) {
                case SOUTH -> Shapes.joinUnoptimized(CONTAINER_SHAPE_RIGHT_S, CONTAINER_SHAPE_STICK, BooleanOp.OR);
                case EAST -> Shapes.joinUnoptimized(CONTAINER_SHAPE_RIGHT_E, CONTAINER_SHAPE_STICK, BooleanOp.OR);
                case WEST -> Shapes.joinUnoptimized(CONTAINER_SHAPE_RIGHT_W, CONTAINER_SHAPE_STICK, BooleanOp.OR);
                default -> Shapes.joinUnoptimized(CONTAINER_SHAPE_RIGHT_N, CONTAINER_SHAPE_STICK, BooleanOp.OR);
            };
        }

        if (state.getValue(PART) == Part.MIDDLE) {
            return switch (state.getValue(FACING)) {
                case SOUTH -> Shapes.joinUnoptimized(CONTAINER_SHAPE_MIDDLE_S, CONTAINER_SHAPE_FLAME, BooleanOp.OR);
                case EAST -> Shapes.joinUnoptimized(CONTAINER_SHAPE_MIDDLE_E, CONTAINER_SHAPE_FLAME, BooleanOp.OR);
                case WEST -> Shapes.joinUnoptimized(CONTAINER_SHAPE_MIDDLE_W, CONTAINER_SHAPE_FLAME, BooleanOp.OR);
                default -> Shapes.joinUnoptimized(CONTAINER_SHAPE_MIDDLE_N, CONTAINER_SHAPE_FLAME, BooleanOp.OR);
            };
        }

        if (state.getValue(PART) == Part.LEFT) {
            return switch (state.getValue(FACING)) {
                case SOUTH -> Shapes.joinUnoptimized(CONTAINER_SHAPE_LEFT_S, CONTAINER_SHAPE_STICK, BooleanOp.OR);
                case EAST -> Shapes.joinUnoptimized(CONTAINER_SHAPE_LEFT_E, CONTAINER_SHAPE_STICK, BooleanOp.OR);
                case WEST -> Shapes.joinUnoptimized(CONTAINER_SHAPE_LEFT_W, CONTAINER_SHAPE_STICK, BooleanOp.OR);
                default -> Shapes.joinUnoptimized(CONTAINER_SHAPE_LEFT_N, CONTAINER_SHAPE_STICK, BooleanOp.OR);
            };
        }

        return CONTAINER_SHAPE_MIDDLE_N;
    }
}
