package de.jeff_media.bettertridents.tasks;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class MoveToOffhand extends BukkitRunnable {
    private final Player player;
    private final ItemStack tridentItem;

    public MoveToOffhand(Player player, ItemStack tridentItem) {
        this.player = player;
        this.tridentItem = tridentItem;
    }

    @Override
    public void run() {
        for (ItemStack item : player.getInventory()) {
            if (item != null && item.equals(tridentItem)) {
                player.getInventory().remove(item);
                player.getInventory().setItemInOffHand(item.clone());
                break;
            }
        }
        player.updateInventory();
    }
}
