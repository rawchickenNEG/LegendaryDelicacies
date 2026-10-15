package io.github.rcneg.legendarydelicacies.events;

import com.mojang.blaze3d.shaders.Effect;
import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.effects.LMDImmunityMobEffect;
import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.BeheadedKnight.BeheadedKnightEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.ResurrectedKnight.ResurrectedKnightEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EffectEvents {
    @SubscribeEvent
    public static void onApplyPotion(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        MobEffect effect = event.getEffectInstance().getEffect();
        for(MobEffect effect1 : entity.getActiveEffectsMap().keySet()){
            if(effect1 instanceof LMDImmunityMobEffect immEffect){
                if(immEffect.getImmuneEffects().contains(effect)){
                    event.setResult(Event.Result.DENY);
                }
            }
        }
        if(entity.hasEffect(EffectRegistry.FLAWLESS.get()) && effect.getCategory() == MobEffectCategory.HARMFUL){
            if(!Config.flawlessBlacklistEffects.contains(effect)){
                event.setResult(Event.Result.DENY);
            }
        }
        if(entity instanceof ResurrectedKnightEntity knight1 && knight1.getTextureVariant() == 3){
            event.setResult(Event.Result.DENY);
        }
        if(entity instanceof BeheadedKnightEntity knight2 && knight2.getTextureVariant() == 3){
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onRemovePotion(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        MobEffect effect = event.getEffectInstance().getEffect();
        if(effect == (EffectRegistry.FLYING.get()) && event.getEffectInstance().getDuration() <= 0){
            if(entity instanceof Player player && !player.getAbilities().instabuild){
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
            }
        }
    }
}
