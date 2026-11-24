package io.github.rcneg.legendarydelicacies.items;

import com.google.common.collect.Lists;
import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import io.github.rcneg.legendarydelicacies.init.EffectRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Pets.SkeloraptorEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import java.util.List;

public class AbominationDogFoodItem extends ConsumableItem {
    public static final List<MobEffectInstance> EFFECTS;
    public static final List<MobEffectInstance> EFFECTS_2;

    public AbominationDogFoodItem(Properties properties) {
        super(properties);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if ((Boolean) Configuration.ENABLE_FOOD_EFFECT_TOOLTIP.get()) {
            MutableComponent textWhenFeeding = TextUtils.tooltip("dog_food.when_feeding", new Object[0]);

            tooltip.add(textWhenFeeding.withStyle(ChatFormatting.GRAY));

            for(MobEffectInstance effectInstance : EFFECTS) {
                MutableComponent effectDescription = Component.literal(" ");
                MutableComponent effectName = Component.translatable(effectInstance.getDescriptionId());
                effectDescription.append(effectName);
                MobEffect effect = effectInstance.getEffect();
                if (effectInstance.getAmplifier() > 0) {
                    effectDescription.append(" ").append(Component.translatable("potion.potency." + effectInstance.getAmplifier()));
                }

                if (effectInstance.getDuration() > 20) {
                    effectDescription.append(" (").append(MobEffectUtil.formatDuration(effectInstance, 1.0F)).append(")");
                }

                tooltip.add(effectDescription.withStyle(effect.getCategory().getTooltipFormatting()));
            }

            MutableComponent textWhenFeeding2 = Component.translatable("tooltip.legendary_delicacies.when_feeding_skeloraptor");
            tooltip.add(textWhenFeeding2.withStyle(ChatFormatting.GRAY));

            for(MobEffectInstance effectInstance : EFFECTS_2) {
                MutableComponent effectDescription = Component.literal(" ");
                MutableComponent effectName = Component.translatable(effectInstance.getDescriptionId());
                effectDescription.append(effectName);
                MobEffect effect = effectInstance.getEffect();
                if (effectInstance.getAmplifier() > 0) {
                    effectDescription.append(" ").append(Component.translatable("potion.potency." + effectInstance.getAmplifier()));
                }

                if (effectInstance.getDuration() > 20) {
                    effectDescription.append(" (").append(MobEffectUtil.formatDuration(effectInstance, 1.0F)).append(")");
                }

                tooltip.add(effectDescription.withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }
    }

    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof Wolf wolf) {
            if (wolf.isAlive() && wolf.isTame()) {
                return InteractionResult.SUCCESS;
            }
        }
        if(target instanceof SkeloraptorEntity skelo){
            if (skelo.isAlive() && skelo.isTame()) {
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    static {
        EFFECTS = Lists.newArrayList(
                new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 1),
                new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 1),
                new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 1));
        EFFECTS_2 = Lists.newArrayList(
                new MobEffectInstance(EffectRegistry.WITHER_STRIKE.get(), 6000, 1),
                new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 4),
                new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 1),
                new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 2));
    }

    @Mod.EventBusSubscriber(
            modid = LegendaryDelicacies.MODID,
            bus = Mod.EventBusSubscriber.Bus.FORGE
    )
    public static class DogFoodEvent {
        public DogFoodEvent() {
        }

        @SubscribeEvent
        public static void onAbominationDogFoodApplied(PlayerInteractEvent.EntityInteract event) {
            Player player = event.getEntity();
            Entity target = event.getTarget();
            ItemStack itemStack = event.getItemStack();
            if (target instanceof LivingEntity entity) {
                if (target.getType().is(ModTags.EntityTypes.DOG_FOOD_USERS) || target instanceof SkeloraptorEntity) {
                    boolean isTameable = entity instanceof TamableAnimal;
                    if (entity.isAlive() && (!isTameable || ((TamableAnimal)entity).isTame()) && itemStack.getItem().equals(ItemRegistry.ABOMINATION_DOG_FOOD.get())) {
                        entity.setHealth(entity.getMaxHealth());

                        if(target instanceof SkeloraptorEntity){
                            for(MobEffectInstance effect : AbominationDogFoodItem.EFFECTS_2) {
                                entity.addEffect(new MobEffectInstance(effect));
                            }

                            entity.level().playSound((Player)null, target.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);

                            for(int i = 0; i < 5; ++i) {
                                double xSpeed = MathUtils.RAND.nextGaussian() * 0.02;
                                double ySpeed = MathUtils.RAND.nextGaussian() * 0.02;
                                double zSpeed = MathUtils.RAND.nextGaussian() * 0.02;
                                entity.level().addParticle((ParticleOptions) ModParticleTypes.STAR.get(), entity.getRandomX((double)1.0F), entity.getRandomY() + (double)0.5F, entity.getRandomZ((double)1.0F), xSpeed, ySpeed, zSpeed);
                            }

                            if (itemStack.getCraftingRemainingItem() != ItemStack.EMPTY && !player.isCreative()) {
                                player.addItem(itemStack.getCraftingRemainingItem());
                                itemStack.shrink(1);
                            }

                            event.setCancellationResult(InteractionResult.SUCCESS);
                            event.setCanceled(true);
                        }else{
                            for(MobEffectInstance effect : AbominationDogFoodItem.EFFECTS) {
                                entity.addEffect(new MobEffectInstance(effect));
                            }

                            entity.level().playSound((Player)null, target.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);

                            for(int i = 0; i < 5; ++i) {
                                double xSpeed = MathUtils.RAND.nextGaussian() * 0.02;
                                double ySpeed = MathUtils.RAND.nextGaussian() * 0.02;
                                double zSpeed = MathUtils.RAND.nextGaussian() * 0.02;
                                entity.level().addParticle((ParticleOptions) ModParticleTypes.STAR.get(), entity.getRandomX((double)1.0F), entity.getRandomY() + (double)0.5F, entity.getRandomZ((double)1.0F), xSpeed, ySpeed, zSpeed);
                            }

                            if (itemStack.getCraftingRemainingItem() != ItemStack.EMPTY && !player.isCreative()) {
                                player.addItem(itemStack.getCraftingRemainingItem());
                                itemStack.shrink(1);
                            }

                            event.setCancellationResult(InteractionResult.SUCCESS);
                            event.setCanceled(true);
                        }
                    }
                }
            }

        }
    }
}
