/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.communicator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Coladi
 *
 */
public class ImonitorControllerCommunicator {

	private static Map<String, String> map = new HashMap<String, String>();

	public static void put(String key, String value) {
		ImonitorControllerCommunicator.map.put(key, value);
	}

	public static String get(String key) {
		return ImonitorControllerCommunicator.map.get(key);
	}


}
