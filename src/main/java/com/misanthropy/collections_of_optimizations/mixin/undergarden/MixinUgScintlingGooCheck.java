package com.misanthropy.collections_of_optimizations.mixin.undergarden;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import quek.undergarden.entity.animal.Scintling;
import quek.undergarden.registry.UGBlocks;

/**
 * Every tick, every Scintling fires a mob griefing event before checking the 4 spots under it for room to leave goo.
 * Once it has left goo (or stands where none fits) there is nowhere to put any, so check the spots first and only
 * fire the event when goo could actually be placed. Same goo in the same places.
 */
@Mixin(value = Scintling.class, remap = false)
public abstract class MixinUgScintlingGooCheck {
    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/event/EventHooks;canEntityGrief(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean coo$roomFirst(Level level, Entity entity) {
        if (CoOConfig.undergardenScintlingGooCheck && !coo$anyRoom(level, entity)) {
            return false;
        }
        return EventHooks.canEntityGrief(level, entity);
    }

    private static boolean coo$anyRoom(Level level, Entity self) {
        BlockState goo = UGBlocks.GOO.get().defaultBlockState();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int y = Mth.floor(self.getY());
        for (int l = 0; l < 4; l++) {
            int x = Mth.floor(self.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
            int z = Mth.floor(self.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
            pos.set(x, y, z);
            if (level.isEmptyBlock(pos) && goo.canSurvive(level, pos)) {
                return true;
            }
        }
        return false;
    }
}
