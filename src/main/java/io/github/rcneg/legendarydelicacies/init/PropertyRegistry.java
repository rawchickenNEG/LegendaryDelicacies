package io.github.rcneg.legendarydelicacies.init;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PropertyRegistry {
    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
        ItemProperties.register(ModItems.SOUL_GREAT_SWORD.get(), new ResourceLocation(LegendaryDelicacies.MODID, "possessed"), (itemstack, level, entity, number) ->
                itemstack.getTag() != null && itemstack.getTag().getBoolean("LMDPossessed") ? 1.0F : 0.0F);
        ItemProperties.register(ItemRegistry.SOUL_DAGGER.get(), new ResourceLocation(LegendaryDelicacies.MODID, "possessed"), (itemstack, level, entity, number) ->
                itemstack.getTag() != null && itemstack.getTag().getBoolean("LMDPossessed") ? 1.0F : 0.0F);
        ItemProperties.register(ItemRegistry.SOUL_SKILLET.get(), new ResourceLocation("parrying"), (itemstack, level, entity, number) ->
                itemstack.getTag() != null && itemstack.getTag().contains("Parrying") ? 1.0F : 0.0F);
        ItemProperties.register(ItemRegistry.SOUL_SKILLET.get(), new ResourceLocation("cooking"), (itemstack, level, entity, number) ->
                itemstack.getTag() != null && itemstack.getTag().contains("Cooking") ? 1.0F : 0.0F);
    }
}
