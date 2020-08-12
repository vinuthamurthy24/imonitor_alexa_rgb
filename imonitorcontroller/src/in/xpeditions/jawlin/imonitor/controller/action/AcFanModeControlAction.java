/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LCDRemoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
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

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class AcFanModeControlAction implements ImonitorControllerAction {

	private ActionModel actionModel;
	private boolean resultExecuted=false;

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeCommand(in.xpeditions.jawlin.imonitor.controller.action.ActionModel)
	 */
	@Override
	public String executeCommand(ActionModel actionModel) {
		XStream stream = new XStream();
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
		acConfiguration acConfiguration = (acConfiguration) device.getDeviceConfiguration();
		String FanMode = acConfiguration.getFanModeValue();
		String FanModevalue="1";
		if(FanMode.equals("1"))
		{
			FanModevalue="1";	
		}
		else if(FanMode.equals("2"))
		{
			FanModevalue="5";	
		}
		else if(FanMode.equals("3"))
		{
			FanModevalue="3";	
		}
		queue.add(new KeyValuePair(Constants.ZXT_AC_FAN_MODE,FanModevalue));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		
//		First issues the command to switch on the A/C
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		requestProcessor.processRequest(messageInXml, ip , port);
		
		IMonitorSession.put(trasactionId, this);
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		DeviceManager deviceManager = new DeviceManager();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		Device device = deviceManager.GetDeviceForFailureByGeneratedId(generatedDeviceId);
		this.actionModel.setDevice(device);
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		XStream stream = new XStream();
		DeviceManager deviceManager = new DeviceManager();
		acConfiguration acConfiguration = (acConfiguration) this.actionModel.getDevice().getDeviceConfiguration();
		String fanmode = acConfiguration.getFanMode();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		String gateWayId = this.actionModel.getDevice().getGateWay().getMacId();
		Device device = deviceManager.updateFanModesOfDeviceByGeneratedId(generatedDeviceId, gateWayId,acConfiguration.getFanModeValue(),1);
		this.actionModel.setDevice(device);
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		
		//Updating status to Alexa
		/*LogUtil.info("received syccess");
		try {
			LogUtil.info("received syccess");
			AlertsFromImvgServiceManager alertService = new AlertsFromImvgServiceManager();
			alertService.checkTokenAndUpdateDeviceToAlexaEndpoint(gateWayId,device,device.getCommandParam(),fanmode);
			LogUtil.info("received syccess");
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#getActionModel()
	 */
	@Override
	public ActionModel getActionModel() {
		return this.actionModel;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#isResultExecuted()
	 */
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
