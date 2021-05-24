package de.jeff_media.bettertridents.listeners;

import de.jeff_media.bettertridents.Main;
import de.jeff_media.bettertridents.config.Config;
import de.jeff_media.bettertridents.tasks.WatchTrident;
import de.jeff_media.bettertridents.utils.EnchantmentUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class VoidListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onThrowTrident(ProjectileLaunchEvent event) {
        main.debug("VoidListener");
        if (event.getEntityType() != EntityType.TRIDENT) {
            main.debug("Not a trident");
            return;
        }
        Trident trident = (Trident) event.getEntity();
        //if (main.isLoyal(trident)) return;
        if (!(trident.getShooter() instanceof Player)) {
            main.debug("Not shot by player");
            return;
        }
        Player player = (Player) trident.getShooter();
        ItemStack tridentItem = null;
        if (player.getInventory().getItemInOffHand() != null) {
            if (player.getInventory().getItemInOffHand().getType() == Material.TRIDENT) {
                tridentItem = player.getInventory().getItemInOffHand();
            }
        }
        if (player.getInventory().getItemInMainHand() != null) {
            if (player.getInventory().getItemInMainHand().getType() == Material.TRIDENT) {
                tridentItem = player.getInventory().getItemInMainHand();
            }
        }
        if (tridentItem == null) {
            main.debug("tridentItem not found");
            return;
        }
        if (!EnchantmentUtils.hasLoyalty(tridentItem)) {
            main.debug("No loyalty");
            return;
        }
        if(!main.getConfig().getBoolean(Config.VOID_SAVING)) {
            main.debug("Void Saving disabled");
            return;
        }
        main.setLoyal(trident);
        main.debug("New task: WatchTrident");
        new WatchTrident(trident).runTaskTimer(main,1,1);
        Bukkit.getScheduler().runTaskLater(main,() ->main.removeLoyal(trident),1200);
    }
}
