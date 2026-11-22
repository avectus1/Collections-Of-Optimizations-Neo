package com.misanthropy.collections_of_optimizations.mixin.emf;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.EmfCompiledAnimations;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import traben.entity_model_features.models.animation.math.asm.ASMParser;

@Mixin(
   value = {ASMParser.class},
   remap = false
)
public abstract class MixinASMParserReuse {
   @WrapOperation(
      method = {"compileOrNull"},
      at = {@At(
         value = "INVOKE",
         target = "Ltraben/entity_model_features/models/animation/math/asm/ASMParser$1;define()Ljava/lang/Class;"
      )},
      require = 0
   )
   private static Class<?> coo$reuseClass(@Coerce ClassLoader loader, Operation<Class<?>> original, @Local byte[] bytes, @Local(ordinal = 0) String className) {
      return !CoOConfig.emfReuseCompiledAnimations
         ? (Class)original.call(new Object[]{loader})
         : EmfCompiledAnimations.define(EmfCompiledAnimations.key(bytes, className), () -> (Class<?>)original.call(new Object[]{loader}));
   }

   @WrapOperation(
      method = {"compileOrNull"},
      at = {@At(
         value = "INVOKE",
         target = "Ljava/lang/invoke/MethodHandles$Lookup;findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;"
      )},
      require = 0
   )
   private static MethodHandle coo$reuseHandle(Lookup lookup, Class<?> type, String name, MethodType methodType, Operation<MethodHandle> original) throws Throwable {
      return !CoOConfig.emfReuseCompiledAnimations
         ? (MethodHandle)original.call(new Object[]{lookup, type, name, methodType})
         : EmfCompiledAnimations.handle(type, () -> (MethodHandle)original.call(new Object[]{lookup, type, name, methodType}));
   }
}
