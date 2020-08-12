/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;



import in.xpeditions.jawlin.imonitor.controller.dao.entity.AdminSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerReport;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertMonitor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertStatus;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AreaCode;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerServices;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IssueStatus;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LocationMap;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Role;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.RootCause;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Severity;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerPasswordHintQuestionAnswer;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AgentManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AlertsFromImvgManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerSupportManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.LocationManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RoleManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScenarioManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.StatusManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.EMailNotifications;
import in.xpeditions.jawlin.imonitor.controller.notifications.SmsNotifications;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.FTPUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.alias.ClassMapper.Null;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.IOUtils;



public class CustomerServiceManager {
	public String saveCustomer(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String result="no";
		XStream stream=new XStream();
		Customer customer=(Customer)stream.fromXML(xml);
//		1. Setting the gateways to null.		
		Set<GateWay> gateWays = customer.getGateWays();
		customer.setGateWays(null);
		CustomerManager customerManager=new CustomerManager();
		GatewayManager gatewayManager = new GatewayManager();
		if(customerManager.saveCustomer(customer)){
			for (GateWay gWay : gateWays) {
				gWay.setCustomer(customer);
				gatewayManager.updateCustomerOfGateWay(gWay);
			}
			result="yes";
		}
		
		return result;
	}
	
	public String saveCustomerAndAssignDefaultLocation(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		XStream stream=new XStream();
		Customer customer=(Customer)stream.fromXML(xml);
//		1. Setting the gateways to null.		
		Set<GateWay> gateWays = customer.getGateWays();
		List<GateWay> usedGateWays = new ArrayList<GateWay>();
		CustomerManager customerManager=new CustomerManager();
		if(gateWays == null || gateWays.size() < 1){
			return "Atleast one gateway is required to save a customer.";
		}else{
			for (GateWay  gateWay:gateWays) {
				Customer customer2 = customerManager.getCustomerByMacId(gateWay.getMacId());
				if(customer2 !=null){
					usedGateWays.add(gateWay);
				}
			}
			if(usedGateWays.size() ==0){
				customerManager.saveCustomerWithDefaultLocation(customer);
			}
		}
		String gateWaysXml = stream.toXML(usedGateWays);
		return gateWaysXml;
	}
	
	public String saveCustomerAndAssignDefaultLocationByGateWay(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		XStream stream=new XStream();
		String result = "";
		Customer customer=(Customer)stream.fromXML(xml);
//		1. Setting the gateways to null.		
		Set<GateWay> gateWays = customer.getGateWays();
		CustomerManager customerManager=new CustomerManager();
		GatewayManager gatewayManager = new GatewayManager();
		DeviceManager devicemanager=new DeviceManager();
		if(customer.getId()!=0)
		{
		Customer PreviousCustomer=customerManager.getCustomerWithGateWaysByCustomerId(customer.getId());
		Customer isCustomer = customerManager.getCustomerByCustomerId(customer.getCustomerId());
		if(isCustomer !=null){
			if((PreviousCustomer.getId() != isCustomer.getId()) )
			{
				result="Failure~Customer with id " + customer.getCustomerId() + " is Already Used.Please choose any other Customer Id!";
				//return result;
			}
			else
			{
				customer.setId(isCustomer.getId());
				gateWays.addAll(gatewayManager.getGateWaysOfCustomer(isCustomer));
				customerManager.updateCustomerAndSetGatewayNull(customer);
				
				for (GateWay  gateWay:gateWays) {
						gateWay.setCustomer(customer);
						gatewayManager.updateCustomerOfGateWay(gateWay, customer);
						
						if(!(((customer.getFeaturesEnabled())&4)==0))
						{
						
						
							
							devicemanager.createSystemDevicesFordashboard(gateWay);
						
							result = "success";
						}
						else
						{
							
								devicemanager.deleteSystemDevicesFordashboard(gateWay);
							
							
						result = "success";
		           }
		    }
			}
		}
		    else{
		    	
			result = customerManager.saveCustomerWithDefaultLocation(customer);
			
		}
		}
		else
		{
			Customer isCustomer = customerManager.getCustomerByCustomerId(customer.getCustomerId());
			if(isCustomer !=null){
					result="Failure~Customer with id " + customer.getCustomerId() + " is Already Used.Please choose any other Customer Id!";
					//return result;
				}
			else
			{
				result = customerManager.saveCustomerWithDefaultLocation(customer);
			}
			
		}
		String gateWaysXml = stream.toXML(result);
		return gateWaysXml;
	} 
	
