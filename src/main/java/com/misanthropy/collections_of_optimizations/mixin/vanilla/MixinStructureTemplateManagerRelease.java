package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.StructureTemplateRelease;
import com.misanthropy.collections_of_optimizations.core.StructureTemplateStamp;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Vanilla keeps every structure template it ever loaded for the whole session, so memory climbs as you
 * explore. Stamp a template whenever it is handed out, and let a periodic sweep drop the ones nobody has
 * asked for in a while; asking again just loads the same file again. Templates made in game by a structure
 * block (not loaded from a file) are never dropped.
 */
@Mixin(value = StructureTemplateManager.class, remap = false)
public abstract class MixinStructureTemplateManagerRelease implements StructureTemplateRelease.Sweepable {
    @Shadow
    private Map<ResourceLocation, Optional<StructureTemplate>> structureRepository;

    @Unique
    private final Set<ResourceLocation> coo$pinned = ConcurrentHashMap.newKeySet();

    @ModifyReturnValue(method = "get", at = @At("RETURN"))
    private Optional<StructureTemplate> coo$stamp(Optional<StructureTemplate> result) {
        if (result.isPresent()) {
            ((StructureTemplateStamp) result.get()).coo$touch(System.nanoTime());
        }
        return result;
    }

    @Inject(method = "getOrCreate", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private void coo$pinCreated(ResourceLocation id, CallbackInfoReturnable<StructureTemplate> cir) {
        this.coo$pinned.add(id);
    }

    @Override
    public int coo$sweep(long olderThanNanos) {
        if (!CoOConfig.vanillaReleaseStructureTemplates) {
            return 0;
        }
        long now = System.nanoTime();
        int dropped = 0;
        for (Map.Entry<ResourceLocation, Optional<StructureTemplate>> entry : this.structureRepository.entrySet()) {
            Optional<StructureTemplate> value = entry.getValue();
            if (value == null || value.isEmpty() || this.coo$pinned.contains(entry.getKey())) {
                continue;
            }
            if (now - ((StructureTemplateStamp) value.get()).coo$lastUsed() > olderThanNanos
                    && this.structureRepository.remove(entry.getKey(), value)) {
                dropped++;
            }
        }
        return dropped;
    }
}
