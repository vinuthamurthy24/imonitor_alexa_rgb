/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.communication;

import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author Coladi
 * 
 */
public class RequestSender {

	private String ip;
	private int port;

	public RequestSender(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void sendRequest(String xml) {
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
				.getDefault();
		SSLSocket sslsocket;
		try {
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(
					this.ip, this.port);
			OutputStream outputStream = sslsocket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeUTF(xml);
		} catch (UnknownHostException e) {
			LogUtil.error("Error when communicating to IP : " + this.ip + " port : " + this.port + " message : " + e.getMessage());
		} catch (IOException e) {
			LogUtil.error("Error when communicating to IP : " + this.ip + " port : " + this.port + " message : " + e.getMessage());
		}
	}

}
