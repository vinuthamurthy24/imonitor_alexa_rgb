/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.executor;

import java.io.IOException;
import java.util.Properties;

import in.xpeditions.jawlin.imonitor.client.communicator.MessageSender;
import in.xpeditions.jawlin.imonitor.client.util.DeviceDiscoveryModeDisplay;
import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;

/**
 * @author Coladi
 *
 */
public class DiscoverDevicesExecutor implements Runnable {

	private Properties properties;

	public DiscoverDevicesExecutor(Properties properties) {
		this.properties = properties;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		DeviceDiscoveryModeDisplay deviceDiscoveryModeDisplay = new DeviceDiscoveryModeDisplay();
		Thread t = new Thread(deviceDiscoveryModeDisplay);
		t.start();
		String deviceDiscoveryMessage = MessageUtil.createDeviceDiscoveryAckMessage(this.properties);
		try {
			MessageSender.sendMessage(deviceDiscoveryMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
