package com.misanthropy.collections_of_optimizations.core;

public interface ElysiumTransformerHolder {
    Object coo$getElysiumTransformer();

    void coo$setElysiumTransformer(Object entry);

    /** One immutable pair, so a reader never sees a value from one reload with the stamp of another. */
    record Entry(int epoch, Object value) {
    }
}
