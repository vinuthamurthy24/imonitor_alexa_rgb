/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CostDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Powerinformation;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.TarrifConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerPasswordHintQuestionAnswer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.targetcost;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AgentManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DashboardManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.LocationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScenarioManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.FTPUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.thoughtworks.xstream.XStream;

@SuppressWarnings("unused")
public class DashboardServiceManager {
	// ******************************************************************** sumit end ************************************************************
	
	public String listDashboarddetails(String xml){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		DashboardManager dashboardManager = new DashboardManager();
		List<DeviceDetails> deviceDetails = dashboardManager.getdashboarddetailsOfCustomer(customer);
		String deviceDetailsXml = xStream.toXML(deviceDetails);
		//LogUtil.info(xStream.toXML(deviceDetails));
		return deviceDetailsXml;
	}
	
	public String RemoveTarriffRow(String xmlRowId){
		XStream xStream=new XStream();
		String Deviceid = (String) xStream.fromXML(xmlRowId);
		DashboardManager dashboardManager = new DashboardManager();
		String result = dashboardManager.RemoveTarriffRow(xmlRowId);
		return result;
	}
	
	public String listDashboardtargetcost(String xml){
		XStream xStream=new XStream();

		Customer customer = (Customer) xStream.fromXML(xml);
	
		DashboardManager dashboardManager = new DashboardManager();
	
		targetcost targetcost= dashboardManager.listDashboardtargetcost(customer);

		String targetcostXml = xStream.toXML(targetcost);

		return targetcostXml;
	}
	
	public String listDashboardUptodateUsage(String xml){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		DashboardManager dashboardManager = new DashboardManager();
		String uptodateusage= dashboardManager.listDashboardUptodateUsage(customer);
		String uptodateusageXml = xStream.toXML(uptodateusage);
		return uptodateusageXml;
	}
	
	
	public String listDashboardUptodatecost(String xml){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		DashboardManager dashboardManager = new DashboardManager();
		long uptodatecost= dashboardManager.listDashboardUptodatecost(customer);
		return xStream.toXML(uptodatecost);
	}
	
	
	public String listDashboardUptotimeUsage(String xml){
		XStream xStream=new XStream();
		//LogUtil.info("---upto time---");
		Customer customer = (Customer) xStream.fromXML(xml);
		DashboardManager dashboardManager = new DashboardManager();
		Object[] uptodateusage= dashboardManager.listDashboardUptotimeUsage(customer);
		//LogUtil.info("uptotimeusage---"+uptodateusage);
		String uptodateusageXml = xStream.toXML(uptodateusage);
        //LogUtil.info("uptotimeusageXml---"+uptodateusageXml);
		return uptodateusageXml;
	}
	
	
	public String listDashboardUptotimecost(String xml){
		XStream xStream=new XStream();
		//LogUtil.info("---upto time cost---");
		Customer customer = (Customer) xStream.fromXML(xml);
		DashboardManager dashboardManager = new DashboardManager();
		float uptodatecost= dashboardManager.listDashboardUptotimecost(customer);
		//LogUtil.info("uptodatecost---"+uptodatecost);
		return xStream.toXML(uptodatecost);
	}
	
	public String listDashboardwattageroom(String xml){
		XStream xStream=new XStream();
		//LogUtil.info("---upto time cost---");
		Customer customer = (Customer) xStream.fromXML(xml);
		DashboardManager dashboardManager = new DashboardManager();
		List<Object[]> rooms= dashboardManager.listDashboardwattageroom(customer);
		
		//LogUtil.info("uptodatecost---"+uptodatecost);
		return xStream.toXML(rooms);
	}
	
	public String listDashboardtotalwattage(String xml){
		XStream xStream=new XStream();
		//LogUtil.info("---upto time cost---");
		Customer customer = (Customer) xStream.fromXML(xml);
		long id=customer.getId();
		DashboardManager dashboardManager = new DashboardManager();
		String totalwat= dashboardManager.listDashboardtotalwattage(id);
		//LogUtil.info("uptodatecost---"+uptodatecost);
		return xStream.toXML(totalwat);
	}
	
