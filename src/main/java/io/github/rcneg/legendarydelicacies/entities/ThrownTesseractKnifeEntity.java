package io.github.rcneg.legendarydelicacies.entities;

import io.github.rcneg.legendarydelicacies.init.EntityTypeRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.miauczel.legendary_monsters.Particle.custom.AnnihilationBombTrail;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationPortalEntity;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ThrownTesseractKnifeEntity extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<Byte> ID_LOYALTY;
    private static final EntityDataAccessor<Boolean> ID_FOIL;
    private ItemStack tridentItem;
    private boolean dealtDamage;
    public int clientSideReturnTridentTickCount;
    public boolean bursted;

    public ThrownTesseractKnifeEntity(EntityType<? extends ThrownTesseractKnifeEntity> p_37561_, Level p_37562_) {
        super(p_37561_, p_37562_);
        this.tridentItem = new ItemStack(ItemRegistry.TESSERACT_KNIFE.get());
    }

    public ThrownTesseractKnifeEntity(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_) {
        super(EntityTypeRegistry.THROWN_TESSERACT_KNIFE.get(), p_37570_, p_37569_);
        this.tridentItem = new ItemStack(ItemRegistry.TESSERACT_KNIFE.get());
        this.tridentItem = p_37571_.copy();
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(p_37571_));
        this.entityData.set(ID_FOIL, p_37571_.hasFoil());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_LOYALTY, (byte)0);
        this.entityData.define(ID_FOIL, false);
    }

    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        double $$d3;
        if (!this.inGround && this.level().isClientSide) {
            if (this.tickCount % 3 == 0) {
                float yaw = (float)Math.toRadians((double)(-this.getYRot() + 180.0F));
                double theta = (double)this.getYRot() * 0.017453292519943295;
                ++theta;
                $$d3 = Math.cos(theta);
                double vecZ = Math.sin(theta);
                Vec3 vec3 = this.getDeltaMovement();
                double spawnX = this.getX() + vec3.x + $$d3 * 0.25;
                double spawnZ = this.getZ() + vec3.z + vecZ * 0.25;
                this.level().addParticle(new Circle.RingData(this.horizontalCollision ? 0.0F : yaw, this.horizontalCollision ? 90.0F : 0.0F, 30, 1.0F, 1.0F, 1.0F, 1.0F, 10.0F, false, Circle.EnumRingBehavior.GROW_THEN_SHRINK), spawnX, this.getY(), spawnZ, 0.0, 0.0, 0.0);
            }
        }

        if(!bursted){
            double dx = this.getX() + (double)(1.5F * (this.random.nextFloat() - 0.5F));
            double dy = this.getY() + (double)(1.5F * (this.random.nextFloat() - 0.5F));
            double dz = this.getZ() + (double)(1.5F * (this.random.nextFloat() - 0.5F));
            float ran = 0.4F;
            float r = 0.0F;
            float g = 0.7647059F + this.random.nextFloat() * ran;
            float b = 0.0F;
            if (this.level().isClientSide) {
                this.level().addParticle(new AnnihilationBombTrail.OrbData(r, g, b, 0.5F, 0.8F, this.getId()), dx, dy, dz, 0.0, 0.0, 0.0);
            }
        }

        Entity $$0 = this.getOwner();
        int $$1 = (Byte)this.entityData.get(ID_LOYALTY);
        if ($$1 > 0 && (this.dealtDamage || this.isNoPhysics()) && $$0 != null && bursted) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 $$2 = $$0.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + $$2.y * 0.015 * (double)$$1, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double $$3 = 0.05 * (double)$$1;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add($$2.normalize().scale($$3)));
                if (this.clientSideReturnTridentTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.clientSideReturnTridentTickCount;
            }
        }

        super.tick();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity $$0 = this.getOwner();
        if ($$0 != null && $$0.isAlive()) {
            return !($$0 instanceof ServerPlayer) || !$$0.isSpectator();
        } else {
            return false;
        }
    }

    protected ItemStack getPickupItem() {
        return this.tridentItem.copy();
    }

    public boolean isFoil() {
        return (Boolean)this.entityData.get(ID_FOIL);
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 p_37575_, Vec3 p_37576_) {
        return this.dealtDamage ? null : super.findHitEntity(p_37575_, p_37576_);
    }

    protected void onHitEntity(EntityHitResult p_37573_) {
        Entity $$1 = p_37573_.getEntity();
        float $$2 = 6.0F;
        if ($$1 instanceof LivingEntity $$3) {
            $$2 += EnchantmentHelper.getDamageBonus(this.tridentItem, $$3.getMobType());
        }

        Entity $$4 = this.getOwner();
        DamageSource $$5 = this.damageSources().trident(this, (Entity)($$4 == null ? this : $$4));
        this.dealtDamage = true;
        if ($$1.hurt($$5, $$2)) {
            if ($$1 instanceof LivingEntity) {
                LivingEntity $$7 = (LivingEntity)$$1;
                if ($$4 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects($$7, $$4);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)$$4, $$7);
                }

                this.doPostHurtEffects($$7);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.doBurst(this);
    }

    public void doBurst(Entity entity) {
        entity.playSound((SoundEvent) ModSounds.FLAME_BURST.get(), 1.0F, 1.0F);
        if(this.getOwner() instanceof LivingEntity living){
            if (!this.bursted) {
                int uniformWarmup = -15;
                int lifeTick = 50;
                this.doPortalEffect(entity, 4.0, 2.0F, 5, uniformWarmup, 2.0F, lifeTick, 8.0F);
                AnnihilationPortalEntity annihilationPortalEntity = new AnnihilationPortalEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), 0.0F, uniformWarmup, living, lifeTick, (float)(12.5 * (Double) ModConfig.MOB_CONFIG.TheTesseractAbilityDamageMultiplier.get()), true, 4.0F);
                entity.level().addFreshEntity(annihilationPortalEntity);
                ParticleOptions particleOptions = new Circle.RingData(0.0F, 1.5707964F, 20, 0.0F, 1.0F, 0.0F, 1.0F, 150.0F, false, Circle.EnumRingBehavior.SHRINK);
                bursted = true;
                CameraShakeEntity.cameraShake(entity.level(), entity.position(), 30.0F, 0.1F, 5, 5);                if (entity.level().isClientSide) {
                    entity.level().addParticle(particleOptions, entity.getX(), entity.getY(), entity.getZ(), 0.0, 0.0, 0.0);
                }
            }
        }
    }

    public void doPortalEffect(Entity entity, double multiplier, float size, int amountOfPortals, int warmup, float scale, int life, float damage) {
        for(int k = 0; k < amountOfPortals; ++k) {
            float f3 = (float)k * 3.1415927F * size / (float)amountOfPortals + 3.1415927F * size / 10.0F;
            this.createAnnihilationPortal(entity, entity.getX() + (double) Mth.cos(f3) * multiplier, entity.getZ() + (double)Mth.sin(f3) * multiplier, entity.getY() - 5.0, entity.getY() + 5.0, life, warmup, scale, (float)((double)damage * (Double)ModConfig.MOB_CONFIG.TheTesseractAbilityDamageMultiplier.get()));
        }

    }

    private void createAnnihilationPortal(Entity entity, double pX, double pZ, double pMinY, double pMaxY, int life, int pWarmupDelay, float scale, float damage) {
        BlockPos blockpos = BlockPos.containing(pX, pMaxY, pZ);
        boolean flag = false;
        double d0 = 0.0;
        if(this.getOwner() instanceof LivingEntity living){
            do {
                BlockPos blockpos1 = blockpos.below();
                BlockState blockstate = entity.level().getBlockState(blockpos1);
                if (blockstate.isFaceSturdy(entity.level(), blockpos1, Direction.UP)) {
                    if (!entity.level().isEmptyBlock(blockpos)) {
                        BlockState blockstate1 = entity.level().getBlockState(blockpos);
                        VoxelShape voxelshape = blockstate1.getCollisionShape(entity.level(), blockpos);
                        if (!voxelshape.isEmpty()) {
                            d0 = voxelshape.max(Direction.Axis.Y);
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.below();
            } while(blockpos.getY() >= Mth.floor(pMinY) - 1);

            if (flag) {
                entity.level().addFreshEntity(new AnnihilationPortalEntity(entity.level(), pX, (double)blockpos.getY() + d0, pZ, 0.0F, pWarmupDelay, living, life, damage, true, scale));
            }
        }
    }

    protected boolean tryPickup(Player p_150196_) {
        return super.tryPickup(p_150196_) || this.isNoPhysics() && this.ownedBy(p_150196_) && p_150196_.getInventory().add(this.getPickupItem());
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return ModSounds.OBLITERATOR_ARM_SHOOT.get();
    }

    public void playerTouch(Player p_37580_) {
        if (this.ownedBy(p_37580_) || this.getOwner() == null) {
            super.playerTouch(p_37580_);
        }

    }

    public void readAdditionalSaveData(CompoundTag p_37578_) {
        super.readAdditionalSaveData(p_37578_);
        if (p_37578_.contains("Trident", 10)) {
            this.tridentItem = ItemStack.of(p_37578_.getCompound("Trident"));
        }

        this.dealtDamage = p_37578_.getBoolean("DealtDamage");
        this.entityData.set(ID_LOYALTY, (byte)EnchantmentHelper.getLoyalty(this.tridentItem));
    }

    public void addAdditionalSaveData(CompoundTag p_37582_) {
        super.addAdditionalSaveData(p_37582_);
        p_37582_.put("Trident", this.tridentItem.save(new CompoundTag()));
        p_37582_.putBoolean("DealtDamage", this.dealtDamage);
    }

    public void tickDespawn() {
        int $$0 = (Byte)this.entityData.get(ID_LOYALTY);
        if (this.pickup != Pickup.ALLOWED || $$0 <= 0) {
            super.tickDespawn();
        }

    }

    protected float getWaterInertia() {
        return 0.99F;
    }

    public boolean shouldRender(double p_37588_, double p_37589_, double p_37590_) {
        return true;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(ItemRegistry.TESSERACT_KNIFE.get());
    }

    static {
        ID_LOYALTY = SynchedEntityData.defineId(ThrownTesseractKnifeEntity.class, EntityDataSerializers.BYTE);
        ID_FOIL = SynchedEntityData.defineId(ThrownTesseractKnifeEntity.class, EntityDataSerializers.BOOLEAN);
    }
}
