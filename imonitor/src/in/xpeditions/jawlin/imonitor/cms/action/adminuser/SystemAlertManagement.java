/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlertAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlertByUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class SystemAlertManagement extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2042756356309463050L;
	
	private User user;
	private List<SystemAlert> systemAlerts;
	private List<Object[]> systemAlertActionsByUsers;
	private String[] systemAlertId;
	private String[] sms;
	private String[] emal;
	

	@SuppressWarnings("unchecked")
	public String toAddSystemAlert() throws Exception {
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		XStream stream = new XStream();
		String valueInXml = stream.toXML(user);
		String serviceName = "systemAlertService";
		String method = "listSystemAlertByUser";
		String xml = IMonitorUtil.sendToController(serviceName,	method, valueInXml);
		this.systemAlertActionsByUsers = (List<Object[]>) stream.fromXML(xml);
		serviceName = "systemAlertService";
		method = "listSystemAlert";
		valueInXml = IMonitorUtil.sendToController(serviceName, method);
		this.systemAlerts = (List<SystemAlert>) stream.fromXML(valueInXml);
		for(SystemAlert sa:systemAlerts)
		{
			sa.setUtf8name(IMonitorUtil.formatMessage(this, sa.getName()));
		}
		return SUCCESS;
	}
	
	public String updateSystemAlert() throws Exception {
		XStream stream = new XStream();
		String message="";
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String valueInXml = stream.toXML(user);
		String smsXml = stream.toXML(sms);
		String emailXml = stream.toXML(emal);
		String serviceName = "systemAlertService";
		String method = "updateSystemAlert";
		String result = IMonitorUtil.sendToController(serviceName,method, valueInXml,smsXml,emailXml);
		if(result.equals("yes")){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.SystemAlert.0001");
			ActionContext.getContext().getSession().put("message", message);
		}
		return SUCCESS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<SystemAlert> getSystemAlerts() {
		return systemAlerts;
	}

	public void setSystemAlerts(List<SystemAlert> systemAlerts) {
		this.systemAlerts = systemAlerts;
	}

	public void setSystemAlertId(String[] systemAlertId) {
		this.systemAlertId = systemAlertId;
	}

	public String[] getSystemAlertId() {
		return systemAlertId;
	}

	

	public void setSms(String[] sms) {
		this.sms = sms;
	}

	public String[] getSms() {
		return sms;
	}

	public void setEmal(String[] emal) {
		this.emal = emal;
	}

	public String[] getEmal() {
		return emal;
	}

	public List<Object[]> getSystemAlertActionsByUsers() {
		return systemAlertActionsByUsers;
	}

	public void setSystemAlertActionsByUsers(
			List<Object[]> systemAlertActionsByUsers) {
		this.systemAlertActionsByUsers = systemAlertActionsByUsers;
	}

	


	
}
