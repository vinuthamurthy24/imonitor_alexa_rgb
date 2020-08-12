/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Asmodeus
 *
 */
public class LogoutAction extends ActionSupport {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public String execute() throws Exception{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		if(user == null){
			return SUCCESS;
		}
		String valueInXml = stream.toXML(user);
		String roleName=user.getRole().getName();
		if(roleName.equals(Constants.NORMAL_USER)){
			/*String serviceName2 = "userService";
			String method2 = "getMainUserAndSendSmsAndEmailing";
			String xmlMessage = stream.toXML(user.getUserId()+":is offline ");
			String xmlString2 = stream.toXML(user.getCustomer().getId());
			IMonitorUtil.sendToController(serviceName2, method2, xmlString2,xmlMessage);*/
		}
		ActionContext.getContext().getSession().remove(Constants.USER);
		ActionContext.getContext().getSession().remove(Constants.LOCALE);
	    String serviceName = "loginService";
		String method = "logout";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, valueInXml);
		String result = (String) stream.fromXML(resultXml);
		
		
		if(result.equalsIgnoreCase("admin")){
			return "admin";
		}else if(result.equalsIgnoreCase("supportuser")){
			return "supportuser";
		}
	    return SUCCESS;
	}
}
