package io.github.notstirred.perfectspawn_continued.mixin;

import io.github.notstirred.perfectspawn_continued.config.PSCConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(WorldProvider.class)
public abstract class MixinWorldProvider {

    @Shadow public abstract boolean canRespawnHere();

    @Shadow public abstract DimensionType getDimensionType();

    @Inject(method = "canRespawnHere", at = @At("HEAD"), cancellable = true)
    public void canRespawnHere(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(Arrays.stream(PSCConfig.dimsPlayersCanSleepIn).anyMatch(predicate -> predicate == getDimensionType().getId()));
    }

    @Inject(method = "canSleepAt", at = @At("HEAD"), cancellable = true, remap = false)
    public void on$canSleepAt(EntityPlayer player, BlockPos pos, CallbackInfoReturnable<WorldProvider.WorldSleepResult> cir) {
        WorldProvider.WorldSleepResult sleepResult;
        if(Arrays.stream(PSCConfig.dimsBedsExplodeIn).anyMatch((predicate) -> predicate == getDimensionType().getId()))
            sleepResult = WorldProvider.WorldSleepResult.BED_EXPLODES;
        else if(canRespawnHere())
            sleepResult = WorldProvider.WorldSleepResult.ALLOW;
        else
            sleepResult = WorldProvider.WorldSleepResult.DENY;
        cir.setReturnValue(sleepResult);
    }

}
