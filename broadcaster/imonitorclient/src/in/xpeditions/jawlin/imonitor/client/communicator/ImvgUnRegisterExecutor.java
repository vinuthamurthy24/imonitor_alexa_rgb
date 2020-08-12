/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.communicator;

import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Coladi
 *
 */
public class ImvgUnRegisterExecutor implements Runnable {

	private Properties properties;

	public ImvgUnRegisterExecutor(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void run() {
		String message = MessageUtil.createImvgUnRegisterMessage(properties);
		try {
			MessageSender.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
