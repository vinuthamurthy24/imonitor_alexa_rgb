/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
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
public class AcControlOnAction implements ImonitorControllerAction {

	private ActionModel actionModel;
	private boolean resultExecuted;

	public AcControlOnAction() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Queue<KeyValuePair> queueToSwitchOn = new LinkedList<KeyValuePair>();
		queueToSwitchOn.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queueToSwitchOn.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		Device device = actionModel.getDevice();
		String macId = device.getGateWay().getMacId();
		queueToSwitchOn.add(new KeyValuePair(Constants.IMVG_ID,macId));
		String generatedDeviceId = device.getGeneratedDeviceId();
		queueToSwitchOn.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queueToSwitchOn.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queueToSwitchOn.add(new KeyValuePair(Constants.ZXT_AC_MODE,"5"));
		queueToSwitchOn.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_TYPE,"2"));
		queueToSwitchOn.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_VALUE,"22"));
		queueToSwitchOn.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));	
		
//		First issues the command to switch on the A/C
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queueToSwitchOn);
		requestProcessor.processRequest(messageInXml, ip , port);
		//LogUtil.info("Message to switch On AC :"+messageInXml);
		
		IMonitorSession.put(trasactionId, this);
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		
		DeviceManager deviceManager = new DeviceManager();
		Device device = actionModel.getDevice();
		String macId = device.getGateWay().getMacId();
		String generatedDeviceId = device.getGeneratedDeviceId();
		
		String commandParam = device.getCommandParam();
		//LogUtil.info("commandParam"+commandParam); 
		
		//if(commandParam == null || commandParam.equals("0"))
		{
			commandParam = "22";
		
		}
		
		/******
		 * 
		 * Bhavya Commented this portion due to Temperature value sent on execute function
		 * 
		 * Queue<KeyValuePair> queueToSetTemp = new LinkedList<KeyValuePair>();		
		queueToSetTemp.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));		
		queueToSetTemp.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.generateTransactionId()));		
		queueToSetTemp.add(new KeyValuePair(Constants.IMVG_ID,macId));		
		queueToSetTemp.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));		
		queueToSetTemp.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queueToSetTemp.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_TYPE,"2"));
		queueToSetTemp.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_VALUE,commandParam));
		device.setCommandParam(commandParam);
		queueToSetTemp.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));

//		Then wait and issue the command to change the level.
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		
//		This time out can be moved to the success API.
		String waitTimeS = ControllerProperties.getProperties().get(Constants.AC_WAIT_DURATION);
		long waitTime = Long.parseLong(waitTimeS);
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			LogUtil.error("Error occured while waiting for the A/C to power on.");
		}
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queueToSetTemp);
		requestProcessor.processRequest(messageInXml, ip , port);
		//LogUtil.info("Message to control AC :"+messageInXml);
*/
		
		
		
		

		String fanmodes="2";
		Device deviceFromDeviceManager = deviceManager.updateCommadParamAndFanModesOfDeviceByGeneratedId(generatedDeviceId, macId, device.getCommandParam(),fanmodes);
		this.actionModel.setDevice(deviceFromDeviceManager);
		
		
		
		
		/*Device deviceFromDeviceManager = deviceManager.updateCommadParamOfDeviceByGeneratedId(generatedDeviceId, macId, device.getCommandParam());
		this.actionModel.setDevice(deviceFromDeviceManager);*/

		return null;
	}
// ********************************************************************** sumit end ************************************************************
	
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
		keyValuePairs.add(new KeyValuePair(Constants.ZXT_AC_MODE, "5"));
		return keyValuePairs;
	}

	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return false;
	}

}
