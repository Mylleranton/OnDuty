package se.myller.onduty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Mylleranton
 */
public class UpdateChecker implements Runnable {

    private OnDuty m;
    public UpdateChecker(OnDuty ins) {
	this.m = ins;
    }
    public static boolean newVersionAvailable = false;
    public static String newVersion;
    
    @Override
    public void run() {
	try {
	    final String address = "https://raw.github.com/Mylleranton/OnDuty/master/src/Version.txt";
	    final URL url = new URL(address.replace(" ", "%20"));
	    final URLConnection conn = url.openConnection();
	    conn.setConnectTimeout(8000);
	    conn.setReadTimeout(15000);
	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String newestVersion = br.readLine();
	    if (newestVersion != null) {
		if (!m.getDescription().getVersion().equals(newestVersion)) {
		    newVersionAvailable = true;
		    newVersion = newestVersion;
		    OnDuty.log.info("New Version available: " + newestVersion + "! Check: ");
		    OnDuty.log.info("http://dev.bukkit.org/server-mods/onduty");
		}
	    }
	    br.close();
	    conn.getInputStream().close();    
	} catch(Exception e) {
	    OnDuty.log.info("UpdateCheck failed.");
	}
    }
}
