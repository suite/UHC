package net.gr8bit.uhc.listeners;

import net.gr8bit.uhc.GameUtils;
import net.gr8bit.uhc.UHCMain;
import net.gr8bit.uhc.gamehandler.GameManager;
import net.gr8bit.uhc.gamehandler.GamePlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

/**
 * Created by Matthew on 8/25/2016.
 */
public class PlayerJoin implements Listener {


    public static Scoreboard board;


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        e.setJoinMessage(null);
        Player p = e.getPlayer();

        board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = null;
        if(board.getObjective("MainBoard") == null) {
            obj = board.registerNewObjective("MainBoard", "dummy");
        }

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "UHC");

        Score statustext = obj.getScore(ChatColor.RESET + "Status:");
        statustext.setScore(10);


        Team status = board.registerNewTeam("status");
        status.addEntry(ChatColor.GOLD.toString());
        status.setPrefix(ChatColor.YELLOW + "" + UHCMain.getGameManager().getGameState().toString());
        obj.getScore(ChatColor.GOLD.toString()).setScore(9);

        Score blankspace = obj.getScore(ChatColor.DARK_AQUA.toString());
        blankspace.setScore(8);

        Score countdowntext = obj.getScore("Game Start:");
        countdowntext.setScore(7);



        Team countDown = board.registerNewTeam("countDown");
        countDown.addEntry(ChatColor.RED.toString());
        countDown.setPrefix(ChatColor.YELLOW + "25 Secs");
        obj.getScore(ChatColor.RED.toString()).setScore(6);

        Score blankspace2 = obj.getScore(ChatColor.DARK_GRAY.toString());
        blankspace2.setScore(5);

        Score bordertext = obj.getScore("Border Size:");
        bordertext.setScore(4);

        int bordersize = UHCMain.getPlugin().getConfig().getInt("UHC.border-size");

        Team border = board.registerNewTeam("border");
        border.addEntry(ChatColor.DARK_PURPLE.toString());
        border.setPrefix(ChatColor.translateAlternateColorCodes('&', "&e"+ bordersize + " &7x &e"+bordersize+""));
        obj.getScore(ChatColor.DARK_PURPLE.toString()).setScore(3);

        Score blankspace3 = obj.getScore(ChatColor.DARK_RED.toString());
        blankspace3.setScore(2);

        Score nametext = obj.getScore("Name: ");
        nametext.setScore(1);

        Score name = obj.getScore(ChatColor.YELLOW + p.getName());
        name.setScore(0);







        p.setScoreboard(board);
        Location spawnLoc = new Location(p.getWorld(), 0, 125, 0);
        p.teleport(spawnLoc);
        p.getActivePotionEffects().clear();
        if(UHCMain.getGameManager().getGameState() == GameManager.GameState.Lobby) {


            p.sendMessage(ChatColor.translateAlternateColorCodes('&', UHCMain.getPlugin().getConfig().getString("UHC.join-message")));
            int neededplayers = UHCMain.getPlugin().getConfig().getInt("UHC.needed-players");
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aUHC&8] &e" + p.getName() + " &aHas joined the game (" + (UHCMain.getGamePlayer().getPlayers().size()+1) + "/" + neededplayers + ")"));


            UHCMain.getGamePlayer().addPlayer(e.getPlayer());

            if(UHCMain.getGamePlayer().players.size() == neededplayers) {
                UHCMain.getGameManager().setGameState(GameManager.GameState.Ingame);
             //   while (keeptimergoing) {
                    new BukkitRunnable() {
                        int startNumber = 25;

                        @Override
                        public void run() {
                            if (startNumber != -1) {
                                if (startNumber != 0) {
                                    for (Player onlineplayer : Bukkit.getOnlinePlayers()) {

                                        board.getTeam("countDown").setPrefix(ChatColor.YELLOW + String.valueOf(startNumber) + " Secs");
                                        onlineplayer.setScoreboard(board);


                                    }
                                    // Bukkit.broadcastMessage("timer: " + startNumber);
                                    startNumber--;
                                } else {


                                    board.getTeam("countDown").setPrefix(ChatColor.YELLOW + "10 Mins");
                                    startNumber--;
                                    for(Player gameplayer : UHCMain.getGamePlayer().getPlayers()) {
                                        gameplayer.setHealth(10.0);
                                        gameplayer.setFoodLevel(10);
                                    }
                                    UHCMain.getGamePlayer().setTakeFallDamage(false);
                                    for(Player gameplayer : UHCMain.getGamePlayer().getPlayers())
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(UHCMain.getPlugin(), new Runnable() {
                                        @Override
                                        public void run() {
                                            UHCMain.getGamePlayer().setTakeFallDamage(true);


                                        }
                                    }, 250);


                                    start10MinCountDown(board);

                                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aUHC&8] &aTeleporting players..."));

                                    UHCMain.getGameUtils().startBorderChange(board, p.getWorld());
                                    UHCMain.getGameUtils().setupWorldBorder(p.getWorld());




                                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                        board.getTeam("status").setPrefix(ChatColor.YELLOW + UHCMain.getGameManager().getGameState().toString());
                                        onlinePlayers.setScoreboard(board);
                                        UHCMain.getGameUtils().teleportUHC(onlinePlayers);
                                    }
                                }
                            }


                        }
                    }.runTaskTimer(UHCMain.getPlugin(), 0L, 20);

                }
           // }


        }











    }
    public void start10MinCountDown(Scoreboard board) {

        board.getTeam("countDown").setPrefix(ChatColor.YELLOW+ " XDDD");

    }


}
