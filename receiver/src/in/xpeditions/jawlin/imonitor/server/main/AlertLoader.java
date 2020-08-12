/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.main;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.UpdatorModel;
import in.xpeditions.jawlin.imonitor.server.util.XmlUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Queue;

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
public class AlertLoader {

	public void loadAllAlerts() {
		try {
			InputStream inputStream = ClassLoader.getSystemResource("alerts.xml").openStream();
			Document document = XmlUtil.getDocument(inputStream);
			NodeList commandElements = document.getElementsByTagName(Constants.IMONITOR_ALERT_STRING);
			int commandsLength = commandElements.getLength();
			for (int i = 0; i < commandsLength; i++) {
				Node commandNode = commandElements.item(i);
				Element idNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_ID);
				Element classNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_CLASS);
				Element methodNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_METHOD);
				String id = XmlUtil.getCharacterDataFromElement(idNode);
				String className = XmlUtil.getCharacterDataFromElement(classNode);
				String methodName = XmlUtil.getCharacterDataFromElement(methodNode);
			//	LogUtil.info("methodName: "+methodName +" id: "+id+" className: "+className);
				Class<?> classRep = Class.forName(className);
				Method method = classRep.getDeclaredMethod(methodName, Queue.class);
				UpdatorModel<?> model = new UpdatorModel<Object>(classRep, method);
				IMonitorUtil.getAlertUpdators().put(id, model);
			}
		} catch (ParserConfigurationException e) {
			LogUtil.error("1.error when loading alerts. " + e.getMessage());
		} catch (SAXException e) {
			LogUtil.error("2.error when loading alerts. " + e.getMessage());
		} catch (IOException e) {
			LogUtil.error("3.error when loading alerts. " + e.getMessage());
		} catch (ClassNotFoundException e) {
			LogUtil.error("4.error when loading alerts. " + e.getMessage());
		} catch (SecurityException e) {
			LogUtil.error("5.error when loading alerts. " + e.getMessage());
		} catch (NoSuchMethodException e) {
			LogUtil.error("6.error when loading alerts. " + e.getMessage());
		}
	}

}
