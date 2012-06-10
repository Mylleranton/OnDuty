package se.myller.onduty.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Mylleranton
 */
public class CommandHELP {
    
    
    private CommandSender s;
    public CommandHELP(CommandSender s) {
	this.s = s;
	onCommand();
    }
    public void onCommand() {
	s.sendMessage(ChatColor.BLUE + "-----------------------------");
	s.sendMessage(ChatColor.GRAY + "/duty - View this page");
	s.sendMessage(ChatColor.GRAY + "/duty on - Set you on duty");
	s.sendMessage(ChatColor.GRAY + "/duty off - Set you off duty");
	s.sendMessage(ChatColor.GRAY + "/duty list - View all players on duty");
	s.sendMessage(ChatColor.GRAY + "/duty check <player> - Check that players stats");
	s.sendMessage(ChatColor.GRAY + "/duty del|delete [-p/-all] <player> - Delete a players(-p) or all(-all) stats");
	s.sendMessage(ChatColor.GRAY + "/duty force -p <player> [on/off] - Force a player(-p) or all(-all) to have duty on/off");
	s.sendMessage(ChatColor.GRAY + "/duty force -all [on/off]");
	s.sendMessage(ChatColor.BLUE + "-----------------------------");
	
    }
}
