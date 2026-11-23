package com.misanthropy.collections_of_optimizations.mixin.corpse;

import com.misanthropy.collections_of_optimizations.core.ClientUnloadRelease;
import de.maxhenkel.corpse.corelib.CachedMap;
import de.maxhenkel.corpse.entities.CorpseEntity;
import de.maxhenkel.corpse.entities.CorpseRenderer;
import de.maxhenkel.corpse.entities.DummyPlayer;
import de.maxhenkel.corpse.entities.DummySkeleton;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** The corpse renderer caches a stand-in player per corpse and only expires them while corpses are being drawn; empty it on unload. */
@Mixin(value = CorpseRenderer.class, remap = false)
public abstract class MixinCorpseRendererRelease implements ClientUnloadRelease.Releasable {
    @Shadow
    @Final
    private CachedMap<CorpseEntity, DummyPlayer> players;
    @Shadow
    @Final
    private CachedMap<CorpseEntity, DummySkeleton> skeletons;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void coo$track(CallbackInfo ci) {
        ClientUnloadRelease.track(this);
    }

    @Override
    public void coo$release() {
        this.players.clear();
        this.skeletons.clear();
    }
}
