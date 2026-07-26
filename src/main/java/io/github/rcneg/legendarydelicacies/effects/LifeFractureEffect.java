package io.github.rcneg.legendarydelicacies.effects;

import io.github.rcneg.legendarydelicacies.tags.LMDTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public class LifeFractureEffect extends MobEffect {
    public LifeFractureEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.MAX_HEALTH, "f0901c40-d450-4d4d-b157-6e2b46ce5fa1", -20, AttributeModifier.Operation.ADDITION);
    }
}
