/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.core;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.command.impl.ImvgCommandExecutor;
import in.xpeditions.jawlin.imonitor.server.command.impl.MaintenanceCommandExecuter;
import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;
import in.xpeditions.jawlin.imonitor.server.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.server.event.UpdateListener;
import in.xpeditions.jawlin.imonitor.server.intf.Server;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Queue;

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
		CommandExecutor commandExecutor = new ImvgCommandExecutor(); 
		return commandExecutor;
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
		LogUtil.info("IMVG Server loading property file...");
		loadPropertyFile();
		
		CommandExecutor commandExecutor = new ImvgCommandExecutor();
		
		LogUtil.info("Initializing Controller-Recevier Server ...");
		ControllerServerStarter controllerServerStarter = new ControllerServerStarter(this.properties, commandExecutor);
		Thread controllerServerStarterThread = new Thread(controllerServerStarter);
		controllerServerStarterThread.start();
		
		LogUtil.info("Initialising Event Server");
		EventServerStarter eventServerStarter = new EventServerStarter(this.properties, commandExecutor);
		Thread eventServerThread = new Thread(eventServerStarter);
		eventServerThread.start();
		
		
		commandExecutor = new MaintenanceCommandExecuter();
		LogUtil.info("Initializing Controller-Recevier Server ...");
		ControllerMaintenanceServerStarter controllerMaintenanceServerStarter = new ControllerMaintenanceServerStarter(this.properties, commandExecutor);
		Thread controllermaintenanceThread = new Thread(controllerMaintenanceServerStarter);
		controllermaintenanceThread.start();
	
		LogUtil.info("Initialising MaintenanceEvent Server");	
		MaintenanceEventServerStarter maintenceeventServerStarter = new MaintenanceEventServerStarter(this.properties, commandExecutor);
		Thread maintanceeventthread = new Thread(maintenceeventServerStarter);
		maintanceeventthread.start();
		
		//Naveen Added for receiving image
		LogUtil.info("Initialising image Server");
		ImageServerStarter imageServerStarter = new ImageServerStarter(this.properties, commandExecutor);
		Thread imageReceiveStarter = new Thread(imageServerStarter);
		imageReceiveStarter.start();
		
		this.running = true;
		LogUtil.info("Receiver started successfully ...");
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.server.intf.Server#stop()
	 */
	@Override
	public void stop() throws FileNotFoundException, IOException {

		LogUtil.info("IMVG Server - issuing Shutdown - loading property file...");
		loadPropertyFile();
		
		int port = Integer.parseInt(this.properties.getProperty("receiverport"));
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
		.getDefault();
		SSLSocket primarySocket = (SSLSocket) sslsocketfactory.createSocket("localhost", port);
		
		String message = "CMD_ID=RECEIVER_SHUTDOWN";
		message += IMonitorUtil.NEW_LINE;
		String tId = IMonitorUtil.generateTransactionId();
		message += "IMVG_ID=";
		message += "Avoiding Exception.";
		message += IMonitorUtil.NEW_LINE;
		message += "TRANSACTION_ID=";
		message += tId;
		message += IMonitorUtil.NEW_LINE;
		message = IMonitorUtil.appendSignature(message);
		
		// Sending the message to server.
		OutputStream outputStream = primarySocket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		dataOutputStream.writeUTF(message);
		
		port = Integer.parseInt(this.properties.getProperty("maintenanceport"));
		primarySocket = (SSLSocket) sslsocketfactory.createSocket("localhost", port);
		
		message = "CMD_ID=MAINTENANCE_SHUTDOWN";
		message += IMonitorUtil.NEW_LINE;
		 tId = IMonitorUtil.generateTransactionId();
		message += "IMVG_ID=";
		message += "Avoiding Exception.";
		message += IMonitorUtil.NEW_LINE;
		message += "TRANSACTION_ID=";
		message += tId;
		message += IMonitorUtil.NEW_LINE;
		message = IMonitorUtil.appendSignature(message);
		outputStream = primarySocket.getOutputStream();
		dataOutputStream = new DataOutputStream(outputStream);
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
		ImonitorControllerCommunicator.getPropertyMap().put(Constants.IMVG_CONNECTION_TIMEOUT, this.properties.getProperty(Constants.IMVG_CONNECTION_TIMEOUT));
	}

}
