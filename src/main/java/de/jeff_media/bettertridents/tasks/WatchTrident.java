package de.jeff_media.bettertridents.tasks;

import de.jeff_media.bettertridents.Main;
import org.bukkit.Location;
import org.bukkit.entity.Trident;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WatchTrident extends BukkitRunnable {

    private static final int MAX_TICKS = 1200;
    private final Trident trident;
    private int ticks = 0;

    public WatchTrident(Trident trident) {
        this.trident = trident;
    }

    @Override
    public void run() {

        ticks++;
        if(ticks >= MAX_TICKS || trident == null || trident.isDead() || !trident.isValid() || trident.getVelocity().length() == 0) {
            cancel();
            return;
        }

        Location nextLocation = trident.getLocation().add(trident.getVelocity());

        if(nextLocation.getBlockY() > 1) return;
        if(!nextLocation.getBlock().getType().isAir()) return;

        if(nextLocation.getBlockY() < 0) {
            nextLocation.setY(0);
            trident.teleport(nextLocation.clone().add(0,1,0));
            trident.setVelocity(new Vector(0,-1,0));
        }

        nextLocation.getBlock().setType(Main.SAFETY_MATERIAL);
        Main.getInstance().debug("New Task: RemoveBarrier");
        new RemoveBarrier(trident, nextLocation.getBlock()).runTaskTimer(Main.getInstance(), 1, 1);
        cancel();
    }
}
