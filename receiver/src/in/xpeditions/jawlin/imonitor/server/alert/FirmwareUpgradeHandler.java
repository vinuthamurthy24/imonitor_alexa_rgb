/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.Queue;

/**
 * 
 * @author sumit
 *
 */
public class FirmwareUpgradeHandler extends AlertUpdateHelper {

	public Queue<KeyValuePair> updateFirmwareUpgradeSuccessful(Queue<KeyValuePair> alerts)
			throws Exception {
//		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
//		IMonitorUtil.sendToController("gateWayService", "updateFirmwareUpgradeSuccessful", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> updateFirmwareUpgradeFailure(Queue<KeyValuePair> alerts)
			throws Exception {
//		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
//		IMonitorUtil.sendToController("gateWayService", "updateFirmwareUpgradeFailure", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
}
