package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Pets.SkeloraptorEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = SkeloraptorEntity.class)
public class SkeloraptorEntityMixin {
    @ModifyConstant(
            method = "spawnShockwaves",
            constant = @Constant(floatValue = 4.0F),
            remap = false
    )
    private float lmd$modifyDamage(float original) {
        SkeloraptorEntity entity = (SkeloraptorEntity)(Object)this;
        if(entity.hasEffect(EffectRegistry.GREAT_STRENGTH.get())){
            int amp = entity.getEffect(EffectRegistry.GREAT_STRENGTH.get()).getAmplifier();
            return original * (amp + 2);
        }
        return original;
    }
}
