package Oauth2.imonitor.detail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


public class OAuth2Scope {

	
	public static final String SCOPE_ARM_STAY = "STAY";
	public static final String SCOPE_ARM_AWAY = "AWAY";
	public static final String SCOPE_DISARM_HOME = "HOME";
	public static final String SCOPE_SCENARIO_EXECUTE = "Execute";
	public static final String SCOPE_ALARMS = "GetAlarms";
	public static final String SCOPE_GET_DEVICES = "GetDevices";
	public static final String SCOPE_GET_SCENARIO = "GetScenario";
	public static final String SCOPE_LOG_OUT = "LogOut";
	public static final String SCOPE_AC_FAN_MODE = "FanMode";
	public static final String SCOPE_CONTROL_DEVICE = "ControlDev";
    
	
	
	
	
	
	
	
	
	
	
	/*public static boolean isScopeValid(String receivedScope, String registeredClientScope) {
		String rscopes[] = receivedScope.split(",");
		String temp[] = registeredClientScope.split(",");
		
		List<String> sscopes = Arrays.asList(temp);
		//System.out.println(sscopes);
		boolean isValid = true;
		for (int i=0; i < rscopes.length; i++) {
			if (sscopes.contains(rscopes[i]) == false) {
				isValid = false;
				break;
			}
		}
		return isValid;
	}

	public static boolean isUriScopeValid(String uriScope, String tokenScopes) {
		String temp[] = tokenScopes.split(",");
		List<String> sscopes = Arrays.asList(temp);
		if (sscopes.contains(uriScope))
			return true;
		else
			return false;
	}*/
}
