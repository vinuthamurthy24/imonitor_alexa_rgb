/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.communicator;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Coladi
 *
 */
public class MessageSender {
	private static DataOutputStream dataOutputStream;

	public static DataOutputStream getDataOutputStream() {
		return dataOutputStream;
	}

	public static void setDataOutputStream(DataOutputStream dataOutputStream) {
		MessageSender.dataOutputStream = dataOutputStream;
	}

	public static void sendMessage(String deviceDiscoveryMessage) throws IOException {
		System.out.println(deviceDiscoveryMessage);
		MessageSender.dataOutputStream.writeUTF(deviceDiscoveryMessage);
	}
}
