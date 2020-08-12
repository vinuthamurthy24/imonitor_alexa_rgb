/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author Coladi
 *
 */
public class GetStoredStreamAction implements ImonitorControllerAction {
	

	private ActionModel actionModel;
	private boolean resultExecuted = false;

	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Device device = this.actionModel.getDevice();
		GateWay gateWay = device.getGateWay();
		String ftpClient = gateWay.getAgent().getFtpIp() + ":" + gateWay.getAgent().getFtpPort();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.IPC_GET_STORED_STREAM));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		String macId = gateWay.getMacId();
		queue.add(new KeyValuePair(Constants.IMVG_ID,macId));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,device.getGeneratedDeviceId()));
		queue.add(new KeyValuePair(Constants.TIME_OFFSET,"45"));
		queue.add(new KeyValuePair(Constants.STREAM_DURATION,"30"));
		queue.add(new KeyValuePair(Constants.IMAGE_FTP_IP_PORT,ftpClient));
		queue.add(new KeyValuePair(Constants.FTP_USERNAME,gateWay.getAgent().getFtpUserName()));
		queue.add(new KeyValuePair(Constants.FTP_PASSWORD,gateWay.getAgent().getFtpPassword()));
		Customer customer = gateWay.getCustomer();
		String customerId = null;
		if(customer == null){
			GatewayManager gatewayManager = new GatewayManager();
			customerId = gatewayManager.getCustomerIdOfGateWay(macId);
		}else{
			customerId = customer.getCustomerId();
		}
		String imageName = "" + customerId + "/" + ControllerProperties.getProperties().get(Constants.STREAMS_DIR) + "/" +  "stream_" + trasactionId;
		queue.add(new KeyValuePair(Constants.FTP_PATH_FILE_NAME, imageName));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		this.actionModel.setQueue(queue);
		IMonitorSession.put(trasactionId, this);
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(messageInXml, ip , port);
		return null;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
//		We won't remove it from session, because we are expecting one stream upload against this command.
		this.resultExecuted = true;
		return null;
	}

	@Override
	public boolean isResultExecuted() {
		return this.resultExecuted;
	}

	@Override
	public ActionModel getActionModel() {
		return this.actionModel;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		String trasactionId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(trasactionId);
		this.resultExecuted = true;
		return null;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();
		keyValuePairs.add(new KeyValuePair(Constants.IPC_GET_STORED_STREAM, "1"));
		keyValuePairs.add(new KeyValuePair(Constants.TIME_OFFSET, "45"));
		keyValuePairs.add(new KeyValuePair(Constants.STREAM_DURATION, "5"));
		GateWay gateWay = actionDataHolder.getGateWay();
		Agent agent = gateWay.getAgent();
		Customer customer = gateWay.getCustomer();
		keyValuePairs.add(new KeyValuePair(Constants.FTP_IP_PORT, agent.getFtpIp() + ":" + agent.getFtpNonSecurePort()));
		String filePath = customer.getCustomerId() + "/" + ControllerProperties.getProperties().get(Constants.IMAGES_DIR) + IMonitorUtil.generateTransactionId();
		keyValuePairs.add(new KeyValuePair(Constants.FTP_PATH_FILE_NAME, filePath));
		keyValuePairs.add(new KeyValuePair(Constants.FTP_USERNAME, agent.getFtpUserName()));
		keyValuePairs.add(new KeyValuePair(Constants.FTP_PASSWORD, agent.getFtpPassword()));
		return keyValuePairs;
	}

	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return false;
	}
}
