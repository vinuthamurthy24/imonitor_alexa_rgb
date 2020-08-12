/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AVCategory;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SceneControllerMake;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.MakeManager;
import in.xpeditions.jawlin.imonitor.general.DataTable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

public class MakeServiceManager {
	
	public String saveMake(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		Make make=(Make)xStream.fromXML(xml);
		MakeManager makeManager = new MakeManager();
		if(makeManager.saveMake(make)){
			result="yes";
		}
		return result;
	}
	public String deleteMake(String idS) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="yes";
		long id = Long.parseLong(idS);
		MakeManager makeManager = new MakeManager();
		makeManager.deleteMake(id);
		return result;
	}
	public String getMakeById(String idS) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		long id = Long.parseLong(idS);
		XStream xStream=new XStream();
		MakeManager makeManager=new MakeManager();
		Make make = makeManager.getMakeById(id);
		result = xStream.toXML(make);
		return result;
	}
	public String updateMake(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		Make make=(Make)xStream.fromXML(xml);
		MakeManager makeManager=new MakeManager();
		if(makeManager.updateMake(make)){result="yes";}
		return result;
	}
	public String listMakes() throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream=new XStream();
		MakeManager makeManager=new MakeManager();
		List<Make>makes=makeManager.listOfMakes();
		String xml=xStream.toXML(makes);
		return xml;
	}
	public String listOnlyMakes() throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream = new XStream();
		MakeManager makeManager = new MakeManager();
		List<Make>makes = makeManager.listOnlyMakes();
		String xml = xStream.toXML(makes);
		return xml;
	}
	public String listAskedMakes(String xml) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream = new XStream();
		DataTable dataTable = (DataTable) xStream.fromXML(xml);
		MakeManager makeManager = new MakeManager();
		int count = makeManager.getTotalMakeCount();
		dataTable.setTotalRows(count);
		String sSearch = dataTable.getsSearch();
		String[] cols = { "m.name","m.number","m.details","d.name"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = makeManager.listAskedMakes(sSearch,sOrder, dataTable
				.getiDisplayStart(), dataTable.getiDisplayLength());
		dataTable.setResults(list);
		int displayRow = makeManager.getTotalMakeCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	public String listMakesByDeviceType(String xmlGeneratedId) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream = new XStream();
		String generatedDeviceId = (String) xStream.fromXML(xmlGeneratedId);
		MakeManager makeManager = new MakeManager();
		List<Make>makes = makeManager.listMakesByDeviceType(generatedDeviceId);
		String xml=xStream.toXML(makes);
		
		return xml;
	}
	public String listMakesByDeviceTypehealth(String xmldevicetype) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream = new XStream();
		String devicetype = (String) xStream.fromXML(xmldevicetype);
		MakeManager makeManager = new MakeManager();
		List<Make>makes = makeManager.listMakesByDeviceTypehealth(devicetype);
		String xml=xStream.toXML(makes);
		return xml;
	}
	
	//naveen start for ac auto search
	public String listAutoMakeForAc() throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream = new XStream();
		MakeManager makeManager = new MakeManager();
		List<String> acBrandList = new ArrayList<String>();
		acBrandList = makeManager.listAutoMakesForAc();
		String xml=xStream.toXML(acBrandList);
	    
		return xml;
	}
	
	
	public String getbrandcodesforselectedAc(String selectedAcBrand) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		XStream xStream = new XStream();
		MakeManager makeManager = new MakeManager();
		String brandName = (String) xStream.fromXML(selectedAcBrand);
		List<String> acBrandcodeList = new ArrayList<String>();
		acBrandcodeList = makeManager.getCodeListbySelectedBrand(brandName);
		String xml=xStream.toXML(acBrandcodeList);
		return xml;
	}
	
	public String savesaveSceneControllermake(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream xStream=new XStream();
		SceneControllerMake sceneControllerMake=(SceneControllerMake)xStream.fromXML(xml);
		MakeManager makeManager = new MakeManager();
		if(makeManager.saveSceneControllermake(sceneControllerMake)){
			result="yes";
		}
		return result;
	}
	
	
	
}
