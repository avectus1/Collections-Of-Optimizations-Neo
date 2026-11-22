package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = StructureTemplate.class, remap = false)
public interface StructureTemplateAccessor {

    @Accessor("palettes")
    List<StructureTemplate.Palette> coo$palettes();

    @Accessor("entityInfoList")
    List<StructureTemplate.StructureEntityInfo> coo$entityInfoList();
}
