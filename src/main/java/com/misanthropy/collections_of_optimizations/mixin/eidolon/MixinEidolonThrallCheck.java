package com.misanthropy.collections_of_optimizations.mixin.eidolon;

import alexthw.eidolon_repraised.util.EntityUtil;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.mixin.vanilla.EntityPersistentDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Eidolon asks "is this mob a thrall?" from isAlliedTo and canAttack, which run constantly during targeting.
 * getPersistentData() creates an empty tag on every entity it is called on; read the field instead.
 */
@Mixin(value = EntityUtil.class, remap = false)
public abstract class MixinEidolonThrallCheck {
    /**
     * @author collections_of_optimizations
     * @reason don't create a persistent data tag on every entity just to look inside it
     */
    @Overwrite
    public static boolean isEnthralled(LivingEntity entity) {
        if (!CoOConfig.eidolonThrallCheck) {
            return entity.getPersistentData().contains("eidolon_repraised:thrall");
        }
        CompoundTag data = ((EntityPersistentDataAccessor) entity).coo$persistentData();
        return data != null && data.contains("eidolon_repraised:thrall");
    }
}
