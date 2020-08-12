package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
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

public class IDUFanDirectionControl implements ImonitorControllerAction
{
	private ActionModel actionModel;
	private boolean resultExecuted=false;

	public IDUFanDirectionControl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeCommand(in.xpeditions.jawlin.imonitor.controller.action.ActionModel)
	 */
	@Override
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		
		Device device = actionModel.getDevice();
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		
		String generatedDeviceId = device.getGeneratedDeviceId();
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		IndoorUnitConfiguration indoorUnitConfiguration =(IndoorUnitConfiguration)this.actionModel.getDevice().getDeviceConfiguration();
		String acSwing=indoorUnitConfiguration.getFanDirectionCapability();
		queue.add(new KeyValuePair(Constants.ZXT_AC_SWING_CONTROL,device.getCommandParam()));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		
//		First issues the command to switch on the A/C
		RequestProcessor requestProcessor = new RequestProcessor();
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		
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
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		
		DeviceManager deviceManager = new DeviceManager();
		Device device = actionModel.getDevice();
		String macId = device.getGateWay().getMacId();
		String generatedDeviceId = device.getGeneratedDeviceId();
//		
		IndoorUnitConfiguration configuration =(IndoorUnitConfiguration)this.actionModel.getDevice().getDeviceConfiguration();
		
		
		
		


		
//		
//		if(commandParam.equals("22"))
//		{
//			Queue<KeyValuePair> queueToSetTemp = new LinkedList<KeyValuePair>();		
//			queueToSetTemp.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));		
//			queueToSetTemp.add(new KeyValuePair(Constants.TRANSACTION_ID,IMonitorUtil.generateTransactionId()));		
//			queueToSetTemp.add(new KeyValuePair(Constants.IMVG_ID,macId));		
//			queueToSetTemp.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));		
//			queueToSetTemp.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
//			queueToSetTemp.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_TYPE,setpointvalue));
//			queueToSetTemp.add(new KeyValuePair(Constants.ZXT_AC_SET_POINT_VALUE,commandParam));
//			queueToSetTemp.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
//	////bhavya updates for fan modes 29-5-13 end
//	//		Then wait and issue the command to change the level.
//			RequestProcessor requestProcessor = new RequestProcessor();
//			Agent agent = device.getGateWay().getAgent();
//			String ip = agent.getIp();
//			int port = agent.getControllerReceiverPort();
//			
//	//		This time out can be moved to the success API.
//			String waitTimeS = ControllerProperties.getProperties().get(Constants.AC_WAIT_DURATION);
//			long waitTime = Long.parseLong(waitTimeS);
//			try {
//				Thread.sleep(waitTime);
//			} catch (InterruptedException e) {
//				LogUtil.error("Error occured while waiting for the A/C to power on.");
//			}
//			String messageInXml = IMonitorUtil.convertQueueIntoXml(queueToSetTemp);
//			requestProcessor.processRequest(messageInXml, ip , port);
//			
//		}
//		}
		String fanDirectionValue=this.actionModel.getDevice().getCommandParam();
		Device deviceFromDeviceManager = deviceManager.updateFanDirectionOfIDUByGeneratedId(generatedDeviceId, macId,fanDirectionValue);
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
		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();
		keyValuePairs.add(new KeyValuePair(Constants.ZXT_AC_MODE, "5"));
		return keyValuePairs;
	}
	@Override
	
	
	public boolean isActionSuccess() {
		// TODO Auto-generated method stub
		return false;
	}

}
