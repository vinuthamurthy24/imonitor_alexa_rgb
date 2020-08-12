package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Action;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.WallmoteConfiguration;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

public class WallmoteDeviceManagement extends ActionSupport{

	private List<WallmoteConfiguration> wallmoteConfig;
	private Device device;
	private WallmoteConfiguration key1;
	private WallmoteConfiguration key2;
	private WallmoteConfiguration key3;
	private WallmoteConfiguration key4;
	
	private List<Action> actions;
	
public String toWallmoteConfiguration() throws Exception {
		
		XStream stream = new XStream();
		try {
			
			User user = (User) ActionContext.getContext().getSession().get(
					Constants.USER);
			Customer customer =  user.getCustomer();
			
			String xmlDevice = stream.toXML(this.device);
			
			String serviceName = "deviceService";
			String method = "listWallmoteConfiguration";
			String result = IMonitorUtil.sendToController(serviceName, method,xmlDevice);
		   
			List<WallmoteConfiguration> wallmote = (List<WallmoteConfiguration>) stream.fromXML(result);
			key1=wallmote.get(0);
			key2=wallmote.get(1);
			key3=wallmote.get(2);
			key4=wallmote.get(3);
			String customerXml = stream.toXML(customer);
			//Action List
			
			
			
			String serviceName1 = "deviceService";
			String method1 = "listWallmoteActions";
			String result1 = IMonitorUtil.sendToController(serviceName1, method1,customerXml);
			//LogUtil.info("Result 1 : "+result1);
			 actions = (List<Action>) stream.fromXML(result1);
			 
			
			
		    
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SUCCESS;
	}

public String saveWallmoteConfiguration() throws Exception {
	XStream stream = new XStream();
	List<WallmoteConfiguration> keyconfigObjects=new ArrayList<WallmoteConfiguration>();
	keyconfigObjects.add(key1);
	
	keyconfigObjects.add(key2);
	
	keyconfigObjects.add(key3);
	
	keyconfigObjects.add(key4);
	
	String keyconfigObjectsXml = stream.toXML(keyconfigObjects);

	
	
	Device device=new Device();
	device.setId(key1.getDevice().getId());
	String xmlString = stream.toXML(device);
	
	
	
	String result = "";
	String message = "";
	String resultXml = IMonitorUtil.sendToController("deviceService", "saveWallMoteConfiguration", xmlString, keyconfigObjectsXml);
	result = (String) stream.fromXML(resultXml);

	if(result.equalsIgnoreCase(Constants.DB_SUCCESS))
	{
		message = "msg.imonitor.Wallmote.success.0001";
	}
	else
	{
		if(result.equalsIgnoreCase(Constants.DB_FAILURE))
		{
			message = "msg.imonitor.Wallmote.0001";
		}
		else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE))
		{
			message = "msg.imonitor.Wallmote.0002";
		}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY))
		{
			message = "msg.imonitor.Wallmote.0003";
		}
		else
		{
			message = "msg.imonitor.Wallmote.0004";
		}
		
	}
	message = IMonitorUtil.formatMessage(this, message);
	ActionContext.getContext().getSession().put("message", message);
	
	if(message.startsWith(Constants.MSG_FAILURE))
	return ERROR;
	else
	return SUCCESS;
}

	public WallmoteConfiguration getKey1() {
		return key1;
	}

	public void setKey1(WallmoteConfiguration key1) {
		this.key1 = key1;
	}

	public WallmoteConfiguration getKey2() {
		return key2;
	}

	public void setKey2(WallmoteConfiguration key2) {
		this.key2 = key2;
	}

	public WallmoteConfiguration getKey3() {
		return key3;
	}

	public void setKey3(WallmoteConfiguration key3) {
		this.key3 = key3;
	}

	public WallmoteConfiguration getKey4() {
		return key4;
	}

	public void setKey4(WallmoteConfiguration key4) {
		this.key4 = key4;
	}

	public List<WallmoteConfiguration> getWallmoteConfig() {
		return wallmoteConfig;
	}

	public void setWallmoteConfig(List<WallmoteConfiguration> wallmoteConfig) {
		this.wallmoteConfig = wallmoteConfig;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	
	
}
