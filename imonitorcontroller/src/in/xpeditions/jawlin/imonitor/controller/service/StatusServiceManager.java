/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.StatusManager;

import java.util.List;

import com.thoughtworks.xstream.XStream;

public class StatusServiceManager {
	
	DaoManager daoManager=new DaoManager();
	public String listStatuses() {
		String xml = "";
		XStream xStream = new XStream();
		StatusManager statusManager = new StatusManager();
		List<Status> statuses = statusManager.listStatuses();
		xml = xStream.toXML(statuses);
		return xml;
	}
	public String getStatusByName(String paramXml) {
		String xml = "";
		XStream xStream = new XStream();
		String statusName = (String) xStream.fromXML(paramXml);
		StatusManager statusManager = new StatusManager();
		Status statuses = statusManager.getStatusByName(statusName);
		xml = xStream.toXML(statuses);
		return xml;
	}
	
	public Status getStatusTypeByName(String name){
		
		Status status = (Status)daoManager.getOne("name", name, Status.class);
		
		return status;
	}
}
