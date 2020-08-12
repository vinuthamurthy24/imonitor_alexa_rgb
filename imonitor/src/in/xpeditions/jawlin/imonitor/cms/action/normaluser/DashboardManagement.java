/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;


import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertsFromImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.H264CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LCDRemoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MotorConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.PIRConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.TarrifConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.targetcost;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorProperties;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
//vibhu start
import in.xpeditions.jawlin.imonitor.util.LogUtil;
//vibhu end


import java.io.File;
import java.io.FileOutputStream;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class DashboardManagement extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5006560264147576121L;
	private ArrayList<String> hours;
	private ArrayList<String> minutes;
	private ArrayList<String> slabrange;
	private ArrayList<String> Endslabrange;
	private String filepath=IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH);
	private String uploadPath=IMonitorProperties.getPropertyMap().get(Constants.REPORT_UPLOAD_PATH);
	private GateWay gateWay;
	private String labelroom;
	private String Name;
	private long unitValue;
	private List<Object[]> currentDate;
	private String maxdate;
	private String mindate;
	private String SelectedMonthValues;
	private targetcost targetcost;
	private List<TarrifConfig> tariffConfigg;
	private String cost;
	private String[] alertExpressions;
	private String TariffID;
	private String type;
	private List<DeviceDetails> ListOfDevicewithPowerValues;
	private DeviceDetails deviceDetails;
	private List<Object[]> costdetails;
	private String energyreport;
	private String labelname;
	private String period;
	
