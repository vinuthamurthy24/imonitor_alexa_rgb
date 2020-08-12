/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;


import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScenarioCreateAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScenarioDeleteAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScenarioExecutionAction;
import in.xpeditions.jawlin.imonitor.controller.action.ScenarioUpdateAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Alexa;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScheduledScenarios;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserScenarioAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.Alexamanager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScenarioManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

import imonitor.cms.alexa.proactive.service.AlexaProactiveDeleteDeviceService;
import imonitor.cms.alexa.proactive.service.AlexaProactiveUpdateScenarioService;

/**
 * @author Coladi
 *
 */
public class ScenarioServiceManager {

	/* ******************************************** sumit start: Moving Scenarios to MID ****************************************************
	 * ******************************************************** ORIGINAL CODE ***************************************************************
	public String saveScenarioWithDetails(String scenarioXml)throws SecurityException,
	DOMException, IllegalArgumentException,
	ParserConfigurationException, SAXException, IOException,
	ClassNotFoundException, InstantiationException,
	IllegalAccessException, NoSuchMethodException,
	InvocationTargetException{
		XStream xStream  = new XStream();
		Scenario scenario = (Scenario) xStream.fromXML(scenarioXml);
		//sumit start: handle Duplicate Schedule Name
		String  error = verifyScenarioDetails(scenario);
		if(error != null)return error;
		//sumit end
		String result = "some problem occured while saving the scenario : " + scenario.getName();
		ScenarioManager scenarioManager = new ScenarioManager();
		if(scenarioManager.saveScenarioWithDetails(scenario)){
			result = "scenario : '" + scenario.getName() + "' is saved.";
		}
		return result;
	}
	*/

	public String saveScenarioWithDetails(String scenarioXml)throws SecurityException,
	DOMException, IllegalArgumentException,
	ParserConfigurationException, SAXException, IOException,
	ClassNotFoundException, InstantiationException,
	IllegalAccessException, NoSuchMethodException,
	InvocationTargetException{
		LogUtil.info("saveScenarioWithDetails"  );
		XStream xStream  = new XStream();
		Scenario scenario = (Scenario) xStream.fromXML(scenarioXml);
		LogUtil.info("step 1"  );
		//1. Check for Duplicate Scenario Entry
		String  error = verifyScenarioDetails(scenario);
		if(error != null)return error;
		LogUtil.info("step 2"  );
		String result = "msg.controller.scenarios.0002"+ Constants.MESSAGE_DELIMITER_1 + scenario.getName();
		LogUtil.info("step 3"  );
		ScenarioManager scenarioManager = new ScenarioManager();
		if(scenarioManager.saveScenarioWithDetails(scenario)){
			//1.Send Create-Scenario Command to iMVG.
			scenario = scenarioManager.retrieveScenarioDetails(scenario.getId());
			LogUtil.info("step 4"  );
			ActionModel actionModel = new ActionModel(null, scenario);
			ImonitorControllerAction scenarioCreateAction = new ScenarioCreateAction();
			LogUtil.info("step 5"  );
			scenarioCreateAction.executeCommand(actionModel);
			LogUtil.info("step 6"  );
			//2.Do appropriate actions if iMVG return a success/failure.
			boolean resultFromImvg = IMonitorUtil.waitForResult(scenarioCreateAction);
			if(resultFromImvg){
				LogUtil.info("step 7"  );
				//result = "msg.controller.scenarios.0003"+ Constants.MESSAGE_DELIMITER_1 + scenarioCreateAction.getActionModel().getMessage();
				result = scenarioCreateAction.getActionModel().getMessage();
				LogUtil.info("step 8"  );
				/*Added By naveen for updating scenario to alexa end point
				 * Date: 26th July 2018
				 */
				boolean isAlexaUserExist = scenarioManager.checkAlexaUserByCustomerId(scenario.getGateWay().getCustomer());;
				
				if(isAlexaUserExist) {
					
					AlexaProactiveUpdateScenarioService updateScenario = new AlexaProactiveUpdateScenarioService(scenario, scenario.getGateWay().getCustomer());
					Thread t = new Thread(updateScenario);
					t.start();
				}
				
				
				
				
			}else{
				result = "msg.controller.scenarios.0004"
						+ Constants.MESSAGE_DELIMITER_1 + scenario.getName()
						+ Constants.MESSAGE_DELIMITER_2 + scenarioCreateAction.getActionModel().getMessage();
				scenarioCreateAction.executeFailureResults(actionModel.getQueue());
			}		
			//result = "scenario : '" + scenario.getName() + "' is saved.";
		}
		LogUtil.info("step 9"  );
		return result;
	}
	//sumit start
	@SuppressWarnings("unchecked")
	public String verifyScenarioDetails(Scenario scenario)
	{
		LogUtil.info("step 10"  );
		XStream xStream  = new XStream();
		 String result = null;
		try {
			LogUtil.info("step 11"  );
			List<Scenario> scenarios = new DaoManager().get("gateWay",scenario.getGateWay().getId(), Scenario.class);
			LogUtil.info("step 12"  );
			for(Scenario scenarioFromDB : scenarios)
			{
				LogUtil.info("step 13"  );
				if(scenario.getName().equalsIgnoreCase(scenarioFromDB.getName())
					&& scenario.getGateWay().getId() == scenarioFromDB.getGateWay().getId()){
					return "msg.controller.scenarios.0001"+ Constants.MESSAGE_DELIMITER_1 + scenario.getName();
					
				}
			}
		} catch (Exception e) {
			LogUtil.info("Got Exception while saving scenario: ", e);
		}
		LogUtil.info("step 14"  );
		LogUtil.info(xStream.toXML(result));
		return result;
	}
	//sumit end
	public String retrieveAllScenarioDetails(String scenarioXml){
		XStream xStream  = new XStream();
		Scenario scenario = (Scenario) xStream.fromXML(scenarioXml);
		ScenarioManager scenarioManager = new ScenarioManager();
		scenario = scenarioManager.retrieveScenarioDetails(scenario.getId());
		return xStream.toXML(scenario);
	}
	