	public String listDashboarddetailsforDevices(String xmlgateWay,String xmlDeviceid,String xmldays){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
		long Days=(Long) xStream.fromXML(xmldays);
		String Deviceid = (String) xStream.fromXML(xmlDeviceid);
		List<Object[]> objects = (List<Object[]>) dashboardManager.listdashboarddetailsfordevice(gateWay,Deviceid,Days);
		String xmlValue = xStream.toXML(objects);
		return xmlValue;
	}
	
	
	
	public String listDashboarddetailsforDevicesforyear(String xmlgateWay,String xmlDeviceid,String xmldays){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
		long Days=(Long) xStream.fromXML(xmldays);
		String Deviceid = (String) xStream.fromXML(xmlDeviceid);
		List<Object[]> objects = (List<Object[]>) dashboardManager.listDashboarddetailsforDevicesforyear(gateWay,Deviceid,Days);
		String xmlValue = xStream.toXML(objects);
		//LogUtil.info("xml value :"+ xmlValue);
		return xmlValue;
	}
	public String listDashboarddetailsforTotal(String xmlgateWay,String xmldays){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
		long Days=(Long) xStream.fromXML(xmldays);
		List<Object[]> objects = (List<Object[]>) dashboardManager.listDashboarddetailsforTotal(gateWay,Days);
		String xmlValue = xStream.toXML(objects);
		return xmlValue;
	}
	
	public String listDashboarddetailsforLocation(String xmlgateWay,String xmlLocationid,String xmldays){
		XStream xStream = new XStream();
		long Days=(Long) xStream.fromXML(xmldays);
		DashboardManager dashboardManager = new DashboardManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
		String LocationId = (String) xStream.fromXML(xmlLocationid);
		List<Object[]> objects = (List<Object[]>) dashboardManager.listDashboarddetailsforlocation(gateWay,LocationId,Days);
		String xmlValue = xStream.toXML(objects);
		
		return xmlValue;
	}
	
	public String saveTargetcost(String xmlSelectedMonthValues,String xmlcustomer){
		XStream xStream = new XStream();
		Customer customer = (Customer) xStream.fromXML(xmlcustomer);
		String result = "msg.controller.Dashboard.0002";
		String SelectedMonthValues= (String) xStream.fromXML(xmlSelectedMonthValues);
		DashboardManager dashboardManager = new DashboardManager();
		if(dashboardManager.saveTargetcost(SelectedMonthValues,customer)){
			result = "msg.controller.Dashboard.0001";
		}
		return result;
	}
	
	
	public String saveUnitvalue(String xmlunitvalue,String xmlcustomer){
		XStream xStream = new XStream();
		Customer customer = (Customer) xStream.fromXML(xmlcustomer);
		String result = "msg.controller.Dashboard.0004";
		targetcost Unitvalue = (targetcost) xStream.fromXML(xmlunitvalue);
		DashboardManager dashboardManager = new DashboardManager();
		if(dashboardManager.saveUnitvalue(Unitvalue,customer)){
			result = "msg.controller.Dashboard.0003";
		}
		return result;
	}
	
	public String saveTariffConfiguration(String xmltariffconfig){
	//	LogUtil.info("--saveTariffConfiguration--");
		XStream xStream = new XStream();
		String result="";
		 result = "msg.controller.Dashboard.0004";
		List <TarrifConfig> tariffConfigg = new ArrayList<TarrifConfig>();
		tariffConfigg=(List<TarrifConfig>) xStream.fromXML(xmltariffconfig);
	//	LogUtil.info("tariffConfigg---"+tariffConfigg);
		 for(TarrifConfig targetconfiguration: tariffConfigg)
		 {
		
		
		 DashboardManager dashboardManager = new DashboardManager();
		if(targetconfiguration.getId() == -1)
		{
		 dashboardManager.saveTariffConfiguration(targetconfiguration);
		 result = "msg.controller.Dashboard.0005";
		}
		else
		{
		 dashboardManager.updateTariffConfiguration(targetconfiguration);
		 result = "msg.controller.Dashboard.0006";
		}
		 }
	
		return result;
	}
	
	
	public String RetriveTariffConfiguration(String xmlCustomer)
	{
		XStream xStream = new XStream();
	    
		Customer customer = (Customer) xStream.fromXML(xmlCustomer);
		DashboardManager dashboardManager = new DashboardManager();
		
		List<TarrifConfig> Listoftariffconfig = dashboardManager.RetriveTariffConfigurationByCustomer(customer);
		return xStream.toXML(Listoftariffconfig);
		
	}
	
