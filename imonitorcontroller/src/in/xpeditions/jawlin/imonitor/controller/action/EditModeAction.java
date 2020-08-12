/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class EditModeAction implements ImonitorControllerAction {

	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;
	
	//sumit start: Alert Device Feature
	private String alertDevice;
	public EditModeAction (String alertDevice){
		if(alertDevice.equalsIgnoreCase(Constants.NO_DEVICE)){
			this.alertDevice = Constants.NO_DEVICE;
		}else{
			this.alertDevice = alertDevice;
		}
		
	}
	//sumit end: Alert Device Feature

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#createImvgConfigParams(in.xpeditions.jawlin.imonitor.controller.action.ActionDataHolder)
	 */
	@Override
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeCommand(in.xpeditions.jawlin.imonitor.controller.action.ActionModel)
	 */
	@Override
	public String executeCommand(ActionModel actionModel) {
		try {
			this.actionModel = actionModel;
			GatewayManager gatewayManager = new GatewayManager();
			GateWay gateWay = null;
			GateWay gateWayFromDB = null;
			DeviceManager deviceManager = new DeviceManager();
			List<Device> devices = (List<Device>) this.actionModel.getData();
			/* LogUtil.info(devices.size());
			LogUtil.info("Before: ");
			for(Device d : devices){
				LogUtil.info(d.getGeneratedDeviceId()+" :: "+d.getMode());
			} */
			gateWay = devices.get(0).getGateWay();
			gateWayFromDB = gatewayManager.getGateWayWithAgentByMacId(gateWay.getMacId());
			
			List<Device> devicesFromDB  = deviceManager.getDeviceByGateWay(gateWayFromDB);

			for(Device d1 : devices){
				for(Device d2 : devicesFromDB){
					if(d1.getGeneratedDeviceId().equalsIgnoreCase(d2.getGeneratedDeviceId())){
						//LogUtil.info("d1: "+d1.getGeneratedDeviceId()+" :: "+d1.getMode());
						//LogUtil.info("d2: "+d1.getGeneratedDeviceId()+" :: "+d2.getMode());
						d2.setMode(d1.getMode());
					}
				}
			}
			/* LogUtil.info("After: ");
			for(Device d : devicesFromDB){
				LogUtil.info(d.getGeneratedDeviceId()+" :: "+d.getMode());
			} */
			
			String commandParam = "1";// sumit TBD: devices.get(0).getCommandParam();
			Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
			queue.add(new KeyValuePair(Constants.CMD_ID, Constants.EDIT_MODE));
			String transactionId = IMonitorUtil.generateTransactionId();
			queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
			queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
			queue.add(new KeyValuePair(Constants.DELAY_HOME, gateWay.getDelayHome()));
			queue.add(new KeyValuePair(Constants.DELAY_STAY, gateWay.getDelayStay()));
			queue.add(new KeyValuePair(Constants.DELAY_AWAY, gateWay.getDelayAway()));
			//sumit added 1 line.
			queue.add(new KeyValuePair(Constants.ALERT_DEVICE, this.alertDevice));
			queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
			if(commandParam.equals("1")){
				queue.add(new KeyValuePair(Constants.SET_MODE,Constants.SYNC));
				//for(Device d: devices){
				for(Device d: devicesFromDB){
					//sumit start HANDLE SYNC FOR VIRTUAL DEVICE	....
					String genDeviceId = d.getGeneratedDeviceId();
					if((!genDeviceId.contains("HOME")) && (!genDeviceId.contains("STAY")) && (!genDeviceId.contains("AWAY"))){
						if(d.getDeviceType().getId() == 6){		//Only for DOOR_SENSORS
							queue.add(new KeyValuePair(Constants.DEV,Constants.START));
							queue.add(new KeyValuePair(Constants.DEVICE_ID,d.getGeneratedDeviceId()));
							queue.add(new KeyValuePair(Constants.MODE,d.getMode()));
							queue.add(new KeyValuePair(Constants.DEV,Constants.END));
						}
						
					}
				}
			}else{
				int count = 0;
				KeyValuePair key = new KeyValuePair(Constants.SET_MODE,Constants.UPDATE);
				queue.add(key);
				for(Device d: devices){
					
					if(!d.getMode().equals(d.getCurrentMode())){
						count++;
						queue.add(new KeyValuePair(Constants.DEV,Constants.START));
						queue.add(new KeyValuePair(Constants.DEVICE_ID,d.getGeneratedDeviceId()));
						queue.add(new KeyValuePair(Constants.MODE,d.getMode()));
						queue.add(new KeyValuePair(Constants.DEV,Constants.END));
					}
				}
				if(count == 0){
					queue.remove(key);
					queue.add(new KeyValuePair(Constants.SET_MODE,Constants.NO_CHANGE));
					
				}
			}
			queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
			
			
			
			Agent agent = gateWayFromDB.getAgent();
			String ip = agent.getIp();
			int port = agent.getControllerReceiverPort();
			RequestProcessor requestProcessor = new RequestProcessor();
			String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
			IMonitorSession.put(transactionId, this);
			requestProcessor.processRequest(messageInXml, ip , port);
		} catch (Exception e) {
			LogUtil.info("Got Exception while sending EDIT_MODE message to gateway: ", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		this.resultExecuted = true;
		this.actionSuccess = false;
		 this.actionModel.setMessage(IMonitorUtil.commandId(queue, Constants.FAILURE_REASON)) ;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
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
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}

}
