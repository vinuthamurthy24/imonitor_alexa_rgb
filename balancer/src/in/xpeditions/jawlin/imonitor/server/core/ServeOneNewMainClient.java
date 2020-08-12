/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.core;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Queue;

/**
 * @author Coladi
 *
 */
public class ServeOneNewMainClient implements Runnable {

	private Socket socket;

	public ServeOneNewMainClient(Socket socket) {
		this.socket = socket;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			InputStream inputStream = this.socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			String messageFromClient = dataInputStream.readUTF();
			LogUtil.info("Message From Client : \n" + messageFromClient);
			
//			Check format of messageFromClient and give back the event server ip and port.
			Queue<KeyValuePair> events = IMonitorUtil.commandToQueue(messageFromClient);
			Queue<KeyValuePair> updateResults = IMonitorUtil.updateEvent(events);
			String messageToClient = IMonitorUtil.convertToStringFromQueue(updateResults);
			messageToClient = IMonitorUtil.appendSignature(messageToClient);
			
			OutputStream outputStream = this.socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeUTF(messageToClient);
			LogUtil.info("Message to Client : \n" + messageToClient);
			
		} catch (IOException e) {
			LogUtil.error("Problem occured while serving a new main client. : " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			LogUtil.error("Problem occured while serving a new main client. : " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			LogUtil.error("Problem occured while serving a new main client. : " + e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			LogUtil.error("Problem occured while serving a new main client. : " + e.getMessage());
			e.printStackTrace();
		} catch (InstantiationException e) {
			LogUtil.error("Problem occured while serving a new main client. : " + e.getMessage());
			e.printStackTrace();
		}
	}

}
