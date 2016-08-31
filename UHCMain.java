package net.gr8bit.uhc;

import net.gr8bit.uhc.commands.UHCCommands;
import net.gr8bit.uhc.gamehandler.GameManager;
import net.gr8bit.uhc.gamehandler.GamePlayer;
import net.gr8bit.uhc.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.messaging.PluginMessageListenerRegistration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Matthew on 8/25/2016.
 */
public class UHCMain extends JavaPlugin implements PluginMessageListener {

    private File configFile;
    private FileConfiguration config;
    private static Plugin plugin;
    private static GamePlayer gamePlayer = new GamePlayer();
    private static GameManager gameManager = new GameManager();
    private static GameUtils gameUtils = new GameUtils();




    public void onEnable() {
        registerEvents(this,
                new PlayerJoin(),
                new PlayerLeave(),
                new EntityDamage(),
                new PlayerChat(),
                new PlayerDeath());

        getCommand("uhc").setExecutor(new UHCCommands());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);


        loadConfig();

        plugin = this;

        WorldBorder worldBorder = Bukkit.getWorld("world-new").getWorldBorder();
        worldBorder.setCenter(0, 0);
        worldBorder.setSize(1000);

    }

    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    private void loadConfig() {

        if (!getDataFolder().exists()) {

            getDataFolder().mkdir();
            Bukkit.shutdown();
        }

        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        if (!config.contains("UHC.join-message")) {
            config.set("UHC.join-message", "&byou got pranked");
        }
        if (!config.contains("UHC.needed-players")) {
            config.set("UHC.needed-players", 2);
        }
        if (!config.contains("UHC.border-size")) {
            config.set("UHC.border-size", 500);
        }
        if (!config.contains("UHC.player-amount-left-to-win")) {
            config.set("UHC.player-amount-left-to-win", 1);
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();


        }

    }
    public static Plugin getPlugin() {
        return plugin;
    }


    public static GameManager getGameManager() {
        return gameManager;
    }

    public static GameUtils getGameUtils() {
        return gameUtils;
    }
    public static GamePlayer getGamePlayer() {
        return gamePlayer;


    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

    }
}

