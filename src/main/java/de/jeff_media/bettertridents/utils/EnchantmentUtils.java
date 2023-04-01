package de.jeff_media.bettertridents.utils;

import de.jeff_media.bettertridents.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class EnchantmentUtils {

    public static int getLevelFromTrident(Player player, Enchantment enchantment) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType()!= Material.TRIDENT) {
            item = player.getInventory().getItemInOffHand();
        }
        if(item.getType()!=Material.TRIDENT) return 0;
        if (!item.hasItemMeta()) return 0;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasEnchant(enchantment)) {
            return meta.getEnchantLevel(enchantment);
        }
        return 0;
    }

    public static int getImpaling(Trident trident) {
        return trident.getPersistentDataContainer().getOrDefault(Main.IMPALING_TAG, PersistentDataType.INTEGER, 0);
    }

    public static void registerImpaling(Trident trident, int level) {
        if(level == 0) return;
        trident.getPersistentDataContainer().set(Main.IMPALING_TAG, PersistentDataType.INTEGER, level);
    }

    public static boolean hasLoyalty(Trident trident) {
        return trident.getItem().getEnchantments().containsKey(Enchantment.LOYALTY);
    }

    public static void registerLoyalty(Trident trident, int level) {
        if(level == 0) return;
        trident.getPersistentDataContainer().set(Main.LOYALTY_TAG, PersistentDataType.INTEGER, level);
    }

    public static boolean isOffhandThrown(Trident trident) {
        return trident.getPersistentDataContainer().has(Main.OFFHAND_TAG, PersistentDataType.BYTE);
    }

    public static boolean hasLoyalty(ItemStack item) {
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasEnchant(Enchantment.LOYALTY);
    }

}
