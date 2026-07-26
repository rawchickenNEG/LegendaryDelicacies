package io.github.rcneg.legendarydelicacies.blocks;

import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class AnnihilationBoxBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public AnnihilationBoxBlock(BlockBehaviour.Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_) {
        p_152043_.add(FACING, OPEN);
    }

    public BlockState updateShape(BlockState p_152036_, Direction p_152037_, BlockState p_152038_, LevelAccessor p_152039_, BlockPos p_152040_, BlockPos p_152041_) {
        return p_152037_ == p_152036_.getValue(FACING).getOpposite() && !p_152036_.canSurvive(p_152039_, p_152040_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_152036_, p_152037_, p_152038_, p_152039_, p_152040_, p_152041_);
    }

    private void openSelfAndNeighbor(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);

        Direction leftDirection = facing.getCounterClockWise();
        BlockPos leftPos = pos.relative(leftDirection);
        BlockState leftState = level.getBlockState(leftPos);

        level.setBlock(pos, state.setValue(OPEN, true), Block.UPDATE_ALL);

        if (leftState.is(this) && leftState.getValue(FACING) == leftDirection && !leftState.getValue(OPEN)) {
            openSelfAndNeighbor(level, leftPos, leftState);
        }
        if(state.is(BlockRegistry.ANNIHILATION_BOX.get()) && !level.getBlockState(pos.above()).getValue(OPEN)){
            openSelfAndNeighbor(level, pos.above(), level.getBlockState(pos.above()));
        }
        if(state.is(BlockRegistry.ANNIHILATION_BOX_TOP.get()) && !level.getBlockState(pos.below()).getValue(OPEN)){
            openSelfAndNeighbor(level, pos.below(), level.getBlockState(pos.below()));
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if(!state.getValue(OPEN)){
            openSelfAndNeighbor(level, pos, state);
            CameraShakeEntity.cameraShake(level, pos.getCenter(), 10.0F, 0.1F, 5, 5);
            level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.TOTEM_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }else{
            ItemStack serving = new ItemStack(ItemRegistry.ANNIHILATION_BLOOM_EYE.get());
            if (!player.getInventory().add(serving)) {
                player.drop(serving, false);
            }
            level.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.removeBlock(pos, false);
        }
        return InteractionResult.CONSUME;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_152019_) {
        Direction dir = p_152019_.getHorizontalDirection().getOpposite();
        return this.defaultBlockState()
                .setValue(FACING, dir)
                .setValue(OPEN, false);
    }

    public BlockState rotate(BlockState p_152033_, Rotation p_152034_) {
        return p_152033_.setValue(FACING, p_152034_.rotate(p_152033_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_152030_, Mirror p_152031_) {
        return p_152030_.rotate(p_152031_.getRotation(p_152030_.getValue(FACING)));
    }

    public ItemStack getCloneItemStack(BlockGetter p_52254_, BlockPos p_52255_, BlockState p_52256_) {
        return new ItemStack(ItemRegistry.ANNIHILATION_BOX.get());
    }
}
