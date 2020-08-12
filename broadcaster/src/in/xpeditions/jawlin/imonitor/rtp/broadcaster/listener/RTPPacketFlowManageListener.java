/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.multicaster.ClientModel;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.multicaster.MultiCaster;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.RtspUrlIdDeviceIdMap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.ServiceException;

/**
 * @author Coladi
 *
*/

public class RTPPacketFlowManageListener implements PacketFlowManageListener {
	private Map<String, MultiCaster> multiCasters;
	private String sdpSection;
	
	public RTPPacketFlowManageListener() {
		Map<String, MultiCaster> tm = new HashMap<String, MultiCaster>();
		this.multiCasters = Collections.synchronizedMap(tm);
	}
	
	@Override
	public synchronized void addStreamProviderSocket(String deviceId, Socket socket) {
		MultiCaster multiCaster = new MultiCaster();
		multiCaster.setSocket(socket);
		multiCaster.setDeviceId(deviceId);
		
		multiCaster.setFlowManageListener(this);
		String rtspUrlStreamId = IMonitorUtil.createRtspUrlStreamId();
		multiCaster.setRtspUrlStreamId(rtspUrlStreamId);
		this.multiCasters.put(deviceId, multiCaster);
		RtspUrlIdDeviceIdMap.put(rtspUrlStreamId, deviceId);
		Thread t = new Thread(multiCaster);
		t.start();
	}

	@Override
	public void addRtspClient(String deviceId, ClientModel clientModel) {
		MultiCaster multiCaster = this.multiCasters.get(deviceId);
		if(multiCaster != null && multiCaster.isRunning()){
			multiCaster.addRtspClient(clientModel);
			LogUtil.info("Adding one RTSP client for " + deviceId);
			return;
		}

//		Streaming stopped, and we are still holding the multicaster.
//		Clearing the url from the controller.
		
		if(multiCaster != null && !multiCaster.isRunning()){
			LogUtil.info("The following section is not required !!!");
			this.multiCasters.remove(deviceId);
			RtspUrlIdDeviceIdMap.remove(multiCaster.getRtspUrlStreamId());
			String serviceName = "cameraStreamingService";
			String methodName = "liveStreamStopped";
			try {
				IMonitorUtil.sendToController(deviceId, serviceName, methodName);
			} catch (ServiceException e) {
				LogUtil.error("Eror while calling the service method to stop live stream for device id : " + deviceId);
			} catch (MalformedURLException e) {
				LogUtil.error("Eror while calling the service method to stop live stream for device id : " + deviceId);
			} catch (RemoteException e) {
				LogUtil.error("Eror while calling the service method to stop live stream for device id : " + deviceId);
			}
		}
	}

	@Override
	public synchronized void stopStreaming(String deviceId, String sessionId) {
		if(deviceId == null){
			LogUtil.info("no device id");
			return;
			}
		MultiCaster multiCaster = this.multiCasters.get(deviceId);
		LogUtil.info("removing client session");
		multiCaster.removeRtspClient(sessionId);
		
	}

	@Override
	public Socket getStreamProviderSocket(String deviceId) {
		MultiCaster multiCaster = this.multiCasters.get(deviceId);
		return multiCaster.getSocket();
	}

	@Override
	public void setSDPSection(String contentSection) {
		this.sdpSection = contentSection;
	}

	@Override
	public String getSDPSection() {
		return this.sdpSection;
	}

	@Override
	public void addTearDownRequest(String deviceId, String tearDownRequest) {
		MultiCaster multiCaster = this.multiCasters.get(deviceId);
		multiCaster.addTearDownRequest(tearDownRequest);
	}

	@Override
	public void clearMulticaster(String deviceId) {
		MultiCaster multiCaster = this.multiCasters.remove(deviceId);
		RtspUrlIdDeviceIdMap.remove(multiCaster.getRtspUrlStreamId());
		if(multiCaster != null){
			try {
				multiCaster.sendTearDownRequest();
			} catch (IOException e) {
				LogUtil.error("Error in sending teardown request for device : " + deviceId + " message : " + e.getMessage());
			}
		}
	}

	@Override
	public boolean isStreamAvailable(String deviceId) {
		if(deviceId == null){
			return false;
		}
		MultiCaster multiCaster = this.multiCasters.get(deviceId);
		if(multiCaster == null){
			return false;
		}
		if(multiCaster.getRtspUrlStreamId() == null){
			return false;
		}
		Socket socket = multiCaster.getSocket();
		if(socket == null){
			LogUtil.info("The stream is not available because socket is null for deviceId : " + deviceId);
			return false;
		}
		if(socket.isClosed()){
			LogUtil.info("The stream is not available because socket is closed for deviceId : " + deviceId);
			return false;
		}
		if(!socket.isConnected()){
			LogUtil.info("The stream is not available because socket is not connected for deviceId : " + deviceId);
			return false;
		}
		if(!socket.isBound()){
			LogUtil.info("The stream is not available because socket is not bound for deviceId : " + deviceId);
			return false;
		}
		return true;
	}

	@Override
	public HashMap<String, List<ClientModel>> getAllStreamsWithClients() {
		HashMap<String, List<ClientModel>> results = new HashMap<String, List<ClientModel>>();
		Set<String> keySet = this.multiCasters.keySet();
		for (String deviceId : keySet) {
			MultiCaster multiCaster = this.multiCasters.get(deviceId);
			List<ClientModel> rtspClients = multiCaster.getRtspClients();
			results.put(deviceId, rtspClients);
		}
		return results;
	}

	@Override
	public String getStreamUrlId(String deviceId) {
		MultiCaster multiCaster = this.multiCasters.get(deviceId);
		if(multiCaster == null){
			return null;
		}
		return multiCaster.getRtspUrlStreamId();
	}
	
	//naveen made changes
	public String setsessionId(String sessionId) {
		MultiCaster multiCaster = this.multiCasters.get(sessionId);
		if(multiCaster == null){
			return null;
		}
		return multiCaster.getRtspUrlStreamId();
	}

	
	@Override
	public String getsessionId() {
		return this.sdpSection;
	}

	//navee end
}
