/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScenarioAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemIcon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class ScenariosActions extends ActionSupport {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8210868544294313562L;
	private Set<GateWay> gateWays;
	private Scenario scenario;
	private String[] actionExpressions;
	private DataTable dataTable;
	private List<Scenario> scenarios;
	private List<SystemIcon> listIcons;
	private User user;

	public String execute() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Long id =  user.getCustomer().getId();
		String serviceName = "scenarioService";
		String method = "listAskedScnarios";
		String idXml = stream.toXML(id);;
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString, idXml);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}

	/**
	 * @author vibhuti
	 * @return
	 * @throws Exception
	 * Date Modified: Jan 22, 2014 by Sumit Kumar.
	 * Modification: Restricting Scenario Access to Sub Users based on the Scenario Access Configuration done by the Main User.
	 * 				 The Desired Scenarios are filtered based on the saved configuration.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String getScenarioDetails() throws Exception
	{
		String returnVal = ERROR;
		/* ********************************** sumit start : SUB USER RESTRICTION ************************* */
		try{
			XStream stream = new XStream();
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			String roleName = user.getRole().getName();
			if(roleName.equalsIgnoreCase(Constants.NORMAL_USER)){
				//Get on the the filtered Scenarios configured for the Normal User.
				String idXML = stream.toXML(user.getCustomer().getId());
				String result = IMonitorUtil.sendToController("scenarioService", "listAllScnariosForSubUser", stream.toXML(user));
				this.scenarios = (List<Scenario>) stream.fromXML(result);
				result = IMonitorUtil.sendToController("customerService", "getCustomerById", idXML);
				Customer customer = (Customer) stream.fromXML(result);
				this.gateWays = customer.getGateWays();
				String valueInXml = stream.toXML(user.getId());
		    	String serviceName = "userService";
				String method = "getUserById";
				result = IMonitorUtil.sendToController(serviceName, method, valueInXml);
				this.setUser(((User)stream.fromXML(result)));
				returnVal = SUCCESS ;
			}else{
				//Get All the Scenarios for the Customer Account.
				String idXML = stream.toXML(user.getCustomer().getId());
				String result = IMonitorUtil.sendToController("scenarioService", "listAllScnarios", idXML);
				this.scenarios = (List<Scenario>) stream.fromXML(result);
				result = IMonitorUtil.sendToController("customerService", "getCustomerById", idXML);
				Customer customer = (Customer) stream.fromXML(result);
				this.gateWays = customer.getGateWays();
				String valueInXml = stream.toXML(user.getId());
		    	String serviceName = "userService";
				String method = "getUserById";
				result = IMonitorUtil.sendToController(serviceName, method, valueInXml);
				this.setUser(((User)stream.fromXML(result)));
				returnVal = SUCCESS ;
			}
			
			
			
			
			/* ******************************************** ORIGINAL CODE *************************************
			 * User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			String idXML = stream.toXML(user.getCustomer().getId());
			String result = IMonitorUtil.sendToController("scenarioService", "listAllScnarios", idXML);
			this.scenarios = (List<Scenario>) stream.fromXML(result);
			result = IMonitorUtil.sendToController("customerService", "getCustomerById", idXML);
			Customer customer = (Customer) stream.fromXML(result);
			this.gateWays = customer.getGateWays();
			//LogUtil.info("VVN001.4");
			String valueInXml = stream.toXML(user.getId());
	    	String serviceName = "userService";
			String method = "getUserById";
			result = IMonitorUtil.sendToController(serviceName, method, valueInXml);
			//User user1=(User)stream.fromXML(result);
			this.setUser(((User)stream.fromXML(result)));
			returnVal = SUCCESS ;*/

		}
		catch(Exception e)
		{
			LogUtil.info(e.getMessage());
			throw e;
		}
		return returnVal;
	}
	//vibhu end
	/* ********************************** sumit start : SUB USER RESTRICTION ************************* */

	public String toAddScenario() throws Exception {
		String serviceName = "customerService";
		String method = "retrieveCustomerDeviceAlertAndActionsAndNoticiationProfiles";
		User user = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		String customerId = user.getCustomer().getCustomerId();
		XStream stream = new XStream();
		String customerIdXml = stream.toXML(customerId);
		String result = IMonitorUtil.sendToController(serviceName, method,
				customerIdXml);
		Customer customer = (Customer) stream.fromXML(result);
		this.gateWays = customer.getGateWays();
		//pari start
		String xmlOfIconList = null;
		try 
		{
			xmlOfIconList = IMonitorUtil.sendToController("iconService", "listIconsForScenario");
			this.setListIcons((List<SystemIcon>)stream.fromXML(xmlOfIconList));	
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		//pari end

		return SUCCESS;
	}
	public String saveScenario() throws Exception {
		HashSet<ScenarioAction> scenarioActions = new HashSet<ScenarioAction>();
		
		XStream stream = new XStream();
		GateWay gateWay=null;
		
		//check gateway version
		try 
		{
			String singleAlertExp=this.actionExpressions[0];
			String[] splitalertObj = singleAlertExp.split("=");
			long dIdofObject = Long.parseLong(splitalertObj[0]);
			String value=Long.toString(dIdofObject);
			String deviceIdxml=stream.toXML(value);
			String serviceName1 = "ruleService";
			String method1 = "getGateway";
			String gatewayXml = IMonitorUtil.sendToController(serviceName1, method1,deviceIdxml);
			gateWay=(GateWay) stream.fromXML(gatewayXml);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LogUtil.info(e.getMessage());
		}
		
		if (this.actionExpressions.length >= 12 && gateWay.getGateWayVersion().contains("IMSZING"))
		{
			
			String message = IMonitorUtil.formatMessage(this, "Scenario Not Saved ..Please select only 10 actions..");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		
		
		for (int i = 0; i < this.actionExpressions.length; i++) {
			String actionExpression = this.actionExpressions[i];
			actionExpression = actionExpression.trim();
			if(actionExpression.equalsIgnoreCase("")){
				continue;
			}
			String[] split = actionExpression.split("=");
			if(split.length < 2){
				continue;
			}
			long deviceId = Long.parseLong(split[0]);
			long actionId = Long.parseLong(split[1]);
			Device device = new Device();
			device.setId(deviceId);
			ActionType actionType = new ActionType();
			actionType.setId(actionId);
			ScenarioAction scenarioAction = new ScenarioAction();
			scenarioAction.setDevice(device);
			scenarioAction.setActionType(actionType);
			if(split.length > 2){
				String level = split[2];
				scenarioAction.setLevel(level);
			}
			scenarioActions.add(scenarioAction);
		}
		this.scenario.setScenarioActions(scenarioActions);
		//XStream stream = new XStream();
		String scenarioXml = stream.toXML(this.scenario);
		String serviceName = "scenarioService";
		String method = "saveScenarioWithDetails";
		String message = IMonitorUtil.sendToController(serviceName, method,
				scenarioXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String toEditScenario() throws Exception {
		String serviceName = "customerService";
		String method = "retrieveCustomerDeviceAlertAndActionsAndNoticiationProfiles";
		User user = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		String customerId = user.getCustomer().getCustomerId();
		XStream stream = new XStream();
		String customerIdXml = stream.toXML(customerId);
		String result = IMonitorUtil.sendToController(serviceName, method,
				customerIdXml);
		Customer customer = (Customer) stream.fromXML(result);
		this.gateWays = customer.getGateWays();
		
//		Retrieving the scenario details.
		serviceName = "scenarioService";
		method = "retrieveAllScenarioDetails";
		String scenarioXml = stream.toXML(this.scenario);
		String resultXml = IMonitorUtil.sendToController(serviceName, method,
				scenarioXml);
		this.scenario = (Scenario) stream.fromXML(resultXml);
		long gateWayId = this.scenario.getGateWay().getId();
		Set<GateWay> rGateWays = new HashSet<GateWay>();
		for (GateWay gateWay : this.gateWays) {
			if(gateWay.getId() != gateWayId){
				rGateWays.add(gateWay);
			}
		}
		
		
	/*	//Added by apoorva for 3gp
				if (this.gateWays.size() > 1) 
				{
					LogUtil.info("Continue");
				} 
				else
				{
					this.gateWays.removeAll(rGateWays); 
				}
				//Added by apoorva for 3gp end
*/		
		
		this.gateWays.removeAll(rGateWays);
		//pari start
		
		String xmlOfIconList = null;
		try {

			xmlOfIconList = IMonitorUtil.sendToController("iconService", "listIconsForScenario");
			this.listIcons = (List<SystemIcon>) stream.fromXML(xmlOfIconList);	
			} catch (Exception e) {
			e.printStackTrace();
		}
		//pari end
		return SUCCESS;
	}
	
	public String updateScenario() throws Exception {

		HashSet<ScenarioAction> scenarioActions = new HashSet<ScenarioAction>();
		
		XStream stream = new XStream();
		GateWay gateWay=null;
		
		//check gateway version
		try 
		{
			String singleAlertExp=this.actionExpressions[0];
			String[] splitalertObj = singleAlertExp.split("=");
			long dIdofObject = Long.parseLong(splitalertObj[0]);
			String value=Long.toString(dIdofObject);
			String deviceIdxml=stream.toXML(value);
			String serviceName1 = "ruleService";
			String method1 = "getGateway";
			String gatewayXml = IMonitorUtil.sendToController(serviceName1, method1,deviceIdxml);
			gateWay=(GateWay) stream.fromXML(gatewayXml);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LogUtil.info(e.getMessage());
		}
		
		if (this.actionExpressions.length >= 12 && gateWay.getGateWayVersion().contains("IMSZING"))
		{
			String message = IMonitorUtil.formatMessage(this, "Scenario Not Saved ..Please select only 10 actions..");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		
		
		for (int i = 0; i < this.actionExpressions.length; i++) {
			String actionExpression = this.actionExpressions[i];
			actionExpression = actionExpression.trim();
			if(actionExpression.equalsIgnoreCase("")){
				continue;
			}
			String[] split = actionExpression.split("=");
			if(split.length < 2){
				continue;
			}
			long deviceId = Long.parseLong(split[0]);
			long actionId = Long.parseLong(split[1]);
			Device device = new Device();
			device.setId(deviceId);
			ActionType actionType = new ActionType();
			actionType.setId(actionId);
			ScenarioAction scenarioAction = new ScenarioAction();
			scenarioAction.setDevice(device);
			scenarioAction.setActionType(actionType);
			if(split.length > 2){
				String level = split[2];
				scenarioAction.setLevel(level);
			}
			scenarioActions.add(scenarioAction);
		}
		this.scenario.setScenarioActions(scenarioActions);
		//XStream stream = new XStream();
		String ruleXml = stream.toXML(this.scenario);
		String serviceName = "scenarioService";
		String method = "updateScheduleWithDetails";
		String message = IMonitorUtil.sendToController(serviceName, method,
				ruleXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	public String deleteScenario() throws Exception{
		Long id = new Long(this.scenario.getId());
		XStream xStream = new XStream();
		String idXml = xStream.toXML(id);
		String message = IMonitorUtil.sendToController("scenarioService", "deleteScenario", idXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	public String executeScenario() throws Exception{
		Long id = new Long(this.scenario.getId());
		XStream xStream = new XStream();
		String idXml = xStream.toXML(id);
		String message = IMonitorUtil.sendToController("scenarioService", "executeScenario", idXml);
		message = IMonitorUtil.formatMessage(this, message);
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}


	public Set<GateWay> getGateWays() {
		return gateWays;
	}


	public void setGateWays(Set<GateWay> gateWays) {
		this.gateWays = gateWays;
	}


	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}


	public String[] getActionExpressions() {
		return actionExpressions;
	}


	public void setActionExpressions(String[] actionExpressions) {
		this.actionExpressions = actionExpressions;
	}


	public DataTable getDataTable() {
		return dataTable;
	}


	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
//vibhu start
	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}
//vibhu end	

	public List<SystemIcon> getListIcons() {
		return listIcons;
	}

	public void setListIcons(List<SystemIcon> listIcons) {
		this.listIcons = listIcons;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
