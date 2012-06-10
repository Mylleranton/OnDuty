package se.myller.onduty;

import java.util.logging.Logger;
import org.bukkit.Bukkit;

/**
 *
 * @author Mylleranton
 */
public class Debug {
    
    
    private final Logger log;
    private static Debug instance;
    
    public Debug() {
	log = Logger.getLogger("OnDuty.Debug");
    }
    public static void enableDebug() {
        Bukkit.getLogger().info("Debug enabled. Disable in config.yml");
        Debug.instance = new Debug();
    }

    public static void log(String message) {
        if (Debug.instance != null) {
            Debug.instance.log.info("<OD DEBUG> " + message);
        }
    }

    public static void disableDebug() {
        Debug.instance = null;
    }
}
