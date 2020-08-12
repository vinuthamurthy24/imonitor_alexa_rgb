/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.controllercommunicator;

import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Properties;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ControllerCommunicationServer implements Runnable {

	private Properties properties;
	private ServerSocket serverSocket;
	private PacketFlowManageListener packetFlowManageListener;

	public ControllerCommunicationServer(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void run() {
		String c2bPort = (String) this.properties.get(Constants.CONTROLLER_BROADCASTER_PORT);
		int port = Integer.parseInt(c2bPort);

		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			this.serverSocket = sslserversocketfactory
					.createServerSocket(port);
			while (true) {
				if (this.serverSocket.isClosed()) {
					break;
				}
				// Blocks until a connection occurs:
				// If the server is stopped then break the loop.
				SSLSocket socket = null;
				try {
					socket = (SSLSocket) this.serverSocket.accept();
					ServeOneNewControllerClient serveOneNewControllerClient = new ServeOneNewControllerClient(socket);
					serveOneNewControllerClient.addPacketFlowManageListener(this.packetFlowManageListener);
					Thread t = new Thread(serveOneNewControllerClient);
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

	public void addPacketFlowManageListener(
			PacketFlowManageListener packetFlowManageListener) {

		this.packetFlowManageListener = packetFlowManageListener;
	}

}

