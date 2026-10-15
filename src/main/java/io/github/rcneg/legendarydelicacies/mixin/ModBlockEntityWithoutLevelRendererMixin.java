package io.github.rcneg.legendarydelicacies.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import io.github.rcneg.legendarydelicacies.items.DiamondResurrectedJavelinItem;
import io.github.rcneg.legendarydelicacies.items.GoldResurrectedJavelinItem;
import net.miauczel.legendary_monsters.client.ModBlockEntityWithoutLevelRenderer;
import net.miauczel.legendary_monsters.entity.client.ModModelLayers;
import net.miauczel.legendary_monsters.entity.client.Model.Item.ResurrectedJavelinItemModel;
import net.miauczel.legendary_monsters.entity.client.Model.Item.ShatteredGreatswordItemModel;
import net.miauczel.legendary_monsters.entity.client.Render.LMRenderTypes;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.item.custom.ResurrectedJavelinItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModBlockEntityWithoutLevelRenderer.class)
public class ModBlockEntityWithoutLevelRendererMixin {
    @Unique
    private static final ShatteredGreatswordItemModel SHATTERED_GREATSWORD_MODEL = new ShatteredGreatswordItemModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.SHATTERED_GREATSWORD_LAYER));;
    @Unique
    private static final ResourceLocation SHATTERED_GREATSWORD_DIAMOND = new ResourceLocation("legendary_monsters", "textures/entity/beheaded_knight/beheaded_knight_diamond.png");
    @Unique
    private static final ResourceLocation SHATTERED_GREATSWORD_GOLD = new ResourceLocation("legendary_monsters", "textures/entity/beheaded_knight/beheaded_knight_golden.png");
    @Unique
    private static final ResurrectedJavelinItemModel JAVELIN_MODEL = new ResurrectedJavelinItemModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.RESURRECTED_JAVELIN_LAYER));;
    @Unique
    private static final ResourceLocation JAVELIN_GOLD = new ResourceLocation("legendary_delicacies", "textures/item/golden_resurrected_javelin.png");
    @Unique
    private static final ResourceLocation JAVELIN_DIAMOND = new ResourceLocation("legendary_delicacies", "textures/item/diamond_resurrected_javelin.png");
    @Inject(
            method = {
                    "renderByItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
                    "m_108829_(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"
            },
            at = @At("HEAD"),
            cancellable = true,
            remap = false,
            require = 1
    )
    private void lmd$renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, CallbackInfo ci) {
        if (stack.getItem() == ItemRegistry.DIAMOND_SHATTERED_GREATSWORD.get()) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.15F, 0.5F);
            float scale = 0.85F;
            poseStack.scale(scale, -scale, -scale);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, LMRenderTypes.entityCutoutNoCull(SHATTERED_GREATSWORD_DIAMOND), false, stack.hasFoil());
            SHATTERED_GREATSWORD_MODEL.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
        if (stack.getItem() == ItemRegistry.GOLD_SHATTERED_GREATSWORD.get()) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.15F, 0.5F);
            float scale = 0.85F;
            poseStack.scale(scale, -scale, -scale);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, LMRenderTypes.entityCutoutNoCull(SHATTERED_GREATSWORD_GOLD), false, stack.hasFoil());
            SHATTERED_GREATSWORD_MODEL.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
        if (stack.getItem() == ItemRegistry.GOLD_RESURRECTED_JAVELIN.get()) {
            poseStack.pushPose();
            Item isUsingStack = stack.getItem();
            if (isUsingStack instanceof GoldResurrectedJavelinItem) {
                poseStack.translate(0.5F, 0.5F, 0.5F);
            }

            float scale = 0.85F;
            poseStack.scale(scale, -scale, -scale);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, LMRenderTypes.entityCutoutNoCull(JAVELIN_GOLD), false, stack.hasFoil());
            JAVELIN_MODEL.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
        if (stack.getItem() == ItemRegistry.DIAMOND_RESURRECTED_JAVELIN.get()) {
            poseStack.pushPose();
            Item isUsingStack = stack.getItem();
            if (isUsingStack instanceof DiamondResurrectedJavelinItem) {
                poseStack.translate(0.5F, 0.5F, 0.5F);
            }

            float scale = 0.85F;
            poseStack.scale(scale, -scale, -scale);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, LMRenderTypes.entityCutoutNoCull(JAVELIN_DIAMOND), false, stack.hasFoil());
            JAVELIN_MODEL.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
}
