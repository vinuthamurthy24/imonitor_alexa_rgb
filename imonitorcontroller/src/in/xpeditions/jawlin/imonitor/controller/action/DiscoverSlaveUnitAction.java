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







public class DiscoverSlaveUnitAction implements ImonitorControllerAction
{
	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		
		this.actionModel = actionModel;
		Device device =  (Device) this.actionModel.getDevice();
		String SlaveId = (String) this.actionModel.getData();
		String SlaveModel = actionModel.getTimeout();
		GateWay gateWay=device.getGateWay();
		
		GatewayManager gatewayManager = new GatewayManager();
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.ADD_VIASLAVE));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, device.getGateWay().getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID, device.getGeneratedDeviceId()));
		queue.add(new KeyValuePair(Constants.SLAVEID, SlaveId));
		queue.add(new KeyValuePair(Constants.SLAVEMODEL, SlaveModel));
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
	public String executeSuccessResults(Queue<KeyValuePair> queue) 
	{
		// TODO Auto-generated method stub
		
		this.actionModel.setQueue(queue);
		/*XStream xStream = new XStream();
		LogUtil.info("executeSuccessResults start for Slave");
		Map<String, String> commands = new HashMap<String, String>();
		String valueInXml = null;
		String macId = "";
		String DeviceTypeName=null;
		long DeviceTypeId=0;
		String DevTypeId=null;
		ModbusSlave modbusSlave=null;
		DeviceManager deviceManager=null;
		XStream stream=new XStream();
		try
		{
			deviceManager=new DeviceManager();
			modbusSlave=new ModbusSlave();
			//Queue<KeyValuePair> queue = IMonitorUtil
				//	.extractCommandsQueueFromXml(xml);
			macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
			GatewayManager gatewayManager = new GatewayManager();
			GateWay gateWay = gatewayManager.getGateWayByMacId(macId);
			String generateddeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
			String SlaveId= IMonitorUtil.commandId(queue, Constants.SLAVEID);
			long sid=Long.parseLong(SlaveId);
			String HAIF_ID=generateddeviceId+"-1"+SlaveId;
			LogUtil.info("Device Id of Slave -->"+generateddeviceId);
			LogUtil.info("Slave Id -->"+SlaveId);
			LogUtil.info("HAIF_ID Id -->"+HAIF_ID);
			//DeviceType from Db
			DeviceTypeName="HA_IF";
			DeviceTypeManager deviceTypeManager = new DeviceTypeManager();
			DeviceType deviceType = deviceTypeManager.getDeviceTypeByName(DeviceTypeName);
			//LogUtil.info("Device Type --"+stream.toXML(deviceType));
			//Icon from DB
			IconManager iconManager = new IconManager();
			Icon defaultIcon = iconManager.getDefaultIcon(deviceType.getId());
			//LogUtil.info("Icon --"+stream.toXML(defaultIcon));
			//Gateway from Db
			GateWay gateWay1 = gatewayManager.getGateWayWithAgentByMacId(macId);
			//LogUtil.info("Gateway --"+stream.toXML(gateWay1));
			
			
			//Extract device id from deviceId and setId and set that device
			String devIdfromString=generateddeviceId.substring(18,22);
			
			Device device=deviceManager.getDevice(generateddeviceId);
			
			
			LogUtil.info("Device from DB--->"+stream.toXML(device));
			LogUtil.info("Device id fetched from DB is "+device.getId());
			
			device.setId(device.getId());
			modbusSlave.setDeviceId(device);
			modbusSlave.setHAIF_Id(HAIF_ID);
			modbusSlave.setFriendlyName("Slave-"+devIdfromString+"-"+SlaveId);
			LogUtil.info("Modbus Slave friendly name "+ modbusSlave.getFriendlyName());

			
			CustomerManager customerManager=new CustomerManager();
			Customer customer=customerManager.getCustomerByMacId(macId);
			//LogUtil.info("Customer --"+stream.toXML(customer));
			LocationManager locationManager=new LocationManager();
			long locationid=locationManager.getLocationIdByName(Constants.DEFAULT_ROOM, customer.getCustomerId());
			Location location=locationManager.getLocationById(locationid);
			//LogUtil.info("Location  --"+stream.toXML(location));
			modbusSlave.setLocation(location);
			modbusSlave.setDeviceType(deviceType);
			modbusSlave.setIcon(defaultIcon);
			modbusSlave.setGateWay(gateWay1);
			modbusSlave.setEnableList("1");
			modbusSlave.setEnableStatus(0);
			modbusSlave.setSlaveId(sid);
			
			
			String posIdx = deviceManager.getMaxPositionIndexBasedOnLocation(Constants.DEFAULT_ROOM);
			long positionIndex = Long.parseLong(posIdx);
			modbusSlave.setPosIdx(++positionIndex);
			LogUtil.info("ModBus device --"+stream.toXML(modbusSlave));
			DaoManager daoManager=new DaoManager();
			boolean result=daoManager.save(modbusSlave);
			if (result) 
			{
				LogUtil.info("Save Successfull");
			}
			else
			{
				LogUtil.info("Failed");
			}
			
		} 
		catch (Exception e) {
			LogUtil.error("General Error in saving device and message is : "
					+ e.getMessage());
			LogUtil.info("", e);
		} */
		this.resultExecuted = true;
		this.actionSuccess = true;
		
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
