package io.github.rcneg.legendarydelicacies.effects;

import io.github.rcneg.legendarydelicacies.tags.LMDTags;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PossessedSoulEffect extends LMDImmunityMobEffect {
    public PossessedSoulEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_, List.of(ModEffects.SOUL_FRACTURE.get()));
        this.addAttributeModifier(Attributes.MAX_HEALTH, "b80ac761-4e47-49da-92a1-f6c5d3b8692d", -0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public void applyEffectTick(LivingEntity living, int p_19468_) {
        if(!living.level().isClientSide()){
            for(EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack equip = living.getItemBySlot(slot);
                if (equip.is(LMDTags.CAN_BE_POSSESSED)) {
                    CompoundTag tag = equip.getTag();
                    if (tag != null) {
                        if (!tag.getBoolean("LMDPossessed")) {
                            tag.putBoolean("LMDPossessed", true);
                        }
                    } else {
                        equip.getOrCreateTag();
                    }
                }
            }
        }
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return -0.5F;
    }
}
