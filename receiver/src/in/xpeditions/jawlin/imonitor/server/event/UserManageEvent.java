/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.event;

import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Coladi
 *
 */
public class UserManageEvent extends EventUpdateHelper {

	public Queue<KeyValuePair> updateCreateUserSuccess(Queue<KeyValuePair> events)
			throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateCreateUserFailure(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateModifyUserSuccess(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateModifyUserFailure(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateDeleteUserSuccess(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateDeleteUserFailure(Queue<KeyValuePair> events)
	throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	public Queue<KeyValuePair> updateModifyMainUserSuccess(Queue<KeyValuePair> events)
			throws Exception {
				Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
				String xml = IMonitorUtil.convertQueueIntoXml(events);
				String serviceName = "commandIssueService";
				String methodName = "commandSuccessUpdater";
				IMonitorUtil.sendToController(serviceName, methodName, xml);
				return queue;
			}
			
			public Queue<KeyValuePair> updateModifyMainUserFailure(Queue<KeyValuePair> events)
			throws Exception {
				Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
				String xml = IMonitorUtil.convertQueueIntoXml(events);
				String serviceName = "commandIssueService";
				String methodName = "commandFailureUpdater";
				IMonitorUtil.sendToController(serviceName, methodName, xml);
				return queue;
			}
			public Queue<KeyValuePair> updateSuspendUserFailure(Queue<KeyValuePair> events)
					throws Exception {
						Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
						String xml = IMonitorUtil.convertQueueIntoXml(events);
						String serviceName = "commandIssueService";
						String methodName = "commandSuccessUpdater";
						IMonitorUtil.sendToController(serviceName, methodName, xml);
						return queue;
					}
			
			public Queue<KeyValuePair> updateSuspendUserSuccess(Queue<KeyValuePair> events)
					throws Exception {
						Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
						String xml = IMonitorUtil.convertQueueIntoXml(events);
						String serviceName = "commandIssueService";
						String methodName = "commandSuccessUpdater";
						IMonitorUtil.sendToController(serviceName, methodName, xml);
						return queue;
					}
			public Queue<KeyValuePair> updateRevokeUserSuccess(Queue<KeyValuePair> events)
					throws Exception {
						Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
						String xml = IMonitorUtil.convertQueueIntoXml(events);
						String serviceName = "commandIssueService";
						String methodName = "commandSuccessUpdater";
						IMonitorUtil.sendToController(serviceName, methodName, xml);
						return queue;
					}
			public Queue<KeyValuePair> updateRevokeUserFailure(Queue<KeyValuePair> events)
					throws Exception {
						Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
						String xml = IMonitorUtil.convertQueueIntoXml(events);
						String serviceName = "commandIssueService";
						String methodName = "commandSuccessUpdater";
						IMonitorUtil.sendToController(serviceName, methodName, xml);
						return queue;
					}
			
			
			public Queue<KeyValuePair> deviceListSuccessUpdater(Queue<KeyValuePair> events)
					throws Exception {
						Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
						String xml = IMonitorUtil.convertQueueIntoXml(events);
						String serviceName = "commandIssueService";
						String methodName = "commandSuccessUpdater";
						IMonitorUtil.sendToController(serviceName, methodName, xml);
						return queue;
					}
			
			public Queue<KeyValuePair> deviceListFailureUpdater(Queue<KeyValuePair> events)
					throws Exception {
						Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
						String xml = IMonitorUtil.convertQueueIntoXml(events);
						String serviceName = "commandIssueService";
						String methodName = "commandFailureUpdater";
						IMonitorUtil.sendToController(serviceName, methodName, xml);
						return queue;
					}
			
			
			public Queue<KeyValuePair> updateDeleteNodeSuccess(Queue<KeyValuePair> events)
					throws Exception {
						Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
						String xml = IMonitorUtil.convertQueueIntoXml(events);
						String serviceName = "commandIssueService";
						String methodName = "commandSuccessUpdater";
						IMonitorUtil.sendToController(serviceName, methodName, xml);
						return queue;
					}
			
			
			public Queue<KeyValuePair> updateDeleteNodeFailure(Queue<KeyValuePair> events)
					throws Exception {
						Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
						String xml = IMonitorUtil.convertQueueIntoXml(events);
						String serviceName = "commandIssueService";
						String methodName = "commandFailureUpdater";
						IMonitorUtil.sendToController(serviceName, methodName, xml);
						return queue;
					}
public Queue<KeyValuePair> getDiagnosticSuccess(Queue<KeyValuePair> events)
					throws Exception {
						Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
						String xml = IMonitorUtil.convertQueueIntoXml(events);
						String serviceName = "commandIssueService";
						String methodName = "commandSuccessUpdater";
						IMonitorUtil.sendToController(serviceName, methodName, xml);
						return queue;
					}
			
			public Queue<KeyValuePair> getDiagnosticFailure(Queue<KeyValuePair> events)
					throws Exception {
						Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
						String xml = IMonitorUtil.convertQueueIntoXml(events);
						String serviceName = "commandIssueService";
						String methodName = "commandFailureUpdater";
						IMonitorUtil.sendToController(serviceName, methodName, xml);
						return queue;
					}
			
}
