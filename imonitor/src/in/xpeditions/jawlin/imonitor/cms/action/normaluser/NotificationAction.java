/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;


import java.util.List;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsService;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class NotificationAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1490861107030149495L;
	
	private UserNotificationProfile systemNotificaion;
	private List<ActionType> systemActions;
	private DataTable dataTable;
	private String optin;
	private SmsService smsservie;
	private boolean checkWhatsApp;
	

	private long Otp;

	public String toNotifications() throws Exception {
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String toAddNotification() throws Exception {
		XStream stream = new XStream();
		String result = IMonitorUtil.sendToController("actionTypeService", "getSystemActions");
		this.systemActions = (List<ActionType>) stream.fromXML(result);
		result = IMonitorUtil.sendToController("serverConfiguration", "getSelectedSMSServiceProvider");
		if(result != null)
		this.smsservie = (SmsService) stream.fromXML(result);
		else
		LogUtil.info("Server operator is NULL");
		/*if(smsservie!=null)
		{
			String Message="setup.not.optin"+ Constants.MESSAGE_DELIMITER_1 + smsservie.getOptin()+Constants.MESSAGE_DELIMITER_2 +smsservie.getSubnumber()+Constants.MESSAGE_DELIMITER_2+smsservie.getSubnumber()+
					Constants.MESSAGE_DELIMITER_2+smsservie.getOptout()+Constants.MESSAGE_DELIMITER_2+smsservie.getSubnumber()+Constants.MESSAGE_DELIMITER_2+smsservie.getSubnumber();
			
			this.optin=IMonitorUtil.formatMessage(this,Message);
		}
		else
		{
			this.optin="";
		}*/
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String toEditSystemNotification() throws Exception {
		XStream stream = new XStream();
		String result = IMonitorUtil.sendToController("actionTypeService", "getSystemActions");
		this.systemActions = (List<ActionType>) stream.fromXML(result);
		String systemNotificationId = stream.toXML(this.systemNotificaion.getId());
		String notificationXml = IMonitorUtil.sendToController("systemNotificaionService", "getSystemNotificaion", systemNotificationId);
		this.systemNotificaion = (UserNotificationProfile) stream.fromXML(notificationXml);
		if(!this.systemNotificaion.getWhatsApp().equalsIgnoreCase("")){
			this.checkWhatsApp = true;
			
		}
		
		result = IMonitorUtil.sendToController("serverConfiguration", "getSelectedSMSServiceProvider");
		if(result != null)
		this.smsservie = (SmsService) stream.fromXML(result);
		else
		LogUtil.info("Server operator is NULL");
		
		return SUCCESS;
	}

	public String addNotification() throws Exception {
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		if(this.checkWhatsApp){
			
			this.systemNotificaion.setWhatsAppStatus(1);
		}
		
		this.systemNotificaion.setCustomer(user.getCustomer());
		String email= this.systemNotificaion.getEMail();
		String sms= this.systemNotificaion.getSMS();
		/* Naveen changed to for system notification feature*/
		ActionType actionType = new ActionType();	
		
		if((!email.equals("")) && (!sms.equals(""))){
		
			String actionName = stream.toXML(Constants.SEND_SMS_EMAIL);
			String action = IMonitorUtil.sendToController("actionTypeService", "getActiontypebyselectedName", actionName);
			actionType = (ActionType) stream.fromXML(action);
			this.systemNotificaion.setActionType(actionType);
		}else if ((!email.equals("")) && (sms.equals(""))){
			String actionName = stream.toXML(Constants.SEND_EMAIL);
			String action = IMonitorUtil.sendToController("actionTypeService", "getActiontypebyselectedName", actionName);
			actionType = (ActionType) stream.fromXML(action);
			this.systemNotificaion.setActionType(actionType);
			
		}else if((email.equals("")) && (!sms.equals(""))){
			String actionName = stream.toXML(Constants.SEND_SMS);
			String action = IMonitorUtil.sendToController("actionTypeService", "getActiontypebyselectedName", actionName);
			actionType = (ActionType) stream.fromXML(action);
			this.systemNotificaion.setActionType(actionType);
			
			
		}else{
			String actionName = stream.toXML(Constants.WHATSAPP);
			String action = IMonitorUtil.sendToController("actionTypeService", "getActiontypebyselectedName", actionName);
			actionType = (ActionType) stream.fromXML(action);
			this.systemNotificaion.setActionType(actionType);
			
		}
				
		/*   Naveen end */
		String xml = stream.toXML(this.systemNotificaion);
		String result = IMonitorUtil.sendToController("systemNotificaionService", "saveSystemNotificaion", xml);
		String message = IMonitorUtil.formatMessage(this, "msg.imonitor.notifications.0001");
		if(result.equals("yes")){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.notifications.0002");
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		else{
			message = IMonitorUtil.formatFailureMessage(this, "msg.imonitor.notifications.0003"+Constants.MESSAGE_DELIMITER_1+systemNotificaion.getName());
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
	}
	
	public String editSystemNotification() throws Exception {
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		this.systemNotificaion.setCustomer(user.getCustomer());
		this.systemNotificaion.setRecipient(null);
		String email= this.systemNotificaion.getEMail();
		String sms= this.systemNotificaion.getSMS();
        if(this.checkWhatsApp){
			
			this.systemNotificaion.setWhatsAppStatus(1);
		}else{
			this.systemNotificaion.setWhatsAppStatus(0);
			
		}
		/* Naveen changed to for system notification feature*/
		ActionType actionType = new ActionType();	
		if((!email.equals("")) && (!sms.equals(""))){
			
			String actionName = stream.toXML(Constants.SEND_SMS_EMAIL);
			String action = IMonitorUtil.sendToController("actionTypeService", "getActiontypebyselectedName", actionName);
			actionType = (ActionType) stream.fromXML(action);
			this.systemNotificaion.setActionType(actionType);
		}else if ((!email.equals("")) && (sms.equals(""))){
			String actionName = stream.toXML(Constants.SEND_EMAIL);
			String action = IMonitorUtil.sendToController("actionTypeService", "getActiontypebyselectedName", actionName);
			actionType = (ActionType) stream.fromXML(action);
			this.systemNotificaion.setActionType(actionType);
			
		}else if((email.equals("")) && (!sms.equals(""))){
			String actionName = stream.toXML(Constants.SEND_SMS);
			String action = IMonitorUtil.sendToController("actionTypeService", "getActiontypebyselectedName", actionName);
			actionType = (ActionType) stream.fromXML(action);
			this.systemNotificaion.setActionType(actionType);
			
			
		}else{
			String actionName = stream.toXML(Constants.WHATSAPP);
			String action = IMonitorUtil.sendToController("actionTypeService", "getActiontypebyselectedName", actionName);
			String xml = stream.toXML(Constants.NOT_ENABLED);
			String statusxml = IMonitorUtil.sendToController("statusService", "getStatusByName", xml);
			Status status = (Status) stream.fromXML(statusxml);
			actionType = (ActionType) stream.fromXML(action);
			this.systemNotificaion.setStatus(status);
			this.systemNotificaion.setActionType(actionType);
			
		}
		/*   Naveen end */
		String xml = stream.toXML(this.systemNotificaion);
		String result = IMonitorUtil.sendToController("systemNotificaionService", "editSystemNotificaion", xml);
		String message = IMonitorUtil.formatMessage(this, "msg.imonitor.notifications.0004");
		if(result.equals("yes")){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.notifications.0005");
			ActionContext.getContext().getSession().put("message",message);
			return SUCCESS;
		}
		else{
			message = IMonitorUtil.formatFailureMessage(this, "msg.imonitor.notifications.0003"+Constants.MESSAGE_DELIMITER_1+systemNotificaion.getName());
			ActionContext.getContext().getSession().put("message",message);
			return ERROR;
		}
	}
	
	public String deleteSystemNotification() throws Exception {
		XStream stream = new XStream();
		String xml = stream.toXML(this.systemNotificaion);
		String result = IMonitorUtil.sendToController("systemNotificaionService", "deleteSystemNotificaion", xml);
		//vibhu start
		String message = IMonitorUtil.formatMessage(this, "msg.imonitor.notifications.0006");
		if(result.equals("success")){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.notifications.0007");
			ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
		else{
			message = IMonitorUtil.formatMessage(this, "Failure:"+"msg.imonitor.notifications.0008");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		
		//vibhu end
	}
	
	public String listAskedSystemNotifications() throws Exception {
		XStream stream = new XStream();
		String xmlDataTable = stream.toXML(this.dataTable);
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String customerIdXml = stream.toXML(user.getCustomer().getId());
		String serviceName = "systemNotificaionService";
		String method = "listAskedSystemNotificaions";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlDataTable, customerIdXml);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	public String toVerifyMobileNumber() throws Exception{
		XStream stream = new XStream();
		long id = this.systemNotificaion.getId();
		String xml = stream.toXML(id);
		String resultXml = IMonitorUtil.sendToController("systemNotificaionService", "toVerfifyMobile", xml);
		this.systemNotificaion = (UserNotificationProfile) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	
	public String verifyMobileNumber() throws Exception{
		XStream stream = new XStream();
		String message = IMonitorUtil.formatMessage(this, "general.notification.otp.001"+Constants.MESSAGE_DELIMITER_1+this.systemNotificaion.getSMS());
		long generatedOtp = this.systemNotificaion.getOTP();
		long enteredOtp = this.Otp;
		if(generatedOtp == enteredOtp){
			
			String xml = stream.toXML(this.systemNotificaion.getId());
			String result = IMonitorUtil.sendToController("systemNotificaionService", "VerfifyAndUpdateNumber", xml);
			if(result.equalsIgnoreCase("no")){
				message = IMonitorUtil.formatMessage(this, "general.notofication.001");
				ActionContext.getContext().getSession().put("message", message);
			}
			ActionContext.getContext().getSession().put("message", message);
		}else{
			
			message = IMonitorUtil.formatMessage(this, "general.notofication.otp.002");
			ActionContext.getContext().getSession().put("message", message);
		}
				
		return SUCCESS;
	}
	
	public String regenerateOtp() throws Exception{
		
		
		
		return SUCCESS;
	}

	public UserNotificationProfile getSystemNotificaion() {
		return systemNotificaion;
	}

	public void setSystemNotificaion(UserNotificationProfile systemNotificaion) {
		this.systemNotificaion = systemNotificaion;
	}

	public List<ActionType> getSystemActions() {
		return systemActions;
	}

	public void setSystemActions(List<ActionType> systemActions) {
		this.systemActions = systemActions;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public String getOptin() {
		return optin;
	}

	public void setOptin(String Optin) {
		optin = Optin;
	}

	public SmsService getSmsservie() {
		return smsservie;
	}

	public void setSmsservie(SmsService smsservie) {
		this.smsservie = smsservie;
	}

	public boolean isCheckWhatsApp() {
		return checkWhatsApp;
	}

	public void setCheckWhatsApp(boolean checkWhatsApp) {
		this.checkWhatsApp = checkWhatsApp;
	}

	

	public long getOtp() {
		return Otp;
	}

	public void setOtp(long otp) {
		Otp = otp;
	}

	
}
