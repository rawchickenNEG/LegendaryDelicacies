package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import io.github.rcneg.legendarydelicacies.tags.LMDTags;
import net.miauczel.legendary_monsters.util.EntityUtil;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityUtil.class)
public class EntityUtilMixin {
    @Redirect(
            method = "cancelBuffs",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;isEdible()Z"
            )
            , remap = false
    )
    private static boolean lmd$redirectBossFoods(ItemStack stack) {
        if (stack.is(LMDTags.BOSS_FOODS) && Config.BOSS_FOOD_CANCEL_COOLDOWN.get()) {
            return false;
        }
        return stack.isEdible();
    }

    @Inject(
            method = "cancelBuffs",
            at = @At("HEAD"),
            cancellable = true,
            remap = false)
    private static void lmd$perseveringSoulEffect(LivingEntity livingEntity, CallbackInfo ci) {
        if(livingEntity.hasEffect(EffectRegistry.PERSEVERING_SOUL.get())){
            if(livingEntity instanceof Player player){
                MobEffectInstance mobEffectInstance = player.getEffect(MobEffects.DAMAGE_RESISTANCE);
                int amp = livingEntity.getEffect(EffectRegistry.PERSEVERING_SOUL.get()).getAmplifier();
                if (player.hasEffect(MobEffects.DAMAGE_RESISTANCE) && mobEffectInstance != null && mobEffectInstance.getAmplifier() > amp) {
                    player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, mobEffectInstance.getDuration(), amp));
                }
                ci.cancel();
            }
        }
    }
}
