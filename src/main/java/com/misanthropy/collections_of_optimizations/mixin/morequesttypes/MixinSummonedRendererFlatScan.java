package com.misanthropy.collections_of_optimizations.mixin.morequesttypes;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.pixeldreamstudios.morequesttypes.api.IQuestSummonedEntity;
import net.pixeldreamstudios.morequesttypes.client.summon.SummonedEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Every frame More Quest Types collects every entity within 128 blocks of the camera (a scan of all entity
 * sections in a 256 block box, plus every mod hook on that lookup) only to find the few quest-summoned ones
 * that carry a texture. Walk the client's flat entity list and keep just those instead. Same entities.
 */
@Mixin(value = SummonedEntityRenderer.class, remap = false)
public abstract class MixinSummonedRendererFlatScan {
    @Unique
    private static final List<Entity> COO$FOUND = new ArrayList<>();

    @Redirect(method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"))
    private static List<Entity> coo$questEntitiesOnly(ClientLevel level, Entity except, AABB box, Predicate<? super Entity> filter) {
        if (!CoOConfig.morequesttypesFlatSummonScan) {
            return level.getEntities(except, box, filter);
        }
        List<Entity> found = COO$FOUND;
        found.clear();
        for (Entity entity : level.entitiesForRendering()) {
            if (entity instanceof IQuestSummonedEntity quest && quest.isQuestSummoned()
                    && entity.getBoundingBox().intersects(box) && filter.test(entity)) {
                found.add(entity);
            }
        }
        return found;
    }
}
