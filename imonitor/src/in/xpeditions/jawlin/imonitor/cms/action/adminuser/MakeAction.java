/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SceneControllerMake;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class MakeAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Make make;
	private List<DeviceType> deviceTypes;
	private DataTable dataTable;
	private SceneControllerMake sceneControllerMake;
	
	@SuppressWarnings("unchecked")
	public String showMake() throws Exception {
		String serviceName = "deviceTypeService";
		String method = "listDeviceTypesWithoutVirtualDevice";
		String result = IMonitorUtil.sendToController(serviceName, method);
		XStream stream = new XStream();
		this.deviceTypes = (List<DeviceType>) stream.fromXML(result);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String toEditMake() throws Exception {
		String serviceName = "makeService";
		String method = "getMakeById";
		String result = IMonitorUtil.sendToController(serviceName, method, "" + this.make.getId());
		XStream stream = new XStream();
		this.make = (Make) stream.fromXML(result);
		
		serviceName = "deviceTypeService";
		method = "listDeviceTypesWithoutVirtualDevice";
		result = IMonitorUtil.sendToController(serviceName, method);
		this.deviceTypes = (List<DeviceType>) stream.fromXML(result);
		
		return SUCCESS;
	}
	
	public String saveMake() throws Exception {
		XStream xStream = new XStream();
		String xml = xStream.toXML(make);
		String serviceName = "makeService";
		String method = "saveMake";
		String result=IMonitorUtil.sendToController(serviceName, method, xml);
		if(result.equals("yes"))
		ActionContext.getContext().getSession().put("message", "Make "+make.getName()+" saved succussfully");
		else
		ActionContext.getContext().getSession().put("message", "Not Able To Save "+make.getName()+"  Because Of Duplicate Entry");
		return SUCCESS;
	}
	
	public String updateMake() throws Exception {
		XStream xStream = new XStream();
		String xml = xStream.toXML(make);
		String serviceName = "makeService";
		String method = "updateMake";
		IMonitorUtil.sendToController(serviceName, method, xml);
		ActionContext.getContext().getSession().put("message", "Make "+make.getName()+" updated succussfully");
		return SUCCESS;
	}
	
	public String deleteMake() throws Exception {
		String xml = "" + make.getId();
		String serviceName = "makeService";
		String method = "deleteMake";
		IMonitorUtil.sendToController(serviceName, method, xml);
		ActionContext.getContext().getSession().put("message", "Make "+make.getId()+" deleted succussfully");
		return SUCCESS;
	}
	public String listMakes(){
		
		return SUCCESS;
	}
	public String listAdkedMakes(){
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		String serviceName = "makeService";
		String method = "listAskedMakes";
		String resultXml = null;
		try {
			resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(resultXml == null){
			return "failure";
		}
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	
	public String saveSceneControllermake() throws Exception {
		XStream xStream = new XStream();
		String xml = xStream.toXML(sceneControllerMake);
		String serviceName = "makeService";
		String method = "savesaveSceneControllermake";
		String result=IMonitorUtil.sendToController(serviceName, method, xml);
		if(result.equals("yes"))
		ActionContext.getContext().getSession().put("message", "SceneControllerMake "+sceneControllerMake.getModelName()+" saved successfully");
		else
		ActionContext.getContext().getSession().put("message", "Not Able To Save "+sceneControllerMake.getModelName()+"  Because Of Duplicate Entry");
		return SUCCESS;
	}
	

	public Make getMake() {
		return make;
	}

	public void setMake(Make make) {
		this.make = make;
	}

	public final List<DeviceType> getDeviceTypes() {
		return deviceTypes;
	}

	public final void setDeviceTypes(List<DeviceType> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

	public final DataTable getDataTable() {
		return dataTable;
	}

	public final void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public SceneControllerMake getSceneControllerMake() {
		return sceneControllerMake;
	}

	public void setSceneControllerMake(SceneControllerMake sceneControllerMake) {
		this.sceneControllerMake = sceneControllerMake;
	}

}
