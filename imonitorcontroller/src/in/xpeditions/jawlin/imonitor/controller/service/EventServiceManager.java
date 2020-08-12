/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;

import java.util.List;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Event;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.EventManager;

import com.thoughtworks.xstream.XStream;

/**
 * @author computer
 *
 */
public class EventServiceManager {
	public String saveEvent(String xml){
		XStream stream = new XStream();
		String items[];
		items = xml.split("-");
		User user = (User)stream.fromXML(items[0]);
		String actionPerformed = (String) stream.fromXML(items[1]);
		Event event = new Event();
		event.setUser(user);
		event.setActionPerformed(actionPerformed);
		EventManager eventManager = new EventManager();
		eventManager.save(event);
		return stream.toXML(event);
	}
	public String listFirstTenEvents(String xml){
		XStream stream = new XStream();
		User user = (User) stream.fromXML(xml);
		EventManager eventManager = new EventManager();
		List<Event>events = eventManager.listFirstTenEvents(user);
		return stream.toXML(events);
	}
}
