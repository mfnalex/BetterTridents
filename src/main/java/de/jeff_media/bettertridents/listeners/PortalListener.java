package de.jeff_media.bettertridents.listeners;

import de.jeff_media.bettertridents.Main;
import de.jeff_media.bettertridents.config.Config;
import de.jeff_media.bettertridents.utils.EnchantmentUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

public class PortalListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTridentEnterPortal(EntityPortalEvent event) {
        if(!main.getConfig().getBoolean(Config.DISABLE_LOYALTY_PORTALS)) return;
        if(event.getEntityType() != EntityType.TRIDENT) return;
        Trident trident = (Trident) event.getEntity();
        if(EnchantmentUtils.getLoyalty(trident)) {
            main.debug("Prevented loyalty trident from travelling through portal");
            event.setCancelled(true);
        }
    }

}
