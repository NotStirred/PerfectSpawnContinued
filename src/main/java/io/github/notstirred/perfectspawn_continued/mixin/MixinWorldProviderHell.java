package io.github.notstirred.perfectspawn_continued.mixin;

import io.github.notstirred.perfectspawn_continued.config.PSCConfig;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(WorldProviderHell.class)
public abstract class MixinWorldProviderHell extends WorldProvider {
    @Inject(method = "canRespawnHere", at = @At("HEAD"), cancellable = true)
    public void canRespawnHere(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(Arrays.stream(PSCConfig.dimsPlayersCanSleepIn).anyMatch(predicate -> predicate == getDimensionType().getId()));
    }
}
