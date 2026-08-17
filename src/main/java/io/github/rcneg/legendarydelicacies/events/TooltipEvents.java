package io.github.rcneg.legendarydelicacies.events;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import io.github.rcneg.legendarydelicacies.tags.LMDTags;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = LegendaryDelicacies.MODID, value = {Dist.CLIENT})
public class TooltipEvents {
    public TooltipEvents() {
    }
    @SubscribeEvent
    public static void addTooltipPlantableFoods(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        CompoundTag tag = stack.getTag();
        if(tag != null && tag.getBoolean("LMDPossessed")){
            if (stack.is(LMDTags.CAN_BE_POSSESSED) && !stack.is(ItemRegistry.SOUL_DAGGER.get()) && !stack.is(ModItems.SOUL_GREAT_SWORD.get())) {
                String string = "tooltip.legendary_delicacies." + stack.getItem() + ".possessed";
                event.getToolTip().add(1, Component.translatable("tooltip.legendary_delicacies.possessed_soul").withStyle(ChatFormatting.DARK_RED));
                event.getToolTip().add(2, Component.translatable(string).withStyle(ChatFormatting.GRAY));
            }
        }
        if(tag != null){
            if(tag.getBoolean("LMDHaunted")){
                event.getToolTip().add(1, Component.translatable("tooltip.legendary_delicacies.haunted").withStyle(ChatFormatting.DARK_AQUA));
            }
            if(tag.getBoolean("LMDRich")){
                event.getToolTip().add(1, Component.translatable("tooltip.legendary_delicacies.rich").withStyle(ChatFormatting.GOLD));
            }
            if(tag.getBoolean("LMDBreed")){
                event.getToolTip().add(1, Component.translatable("tooltip.legendary_delicacies.breed").withStyle(ChatFormatting.LIGHT_PURPLE));
            }
            if(tag.getBoolean("LMDFission")){
                event.getToolTip().add(1, Component.translatable("tooltip.legendary_delicacies.fission").withStyle(ChatFormatting.DARK_GREEN));
            }
            if(tag.getBoolean("LMDIsOffspring")){
                event.getToolTip().add(1, Component.translatable("tooltip.legendary_delicacies.is_offspring").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
            }
        }
    }
}