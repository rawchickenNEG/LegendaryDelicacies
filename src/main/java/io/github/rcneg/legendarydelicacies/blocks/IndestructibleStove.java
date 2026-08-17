package io.github.rcneg.legendarydelicacies.blocks;

import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import vectorwing.farmersdelight.common.registry.ModSounds;

public class IndestructibleStove extends StoveBlock {
    public IndestructibleStove(Properties pProperties) {
        super(pProperties);
    }
    //1.3的点燃
    /*
    @Override
    protected InteractionResult tryToIgnite(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (heldStack.is(ModItems.CORRUPTED_SOUL.get())) {
            if (!level.isClientSide()) {
                level.playSound((Player)null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, (MathUtils.RAND.nextFloat() - MathUtils.RAND.nextFloat()) * 0.2F + 1.0F);
            }

            this.ignite(player, level, pos, state);
            if (!player.getAbilities().instabuild) {
                heldStack.shrink(1);
            }

            return InteractionResult.SUCCESS;
        } else {
            player.displayClientMessage(Component.translatable("message.legendary_delicacies.indestructible_stove"), true);
            return InteractionResult.PASS;
        }
    }
     */

    //默认不点燃
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(LIT, false);
    }

    public void animateTick(BlockState stateIn, Level level, BlockPos pos, RandomSource rand) {
        if ((Boolean)stateIn.getValue(CampfireBlock.LIT)) {
            double x = (double)pos.getX() + (double)0.5F;
            double y = (double)pos.getY();
            double z = (double)pos.getZ() + (double)0.5F;
            if (rand.nextInt(10) == 0) {
                level.playLocalSound(x, y, z, (SoundEvent) ModSounds.BLOCK_STOVE_CRACKLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = (Direction)stateIn.getValue(HorizontalDirectionalBlock.FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double horizontalOffset = rand.nextDouble() * 0.6 - 0.3;
            double xOffset = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : horizontalOffset;
            double yOffset = rand.nextDouble() * (double)6.0F / (double)16.0F;
            double zOffset = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : horizontalOffset;
            level.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, (double)0.0F, (double)0.0F, (double)0.0F);
            level.addParticle(ModParticles.GHOSTLY_SOUL.get(), x + xOffset, y + yOffset, z + zOffset, (double)0.0F, (double)0.0F, (double)0.0F);
        }

    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }
}
