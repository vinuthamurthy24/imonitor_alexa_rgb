/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author Coladi
 *
 */
public class BroadCasterActionMap{

	private static Map<String, UpdatorModel<?>> rtspUrlIdDeviceIdMap = new HashMap<String, UpdatorModel<?>>();
	
	public static void clear() {
		BroadCasterActionMap.rtspUrlIdDeviceIdMap.clear();
	}

	public static boolean containsKey(Object key) {
		return BroadCasterActionMap.rtspUrlIdDeviceIdMap.containsKey(key);
	}

	public static boolean containsValue(Object value) {
		return BroadCasterActionMap.rtspUrlIdDeviceIdMap.containsValue(value);
	}

	public static Set<Entry<String, UpdatorModel<?>>> entrySet() {
		return BroadCasterActionMap.rtspUrlIdDeviceIdMap.entrySet();
	}

	public static UpdatorModel<?> get(Object key) {
		return BroadCasterActionMap.rtspUrlIdDeviceIdMap.get(key);
	}

	public static boolean isEmpty() {
		return BroadCasterActionMap.rtspUrlIdDeviceIdMap.isEmpty();
	}

	public static Set<String> keySet() {
		return BroadCasterActionMap.rtspUrlIdDeviceIdMap.keySet();
	}

	public static UpdatorModel<?> put(String key, UpdatorModel<?> value) {
		return BroadCasterActionMap.rtspUrlIdDeviceIdMap.put(key, value);
	}

	public static void putAll(Map<? extends String, ? extends UpdatorModel<?>> map) {
		BroadCasterActionMap.rtspUrlIdDeviceIdMap.putAll(map);
	}

	public static UpdatorModel<?> remove(Object key) {
		return BroadCasterActionMap.rtspUrlIdDeviceIdMap.remove(key);
	}

	public static int size() {
		return BroadCasterActionMap.rtspUrlIdDeviceIdMap.size();
	}

	public static Collection<UpdatorModel<?>> values() {
		return BroadCasterActionMap.rtspUrlIdDeviceIdMap.values();
	}

}
