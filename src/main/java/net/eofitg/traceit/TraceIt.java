package net.eofitg.traceit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.eofitg.traceit.command.TraceItCommand;
import net.eofitg.traceit.config.TraceItConfig;
import net.eofitg.traceit.listener.BlockListener;
import net.eofitg.traceit.listener.ItemListener;
import net.eofitg.traceit.listener.TickListener;
import net.eofitg.traceit.util.Reference;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.MOD_VERSION,
        acceptedMinecraftVersions = "[1.8.9]",
        clientSideOnly = true
)
public class TraceIt {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static TraceItConfig config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        File configFile = new File(e.getModConfigurationDirectory(), "trace-it.json");
        loadConfig(configFile);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveConfig(configFile)));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        registerListeners(new BlockListener(), new ItemListener(), new TickListener());
        registerCommands(new TraceItCommand());
    }

    private void registerListeners(Object... listeners) {
        Arrays.stream(listeners).forEachOrdered(MinecraftForge.EVENT_BUS::register);
    }

    private void registerCommands(ICommand... commands) {
        Arrays.stream(commands).forEachOrdered(ClientCommandHandler.instance::registerCommand);
    }

    public static void loadConfig(File configFile) {
        if (configFile.exists()) {
            try {
                String json = FileUtils.readFileToString(configFile);
                config = gson.fromJson(json, TraceItConfig.class);
            } catch (Exception ignored) {}
        } else {
            config = new TraceItConfig();
            saveConfig(configFile);
        }
    }

    public static void saveConfig(File configFile) {
        try {
            String json = gson.toJson(config);
            FileUtils.write(configFile, json);
        } catch (Exception ignored) {}
    }

}
