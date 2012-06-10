package se.myller.onduty.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import se.myller.onduty.Debug;
import se.myller.onduty.OnDuty;
import se.myller.onduty.Permissions;

/**
 *
 * @author Mylleranton
 */
public class CommandLIST {

    
    private CommandSender sender;
    private String[] args;
    public CommandLIST(CommandSender cs, String[] args) {
	this.sender = cs;
	this.args = args;
	onCommand();
    }
    
    private void onCommand() {
	if (Permissions.canList(sender)) {
	    Debug.log("Command 'list' by " + sender.getName());
	    if (args.length != 1) {
		sender.sendMessage(ChatColor.RED + "Wrong length of arguments!");
		return;
	    }
	    if(OnDuty.playersInDuty.isEmpty()) {
		sender.sendMessage(ChatColor.BLUE + "There is currently no one on duty.");
		return;
	    } else {
		 sender.sendMessage(ChatColor.BLUE + "----- Currently on duty  -----");
	    for (String player : OnDuty.playersInDuty.toArray(new String[OnDuty.playersInDuty.size()])) {
		sender.sendMessage(ChatColor.GOLD + player);
	    }
	    sender.sendMessage(ChatColor.BLUE + "------------------------------");
	    }
	}
    }
}
