package io.github.rcneg.legendarydelicacies.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ElectricityEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class LightningBootsItem extends ArmorItem {
    public LightningBootsItem(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.FEET) {
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("12345678-4321-1145-1145-114514191981"), "add_speed", 0.3D, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return builder.build();
    }

    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity0, int slot, boolean selected) {
        if(!level.isClientSide() && entity0 instanceof LivingEntity entity) {
            if(entity.getItemBySlot(this.getEquipmentSlot())==stack) {
                CompoundTag tag = stack.getOrCreateTag();
                if(!entity.onGround()){
                    if(tag.getBoolean("LMDLigntningBootsOnFloor")){
                        tag.putBoolean("LMDLigntningBootsOnFloor", false);
                    }
                    tag.putFloat("LMDLigntningBootsFallingDis", entity.fallDistance);
                }
                if(!tag.getBoolean("LMDLigntningBootsOnFloor") && entity.onGround()){
                    tag.putBoolean("LMDLigntningBootsOnFloor", true);
                    float fallDistance = tag.getFloat("LMDLigntningBootsFallingDis");
                    if(fallDistance > 3.0F){
                        level.playSound(null, entity.getOnPos(), SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.0F, 0.9F);
                        float fallAddition = fallDistance - 3.0F;
                        int life = 12 + (int)Math.floor(fallAddition) * 6;
                        double theta = (double)entity.getYHeadRot() * (Math.PI / 180D);
                        ++theta;
                        double vecX = Math.cos(theta);
                        double vecZ = Math.sin(theta);
                        int numberOfSkulls = 16;
                        float angleStep = 360.0F / numberOfSkulls;
                        entity.playSound(SoundEvents.WITHER_SHOOT, 1.0F, 0.9F);

                        for(int i = 0; i < numberOfSkulls; ++i) {
                            float angle = entity.getYHeadRot() + (float)(i - numberOfSkulls / 2) * angleStep;
                            float rad = (float)Math.toRadians(angle);
                            double dx = -Math.sin(rad);
                            double dz = Math.cos(rad);
                            float damage = fallAddition + 8;
                            float y = (float) Mth.floor(entity.getY());
                            ElectricityEntity witherskull = new ElectricityEntity(entity, dx, (double)0.0F, dz, entity.level(), damage, angle, life);
                            double spawnX = entity.getX() + vecX;
                            double spawnZ = entity.getZ() + vecZ;
                            witherskull.setPos(spawnX, y, spawnZ);
                            level.addFreshEntity(witherskull);
                        }

                        tag.putFloat("LMDLigntningBootsFallingDis", 0);
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("item.legendary_monsters.atm_boots1").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.legendary_delicacies.lightning_boots").withStyle(ChatFormatting.GRAY));
    }
}
