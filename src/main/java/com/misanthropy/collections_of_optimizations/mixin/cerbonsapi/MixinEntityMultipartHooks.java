package com.misanthropy.collections_of_optimizations.mixin.cerbonsapi;

import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.misanthropy.collections_of_optimizations.core.CoOMixinCanceller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Same behaviour as CERBON's EntityMixin, but the "is this a multipart entity" answer is worked out once
 * per entity instead of on every getBoundingBox call, and there is no CallbackInfoReturnable per call.
 * Only does anything when CoOMixinCanceller actually removed CERBON's hook.
 */
@Mixin(value = Entity.class, priority = 1500, remap = false)
public abstract class MixinEntityMultipartHooks {
    @Unique
    private byte coo$multipartKind;

    @Unique
    private byte coo$multipartKind() {
        byte kind = this.coo$multipartKind;
        if (kind == 0) {
            kind = 1;
            if (CoOMixinCanceller.isActive("com.cerbon.cerbons_api.mixin.multipart_entities.EntityMixin")) {
                if ((Object) this instanceof MultipartEntity) {
                    kind |= 2;
                }
                if ((Object) this instanceof MultipartAwareEntity) {
                    kind |= 4;
                }
            }
            this.coo$multipartKind = kind;
        }
        return kind;
    }

    @ModifyReturnValue(method = "getBoundingBox", at = @At("RETURN"))
    private AABB coo$compoundBoundingBox(AABB original) {
        if ((this.coo$multipartKind() & 2) == 0 || original instanceof CompoundOrientedBox) {
            return original;
        }
        return ((MultipartEntity) (Object) this).getCompoundBoundingBox(original);
    }

    @Inject(method = "setPosRaw", at = @At("TAIL"))
    private void coo$notifyMultipartPos(double x, double y, double z, CallbackInfo ci) {
        if ((this.coo$multipartKind() & 4) != 0) {
            ((MultipartAwareEntity) (Object) this).onSetPos(x, y, z);
        }
    }
}
