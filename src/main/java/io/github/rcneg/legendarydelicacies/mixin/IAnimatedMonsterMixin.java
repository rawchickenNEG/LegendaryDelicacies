package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses.IAnimatedBoss;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses.IAnimatedMonster;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IAnimatedMonster.class)
public abstract class IAnimatedMonsterMixin {
    @Shadow public abstract LivingEntity target();

    @Shadow public abstract boolean targetIsNotNull();

    @Inject(
            method = "isTargetCheesing",
            at = @At("HEAD"),
            cancellable = true,
            remap = false)
    //绕过落差保护
    private void lmd$ignoreCheesing(float minHeight, float maxHeight, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity target = this.target();
        if(this.targetIsNotNull() && target.hasEffect(EffectRegistry.ATOM_ANNIHILATION.get())){
            cir.setReturnValue(false);
        }
    }
}
