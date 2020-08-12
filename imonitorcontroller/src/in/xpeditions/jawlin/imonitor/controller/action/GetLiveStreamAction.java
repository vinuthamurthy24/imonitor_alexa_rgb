/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.BroadCasterCommunicator;
import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author Coladi
 *
 */
public class GetLiveStreamAction implements ImonitorControllerAction {
	

	private ActionModel actionModel;
	private boolean resultExecuted = false;

	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Device device = actionModel.getDevice();
		String generatedDeviceId = device.getGeneratedDeviceId();
		String gateWayId = device.getGateWay().getMacId();
		GateWay gateWay = findGateWayWithAgentFromMacId(gateWayId);
		
//		1. Ask to broadcaster about the current status of streaming.
		BroadCasterCommunicator broadCasterCommunicator = new BroadCasterCommunicator();
		String rtspUrl = broadCasterCommunicator.getRtspUrlForDevice(device.getGeneratedDeviceId(), gateWay.getAgent());
//		If streaming already started ...
		if(!rtspUrl.equalsIgnoreCase(Constants.FAILURE)){
			this.actionModel.setData(rtspUrl);
			this.resultExecuted = true;
			return null;
		}
		
//		If you are here, it is a fresh request to iMVG for streaming.
//		Let's send to iMVG.
		
		String rtspClient = gateWay.getAgent().getStreamingIp() + ":" + gateWay.getAgent().getStreamingPort();
		String trasactionId = IMonitorUtil.generateTransactionId();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.IPC_GET_LIVE_STREAM));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWayId));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.RTSP_CLIENT_IP_PORT,rtspClient));
		
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		Agent agent = gateWay.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		IMonitorSession.put(trasactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);
			
		return null;
	}
	/**
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Device device = actionModel.getDevice();
		String generatedDeviceId = device.getGeneratedDeviceId();
		String gateWayId = device.getGateWay().getMacId();
		Object urlObject = ImonitorCameraUrl.get(generatedDeviceId);
		GateWay gateWay = null;
		if(null == urlObject){
//			Fresh request.
			ImonitorCameraUrl.put(generatedDeviceId, Constants.WAITING_FOR_LIVE_STREAM_RESPONCE);
		} else if(Constants.WAITING_FOR_LIVE_STREAM_RESPONCE.compareTo(urlObject.toString()) == 0){
//			Other users have requested and waiting for the results.
			String urlString = ImonitorCameraUrl.get(generatedDeviceId).toString();
			String tOut = ControllerProperties.getProperties().get(Constants.TIMOUT_DURATION);
			long timeOut = Long.parseLong(tOut);
			long waitTime = 1000;
			while(Constants.WAITING_FOR_LIVE_STREAM_RESPONCE.compareTo(urlString) == 0){
//				Do nothing, just wait.
				timeOut = timeOut - waitTime;
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(timeOut < 0){
					this.actionModel.setData(Constants.FAILURE);
					ImonitorCameraUrl.remove(generatedDeviceId);
					this.resultExecuted = true;
					return null;
				}
			}
			this.actionModel.setData(urlString);
			this.resultExecuted = true;
			return null;
		}else{
//			Other user is viewing the stream. Just give the url to this user too.
			String mrl = urlObject.toString();
//			Here we are checking whether the broadcaster is closed the connection already.
			gateWay = findGateWayWithAgentFromMacId(gateWayId);
			BroadCasterCommunicator broadCasterCommunicator = new BroadCasterCommunicator();
			boolean result = broadCasterCommunicator.isStreamExists(generatedDeviceId, gateWay.getAgent());
			if(result){
				this.actionModel.setData(mrl);
				this.resultExecuted = true;
				return null;
			} else {
//			remove the current value form our stack and treat as fresh request.
				ImonitorCameraUrl.remove(generatedDeviceId);
			}
		}
//			Updating the fresh request.
		if(gateWay == null){
			gateWay = findGateWayWithAgentFromMacId(gateWayId);
			if(gateWay == null){
				return Constants.FAILURE;
			}
		}
		String rtspClient = gateWay.getAgent().getStreamingIp() + ":" + gateWay.getAgent().getStreamingPort();
		String trasactionId = IMonitorUtil.generateTransactionId();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.IPC_GET_LIVE_STREAM));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWayId));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.RTSP_CLIENT_IP_PORT,rtspClient));
		
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		Agent agent = gateWay.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		IMonitorSession.put(trasactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);
		
		return null;
	}
**/
	private GateWay findGateWayWithAgentFromMacId(
			String gateWayId) {
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(gateWayId);
		this.actionModel.getDevice().setGateWay(gateWay);
		return gateWay;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
//		Your request for streaming is success...
		Device device = this.actionModel.getDevice();
		Agent agent = device.getGateWay().getAgent();
//		Ask broadcaster for the url.
		BroadCasterCommunicator broadCasterCommunicator = new BroadCasterCommunicator();
		String rtspUrl = broadCasterCommunicator.waitForRtspUrlForDevice(device.getGeneratedDeviceId(), agent);
//		Random randomGenerator = new Random();
//		String streamingIp = agent.getStreamingIp();
//		String mediaPart = IMonitorUtil.generateTransactionId() + randomGenerator.nextInt(1000);
//		String rtspUrl = "rtsp://" + streamingIp + ":" + agent.getRtspPort() + "/" + mediaPart;
////		The result holds the url.
//		ImonitorCameraUrl.put(device.getGeneratedDeviceId(), rtspUrl);
		this.actionModel.setData(rtspUrl);
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		return null;
	}

	@Override
	public boolean isResultExecuted() {
		return this.resultExecuted;
	}

	@Override
	public ActionModel getActionModel() {
		return this.actionModel;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		this.actionModel.setData(Constants.FAILURE);
		this.resultExecuted = true;
		return null;
	}
	@Override
	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return false;
	}
}
