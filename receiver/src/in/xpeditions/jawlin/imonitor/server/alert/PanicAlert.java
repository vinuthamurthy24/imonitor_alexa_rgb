/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Coladi
 * 
 */
public class PanicAlert extends AlertUpdateHelper {

	public Queue<KeyValuePair> updatePanic(Queue<KeyValuePair> alerts)
			throws Exception {
		
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "panicAlert",
				xml);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue
				.add(new KeyValuePair(Constants.CMD_ID,
						Constants.DEVICE_ALERT_ACK));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil
				.commandId(alerts, Constants.TRANSACTION_ID)));
		queue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil.commandId(
				alerts, Constants.IMVG_ID)));
		return queue;
	}
}
