package io.github.notstirred.perfectspawn_continued.mixin;

import io.github.notstirred.perfectspawn_continued.PSC;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Inject(method = "loadAllWorlds", at = @At("HEAD"))
    private void on$loadAllWorlds(String saveName, String worldNameIn, long seed, WorldType type, String generatorOptions, CallbackInfo ci) {
        PSC.LOGGER.error("MIXIN WORKING!");
    }

}
