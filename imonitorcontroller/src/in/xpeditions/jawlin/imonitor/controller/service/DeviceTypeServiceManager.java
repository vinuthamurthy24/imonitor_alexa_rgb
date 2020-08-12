/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Action;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.NewDeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceTypeManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.general.DataTable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

public class DeviceTypeServiceManager {
	public String saveDeviceType(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		DeviceType deviceType=(DeviceType)xStream.fromXML(xml);
		DeviceTypeManager deviceTypeManager=new DeviceTypeManager();
		if(deviceTypeManager.saveDeviceType(deviceType)){result="yes";}
		return result;
	}
	public String deleteDeviceType(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		DeviceType deviceType=(DeviceType)xStream.fromXML(xml);
		DeviceTypeManager deviceTypeManager=new DeviceTypeManager();
		if(deviceTypeManager.deleteDeviceType(deviceType)){result="yes";}
		return result;
	}
	public String updateDeviceType(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		DeviceType deviceType=(DeviceType)xStream.fromXML(xml);
		DeviceTypeManager deviceTypeManager=new DeviceTypeManager();
		if(deviceTypeManager.updateDeviceType(deviceType)){result="yes";}
		return result;
	}
	public String listDeviceTypes() throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream=new XStream();
		DeviceTypeManager deviceTypeManager=new DeviceTypeManager();
		List<DeviceType>deviceTypes=deviceTypeManager.listOfDeviceTypes();
		String xml=xStream.toXML(deviceTypes);
		return xml;
	}
	public String listDeviceTypesWithoutVirtualDevice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream=new XStream();
		DeviceTypeManager deviceTypeManager=new DeviceTypeManager();
		List<DeviceType>deviceTypes=deviceTypeManager.listOfDeviceTypesWithoutVirtualDevice();
		String xml=xStream.toXML(deviceTypes);
		return xml;
	}
	
	public String saveNewDeviceType(String newDevicexml){
		String result = "no";
		XStream xStream=new XStream();
		NewDeviceType newDeviceType = (NewDeviceType)xStream.fromXML(newDevicexml);
		boolean res=new DaoManager().save(newDeviceType);
		
		if (res==true) {
			result = "yes";
		}
	
		return result;
	}
	
	public String listAskedDeviceTypes(String xml) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		/*XStream xStream = new XStream();
		DataTable dataTable = (DataTable) xStream.fromXML(xml);
		DeviceTypeManager deviceTypeManager=new DeviceTypeManager();
		int count = deviceTypeManager.getTotalDeviceTypeCount();
		dataTable.setTotalRows(count);
		String sSearch = dataTable.getsSearch();
		String[] cols = { "m.name","m.number","m.details","d.name"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = deviceTypeManager.listAskedDeviceTypes(sSearch,sOrder, dataTable
				.getiDisplayStart(), dataTable.getiDisplayLength());
		dataTable.setResults(list);
		int displayRow = deviceTypeManager.getTotalDeviceTypeCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);*/
		return null;
	}
	
}
