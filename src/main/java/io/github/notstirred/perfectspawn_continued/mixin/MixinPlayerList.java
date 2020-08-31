package io.github.notstirred.perfectspawn_continued.mixin;

import io.github.notstirred.perfectspawn_continued.PSC;
import net.minecraft.server.management.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(PlayerList.class)
public class MixinPlayerList {
    @ModifyConstant(method = "createPlayerForUser", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;isDemo()Z")))
    private int on$getDefaultDimension(int _0) {
        return PSC.INSTANCE.CONFIG.getInitialSpawnDimension();
    }
}
