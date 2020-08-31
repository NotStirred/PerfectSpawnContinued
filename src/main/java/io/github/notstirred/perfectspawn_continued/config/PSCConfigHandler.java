package io.github.notstirred.perfectspawn_continued.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.notstirred.perfectspawn_continued.PSC;
import io.github.notstirred.perfectspawn_continued.config.PSCConfig;
import io.github.notstirred.perfectspawn_continued.config.internal.Rule;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PSCConfigHandler
{
    PSCConfig activeConfig;

    public PSCConfigHandler()
    {
        activeConfig = null;
    }

    public PSCConfig getActiveConfig()
    {
        return activeConfig;
    }

    // Active
    public void serverStarting(FMLServerStartingEvent event)
    {
        reloadConfig();
    }

    public void reloadConfig()
    {
        PSC.LOGGER.debug("Reloading PSC config");

        activeConfig = null;

        if (FMLCommonHandler.instance().getMinecraftServerInstance().getServerOwner() != null)
        {
            String world = FMLCommonHandler.instance().getMinecraftServerInstance().getFolderName();

            File worldDictionary = DimensionManager.getCurrentSaveRootDirectory();
            if (worldDictionary != null)
            {
                File worldConfig = new File(worldDictionary, "PSC.json");

                PSC.LOGGER.debug(" - Trying world config: " + worldConfig.getAbsolutePath());

                if (worldConfig.isFile())
                {
                    activeConfig = loadConfig(worldConfig);
                }
            }
        }

        if (activeConfig == null)
        {
            File globalConfigFile = new File(DimensionManager.getCurrentSaveRootDirectory().getParentFile().getParentFile(), "PSC.json");

            PSC.LOGGER.debug(" - Trying global config: " + globalConfigFile.getAbsolutePath());

            if (globalConfigFile.exists())
            {
                activeConfig = loadConfig(globalConfigFile);
            }
        }
    }

    private PSCConfig loadConfig(File file)
    {
        JsonParser jsonParser = new JsonParser();
        FileReader fileReader = null;

        try
        {
            fileReader = new FileReader(file);
        }
        catch (FileNotFoundException fileNotFound)
        {
            PSC.LOGGER.warn("Couldn't read " + file.getAbsolutePath());
        }

        if (fileReader != null)
        {
            JsonElement jsonElement = jsonParser.parse(fileReader);
            try
            {
                fileReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            JsonObject jsonObject = (JsonObject) jsonElement;

            boolean rulesExist = jsonObject.has("rules");
            boolean generalExists = jsonObject.has("general");

            PSCConfig config = new PSCConfig();

            if (generalExists)
            {
                JsonObject generalObject = jsonObject.getAsJsonObject("general");

                if (generalObject.has("initialSpawnDimension"))
                {
                    config.setInitialSpawnDimension(generalObject.get("initialSpawnDimension").getAsInt());
                }
            }

            if (rulesExist)
            {
                JsonArray rulesArray = jsonObject.getAsJsonArray("rules");

                ArrayList<Rule> ruleList = new ArrayList<Rule>();
                for (int i = 0; i < rulesArray.size(); i++)
                {
                    Rule rule = new Rule();

                    JsonObject ruleObject = (JsonObject) rulesArray.get(i);

                    JsonArray appliesArray = ruleObject.getAsJsonArray("appliesTo");

                    Object[] appliesTo = new Object[appliesArray.size()];

                    for (int a = 0; a < appliesArray.size(); a++)
                    {
                        JsonElement element = appliesArray.get(a);

                        if (element.getAsJsonPrimitive().isNumber())
                        {
                            appliesTo[a] = element.getAsInt();
                        }
                        else
                        {
                            appliesTo[a] = element.getAsString();
                        }
                    }

                    rule.setAppliesTo(appliesTo);

                    if (ruleObject.has("respawnDimension"))
                    {
                        rule.setRespawnDimension(ruleObject.get("respawnDimension").getAsInt());
                    }

                    if (ruleObject.has("canSleepHere"))
                    {
                        rule.setCanSleepHere(ruleObject.get("canSleepHere").getAsBoolean() ? true : false);
                    }

                    if (ruleObject.has("canRespawnHere"))
                    {
                        rule.setCanRespawnHere(ruleObject.get("canRespawnHere").getAsBoolean() ? true : false);
                    }

                    if (ruleObject.has("spawnPoint"))
                    {
                        JsonObject spawnPointObject = ruleObject.getAsJsonObject("spawnPoint");

                        int posX = spawnPointObject.get("posX").getAsInt();
                        int posY = spawnPointObject.get("posY").getAsInt();
                        int posZ = spawnPointObject.get("posZ").getAsInt();

                        BlockPos pos = new BlockPos(posX, posY, posZ);

                        rule.setSpawnPoint(pos);
                    }

                    ruleList.add(rule);
                }

                config.setRules(ruleList.toArray(new Rule[0]));
            }

            return config;
        }

        return null;
    }
}