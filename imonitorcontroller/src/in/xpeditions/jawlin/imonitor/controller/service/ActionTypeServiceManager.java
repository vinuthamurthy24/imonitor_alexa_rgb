/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ActionTypeManager;
import in.xpeditions.jawlin.imonitor.util.Constants;

import java.util.List;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class ActionTypeServiceManager {
	public String getSystemActions(String xml){
		String[] actions = new String[]{Constants.SEND_EMAIL, Constants.SEND_SMS, Constants.SEND_SMS_EMAIL};
		ActionTypeManager actionTypeManager = new ActionTypeManager();
		List<ActionType> actionTypes = actionTypeManager.getActionTypesByName(actions);
		XStream stream = new XStream();
		String result = stream.toXML(actionTypes);
		return result;
	}
	
	public String getActiontypebyselectedName(String xml){
		XStream stream = new XStream();
		//String[] actions = new String[]{Constants.SEND_EMAIL, Constants.SEND_SMS, Constants.SEND_SMS_EMAIL};
	    String actionname = (String) stream.fromXML(xml);
		ActionTypeManager actionTypeManager = new ActionTypeManager();
		ActionType actionTypes = actionTypeManager.getActionTypeName(actionname);
		String result = stream.toXML(actionTypes);
		return result;
	}
}
