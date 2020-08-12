/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.List;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;

/**
 * @author Coladi
 * 
 */
public class BroadCasterCommunicator {

	public String listAllStreamsWithClients(List<Agent> agents) {
//		Change the following implementations ...
		String streams = "<livestreams>";
		for (Agent agent : agents) {
			String host = agent.getStreamingIp();
			int port = agent.getControllerBroadCasterPort();
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
					.getDefault();
			SSLSocket sslsocket;
			try {
				sslsocket = (SSLSocket) sslsocketfactory.createSocket(host, port);
				OutputStream outputStream = sslsocket.getOutputStream();
				DataOutputStream dataOutputStream = new DataOutputStream(
						outputStream);
				DataInputStream dataInputStream = new DataInputStream(sslsocket.getInputStream());
				dataOutputStream.writeUTF(Constants.GET_ALL_STREAMS_WITH_CLIENTS);
				String result = dataInputStream.readUTF();
				streams += result;
				sslsocket.close();
			}catch (UnknownHostException e) {
				LogUtil.error("Error when communicating to broadcaster. " + e.getMessage());
			} catch (IOException e) {
				LogUtil.error("Error when communicating to broadcaster. " + e.getMessage());
			}
		}
		streams += "</livestreams>";
		return streams;
	}

	public String killLiveStream(String deviceId, Agent agent) {

		String actionXml = createRequestXml(deviceId,"STOP_STREAM");
		
		//		Change the following implementations ...
		String host = agent.getStreamingIp();
		int port = agent.getControllerBroadCasterPort();
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
				.getDefault();
		SSLSocket sslsocket;
		String result = null;
		try {
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(host, port);
			OutputStream outputStream = sslsocket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);
			DataInputStream dataInputStream = new DataInputStream(sslsocket.getInputStream());
			//dataOutputStream.writeUTF(Constants.SEND_TEAR_DOWN_TO_PROVIDER + ":" + deviceId);
			dataOutputStream.writeUTF(actionXml);
			result = dataInputStream.readUTF();
			sslsocket.close();
		}catch (UnknownHostException e) {
			LogUtil.error("Error when communicating to broadcaster. " + e.getMessage());
		} catch (IOException e) {
			LogUtil.error("Error when communicating to broadcaster. " + e.getMessage());
		}
		return result;
	}

	public String getRtspUrlForDevice(String generatedDeviceId, Agent agent) {
		String actionXml = createRequestXml(generatedDeviceId, Constants.GET_RTSP_URL);
		String host = agent.getStreamingIp();
		int port = agent.getControllerBroadCasterPort();
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
				.getDefault();
		SSLSocket sslsocket;
		String result = null;
		try {
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(host, port);
			OutputStream outputStream = sslsocket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);
			DataInputStream dataInputStream = new DataInputStream(sslsocket.getInputStream());
			dataOutputStream.writeUTF(actionXml);
			result = dataInputStream.readUTF();
			sslsocket.close();
		}catch (UnknownHostException e) {
			LogUtil.error("Error when communicating to broadcaster. " + e.getMessage());
		} catch (IOException e) {
			LogUtil.error("Error when communicating to broadcaster. " + e.getMessage());
		}
		return result;
	}

	public String waitForRtspUrlForDevice(String generatedDeviceId, Agent agent) {
		String actionXml = createRequestXml(generatedDeviceId, Constants.WAIT_GET_RTSP_URL);
		String host = agent.getStreamingIp();
		int port = agent.getControllerBroadCasterPort();
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
				.getDefault();
		SSLSocket sslsocket;
		String result = null;
		try {
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(host, port);
			OutputStream outputStream = sslsocket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);
			DataInputStream dataInputStream = new DataInputStream(sslsocket.getInputStream());
			dataOutputStream.writeUTF(actionXml);
			result = dataInputStream.readUTF();
			sslsocket.close();
		}catch (UnknownHostException e) {
			LogUtil.error("Error when communicating to broadcaster. " + e.getMessage());
		} catch (IOException e) {
			LogUtil.error("Error when communicating to broadcaster. " + e.getMessage());
		}
		return result;
	}

	private String createRequestXml(String generatedDeviceId, String actionName) {
		String actionXml = "";
		actionXml += "<imoitor>";
		actionXml += "<action>";
		actionXml += "<name>";
		actionXml += actionName;
		actionXml += "</name>";
		actionXml += "<params>";
		actionXml += "<param>";
		actionXml += "<name>";
		actionXml += Constants.DEVICE_ID;
		actionXml += "</name>";
		actionXml += "<value>";
		actionXml += generatedDeviceId;
		actionXml += "</value>";
		actionXml += "</param>";
		actionXml += "</params>";
		actionXml += "</action>";
		actionXml += "</imoitor>";
		return actionXml;
	}
}
