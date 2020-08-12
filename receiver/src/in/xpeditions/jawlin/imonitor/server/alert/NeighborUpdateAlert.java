/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

public class NeighborUpdateAlert extends AlertUpdateHelper 
{
	//vibhu start
	public Queue<KeyValuePair> updateNUpdateStart(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateNUpdateStart", xml);
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> updateNUpdateDone(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateNUpdateDone", xml);
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		return queue;
	}

	public Queue<KeyValuePair> updateNUpdateFailed(Queue<KeyValuePair> alerts)
	throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateNUpdateFailed", xml);
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
	//vibhu end
	

}
