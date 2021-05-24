package de.jeff_media.notridentvoid.tasks;

import de.jeff_media.notridentvoid.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Trident;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveBarrier extends BukkitRunnable {

    private static final int MAX_TICKS = 40;
    private final Block block;
    private final Trident trident;
    private int ticks = 0;

    public RemoveBarrier(Trident trident, Block block) {
        this.block = block;
        this.trident = trident;
    }

    @Override
    public void run() {
        ticks++;
        if(ticks >= MAX_TICKS || (trident.getVelocity().length() > 0 && trident.getLocation().distanceSquared(block.getLocation())>2)) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () ->{
                if (block.getType() == Main.SAFETY_MATERIAL) {
                    block.setType(Material.AIR);
                }
                cancel();
            },20);
        }
    }
}
