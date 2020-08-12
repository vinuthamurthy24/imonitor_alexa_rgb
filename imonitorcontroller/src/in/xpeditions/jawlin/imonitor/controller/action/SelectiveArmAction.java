/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
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
public class SelectiveArmAction implements ImonitorControllerAction {

	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;

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
		this.actionModel = actionModel;
		GateWay gateWay = (GateWay) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.STAY_DEVICES));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		Agent agent = gateWay.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		IMonitorSession.put(transactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);
		return null;
	}
	
	//****************************************** sumit start HANDLE STAY-DEVICES-PROGRESS-FAIL *****************************************************

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		//POPULATE APPROPRIATE DEVICES ON GETTING STAY_DEVICES_PROGRESS_FAIL
		this.resultExecuted = true;
		this.actionSuccess = false;
	
/*		String macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
		//We are assuming that the device list will always be sent in the FAILURE message.
		String deviceIds = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		List<String> deviceIdList = new ArrayList<String>();
		String[] gendevcIds = deviceIds.split(",");
		String fNameInMessage ="";
		try {
			for(int i=0; i<gendevcIds.length; i++){
				int idx = gendevcIds[i].indexOf('-');
				String dId = gendevcIds[i].substring(idx+1);
				deviceIdList.add(dId);
				LogUtil.info("deviceId ="+ dId);
			}
			//call a function(in deviceManager) that will return the friendly name for the deviceId.
			List<String> friendlyName = DeviceManager.getDeviceFriendlyNameByDeviceId(macId, deviceIdList);
			//populate the retrieved friendlyNames.
			for(int i=0; i<friendlyName.size(); i++){
				LogUtil.info("C");
				fNameInMessage += (i+1)+"."+friendlyName.get(i).toString();
				fNameInMessage += " ";
			}
			this.actionModel.setMessage(IMonitorUtil.commandId(queue, Constants.FAILURE_REASON)+" for devices :"+ fNameInMessage ) ;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("Got an exception :"+e.getMessage());
			LogUtil.info(e.getMessage(),e);
		}*/
		String message = IMonitorUtil.commandId(queue, Constants.FAILURE_REASON);
		message += ": "+ IMonitorUtil.commandId(queue, Constants.DEVICE_NAME);
		this.actionModel.setMessage(message);
		return null;
	}

	// ********************************************************* SUMIT END ***********************************************************************
	
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

