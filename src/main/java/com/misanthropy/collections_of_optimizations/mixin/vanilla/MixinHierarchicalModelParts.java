package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ModelPartLookup;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Stock getAnyDescendantWithName builds a stream over every part of the model on each call, and keyframe
 * animations call it for every animated bone every frame. Remember each answer per model root.
 */
@Mixin(value = HierarchicalModel.class, remap = false)
public abstract class MixinHierarchicalModelParts implements ModelPartLookup {

    @Shadow
    public abstract ModelPart root();

    @Unique
    private ModelPart coo$cachedRoot;

    @Unique
    private Map<String, Optional<ModelPart>> coo$partsByName;

    @Inject(method = "getAnyDescendantWithName", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$cachePartLookup(String name, CallbackInfoReturnable<Optional<ModelPart>> cir) {
        if (CoOConfig.vanillaCacheModelPartLookups && RenderSystem.isOnRenderThread()) {
            cir.setReturnValue(this.coo$cachedDescendant(name));
        }
    }

    @Override
    public Optional<ModelPart> coo$cachedDescendant(String name) {
        ModelPart root = this.root();
        Map<String, Optional<ModelPart>> cache = this.coo$partsByName;
        if (cache == null || this.coo$cachedRoot != root) {
            cache = new Object2ObjectOpenHashMap<>(16);
            this.coo$partsByName = cache;
            this.coo$cachedRoot = root;
        }
        Optional<ModelPart> hit = cache.get(name);
        if (hit == null) {
            hit = name.equals("root")
                    ? Optional.of(root)
                    : root.getAllParts().filter(part -> part.hasChild(name)).findFirst().map(part -> part.getChild(name));
            cache.put(name, hit);
        }
        return hit;
    }
}
