package de.jeff_media.bettertridents.tasks;

import com.github.Anon8281.universalScheduler.UniversalRunnable;
import de.jeff_media.bettertridents.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Trident;
public class RemoveBarrier extends UniversalRunnable {

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
            Main.getScheduler().runTaskLater(() ->{
                if (block.getType() == Material.BARRIER) {
                    block.setType(Material.AIR);
                }
                cancel();
            },20);
        }
    }
}