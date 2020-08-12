/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.core;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;
import in.xpeditions.jawlin.imonitor.server.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.server.event.UpdateListener;
import in.xpeditions.jawlin.imonitor.server.intf.Server;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author Coladi
 *
 */
public class ImvgServer implements Server {

	private String propertiesFile = "imvgserver.properties";
	private Properties properties;
	private boolean running;

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.server.intf.Server#addUpdateListener(in.xpeditions.jawlin.imonitor.server.event.UpdateListener)
	 */
	@Override
	public void addUpdateListener(UpdateListener updateListener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.server.intf.Server#getCommandExecutor()
	 */
	@Override
	public CommandExecutor getCommandExecutor() {
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.server.intf.Server#setPropertyFile(java.lang.String)
	 */
	@Override
	public void setPropertyFile(String fileLocation) {
		this.propertiesFile = fileLocation;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.server.intf.Server#start()
	 */
	@Override
	public void start() throws FileNotFoundException, IOException {
		LogUtil.info("IMVG Server - loading property file...");
		loadPropertyFile();
		
		LogUtil.info("Initialising main server...");
		MainServerStarter mainServerStarter = new MainServerStarter(this.properties);
		Thread mainServerThread = new Thread(mainServerStarter);
		mainServerThread.start();
		
		this.running = true;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.server.intf.Server#stop()
	 */
	@Override
	public void stop() throws FileNotFoundException, IOException {
		LogUtil.info("IMVG Server - issuing Shutdown - loading property file...");
		loadPropertyFile();

		int port = Integer.parseInt(this.properties.getProperty("imvgreceiverport"));
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
		.getDefault();
		SSLSocket primarySocket = (SSLSocket) sslsocketfactory.createSocket("localhost", port);
		
		String message = "CMD_ID=BALANCER_SHUTDOWN";
		message += IMonitorUtil.NEW_LINE;
		String tId = IMonitorUtil.generateTransactionId();
		message += "TRANSACTION_ID=";
		message += tId;
		message += IMonitorUtil.NEW_LINE;
		message = IMonitorUtil.appendSignature(message);
		
		// Sending the message to server.
		OutputStream outputStream = primarySocket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(
				outputStream);
		dataOutputStream.writeUTF(message);
	}
	
	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return this.running;
	}

	private void loadPropertyFile() throws FileNotFoundException, IOException {
		this.properties = new Properties();
		File propertyFile = new File(this.propertiesFile);
		this.properties.load(new FileInputStream(propertyFile));
		ImonitorControllerCommunicator.getPropertyMap().put(Constants.CONTROLLER_IP, this.properties.getProperty(Constants.CONTROLLER_IP));
		ImonitorControllerCommunicator.getPropertyMap().put(Constants.CONTROLLER_PORT, this.properties.getProperty(Constants.CONTROLLER_PORT));
		ImonitorControllerCommunicator.getPropertyMap().put(Constants.CONTROLLER_PROTOCOL, this.properties.getProperty(Constants.CONTROLLER_PROTOCOL));
	}

}
