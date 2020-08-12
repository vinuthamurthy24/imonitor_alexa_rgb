/* Copyright  2012 iMonitor Solutions India Private Limited */

package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import java.util.List;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AVBlaster;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AVCategory;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author sumit kumar
 *
 */
public class AVBlasterAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AVBlaster avBlaster;
	private List<AVCategory> avCategoryList;
	private DataTable dataTable;
	
	XStream stream = new XStream();
	private String serviceName, method, result = "";
	
	public String toListAVBlasterCodes() throws Exception{
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String toAddAVBlasterCodeInfo() throws Exception{
		result = SUCCESS;
		serviceName = "avBlasterService";
		method = "getListOfAVCategories";
		String avCategoriesXml = IMonitorUtil.sendToController(serviceName, method);
		this.avCategoryList = (List<AVCategory>) stream.fromXML(avCategoriesXml);
		return result;
	}
	
	public String addAVBlasterCodeInfo() throws Exception{
		result = SUCCESS;
		String avBlasterFromWeb = stream.toXML(avBlaster);
		
		//1. If duplicate entry return ERROR else save details and return SUCCESS.
		serviceName = "avBlasterService";
		method = "addAVBlasterCodeInfo";
		String resultXML = IMonitorUtil.sendToController(serviceName, method, avBlasterFromWeb);
		String resultFromDB = (String) stream.fromXML(resultXML);
		if(resultFromDB.equalsIgnoreCase("alreadyExists")){
			ActionContext.getContext().getSession().put("message","Failure~The AV Blaster IR CODE info already exists. ");
			result = ERROR;
		}else if (resultFromDB.equalsIgnoreCase("dbError")) {
			ActionContext.getContext().getSession().put("message","Failure~Internal error occured. AV IR Code info not saved. ");
			result = ERROR;
		}	
		ActionContext.getContext().getSession().put("message","Success ~ AV IR Code info saved successfully. ");
		return result;
	}

	@SuppressWarnings("unchecked")
	public String toEditAVBlasterCodeInfo() throws Exception{
		//1. Get list of AV Categories
		serviceName = "avBlasterService";
		method = "getListOfAVCategories";
		String avCategoriesXml = IMonitorUtil.sendToController(serviceName, method);
		this.avCategoryList = (List<AVCategory>) stream.fromXML(avCategoriesXml);
		
		//2. Get AVBlaster Code details based on ID.
		serviceName = "avBlasterService";
		method = "getAVBlasterCodeById";
		long id = avBlaster.getId();
		String avBlasterIdXml = stream.toXML(id);
		String avBlasterXml = IMonitorUtil.sendToController(serviceName, method, avBlasterIdXml);
		this.avBlaster = (AVBlaster) stream.fromXML(avBlasterXml);
		return SUCCESS;
	}
	
	public String updateAVBlasterCodeInfo() throws Exception{
		String avBlasterFromWeb = stream.toXML(avBlaster);
		serviceName = "avBlasterService";
		method = "updateAVBlasterCodeInfo";
		result = IMonitorUtil.sendToController(serviceName, method, avBlasterFromWeb);
		String message = "";
		if(result.equalsIgnoreCase("yes")){
			message = "AV Blaster Code info updated successfully.";
			ActionContext.getContext().getSession().put("message", message);
		}else{
			message = "Internal Error : Unable to update the entry";
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String deleteAVBlasterCodeInfo() throws Exception{
		String avBlasterXml = stream.toXML(avBlaster);
		serviceName = "avBlasterService";
		method = "deleteAVBlasterCodeInfoById";
		result = IMonitorUtil.sendToController(serviceName, method, avBlasterXml);
		String message = "";
		if(result.equalsIgnoreCase("yes")){
			message = "AV Blaster Code info deleted successfully.";
			ActionContext.getContext().getSession().put("message", message);
		}else{
			message = "Internal Error : Unable to update the entry";
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String listAskedAVBlasterCodes() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "avBlasterService";
		String method = "listAskedAVBlasterCodes";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	public AVBlaster getAvBlaster() {
		return avBlaster;
	}

	public void setAvBlaster(AVBlaster avBlaster) {
		this.avBlaster = avBlaster;
	}

	public List<AVCategory> getAvCategoryList() {
		return avCategoryList;
	}

	public void setAvCategoryList(List<AVCategory> avCategoryList) {
		this.avCategoryList = avCategoryList;
	}
	
	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

}