	public String updateScenarioWithAction(String scenarioXml, String action)throws SecurityException,
	DOMException, IllegalArgumentException,
	ParserConfigurationException, SAXException, IOException,
	ClassNotFoundException, InstantiationException,
	IllegalAccessException, NoSuchMethodException,
	InvocationTargetException{
		XStream xStream  = new XStream();
		Scenario scenario = (Scenario) xStream.fromXML(scenarioXml);
		ScenarioManager scenarioManager = new ScenarioManager();
		Scenario scenario2 = scenarioManager.getScenarioById(scenario.getId()); 
//		scenario2.setCustomer(scenario.getCustomer());
		scenario2.setDescription(scenario.getDescription());
		scenario2.setName(scenario.getName());
		String result = "no";
		scenarioManager.deleteScenarioActionsAndScheduleTimeByScheduleId(scenario.getId());
		if(scenarioManager.updateScenarioWithAction(scenario2, action)){
			result = "yes";
		}
		return result;
	}
	
	public String listAskedScnarios(String dataTableXml, String customerIdXml){
		XStream xStream=new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(dataTableXml);
		long id = (Long) xStream.fromXML(customerIdXml);
		ScenarioManager scenarioManager = new ScenarioManager();
		String sSearch = dataTable.getsSearch();
		String[] cols = { "s.name","s.description","g.macId"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = scenarioManager.listAskedScnarios(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength(),id);
		dataTable.setTotalRows(list.size());
		dataTable.setResults(list);
		int displayRow = scenarioManager.getScenarioCountByCustomerId(sSearch,id);
		dataTable.setTotalDispalyRows(displayRow);
		dataTable.setTotalRows(scenarioManager.getTotalScenarioCountByCustomerId(id));
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	
	/**
	 * @author sumit kumar
	 * @param userXml
	 * @return
	 * Date Modified : Jan02, 2014.
	 * Modification: to get list of all scenarios for the main user to restrict the sub users.
	 * Invocation From: Home Page Scenarios Section.
	 */
	public String listAllScnariosForSubUser(String userXml){
		XStream xStream=new XStream();
		User user = (User) xStream.fromXML(userXml);
		long id = user.getCustomer().getId();
		UserManager userManager = new UserManager();
		ScenarioManager scenarioManager = new ScenarioManager();
		List<Object[]> scenariosObjects = scenarioManager.listAllScenariosByCustomerId(id);
		List<Scenario> scenarios = new ArrayList<Scenario>();
		try {
			if(scenariosObjects != null)
			{
				for(Object[] scenario:scenariosObjects){
					Scenario scenario2 = new Scenario();
					scenario2.setId(Long.parseLong(IMonitorUtil.convertToString(scenario[0])));
					scenario2.setName(IMonitorUtil.convertToString(scenario[1]));				
					scenario2.setDescription(IMonitorUtil.convertToString(scenario[2]));
					scenario2.setIconFile(IMonitorUtil.convertToString(scenario[3]));
					scenario2.setScenarioActions(null);
					List<subUserScenarioAccess> accessibleScenariosList = userManager.listScenariosForSubuser(user.getId());
					for(subUserScenarioAccess su : accessibleScenariosList){
						long sid = su.getScenario().getId();
						if(sid == scenario2.getId()){
							scenarios.add(scenario2);
						}
					}
					//scenarios.add(scenario2);
				}
			}
		} catch (NumberFormatException e) {
			LogUtil.info("Caught NumberFormatException while retrieving scenarios for Sub User.", e);
		} catch (IllegalArgumentException e) {
			LogUtil.info("Caught IllegalArgumentException while retrieving scenarios for Sub User.", e);
		}
		String valueInXml = xStream.toXML(scenarios);
		return valueInXml;
	}
	
	public String listAllScnarios(String customerId){
		XStream xStream=new XStream();
		long id = (Long)xStream.fromXML(customerId);
		ScenarioManager scenarioManager = new ScenarioManager();
		List<Object[]> scenariosObjects = scenarioManager.listAllScenariosByCustomerId(id);
		List<Scenario> scenarios = new ArrayList<Scenario>();
		if(scenariosObjects != null)
		{
			for(Object[] scenario:scenariosObjects){
				Scenario scenario2 = new Scenario();
				scenario2.setId(Long.parseLong(IMonitorUtil.convertToString(scenario[0])));
				scenario2.setName(IMonitorUtil.convertToString(scenario[1]));				
				scenario2.setDescription(IMonitorUtil.convertToString(scenario[2]));
				scenario2.setIconFile(IMonitorUtil.convertToString(scenario[3]));
				scenario2.setScenarioActions(null);
				scenarios.add(scenario2);
			}
		}
		String valueInXml = xStream.toXML(scenarios);
		return valueInXml;
	}
	
	public String listAllScheduleScnarios(String scheduleId){
		XStream xStream=new XStream();
		long id = Long.parseLong(scheduleId);
		ScenarioManager scenarioManager = new ScenarioManager();
		List<Object[]> scenariosObjects = scenarioManager.listScenariosByScheduleId(id);
		List<ScheduledScenarios> scenarios = new ArrayList<ScheduledScenarios>();
		for(Object[] scenario:scenariosObjects){
			ScheduledScenarios scenario2 = new ScheduledScenarios();
			scenario2.setScenarioId(Long.parseLong(scenario[0].toString()));
			scenarios.add(scenario2);
		}
		String valueInXml = xStream.toXML(scenarios);
		return valueInXml;
	}
	
	/* ************************************************** sumit start: Moving Scenarios to MID ***************************************************
	 * *********************************************************** ORIGINAL CODE *****************************************************************

	public String deleteScenario(String idXml){
		XStream stream = new XStream();
		Long id = (Long) stream.fromXML(idXml);
		ScenarioManager scenarioManager = new ScenarioManager();
		Scenario scenario = scenarioManager.getScenarioById(id);
		String result = "Some Problem occured while deleted scenario with name: " + scenario.getName();
		if(scenarioManager.deleteScenario(scenario)){
			result = "scenario with name :" + scenario.getName() + " is deleted";
		}
		return result;
	}*/
	
	public String deleteScenario(String idXml){
		ScenarioManager scenarioManager = new ScenarioManager();
		XStream stream = new XStream();
		Long id = (Long) stream.fromXML(idXml);
		Scenario scenario = scenarioManager.retrieveScenarioDetails(id.longValue());
		String result = "msg.controller.scenarios.0013"+ Constants.MESSAGE_DELIMITER_1 + scenario.getName();
		
		ActionModel actionModel = new ActionModel(null, scenario);
		ImonitorControllerAction scenarioDeleteAction = new ScenarioDeleteAction();
		scenarioDeleteAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForResult(scenarioDeleteAction);
		if(resultFromImvg){
			if(scenarioDeleteAction.isActionSuccess()){
				boolean res = scenarioManager.deleteScenario(scenario);
				if(res){
					result = "msg.controller.scenarios.0016" + Constants.MESSAGE_DELIMITER_1 + scenario.getName();
				}else{
					result = "msg.controller.scenarios.0017" + Constants.MESSAGE_DELIMITER_1 + scenario.getName();
				}
				
				/*Update to alexa end point
				 * By Apoorva
				 *2 Sep
				 */
				CustomerManager customerManager = new CustomerManager();
				Customer customer = customerManager.getCustomerByGateWay(scenario.getGateWay());
				Alexamanager alexamanager =  new Alexamanager();
				Alexa alexaUser = alexamanager.getAlexaUserByCustomerId(customer);
				if(alexaUser != null) {
					try {
						
						AlexaProactiveDeleteDeviceService alexaProactiveService = new AlexaProactiveDeleteDeviceService(alexaUser,scenario);
						Thread t = new Thread(alexaProactiveService);
						 t.start();
						//alexaServiceManager.updateDeviceListToAlexa(customer,alexaUser);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				
				
			}else{
				result = "msg.controller.scenarios.0003" + Constants.MESSAGE_DELIMITER_1 + scenarioDeleteAction.getActionModel().getMessage();
				//result = scenarioDeleteAction.getActionModel().getMessage();
			}
		}else{
			result = "msg.controller.scenarios.0018" 
					+ Constants.MESSAGE_DELIMITER_1 + scenario.getName() 
					+ Constants.MESSAGE_DELIMITER_2 + scenarioDeleteAction.getActionModel().getMessage();
		}
		return result;
	}
	
	public String getScenarioById(String scenaroId) {
		ScenarioManager scenarioManager = new ScenarioManager();
		Scenario scenario = scenarioManager.getScenarioWithCustomerByScenarioId(scenaroId);
		XStream stream = new XStream();
		return stream.toXML(scenario);
	}
	
	public String executeScenario(String scenarioIdXml) {
		String result ="msg.controller.scenarios.0019";
		try
		{
		XStream stream = new XStream();
			long id = (Long) stream.fromXML(scenarioIdXml);
		ScenarioManager scenarioManager = new ScenarioManager();
			//vibhu start
			/*if(scenarioManager.executeScenarioActionsBySenarioId(id.longValue())){
			result = "msg.controller.scenarios.0020";
			}*/
			Scenario s = scenarioManager.getScenarioWithGatewayById(id);
			ActionModel actionModel = new ActionModel(null, s);
			ImonitorControllerAction scenarioExecutionAction = new ScenarioExecutionAction();
			scenarioExecutionAction.executeCommand(actionModel);
			boolean resultFromImvg = IMonitorUtil.waitForScenarioResult(scenarioExecutionAction);
			if(resultFromImvg)
			{
				if(scenarioExecutionAction.isActionSuccess())
				{
						result = "msg.controller.scenarios.0020";
				}
			}
			else
			{
		}
		}
		catch(Exception e)
		{
			LogUtil.error(e.getMessage());
			LogUtil.info("", e);
		}
			//vibhu end
		return result;
	}
	

	
	/* ************************************************** sumit start: Moving Scenarios to MID **************************************************
	 * ************************************************************* ORIGINAL CODE **************************************************************
	public String updateScheduleWithDetails(String scheduleXml)throws SecurityException,
	DOMException, IllegalArgumentException,
	ParserConfigurationException, SAXException, IOException,
	ClassNotFoundException, InstantiationException,
	IllegalAccessException, NoSuchMethodException,
	InvocationTargetException{
		XStream xStream  = new XStream();
		Scenario scenario = (Scenario) xStream.fromXML(scheduleXml);
		ScenarioManager scenarioManager = new ScenarioManager();
		String result = "Some Problem Occured while updating the scenario with name : '" + scenario.getName() + "'";
		if(scenarioManager.updateScenarioWithDetails(scenario)){
			result = "Scenario with name : '" + scenario.getName() + "' is updated";
		}
		return result;
	} */
	
	public String updateScheduleWithDetails(String scheduleXml)throws SecurityException,
	DOMException, IllegalArgumentException,
	ParserConfigurationException, SAXException, IOException,
	ClassNotFoundException, InstantiationException,
	IllegalAccessException, NoSuchMethodException,
	InvocationTargetException{
		XStream xStream  = new XStream();
		Scenario scenario = (Scenario) xStream.fromXML(scheduleXml);
		ScenarioManager scenarioManager = new ScenarioManager();
		Scenario scenario2 = scenarioManager.retrieveScenarioDetails(scenario.getId());
		String result = "msg.controller.scenarios.0007"+ Constants.MESSAGE_DELIMITER_1 + scenario.getName();
		if(scenarioManager.updateScenarioWithDetails(scenario)){
			//1.Send Modify-Scenario Command to iMVG.
			scenario = scenarioManager.retrieveScenarioDetails(scenario.getId());
			ActionModel actionModel = new ActionModel(null, scenario);
			ImonitorControllerAction scenarioUpdateAction = new ScenarioUpdateAction();
			scenarioUpdateAction.executeCommand(actionModel);
			
			//2.Do appropriate actions when iMVG returns success/failure.
			boolean resultFromImvg = IMonitorUtil.waitForResult(scenarioUpdateAction);
			if(resultFromImvg){
				//result = "msg.controller.scenarios.0003"+ Constants.MESSAGE_DELIMITER_1 + scenarioUpdateAction.getActionModel().getMessage();
				result = scenarioUpdateAction.getActionModel().getMessage();
//				If the update is a failure, then we should roll back the updated rule.
				
				//Added by apoorva
				boolean isAlexaUserExist = scenarioManager.checkAlexaUserByCustomerId(scenario.getGateWay().getCustomer());;
				
				if(isAlexaUserExist) {
					AlexaProactiveUpdateScenarioService updateScenario = new AlexaProactiveUpdateScenarioService(scenario, scenario.getGateWay().getCustomer());
					Thread t = new Thread(updateScenario);
					t.start();
				}
				// End
				
				
				if(!scenarioUpdateAction.isActionSuccess()){
					
					boolean res = scenarioManager.updateScenarioWithDetails(scenario2);
					if(!res){
						result = "msg.controller.scenarios.0008"+ Constants.MESSAGE_DELIMITER_1 + scenario.getName();
					}
				}
			}else{
				result = "msg.controller.scenarios.0009"
						+ Constants.MESSAGE_DELIMITER_1 + scenario.getName()
						+ Constants.MESSAGE_DELIMITER_2 + scenarioUpdateAction.getActionModel().getMessage();
				boolean res = scenarioManager.updateScenarioWithDetails(scenario2);
				/*Added by Naveen
				 * Date: 26th July 2018
				 * For updating scenario to alexa end point
				 */
				boolean isAlexaUserExist = scenarioManager.checkAlexaUserByCustomerId(scenario.getGateWay().getCustomer());;
				
				if(isAlexaUserExist) {
					
					AlexaProactiveUpdateScenarioService updateScenario = new AlexaProactiveUpdateScenarioService(scenario, scenario.getGateWay().getCustomer());
					Thread t = new Thread(updateScenario);
					t.start();
				}
				
				
				if(!res){
					result = "msg.controller.scenarios.0008"+ Constants.MESSAGE_DELIMITER_1 + scenario.getName();
				}
			}
		}else{
			result = "msg.controller.scenarios.0010"+ Constants.MESSAGE_DELIMITER_1 + scenario.getName();
		}			
		//result = "Scenario with name : '" + scenario.getName() + "' is updated";
		return result;
	}
	
	//parishod start
	public String getScenarioCountForDevice(String xml){
		String result = "NO_SCENARIO_EXISTS";
		XStream xStream = new XStream();
		String genDevcId = (String) xStream.fromXML(xml);
		ScenarioManager scenarioManager = new ScenarioManager();
		long count = scenarioManager.getScenarioCountByGeneratedDeviceId(genDevcId);
		if(count > 0){
			result = "SCENARIO_EXISTS";
		}
		return result;
	}
	//parishod end
	
	public String getscenariosforapi(String id){
		XStream stream = new XStream();
		String customerId=id;
		ScenarioManager scenarioManager = new ScenarioManager();
	List<Object[]> Objects =scenarioManager.listAllScenariosByCustomerIdForApi(customerId); 
	
	return stream.toXML(Objects);	
	}
	
	public String getScenarioControl(String id,String customerId){
		XStream stream = new XStream();
		String result="False";
		ScenarioManager scenarioManager = new ScenarioManager();
		Scenario s = scenarioManager.getScenarioApiByIdAndCustId(customerId, id);
		
		ActionModel actionModel = new ActionModel(null, s);
		ImonitorControllerAction scenarioExecutionAction = new ScenarioExecutionAction();
		scenarioExecutionAction.executeCommand(actionModel);
		boolean resultFromImvg = IMonitorUtil.waitForScenarioResult(scenarioExecutionAction);
		if(resultFromImvg)
		{
			if(scenarioExecutionAction.isActionSuccess())
			{
				result = "True";
			}
		}
		return stream.toXML(result);
	}
	
	
}
