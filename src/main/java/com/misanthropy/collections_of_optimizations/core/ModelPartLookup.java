package com.misanthropy.collections_of_optimizations.core;

import java.util.Optional;
import net.minecraft.client.model.geom.ModelPart;

/** Implemented on HierarchicalModel: cached bone lookup that allocates nothing on a hit. */
public interface ModelPartLookup {
    Optional<ModelPart> coo$cachedDescendant(String name);
}
