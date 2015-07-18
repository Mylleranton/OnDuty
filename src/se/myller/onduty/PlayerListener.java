package se.myller.onduty;

import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Mylleranton
 */
public class PlayerListener implements Listener {

    private OnDuty plugin;
    
    public PlayerListener(OnDuty instance) {
	this.plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
	if (Permissions.canUse(e.getPlayer())) {
	    final Player p = e.getPlayer();
	    if (new FileHandler(this.plugin).check(p)) {
		FileHandler.setLatestLogin(p);
	    }
	    if (UpdateChecker.newVersionAvailable) {
		e.getPlayer().sendMessage(ChatColor.AQUA + "New version available for OnDuty: " + UpdateChecker.newVersion);
		e.getPlayer().sendMessage(ChatColor.AQUA + "Please check: http://dev.bukkit.org/server-mods/onduty");
	    }
	}
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
	if (Permissions.canUse(e.getPlayer())) {
	    if (OnDuty.playersInDuty.contains(e.getPlayer().getName())) {
		OnDuty.playersInDuty.remove(e.getPlayer().getName());
	    }
	    FileHandler.setTimeInDuty(e.getPlayer(), FileHandler.getPlayTime(e.getPlayer().getName()));
	}
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
	OnDuty.log.info(e.getMessage());
	    if (ConfigurationHandler.getDisabledCommands().contains(e.getMessage()) && !OnDuty.playersInDuty.contains(e.getPlayer().getName())) {
		e.setCancelled(true);
		FileHandler.addToLog(e.getPlayer(), "Tried to use " + e.getMessage() + " off duty. " + Calendar.getInstance().getTime());
		e.getPlayer().sendMessage(ChatColor.RED + "You have to be on duty to perform that command!");
	    }
	    else {
		FileHandler.addToLog(e.getPlayer(), "'Used " + e.getMessage() + " " +  Calendar.getInstance().getTime() + "'");
	    }
    }
}
