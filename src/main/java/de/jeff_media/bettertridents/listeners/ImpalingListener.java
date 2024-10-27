package de.jeff_media.bettertridents.listeners;

import de.jeff_media.bettertridents.Main;
import de.jeff_media.bettertridents.config.Config;
import de.jeff_media.bettertridents.utils.EnchantmentUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class ImpalingListener implements Listener {

    private final Main main = Main.getInstance();

    private final Random random = new Random();

    private static boolean isAquatic(EntityType type) {
        switch (type) {
            case DOLPHIN:
            case GUARDIAN:
            case ELDER_GUARDIAN:
            case SQUID:
            case TURTLE:
            case COD:
            case SALMON:
            case PUFFERFISH:
            case TROPICAL_FISH:
                return true;
            default:
                return false;
        }
    }

    private static boolean isInRain(Entity entity) {
        World world = entity.getWorld();
        if (!world.hasStorm()) return false;
        Location min = entity.getBoundingBox().getMin().toLocation(world);
        Location max = entity.getBoundingBox().getMax().toLocation(world);
        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                Block highest = world.getHighestBlockAt(x, z);
                if (highest == null || highest.getType().isAir()) return true;
                if (highest.getY() < entity.getLocation().getY()) return true;
            }
        }
        return false;
    }

    private static boolean isInWater(Entity entity) {
        World world = entity.getWorld();
        Location min = entity.getBoundingBox().getMin().toLocation(entity.getWorld());
        Location max = entity.getBoundingBox().getMax().toLocation(entity.getWorld());
        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    if (world.getBlockAt(x, y, z).getType() == Material.WATER) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void displayEnchantedHit(Entity entity) {
        for (int i = 0; i < 16 * 3; i++) {
            double d = (this.random.nextFloat() * 2.0F - 1.0F);
            double e = (this.random.nextFloat() * 2.0F - 1.0F);
            double f = (this.random.nextFloat() * 2.0F - 1.0F);
            if (Math.sqrt(d) + Math.sqrt(e) + Math.sqrt(f) <= 1.0D) {
                Location loc = entity.getLocation().clone();
                loc.add((entity.getWidth() * (d / 4.0D)),
                        (entity.getHeight() * (0.5D + e / 4.0D)),
                        (entity.getWidth() * (f / 4.0D)));
                loc.getWorld().spawnParticle(Particle.CRIT, loc, 0, d, e + 0.2D, f);
            }
        }
    }



    @EventHandler(ignoreCancelled = true)
    private void onHitByImpaling(EntityDamageByEntityEvent event) {
        if (!main.getConfig().getBoolean(Config.BEDROCK_IMPALING)) return;

        Entity entity = event.getEntity();
        int impaling = 0;
        if (event.getDamager().getType() == EntityType.TRIDENT) {
            impaling = EnchantmentUtils.getImpaling((Trident) event.getDamager());
        } else if (event.getDamager().getType() == EntityType.PLAYER) {
            impaling = ((Player) event.getDamager()).getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.IMPALING);
        }

        if (impaling > 0) {
            if (!isAquatic(entity.getType()) && (isInRain(entity) || isInWater(entity))) {
                event.setDamage(event.getDamage() + (2.5 * impaling));
                main.debug("Adjusting Impaling damage: adding " + 2.5*impaling + " damage");
                displayEnchantedHit(entity);
            }
        }
    }

}
