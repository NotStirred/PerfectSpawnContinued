package io.github.notstirred.perfectspawn_continued.mixin;

import com.mojang.authlib.GameProfile;
import io.github.notstirred.perfectspawn_continued.config.PSCConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Arrays;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {

    @Shadow @Nullable private Integer spawnDimension;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void on$init(World worldIn, GameProfile gameProfileIn, CallbackInfo ci) {
        this.spawnDimension = PSCConfig.initialSpawnDimension;
    }

    /**
     * Minecraft checks if the dimension is the overworld, if not the player is denied sleeping there.
     */
    @Redirect(method = "trySleep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldProvider;isSurfaceWorld()Z"))
    public boolean on$isSurfaceWorld(WorldProvider worldProvider) {
        return Arrays.stream(PSCConfig.dimsPlayersCanSleepIn).anyMatch(predicate -> predicate == worldProvider.getDimensionType().getId());
    }
}
