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
public class CaptureImageAlert extends AlertUpdateHelper {

	public Queue<KeyValuePair> updateCaptureImageSuccess(Queue<KeyValuePair> alerts)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "saveFtpImvgAlert", xml);
		Queue<KeyValuePair> queue = new IMonitorUtil().createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> updateCaptureImageFailure(Queue<KeyValuePair> alerts)
	throws Exception {
		Queue<KeyValuePair> queue = new IMonitorUtil().createResultQueue(alerts);
		super.updateAlert(alerts);
		return queue;
	}
}
