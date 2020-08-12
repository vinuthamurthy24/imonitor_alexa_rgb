/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.FirmWare;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.FirmWareManager;
import  in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class FirmwareUpgradeAction implements ImonitorControllerAction {

	private ActionModel actionModel;
	private boolean resultExecuted;
	private boolean isResultSuccess = false;

	public FirmwareUpgradeAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeCommand(in.xpeditions.jawlin.imonitor.controller.action.ActionModel)
	 */
	@Override
	public String executeCommand(ActionModel actionModel) {
		
		this.actionModel = actionModel;
		String xml = (String)actionModel.getData();
		XStream stream = new XStream();
		String[] split = xml.split("_");
		String macId = (String) stream.fromXML(split[0]);
		long firmId = (Long) stream.fromXML(split[1]);
		FirmWareManager firmWareManager = new FirmWareManager();
		FirmWare firmWare = firmWareManager.getFirmWareById(firmId);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(macId);

		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.DEVICE_CONTROL));
		String transId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, macId));
		queue.add(new KeyValuePair(Constants.DEVICE_ID, macId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM, Constants.START));
		queue.add(new KeyValuePair(Constants.FIRMWARE_UPGRADE, "1"));
		Agent firmWareAgent = firmWare.getAgent();
		int ftpPort = firmWareAgent.getFtpPort();
		ftpPort = 21;
		String ftpUrl = firmWare.getAgent().getFtpIp() + ":"
				+ ftpPort;
		queue.add(new KeyValuePair(Constants.FTP_IP_PORT, ftpUrl));
		queue.add(new KeyValuePair(Constants.FTP_PATH_FILE_NAME, firmWare
				.getFile()));
		queue.add(new KeyValuePair(Constants.FTP_USERNAME, firmWareAgent
				.getFtpUserName()));
		queue.add(new KeyValuePair(Constants.FTP_PASSWORD, firmWareAgent
				.getFtpPassword()));
		queue.add(new KeyValuePair(Constants.HTTP_PATH, ControllerProperties.getProperties().get(Constants.imvg_upload_context_path)+firmWare.getFile()));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM, Constants.END));

		String valueInxml = IMonitorUtil.convertQueueIntoXml(queue);
		RequestProcessor requestProcessor = new RequestProcessor();
		requestProcessor.processRequest(valueInxml, gateWay);

		
		IMonitorSession.put(transId, this);
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		this.resultExecuted = true;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {

		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.actionModel.setMessage("Gateway upgradation is in progress.");
		this.resultExecuted = true;
		this.isResultSuccess = true;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#getActionModel()
	 */
	@Override
	public ActionModel getActionModel() {
		return this.actionModel;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#isResultExecuted()
	 */
	@Override
	public boolean isResultExecuted() {
		return this.resultExecuted;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
//		ZXT_AC_SET_POINT_VALUE
		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();
		return keyValuePairs;
	}

	@Override
	public boolean isActionSuccess() {
		return isResultSuccess;
	}

}
