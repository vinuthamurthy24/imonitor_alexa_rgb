/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.communication;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;



/**
 * @author Coladi
 *
 */
public class RequestProcessor {

	public void processRequest(String message) throws ParserConfigurationException, SAXException, IOException {
		Map<String, String> convertedMap = IMonitorUtil.convertToMapFromXml(message);
		String imvgId = convertedMap.get(Constants.IMVG_ID);
		GatewayManager gatewayManager = new GatewayManager();
		GateWay gateWay = gatewayManager.getGateWayByMacId(imvgId);
		this.processRequest(message, gateWay);
//		4. Save the current status of request in db.
	}

	public void processRequest(String message, GateWay gateWay) {
		String ip = gateWay.getAgent().getIp();
		int port = gateWay.getAgent().getControllerReceiverPort();
		this.processRequest(message, ip, port);
	}

	public void processRequest(String message, String ip, int port) {
		RequestSender requestSender = new RequestSender(ip, port);
		requestSender.sendRequest(message);
	}

}
