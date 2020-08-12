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
public class ImvgWanIpUpdator extends EventUpdateHelper {
	@Override
	public Queue<KeyValuePair> updateEvent(Queue<KeyValuePair> events)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "gateWayService";
		String methodName = "updateGateWayWanIp";
		String result = IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> resultQueue = IMonitorUtil.extractCommandsQueueFromXml(result);
		return resultQueue;
	}
	
	public Queue<KeyValuePair> panicChannelUp(Queue<KeyValuePair> events)throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "gateWayService";
		String methodName = "checkForMaintence";
		String result = IMonitorUtil.sendToController(serviceName, methodName, xml);
		
		Queue<KeyValuePair> resultQueue = IMonitorUtil.extractCommandsQueueFromXml(result);
		String status = IMonitorUtil.commandId(resultQueue,"STATUS");
		Queue<KeyValuePair> results = new LinkedList<KeyValuePair>();
		results.add(new KeyValuePair(Constants.CMD_ID,IMonitorUtil.commandId(resultQueue, Constants.CMD_ID)));
		results.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil.commandId(events, Constants.TRANSACTION_ID)));
		results.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil.commandId(events, Constants.IMVG_ID)));
		
		if(status.equals(Constants.FAILURE))
		{
			results.add(new KeyValuePair(Constants.FAILURE_COMMAND, "NOT_ENABLED"));
		}
		return results;
		
	
	}
}
