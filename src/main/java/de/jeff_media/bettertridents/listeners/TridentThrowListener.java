package de.jeff_media.bettertridents.listeners;

import de.jeff_media.bettertridents.Main;
import de.jeff_media.bettertridents.utils.EnchantmentUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
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

        // Loyalty
        int loyalty = EnchantmentUtils.getLevelFromTrident(player, Enchantment.LOYALTY);
        main.debug("Applying loyalty level " + loyalty);
        EnchantmentUtils.registerLoyalty((Trident) event.getEntity(), loyalty);

        // Offhand
        if (player.getInventory().getItemInMainHand().getType() == Material.TRIDENT) return;
        if (player.getInventory().getItemInOffHand().getType() != Material.TRIDENT) return;
        trident.getPersistentDataContainer().set(Main.OFFHAND_TAG, PersistentDataType.BYTE, (byte) 1);
        main.debug("This trident was thrown by the offhand.");
    }

}
