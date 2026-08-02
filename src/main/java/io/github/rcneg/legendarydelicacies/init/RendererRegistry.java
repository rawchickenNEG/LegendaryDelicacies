package io.github.rcneg.legendarydelicacies.init;

import io.github.rcneg.legendarydelicacies.client.ThrownMonstrousKnifeRenderer;
import io.github.rcneg.legendarydelicacies.client.ThrownTesseractKnifeRenderer;
import io.github.rcneg.legendarydelicacies.entities.ThrownMonstrousKnifeEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RendererRegistry {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.THROWN_TESSERACT_KNIFE.get(), ThrownTesseractKnifeRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.THROWN_MONSTROUS_KNIFE.get(), ThrownMonstrousKnifeRenderer::new);

    }

}