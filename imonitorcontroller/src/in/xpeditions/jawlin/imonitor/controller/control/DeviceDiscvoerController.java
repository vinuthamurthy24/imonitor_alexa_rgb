/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.control;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;

/**
 * @author Coladi
 *
 */
public class DeviceDiscvoerController implements Runnable{

	private GateWay gateWay;
	private long timeOut;
	
	public DeviceDiscvoerController(long timeoutDuration){
		this.setTimeOut(timeoutDuration);
	}

	@Override
	public void run() {
		//long duration = Long.parseLong(ControllerProperties.getProperties().get(Constants.DISCOVERY_DURATION)) * 1000 + 30000;
		long duration = this.timeOut + 30000;
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			//This thread will be interrupted when [DEVICE_DISCOVERY_STOP]/[DEVICE_REMOVE_STOP] is received from iMVG.
			LogUtil.error("Error when sleeping. Message : " + e.getMessage());
//			String macId = this.gateWay.getMacId();
//			IMonitorSession.remove(macId);
//			GatewayManager gatewayManager = new GatewayManager();
//			gatewayManager.updateGateWayToOnline(this.gateWay.getId());
		}
		finally{
			//If Communication failure occurs after successful DEVICE DISCOVERY or DEVICE REMOVE, the gateway must be updated to online after timeout. 
			String macId = this.gateWay.getMacId();
			IMonitorSession.remove(macId);
			GatewayManager gatewayManager = new GatewayManager();
			gatewayManager.updateGateWayToOnline(this.gateWay.getId());
		}
	}

	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

}
