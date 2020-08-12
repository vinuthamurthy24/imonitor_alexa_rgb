/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScenarioCreateAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertsFromImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.BackupOfAlertsFromImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AlertsFromImvgManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ImvgAlertsActionsManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScenarioManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;


import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

import imonitor.cms.alexa.proactive.service.AlexaProactiveUpdateScenarioService;

/**
 * @author computer
 *
 */
public class AlertServiceManager {
	
	@SuppressWarnings("unchecked")
	public String listImvgAlert(String xml1,String xml2,String xml3){
		XStream xStream = new XStream();
		DataTable dataTable = new DataTable();
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		Customer customer = (Customer) xStream.fromXML(xml1);
		String startString = (String) xStream.fromXML(xml2);
		int start = Integer.parseInt(startString);
		String lengthString = (String) xStream.fromXML(xml3);
		int  length = Integer.parseInt(lengthString);
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		
		List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsImvgType(start, length ,gateWay.getId());
	    Date currentTime = new Date();
	    for (Object[] row : objects)
	    {
	    	 long diff = (currentTime.getTime() - ((Date)row[0]).getTime())/(1000*60);
	    	 row[8] = diff;	 
	    }
	    
		dataTable.setResults(objects);
		int count =Integer.parseInt(imvgAlertsActionsManager.getCountOfImvgAlerts(gateWay.getId()).toString());
		dataTable.setTotalRows(count);
		String xmlValue = xStream.toXML(dataTable);	
		//LogUtil.info("alarms list :"+xStream.toXML(dataTable));
		return xmlValue;
	}
	@SuppressWarnings("unchecked")
	public String listImvgAlertForAlarm(String xml1,String xml2,String xml3){
		XStream xStream = new XStream();
		DataTable dataTable = new DataTable();
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		Customer customer = (Customer) xStream.fromXML(xml1);
		
		String startString = (String) xStream.fromXML(xml2);
		int start = Integer.parseInt(startString);
		String lengthString = (String) xStream.fromXML(xml3);
		int  length = Integer.parseInt(lengthString);
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlerts(start, length ,gateWay.getId());
	
		dataTable.setResults(objects);
		int count =Integer.parseInt(imvgAlertsActionsManager.getCountAlerts(gateWay.getId()).toString());
		dataTable.setTotalRows(count);
		String xmlValue = xStream.toXML(dataTable);
		return xmlValue;
	}
	@SuppressWarnings("unchecked")
	public String listImvgAlertForFiltering(String xml1,String DisplayStart,String xml2,String xml3,String xml4,String DataTableSent){
		
		XStream xStream = new XStream();
		DataTable dataTable = (DataTable) xStream.fromXML(DataTableSent);
		
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		
		Customer customer = (Customer) xStream.fromXML(xml1);
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		Long SelectedDevice=(Long)xStream.fromXML(xml2);
		String XmlFromdate=(String) xStream.fromXML(xml3);
		String XmlTodate=(String) xStream.fromXML(xml4);
		String start=(String)xStream.fromXML(DisplayStart);
		int Displaystart=Integer.parseInt(start);
		int length=10;
		
		Timestamp from = null;
		Timestamp to = null;
		java.util.Date Fromdate = null;
		java.util.Date Todate = null;
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty())
		{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
		try 
		{
			Fromdate = formatter.parse(XmlFromdate);
			long time=Fromdate.getTime();
			from=new java.sql.Timestamp(time);
			
			Todate = formatter.parse(XmlTodate);
			time=Todate.getTime();
			to=new java.sql.Timestamp(time);
			
		} 
		catch (ParseException e) {
				e.printStackTrace();
			}  		
		}		
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty()&& SelectedDevice==1)
		{	
			List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlerts(Displaystart, length ,gateWay.getId());
			dataTable.setResults(objects);
			int count =Integer.parseInt(imvgAlertsActionsManager.getCountAlerts(gateWay.getId()).toString());
			dataTable.setTotalRows(count);
		}
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty() && SelectedDevice!=1)
		{
			List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsPerDeviceWithoutdate(SelectedDevice,Displaystart,gateWay.getId());
			dataTable.setResults(objects);
			objects=imvgAlertsActionsManager.getCountOfAlertsPerDeviceWithoutdate(gateWay.getId(),SelectedDevice);
			dataTable.setTotalRows(objects.size());
			dataTable.setTotalDispalyRows(10);
		}
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice==1))
		{	
			List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsPerDate(Displaystart,from,to,gateWay.getId());
			dataTable.setResults(objects);
			objects=imvgAlertsActionsManager.getCountOfAlertsPerDate(gateWay.getId(),from,to);
			dataTable.setTotalRows(objects.size());
			dataTable.setTotalDispalyRows(10);
		}
		
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice!=1))
		{	
			List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsPerDeviceAndDate(gateWay.getId(),SelectedDevice,from,to,Displaystart);
			dataTable.setResults(objects);
			objects=imvgAlertsActionsManager.getCountOfAlertsPerDeviceAndDate(gateWay.getId(),SelectedDevice,from,to);
			dataTable.setTotalRows(objects.size());
			dataTable.setTotalDispalyRows(10);
		}
		String xmlValue = xStream.toXML(dataTable);
		
		return xmlValue;
	}
	public String getImvgAlertAttachment(String xml){
		XStream xStream = new XStream();
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		String alertId = (String) xStream.fromXML(xml);
		AlertsFromImvg alertsFromImvg = (AlertsFromImvg) new DaoManager().get(Long.parseLong(alertId),AlertsFromImvg.class);
		List<Object[]> objects =  imvgAlertsActionsManager.getUploadedImage(alertsFromImvg.getId(),alertsFromImvg.getDevice().getId());
		return xStream.toXML(objects);
	}
	
	public String getUploadedImvgAlertStreamAttachment(String xml){
		XStream xStream = new XStream();
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		String alertId = (String) xStream.fromXML(xml);
		AlertsFromImvg alertsFromImvg = (AlertsFromImvg) new DaoManager().get(Long.parseLong(alertId),AlertsFromImvg.class);
		List<Object[]> objects =  imvgAlertsActionsManager.getUploadedStream(alertsFromImvg.getId(),alertsFromImvg.getDevice().getId());
		return xStream.toXML(objects);
	}
	
	public String GetUserChoosenlistOfImvgAlert(String xml){
		XStream xStream = new XStream();
		DataTable dataTable = new DataTable();
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		Customer customer = (Customer) xStream.fromXML(xml);
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		List<Object[]> Alertobjects=(List<Object[]>)imvgAlertsActionsManager.GetUserChoosenAlertsByGateWayId(gateWay.getId());
		dataTable.setResults(Alertobjects);
		String xmlValue = xStream.toXML(dataTable);
		return xmlValue;
	}
	
	public String listImvgAlertForAlarm(String xml1,String xml2,String xml3,String Datatable){
		XStream xStream = new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(Datatable);
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		Customer customer = (Customer) xStream.fromXML(xml1);
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		String sSearch = dataTable.getsSearch();
		
		String deviceToSearch=dataTable.getsSearch_2();
		String SentDate=dataTable.getsSearch_1();
		String[] SpliteDate = null;
		String XmlFromdate = null;
		String XmlTodate = null;
		
		
		SpliteDate=SentDate.split(":");
		if(SpliteDate.length>1)
		{
			XmlFromdate=SpliteDate[0];
			XmlTodate=SpliteDate[1];
		}
		
		
		Timestamp from = null;
		Timestamp to = null;
		java.util.Date Fromdate = null;
		java.util.Date Todate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
		if( XmlFromdate != null)
		{
			try {
				Fromdate = formatter.parse(XmlFromdate);
				long time=Fromdate.getTime();
				from=new java.sql.Timestamp(time);
			} catch (ParseException e) {
				
			}
		}
		if(XmlTodate != null)
		{
			try {
				Todate = formatter.parse(XmlTodate);
				long time=Todate.getTime();
				to=new java.sql.Timestamp(time);
				to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
			} catch (ParseException e) {
				// TODO: handle exception
			}
		}
		
		
		
		String[] cols = {"a.alertTime","d.friendlyName",};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		String colName = cols[col];
		sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		  
		List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsImvgTypeForAlarm(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),gateWay.getId(),deviceToSearch,from,to);
	    		
		
		dataTable.setResults(objects);
		int count =Integer.parseInt(imvgAlertsActionsManager.getCountOfImvgAlerts(gateWay.getId(),deviceToSearch,from,to).toString());
		dataTable.setTotalRows(count);
		dataTable.setTotalDispalyRows(count);
		String xmlValue = xStream.toXML(dataTable);
		return xmlValue;
	}
	
	
	@SuppressWarnings({ })
	public String listImvgAlertForAlarmPerDevice(String xml1,String xml2,String xml3,String DisplayStart){
	
		XStream xStream = new XStream();
		DataTable dataTable = new DataTable();
		
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		
		Long SelectedDevice=(Long)xStream.fromXML(xml1);
		String XmlFromdate=(String) xStream.fromXML(xml2);
		String XmlTodate=(String) xStream.fromXML(xml3);
		
		Timestamp from = null;
		Timestamp to = null;
		java.util.Date Fromdate = null;
		java.util.Date Todate = null;
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty())
		{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
		try 
		{
			Fromdate = formatter.parse(XmlFromdate);
			long time=Fromdate.getTime();
			from=new java.sql.Timestamp(time);
			
			Todate = formatter.parse(XmlTodate);
			time=Todate.getTime();
			to=new java.sql.Timestamp(time);
			
		} 
		catch (ParseException e) {
				e.printStackTrace();
			}  		
		}
		String start=(String)xStream.fromXML(DisplayStart);
		int Displaystart=Integer.parseInt(start);
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty()&& SelectedDevice!=1)
		{
		List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDevice(SelectedDevice,Displaystart,from,to);
		dataTable.setResults(objects);
		//int count =Integer.parseInt((imvgAlertsActionsManager.getCountOflistAskedAlarmPerDevice(SelectedDevice,from,to)).toString());
		objects=imvgAlertsActionsManager.getCountOflistAskedAlarmPerDevice(SelectedDevice,from,to);
		dataTable.setTotalRows(objects.size());
		dataTable.setTotalDispalyRows(10);
		}
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty() && SelectedDevice!=1)
		{
			List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDeviceWithoutdate(SelectedDevice,Displaystart);
			dataTable.setResults(objects);
			//int count =Integer.parseInt((imvgAlertsActionsManager.getCountOflistAskedAlarmPerDevice(SelectedDevice,from,to)).toString());
			objects=imvgAlertsActionsManager.getCountOfAlarmPerDeviceWithoutdate(SelectedDevice);
			dataTable.setTotalRows(objects.size());
			dataTable.setTotalDispalyRows(10);
		}
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice==1))
		{	
			List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDate(Displaystart,from,to);
			dataTable.setResults(objects);
			//int count =Integer.parseInt((imvgAlertsActionsManager.getCountOflistAskedAlarmPerDevice(SelectedDevice,from,to)).toString());
			objects=imvgAlertsActionsManager.getCountOfAlarmPerDate(from,to);
			dataTable.setTotalRows(objects.size());
			dataTable.setTotalDispalyRows(10);
		}
		String xmlValue = xStream.toXML(dataTable);		
		
		return xmlValue;
	}
	//bhavya start
	public String listImvgAlertForAlarmPerDeviceandalertType(String xml1,String xml2){
	
		XStream xStream = new XStream();
		DataTable dataTable = new DataTable();
		
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		
		Long SelectedDevice=(Long)xStream.fromXML(xml1);
		String alertType=(String) xStream.fromXML(xml2);
		
		
		
		
		List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.getCountOflistAskedAlarmPerDeviceandalertType(SelectedDevice,alertType);
		
		dataTable.setResults(objects);
		//int count =Integer.parseInt((imvgAlertsActionsManager.getCountOflistAskedAlarmPerDevice(SelectedDevice,from,to)).toString());
		objects=imvgAlertsActionsManager.getCountOflistAskedAlarmPerDeviceandalertType(SelectedDevice,alertType);
		dataTable.setTotalRows(objects.size());
		dataTable.setTotalDispalyRows(10);
		
		
			
		
			
			dataTable.setResults(objects);
			
			
		String xmlValue = xStream.toXML(dataTable);		
		
		return xmlValue;
	}
	//bhavya end
	
	public String DeleteSelectedAlarms(String xml){
		XStream xStream = new XStream();
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		String SelectedAlarams=(String) xStream.fromXML(xml);
		String Temp[]=SelectedAlarams.split(",");
		for(int i=0;i<Temp.length;i++)
		{
			
			imvgAlertsActionsManager.DeleteSelectedAlerts(Temp[i]);
		}
		return xml;
	}
	
	@SuppressWarnings("unchecked")
	public String requestForPdf(String Customer,String selectedDevice,String fromDate,String todate,String SelectedType,String DisplayStart,String DisplayLength){
		
		XStream xStream = new XStream();
		List<Object[]> objects=null;
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		Customer customer = (Customer) xStream.fromXML(Customer);
		String selectedType = (String) xStream.fromXML(SelectedType);
		Long SelectedDevice=(Long)xStream.fromXML(selectedDevice);
		String XmlFromdate=(String) xStream.fromXML(fromDate);
		String XmlTodate=(String) xStream.fromXML(todate);
		
		
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		Timestamp from = null;
		Timestamp to = null;
		java.util.Date Fromdate = null;
		java.util.Date Todate = null;
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty())
		{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
		try 
		{
			Fromdate = formatter.parse(XmlFromdate);
			long time=Fromdate.getTime();
			from=new java.sql.Timestamp(time);
			
			Todate = formatter.parse(XmlTodate);
			time=Todate.getTime();
			to=new java.sql.Timestamp(time);
			
		} 
		catch (ParseException e) {
				e.printStackTrace();
			}  		
		}
		if(selectedType.equals("Alarm"))
		{
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty()&& SelectedDevice==0)
		{
			 objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDevice(gateWay.getId());
		}
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty() && SelectedDevice!=0)
		{
			
			objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDeviceWithoutdate(SelectedDevice);
		}
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice==0))
		{	
			
			objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDate(from,to);
		}
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice!=0))
		{	
			
		 objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDevice(SelectedDevice,from,to);
		}
		}
		else
		{
			if(XmlFromdate.isEmpty() && XmlTodate.isEmpty()&& SelectedDevice==0)
			{
				objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlerts(gateWay.getId());
			}
			if(XmlFromdate.isEmpty() && XmlTodate.isEmpty() && SelectedDevice!=0)
			{
				
				objects=imvgAlertsActionsManager.getCountOfAlertsPerDeviceWithoutdate(gateWay.getId(),SelectedDevice);
			}
			if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice==0))
			{	
				objects=imvgAlertsActionsManager.getCountOfAlertsPerDate(gateWay.getId(),from,to);
				
			}
			if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice!=0))
			{	
				
				objects=imvgAlertsActionsManager.getCountOfAlertsPerDeviceAndDate(gateWay.getId(),SelectedDevice,from,to);
			}
			
		}
		
		String xmlValue = xStream.toXML(objects);		
		
		return xmlValue;
	}
	
	@SuppressWarnings("unchecked")
	public String listImvgAlertForPerdeviceAndDate(String xml1,String DisplayStart,String xml2,String xml3,String xml4){
		
		XStream xStream = new XStream();
		DataTable dataTable = new DataTable();
		
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		
		Customer customer = (Customer) xStream.fromXML(xml1);
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		Long SelectedDevice=(Long)xStream.fromXML(xml2);
		String XmlFromdate=(String) xStream.fromXML(xml3);
		String XmlTodate=(String) xStream.fromXML(xml4);
		String start=(String)xStream.fromXML(DisplayStart);
		int Displaystart=Integer.parseInt(start);
		int length=10;
		
		
		
		Timestamp from = null;
		Timestamp to = null;
		java.util.Date Fromdate = null;
		java.util.Date Todate = null;
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty())
		{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
		try 
		{
			Fromdate = formatter.parse(XmlFromdate);
			long time=Fromdate.getTime();
			from=new java.sql.Timestamp(time);
			
			Todate = formatter.parse(XmlTodate);
			time=Todate.getTime();
			to=new java.sql.Timestamp(time);
			
		} 
		catch (ParseException e) {
				e.printStackTrace();
			}  		
		}		
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty()&& SelectedDevice==1)
		{	
			List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlerts(Displaystart, length ,gateWay.getId());
			dataTable.setResults(objects);
			int count =Integer.parseInt(imvgAlertsActionsManager.getCountAlerts(gateWay.getId()).toString());
			dataTable.setTotalRows(count);
		}
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty() && SelectedDevice!=1)
		{
			List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsPerDeviceWithoutdate(SelectedDevice,Displaystart,gateWay.getId());
			dataTable.setResults(objects);
			objects=imvgAlertsActionsManager.getCountOfAlertsPerDeviceWithoutdate(gateWay.getId(),SelectedDevice);
			dataTable.setTotalRows(objects.size());
			dataTable.setTotalDispalyRows(10);
		}
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice==1))
		{	
			List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsPerDate(Displaystart,from,to,gateWay.getId());
			dataTable.setResults(objects);
			objects=imvgAlertsActionsManager.getCountOfAlertsPerDate(gateWay.getId(),from,to);
			dataTable.setTotalRows(objects.size());
			dataTable.setTotalDispalyRows(10);
		}
		
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice!=1))
		{	
			List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsPerDeviceAndDate(gateWay.getId(),SelectedDevice,from,to,Displaystart);
			dataTable.setResults(objects);
			objects=imvgAlertsActionsManager.getCountOfAlertsPerDeviceAndDate(gateWay.getId(),SelectedDevice,from,to);
			dataTable.setTotalRows(objects.size());
			dataTable.setTotalDispalyRows(10);
		}
		String xmlValue = xStream.toXML(dataTable);		
		
		return xmlValue;
	}
	
	public List<Object[]> listAlarmsForMid(Customer customer, Timestamp timest){
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		
		List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlamPerGateWay(gateWay.getId(),timest);
	    Date currentTime = new Date();
	    for (Object[] row : objects)
	    {
	    	 long diff = (currentTime.getTime() - ((Date)row[0]).getTime())/(1000*60);
	    	 row[3] = diff;	 
	    }
		return objects;
	}
	
	
	public String listImvgAlarm(String Customer,String Datatable){
		XStream xStream = new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(Datatable);
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		Customer customer = (Customer) xStream.fromXML(Customer);
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		
		
		String deviceToSearch=dataTable.getsSearch_2();
		String SentDate=dataTable.getsSearch_1();
		String[] SpliteDate = null;
		String XmlFromdate = null;
		String XmlTodate = null;
		
		
		SpliteDate=SentDate.split(":");
		if(SpliteDate.length>1)
		{
			XmlFromdate=SpliteDate[0];
			XmlTodate=SpliteDate[1];
		}
		
		
		Timestamp from = null;
		Timestamp to = null;
		java.util.Date Fromdate = null;
		java.util.Date Todate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
		if( XmlFromdate != null)
		{
			try {
				Fromdate = formatter.parse(XmlFromdate);
				long time=Fromdate.getTime();
				from=new java.sql.Timestamp(time);
			} catch (ParseException e) {
				
			}
		}
		if(XmlTodate != null)
		{
			try {
				Todate = formatter.parse(XmlTodate);
				long time=Todate.getTime();
				to=new java.sql.Timestamp(time);
				to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
			} catch (ParseException e) {
				// TODO: handle exception
			}
		}
		
		
		
		//String[] cols = {"a.alertTime","d.friendlyName",};
	//	String sOrder = "";
		//int col = Integer.parseInt(dataTable.getiSortCol_0());
		//String colName = cols[col];
	//	sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		  
		List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarm(dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),gateWay.getId(),deviceToSearch,from,to);
		dataTable.setResults(objects);
		int count =Integer.parseInt(imvgAlertsActionsManager.getCountOfImvgAlarms(gateWay.getId(),deviceToSearch,from,to).toString());
		dataTable.setTotalRows(count);
		dataTable.setTotalDispalyRows(count);
		String xmlValue = xStream.toXML(dataTable);		
		return xmlValue;
	}
	
	public String updateAlert(String alertIdXml){
		LogUtil.info("updateAlert"+alertIdXml);
		XStream  stream = new XStream();
		String alertId = (String) stream.fromXML(alertIdXml);
		
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		imvgAlertsActionsManager.updateStatusoftheAlert(alertId);
		return "SUCCESS";
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listAlertsForMid(Customer customer, Timestamp timest){
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		List<Object[]> objects=null;
		objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsforMid(gateWay.getId());
	   
		return objects;
	}
	
	
	public String listImvgOldAlertForAlarm(String xml1,String xml2,String xml3,String Datatable){
		XStream xStream = new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(Datatable);
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		Customer customer = (Customer) xStream.fromXML(xml1);
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		String sSearch = dataTable.getsSearch();
		
		String deviceToSearch=dataTable.getsSearch_2();
		String SentDate=dataTable.getsSearch_1();
		String[] SpliteDate = null;
		String XmlFromdate = null;
		String XmlTodate = null;
		
		
		SpliteDate=SentDate.split(":");
		if(SpliteDate.length>1)
		{
			XmlFromdate=SpliteDate[0];
			XmlTodate=SpliteDate[1];
		}
		
		
		Timestamp from = null;
		Timestamp to = null;
		java.util.Date Fromdate = null;
		java.util.Date Todate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
		if( XmlFromdate != null)
		{
			try {
				Fromdate = formatter.parse(XmlFromdate);
				long time=Fromdate.getTime();
				from=new java.sql.Timestamp(time);
			} catch (ParseException e) {
				
			}
		}
		if(XmlTodate != null)
		{
			try {
				Todate = formatter.parse(XmlTodate);
				long time=Todate.getTime();
				to=new java.sql.Timestamp(time);
				to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
			} catch (ParseException e) {
				// TODO: handle exception
			}
		}
		
		
		
		String[] cols = {"a.alertTime","d.friendlyName",};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		String colName = cols[col];
		sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		  
		List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedOldAlertsImvgTypeForAlarm(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),gateWay.getId(),deviceToSearch,from,to);
		
		
		
		dataTable.setResults(objects);
		int count =Integer.parseInt(imvgAlertsActionsManager.getCountOfImvgBackupAlerts(gateWay.getId(),deviceToSearch,from,to).toString());
		//LogUtil.info("Data table no of rows: "+ count);
		dataTable.setTotalRows(count);
		dataTable.setTotalDispalyRows(count);
		String xmlValue = xStream.toXML(dataTable);
		return xmlValue;
	}
	
	public String listImvgOldAlarm(String Customer,String Datatable){
		XStream xStream = new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(Datatable);
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		Customer customer = (Customer) xStream.fromXML(Customer);
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		//String sSearch = dataTable.getsSearch();
		
		String deviceToSearch=dataTable.getsSearch_2();
		String SentDate=dataTable.getsSearch_1();
		String[] SpliteDate = null;
		String XmlFromdate = null;
		String XmlTodate = null;
		
		
		SpliteDate=SentDate.split(":");
		if(SpliteDate.length>1)
		{
			XmlFromdate=SpliteDate[0];
			XmlTodate=SpliteDate[1];
		}
		
		
		Timestamp from = null;
		Timestamp to = null;
		java.util.Date Fromdate = null;
		java.util.Date Todate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
		if( XmlFromdate != null)
		{
			try {
				Fromdate = formatter.parse(XmlFromdate);
				long time=Fromdate.getTime();
				from=new java.sql.Timestamp(time);
			} catch (ParseException e) {
				
			}
		}
		if(XmlTodate != null)
		{
			try {
				Todate = formatter.parse(XmlTodate);
				long time=Todate.getTime();
				to=new java.sql.Timestamp(time);
				to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
			} catch (ParseException e) {
				// TODO: handle exception
			}
		}
		
		
		
	//	String[] cols = {"a.alertTime","d.friendlyName",};
	//	String sOrder = "";
	//	int col = Integer.parseInt(dataTable.getiSortCol_0());
	//	String colName = cols[col];
	//	sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		  
		List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmFromBackup(dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),gateWay.getId(),deviceToSearch,from,to);
		dataTable.setResults(objects);
		int count =Integer.parseInt(imvgAlertsActionsManager.getCountOfImvgAlarms(gateWay.getId(),deviceToSearch,from,to).toString());
		dataTable.setTotalRows(count);
		dataTable.setTotalDispalyRows(count);
		String xmlValue = xStream.toXML(dataTable);		
		return xmlValue;
	}
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public String requestForOldPdf(String Customer,String selectedDevice,String fromDate,String todate,String SelectedType,String DisplayStart,String DisplayLength){
		
		XStream xStream = new XStream();
		List<Object[]> objects=null;
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		Customer customer = (Customer) xStream.fromXML(Customer);
		String selectedType = (String) xStream.fromXML(SelectedType);
		Long SelectedDevice=(Long)xStream.fromXML(selectedDevice);
		String XmlFromdate=(String) xStream.fromXML(fromDate);
		String XmlTodate=(String) xStream.fromXML(todate);
		
		
		GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
		Timestamp from = null;
		Timestamp to = null;
		java.util.Date Fromdate = null;
		java.util.Date Todate = null;
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty())
		{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
		try 
		{
			Fromdate = formatter.parse(XmlFromdate);
			long time=Fromdate.getTime();
			from=new java.sql.Timestamp(time);
			
			Todate = formatter.parse(XmlTodate);
			time=Todate.getTime();
			to=new java.sql.Timestamp(time);
			
		} 
		catch (ParseException e) {
				e.printStackTrace();
			}  		
		}
		if(selectedType.equals("Alarm"))
		{
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty()&& SelectedDevice==0)
		{
			 objects = (List<Object[]>) imvgAlertsActionsManager.listAskedOldAlarmPerDevice(gateWay.getId());
		}
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty() && SelectedDevice!=0)
		{
			
			objects = (List<Object[]>) imvgAlertsActionsManager.listAskedOldAlarmPerDeviceWithoutdate(SelectedDevice);
		}
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice==0))
		{	
			
			objects = (List<Object[]>) imvgAlertsActionsManager.listAskedOldAlarmPerDate(from,to);
		}
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice!=0))
		{	
			
		 objects = (List<Object[]>) imvgAlertsActionsManager.listAskedOldAlarmPerDevice(SelectedDevice,from,to);
		}
		}
		else
		{
			if(XmlFromdate.isEmpty() && XmlTodate.isEmpty()&& SelectedDevice==0)
			{
				objects = (List<Object[]>) imvgAlertsActionsManager.listAskedOldAlerts(gateWay.getId());
			}
			if(XmlFromdate.isEmpty() && XmlTodate.isEmpty() && SelectedDevice!=0)
			{
				
				objects=imvgAlertsActionsManager.getCountOfOldAlertsPerDeviceWithoutdate(gateWay.getId(),SelectedDevice);
			}
			if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice==0))
			{	
				objects=imvgAlertsActionsManager.getCountOfOldAlertsPerDate(gateWay.getId(),from,to);
				
			}
			if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice!=0))
			{	
				
				objects=imvgAlertsActionsManager.getCountOfOldAlertsPerDeviceAndDate(gateWay.getId(),SelectedDevice,from,to);
			}
			
		}
		
		String xmlValue = xStream.toXML(objects);		
		
		return xmlValue;
	}
	
	
	public String DeleteSelectedOldAlarms(String xml){
		XStream xStream = new XStream();
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		String SelectedAlarams=(String) xStream.fromXML(xml);
		String Temp[]=SelectedAlarams.split(",");
		for(int i=0;i<Temp.length;i++)
		{
			
			imvgAlertsActionsManager.DeleteSelectedOldAlerts(Temp[i]);
		}
		return xml;
	}
	
	public String getImvgAlertAttachmentforbackup(String xml){
		XStream xStream = new XStream();
		ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
		String alertId = (String) xStream.fromXML(xml);
		BackupOfAlertsFromImvg alertsFromImvg = (BackupOfAlertsFromImvg) new DaoManager().get(Long.parseLong(alertId),BackupOfAlertsFromImvg.class);
		Object objects =  imvgAlertsActionsManager.getUploadedImagefrombackup(alertsFromImvg.getId(),alertsFromImvg.getDevice().getId());
		return xStream.toXML(objects);
	}
	
	@SuppressWarnings("static-access")
	public String getRoutingInfo(String xml, String customerXml){
		XStream xStream = new XStream();
		List<Object[]> objects = new ArrayList<Object[]>();
		Customer customer = (Customer) xStream.fromXML(customerXml);
		DeviceManager deviceManager = new DeviceManager();
		String alertId = (String) xStream.fromXML(xml);
	    AlertsFromImvg alertsFromImvg = (AlertsFromImvg) new DaoManager().get(Long.parseLong(alertId),AlertsFromImvg.class);
		String List = alertsFromImvg.getAlertValue();
		//LogUtil.info("Node List: "+ List);
		if(List.equals(Constants.NO_NEIGHBOURS)){
			Object[] object = {"No Neighbours", "--"};
			objects.add(object);
		}else{
		String[] Nodes = List.split(",");
		for(String Id : Nodes){
			
			if(Id.equals("1")){
				Object[] object = {"Master Controller", "Default Room"};
				objects.add(object);
			
			
			}else{
				Object[] device = deviceManager.getFriendlyNameByDeviceId(Id,customer);
				objects.add(device);
			}
			
		}
		}
	  
		return xStream.toXML(objects);
	}
	

