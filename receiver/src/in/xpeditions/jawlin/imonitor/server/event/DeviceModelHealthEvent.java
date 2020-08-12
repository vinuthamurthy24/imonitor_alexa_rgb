/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.event;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

public class DeviceModelHealthEvent extends EventUpdateHelper {
	
	/**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return queue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> updatehealthEventSuccess(Queue<KeyValuePair> events)throws Exception {
		LogUtil.info("begain with updatehealthEventSuccess");
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	/**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return queue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> updatehealthEventFailure(Queue<KeyValuePair> events)throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}

}
