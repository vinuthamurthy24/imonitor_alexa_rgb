/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;



import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlert;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.SystemAlertManager;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;


public class SystemAlertServiceManager {
	
	public String listSystemAlert() throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream=new XStream();
		SystemAlertManager systemAlertManager = new SystemAlertManager();
		List<SystemAlert> systemAlerts = systemAlertManager.listOfSystemAlerts();
		String xml=xStream.toXML(systemAlerts);
		return xml;
	}
	
	public String listSystemAlertByUser(String xml) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream=new XStream();
		User user = (User) xStream.fromXML(xml);
		SystemAlertManager systemAlertManager = new SystemAlertManager();
		List<Object[]> systemAlertAction = systemAlertManager.getSystemAlertByUser(user.getId());
		String xmlString=xStream.toXML(systemAlertAction);
		return xmlString;
	}
	
	public String updateSystemAlert(String xml,String smsXml,String emailXml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		SystemAlertManager systemAlertManager = new SystemAlertManager();
		User user= (User) xStream.fromXML(xml);
		String[] sms = (String[]) xStream.fromXML(smsXml);
		String[] email = (String[]) xStream.fromXML(emailXml);
		
				systemAlertManager.deleteSystemAlertByUser(user.getId());
				if(systemAlertManager.saveSystemAlertAction(email,sms,user))
					{result="yes";}
		return result;
	}
	
}
