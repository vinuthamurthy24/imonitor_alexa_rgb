package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class UpdateNeighbor implements ImonitorControllerAction{

	private ActionModel actionModel;
	private boolean isResultSuccess = false;
	private boolean actionSuccess;
	
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
	
		this.actionModel=actionModel;
		Device device=actionModel.getDevice();
		
		DeviceManager deviceManager=new DeviceManager();
		Device deviceDB = deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(device.getGeneratedDeviceId());
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		GateWay gateWay = deviceDB.getGateWay();
		queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,deviceDB.getGeneratedDeviceId()));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair(Constants.NEIGHBOUR_UPDATE, ""+device.getSwitchType()));
		queue.add(new KeyValuePair(Constants.PARAM_VAL,"0"));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = gateWay.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		requestProcessor.processRequest(messageInXml, ip , port);
		
		IMonitorSession.put(trasactionId, this);
		return null;
		
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		
		this.isResultSuccess = true;
		this.actionSuccess = true;
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		
		this.actionSuccess = false;
		return null;
	}
	
	@Override
	public ActionModel getActionModel() {
		return this.actionModel;
	}


	public boolean isResultSuccess() {
		return isResultSuccess;
	}


	public void setResultSuccess(boolean isResultSuccess) {
		this.isResultSuccess = isResultSuccess;
	}
	
	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}

	@Override
	public boolean isResultExecuted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		// TODO Auto-generated method stub
		return null;
	}
}
