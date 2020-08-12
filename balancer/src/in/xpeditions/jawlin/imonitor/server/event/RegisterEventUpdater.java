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

import com.thoughtworks.xstream.XStream;

/**
 * @author Asmodeus
 * 
 */
public class RegisterEventUpdater extends EventUpdateHelper {
	@Override
	public Queue<KeyValuePair> updateEvent(Queue<KeyValuePair> events) throws Exception {
		String imvgId = IMonitorUtil.commandId(events, Constants.IMVG_ID);
		XStream stream=new XStream();
		String eventInXmls=stream.toXML(imvgId);
		
		String serviceName = "gateWayService";
		String methodName = "register";
		String result = IMonitorUtil.sendToController(eventInXmls, serviceName, methodName);
		
		Queue<KeyValuePair> resultQueue = IMonitorUtil.extractCommandsQueueFromXml(result);
		String status = IMonitorUtil.commandId(resultQueue, Constants.STATUS);
		Queue<KeyValuePair> results = new LinkedList<KeyValuePair>();
		
		if(status.equals(Constants.SUCCESS)){
			results.add(new KeyValuePair(Constants.CMD_ID, Constants.IMVG_REGISTER_SUCCESS));
			results.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil.commandId(events, Constants.TRANSACTION_ID)));
			results.add(new KeyValuePair(Constants.IMVG_ID, imvgId));
			results.add(new KeyValuePair(Constants.PRIMARY_SERVER_IP_PORT, IMonitorUtil.commandId(resultQueue, Constants.PRIMARY_IP) + ":" + IMonitorUtil.commandId(resultQueue, Constants.PRIMARY_PORT)));
			results.add(new KeyValuePair(Constants.SECONDARY_SERVER_IP_PORT, IMonitorUtil.commandId(resultQueue, Constants.SECONDARY_IP) + ":" + IMonitorUtil.commandId(resultQueue, Constants.SECONDARY_PORT)));
			results.add(new KeyValuePair(Constants.CONFIG_PARAM, Constants.START));
			results.add(new KeyValuePair(Constants.CONFIG_PARAM, Constants.END));
		}
		if(status.equals(Constants.FAILURE)){
			results.add(new KeyValuePair(Constants.CMD_ID, Constants.IMVG_REGISTER_FAIL));
			results.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil.commandId(events, Constants.TRANSACTION_ID)));
			results.add(new KeyValuePair(Constants.IMVG_ID, imvgId));
			results.add(new KeyValuePair(Constants.FAILURE_COMMAND, IMonitorUtil.commandId(resultQueue, Constants.FAILURE_REASON)));
		}
		return results;
	}
	public Queue<KeyValuePair> updateUnRegisterEvent(Queue<KeyValuePair> events) throws Exception {
		String imvgId = IMonitorUtil.commandId(events, Constants.IMVG_ID);
		XStream stream=new XStream();
		String eventInXmls=stream.toXML(imvgId);
		String serviceName = "gateWayService";
		String methodName = "updateUnRegisterGateWay";
		IMonitorUtil.sendToController(eventInXmls, serviceName, methodName);
		Queue<KeyValuePair> results = new LinkedList<KeyValuePair>();
		return results;
	}
}
