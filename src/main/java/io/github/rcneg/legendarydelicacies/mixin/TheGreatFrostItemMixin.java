package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.miauczel.legendary_monsters.item.custom.TheGreatFrostItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = TheGreatFrostItem.class)
public class TheGreatFrostItemMixin {
    @ModifyConstant(
            method = "use",
            constant = @Constant(intValue = 5)
    )
    private int lmd$modifyLoopCount(int original, Level world, Player player, InteractionHand hand) {
        if(player.hasEffect(EffectRegistry.POSSESSED_SOUL.get())){
            return 15;
        }
        return original;
    }
}
