package de.jeff_media.bettertridents.listeners;

import de.jeff_media.bettertridents.Main;
import de.jeff_media.bettertridents.config.Config;
import de.jeff_media.bettertridents.tasks.MoveToOffhand;
import de.jeff_media.bettertridents.utils.EnchantmentUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.ItemStack;

public class OffhandListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler(ignoreCancelled = true)
    private void onPickupTrident(PlayerPickupArrowEvent event) {
        main.debug("onPickupTrident");
        if (!main.getConfig().getBoolean(Config.RETURN_TO_OFFHAND)) return;
        if (!(event.getArrow() instanceof Trident)) return;
        Trident trident = (Trident) event.getArrow();
        if(!EnchantmentUtils.isOffhandThrown(trident)) {
            main.debug("This trident wasn't thrown from the offhand.");
            return;
        }
        Player player = event.getPlayer();
        if (player.getInventory().getItemInOffHand().getType() != Material.AIR) return;

        ItemStack tridentItem = event.getItem().getItemStack().clone();

        main.debug("Starting offhand task...");
        new MoveToOffhand(player, tridentItem).runTask(main);
    }
}


