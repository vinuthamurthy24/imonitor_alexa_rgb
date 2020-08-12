package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.service.AlertsFromImvgServiceManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class IduTemperatureControlAction implements ImonitorControllerAction{
	private ActionModel actionModel;
	private boolean resultExecuted=false;
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
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		DeviceManager deviceManager = new DeviceManager();
		Device deviceFromDB = deviceManager.getDeviceWithConfiguration(generatedDeviceId);
		IndoorUnitConfiguration configuration=(IndoorUnitConfiguration) new DaoManager().get(deviceFromDB.getDeviceConfiguration().getId(), IndoorUnitConfiguration.class);
		String setpointvalue = configuration.getFanModeValue();
		if(setpointvalue == null || setpointvalue.equals("5"))
		{
			setpointvalue="2";
		}
	
		queue.add(new KeyValuePair(Constants.ZXT_AC_MODE,setpointvalue));
		queue.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_TYPE,setpointvalue));
		queue.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_VALUE,this.actionModel.getDevice().getCommandParam()));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
		return null;
		
	}
	
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		DeviceManager deviceManager = new DeviceManager();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		Device device = deviceManager.GetDeviceForFailureByGeneratedId(generatedDeviceId);
		this.actionModel.setDevice(device);
		return null;
	}
	
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		DeviceManager deviceManager = new DeviceManager();
		String commandParam = this.actionModel.getDevice().getCommandParam();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		String gateWayId = this.actionModel.getDevice().getGateWay().getMacId();
		Device device = deviceManager.updateCommadParamForIDU(generatedDeviceId, gateWayId, commandParam);
		this.actionModel.setDevice(device);
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		return null;
	}
	
	@Override
	public ActionModel getActionModel() {
		return this.actionModel;
	}

	
	@Override
	public boolean isResultExecuted() {
		return this.resultExecuted;
	}
	
	@Override
	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();
		keyValuePairs.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_TYPE,"2"));
		keyValuePairs.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_VALUE, actionDataHolder.getLevel()));
		return keyValuePairs;
	}

	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return false;
	}

}
