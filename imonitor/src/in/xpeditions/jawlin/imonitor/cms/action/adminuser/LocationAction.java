/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.adminuser;

import in.xpeditions.jawlin.imonitor.cms.communicator.IMonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class LocationAction extends ActionSupport {

	/**
	 * @author Asmodeus
	 */
	private static final long serialVersionUID = -4569327681810053669L;
	private Location location;
	public String addLocation() throws ServiceException, MalformedURLException, RemoteException{
		String address = IMonitorControllerCommunicator.getControllerAddress();
		String port = IMonitorControllerCommunicator.getControllerPort();
		XStream xStream=new XStream();
		String xml=xStream.toXML(location);
		String endpoint = "http://" + address + ":" + port
		+ "/imonitorcontroller/services/locationService";
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName("saveLocation"));
		call.invoke(new Object[] {xml});
		ActionContext.getContext().getSession().put("message", "Location "+location.getName()+"saved succussfully");
		return SUCCESS;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
}
