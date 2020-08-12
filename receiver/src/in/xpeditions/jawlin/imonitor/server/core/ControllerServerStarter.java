/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.core;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Properties;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 * @author Coladi
 *
 */
public class ControllerServerStarter implements Runnable {

	private Properties properties;
	private ServerSocket serverSocket;
	private CommandExecutor commandExecutor;

	public ControllerServerStarter(Properties properties, CommandExecutor commandExecutor) {
		this.properties = properties;
		this.commandExecutor = commandExecutor;
	}

	@Override
	public void run() {
		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			int port = Integer.parseInt(this.properties.getProperty("controllerreceiverport"));
			this.serverSocket = sslserversocketfactory
					.createServerSocket(port);
			while (true) {
				if (this.serverSocket.isClosed()) {
					LogUtil.error("Server soket is closed");
					break;
				}
				// Blocks until a connection occurs:
				// If the server is stopped then break the loop.
				SSLSocket socket = null;
				try {
					socket = (SSLSocket) this.serverSocket.accept();
					ServeOneNewControllerClient serveOneNewControllerClient = new ServeOneNewControllerClient(socket, this.commandExecutor);
					Thread t = new Thread(serveOneNewControllerClient);
					t.start();
				} catch (SocketException e) {
					// If exception then it means, the server is closed.
					// Then break the loop.
					LogUtil.error("Exception in socket communication. " + e.getMessage());
					break;
				}
			}
		} catch (IOException e) {
			LogUtil.error("Exception in socket communication. " + e.getMessage());
		} finally {
			try {
				if (!this.serverSocket.isClosed()) {
					this.serverSocket.close();
				}
			} catch (IOException e) {
				LogUtil.error("Exception in socket communication. " + e.getMessage());
			}
		}
	}

}
