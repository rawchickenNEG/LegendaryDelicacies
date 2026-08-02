package io.github.rcneg.legendarydelicacies.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FlourishEffect extends MobEffect {
    public FlourishEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public void applyEffectTick(LivingEntity p_19467_, int p_19468_) {
        Level level = p_19467_.level();
        if(level instanceof ServerLevel serverLevel){
            BlockPos pos = p_19467_.getOnPos();
            int r = 3 + p_19468_;
            for (BlockPos tmpPos : BlockPos.withinManhattan(pos, r, r, r)){
                if(Math.round(getBlockPosDistance(tmpPos, pos)) <= r){
                    BlockState state = serverLevel.getBlockState(tmpPos);
                    if(state.getBlock() instanceof BonemealableBlock plant){
                        serverLevel.levelEvent(3009, tmpPos, 0);
                        plant.performBonemeal(serverLevel, p_19467_.getRandom(), tmpPos, serverLevel.getBlockState(tmpPos));
                    }
                }
            }
        }

    }
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return p_19455_ % 50 == 0;
    }

    public static float getBlockPosDistance(BlockPos pos1, BlockPos pos2){
        return (float) (Math.sqrt(Math.pow(Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2)), 2) + Math.pow(pos1.getZ() - pos2.getZ(), 2)));
    }
}
