/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

public class DoorAlert extends AlertUpdateHelper {
	
	public Queue<KeyValuePair> updateDoorOpen(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateDoorOpenAlert", xml);
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> updateDoorClose(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateDoorCloseAlert", xml);
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> updateDoorLockClose(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "", xml);
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> updateDoorLockOpen(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "", xml);
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
	
	public Queue<KeyValuePair> updateNewAlarm(Queue<KeyValuePair> alerts)
			throws Exception {
				String xml = IMonitorUtil.convertQueueIntoXml(alerts);
				IMonitorUtil.sendToController("alertsFromImvgService", "updateNewAlarmAlert", xml);
				Queue<KeyValuePair> queue = createResultQueue(alerts);
				return queue;
			}
	
	public Queue<KeyValuePair> updateDoorOpenAfterScheduleExecution(Queue<KeyValuePair> alerts)
			throws Exception {
				String xml = IMonitorUtil.convertQueueIntoXml(alerts);
				LogUtil.info("sending service request");
				IMonitorUtil.sendToController("alertsFromImvgService", "updateDoorOpenStatus", xml);
				Queue<KeyValuePair> queue = createResultQueue(alerts);
				return queue;
			}
}
