package de.jeff_media.bettertridents;

import de.jeff_media.bettertridents.commands.ReloadCommand;
import de.jeff_media.bettertridents.config.Config;
import de.jeff_media.bettertridents.config.ConfigUpdater;
import de.jeff_media.bettertridents.listeners.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;
    //private final ArrayList<UUID> tridents = new ArrayList<>();
    public static NamespacedKey LOYALTY_TAG;
    public static NamespacedKey IMPALING_TAG;
    public static NamespacedKey OFFHAND_TAG;

    public static Main getInstance() {
        return instance;
    }
    private boolean debug = false;

    public void debug(String text) {
        if(debug) getLogger().warning("[DEBUG] " + text);
    }

    @Override
    public void onEnable() {
        instance = this;
        LOYALTY_TAG = new NamespacedKey(this, "loyalty");
        IMPALING_TAG = new NamespacedKey(this, "impaling");
        OFFHAND_TAG = new NamespacedKey(this, "offhand");
        reload();
        Bukkit.getPluginManager().registerEvents(new DropListener(), this);
        Bukkit.getPluginManager().registerEvents(new ImpalingListener(), this);
        Bukkit.getPluginManager().registerEvents(new OffhandListener(), this);
        Bukkit.getPluginManager().registerEvents(new TridentThrowListener(), this);
        Bukkit.getPluginManager().registerEvents(new PortalListener(), this);
        getCommand("bettertridents").setExecutor(new ReloadCommand());
        @SuppressWarnings("unused") Metrics metrics = new Metrics(this, 11460);
    }

    public void reload() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        reloadConfig();
        new Config();
        ConfigUpdater.updateConfig();

        debug = getConfig().getBoolean(Config.DEBUG);
        if(debug) {
            getLogger().warning("Debug mode enabled - this may affect performance.");
        }
    }

//    public void setLoyal(Trident trident) {
//        tridents.add(trident.getUniqueId());
//    }
//
//    public void removeLoyal(Trident trident) {
//        tridents.remove(trident.getUniqueId());
//    }
}
