/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWayType;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GateWayTypeManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

public class GatewayTypeServiceManager {
	public String saveGateWayType(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		GateWayType gateWayType=(GateWayType)xStream.fromXML(xml);
		GateWayTypeManager gateWayTypeManager=new GateWayTypeManager();
		if(gateWayTypeManager.saveGateWayType(gateWayType)){result="yes";}
		return result;
		
	}
	public String deleteGateWayType(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		GateWayType gateWayType=(GateWayType)xStream.fromXML(xml);
		GateWayTypeManager gateWayTypeManager=new GateWayTypeManager();
		if(gateWayTypeManager.deleteGateWayType(gateWayType)){result="yes";}
		return result;
	}
	public String updateGateWayType(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		GateWayType gateWayType=(GateWayType)xStream.fromXML(xml);
		GateWayTypeManager gateWayTypeManager=new GateWayTypeManager();
		if(gateWayTypeManager.deleteGateWayType(gateWayType)){result="yes";}
		return result;
	}
	public String listGateWayTypes() throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream=new XStream();
		GateWayTypeManager gateWayTypeManager=new GateWayTypeManager();
		List<GateWayType>gateWayTypes= gateWayTypeManager.listGateWayTypes();
		String xml=xStream.toXML(gateWayTypes);
		return xml;
	}
}
