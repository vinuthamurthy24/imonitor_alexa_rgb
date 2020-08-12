/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.multicaster;

import java.net.Socket;

/**
 * @author Coladi
 *
 */
public class ClientModel {
	private String sessionId;
	private Socket socket;
	
	public ClientModel(Socket socket, String sessionId) {
		this.socket = socket;
		this.sessionId = sessionId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public Socket getSocket() {
		return socket;
	}
}