	@SuppressWarnings("unused")
	public String deleteCustomer(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, JSchException, SftpException{
		//LogUtil.info("customer Service Manager class");
		String result="no";
		XStream stream=new XStream();
		long id = (Long) stream.fromXML(xml);
		
		CustomerManager customerManager=new CustomerManager();
		Customer customer = customerManager.getCustomerById(id);
		
		UserManager userManager = new UserManager();
		List<User> users = userManager.getUsersByCustomer(customer);
		
		for(User user:users){
			userManager.deleteUser(user);
			
		}
		GatewayManager gatewayManager = new GatewayManager();
		DeviceManager devicemanager=new DeviceManager();
		List<GateWay> gateWays = gatewayManager.getGateWaysByCustomer(customer);
		Boolean createdevice=false;
		if(gateWays != null && gateWays.size() > 0){
			for(GateWay gateWay:gateWays){
				
				gatewayManager.updateGateWay(gateWay);
				createdevice = new DeviceManager().deleteSystemDevicesFordashboard(gateWay);
				gateWay.setCustomer(null);
				
			}
			String macId = gateWays.get(0).getMacId();
			Agent agent = new AgentManager().getReceiverIpAndPortWithFtpDetails(macId);
			/*if(agent != null){
				String customerId = customer.getCustomerId();
				//String imagesDir = customerId + "/" + ControllerProperties.getProperties().get(Constants.IMAGES_DIR);
				//String streamsDir = customerId + "/" + ControllerProperties.getProperties().get(Constants.STREAMS_DIR);
				FTPUtil.deleteFolders(agent.getFtpIp(), agent.getFtpPort(),agent.getFtpUserName(), agent.getFtpPassword(),customerId);
			}*/
		}
		if(customerManager.deleteCustomer(customer)){result="yes";}
		//LogUtil.info("customer Service Manager class result="+stream.toXML(result));
		return result;
	}
	
	public String updateCustomer(String xml) throws SecurityException, DOMException, IllegalArgumentException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		XStream stream=new XStream();
		Customer customer=(Customer)stream.fromXML(xml);
		CustomerManager customerManager=new CustomerManager();
		DeviceManager devicemanager=new DeviceManager();
		Customer isCustomer = customerManager.getCustomerByCustomerId(customer.getCustomerId());
		Customer PreviousCustomer=customerManager.getCustomerWithGateWaysByCustomerId(customer.getId());
		//GateWay gat=customerManager.getCustomerWithGateWaysByCustomerId(id)
		String result = null;
		
		if(isCustomer != null)
		{
			if((PreviousCustomer.getId() != isCustomer.getId()) )
			{
				result="Failure";
				return result;
			}
			else
			{
				List<GateWay> usedGateWays = new ArrayList<GateWay>();
                //1. Setting the gateways to null.		
				Set<GateWay> gateWays = customer.getGateWays();
				GatewayManager gatewayManager = new GatewayManager();
				for (GateWay  gateWay:gateWays) {
					Customer customer2 = customerManager.getCustomerByMacId(gateWay.getMacId());
					if(customer2 != null && customer2.getId() != customer.getId()){
						usedGateWays.add(gateWay);
					}
				}
				if(usedGateWays.size() ==0){
					customerManager.updateCustomerAndSetGatewayNull(customer);
					for (GateWay  gateWay:gateWays) {
						Customer customer2 = customerManager.getCustomerByMacId(gateWay.getMacId());
						if(customer2 ==null){
							gateWay.setCustomer(customer);
							gatewayManager.updateCustomerOfGateWay(gateWay, customer);
							
							
							if(!(((customer.getFeaturesEnabled())&4)==0))
							{
								
							 devicemanager.createSystemDevicesFordashboard(gateWay);
							
							
							}
							else
							{
							devicemanager.deleteSystemDevicesFordashboard(gateWay);
								
							}
							
							
						}
					}
				}
				String gateWaysXml = stream.toXML(usedGateWays);
				return gateWaysXml;
			}
		}
		else
		{
			
			List<GateWay> usedGateWays = new ArrayList<GateWay>();
//			1. Setting the gateways to null.		
			Set<GateWay> gateWays = customer.getGateWays();
			GatewayManager gatewayManager = new GatewayManager();
			for (GateWay  gateWay:gateWays) {
				Customer customer2 = customerManager.getCustomerByMacId(gateWay.getMacId());
				if(customer2 != null && customer2.getId() != customer.getId()){
					usedGateWays.add(gateWay);
				}
			}
			if(usedGateWays.size() ==0){
				customerManager.updateCustomerAndSetGatewayNull(customer);
				for (GateWay  gateWay:gateWays) {
					Customer customer2 = customerManager.getCustomerByMacId(gateWay.getMacId());
					if(customer2 ==null){
						gateWay.setCustomer(customer);
						gatewayManager.updateCustomerOfGateWay(gateWay, customer);
						
						
						if(!(((customer.getFeaturesEnabled())&4)==0))
						{
							
						
							
							devicemanager.createSystemDevicesFordashboard(gateWay);
						
						
						}
						else
						{
							
								
								 devicemanager.deleteSystemDevicesFordashboard(gateWay);
							
						}
					}
				}
			}
			
			String gateWaysXml = stream.toXML(usedGateWays);
			return gateWaysXml;
		}
	}
	
	public String listCustomers(){
		String xml="";
		XStream xStream=new XStream();
		CustomerManager customerManager=new CustomerManager();
		List<Customer>customers=customerManager.listOfCustomers();
		xml=xStream.toXML(customers);
		return xml;
	}

	public String listGateWaysAndDevicesOfCustomerByCustomerId(String xml){
		XStream stream=new XStream();
		String customerId=(String) stream.fromXML(xml);
		CustomerManager customerManager=new CustomerManager();
		Customer customer = customerManager.getCustomerByCustomerId(customerId);
		List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomer(customer);
		Set<GateWay> gateWaysList = new HashSet<GateWay>(gateWays);
		customer.setGateWays(gateWaysList);
		String customerXml = stream.toXML(customer);
		return customerXml;
	}
	
	// ******************************************************************** sumit begin **********************************************************
	
	/**
	 * @author sumit kumar
	 * @param mainUserXml
	 * @return
	 * 
	 * Date Modified : Jan02, 2014.
	 * Modified to get list of all devices for the main user to restrict the sub users.
	 */
	/*public String listAllDevicesForAllGateWaysOfCustomerByMainUser(String customerIdXml){
		LogUtil.info("listAllDevicesForAllGateWaysOfCustomerByMainUser : IN");
		XStream stream=new XStream();
		//User mainUser = (User) stream.fromXML(mainUserXml);
		LogUtil.info("listAllDevicesForAllGateWaysOfCustomerByMainUser : OUT");
		return listAllDevicesForAllGateWaysOfCustomerByCustomerId(customerIdXml);
	}*/
	
	public String listAllDevicesForAllGateWaysOfCustomerByCustomerId(String xml){
		XStream stream=new XStream();
		String customerId=(String) stream.fromXML(xml);
		CustomerManager customerManager=new CustomerManager();
		Customer customer = customerManager.getCustomerByCustomerId(customerId);
		List<Device> devices = customerManager.getAllDevicesOfCustomer(customer);
		List<Device> devicesListOfCustomer = new ArrayList<Device>(devices);
		String deviceListXml = stream.toXML(devicesListOfCustomer);
		return deviceListXml;
	}	
	public String listAllscenarios(String xml){
		XStream stream=new XStream();
		
		List<Scenario> scenarios = new ArrayList<Scenario>();
		
		
		String deviceListXml = stream.toXML(scenarios);
		return deviceListXml;
	}	
	// *********************************************** To get the Forgot Password Hint Question with Answer. *************************************
	public String getForgotPasswordQuestionsWithAnswer(String IdXml){
		XStream stream = new XStream();
		String quesAnswerXml = null;
		long cId = (Long) stream.fromXML(IdXml);
		CustomerPasswordHintQuestionAnswer passwordHintQuesAns = new CustomerPasswordHintQuestionAnswer();
		CustomerManager customerManager = new CustomerManager();
		passwordHintQuesAns = customerManager.getForgotPasswordQuestionsWithAnswersForCustomerById(cId);
		if(passwordHintQuesAns != null){
			quesAnswerXml = stream.toXML(passwordHintQuesAns);
		}else {
			passwordHintQuesAns = new CustomerPasswordHintQuestionAnswer();
			quesAnswerXml = stream.toXML(passwordHintQuesAns);
		}
		return quesAnswerXml;
	}
	
