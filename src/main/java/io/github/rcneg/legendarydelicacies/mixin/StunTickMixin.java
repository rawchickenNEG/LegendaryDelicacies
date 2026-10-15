package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import io.github.rcneg.legendarydelicacies.tags.LMDTags;
import net.miauczel.legendary_monsters.effect.custom.StunTick;
import net.miauczel.legendary_monsters.util.EntityUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StunTick.class)
public class StunTickMixin {

    @Inject(
            method = "execute",
            at = @At("HEAD"),
            cancellable = true,
            remap = false)
    private static void lmd$perseveringSoulEffect(Level level, Entity entity, CallbackInfo ci) {
        if(entity instanceof Player player){
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            boolean flag = false;
            player.setNoActionTime(10);
            boolean mainHandSpecial = mainHandItem.is(LMDTags.BOSS_FOODS) || (mainHandItem.hasTag() && mainHandItem.getTag().getBoolean("LMDHaunted"));
            boolean offhandSpecial = offhandItem.is(LMDTags.BOSS_FOODS) || (offhandItem.hasTag() && offhandItem.getTag().getBoolean("LMDHaunted"));

            if(player.hasEffect(EffectRegistry.PERSEVERING_SOUL.get())){
                flag = true;
            }else if (Config.BOSS_FOOD_CANCEL_COOLDOWN.get()) {
                if (mainHandSpecial && offhandSpecial) {
                    flag = true;
                }else{
                    flag = true;
                    if (!offhandSpecial) {
                        player.getCooldowns().addCooldown(offhandItem.getItem(), 2);

                    }
                    if (!mainHandSpecial) {
                        player.getCooldowns().addCooldown(mainHandItem.getItem(), 2);
                    }
                }
            } else {
                flag = true;
                player.getCooldowns().addCooldown(mainHandItem.getItem(), 2);
                player.getCooldowns().addCooldown(offhandItem.getItem(), 2);
            }

            if(flag){
                ci.cancel();
            }
        }
    }
}
