package com.misanthropy.collections_of_optimizations.mixin.ambientsounds;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.AmbientBiomeMatchCache;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.creative.ambientsounds.condition.BiomeCondition;
import team.creative.ambientsounds.environment.BiomeEnvironment;

@Mixin(value = BiomeEnvironment.BiomeArea.class, remap = false)
public abstract class MixinBiomeArea {
    @Shadow
    @Final
    public Holder<Biome> biome;

    @Inject(method = "checkBiome", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$readMemo(BiomeCondition[] conditions, CallbackInfoReturnable<Boolean> cir) {
        if (!CoOConfig.ambientsoundsMemoBiomeMatch || conditions == null || !Minecraft.getInstance().isSameThread())
            return;
        Boolean cached = AmbientBiomeMatchCache.get(conditions, biome);
        if (cached != null)
            cir.setReturnValue(cached);
    }

    @Inject(method = "checkBiome", at = @At("RETURN"), require = 0)
    private void coo$storeMemo(BiomeCondition[] conditions, CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.ambientsoundsMemoBiomeMatch && conditions != null && Minecraft.getInstance().isSameThread()) {
            AmbientBiomeMatchCache.put(conditions, biome, cir.getReturnValueZ());
        }
    }
}
