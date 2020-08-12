/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;

import javax.xml.rpc.ServiceException;

/**
 * @author Coladi
 *
 */
public class MotionDetectionAlert extends AlertUpdateHelper {

	public Queue<KeyValuePair> updateMotionDetectionAlert(Queue<KeyValuePair> alerts)
			throws Exception {
		sendDeviceAlert(alerts, "alertsFromImvgService","updateMotionDetectionAlert");
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		return queue;
	}
	//sumit start
	public Queue<KeyValuePair> updateNoMotionDetectionAlert(Queue<KeyValuePair> alerts) throws Exception {
		sendDeviceAlert(alerts, "alertsFromImvgService", "updateNoMotionDetectionAlert");
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		return queue;
	}
	//sumit end
	private String sendDeviceAlert(Queue<KeyValuePair> alerts,
			String serviceName,String methodName) throws ServiceException,
			MalformedURLException, RemoteException {
		String xml = IMonitorUtil.convertQueueIntoXml(alerts);
		String results = IMonitorUtil.sendToController(serviceName, methodName, xml);
		return results;
	}
	private Queue<KeyValuePair> createResultQueue(Queue<KeyValuePair> alerts) {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_ALERT_ACK));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.commandId(alerts, Constants.TRANSACTION_ID)));
		queue.add(new KeyValuePair(Constants.IMVG_ID,IMonitorUtil.commandId(alerts, Constants.IMVG_ID)));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,IMonitorUtil.commandId(alerts, Constants.DEVICE_ID)));
		return queue;
	}
}