public String getalertsapi(String id){
	XStream stream = new XStream();
	String customerId=id;
	CustomerManager customer=new CustomerManager();
	Customer customerfromdb=customer.getCustomerByCustomerId(customerId);
	//LogUtil.info(stream.toXML(customerfromdb)+"customer");
	AlertServiceManager alertserviceManger=new AlertServiceManager(); 
	
List<Object[]> Objects = alertserviceManger.listAlertsForApi(customerfromdb);

LogUtil.info(stream.toXML(Objects));
String result =stream.toXML(Objects);

return stream.toXML(Objects);	
}

public List<Object[]> listAlertsForApi(Customer customer){
	AlertsFromImvgManager alertsmanager = new AlertsFromImvgManager();
	GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
	
	List<Object[]> objects = (List<Object[]>) alertsmanager.listalertsforapi(gateWay.getId());
    
	return objects;
}

public String getalarmsapi(String id){
	XStream stream = new XStream();
	String customerId=id;
	CustomerManager customer=new CustomerManager();
	Customer customerfromdb=customer.getCustomerByCustomerId(customerId);
	//LogUtil.info(stream.toXML(customerfromdb)+"customer");
	AlertServiceManager alertserviceManger=new AlertServiceManager(); 
	

List<Object[]> Objects = alertserviceManger.listAlarmsForApi(customerfromdb);
//LogUtil.info(stream.toXML(Objects));
return stream.toXML(Objects);	
}

