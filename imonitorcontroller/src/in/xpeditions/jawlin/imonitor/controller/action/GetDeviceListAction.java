package in.xpeditions.jawlin.imonitor.controller.action;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GetDeviceListAction implements ImonitorControllerAction{

	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		this.actionModel = actionModel;
		XStream stream = new XStream();
		String xml = (String)actionModel.getData();
		String macId = (String) stream.fromXML(xml);
		
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(macId);
		String value = "1";
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.RMV_DEADNODE));
		String transId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, macId));
		queue.add(new KeyValuePair(Constants.GET_DEVICELIST, value));
		String valueInxml = IMonitorUtil.convertQueueIntoXml(queue);
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = gateWay.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(valueInxml, ip , port);
		IMonitorSession.put(transId, this);
		return null;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		// TODO Auto-generated method stub
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		String deviceNodeIds = IMonitorUtil.commandId(queue, Constants.LIST);
		this.actionModel.setData(deviceNodeIds);
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
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionModel getActionModel() {
		return actionModel;
	}

	public boolean isResultExecuted() {
		return resultExecuted;
	}

	

	public boolean isActionSuccess() {
		return actionSuccess;
	}
	
}
