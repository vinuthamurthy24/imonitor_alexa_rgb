/* Copyright  2012 iMonitor Solutions India Private Limited */
/**
 * 
 */

package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.Queue;

/**
 * 
 * @author sumit kumar
 *
 * Implemented to update the Schedule Execution to CMS.
 * Also send out notification if configured for the Alert.
 * 
 */
public class ScheduleExecAlert extends AlertUpdateHelper {

	public Queue<KeyValuePair> updateScheduleExecSuccess(Queue<KeyValuePair> alerts)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateScheduleExecutionSuccess", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
	public Queue<KeyValuePair> updateScheduleExecFailure(Queue<KeyValuePair> alerts)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		IMonitorUtil.sendToController("alertsFromImvgService", "updateScheduleExecutionFailure", xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createResultQueue(alerts);
		return queue;
	}
	
}