	public String getEmailOfCustomer(String cIdXml, String custIdXml){
		XStream stream = new XStream();
		long cId = (Long) stream.fromXML(cIdXml);
		String customerId = (String) stream.fromXML(custIdXml);
		CustomerManager customerManager = new CustomerManager();
		String email = customerManager.getEmailOfCustomer(cId, customerId);
		String emailXml = stream.toXML(email);
		return emailXml;
	}
	
	public String saveResetPasswordInfo(String custId, String quesXml, String answerXml){
		XStream stream = new XStream();
		String result = "";
		long cId = (Long) stream.fromXML(custId);
		long quesId = (Long) stream.fromXML(quesXml);
		String answer = (String) stream.fromXML(answerXml);
		CustomerManager customerManager = new CustomerManager();
		if(customerManager.saveResetPasswordInfo(cId, quesId, answer)){
			result = "success";
		}else{
			result = "failure";
		}
		return stream.toXML(result);
	}
	// ******************************************************************** sumit end ************************************************************
	
	/**
	 * @author sumit kumar
	 * @param userXml
	 * @return
	 * Date Modified: Jan 22, 2014 by Sumit Kumar.
	 * Modification: Implemented to Filter out the Devices and Scenarios Accessible to the Sub User.
	 * Invocation From: Home Page of the Sub User.
	 */
	public String listGateWaysAndDevicesOfCustomerForSubUser(String userXml){
		XStream xStream=new XStream();
		User user = (User) xStream.fromXML(userXml);
		CustomerManager customerManager = new CustomerManager();
		List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomerForSubUser(user);
		String gateWaysXml = xStream.toXML(gateWays);
		return gateWaysXml;
	}
	
	public String listGateWaysAndDevicesOfCustomer(String xml){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		CustomerManager customerManager = new CustomerManager();
		List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfCustomer(customer);
		String gateWaysXml = xStream.toXML(gateWays);
		return gateWaysXml;
	}
	
	public String getCustomerByCustomerId(String xml){
		XStream stream=new XStream();
		String customerId=(String) stream.fromXML(xml);
		CustomerManager customerManager=new CustomerManager();
		Customer customer = customerManager.getCustomerByCustomerId(customerId);
		return stream.toXML(customer);
	}
	
