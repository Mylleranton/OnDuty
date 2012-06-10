package se.myller.onduty;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Mylleranton
 */
public class Permissions {

    public Permissions() {}
    
    public static boolean canUse(Player player) {
	return player.hasPermission("duty.use");
    }
    public static boolean canCheck(CommandSender sender) {
	return sender.hasPermission("duty.check");
    }
    public static boolean canList(CommandSender sender) {
	return sender.hasPermission("duty.list");
    }
    public static boolean canDelPlayer(CommandSender sender) {
	return sender.hasPermission("duty.delp");
    }
    public static boolean canDelAll(CommandSender sender) {
	return sender.hasPermission("duty.delall");
    }
    public static boolean canForce(CommandSender sender) {
	return sender.hasPermission("duty.force");
    }
}
