/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Panic;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class PanicManagement extends ActionSupport {


	/**
	 * 
	 */
	
	private static final long serialVersionUID = -3380398489319609365L;
	private List<Object[]> deviceAlertDetails;
	private List<Object[]> deviceActionDetails;
	private List<Object[]> userNotificationDetails;
	private List<Object[]> imvgAlertActions;
	private List<Object[]> imvgUnSelectedAlertNotifications;
	private List<Object[]> imvgSelectedAlertNotifications;
	private int[] minute;
	private Panic panic;
	private String action;
	private String[] notifications;
	private DataTable dataTable;

	
	public String updatePanic() throws Exception {
		String message ="";
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		if(notifications !=null || !this.action.equals("")){
		this.panic.setCustomer(user.getCustomer());
		XStream stream = new XStream();
		String ruleXml = stream.toXML(this.panic);
		String notificationXml = stream.toXML(this.notifications);
		String serviceName = "panicService";
		String method = "updatePanicWithActionAndNotification";
		IMonitorUtil.sendToController(serviceName, method, ruleXml, this.action, notificationXml);
		//message = "msg.imonitor.homepanic.0002 ";
		message = IMonitorUtil.formatMessage(this, "msg.imonitor.homepanic.0002");
		ActionContext.getContext().getSession().put("message",message);
		return SUCCESS;
		}else{
			//message = "Failure:Please select atleast one device action add email or sms notificaton ";
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.homepanic.0003");
			ActionContext.getContext().getSession().put("message",message);
			return ERROR;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String toEditPanic() throws Exception {
		String serviceName = "deviceService";
		String method = "listDeviceDetailsAlertTypesOfDevicesByCustomerId";
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String customerId = "" + user.getCustomer().getId();
		String result = IMonitorUtil.sendToController(serviceName, method, customerId);
		XStream stream  = new XStream();
		Object[] details = (Object[]) stream.fromXML(result);
		this.deviceAlertDetails = (List<Object[]>) details[0];
		this.deviceActionDetails = (List<Object[]>) details[1];
		this.userNotificationDetails = (List<Object[]>) details[2];
//		2. Get the rule as per the selected value.
		serviceName = "panicService";
		method = "getPanicAndAlertsByCustomerId";
		result = IMonitorUtil.sendToController(serviceName, method, "" + user.getCustomer().getId());
		this.panic = (Panic) stream.fromXML(result);
		return SUCCESS;
	}
	
	public List<Object[]> getDeviceAlertDetails() {
		return deviceAlertDetails;
	}

	public void setDeviceAlertDetails(List<Object[]> deviceAlertDetails) {
		this.deviceAlertDetails = deviceAlertDetails;
	}

	public List<Object[]> getDeviceActionDetails() {
		return deviceActionDetails;
	}

	public void setDeviceActionDetails(List<Object[]> deviceActionDetails) {
		this.deviceActionDetails = deviceActionDetails;
	}

	public List<Object[]> getUserNotificationDetails() {
		return userNotificationDetails;
	}

	public void setUserNotificationDetails(List<Object[]> userNotificationDetails) {
		this.userNotificationDetails = userNotificationDetails;
	}

	public final String getAction() {
		return action;
	}

	public final void setAction(String action) {
		this.action = action;
	}

	public final String[] getNotifications() {
		return notifications;
	}

	public final void setNotifications(String[] notifications) {
		this.notifications = notifications;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public List<Object[]> getImvgAlertActions() {
		return imvgAlertActions;
	}

	public void setImvgAlertActions(List<Object[]> imvgAlertActions) {
		this.imvgAlertActions = imvgAlertActions;
	}


	public List<Object[]> getImvgUnSelectedAlertNotifications() {
		return imvgUnSelectedAlertNotifications;
	}

	public void setImvgUnSelectedAlertNotifications(
			List<Object[]> imvgUnSelectedAlertNotifications) {
		this.imvgUnSelectedAlertNotifications = imvgUnSelectedAlertNotifications;
	}

	public List<Object[]> getImvgSelectedAlertNotifications() {
		return imvgSelectedAlertNotifications;
	}

	public void setImvgSelectedAlertNotifications(
			List<Object[]> imvgSelectedAlertNotifications) {
		this.imvgSelectedAlertNotifications = imvgSelectedAlertNotifications;
	}

	public void setMinute(int[] minute) {
		this.minute = minute;
	}

	public int[] getMinute() {
		return minute;
	}

	public void setPanic(Panic panic) {
		this.panic = panic;
	}

	public Panic getPanic() {
		return panic;
	}
	
}