public List<Object[]> listAlarmsForApi(Customer customer){
	AlertsFromImvgManager alertsmanager = new AlertsFromImvgManager();
	GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
	
	List<Object[]> objects = (List<Object[]>) alertsmanager.listalarmsforapi(gateWay.getId());
    
	return objects;
}

//3gp by apoorva
public String listImvgAlertForAlarmofMultipleGateway(String xml1,String xml2,String xml3,String Datatable){
	XStream xStream = new XStream();
	DataTable dataTable =  (DataTable) xStream.fromXML(Datatable);
	ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
	Customer customer = (Customer) xStream.fromXML(xml1);
	GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
	String sSearch = dataTable.getsSearch();
	
	String deviceToSearch=dataTable.getsSearch_2();
	String SentDate=dataTable.getsSearch_1();
	String[] SpliteDate = null;
	String XmlFromdate = null;
	String XmlTodate = null;
	
	
	SpliteDate=SentDate.split(":");
	if(SpliteDate.length>1)
	{
		XmlFromdate=SpliteDate[0];
		XmlTodate=SpliteDate[1];
	}
	
	
	Timestamp from = null;
	Timestamp to = null;
	java.util.Date Fromdate = null;
	java.util.Date Todate = null;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
	if( XmlFromdate != null)
	{
		try {
			Fromdate = formatter.parse(XmlFromdate);
			long time=Fromdate.getTime();
			from=new java.sql.Timestamp(time);
		} catch (ParseException e) {
			
		}
	}
	if(XmlTodate != null)
	{
		try {
			Todate = formatter.parse(XmlTodate);
			long time=Todate.getTime();
			to=new java.sql.Timestamp(time);
			to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
		} catch (ParseException e) {
			// TODO: handle exception
		}
	}
	
	
	
	String[] cols = {"a.alertTime","d.friendlyName",};
	String sOrder = "";
	int col = Integer.parseInt(dataTable.getiSortCol_0());
	String colName = cols[col];
	sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
	  
	List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsImvgTypeForAlarmOfMultipleGateway(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),customer.getId(),deviceToSearch,from,to);
    		
	
	dataTable.setResults(objects);
	int count =Integer.parseInt(imvgAlertsActionsManager.getCountOfImvgAlerts(gateWay.getId(),deviceToSearch,from,to).toString());
	dataTable.setTotalRows(count);
	dataTable.setTotalDispalyRows(count);
	String xmlValue = xStream.toXML(dataTable);
	return xmlValue;
}

