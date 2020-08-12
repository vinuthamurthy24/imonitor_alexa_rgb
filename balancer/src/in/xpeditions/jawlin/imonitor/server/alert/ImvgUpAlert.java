/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 * 
 */
public class ImvgUpAlert extends AlertUpdateHelper {
	
	@Override
	public Queue<KeyValuePair> updateAlert(Queue<KeyValuePair> alerts)
			throws Exception {
		String imvgId = IMonitorUtil.commandId(alerts, Constants.IMVG_ID);
		XStream stream = new XStream();
		String xml = stream.toXML(imvgId);
		String address = ImonitorControllerCommunicator.getControllerAddress();
		String port = ImonitorControllerCommunicator.getControllerPort();
		String endpoint = "http://" + address + ":" + port
				+ "/imonitorcontroller/services/gateWayService";
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName("handleImvgUp"));
		call.invoke(new Object[] { xml });
//		the above method returns success of failure.
//		send the results according as the above result.
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_ALERT_ACK));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.commandId(alerts, Constants.TRANSACTION_ID)));
		queue.add(new KeyValuePair(Constants.IMVG_ID,IMonitorUtil.commandId(alerts, Constants.IMVG_ID)));
		return queue;
	}
}
