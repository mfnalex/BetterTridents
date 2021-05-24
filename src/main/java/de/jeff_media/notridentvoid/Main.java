package de.jeff_media.notridentvoid;

import de.jeff_media.notridentvoid.config.Config;
import de.jeff_media.notridentvoid.listeners.ProjectileListener;
import de.jeff_media.updatechecker.UpdateChecker;
import de.jeff_media.updatechecker.UserAgentBuilder;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Trident;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main instance;
    private final ArrayList<UUID> tridents = new ArrayList<>();
    public static final Material SAFETY_MATERIAL = Material.BARRIER;
    public static NamespacedKey LOYALTY_TAG;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        LOYALTY_TAG = new NamespacedKey(this, "loyalty");
        reload();
        Bukkit.getPluginManager().registerEvents(new ProjectileListener(), this);
        @SuppressWarnings("unused") Metrics metrics = new Metrics(this, 11460);
    }

    public void reload() {
        new Config();
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
    }

    public void register(Trident trident) {
        tridents.add(trident.getUniqueId());
    }

    public boolean isRegistered(Trident trident) {
        return tridents.contains(trident.getUniqueId());
    }

    public void unregister(Trident trident) {
        tridents.remove(trident.getUniqueId());
    }
}
