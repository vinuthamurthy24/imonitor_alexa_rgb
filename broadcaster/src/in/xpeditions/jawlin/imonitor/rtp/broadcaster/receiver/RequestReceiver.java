/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.receiver;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Coladi
 *
 */
public class RequestReceiver implements Runnable {



	private PacketFlowManageListener packetFlowManageListener;

	@Override
	public void run() {
		try {
			String broadCasterPortFromProperty = ImonitorControllerCommunicator.get(Constants.RTSP_PORT);
			int broadCasterPort = Integer.parseInt(broadCasterPortFromProperty);
			ServerSocket serverSocket = new ServerSocket(broadCasterPort);
			while (true) {
				Socket socket = serverSocket.accept();
				socket.setSoTimeout(60000); // infinite timeout.
				//InetAddress inetAddress = socket.getInetAddress();
				//LogUtil.info("One VLC Client is connected from " + inetAddress.getCanonicalHostName() + " " + inetAddress.getHostAddress() + " " + inetAddress.getHostName() + " " + inetAddress.getAddress());
				LogUtil.info("One VLC Client is connected from ");
				HandleOneRTSPClient handleOneRTSPClient = new HandleOneRTSPClient(socket);
				handleOneRTSPClient.addPacketFlowManageListener(this.packetFlowManageListener);
				Thread t = new Thread(handleOneRTSPClient);
				t.start();
			}
		} catch (UnknownHostException e) {
			LogUtil.error("Error when starting request receiver " + e.getMessage());
		} catch (IOException e) {
			LogUtil.error("Error when starting request receiver " + e.getMessage());
		}
	}

	public void addPacketFlowManageListener(
			PacketFlowManageListener packetFlowManageListener) {
		this.packetFlowManageListener = packetFlowManageListener;
	}

}
