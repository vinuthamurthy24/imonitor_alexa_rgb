/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Coladi
 *
 */
public class DeviceAlert extends AlertUpdateHelper {
	
	public Queue<KeyValuePair> updateUpAlert(Queue<KeyValuePair> alerts)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateDeviceUpAlert", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> updateDownAlert(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateDeviceDownAlert", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> updateRemoveAlert(Queue<KeyValuePair> alerts)
	throws Exception {
	
		
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
	
		
		String serviceName = "alertsFromImvgService";
		String methodName = "removeDeviceAlert";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
	/**
	 * @author naveen
	 * @param events java.util.Queue
	 * @return queue java.util.Queue
	 * @throws Exception
	 */
	public Queue<KeyValuePair> updateSyncStatus(Queue<KeyValuePair> events) throws Exception {
		//1. Update the device status
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "alertsFromImvgService";
		String methodName = "updateSyncDeviceStatus";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_ALERT_ACK));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil.commandId(events, Constants.TRANSACTION_ID)));
		queue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil.commandId(
				events, Constants.IMVG_ID)));
		queue.add(new KeyValuePair(Constants.DEVICE_ID, IMonitorUtil.commandId(
				events, Constants.IMVG_ID)));
		return queue;
		
	}

	
}
