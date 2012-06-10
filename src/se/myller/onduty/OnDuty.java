package se.myller.onduty;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Mylleranton
 */
public class OnDuty extends JavaPlugin {

    public static final Logger log = Logger.getLogger(OnDuty.class.getName());
    public static FileConfiguration config;
    public static ArrayList<String> playersInDuty = new ArrayList<String>();
    
    public OnDuty() {}
    
    @Override
    public void onEnable() {
	
	onEnable(false);
    }
    @Override
    public void onDisable() {
	onDisable(false);
    }
    public void onDisable(boolean reload) {
	
    }
    public void onEnable(boolean reload) {
	if (!reload) {
	    new ConfigurationHandler(this);
	    config = ConfigurationHandler.getConfigFile();
	    this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}
	else {
	     config = ConfigurationHandler.getConfigFile();
	}
	getCommand("duty").setExecutor(new CommandHandler(this));
	if (config.getBoolean("Main.Debug")) {
	    Debug.enableDebug();
	}
	Thread t = new Thread(new UpdateChecker(this));
	t.start();
    }
}
