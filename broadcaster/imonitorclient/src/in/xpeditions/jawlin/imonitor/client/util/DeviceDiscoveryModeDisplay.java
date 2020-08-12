/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.util;

/**
 * @author Coladi
 *
 */
public class DeviceDiscoveryModeDisplay implements Runnable{

	@Override
	public void run() {
		while (true) {
			Util.DEVICE_DISCOVERY_LABEL.setVisible(!Util.DEVICE_DISCOVERY_LABEL.isVisible());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
