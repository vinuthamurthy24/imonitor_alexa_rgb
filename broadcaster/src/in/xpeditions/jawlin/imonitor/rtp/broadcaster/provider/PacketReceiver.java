/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.provider;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.net.ssl.SSLServerSocketFactory;

/**
 * @author Coladi
 * 
 */
public class PacketReceiver implements Runnable {
	private ServerSocket serverSocket;
	private PacketFlowManageListener packetFlowManageListener;

	public PacketReceiver() {
	}

	@Override
	public void run() {
		String sPort = ImonitorControllerCommunicator
				.get(Constants.STREAMING_PORT);
		int port = Integer.parseInt(sPort);
		String mode = ImonitorControllerCommunicator.get(Constants.SECURE_MODE);
		boolean isSecureMode = Boolean.parseBoolean(mode);
		try {
			if (isSecureMode) {
				SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory
						.getDefault();
				this.serverSocket = sslserversocketfactory
						.createServerSocket(port);
			} else {
				this.serverSocket = new ServerSocket(port);
			}
			String sTimeOut = ImonitorControllerCommunicator.get(Constants.CONNECTION_TIMEOUT);
			int timeOut = Integer.parseInt(sTimeOut );
			while (true) {
				if (this.serverSocket.isClosed()) {
					break;
				}
				// Blocks until a connection occurs:
				// If the server is stopped then break the loop.
				Socket socket = null;
				try {
					socket = this.serverSocket.accept();
					/*InetAddress inetAddress = socket.getInetAddress();
					LogUtil.info("One client is connected to provide streams... We have given timeout : " + sTimeOut + " ms" +
							" " + inetAddress.getCanonicalHostName() + " " + inetAddress.getHostAddress() + " " + inetAddress.getHostName() + " " + inetAddress.getAddress());*/
					
				
					LogUtil.info("One client is connected to provide streams... We have given timeout : " + sTimeOut + " ms");
					
					socket.setSoTimeout(timeOut);
					HandleStreamProvider handleStreamProvider = new HandleStreamProvider(socket);
					handleStreamProvider.addPacketFlowManageListener(this.packetFlowManageListener);
					Thread t = new Thread(handleStreamProvider);
					t.start();
				} catch (SocketException se) {
					// If exception then it means, the server is closed.
					// Then break the loop.
					LogUtil.error("Error in packet receiver server SocketException : " + se.getMessage());
					break;
				}
			}
			
		} catch (IOException e) {
			LogUtil.error("Error in packet receiver server IOException : " + e.getMessage());
		}
	}

	public void addPacketFlowManageListener(
			PacketFlowManageListener packetFlowManageListener) {
		this.packetFlowManageListener = packetFlowManageListener;
	}

}
