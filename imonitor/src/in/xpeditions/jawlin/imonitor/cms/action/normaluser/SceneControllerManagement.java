package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Action;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyObjects;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

public class SceneControllerManagement extends ActionSupport {
	
	
	private Device device;
	private List<KeyConfiguration> keyConfig;
	private List<KeyObjects> keyObjects;
	private KeyConfiguration key1;
	private KeyConfiguration key12;
	private KeyConfiguration key2;
	private KeyConfiguration key22;
	private KeyConfiguration key3;
	private KeyConfiguration key32;
	private KeyConfiguration key4;
	private KeyConfiguration key42;
	private KeyConfiguration key5;
	private KeyConfiguration key52;
	private KeyConfiguration key6;
	private KeyConfiguration key62;
	private KeyConfiguration key7;
	private KeyConfiguration key72;
	private KeyConfiguration key8;
	private KeyConfiguration key82;
	
	private List<Action> actions;
	
	//3gp
	private GateWay gateWay;
	
	
	public GateWay getGateWay() {
		return gateWay;
	}


	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}


	@SuppressWarnings("unchecked")
	public String toSceneControlConfiguration() throws Exception {
		try {
			User user = (User) ActionContext.getContext().getSession().get(
					Constants.USER);
			Customer customer =  user.getCustomer();
			
		
			XStream stream = new XStream();
			
			
			//KeyConfiguration List
			String xmlDevice = stream.toXML(this.device);
			String serviceName = "deviceService";
			String method = "listAllKeyConfiguration";
			String result = IMonitorUtil.sendToController(serviceName, method,xmlDevice);
			this.keyConfig = (List<KeyConfiguration>)stream.fromXML(result);
			//String resultGateWay = IMonitorUtil.sendToController("deviceService", "getGateWay",customerIdXml);
		//	LogUtil.info("key objects from method is "+result);
			
			List<KeyConfiguration> keyconfigObjects = (List<KeyConfiguration>) stream.fromXML(result);
			
			key1=keyconfigObjects.get(0);
			key12=keyconfigObjects.get(1);
			key2=keyconfigObjects.get(2);
			key22=keyconfigObjects.get(3);
			key3=keyconfigObjects.get(4);
			key32=keyconfigObjects.get(5);
			key4=keyconfigObjects.get(6);
			key42=keyconfigObjects.get(7);
			key5=keyconfigObjects.get(8);
			key52=keyconfigObjects.get(9);
			key6=keyconfigObjects.get(10);
			key62=keyconfigObjects.get(11);
			key7=keyconfigObjects.get(12);
			key72=keyconfigObjects.get(13);
			key8=keyconfigObjects.get(14);
			key82=keyconfigObjects.get(15);
		
			String customerXml = stream.toXML(customer);
			//Action List
		
			
			
			String serviceName1 = "deviceService";
			String method1 = "listAllActions";
			String result1 = IMonitorUtil.sendToController(serviceName1, method1,customerXml);
			
			 actions = (List<Action>) stream.fromXML(result1);
			 
			 if (actions.isEmpty())
			 {
				
				
					return ERROR;
			 }
			
					
		} catch (Exception e) {
			LogUtil.info("EX", e);
		}
		
		return SUCCESS;
	}
	
	
	public String saveSceneControlConfiguration() throws Exception {
		XStream stream = new XStream();
		List<KeyConfiguration> keyconfigObjects=new ArrayList<KeyConfiguration>();
		keyconfigObjects.add(key1);
		keyconfigObjects.add(key12);
		keyconfigObjects.add(key2);
		keyconfigObjects.add(key22);
		keyconfigObjects.add(key3);
		keyconfigObjects.add(key32);
		keyconfigObjects.add(key4);
		keyconfigObjects.add(key42);
		keyconfigObjects.add(key5);
		keyconfigObjects.add(key52);
		keyconfigObjects.add(key6);
		keyconfigObjects.add(key62);
		keyconfigObjects.add(key7);
		keyconfigObjects.add(key72);
		keyconfigObjects.add(key8);
		keyconfigObjects.add(key82);
		String keyconfigObjectsXml = stream.toXML(keyconfigObjects);

		
		
		Device device=new Device();
		device.setId(key1.getDevice().getId());
		String xmlString = stream.toXML(device);
		
		
		
		String result = "";
		String message = "";
		String resultXml = IMonitorUtil.sendToController("deviceService", "saveSceneControllerConfiguration", xmlString, keyconfigObjectsXml);
		result = (String) stream.fromXML(resultXml);

		if(result.equalsIgnoreCase(Constants.DB_SUCCESS))
		{
			message = "msg.imonitor.Sceneconfiguration.success.0001";
		}
		else
		{
			if(result.equalsIgnoreCase(Constants.DB_FAILURE))
			{
				message = "msg.imonitor.Sceneconfiguration.0001";
			}
			else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE))
			{
				message = "msg.imonitor.Sceneconfiguration.0002";
			}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY))
			{
				message = "msg.imonitor.Sceneconfiguration.0003";
			}
			else
			{
				message = "msg.imonitor.Sceneconfiguration.0004";
			}
			
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		
		if(message.startsWith(Constants.MSG_FAILURE))
		return ERROR;
		else
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String toKeyfobeConfiguration() throws Exception {
		try {
			User user = (User) ActionContext.getContext().getSession().get(
					Constants.USER);
			Customer customer =  user.getCustomer();
			
		
			XStream stream = new XStream();
			
			
			//KeyConfiguration List
			String xmlDevice = stream.toXML(this.device);
			String serviceName = "deviceService";
			String method = "listAllKeyConfiguration";
			String result = IMonitorUtil.sendToController(serviceName, method,xmlDevice);
			this.keyConfig = (List<KeyConfiguration>)stream.fromXML(result);
			
			
			List<KeyConfiguration> keyconfigObjects = (List<KeyConfiguration>) stream.fromXML(result);
			
			key1=keyconfigObjects.get(0);
			key12=keyconfigObjects.get(1);
			key2=keyconfigObjects.get(2);
			key22=keyconfigObjects.get(3);
			key3=keyconfigObjects.get(4);
			key32=keyconfigObjects.get(5);
			key4=keyconfigObjects.get(6);
			key42=keyconfigObjects.get(7);
			key5=keyconfigObjects.get(8);
			key52=keyconfigObjects.get(9);
			key6=keyconfigObjects.get(10);
			key62=keyconfigObjects.get(11);
			
		
			String customerXml = stream.toXML(customer);
			//Action List
			
			
			
			String serviceName1 = "deviceService";
			String method1 = "listAllActions";
			String result1 = IMonitorUtil.sendToController(serviceName1, method1,customerXml);
			//LogUtil.info("Result 1 : "+result1);
			 actions = (List<Action>) stream.fromXML(result1);
			 
			 if (actions.isEmpty())
			 {
				
				/* String message = "No Actions Configured";
				 message = IMonitorUtil.formatMessage(this, message);
					ActionContext.getContext().getSession().put("message", message);*/
					return ERROR;
			 }
			
			
			
			
			
		} catch (Exception e) {
			LogUtil.info("EX", e);
		}
		
		return SUCCESS;
	}
	
	public String saveKeyfobeConfiguration() throws Exception {
		XStream stream = new XStream();
		List<KeyConfiguration> keyconfigObjects=new ArrayList<KeyConfiguration>();
		
		keyconfigObjects.add(key1);
		keyconfigObjects.add(key12);
		keyconfigObjects.add(key2);
		keyconfigObjects.add(key22);
		keyconfigObjects.add(key3);
		keyconfigObjects.add(key32);
		keyconfigObjects.add(key4);
		keyconfigObjects.add(key42);
		keyconfigObjects.add(key5);
		keyconfigObjects.add(key52);
		keyconfigObjects.add(key6);
		keyconfigObjects.add(key62);
		
		String keyconfigObjectsXml = stream.toXML(keyconfigObjects);

		
		
		Device device=new Device();
		device.setId(key1.getDevice().getId());
		
		String xmlString = stream.toXML(device);
		
		
		
		String result = "";
		String message = "";
	
		String resultXml = IMonitorUtil.sendToController("deviceService", "saveKeyFobeConfiguration", xmlString, keyconfigObjectsXml);
		result = (String) stream.fromXML(resultXml);

		if(result.equalsIgnoreCase(Constants.DB_SUCCESS))
		{
			message = "msg.imonitor.keyfobeconfiguration.success.0001";
		}
		else
		{
			if(result.equalsIgnoreCase(Constants.DB_FAILURE))
			{
				message = "msg.imonitor.keyfobeconfiguration.0001";
			}
			else if(result.equalsIgnoreCase(Constants.iMVG_FAILURE))
			{
				message = "msg.imonitor.keyfobeconfiguration.0002";
			}else if(result.equalsIgnoreCase(Constants.NO_RESPONSE_FROM_GATEWAY))
			{
				message = "msg.imonitor.keyfobeconfiguration.0003";
			}
			else
			{
				message = "msg.imonitor.keyfobeconfiguration.0004";
			}
			
		}
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		
		if(message.startsWith(Constants.MSG_FAILURE))
		return ERROR;
		else
		return SUCCESS;
	}

	public KeyConfiguration getKey1() {
		return key1;
	}

	public void setKey1(KeyConfiguration key1) {
		this.key1 = key1;
	}

	public KeyConfiguration getKey12() {
		return key12;
	}

	public void setKey12(KeyConfiguration key12) {
		this.key12 = key12;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public List<KeyConfiguration> getKeyConfig() {
		return keyConfig;
	}

	public void setKeyConfig(List<KeyConfiguration> keyConfig) {
		this.keyConfig = keyConfig;
	}

	public List<KeyObjects> getKeyObjects() {
		return keyObjects;
	}

	public void setKeyObjects(List<KeyObjects> keyObjects) {
		this.keyObjects = keyObjects;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public KeyConfiguration getKey2() {
		return key2;
	}

	public void setKey2(KeyConfiguration key2) {
		this.key2 = key2;
	}

	public KeyConfiguration getKey22() {
		return key22;
	}

	public void setKey22(KeyConfiguration key22) {
		this.key22 = key22;
	}

	public KeyConfiguration getKey3() {
		return key3;
	}

	public void setKey3(KeyConfiguration key3) {
		this.key3 = key3;
	}

	public KeyConfiguration getKey32() {
		return key32;
	}

	public void setKey32(KeyConfiguration key32) {
		this.key32 = key32;
	}

	public KeyConfiguration getKey4() {
		return key4;
	}

	public void setKey4(KeyConfiguration key4) {
		this.key4 = key4;
	}

	public KeyConfiguration getKey42() {
		return key42;
	}

	public void setKey42(KeyConfiguration key42) {
		this.key42 = key42;
	}

	public KeyConfiguration getKey5() {
		return key5;
	}

	public void setKey5(KeyConfiguration key5) {
		this.key5 = key5;
	}

	public KeyConfiguration getKey52() {
		return key52;
	}

	public void setKey52(KeyConfiguration key52) {
		this.key52 = key52;
	}

	public KeyConfiguration getKey6() {
		return key6;
	}

	public void setKey6(KeyConfiguration key6) {
		this.key6 = key6;
	}

	public KeyConfiguration getKey62() {
		return key62;
	}

	public void setKey62(KeyConfiguration key62) {
		this.key62 = key62;
	}

	public KeyConfiguration getKey7() {
		return key7;
	}

	public void setKey7(KeyConfiguration key7) {
		this.key7 = key7;
	}

	public KeyConfiguration getKey72() {
		return key72;
	}

	public void setKey72(KeyConfiguration key72) {
		this.key72 = key72;
	}

	public KeyConfiguration getKey8() {
		return key8;
	}

	public void setKey8(KeyConfiguration key8) {
		this.key8 = key8;
	}

	public KeyConfiguration getKey82() {
		return key82;
	}

	public void setKey82(KeyConfiguration key82) {
		this.key82 = key82;
	}
	
	

}
