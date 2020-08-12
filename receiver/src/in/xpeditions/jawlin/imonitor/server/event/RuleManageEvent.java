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
public class RuleManageEvent extends EventUpdateHelper {

	public Queue<KeyValuePair> updateCreateRuleSuccess(Queue<KeyValuePair> events)
			throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateCreateRuleFailure(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateModifyRuleSuccess(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateModifyRuleFailure(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateDeleteRuleSuccess(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateDeleteRuleFailure(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
}