//	private long dayss;
	public DashboardManagement()
	{
		hours = new ArrayList<String>();
		for(int i=0;i<24;i++)
			hours.add(""+i);
		
		minutes=new ArrayList<String>();
		for(int i=0;i<60;i++)
			minutes.add(""+i);
		
		slabrange=new ArrayList<String>();
		slabrange.add("select");
		for(int i=0;i<1000;i+=100)
			slabrange.add(""+i);
		
		Endslabrange=new ArrayList<String>();
		Endslabrange.add("select");
		for(int i=99;i<1000;i+=100)
			Endslabrange.add(""+i);
		
	}
	
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	
	
	public String totarrifConfig() throws Exception
	{

		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		
		String customerxml = stream.toXML(customer);
	
		
		String serviceName = "DashboardService";
		String method = "RetriveTariffConfiguration";
		String result = IMonitorUtil.sendToController(serviceName, method,customerxml);
		
		this.tariffConfigg = (List<TarrifConfig>) stream.fromXML(result);
		
		 serviceName = "DashboardService";
		 method = "listDashboardtargetcost";
		 result = IMonitorUtil.sendToController(serviceName, method, customerxml);
		 
		this.targetcost=(((targetcost)stream.fromXML(result)));
		this.unitValue=(targetcost.getKwPerUnit());
		
		
		
		return SUCCESS;
		
		
		
		
	}
	
	
	
	public String totargetCost() throws Exception
	{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString = stream.toXML(customer);
		String serviceName = "DashboardService";
		String method = "listDashboardtargetcost";
		String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.targetcost=(((targetcost)stream.fromXML(result)));
		String tt=targetcost.getTargetCost();
		this.setCost(tt);
		return SUCCESS;
	}
	
	//bhavya atart
		@SuppressWarnings("unchecked")
		public String piechart() throws Exception
		{
			XStream stream = new XStream();
			String label = this.labelroom;
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
			String xmlString = stream.toXML(this.labelroom);
			long days=7;
			if(label.equalsIgnoreCase("Weekly Power Consumption"))
			{
				 days=7;
			}
			else if(label.equalsIgnoreCase("Monthly Power Consumption"))
				{
				days=30;
				}
			else if(label.equalsIgnoreCase("Yearly Power Consumption"))
			{
				days=365;
			
			}
			
			//LogUtil.info("days----"+days);
	//		this.dayss = days;
			String XMLDays=stream.toXML(days);
			String xmlgetaway=stream.toXML(gatewayid);
		
			String serviceName = "DashboardService";
			String method = "listDashboarddetailsforTotal";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway,XMLDays);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		
		public String dashboardTotalOfCustomer(){
			
			
			return SUCCESS;
		}
		
		@SuppressWarnings("unchecked")
		public String piechartroom() throws Exception
		{
			
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
			String label = this.labelroom;
			String xmllocationId = stream.toXML(this.Name);
			long days=7;
			if(label.equalsIgnoreCase("Weekly Power Consumption"))
			{
				 days=7;
			}
			else if(label.equalsIgnoreCase("Monthly Power Consumption"))
				{
				days=30;
				}
			else if(label.equalsIgnoreCase("Yearly Power Consumption"))
			{
				days=365;
			}
			//LogUtil.info("deays--"+days);
			String XMLDays=stream.toXML(days);
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "listDashboarddetailsforLocation";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway,xmllocationId,XMLDays);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		
		@SuppressWarnings("unchecked")
		public String barchart() throws Exception
		{
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
			String label = this.labelroom;
			String xmldeviceName = stream.toXML(this.labelroom);
			String xmlStringName = stream.toXML(this.Name);
			long days=365;
			String XMLDays=stream.toXML(days);
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "listDashboarddetailsforDevicesforyear";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway,xmlStringName,XMLDays);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		@SuppressWarnings("unchecked")
		public String barchartweek() throws Exception
		{
			//LogUtil.info("barchartweek");
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
			String label = this.labelroom;
			String xmldeviceName = stream.toXML(label);
			String xmlStringName = stream.toXML(this.Name);
			long days=7;
			String XMLDays=stream.toXML(days);
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "listDashboarddetailsforDevices";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway,xmlStringName,XMLDays);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		@SuppressWarnings("unchecked")
		public String barchartmonth() throws Exception
		{
			
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
			String label = this.labelroom;
			String xmldeviceName = stream.toXML(this.labelroom);
			String xmlStringName = stream.toXML(this.Name);
			
			long days=30;
			String XMLDays=stream.toXML(days);
			String xmlgetaway=stream.toXML(gatewayid);
			
			String serviceName = "DashboardService";
			String method = "listDashboarddetailsforDevices";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway,xmlStringName,XMLDays);
			
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
			
		}
		
		@SuppressWarnings("unchecked")
		public String linechart() throws Exception
		{
			//LogUtil.info("linechart");
			//String Id =  IMonitorUtil.sendToController("deviceService", "dashboardvalues");
			XStream stream = new XStream();
			String xmldeviceName = stream.toXML(this.labelroom);
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
			String label = this.labelroom;

			String xmlString = stream.toXML(this.labelroom);
			long days=7;
			String XMLDays=stream.toXML(days);
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "listDashboarddetailsforDevices";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway,xmldeviceName,XMLDays);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
		
			Object[] minlist=objects.get(0);
			
			this.setMindate(""+minlist[0]);
			
			Object[] maxlist=objects.get((objects.size() - 1));
			
			this.setMaxdate(""+maxlist[0]);
			
			return SUCCESS;
		}
		
		public String tarrifConfig() throws Exception
		{
		//LogUtil.info("tarrifConfig----");
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String customerxml = stream.toXML(customer);
			String xmlString = stream.toXML(this.unitValue);
			targetcost targetcostt=new targetcost();
			targetcostt.setKwPerUnit(this.unitValue);
			String targetcosttxml = stream.toXML(targetcostt);
			
			
			String serviceName = "DashboardService";
			String method = "saveUnitvalue";
			String result = IMonitorUtil.sendToController(serviceName, method, targetcosttxml, customerxml);
			//result = IMonitorUtil.formatMessage(this, result);
			//ActionContext.getContext().getSession().put("message", result);
			
			
			long customerId=customer.getId();
			
			if(this.alertExpressions.length < 1){
				
				String message = IMonitorUtil.formatMessage(this, "msg.imonitor.Energy.0001");
				ActionContext.getContext().getSession().put("message", message);
				return ERROR;
			}
		

			this.tariffConfigg = new ArrayList<TarrifConfig>();  
			
			//LogUtil.info("this.alertExpressions.length=="+this.alertExpressions.length);
			for (int i = 0; i < this.alertExpressions.length; i++)
			{
				
				String alertExpression = this.alertExpressions[i];
				
				if(alertExpression=="")
				{
					String message = IMonitorUtil.formatMessage(this, "msg.imonitor.Energy.0002");
					ActionContext.getContext().getSession().put("message", message);
					return SUCCESS;
				
				}
				else
				{
				String[] split = alertExpression.split("=");
				//LogUtil.info("split len--"+split.length);
				if((split.length)!=6)
				{
						String message = IMonitorUtil.formatMessage(this, "msg.imonitor.Energy.0002");
						ActionContext.getContext().getSession().put("message", message);
						return SUCCESS;
				}
				else
				{
					for(int j=1;j<split.length;j++)
					{
						if(split[j].isEmpty())
						{
							String message = IMonitorUtil.formatMessage(this, "msg.imonitor.Energy.0002");
							ActionContext.getContext().getSession().put("message", message);
							return SUCCESS;
						}
					}
				
					
					//LogUtil.info("after for");
						long id;
						if(split[0].isEmpty())
						{
							 id=-1;	
						}
						else
						{
						 id=Long.parseLong(split[0]);
						}
						long StartHours=Long.parseLong(split[1]);
						long EndHours=Long.parseLong(split[2]);
						String tariffrate=split[3];
						
						long startslabrange = Long.parseLong(split[4]);
						long endslabrange = Long.parseLong(split[5]);
						
						//LogUtil.info("startslabrange---"+startslabrange);
						//LogUtil.info("endslabrange---"+endslabrange);
						
						TarrifConfig tariffconfig=  new TarrifConfig();
						tariffconfig.setId(id);
						tariffconfig.setCustomer(customerId);
						tariffconfig.setStartSlabRange(startslabrange);
						tariffconfig.setEndSlabRange(endslabrange);
						tariffconfig.setTarrifRate(tariffrate);
						
							tariffconfig.setStartHour(StartHours);
							tariffconfig.setEndHour(EndHours);
							this.tariffConfigg.add(tariffconfig);	
					
				
				
				}
				 
				}
			}
			
		
			String tariffConfigxml = stream.toXML(this.tariffConfigg);
			
			 serviceName = "DashboardService";
			 method = "saveTariffConfiguration";
			 result = IMonitorUtil.sendToController(serviceName, method, tariffConfigxml);
			 result = IMonitorUtil.formatMessage(this, result);
				ActionContext.getContext().getSession().put("message", result);
			return SUCCESS;
		}
		
		public String targetCost() throws Exception
		{
			XStream stream = new XStream();
			String xmlSelectedMonthValues = stream.toXML(this.SelectedMonthValues);
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String xmlcustomer = stream.toXML(customer);
			String serviceName = "DashboardService";
			String method = "saveTargetcost";
			String message = IMonitorUtil.sendToController(serviceName, method,xmlSelectedMonthValues,xmlcustomer);
			message = IMonitorUtil.formatMessage(this, message);
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		
		
		
		
		public String removeRow() throws Exception
		{
			XStream stream = new XStream();
			String xmlTariffID = stream.toXML(this.TariffID);
			if((this.TariffID).isEmpty())
			{
				return SUCCESS;	
            }
			else
			{
          	String serviceName = "DashboardService";
			String method = "RemoveTarriffRow";
		String result = IMonitorUtil.sendToController(serviceName, method,xmlTariffID);
		if(result == "success")
		{
			return SUCCESS;
		}
		else
		{
			return SUCCESS;
		}
			}
		
		}
		
		//bhavya end
		
		
	/********************
	 * Naveen added for new dashboard Design
	 * Date: 10 OCT 2014 
	 * *****************/
		public String GetRoomWiseDataFromDB() throws Exception
		{
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String customerXml = stream.toXML(customer);
			String SelectedTypexmlString = stream.toXML(this.type);
			String roomXml = stream.toXML(this.labelroom);
			String serviceName = "DashboardService";
			String method = "GetRoomWiseDataFromDB";
			String result = IMonitorUtil.sendToController(serviceName, method, customerXml,SelectedTypexmlString,roomXml);
			this.setListOfDevicewithPowerValues((List<DeviceDetails>) stream.fromXML(result));
		   // LogUtil.info("set list of power values for room: "+ stream.toXML(this.ListOfDevicewithPowerValues));
			return SUCCESS;
		}
		
		
		public String GetDeviceWiseDataFromDB() throws Exception
		{
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String customerXml = stream.toXML(customer);
			String SelectedTypexmlString = stream.toXML(this.type);
			String deviceXml = stream.toXML(this.Name);
			String serviceName = "DashboardService";
			String method = "GetdeviceWiseDataFromDB";
			String result = IMonitorUtil.sendToController(serviceName, method, customerXml,SelectedTypexmlString,deviceXml);
			this.deviceDetails=((DeviceDetails) stream.fromXML(result));
		 //   LogUtil.info("set list of power values for device: "+ stream.toXML(this.deviceDetails));
			return SUCCESS;
		}
		
		public String GetTargetCostdetailsOfCustomer() throws Exception{
			
			XStream stream = new XStream();
			User user =(User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String customerXml = stream.toXML(customer);
			String serviceName = "DashboardService";
			String method = "GetTargetCostDetailsOfCustomer";
			String result = IMonitorUtil.sendToController(serviceName, method, customerXml);
		//	LogUtil.info("Target cost details: "+ result);
			this.costdetails = (List<Object[]>) stream.fromXML(result);
			
			return SUCCESS;
		}
		
		@SuppressWarnings("unchecked")
		public String piechartroomfordaily() throws Exception
		{
			
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
					
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "listDashboarddetailsforDailyTotal";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		public String piechartroomforweekly() throws Exception
		{
			
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
					
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "listDashboarddetailsforweektotal";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway);
		//	LogUtil.info("monthly pie chart: "+ resultXml);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		
		@SuppressWarnings("unchecked")
		public String piechartroomformonthly() throws Exception
		{
			
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
					
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "listDashboarddetailsforMonthlyotal";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway);
		//	LogUtil.info("monthly pie chart: "+ resultXml);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		
		public String subpiefordaily() throws Exception
		{
			
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String custname=stream.toXML(customer.getCustomerId());
			String gatewayid =stream.toXML(customer.getId());
			String label=stream.toXML(this.labelname);		
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "subpiechartfordaily";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway,label,custname);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}

		public String subpieforweek() throws Exception
		{
			
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
			String custname=stream.toXML(customer.getCustomerId());
			String label=stream.toXML(this.labelname);		
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "subpiechartforweek";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway,label,custname);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		public String subpieformonth() throws Exception
		{
			
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
			String custname=stream.toXML(customer.getCustomerId());
			String label=stream.toXML(this.labelname);		
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "subpiechartformonth";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway,label,custname);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		
		
		public String barchartroomformonth() throws Exception
		{
			
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
					
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "listDashboarddetailsforbarmonth";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway);
			//LogUtil.info("monthly bar chart: "+ resultXml);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		
		public String barchartthreemonth() throws Exception
		{
			
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String gatewayid =stream.toXML(customer.getId());
					
			String xmlgetaway=stream.toXML(gatewayid);
			String serviceName = "DashboardService";
			String method = "listbardetailsforthreemonth";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlgetaway);
			//LogUtil.info("monthly bar chart: "+ resultXml);
			List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
			setCurrentDate(objects);
			return SUCCESS;
		}
		
	@SuppressWarnings("unchecked")
	public String torequestforExel() throws Exception{
	
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String togetExeldata() throws Exception{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String customerId = customer.getCustomerId();
		String xml2=stream.toXML(customer.getId());
		String xml=stream.toXML(energyreport);
		String serviceName = "DashboardService";
		String method = "downloadenergyReport";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xml,xml2);
		List<Object[]> object=(List<Object[]>) stream.fromXML(resultXml);	
		String Filepath = uploadPath+customerId+"_EnergyReport.xls";
		filepath+=customerId+"_EnergyReport.xls";
		
		Workbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet("EnergyConsumption");
	    
	    Row rowhead= sheet.createRow(0);
	    CellStyle style=workbook.createCellStyle();;
			style.setAlignment(CellStyle.ALIGN_CENTER);
	     style.setWrapText(true);
	     
	   
	     rowhead.setRowStyle(style);
	     rowhead.createCell(0).setCellValue("DeviceName");
	     rowhead.createCell(1).setCellValue("LocationName");
	     rowhead.createCell(2).setCellValue("ConsumptionValue in kWh");
	     rowhead.createCell(3).setCellValue("AlertTime");
	     int rowcount=1;
	     for (Object[] aBook : object) {
	    //	 LogUtil.info(aBook[0]+" "+aBook[1]+" "+aBook[2]+" "+aBook[3]);	 
	     	String DeviceName=(String) aBook[0];
	     	String LocationName=(String) aBook[1];
	     	String ConsumptionValue =String.format("%1$,.4f",aBook[2]);
	     	Date date=(Date) aBook[3];
	     	DateFormat dateformat=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	     	String AlertTime =dateformat.format(date);
	     	Row dataRow = sheet.createRow(rowcount);
	     	
	     	
	     	dataRow.setRowStyle(style);
	     	dataRow.createCell(0).setCellValue(DeviceName);
	 	    dataRow.createCell(1).setCellValue(LocationName);
	 	    dataRow.createCell(2).setCellValue(ConsumptionValue);
	 	   dataRow.createCell(3).setCellValue(AlertTime);
	     	rowcount++;
	     }
	     
	         //Write the workbook in file system
	         FileOutputStream out = new FileOutputStream(new File(Filepath));
	         workbook.write(out);
	         
	     //   LogUtil.info("report downloaded to this path :"+filepath);
	         out.close();
		return SUCCESS;
	}
	
	
	public String enrgyusageovertime() throws Exception
	{
		
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String idxml =stream.toXML(customer.getId());
		String xml = stream.toXML(this.period);		
		String serviceName = "DashboardService";
		String method = "listenergyusageovertime";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, idxml,xml);
		//LogUtil.info("monthly bar chart: "+ resultXml);
		List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
		setCurrentDate(objects);
		return SUCCESS;
	}
	public ArrayList<String> getHours() {
		return hours;
	}

	public void setHours(ArrayList<String> hours) {
		this.hours = hours;
	}

	public ArrayList<String> getMinutes() {
		return minutes;
	}

	public void setMinutes(ArrayList<String> minutes) {
		this.minutes = minutes;
	}

	public ArrayList<String> getSlabrange() {
		return slabrange;
	}

	public void setSlabrange(ArrayList<String> slabrange) {
		this.slabrange = slabrange;
	}

	public ArrayList<String> getEndslabrange() {
		return Endslabrange;
	}

	public void setEndslabrange(ArrayList<String> endslabrange) {
		Endslabrange = endslabrange;
	}

	public String getLabelroom() {
		return labelroom;
	}

	public void setLabelroom(String labelroom) {
		this.labelroom = labelroom;
	}

	

	public long getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(long unitValue) {
		this.unitValue = unitValue;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public List<Object[]> getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(List<Object[]> currentDate) {
		this.currentDate = currentDate;
	}

	public String getMaxdate() {
		return maxdate;
	}

	public void setMaxdate(String maxdate) {
		this.maxdate = maxdate;
	}

	public String getMindate() {
		return mindate;
	}

	public void setMindate(String mindate) {
		this.mindate = mindate;
	}


	public String getSelectedMonthValues() {
		return SelectedMonthValues;
	}

	public void setSelectedMonthValues(String selectedMonthValues) {
		SelectedMonthValues = selectedMonthValues;
	}

	

	public targetcost getTargetcost() {
		return targetcost;
	}

	public void setTargetcost(targetcost targetcost) {
		this.targetcost = targetcost;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	

	public String[] getAlertExpressions() {
		return alertExpressions;
	}

	public void setAlertExpressions(String[] alertExpressions) {
		this.alertExpressions = alertExpressions;
	}

	
	public List<TarrifConfig> getTariffConfigg() {
		return tariffConfigg;
	}

	public void setTariffConfigg(List<TarrifConfig> tariffConfigg) {
		this.tariffConfigg = tariffConfigg;
	}

	public String getTariffID() {
		return TariffID;
	}

	public void setTariffID(String tariffID) {
		TariffID = tariffID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<DeviceDetails> getListOfDevicewithPowerValues() {
		return ListOfDevicewithPowerValues;
	}

	public void setListOfDevicewithPowerValues(
			List<DeviceDetails> listOfDevicewithPowerValues) {
		ListOfDevicewithPowerValues = listOfDevicewithPowerValues;
	}

	public List<Object[]> getCostdetails() {
		return costdetails;
	}

	public void setCostdetails(List<Object[]> costdetails) {
		this.costdetails = costdetails;
	}

	public DeviceDetails getDeviceDetails() {
		return deviceDetails;
	}

	public void setDeviceDetails(DeviceDetails deviceDetails) {
		this.deviceDetails = deviceDetails;
	}

	

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getEnergyreport() {
		return energyreport;
	}

	public void setEnergyreport(String energyreport) {
		this.energyreport = energyreport;
	}

	public String getLabelname() {
		return labelname;
	}

	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

/*	public long getDayss() {
		return dayss;
	}

	public void setDayss(long dayss) {
		this.dayss = dayss;
	}*/

	
	
}