	public String listDashboardtargetcostandupdateUsage(String xml){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		Customer customer = (Customer) xStream.fromXML(xml);	
		targetcost targetcost= dashboardManager.listDashboardtargetcost(customer);
		String uptodateusage= dashboardManager.listDashboardUptodateUsage(customer);
		long uptodatecost= dashboardManager.listDashboardUptodatecost(customer);
		float uptodatecostfromDB= dashboardManager.listDashboardUptotimecost(customer);
		String resultxml = xStream.toXML(targetcost);
		        resultxml += xStream.toXML(uptodateusage);
		        resultxml += xStream.toXML(uptodatecost);
	            resultxml += xStream.toXML(uptodatecostfromDB);
		return resultxml;
		
	}
	
	public String getHourlyDataFromDB(String xml,String SelectedTypexmlString){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		String SelectedType = (String) xStream.fromXML(SelectedTypexmlString);
		DashboardManager dashboardManager = new DashboardManager();
		
		List<DeviceDetails> deviceDetails = dashboardManager.getdashboarddetailsOfCustomer(customer);
		
		
		for (DeviceDetails devicedetil : deviceDetails)
		{
			
			List<Powerinformation> PowerValues = null;
			if(SelectedType.equals("year"))
			{
				PowerValues=dashboardManager.listYearlyConsumptionOfdevice(devicedetil.getDid());
			}
			else
			{
				PowerValues=dashboardManager.listHourlyConsumptionOfdevice(devicedetil.getDid(),SelectedType);
			}
			
			
			devicedetil.setPowerinformation(PowerValues);
			
		}
		
				
		String deviceDetailsXml = xStream.toXML(deviceDetails);
		
		return deviceDetailsXml;
	}
	
	public String GetRoomWiseDataFromDB(String xml,String SelectedTypexmlString,String roomXml){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		String SelectedType = (String) xStream.fromXML(SelectedTypexmlString);
		String SelectedRoom = (String)xStream.fromXML(roomXml); 
		DashboardManager dashboardManager = new DashboardManager();
		
		List<DeviceDetails> deviceDetails = dashboardManager.getdashboarddetailsOfCustomerForSelectedRoom(customer,SelectedRoom);
		
		
		for (DeviceDetails devicedetil : deviceDetails)
		{
			
			List<Powerinformation> PowerValues = null;
			if(SelectedType.equals("year"))
			{
				PowerValues=dashboardManager.listYearlyConsumptionOfdevice(devicedetil.getDid());
			}
			else
			{
				PowerValues=dashboardManager.listHourlyConsumptionOfdevice(devicedetil.getDid(),SelectedType);
			}
			
			
			devicedetil.setPowerinformation(PowerValues);
			
		}
		
		
		
		String deviceDetailsXml = xStream.toXML(deviceDetails);
	//	LogUtil.info("power valuesssssss for room:"+ deviceDetailsXml);
		return deviceDetailsXml;
	}
	
