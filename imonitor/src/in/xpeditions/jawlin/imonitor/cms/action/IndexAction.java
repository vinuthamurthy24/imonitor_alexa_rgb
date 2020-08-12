/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Coladi
 *
 */
public class IndexAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8949636762932518611L;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String index() throws Exception {
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		if(null == user){
			return SUCCESS;
		}else{
			return user.getRole().getName();
		}
	}
	
	public String admin() throws Exception {
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		if(null == user){
			return SUCCESS;
		}else{
			return user.getRole().getName();
		}
	}
	
	public String support() throws Exception {
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		if(null == user){
			return SUCCESS;
		}else{
			return user.getRole().getName();
		}
	}
	
	public String monitorAlert() throws Exception {
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		if(null == user){
			return SUCCESS;
		}else{
			return user.getRole().getName();
		}
	}
}