//3gp
@SuppressWarnings("unchecked")
public String requestForPdfForMultipleGateways(String Customer,String selectedDevice,String fromDate,String todate,String SelectedType,String DisplayStart,String DisplayLength){
	XStream xStream = new XStream();
	List<Object[]> objects=null;
	ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
	Customer customer = (Customer) xStream.fromXML(Customer);
	String selectedType = (String) xStream.fromXML(SelectedType);
	Long SelectedDevice=(Long)xStream.fromXML(selectedDevice);
	String XmlFromdate=(String) xStream.fromXML(fromDate);
	String XmlTodate=(String) xStream.fromXML(todate);
	
	
	GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
	Timestamp from = null;
	Timestamp to = null;
	java.util.Date Fromdate = null;
	java.util.Date Todate = null;
	if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty())
	{
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
	try 
	{
		Fromdate = formatter.parse(XmlFromdate);
		long time=Fromdate.getTime();
		from=new java.sql.Timestamp(time);
		
		Todate = formatter.parse(XmlTodate);
		time=Todate.getTime();
		to=new java.sql.Timestamp(time);
		
	} 
	catch (ParseException e) {
			e.printStackTrace();
		}  		
	}
	
	
	if(selectedType.equals("Alarm"))
	{
	if(XmlFromdate.isEmpty() && XmlTodate.isEmpty()&& SelectedDevice==0)
	{
		 objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDeviceForMultipleGateways(customer.getId());
	}
	if(XmlFromdate.isEmpty() && XmlTodate.isEmpty() && SelectedDevice!=0)
	{
		
		objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDeviceWithoutdate(SelectedDevice);
	}
	if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice==0))
	{	
		
		objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDate(from,to);
	}
	if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice!=0))
	{	
		
	 objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmPerDevice(SelectedDevice,from,to);
	}
	}
	else
	{
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty()&& SelectedDevice==0)
		{
			
			objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlertsForMultipleGateway(customer.getId());
		}
		if(XmlFromdate.isEmpty() && XmlTodate.isEmpty() && SelectedDevice!=0)
		{
			
			objects=imvgAlertsActionsManager.getCountOfAlertsPerDeviceWithoutdateForMultipleGateway(customer.getId(),SelectedDevice);
		}
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice==0))
		{	
			
			objects=imvgAlertsActionsManager.getCountOfAlertsPerDateFormultipleGateway(customer.getId(),from,to);
			
		}
		if(!XmlFromdate.isEmpty() && !XmlTodate.isEmpty() && (SelectedDevice!=0))
		{	
			
			objects=imvgAlertsActionsManager.getCountOfAlertsPerDeviceAndDateForMultipleGateway(customer.getId(),SelectedDevice,from,to);
		}
		
	}
	
	String xmlValue = xStream.toXML(objects);		
	return xmlValue;
}

