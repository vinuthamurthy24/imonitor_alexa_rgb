package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class AllonoffAction implements ImonitorControllerAction {

	
	private ActionModel actionModel;
	private boolean resultExecuted = false;
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Device device = actionModel.getDevice();
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		String trasactionId = IMonitorUtil.generateTransactionId();
		
		
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.SWITCH_ALL));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		queue.add(new KeyValuePair(Constants.SWITCH_ALL,""+device.getGateWay().getAllOnOffStatus()));
		
		
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
		
		GatewayManager gwMng=new GatewayManager();
		
		gwMng.UpdateAllOnOffStates(this.actionModel.getDevice().getGateWay());
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		this.resultExecuted = false;
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
		return this.actionModel;
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
		return resultExecuted;
	}

	public void setActionModel(ActionModel actionModel) {
		this.actionModel = actionModel;
	}

	public void setResultExecuted(boolean resultExecuted) {
		this.resultExecuted = resultExecuted;
	}

}
