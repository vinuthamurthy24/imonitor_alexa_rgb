/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExcuteUseCommandAction implements ImonitorControllerAction {

	private boolean resultExecuted;
	private ActionModel actionModel;
	private boolean isResultSuccess = false;
	private boolean actionSuccess;

	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		this.actionModel = actionModel;
		Device device=actionModel.getDevice();
		GateWay gateWay=device.getGateWay();
		String Command=(String)actionModel.getData();
		String Timeout=(String)actionModel.getTimeout();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.EXCUTE_USER_COMMAND));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,gateWay.getMacId()));
		queue.add(new KeyValuePair(Constants.COMMAND_NAME,Command));
		queue.add(new KeyValuePair(Constants.TIME_OUT,Timeout));
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = gateWay.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();//2030;
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
		return null;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue){
		
		
		String Data = IMonitorUtil.commandId(queue, Constants.DATA);
		Data=java.net.URLDecoder.decode(Data);
		
		this.actionModel.setMessage("Sucess~ SucessFully Excuted Command~ "+Data);
		this.resultExecuted = true;
		this.isResultSuccess = true;
		this.actionSuccess = true;
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		return null;
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue){
		this.actionModel.setMessage("Failure~ Failed To Excuted Command :"+actionModel.getData());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}

	public boolean isResultSuccess() {
		return isResultSuccess;
	}

	public void setResultSuccess(boolean isResultSuccess) {
		this.isResultSuccess = isResultSuccess;
	}

	public void setResultExecuted(boolean resultExecuted) {
		this.resultExecuted = resultExecuted;
	}

}
