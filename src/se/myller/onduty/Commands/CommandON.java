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
public final class CommandON {

    private Player sender;
    private String[] args;
    public CommandON(Player sender, String[] args) {
	this.sender = sender;
	this.args = args;
	onCommand();
    }
    public CommandON(Player sender) {
	this.sender = sender;
	onCommand();
    }
    public void onCommand() {
	if (Permissions.canUse(sender)) {
	    Debug.log("Command 'on' by " + sender.getName());
	    if (args == null && sender != null) {
		toggleOn();
	    }
	    if (args.length != 1) {
		sender.sendMessage(ChatColor.RED + "Wrong length of arguments!");
		return;
	    } else {
		toggleOn();
	    } 
	}
    }
    private void toggleOn() {
	if(!OnDuty.playersInDuty.contains(sender.getName())) {
	    FileHandler.setLatestDuty(sender);
	    FileHandler.setTimesInDuty(sender);
	    FileHandler.setLatestTimeInDuty(sender);
	    sender.sendMessage(ChatColor.GREEN + "You are now on duty!");
	    OnDuty.playersInDuty.add(sender.getName());
	} 
	else {
	    sender.sendMessage(ChatColor.RED + "You are already on duty");
	}
    }
}
