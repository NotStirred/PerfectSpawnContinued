package io.github.notstirred.perfectspawn_continued.config;

import io.github.notstirred.perfectspawn_continued.PSC;
import net.minecraftforge.common.config.Config;

@Config(modid = PSC.MOD_ID, type = Config.Type.INSTANCE)
public class PSCConfig {
    @Config.Comment("The initial spawn dimension for new players, and those who don't have beds.")
    public static int initialSpawnDimension = 0;

    @Config.Comment({"A list of the dimensions that players can respawn in.", "Yes ForgeConfig lists look terrible, I know."})
    public static int[] dimsPlayersCanSleepIn = { -1, 0 };

    @Config.Comment({"A list of the dimensions that beds explode in.", "Overrides \"dimsPlayersCanSleepIn\"", "Yes ForgeConfig lists look terrible, I know."})
    public static int[] dimsBedsExplodeIn = { -1, 1 };

}