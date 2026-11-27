package com.misanthropy.collections_of_optimizations.mixin.cataclysm;

import com.github.L_Ender.cataclysm.client.render.layer.Maledictus_Cicle_Layer;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Maledictus_Cicle_Layer.class, remap = false)
public abstract class MixinCataclysmCicleTexture {

    @Unique
    private static final ResourceLocation coo$RING = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/particle/ring_1.png");

    @Redirect(
            method = "rendercicle",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;fromNamespaceAndPath(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"),
            require = 0
    )
    private ResourceLocation coo$sharedRing(String namespace, String path) {
        if (CoOConfig.cataclysmCacheCicleTexture && "cataclysm".equals(namespace) && "textures/particle/ring_1.png".equals(path)) {
            return coo$RING;
        }
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
