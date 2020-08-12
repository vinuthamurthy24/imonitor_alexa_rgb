package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Action;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.NetworkStats;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

public class NetworkStatusAction implements ImonitorControllerAction
{
	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;
	
	@Override
	public String executeCommand(ActionModel actionModel) {
		// TODO Auto-generated method stub
		Device device =  (Device) actionModel.getDevice();
		GateWay gateWay=device.getGateWay();
		
		GatewayManager gatewayManager = new GatewayManager();
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID, Constants.NW_STATS_REQ));
		String transactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, transactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID, device.getGateWay().getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID, device.getDeviceId()));
		
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

		XStream xStream = new XStream();
		try 
		{
			String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
			IMonitorSession.remove(tId);
			String imvgId=IMonitorUtil.commandId(queue, Constants.IMVG_ID);
			String deviceId=IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
			String rssi=IMonitorUtil.commandId(queue, Constants.RSSI);
			String retries1=IMonitorUtil.commandId(queue, Constants.R_RETRIES);
			long retries=Long.parseLong(retries1);
			String nc1=IMonitorUtil.commandId(queue, Constants.NC);
			long nc=Long.parseLong(nc1);
			String rc1=IMonitorUtil.commandId(queue, Constants.RC);
			long rc=Long.parseLong(rc1);

			GatewayManager gatewayManager=new GatewayManager();
			DeviceManager deviceManager=new DeviceManager();
			
			GateWay gateway1=gatewayManager.getGateWayByMacId(imvgId);
			BigInteger did1=deviceManager.getDeviceBasedOnDeviceId(deviceId, gateway1.getId());
			long id1=did1.longValue();
			
			String xml=deviceManager.getNetworkStatsBasedOnDeviceId(id1);
			
			NetworkStats networkStatsfrmDb=(NetworkStats) xStream.fromXML(xml);
			
			
			
			
			Device device=new Device();
			if (networkStatsfrmDb==null) 
			{
				//Save Block
				NetworkStats networkStats=new NetworkStats();
				networkStats.setImvgId(imvgId);
				
				
				
				GateWay gateway=gatewayManager.getGateWayByMacId(imvgId);
				
				BigInteger did=deviceManager.getDeviceBasedOnDeviceId(deviceId, gateway.getId());
				long id=did.longValue();
				device.setId(id);
				networkStats.setDevice(device);
				networkStats.setRssi(rssi);
				networkStats.setRetries(retries);
				networkStats.setNc(nc);
				networkStats.setRc(rc);
				 boolean result=new DaoManager().save(networkStats);
				
				 
				 	actionModel=new ActionModel(networkStats.getDevice(), null);
					//this.actionModel.setData(networkStats);
			}
			else {
				//Update Block
				GateWay gateway=gatewayManager.getGateWayByMacId(imvgId);
				BigInteger did=deviceManager.getDeviceBasedOnDeviceId(deviceId, gateway.getId());
				long id=did.longValue();
				device.setId(id);
				networkStatsfrmDb.setDevice(device);
				networkStatsfrmDb.setRssi(rssi);
				networkStatsfrmDb.setRetries(retries);
				networkStatsfrmDb.setNc(nc);
				networkStatsfrmDb.setRc(rc);
				boolean result=new DaoManager().update(networkStatsfrmDb);
				actionModel=new ActionModel(networkStatsfrmDb.getDevice(), null);
				//this.actionModel.setData(networkStatsfrmDb);
			}
					
			
			this.resultExecuted = true;
			this.actionSuccess = true;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LogUtil.info("Exception :"+e.getMessage());
		}
	
		return null;
		
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		// TODO Auto-generated method stub
		this.resultExecuted = true;
		String failureReason=IMonitorUtil.commandId(queue, Constants.FAILURE_REASON);
		String imvgId=IMonitorUtil.commandId(queue, Constants.IMVG_ID);
		String deviceId=IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		GatewayManager gatewayManager=new GatewayManager();
		DeviceManager deviceManager=new DeviceManager();
		
		GateWay gateway=gatewayManager.getGateWayByMacId(imvgId);
		BigInteger did1=deviceManager.getDeviceBasedOnDeviceId(deviceId, gateway.getId());
		long id=did1.longValue();
		Device device=new Device();
		device.setId(id);
		actionModel=new ActionModel(device, failureReason);
		this.actionSuccess = false;
		
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
