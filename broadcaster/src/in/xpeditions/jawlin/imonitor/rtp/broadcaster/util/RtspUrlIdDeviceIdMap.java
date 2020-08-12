/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Coladi
 *
 */
public class RtspUrlIdDeviceIdMap{

	private static Map<String, String> rtspUrlIdDeviceIdMap = new HashMap<String, String>();
	
	public static void clear() {
		RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.clear();
	}

	public static boolean containsKey(Object key) {
		return RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.containsKey(key);
	}

	public static boolean containsValue(Object value) {
		return RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.containsValue(value);
	}

	public static Set<java.util.Map.Entry<String, String>> entrySet() {
		return RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.entrySet();
	}

	public static String get(Object key) {
		return RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.get(key);
	}

	public static boolean isEmpty() {
		return RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.isEmpty();
	}

	public static Set<String> keySet() {
		return RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.keySet();
	}

	public static String put(String key, String value) {
		return RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.put(key, value);
	}

	public static void putAll(Map<? extends String, ? extends String> map) {
		RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.putAll(map);
	}

	public static String remove(Object key) {
		return RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.remove(key);
	}

	public static int size() {
		return RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.size();
	}

	public static Collection<String> values() {
		return RtspUrlIdDeviceIdMap.rtspUrlIdDeviceIdMap.values();
	}

}
