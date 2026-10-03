package com.misanthropy.collections_of_optimizations.mixin.architectury;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import dev.architectury.event.EventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.invoke.MethodHandles;

@Mixin(value = EventFactory.class, remap = false)
public abstract class MixinEventFactoryDispatch {

    /**
     * @author Collections Of Optimizations
     * @reason Preserve listener exception behavior without constructing a MethodHandle per call.
     */
    @SuppressWarnings("unchecked")
    @Overwrite(remap = false)
    private static <T, R> R invokeMethod(T listener, Method method, Object[] args) throws Throwable {
        if (!CoOConfig.architecturyDirectEventDispatch) {
            return (R) MethodHandles.lookup().unreflect(method).bindTo(listener).invokeWithArguments(args);
        }
        try {
            return (R) method.invoke(listener, args);
        } catch (InvocationTargetException exception) {
            throw exception.getCause();
        }
    }
}
