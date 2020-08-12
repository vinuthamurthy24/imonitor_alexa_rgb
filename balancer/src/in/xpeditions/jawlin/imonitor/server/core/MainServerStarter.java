/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

import javax.net.ssl.SSLServerSocketFactory;

/**
 * @author Coladi
 *
 */
public class MainServerStarter implements Runnable {
	
	private ServerSocket serverSocket;
	private Properties properties;

	public MainServerStarter(Properties properties) {
		this.properties = properties;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			int port = Integer.parseInt(this.properties.getProperty("imvgreceiverport"));
			String mode = this.properties.getProperty("securemode");
			boolean isSecureMode = Boolean.parseBoolean(mode);
			if(isSecureMode){
				this.serverSocket = sslserversocketfactory
				.createServerSocket(port);
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
					ServeOneNewMainClient serveOneNewClient = new ServeOneNewMainClient(socket);
					Thread t = new Thread(serveOneNewClient);
					t.start();
				} catch (SocketException se) {
					// If exception then it means, the server is closed.
					// Then break the loop.
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (!this.serverSocket.isClosed()) {
					this.serverSocket.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
