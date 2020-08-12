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
public class SensorDeviceHandler extends AlertUpdateHelper {

	public Queue<KeyValuePair> updateShockDetected(Queue<KeyValuePair> alerts)
		throws Exception {
			String xml = IMonitorUtil.convertQueueIntoXml(alerts);
			IMonitorUtil.sendToController("alertsFromImvgService", "updateShockDetected", xml);
			Queue<KeyValuePair> queue = createResultQueue(alerts);
			return queue;
	}
	
	public Queue<KeyValuePair> updateShockStopped(Queue<KeyValuePair> alerts)
			throws Exception {
				String xml = IMonitorUtil.convertQueueIntoXml(alerts);
				IMonitorUtil.sendToController("alertsFromImvgService", "updateShockStopped", xml);
				Queue<KeyValuePair> queue = createResultQueue(alerts);
				return queue;
	}
	
	public Queue<KeyValuePair> updateSmokeCleared(Queue<KeyValuePair> alerts)
			throws Exception {
				String xml = IMonitorUtil.convertQueueIntoXml(alerts);
				IMonitorUtil.sendToController("alertsFromImvgService", "updateSmokeCleared", xml);
				Queue<KeyValuePair> queue = createResultQueue(alerts);
				return queue;
	}
	
	public Queue<KeyValuePair> updateSmokeDetected(Queue<KeyValuePair> alerts)
			throws Exception {
				String xml = IMonitorUtil.convertQueueIntoXml(alerts);
				IMonitorUtil.sendToController("alertsFromImvgService", "updateSmokeDetected", xml);
				Queue<KeyValuePair> queue = createResultQueue(alerts);
				return queue;
	}
	
	public Queue<KeyValuePair> updateTemperatureChange(Queue<KeyValuePair> alerts)
			throws Exception{
				String xml = IMonitorUtil.convertQueueIntoXml(alerts);
				IMonitorUtil.sendToController("alertsFromImvgService", "updateTemperatureChange", xml);
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
	
	public Queue<KeyValuePair> updateLUXDeviceParam(Queue<KeyValuePair> alerts) throws Exception {
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateLUXDeviceAlert", xml);
		return queue;
	}
	
	public Queue<KeyValuePair> PmDevicealerthandle(Queue<KeyValuePair> alerts) throws Exception {
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updatePMDeviceAlert", xml);
		return queue;
	}
	
}
