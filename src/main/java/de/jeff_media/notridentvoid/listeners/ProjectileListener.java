package de.jeff_media.notridentvoid.listeners;

import de.jeff_media.notridentvoid.Main;
import de.jeff_media.notridentvoid.config.Config;
import de.jeff_media.notridentvoid.tasks.WatchTrident;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class ProjectileListener implements Listener {

    private final Main main = Main.getInstance();

    /*private boolean hasLoyalty(Entity trident) {
        return trident.getPersistentDataContainer().has(Main.LOYALTY_TAG, PersistentDataType.BYTE);
    }

    private void addLoyalty(Entity trident) {
        trident.getPersistentDataContainer().set(Main.LOYALTY_TAG,PersistentDataType.BYTE, (byte) 1);
    }*/

    private boolean hasLoyalty(ItemStack item) {
        if(!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasEnchant(Enchantment.LOYALTY);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onShoot(ProjectileLaunchEvent event) {
        if(event.getEntityType() != EntityType.TRIDENT) return;
        Trident trident = (Trident) event.getEntity();
        if(main.isRegistered(trident)) return;
        //if(!hasLoyalty(trident)) return;
        if(!(trident.getShooter() instanceof Player)) return;
        Player player = (Player) trident.getShooter();
        ItemStack tridentItem = null;
        if(player.getInventory().getItemInMainHand() != null) {
            if(player.getInventory().getItemInMainHand().getType() == Material.TRIDENT) {
                tridentItem = player.getInventory().getItemInMainHand();
            }
        } else if(player.getInventory().getItemInOffHand() != null) {
            if(player.getInventory().getItemInOffHand().getType() == Material.TRIDENT) {
                tridentItem = player.getInventory().getItemInOffHand();
            }
        }
        if(tridentItem == null) return;
        if(!hasLoyalty(tridentItem)) return;
        if(!main.getConfig().getBoolean(Config.VOID_SAVING)) return;
        main.register(trident);
        new WatchTrident(trident).runTaskTimer(main,1,1);
        Bukkit.getScheduler().runTaskLater(main,() ->main.unregister(trident),20);
    }
}
