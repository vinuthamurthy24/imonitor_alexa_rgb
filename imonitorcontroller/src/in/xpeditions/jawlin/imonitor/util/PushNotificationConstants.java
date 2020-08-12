package in.xpeditions.jawlin.imonitor.util;

import java.util.HashMap;
import java.util.Map;

public class PushNotificationConstants 
{
	public static Map<Object, String> pushNoticationConstants = new HashMap<Object,String>();
	{
		pushNoticationConstants.put("MOTION_DETECTED","Motion Detected");
		pushNoticationConstants.put("NOMOTION_DETECTED","No Motion Detected");
		pushNoticationConstants.put("DEVICE_UP","Device up");
		pushNoticationConstants.put("DEVICE_DOWN","Device down");
		pushNoticationConstants.put("BATTERY_STATUS","Battery level is less than 10%");
		pushNoticationConstants.put("DOOR_LOCK_OPEN","Door Lock Open");
		pushNoticationConstants.put("DOOR_LOCK_CLOSE","Door Lock Close");
		pushNoticationConstants.put("DOOR_OPEN","Door is Opened");
		pushNoticationConstants.put("DOOR_CLOSE","Door is Closed");
		pushNoticationConstants.put("STREAM_FTP_SUCCESS","Stream FTP Success");
		pushNoticationConstants.put("STREAM_FTP_FAILED","Stream FTP Failed");
		pushNoticationConstants.put("IMAGE_FTP_SUCCESS","Image FTP Success");
		pushNoticationConstants.put("IMAGE_FTP_FAILED","Image FTP Failed");
		pushNoticationConstants.put("PANIC_SITUATION","Panic Situation");
		pushNoticationConstants.put("DEVICE_STATE_CHANGED","Device State Changed");
		pushNoticationConstants.put("CURTAIN_OPEN","Curtain Open");
		pushNoticationConstants.put("CURTAIN_CLOSE","Curtain Close");
		pushNoticationConstants.put("REQUEST_NEIGHBOR_UPDATE_STARTED","Requested Neighbor Update Started");
		pushNoticationConstants.put("REQUEST_NEIGHBOR_UPDATE_DONE","Requested Neighbor Update Done");
		pushNoticationConstants.put("REQUEST_NEIGHBOR_UPDATE_FAILED","Requested Neighbor Update Failed");
		pushNoticationConstants.put("HOME_SUCCESS","Home Success");
		pushNoticationConstants.put("HOME_FAILURE","Home Failure");
		pushNoticationConstants.put("STAY_SUCCESS","Stay Success");
		pushNoticationConstants.put("STAY_FAILURE","Stay Failure");
		pushNoticationConstants.put("AWAY_SUCCESS","Away Success");
		pushNoticationConstants.put("AWAY_FAILURE","Away Failure");
		pushNoticationConstants.put("SHOCK_DETECTED","Shock Detected");
		pushNoticationConstants.put("SHOCK_STOPPED","Shock Stopped");
		pushNoticationConstants.put("SMOKE_SENSED","Smoke Sensed");
		pushNoticationConstants.put("SMOKE_CLEARED","Smoke Cleared");
		pushNoticationConstants.put("TEMPERATURE_CHANGE","Temperature changed");
		pushNoticationConstants.put("SENSED_LIGHT_INTENSITY","Sensed Light Intensity");
		pushNoticationConstants.put("HMD_WARNING","HMD Warning");
		pushNoticationConstants.put("HMD_NORMAL","HMD Normal");
		pushNoticationConstants.put("HMD_FAILURE","HMD Failure");
		pushNoticationConstants.put("POWER_INFORMATION","Power Information");
		pushNoticationConstants.put("REQUEST_CONFIGURATION_STARTED","Request Configuration Started");
		pushNoticationConstants.put("REQUEST_CONFIGURATION_DONE","Request Configuration Done");
		pushNoticationConstants.put("REQUEST_CONFIGURATION_FAILED","Request Configuration Failed");
		pushNoticationConstants.put("REQUEST_ASSOCIATION_STARTED","Request Association Started");
		pushNoticationConstants.put("REQUEST_ASSOCIATION_DONE","Request Association Done");
		pushNoticationConstants.put("REQUEST_ASSOCIATION_FAILED","Request Association Failed");
		pushNoticationConstants.put("PWR_LMT_WARNING","Power Limit Warning");
		pushNoticationConstants.put("PWR_LMT_REACHED","Power Limit Reached");
		pushNoticationConstants.put("SCHEDULE_EXEC_FAIL","Schedule Execution Failed");
		pushNoticationConstants.put("SCHEDULE_EXEC_SUCCESS","Schedule Execution Success");
		pushNoticationConstants.put("ASSIGN_RETURN_ROUTE_SUCCESS","Assigned Return Route Success");
		pushNoticationConstants.put("ASSIGN_RETURN_NO_ROUTE","Assigned Return Route Success");
		pushNoticationConstants.put("ASSIGN_RETURN_ROUTE_FAIL","Assigned Return Route Failed");
		pushNoticationConstants.put("NEW_ALARM","New Alarm");
		pushNoticationConstants.put("DEVICE_OFF","Device Turned Off");
		pushNoticationConstants.put("DEVICE_ON","Device Turned On");
		pushNoticationConstants.put("NORMAL_SWITCH_CONFIG_SUCCESS","Normal Switch Configuration Failed");
		pushNoticationConstants.put("NODE_PROTOCOL_INFO_SUCCESS","Node Protocol Info Success");
		pushNoticationConstants.put("ROUTING_INFO_SUCCESS","Routing Info Success");
		pushNoticationConstants.put("NODE_PROTOCOL_INFO_FAILED","Node Protocol Info Failed");
		pushNoticationConstants.put("ROUTING_INFO_FAILED","Routing Info Failed");
		pushNoticationConstants.put("SENSED_PM_LEVEL","Sensed PM Level");
		pushNoticationConstants.put("ENERGY_INFORMATION","Energy Information");	
		pushNoticationConstants.put("DOOR_IS_OPENED","Door left opened");	
	}
	
}
