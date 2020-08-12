package in.xpeditions.jawlin.imonitor.server.event;

import java.util.LinkedList;
import java.util.Queue;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

public class ConfigureRGBUpdater {
	public Queue<KeyValuePair> updateConfigureRGBSuccess(Queue<KeyValuePair> events)
			throws Exception {
		LogUtil.info("updateConfigureRGBSuccess");
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateConfigureRGBFailure(Queue<KeyValuePair> events)
			throws Exception {
		LogUtil.info("updateConfigureRGBFailure");
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
}
