/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.event;

/**
 * @author Coladi
 * 
 */
public interface UpdateListener {
	/**
	 * @param executedUpdates - executed updates in xml format.
	 */
	public void executedUpdates(String executedUpdates);
}
