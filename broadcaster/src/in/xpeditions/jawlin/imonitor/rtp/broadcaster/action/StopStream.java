package in.xpeditions.jawlin.imonitor.rtp.broadcaster.action;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.IMonitorUtil;

import java.util.Map;

public class StopStream extends BroadCasterDefaultAction{

	@Override
	public String execute(Map<String, Object> params) {
		String deviceId = (String) params.get(Constants.DEVICE_ID);
		PacketFlowManageListener packetFlowManageListener = (PacketFlowManageListener) params.get(Constants.PACKET_FLOW_MANAGE_LISTENER);
		if(!packetFlowManageListener.isStreamAvailable(deviceId))
		{
			LogUtil.info("NO packet listrews");
			return Constants.FAILURE;
		}
		else
		{
			LogUtil.info("Hello got packet listerres..");
			packetFlowManageListener.clearMulticaster(deviceId);
		}
		return deviceId;
	}
}