	public String listAskedCustomers(String xml){
		XStream xStream=new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(xml);
		CustomerManager customerManager = new CustomerManager();
		int count = customerManager.getTotalGateWayCount();
		dataTable.setTotalRows(count);
		String sSearch = dataTable.getsSearch();
		String[] cols = { "c.customerId","c.firstName","c.lastName","c.phoneNumber","s.name"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = customerManager.listAskedCustomer(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength());
		dataTable.setResults(list);
		int displayRow = customerManager.getTotalCustomerCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	
	public String getCustomerById(String xml){
		XStream xStream=new XStream();
		long id = (Long) xStream.fromXML(xml);
		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.getCustomerWithGateWaysByCustomerId(id);
		String valueInXml = xStream.toXML(customer);
		return valueInXml;
	}
	
	public String getCustomerByMacId(String xml){
		XStream xStream=new XStream();
		String macid = (String) xStream.fromXML(xml);
		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.getCustomerByMacId(macid);
		String valueInXml = xStream.toXML(customer);
		return valueInXml;
	}
	
	public String retrieveCustomerDeviceAlertAndActionsAndNotificationProfiles(String xml){
		XStream xStream=new XStream();
		String customerId = (String) xStream.fromXML(xml);
		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.retrieveCustomerDeviceAlertAndActionsAndNotificationProfiles(customerId);
		String valueInXml = xStream.toXML(customer);
		return valueInXml;
	}
	public String getAllDevicesOfCustomer(String xml) {
		XStream stream = new XStream();
		Customer customer = (Customer) stream.fromXML(xml);
		CustomerManager customerManager=new CustomerManager();
		List<Device> devices = customerManager.getAllDevicesOfCustomer(customer);
		String result = stream.toXML(devices);
		return result;
	}
	
	public String listcustomerforreport() {
		XStream stream = new XStream();
		CustomerManager customerManager=new CustomerManager();
		List<Customer> customers = customerManager.listAskedCustomerToList();
		String result = stream.toXML(customers);
		return result;
	}
	
	public String togetseveritylist() {
		XStream stream = new XStream();
		CustomerSupportManager customerSupportManager = new CustomerSupportManager(); 
		List<Severity> severitylevels = customerSupportManager.listOfSeverityLevels();
		String result = stream.toXML(severitylevels);
		return result;
	}
	
	public String togetstatuslist() {
		XStream stream = new XStream();
		CustomerSupportManager customerSupportManager = new CustomerSupportManager(); 
		List<IssueStatus> statuslevels = customerSupportManager.listOfIssueStatus();
		String result = stream.toXML(statuslevels);
		return result;
	}
	
	public String togetrootcauselist() {
		XStream stream = new XStream();
		CustomerSupportManager customerSupportManager = new CustomerSupportManager(); 
		List<RootCause> rcLists = customerSupportManager.listOfRootCause();
		String result = stream.toXML(rcLists);
		return result;
	}
	
	public String savereportforcustomer(String reportXml){
		XStream stream = new XStream();
		CustomerReport customerReport = new CustomerReport();
		customerReport = (CustomerReport) stream.fromXML(reportXml);
		String res = "failure";
		CustomerSupportManager customer = new CustomerSupportManager();
		//Date date = customerReport.getReportedDate();
		
		boolean result = customer.saveReportForCustomer(customerReport);
		if(result){
			res="success";
		}
		return res;
	}
	
	
	public String listAskedCustomersReports(String xml){
		
		XStream xStream=new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(xml);
		CustomerSupportManager customerManager = new CustomerSupportManager();
		int count = customerManager.getTotalReportsCount();
		String statusToSearch=dataTable.getsSearch_1();
		String severityToSearch=dataTable.getsSearch_2();
		String rootCauseToSearch=dataTable.getsSearch_3();
		dataTable.setTotalRows(count);
		String sSearch = dataTable.getsSearch();
		String[] cols = { "c.customerId","cr.resolutionDate","s.severityLevel","fs.name","rc.cause"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by "+ colName + " "+ dataTable.getsSortDir_0(); ;
		List<?> list = customerManager.listAskedCustomerIssueList(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),statusToSearch,severityToSearch,rootCauseToSearch);
		dataTable.setResults(list);
		int displayRow = customerManager.getTotalDisplayReportCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	
	public String listAskedResolvedReports(String xml){
		
		XStream xStream=new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(xml);
		CustomerSupportManager customerManager = new CustomerSupportManager();
		int count = customerManager.getTotalReportsCount();
		String statusToSearch=dataTable.getsSearch_1();
		String severityToSearch=dataTable.getsSearch_2();
		
		dataTable.setTotalRows(count);
		String sSearch = dataTable.getsSearch();
		String[] cols = { "c.customerId","s.severityLevel","cr.resolutionDate","fs.name"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by c.customerId desc," + colName ;
		List<?> list = customerManager.listAskedResolvedIssueList(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),statusToSearch,severityToSearch);
		dataTable.setResults(list);
		int displayRow = customerManager.getTotalDisplayReportCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
	   	return valueInXml;
	}
	
	public String togetselectedCustomerReport(String xml){
		XStream stream = new XStream();
		String reportId= (String) stream.fromXML(xml); 
		CustomerSupportManager supportManager = new CustomerSupportManager();
		CustomerReport report = supportManager.getSelectedCustomerReport(reportId);
		String reportXml = stream.toXML(report);
		return reportXml;
	}
	
	public String updatecustomerreport (String reportXml){
		String result = "failure";
		XStream stream = new XStream();
		CustomerSupportManager supportManager = new CustomerSupportManager();
		CustomerReport report = (CustomerReport) stream.fromXML(reportXml);
		boolean isDBUpdate = supportManager.saveUpdatedCustomerReport(report);
		if(isDBUpdate){
			result = "success";
			result = stream.toXML(result);
			
		}else{
			
			result = stream.toXML(result);
		}
		return result;
	}
	
	public String deleteCustomerReport (String idXml){
		
		XStream stream = new XStream();
		String id = (String) stream.fromXML(idXml);
		CustomerSupportManager supportManager = new CustomerSupportManager();
		supportManager.deleteSelectedCustomerReport(id);
		return "success";
	}
	
	public String objectsforexcelreport(){
		
		XStream stream = new XStream();
		CustomerSupportManager supportManager = new CustomerSupportManager();
		List<Object[]> list = new ArrayList<Object[]>();
		list = supportManager.getCustomerReportObjects();
		String resultxml = stream.toXML(list);
		return resultxml;
	} 
	
	
	public String getCustomerServicesById(String xml){
		
		XStream stream=new XStream();
		long id = (Long) stream.fromXML(xml);
		CustomerManager customerManager = new CustomerManager();
		customerManager.getCustomerServicesByCustomerId(id);
		CustomerServices Services = customerManager.getCustomerServicesByCustomerId(id);
		return stream.toXML(Services);
	}
	
	
	public String updateServices(String xmlString){
		XStream stream=new XStream();
		String result = "failure";
		CustomerServices customerService =(CustomerServices) stream.fromXML(xmlString);
		CustomerManager customerManager = new CustomerManager();
		boolean update = customerManager.updateCustomerServices(customerService);
		Customer customer = customerManager.getCustomerById(customerService.getCustomer().getId());
		if(update){
			result = "success";
			
		int Intimation = customerService.getIntimation();
		int check=customerService.getServiceEnabled();
		int check1=customerService.getServiceEnabled();
		String output;
		if(check==0){
			output="Enabled";
		}else{output="Disabled";}
	
		String outcome = null;
		if(check==1){
			outcome="SMS";
		}
		else if(check1==2){outcome="Email";}
		else if(check1==3){outcome="SMS,Email";}
		else if(check1==4){outcome="VideoStreaming";}
		else if(check1==5){outcome="SMS,VideoStreaming";}
		else if(check1==6){outcome="Email,VideoStreaming";}
		else if(check1==7){outcome="SMS,Email and VideoStreaming";}
		else if(check1==8){outcome="WhatsApp";}
		else if(check1==9){outcome="SMS,WhatsApp";}
		else if(check1==10){outcome="Email,WhatsApp";}
		else if(check1==11){outcome="SMS,Email and WhatsApp";}
		else if(check1==12){outcome="VideoStreaming,WhatsApp";}
		else if(check1==13){outcome="SMS,VideoStreaming and WhatsApp ";}
		else if(check1==14){outcome="Email,VideoStreaming and WhatsApp ";}
		else if(check1==15){outcome="SMS,Email,VideoStreaming and WhatsApp";}
		
		
		String messageMail = " Following Service  "+outcome+"  "+output+" for your account. \n Please contact your supplier ";
		EMailNotifications eMailNotifications = new EMailNotifications();
		
		
		try {
			
			if((Intimation & 1)==1)
			{
				
				String[] toSupport = {"support@imonitorsolutions.com"};
				eMailNotifications.notify1(messageMail,toSupport, null,customer.getCustomerId());
				
			}
			if((Intimation & 2) == 2)
			{
				
				String[] toCustomer = {customer.getEmail()};
				eMailNotifications.notify1(messageMail,toCustomer, null,customer.getCustomerId());
				
			}
				if((Intimation & 4) == 4)
				{
					
					String[] toOther ={customerService.getIntimationMail()};
					eMailNotifications.notify1(messageMail,toOther, null,customer.getCustomerId());
				
				}
				 
		} catch (Exception e) {
      
           LogUtil.info("could not execute: "+ e.getMessage());
		}
				
		}
		return stream.toXML(result);
	}

	public String RenewalServices(String valueInXml,String intimationXml,String otherMAilXml){
		XStream stream=new XStream();
		String result = "failure";
		CustomerManager customerManager = new CustomerManager();
		Customer customer=(Customer)stream.fromXML(valueInXml);
		Customer customerFromdb= customerManager.getCustomerByCustomerId(customer.getCustomerId());
		customerFromdb.setRenewalDate(customer.getRenewalDate());
		customerFromdb.setRenewalDuration(customer.getRenewalDuration());
		customerFromdb.setDateOfExpiry(customer.getDateOfExpiry());
		boolean update=customerManager.updateCustomer(customerFromdb);
		if(update){
			result = "success";
			
			int Intimation =(Integer)stream.fromXML(intimationXml);
			
			String OtherMail=(String)stream.fromXML(otherMAilXml);
			
			String messageMail = " Your Maintainence contract with iMonitor Smart Home Solutions has been renewed.Your account will expire on "+customer.getDateOfExpiry()+" .\n Please contact your supplier for any queries or contact iMonitor support via support@imonitorsolutions.com \n "+" \n with best regards \n iMonitor Support ";
			EMailNotifications eMailNotifications = new EMailNotifications();
		try {
			
				if((Intimation & 1)==1)
				{
					
					String[] toSupport = {"support@imonitorsolutions.com"};
					eMailNotifications.notify(messageMail,toSupport, null,customer.getCustomerId());
				}
				if((Intimation & 2) == 2)
				{
					
					String[] toCustomer = {customer.getEmail()};
					eMailNotifications.notify(messageMail,toCustomer, null,customer.getCustomerId());
				}
				if((Intimation & 4) == 4)
				{
					
					String[] toOther ={OtherMail};
					eMailNotifications.notify(messageMail,toOther, null,customer.getCustomerId());
				}
				 
		} catch (Exception e) {
      
           LogUtil.info("could not execute: "+ e.getMessage());
		}
		}
		return stream.toXML(result);
 }
	
	public String listCustomersapi(){
		String xml="";
		XStream xStream=new XStream();
		CustomerManager customerManager=new CustomerManager();
		List<Object[]>customers=customerManager.listofcustomerapi();
		xml=xStream.toXML(customers);
		return xml;
	}
	
	public String loginpasscheckapi(String userId,String password,String custid){
		XStream xStream=new XStream();
		CustomerManager customerManager=new CustomerManager();
		
		boolean user=customerManager.loginpasscheckapi(userId,password,custid);
		
		List<Object[]> gateway = null;
		if(user==true){
		gateway=customerManager.getcustomeraccDetails(custid,userId);
		
		}else{
		gateway=null;
		}
		String xml=xStream.toXML(gateway);
		return xml;		
	}
	
	public String userdetailsapi(String userId){
		XStream xStream=new XStream();
		UserManager userManager = new UserManager();
		List<Object[]> userinfo=userManager.getUserIdlastloginapi(userId);
		String xml=xStream.toXML(userinfo);
		return xml;		
	}
	
	public String loginpasscheckapi1(String userId,String password,String custid){
		XStream xStream=new XStream();
		CustomerManager customerManager=new CustomerManager();
		//UserManager userManager = new UserManager();
		boolean user=customerManager.loginpasscheckapi(userId,password,custid);
		
		List<Object[]> list1;
		List<Object[]> gateway1 = null;
		if(user==true){
		list1=customerManager.getCustomerdeviceAcDetails(custid);
		gateway1=customerManager.getCustomerdevicePowerDetails(custid);
		
		gateway1.addAll(list1);
		
		}else{
		gateway1=null;
		}
		String xml=xStream.toXML(gateway1);
		return xml;
	}
	

public String listAskedCustomersAlertsForModify(String xml){
		
		XStream stream = new XStream();
		DataTable dataTable =  (DataTable) stream.fromXML(xml);
		CustomerSupportManager customerSupport = new CustomerSupportManager();
		int count = customerSupport.getTotalAlertsCount();
		dataTable.setTotalRows(count);
		String sSearch = dataTable.getsSearch();
		String[] cols = { "c.customerId","am.attended", "s.name"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		String colName = cols[col];
		sOrder = "order by "+ colName + " "+ dataTable.getsSortDir_0();
		List<?> list = customerSupport.getListOfCustomerAlertsWithStatus(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength());
		dataTable.setResults(list);
		int displayRow = customerSupport.getTotalCustomerAlertCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = stream.toXML(dataTable);
		return valueInXml;
	}

public String Savealertmonitor(String xml,String attended,String contactno,String statusname){
	XStream stream = new XStream();
	String result="no";
	AlertsFromImvgManager alertsmanager=new AlertsFromImvgManager();
	AlertMonitor alert=new AlertMonitor();
	AlertStatus status=new AlertStatus();
	long id=(Long) stream.fromXML(xml);
	long statusid=(Long) stream.fromXML(statusname);
	alert=alertsmanager.getalertsmonitorById(id);
	status=alertsmanager.getalertstatusbyid(statusid);
	alert.setAttended(attended);
	alert.setContactnumber(contactno);
	alert.setAlertStatus(status);
	boolean resultxml=alertsmanager.updatealertsmonitor(alert);
	if(resultxml){
		result="yes";
	}
	return result;
}

public String deletealertmonitor(String xml){
	String result="no";
	XStream stream = new XStream();
	AlertsFromImvgManager alertsmanager=new AlertsFromImvgManager();
	AlertMonitor alertmonitor=new AlertMonitor();
	long id=(Long) stream.fromXML(xml);
	alertmonitor=alertsmanager.getalertsmonitorById(id);
	boolean results=alertsmanager.deletealertmonitor(alertmonitor);
	if(results){
		result="yes";
	}
	return result;	
}

public String getalertreportsbyid(String xml){
	XStream stream = new XStream();
	AlertsFromImvgManager alertsmanager=new AlertsFromImvgManager();
	AreaCode areacode=new AreaCode();
	CustomerManager custmanager=new CustomerManager();
	AlertMonitor alertmonitor=new AlertMonitor();
	long id=(Long) stream.fromXML(xml);
	alertmonitor=alertsmanager.getalertsmonitorById(id);
	long custid=alertmonitor.getCustomer().getId();
	Customer customer=custmanager.getCustomerById(custid);
	//long areaid=alertmonitor.getAreaCode().getId();
//	areacode=alertsmanager.getareaCodeById(areaid);
	alertmonitor.setCustomer(customer);
//	alertmonitor.setAreaCode(areacode);
	String valuexml=stream.toXML(alertmonitor);
	return valuexml;
}

public String logincheckforAlexa(String id,String userid,String password){
	LogUtil.info("comming to check");
	String result=null;
	CustomerManager customerManager=new CustomerManager();
	String hashPassword = IMonitorUtil.getHashPassword(password);
	LogUtil.info("password=="+hashPassword);
	boolean user=customerManager.loginpasscheckapi(userid,hashPassword,id);
	LogUtil.info("customer boolean result"+user);
	
	if(user){
		result="True";
	}else{
		result="False";}
		return result;	
}

/*public String logincheckforAlexaSuperUser(String id,String password){
	LogUtil.info("comming to check super user");
	String result=null;
	CustomerManager customerManager=new CustomerManager();
	String hashPassword = IMonitorUtil.getHashPassword(password);
	LogUtil.info("password=="+hashPassword);
	boolean user=customerManager.loginsuperUserpasscheckapi(hashPassword,id);
	LogUtil.info("customer boolean result"+user);
	
	if(user){
		result="True";
	}else{
		result="False";}
		return result;	
}*/

public String logincheckforAlexaVideo(String id,String userid,String password){
	XStream stream=new XStream();
	//LogUtil.info("comming to check logincheckforAlexaVideo");
	String result=null;
	CustomerManager customerManager=new CustomerManager();
	String hashPassword = IMonitorUtil.getHashPassword(password);
	//LogUtil.info("password=="+hashPassword);
	boolean user=customerManager.loginpasscheckapi(userid,hashPassword,id);
	//LogUtil.info("user result="+user);
	
	if(user){
		result="True";
	}else{
		result="False";}
	
	LogUtil.info("logincheckforAlexaVideo result"+stream.toXML(result));
		return result;	
}


public String Discoverdevicealexa(String customerId,String tokenXml){
	
	LogUtil.info(" alexa user device discover=="+"customerId=="+customerId+"tokenXml=="+tokenXml);
	
	XStream stream=new XStream();
	String result=null;
	DeviceManager devmanager=new DeviceManager();
	Alexa alexa = devmanager.getdetailsforalexanormaluser(customerId,tokenXml);
	
	LogUtil.info(alexa.getUser().getRole().getName());
	
	if(alexa.getUser().getRole().getName().equals(Constants.NORMAL_USER)){
		
		List<Object[]> objects=devmanager.getdevicedetailsforalexaforsubuser(customerId,alexa.getUser());
		result=stream.toXML(objects);
		
	}else{
		List<Object[]> objects=devmanager.getdevicedetailsforalexa(customerId);
		result=stream.toXML(objects);
	}
	LogUtil.info(" alexa user device discover result=="+result);
	return result;
} 




public String Discoverdevicegoogle(String customerId){
	
	XStream stream=new XStream();
	String result=null;
	DeviceManager devmanager=new DeviceManager();
	List<Object[]> objects=devmanager.getdevicedetailsforgoogle(customerId);
	result=stream.toXML(objects);
	
	return result;
}


//Apoorva(Devices)
public String getGateWay(String customerId){
	
	XStream xStream=new XStream();
	long id = (Long)xStream.fromXML(customerId);
	CustomerManager customerManager=new CustomerManager();
	long gatewayNumber = customerManager.getGateWay(id);
	
	String valueInXml = xStream.toXML(gatewayNumber);
	
	return valueInXml;
}

	public String getDevices(String gatewayIdXml){
		
		XStream xStream=new XStream();
		long id = (Long)xStream.fromXML(gatewayIdXml);
		CustomerManager customerManager=new CustomerManager();
		List<Object[]> ObjectsfromDb = customerManager.listAllDevices(id);
		
		List<Device> deviceList = new ArrayList<Device>();
		if(ObjectsfromDb != null)
		{
		
			for(Object[] devicefrmdb:ObjectsfromDb){
				Device device=new Device();
				DeviceType deviceType =new DeviceType();
				device.setId(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[0])));
				device.setDeviceId(((IMonitorUtil.convertToString(devicefrmdb[1]))));
				device.setGateWay(null);
				device.setGeneratedDeviceId(((IMonitorUtil.convertToString(devicefrmdb[3]))));
				device.setFriendlyName(((IMonitorUtil.convertToString(devicefrmdb[4]))));
				device.setBatteryStatus(((IMonitorUtil.convertToString(devicefrmdb[5]))));
				device.setModelNumber(((IMonitorUtil.convertToString(devicefrmdb[6]))));
				device.setDeviceType(null);
				device.setCommandParam(((IMonitorUtil.convertToString(devicefrmdb[8]))));
				device.setLastAlert(null);
				device.setArmStatus(null);
				device.setMake(null);
				device.setLocation(null);
				device.setDeviceConfiguration(null);
				device.setEnableStatus(((IMonitorUtil.convertToString(devicefrmdb[14]))));
				device.setIcon(null);
				device.setMode(((IMonitorUtil.convertToString(devicefrmdb[16]))));
				device.setEnableList(((IMonitorUtil.convertToString(devicefrmdb[17]))));
				device.setPosIdx(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[18])));
				device.setLocationMap(null);
				device.setSwitchType(Long.parseLong(IMonitorUtil.convertToString(devicefrmdb[20])));
				device.setDevicetimeout(((IMonitorUtil.convertToString(devicefrmdb[21]))));
				device.setPulseTimeOut(((IMonitorUtil.convertToString(devicefrmdb[22]))));
				deviceType.setName(((IMonitorUtil.convertToString(devicefrmdb[23]))));
				device.setDeviceType(deviceType);
				deviceList.add(device);
			}
		}
		String valueInXml = xStream.toXML(deviceList);
		return valueInXml;
	}
	
	
	//Registration for zing gateway
	public String saveCustomerForZing(String customerXml,String gatewayXml,String userXml)
	{
		
		String result="";
		XStream stream=new XStream();
		Customer customer=(Customer) stream.fromXML(customerXml);
		GateWay gateWay=(GateWay) stream.fromXML(gatewayXml);
		GatewayManager gatewayManager=new GatewayManager();
		
		//Verify gateway macId in the saved list of CMS
		
		boolean macIdExist = gatewayManager.verifyGetwayMacIdFromSavedList(gateWay.getMacId());
		LogUtil.info("boolean result: " + macIdExist);
		if(!macIdExist) {
			result = "GatewayFailure";
			return result;
		}
		
		//End
		
		User user=(User) stream.fromXML(userXml);
		CustomerManager customerManager=new CustomerManager();
		LocationManager locationManager=new LocationManager();
		DeviceManager devicemanager=new DeviceManager();
		
		//Verify Customer Existence
		boolean customerResult=customerManager.checkCustomerExistence(customer.getCustomerId());
		if (customerResult) 
		{
			
			result="failure";
			return result;
		}
		else 
		{
			//Save Customer
			
			customer.setRenewalDuration(12);
			customer.setPaidStorageMB(0);
			customer.setFreeStorageMB(0);
			long featuresEnabled =  0;
			customer.setFeaturesEnabled(featuresEnabled);
			StatusManager statusManager = new StatusManager();
			Status status = statusManager.getStatusByName("Running");
			customer.setStatus(status);
			boolean afterSave=customerManager.saveCustomer(customer);
			
			if (afterSave) 
			{
				//Save Gateway after saving Customer 
				Customer customerAfterSave= customerManager.getCustomerByCustomerId(customer.getCustomerId());
				gateWay.setCustomer(customerAfterSave);
				gateWay.setDelayHome("0");
				gateWay.setDelayStay("0");
				gateWay.setDelayAway("0");
				gateWay.setCurrentMode("0");
				gateWay.setMaintenancemode("0");
				Date date=new Date();
				gateWay.setEntryDate(date);
			
				status = statusManager.getStatusByName(Constants.READY_TO_REGISTER);
				gateWay.setStatus(status);
				
				AgentManager agentManager = new AgentManager();
				Agent agent = agentManager.getAgentForGateway();
				gateWay.setAgent(agent);
				//LogUtil.info("Final Gateway is "+stream.toXML(gateWay));
				
				
				GateWay gateWay1 = gatewayManager.getGateWayByMacId(gateWay.getMacId());
				//Verify Gateway exist
				if (gateWay1 == null)
				{
					//New Gateway Save block
					boolean saveGateway = gatewayManager.saveGateWay(gateWay);
				
					result="Gateway is saved";
					
					//Saving default location
					Location location = new Location();
					location.setName("Default Room");
					location.setDetails("Default Room");
					location.setCustomer(customerAfterSave);
					boolean locationSave=locationManager.saveLocation(location);
					
					//DIY feature add pre defined rooms start
					Location location1 = new Location();
					location1.setName("Living Room");
					location1.setDetails("Living Room");
					location1.setCustomer(customerAfterSave);
					locationManager.saveLocation(location1);
					
					Location location2 = new Location();
					location2.setName("Bedroom");
					location2.setDetails("Bedroom");
					location2.setCustomer(customerAfterSave);
					locationManager.saveLocation(location2);
					
					Location location3 = new Location();
					location3.setName("Kitchen");
					location3.setDetails("Kitchen");
					location3.setCustomer(customerAfterSave);
					locationManager.saveLocation(location3);
					LogUtil.info("DIY feature add pre defined rooms end");
					//DIY feature add pre defined rooms end
					
					
					//GetSavedGateway
				//	GateWay gatewayForSystemDevice = gatewayManager.getGateWayByMacId(gateWay.getMacId());
				//	Boolean createDeviceResult = gatewayManager.createSystemDevices(gatewayForSystemDevice);
				//	LogUtil.info("Create System Device Result----->"+createDeviceResult);
					
					//Save User
					user.setEmail(customerAfterSave.getEmail());
					user.setMobile(customerAfterSave.getMobile());
					user.setShowTips(1);
					user.setCustomer(customerAfterSave);
					status = statusManager.getStatusByName(Constants.ONLINE);
					user.setStatus(status);
					//Pick a role
					RoleManager roleManager = new RoleManager();
					Role role = roleManager.getRoleByRoleName(Constants.MAIN_USER);
					user.setRole(role);
					UserManager userManager=new UserManager();
					boolean userSaveResult=userManager.saveUser(user);
					
					
					//Create Ftp folders
					
					String customerId = customerAfterSave.getCustomerId();
					String imagesDir = customerId+ "/"+ ControllerProperties.getProperties().get(Constants.IMAGES_DIR);
					String streamsDir = customerId+ "/"+ ControllerProperties.getProperties().get(Constants.STREAMS_DIR);
					String logsDir = customerId+ "/"+ ControllerProperties.getProperties().get(Constants.LOG_DIR);
					String locationsDir = imagesDir+ "/"+ ControllerProperties.getProperties().get(Constants.UPLOADED_LOC_IMAGES_DIR);
						
					try 
					{
						
						FTPUtil.createFolders(agent.getFtpIp(), agent.getFtpPort(),
								agent.getFtpUserName(), agent.getFtpPassword(),
								customerId, imagesDir, streamsDir,logsDir,locationsDir);
					} catch (JSchException e)
					{
						e.printStackTrace();
					} catch (SftpException e) {
						e.printStackTrace();
					}
					
					
					try 
					{
						//Sms notification
						SmsNotifications notifications = new SmsNotifications();
						String[] recipiantsBYsms={customerAfterSave.getMobile().toString()};
						LogUtil.info("recipiantsBYsms "+ stream.toXML(recipiantsBYsms));
						notifications.notify("Dear "+ customerAfterSave.getFirstName()+", Congratulations !! Your iMonitor smarthome account with customerId '"+customerAfterSave.getCustomerId()+
								"' has been created successfully.",recipiantsBYsms, null,user.getCustomer());
						
						 //Email notification
						 EMailNotifications eMailNotifications = new EMailNotifications();
						 String[] recipiantsBYmail= {customerAfterSave.getEmail().toString()};
						 LogUtil.info("recipiantsBYmail "+stream.toXML(recipiantsBYmail));
						 String message = "Dear "+ customerAfterSave.getFirstName() + "<br><br>" +
						                  " Congratulations !! Your iMonitor smarthome account with customerId '" + customerAfterSave.getCustomerId() +"' has been created successfully. <br><br>" +
								          "Regards,<br> iMonitor Smarthome";
						 eMailNotifications.notifyRegistrationSuccessToCustomer(message,recipiantsBYmail, null, customerAfterSave.getCustomerId().toString());	
					} catch (Exception e) 
					{
						e.printStackTrace();
						LogUtil.info("Email/Phone message sending error : " + e.getMessage());
						result="success";
					}
					
					
				}
				else
				{
					//Duplicate gateway exit
					boolean customerDelete=customerManager.deleteCustomer(customerAfterSave);
					
					result = "GatewayExists";
					
					return result;
				}
			}
			result="success";
			return result;
		}
	}
	

