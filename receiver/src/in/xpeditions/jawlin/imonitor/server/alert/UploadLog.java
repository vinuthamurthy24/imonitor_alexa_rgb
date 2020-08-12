/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.Queue;

public class UploadLog  extends AlertUpdateHelper {
	
	public Queue<KeyValuePair> UploadLogSucess(Queue<KeyValuePair> alert)throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alert);
		String serviceName = "alertsFromImvgService";
		String methodName = "LogUploadSucess";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alert);
		return queue;
	}

}
