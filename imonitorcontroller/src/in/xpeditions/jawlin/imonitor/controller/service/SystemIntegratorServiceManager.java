/* Copyright  2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;


import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemIntegrator;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;

import in.xpeditions.jawlin.imonitor.controller.dao.manager.SystemIntegratorManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;

/**
 * 
 * @author sumit kumar
 *
 */
public class SystemIntegratorServiceManager {
	
	public String saveSystemIntegrator(String xmlString){
		LogUtil.info("saveSystemIntegrator : IN");
		String result = "";
		XStream stream = new XStream();
		SystemIntegrator systemIntegrator = (SystemIntegrator) stream.fromXML(xmlString);
		
		//1. Check for duplicate entry by name of system integrator
		SystemIntegratorManager integratorManager = new SystemIntegratorManager();
		List<SystemIntegrator> listOfSystemIntegrators = integratorManager.listOfSystemIntegrators();
		boolean siExists = false;
		for(SystemIntegrator si : listOfSystemIntegrators){
			if (systemIntegrator.getName().equalsIgnoreCase(si.getName())) {
				siExists = true;
			}
		}
		if(siExists){
			//System Integrator already exists
			result = "alreadyExists";
		}else{
			//Save System Integrator details
			boolean resultFromDB = integratorManager.saveSystemIntegrator(systemIntegrator);
			if(resultFromDB){
				result = "success";
			}else {
				result = "DB_ERROR";
			}
		}
		LogUtil.info("saveSystemIntegrator : OUT");
		return result;
	}

	public String getListOfSystemIntegrators(){
		
		String xml = "";
		XStream xStream = new XStream();
		SystemIntegratorManager integratorManager = new SystemIntegratorManager();
		List<SystemIntegrator> listOfSIs = integratorManager.listOfSystemIntegrators();
		xml = xStream.toXML(listOfSIs);
		
		return xml;
	}
	
	
	
	public String listAskedSystemIntegrators(String xml) {
		XStream xStream = new XStream();
		DataTable dataTable = (DataTable) xStream.fromXML(xml);
		SystemIntegratorManager systemIntegratormg = new SystemIntegratorManager();
		
		String sSearch = dataTable.getsSearch();
		String[] cols = {"si.id,si.systemIntegratorId,si.name"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		String colName = cols[col];
		sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		
		List<?> list = systemIntegratormg.listAskedSystemIntegrator(sSearch,sOrder, dataTable
				.getiDisplayStart(), dataTable.getiDisplayLength());
		 
		dataTable.setResults(list);
		int displayRow = systemIntegratormg.getTotalSystemIntegratorCount();
		
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		
		return valueInXml;
	}
	
	
	public String toEditSystemIntegrator(String xmlString){
		
		XStream stream = new XStream();
		SystemIntegrator systemIntegrator = (SystemIntegrator) stream.fromXML(xmlString);
		SystemIntegratorManager integratorManager = new SystemIntegratorManager();
		SystemIntegrator DBsystemIntegrators = integratorManager.getSystemIntergratorById(systemIntegrator.getId());
		return stream.toXML(DBsystemIntegrators);
	}
	
	public String deleteSystemIntegrtor(String xmlString){
		
		String result = "";
		XStream stream = new XStream();
		SystemIntegrator systemIntegrator = (SystemIntegrator) stream.fromXML(xmlString);
		
		
		
		new CustomerManager().UpdateCustomerSIINformation(systemIntegrator);
		SystemIntegratorManager integratorManager = new SystemIntegratorManager();
		Boolean status = integratorManager.DeleteSystemIntegratorById(systemIntegrator);
		if(status)
		result="Successfuly Delted SI";
		else
		result="Not able to delete selected SystemIntegrator, Please try again";
		return stream.toXML(result);
	}
	
	
	public String UpdateSystemIntegrator(String xmlString)
	{
		
		String result = "";
		XStream stream = new XStream();
		LogUtil.info(xmlString);
		SystemIntegrator systemIntegrator = (SystemIntegrator) stream.fromXML(xmlString);
		SystemIntegratorManager integratorManager = new SystemIntegratorManager();
		Boolean status = integratorManager.UpdatedSystemIntegrator(systemIntegrator);
		if(status)
		result="Successfuly Updated SystemIntegrator Information";
		else
		result="Not able to Updated selected SystemIntegrator, Please try again";
		return stream.toXML(result);
	}
	
	
	public String getSILogoPath(){
		XStream stream = new XStream();
		Map<String, String> properties = ControllerProperties.getProperties();
		String filePath = properties.get(Constants.SI_DIR);
		return stream.toXML(filePath);
	}
}
