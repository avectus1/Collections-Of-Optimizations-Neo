package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.VanillaOverrideChecks;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Entity.class, remap = false)
public abstract class MixinEntityFireScanGate {

    @Shadow
    private int remainingFireTicks;

    @Shadow
    public boolean wasOnFire;

    @Shadow
    protected abstract int getFireImmuneTicks();

    @Unique
    private boolean coo$fireScanSkipped;

    @Redirect(
            method = "move",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getBlockStatesIfLoaded(Lnet/minecraft/world/phys/AABB;)Ljava/util/stream/Stream;"
            ),
            require = 0
    )
    private Stream<BlockState> coo$skipIrrelevantFireScan(Level level, AABB box) {
        this.coo$fireScanSkipped = false;
        if (CoOConfig.vanillaSkipIrrelevantFireScan && !this.wasOnFire) {
            int ticks = this.remainingFireTicks;
            if (ticks > 0 || ticks == -this.getFireImmuneTicks() && VanillaOverrideChecks.stockFireSetter(this.getClass())) {
                this.coo$fireScanSkipped = true;
                return Stream.empty();
            }
        }
        return level.getBlockStatesIfLoaded(box);
    }

    @Redirect(
            method = "move",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/stream/Stream;noneMatch(Ljava/util/function/Predicate;)Z",
                    remap = false
            ),
            require = 0
    )
    private boolean coo$answerSkippedFireScan(Stream<BlockState> states, Predicate<? super BlockState> test) {
        if (this.coo$fireScanSkipped) {
            this.coo$fireScanSkipped = false;
            return false;
        }
        return states.noneMatch(test);
    }
}
