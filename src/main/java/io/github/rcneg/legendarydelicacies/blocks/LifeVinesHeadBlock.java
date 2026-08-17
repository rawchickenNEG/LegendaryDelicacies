package io.github.rcneg.legendarydelicacies.blocks;

import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class LifeVinesHeadBlock extends CaveVinesBlock {
    public static final int MAX_AGE = 3;
    private final double growPerTickProbability = 0.1D;

    public LifeVinesHeadBlock(Properties p_152959_) {
        super(p_152959_);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(BERRIES, Boolean.valueOf(false)));
    }

    public BlockState getStateForPlacement(LevelAccessor p_53949_) {
        return this.defaultBlockState().setValue(AGE, Integer.valueOf(p_53949_.getRandom().nextInt(MAX_AGE)));
    }

    public ItemStack getCloneItemStack(BlockGetter p_152966_, BlockPos p_152967_, BlockState p_152968_) {
        return new ItemStack(ItemRegistry.LIFE_FRUIT_SEEDS.get());
    }

    @Override
    protected Block getBodyBlock() {
        return BlockRegistry.LIFE_VINES_PLANT.get();
    }

    public boolean isRandomlyTicking(BlockState p_53961_) {
        return p_53961_.getValue(AGE) < MAX_AGE;
    }

    public void randomTick(BlockState p_221350_, ServerLevel p_221351_, BlockPos p_221352_, RandomSource p_221353_) {
        if (p_221350_.getValue(AGE) < MAX_AGE && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_221351_, p_221352_.relative(this.growthDirection), p_221351_.getBlockState(p_221352_.relative(this.growthDirection)),p_221353_.nextDouble() < this.growPerTickProbability)) {
            BlockPos blockpos = p_221352_.relative(this.growthDirection);
            if (this.canGrowInto(p_221351_.getBlockState(blockpos))) {
                p_221351_.setBlockAndUpdate(blockpos, this.getGrowIntoState(p_221350_, p_221351_.random));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_221351_, blockpos, p_221351_.getBlockState(blockpos));
            }
        }
    }

    public BlockState getMaxAgeState(BlockState p_187439_) {
        return p_187439_.setValue(AGE, Integer.valueOf(MAX_AGE));
    }

    public boolean isMaxAge(BlockState p_187441_) {
        return p_187441_.getValue(AGE) == MAX_AGE;
    }

    public void performBonemeal(ServerLevel p_220923_, RandomSource p_220924_, BlockPos p_220925_, BlockState p_220926_) {
        if(p_220926_.getValue(AGE) < MAX_AGE){
            BlockPos blockpos = p_220925_.relative(this.growthDirection);
            int i = Math.min(p_220926_.getValue(AGE) + 1, MAX_AGE);
            int j = this.getBlocksToGrowWhenBonemealed(p_220924_);

            for(int k = 0; k < j && this.canGrowInto(p_220923_.getBlockState(blockpos)); ++k) {
                p_220923_.setBlockAndUpdate(blockpos, p_220926_.setValue(AGE, Integer.valueOf(i)));
                blockpos = blockpos.relative(this.growthDirection);
                i = Math.min(i + 1, MAX_AGE);
            }
        }else{
            if(p_220924_.nextFloat() < 0.3F){
                p_220923_.setBlock(p_220925_, p_220926_.setValue(BERRIES, Boolean.valueOf(true)), 2);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState p_152980_, Level p_152981_, BlockPos p_152982_, Player p_152983_, InteractionHand p_152984_, BlockHitResult p_152985_) {
        return vineUse(p_152983_, p_152980_, p_152981_, p_152982_);
    }

    public static InteractionResult vineUse(@Nullable Entity p_270738_, BlockState p_270772_, Level p_270721_, BlockPos p_270587_) {
        if (p_270772_.getValue(BERRIES)) {
            Block.popResource(p_270721_, p_270587_, new ItemStack(ItemRegistry.LIFE_FRUIT.get(), 1));
            float f = Mth.randomBetween(p_270721_.random, 0.8F, 1.2F);
            p_270721_.playSound((Player)null, p_270587_, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, f);
            BlockState blockstate = p_270772_.setValue(BERRIES, Boolean.valueOf(false));
            p_270721_.setBlock(p_270587_, blockstate, 2);
            p_270721_.gameEvent(GameEvent.BLOCK_CHANGE, p_270587_, GameEvent.Context.of(p_270738_, blockstate));
            return InteractionResult.sidedSuccess(p_270721_.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }
}
