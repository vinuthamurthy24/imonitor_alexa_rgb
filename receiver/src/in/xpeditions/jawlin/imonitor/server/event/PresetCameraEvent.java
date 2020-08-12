/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.event;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Coladi
 *
 */
public class PresetCameraEvent extends EventUpdateHelper {

	public Queue<KeyValuePair> updateEventSuccess(Queue<KeyValuePair> events)
			throws Exception {
		
		LogUtil.info("I AM IN updateEventSuccess");
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String status = IMonitorUtil.commandId(events, "STATUS");
		String serviceName = "commandIssueService";
		String methodName = "";
		if(status.equalsIgnoreCase("PRESET_CAMERA_INITIATED")){
			methodName = "commandSuccessUpdater";
		}else{
			methodName = "commandFailureUpdater";
		}
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
}
