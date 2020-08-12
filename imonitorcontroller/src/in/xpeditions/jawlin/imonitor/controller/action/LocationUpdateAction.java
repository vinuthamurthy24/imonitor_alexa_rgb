/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertsAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AgentManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;
import in.xpeditions.jawlin.imonitor.util.UpdatorModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
// Parishod added whole class
public class LocationUpdateAction implements ImonitorControllerAction {
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

	
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Location location = (Location) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, "UPDATE_LOCATION"));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		Customer  customer= location.getCustomer();
		GatewayManager gateWaymanager = new GatewayManager();
		GateWay gateWay = null;
		try{
			gateWay = gateWaymanager.getGateWayByCustomer(customer);
			queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));				
		}catch (Exception e) {
			LogUtil.info("Some serious error while trying to modify location. Revert to origonal location.", e);
			// TODO: handle exception
		}
		GateWay gatewaywithagent =  gateWaymanager.getGateWayWithAgent(gateWay.getMacId(), customer.getCustomerId());

		queue.add(new KeyValuePair("DEVICE_LOCATION", location.getName()));
		queue.add(new KeyValuePair("DEVICE_LOCATION_ID", ""+ location.getId()));
		
		this.actionModel.setQueue(queue);
		String ip = gatewaywithagent.getAgent().getIp();
		int port = gatewaywithagent.getAgent().getControllerReceiverPort();
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		IMonitorSession.put(transactionId, this);
		requestProcessor.processRequest(messageInXml, ip , port);
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
//		If it is a failure results, pass it. Nothing more to do...
		Location location = (Location) this.actionModel.getData();
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		if(tId != null)IMonitorSession.remove(tId);
		this.actionModel.setMessage("Update Location with name '" + location.getName() + "' is failed. The reason is " + IMonitorUtil.commandId(queue, Constants.FAILURE_REASON));
		this.resultExecuted = true;
		this.actionSuccess = false;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		Location location = (Location) this.actionModel.getData();
		this.actionModel.setMessage("Location with name '" + location.getName() + "' is updated");
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
//Parishod end
