package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.core.StructureTemplateStamp;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = StructureTemplate.class, remap = false)
public abstract class MixinStructureTemplateStamp implements StructureTemplateStamp {
    @Unique
    private volatile long coo$lastUsed = System.nanoTime();

    @Override
    public long coo$lastUsed() {
        return this.coo$lastUsed;
    }

    @Override
    public void coo$touch(long now) {
        this.coo$lastUsed = now;
    }
}
