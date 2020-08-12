package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Action;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ConfigureAddActions  implements ImonitorControllerAction{

	
	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;
	
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		
		// TODO Auto-generated method stub
		Action action =  (Action) actionModel.getData();
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = action.getGateWay();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.ADD_ACTIONS));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateWay.getMacId()));
		if (action.getFunctions().getName().equals("SCENARIO"))
		{
			queue.add(new KeyValuePair(Constants.ACT_PARAM, "" + action.getId()+":"+action.getConfigurationId()+":"+action.getFunctions().getId()));
		}
		else {
			queue.add(new KeyValuePair(Constants.ACT_PARAM, "" + action.getId()+":"+action.getDevice().getDeviceId()+":"+action.getFunctions().getId()));
		}
		RequestProcessor requestProcessor = new RequestProcessor();
		gateWay = gatewayManager.getGateWayWithAgentByMacId(gateWay.getMacId());
		Agent agent = gateWay.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		LogUtil.info("message to gateway" + messageInXml);
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(transactionId, this);
		return null;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		// TODO Auto-generated method stub

		
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = true;
		
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isResultExecuted() {
		// TODO Auto-generated method stub
		return resultExecuted;
	}

	@Override
	public ActionModel getActionModel() {
		// TODO Auto-generated method stub
		return actionModel;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return this.actionSuccess;
	
	}

}
