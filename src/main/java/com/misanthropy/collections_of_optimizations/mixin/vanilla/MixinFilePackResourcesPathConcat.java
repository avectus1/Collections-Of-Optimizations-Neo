package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.Locale;
import net.minecraft.server.packs.FilePackResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {FilePackResources.class}, remap = false)
public abstract class MixinFilePackResourcesPathConcat {
   @WrapOperation(
      method = {"getPathFromLocation"},
      at = {@At(
         value = "INVOKE",
         target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"
      )},
      require = 0
   )
   private static String coo$concatPath(Locale locale, String format, Object[] args, Operation<String> original) {
      return CoOConfig.vanillaFastZipPackPaths
            && args.length == 3
            && "%s/%s/%s".equals(format)
            && args[0] instanceof String a
            && args[1] instanceof String b
            && args[2] instanceof String c
         ? a + "/" + b + "/" + c
         : (String)original.call(new Object[]{locale, format, args});
   }
}
