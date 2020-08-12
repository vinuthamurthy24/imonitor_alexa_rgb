/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.multicaster;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.generic.util.Util;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Coladi
 * 
 */

public class MultiCaster implements Runnable {

	private Socket socket;
	private List<ClientModel> rtspClients;
	private List<ClientModel> clientsToBeRemoved;
	private String deviceId;
	private String tearDownRequest;
	private PacketFlowManageListener flowManageListener;
	private boolean isRunning;
	public String rtspUrlStreamId;
	public String clientsessionId;
	
	public MultiCaster() {
		this.clientsToBeRemoved = new ArrayList<ClientModel>();
	}

	@Override
	public void run() {
		this.isRunning = true;
		try {
			InputStream inputStream = this.socket.getInputStream();
			long lastTimeOfProperCommunicaton = System.currentTimeMillis(); 
			if (inputStream != null) {
				while (true) {
					
					//LogUtil.info("last Time Of ProperCommunicaton"+ lastTimeOfProperCommunicaton);
					
//					If all clients are closed we can close this section.
					if(this.rtspClients != null && this.rtspClients.size() == 0){
//						We'll wait for one minute before stopping the stream ....
						long curTime = System.currentTimeMillis();
						int available = inputStream.available();
						byte[] b = new byte[available];
						inputStream.read(b,0,available);
						
						
						
						
						if( (curTime - lastTimeOfProperCommunicaton) > 10000){
							sendTearDownRequest();
//							closeConnectionClearMultiCaster();
							break;
						} else{
							LogUtil.info("curTime - lastTimeOfProperCommunicaton"+(curTime - lastTimeOfProperCommunicaton));
							continue;
						}
					}
					
					
					if(this.rtspClients != null){
//					Removing the closed clients.
						synchronized (this.rtspClients) {
							this.rtspClients.removeAll(this.clientsToBeRemoved);
						}
						synchronized (this.clientsToBeRemoved) {
							this.clientsToBeRemoved.clear();
						}
					}
//					Changing the last proper time.
					lastTimeOfProperCommunicaton = System.currentTimeMillis();
//					Read from input stream.
					int dollarBit = inputStream.read();
					if(dollarBit == -1){
						throw new IOException("Somehow the streaming is stopped, So exiting the connection");
					}
					/*if(dollarBit != 36){
						int available = inputStream.available();
						byte[] b = new byte[available];
						inputStream.read(b , 0, available);
						String replyMessage = new String(b);
						if(replyMessage.contains("200 OK")){
							LogUtil.info("We got reply to tear down request from device " + this.deviceId);
							closeConnectionClearMultiCaster();
							break;
						}
						b = null;
						continue;
					}*/
//					Now we have proper data in the stream.
					int statusBit = inputStream.read();
					byte lengthBytes[] = new byte[2];
					inputStream.read(lengthBytes, 0, 2);
					String rtpLengthS = Util.getHex(lengthBytes);
					int rtpLength = Integer.parseInt(rtpLengthS, 16);
					LogUtil.info("RtpLength : "+rtpLength);
					byte[] contentBytes = new byte[rtpLength];
					inputStream.read(contentBytes, 0, rtpLength);
					
					
//					If first byte is not $, then ignore it.
					if(this.rtspClients == null || dollarBit != 36){
						lengthBytes = null;
						rtpLengthS = null;
						rtpLengthS = null;
						contentBytes = null;
						continue;
					}
					synchronized (this.rtspClients) {
						
						LogUtil.info("this.rtspClients lenght : "+this.rtspClients.size());
						
						for (ClientModel clientModel : this.rtspClients) {
							Socket socket = clientModel.getSocket();
							if (socket != null && !socket.isClosed()
									&& !socket.isOutputShutdown()
									&& socket.isConnected()) {
								OutputStream outputStream = socket
								.getOutputStream();
								try {
									outputStream.write(dollarBit);
									outputStream.write(statusBit);
									outputStream.write(lengthBytes);
									outputStream.write(contentBytes);
									outputStream.flush();
								} catch (Exception ex) {
									this.clientsToBeRemoved.add(clientModel);
									LogUtil
									.error("Error Occured while giving streams to one client May be client is closed !!. Session is : "
											+ clientModel
											.getSessionId() + " and message is : " + ex.getMessage());
								}
								outputStream = null;
							} else {
								this.clientsToBeRemoved.add(clientModel);
							}
								socket = null;
						}
					}
					
					lengthBytes = null;
					rtpLengthS = null;
					rtpLengthS = null;
					contentBytes = null;
					LogUtil.info("this.clientsToBeRemoved.size() : "+this.clientsToBeRemoved.size());
				}
			}
		} catch (IOException e) {
			LogUtil.error("Error occured while receiving the streams from deviceid : " + this.deviceId
					+ e.getMessage());
		}catch (Exception e) {
			LogUtil.error("Error occured while receiving the streams from deviceid : " + this.deviceId
					+ e.getMessage());
		}finally {
			try {
				closeConnectionClearMultiCaster();
			} catch (IOException e) {
				LogUtil.error("Error When sending closing streaming connection and sending tear down request. It may cause serious problem in the streaming.");
			}
		}
		this.isRunning = false;
	}

	public void sendTearDownRequest() throws IOException {
		if(this.socket != null && this.socket.isConnected()){
			this.socket.getOutputStream().write(tearDownRequest.getBytes());
			LogUtil.info("Sending Teardown request to the device : " + this.deviceId);
			return;
		}
		LogUtil.info("We cant send Teardown request to the device :(Either socket is not connected or socket is null) " + this.deviceId);
	}

	private void closeConnectionClearMultiCaster() throws IOException {
//		Clearing the url from the controller.
		flowManageListener.clearMulticaster(this.deviceId);
		
		LogUtil.info("Clearing the url from the controller for DeviceId : " + this.deviceId);
		/*String serviceName = "cameraStreamingService";
		String methodName = "liveStreamStopped";
		try {
			IMonitorUtil.sendToController(this.deviceId, serviceName, methodName);
		} catch (ServiceException e) {
			LogUtil.error("Eror while calling the service method to stop live stream for device id : " + this.deviceId);
		}*/
		if(this.socket !=null && !this.socket.isClosed()){
			this.socket.close();
		}
		this.socket = null;
	}

	
	public void addRtspClient(ClientModel clientModel) {
		if(this.rtspClients == null){
			this.rtspClients = new ArrayList<ClientModel>();
		}
		synchronized (this.rtspClients) {
			this.rtspClients.add(clientModel);
           
           
			LogUtil.info("One client is added to the streaming server sessionId : "
					+ clientModel.getSessionId());
		}
	}

	public void removeRtspClient(String sessionId) {
		
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void addTearDownRequest(String tearDownRequest) {
		this.tearDownRequest = tearDownRequest;
	}

	public void setFlowManageListener(PacketFlowManageListener flowManageListener) {
		this.flowManageListener = flowManageListener;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public final boolean isRunning() {
		return isRunning;
	}

	public final List<ClientModel> getRtspClients() {
		return rtspClients;
	}

	public final String getRtspUrlStreamId() {
		return rtspUrlStreamId;
	}

	public final void setRtspUrlStreamId(String rtspUrlStreamId) {
		this.rtspUrlStreamId = rtspUrlStreamId;
	}
	
	
}