	public String GetdeviceWiseDataFromDB(String xml,String SelectedTypexmlString,String deviceXml){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		String SelectedType = (String) xStream.fromXML(SelectedTypexmlString);
		String SelectedDevice = (String)xStream.fromXML(deviceXml); 
		DashboardManager dashboardManager = new DashboardManager();
		
		//List<DeviceDetails> deviceDetails = dashboardManager.getdashboarddetailsOfCustomerForSelectedRoom(customer,SelectedDevice);
		DeviceDetails devicedetil = new DeviceDetails();
		devicedetil = dashboardManager.getdashboarddetailsOfCustomerForSelectedDevice(customer, SelectedDevice);
	/*	for (DeviceDetails devicedetil : deviceDetails)
		{*/
			
			List<Powerinformation> PowerValues = null;
			/*if(SelectedType.equals("year"))
			{
				PowerValues=dashboardManager.listYearlyConsumptionOfdevice(devicedetil.getDid());
			}
			else
			{*/
				PowerValues=dashboardManager.listHourlyConsumptionOfdevice(devicedetil.getDid(),SelectedType);
			//}
			
			
			devicedetil.setPowerinformation(PowerValues);
			
		//}
		
		
		
		String deviceDetailsXml = xStream.toXML(devicedetil);
		
		return deviceDetailsXml;
	}
	
	public String GetTargetCostDetailsOfCustomer(String customerxml){
		
		String result = "yes";
		XStream stream = new XStream();
		Customer customer = (Customer)stream.fromXML(customerxml);
		DashboardManager dashboardManager = new DashboardManager();
		List<Object[]> objects = (List<Object[]>)dashboardManager.listDashboardCostDetails(customer);
		result = stream.toXML(objects);
		return result;
	}
	
	public String listDashboarddetailsforDailyTotal(String xmlgateWay){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
	
		List<Object[]> objects = (List<Object[]>) dashboardManager.listDashboarddetailsforDaily(gateWay);
		String xmlValue = xStream.toXML(objects);
		return xmlValue;
	}
	public String listDashboarddetailsforweektotal(String xmlgateWay){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
	
		List<Object[]> objects = (List<Object[]>) dashboardManager.listDashboarddetailsforweek(gateWay);
		String xmlValue = xStream.toXML(objects);
		return xmlValue;
	}
	public String listDashboarddetailsforMonthlyotal(String xmlgateWay){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
	
		List<Object[]> objects = (List<Object[]>) dashboardManager.listDashboarddetailsforMonth(gateWay);
		String xmlValue = xStream.toXML(objects);
		return xmlValue;
	}
	public String subpiechartfordaily(String xmlgateWay,String label,String name){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		LocationManager locmang= new LocationManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
		String labelname=(String) xStream.fromXML(label);
		String custname=(String) xStream.fromXML(name);
		long locid=locmang.getLocationIdByName(labelname, custname);
		//LogUtil.info(custname+""+labelname+""+locid);
		List<Object[]> objects = (List<Object[]>) dashboardManager.subpiedaily(gateWay,locid);
		String xmlValue = xStream.toXML(objects);
		return xmlValue;
	}
	public String subpiechartforweek(String xmlgateWay,String label,String name){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		LocationManager locmang= new LocationManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
		String labelname=(String) xStream.fromXML(label);
		String custname=(String) xStream.fromXML(name);
		long locid=locmang.getLocationIdByName(labelname, custname);
		List<Object[]> objects = (List<Object[]>) dashboardManager.subpieweek(gateWay,locid);
		String xmlValue = xStream.toXML(objects);
		return xmlValue;
	}
	public String subpiechartformonth(String xmlgateWay,String label,String name){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		LocationManager locmang= new LocationManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
		String labelname=(String) xStream.fromXML(label);
		String custname=(String) xStream.fromXML(name);
		long locid=locmang.getLocationIdByName(labelname, custname);
		List<Object[]> objects = (List<Object[]>) dashboardManager.subpiemonth(gateWay,locid);
		String xmlValue = xStream.toXML(objects);
		return xmlValue;
	}
	
	public String listDashboarddetailsforbarmonth(String xmlgateWay){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
		List<Object[]> objects = (List<Object[]>) dashboardManager.barchartmonthly(gateWay);
		String xmlValue = xStream.toXML(objects);
		return xmlValue;
	}
	
