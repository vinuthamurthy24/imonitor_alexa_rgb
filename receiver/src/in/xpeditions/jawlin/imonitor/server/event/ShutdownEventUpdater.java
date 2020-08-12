/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.event;

import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.util.Queue;

/**
 * @author Coladi
 *
 */
public class ShutdownEventUpdater extends EventUpdateHelper {
	@Override
	public Queue<KeyValuePair> updateEvent(Queue<KeyValuePair> events)
			throws Exception {
		System.exit(0);
		return events;
	}
}
