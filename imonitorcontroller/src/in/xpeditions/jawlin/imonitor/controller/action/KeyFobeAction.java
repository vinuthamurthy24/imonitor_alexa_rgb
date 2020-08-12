package in.xpeditions.jawlin.imonitor.controller.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

public class KeyFobeAction implements ImonitorControllerAction{
	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;
	
	@SuppressWarnings("unchecked")
	@Override
	public String executeCommand(ActionModel actionModel) {
		
		this.actionModel = actionModel;
		//Scenario scenario = (Scenario) this.actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.KEY_CONFIGURATION));
		GatewayManager gatewayManager = new GatewayManager();
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		ArrayList<KeyConfiguration> keyconfigObjects=(ArrayList<KeyConfiguration>) actionModel.getData();
		XStream stream = new XStream();
		//LogUtil.info("Objects to gateway : "+stream.toXML(keyconfigObjects));
		Device device = actionModel.getDevice();
		DeviceManager deviceManager = new DeviceManager();
		device = deviceManager.getDevice(device.getId());
		GateWay gateway = gatewayManager.getGateWayByDevice(device);
		
		queue.add(new KeyValuePair(Constants.IMVG_ID,gateway.getMacId()));
		
		queue.add(new KeyValuePair(Constants.DEVICE_ID,device.getGeneratedDeviceId()));
		//Edit Apoorva
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.KN,""+"1"+";"+keyconfigObjects.get(0).getKeyCode()+";"+keyconfigObjects.get(0).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"1"+";"+keyconfigObjects.get(1).getKeyCode()+";"+keyconfigObjects.get(1).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"2"+";"+keyconfigObjects.get(2).getKeyCode()+";"+keyconfigObjects.get(2).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"2"+";"+keyconfigObjects.get(3).getKeyCode()+";"+keyconfigObjects.get(3).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"3"+";"+keyconfigObjects.get(4).getKeyCode()+";"+keyconfigObjects.get(4).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"3"+";"+keyconfigObjects.get(5).getKeyCode()+";"+keyconfigObjects.get(5).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"4"+";"+keyconfigObjects.get(6).getKeyCode()+";"+keyconfigObjects.get(6).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"4"+";"+keyconfigObjects.get(7).getKeyCode()+";"+keyconfigObjects.get(7).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"5"+";"+keyconfigObjects.get(8).getKeyCode()+";"+keyconfigObjects.get(8).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"5"+";"+keyconfigObjects.get(9).getKeyCode()+";"+keyconfigObjects.get(9).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"6"+";"+keyconfigObjects.get(10).getKeyCode()+";"+keyconfigObjects.get(10).getAction().getId() ));
		queue.add(new KeyValuePair(Constants.KN,""+"6"+";"+keyconfigObjects.get(11).getKeyCode()+";"+keyconfigObjects.get(11).getAction().getId() ));
		
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		
		
        gateway = gatewayManager.getGateWayWithAgentByMacId(gateway.getMacId());
//		First issues the command to switch on the A/C
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = gateway.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
		
		return null;

	}
	
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = true;
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		if(tId != null)IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = false;
		return null;
	}
	
	public ActionModel getActionModel() {
		return actionModel;
	}
	
	public void setActionModel(ActionModel actionModel) {
		this.actionModel = actionModel;
	}
	
	public boolean isResultExecuted() {
		return resultExecuted;
	}
	
	public void setResultExecuted(boolean resultExecuted) {
		this.resultExecuted = resultExecuted;
	}
	
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}
	
	public void setActionSuccess(boolean actionSuccess) {
		this.actionSuccess = actionSuccess;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		return null;
	}
}
