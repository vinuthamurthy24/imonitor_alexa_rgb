/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.cms.communicator.IMonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWayType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class GateWayTypeAction extends ActionSupport {
	/**
	 * Asmodeus
	 */
	private static final long serialVersionUID = 1L;
	private List<Make> makes;
	private GateWayType gateWayType;
	private List<GateWayType> gateWayTypes;

	public String addGateWayType() throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		XStream stream = new XStream();
		String xmlString = stream.toXML(gateWayType);
		try {
			String address = IMonitorControllerCommunicator.getControllerAddress();
			String port = IMonitorControllerCommunicator.getControllerPort();
			String endpoint = "http://" + address + ":" + port
			+ "/imonitorcontroller/services/gateWayTypeService";
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("saveGateWayType"));
			call.invoke(new Object[] { xmlString });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String toAddGateWayType() {
		try {
			String address = IMonitorControllerCommunicator.getControllerAddress();
			String port = IMonitorControllerCommunicator.getControllerPort();
			String endpoint = "http://" + address + ":" + port
			+ "/imonitorcontroller/services/makeService";
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("listMakes"));
			String valueInXml = (String) call.invoke(new Object[] {});
			XStream stream = new XStream();
			makes = (List<Make>) stream.fromXML(valueInXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public List<Make> getMakes() {
		return makes;
	}

	public void setMakes(List<Make> makes) {
		this.makes = makes;
	}

	public GateWayType getGateWayType() {
		return gateWayType;
	}

	public void setGateWayType(GateWayType gateWayType) {
		this.gateWayType = gateWayType;
	}

	public List<GateWayType> getGateWayTypes() {
		return gateWayTypes;
	}

	public void setGateWayTypes(List<GateWayType> gateWayTypes) {
		this.gateWayTypes = gateWayTypes;
	}

}
