/* Copyright © 2012 iMonitor Solutions India Private Limited */
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


public class Doorlock implements ImonitorControllerAction {
		private ActionModel actionModel;
		private boolean resultExecuted = false;

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
			queue.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL,"1"));
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
			DeviceManager deviceManager = new DeviceManager();
			String commandParam = "1";
			String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
			String gateWayId = this.actionModel.getDevice().getGateWay().getMacId();
			Device device = deviceManager.updateCommadParamOfDeviceByGeneratedId(generatedDeviceId, gateWayId, commandParam);
			this.actionModel.setDevice(device);
			String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
			IMonitorSession.remove(tId);
			this.resultExecuted = true;
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
		public String executeFailureResults(Queue<KeyValuePair> queue) {
			DeviceManager deviceManager = new DeviceManager();
			String generatedDeviceId = IMonitorUtil.commandId(queue, Constants.DEVICE_ID);
			Device device = deviceManager.GetDeviceForFailureByGeneratedId(generatedDeviceId);
			this.actionModel.setDevice(device);
			
			return null;
		}

		@Override
		public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
			List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();
			keyValuePairs.add(new KeyValuePair(Constants.ZW_DEVICE_CONTROL, "1"));
			return keyValuePairs;
		}

		@Override
		public boolean isActionSuccess() {
			// TODO Auto-generated method stub
			return false;
		}
		
		
	}


