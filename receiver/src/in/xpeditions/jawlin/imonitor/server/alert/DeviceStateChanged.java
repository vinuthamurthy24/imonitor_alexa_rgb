/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.Queue;

/**
 * @author Coladi
 *
 */
public class DeviceStateChanged extends AlertUpdateHelper {

	public Queue<KeyValuePair> updateDeviceState(Queue<KeyValuePair> alerts)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateDeviceStatus", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
}
