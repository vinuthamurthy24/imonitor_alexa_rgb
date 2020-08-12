package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.LocationManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class UpdateFriendlyNameForSlave implements ImonitorControllerAction
{


	private ActionModel actionModel=null;
	private boolean resultExecuted=false;
	private boolean actionSuccess=false;

	// **************************************************** sumit start *****************************************************
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		
		DeviceManager deviceManager = new DeviceManager();
		LocationManager locationManager = new LocationManager();
		//IconManager iconManager = new IconManager();
		Device device=actionModel.getDevice();
		long id = device.getId();
		long locationId = device.getLocation().getId();
	//	long iconId = device.getIcon().getId();
		String friendlyName = device.getFriendlyName();
		String enableDeviceList = device.getEnableList();
		//Device deviceFromID = deviceManager.getDevice(id);
		String generatedDeviceId = device.getGeneratedDeviceId();
		//deviceFromID=deviceManager.getDeviceWithGateWayAndAgentByGeneratedDeviceId(generatedDeviceId);
		Location location = locationManager.getLocationById(locationId);
		String LocationName=location.getName();
		//Icon icon = iconManager.getIconById(iconId);
		//String fileName = icon.getFileName();
		//String pulseTimeOut = device.getPulseTimeOut();
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.FRIENDLY_DEVICE_NAME,IMonitorUtil.unicodeEscape(friendlyName)  ));
		queue.add(new KeyValuePair(Constants.DEVICE_LOCATION,IMonitorUtil.unicodeEscape(LocationName)));
		queue.add(new KeyValuePair(Constants.DEVICE_LOCATION_ID,""+locationId));
		queue.add(new KeyValuePair(Constants.SHOW_DEVICE,enableDeviceList));
		//queue.add(new KeyValuePair(Constants.PULSE, pulseTimeOut));
			
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		//LogUtil.info("messageInXml :"+messageInXml);

		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = device.getGateWay().getAgent();
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
