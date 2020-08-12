/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
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
public class UpdateModelNumberAction implements ImonitorControllerAction {

	private ActionModel actionModel;
	private boolean resultExecuted;
	private boolean actionSuccess;
	private String pollingInerval;
	private boolean pollingIntervalSet;

	public UpdateModelNumberAction(String pollingInterval) {
		this.setPollingInerval(pollingInterval);
		this.setPollingIntervalSet(true);
	}

	public UpdateModelNumberAction() {
		this.setPollingIntervalSet(false);
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeCommand(in.xpeditions.jawlin.imonitor.controller.action.ActionModel)
	 */
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

		Make make = (Make) this.actionModel.getData();
		
		if(device.getDeviceType().getName().equalsIgnoreCase(Constants.Z_WAVE_AC_EXTENDER)){
			queue.add(new KeyValuePair(Constants.ZXT_AC_CODE_NO,make.getNumber()));
			if(getPollingIntervalSet()){
				queue.add(new KeyValuePair(Constants.POLL_INTERVAL,this.pollingInerval));
			}
		}else if(device.getDeviceType().getName().equalsIgnoreCase("Z_WAVE_AV_BLASTER")){
			queue.add(new KeyValuePair("ZXT_AV_BLASTER_CODE_NO",make.getNumber()));
		}
				
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

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		this.resultExecuted = true;
		this.actionSuccess = false;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		
		//String modelNumber = this.actionModel.getDevice().getModelNumber();
		//String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		//Device device = deviceManager.updateModelNumberOfDeviceByGeneratedId(generatedDeviceId, modelNumber);
		//this.actionModel.setDevice(device);
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = true;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}

	public String getPollingInerval() {
		return pollingInerval;
	}

	public void setPollingInerval(String pollingInerval) {
		this.pollingInerval = pollingInerval;
	}

	public boolean getPollingIntervalSet() {
		return this.pollingIntervalSet;
	}

	public void setPollingIntervalSet(boolean pollingIntervalSet) {
		this.pollingIntervalSet = pollingIntervalSet;
	}

}
