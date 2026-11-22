package com.misanthropy.collections_of_optimizations.mixin.redomesticate;

import com.evandev.redomesticate.api.ITameableEntity;
import com.evandev.redomesticate.util.TameableUtils;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

/**
 * Redomesticate asks "is this mob tamed?" several times per tick for every living entity (fire immunity,
 * fluid pushing, movement sounds, its tick handler). The answer starts with an interface instanceof
 * check, and on Java 21 a failed interface check scans every interface the class implements; in a big
 * pack Entity carries a long list of mixed-in interfaces, so the scan is long and repeated each call.
 * Remember the interface answer per class instead. Same result.
 */
@Mixin(value = TameableUtils.class, remap = false)
public abstract class MixinTameableUtilsFastCheck {
    @Unique
    private static final ClassValue<Boolean> COO$TAMEABLE = new ClassValue<>() {
        @Override
        protected Boolean computeValue(Class<?> type) {
            return ITameableEntity.class.isAssignableFrom(type);
        }
    };

    /**
     * @author CoO
     * @reason per-class interface answer
     */
    @Overwrite
    public static boolean isTamed(Entity entity) {
        if (entity instanceof TamableAnimal tamable && tamable.isTame()) {
            return true;
        }
        if (!CoOConfig.redomesticateFastTamedCheck) {
            return entity instanceof ITameableEntity tameable && tameable.redomesticate$isTame();
        }
        if (entity == null) {
            return false;
        }
        return COO$TAMEABLE.get(entity.getClass()) && ((ITameableEntity) entity).redomesticate$isTame();
    }
}
