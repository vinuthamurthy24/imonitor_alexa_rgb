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
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DashboardManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RuleManager;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;
import in.xpeditions.jawlin.imonitor.util.UpdatorModel;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class DashboardAlertForReachedLimitAction implements ImonitorControllerAction {
	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;
	private String alerttype;
	private long limit;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		
		this.actionModel = actionModel;
		XStream stream = new XStream();
		
	
		Device device=actionModel.getDevice();
		//String data =  (String) this.actionModel.getData();
		List<Object> data= (List<Object>)actionModel.getData();
		limit=(Long) data.get(2);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.PWR_LMT_REACHED));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,device.getDeviceId()));
	    alerttype=""+data.get(1);
		queue.add(new KeyValuePair(alerttype,""+data.get(0)));
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
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
				String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		String macId = IMonitorUtil.commandId(queue,Constants.IMVG_ID);
		GatewayManager gatewaymanager=new GatewayManager();
		GateWay gateway=gatewaymanager.getGateWayByMacId(macId);
		Customer customer=gateway.getCustomer();
		DashboardManager dashboardManager = new DashboardManager();
		if(alerttype.equalsIgnoreCase("PWR_LMT_WARNING"))
		{
			dashboardManager.savewarningPowerlimit(limit,customer);
		}
		else if(alerttype.equalsIgnoreCase("PWR_LMT_REACHED"))
		{
			dashboardManager.saveReachedPowerlimit(limit,customer);
		}
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = true;
		
		return null;// TODO Auto-generated method stub
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		
		String reason = IMonitorUtil.commandId(queue, Constants.FAILURE_REASON);
		IMonitorSession.get(reason);
		//LogUtil.info("failure reason=="+reason);
		this.actionModel.setMessage(reason);
		String macId = IMonitorUtil.commandId(queue,Constants.IMVG_ID);
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
