/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.event;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

public class DeviceModelUpdateEvent extends EventUpdateHelper {
	
	/**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return queue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> updateEventSuccess(Queue<KeyValuePair> events)throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	/**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return queue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> updateEventFailure(Queue<KeyValuePair> events)throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	/**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return queue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> updatestatusSuccess(Queue<KeyValuePair> events)throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandSuccessUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	/**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return queue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> updatestatusFailure(Queue<KeyValuePair> events)throws Exception {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "commandIssueService";
		String methodName = "commandFailureUpdater";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return queue;
	}
	
	/**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return queue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> UpdatedDeviceStatus(Queue<KeyValuePair> events)throws Exception {		
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "alertsFromImvgService";
		String methodName = "UpdatedDeviceStatus";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> queue = IMonitorUtil.createUpdatedQueue(events);
		return queue;
	}
	
	/**
	 * @author sumit
	 * @param events java.util.Queue
	 * @return queue java.util.Queue
	 * @throws Exception
	 */
	public Queue<KeyValuePair> syncDeviceStatus(Queue<KeyValuePair> events) throws Exception {
		//1. Update the device status
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "alertsFromImvgService";
		String methodName = "syncDeviceStatus";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		
		//2. Return Ack
		Queue<KeyValuePair> queue = IMonitorUtil.createStatusQueue(events);
		return queue;
	}

	/**
	 * @author naveen
	 * @param events java.util.Queue
	 * @return queue java.util.Queue
	 * @throws Exception
	 */
	public Queue<KeyValuePair> updateSyncStatus(Queue<KeyValuePair> events) throws Exception {
		//1. Update the device status
	
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "alertsFromImvgService";
		String methodName = "updateSyncDeviceStatus";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_ALERT_ACK));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil.commandId(events, Constants.TRANSACTION_ID)));
		queue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil.commandId(
				events, Constants.IMVG_ID)));
		queue.add(new KeyValuePair(Constants.DEVICE_ID, IMonitorUtil.commandId(
				events, Constants.IMVG_ID)));
		
		return queue;
		
	}


}
