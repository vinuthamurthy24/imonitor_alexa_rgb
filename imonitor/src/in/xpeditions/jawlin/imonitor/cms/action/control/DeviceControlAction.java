/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.control;
import java.util.Map;

import in.xpeditions.jawlin.imonitor.cms.communicator.IMonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import com.opensymphony.xwork2.ActionSupport;
import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class DeviceControlAction extends ActionSupport {
	/**
	 * 
	 */
	private String selRoom;
	private String radClass;
	private static final long serialVersionUID = 1L;

	public  String execute(){
		String command="IMVG_ID=aa:bb:cc:dd"+Constants.NEW_LINE+"DEVICE_ID="+this.selRoom+Constants.NEW_LINE+"CMD_ID="+this.radClass+Constants.NEW_LINE;
		Map<String,String>map=IMonitorUtil.convertToMapFromCommand(command);
		String xml=IMonitorUtil.convertToXml(map);
		try {
			String address = IMonitorControllerCommunicator.getControllerAddress();
			String port = IMonitorControllerCommunicator.getControllerPort();
			String endpoint = "http://" + address + ":" + port + "/imonitorcontroller/services/deviceControlService";
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("controlDevice"));
			@SuppressWarnings("unused")
			String ret = (String) call.invoke(new Object[] {xml});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getSelRoom() {
		return selRoom;
	}

	public void setSelRoom(String selRoom) {
		this.selRoom = selRoom;
	}

	public String getRadClass() {
		return radClass;
	}

	public void setRadClass(String radClass) {
		this.radClass = radClass;
	}
	
	
}
