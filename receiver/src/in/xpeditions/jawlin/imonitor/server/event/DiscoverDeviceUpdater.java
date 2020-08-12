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
 * @author Asmodeus
 * 
 */
public class DiscoverDeviceUpdater {

	/**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return resultQueue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> updateEvent(Queue<KeyValuePair> events)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "gateWayService";
		String methodName = "discoverDeviceUpdater";
		String result = IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> resultQueue = IMonitorUtil
				.extractCommandsQueueFromXml(result);
		return resultQueue;
	}

	/**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return queue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> updateGateWay(Queue<KeyValuePair> events)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "gateWayService";
		String methodName = "changeDeviceDiscoveryMode";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		return queue;
	}
	
	/**
	 * @author sumit
	 * @param events java.util.Queue<KeyValuePair>
	 * @return resultQueue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> stopDiscoveryEvent(Queue<KeyValuePair> events)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "gateWayService";
		String methodName = "discoverDeviceStopper";
		String result = IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> resultQueue = IMonitorUtil
				.extractCommandsQueueFromXml(result);
		return resultQueue;
	}
	
	/**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return queue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> abortMode(Queue<KeyValuePair> events)
			throws Exception {
		//String xml = IMonitorUtil.convertQueueIntoXml(events);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		return queue;
	}
	
	
	/*//VIA_SLAVE Success
	*//**
	 * 
	 * @param events java.util.Queue<KeyValuePair>
	 * @return resultQueue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 *//*
	public Queue<KeyValuePair> updateEventForVIASlave(Queue<KeyValuePair> events)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "gateWayService";
		String methodName = "updateEventForVIASlave";
		String result = IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> resultQueue = IMonitorUtil
				.extractCommandsQueueFromXml(result);
		return resultQueue;
	}*/
	
	
	/*//Indoor Unit Success
		*//**
		 * 
		 * @param events java.util.Queue<KeyValuePair>
		 * @return resultQueue java.util.Queue<KeyValuePair>
		 * @throws Exception
		 *//*
		public Queue<KeyValuePair> updateEventForIndoorUnit(Queue<KeyValuePair> events)
				throws Exception {
			String xml = IMonitorUtil.convertQueueIntoXml(events);
			String serviceName = "gateWayService";
			String methodName = "updateEventForIndoorUnit";
			String result = IMonitorUtil.sendToController(serviceName, methodName, xml);
			Queue<KeyValuePair> resultQueue = IMonitorUtil
					.extractCommandsQueueFromXml(result);
			return resultQueue;
		}*/
		
		
		/*//updateCapabilityEvent
		*//**
		 * 
		 * @param events java.util.Queue<KeyValuePair>
		 * @return resultQueue java.util.Queue<KeyValuePair>
		 * @throws Exception
		 *//*
		public Queue<KeyValuePair> updateCapabilityEvent(Queue<KeyValuePair> events)
				throws Exception {
			String xml = IMonitorUtil.convertQueueIntoXml(events);
			String serviceName = "gateWayService";
			String methodName = "updateCapabilityEvent";
			String result = IMonitorUtil.sendToController(serviceName, methodName, xml);
			Queue<KeyValuePair> resultQueue = IMonitorUtil
					.extractCommandsQueueFromXml(result);
			return resultQueue;
		}*/
	
	//IDU ststus alert
		public Queue<KeyValuePair> updateIDUStatus(Queue<KeyValuePair> events)
				throws Exception {
			String xml = IMonitorUtil.convertQueueIntoXml(events);
			String serviceName = "deviceService";
			String methodName = "updateIDUStatus";
			IMonitorUtil.sendToController(serviceName, methodName, xml);
			Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
			return queue;
		}
	
}