	public String getCustomerIdByDeviceId(String xml) {
		XStream stream = new XStream();
		String imvgId = (String)stream.fromXML(xml);
	//	String[] data = deviceId.split("-");
		CustomerManager customerManager = new CustomerManager();
	//	String customerId = customerManager.getCustomerIdByMacId(data[0]);
		Customer customer = customerManager.getCustomerByMacId(imvgId);
		return customer.getCustomerId();
	}

	//3 gateways proj
	public String listGateWaysAndDevicesForMultipleGateway(String xml){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		CustomerManager customerManager = new CustomerManager();
		List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfMultipleGateway(customer);
		String gateWaysXml = xStream.toXML(gateWays);
		return gateWaysXml;
	}
	
	//3 gateways proj
	public String listGateWaysAndNonHmdDevicesForMultipleGateway(String xml){
		XStream xStream=new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		CustomerManager customerManager = new CustomerManager();
		List<GateWay> gateWays = customerManager.getGateWaysAndDeviceOfMultipleGateway(customer);
		String gateWaysXml = xStream.toXML(gateWays);
		return gateWaysXml;
	}
	public String retrieveCustomerDeviceAlertAndActionsAndNoticiationProfiles(String xml) {
		XStream xStream = new XStream();
		String customerId = (String) xStream.fromXML(xml);
		CustomerManager customerManager = new CustomerManager();
		Customer customer = customerManager.retrieveCustomerDeviceAlertAndActionsAndNoticiationProfiles(customerId);
		String valueInXml = xStream.toXML(customer);
		return valueInXml;
	}
	public String saveCustomerDetailsFromMid(String customerXml,String userXml)
	{
		
		String result="";
		XStream stream=new XStream();
		Customer customer=(Customer) stream.fromXML(customerXml);
	
		
		
		
		
		User user=(User) stream.fromXML(userXml);
		CustomerManager customerManager=new CustomerManager();
		LocationManager locationManager=new LocationManager();
		DeviceManager devicemanager=new DeviceManager();
		
	
		
		//Verify Customer Existence
		boolean customerResult=customerManager.checkCustomerExistence(customer.getCustomerId());
		if (customerResult) 
		{
			
			result="failure";
			return result;
		}
		else 
		{
			//Save Customer
			
			customer.setRenewalDuration(12);
			customer.setPaidStorageMB(0);
			customer.setFreeStorageMB(0);
			long featuresEnabled =  0;
			customer.setFeaturesEnabled(featuresEnabled);
			StatusManager statusManager = new StatusManager();
			Status status = statusManager.getStatusByName("Running");
			customer.setStatus(status);
			boolean afterSave=customerManager.saveCustomer(customer);
			
			if (afterSave) 
			{
				//Save Gateway after saving Customer 
				Customer customerAfterSave= customerManager.getCustomerByCustomerId(customer.getCustomerId());
								
				
							
					//DIY feature add pre defined rooms start
					Location location1 = new Location();
					location1.setName("Living Room");
					location1.setDetails("Living Room");
					location1.setCustomer(customerAfterSave);
					locationManager.saveLocation(location1);
					
					Location location2 = new Location();
					location2.setName("Bedroom");
					location2.setDetails("Bedroom");
					location2.setCustomer(customerAfterSave);
					locationManager.saveLocation(location2);
					
					Location location3 = new Location();
					location3.setName("Kitchen");
					location3.setDetails("Kitchen");
					location3.setCustomer(customerAfterSave);
					locationManager.saveLocation(location3);
					LogUtil.info("DIY feature add pre defined rooms end");
					//DIY feature add pre defined rooms end
					
					
				
					
					//Save User
					user.setEmail(customerAfterSave.getEmail());
					user.setMobile(customerAfterSave.getMobile());
					
					user.setCustomer(customerAfterSave);
					status = statusManager.getStatusByName(Constants.ONLINE);
					user.setStatus(status);
					//Pick a role
					RoleManager roleManager = new RoleManager();
					Role role = roleManager.getRoleByRoleName(Constants.MAIN_USER);
					user.setRole(role);
					UserManager userManager=new UserManager();
					boolean userSaveResult=userManager.saveUser(user);
					
					
					//Create Ftp folders
					
				/*	String customerId = customerAfterSave.getCustomerId();
					String imagesDir = customerId+ "/"+ ControllerProperties.getProperties().get(Constants.IMAGES_DIR);
					String streamsDir = customerId+ "/"+ ControllerProperties.getProperties().get(Constants.STREAMS_DIR);
					String logsDir = customerId+ "/"+ ControllerProperties.getProperties().get(Constants.LOG_DIR);
					String locationsDir = imagesDir+ "/"+ ControllerProperties.getProperties().get(Constants.UPLOADED_LOC_IMAGES_DIR);
						
					try 
					{
						
						FTPUtil.createFolders(agent.getFtpIp(), agent.getFtpPort(),
								agent.getFtpUserName(), agent.getFtpPassword(),
								customerId, imagesDir, streamsDir,logsDir,locationsDir);
					} catch (JSchException e)
					{
						e.printStackTrace();
					} catch (SftpException e) {
						e.printStackTrace();
					}*/
					
					
					
					
					
				
				
				
				
			}
		
			result="success";
			return result;
		}
	}
	
}



