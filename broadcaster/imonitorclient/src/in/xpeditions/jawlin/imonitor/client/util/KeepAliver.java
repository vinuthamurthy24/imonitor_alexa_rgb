/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.util;

import java.io.IOException;

import in.xpeditions.jawlin.imonitor.client.communicator.MessageSender;

/**
 * @author Coladi
 *
 */
public class KeepAliver implements Runnable{

	@Override
	public void run() {
		String keepAliveMessage = MessageUtil.createKeepAliveMessage();
		while(true){
			try {
				MessageSender.sendMessage(keepAliveMessage);
				Thread.sleep(15000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
