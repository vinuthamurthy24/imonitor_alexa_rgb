/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;


import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class DeleteUser implements ImonitorControllerAction {

	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;

	// **************************************************** sumit start *****************************************************
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		//XStream stream = new XStream();
		//LogUtil.info("start add user");
		User user=actionModel.getUser();
		GateWay gateway= (GateWay) actionModel.getData();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DELETE_USR));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,gateway.getMacId()));
		queue.add(new KeyValuePair(Constants.USR_ID,user.getId()+""));
		
			
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		

		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = gateway.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
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
		this.resultExecuted = true;
		this.actionSuccess = false;
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
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}

}
