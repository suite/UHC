package net.gr8bit.uhc.gamehandler;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Matthew on 8/25/2016.
 */
public class GamePlayer {

    public Boolean getTakeFallDamage() {
        return takeFallDamage;
    }

    public void setTakeFallDamage(Boolean takeFallDamage) {
        this.takeFallDamage = takeFallDamage;
    }

    public Boolean takeFallDamage = false;

    public Map<Player, Boolean> alive;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> players = new ArrayList<Player>();




    public void addPlayer(Player p) {
        players.add(p);
    }
}
