package io.github.rcneg.legendarydelicacies.items;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.BeheadedKnight.BeheadedKnightEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.ResurrectedKnight.ResurrectedKnightEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class KnightsChallengeSummoner extends Item {
    private final int tier;
    public KnightsChallengeSummoner(Properties p_41383_, int tier) {
        super(p_41383_);
        this.tier = tier;
    }

    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level instanceof ServerLevel serverLevel) {
            ItemStack itemstack = pContext.getItemInHand();
            BlockPos blockpos = pContext.getClickedPos();
            Direction direction = pContext.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);
            BlockPos spawnPos = blockstate.getCollisionShape(level, blockpos).isEmpty() ? blockpos : blockpos.relative(direction);

            BeheadedKnightEntity beheaded = ModEntities.BEHEADED_KNIGHT.get().create(serverLevel);
            ResurrectedKnightEntity resurrected = ModEntities.RESURRECTED_KNIGHT.get().create(serverLevel);
            if(beheaded != null && resurrected != null){
                beheaded.setTextureVariant(tier + 1);
                resurrected.setTextureVariant(tier + 1);
                if(tier == 1){
                    beheaded.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier(UUID.fromString("28422144-0cd1-4c57-861f-4af459157479"), "add_damage", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE));
                    beheaded.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(UUID.fromString("cdac0d47-f546-4d3e-8b06-fce9897c879b"), "add_health", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE));
                    resurrected.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier(UUID.fromString("28422144-0cd1-4c57-861f-4af459157479"), "add_damage", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE));
                    resurrected.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(UUID.fromString("cdac0d47-f546-4d3e-8b06-fce9897c879b"), "add_health", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE));
                }
                if(tier == 2){
                    beheaded.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier(UUID.fromString("28422144-0cd1-4c57-861f-4af459157479"), "add_damage", 2.5D, AttributeModifier.Operation.MULTIPLY_BASE));
                    beheaded.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(UUID.fromString("cdac0d47-f546-4d3e-8b06-fce9897c879b"), "add_health", 1.5D, AttributeModifier.Operation.MULTIPLY_BASE));
                    beheaded.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(new AttributeModifier(UUID.fromString("0184fa2f-c959-4778-8165-69e4814ba78d"), "add_speed", 0.4D, AttributeModifier.Operation.MULTIPLY_BASE));
                    resurrected.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier(UUID.fromString("28422144-0cd1-4c57-861f-4af459157479"), "add_damage", 2.5D, AttributeModifier.Operation.MULTIPLY_BASE));
                    resurrected.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(UUID.fromString("cdac0d47-f546-4d3e-8b06-fce9897c879b"), "add_health", 1.5D, AttributeModifier.Operation.MULTIPLY_BASE));
                    resurrected.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(new AttributeModifier(UUID.fromString("0184fa2f-c959-4778-8165-69e4814ba78d"), "add_speed", 0.4D, AttributeModifier.Operation.MULTIPLY_BASE));
                }
                beheaded.heal(beheaded.getMaxHealth());
                beheaded.moveToBlockPos(spawnPos.offset(1, 0, 0));
                beheaded.setSleep(true);
                serverLevel.addFreshEntity(beheaded);
                resurrected.heal(resurrected.getMaxHealth());
                resurrected.moveToBlockPos(spawnPos.offset(-1, 0, 0));
                resurrected.setSleep(true);
                serverLevel.addFreshEntity(resurrected);
            }
            itemstack.shrink(1);
            level.gameEvent(pContext.getPlayer(), GameEvent.ENTITY_PLACE, spawnPos);

            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.SUCCESS;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.legendary_delicacies." + stack.getItem();
        tooltip.add(Component.translatable("tooltip.legendary_delicacies.knights_challenge_summoner").withStyle(ChatFormatting.BLUE));
        ChatFormatting format = tier == 1 ? ChatFormatting.RED : tier == 2 ? ChatFormatting.DARK_PURPLE : ChatFormatting.GRAY;
        tooltip.add(Component.translatable(string).withStyle(format));
    }
}
