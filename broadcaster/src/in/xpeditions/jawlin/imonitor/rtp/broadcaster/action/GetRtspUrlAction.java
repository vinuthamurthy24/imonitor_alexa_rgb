/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.action;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.IMonitorUtil;

import java.util.Map;

/**
 * @author Coladi
 *
 */
public class GetRtspUrlAction extends BroadCasterDefaultAction{
	
	@Override
	public String execute(Map<String, Object> params) {
		String deviceId = (String) params.get(Constants.DEVICE_ID);
		PacketFlowManageListener packetFlowManageListener = (PacketFlowManageListener) params.get(Constants.PACKET_FLOW_MANAGE_LISTENER);
	//	LogUtil.info("is Strema available:"+packetFlowManageListener.isStreamAvailable(deviceId));
		if(!packetFlowManageListener.isStreamAvailable(deviceId)){
			
			return Constants.FAILURE;
		}
		String streamUrlId = packetFlowManageListener.getStreamUrlId(deviceId);
	//	LogUtil.info("streamUrlId:"+streamUrlId);
		return IMonitorUtil.createRtspUrl(ImonitorControllerCommunicator.get(Constants.RTSP_IP), ImonitorControllerCommunicator.get(Constants.RTSP_PORT),streamUrlId );
	}
	
	public String executeTillTimeout(Map<String, Object> params) {
		String deviceId = (String) params.get(Constants.DEVICE_ID);
		PacketFlowManageListener packetFlowManageListener = (PacketFlowManageListener) params.get(Constants.PACKET_FLOW_MANAGE_LISTENER);
		String sTimeOut = ImonitorControllerCommunicator.get(Constants.RTSP_CONNECTION_FROM_IMVGWAIT);
		int timout = Integer.parseInt(sTimeOut);
		long currentTime = System.currentTimeMillis();
		long lastTime = System.currentTimeMillis();
		boolean isTimeOut = false;
		while(true){
			isTimeOut = (lastTime - currentTime) > timout;
			if(packetFlowManageListener.isStreamAvailable(deviceId)){
				break;
			}
			if(isTimeOut){
				return Constants.SUCCESS;
			}
			lastTime = System.currentTimeMillis();
		}
		String streamUrlId = packetFlowManageListener.getStreamUrlId(deviceId);
		return IMonitorUtil.createRtspUrl(ImonitorControllerCommunicator.get(Constants.RTSP_IP), ImonitorControllerCommunicator.get(Constants.RTSP_PORT),streamUrlId );
	}
}
