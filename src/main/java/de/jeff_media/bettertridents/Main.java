package de.jeff_media.bettertridents;

import de.jeff_media.bettertridents.commands.ReloadCommand;
import de.jeff_media.bettertridents.config.Config;
import de.jeff_media.bettertridents.config.ConfigUpdater;
import de.jeff_media.bettertridents.listeners.*;
import de.jeff_media.updatechecker.UpdateChecker;
import de.jeff_media.updatechecker.UserAgentBuilder;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Trident;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main instance;
    private final ArrayList<UUID> tridents = new ArrayList<>();
    public static final Material SAFETY_MATERIAL = Material.BARRIER;
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
        Bukkit.getPluginManager().registerEvents(new VoidListener(), this);
        Bukkit.getPluginManager().registerEvents(new DropListener(), this);
        Bukkit.getPluginManager().registerEvents(new ImpalingListener(), this);
        Bukkit.getPluginManager().registerEvents(new OffhandListener(), this);
        Bukkit.getPluginManager().registerEvents(new TridentThrowListener(), this);
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
        UpdateChecker.init(this, "https://api.jeff-media.de/notridentvoid/latest-version.txt")
                .setDonationLink("https://paypal.me/mfnalex")
                .setDownloadLink(92656)
                .setChangelogLink(92656)
                .setUserAgent(UserAgentBuilder.getDefaultUserAgent());
        if(getConfig().getString(Config.CHECK_FOR_UPDATES).equalsIgnoreCase("true")) {
            UpdateChecker.getInstance().checkEveryXHours(getConfig().getDouble(Config.UPDATE_CHECK_INTERVAL))
                    .checkNow();
        } else if(getConfig().getString(Config.CHECK_FOR_UPDATES).equalsIgnoreCase("on-startup")) {
            UpdateChecker.getInstance().checkNow();
        }
        debug = getConfig().getBoolean(Config.DEBUG);
        if(debug) {
            getLogger().warning("Debug mode enabled - this may affect performance.");
        }
    }

    public void setLoyal(Trident trident) {
        tridents.add(trident.getUniqueId());
    }

    public boolean isLoyal(Trident trident) {
        return tridents.contains(trident.getUniqueId());
    }

    public void removeLoyal(Trident trident) {
        tridents.remove(trident.getUniqueId());
    }
}
