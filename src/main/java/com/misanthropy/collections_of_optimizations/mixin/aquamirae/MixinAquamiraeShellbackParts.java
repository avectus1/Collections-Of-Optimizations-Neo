package com.misanthropy.collections_of_optimizations.mixin.aquamirae;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.misanthropy.collections_of_optimizations.core.AquamiraeMultipartCount;
import com.misanthropy.collections_of_optimizations.core.CoOMixinCanceller;
import dev.obscuria.aquamirae.api.common.entity.MultipartEntity;
import dev.obscuria.aquamirae.api.common.entity.SubPartEntity;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Aquamirae wraps every Level.getEntities(entity, box, predicate) call (collisions, pushing, AI, attacks)
 * in a new consumer that checks every found entity for its Shellback chest parts. Same behaviour here, but
 * the wrapper is skipped entirely while no Shellback is loaded, which is nearly always.
 */
@Mixin(value = Level.class, remap = false)
public abstract class MixinAquamiraeShellbackParts {
    @WrapOperation(method = "getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/entity/LevelEntityGetter;get(Lnet/minecraft/world/phys/AABB;Ljava/util/function/Consumer;)V"))
    private void coo$shellbackParts(LevelEntityGetter<Entity> getter, AABB box, Consumer<Entity> consumer, Operation<Void> original,
                                    @Local(argsOnly = true) Entity self, @Local(argsOnly = true) Predicate<? super Entity> predicate,
                                    @Local List<Entity> list) {
        if (!AquamiraeMultipartCount.any() || !CoOMixinCanceller.isActive("dev.obscuria.aquamirae.integration.mixin.MixinLevel")) {
            original.call(getter, box, consumer);
            return;
        }
        original.call(getter, box, (Consumer<Entity>) target -> {
            consumer.accept(target);
            if (target != self && target instanceof MultipartEntity multipart) {
                for (SubPartEntity<?> part : multipart.getSubParts()) {
                    if (predicate.test(part)) {
                        list.add(part);
                    }
                }
            }
        });
    }
}
