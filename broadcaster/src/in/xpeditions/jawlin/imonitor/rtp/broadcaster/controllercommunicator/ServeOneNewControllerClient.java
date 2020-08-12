/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.controllercommunicator;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.action.ActionExecutorManager;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.XmlUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import javax.net.ssl.SSLSocket;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ServeOneNewControllerClient implements Runnable {

	private SSLSocket socket;
	private PacketFlowManageListener packetFlowManageListener;

	public ServeOneNewControllerClient(SSLSocket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
//			Now we check the existence of streaming socket. If exists return success, else failure
			DataInputStream dataInputStream = new DataInputStream(this.socket.getInputStream());
			String xmlContent = dataInputStream.readUTF();
			Document document = XmlUtil.getDocument(xmlContent);
			NodeList actionElements = document.getElementsByTagName(Constants.IMONITOR_ACTION_STRING);
			int commandsLength = actionElements.getLength();
			for (int i = 0; i < commandsLength; i++) {
				Node action = actionElements.item(i);
				Element nameNode = (Element) XmlUtil.getChildElementByTagName(action, Constants.NAME_TAG);
				List<Node> paramsNodes = XmlUtil.getChildElementsByTagName(action, Constants.PARAMS_TAG);
				String actionName = XmlUtil.getCharacterDataFromElement(nameNode);
				if(actionName != null){
					actionName = actionName.trim();
				}else{
					LogUtil.info("Proper action name has not been provided from the client");
					return;
				}
				ActionExecutorManager actionExecutorManager = new ActionExecutorManager(actionName, paramsNodes, this.socket, this.packetFlowManageListener);
				Thread t = new Thread(actionExecutorManager);
				t.start();
			}
		} catch (IOException e) {
			LogUtil.error("Error when listing the live stream sessions : " + e.getMessage());
		} catch (ParserConfigurationException e) {
			LogUtil.error("Error in the xml format : " + e.getMessage());
		} catch (SAXException e) {
			LogUtil.error("Error in the xml format : " + e.getMessage());
		}
	}

	public void addPacketFlowManageListener(
			PacketFlowManageListener packetFlowManageListener) {
		this.packetFlowManageListener = packetFlowManageListener;
	}

}
