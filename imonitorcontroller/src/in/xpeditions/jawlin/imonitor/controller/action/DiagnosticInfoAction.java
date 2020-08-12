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

public class DiagnosticInfoAction implements ImonitorControllerAction{

	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;
	
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		this.actionModel = actionModel;
		GatewayManager gatewayManager = new GatewayManager();
	    GateWay gateway1 = (GateWay)actionModel.getData();
	    String macId = gateway1.getMacId();
	    GateWay gateway = gatewayManager.getGateWayWithAgentByMacId(macId);
	    Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
	    queue.add(new KeyValuePair(Constants.CMD_ID,Constants.GET_DIAGNOSTIC_DETAIL));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, gateway.getMacId()));
		queue.add(new KeyValuePair(Constants.FTP_IP_PORT, gateway.getAgent().getFtpIp()));
		queue.add(new KeyValuePair(Constants.FILE_PATH,"imvg/testfile.txt"));
		queue.add(new KeyValuePair(Constants.FTP_USERNAME, gateway.getAgent().getFtpUserName()));
		queue.add(new KeyValuePair(Constants.FTP_PASSWORD, gateway.getAgent().getFtpPassword()));
		String valueInxml = IMonitorUtil.convertQueueIntoXml(queue);
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = gateway.getAgent();
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
		ArrayList<String> diagnosticInfo = new ArrayList<String>();
		String broadcastPort = IMonitorUtil.commandId(queue, Constants.MIDBROADCAST_PORT);
		String midLoginPort = IMonitorUtil.commandId(queue, Constants.MIDL_LOGIN_PORT);
		String lftpDownload = IMonitorUtil.commandId(queue, Constants.LFTPDOWNLOAD);
		diagnosticInfo.add(broadcastPort);
		diagnosticInfo.add(midLoginPort);
		diagnosticInfo.add(lftpDownload);
		String xml = new XStream().toXML(diagnosticInfo);
		this.actionModel.setData(xml);
		this.resultExecuted = true;
		this.actionSuccess = true;
		return null;
	}
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		this.resultExecuted = true;
		this.actionSuccess = false;
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
