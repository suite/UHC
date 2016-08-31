package net.gr8bit.uhc.listeners;

import net.gr8bit.uhc.UHCMain;
import net.gr8bit.uhc.gamehandler.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Matthew on 8/26/2016.
 */
public class EntityDamage implements Listener {

    GamePlayer gamePlayer = new GamePlayer();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = (Player)e.getEntity();

            if(!UHCMain.getGamePlayer().getTakeFallDamage()) {
                e.setCancelled(true);
            }

        }
    }

}
