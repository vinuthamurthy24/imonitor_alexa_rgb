/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.cms.action.adminuser.CameraConfigurationManagement;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertsAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgSecurityActions;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 * 
 */
public class RuleManagement extends ActionSupport {

	private static final long serialVersionUID = -3380398489319609365L;
	private Set<GateWay> gateWays;
	private Set<Device> devices;
	private GateWay gatewys;
	private Rule rule;
	private String[] actionExpressions;
	private String[] alertExpressions;
	private DataTable dataTable;
	private Set<UserNotificationProfile> userNotificationProfiles;
	private long[] emailNotifications;
	private long[] smsNotifications;
	private ArrayList<String> hours;
	private ArrayList<String> minutes;
	//sumit start
	private List<ShowNotification> notification;
	private List<UserNotificationProfile> savedNotificationIds;
//	private boolean AlertMonitor;
	private boolean alert;
	private boolean securityAlert;
	private long customer;
	private Set<ImvgSecurityActions> imvgSecurityActions;
	//sumit end
	
	
	public RuleManagement()
	{
		hours = new ArrayList<String>();
		for(int i=0;i<24;i++)
			hours.add(""+i);
		
		minutes=new ArrayList<String>();
		for(int i=0;i<60;i++)
			minutes.add(""+i);
		
	}
	public String execute() throws Exception {
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		Long id = new Long(user.getCustomer().getId());
		String dataTableXml = stream.toXML(this.dataTable);
		String serviceName = "ruleService";
		String method = "listAskedRules";
		String xmlString = stream.toXML(id);
		String resultXml = IMonitorUtil.sendToController(serviceName, method,dataTableXml,
				xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}

	public String toAddRule() throws Exception {
		String serviceName = "customerService";
		String method = "retrieveCustomerDeviceAlertAndActionsAndNoticiationProfiles";
		User user = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		String customerId = user.getCustomer().getCustomerId();
		XStream stream = new XStream();
		String customerIdXml = stream.toXML(customerId);
		String result = IMonitorUtil.sendToController(serviceName, method,
				customerIdXml);
		
		Customer customer = (Customer) stream.fromXML(result);
		this.gateWays = customer.getGateWays();
		this.userNotificationProfiles = customer.getUserNotificationProfiles();
		
		for (GateWay g : this.gateWays)
		{
			devices = g.getDevices();
			
			for (Device d : devices)
			{
				
				if (d.getDeviceType().getName().equalsIgnoreCase("IP_CAMERA"))
				{
					

					/*String devtypename = d.getDeviceType().getName();
					
					Long deviceId = d.getId();*/
					
					
					//CameraConfigurationManagement cameraConf = new CameraConfigurationManagement();
				//	CameraConfiguration cameraConfiguration = (CameraConfiguration) daoManager.get(deviceId, CameraConfiguration.class);
			
				}
			}
				
		}
		
		// ******************************************************** sumit start **************************************************************
		//1.Map used for convenience of checking if we already have a ShowNotification with gven name
		Map<String, ShowNotification> notificationMapping = new HashMap<String, ShowNotification>();
		for(UserNotificationProfile unp: this.userNotificationProfiles){	
			long id = unp.getId();
			String name = unp.getName();
			String actionName = unp.getActionType().getName();
			int whatsApp = unp.getWhatsAppStatus();
			ShowNotification showNotification = notificationMapping.get(name);
			if(null == showNotification)
			{
				showNotification = new ShowNotification();
				showNotification.setName(name);
				notificationMapping.put(name, showNotification);
			}
			if((actionName!= null) && actionName.equalsIgnoreCase("E-Mail")){
				showNotification.setAction(actionName);
				showNotification.setEmail(id);
				showNotification.setIsEmailDefined(true);
				if(whatsApp == 1){
					showNotification.setWhatsApp(id);
					showNotification.setWhatsAppDefined(true);
				}
			}else if((actionName!= null) && actionName.equalsIgnoreCase("SMS")){
				showNotification.setAction(actionName);
				showNotification.setSms(id);
				showNotification.setIsSmsDefined(true);
				if(whatsApp == 1){
					showNotification.setWhatsApp(id);
				showNotification.setWhatsAppDefined(true);
				}
				//naveen start
			}else if((actionName!= null) && actionName.equalsIgnoreCase("EmailAndSMS")){
				showNotification.setAction(actionName);
				showNotification.setSms(id);
				showNotification.setEmail(id);
				showNotification.setSmsAndEmailDefined(true);
				if(whatsApp == 1){
				showNotification.setWhatsAppDefined(true);
				showNotification.setWhatsApp(id);
				}
			}else if((actionName!= null) && actionName.equalsIgnoreCase("WhatsApp")){
				showNotification.setWhatsAppDefined(true);
				showNotification.setAction(actionName);
				showNotification.setWhatsApp(id);
				showNotification.setSms(id);
			}
			//navee end
		}
		//2.Now populate the List which we can easily access in JSP
		this.notification = new ArrayList<ShowNotification>();
		if(notificationMapping.size() > 0){
			for(ShowNotification s : notificationMapping.values()){
				this.notification.add(s);
			}
		}
		// ************************************************************* sumit end **********************************************************
		return SUCCESS;
	}

	public String toAddScenario() throws Exception {
		return SUCCESS;
	}

	public String saveRule() throws Exception {
		String message="";
		XStream stream = new XStream();
		GateWay gateWay=null;
		
		//check gateway version
				try 
				{
					String singleAlertExp=this.alertExpressions[0];
					String[] splitalertObj = singleAlertExp.split("=");
					long dIdofObject = Long.parseLong(splitalertObj[0]);
					String value=Long.toString(dIdofObject);
					String deviceIdxml=stream.toXML(value);
					String serviceName1 = "ruleService";
					String method1 = "getGateway";
					String gatewayXml = IMonitorUtil.sendToController(serviceName1, method1,deviceIdxml);
					gateWay=(GateWay) stream.fromXML(gatewayXml);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					LogUtil.info(e.getMessage());
				}
		
		if(this.alertExpressions.length < 1){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.rule.0001");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		else if (this.actionExpressions.length >= 12 && gateWay.getGateWayVersion().contains("IMSZING"))
		{
		
			message = IMonitorUtil.formatMessage(this, "Rule Not Saved ..Please select only 10 actions..");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		
		
		Set<DeviceAlert> deviceAlerts = new HashSet<DeviceAlert>();
		for (int i = 0; i < this.alertExpressions.length; i++){
			
			String alertExpression = this.alertExpressions[i];
			String[] split = alertExpression.split("=");
			long deviceId = Long.parseLong(split[0]);
			long alertId = Long.parseLong(split[1]);
			long StartHours=Long.parseLong(split[2]);
			long StartMinutes=Long.parseLong(split[3]);
			long EndHours=Long.parseLong(split[4]);
			long EndMinute=Long.parseLong(split[5]);
			//sumit start: ZXT120 
			//long deviceSpecificAlertId = Long.parseLong(split[6]);//1:temperature is equal to, 2:temperature is less than, 3:temperature is greater than
			//long temperature = Long.parseLong(split[7]);
			String comparatorName = split[6]+"-"+split[7];
			String alertTypeName = split[8];//vibhu added
			long StarTime=(StartHours*60)+StartMinutes;
			long EndTime=(EndHours*60)+EndMinute;
			if(StarTime!=EndTime  || (StarTime ==0 && EndTime==0))
			{
			DeviceAlert deviceAlert = new DeviceAlert();
			Device device = new Device();
			device.setId(deviceId);
			deviceAlert.setDevice(device);
			deviceAlert.setStartTime(StarTime);
			deviceAlert.setEndTime(EndTime);
			deviceAlert.setComparatorName(comparatorName);		//using comparatorName to save ZXT120 specific parameters due to Optimization Issue.
			
			AlertType alertType = new AlertType();
			alertType.setId(alertId);
			alertType.setName(alertTypeName);//vibhu added
			deviceAlert.setAlertType(alertType);
			deviceAlerts.add(deviceAlert);
			}
			else
			{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.rule.0002");
				ActionContext.getContext().getSession().put("message", message);
				return ERROR;
			}
		}
		Set<ImvgAlertsAction> imvgAlertsActions  = new LinkedHashSet<ImvgAlertsAction>(15);//<ImvgAlertsAction>();
		LogUtil.info("Actions length is "+this.actionExpressions.length);
		
		
		for (int i = 0; i < this.actionExpressions.length; i++) {
			String actionExpression = this.actionExpressions[i];

			actionExpression = actionExpression.trim();
			if(actionExpression.equalsIgnoreCase("")){
				continue;
			}
			String[] split = actionExpression.split("=");
			if(split.length < 2){
				continue;
			}
			
			long deviceId = Long.parseLong(split[1]);
			long actionId = Long.parseLong(split[2]);
			int AfterDelay=Integer.parseInt(split[0]);
			
			Device device = new Device();
			device.setId(deviceId);
			ActionType actionType = new ActionType();
			actionType.setId(actionId);
			ImvgAlertsAction imvgAlertsAction = new ImvgAlertsAction();
			imvgAlertsAction.setDevice(device);
			imvgAlertsAction.setAfterDelay(AfterDelay);
			imvgAlertsAction.setActionType(actionType);
			if(split.length > 3){
				String level = split[3];
				
				imvgAlertsAction.setLevel(level);
			}
			imvgAlertsActions.add(imvgAlertsAction);
		}
	
		Set<ImvgAlertNotification> imvgAlertNotifications = new HashSet<ImvgAlertNotification>();


		try {
			if(this.notification != null && !this.notification.isEmpty()){
				
				for(ShowNotification showNotification: this.notification){
					boolean isEmailChecked = showNotification.getEmailCheck();
					boolean isSmsChecked = showNotification.getSmsCheck();
					boolean isWhatsAppChecked = showNotification.getWhatsAppCheck();
					UserNotificationProfile userNotificationProfile;// = new UserNotificationProfile();
					ImvgAlertNotification alertNotification;// = new ImvgAlertNotification();
					//naveen added changes to save notification check from user
					if(isEmailChecked && !isSmsChecked){
						alertNotification = new ImvgAlertNotification();
						userNotificationProfile = new UserNotificationProfile();
						long emailId = showNotification.getEmail();
						userNotificationProfile.setId(emailId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("EMail");
						if(isWhatsAppChecked){
							alertNotification.setWhatsApp((long)1);
						}else{
							alertNotification.setWhatsApp((long)0);
						}
						imvgAlertNotifications.add(alertNotification);
						
					}
					if(isSmsChecked && !isEmailChecked){
						alertNotification = new ImvgAlertNotification();
						userNotificationProfile = new UserNotificationProfile();
						long smsId = showNotification.getSms();
						userNotificationProfile.setId(smsId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("SMS");
						if(isWhatsAppChecked){
							alertNotification.setWhatsApp((long)1);
						}else{
							alertNotification.setWhatsApp((long)0);
						}
						imvgAlertNotifications.add(alertNotification);
						
					}if(isEmailChecked && isSmsChecked){
						alertNotification = new ImvgAlertNotification();
						userNotificationProfile = new UserNotificationProfile();
						long smsId = showNotification.getSms();
						long emailId = showNotification.getEmail();
						userNotificationProfile.setId(emailId);
						userNotificationProfile.setId(smsId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("EmailAndSMS");
						if(isWhatsAppChecked){
							alertNotification.setWhatsApp((long) 1);
							}else{
								alertNotification.setWhatsApp((long) 0);
							}
						imvgAlertNotifications.add(alertNotification);
						
					}if(!isSmsChecked && !isEmailChecked && isWhatsAppChecked){
						alertNotification = new ImvgAlertNotification();
						userNotificationProfile = new UserNotificationProfile();
						long whatsAppId = showNotification.getWhatsApp();
						userNotificationProfile.setId(whatsAppId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("WhatsApp");
						
							alertNotification.setWhatsApp((long) 1);
						
							imvgAlertNotifications.add(alertNotification);
					}
					//naveen end
				}
			}else{//sumit added this else block. Can delete after testing.
				LogUtil.info("NO NOTIFICATION");
			}
		} catch (Exception e) {
			LogUtil.error("1.GOT EXCEPTION while saving Rule: "+e.getMessage());
		}
		// ************************************************************** sumit end *************************************************************
		this.rule.setImvgAlertsActions(imvgAlertsActions);
		this.rule.setDeviceAlerts(deviceAlerts);
		this.rule.setImvgAlertNotifications(imvgAlertNotifications);
		boolean alt=this.alert;
		boolean sec = this.securityAlert;
		if(alt){
			this.rule.setAlert(1);
		}else{
			this.rule.setAlert(0);
		}
		if(sec){
			this.rule.setSecurity(1);
		}else{
			this.rule.setSecurity(0);
		}
		String ruleXml = stream.toXML(this.rule);
		String serviceName = "ruleService";
		String method = "saveRuleWithAllDetails";
		message = IMonitorUtil.sendToController(serviceName, method,
				ruleXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;

	}

	public String updateRule() throws Exception {
		
		XStream stream = new XStream();
		GateWay gateWay=null;
		
		//check gateway version
		try 
		{
			String singleAlertExp=this.alertExpressions[0];
			String[] splitalertObj = singleAlertExp.split("=");
			long dIdofObject = Long.parseLong(splitalertObj[0]);
			String value=Long.toString(dIdofObject);
			String deviceIdxml=stream.toXML(value);
			String serviceName1 = "ruleService";
			String method1 = "getGateway";
			String gatewayXml = IMonitorUtil.sendToController(serviceName1, method1,deviceIdxml);
			gateWay=(GateWay) stream.fromXML(gatewayXml);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LogUtil.info(e.getMessage());
		}
		
		String message="";
		if(this.alertExpressions.length < 1){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.rule.0001");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		
		else if (this.actionExpressions.length >= 12 && gateWay.getGateWayVersion().contains("IMSZING"))
		{
			
			
			message = IMonitorUtil.formatMessage(this, "Rule Not Saved ..Please select only 10 actions..");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		
		Set<DeviceAlert> deviceAlerts = new HashSet<DeviceAlert>();
		for (int i = 0; i < this.alertExpressions.length; i++) {
			String alertExpression = this.alertExpressions[i];
			String[] split = alertExpression.split("=");
			long deviceId = Long.parseLong(split[0]);
			long alertId = Long.parseLong(split[1]);
			long StartHours=Long.parseLong(split[2]);
			long StartMinutes=Long.parseLong(split[3]);
			long EndHours=Long.parseLong(split[4]);
			long EndMinute=Long.parseLong(split[5]);
			long StarTime=(StartHours*60)+StartMinutes;
			long EndTime=(EndHours*60)+EndMinute;
			
			//sumit start: ZXT120 
			//long deviceSpecificAlertId = Long.parseLong(split[6]);//1:temperature is equal to, 2:temperature is less than, 3:temperature is greater than
			//long temperature = Long.parseLong(split[7]);
			String comparatorName = split[6]+"-"+split[7];
			String alertTypeName = split[8];//vibhu added
			if(StarTime != EndTime ||(StarTime ==0 && EndTime==0))
			{
			DeviceAlert deviceAlert = new DeviceAlert();
			Device device = new Device();
			device.setId(deviceId);
			deviceAlert.setDevice(device );
			deviceAlert.setStartTime(StarTime);
			deviceAlert.setEndTime(EndTime);
			deviceAlert.setComparatorName(comparatorName);		//using comparatorName to save ZXT120 specific parameters due to Optimization Issue.
			AlertType alertType = new AlertType();
			alertType.setId(alertId);
			alertType.setName(alertTypeName);//vibhu added
			deviceAlert.setAlertType(alertType);
			deviceAlerts.add(deviceAlert);
			}
			else 
			{
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.rule.0002");
				ActionContext.getContext().getSession().put("message", message);
				return ERROR;
			}
			
		}
		Set<ImvgAlertsAction> imvgAlertsActions  = new LinkedHashSet<ImvgAlertsAction>(15);//<ImvgAlertsAction>();
		for (int i = 0; i < this.actionExpressions.length; i++) {
			String actionExpression = this.actionExpressions[i];
			actionExpression = actionExpression.trim();
			if(actionExpression.equalsIgnoreCase("")){
				continue;
			}
			String[] split = actionExpression.split("=");
			if(split.length < 2){
				continue;
			}
			long deviceId = Long.parseLong(split[1]);
			long actionId = Long.parseLong(split[2]);
			int AfterDelay=Integer.parseInt(split[0]);
			Device device = new Device();
			device.setId(deviceId);
			ActionType actionType = new ActionType();
			actionType.setId(actionId);
			ImvgAlertsAction imvgAlertsAction = new ImvgAlertsAction();
			imvgAlertsAction.setDevice(device);
			imvgAlertsAction.setActionType(actionType);
			imvgAlertsAction.setAfterDelay(AfterDelay);
			if(split.length > 3 && split[3]!=null && split[3].length() > 0){
				String level = split[3];
				imvgAlertsAction.setLevel(level);
			}
			imvgAlertsActions.add(imvgAlertsAction);
		}
		Set<ImvgAlertNotification> imvgAlertNotifications = new HashSet<ImvgAlertNotification>();
		
				
		
		try {
			if(this.notification != null && !this.notification.isEmpty()){
				for(ShowNotification showNotification: this.notification){
					boolean isEmailChecked = showNotification.getEmailCheck();
					boolean isSmsChecked = showNotification.getSmsCheck();
					boolean isWhatsAppChecked = showNotification.getWhatsAppCheck();
					UserNotificationProfile userNotificationProfile;// = new UserNotificationProfile();
					ImvgAlertNotification alertNotification;// = new ImvgAlertNotification();				
					//naveen start
					if(isEmailChecked && !isSmsChecked){
						userNotificationProfile = new UserNotificationProfile();
						alertNotification = new ImvgAlertNotification();
						long mailId = showNotification.getEmail();
						userNotificationProfile.setId(mailId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("EMail");
						if(isWhatsAppChecked){
							alertNotification.setWhatsApp((long) 1);
							}else{
								alertNotification.setWhatsApp((long) 0);
								
							}
						imvgAlertNotifications.add(alertNotification);
					}
					if(isSmsChecked && !isEmailChecked){
						userNotificationProfile = new UserNotificationProfile();
						alertNotification = new ImvgAlertNotification();
						long smsId = showNotification.getSms();
						userNotificationProfile.setId(smsId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("SMS");
						if(isWhatsAppChecked){
							alertNotification.setWhatsApp((long) 1);
							}else{
								alertNotification.setWhatsApp((long)0);
								
							}
						imvgAlertNotifications.add(alertNotification);
					}if(isEmailChecked && isSmsChecked){
						alertNotification = new ImvgAlertNotification();
						userNotificationProfile = new UserNotificationProfile();
						long smsId = showNotification.getSms();
						long emailId = showNotification.getEmail();
						userNotificationProfile.setId(emailId);
						userNotificationProfile.setId(smsId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("EmailAndSMS");
						if(isWhatsAppChecked){
							alertNotification.setWhatsApp((long) 1);
							}else{
								alertNotification.setWhatsApp((long)0);
							}
						imvgAlertNotifications.add(alertNotification);
						
					}if(!isSmsChecked && !isEmailChecked && isWhatsAppChecked){
						alertNotification= new ImvgAlertNotification();
						userNotificationProfile = new UserNotificationProfile();
						long whatsAppId = showNotification.getWhatsApp();
						userNotificationProfile.setId(whatsAppId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("WhatsApp");
						alertNotification.setWhatsApp((long) 1);
						imvgAlertNotifications.add(alertNotification);
						
					}
					//naveen end
				}
				
			}else{//sumit added this else block. Can delete after testing.
				LogUtil.info("NO NOTIFICATION");
			}
		} catch (Exception e) {
			LogUtil.error("1.GOT EXCEPTION while Updating Rule: "+e.getMessage());
		}
		// ************************************************************ sumit end **************************************************************
		this.rule.setImvgAlertsActions(imvgAlertsActions);
		this.rule.setDeviceAlerts(deviceAlerts);
		this.rule.setImvgAlertNotifications(imvgAlertNotifications);
		boolean alt=this.alert;
		boolean sec = this.securityAlert;
		if(alt){
			this.rule.setAlert(1);
		}else{
			this.rule.setAlert(0);
		}
		
		if(sec){
			this.rule.setSecurity(1);
		}else{
			this.rule.setSecurity(0);
		}
		//XStream stream = new XStream();
		String ruleXml = stream.toXML(this.rule);
		String serviceName = "ruleService";
		String method = "updateRuleWithAllDetails";
		message = IMonitorUtil.sendToController(serviceName, method,
				ruleXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}

	public String deleteRule() throws Exception {
		XStream stream = new XStream();
		String ruleXml = stream.toXML(this.rule);
		String serviceName = "ruleService";
		String method = "deleteRuleWithAllDetails";
		String message = IMonitorUtil.sendToController(serviceName, method,
				ruleXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}

	public String toEditRule() throws Exception {
		String serviceName = "customerService";
		String method = "retrieveCustomerDeviceAlertAndActionsAndNoticiationProfiles";
		User user = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		String customerId = user.getCustomer().getCustomerId();
		XStream stream = new XStream();
		String customerIdXml = stream.toXML(customerId);
		String result = IMonitorUtil.sendToController(serviceName, method,
				customerIdXml);
		
		Customer customer = (Customer) stream.fromXML(result);
		
		this.gateWays = customer.getGateWays();
		for (GateWay g : this.gateWays)
		{
			devices = g.getDevices();
			
			for (Device d : devices)
			{
				
				
				
				if (d.getDeviceType().getName().equalsIgnoreCase("IP_CAMERA"))
				{
					

					/*String devtypename = d.getDeviceType().getName();
					
					Long deviceId = d.getId();*/
										
					//CameraConfigurationManagement cameraConf = new CameraConfigurationManagement();
				//	CameraConfiguration cameraConfiguration = (CameraConfiguration) daoManager.get(deviceId, CameraConfiguration.class);
			
				}
			}
				
		}
//		Retrieving the rule details
		serviceName = "ruleService";
		method = "retrieveAllRuleDetails";
		String ruleXml = stream.toXML(this.rule);
		String resultXml = IMonitorUtil.sendToController(serviceName, method,
				ruleXml);
		this.rule = (Rule) stream.fromXML(resultXml);
	if(this.rule.getAlert() == 1){
		this.alert = true;
	}
	
	if(this.rule.getSecurity() == 1) {
		this.securityAlert = true;
	}
		long gateWayId = this.rule.getGateWay().getId();
		Set<GateWay> rGateWays = new HashSet<GateWay>();
		for (GateWay gateWay : this.gateWays) {
			if(gateWay.getId() != gateWayId){
				rGateWays.add(gateWay);
			}
		}
		
		//Added by apoorva for 3gp
			/*	if (this.gateWays.size() > 1) 
				{
					LogUtil.info("Continue");
				} 
				else
				{
					this.gateWays.removeAll(rGateWays); 
				}*/
				//Added by apoorva for 3gp end
		
		this.gateWays.removeAll(rGateWays);
		
		this.userNotificationProfiles = customer.getUserNotificationProfiles();
		// ************************************************************* sumit start ************************************************************
		//1.Based on the userNotificationProfiles generate the entries for the table in the editRule-UI
		Map<String, ShowNotification> notificationMapping = new HashMap<String, ShowNotification>();
		for(UserNotificationProfile unp: this.userNotificationProfiles){	
			long id = unp.getId();
			String name = unp.getName();
			String actionName = unp.getActionType().getName();
			ShowNotification showNotification = notificationMapping.get(name);
			int whatsApp = unp.getWhatsAppStatus();
			if(null == showNotification)
			{
				showNotification = new ShowNotification();
				showNotification.setName(name);
				notificationMapping.put(name, showNotification);
			}
			if((actionName!= null) && actionName.equalsIgnoreCase("E-Mail")){
				showNotification.setAction(actionName);
				showNotification.setEmail(id);
				showNotification.setIsEmailDefined(true);
				if(whatsApp == 1){
					showNotification.setWhatsApp(id);
					showNotification.setWhatsAppDefined(true);
			}
			}else if((actionName!= null) && actionName.equalsIgnoreCase("SMS")){
				showNotification.setAction(actionName);
				showNotification.setSms(id);
				showNotification.setIsSmsDefined(true);
				if(whatsApp == 1){
					showNotification.setWhatsApp(id);
					showNotification.setWhatsAppDefined(true);
				}
            /*-----------------------------Naveen Start------------------------------------------*/
	        //To enable checkbox in jsp page if user has selected both email and sms
			}else if((actionName!= null) && actionName.equalsIgnoreCase("EmailAndSMS")){
				showNotification.setAction(actionName);
				showNotification.setSms(id);
				showNotification.setEmail(id);
				showNotification.setSmsAndEmailDefined(true);
				if(whatsApp == 1){
				showNotification.setWhatsAppDefined(true);
				showNotification.setWhatsApp(id);
				}
			}
			 else if((actionName!= null) && actionName.equalsIgnoreCase("WhatsApp")){
					showNotification.setWhatsAppDefined(true);
					showNotification.setAction(actionName);
					showNotification.setWhatsApp(id);
					showNotification.setSms(id);
						}
            /*-----------------------------Naveen End----------------------------------------------*/
		}
		
		//2.Now populate the List which we can easily access in JSP
		this.notification = new ArrayList<ShowNotification>();
		for(ShowNotification s : notificationMapping.values()){
//			LogUtil.info("name: "+s.getName()+"isSmsDefined: "+s.getIsSmsDefined()+", isEmailDefined: "+s.getIsEmailDefined()+", email: "+s.getEmail()+", sms: "+s.getSms());
			this.notification.add(s);
		}	

		//3.Based on the savedNotificationId check the appropriate entry in the table for editRule-UI. 
		this.savedNotificationIds = new ArrayList<UserNotificationProfile>();

		for(ImvgAlertNotification imvgAlertNotification: this.rule.getImvgAlertNotifications()){
			UserNotificationProfile unp = imvgAlertNotification.getUserNotificationProfile();
			String nc = imvgAlertNotification.getNotificationCheck();
			long wa = imvgAlertNotification.getWhatsApp();
			long id = unp.getId();
			UserNotificationProfile userNotificationProfile = new UserNotificationProfile();
			userNotificationProfile.setId(id);
			userNotificationProfile.setNotifycheck(nc);
			userNotificationProfile.setWaCheck(wa);
			this.savedNotificationIds.add(userNotificationProfile);			
		}
		
		for(UserNotificationProfile alert : this.savedNotificationIds){
			// naveen made change to handle checkbox for "EmailAndSms" action type
			long notificationId = alert.getId();
			String abc = alert.getNotifycheck();
			Long wappcheck = alert.getWaCheck();
			
			for(ShowNotification s: this.notification){
								
				if(notificationId == s.getEmail()){
					
					s.setEmailCheck(true);
					if(wappcheck == 1){
						s.setWhatsAppCheck(true);
					}
				}
				if(notificationId == s.getSms()){
					s.setSmsCheck(true);
					if(wappcheck == 1){
						
						//s.setSmsCheck(false);
						s.setWhatsAppCheck(true);
					}
				}
				if(notificationId == s.getSms() && abc.equals("EMail")){
					s.setEmailCheck(true);
					s.setSmsCheck(false);
					if(wappcheck.equals("1") || wappcheck.equals(1) || wappcheck == 1){
						
						s.setWhatsAppCheck(true);
					}
				}
				if(notificationId == s.getSms() && abc.equals("SMS")){
					s.setSmsCheck(true);
					s.setEmailCheck(false);
					if(wappcheck.equals("1") || wappcheck.equals(1) || wappcheck == 1){
						
						s.setWhatsAppCheck(true);
					}
					
				}
				
				if(notificationId == s.getWhatsApp() && abc.equals("WhatsApp")){
					
					s.setSmsCheck(false);
					s.setEmailCheck(false);
					s.setWhatsAppCheck(true);
				}
				// naveen end
			}
		}	
		// ************************************************************* sumit end **********************************************************		
		return SUCCESS;
	}
	
	public String toEditRuleSecurity() throws Exception {
		String serviceName = "customerService";
		String method = "retrieveCustomerDeviceAlertAndActionsAndNoticiationProfiles";
		User user = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		String customerId = user.getCustomer().getCustomerId();
		this.customer = user.getCustomer().getId();
		XStream stream = new XStream();
		String customerIdXml = stream.toXML(customerId);
		String result = IMonitorUtil.sendToController(serviceName, method,
				customerIdXml);
		
		Customer customer = (Customer) stream.fromXML(result);
		this.gateWays = customer.getGateWays();
	
		
		for (GateWay g : this.gateWays)
		{
			devices = g.getDevices();
			
			for (Device d : devices)
			{
				
				if (d.getDeviceType().getName().equalsIgnoreCase("IP_CAMERA"))
				{
					

			
				}
			}
				
		}
		
		serviceName = "ruleService";
		method = "retrieveAllImvgSecurityDetails";
		String cXml = stream.toXML(this.customer);
		String resultXml = IMonitorUtil.sendToController(serviceName, method,cXml);
		
		this.imvgSecurityActions = (Set<ImvgSecurityActions>) stream.fromXML(resultXml);
			
		return SUCCESS;
	}
	
	public String toSaveSecurityConfiguration() throws Exception {
		String message="";
		XStream stream = new XStream();
				
		
		if (this.actionExpressions.length >= 6)
		{
			
			message = IMonitorUtil.formatMessage(this, "Configuration Not Saved ..Please select only 6 actions..");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		
		
		
		
		Set<ImvgSecurityActions> imvgAlertsActions  = new LinkedHashSet<ImvgSecurityActions>(6);//<ImvgAlertsAction>();
		
		
		
		for (int i = 0; i < this.actionExpressions.length; i++) {
			String actionExpression = this.actionExpressions[i];

			actionExpression = actionExpression.trim();
			if(actionExpression.equalsIgnoreCase("")){
				continue;
			}
			String[] split = actionExpression.split("=");
			if(split.length < 2){
				continue;
			}
			long gatewayId = Long.parseLong(split[0]);
			long deviceId = Long.parseLong(split[1]);
			long actionId = Long.parseLong(split[2]);
			
			
			Device device = new Device();
			device.setId(deviceId);
			ActionType actionType = new ActionType();
			actionType.setId(actionId);
			GateWay gateway = new GateWay();
			gateway.setId(gatewayId);
			Customer customer = new Customer();
			customer.setId(this.customer);
			ImvgSecurityActions imvgAlertsAction = new ImvgSecurityActions();
			imvgAlertsAction.setDevice(device);
			imvgAlertsAction.setActionType(actionType);
			imvgAlertsAction.setGateway(gateway);
			imvgAlertsAction.setCustomer(customer);
			if(split.length > 3){
				String level = split[3];
				
				imvgAlertsAction.setLevel(level);
			}
			imvgAlertsActions.add(imvgAlertsAction);
		}
		
		String imvgActionXml = stream.toXML(imvgAlertsActions);
		String customerXml = stream.toXML(this.customer);
		String serviceName = "ruleService";
		String method = "saveImvgSecurityActions";
		message = IMonitorUtil.sendToController(serviceName, method,imvgActionXml,customerXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;

	}

	public Set<GateWay> getGateWays() {
		return gateWays;
	}

	public void setGateWays(Set<GateWay> gateWays) {
		this.gateWays = gateWays;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public String[] getActionExpressions() {
		return actionExpressions;
	}

	public void setActionExpressions(String[] actionExpressions) {
		this.actionExpressions = actionExpressions;
	}

	public String[] getAlertExpressions() {
		return alertExpressions;
	}

	public void setAlertExpressions(String[] alertExpressions) {
		this.alertExpressions = alertExpressions;
	}

	public final DataTable getDataTable() {
		return dataTable;
	}

	public final void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public Set<UserNotificationProfile> getUserNotificationProfiles() {
		return userNotificationProfiles;
	}

	public void setUserNotificationProfiles(
			Set<UserNotificationProfile> userNotificationProfiles) {
		this.userNotificationProfiles = userNotificationProfiles;
	}

	public long[] getEmailNotifications() {
		return emailNotifications;
	}

	public void setEmailNotifications(long[] emailNotifications) {
		this.emailNotifications = emailNotifications;
	}

	public long[] getSmsNotifications() {
		return smsNotifications;
	}

	public void setSmsNotifications(long[] smsNotifications) {
		this.smsNotifications = smsNotifications;
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
	// ***************************************************************************************** sumit start ************************************************************************************
	public List<ShowNotification> getNotification() {
		return notification;
	}
	
	public void setNotification(List<ShowNotification> notification) {
		this.notification = notification;
	}
	
	public List<UserNotificationProfile> getSavedNotificationIds() {
		return savedNotificationIds;
	}
	
	public void setSavedNotificationIds(List<UserNotificationProfile> savedNotificationIds) {
		this.savedNotificationIds = savedNotificationIds;
	}
	// ***************************************************************************************** sumit end **********************************************************************************
	//pari added
	public Set<Device> getDevices() {
		return devices;
	}
	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}
	//pari end
	public GateWay getGatewys() {
		return gatewys;
	}
	public void setGatewys(GateWay gatewys) {
		this.gatewys = gatewys;
	}
	
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	public boolean isSecurityAlert() {
		return securityAlert;
	}
	public void setSecurityAlert(boolean securityAlert) {
		this.securityAlert = securityAlert;
	}
	public long getCustomer() {
		return customer;
	}
	public void setCustomer(long customer) {
		this.customer = customer;
	}
	public Set<ImvgSecurityActions> getImvgSecurityActions() {
		return imvgSecurityActions;
	}
	public void setImvgSecurityActions(Set<ImvgSecurityActions> imvgSecurityActions) {
		this.imvgSecurityActions = imvgSecurityActions;
	}
	

}
