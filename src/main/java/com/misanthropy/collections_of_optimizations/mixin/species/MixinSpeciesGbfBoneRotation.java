package com.misanthropy.collections_of_optimizations.mixin.species;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.species.api.disguise.client.DisguiseSurfingPose;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.util.RenderUtil;

/**
 * Species and GeckoBetterFPS both change GeckoLib's bone rotation: GeckoBetterFPS replaces the whole method
 * with a faster one, and Species hooks into the original to add its surfing pose. Mixin can't apply both, so
 * Species' hook failed for every GeckoLib model and resource loading got stuck. With both installed, CoO
 * swaps out both of their versions for this one, which is GeckoBetterFPS' fast rotation with Species'
 * surfing pose applied to the angles.
 */
@Mixin(value = RenderUtil.class, remap = false)
public abstract class MixinSpeciesGbfBoneRotation {
    /**
     * @author collections_of_optimizations
     * @reason GeckoBetterFPS rotation + Species surfing pose
     */
    @Overwrite
    public static void rotateMatrixAroundBone(PoseStack poseStack, GeoBone bone) {
        float z = DisguiseSurfingPose.zRotation(bone, bone.getRotZ());
        float y = DisguiseSurfingPose.yRotation(bone, bone.getRotY());
        float x = DisguiseSurfingPose.xRotation(bone, bone.getRotX());
        if (z != 0.0F || y != 0.0F || x != 0.0F) {
            poseStack.mulPose(new Quaternionf().rotationZYX(z, y, x));
        }
    }

    /**
     * @author collections_of_optimizations
     * @reason same as GeckoBetterFPS
     */
    @Overwrite
    public static Matrix4f translateMatrix(Matrix4f matrix, Vector3f vector) {
        return matrix.m30(matrix.m30() + vector.x).m31(matrix.m31() + vector.y).m32(matrix.m32() + vector.z);
    }
}
