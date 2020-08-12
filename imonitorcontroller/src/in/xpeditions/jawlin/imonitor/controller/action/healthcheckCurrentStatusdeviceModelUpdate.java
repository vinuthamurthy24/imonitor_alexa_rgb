/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.action.ActionDataHolder;
import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

public class healthcheckCurrentStatusdeviceModelUpdate implements ImonitorControllerAction {
	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;
	@Override
	public String executeCommand(ActionModel actionModel) {
		
		this.actionModel = actionModel;
		XStream stream = new XStream();
	//	DeviceManager deviceManager = new DeviceManager();
		
		
		
		Device device=actionModel.getDevice();
		
		
	
		//Schedule schedule = (Schedule) this.actionModel.getData();
		/*Device deviceFromDB=deviceManager.getDeviceWithConfigurationAndMake(device.getGeneratedDeviceId());
	
		//Kantha: Please DO NOT remove this line even though it seems redundant.
		deviceFromDB = (Device)stream.fromXML(stream.toXML(deviceFromDB));*/
		
		
		
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,"MONITOR_DEVICE_HEALTH"));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		device = actionModel.getDevice();
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		String generatedDeviceId = device.getGeneratedDeviceId();
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair("SCAN_HMD",""+2));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		LogUtil.info(messageInXml);
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
		return null;
	
	}


	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		LogUtil.error("I AM INSIDE health check device model executeSuccessResults COMMAND");
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = true;
		
		return null;// TODO Auto-generated method stub
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		LogUtil.error("I AM INSIDE health check device model executefailureResults COMMAND");
		this.resultExecuted = true;
		this.actionSuccess = false;
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
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}

}
