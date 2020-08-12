package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



public class KeepAliveAction implements ImonitorControllerAction{

	private ActionModel actionModel;
	
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		//XStream stream = new XStream();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
	
		GateWay gateway= (GateWay) actionModel.getData();
		String macId = gateway.getMacId();
	
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.IMVG_KEEP_ALIVE_ACK));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, macId));
		
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		
		Agent agent = gateway.getAgent();
		
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);	
		return null;
	}


	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		// TODO Auto-generated method stub
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
		return false;
	}


	@Override
	public ActionModel getActionModel() {
		// TODO Auto-generated method stub
		return null;
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
		return false;
	}
	
}
