/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.intf;

import java.io.FileNotFoundException;
import java.io.IOException;

import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;
import in.xpeditions.jawlin.imonitor.server.event.UpdateListener;

/**
 * @author Coladi
 * 
 */
public interface Server {
	
	/**
	 * To set the property file. This method should call before start() method.
	 * It will set the properties of the server to be started.
	 * 
	 * @param fileLocation - Location of the property file which contains
	 * the properties of the server such as ports, ip etc.
	 */
	public void setPropertyFile(String fileLocation);

	/**
	 * Start the server
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void start() throws FileNotFoundException, IOException;

	/**
	 * Stop the server.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void stop() throws FileNotFoundException, IOException;

	/**
	 * To notify the communication from the gateway.
	 * 
	 * @param updateListener - This listener will be notified when any event occur
	 * in the server.
	 */
	public void addUpdateListener(UpdateListener updateListener);

	/**
	 * @return - object of CommandExecutor.
	 * This object will help to execute commands in the server.
	 */
	public CommandExecutor getCommandExecutor();
	
	/**
	 * @return - true if server is running, false if server is stoped.
	 */
	public boolean isRunning();
	
}
