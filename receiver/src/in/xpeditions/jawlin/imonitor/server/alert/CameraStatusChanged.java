/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.Queue;

public class CameraStatusChanged extends AlertUpdateHelper {

	public Queue<KeyValuePair> updateCameraStatus(Queue<KeyValuePair> alerts)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateCameraStatus", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
}
