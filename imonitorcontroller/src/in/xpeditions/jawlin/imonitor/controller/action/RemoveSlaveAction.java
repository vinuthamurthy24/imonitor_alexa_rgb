package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ModbusSlave;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class RemoveSlaveAction implements ImonitorControllerAction
{
	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		
		Device device =  (Device) actionModel.getDevice();
		XStream stream=new XStream();
		DeviceManager deviceManager=new DeviceManager();
		ModbusSlave modbusSlave=deviceManager.getModbusSlaveDevice(device.getId());
		
		long SlaveId =  modbusSlave.getSlaveId();
		String sid=Long.toString(SlaveId);
		GateWay gateWay=device.getGateWay();
		
		GatewayManager gatewayManager = new GatewayManager();
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.REMOVE_VIASLAVE));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, device.getGateWay().getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID, modbusSlave.getHAIF_Id()));
		queue.add(new KeyValuePair(Constants.SLAVE_ID,sid));
		RequestProcessor requestProcessor = new RequestProcessor();
		gateWay = gatewayManager.getGateWayWithAgentByMacId(gateWay.getMacId());
		
		Agent agent = gateWay.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		
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
		
		this.resultExecuted=false;
		this.actionSuccess=false;
		
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