	public String listbardetailsforthreemonth(String xmlgateWay){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		String gateWay = (String) xStream.fromXML(xmlgateWay);
		List<Object[]> objects = (List<Object[]>) dashboardManager.barchartthreemonthly(gateWay);
		String xmlValue = xStream.toXML(objects);
		return xmlValue;
	}
	public String getLastMonthUsage(String xml){
		XStream stream = new XStream();
		DashboardManager dasboard = new DashboardManager();
		Customer customer = (Customer) stream.fromXML(xml);
		String lastMonthUsage= dasboard.listMonthUsage(customer);
		lastMonthUsage = stream.toXML(lastMonthUsage);
		return lastMonthUsage;
	}

	public String downloadenergyReport(String xml,String xml2){
		XStream stream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		String selected=(String) stream.fromXML(xml);
		long custid=(Long)stream.fromXML(xml2);
		String id=Long.toString(custid);
		//LogUtil.info(selected+" "+id);
		List<Object[]> objects=null;
		String SelectedType=null;
		if(!selected.equals("5")){
		if(selected.equals("1")){
			SelectedType="hour";
		}else if(selected.equals("2")){
			SelectedType="day";
		}else if(selected.equals("3")){
			SelectedType="month";
		}else if(selected.equals("4")){
			SelectedType="year";
		}
		objects=dashboardManager.energyconsumptiondatafromdb(id,SelectedType);
		
		}else{
			objects=dashboardManager.energyintervaldatafromdb(id);
			
		}
		String xmlValue = stream.toXML(objects);
		return xmlValue;
	}

public String GetPowerinfoOfTwoDateUsingCustomerID(String xml,String FromDatexmlString,String ToDatexmlString){
		XStream stream=new XStream();
		String customerId = (String) stream.fromXML(xml);
		CustomerManager custmang=new CustomerManager();
		DashboardManager dashboardManager = new DashboardManager();
		Customer customer=custmang.getCustomerBycustomerId(customerId);
		String FromDate=(String) stream.fromXML(FromDatexmlString);
		
		String ToDate=(String) stream.fromXML(ToDatexmlString);
		
		Timestamp FromTimeStamp = null;
		
		java.util.Date fromDate = null;
		
		Timestamp ToTimeStamp = null;
		
		java.util.Date toDate = null;
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
		try 
		{

			fromDate = formatter.parse(FromDate);
			long time=fromDate.getTime();
			FromTimeStamp=new java.sql.Timestamp(time);
			
			
			toDate = formatter.parse(ToDate);
			long totime=toDate.getTime();
			ToTimeStamp=new java.sql.Timestamp(totime);
			
		} 
		catch (ParseException e) {
				e.printStackTrace();
			}
		List<Powerinformation> PowerValues = null;
		List<DeviceDetails> deviceDetails = dashboardManager.getdashboarddetailsOfCustomer(customer);
		
		for (DeviceDetails devicedetil : deviceDetails)
		{	
			PowerValues=dashboardManager.listHourlyConsumptionOfdeviceWithTwodates(customer,FromTimeStamp,ToTimeStamp);		
		}
		String devicedetailsxml=stream.toXML(PowerValues);
		return devicedetailsxml;
	}
	
