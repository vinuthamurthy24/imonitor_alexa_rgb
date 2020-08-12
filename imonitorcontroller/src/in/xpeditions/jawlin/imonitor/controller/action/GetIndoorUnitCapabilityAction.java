package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceConfigurationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class GetIndoorUnitCapabilityAction implements ImonitorControllerAction
{
	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		
		this.actionModel=actionModel;
		Device device =  (Device) actionModel.getDevice();
		
		GateWay gateWay=device.getGateWay();
		
		GatewayManager gatewayManager = new GatewayManager();
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.GET_VRVINDOOR_CAPABILITY));	
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, device.getGateWay().getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID, device.getGeneratedDeviceId()));
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

		
	
		this.actionModel.setQueue(queue);
		this.resultExecuted = true;
		this.actionSuccess = true;
		/*Map<String, String> commands = new HashMap<String, String>();
		String macId = "";
		String DeviceTypeName=null;
		XStream stream=new XStream();
		try
		{
			//Queue<KeyValuePair> queue = IMonitorUtil
			//		.extractCommandsQueueFromXml(xml);
			macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
			String UnitId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
			String fanModeCapability = IMonitorUtil.commandId(queue, Constants.FANMODE_CAPABILITY);
			String coolModeCapability = IMonitorUtil.commandId(queue, Constants.COOLMODE_CAPABILITY);
			String heatModeCapability = IMonitorUtil.commandId(queue, Constants.HEATMODE_CAPABILITY);
			String autoModeCapability = IMonitorUtil.commandId(queue, Constants.AUTOMODE_CAPABILITY);
			String dryModeCapability = IMonitorUtil.commandId(queue, Constants.DRYMODE_CAPABILITY);
			String fanDirectionLevelCapability = IMonitorUtil.commandId(queue, Constants.FANDIRECTION_LEVEL_CAPABILITY);
			String fanDirectionCapability = IMonitorUtil.commandId(queue, Constants.FANDIRECTION_CAPABILITY);
			String fanVolumeLevelCapability = IMonitorUtil.commandId(queue, Constants.FANVOLUME_LEVEL_CAPABILITY);
			String fanVolumeCapability = IMonitorUtil.commandId(queue, Constants.FANVOLUME_CAPABILITY);
			String coolingUpperlimit = IMonitorUtil.commandId(queue, Constants.COOLING_UPPERLIMIT);
			String coolingLowerlimit = IMonitorUtil.commandId(queue, Constants.COOLING_LOWERLIMIT);
			String heatingUpperlimit = IMonitorUtil.commandId(queue, Constants.HEATING_UPPERLIMIT);
			String heatingLowerlimit = IMonitorUtil.commandId(queue, Constants.HEATING_LOWERLIMIT);
			
			IndoorUnitConfiguration configuration=new IndoorUnitConfiguration();
			configuration.setFanModeCapability(fanModeCapability);
			configuration.setCoolModeCapability(coolModeCapability);
			configuration.setHeatModeCapability(heatModeCapability);
			configuration.setAutoModeCapability(autoModeCapability);
			configuration.setDryModeCapability(dryModeCapability);
			configuration.setFanDirectionLevelCapability(fanDirectionLevelCapability);
			configuration.setFanDirectionCapability(fanDirectionCapability);
			configuration.setFanVolumeLevelCapability(fanVolumeLevelCapability);
			configuration.setFanVolumeCapability(fanVolumeCapability);
			configuration.setCoolingUpperlimit(coolingUpperlimit);
			configuration.setCoolingLowerlimit(coolingLowerlimit);
			configuration.setHeatingUpperlimit(heatingUpperlimit);
			configuration.setHeatingLowerlimit(heatingLowerlimit);
			
			DaoManager daoManager=new DaoManager();
			boolean afterSave=daoManager.save(configuration);
			DeviceManager deviceManager=new DeviceManager();
			//IndoorUnit indoorUnit=deviceManager.getIndoorUnitbyUnitId(UnitId);
			LogUtil.info("Generated Device Id "+UnitId);
			Device device=deviceManager.getDevice(UnitId);
			LogUtil.info("Device from Db"+stream.toXML(device));
			DeviceConfigurationManager deviceConfigurationManager = new DeviceConfigurationManager();
			deviceConfigurationManager.saveDeviceConfiguration(configuration);
			device.setDeviceConfiguration(configuration);
			
			DaoManager daoManager=new DaoManager();
			boolean result=daoManager.update(device);
			LogUtil.info("Update Result IUD Configuration --- >"+result);
			
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
		
		LogUtil.info("executeSuccessResults end");	*/
			
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
