/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Coladi
 *
 */
public class ConfigurationAlert extends AlertUpdateHelper {
	
	public Queue<KeyValuePair> updateSuccessAlert(Queue<KeyValuePair> alerts)
			throws Exception {
//		REF_TRANSACTION_ID - using this transaction we can update the config params.
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		String serviceName = "commandIssueService";
		String methodName = "ConfigSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
		
	}
	
	public Queue<KeyValuePair> updatePartialSuccessAlert(Queue<KeyValuePair> alerts)
	throws Exception {
//		REF_TRANSACTION_ID - using this transaction we can update the config params.
		return new LinkedList<KeyValuePair>();
	}
	
	public Queue<KeyValuePair> updateFailureAlert(Queue<KeyValuePair> alerts)
	throws Exception {
//		REF_TRANSACTION_ID - using this transaction we can update the config params.
		return new LinkedList<KeyValuePair>();
	}
	
	public Queue<KeyValuePair> updateDefaultSuccessAlert(Queue<KeyValuePair> alerts)
	throws Exception {
//		REF_TRANSACTION_ID - using this transaction we can update the config params.
		return new LinkedList<KeyValuePair>();
	}
	
	public Queue<KeyValuePair> updateDefaultFailureAlert(Queue<KeyValuePair> alerts)
	throws Exception {
//		REF_TRANSACTION_ID - using this transaction we can update the config params.
		return new LinkedList<KeyValuePair>();
	}
}
