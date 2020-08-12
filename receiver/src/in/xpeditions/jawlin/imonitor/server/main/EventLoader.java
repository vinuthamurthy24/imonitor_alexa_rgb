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
public class EventLoader {

	public void loadAllEvents() {
		try {
			InputStream inputStream = ClassLoader.getSystemResource("events.xml").openStream();
			Document document = XmlUtil.getDocument(inputStream);
			NodeList commandElements = document.getElementsByTagName(Constants.IMONITOR_EVENT_STRING);
			int commandsLength = commandElements.getLength();
			for (int i = 0; i < commandsLength; i++) {
				Node commandNode = commandElements.item(i);
				Element idNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_ID);
				Element classNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_CLASS);
				Element methodNode = (Element) XmlUtil.getChildElementByTagName(commandNode, Constants.IMONITOR_COMMAND_METHOD);
				String id = XmlUtil.getCharacterDataFromElement(idNode);
				LogUtil.info("id::"+id);
				String className = XmlUtil.getCharacterDataFromElement(classNode);
				String methodName = XmlUtil.getCharacterDataFromElement(methodNode);
				LogUtil.info("methodName: "+methodName +" id: "+id+" className: "+className);
				Class<?> classRep = Class.forName(className);
				Method method = classRep.getDeclaredMethod(methodName, Queue.class);
				UpdatorModel<?> model = new UpdatorModel<Object>(classRep, method);
				IMonitorUtil.getEventUpdators().put(id, model);
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

}
