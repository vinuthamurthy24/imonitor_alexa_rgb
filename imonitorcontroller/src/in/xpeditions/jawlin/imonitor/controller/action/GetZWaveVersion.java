package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class GetZWaveVersion implements ImonitorControllerAction{
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
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.GETZWAVE_VERSION));
		String transId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, macId));
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
		ArrayList<String> gatewayInfo = new ArrayList<String>();
		String type = IMonitorUtil.commandId(queue, Constants.TYPE);
		String zWaveVersion = IMonitorUtil.commandId(queue, Constants.VERSION);
		String homeId = IMonitorUtil.commandId(queue, Constants.HOME_ID);
		String nodeId = IMonitorUtil.commandId(queue, Constants.NODE_ID);
		String chipType = IMonitorUtil.commandId(queue, Constants.CHIP_TYPE);
		String numberOfNodes = IMonitorUtil.commandId(queue, Constants.NUM_NODES);
		String chipVersion = IMonitorUtil.commandId(queue, Constants.CHIP_VER);
		String capFlag = IMonitorUtil.commandId(queue, Constants.CAP_FLAG);		
		String zWaveSeries = IMonitorUtil.commandId(queue, Constants.ZWSER_VER);
		gatewayInfo.add(type);
		if(type.equalsIgnoreCase("MASTERSLAVE")){
			
			String slaveCount = IMonitorUtil.commandId(queue, Constants.SLAVECOUNT);
			gatewayInfo.add(slaveCount);
		}
		gatewayInfo.add(zWaveVersion);
		gatewayInfo.add(homeId);
		gatewayInfo.add(nodeId);
		gatewayInfo.add(chipType);
		gatewayInfo.add(numberOfNodes);
		gatewayInfo.add(chipVersion);
		gatewayInfo.add(capFlag);
		gatewayInfo.add(zWaveSeries);
		
		String xml = new XStream().toXML(gatewayInfo);
		
		this.actionModel.setData(xml);
		this.resultExecuted = true;
		this.actionSuccess = true;
		return null;
	}
	
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		this.resultExecuted = true;
		String zWaveVersion = IMonitorUtil.commandId(queue, Constants.FAILURE_REASON);
		this.actionModel.setData(zWaveVersion);
		this.actionSuccess = true;
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
