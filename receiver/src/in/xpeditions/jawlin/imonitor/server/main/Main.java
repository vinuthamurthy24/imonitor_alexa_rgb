/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.main;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.factory.ServerFactory;
import in.xpeditions.jawlin.imonitor.server.intf.Server;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * @author Coladi
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Get the server object.
		Server server = ServerFactory.getServerObject();

		String argument = "start";
		if(args.length > 0){
			argument = args[0];
		}
		
		if(argument.equalsIgnoreCase("stop")){
//			Shut down the server.
			LogUtil.info("Issuing command to shutdown the system.");
			try {
				server.stop();
			} catch (FileNotFoundException e) {
				LogUtil.error("Could not stop the server " + e.getMessage());
			} catch (IOException e) {
				LogUtil.error("Could not stop the server " + e.getMessage());
			}
			return;
		}
//		Loading events.
		EventLoader eventLoader = new EventLoader();
		eventLoader.loadAllEvents();
		
//		loading alerts
		AlertLoader alertLoader = new AlertLoader();
		alertLoader.loadAllAlerts();

//		loading MaintenanceEcents
		MaintenanceEvents maintenanceEvent=new MaintenanceEvents();
		maintenanceEvent.loadAllMaintenanceEvents();
		
		try {
			server.start();
		} catch (FileNotFoundException e) {
			LogUtil.error("Error when starting the server. " + e.getMessage());
		} catch (IOException e) {
			LogUtil.error("Error when starting the server. " + e.getMessage());
		}
	}

}
