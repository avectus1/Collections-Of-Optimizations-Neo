package com.misanthropy.collections_of_optimizations.mixin.enderscape;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import net.bunten.enderscape.registry.EnderscapeStructureMusic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Every few seconds Enderscape asks, for each player, every structure in the registry (hundreds in a modpack)
 * whether the player is standing in it. Only the structures that actually have a piece in the player's chunk
 * can answer yes, so check just those, in the same registry order. Same answer.
 */
@Mixin(value = EnderscapeStructureMusic.class, remap = false)
public abstract class MixinEnderscapeStructureMusicScan {
    /**
     * @author collections_of_optimizations
     * @reason only test structures referenced in the player's chunk
     */
    @Overwrite
    private static ResourceLocation getStructure(ServerLevel level, ServerPlayer player) {
        BlockPos pos = BlockPos.containing(player.position());
        StructureManager structures = level.structureManager();
        Map<Structure, LongSet> present = null;
        if (CoOConfig.enderscapeStructureMusicScan) {
            present = structures.getAllStructuresAt(pos);
            if (present.isEmpty()) {
                return ResourceLocation.withDefaultNamespace("none");
            }
        }
        for (Holder.Reference<Structure> structure : level.registryAccess().lookupOrThrow(Registries.STRUCTURE).listElements().toList()) {
            if (structure != null && (present == null || present.containsKey(structure.value()))
                    && structures.getStructureAt(pos, structure.value()).isValid()) {
                return structure.key().location();
            }
        }
        return ResourceLocation.withDefaultNamespace("none");
    }
}