public String listAlarmsForMultipleGateway(String Customer,String Datatable){
	XStream xStream = new XStream();
	DataTable dataTable =  (DataTable) xStream.fromXML(Datatable);
	ImvgAlertsActionsManager imvgAlertsActionsManager = new ImvgAlertsActionsManager();
	Customer customer = (Customer) xStream.fromXML(Customer);
	GateWay gateWay =new GatewayManager().getGateWayByCustomer(customer);
	
	
	String deviceToSearch=dataTable.getsSearch_2();
	String SentDate=dataTable.getsSearch_1();
	String[] SpliteDate = null;
	String XmlFromdate = null;
	String XmlTodate = null;
	
	
	SpliteDate=SentDate.split(":");
	if(SpliteDate.length>1)
	{
		XmlFromdate=SpliteDate[0];
		XmlTodate=SpliteDate[1];
	}
	
	
	Timestamp from = null;
	Timestamp to = null;
	java.util.Date Fromdate = null;
	java.util.Date Todate = null;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
	if( XmlFromdate != null)
	{
		try {
			Fromdate = formatter.parse(XmlFromdate);
			long time=Fromdate.getTime();
			from=new java.sql.Timestamp(time);
		} catch (ParseException e) {
			
		}
	}
	if(XmlTodate != null)
	{
		try {
			Todate = formatter.parse(XmlTodate);
			long time=Todate.getTime();
			to=new java.sql.Timestamp(time);
			to.setTime(to.getTime() + Constants.MILLISECONDS_IN_A_DAY);
		} catch (ParseException e) {
			// TODO: handle exception
		}
	}
	
	List<Object[]> objects = (List<Object[]>) imvgAlertsActionsManager.listAskedAlarmForMultipleGateway(dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),customer.getId(),deviceToSearch,from,to);
	dataTable.setResults(objects);
	int count =Integer.parseInt(imvgAlertsActionsManager.getCountOfImvgAlarmsForMultipleGateways(customer.getId(),deviceToSearch,from,to).toString());
	dataTable.setTotalRows(count);
	dataTable.setTotalDispalyRows(count);
	String xmlValue = xStream.toXML(dataTable);		
	return xmlValue;
}
/*public String saveAlert(String alert)throws SecurityException,
DOMException, IllegalArgumentException,
ParserConfigurationException, SAXException, IOException,
ClassNotFoundException, InstantiationException,
IllegalAccessException, NoSuchMethodException,
InvocationTargetException{
	XStream xStream  = new XStream();
	Scenario scenario = (Scenario) xStream.fromXML(scenarioXml);
	//1. Check for Duplicate Scenario Entry
	String  error = verifyScenarioDetails(scenario);
	if(error != null)return error;
	AlertType alertType=new AlertType();
	
	DeviceManager deviceManager = new DeviceManager();
	Device device = deviceManager.getDeviceWithStatus(gatewayId,deviceId);
	if(deviceManager.saveAlert(alertType)){
		//1.Send Create-Scenario Command to iMVG.
		scenario = device.retrieveScenarioDetails(scenario.getId());
		ActionModel actionModel = new ActionModel(null, scenario);
		ImonitorControllerAction scenarioCreateAction = new ScenarioCreateAction();
		scenarioCreateAction.executeCommand(actionModel);
		
		//2.Do appropriate actions if iMVG return a success/failure.
		boolean resultFromImvg = IMonitorUtil.waitForResult(scenarioCreateAction);
		if(resultFromImvg){
			//result = "msg.controller.scenarios.0003"+ Constants.MESSAGE_DELIMITER_1 + scenarioCreateAction.getActionModel().getMessage();
			result = scenarioCreateAction.getActionModel().getMessage();
			
			Added By naveen for updating scenario to alexa end point
			 * Date: 26th July 2018
			 
			boolean isAlexaUserExist = scenarioManager.checkAlexaUserByCustomerId(scenario.getGateWay().getCustomer());;
			
			if(isAlexaUserExist) {
				
				AlexaProactiveUpdateScenarioService updateScenario = new AlexaProactiveUpdateScenarioService(scenario, scenario.getGateWay().getCustomer());
				Thread t = new Thread(updateScenario);
				t.start();
			}
			
			
			
			
		}else{
			result = "msg.controller.scenarios.0004"
					+ Constants.MESSAGE_DELIMITER_1 + scenario.getName()
					+ Constants.MESSAGE_DELIMITER_2 + scenarioCreateAction.getActionModel().getMessage();
			scenarioCreateAction.executeFailureResults(actionModel.getQueue());
		}		
		//result = "scenario : '" + scenario.getName() + "' is saved.";
	}
	return result;
}*/

}
