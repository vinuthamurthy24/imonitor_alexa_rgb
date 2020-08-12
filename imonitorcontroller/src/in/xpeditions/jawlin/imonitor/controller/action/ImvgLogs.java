/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class ImvgLogs implements ImonitorControllerAction {


private ActionModel actionModel;
private boolean resultExecuted = false;
private boolean actionSuccess;

	public String executeCommand(ActionModel actionModel) {
		int sendingvalue=0;
		this.actionModel = actionModel;
		Device device = this.actionModel.getDevice();
		String timeout=this.actionModel.getTimeout();
		String Selectedvalue=(String) this.actionModel.getData();
		if((Selectedvalue.equals("UPLOAD"))||(Selectedvalue.equals("DELETE")))
		{
			String temp[]=timeout.split(", ");
		
			for(int i=0;i<temp.length;i++)
			{
				
				if(temp[i].equals("1"))
				{
					sendingvalue+=1;
				}
				if(temp[i].equals("2"))
				{
					sendingvalue+=2;
				}
				if(temp[i].equals("3"))
				{
					sendingvalue+=4;
				}
				if(temp[i].equals("4"))
				{
					sendingvalue+=8;
				}
				if(temp[i].equals("5"))
				{
					sendingvalue+=16;
				}
				if(temp[i].equals("6"))
				{
					sendingvalue+=32;
				}
				if(temp[i].equals("7"))
				{
					sendingvalue+=64;
				}
			}
		}
	
		String macid = device.getGateWay().getMacId();
		
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayWithAgentByMacId(macid);
		
		String ftpClient = gateWay.getAgent().getFtpIp() + ":" + gateWay.getAgent().getFtpNonSecurePort();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		String macIdfromdb = gateWay.getMacId();
		queue.add(new KeyValuePair(Constants.IMVG_ID,macIdfromdb));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,macIdfromdb));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		if((Selectedvalue.equals("UPLOAD")))
		{
		queue.add(new KeyValuePair(Constants.GET_IMVGLOGS,"1"));	
		queue.add(new KeyValuePair("UPLOAD_LOG","1"));
		queue.add(new KeyValuePair("LOG_SELECT",Integer.toHexString(sendingvalue)));
		queue.add(new KeyValuePair(Constants.FTP_IP_PORT,ftpClient));
		queue.add(new KeyValuePair(Constants.FTP_USERNAME,gateWay.getAgent().getFtpUserName()));
		queue.add(new KeyValuePair(Constants.FTP_PASSWORD,gateWay.getAgent().getFtpPassword()));
		String customerId = null;
		customerId = gatewayManager.getCustomerIdOfGateWay(macid);
		String logsdir = ""+customerId + "/" + ControllerProperties.getProperties().get(Constants.LOG_DIR)+"/";
		//String logsdir = "imvglogs"+"/" + customerId + "/" + ControllerProperties.getProperties().get(Constants.LOG_DIR)+"/";
		queue.add(new KeyValuePair(Constants.FTP_PATH_FILE_NAME, logsdir));
		
		}
		else if((Selectedvalue.equals("DELETE")))
		{
		queue.add(new KeyValuePair(Constants.GET_IMVGLOGS,"0"));
		queue.add(new KeyValuePair("LOG_SELECT",Integer.toHexString(sendingvalue)));
		
		}
		if (!(Selectedvalue.equals("UPLOAD")))
		{
			if(!(Selectedvalue.equals("DELETE")))
			{
		
		queue.add(new KeyValuePair(Constants.GET_IMVGLOGS,"1"));
		queue.add(new KeyValuePair("LOG_LEVEL",Selectedvalue));
		queue.add(new KeyValuePair("TIME_OUT",timeout));
			}
		}
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		IMonitorSession.put(trasactionId, this);
		Agent agent =gateWay.getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(messageInXml, ip , port);
		return null;
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}

}
