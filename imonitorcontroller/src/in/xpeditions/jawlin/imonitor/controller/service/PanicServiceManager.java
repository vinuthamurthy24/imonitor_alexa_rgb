/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;


import in.xpeditions.jawlin.imonitor.controller.dao.entity.Panic;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.PanicManager;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class PanicServiceManager {

	public String savePanicWithActionAndNotification(String ruleXml, String action, String notificationXml){
		XStream xStream  = new XStream();
		Panic panic = (Panic) xStream.fromXML(ruleXml);
		String[] userNotificationProfiles = (String[]) xStream.fromXML(notificationXml);
		PanicManager panicManager = new PanicManager(); 
		panicManager.savePanicWithActionAndNotification(panic, action, userNotificationProfiles);
		return "success";
	}
	
	public String updatePanicWithActionAndNotification(String panicXml, String action, String notificationXml){
		XStream xStream  = new XStream();
		Panic panic = (Panic) xStream.fromXML(panicXml);
		String[] userNotificationProfiles = (String[]) xStream.fromXML(notificationXml);
		PanicManager panicManager = new PanicManager(); 
		Panic selectedPanic = null;
		selectedPanic = panicManager.getPanicByCustomerId(panic.getCustomer().getId());
		if( selectedPanic!=null){
			panicManager.deleteAlertActionsAndUserNotificationsByPanicId(selectedPanic.getId());
		}
		panicManager.savePanicWithActionAndNotification(panic, action, userNotificationProfiles);
		return "success";
	}
	
	public String getPanicAndAlertsByCustomerId(String customerId) {
		PanicManager panicManager = new PanicManager(); 
		Panic panic = panicManager.getPanicWithByCustomerId(customerId);
		XStream stream = new XStream();
		return stream.toXML(panic);
	}
}
