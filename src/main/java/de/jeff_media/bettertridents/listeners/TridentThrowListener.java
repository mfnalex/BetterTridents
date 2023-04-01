package de.jeff_media.bettertridents.listeners;

import de.jeff_media.bettertridents.Main;
import de.jeff_media.bettertridents.config.Config;
import de.jeff_media.bettertridents.config.Permissions;
import de.jeff_media.bettertridents.tasks.WatchTrident;
import de.jeff_media.bettertridents.utils.EnchantmentUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class TridentThrowListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTridentThrow(ProjectileLaunchEvent event) {
        if (event.getEntityType() != EntityType.TRIDENT) return;
        main.debug("Trident throw Listener: ProjectileLaunchEvent");

        Trident trident = (Trident) event.getEntity();
        if (!(trident.getShooter() instanceof Player)) {
            main.debug("This trident wasn't thrown by a player.");
            return;
        }
        Player player = (Player) trident.getShooter();

        // Impaling
        int impaling = EnchantmentUtils.getLevelFromTrident(player, Enchantment.IMPALING);
        main.debug("Applying impaling level " + impaling);
        EnchantmentUtils.registerImpaling((Trident) event.getEntity(), impaling);

        // Offhand
        if (player.getInventory().getItemInMainHand().getType() == Material.TRIDENT) return;
        if (player.getInventory().getItemInOffHand().getType() != Material.TRIDENT) return;
        trident.getPersistentDataContainer().set(Main.OFFHAND_TAG, PersistentDataType.BYTE, (byte) 1);
        main.debug("This trident was thrown by the offhand.");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void applyLoyalty(ProjectileLaunchEvent event) {
        main.debug("VoidListener");
        if (event.getEntityType() != EntityType.TRIDENT) {
            main.debug("Not a trident");
            return;
        }
        Trident trident = (Trident) event.getEntity();
        if (!(trident.getShooter() instanceof Player)) {
            main.debug("Not shot by player");
            return;
        }
        Player player = (Player) trident.getShooter();
        if(!player.hasPermission(Permissions.SAVE_VOID)) return;
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
        //main.setLoyal(trident);
        main.debug("New task: WatchTrident");
        new WatchTrident(trident).runTaskTimer(main,1,1);
        //Bukkit.getScheduler().runTaskLater(main,() ->main.removeLoyal(trident),1200);
    }

}
