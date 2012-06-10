package se.myller.onduty;

import java.io.BufferedReader;
import java.io.InputStream;
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
    private String version = m.getDescription().getVersion();
    public static boolean newVersionAvailable = false;
    
    @Override
    public void run() {
	try {
	    final String address = "";
	    final URL url = new URL(address.replace(" ", "%20"));
	    final URLConnection conn = url.openConnection();
	    conn.setConnectTimeout(8000);
	    conn.setReadTimeout(15000);
	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String newestVersion = br.readLine();
	    if (newestVersion != null) {
		if (!version.equals(newestVersion)) {
		    newVersionAvailable = true;
		    OnDuty.log.info("New Version available! Check: ");
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
