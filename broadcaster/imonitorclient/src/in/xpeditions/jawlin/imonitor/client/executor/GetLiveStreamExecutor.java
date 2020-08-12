/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.executor;

import in.xpeditions.jawlin.imonitor.client.communicator.MessageSender;
import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Coladi
 *
 */
public class GetLiveStreamExecutor implements Runnable {

	private Properties properties;

	public GetLiveStreamExecutor(Properties properties) {
		this.properties = properties;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		String message = MessageUtil
		.createDeviceControlSuccessMessage(properties);
		try {
			MessageSender.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
