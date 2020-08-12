/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.server;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.controllercommunicator.ControllerCommunicationServer;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.RTPPacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.provider.PacketReceiver;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.receiver.RequestReceiver;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.BroadCasterActionMap;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.UpdatorModel;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.XmlUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Coladi
 *
 */
public class RTPBroadCaster implements Runnable{

	private Properties properties;
	private String propertiesFile = "imvgserver.properties";
	@Override
	public void run() {
		try {
			
//		1. Load the property file.
			LogUtil.info("Loading Property File.");
			loadPropertyFile();
			loadActions();
//			Listener which handles the communication.	
			PacketFlowManageListener packetFlowManageListener = new RTPPacketFlowManageListener();
			
//		2. Start the packet receiver.
			LogUtil.info("Starting packet receiver.");
			PacketReceiver packetReceiver = new PacketReceiver();
			packetReceiver.addPacketFlowManageListener(packetFlowManageListener);
			Thread t = new Thread(packetReceiver);
			t.start();
			
//		3. Start the request receiver.
			LogUtil.info("Starting request receiver.");
			RequestReceiver requestReceiver = new RequestReceiver();
			requestReceiver.addPacketFlowManageListener(packetFlowManageListener);
			Thread tr = new Thread(requestReceiver);
			tr.start();
			
//			4. Starting the controller communication server.
			ControllerCommunicationServer controllerCommunicationServer = new ControllerCommunicationServer(this.properties);
			controllerCommunicationServer.addPacketFlowManageListener(packetFlowManageListener);
			Thread tc = new Thread(controllerCommunicationServer);
			tc.start();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LogUtil.error("Error when starting broadcaster " + e.getMessage());
		} catch (IOException e) {
			LogUtil.error("Error when starting broadcaster " + e.getMessage());
			e.printStackTrace();
		}
	}
	private void loadActions() {

//		Reading actions.
		try {
			InputStream inputStream = ClassLoader.getSystemResource("actions.xml").openStream();
			Document document = XmlUtil.getDocument(inputStream);
			NodeList actionElements = document.getElementsByTagName(Constants.IMONITOR_ACTION_STRING);
			int actionsLength = actionElements.getLength();
			for (int i = 0; i < actionsLength; i++) {
				Node commandNode = actionElements.item(i);
				Element nameNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.NAME_TAG);
				Element classNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_CLASS);
				Element methodNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_METHOD);
				String name = XmlUtil.getCharacterDataFromElement(nameNode);
				String className = XmlUtil.getCharacterDataFromElement(classNode);
				String methodName = XmlUtil.getCharacterDataFromElement(methodNode);
				if(methodName == null){
					methodName = "execute";
				}
				Class<?> classRep = Class.forName(className);
				Method method = classRep.getDeclaredMethod(methodName, Map.class);
				UpdatorModel<?> model = new UpdatorModel<Object>(classRep, method);
				BroadCasterActionMap.put(name, model);
			}
		} catch (ParserConfigurationException e) {
			LogUtil.error("Error when starting the events. " + e.getMessage());
		} catch (SAXException e) {
			LogUtil.error("Error when starting the events. " + e.getMessage());
		} catch (IOException e) {
			LogUtil.error("Error when starting the events. " + e.getMessage());
		} catch (ClassNotFoundException e) {
			LogUtil.error("Error when starting the events. " + e.getMessage());
		} catch (SecurityException e) {
			LogUtil.error("Error when starting the events. " + e.getMessage());
		} catch (NoSuchMethodException e) {
			LogUtil.error("Error when starting the events. " + e.getMessage());
		}
		
	}
	private void loadPropertyFile() throws FileNotFoundException, IOException {
		this.properties = new Properties();
		File propertyFile = new File(this.propertiesFile);
		this.properties.load(new FileInputStream(propertyFile));
		ImonitorControllerCommunicator.put(Constants.CONTROLLER_IP, this.properties.getProperty(Constants.CONTROLLER_IP));
		ImonitorControllerCommunicator.put(Constants.CONTROLLER_PORT, this.properties.getProperty(Constants.CONTROLLER_PORT));
		ImonitorControllerCommunicator.put(Constants.CONTROLLER_PROTOCOL, this.properties.getProperty(Constants.CONTROLLER_PROTOCOL));
		ImonitorControllerCommunicator.put(Constants.SECURE_MODE, this.properties.getProperty(Constants.SECURE_MODE));
		ImonitorControllerCommunicator.put(Constants.STREAMING_PORT, this.properties.getProperty(Constants.STREAMING_PORT));
		ImonitorControllerCommunicator.put(Constants.CONTROLLER_BROADCASTER_PORT, this.properties.getProperty(Constants.CONTROLLER_BROADCASTER_PORT));
		ImonitorControllerCommunicator.put(Constants.RTSP_IP, this.properties.getProperty(Constants.RTSP_IP));
		ImonitorControllerCommunicator.put(Constants.RTSP_PORT, this.properties.getProperty(Constants.RTSP_PORT));
		ImonitorControllerCommunicator.put(Constants.CONNECTION_TIMEOUT, this.properties.getProperty(Constants.CONNECTION_TIMEOUT));
		ImonitorControllerCommunicator.put(Constants.RTSP_CONNECTION_FROM_IMVGWAIT, this.properties.getProperty(Constants.RTSP_CONNECTION_FROM_IMVGWAIT));
	}
	public void stop() throws FileNotFoundException, IOException {

//		1. Load the property file.
			LogUtil.info("Loading Property File.");
			loadPropertyFile();
			
			int port = Integer.parseInt((String) this.properties.get(Constants.CONTROLLER_BROADCASTER_PORT));
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
			.getDefault();
			SSLSocket primarySocket = (SSLSocket) sslsocketfactory.createSocket("localhost", port);
			
			String actionXml = "";
			actionXml += "<imoitor>";
			actionXml += "<action>";
			actionXml += "<name>";
			actionXml += "BROADCASTER_SHUTDOWN";
			actionXml += "</name>";
			actionXml += "</action>";
			actionXml += "</imoitor>";
			
			// Sending the message to server.
			OutputStream outputStream = primarySocket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);
			dataOutputStream.writeUTF(actionXml);
	}
}
