package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ModbusSlave;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceConfigurationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.IconManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.LocationManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

public class DiscoverIndoorUnitAction implements ImonitorControllerAction
{
	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		
		this.actionModel = actionModel;
		Device device =  (Device) this.actionModel.getDevice();
		
		GateWay gateWay=device.getGateWay();
		
		GatewayManager gatewayManager = new GatewayManager();
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.DISCOVER_INDOOR_UNIT));
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
	public String executeSuccessResults(Queue<KeyValuePair> queue) 
	{
			
		
		//this.actionModel.setQueue(queue);
		this.resultExecuted = true;
		this.actionSuccess = true;
		
		this.actionModel.setQueue(queue);
		
		// TODO Auto-generated method stub
		/*XStream xStream = new XStream();
		LogUtil.info("executeSuccessResults start in IDU action");
		Map<String, String> commands = new HashMap<String, String>();
		String macId = "";
		String DeviceTypeName=null;
		XStream stream=new XStream();
		try
		{
			//Queue<KeyValuePair> queue = IMonitorUtil
				//	.extractCommandsQueueFromXml(xml);
			macId = IMonitorUtil.commandId(queue, Constants.IMVG_ID);
			String HAIF_Id = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
			String VRV1 = IMonitorUtil.commandId(queue, Constants.VRV1);
			String VRV2 = IMonitorUtil.commandId(queue, Constants.VRV2);
			String VRV3 = IMonitorUtil.commandId(queue, Constants.VRV3);
			String VRV4 = IMonitorUtil.commandId(queue, Constants.VRV4);
			
			LogUtil.info("VRV 1 is --->"+VRV1);
			
			//Modbus Slave from DB
			LogUtil.info("HAIF_Id/Slave ID --->"+HAIF_Id);
			DeviceManager deviceManager=new DeviceManager();
			ModbusSlave modbusSlave=deviceManager.getVIASlavesBasedOnHAIFId(HAIF_Id);
			LogUtil.info("Slave Device From Db -->"+stream.toXML(modbusSlave));
			ArrayList<String> arrayList=new ArrayList<String>();
			arrayList.add(VRV1);
			arrayList.add(VRV2);
			arrayList.add(VRV3);
			arrayList.add(VRV4);
			int vrvNumber=1;
			for (String IDU : arrayList)
			{
				LogUtil.info("VRV ---- >"+stream.toXML(IDU));
				String[] Idus=IDU.split(";");
				
				LogUtil.info("VRV Number ---- >"+stream.toXML(vrvNumber));
			//String[] Idus=VRV1.split(";");
			for (int i = 0; i < Idus.length; i++) 
			{
				LogUtil.info("for start");
				//IndoorUnit indoorUnit=new IndoorUnit();
				Device device=new Device();
				
				String IndivialIDU=Idus[i];
				String[] splitIDU=IndivialIDU.split(":");
				String iduID=splitIDU[0];
				String ConnectionStatus=splitIDU[1];
				//String CommStatus=splitIDU[2];
				String vrvNumbString=Integer.toString(vrvNumber);
				String UnitId=HAIF_Id+"-"+vrvNumbString+":"+iduID;
				LogUtil.info("Indoor Unit Id and name "+UnitId);
				//indoorUnit.setSlaveId(modbusSlave);
				//indoorUnit.setUnit_Id(UnitId);
				String deviceId = UnitId.substring(18,UnitId.length());
				device.setDeviceId(deviceId);
				device.setGeneratedDeviceId(UnitId);
				//indoorUnit.setFriendlyName(UnitId);
				device.setFriendlyName(UnitId);
				long connStatus=Long.valueOf(ConnectionStatus);
				if (connStatus!=0) 
				{
					LogUtil.info("Connection status before save "+connStatus);
					IndoorUnitConfiguration configuration=new IndoorUnitConfiguration();
					//Saving Device Configuration
					configuration.setConnectStatus(connStatus);
					DeviceConfigurationManager configurationManager=new DeviceConfigurationManager();
					configurationManager.saveDeviceConfiguration(configuration);
					device.setDeviceConfiguration(configuration);
					//indoorUnit.setConnectStatus(connStatus);
					//long commStatus=Long.valueOf(CommStatus);
					//indoorUnit.setCommStatus(commStatus);
					//Customer and Location
					CustomerManager customerManager=new CustomerManager();
					Customer customer=customerManager.getCustomerByMacId(macId);
					LocationManager locationManager=new LocationManager();
					long locationid=locationManager.getLocationIdByName(Constants.DEFAULT_ROOM, customer.getCustomerId());
					Location location=locationManager.getLocationById(locationid);
					//indoorUnit.setLocation(location);
					device.setLocation(location);
					//DeviceType
					DeviceTypeName="VIA_UNIT";
					DeviceTypeManager deviceTypeManager = new DeviceTypeManager();
					DeviceType deviceType = deviceTypeManager.getDeviceTypeByName(DeviceTypeName);
					//indoorUnit.setDeviceType(deviceType);
					device.setDeviceType(deviceType);
					//Gateway
					GatewayManager gatewayManager = new GatewayManager();
					GateWay gateWay = gatewayManager.getGateWayByMacId(macId);
					//indoorUnit.setGateWay(gateWay);
					device.setGateWay(gateWay);
					//Icon
					IconManager iconManager = new IconManager();
					Icon defaultIcon = iconManager.getDefaultIcon(deviceType.getId());
					//indoorUnit.setIcon(defaultIcon);
					device.setIcon(defaultIcon);
					//indoorUnit.setEnableStatus(0);
					device.setEnableStatus("0");
					//indoorUnit.setEnableList("1");
					device.setEnableList("1");
					String posIdx = deviceManager.getMaxPositionIndexBasedOnLocation(Constants.DEFAULT_ROOM);
					long positionIndex = Long.parseLong(posIdx);
					//indoorUnit.setPosIdx(++positionIndex);
					device.setPosIdx(++positionIndex);
					DaoManager daoManager=new DaoManager();
					//boolean afterSave=daoManager.save(indoorUnit);
					boolean afterSave=daoManager.save(device);
					LogUtil.info(" Device Save result-->"+afterSave);
				}
				else
				{
					LogUtil.info("Connection status is zero device not saved");
				}
				LogUtil.info("For loop end");
			}
			vrvNumber=vrvNumber+1;
				//Device device=new Device();
			
			
			DaoManager daoManager=new DaoManager();
			boolean result=daoManager.save(indoorUnit);
			if (result) 
			{ 
				LogUtil.info("Save Successfull");
			}
			else
			{
				LogUtil.info("Failed");
			}
			}
			
		} 
		catch (Exception e) {
			LogUtil.error("General Error in saving device and message is : "
					+ e.getMessage());
			LogUtil.info("", e);
		} */
		
		
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
