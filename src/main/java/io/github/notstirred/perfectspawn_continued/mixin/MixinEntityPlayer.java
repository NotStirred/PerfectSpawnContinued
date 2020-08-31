package io.github.notstirred.perfectspawn_continued.mixin;

import com.mojang.authlib.GameProfile;
import io.github.notstirred.perfectspawn_continued.PSC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {

    @Shadow @Nullable private Integer spawnDimension;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void on$init(World worldIn, GameProfile gameProfileIn, CallbackInfo ci) {
        this.spawnDimension = PSC.INSTANCE.CONFIG.getRespawnDimension(worldIn.provider);
    }

}
