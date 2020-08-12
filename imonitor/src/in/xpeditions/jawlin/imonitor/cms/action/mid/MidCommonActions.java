/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.mid;

import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class MidCommonActions extends ActionSupport {
	private String data;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5859258685545122261L;

	public String synchronizeGateWayDetails() throws Exception {
		String serviceName = "midService";
		String method = "synchronizeGateWayDetails";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String synchronizeDeviceDetails() throws Exception {
		String serviceName = "midService";
		String method = "synchronizeDeviceDetails";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String synchronizeScenarioDetails() throws Exception {
		String serviceName = "midService";
		String method = "synchronizeScenarioDetails";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String controldevice() throws Exception {
		String serviceName = "midService";
		String method = "controldevice";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String Devicestatus() throws Exception {
		String serviceName = "midService";
		String method = "deviceStatus";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String executeScenario() throws Exception {
		String serviceName = "midService";
		String method = "executeScenario";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String armDevice() throws Exception {
		String serviceName = "midService";
		String method = "armDevice";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String disarmDevice() throws Exception {
		String serviceName = "midService";
		String method = "disarmDevice";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String startLiveStream() throws Exception {
		String serviceName = "midService";
		String method = "startLiveStream";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String controlCamera() throws Exception {
		String serviceName = "midService";
		String method = "controlCamera";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String controlNightVision() throws Exception {
		String serviceName = "midService";
		String method = "controlNightVision";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}

	public String EnableCamera() throws Exception {
		String serviceName = "midService";
		String method = "Enablecamera";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}

	public String DisableCamera() throws Exception {
		String serviceName = "midService";
		String method = "Enablecamera";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String GetAlarm() throws Exception
	{
		String ServiceName="midService";
		String Method="GetAlarm";
		this.data=IMonitorUtil.sendToController(ServiceName, Method,data);
		return SUCCESS;
	}
	
	public String controlFanModes() throws Exception {
		String serviceName = "midService";
		String method = "controlFanModes";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String ControlGateway() throws Exception {
		String serviceName = "midService";
		String method = "ControlGateway";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String getRSSIValues() throws Exception {
		String serviceName = "midService";
		String method = "getRSSIValuesBasedOnDeviceId";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
public String partialopenstart() throws Exception {
		String serviceName = "midService";
		String method = "partialOpenStart";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	public String partialopenstop() throws Exception {
		String serviceName = "midService";
		String method = "partialOpenStop";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}public String partialclosestart() throws Exception {
		String serviceName = "midService";
		String method = "partialCloseStart";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}public String partialclosestop() throws Exception {
		String serviceName = "midService";
		String method = "partialCloseStop";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	

	public String controlIduDevice() throws Exception {
		
		String serviceName = "midService";
		String method = "controlIdu";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	
	public String controlIDUFanModes() throws Exception {
		
		String serviceName = "midService";
		String method = "controlIDUFanModes";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	
	public String fullOpenCLose() throws Exception {
		String serviceName = "midService";
		String method = "fullOpenClose";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	public String filterSignMid() throws Exception {
	
		String serviceName = "midService";
		String method = "filterSignMid";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	
	
	
	//SuperUSer
	public String verifySuperUser() throws Exception
	  {
	    String str1 = "midService";
	    String str2 = "verifySuperUser";
	    this.data = IMonitorUtil.sendToController(str1, str2, data);
	    return SUCCESS;
	  }
	
	public String customerListForSuperUser() throws Exception
	  {
	    String str1 = "midService";
	    String str2 = "customerListForSuperUser";
	    this.data = IMonitorUtil.sendToController(str1, str2, data);
	    return SUCCESS;
	  }
	
	public String singleCustomerDeviceDetailsForSuperUser() throws Exception
	  {
	    String str1 = "midService";
	    String str2 = "singleCustomerDeviceDetailsForSuperUser";
	    this.data = IMonitorUtil.sendToController(str1, str2, data);
	    return SUCCESS;
	  }
	
	public String multipleCustomerDeviceDetailsForSuperUser() throws Exception
	  {
	    String str1 = "midService";
	    String str2 = "multipleCustomerDeviceDetailsForSuperUser";
	    this.data = IMonitorUtil.sendToController(str1, str2, data);
	    return SUCCESS;
	  }
	
	public String armDevicesSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "armDevicesSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String disArmDevicesSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "disArmDevicesSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String scenarioDetailsSingleUser() throws Exception {
		String serviceName = "midService";
		String method = "scenarioDetailsSingleUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String executeScenarioSingleUser() throws Exception {
		String serviceName = "midService";
		String method = "executeScenarioSingleUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String scenarioDetailsMultipleUser() throws Exception {
		String serviceName = "midService";
		String method = "scenarioDetailsMultipleUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String GetAlarmForSingleUser() throws Exception {
		String serviceName = "midService";
		String method = "GetAlarmForSingleUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String GetAlarmForMultipleUser() throws Exception {
		String serviceName = "midService";
		String method = "GetAlarmForMultipleUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String ScheduleDetailsForSingleUser() throws Exception {
		String serviceName = "midService";
		String method = "ScheduleDetailsForSingleUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String ScheduleDetailsForMultipleUser() throws Exception {
		String serviceName = "midService";
		String method = "ScheduleDetailsForMultipleUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String activateScheduleforSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "activateScheduleforSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String controlSuperUserdevice() throws Exception {
		String serviceName = "midService";
		String method = "controlSuperUserdevice";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String startLiveStreamForSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "startLiveStreamForSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String controlCameraForSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "controlCameraForSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String controlNightVisionForSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "controlNightVisionForSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String partialOpenStartForSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "partialOpenStartForSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	public String partialOpenStopForSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "partialOpenStopForSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	public String partialCloseStartForSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "partialCloseStartForSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	public String partialCloseStopForSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "partialCloseStopForSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String fullOpenCloseForSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "fullOpenCloseForSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String controlFanModesForSuperUser() throws Exception {
		String serviceName = "midService";
		String method = "controlFanModesForSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String saveDeviceDetailsForAndroidPushNotification() throws Exception
	{
		LogUtil.info("saveDeviceDetailsForAndroidPushNotification"+this.data);
		String serviceName = "midService";
		String method = "saveDeviceDetailsForAndroidPushNotification";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String disablePushNotification() throws Exception
	{
		String serviceName = "midService";
		String method = "disablePushNotificationAfterCustomerLogout";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String disablePushNotificationForSuperUser() throws Exception
	{
		String serviceName = "midService";
		String method = "disablePushNotificationForSuperUser";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String saveDeviceDetailsForIOSPushNotification() throws Exception
	{
		String serviceName = "midService";
		String method = "saveDeviceDetailsForIOSPushNotification";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	public String disablePushNotificationForIOS() throws Exception
	{
		String serviceName = "midService";
		String method = "disablePushNotificationForIOS";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
	
	//RGB
	public String sendRGBValue() throws Exception {
		LogUtil.info("sendRGBValue start");	
		String serviceName = "midService";
			String method = "sendRGBValue";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("sendRGBValue end");
			return SUCCESS;
		}
	
	public String sendRGBValueForSuperUser() throws Exception {
		LogUtil.info("sendRGBValueForSuperUser start");	
		String serviceName = "midService";
			String method = "sendRGBValueForSuperUser";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("sendRGBValueForSuperUser end");
			return SUCCESS;
		}
	//RGB End
	
	// syncGatewayDetails with Mobile Icons start
	public String synchronizegatewaydetailsLiIcon() throws Exception {
		String serviceName = "midService";
		String method = "synchronizegatewaydetailsLiIcon";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		return SUCCESS;
	}
		
	public String singleCustomerDeviceDetailsForSuperUserLiIcon() throws Exception {
			String serviceName = "midService";
			String method = "singleCustomerDeviceDetailsForSuperUserLiIcon";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			return SUCCESS;
	}
			
	public String multipleCustomerDeviceDetailsForSuperUserLiIcon() throws Exception {
				String serviceName = "midService";
				String method = "multipleCustomerDeviceDetailsForSuperUserLiIcon";
				this.data = IMonitorUtil.sendToController(serviceName, method, data);
				return SUCCESS;
	}
	// syncGatewayDetails with Mobile Icons end
	
	public String rgbDiscoAndPartyMode() throws Exception {
		LogUtil.info("rgbDiscoMode start");	
		String serviceName = "midService";
			String method = "rgbDiscoAndPartyMode";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("rgbDiscoAndPartyMode end");
			return SUCCESS;
		}
	
	public String rgbDiscoAndPartyModeForSuperUser() throws Exception {
		LogUtil.info("rgbDiscoAndPartyModeForSuperUser start");	
		String serviceName = "midService";
			String method = "rgbDiscoAndPartyModeForSuperUser";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("rgbDiscoAndPartyModeForSuperUser end");
			return SUCCESS;
		}
	
	//Self Registration Mid
	
	
	public String zingMacIdValidationForMid() throws Exception {
		LogUtil.info("zingMacIdValidationForMid start");	
		String serviceName = "midService";
			String method = "zingMacIdValidationForMid";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("zingMacIdValidationForMid end");
			return SUCCESS;
		}
	
	public String customerIdValidationForMid() throws Exception {
		LogUtil.info("customerIdValidationForMid start");	
		String serviceName = "midService";
			String method = "customerIdValidationForMid";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("customerIdValidationForMid end");
			return SUCCESS;
		}
	
	public String saveZingGatewayForMid() throws Exception {
		LogUtil.info("saveZingGatewayForMid start");	
		String serviceName = "midService";
			String method = "saveZingGatewayForMid";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("saveZingGatewayForMid end");
			return SUCCESS;
		}
	
	public String sendOTPForSelfRegistration() throws Exception {
		LogUtil.info("sendOTPForSelfRegistration start"+this.data);	
		String serviceName = "midService";
			String method = "sendOTPForSelfRegistration";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("sendOTPForSelfRegistration end");
			return SUCCESS;
		}
	
	
	//Edit device friendly name
	public String editDeviceNameMID() throws Exception {
		LogUtil.info("editDeviceNameMID start");	
		String serviceName = "midService";
			String method = "editDeviceNameMID";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("editDeviceNameMID end");
			return SUCCESS;
		}
	
	//Add new Room From mobile
	public String addNewRoomMID() throws Exception {
		
		String serviceName = "midService";
			String method = "addNewRoomMID";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			
			return SUCCESS;
		}
	
	public String synchronizeLocationDetails() throws Exception {
			
		String serviceName = "midService";
			String method = "synchronizeLocationDetails";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			
			return SUCCESS;
		}
	
	//Device discovery
	public String deviceDiscoveryMID() throws Exception {
		
		String serviceName = "midService";
			String method = "deviceDiscoveryMID";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			
			return SUCCESS;
		}
	
	//Device discovery abort 
	public String deviceDiscoveryAbortMID() throws Exception {
		LogUtil.info("deviceDiscoveryAbortMID start");	
		String serviceName = "midService";
			String method = "deviceDiscoveryAbortMID";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("deviceDiscoveryAbortMID end");
			return SUCCESS;
		}
	
	//Device discovery abort 
		public String panicRing() throws Exception {
			LogUtil.info("panic start");	
			String serviceName = "midService";
				String method = "panicRingAlert";
				this.data = IMonitorUtil.sendToController(serviceName, method, data);
				LogUtil.info("painc end");
				return SUCCESS;
			}
	
		public String changeUserPassword() throws Exception {
				
			String serviceName = "midService";
				String method = "checkAndUpdateUserPassword";
				this.data = IMonitorUtil.sendToController(serviceName, method, data);
				
				return SUCCESS;
			}
	
	public String requestAssociation() throws Exception {
		
		String serviceName = "midService";
			String method = "requestAssociateForSensor";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("association assistence end");
			return SUCCESS;
		}
	
	public final String getData() {
		return data;
	}

	public final void setData(String data) {
		this.data = data;
	}
	
		/*Schedule start*/
	//Schedule create
	public String createSchedule() throws Exception {
		LogUtil.info("createSchedule"+this.data);
			String serviceName = "midService";
			String method = "createScheduleMID";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			
			return SUCCESS;
		}
	//Schedules list
			public String synchronizeScheduleDetails() throws Exception {
				LogUtil.info("synchronizeScheduleDetails"+this.data);
				String serviceName = "midService";
				String method = "synchronizeScheduleDetails";
				this.data = IMonitorUtil.sendToController(serviceName, method, data);
				return SUCCESS;
			}

			
  //Schedule activation
		public String ActivateSchedule() throws Exception
		  {
			LogUtil.info("ActivateSchedule"+this.data);
		    String str1 = "midService";
		    String str2 = "activateScheduleFromMid";
		    this.data = IMonitorUtil.sendToController(str1, str2, data);
		    return SUCCESS;
		  }
		
		//Schedule save 
	public String saveSchedule() throws Exception
		  {
			LogUtil.info("saveSchedule"+this.data);
		    String str1 = "midService";
		    String str2 = "saveScheduleFromMid";
		    this.data = IMonitorUtil.sendToController(str1, str2, data);
		    return SUCCESS;
		  }
		
		
	//Schedule modification
	public String editSchedule() throws Exception {
		LogUtil.info("editSchedule"+this.data);
			String serviceName = "midService";
			String method = "editScheduleMID";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			
			return SUCCESS;
		}
   //Schedule delete
	public String deleteSchedule() throws Exception {
		LogUtil.info("deleteSchedule"+this.data);
		String serviceName = "midService";
		String method = "deleteScheduleMID";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	/*Schedule end*/
	
	/*SSID start*/
	
	//SSID create
	public String getSSIDInfo() throws Exception {
		LogUtil.info("getSSIDInfo"+this.data);
		String serviceName = "midService";
		String method = "getSSIDInfo";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	
	//SSID Modify
	public String setSSIDInfo() throws Exception {
		LogUtil.info("setSSIDInfo"+this.data);
		String serviceName = "midService";
		String method = "setSSIDInfo";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	
	/*SSID End*/
	
	//remove device
	public String removeDevice() throws Exception {
		LogUtil.info("removeDevice"+this.data);
		String serviceName = "midService";
		String method = "removeDevice";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}

	// create Scenario
	public String createScenario() throws Exception {
		LogUtil.info("createScenario"+this.data);
		String serviceName = "midService";
		String method = "createScenario";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	
	// save Scenario
		public String saveScenario() throws Exception {
			LogUtil.info("saveScenario"+this.data);
			String serviceName = "midService";
			String method = "saveScenario";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			
			return SUCCESS;
		}
   // edit Scenario
	public String editScenario() throws Exception {
		LogUtil.info("editScenario"+this.data);
		String serviceName = "midService";
		String method = "editScenario";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	
	//delete Scenario
	public String deleteScenario() throws Exception {
		LogUtil.info("deleteScenario"+this.data);
		String serviceName = "midService";
		String method = "deleteScenario";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	// list Action
		public String listActions() throws Exception {
			LogUtil.info("listActions"+this.data);
			String serviceName = "midService";
			String method = "listActions";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
					
			return SUCCESS;
		}
	
	// save virtual switch configuration 
	public String saveVirtualSwitchConfig() throws Exception {
		LogUtil.info("saveVirtualSwitchConfig"+this.data);
		String serviceName = "midService";
		String method = "saveVirtualSwitchConfig";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	
	// display virtual switch configuration 
	public String listVirtualSwitchConfig() throws Exception {
		LogUtil.info("listVirtualSwitchConfig"+this.data);
		String serviceName = "midService";
		String method = "listVirtualSwitchConfig";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
		
		return SUCCESS;
	}
	
	//create action
	public String createAction() throws Exception {
		LogUtil.info("createAction"+this.data);
		String serviceName="midService";
		String method="createAction";
		this.data=IMonitorUtil.sendToController(serviceName, method,data);
		return SUCCESS;
	}
	
	
	// save Action
	public String saveAction() throws Exception {
		LogUtil.info("saveAction"+this.data);
		String serviceName = "midService";
		String method = "saveAction";
		this.data = IMonitorUtil.sendToController(serviceName, method, data);
				
		return SUCCESS;
	}
	
	//Modify Action
	public String editAction() throws Exception {
		LogUtil.info("editAction"+this.data);
		String serviceName="midService";
		String method="editAction";
		this.data=IMonitorUtil.sendToController(serviceName, method,data);
		return SUCCESS;
		
	}
	
	//delete Action
	public String deleteAction() throws Exception {
		LogUtil.info("deleteAction"+this.data);
		String serviceName="midService";
		String method="deleteAction";
		this.data=IMonitorUtil.sendToController(serviceName, method,data);
		return SUCCESS;
		
	}
	
	public String registerCustomerFromMid() throws Exception {
		LogUtil.info("saveZingGatewayForMid start");	
		String serviceName = "midService";
			String method = "registerCustomerDetails";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("saveZingGatewayForMid end");
			return SUCCESS;
		}
	
	public String registerGatewayWithCustomer() throws Exception {
		LogUtil.info("saveZingGatewayForMid start" + data);	
		String serviceName = "midService";
			String method = "registerGatewayWithCustomer";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("saveZingGatewayForMid end" + this.data);
			return SUCCESS;
		}
	
	public String unregisterGatewayRequest() throws Exception {
		LogUtil.info("saveZingGatewayForMid start");	
		String serviceName = "midService";
			String method = "unregisterGatewayRequest";
			this.data = IMonitorUtil.sendToController(serviceName, method, data);
			LogUtil.info("saveZingGatewayForMid end");
			return SUCCESS;
		}
}
