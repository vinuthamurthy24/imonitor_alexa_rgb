package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceTypeModel;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class MultipleGatewayDevicesNonHmd extends ActionSupport
{
	private static final long serialVersionUID = 1507401089613192289L;
	private List<GateWay> gateWays;
	private User user;
	private GateWay gateWay;
	private List<DeviceTypeModel> deviceTypeModels;
	
	/* ********************************** sumit end : SUB USER RESTRICTION ********************************** */
	/**
	 * @author sumit kumar
	 * Date Modified: Jan 22, 2014 by Sumit Kumar.
	 * Modification: Filter the Devices based on the Role. If User's role is Normal User then filter the devices based on the Device Access as configured by Main User.
	 * 				 The Desired Devices are filtered based on the saved configuration.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		/* ************************************ ORIGINAL CODE *********************************
		 * XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString = stream.toXML(customer);
		String serviceName = "customerService";
		String method = "listGateWaysAndDevicesOfCustomer";
		String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.gateWays = (List<GateWay>) stream.fromXML(result);
		String valueInXml = stream.toXML(user.getId());
    	serviceName = "userService";
		method = "getUserById";
		result = IMonitorUtil.sendToController(serviceName, method, valueInXml);
		//User user1=(User)stream.fromXML(result);
		this.user=((User)stream.fromXML(result));
		//ActionContext.getContext().getSession().put(Constants.USER, user1);
		return SUCCESS;
		 */
		
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String roleName = user.getRole().getName();
		Customer customer = user.getCustomer();
		String xmlString = stream.toXML(customer);
		
		if(roleName.equalsIgnoreCase(Constants.NORMAL_USER)){
			String serviceName = "customerService";
			String method = "listGateWaysAndDevicesOfCustomerForSubUser";
			String result = IMonitorUtil.sendToController(serviceName, method, stream.toXML(user));
			this.gateWays = (List<GateWay>) stream.fromXML(result);
			String valueInXml = stream.toXML(user.getId());
	    	serviceName = "userService";
			method = "getUserById";
			result = IMonitorUtil.sendToController(serviceName, method, valueInXml);
			//User user1=(User)stream.fromXML(result);
			this.user=((User)stream.fromXML(result));
			//ActionContext.getContext().getSession().put(Constants.USER, user1);		
		}else{	
			String serviceName = "customerService";
			String method = "listGateWaysAndNonHmdDevicesForMultipleGateway";
			String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
			this.gateWays = (List<GateWay>) stream.fromXML(result);
			
			/*for (GateWay gateWay : gateWays)
			{
				
				LogUtil.info("Gateway for 3 accounts--- > "+stream.toXML(gateWay));
			}*/
			
			String valueInXml = stream.toXML(user.getId());
	    	serviceName = "userService";
			method = "getUserById";
			result = IMonitorUtil.sendToController(serviceName, method, valueInXml);
			this.user=((User)stream.fromXML(result));
		}
		return SUCCESS;
	}
	/* ********************************** sumit end : SUB USER RESTRICTION ********************************** */
	
	public List<GateWay> getGateWays() {
		return gateWays;
	}
	public void setGateWays(List<GateWay> gateWays) {
		this.gateWays = gateWays;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GateWay getGateWay() {
		return gateWay;
	}

	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}

	public List<DeviceTypeModel> getDeviceTypeModels() {
		return deviceTypeModels;
	}

	public void setDeviceTypeModels(List<DeviceTypeModel> deviceTypeModels) {
		this.deviceTypeModels = deviceTypeModels;
	}
}
