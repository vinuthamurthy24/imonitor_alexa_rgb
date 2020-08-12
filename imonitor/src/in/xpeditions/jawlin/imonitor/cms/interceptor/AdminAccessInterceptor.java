/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.interceptor;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.Constants;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author Coladi
 *
 */
public class AdminAccessInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -148736756553508702L;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
	 */
	@Override
	public void destroy() {
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
	 */
	@Override
	public void init() {
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		if(null == user || user.getStatus().getName().compareTo(Constants.ONLINE) != 0){
			return Constants.SESSION_EXPIRED;
		}
		if(user.getRole().getName().compareTo(Constants.ADMIN_USER) != 0){
			return Constants.FORBIDDEN;
		}
//		Check whether the user is in logged-in state.
		return invocation.invoke();
	}

}
