package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public class EnderManMixin {

    @Inject(
            method = "isLookingAtMe",
            at = @At("HEAD"),
            cancellable = true
    )
    private void lmd$ignoreStaringPlayer(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (player.hasEffect(EffectRegistry.WARPED_HOSTILITY.get())) {
            cir.setReturnValue(false);
        }
    }
}