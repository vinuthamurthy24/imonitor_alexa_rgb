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
 * 
 * @author sumit
 *
 */
public class RemoveDeviceUpdater {

	/**
	 * @author sumit
	 * @param events java.util.Queue<KeyValuePair>
	 * @return resultQueue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> stopRemoveDeviceEvent(Queue<KeyValuePair> events)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "gateWayService";
		String methodName = "removeDeviceStopper";
		String result = IMonitorUtil.sendToController(serviceName, methodName, xml);
		Queue<KeyValuePair> resultQueue = IMonitorUtil
				.extractCommandsQueueFromXml(result);
		return resultQueue;
	}
	/**
	 * @author vibhu
	 * @param events java.util.Queue<KeyValuePair>
	 * @return resultQueue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> removeAllDevicesSuccess(Queue<KeyValuePair> events)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "gateWayService";
		String methodName = "removeAllDevicesSuccess";
		String result = IMonitorUtil.sendToController(serviceName, methodName, xml);
		
		return new LinkedList<KeyValuePair>();
	}
	/**
	 * @author vibhu
	 * @param events java.util.Queue<KeyValuePair>
	 * @return resultQueue java.util.Queue<KeyValuePair>
	 * @throws Exception
	 */
	public Queue<KeyValuePair> removeAllDevicesFailure(Queue<KeyValuePair> events)
			throws Exception {
		String xml = IMonitorUtil.convertQueueIntoXml(events);
		String serviceName = "gateWayService";
		String methodName = "removeAllDevicesFailure";
		String result = IMonitorUtil.sendToController(serviceName, methodName, xml);
		
		return new LinkedList<KeyValuePair>();
	}
}
