/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

public class DeviceHealthScanUpdateAlert extends AlertUpdateHelper 
{
	//bhavya start
	
	
	public Queue<KeyValuePair> UpdateScanHealthDeviceSuccess(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "healthmonitorscansuccess", xml);
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		return queue;
	}

	public Queue<KeyValuePair> UpdateScanHealthDeviceFailed(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "healthmonitorscanfailure", xml);
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
	//bhavya end
}
