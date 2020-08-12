package in.xpeditions.jawlin.imonitor.cms.interceptor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;


public class AlertMonitorInterceptor implements Interceptor{
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		if(null == user || user.getStatus().getName().compareTo(Constants.ONLINE) != 0){
			return Constants.SESSION_EXPIRED;
		}
		if(user.getRole().getName().compareTo(Constants.ALERT_SERVICE) != 0){
			return Constants.FORBIDDEN;
		}
//		Check whether the user is in logged-in state.
		return invocation.invoke();
	}
}
