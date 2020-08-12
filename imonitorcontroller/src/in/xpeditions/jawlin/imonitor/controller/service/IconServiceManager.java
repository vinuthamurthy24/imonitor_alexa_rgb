/* Copyright © 2012 iMonitor Solutions India Private Limited */


package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemIcon;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.IconManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;



import com.thoughtworks.xstream.XStream;

public class IconServiceManager {
	
	public String listIconsByDeviceType(String xml){							
		XStream xStream = new XStream();
		long deviceType = (Long) xStream.fromXML(xml);							
		String xmlOfIconList = null;
		try {		
			List<Icon> listOfIconsByDeviceType = new IconManager()
					.getListOfIconsByDeviceType(deviceType);					
			
			XStream stream = new XStream();
			xmlOfIconList = stream.toXML(listOfIconsByDeviceType);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
			
		}
		return xmlOfIconList;					

		
	}
	
//pari start
	public String listIconsForLocation(){							
		String xmlOfIconList = null;
		try {					
			List<SystemIcon> listIconsforlocation = new IconManager().getListOfIconsForLocation();					

			XStream stream = new XStream();
			xmlOfIconList = stream.toXML(listIconsforlocation);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());			
		}
		return xmlOfIconList;					
	}
//pari end
	
//pari start
		public String listIconsForScenario(){							
			String xmlOfIconList = null;
			try {					
				List<SystemIcon> listIconsforscenario = new IconManager().getListOfIconsForScenario();					

				XStream stream = new XStream();
				xmlOfIconList = stream.toXML(listIconsforscenario);
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.error(e.getMessage());			
			}
			return xmlOfIconList;					
		}
	//pari end
	
	public String updateIcon(String xml) throws SecurityException,
			DOMException, IllegalArgumentException,
			ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		
		
		XStream xStream = new XStream();
		Device deviceFromWeb = (Device) xStream.fromXML(xml);
		long id = deviceFromWeb.getId();
		DeviceManager deviceManager = new DeviceManager();
		Device device = deviceManager.getDevice(id);
		
		long iconId = deviceFromWeb.getIcon().getId();  					
		
		IconManager iconManager = new IconManager();
		Icon icon = iconManager.getIconById(iconId);	
		device.setIcon(icon);
		deviceManager.updateDevice(device);
		return xStream.toXML(device);
		
	}
	
	////Mobile Icons
	
	public String listMobileIconsForLocation(){							
		String xmlOfIconList = null;
		try {					
			List<SystemIcon> listIconsforlocation = new IconManager().getListOfMobileIconsForLocation();					

			XStream stream = new XStream();
			xmlOfIconList = stream.toXML(listIconsforlocation);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());			
		}
		return xmlOfIconList;					
	}

}
