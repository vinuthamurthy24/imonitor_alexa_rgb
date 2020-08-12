/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.server.event;

import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.Queue;

public class DeviceAlertEvent {
	public Queue<KeyValuePair> handleAlert(Queue<KeyValuePair> events)throws Exception {
		Queue<KeyValuePair> commands = IMonitorUtil.updateAlert(events);
		return commands;
	}
}
