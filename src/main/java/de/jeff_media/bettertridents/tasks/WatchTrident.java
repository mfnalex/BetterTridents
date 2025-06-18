package de.jeff_media.bettertridents.tasks;

import com.jeff_media.jefflib.WorldUtils;
import de.jeff_media.bettertridents.Main;
import com.jeff_media.jefflib.ReflUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Trident;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WatchTrident extends BukkitRunnable {

    private static final int MAX_TICKS = 1200;
    private static Field damageDealtField;
    private static Method getHandleMethod;

    static {
        int fieldCount = 0;
        Class<?> entityThrownTridentClass = null;
        try {
             entityThrownTridentClass = ReflUtils.getNMSClass("EntityThrownTrident");
        } catch (Throwable ignored) {
            // 1.17+
        }

        try {
            if(entityThrownTridentClass == null) {
                // 1.17+
                entityThrownTridentClass = Class.forName("net.minecraft.world.entity.projectile.EntityThrownTrident");
            }
            getHandleMethod = ReflUtils.getOBCClass("entity.CraftTrident").getMethod("getHandle");
            for (Field field : entityThrownTridentClass.getDeclaredFields()) {
                if (field.getType() == Boolean.TYPE) {
                    damageDealtField = field;
                    damageDealtField.setAccessible(true);
                    fieldCount++;
                }
            }
        } catch (Exception e) {
            damageDealtField = null;
            getHandleMethod = null;
            //e.printStackTrace();
        }

        if (fieldCount == 1) {
            Main.getInstance().debug("Tridents will be rescued using reflection (Field: " + damageDealtField.getName() + ")");
        } else {
            damageDealtField = null;
            Main.getInstance().debug("Tridents will be rescued using the legacy setBlock method");
        }
    }

    private final Trident trident;
    private int ticks = 0;

    public WatchTrident(Trident trident) {
        this.trident = trident;
    }

    private void legacyRescue() {
        Location nextLocation = trident.getLocation().add(trident.getVelocity());

        if (nextLocation.getBlockY() > -62) return;
        if (!nextLocation.getBlock().getType().isAir()) return;

        if (nextLocation.getBlockY() < -63) {
            nextLocation.setY(0);
            trident.teleport(nextLocation.clone().add(0, 1, 0));
            trident.setVelocity(new Vector(0, -1, 0));
        }

        nextLocation.getBlock().setType(Material.BARRIER);
        new RemoveBarrier(trident, nextLocation.getBlock()).runTaskTimer(Main.getInstance(), 1, 1);
        cancel();
    }

    private void rescue() {
        if (trident.getLocation().getY() >= WorldUtils.getWorldMinHeight(trident.getWorld()) - 20) return;
        try {
            damageDealtField.set(getHandleMethod.invoke(trident), true);
        } catch (Exception e) {
            e.printStackTrace();
            cancel();
        }
    }

    @Override
    public void run() {

        ticks++;
        if (ticks >= MAX_TICKS || trident == null || trident.isDead() || !trident.isValid() || trident.getVelocity().length() == 0) {
            cancel();
            return;
        }

        if (damageDealtField != null) {
            rescue();
        } else {
            legacyRescue();
        }

    }
}
