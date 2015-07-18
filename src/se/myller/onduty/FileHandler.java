package se.myller.onduty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Mylleranton
 */
public class FileHandler {

    static FileConfiguration playerFileConf = new YamlConfiguration();
    private static File playerFile;
    
    private OnDuty plugin;
    public FileHandler(OnDuty instance) {
	this.plugin = instance;
    }
    
    public boolean check(Player player) {
	Debug.log("Checking if " + player.getName() + " has a log");
	File logDir = new File("plugins" + File.separator + "OnDuty" + File.separator + "Logs");
	playerFile = new File("plugins" + File.separator + "OnDuty" + File.separator + "Logs" + File.separator, player.getName() + ".yml");
	
	if (!logDir.isDirectory()) {
	    Debug.log("Creating log-directory");
	    logDir.mkdirs();
	}
	if (!playerFile.isFile() || (playerFile.length() < 150)) {    
	    try {
		Debug.log("Creating new Log-file");
		playerFile.createNewFile();
		copy(plugin.getResource("Template.yml"), playerFile);
		try {
		    Debug.log("Loading new File");
		    playerFileConf.load(playerFile);
		    return true;
		} 
		catch (InvalidConfigurationException e) {
		    OnDuty.log.log(Level.SEVERE, e.getLocalizedMessage());
		    return false;
		}
	    } 
	    catch (IOException e) {
		OnDuty.log.log(Level.SEVERE, e.getLocalizedMessage());
		return false;
	    }
	}
	else {
	    return true;
	}
    }
    private void copy(InputStream in, File out) {
        if (in == null || out == null) {
	    OnDuty.log.log(Level.SEVERE, "Failed to copy defaults.");
	} 
	else {
	    try {
		Debug.log("Copying default layout from template.yml");
		OutputStream outS = new FileOutputStream(out);
		    byte[] buf = new byte[1024];
		    int len;
		    while ((len = in.read(buf)) > 0) {
			outS.write(buf, 0, len);
		    }
		outS.close();
		in.close();
	    } catch (Exception e) {
		OnDuty.log.log(Level.SEVERE, e.getLocalizedMessage());
	    }
	}
    }
    public static FileConfiguration loadFile(String playerName) {
	try {
	    Debug.log("Loading " + playerName + "s log-file");
	    playerFileConf.load("plugins" + File.separator + "OnDuty" + File.separator + "Logs" + File.separator + playerName + ".yml");    
	} 
	catch(FileNotFoundException e) {
	    OnDuty.log.log(Level.SEVERE, e.getLocalizedMessage());
	}
	catch(IOException e) {
	    OnDuty.log.log(Level.SEVERE, e.getLocalizedMessage());
	}
	catch(InvalidConfigurationException e) {
	    OnDuty.log.log(Level.SEVERE, e.getLocalizedMessage());
	}
	return playerFileConf;
    } 
    
    public static int getTimeInDuty(String player) {
	return loadFile(player).getInt("Main.Time_In_Duty");
    }
    
    public static int getTimesInDuty(String player) {
	return loadFile(player).getInt("Main.Times_In_Duty");
    }
    
    public static String getLatestTimeInDuty(String player) {
	return loadFile(player).getString("Main.Latest_Time_In_Duty");
    }
    
    public static String getLatestLogin(String player) {
	return loadFile(player).getString("Main.Latest_Login");
    }
    
    public static List getLog(String player) {
	return loadFile(player).getList("Log.Log");
    }
    
    public static Long getLatestDuty(String player) {
	return loadFile(player).getLong("Main.Latest_Duty");
    }
    public static void setTimeInDuty(Player player, int timeToAdd) {
	Debug.log("Setting " + player.getName() + "s total time in duty, adding " + timeToAdd);
	FileConfiguration playerConf = loadFile(player.getName());
	int timeInDuty = playerConf.getInt("Main.Time_In_Duty");
	int tot = timeToAdd + timeInDuty;
	playerConf.set("Main.Time_In_Duty", tot);
	save(player.getName(), playerConf);
    }
    public static void setTimesInDuty(Player player) {
	Debug.log("Incrementing " + player.getName() + "s total times in duty");
	FileConfiguration playerConf = loadFile(player.getName());
	int timesInDuty = playerConf.getInt("Main.Times_In_Duty");
	int tot = timesInDuty + 1;
	playerConf.set("Main.Times_In_Duty", tot);
	save(player.getName(), playerConf);
    }
    public static void setLatestTimeInDuty(Player player) {
	Debug.log("Setting " + player.getName() + "s latest time in duty to now");
	FileConfiguration playerConf = loadFile(player.getName());
	OnDuty.log.info("Latest time " + Calendar.getInstance().getTime().toString());
	playerConf.set("Main.Latest_Time_In_Duty", Calendar.getInstance().getTime().toString());
	save(player.getName(), playerConf);
    }
    public static void addToLog(Player player, String addToLog) {
	Debug.log("Adding entry to " + player.getName() + "s log");
	FileConfiguration playerConf = loadFile(player.getName());
	List<String> currentLogEntries = (List<String>) playerConf.getList("Log.Log");
	currentLogEntries.add(addToLog);
	playerConf.set("Log.Log", currentLogEntries);
	save(player.getName(), playerConf);
    }
    
    public static void setLatestLogin(Player player) {
	Debug.log("Setting " + player.getName() + "s latest login to now");
	FileConfiguration playerConf = loadFile(player.getName());
	playerFile = new File("plugins" + File.separator + "OnDuty" + File.separator + "Logs" + File.separator, player.getName() + ".yml");
	playerConf.set("Main.Latest_Login", Calendar.getInstance().getTime().toString());
	save(player.getName(), playerConf);
    }
    
    public static void setLatestDuty(Player player) {
	Debug.log("Setting " + player.getName() + "s latest time in duty to now");
	FileConfiguration playerConf = loadFile(player.getName());
	playerFile = new File("plugins" + File.separator + "OnDuty" + File.separator + "Logs" + File.separator, player.getName() + ".yml");
	playerConf.set("Main.Latest_Duty", Calendar.getInstance().getTimeInMillis());
	save(player.getName(), playerConf);
    }
    
    public static int getPlayTime(String player) {
	Debug.log("Getting " + player + "s time in duty");
	Long quit = Calendar.getInstance().getTimeInMillis();
	Long enabledDuty = getLatestDuty(player);
	Long timeInMillis = quit - enabledDuty;
	Long timeInSeconds = (timeInMillis / 1000);
	int timeInMinutes = (int) (timeInSeconds / 60);
	return timeInMinutes;
    }
    public static void save(String fileName, FileConfiguration playerConf) {
	File saveFile = new File("plugins" + File.separator + "OnDuty" + File.separator + "Logs" + File.separator, fileName + ".yml");
	try {
	    Debug.log("Saving " + fileName + ".yml");
	    playerConf.save(saveFile);
	}
	catch (IOException ex) {
	    OnDuty.log.log(Level.SEVERE, "5", ex);
	}
    }
    public static boolean hasStats(String player) {
	Debug.log("Checking if " + player + " has a log-file");
	File dir = new File("plugins" + File.separator + "OnDuty" + File.separator + "Logs");
	for (File f : dir.listFiles()) {
	    OnDuty.log.info(f.getName());
	    if (f.getName().equalsIgnoreCase(player + ".yml")) {
		return true;
	    }
	}
	return false;
    }
}
