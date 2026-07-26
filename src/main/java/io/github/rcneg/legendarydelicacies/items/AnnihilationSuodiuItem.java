package io.github.rcneg.legendarydelicacies.items;

import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SmallAnnihilationBombEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AnnihilationSuodiuItem extends TippedItems{
    public AnnihilationSuodiuItem(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack p_42923_, Level p_42924_, LivingEntity p_42925_) {
        if (isEdible()) {
            p_42925_.eat(p_42924_, p_42923_.copy());
            if(p_42925_ instanceof Player player){
                p_42924_.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.LLAMA_SPIT, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.shootAnnihilationBomb(player, 1.0F, player.getX(), player.getY() + 1.0, player.getZ(), 5);
                p_42923_.hurtAndBreak(1, player, (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                });
            }
        }
        return p_42923_.isEmpty() ? new ItemStack(ModItems.PORTAL_SHARD.get()) : p_42923_;
    }

    public void shootAnnihilationBomb(Player player, float velocity, double x, double y, double z, int inaccuracy) {
        SmallAnnihilationBombEntity chorusBomb = new SmallAnnihilationBombEntity(ModEntities.SMALL_ANNIHILATION_BOMB_ENTITY.get(), player.level(), player, (float)(8.0 * (Double) ModConfig.MOB_CONFIG.BucklerOfAnnihilationProjectileDamageMultiplier.get()));
        chorusBomb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity, (float)inaccuracy);
        chorusBomb.setPosRaw(x, y, z);
        chorusBomb.setOwner(player);
        player.level().addFreshEntity(chorusBomb);
    }
}
