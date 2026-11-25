package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ModelPartLookup;
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
 * animations call it for every animated bone every frame. Walk the tree directly instead, in the same order
 * as the stream, without the stream objects. Nothing is cached: mods such as Entity Model Features swap
 * parts of a model at runtime, and a remembered part could be one that is no longer drawn.
 */
@Mixin(value = HierarchicalModel.class, remap = false)
public abstract class MixinHierarchicalModelParts implements ModelPartLookup {

    @Shadow
    public abstract ModelPart root();

    @Inject(method = "getAnyDescendantWithName", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$walkPartLookup(String name, CallbackInfoReturnable<Optional<ModelPart>> cir) {
        if (CoOConfig.vanillaCacheModelPartLookups) {
            cir.setReturnValue(this.coo$cachedDescendant(name));
        }
    }

    @Override
    public Optional<ModelPart> coo$cachedDescendant(String name) {
        ModelPart root = this.root();
        if (name.equals("root")) {
            return Optional.of(root);
        }
        return Optional.ofNullable(coo$find(root, name));
    }

    /** Pre-order like ModelPart.getAllParts: a part first, then each child subtree in map order. */
    @Unique
    private static ModelPart coo$find(ModelPart part, String name) {
        Map<String, ModelPart> children = ((ModelPartChildrenAccessor) (Object) part).coo$children();
        ModelPart direct = children.get(name);
        if (direct != null) {
            return direct;
        }
        for (ModelPart child : children.values()) {
            ModelPart found = coo$find(child, name);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
