package se.myller.onduty.Commands;

import java.io.File;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import se.myller.onduty.Debug;
import se.myller.onduty.FileHandler;
import se.myller.onduty.OnDuty;
import se.myller.onduty.Permissions;

/**
 *
 * @author Mylleranton
 */
public class CommandDELETE {

    private CommandSender sender;
    private final String[] args;
    private Player player;
    public CommandDELETE(CommandSender cs, String[] args) {
	this.sender = cs;
	this.args = args;
	onCommand();
    }
    private void onCommand() {
	if (Permissions.canDelPlayer(sender) || Permissions.canDelAll(sender)) {
	    Debug.log("Command 'delete' by " + sender.getName());
	    if (args.length != 3 && args[1].equalsIgnoreCase("-p")) {
		sender.sendMessage(ChatColor.RED + "Wrong length of arguments!");
		return;
	    }
	    else if (args.length != 2 && args[1].equalsIgnoreCase("-all")) {
		sender.sendMessage(ChatColor.RED + "Wrong length of arguments!");
	    }
	    else if (!args[1].startsWith("-")) {
		sender.sendMessage(ChatColor.RED + "Wrong in argument 2, " + args[1]);
	    }
	    else if (args[1].equalsIgnoreCase("-p") && Permissions.canDelPlayer(sender)) {
		try {
		    if (FileHandler.hasStats(args[2])) {
			OnDuty.log.info("1");
			FileConfiguration conf = FileHandler.loadFile(args[2]);
			OnDuty.log.info("2");
			Set<String> allValues = conf.getKeys(true);
			for (String path : allValues) {
			    if (path.contains(".")) {
				conf.set(path, null);
			    }
			}
			FileHandler.save(args[2], conf);
			sender.sendMessage(ChatColor.GREEN + "Deleted " + args[2] + "s log-records.");
		    } 
		    else {
			 sender.sendMessage(ChatColor.RED + args[2] + " has no stats");
		    }
		    
		}
		catch(Exception e) {
		    sender.sendMessage(ChatColor.RED + args[2] + " has no stats");
		    return;
		}
	    }
	    else if (args[1].equalsIgnoreCase("-all") && Permissions.canDelAll(sender)) {
		File dir = new File("plugins" + File.separator + "OnDuty" + File.separator + "Logs");
		File[] files = dir.listFiles();
		for(File f : files) {
		    FileConfiguration conf = FileHandler.loadFile(f.getName().replace(".yml", ""));
		    Set<String> allValues = conf.getKeys(true);
		    for (String path : allValues) {
			if (path.contains(".")) {
			    conf.set(path, null);
			}
		    }
		    FileHandler.save(f.getName().replace(".yml", ""), conf);
		    sender.sendMessage(ChatColor.GREEN + "Deleted " + f.getName().replace(".yml", "") + "s log-records.");
		}
	    }
	    else {
		sender.sendMessage(ChatColor.RED + "Error in syntax, try again!");
	    }
	}
    }
}
