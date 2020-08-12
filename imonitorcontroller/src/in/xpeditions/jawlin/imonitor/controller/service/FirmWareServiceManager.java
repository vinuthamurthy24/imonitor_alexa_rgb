/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.FirmWare;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.FirmWareManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

/**
 * @author computer
 *
 */
public class FirmWareServiceManager {
	
	public String saveFirmWare(String xml){
		String result = "no";
		FirmWareManager firmWareManager = new FirmWareManager();
		XStream stream = new XStream();
		
		FirmWare firmWare =  (FirmWare) stream.fromXML(xml);
		if(firmWareManager.saveFirmWare(firmWare)){
			result = "yes";
		}
		return stream.toXML(result) ;
	}
	
	
	public String getLatestFirmwareversion900(){
		LogUtil.info("================inside controller ==================");
		FirmWareManager firmWareManager = new FirmWareManager();
		Object[] objects1 = (Object[])firmWareManager.getLatestFirmwareversionNA900();
		String result="";
		if(objects1!=null)
		{
		result=IMonitorUtil.convertToString(objects1[1]);
		}
		LogUtil.info("================controller 1111==================="+result);
		return result;
	}
	public String getLatestFirmwareversion910(){
		LogUtil.info("================inside controller ==================");
		FirmWareManager firmWareManager = new FirmWareManager();
		Object[] objects1 = (Object[])firmWareManager.getLatestFirmwareversionNA910();
		String result="";
		if(objects1!=null)
		{
		result=IMonitorUtil.convertToString(objects1[1]);
		}
		LogUtil.info("================controller 222222222==================="+result);
		return result;
	}
	public String updateFirmWare(String xml){
		LogUtil.info("update firmware in controller"+xml);
		
		String result = "no";
		FirmWareManager firmWareManager = new FirmWareManager();
		XStream stream = new XStream();
		FirmWare firmWare =  (FirmWare) stream.fromXML(xml);
		LogUtil.info("in controller firmware"+firmWare.getActivation());
		if(firmWareManager.updateFirmWare(firmWare)){
			result = "yes";
		}
		LogUtil.info("result from manager in controller"+result);
		return stream.toXML(result) ;
	}
	public String updateFirmwareactivation(String xmlid,String xmlactivation){
		LogUtil.info("update firmware in controller");
		LogUtil.info("update firmware in controller"+xmlid);
		LogUtil.info("update firmware in controller activation"+xmlactivation);
		XStream stream = new XStream();
		long id=(Long) stream.fromXML(xmlid);
		long activation=(Long) stream.fromXML(xmlactivation);
		String result = "no";
		FirmWareManager firmWareManager = new FirmWareManager();
		LogUtil.info("in controller firmware"+xmlactivation);
		if(firmWareManager.updateFirmwareactivation(id,activation)){
			result = "yes";
		}
		LogUtil.info("result from manager in controller"+result);
		return stream.toXML(result) ;
	}
	
	public String deleteFirmWare(String xml){
		String result = "no";
		FirmWareManager firmWareManager = new FirmWareManager();
		XStream stream = new XStream();
		FirmWare firmWare =  (FirmWare) stream.fromXML(xml);
		if(firmWareManager.deleteFirmWare(firmWare)){
			result = "yes";
		}
		return stream.toXML(result) ;
	}
	
	public String getPath(){
		XStream stream = new XStream();
		Map<String, String> properties = ControllerProperties.getProperties();
		String filePath = properties.get(Constants.FILE_LOCATION);
		return stream.toXML(filePath);
	}
	
	public String listAllFirmWares(){
		XStream stream = new XStream();
		FirmWareManager firmWareManager = new FirmWareManager();
		List<FirmWare> firmWares = firmWareManager.listAllFirmwares();
		String result = stream.toXML(firmWares);
		return result;
	}
	public String listAskedFirmWare(String xml){
		XStream xStream=new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(xml);
		//GatewayManager gatewayManager = new GatewayManager();
		FirmWareManager firmWareManager = new FirmWareManager();
		int count = firmWareManager.getTotalFirmWareCount();
		dataTable.setTotalRows(count);
		String sSearch = dataTable.getsSearch();
		String[] cols = { "f.version","f.file","f.description"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = firmWareManager.listAskedFirmWare(sSearch,sOrder,dataTable.getiDisplayStart(),dataTable.getiDisplayLength());
		dataTable.setResults(list);
		int displayRow = firmWareManager.getTotalFirmWareCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	public String getFirmWareById(String xml){
		XStream stream = new XStream();
		FirmWare firmWare = (FirmWare) stream.fromXML(xml);
		FirmWareManager firmWareManager = new FirmWareManager();
		firmWare = firmWareManager.getFirmWareById(firmWare.getId());
		return stream.toXML(firmWare);
	}
}
