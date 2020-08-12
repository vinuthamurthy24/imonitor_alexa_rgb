/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.core;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;
import in.xpeditions.jawlin.imonitor.server.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.server.util.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

import javax.net.ssl.SSLServerSocketFactory;

/**
 * @author Coladi
 * 
 */
public class EventServerStarter implements Runnable {

	private Properties properties;
	private ServerSocket serverSocket;
	private CommandExecutor commandExecutor;

	public EventServerStarter(Properties properties, CommandExecutor commandExecutor) {
		this.properties = properties;
		this.commandExecutor = commandExecutor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			int port = Integer.parseInt(this.properties.getProperty("receiverport"));
			String mode = this.properties.getProperty("securemode");
			boolean isSecureMode = Boolean.parseBoolean(mode);
			String sTimeOut = ImonitorControllerCommunicator.getPropertyMap().get(Constants.IMVG_CONNECTION_TIMEOUT);
			int timeOut = Integer.parseInt(sTimeOut);
			if(isSecureMode){
				this.serverSocket = sslserversocketfactory.createServerSocket(port);
			}else{
				this.serverSocket = new ServerSocket(port);
			}
			while (true) {
				if (this.serverSocket.isClosed()) {
					break;
				}
				// Blocks until a connection occurs:
				// If the server is stopped then break the loop.
				Socket socket = null;
				try {
					socket = this.serverSocket.accept();
					socket.setSoTimeout(timeOut);
					/*InetAddress inetAddress = socket.getInetAddress();
					if(inetAddress != null){
						LogUtil.info("New Connection from : " + inetAddress.getCanonicalHostName() + "," + inetAddress.getHostAddress() + "," + inetAddress.getHostName());
					}
				*/	
					
					ServeOneEventClient serveOneEventClient = new ServeOneEventClient(
							socket, this.commandExecutor);
					Thread t = new Thread(serveOneEventClient);
					t.start();
					
				} catch (SocketException e) {
					// If exception then it means, the server is closed.
					// Then break the loop.
					LogUtil.error("Exception in Socket Communication : " + e.getMessage());
					break;
				}
			}
		} catch (IOException e) {
			LogUtil.error("Exception in Socket Communication : " + e.getMessage());
		} finally {
			try {
				if (!this.serverSocket.isClosed()) {
					this.serverSocket.close();
				}
			} catch (IOException e) {
				LogUtil.error("Exception in Socket Communication : " + e.getMessage());
			}
		}
	}

}
