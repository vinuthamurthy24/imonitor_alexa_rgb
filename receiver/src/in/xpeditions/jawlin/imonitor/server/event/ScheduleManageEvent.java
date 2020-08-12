/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.event;

import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Coladi
 *
 */
public class ScheduleManageEvent extends EventUpdateHelper {

	public Queue<KeyValuePair> updateCreateScheduleSuccess(Queue<KeyValuePair> events)
			throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateCreateScheduleFailure(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateModifyScheduleSuccess(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateModifyScheduleFailure(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateDeleteScheduleSuccess(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateDeleteScheduleFailure(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}

	public Queue<KeyValuePair> updateScheduleActivationSuccess(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateScheduleActivationFailure(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);		
		return queue;
	}

}
