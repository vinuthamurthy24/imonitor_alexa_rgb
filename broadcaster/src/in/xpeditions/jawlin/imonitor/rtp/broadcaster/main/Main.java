/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.server.RTPBroadCaster;

/**
 * @author Coladi
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RTPBroadCaster rtpBroadCaster = new RTPBroadCaster();

		String argument = "start";
		if(args.length > 0){
			argument = args[0];
		}
		
		if(argument.equalsIgnoreCase("stop")){
//			Shut down the server.
			LogUtil.info("Issuing command to shutdown the system.");
			try {
				rtpBroadCaster.stop();
			} catch (FileNotFoundException e) {
				LogUtil.error("Could not stop the server " + e.getMessage());
			} catch (IOException e) {
				LogUtil.error("Could not stop the server " + e.getMessage());
			}
			return;
		}
		
		Thread t = new Thread(rtpBroadCaster);
		t.start();
	}

}
