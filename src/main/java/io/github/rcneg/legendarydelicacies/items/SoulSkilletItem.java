package io.github.rcneg.legendarydelicacies.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.rcneg.legendarydelicacies.config.Config;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import io.github.rcneg.legendarydelicacies.tags.LMDTags;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.item.custom.Capability.IParry;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.block.SkilletBlock;
import vectorwing.farmersdelight.common.item.SkilletItem;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class SoulSkilletItem extends SkilletItem implements IParry {
    private final Multimap<Attribute, AttributeModifier> toolAttributes;

    public int timeUsed = 0;
    public boolean parrySucced = false;
    public SoulSkilletItem(Block block, Properties properties) {
        super(block, properties);
        float attackDamage = 14.0F;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", (double)attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)-3F, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(FD_ATTACK_KNOCKBACK_UUID, "Tool modifier", (double)1.0F, AttributeModifier.Operation.ADDITION));
        this.toolAttributes = builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.toolAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public int maxUseDuration() {
        return 72000;
    }

    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, -1);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack skilletStack = player.getItemInHand(hand);
        if (isPlayerNearHeatSource(player, level)) {
            InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            ItemStack cookingStack = player.getItemInHand(otherHand);
            if (skilletStack.getOrCreateTag().contains("Cooking")) {
                player.startUsingItem(hand);
                return InteractionResultHolder.pass(skilletStack);
            }

            Optional<CampfireCookingRecipe> recipe = getCookingRecipe(cookingStack, level);
            int cookingTime = recipe.map(AbstractCookingRecipe::getCookingTime).orElse(200);
            boolean flag = !(cookingStack.getTag() != null && cookingStack.getTag().contains("LMDHaunted")) && cookingStack.isEdible() && !cookingStack.is(LMDTags.BOSS_FOODS);
            if (recipe.isPresent() || flag) {
                if (player.isUnderWater()) {
                    player.displayClientMessage(Component.translatable("farmersdelight.item.skillet.underwater"), true);
                    return InteractionResultHolder.pass(skilletStack);
                }
                ItemStack cookingStackCopy = cookingStack.copy();
                ItemStack cookingStackUnit = cookingStackCopy.split(1);
                skilletStack.getOrCreateTag().put("Cooking", cookingStackUnit.serializeNBT());
                skilletStack.getOrCreateTag().putInt("CookTimeHandheld", cookingTime);
                skilletStack.getOrCreateTag().putBoolean("Flipped", false);
                player.startUsingItem(hand);
                player.setItemInHand(otherHand, cookingStackCopy);
                return InteractionResultHolder.consume(skilletStack);
            }

            player.displayClientMessage(Component.translatable("message.legendary_delicacies.cannot_haunted"), true);

        }else{
            player.startUsingItem(hand);
        }

        return InteractionResultHolder.pass(skilletStack);
    }

    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (!level.isClientSide) {
            if (entity instanceof Player player) {
                CompoundTag tag = stack.getOrCreateTag();
                if(tag.contains("Cooking")){
                    Vec3 pos = player.position();
                    double x = pos.x() + (double)0.5F;
                    double y = pos.y();
                    double z = pos.z() + (double)0.5F;
                    if (level.random.nextInt(50) == 0) {
                        level.playLocalSound(x, y, z, (SoundEvent) vectorwing.farmersdelight.common.registry.ModSounds.BLOCK_SKILLET_SIZZLE.get(), SoundSource.BLOCKS, 0.4F, level.random.nextFloat() * 0.2F + 0.9F, false);
                    }

                    if (tag.contains("FlipTimeStamp")) {
                        long flipTimeStamp = tag.getLong("FlipTimeStamp");
                        if ((float)(level.getGameTime() - flipTimeStamp) > 12.0F) {
                            tag.remove("FlipTimeStamp");
                            tag.putBoolean("Flipped", !tag.getBoolean("Flipped"));
                            level.playSound((Player)null, x, y, z, (SoundEvent) vectorwing.farmersdelight.common.registry.ModSounds.BLOCK_SKILLET_ADD_FOOD.get(), SoundSource.BLOCKS, 0.4F, level.random.nextFloat() * 0.2F + 0.9F);
                        }
                    }
                }else{
                    ++this.timeUsed;
                    if(!tag.contains("Parrying")){
                        stack.getOrCreateTag().putBoolean("Parrying", true);
                    }
                    if (this.timeUsed >= Config.SKILLET_PARRY_TIME.get() && !player.isShiftKeyDown() && ModConfig.MOB_CONFIG.canSoulGreatSwordUseParry.get()) {
                        int cooldown = this.parrySucced ? Config.SKILLET_PARRY_CD.get() : Config.SKILLET_PARRY_FAILED_CD.get();
                        player.getCooldowns().addCooldown(stack.getItem(), cooldown);
                        this.parrySucced = false;
                        this.timeUsed = 0;
                        if (tag.contains("Parrying")) {
                            tag.remove("Parrying");
                        }
                        player.stopUsingItem();
                    }
                }
            }
            super.onUseTick(level, entity, stack, remainingUseDuration);
        }
    }

    private static boolean isPlayerNearHeatSource(Player player, LevelReader level) {
        if (player.isOnFire()) {
            return true;
        } else {
            BlockPos pos = player.blockPosition();

            for(BlockPos nearbyPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
                if (level.getBlockState(nearbyPos).is(BlockTags.create(new ResourceLocation("farmersdelight:heat_sources")))) {
                    return true;
                }
            }

            return false;
        }
    }

    public int getUseDuration(ItemStack stack) {
        if(stack.getOrCreateTag().contains("Cooking")){
            int fireAspectLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
            int cookingTime = stack.getOrCreateTag().getInt("CookTimeHandheld");
            return SkilletBlock.getSkilletCookingTime(cookingTime, fireAspectLevel);
        }
        else{
            return 72000;
        }
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity pLivingEntity, int pTimeCharged) {
        if (!level.isClientSide()) {
            if (pLivingEntity instanceof Player player) {
                CompoundTag tag = stack.getOrCreateTag();
                if (tag.contains("Cooking")) {
                    ItemStack cookingStack = ItemStack.of(tag.getCompound("Cooking"));
                    player.getInventory().placeItemBackInInventory(cookingStack);
                    tag.remove("Cooking");
                    tag.remove("CookTimeHandheld");
                }else{
                    int cooldown = this.parrySucced ? Config.SKILLET_PARRY_CD.get() : Config.SKILLET_PARRY_FAILED_CD.get();
                    if (ModConfig.MOB_CONFIG.canSoulGreatSwordUseParry.get()) {
                        player.getCooldowns().addCooldown(stack.getItem(), cooldown);
                    }
                    this.timeUsed = 0;
                    this.parrySucced = false;
                    if (tag.contains("Parrying")) {
                        tag.remove("Parrying");
                    }
                }
            }
            super.releaseUsing(stack, level, pLivingEntity, pTimeCharged);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("Cooking")) {
                ItemStack cookingStack = ItemStack.of(tag.getCompound("Cooking"));
                Optional<CampfireCookingRecipe> cookingRecipe = getCookingRecipe(cookingStack, level);
                if(cookingRecipe.isPresent()){
                    cookingRecipe.ifPresent((recipe) -> {
                        ItemStack resultStack = recipe.assemble(new SimpleContainer(), level.registryAccess());
                        addHaultedResultFood(resultStack, player);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
                        }

                    });
                }else{
                    addHaultedResultFood(cookingStack, player);
                }

                tag.remove("Cooking");
                tag.remove("CookTimeHandheld");
            }
        }

        return stack;
    }

    public int getBarColor(ItemStack stack) {
        return stack.getTagElement("Cooking") != null ? 0x40E2ED : super.getBarColor(stack);
    }

    public void addHaultedResultFood(ItemStack resultStack, Player player){
        resultStack.getOrCreateTag().putBoolean("LMDHaunted", true);
        if (!player.getInventory().add(resultStack)) {
            player.drop(resultStack, false);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.legendary_delicacies." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(string + "_1").withStyle(ChatFormatting.GRAY));
    }
}
