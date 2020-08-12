package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class IndoorUnitActionControl implements ImonitorControllerAction
{
	private ActionModel actionModel;
	private boolean resultExecuted=false;

	public IndoorUnitActionControl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeCommand(in.xpeditions.jawlin.imonitor.controller.action.ActionModel)
	 */
	@Override
	public String executeCommand(ActionModel actionModel)
		{
		this.actionModel = actionModel;
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		LogUtil.info("Step 1");
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		
		Device device = actionModel.getDevice();
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		
		String generatedDeviceId = device.getGeneratedDeviceId();
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		LogUtil.info("Step 2" + generatedDeviceId);
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		String commandParam = this.actionModel.getDevice().getCommandParam();
		queue.add(new KeyValuePair(Constants.ZXT_AC_MODE,commandParam));
		LogUtil.info("Step 3" + commandParam);
		String setpointvalue = "2";
		if(!commandParam.equals("6"))
		{
		 if(commandParam.equals("1"))
		{
			setpointvalue="1";
		}
		else if(commandParam.equals("8"))
		{
			setpointvalue="8";
		}
		else if(commandParam.equals("10"))
		{
			setpointvalue="10";
		}
		 LogUtil.info("Step 4" + setpointvalue);
		if(commandParam == null || commandParam.equals("5") || commandParam.equals("2") || commandParam.equals("1") || commandParam.equals("10") || commandParam.equals("8"))
		{
				DeviceConfiguration configuration=device.getDeviceConfiguration();
				DeviceManager manager=new DeviceManager();
				 LogUtil.info("Step 5" + configuration.getId());
				IndoorUnitConfiguration idu= manager.getIndoorUnitConfigurationById(configuration.getId());
				 LogUtil.info("Step 6");
				int tempValue=idu.getTemperatureValue();
				String value=Integer.toString(tempValue);
				 LogUtil.info("Step 7" + tempValue);
				commandParam = value;
				 LogUtil.info("Step 8" + commandParam);
		}
		//device.setCommandParam(commandParam);
		 LogUtil.info("Step 9");
			queue.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_TYPE,setpointvalue));
			queue.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_VALUE,commandParam));
		}
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		
//		First issues the command to switch on the A/C
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		 LogUtil.info("Step 10");
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		requestProcessor.processRequest(messageInXml, ip , port);
		
		IMonitorSession.put(trasactionId, this);
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		DeviceManager deviceManager = new DeviceManager();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		Device device = deviceManager.GetDeviceForFailureByGeneratedId(generatedDeviceId);
		this.actionModel.setDevice(device);
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@SuppressWarnings("unused")
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) 
	{
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		
		DeviceManager deviceManager = new DeviceManager();
		Device device = actionModel.getDevice();
		String macId = device.getGateWay().getMacId();
		String generatedDeviceId = device.getGeneratedDeviceId();	
		String commandParam = device.getCommandParam();
	//LogUtil.info("commandParam"+commandParam); 

		String fanmodes=commandParam;
		String setpointvalue = "2";
		if(!commandParam.equals("6"))
		{
		 if(commandParam.equals("1"))
		{
			setpointvalue="1";
		}
		else if(commandParam.equals("8"))
		{
			setpointvalue="8";
		}
		else if(commandParam.equals("10"))
		{
			setpointvalue="10";
		}
		if(commandParam == null || commandParam.equals("5") || commandParam.equals("2") || commandParam.equals("1") || commandParam.equals("10") || commandParam.equals("8"))
		{
			commandParam = "22";
		}
		}
		Device deviceFromDeviceManager = deviceManager.updateCommadParamOnlyForIDUOffandMode(generatedDeviceId, macId, device.getCommandParam(),fanmodes);
		this.actionModel.setDevice(deviceFromDeviceManager);
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
		String setpointvalueandmode=actionDataHolder.getLevel();
		
		String[] split = setpointvalueandmode.split(":");
		String Fan_Mode="";
		String setpointtype="";
		String setpointvalue="";
		if(split.length>1)
		{
			setpointvalue= split[0];
			setpointtype= split[1];
			Fan_Mode=split[1];
			if(setpointtype.equals("10"))
			{
				setpointtype="10";
			}
		}
		else
		{
			Fan_Mode=split[0];
			setpointtype="6";
		}
		
		keyValuePairs.add(new KeyValuePair(Constants.ZXT_AC_MODE,Fan_Mode));
		
		if(!setpointtype.equals("6"))
		{
		keyValuePairs.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_TYPE,setpointtype));
		keyValuePairs.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_VALUE, setpointvalue));
		}
		return keyValuePairs;
	}

	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return false;
	}

}
