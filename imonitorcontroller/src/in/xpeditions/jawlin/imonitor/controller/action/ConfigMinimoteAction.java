package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MinimoteConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class ConfigMinimoteAction implements ImonitorControllerAction{
	
	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;
	
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		this.actionModel = actionModel;
		XStream stream = new XStream();
		Device device=actionModel.getDevice();
		MinimoteConfig minimote = new MinimoteConfig();
		minimote = (MinimoteConfig)device.getDeviceConfiguration();
		if(minimote.getButtonone().contains("HOME") || minimote.getButtonone().contains("STAY") || minimote.getButtonone().contains("AWAY")){
			String[] name = minimote.getButtonone().split("-");
			minimote.setButtonone(name[1]);
		}
		if(minimote.getButtontwo().contains("HOME") || minimote.getButtontwo().contains("STAY") || minimote.getButtontwo().contains("AWAY")){
			String[] name = minimote.getButtontwo().split("-");
			minimote.setButtontwo(name[1]);
		}
		if(minimote.getButtonthree().contains("HOME") || minimote.getButtonthree().contains("STAY") || minimote.getButtonthree().contains("AWAY")){
			String[] name = minimote.getButtonthree().split("-");
			minimote.setButtonthree(name[1]);
		}
		if(minimote.getButtonfour().contains("HOME") || minimote.getButtonfour().contains("STAY") || minimote.getButtonfour().contains("AWAY")){
			String[] name = minimote.getButtonfour().split("-");
			minimote.setButtonfour(name[1]);
		}
		String generatedDeviceId = device.getGeneratedDeviceId();
		String macId = device.getGateWay().getMacId();
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWayFromDB = null;
		gateWayFromDB = gatewayManager.getGateWayWithAgentByMacId(macId);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_UPDATE_MODEL));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,macId));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.B1,minimote.getButtonone()));
		queue.add(new KeyValuePair(Constants.B2,minimote.getButtontwo()));
		queue.add(new KeyValuePair(Constants.B3,minimote.getButtonthree()));
		queue.add(new KeyValuePair(Constants.B4,minimote.getButtonfour()));
		String valueInxml = IMonitorUtil.convertQueueIntoXml(queue);
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = gateWayFromDB.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(valueInxml, ip , port);
		IMonitorSession.put(transactionId, this);
		return null;
	}
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
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
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ActionModel getActionModel() {
		return actionModel;
	}
	
	@Override
	public boolean isResultExecuted() {
		return resultExecuted;
	}
	
	@Override
	public boolean isActionSuccess() {
		return actionSuccess;
	}
	
	
	

}
