package se.myller.onduty.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import se.myller.onduty.Debug;
import se.myller.onduty.FileHandler;
import se.myller.onduty.OnDuty;
import se.myller.onduty.Permissions;

/**
 *
 * @author Mylleranton
 */
public class CommandFORCE {

    
    private CommandSender sender;
    private String[] args;
    private Player player;
    public CommandFORCE(CommandSender cs, String[] args) {
	this.args = args;
	this.sender = cs;
	onCommand();
    }
    private void onCommand() {
	if (Permissions.canForce(sender)) {
	    Debug.log("Command 'force' by " + sender.getName());
	    if (args.length != 4 && args[1].equalsIgnoreCase("-p")) {
		sender.sendMessage(ChatColor.RED + "Wrong length of arguments!");
	    }
	    else if (args.length != 3 && args[1].equalsIgnoreCase("-all")) {
		sender.sendMessage(ChatColor.RED + "Wrong length of arguments!");
	    }
	    else if (args[1].equalsIgnoreCase("-p")) {
		try {
		    player = Bukkit.getPlayer(args[2]);
		    if (!player.isOnline()) {
			sender.sendMessage(ChatColor.RED + "Target must be online!");
			return;
		    }
		}
		catch(Exception e) {
		    sender.sendMessage(ChatColor.RED + args[2] + " is not a player");
		    return;
		}
		if(args[3].equalsIgnoreCase("on")) {
		    if (FileHandler.hasStats(player.getName())) {
			if (!OnDuty.playersInDuty.contains(player.getName())) {
			    OnDuty.playersInDuty.add(player.getName());
			    new CommandON(player);
			    sender.sendMessage(ChatColor.GREEN + "Set " + player.getName() + " on duty!");
			}
			else {
			    sender.sendMessage(ChatColor.RED + player.getName() + " is already on duty!");
			}
		    }
		}
	        else if (args[3].equalsIgnoreCase("off")) {
		    if (OnDuty.playersInDuty.contains(player.getName())) {
		        OnDuty.playersInDuty.remove(player.getName());
			new CommandOFF(player);
		        sender.sendMessage(ChatColor.GREEN + "Set " + player.getName() + " off duty!");
		    } 
		    else {
		    	sender.sendMessage(ChatColor.RED + player.getName() + " is already off duty!");
		    }
		}
	    }
	    // All Players
	    else if (args[1].equalsIgnoreCase("-all")) {
		for(Player p : Bukkit.getOnlinePlayers()) {
		    if (Permissions.canUse(p)) {
			try {
			    player = Bukkit.getPlayer(p.getName());
			}
		        catch(Exception e) {
			    sender.sendMessage(ChatColor.RED + p.getName() + " is not a player");
			    return;
			}
		        if(args[2].equalsIgnoreCase("on")) {
			    if (!OnDuty.playersInDuty.contains(p.getName())) {
			        OnDuty.playersInDuty.add(p.getName());
				new CommandON(p);
			        sender.sendMessage(ChatColor.GREEN + "Set " + p.getName() + " on duty!");
			    }
			    else {
			        sender.sendMessage(ChatColor.RED + p.getName() + " is already on duty!");
			    }
		        }
		        else if (args[2].equalsIgnoreCase("off")) {
			    if (OnDuty.playersInDuty.contains(p.getName())) {
			        OnDuty.playersInDuty.remove(p.getName());
				new CommandOFF(p);
			        sender.sendMessage(ChatColor.GREEN + "Set " + p.getName() + " off duty!");
			    } 
			    else {
			        sender.sendMessage(ChatColor.RED + p.getName() + " is already off duty!");
			    }
			}
		    }
		}	
	    }
	    else {
		sender.sendMessage(ChatColor.RED + "Error in syntax, try again!");
	    }
	}
    }
}
