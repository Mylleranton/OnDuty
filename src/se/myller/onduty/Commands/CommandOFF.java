package se.myller.onduty.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import se.myller.onduty.Debug;
import se.myller.onduty.FileHandler;
import se.myller.onduty.OnDuty;
import se.myller.onduty.Permissions;

/**
 *
 * @author Mylleranton
 */
public final class CommandOFF {

    private Player sender;
    private String[] args;
    public CommandOFF(Player sender, String[] args) {
	this.sender = sender;
	this.args = args;
	onCommand();
    }
    public CommandOFF(Player sender) {
	this.sender = sender;
	onCommand();
    }
    public void onCommand() {
	if (Permissions.canUse(sender)) {
	    Debug.log("Command 'off' by " + sender.getName());
	    if (args == null && sender != null) {
		toggleOff();
	    }
	    if (args.length != 1) {
		sender.sendMessage(ChatColor.RED + "Wrong length of arguments!");
	    } 
	    else {
		toggleOff();
	    } 
	}
    }
    private void toggleOff() {
	if(OnDuty.playersInDuty.contains(sender.getName())) {
	    FileHandler.setTimeInDuty(sender, FileHandler.getPlayTime(sender.getName()));
	    sender.sendMessage(ChatColor.RED + "You are now off duty!");
	    OnDuty.playersInDuty.remove(sender.getName());
	} 
	else {
	    sender.sendMessage(ChatColor.RED + "You are already off duty");
	}
	    
    }
}
