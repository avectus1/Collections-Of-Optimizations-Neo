package com.misanthropy.collections_of_optimizations.mixin.byepregen;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moepus.byepregen.dfc.runtime.ColumnTemplate;
import com.moepus.byepregen.dfc.runtime.CompiledColumnEvaluator;
import com.moepus.byepregen.dfc.runtime.ColumnTemplate.BindingResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
   value = {ColumnTemplate.class},
   remap = false
)
public abstract class MixinColumnTemplate {
   @Unique
   private volatile boolean coo$unbindable;

   @ModifyReturnValue(
      method = {"available"},
      at = {@At("RETURN")}
   )
   private boolean coo$skipUnbindable(boolean available) {
      return available && !this.coo$unbindable;
   }

   @WrapMethod(
      method = {"bindResolved"}
   )
   private CompiledColumnEvaluator coo$rememberUnbindable(BindingResolver resolver, Operation<CompiledColumnEvaluator> original) {
      try {
         return (CompiledColumnEvaluator)original.call(new Object[]{resolver});
      } catch (IllegalStateException var5) {
         String message = var5.getMessage();
         if (message != null && message.startsWith("Cannot bind an unwrapped density subtree containing Interpolated")) {
            this.coo$unbindable = true;
         }

         throw var5;
      }
   }
}
