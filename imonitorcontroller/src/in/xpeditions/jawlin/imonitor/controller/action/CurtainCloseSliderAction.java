package in.xpeditions.jawlin.imonitor.controller.action;


import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CurtainCloseSliderAction implements ImonitorControllerAction{

	private ActionModel actionModel;
	private boolean resultExecuted = false;
	@Override
	public String executeCommand(ActionModel actionModel) {
		
		this.actionModel = actionModel;
		Device device=actionModel.getDevice();
		DeviceManager devicemanger = new DeviceManager();
		String id=device.getGeneratedDeviceId();
		Device devicefromdb=devicemanger.getDeviceByGeneratedDeviceId(id);
		long commandparamFromDb=Long.parseLong(devicefromdb.getCommandParam());
		long commandparamFromWeb=Long.parseLong(device.getCommandParam());
		int sendingvalue=(int) (commandparamFromDb-commandparamFromWeb);
		sendingvalue=(int) (-commandparamFromWeb+commandparamFromDb);
		/*XStream stream = new XStream();
		DeviceManager deviceManager = new DeviceManager();
		double curtainRPM = 0;
		double curtainLD = 0;
		double curtainLength = 0;
		double curtainMounting = 0;
		double buffertime=0;
		int time=0;
		
		Device deviceFromDB=deviceManager.getDeviceWithConfigurationAndMake(device.getGeneratedDeviceId());
	
		//Kantha: Please DO NOT remove this line even though it seems redundant.
		deviceFromDB = (Device)stream.fromXML(stream.toXML(deviceFromDB));

		curtainRPM=Integer.parseInt(deviceFromDB.getMake().getNumber());
	
		String temp=deviceFromDB.getMake().getDetails();
		String temp1[]=temp.split(", ");
		curtainLD=Integer.parseInt(temp1[0]);
		buffertime = Integer.parseInt(temp1[1]);
		
		MotorConfiguration m = (MotorConfiguration)deviceFromDB.getDeviceConfiguration();
		
		curtainLength=m.getLength();
		
		curtainMounting=((MotorConfiguration)deviceFromDB.getDeviceConfiguration()).getMountingtype();
		
		if(curtainMounting == 3)curtainMounting = 1;
		
		time= (int)(((curtainLength/curtainMounting) / ((curtainRPM/60) * curtainLD))+buffertime);
		*/
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		//device = actionModel.getDevice();
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		String generatedDeviceId = device.getGeneratedDeviceId();
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,"0"));
		queue.add(new KeyValuePair("ZW_CONTROL_TIME",""+sendingvalue));
		queue.add(new KeyValuePair(Constants.ZW_CONTROL_LEVEL,device.getCommandParam()));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
		return null;
	
	}

	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		
		/*DeviceManager deviceManager = new DeviceManager();	
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		String gateWayId = this.actionModel.getDevice().getGateWay().getMacId();
		String commandParam = this.actionModel.getDevice().getCommandParam();
		Device device = deviceManager.updateCommadParamOfDeviceByGeneratedId(generatedDeviceId, gateWayId, commandParam);
		
		this.actionModel.setDevice(device);
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		return null;// TODO Auto-generated method stub*/
		long commandParam;
		DeviceManager deviceManager = new DeviceManager();	
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		String gateWayId = this.actionModel.getDevice().getGateWay().getMacId();
		Device device=this.actionModel.getDevice();
		long commandParamfromaction = Long.parseLong(device.getCommandParam());
		String id=device.getGeneratedDeviceId();
		Device devicefromdb=deviceManager.getDeviceByGeneratedDeviceId(id);
		long commandParamfromdb=Long.parseLong(devicefromdb.getCommandParam());
		if(commandParamfromaction!=100)
			commandParam=commandParamfromaction;//commandParamfromdb-commandParamfromaction;
		else
			commandParam=0;
		device = deviceManager.updateCommadParamOfDeviceByGeneratedId(generatedDeviceId, gateWayId, ""+commandParam);
		this.actionModel.setDevice(device);
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		return null;// TODO Auto-generated method stub
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		DeviceManager deviceManager = new DeviceManager();
		String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
		Device device = deviceManager.GetDeviceForFailureByGeneratedId(generatedDeviceId);
		this.actionModel.setDevice(device);
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
		
	/*	DeviceManager deviceManager=new DeviceManager();
		String GeneratedDeviceId=(actionDataHolder.getDevice()).getGeneratedDeviceId();
		
		Device deviceFromDB = deviceManager.getDeviceWithConfigurationAndMake(GeneratedDeviceId);
		double curtainRPM = Integer.parseInt(deviceFromDB.getMake().getNumber());
		
		String temp=deviceFromDB.getMake().getDetails();
		String temp1[]=temp.split(", ");
		double curtainLD=Integer.parseInt(temp1[0]);
		double buffertime = Integer.parseInt(temp1[1]);
		
		
		//Kantha: Please DO NOT remove this line even though it seems redundant.
		XStream stream = new XStream();
		deviceFromDB = (Device)stream.fromXML(stream.toXML(deviceFromDB));
		MotorConfiguration m = (MotorConfiguration)deviceFromDB.getDeviceConfiguration();
		
		double curtainLength = m.getLength();
		
		double curtainMounting = ((MotorConfiguration)deviceFromDB.getDeviceConfiguration()).getMountingtype();
		if(curtainMounting == 3)curtainMounting = 1;
		int time = (int)(((curtainLength/curtainMounting) / ((curtainRPM/60) * curtainLD))+buffertime);
		
	
		*/
		
		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();
		keyValuePairs.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,"0"));
		keyValuePairs.add(new KeyValuePair("ZW_CONTROL_TIME","100"));
	//	keyValuePairs.add(new KeyValuePair("ZW_CONTROL_TIME",actionDataHolder.getLevel()));
//		keyValuePairs.add(new KeyValuePair("ZW_MOTOR_LEVEL", actionDataHolder.getLevel()));
		return keyValuePairs;
	}

	@Override
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return false;
	}
}
