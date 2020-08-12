/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotificationForScedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScheduleAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class EventScheduleAction extends ActionSupport {
	
	private static final long serialVersionUID = -3380398489319609365L;
	private Set<GateWay> gateWays;
	private String[] actionExpressions;
	private Schedule schedule;
	private DataTable dataTable;
	private Set<Device> devices;
	private Set<UserNotificationProfile> userNotificationProfiles;
	private List<ShowNotification> notification;
	private List<UserNotificationProfile> savedNotificationIds;
	private long[] emailNotifications;
	private long[] smsNotifications;
	private String[] actionExpressionsEnd; //Naveen added for end actions in schedule
    private Schedule endSchedule;
	private boolean endAction;
	
	public String execute() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Long id = new Long(user.getCustomer().getId());
		String xmlId = stream.toXML(id);
		String serviceName = "scheduleService";
		String method = "listAskedSchedules";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString, xmlId);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	public String toAddSchedule() throws Exception {
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
					

				//	String devtypename = d.getDeviceType().getName();
				//	LogUtil.info("DEVICE TYPE NAME="+devtypename);
				//	Long deviceId = d.getId();
					
				//	LogUtil.info(d.getDeviceConfiguration());
					
					//CameraConfigurationManagement cameraConf = new CameraConfigurationManagement();
				//	CameraConfiguration cameraConfiguration = (CameraConfiguration) daoManager.get(deviceId, CameraConfiguration.class);
			
				}
			}
				
		}
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
				showNotification.setWhatsAppDefined(true);
				showNotification.setWhatsApp(id);
				}//naveen start
			}else if((actionName!= null) && actionName.equalsIgnoreCase("EmailAndSMS")){
				showNotification.setAction(actionName);
				showNotification.setSms(id);
				showNotification.setEmail(id);
				showNotification.setSmsAndEmailDefined(true);
				if(whatsApp == 1){
					showNotification.setWhatsApp(id);
				    showNotification.setWhatsAppDefined(true);
			}
			}else if((actionName!= null) && actionName.equalsIgnoreCase("WhatsApp")){
				showNotification.setWhatsAppDefined(true);
				showNotification.setAction(actionName);
				showNotification.setWhatsApp(id);
				showNotification.setSms(id);
			}
			//navee end
		}
		    
			this.notification = new ArrayList<ShowNotification>();
			if(notificationMapping.size() > 0){
				for(ShowNotification s : notificationMapping.values()){
					this.notification.add(s);
					
				}
			}
		
		return SUCCESS;
	}
	
	public String saveSchedule() throws Exception {
		Set<ScheduleAction> scheduleActions = new LinkedHashSet<ScheduleAction>();
		Set<ScheduleAction> scheduleEndActions = new LinkedHashSet<ScheduleAction>();
		XStream stream = new XStream();
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
			long deviceId = Long.parseLong(split[0]);
			long actionId = Long.parseLong(split[1]);
			Device device = new Device();
			device.setId(deviceId);
			ActionType actionType = new ActionType();
			actionType.setId(actionId);
			ScheduleAction scheduleAction = new ScheduleAction();
			scheduleAction.setDevice(device);
			scheduleAction.setActionType(actionType);
			if(split.length > 2){
				String level = split[2];
				scheduleAction.setLevel(level);
			}
			scheduleActions.add(scheduleAction);
		}
		
		if(this.endAction){
		for(int i = 0; i < this.actionExpressionsEnd.length; i++){
			
			String actionEndExpression = actionExpressionsEnd[i];
			actionEndExpression = actionEndExpression.trim();
			if(actionEndExpression.equalsIgnoreCase("")){
				continue;
			}
			String[] split = actionEndExpression.split("=");
			if(split.length < 2){
				continue;
			}
			long deviceId = Long.parseLong(split[0]);
			long actionId = Long.parseLong(split[1]);
			Device device = new Device();
			device.setId(deviceId);
			ActionType actionType = new ActionType();
			actionType.setId(actionId);
			ScheduleAction scheduleEndAction = new ScheduleAction();
			scheduleEndAction.setDevice(device);
			scheduleEndAction.setActionType(actionType);
			if(split.length > 2){
				String level = split[2];
				scheduleEndAction.setLevel(level);
			}
			
			scheduleEndActions.add(scheduleEndAction);
		}
		}
		
		//LogUtil.info("Set of schedule actions"+stream.toXML(scheduleActions));
		this.schedule.setScheduleActions(scheduleActions);
		//this.schedule.setScheduleEndActions(scheduleEndActions); // Naveen added to store end actions
		// Naveen start: to save schedule with user choosen notification
		Set<ImvgAlertNotificationForScedule> imvgAlertNotificationsForSchedule = new HashSet<ImvgAlertNotificationForScedule>();
		try {
			if(this.notification != null && !this.notification.isEmpty()){
				
				for(ShowNotification showNotification: this.notification){
					
					boolean isEmailChecked = showNotification.getEmailCheck();
					boolean isSmsChecked = showNotification.getSmsCheck();
					boolean isWhatsAppChecked = showNotification.getWhatsAppCheck();
					UserNotificationProfile userNotificationProfile;// = new UserNotificationProfile();
					ImvgAlertNotificationForScedule alertNotification;// = new ImvgAlertNotification();
					//naveen added changes to save notification check from user
					if(isEmailChecked && !isSmsChecked){
						alertNotification = new ImvgAlertNotificationForScedule();
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
						imvgAlertNotificationsForSchedule.add(alertNotification);
						
					}
					if(isSmsChecked && !isEmailChecked){
						alertNotification = new ImvgAlertNotificationForScedule();
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
						imvgAlertNotificationsForSchedule.add(alertNotification);
						
					}if(isEmailChecked && isSmsChecked){
						alertNotification = new ImvgAlertNotificationForScedule();
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
						imvgAlertNotificationsForSchedule.add(alertNotification);
						
					}if(!isSmsChecked && !isEmailChecked && isWhatsAppChecked){
						alertNotification = new ImvgAlertNotificationForScedule();
						userNotificationProfile = new UserNotificationProfile();
						long whatsAppId = showNotification.getWhatsApp();
						userNotificationProfile.setId(whatsAppId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("WhatsApp");
							alertNotification.setWhatsApp((long) 1);
							imvgAlertNotificationsForSchedule.add(alertNotification);
					}
					//naveen end
				}
			}else{//sumit added this else block. Can delete after testing.
				LogUtil.info("NO NOTIFICATION");
			}
		} catch (Exception e) {
			LogUtil.error("1.GOT EXCEPTION while saving Rule: "+e.getMessage());
		}
		this.schedule.setImvgAlertNotificationsForScedule(imvgAlertNotificationsForSchedule);
		String ruleXml = stream.toXML(this.schedule);
		if(this.endAction){
			
			
			this.endSchedule.setActivationStatus(1);
			this.endSchedule.setDescription(this.schedule.getDescription());
			this.endSchedule.setGateWay(this.schedule.getGateWay());
			this.endSchedule.setImvgAlertNotificationsForScedule(this.schedule.getImvgAlertNotificationsForScedule());
			this.endSchedule.setName(this.schedule.getName());
			this.endSchedule.setScheduleActions(scheduleEndActions);
			this.endSchedule.setScheduleType(1);
			String[] Time = this.endSchedule.getScheduleTime().split(" ");	
			LogUtil.info("Time"+Time);
			String endTime = Time[1]+":"+Time[0];
			
		}
		
		String serviceName = "scheduleService";
		String method = "saveScheduleWithDetails";
		String message = "";
		if(this.endAction){
			String endScheduleXml = stream.toXML(this.endSchedule);
			method = "saveScheduleWithStartAndEndDetails";
			message = IMonitorUtil.sendToController(serviceName, method,
					ruleXml,endScheduleXml);
		}else{
			message = IMonitorUtil.sendToController(serviceName, method,
					ruleXml);
		}
		
		
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	// ****************************************** sumit start: Schedule Activation/De-Activation ******************************************
	public String toDeactivateSchedule() throws Exception {
		XStream stream = new XStream();
		String serviceName = "scheduleService";
		String method = "deActivateSchedule";
		String scheduleXml = stream.toXML(schedule);
		String message = IMonitorUtil.sendToController(serviceName, method, scheduleXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	public String toActivateSchedule() throws Exception {
		XStream stream = new XStream();
		String serviceName = "scheduleService";
		String method = "activateSchedule";
		String scheduleXml = stream.toXML(schedule);
		String message = IMonitorUtil.sendToController(serviceName, method, scheduleXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	// ***************************************************** sumit end: Schedule Activation/De-Activation **********************************
	public String toEditSchedule() throws Exception {
		LogUtil.info("toEditSchedule");
		String serviceName = "customerService";
		String method = "retrieveCustomerDeviceAlertAndActionsAndNotificationProfiles";
		User user = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		String customerId = user.getCustomer().getCustomerId();
		XStream stream = new XStream();
		String customerIdXml = stream.toXML(customerId);
		String result = IMonitorUtil.sendToController(serviceName, method,
				customerIdXml);
		Customer customer = (Customer) stream.fromXML(result);
		this.gateWays = customer.getGateWays();
//		Retrieving Schedule Details.
		serviceName = "scheduleService";
		method = "retrieveAllScheduleDetails";
		String ruleXml = stream.toXML(this.schedule);
		String resultXml = IMonitorUtil.sendToController(serviceName, method,
				ruleXml);
		this.schedule = (Schedule) stream.fromXML(resultXml);
	
		if(this.schedule.getEndSchedule() != 0){
			
			resultXml = IMonitorUtil.sendToController(serviceName, "retrieveAllEndScheduleDetails",
					resultXml);
			
			this.endSchedule = (Schedule) stream.fromXML(resultXml);
			this.endAction = true;
			
		}
		
		long gateWayId = this.schedule.getGateWay().getId();
		Set<GateWay> rGateWays = new HashSet<GateWay>();
		for (GateWay gateWay : this.gateWays) {
			if(gateWay.getId() != gateWayId){
				rGateWays.add(gateWay);
			}
		}
		
		/*//Added by apoorva for 3gp
		if (this.gateWays.size() > 1) 
		{
			LogUtil.info("Continue");
		} 
		else
		{
			this.gateWays.removeAll(rGateWays); 
		}
		//Added by apoorva for 3gp end
*/		
		this.gateWays.removeAll(rGateWays); 
		// Naveen start: to handle user choosen notification
		this.userNotificationProfiles = customer.getUserNotificationProfiles();
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
					showNotification.setWhatsAppDefined(true);
				    showNotification.setWhatsApp(id);	
				}
			}else if((actionName!= null) && actionName.equalsIgnoreCase("SMS")){
				showNotification.setAction(actionName);
				showNotification.setSms(id);
				showNotification.setIsSmsDefined(true);
				if(whatsApp == 1){
					showNotification.setWhatsAppDefined(true);
				    showNotification.setWhatsApp(id);	
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
		this.notification = new ArrayList<ShowNotification>();
		for(ShowNotification s : notificationMapping.values()){
			this.notification.add(s);
		}
		this.savedNotificationIds = new ArrayList<UserNotificationProfile>();
		
		
		for(ImvgAlertNotificationForScedule imvgAlertNotificationforschedule: this.schedule.getImvgAlertNotificationsForScedule()){
			UserNotificationProfile unp = imvgAlertNotificationforschedule.getUserNotificationProfile();
			long id = unp.getId();
			String nc = imvgAlertNotificationforschedule.getNotificationCheck();
			long wa = imvgAlertNotificationforschedule.getWhatsApp();
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
		return SUCCESS;
	}
	
	public String updateSchedule() throws Exception {
		Set<ScheduleAction> scheduleActions = new LinkedHashSet<ScheduleAction>();
		Set<ScheduleAction> scheduleEndActions = new LinkedHashSet<ScheduleAction>();
		XStream stream = new XStream();
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
			long deviceId = Long.parseLong(split[0]);
			long actionId = Long.parseLong(split[1]);
			Device device = new Device();
			device.setId(deviceId);
			ActionType actionType = new ActionType();
			actionType.setId(actionId);
			ScheduleAction scheduleAction = new ScheduleAction();
			scheduleAction.setDevice(device);
			scheduleAction.setActionType(actionType);
			if(split.length > 2){
				String level = split[2];
				scheduleAction.setLevel(level);
			}
			scheduleActions.add(scheduleAction);
		}
		if(this.endAction){
	    for(int i = 0; i < this.actionExpressionsEnd.length; i++){
			
			String actionEndExpression = actionExpressionsEnd[i];
			actionEndExpression = actionEndExpression.trim();
			if(actionEndExpression.equalsIgnoreCase("")){
				continue;
			}
			String[] split = actionEndExpression.split("=");
			if(split.length < 2){
				continue;
			}
			long deviceId = Long.parseLong(split[0]);
			long actionId = Long.parseLong(split[1]);
			Device device = new Device();
			device.setId(deviceId);
			ActionType actionType = new ActionType();
			actionType.setId(actionId);
			ScheduleAction scheduleEndAction = new ScheduleAction();
			scheduleEndAction.setDevice(device);
			scheduleEndAction.setActionType(actionType);
			if(split.length > 2){
				String level = split[2];
				scheduleEndAction.setLevel(level);
			}
			
			scheduleEndActions.add(scheduleEndAction);
		}
		
		}
		//Naveen start
		Set<ImvgAlertNotificationForScedule> imvgAlertNotificationsForSchedule = new HashSet<ImvgAlertNotificationForScedule>();
		try {
			if(this.notification != null && !this.notification.isEmpty()){
				for(ShowNotification showNotification: this.notification){
					boolean isEmailChecked = showNotification.getEmailCheck();
					boolean isSmsChecked = showNotification.getSmsCheck();
					boolean isWhatsAppChecked = showNotification.getWhatsAppCheck();
					UserNotificationProfile userNotificationProfile;// = new UserNotificationProfile();
					ImvgAlertNotificationForScedule alertNotification;// = new ImvgAlertNotification();				
					//naveen start
					if(isEmailChecked && !isSmsChecked){
						userNotificationProfile = new UserNotificationProfile();
						alertNotification = new ImvgAlertNotificationForScedule();
						long mailId = showNotification.getEmail();
						userNotificationProfile.setId(mailId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("EMail");
						if(isWhatsAppChecked){
							alertNotification.setWhatsApp((long) 1);
							}else{
								alertNotification.setWhatsApp((long) 0);
								
							}
						imvgAlertNotificationsForSchedule.add(alertNotification);
					}
					if(isSmsChecked && !isEmailChecked){
						userNotificationProfile = new UserNotificationProfile();
						alertNotification = new ImvgAlertNotificationForScedule();
						long smsId = showNotification.getSms();
						userNotificationProfile.setId(smsId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("SMS");
						if(isWhatsAppChecked){
							alertNotification.setWhatsApp((long) 1);
							}else{
								alertNotification.setWhatsApp((long)0);
								
							}
						imvgAlertNotificationsForSchedule.add(alertNotification);
					}if(isEmailChecked && isSmsChecked){
						alertNotification = new ImvgAlertNotificationForScedule();
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
						imvgAlertNotificationsForSchedule.add(alertNotification);
						
					}if(!isSmsChecked && !isEmailChecked && isWhatsAppChecked){
						alertNotification= new ImvgAlertNotificationForScedule();
						userNotificationProfile = new UserNotificationProfile();
						long whatsAppId = showNotification.getWhatsApp();
						userNotificationProfile.setId(whatsAppId);
						alertNotification.setUserNotificationProfile(userNotificationProfile);
						alertNotification.setNotificationCheck("WhatsApp");
						alertNotification.setWhatsApp((long) 1);
						imvgAlertNotificationsForSchedule.add(alertNotification);
						
					}
					//naveen end
				}
				
			}else{//sumit added this else block. Can delete after testing.
				LogUtil.info("NO NOTIFICATION");
			}
		} catch (Exception e) {
			LogUtil.error("1.GOT EXCEPTION while saving Rule: "+e.getMessage());
		}
		this.schedule.setImvgAlertNotificationsForScedule(imvgAlertNotificationsForSchedule);
		this.schedule.setScheduleActions(scheduleActions);
		//XStream stream = new XStream();
		String ruleXml = stream.toXML(this.schedule);
	    if(this.endAction){
			
			
			this.endSchedule.setActivationStatus(1);
			this.endSchedule.setDescription(this.schedule.getDescription());
			this.endSchedule.setGateWay(this.schedule.getGateWay());
			this.endSchedule.setImvgAlertNotificationsForScedule(this.schedule.getImvgAlertNotificationsForScedule());
			this.endSchedule.setName(this.schedule.getName());
			this.endSchedule.setScheduleActions(scheduleEndActions);
			this.endSchedule.setScheduleType(1);
			
						
         }
         
		String serviceName = "scheduleService";
		String method = "updateScheduleWithDetails";
		String message = "";
	//	LogUtil.info("end schedule: "+ stream.toXML(this.endSchedule));
		if(this.endAction){
			String endScheduleXml = stream.toXML(this.endSchedule);
			method = "updateScheduleWithStartAndEndDetails";
			message = IMonitorUtil.sendToController(serviceName, method,
					ruleXml,endScheduleXml);
		}else{
			message = IMonitorUtil.sendToController(serviceName, method,
					ruleXml);
		}
		
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	public String deleteSchedule() throws Exception{
		Long id = new Long(schedule.getId());
		XStream xStream = new XStream();
		String idXml = xStream.toXML(id);
		String message = IMonitorUtil.sendToController("scheduleService", "deleteSchedule", idXml);
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

	public String[] getActionExpressions() {
		return actionExpressions;
	}

	public void setActionExpressions(String[] actionExpressions) {
		this.actionExpressions = actionExpressions;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	//pari added
	public Set<Device> getDevices() {
			return devices;
	}
	public void setDevices(Set<Device> devices) {
			this.devices = devices;
	}
	//pari end
	
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
	// naveen start
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
// naveen end

	public String[] getActionExpressionsEnd() {
		return actionExpressionsEnd;
	}

	public void setActionExpressionsEnd(String[] actionExpressionsEnd) {
		this.actionExpressionsEnd = actionExpressionsEnd;
	}

	public Schedule getEndSchedule() {
		return endSchedule;
	}

	public void setEndSchedule(Schedule endSchedule) {
		this.endSchedule = endSchedule;
	}

	public boolean isEndAction() {
		return endAction;
	}

	public void setEndAction(boolean endAction) {
		this.endAction = endAction;
	}

}
