/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Returns acknowledgments to alerts received from iMVG
 * 
 * @author sumit
 *
 */
public class GenericAlertAck extends AlertUpdateHelper{

	/**
	 * This method will return just an acknowledgment to UPDATE_IMVG_WAN_IP alert received from iMVG.
	 * 
	 * @param alerts Queue<KeyValuePair>
	 * @return queue Queue<KeyValuePair>
	 * @throws Exception NullPointerException
	 */
	public Queue<KeyValuePair> updateImvgWanIp(Queue<KeyValuePair> alerts) throws Exception {
		Queue<KeyValuePair> queue = createResultQueue(alerts);
		return queue;
	}
	
	/**
	 * This is a private method that forms the Acknowledgment Message to be sent to iMVG from CMS.
	 * 
	 * @param alerts Queue<KeyValuePair>
	 * @return queue Queue<KeyValuePair>
	 */
	private Queue<KeyValuePair> createResultQueue(Queue<KeyValuePair> alerts) {
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_ALERT_ACK));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.commandId(alerts, Constants.TRANSACTION_ID)));
		queue.add(new KeyValuePair(Constants.IMVG_ID,IMonitorUtil.commandId(alerts, Constants.IMVG_ID)));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,IMonitorUtil.commandId(alerts, Constants.DEVICE_ID)));
		return queue;
	}
	


}
