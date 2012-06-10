package se.myller.onduty.Commands;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import se.myller.onduty.Debug;
import se.myller.onduty.FileHandler;
import se.myller.onduty.Permissions;

/**
 *
 * @author Mylleranton
 */
public class CommandCHECK {

    
    private CommandSender sender;
    private String[] args;
    public CommandCHECK(CommandSender cs, String[] args) {
	this.args = args;
	this.sender = cs;
	onCommand();
    }
    
    public void onCommand() {
	if (Permissions.canCheck(sender)) {
	    Debug.log("Command 'check' by " + sender.getName());
	    if (args.length != 2) {
		sender.sendMessage(ChatColor.RED + "Wrong length of arguments!");
		return;
	    }
	    try {
		if(FileHandler.hasStats(args[1])) {
		    onContinue(args[1]);
		} 
		else {
		    sender.sendMessage(ChatColor.RED + args[1] + " has no stats");
		}
	    }
	    catch(Exception e) {
		sender.sendMessage(ChatColor.RED + args[1] + " has no stats");
	    }
	}
    }
    public void onContinue(String p) {
	sender.sendMessage(ChatColor.BLUE + "----- " + p + "´s stats -----");
	sender.sendMessage(ChatColor.GOLD + "Latest Login: " + FileHandler.getLatestLogin(p));
	sender.sendMessage(ChatColor.GOLD + "Latest Time on Duty: " + FileHandler.getLatestTimeInDuty(p));
	sender.sendMessage(ChatColor.GOLD + "Times in Duty: " + FileHandler.getTimesInDuty(p));    
	sender.sendMessage(ChatColor.GOLD + "Time in Duty: " + FileHandler.getTimeInDuty(p));
	sender.sendMessage(ChatColor.BLUE + "---------------------------------------");
    }
}