	public String GetPowerinfoOfTwoDateforDeviceWise(String xml,String devicexml,String FromDatexmlString,String ToDatexmlString,String type){
		XStream stream=new XStream();
		String customerId = (String) stream.fromXML(xml);
		CustomerManager custmang=new CustomerManager();
		DashboardManager dashboardManager = new DashboardManager();
		Customer customer=custmang.getCustomerBycustomerId(customerId);
		String FromDate=(String) stream.fromXML(FromDatexmlString);
		String SelectedDevice = (String)stream.fromXML(devicexml); 
		String ToDate=(String) stream.fromXML(ToDatexmlString);
		String selectedtype=(String) stream.fromXML(type);
		Timestamp FromTimeStamp = null;
		
		java.util.Date fromDate = null;
		
		Timestamp ToTimeStamp = null;
		
		java.util.Date toDate = null;
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
		try 
		{

			fromDate = formatter.parse(FromDate);
			long time=fromDate.getTime();
			FromTimeStamp=new java.sql.Timestamp(time);
			
			
			toDate = formatter.parse(ToDate);
			long totime=toDate.getTime();
			ToTimeStamp=new java.sql.Timestamp(totime);
			
		} 
		catch (ParseException e) {
				e.printStackTrace();
			}
		
		List<Powerinformation> PowerValues = null;
		DeviceDetails devicedetil = dashboardManager.getdashboarddetailsOfCustomerForSelectedDevice(customer, SelectedDevice);
		
			PowerValues=dashboardManager.listDeviceWiseConsumptionWithTwodates(devicedetil.getDid(),FromTimeStamp,ToTimeStamp);	
		String devicedetailsxml=stream.toXML(PowerValues);
		return devicedetailsxml;
	}
	public String GetPowerinfoOfTwoDateforRoomWise(String xml,String roomxml,String FromDatexmlString,String ToDatexmlString,String type){
		XStream stream=new XStream();
		String customerId = (String) stream.fromXML(xml);
		CustomerManager custmang=new CustomerManager();
		DashboardManager dashboardManager = new DashboardManager();
		Customer customer=custmang.getCustomerBycustomerId(customerId);
		String FromDate=(String) stream.fromXML(FromDatexmlString);
		String Room = (String)stream.fromXML(roomxml); 
		String ToDate=(String) stream.fromXML(ToDatexmlString);
		String selectedtype=(String) stream.fromXML(type);
		Timestamp FromTimeStamp = null;
		
		java.util.Date fromDate = null;
		
		Timestamp ToTimeStamp = null;
		
		java.util.Date toDate = null;
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
		try 
		{

			fromDate = formatter.parse(FromDate);
			long time=fromDate.getTime();
			FromTimeStamp=new java.sql.Timestamp(time);
			
			
			toDate = formatter.parse(ToDate);
			long totime=toDate.getTime();
			ToTimeStamp=new java.sql.Timestamp(totime);
			
		} 
		catch (ParseException e) {
				e.printStackTrace();
			}
		
		List<Powerinformation> PowerValues = null;
		List<DeviceDetails> deviceDetails= dashboardManager.getdashboarddetailsOfCustomerForSelectedRoom(customer,Room);
		
		for (DeviceDetails devicedetil : deviceDetails)
		{
			PowerValues=dashboardManager.listDeviceWiseConsumptionWithTwodates(devicedetil.getDid(),FromTimeStamp,ToTimeStamp);	
		}
		String devicedetailsxml=stream.toXML(PowerValues);
		return devicedetailsxml;
	}
	public String listtoppowerconsumeddevices(String xml){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		DashboardManager dashboardManager = new DashboardManager();
		List<Object[]> topdevices=dashboardManager.listtoppowerdevices(customer);
		return xStream.toXML(topdevices);
	}
	
	public String listenergyusageovertime(String cutomerxml, String xml){
		XStream xStream = new XStream();
		DashboardManager dashboardManager = new DashboardManager();
		long id = (Long) xStream.fromXML(cutomerxml);
		
		String period = (String) xStream.fromXML(xml);
		
		List<Object[]> objects = (List<Object[]>) dashboardManager.overtimeusage(id,period);
		String xmlValue = xStream.toXML(objects);
		
		return xmlValue;
	}
	
	public String getComparativeEnergyData(String xml){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		
		DashboardManager dashboardManager = new DashboardManager();
		
		List<DeviceDetails> deviceDetails = dashboardManager.getdashboardLocationOfCustomer(customer);
		
		
		for (DeviceDetails devicedetil : deviceDetails)
		{
			
			List<Powerinformation> PowerValues = null;
			
				PowerValues=dashboardManager.listYearlyComparativeData(devicedetil.getLocationName());
				
			
			
			
			devicedetil.setPowerinformation(PowerValues);
			
		}
		
				
		String deviceDetailsXml = xStream.toXML(deviceDetails);
		
		return deviceDetailsXml;
	}
	
}
