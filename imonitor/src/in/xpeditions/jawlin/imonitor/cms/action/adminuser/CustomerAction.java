/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerServices;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AdminSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemIntegrator;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorProperties;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class CustomerAction extends ActionSupport {

	/**
	 * Asmodeus
	 */
	private static final long serialVersionUID = 1L;
	private Customer customer = new Customer();
	private List<Status> statuses;
	private List<GateWay> gateWaysList = new ArrayList<GateWay>();
	private String selectedValue;
	private DataTable dataTable;
	private String[] gateWayMacIds;
	private String dateFormat;
	private boolean healthcheck;
	private boolean preset;
	private boolean Dashboard;
	private User user;
	private List<SystemIntegrator> systemIntegrators; 
	private String type;
	private List<DeviceDetails> ListOfDevicewithPowerValues;
    private String backupfile;
    private boolean sms;
    private boolean email;
    private boolean videoStreaming;
    private boolean WhatsApp;
    private boolean support;
    private boolean customeremail;
    private boolean otherMail;
    private String othermail;
    private CustomerServices customerServices;
    private CustomerType customerType;
    
    private List<CustomerType> customerTypes;
    
    
    //3gp 
    private GateWay gateWay1;
    private GateWay gateWay2;
    private GateWay gateWay3;
    
    //RegisterZIngGateway
    private GateWay gateWay;
    //SuperUser
    private String mappingResult;
    
	@SuppressWarnings("unchecked")
	public String toAddCustomer() throws Exception 
	{
		
		String result ="";
		customer = null;
		if(gateWayMacIds !=null){
			XStream stream = new XStream();
			String valueInXml = stream.toXML(gateWayMacIds[0]);
			String serviceName = "customerService";
			String method = "getCustomerByMacId";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, valueInXml);
			customer = (Customer) stream.fromXML(resultXml);
		}
			if(customer ==null){
				result ="addcustomer";
			}else{
				long value = this.customer.getFeaturesEnabled();
				
				if((value & 1)==1)
				{
					healthcheck=true;
				}
				if((value & 2) == 2)
				{
					preset =true;
				}
				if((value & 4) == 4)
				{
					Dashboard =true;
				}
				
				result ="editcustomer";
			}
		String serviceName = "statusService";
		String method = "listStatuses";
		String valueInXml = IMonitorUtil.sendToController(serviceName, method);
		XStream stream = new XStream();
		this.statuses = (List<Status>) stream.fromXML(valueInXml);
		this.dateFormat = IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER_DISPLAY);
		
		
		 
		 
		//Get the List of System Integrators
		serviceName = "systemIntegratorService";
		method = "getListOfSystemIntegrators";
		String xmlIntegrators = IMonitorUtil.sendToController(serviceName, method);
		
		this.systemIntegrators = (List<SystemIntegrator>) stream.fromXML(xmlIntegrators);
		return result;
	}


	//to change password of customer by admin.
	public String changePasswordOfCustomer() throws Exception {
	
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String addCustomer() throws Exception {
		try {
			XStream stream = new XStream();
			long featuresEnabled =  0;
			Set<GateWay> gateWays = this.customer.getGateWays();
			if (null == gateWays) {
				gateWays = new HashSet<GateWay>();
				this.customer.setGateWays(gateWays);
			}
			for (int i = 0; i < gateWayMacIds.length; i++) {
				GateWay gateWay = new GateWay();
				gateWay.setMacId(gateWayMacIds[i]);
				gateWays.add(gateWay);
			}
			String serviceName = "customerService";
			String method = "getCustomerByCustomerId";
			String valueInXml = stream.toXML(customer.getCustomerId());
			String xml = IMonitorUtil.sendToController(serviceName,method, valueInXml);
			Customer newCustomer = (Customer) stream.fromXML(xml);
			if(newCustomer !=null){
				ActionContext.getContext().getSession().put("message","Failure~The following  customerId "+customer.getCustomerId()+" already used ");
				return ERROR;
			}
			
			
			if(this.isHealthcheck())
			{
				featuresEnabled |= 1;
			}
			if(this.isPreset())
			{
				featuresEnabled |= 2;
			}
			if(this.isDashboard())
			{
				featuresEnabled |= 4;	
			}
			
			this.customer.setFeaturesEnabled(featuresEnabled);
			
			if(this.customer.getSystemIntegrator().getId()==0)
			this.customer.setSystemIntegrator(null);
			
			//is valid gateways
			String xmlString = stream.toXML(this.customer);
			
			serviceName = "gateWayService";
			method = "iSValidMacId";
			String gateWayXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
			this.gateWaysList = (List<GateWay>) stream.fromXML(gateWayXml);
			if(gateWaysList.size() > 0){
				String inValidMacId ="";
				for(GateWay way:gateWaysList){
					inValidMacId+=way.getMacId()+",";
					ActionContext.getContext().getSession().put("message","Failure~The following  "+inValidMacId+" macids Invalid ");
					
				}
					return ERROR;
			}
			// Saving the customer
			serviceName = "customerService";
			method = "saveCustomerAndAssignDefaultLocation";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
			this.gateWaysList = (List<GateWay>) stream.fromXML(resultXml);
			if(gateWaysList.size() ==0){
					ActionContext.getContext().getSession().put("message",
							"Sucess~Customer with id " + this.customer.getCustomerId() + " is saved successfully!");
					return SUCCESS;
				} else{
					String gateWayMacIds ="";
					for(GateWay way:gateWaysList){
						gateWayMacIds+=way.getMacId()+" ";
					}
					ActionContext.getContext().getSession().put("message","Failure~The following  "+gateWayMacIds+" macids already used ");
					return ERROR;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String saveCustomer() throws Exception {
		try {
			
			XStream stream = new XStream();
			long featuresEnabled =  0;
			Set<GateWay> gateWays = this.customer.getGateWays();
			if (null == gateWays) {
				gateWays = new HashSet<GateWay>();
				this.customer.setGateWays(gateWays);
			}
			for (int i = 0; i < gateWayMacIds.length; i++) {
				GateWay gateWay = new GateWay();
				gateWay.setMacId(gateWayMacIds[i]);
				gateWays.add(gateWay);
			}
			
			
			
			if(this.isHealthcheck())
			{
				featuresEnabled |= 1;
			}
			if(this.isPreset())
			{
				featuresEnabled |= 2;
			}
			if(this.isDashboard())
			{
				featuresEnabled |= 4;
			}
			this.customer.setFeaturesEnabled(featuresEnabled);
			
			
			if(this.customer.getSystemIntegrator().getId()==0)
				this.customer.setSystemIntegrator(null);
			
			// Saving the customer
			String xmlString = stream.toXML(this.customer);
			
			String serviceName = "customerService";
			String method = "saveCustomerAndAssignDefaultLocationByGateWay";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
			String result= (String) stream.fromXML(resultXml);
			if(result.equals("success")){
				ActionContext.getContext().getSession().put("message","Sucess~Customer with id " + this.customer.getCustomerId() + " is saved successfully!");
					return SUCCESS;
				} else{
					ActionContext.getContext().getSession().put("message",result);
					return ERROR;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getCustomerByCustomerId() throws Exception {
		XStream stream = new XStream();
		String valueInXml = stream.toXML(selectedValue);
		String serviceName = "customerService";
		String method = "getCustomerByCustomerId";
		String xml = IMonitorUtil.sendToController(serviceName,
				method, valueInXml);
		customer = (Customer) stream.fromXML(xml);
		if (customer == null) {
			ActionContext.getContext().getSession().put("message",
					"No customer by the specified customerID");
		}
		return SUCCESS;
	}

	public String listCustomers() throws Exception {
		return SUCCESS;
	}

	public String listAskedCustomers() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "customerService";
		String method = "listAskedCustomers";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String toEditCustomer() throws Exception {
		long id = customer.getId();
		long value ;
		XStream stream = new XStream();
		String valueInXml = stream.toXML(id);
		String serviceName = "customerService";
		String method = "getCustomerById";
		String xml = IMonitorUtil.sendToController(serviceName,
				method, valueInXml);
		this.customer = (Customer) stream.fromXML(xml);
		serviceName = "statusService";
		method = "listStatuses";
		valueInXml = IMonitorUtil.sendToController(serviceName, method);
		value = this.customer.getFeaturesEnabled();
		
		
		if((value & 1)==1)
		{
			healthcheck=true;
		}
		if((value & 2) == 2)
		{
			preset =true;
		}
		if((value & 4) == 4)
		{
			Dashboard =true;
		}
		this.statuses = (List<Status>) stream.fromXML(valueInXml);
		this.dateFormat = IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER_DISPLAY);
		
		//Get the List of System Integrators
		serviceName = "systemIntegratorService";
		method = "getListOfSystemIntegrators";
		String xmlIntegrators = IMonitorUtil.sendToController(serviceName, method);
		this.systemIntegrators = (List<SystemIntegrator>) stream.fromXML(xmlIntegrators);
		return SUCCESS;
	}
	
	
	/***
	 * Kantharaj is changed to retrieve main-user for correcting bug in Forgot Password 
	 * ***/
	@SuppressWarnings("unchecked")
	public String editCustomerByCustomer() throws Exception {
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		long id = user.getId();
		XStream stream = new XStream();
		String valueInXml = stream.toXML(id);
		String serviceName = "userService";
		String method = "getUserById";
		String xml = IMonitorUtil.sendToController(serviceName,
				method, valueInXml);
		//this.customer = (Customer) stream.fromXML(xml);
		this.user=(User) stream.fromXML(xml);
		serviceName = "statusService";
		method = "listStatuses";
		valueInXml = IMonitorUtil.sendToController(serviceName, method);
		
		//3gp
		String  serviceName1 = "gateWayService";
		String  method1 = "getMultipleGateways";
		long cid = this.user.getCustomer().getId();
		String customerId=Long.toString(cid);
		String customerXml = stream.toXML(customerId);
		String gatewaysXml = IMonitorUtil.sendToController(serviceName1, method1,customerXml);
		this.gateWaysList = (List<GateWay>) stream.fromXML(gatewaysXml);
		this.statuses = (List<Status>) stream.fromXML(valueInXml);
		
		//SuperUser (To check mapping of SuperUser To Customer)
		String  serviceName2 = "superUserService";
		String  method2 = "checkMappingOfSuperUserToCustomer";
		String result = IMonitorUtil.sendToController(serviceName2, method2,customerXml);
		if (result.equals("NotPresent")) 
		{
			this.mappingResult="NotPresent";
		}
		else
		{
			this.mappingResult="present";
		}
		
		return SUCCESS;
	}

	public String updatCustomer() throws Exception {
		XStream stream = new XStream();
		String message="";
		
		
		/*User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		long id = user.getCustomer().getId();
		String valueInXml = stream.toXML(id);
		String serviceName = "customerService";
		String method = "getCustomerById";
		String xml = IMonitorUtil.sendToController(serviceName,method, valueInXml);
		Customer  onlineCustomer = (Customer) stream.fromXML(xml);
			onlineCustomer.setFirstName(customer.getFirstName());
			onlineCustomer.setMiddleName(customer.getMiddleName());
			onlineCustomer.setLastName(customer.getLastName());
			onlineCustomer.setPlace(customer.getPlace());
			onlineCustomer.setPincode(customer.getPincode());
			onlineCustomer.setPost(customer.getPost());
			onlineCustomer.setCity(customer.getCity());
			onlineCustomer.setProvince(customer.getProvince());
			onlineCustomer.setPhoneNumber(customer.getPhoneNumber());
			onlineCustomer.setEmail(customer.getEmail());
			onlineCustomer.setMobile(customer.getMobile());
		 serviceName = "customerService";
		 method = "updateCustomer";
		String xmlString = stream.toXML(onlineCustomer);
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.gateWaysList = (List<GateWay>) stream.fromXML(resultXml);
		if(gateWaysList.size() ==0){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.Myprofile.0001");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message","Sucess~Successfully saved");
				return SUCCESS;
			} else{
				String gateWayMacIds ="";
				for(GateWay way:gateWaysList){
					gateWayMacIds+=way.getMacId()+" ";
				}
				ActionContext.getContext().getSession().put("message","Failure~The following  "+gateWayMacIds+" macids already used ");
				return ERROR;
			}*/
		/**
		 * kAntharaj commented save only updated user mobile and e-mail information.*/
		
		String serviceName = "userService";
		String method = "updateUserMailandMobile";
		
		String xmlString = stream.toXML(this.user);
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		
		if(resultXml.equals("yes"))
		{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.Myprofile.0001");
			ActionContext.getContext().getSession().put("message", message);
			
		}
		else
		{
			ActionContext.getContext().getSession().put("message","Failure~Unable To save Provided information");
		}
		
		
	    return SUCCESS;
		
	}

	
	@SuppressWarnings("unchecked")
	public String editCustomer() throws Exception {
		XStream stream = new XStream();
		long featuresEnabled =  0;
		
		Set<GateWay> gateWays = this.customer.getGateWays();
		if (null == gateWays) {
			gateWays = new HashSet<GateWay>();
			this.customer.setGateWays(gateWays);
		}
		if (gateWayMacIds != null) {
			for (int i = 0; i < gateWayMacIds.length; i++) {
				GateWay gateWay = new GateWay();
				gateWay.setMacId(gateWayMacIds[i]);
				gateWays.add(gateWay);
			}
		}
		//bhavya start
		if(this.isHealthcheck())
		{
			featuresEnabled |= 1;
		}
		if(this.isPreset())
		{
			featuresEnabled |= 2;
		}
		if(this.isDashboard())
		{
			featuresEnabled |= 4;
		}
		this.customer.setFeaturesEnabled(featuresEnabled);
		
		if(this.customer.getSystemIntegrator().getId()==0)
			this.customer.setSystemIntegrator(null);
		
		// Saving the customer
		String xmlString = stream.toXML(this.customer);
		
		
		String serviceName = "gateWayService";
		
		
		String method = "iSValidMacId";
		String gateWayXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.gateWaysList = (List<GateWay>) stream.fromXML(gateWayXml);
		if(gateWaysList.size() > 0){
			String inValidMacId ="";
			for(GateWay way:gateWaysList){
				inValidMacId+=way.getMacId()+",";
				ActionContext.getContext().getSession().put("message","Failure~The following  "+inValidMacId+" macids Invalid ");
				
			}
				return ERROR;
		}
		 serviceName = "customerService";
		 method = "updateCustomer";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		if(resultXml.equalsIgnoreCase("Failure"))
		{
			ActionContext.getContext().getSession().put("message",
					"Failure~Customer with id " + this.customer.getCustomerId() + " is Already Used. Please choose any other Customer Id!");
			return ERROR;
		}
		else
		{
		this.gateWaysList = (List<GateWay>) stream.fromXML(resultXml);
		if(gateWaysList.size() ==0){
				ActionContext.getContext().getSession().put("message",
						"Sucess~Customer with id " + this.customer.getCustomerId() + " is saved successfully!");
				return SUCCESS;
			} else{
				String gateWayMacIds ="";
				for(GateWay way:gateWaysList){
					gateWayMacIds+=way.getMacId()+" ";
				}
				ActionContext.getContext().getSession().put("message","Failure~The following  "+gateWayMacIds+" macids already used ");
				return ERROR;
			}
		}
	}

	public String deleteCustomer() throws Exception {
		
		long id = customer.getId();
		XStream stream = new XStream();
		String xmlString = stream.toXML(id);
		LogUtil.info("customer action class xmlString"+xmlString);
		String serviceName = "customerService";
		String method = "deleteCustomer";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	}
	
	
	public String GetHourlyDatafromDB() throws Exception
	{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String customerXml = stream.toXML(customer);
		String SelectedTypexmlString = stream.toXML(this.type);
		
		
		
		String serviceName = "DashboardService";
		String method = "getHourlyDataFromDB";
		String result = IMonitorUtil.sendToController(serviceName, method, customerXml,SelectedTypexmlString);
		this.setListOfDevicewithPowerValues((List<DeviceDetails>) stream.fromXML(result));
		return SUCCESS;
		
	}
	
 public String toEnableCustomerServices()throws Exception{
	   
	   long id = customer.getId();
		int value ;
		int value1;
		XStream stream = new XStream();
		String valueInXml = stream.toXML(id);
		String serviceName = "customerService";
		String method = "getCustomerServicesById";
		
		String xml = IMonitorUtil.sendToController(serviceName,	method, valueInXml);
		
		
		this.customerServices = (CustomerServices) stream.fromXML(xml);


		
		value = this.customerServices.getServiceEnabled();
		
		if((value & 1)==1)
		{
			setSms(true);
		}
		if((value & 2) == 2)
		{
			setEmail(true);
		}
		if((value & 4) == 4)
		{
			setVideoStreaming(true);
		}
		
		if ((value & 8) == 8)	
		{
			setWhatsApp(true);
		}
		
		value1=this.customerServices.getIntimation();
		
		if((value1 & 1)==1)
		{
			setSupport(true);
		}
		if((value1 & 2) == 2)
		{
			setCustomeremail(true);
		}
		if((value1 & 4) == 4)
		{
			setOtherMail(true);
		}

		return SUCCESS;
	   
	   
	
   }
   
   

   
   
 public String EnableCustomerServices()throws Exception{
	 
	 XStream stream = new XStream();
	 
	 long serviceEnabled =  0;
	 int intimation=0;
	 long id = this.customer.getId();
	 	
	 if(this.isSms())
		{
		 serviceEnabled |= 1;
		}
		if(this.isEmail())
		{
			serviceEnabled |= 2;
		}
		if(this.isVideoStreaming())
		{
			serviceEnabled |= 4;
		}
		if(this.isWhatsApp())
		{
			serviceEnabled |= 8;
		}
		 	 
		
		 
		if(this.isSupport())
		{
		 intimation |= 1;
		}
		if(this.isCustomeremail())
		{
			intimation |= 2;
		}
		if(this.isOtherMail())
		{
			intimation |= 4;
		}
		this.customerServices.setIntimation((int)intimation);
		
		this.customerServices.setServiceEnabled((int)serviceEnabled);

		this.customerServices.setCustomer(this.customer);
		
				
		String xmlString = stream.toXML(this.customerServices);
		String serviceName = "customerService";
		String method = "updateServices";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		String result=(String) stream.fromXML(resultXml);

		
		if(result.equalsIgnoreCase("Failure"))
		
			
		{
			ActionContext.getContext().getSession().put("message",
					"Failure~Service disable " + this.customer.getCustomerId() + " could not update the services");
			
			return ERROR;
			
		}else{
			
			ActionContext.getContext().getSession().put("message",
					"success~Service disable" + this.customer.getCustomerId() + " updated successfully");
			
			return SUCCESS;
		}
			
		
		
	 
   }
 
public String ToRenewCustomerService() throws Exception{
	 
	 XStream stream = new XStream();
	 long id = customer.getId();
	 String valueInXml = stream.toXML(id);
	 String serviceName = "customerService";
	 String method = "getCustomerById";
	 String xml = IMonitorUtil.sendToController(serviceName,method, valueInXml);
	 this.customer = (Customer) stream.fromXML(xml);
	 return SUCCESS;
	 
 }

@SuppressWarnings("deprecation")
public String RenewalCustomerServices()throws Exception{
	 
	 XStream stream = new XStream();
	 int value=0;
	 
	 if(this.isSupport())
		{
		 value |= 1;
		}
		if(this.isCustomeremail())
		{
			value |= 2;
		}
		if(this.isOtherMail())
		{
			value |= 4;
		}
		
		Date renewaldate = this.customer.getRenewalDate();
		Calendar cal=Calendar.getInstance();
		cal.setTime(renewaldate);
		cal.add(Calendar.MONTH,this.customer.getRenewalDuration());
		
		String dateOfExpiry=cal.getTime().toString();
		this.customer.setDateOfExpiry(cal.getTime());
		this.customer.setIntimationCount(0);
		String intimationXml = stream.toXML(value);
		String otherMAilXml=stream.toXML(this.getOthermail());
		String valueInXml = stream.toXML(this.customer);
		String serviceName = "customerService";
		String method = "RenewalServices";
		String xml = IMonitorUtil.sendToController(serviceName,
					method,valueInXml,intimationXml,otherMAilXml);
		 String result = (String) stream.fromXML(xml);
		 
				if(result.equalsIgnoreCase("Failure"))
				
					
				{
					ActionContext.getContext().getSession().put("message",
							"Failure~Renewal service " + this.customer.getCustomerId() + " could not update ");
					
					return ERROR;
					
				}else{
					
					ActionContext.getContext().getSession().put("message",
							"success~Renewal service " + this.customer.getCustomerId() + " updated successfully");
					
					return SUCCESS;
				}
					
				
					
					
		 
}

	public String toAddEnergydeviceDetails() throws Exception{
		
		XStream stream = new XStream();
		String result = IMonitorUtil.sendToController("", "");
		
		return SUCCESS;
	}
	
	public String GetComparativeData() throws Exception
	{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String customerXml = stream.toXML(customer);
				
		String serviceName = "DashboardService";
		String method = "getComparativeEnergyData";
		String result = IMonitorUtil.sendToController(serviceName, method, customerXml);
		this.setListOfDevicewithPowerValues((List<DeviceDetails>) stream.fromXML(result));
		return SUCCESS;
		
	}
	
	//To Zing gateway customer
	public String toZingGatewayCustomer() throws Exception
	{
		
		return SUCCESS;
	}
	
	
	
	
	//Save Zing Gateway Customer (Apoorva)
	
	public String saveCustomerForZing() throws Exception
	{
		XStream stream = new XStream();
		String customerXml = stream.toXML(this.customer);
		String gateWayXml=stream.toXML(this.gateWay);
		
		String hashPassword = IMonitorUtil.getHashPassword(this.user.getPassword());
		this.user.setPassword(hashPassword);
		String userXml=stream.toXML(this.user);
		String serviceName = "customerService";
		String method = "saveCustomerForZing";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, customerXml,gateWayXml,userXml);
	  
		if (resultXml.equals("failure")) 
		{
			ActionContext.getContext().getSession().put("message","Failure~The following customerId " +this.customer.getCustomerId()+ " already Exists.");
			return ERROR;
		}
		else if (resultXml.equals("GatewayFailure"))
		{
			ActionContext.getContext().getSession().put("message","Failure~The following macId "+this.gateWay.getMacId()+ " is invalid.");
			return ERROR;
		}
		else if (resultXml.equals("GatewayExists"))
		{
			ActionContext.getContext().getSession().put("message","Failure~The following macId "+this.gateWay.getMacId()+ " already Exists.");
			return ERROR;
		}
		else
		{
			ActionContext.getContext().getSession().put("message","Congratulations !!,Your iMonitor smarthome account with customerId "+ this.customer.getCustomerId()
					+ " has been successfully created. ");
			return SUCCESS;
		}
		
	}
	
	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public String[] getGateWayMacIds() {
		return gateWayMacIds;
	}

	public void setGateWayMacIds(String[] gateWayMacIds) {
		this.gateWayMacIds = gateWayMacIds;
	}

	public List<GateWay> getGateWaysList() {
		return gateWaysList;
	}

	public void setGateWaysList(List<GateWay> gateWaysList) {
		this.gateWaysList = gateWaysList;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	
	public boolean isHealthcheck() {
		return healthcheck;
	}


	public void setHealthcheck(boolean healthcheck) {
		this.healthcheck = healthcheck;
	}


	public boolean isPreset() {
		return preset;
	}


	public void setPreset(boolean preset) {
		this.preset = preset;
	}




	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<SystemIntegrator> getSystemIntegrators() {
		return systemIntegrators;
	}


	public void setSystemIntegrators(List<SystemIntegrator> systemIntegrators) {
		this.systemIntegrators = systemIntegrators;
	}
	
		public boolean isDashboard() {
		return Dashboard;
	}


	public void setDashboard(boolean dashboard) {
		Dashboard = dashboard;
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


	public String getBackupfile() {
		return backupfile;
	}


	public void setBackupfile(String backupfile) {
		this.backupfile = backupfile;
	}


	public boolean isSms() {
		return sms;
	}


	public void setSms(boolean sms) {
		this.sms = sms;
	}


	public boolean isEmail() {
		return email;
	}


	public void setEmail(boolean email) {
		this.email = email;
	}

	public void getEMail(boolean email){
	this.email=email;
	}
	public boolean isVideoStreaming() {
		return videoStreaming;
	}


	public void setVideoStreaming(boolean videoStreaming) {
		this.videoStreaming = videoStreaming;
	}


	public boolean isWhatsApp() {
		return WhatsApp;
	}


	public void setWhatsApp(boolean whatsApp) {
		WhatsApp = whatsApp;
	}


	public boolean isSupport() {
		return support;
	}


	public void setSupport(boolean support) {
		this.support = support;
	}


	

	public boolean isCustomeremail() {
		return customeremail;
	}


	public void setCustomeremail(boolean customeremail) {
		this.customeremail = customeremail;
	}


	public boolean isOtherMail() {
		return otherMail;
	}


	public void setOtherMail(boolean otherMail) {
		this.otherMail = otherMail;
	} 

	public CustomerServices getCustomerServices() {
		return customerServices;
	}


	public void setCustomerServices(CustomerServices customerServices) {
		this.customerServices = customerServices;
	}


	public String getOthermail() {
		return othermail;
	}


	public void setOthermail(String othermail) {
		this.othermail = othermail;
	}


	public GateWay getGateWay() {
		return gateWay;
	}


	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}
	
	//3gp
	@SuppressWarnings("unchecked")
	public String getGatewaysForRename() throws Exception {
		
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		long id = user.getId();
		String valueInXml = stream.toXML(id);
		String serviceName = "userService";
		String method = "getUserById";
		String xml = IMonitorUtil.sendToController(serviceName,
				method, valueInXml);
		//this.customer = (Customer) stream.fromXML(xml);
		this.user=(User) stream.fromXML(xml);

			String  serviceName1 = "gateWayService";
			String  method1 = "getMultipleGateways";
			long cid = this.user.getCustomer().getId();
			String customerId=Long.toString(cid);
			String customerXml = stream.toXML(customerId);
			String gatewaysXml = IMonitorUtil.sendToController(serviceName1, method1,customerXml);
			
			this.gateWaysList = (List<GateWay>) stream.fromXML(gatewaysXml);
			
			this.gateWay1 = gateWaysList.get(0);
			this.gateWay2 = gateWaysList.get(1);
			this.gateWay3 = gateWaysList.get(2);
		//3gp
		
		return SUCCESS;
	}
	
	//3gp 
	public String saveGatewayNames() throws Exception
	{
		
		XStream stream = new XStream();
		List<GateWay> gateWays=new ArrayList<GateWay>();
		gateWays.add(this.gateWay1);
		gateWays.add(this.gateWay2);
		gateWays.add(this.gateWay3);
		String gatewaysXml = stream.toXML(gateWays);
		
		String resultXml = IMonitorUtil.sendToController("gateWayService","saveGatewayNames",gatewaysXml);
		
		
		
		return SUCCESS;
	}


	public GateWay getGateWay1() {
		return gateWay1;
	}


	public void setGateWay1(GateWay gateWay1) {
		this.gateWay1 = gateWay1;
	}


	public GateWay getGateWay2() {
		return gateWay2;
	}


	public void setGateWay2(GateWay gateWay2) {
		this.gateWay2 = gateWay2;
	}


	public GateWay getGateWay3() {
		return gateWay3;
	}


	public void setGateWay3(GateWay gateWay3) {
		this.gateWay3 = gateWay3;
	}


	public String getMappingResult() {
		return mappingResult;
	}


	public void setMappingResult(String mappingResult) {
		this.mappingResult = mappingResult;
	}


	/*public AdminSuperUser getSuperuser() {
		return superuser;
	}


	public void setSuperuser(AdminSuperUser superuser) {
		this.superuser = superuser;
	}


	public List<AdminSuperUser> getAdminSuperUsers() {
		return adminSuperUsers;
	}


	public void setAdminSuperUsers(List<AdminSuperUser> adminSuperUsers) {
		this.adminSuperUsers = adminSuperUsers;
	}*/




}
