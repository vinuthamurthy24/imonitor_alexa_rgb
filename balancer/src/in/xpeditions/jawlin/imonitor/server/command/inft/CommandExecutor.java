/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.command.inft;

import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.net.Socket;
import java.util.Queue;

/**
 * @author Coladi
 * 
 */
public interface CommandExecutor {

	/**
	 * Executing the commands in Map format.
	 * 
	 * @param commands - commands in Map format.
	 */
	
	public void addImvg(String imvgId, Socket socket);

	public void executeCommand(Queue<KeyValuePair> commands);
}
