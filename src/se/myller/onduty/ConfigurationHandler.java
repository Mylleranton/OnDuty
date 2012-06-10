package se.myller.onduty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Mylleranton
 */
public class ConfigurationHandler {

    
    private static File configFile = new File("plugins" + File.separator + "OnDuty" + File.separator + "config.yml");
    private static FileConfiguration config = new YamlConfiguration();
    private static boolean isConfigLoaded = false;
    private static boolean existsDir = false;
    private static boolean existsConf = false;
    private static OnDuty plugin;
    
    public ConfigurationHandler(OnDuty instance) {
	plugin = instance;
	createDir();
    }
    
    private static void createDir() {
	File dir = new File("plugins" + File.separator + "OnDuty" + File.separator + "");
	if (!dir.isDirectory()) {
	    Debug.log("Creating Directory");
	    dir.mkdirs();
	    createFile();
	    existsDir = true;
	}
	else {
	    createFile();
	    existsDir = true;
	}
    }
    
    private static void createFile() {
	 if (!configFile.isFile()) {
	     plugin.saveDefaultConfig();
	     loadFile();
	     existsConf = true;
	 }
	 else {
	     existsConf = true;
	 }
    }
    
    public static void loadFile() {
	try {
	    Debug.log("Loading Config");
	    config.load(configFile);    
	} 
	catch(FileNotFoundException e) {
	    OnDuty.log.log(Level.SEVERE, e.getMessage());
	}
	catch(IOException e) {
	    OnDuty.log.log(Level.SEVERE, e.getMessage());
	}
	catch(InvalidConfigurationException e) {
	    OnDuty.log.log(Level.SEVERE, e.getMessage());
	}
    }
    public static FileConfiguration getConfigFile() {
	if (isConfigLoaded && existsConf && existsDir) {
	    return config;
	} 
	else {
	    createDir();
	    loadFile();
	    return config;
	}
    }
    public static List getDisabledCommands() {
	return getConfigFile().getList("Disabled_Commands");
    }
}
