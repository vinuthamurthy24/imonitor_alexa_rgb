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
public class ArmDisarmHandler extends AlertUpdateHelper {

	public Queue<KeyValuePair> armADevice(Queue<KeyValuePair> alerts)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "armADevice", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> disarmADevice(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "disarmADevice", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> armAllDevices(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "armAllDevices", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> disarmAllDevices(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "disarmAllDevices", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
	// ***************************************************************** sumit start **********************************************************
	
	public Queue<KeyValuePair> armStayDevices(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "armStayDevices", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}

	public Queue<KeyValuePair> armStayDevicesFailure(Queue<KeyValuePair> alerts)
	throws Exception {
		//LogUtil.info("GOT FAILURE");
		//String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		//IMonitorUtil.sendToController("alertsFromImvgService", "armStaylDevices", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	public Queue<KeyValuePair> armDevicesFailure(Queue<KeyValuePair> alerts)
	throws Exception {
		//String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		//IMonitorUtil.sendToController("alertsFromImvgService", "armStaylDevices", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	public Queue<KeyValuePair> disarmDevicesFailure(Queue<KeyValuePair> alerts)
	throws Exception {
		//String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		//IMonitorUtil.sendToController("alertsFromImvgService", "armStaylDevices", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
	//sumit end
}
