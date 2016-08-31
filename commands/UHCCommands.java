package net.gr8bit.uhc.commands;

import net.gr8bit.uhc.UHCMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Matthew on 8/27/2016.
 */
public class UHCCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player)commandSender;
        if(p instanceof Player && p.isOp()) {
            if(command.getName().equalsIgnoreCase("uhc")) {
                if(args.length == 0)
                    p.sendMessage("do /uhc help xD");
                else if (args[0].equalsIgnoreCase("help")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&7&m----&r &8[&aUHC Help&8] &7&m----\n" +
                                    "&8[&aUHC&8] &e/uhc status &aTo view game status"));
                } else if (args[0].equalsIgnoreCase("status")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&8[&aUHC&8] &aStatus &7> &e" + UHCMain.getGameManager().getGameState().toString()));

                }
            }

        }



        return true;
    }
}
