package io.github.rcneg.legendarydelicacies.init;

import io.github.rcneg.legendarydelicacies.client.renderer.DiamondJavelinRenderer;
import io.github.rcneg.legendarydelicacies.client.renderer.GoldenJavelinRenderer;
import io.github.rcneg.legendarydelicacies.client.renderer.ThrownMonstrousKnifeRenderer;
import io.github.rcneg.legendarydelicacies.client.renderer.ThrownTesseractKnifeRenderer;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ThrownResurrectedJavelin;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.ResurrectedJavelinRenderer;
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
        event.registerEntityRenderer(EntityTypeRegistry.GOLDEN_RESURRECTED_JAVELIN.get(), GoldenJavelinRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.DIAMOND_RESURRECTED_JAVELIN.get(), DiamondJavelinRenderer::new);
    }

}