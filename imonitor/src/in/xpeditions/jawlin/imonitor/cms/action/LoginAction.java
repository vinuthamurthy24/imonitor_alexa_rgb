/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action;

import java.util.Locale;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class LoginAction extends ActionSupport implements SessionAware {

	/**
	 * @author Asmodeus
	 */
	private static final long serialVersionUID = 1L;
	private String reqLocale = null;
	private Map<String, Object> session;
	private String customerId;
	private String UserId;
	private String password;
	  
	private User user;
	public String execute() throws Exception{
		String result = "";
		XStream stream = new XStream();
		String hashPassword = IMonitorUtil.getHashPassword(this.user.getPassword());
		this.user.setPassword(hashPassword);
		String xmlString = stream.toXML(this.user);
		String serviceName = "loginService";
		String method = "loginCheck";
		String resultValue=(String) IMonitorUtil.sendToController(serviceName, method, xmlString);
		if(resultValue.compareTo(Constants.LOGIN_FAIL_MESSAGE) == 0){
			ActionContext.getContext().getSession().put(Constants.MESSAGE,Constants.LOGIN_FAIL_MESSAGE);
			result=ERROR;
		}
		else {
			Locale locale = new Locale("en", "US");
			if(reqLocale == null)
			{
				locale = new Locale("en", "US");
			}
			else
			{
				if(reqLocale.contains("_"))
				{
					String t[] = reqLocale.split("_");
					locale = new Locale(t[0], t[1]);
				}
				else
					locale = new Locale(reqLocale);
			}
			ActionContext.getContext().setLocale(locale);
			ActionContext.getContext().getSession().put(Constants.LOCALE,locale);
			
			
			User user1=(User)stream.fromXML(resultValue);
			ActionContext.getContext().getSession().put(Constants.USER, user1);
			session.put("loginId", user1.getUserId());
			session.put("userlogin",2);
			String roleName=user1.getRole().getName();
			if(roleName.equals(Constants.MAIN_USER)){
				result=Constants.MAIN_USER;
				//sumit start: [Sept 06, 2012] Notify mainuser if system alert is configured. 
				String serviceName1 = "userService";
				String method1 = "notifyMainUserBySmsAndEmail";
				String userXml = stream.toXML(user1);
				String alertXml = stream.toXML(Constants.LOG_IN);
				IMonitorUtil.sendToController(serviceName1, method1, userXml, alertXml);
				//sumit end:
			}
			else if(roleName.equals(Constants.NORMAL_USER)){
				String serviceName2 = "userService";
				Device device = null;
				String method2 = "getMainUserAndSendSmsAndEmailing";
				String actionXml = stream.toXML(Constants.LOG_IN);
				String devceXmlString = stream.toXML(device);
				String xmlMessage = stream.toXML(user1.getUserId()+" : is online ");
				String xmlString2 = stream.toXML(user1.getCustomer().getId());
				IMonitorUtil.sendToController(serviceName2, method2, xmlString2,xmlMessage,actionXml,devceXmlString);
				result=Constants.NORMAL_USER;
			}
			else if(roleName.equals(Constants.ADMIN_USER)){
				result=Constants.ADMIN_USER;
			}
			
		}
		return result;
	}
public String adminLogin() throws Exception{
	String result = "";
	XStream stream = new XStream();
	String hashPassword = IMonitorUtil.getHashPassword(this.user.getPassword());
	this.user.setPassword(hashPassword);
	String xmlString = stream.toXML(this.user);
	String serviceName = "loginService";
	String method = "adminLoginCheck";
	String resultValue=(String) IMonitorUtil.sendToController(serviceName, method, xmlString);
	if(resultValue.compareTo(Constants.LOGIN_FAIL_MESSAGE_ADMIN) == 0){
		ActionContext.getContext().getSession().put(Constants.MESSAGE,Constants.LOGIN_FAIL_MESSAGE_ADMIN);
		result=ERROR;
	}
	else {
		User user1=(User)stream.fromXML(resultValue);
		ActionContext.getContext().getSession().put(Constants.USER, user1);
		String roleName=user1.getRole().getName();
	    if(roleName.equals(Constants.ADMIN_USER)){
			result=Constants.ADMIN_USER;
		}
		
	}
	return result;
}


public String supportLogin() throws Exception{
	String result = "";
	XStream stream = new XStream();
	String hashPassword = IMonitorUtil.getHashPassword(this.user.getPassword());
	this.user.setPassword(hashPassword);
	String xmlString = stream.toXML(this.user);
	String serviceName = "loginService";
	String method = "supportlogincheck";
	String resultValue=(String) IMonitorUtil.sendToController(serviceName, method, xmlString);
	if(resultValue.compareTo(Constants.LOGIN_FAIL_MESSAGE_ADMIN) == 0){
		ActionContext.getContext().getSession().put(Constants.MESSAGE,Constants.LOGIN_FAIL_MESSAGE_ADMIN);
		result=ERROR;
	}
	else {
		User user1=(User)stream.fromXML(resultValue);
		ActionContext.getContext().getSession().put(Constants.USER, user1);
		String roleName=user1.getRole().getName();
		if(roleName.equals(Constants.SUPPORT_USER)){
			result=Constants.SUPPORT_USER;
		}
		
	}
	return result;
}

public String alertServiceLogin() throws Exception{
	String result = "";
	XStream stream = new XStream();
	String hashPassword = IMonitorUtil.getHashPassword(this.user.getPassword());
	this.user.setPassword(hashPassword);
	String xmlString = stream.toXML(this.user);
	String serviceName = "loginService";
	String method = "alertServicelogincheck";
	String resultValue=(String) IMonitorUtil.sendToController(serviceName, method, xmlString);
	if(resultValue.compareTo(Constants.LOGIN_FAIL_MESSAGE_ADMIN) == 0){
		ActionContext.getContext().getSession().put(Constants.MESSAGE,Constants.LOGIN_FAIL_MESSAGE_ADMIN);
		result=ERROR;
	}
	else {
		User user1=(User)stream.fromXML(resultValue);
		ActionContext.getContext().getSession().put(Constants.USER, user1);
		String roleName=user1.getRole().getName();
		if(roleName.equals(Constants.ALERT_SERVICE)){
			result=Constants.ALERT_SERVICE;
		}
		
	}
	return result;
}

public String api()throws Exception{
	String result = "";
	XStream stream = new XStream();
	String hashPassword = this.getPassword();
	String custid=this.getCustomerId();
	String UserId=this.getUserId();
	String serviceName = "loginService";
	String method = "loginCheckapi";
	String resultValue=(String) IMonitorUtil.sendToController(serviceName, method,UserId,hashPassword,custid);
	if(resultValue.compareTo(Constants.LOGIN_FAIL_MESSAGE) == 0){
		ActionContext.getContext().getSession().put(Constants.MESSAGE,Constants.LOGIN_FAIL_MESSAGE);
		result=ERROR;
	}
	else {
		Locale locale = new Locale("en", "US");
		if(reqLocale == null)
		{
			locale = new Locale("en", "US");
		}
		else
		{
			if(reqLocale.contains("_"))
			{
				String t[] = reqLocale.split("_");
				locale = new Locale(t[0], t[1]);
			}
			else
				locale = new Locale(reqLocale);
		}
		ActionContext.getContext().setLocale(locale);
		ActionContext.getContext().getSession().put(Constants.LOCALE,locale);
		
		
		User user1=(User)stream.fromXML(resultValue);
		ActionContext.getContext().getSession().put(Constants.USER, user1);
		session.put("loginId", user1.getUserId());
		session.put("userlogin",2);
		result="mainuser";		
	}
		return result;
}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getReqLocale() {
		return reqLocale;
	}
	public void setReqLocale(String reqLocale) {
		this.reqLocale = reqLocale;
	}
	
	public Map<String,Object> getSession(){
		
		return session;
	}
	
	public void setSession(Map<String, Object> map){
		
		this.session = map;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
