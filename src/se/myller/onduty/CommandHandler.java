package se.myller.onduty;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import se.myller.onduty.Commands.*;

/**
 *
 * @author Mylleranton
 */
public class CommandHandler implements CommandExecutor {

    private OnDuty plugin;
    public CommandHandler(OnDuty instance) {
	this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args) {
	String cmdName = cmd.getName();
	
	    if (cmdName.equalsIgnoreCase("duty")) {
		Debug.log("Command Recived");
	    /* ------ */
		/* ------ */
		if (args.length == 0) {
		    return false;
		}
		else if (args[0].equalsIgnoreCase("help")) {
		    new CommandHELP(cs);
		    return true;
		}
		else if (args[0].equalsIgnoreCase("on")) {
		    if (cs instanceof Player) { 
			new CommandON((Player) cs, args); 
			return true;
		    } else {
			cs.sendMessage(ChatColor.RED + "You must a player to go on duty!");
			return true;
		    }
		}
		else if (args[0].equalsIgnoreCase("off")) {		
		    if (cs instanceof Player) { 
			new CommandOFF((Player) cs, args); 
			return true;
		    } else {
			cs.sendMessage(ChatColor.RED + "You must a player to go off duty!");
			return true;
		    }
		}
		else if (args[0].equalsIgnoreCase("list") && args.length == 1) {
		    new CommandLIST(cs, args);
		    return true;
		}
		else if (args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("delete") && args.length >= 2) {
		    new CommandDELETE(cs, args);
		    return true;
		} 
		else if (args[0].equalsIgnoreCase("check") && args.length == 2) {
		    new CommandCHECK(cs, args);
		    return true;
		}
		else if (args[0].equalsIgnoreCase("force") && args.length >= 3) {
		    new CommandFORCE(cs, args);
		    return true;
		}
		return false;
	}
	return false;
    }
}
