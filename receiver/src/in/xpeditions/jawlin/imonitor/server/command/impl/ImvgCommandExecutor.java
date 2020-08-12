/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.command.impl;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Coladi
 *
 */
public class ImvgCommandExecutor implements CommandExecutor {
	
	private Map<String, Socket> imvgConnections;
	
	public ImvgCommandExecutor() {
		this.imvgConnections = new ConcurrentHashMap<String, Socket>();
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor#executeCommand(java.util.Map)
	 */
	@Override
	public void executeCommand(Queue<KeyValuePair> commands) {
		
		String imvgId = IMonitorUtil.commandId(commands, Constants.IMVG_ID);
		String messageToClient = IMonitorUtil.convertToStringFromQueue(commands);
		//String cmdID = IMonitorUtil.commandId(commands, Constants.CMD_ID);
		if(imvgId == null){
			LogUtil.error("iMVG is request is null. message you send is : " + messageToClient);
			return;
		}
		try {
			messageToClient = IMonitorUtil.appendSignature(messageToClient);
			Socket socket = this.imvgConnections.get(imvgId);
			if(socket == null){
				LogUtil.error("Connection is either closed or not established from the imvg : " + imvgId);
				return;
			}
			OutputStream outputStream = socket.getOutputStream();
			
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeUTF(messageToClient);
			LogUtil.info("Message To Client:\n" + messageToClient);
		
		
		} catch (IOException e) {
			LogUtil.error("Exception occured while communicating with imvg with id : " + imvgId + " and message is : " + e.getMessage());
//			Removing the output stream from our map.
			this.imvgConnections.remove(imvgId);
		}
	}

	@Override
	public void addImvg(String imvgId, Socket socket) {
		this.imvgConnections.put(imvgId, socket);
		LogUtil.info("adding imvg to hash : " + imvgId + "and the hash size is: " + this.imvgConnections.size());
	}

	@Override
	public boolean isConnectionExists(String imvgId) {
		return this.imvgConnections.get(imvgId) != null;
	}

	@Override
	public void killConnection(String imvgId) {
		Socket socket = null;
		synchronized (this.imvgConnections) {
			socket = this.imvgConnections.remove(imvgId);
			LogUtil.info("killing connection for imvg : " + imvgId + "and the hash size is: " + this.imvgConnections.size());
			
		}
		if(socket != null && !socket.isClosed()){
			try {
				LogUtil.info("closing socket");
				socket.close();
			} catch (IOException e) {
				LogUtil.error("Error when closing connection from iMVG. may not be serios. Because we are here, since iMVG reconnected and we have new connection now." + e.getMessage());
			} 
		}
	}

}
