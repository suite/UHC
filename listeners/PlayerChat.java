package net.gr8bit.uhc.listeners;

import jdk.Exported;
import net.gr8bit.uhc.UHCMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

/**
 * Created by Matthew on 8/26/2016.
 */
public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (UHCMain.getGameManager().getGameState().toString() != "Lobby" && !UHCMain.getGamePlayer().players.contains(player)) {

            event.setFormat("DEAD : " + player.getName() + " > " + event.getMessage());

        } else {
            switch (UHCMain.getGameManager().getGameState()) {
                case Lobby:
                break;
                case Ingame:
                    event.setFormat("INGAME : " + player.getName() + " > " + event.getMessage());
                    break;
                case Ended:
                    event.setFormat("ENDED : " + player.getName() + " > " + event.getMessage());
                    break;
                default:
                    event.setFormat("DEFAULT : " + player.getName() + " > " + event.getMessage());
                    break;


            }

        }
    }

}
