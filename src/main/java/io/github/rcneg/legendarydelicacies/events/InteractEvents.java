package io.github.rcneg.legendarydelicacies.events;

import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import io.github.rcneg.legendarydelicacies.tags.LMDTags;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.CloudGolem.Cloud_GolemEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.PossessedPaladin.PossessedPaladinEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.PosessedPaladinEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.LightningBoltEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ThunderCloud;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InteractEvents {
    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        ItemStack weapon = event.getEntity().getItemBySlot(event.getSlot());
        CompoundTag tag = weapon.getTag();
        if(event.getEntity().hasEffect(EffectRegistry.POSSESSED_SOUL.get()) && weapon.is(LMDTags.CAN_BE_POSSESSED)){
            if (tag != null) {
                if(!tag.getBoolean("LMDPossessed")){
                    tag.putBoolean("LMDPossessed", true);
                }
            } else {
                weapon.getOrCreateTag();
            }
        }else{
            if (tag != null) {
                if(tag.getBoolean("LMDPossessed")){
                    tag.remove("LMDPossessed");
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if(!event.getLevel().isClientSide){
            ItemStack stack = ItemStack.EMPTY;
            Player player = event.getEntity();
            InteractionHand hand = event.getHand();
            ItemStack heldStack = event.getItemStack();
            if(!player.getCooldowns().isOnCooldown(heldStack.getItem())){
                if(heldStack.is(Items.GLASS_BOTTLE)) {
                    if(event.getTarget() instanceof PossessedPaladinEntity possess && possess.isDeadOrDying()){
                        possess.playSound(SoundEvents.BOTTLE_FILL, 1, 0.5f);
                        stack = new ItemStack(ItemRegistry.POSSESSED_BLOOD_BOTTLE.get());
                    }
                }

                if (!stack.isEmpty()) {
                    player.swing(hand, true);
                    player.getCooldowns().addCooldown(heldStack.getItem(), 20);
                    if (!player.getAbilities().instabuild) {
                        heldStack.shrink(1);
                    }
                    if (!player.getInventory().add(stack)) {
                        player.drop(stack, false);
                    }
                }
            }
        }
    }
}
