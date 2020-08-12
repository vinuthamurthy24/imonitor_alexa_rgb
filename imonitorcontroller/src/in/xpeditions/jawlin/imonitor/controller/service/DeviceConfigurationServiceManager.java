/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CameraConfigurationManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

public class DeviceConfigurationServiceManager {
	public String saveCameraConfiguration(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		XStream stream=new XStream();
		CameraConfiguration cameraConfiguration =(CameraConfiguration)stream.fromXML(xml);
		CameraConfigurationManager deviceManager=new CameraConfigurationManager();
		deviceManager.saveCameraConfiguration(cameraConfiguration);
		return "success";
	}
	
	public String updateCameraConfiguration(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		XStream stream=new XStream();
		CameraConfiguration cameraConfiguration =(CameraConfiguration)stream.fromXML(xml);
		CameraConfigurationManager deviceManager=new CameraConfigurationManager();
		deviceManager.updateCameraConfiguration(cameraConfiguration);
		return "success";
	}
}
