/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.event;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

public class KeepAliveEventUpdater {
	public Queue<KeyValuePair> updateEvent(Queue<KeyValuePair> events) throws Exception {
		//Queue<KeyValuePair> results = new LinkedList<KeyValuePair>();
		//Naveen start to verify gateway registration before sending ACK
	
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String result = IMonitorUtil.sendToController("gateWayService", "verifyGateway", xml);
		Queue<KeyValuePair> resultQueue = IMonitorUtil.extractCommandsQueueFromXml(result);
		return resultQueue;
		
		//Naveen end
		/*results.add(new KeyValuePair(Constants.CMD_ID,Constants.IMVG_KEEP_ALIVE_ACK));
		results.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.commandId(events, Constants.TRANSACTION_ID)));
		results.add(new KeyValuePair(Constants.IMVG_ID,IMonitorUtil.commandId(events, Constants.IMVG_ID)));
		return results;*/
	}
}
