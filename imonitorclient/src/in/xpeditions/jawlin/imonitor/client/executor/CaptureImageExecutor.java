/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.executor;

import in.xpeditions.jawlin.imonitor.client.communicator.MessageSender;
import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Coladi
 *
 */
public class CaptureImageExecutor implements Runnable {

	private Properties properties;

	public CaptureImageExecutor(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void run() {
		String message = MessageUtil
		.createCaptureImageAckMessage(properties);
		try {
			MessageSender.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			message = MessageUtil
			.uploadImageAndCreateFtpImageSuccess(properties);
			MessageSender.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
