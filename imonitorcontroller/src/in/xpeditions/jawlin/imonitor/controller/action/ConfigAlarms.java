/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class ConfigAlarms implements ImonitorControllerAction {
	
	private boolean resultExecuted;
	private ActionModel actionModel;
	private boolean isResultSuccess = false;
	private boolean actionSuccess;

	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		XStream stream=new XStream();
		this.actionModel=actionModel;
		Device device=actionModel.getDevice();
		String xml=(String) actionModel.getData();
		String Temp[]=xml.split("` ");
		String Param=(String)stream.fromXML(Temp[1]);
		String NotSelected=(String)stream.fromXML(Temp[2]);
		
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.STORE_ALARM));
		
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));	
		String generatedDeviceId = device.getGeneratedDeviceId();
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));	
		List<AlertType> alerttype=device.getDeviceType().getAlertTypes();
		String NotSelect[]=null;
		String Parm[]=null;
		
		if(!NotSelected.isEmpty())
		{
			 NotSelect=NotSelected.split(",");		
			
		}
		
		if(Param!=null)
		{
			Parm=Param.split(", ");
		}
		
		queue.add(new KeyValuePair("SELECTED_ALARM","START"));
		for(AlertType alert:  alerttype)
		{
			
				long id=alert.getId();
				if(Param!=null)
				{
				for(int i = 0;i<Parm.length;i++)
				{
					int comid=Integer.parseInt(Parm[i]);
					if((id == comid))
					{
						queue.add(new KeyValuePair(alert.getName(),null));
					}
				}
				}
				else if((alert.getName().equals("PANIC_SITUATION")))
				{
					queue.add(new KeyValuePair(alert.getName(),null));
				}
		}
		queue.add(new KeyValuePair("SELECTED_ALARM","END"));
		queue.add(new KeyValuePair("REMOVED_ALARM","START"));
		for(AlertType alert:  alerttype)
		{	
				long id=alert.getId();
				if(!NotSelected.isEmpty())
				{
				for(int i = 0;i<NotSelect.length;i++)
				{
					int comid=Integer.parseInt(NotSelect[i]);
					if(!(alert.getName().equals("PANIC_SITUATION")))
					{
					if(id==comid)
					{
						queue.add(new KeyValuePair(alert.getName(),null));
					}
					}
				}
				}
		}	
		queue.add(new KeyValuePair("REMOVED_ALARM","END"));
		
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		requestProcessor.processRequest(messageInXml, ip , port);
		
		IMonitorSession.put(trasactionId, this);
		return null;
		
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		this.resultExecuted = true;
		this.isResultSuccess = true;
		this.actionSuccess = true;
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
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
