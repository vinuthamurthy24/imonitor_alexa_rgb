/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import java.util.List;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Event;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;

/**
 * @author computer
 *
 */
public class EventManager {
	DaoManager daoManager = new DaoManager();
	@SuppressWarnings("unchecked")
	public List<Event> listFirstTenEvents(User user) {
		List<Event>events = daoManager.listFirstTenEvents("user",user,Event.class);
		return events;
	}

	public boolean save(Event event) {
		boolean result=false;
		try{
			daoManager.save(event);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}

}
