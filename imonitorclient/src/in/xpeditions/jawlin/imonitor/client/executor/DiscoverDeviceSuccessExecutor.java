/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.executor;

import in.xpeditions.jawlin.imonitor.client.util.Constants;
import in.xpeditions.jawlin.imonitor.client.util.Util;

import java.util.Properties;

/**
 * @author Coladi
 *
 */
public class DiscoverDeviceSuccessExecutor implements Runnable {

	private Properties properties;

	public DiscoverDeviceSuccessExecutor(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void run() {
		String tId = this.properties.getProperty(Constants.TRANSACTION_ID);
		String deviceId = Util.transactionIdDevice.get(tId);
		Util.generateDevieIdDevice.put(this.properties.getProperty(Constants.DEVICE_ID), deviceId);
		Util.deviceGenerateDevieId.put(deviceId,this.properties.getProperty(Constants.DEVICE_ID));
	}

}
