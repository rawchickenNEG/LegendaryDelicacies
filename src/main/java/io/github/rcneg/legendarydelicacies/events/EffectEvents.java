package io.github.rcneg.legendarydelicacies.events;

import io.github.rcneg.legendarydelicacies.effects.LMDImmunityMobEffect;
import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
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
            event.setResult(Event.Result.DENY);
        }
    }
}
