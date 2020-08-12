/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.event;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class ExecuteCommandEvent extends EventUpdateHelper {
	
public Queue<KeyValuePair> updateExcuteCommandSuccess(Queue<KeyValuePair> events)
					throws Exception {
				Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
				String xml = IMonitorUtil.convertQueueIntoXml(events);
				String serviceName = "commandIssueService";
				String methodName = "commandSuccessUpdater";
				
				IMonitorUtil.sendToController(serviceName, methodName, xml);
				return queue;
}
		
public Queue<KeyValuePair> updateExcuteCommandFail(Queue<KeyValuePair> events)
		throws Exception {
				Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
				String xml = IMonitorUtil.convertQueueIntoXml(events);
				String serviceName = "commandIssueService";
				String methodName = "commandFailureUpdater";
				IMonitorUtil.sendToController(serviceName, methodName, xml);
				return queue;
}

}
