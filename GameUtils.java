package net.gr8bit.uhc;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Created by Matthew on 8/26/2016.
 */
public class GameUtils {



    public void teleportUHC(Player p) {

        p.getInventory().clear();


        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, Integer.MAX_VALUE));


        int bordersize = UHCMain.getPlugin().getConfig().getInt("UHC.border-size");
        int randx = (int)(Math.random() * bordersize);
        int randz = (int)(Math.random() * bordersize);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aUHC&8] &aYou were spawned at &ex: " + randx + "&7, &ez: " + randz + "&a!"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aUHC&8] &aWith a border size of " + bordersize));
        Location randLoc = new Location(p.getWorld(), randx - ((bordersize/2)-1), 150, randz - ((bordersize/2)-1));



        Bukkit.getScheduler().scheduleSyncDelayedTask(UHCMain.getPlugin(), new Runnable() {
            @Override
            public void run() {
                p.teleport(randLoc);
            }
        }, 10);



    }

    public void clearWeather(World w) {
        w.setTime(100);
        //set day
        //stop raining
    }

    public void setupWorldBorder(World w) {
        int bordersize = UHCMain.getPlugin().getConfig().getInt("UHC.border-size");
        WorldBorder wb = w.getWorldBorder();
        wb.setCenter(0, 0);

        wb.setSize(bordersize);

        wb.setDamageAmount(4.0);

        wb.setSize(50, 600);


    }


    public void startBorderChange(Scoreboard board, World world) {

        new BukkitRunnable() {
            @Override
            public void run() {
                WorldBorder worldBorder = world.getWorldBorder();
                board.getTeam("border").setPrefix("§e"+ (int)worldBorder.getSize() + " §7x §e"+(int)worldBorder.getSize()+"");
            }
        }.runTaskTimer(UHCMain.getPlugin(), 0L, 20);


    }

    public void backToLobby(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);

        Bukkit.getScheduler().scheduleSyncDelayedTask(UHCMain.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for(Player onlineplayer : Bukkit.getOnlinePlayers()) {
                    ByteArrayDataOutput output = ByteStreams.newDataOutput();
                    output.writeUTF("Connect");
                    output.writeUTF("lobby");

                    onlineplayer.sendPluginMessage(UHCMain.getPlugin(), "BungeeCord", output.toByteArray());

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
                }
            }
        }, 50);

    }

}
