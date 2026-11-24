package io.github.rcneg.legendarydelicacies.mixin;

import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Lava_eaterEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.FireBreath;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBreath.class)
public class FireBreathMixin {
    @Inject(
            method = "hitEntities",
            at = @At("HEAD"),
            remap = false
    )
    public void lmd$fireBreathConvertBottle(CallbackInfo ci) {
        FireBreath fireBreath = (FireBreath)(Object)this;
        for(ItemEntity entityHit : fireBreath.getEntitiesNearby(ItemEntity.class, (double)10.0F, (double)10.0F, (double)10.0F, (double)10.0F)) {
            float entityRelativeYaw = lmd$getEntityRelativeYaw((Math.atan2(entityHit.getZ() - fireBreath.getZ(), entityHit.getX() - fireBreath.getX()) * (180D / Math.PI) - (double) 90.0F), fireBreath.getYRot());
            float xzDistance = (float)Math.sqrt((entityHit.getZ() - fireBreath.getZ()) * (entityHit.getZ() - fireBreath.getZ()) + (entityHit.getX() - fireBreath.getX()) * (entityHit.getX() - fireBreath.getX()));
            double hitY = entityHit.getY() + (double)entityHit.getBbHeight() / (double)2.0F;
            float entityRelativePitch = lmd$getEntityRelativeYaw(Math.atan2(hitY - fireBreath.getY(), (double) xzDistance) * (180D / Math.PI), -fireBreath.getXRot());
            float entityHitDistance = (float)Math.sqrt((entityHit.getZ() - fireBreath.getZ()) * (entityHit.getZ() - fireBreath.getZ()) + (entityHit.getX() - fireBreath.getX()) * (entityHit.getX() - fireBreath.getX()) + (hitY - fireBreath.getY()) * (hitY - fireBreath.getY()));
            int distance = fireBreath.tickCount / 2;
            boolean inRange = entityHitDistance <= (float)distance + 1.0F;
            boolean yawCheck = entityRelativeYaw <= 22.5F && entityRelativeYaw >= -22.5F || entityRelativeYaw >= 337.5F || entityRelativeYaw <= -337.5F;
            boolean pitchCheck = entityRelativePitch <= 22.5F && entityRelativePitch >= -22.5F || entityRelativePitch >= 337.5F || entityRelativePitch <= -337.5F;
            boolean CloseCheck = fireBreath.getCaster() instanceof Lava_eaterEntity && entityHitDistance <= 2.0F;

            if ((inRange && yawCheck && pitchCheck || CloseCheck) && fireBreath.tickCount % 3 == 0) {
                if(entityHit.getItem().is(Items.GLASS_BOTTLE)){
                    entityHit.getItem().shrink(1);
                    entityHit.spawnAtLocation(ItemRegistry.BURNING_BOTTLE.get());
                }
            }
        }
    }

    @Unique
    private static float lmd$getEntityRelativeYaw(double entityHit, float fireBreath) {
        float entityHitYaw = (float) (entityHit % (double) 360.0F);
        float entityAttackingYaw = fireBreath % 360.0F;
        if (entityHitYaw < 0.0F) {
            entityHitYaw += 360.0F;
        }

        if (entityAttackingYaw < 0.0F) {
            entityAttackingYaw += 360.0F;
        }

        return entityHitYaw - entityAttackingYaw;
    }
}
