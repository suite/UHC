package net.gr8bit.uhc.listeners;

import net.gr8bit.uhc.UHCMain;
import net.gr8bit.uhc.gamehandler.GameManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

/**
 * Created by Matthew on 8/26/2016.
 */
public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        int playersneededtolose = UHCMain.getPlugin().getConfig().getInt("UHC.player-amount-left-to-win");
        if(UHCMain.getGamePlayer().players.contains(player) && UHCMain.getGameManager().getGameState().toString() != "Lobby") {
            if(player.getKiller() != null)
                e.setDeathMessage(ChatColor.translateAlternateColorCodes('&',"&8[&aUHC&8] &e" + player.getKiller().getName() + " &ahas utterly slaughtered &e" + player.getName() + "&a!"));
            else
                e.setDeathMessage(ChatColor.translateAlternateColorCodes('&',"&8[&aUHC&8] " + player.getName() +" &aHas died!!"));

            UHCMain.getGamePlayer().getPlayers().remove(player);


            if(UHCMain.getGamePlayer().getPlayers().size() <= playersneededtolose) {
                UHCMain.getGameManager().setGameState(GameManager.GameState.Ended);
                for(Player playerwinner : UHCMain.getGamePlayer().getPlayers()) {
                    for (int i = 0; i < 6; i++) {
                        Bukkit.broadcastMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&8[&aUHC&8] &aPlayer &e" + playerwinner.getName() + " &ahas one UHC!"));
                    }

                }

                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    PlayerJoin.board.getTeam("status").setPrefix(org.bukkit.ChatColor.YELLOW + UHCMain.getGameManager().getGameState().toString());
                    onlinePlayers.setScoreboard(PlayerJoin.board);

                }
            }

        }
    }

}
