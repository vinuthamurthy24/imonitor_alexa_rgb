/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author sumit
 *
 */
public class HMDDeviceHandler extends AlertUpdateHelper {

	public Queue<KeyValuePair> updateHMDWarning(Queue<KeyValuePair> alerts)
		throws Exception {
			String xml = IMonitorUtil.convertQueueIntoXml(alerts);
			IMonitorUtil.sendToController("alertsFromImvgService", "updateHMDWarning", xml);
			Queue<KeyValuePair> queue = createResultQueue(alerts);
			return queue;
	}
	
	public Queue<KeyValuePair> updateHMDNormal(Queue<KeyValuePair> alerts)
			throws Exception {
				String xml = IMonitorUtil.convertQueueIntoXml(alerts);
				IMonitorUtil.sendToController("alertsFromImvgService", "updateHMDNormal", xml);
				Queue<KeyValuePair> queue = createResultQueue(alerts);
				return queue;
	}
	
	public Queue<KeyValuePair> updateHMDFailure(Queue<KeyValuePair> alerts)
			throws Exception {
				String xml = IMonitorUtil.convertQueueIntoXml(alerts);
				IMonitorUtil.sendToController("alertsFromImvgService", "updateHMDFailure", xml);
				Queue<KeyValuePair> queue = createResultQueue(alerts);
				return queue;
	}
	public Queue<KeyValuePair> updateHMDPowerInformation(Queue<KeyValuePair> alerts)
			throws Exception {
				String xml = IMonitorUtil.convertQueueIntoXml(alerts);
				IMonitorUtil.sendToController("alertsFromImvgService", "updateHMDPowerInformation", xml);
				Queue<KeyValuePair> queue = createResultQueue(alerts);
				return queue;
	}
	public Queue<KeyValuePair> updateHMDEnergyInformation(Queue<KeyValuePair> alerts)
			throws Exception {
				String xml = IMonitorUtil.convertQueueIntoXml(alerts);
				IMonitorUtil.sendToController("alertsFromImvgService", "updateHMDEnergyInformation", xml);
				Queue<KeyValuePair> queue = createResultQueue(alerts);
				return queue;
	}
	private Queue<KeyValuePair> createResultQueue(Queue<KeyValuePair> alerts) {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_ALERT_ACK));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.commandId(alerts, Constants.TRANSACTION_ID)));
		queue.add(new KeyValuePair(Constants.IMVG_ID,IMonitorUtil.commandId(alerts, Constants.IMVG_ID)));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,IMonitorUtil.commandId(alerts, Constants.DEVICE_ID)));
		return queue;
	}
}
