package de.jeff_media.bettertridents.tasks;

import org.bukkit.entity.Trident;
import org.bukkit.scheduler.BukkitRunnable;

public class WatchTrident extends BukkitRunnable {

    private static final int MAX_TICKS = 1200;

    private final Trident trident;
    private int ticks = 0;

    public WatchTrident(Trident trident) {
        this.trident = trident;
    }

    private void rescue() {
        if (trident.getLocation().getY() < trident.getWorld().getMinHeight() - 20) {
            trident.setHasDealtDamage(true);
        }
    }

    @Override
    public void run() {
        ticks++;
        if (ticks >= MAX_TICKS || trident == null || trident.isDead() || !trident.isValid() || trident.getVelocity().length() == 0) {
            cancel();
            return;
        }

        rescue();
    }
}
