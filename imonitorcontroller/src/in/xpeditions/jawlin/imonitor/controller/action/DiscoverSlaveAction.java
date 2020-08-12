package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class DiscoverSlaveAction implements ImonitorControllerAction
{
	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		
		Device device =  (Device) actionModel.getDevice();
		GateWay gateWay=device.getGateWay();
		
		GatewayManager gatewayManager = new GatewayManager();
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.DISCOVER_SLAVE));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, device.getGateWay().getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID, device.getGeneratedDeviceId()));
		queue.add(new KeyValuePair(Constants.DISCOVERY_DURATION, "300"));
		queue.add(new KeyValuePair(Constants.DISCOVERY_MODE, "4"));
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

		XStream xStream = new XStream();
		
			
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
