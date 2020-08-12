/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.communicator;

import in.xpeditions.jawlin.imonitor.client.util.Constants;
import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;
import in.xpeditions.jawlin.imonitor.client.util.Util;

import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;

/**
 * @author Coladi
 * 
 */
public class DeviceControlExecutor implements Runnable {

	private Properties properties;

	public DeviceControlExecutor(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void run() {
		String generatedDeviceId = this.properties
				.getProperty(Constants.DEVICE_ID);
		String temeratorReq = this.properties
				.getProperty(Constants.ZW_GET_TEMPERATURE_VALUE);
		String message = MessageUtil
				.createDeviceControlSuccessMessage(properties);
		try {
			if (temeratorReq != null) {
				String transactionId = this.properties
						.getProperty(Constants.TRANSACTION_ID);
				message = MessageUtil.createGetTemperatureReturnMessage(
						generatedDeviceId, transactionId);

			} else {
				String deviceId = Util.generateDevieIdDevice
						.get(generatedDeviceId);
				JButton jButton = Util.deviceIdButton.get(deviceId);
				String command = this.properties
						.getProperty(Constants.ZW_DEVICE_CONTROL);
				String action = command.compareTo("0") == 0 ? Constants.DEVICE_DOWN
						: Constants.DEVICE_UP;
				Util.handleLightButtonClick(jButton, action);
				message = MessageUtil
						.createDeviceControlSuccessMessage(properties);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			MessageSender.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// String message =
		// MessageUtil.createRemoveDeviceReturnMessage(generatedDeviceId,
		// transactionId);
	}

}
