package io.github.rcneg.legendarydelicacies.events;

import com.mojang.datafixers.util.Pair;
import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.init.BlockRegistry;
import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import io.github.rcneg.legendarydelicacies.tags.LMDTags;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.CloudGolem.Cloud_GolemEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.PossessedPaladin.PossessedPaladinEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.PosessedPaladinEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.LightningBoltEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ThunderCloud;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.utility.MathUtils;

import java.util.List;

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

    @SubscribeEvent
    public static void onUseItemTick(LivingEntityUseItemEvent.Tick event) {
        Level level = event.getEntity().level();
        if(!level.isClientSide()){
            ItemStack stack = event.getItem();
            if (event.getDuration() == 1 && stack.isEdible() && stack.getTag() != null){
                if(stack.getTag().getBoolean("LMDFission")){
                    if(event.getEntity().getRandom().nextInt(100) < Config.FISSION_CHANCE.get()){
                        stack.getOrCreateTag().putBoolean("LMDShouldFission", true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingEntityFinishUseItem(LivingEntityUseItemEvent.Finish event) {
        Level level = event.getEntity().level();
        if(!level.isClientSide()){
            ItemStack stack = event.getItem();
            if(stack.isEdible() && stack.getTag() != null){
                if(stack.getTag().getBoolean("LMDRich")){
                    List<Pair<MobEffectInstance, Float>> effects = stack.getFoodProperties(event.getEntity()).getEffects();
                    for(Pair<MobEffectInstance, Float> effectP : effects){
                        if(event.getEntity().getRandom().nextFloat() < effectP.getSecond()){
                            MobEffectInstance effectI = effectP.getFirst();
                            MobEffectInstance effectIm = new MobEffectInstance(effectI.getEffect(), effectI.getDuration() * 2, effectI.getAmplifier());
                            event.getEntity().addEffect(effectIm);
                        }
                    }
                }
                if(stack.getTag().getBoolean("LMDShouldFission")){
                    stack.getOrCreateTag().putBoolean("LMDShouldFission", false);
                    event.setResultStack(stack);
                }
                if(stack.getTag().getBoolean("LMDBreed")){
                    ItemStack resultStack = new ItemStack(stack.getItem());
                    resultStack.getOrCreateTag().putBoolean("LMDIsOffspring", true);
                    if(event.getEntity() instanceof Player player){
                        if (!player.getInventory().add(resultStack)) {
                            player.drop(resultStack, false);
                        }
                    }
                }
                if(stack.getTag().getBoolean("LMDIsOffspring") && !(stack.getItem() instanceof ConsumableItem)){
                    event.setResultStack(ItemStack.EMPTY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();

        if (state.is(BlockRegistry.INDESTRUCTIBLE_STOVE.get()) && state.hasProperty(BlockStateProperties.LIT) && !state.getValue(BlockStateProperties.LIT)) {
            if(stack.is(ModItems.CORRUPTED_SOUL.get())){
                level.setBlock(pos, state.setValue(BlockStateProperties.LIT, true), Block.UPDATE_ALL);
                level.playSound((Player)null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, (MathUtils.RAND.nextFloat() - MathUtils.RAND.nextFloat()) * 0.2F + 1.0F);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                player.swing(event.getHand());
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }else{
                player.displayClientMessage(Component.translatable("message.legendary_delicacies.indestructible_stove"), true);
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
    }
}
