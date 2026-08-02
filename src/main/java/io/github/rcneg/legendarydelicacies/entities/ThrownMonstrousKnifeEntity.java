package io.github.rcneg.legendarydelicacies.entities;

import io.github.rcneg.legendarydelicacies.init.EntityTypeRegistry;
import io.github.rcneg.legendarydelicacies.init.ItemRegistry;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.AnnihilationBombTrail;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.effect.ModEffects;
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
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
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

public class ThrownMonstrousKnifeEntity extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<Byte> ID_LOYALTY;
    private static final EntityDataAccessor<Boolean> ID_FOIL;
    private ItemStack tridentItem;
    private boolean dealtDamage;
    public int clientSideReturnTridentTickCount;
    public boolean bursted;

    public ThrownMonstrousKnifeEntity(EntityType<? extends ThrownMonstrousKnifeEntity> p_37561_, Level p_37562_) {
        super(p_37561_, p_37562_);
        this.tridentItem = new ItemStack(ItemRegistry.MONSTROUS_KNIFE.get());
    }

    public ThrownMonstrousKnifeEntity(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_) {
        super(EntityTypeRegistry.THROWN_MONSTROUS_KNIFE.get(), p_37570_, p_37569_);
        this.tridentItem = new ItemStack(ItemRegistry.MONSTROUS_KNIFE.get());
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
        this.doBurst(this, result.getBlockPos());
    }

    public void doBurst(Entity entity, BlockPos pos) {
        entity.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, 1.0F);
        Level level = this.level();
        if(this.getOwner() instanceof LivingEntity living){
            if (!this.bursted) {
                bursted = true;
                CameraShakeEntity.cameraShake(this.level(), this.position(), 10.0F, 0.3F, 0, 15);
                for(LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate((double)3.0F))) {
                    if (target != living) {
                        if (target instanceof Player living1) {
                            if (living1.getAbilities().invulnerable) {
                                continue;
                            }
                        }

                        if (target instanceof TamableAnimal) {
                            TamableAnimal animal = (TamableAnimal)target;
                            if (animal.isTame() && animal.getOwner() == living) {
                                continue;
                            }
                        }

                        target.push((double)0.0F, (double)0.6F, (double)0.0F);
                        if(living instanceof Player player){
                            target.hurt(player.damageSources().playerAttack(player), (float)((double)6.0F * (Double)ModConfig.MOB_CONFIG.MonstrousAnchorAbilityDamageMultiplier.get()));
                        }else{
                            target.hurt(living.damageSources().mobAttack(living), (float)((double)6.0F * (Double)ModConfig.MOB_CONFIG.MonstrousAnchorAbilityDamageMultiplier.get()));
                        }
                        target.addEffect(new MobEffectInstance((MobEffect) ModEffects.STUN.get(), 60, 0));
                    }
                }

                if (level.isClientSide) {
                    level.addParticle(new Circle.RingData(0.0F, ((float)Math.PI / 2F), 30, 1.0F, 1.0F, 1.0F, 1.0F, 50.0F, false, Circle.EnumRingBehavior.GROW), pos.getX(), this.getY(), pos.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);

                    for(int i = 0; i < 360; ++i) {
                        if (i % 2 == 0) {
                            double d1 = living.getRandom().nextGaussian() * (double)0.25F;
                            double d2 = living.getRandom().nextGaussian() * (double)0.25F;
                            double d3 = living.getRandom().nextGaussian() * (double)0.25F;
                            double angle = Math.toRadians((double)(-living.getYRot() + 180.0F)) + (double)i;
                            double x = (double)Mth.sin((float)(Math.PI * angle));
                            double z = (double)Mth.cos((float)(Math.PI * angle));
                            level.addParticle((ParticleOptions) ModParticles.LM_COSY_SMOKE.get(), pos.getX() + x, pos.getY() + (double)1.0F, pos.getZ() + z, d1, d2, d3);
                        }
                    }
                }
            }
        }
    }

    protected boolean tryPickup(Player p_150196_) {
        return super.tryPickup(p_150196_) || this.isNoPhysics() && this.ownedBy(p_150196_) && p_150196_.getInventory().add(this.getPickupItem());
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
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
        return new ItemStack(ItemRegistry.MONSTROUS_KNIFE.get());
    }

    static {
        ID_LOYALTY = SynchedEntityData.defineId(ThrownMonstrousKnifeEntity.class, EntityDataSerializers.BYTE);
        ID_FOIL = SynchedEntityData.defineId(ThrownMonstrousKnifeEntity.class, EntityDataSerializers.BOOLEAN);
    }
}
