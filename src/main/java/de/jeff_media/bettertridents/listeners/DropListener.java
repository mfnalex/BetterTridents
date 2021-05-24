package de.jeff_media.bettertridents.listeners;

import de.jeff_media.bettertridents.Main;
import de.jeff_media.bettertridents.config.Config;
import de.jeff_media.bettertridents.utils.EnchantmentUtils;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class DropListener implements Listener {

    private final Main main = Main.getInstance();
    final Random random = new Random();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onKillDrowned(EntityDeathEvent event) {
        if (!main.getConfig().getBoolean(Config.DROP_BEDROCK_CHANCE)) return;
        if (!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
        if (damageEvent.getDamager().getType() != EntityType.PLAYER) return;
        Player lastDamager = (Player) damageEvent.getDamager();
        int fortune = EnchantmentUtils.getLevelFromTrident(lastDamager, Enchantment.LOOT_BONUS_MOBS);
        if (!(event.getEntity() instanceof Drowned)) return;

        Drowned drowned = (Drowned) event.getEntity();
        if (!drowned.getWorld().getGameRuleValue(GameRule.DO_MOB_LOOT)) return;
        if (drowned.getEquipment().getItemInMainHand().getType() != Material.TRIDENT) return;

        for (ItemStack drop : event.getDrops()) {
            if (drop.getType() == Material.TRIDENT) return;
        }

        main.debug("Using bedrock drop chance...");

        int chance = random.nextInt(100);
        main.debug("Drop chance: " + chance);
        if (chance > 25 + (4 * fortune)) return;
        ItemStack trident = new ItemStack(Material.TRIDENT);
        Damageable meta = (Damageable) trident.getItemMeta();
        meta.setDamage(random.nextInt(248) + 1);
        trident.setItemMeta((ItemMeta) meta);
        event.getDrops().add(trident);

    }


}
