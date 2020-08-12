package in.xpeditions.jawlin.imonitor.controller.control;

import java.util.Set;

import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgSecurityActions;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RuleManager;

import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.UpdatorModel;

public class SecurityActionControl implements Runnable {

	private Customer customer;

	public SecurityActionControl(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		RuleManager ruleManager = new RuleManager();
		Set<ImvgSecurityActions> securityActions = ruleManager.retrieveImvgSecurityDetails(this.customer.getId());
		DeviceManager deviceManager = new DeviceManager();
		for (ImvgSecurityActions action : securityActions) {

			String command = action.getActionType().getCommand();
			
			Object[] result = deviceManager.checkDeviceAndReturnCommunicationDetails(
					action.getDevice().getGeneratedDeviceId(), action.getCustomer().getCustomerId(), command);
			if (result != null) {

				String ip = IMonitorUtil.convertToString(result[3]);
				String macId = IMonitorUtil.convertToString(result[0]);
				int port = Integer.parseInt(IMonitorUtil.convertToString(result[4]));
				UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(command);
				Class<?> className = updatorModel.getClassName();
				ImonitorControllerAction controllerAction;
				try {
					controllerAction = (ImonitorControllerAction) className.newInstance();
					Device device = new Device();
					device.setGeneratedDeviceId(action.getDevice().getGeneratedDeviceId());
					GateWay gateWay = new GateWay();
					gateWay.setMacId(macId);
					Agent agent = new Agent();
					agent.setControllerReceiverPort(port);
					agent.setIp(ip);
					gateWay.setAgent(agent);
					device.setGateWay(gateWay);

					if ((command.equals(Constants.SWITCH_ON)) || (command.equals(Constants.ACTIVATE_ALARM))
							|| (command.equals("LOCK"))) {
						device.setCommandParam("1");
					} else {
						device.setCommandParam("0");
					}

					if (command.equals(Constants.AC_ON))
						device.setCommandParam("22");

					if (command.equals(Constants.AC_OFF))
						device.setCommandParam("0");

					// if(level != null){
					// device.setCommandParam(level);
					// }

					ActionModel actionModel = new ActionModel(device, null);
					controllerAction.executeCommand(actionModel);
					boolean exResult = waitForResult(controllerAction);
					
					device = controllerAction.getActionModel().getDevice();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	private boolean waitForResult(ImonitorControllerAction controllerAction) {

		String tOut = ControllerProperties.getProperties().get(Constants.MID_TIMEOUT_DURATION);

		long timeOut = Long.parseLong(tOut);
		long waitTime = 1000;
		do {
			timeOut = timeOut - waitTime;
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (timeOut < 0) {
				return false;
			}
		} while (!controllerAction.isResultExecuted());
		return true;
	}

}
