package net.gr8bit.uhc.listeners;

import net.gr8bit.uhc.UHCMain;
import net.gr8bit.uhc.gamehandler.GameManager;
import net.gr8bit.uhc.gamehandler.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Created by Matthew on 8/26/2016.
 */
public class PlayerLeave implements Listener {



    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        int playersneededtolose = UHCMain.getPlugin().getConfig().getInt("UHC.player-amount-left-to-win");
        e.setQuitMessage(null);
        if(UHCMain.getGamePlayer().players.contains(player)) {
            UHCMain.getGamePlayer().players.remove(player);

            if(UHCMain.getGamePlayer().getPlayers().size() <= playersneededtolose) {
                UHCMain.getGameManager().setGameState(GameManager.GameState.Ended);
                for(Player playerwinner : UHCMain.getGamePlayer().getPlayers()) {
                    for (int i = 0; i < 6; i++) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aUHC&8] &aPlayer &e" + playerwinner.getName() + " &ahas one UHC!"));
                    }
                }


                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    PlayerJoin.board.getTeam("status").setPrefix(ChatColor.YELLOW + UHCMain.getGameManager().getGameState().toString());
                    onlinePlayers.setScoreboard(PlayerJoin.board);
                }

            }
        }

        if(UHCMain.getGameManager().getGameState().toString() == "Lobby") {
            int neededplayers = UHCMain.getPlugin().getConfig().getInt("UHC.needed-players");
            if(UHCMain.getGamePlayer().getPlayers().contains(player)) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(UHCMain.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aUHC&8] &e" + player.getName() + " &aHas left the game (" + Bukkit.getOnlinePlayers().size() + "/" + neededplayers + ")"));

                    }
                }, 10);
            }

        } else {
            e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aUHC&8] &e" + player.getName() + " &ahas left the game! What a coward!"));

        }

    }
}
