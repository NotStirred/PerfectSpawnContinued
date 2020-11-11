package io.github.notstirred.perfectspawn_continued;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = PSC.MOD_ID,
        name = PSC.MOD_NAME,
        version = PSC.VERSION,
        acceptableRemoteVersions = "*"
)
public class PSC {
    public static final String MOD_ID = "perfectspawn_continued";
    public static final String MOD_NAME = "Perfect Spawn Continued";
    public static final String VERSION = "1.0-SNAPSHOT";

    public static Logger LOGGER;

    @Mod.Instance(MOD_ID)
    public static PSC INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }
}
