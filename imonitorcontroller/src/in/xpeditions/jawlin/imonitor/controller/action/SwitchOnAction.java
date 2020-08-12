/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author Coladi
 *
 */
public class SwitchOnAction implements ImonitorControllerAction {
	

	private ActionModel actionModel;
	private boolean resultExecuted = false;

	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		Device device = actionModel.getDevice();
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		String generatedDeviceId = device.getGeneratedDeviceId();
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,"1"));
//		GatewayManager gatewaymanager=new GatewayManager();
//		GateWay gateway=gatewaymanager.getGateWayByMacId(device.getGateWay().getMacId());
//		String temp[]=gateway.getGateWayVersion().split("_");
//		String temp1[]=temp[1].split("\\.");
//		int ArchitectureVersion=Integer.parseInt(temp1[0]);
//		int FeatureVer=Integer.parseInt(temp1[1]);
//		int BugFixesVer=Integer.parseInt(temp1[2]);
//		
//		if(ArchitectureVersion>=01)
//		{
//			queue.add(new KeyValuePair(Constants.ZW_TIMEOUT,device.getDevicetimeout()));
//		}
//		else if(FeatureVer>=00)
//		{
//			queue.add(new KeyValuePair(Constants.ZW_TIMEOUT,device.getDevicetimeout()));
//		}
//		else if(BugFixesVer>=02)
//		{
		//	queue.add(new KeyValuePair(Constants.ZW_TIMEOUT,device.getDevicetimeout()));
//		}
		if(device.getDevicetimeout()== null)
		{
			queue.add(new KeyValuePair(Constants.ZW_TIMEOUT,"0"));
		}
		else
		{
			queue.add(new KeyValuePair(Constants.ZW_TIMEOUT,device.getDevicetimeout()));
		}
		
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
		//sumit start: test map size
		int mapSize = IMonitorSession.getMapSize();
		
		return null;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		DeviceManager deviceManager = new DeviceManager();
		String commandParam = "1";
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		String gateWayId = this.actionModel.getDevice().getGateWay().getMacId();
		Device device = deviceManager.updateCommadParamOfDeviceByGeneratedId(generatedDeviceId, gateWayId, commandParam);
		this.actionModel.setDevice(device);
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
		DeviceManager deviceManager = new DeviceManager();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		Device device = deviceManager.GetDeviceForFailureByGeneratedId(generatedDeviceId);
		this.actionModel.setDevice(device);
		return null;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();
		keyValuePairs.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL, "1"));
		return keyValuePairs;
	}

	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return false;
	}
}
