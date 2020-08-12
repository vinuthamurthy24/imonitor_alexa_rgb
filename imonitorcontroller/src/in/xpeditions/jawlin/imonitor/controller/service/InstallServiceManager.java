/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Role;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemAlert;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ActionTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AlertTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceTypeManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RoleManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.StatusManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.UserManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.ArrayList;
import java.util.List;


public class InstallServiceManager {

	public void install() {
		Status status=new Status();
		status.setName("Running");
		status.setDescription("running");
		
		Status status2 = new Status();
		status2.setName("Stopping");
		status2.setDescription("stopping");
		
		Status status3 = new Status();
		status3.setName(Constants.REGISTERED);
		status3.setDescription("Registered");
		
		Status status4 = new Status();
		status4.setName(Constants.READY_TO_REGISTER);
		status4.setDescription("Not Registered");
		
		Status status5 = new Status();
		status5.setName(Constants.UN_REGISTERED);
		status5.setDescription("Un Registered");
		
		Status status6 = new Status();
		status6.setName(Constants.DEVICE_DISCOVERY_MODE);
		status6.setDescription("Device Discovery Mode");
		
		Status status7 = new Status();
		status7.setName(Constants.ONLINE);
		status7.setDescription("Online Status");
		
		Status status8 = new Status();
		status8.setName(Constants.OFFLINE);
		status8.setDescription("Offline Status");
		
		Status status9 = new Status();
		status9.setName(Constants.SUSPEND);
		status9.setDescription(Constants.SUSPEND_USER);
		
		Status armStatus = new Status();
		armStatus.setName(Constants.ARM);
		armStatus.setDescription("Arm Status");
		
		Status disarmStatus = new Status();
		disarmStatus.setName(Constants.DISARM);
		disarmStatus.setDescription("Disarm Status");
		
		StatusManager statusManager=new StatusManager();
		statusManager.save(status);
		statusManager.save(status2);
		statusManager.save(status3);
		statusManager.save(status4);
		statusManager.save(status5);
		statusManager.save(status6);
		statusManager.save(status7);
		statusManager.save(status8);
		statusManager.save(status9);
		statusManager.save(armStatus);
		statusManager.save(disarmStatus);
		
		Role role = new Role();
		role.setName(Constants.MAIN_USER);
		role.setDetails(Constants.MAIN_USER);
		
		Role normalUserRole= new Role();
		normalUserRole.setName(Constants.NORMAL_USER);
		normalUserRole.setDetails(Constants.NORMAL_USER);
		
		Role adminUserRole= new Role();
		adminUserRole.setName(Constants.ADMIN_USER);
		adminUserRole.setDetails(Constants.ADMIN_USER);
		
		RoleManager roleManager=new RoleManager();
		roleManager.save(role);
		roleManager.save(normalUserRole);
		roleManager.save(adminUserRole);
		
//		Installing one admin customer.
		Customer customer = new Customer();
		customer.setCustomerId("admin");
		new CustomerManager().saveCustomer(customer);
		
//      Installing System Alert
		
		SystemAlert systemAlert = new SystemAlert();
		
		DaoManager daoManager  = new DaoManager();
		systemAlert.setName(Constants.LOG_IN);
		daoManager.save(systemAlert);
		
		systemAlert.setName(Constants.PW_CHANGE);
		daoManager.save(systemAlert);
		
		systemAlert.setName(Constants.MEMORY_QUOTA_FULL);
		daoManager.save(systemAlert);
		
		systemAlert.setName(Constants.MEMORY_QUOTA_NOTFULL);
		daoManager.save(systemAlert);
		
		systemAlert.setName(Constants.ADD_NEW_GATEWAY);
		daoManager.save(systemAlert);
		
		systemAlert.setName(Constants.DELETE_GATEWAY);
		daoManager.save(systemAlert);
		
		systemAlert.setName(Constants.ADD_USER);
		daoManager.save(systemAlert);
		
		systemAlert.setName(Constants.DELETE_USER);
		daoManager.save(systemAlert);
		
		systemAlert.setName(Constants.IMVG_ON);
		daoManager.save(systemAlert);
		
		systemAlert.setName(Constants.IMVG_OFF);
		daoManager.save(systemAlert);
		
		systemAlert.setName(Constants.SUSPEND_USER);
		daoManager.save(systemAlert);
		
		
		
//		Installing one admin user.
		User user = new User();
		String password = "admin";
		user.setUserId("admin");
		String hashPassword = IMonitorUtil.getHashPassword(password);
		user.setPassword(hashPassword);
		user.setCustomer(customer);
		user.setRole(adminUserRole);
		new UserManager().saveUser(user);
		
		
//		Installing different alert types
		AlertType imvgUpAlert = new AlertType();
		imvgUpAlert.setDetails(Constants.IMVG_UP);
		imvgUpAlert.setAlertCommand(Constants.IMVG_UP);
		imvgUpAlert.setName(Constants.IMVG_UP_NAME);
		
		AlertType motionDetectedAlert = new AlertType();
		motionDetectedAlert.setDetails(Constants.MOTION_DETECTED);
		motionDetectedAlert.setAlertCommand(Constants.MOTION_DETECTED);
		motionDetectedAlert.setName(Constants.MOTION_DETECTED_NAME);
		
		AlertType noMotionDetectedAlert = new AlertType();
		noMotionDetectedAlert.setDetails(Constants.NOMOTION_DETECTED);
		noMotionDetectedAlert.setAlertCommand(Constants.NOMOTION_DETECTED);
		noMotionDetectedAlert.setName(Constants.NOMOTION_DETECTED_NAME);
		
		AlertType deviceDownAlert = new AlertType();
		deviceDownAlert.setDetails(Constants.DEVICE_DOWN);
		deviceDownAlert.setAlertCommand(Constants.DEVICE_DOWN);
		deviceDownAlert.setName(Constants.DEVICE_DOWN_NAME);
		
		AlertType deviceUpAlert = new AlertType();
		deviceUpAlert.setDetails(Constants.DEVICE_UP);
		deviceUpAlert.setAlertCommand(Constants.DEVICE_UP);
		deviceUpAlert.setName(Constants.DEVICE_UP_NAME);
		
		AlertType panicAlert = new AlertType();
		panicAlert.setDetails(Constants.PANIC_SITUATION);
		panicAlert.setAlertCommand(Constants.PANIC_SITUATION);
		panicAlert.setName(Constants.PANIC_SITUATION);
		
		AlertType batteryStatusAlert = new AlertType();
		batteryStatusAlert.setDetails(Constants.BATTERY_STATUS);
		batteryStatusAlert.setAlertCommand(Constants.BATTERY_STATUS);
		batteryStatusAlert.setName(Constants.BATTERY_STATUS_NAME);
		
		AlertType doorLockCloseAlert = new AlertType();
		doorLockCloseAlert.setDetails(Constants.DOOR_LOCK_CLOSE);
		doorLockCloseAlert.setAlertCommand(Constants.DOOR_LOCK_CLOSE);
		doorLockCloseAlert.setName(Constants.DOOR_LOCK_CLOSE_NAME);
		
		AlertType doorLockOpenAlert = new AlertType();
		doorLockOpenAlert.setDetails(Constants.DOOR_LOCK_OPEN);
		doorLockOpenAlert.setAlertCommand(Constants.DOOR_LOCK_OPEN);
		doorLockOpenAlert.setName(Constants.DOOR_LOCK_OPEN_NAME);
		
		AlertType doorOpenAlert = new AlertType();
		doorOpenAlert.setDetails(Constants.DOOR_OPEN);
		doorOpenAlert.setAlertCommand(Constants.DOOR_OPEN);
		doorOpenAlert.setName(Constants.DOOR_OPEN_NAME);
		
		AlertType doorCloseAlert = new AlertType();
		doorCloseAlert.setDetails(Constants.DOOR_CLOSE);
		doorCloseAlert.setAlertCommand(Constants.DOOR_CLOSE);
		doorCloseAlert.setName(Constants.DOOR_CLOSE_NAME);
		//vibhu start
		AlertType nUpdateStartAlert = new AlertType();
		nUpdateStartAlert.setDetails(Constants.REQUEST_NEIGHBOR_UPDATE_STARTED);
		nUpdateStartAlert.setAlertCommand(Constants.REQUEST_NEIGHBOR_UPDATE_STARTED);
		nUpdateStartAlert.setName(Constants.REQUEST_NEIGHBOR_UPDATE_STARTED_NAME);
		
		AlertType nUpdateDoneAlert = new AlertType();
		nUpdateDoneAlert.setDetails(Constants.REQUEST_NEIGHBOR_UPDATE_DONE);
		nUpdateDoneAlert.setAlertCommand(Constants.REQUEST_NEIGHBOR_UPDATE_DONE);
		nUpdateDoneAlert.setName(Constants.REQUEST_NEIGHBOR_UPDATE_DONE_NAME);
		
		AlertType nUpdateFailAlert = new AlertType();
		nUpdateStartAlert.setDetails(Constants.REQUEST_NEIGHBOR_UPDATE_FAILED);
		nUpdateStartAlert.setAlertCommand(Constants.REQUEST_NEIGHBOR_UPDATE_FAILED);
		nUpdateStartAlert.setName(Constants.REQUEST_NEIGHBOR_UPDATE_FAILED_NAME);
		//vibhu end
		AlertType streamUploadSuccessAlert = new AlertType();
		streamUploadSuccessAlert.setDetails(Constants.STREAM_FTP_SUCCESS);
		streamUploadSuccessAlert.setAlertCommand(Constants.STREAM_FTP_SUCCESS);
		streamUploadSuccessAlert.setName(Constants.STREAM_FTP_SUCCESS_NAME);
		
		AlertType streamUploadFailureAlert = new AlertType();
		streamUploadFailureAlert.setDetails(Constants.STREAM_FTP_FAILED);
		streamUploadFailureAlert.setAlertCommand(Constants.STREAM_FTP_FAILED);
		streamUploadFailureAlert.setName(Constants.STREAM_FTP_FAILED_NAME);
		
		AlertType imageUploadSuccessAlert = new AlertType();
		imageUploadSuccessAlert.setDetails(Constants.IMAGE_FTP_SUCCESS);
		imageUploadSuccessAlert.setAlertCommand(Constants.IMAGE_FTP_SUCCESS);
		imageUploadSuccessAlert.setName(Constants.IMAGE_FTP_SUCCESS_NAME);
		
		AlertType imageUploadFailureAlert = new AlertType();
		imageUploadFailureAlert.setDetails(Constants.IMAGE_FTP_FAILED);
		imageUploadFailureAlert.setAlertCommand(Constants.IMAGE_FTP_FAILED);
		imageUploadFailureAlert.setName(Constants.IMAGE_FTP_FAILED_NAME);
		
		AlertType deviceStateChangedAlert = new AlertType();
		deviceStateChangedAlert.setDetails(Constants.DEVICE_STATE_CHANGED);
		deviceStateChangedAlert.setAlertCommand(Constants.DEVICE_STATE_CHANGED);
		deviceStateChangedAlert.setName(Constants.DEVICE_STATE_CHANGED_NAME);
		
		AlertTypeManager alertTypeManager = new AlertTypeManager();
		alertTypeManager.save(imvgUpAlert);
		alertTypeManager.save(motionDetectedAlert);
		alertTypeManager.save(noMotionDetectedAlert);
		alertTypeManager.save(deviceDownAlert);
		alertTypeManager.save(deviceUpAlert);
		alertTypeManager.save(batteryStatusAlert);
		alertTypeManager.save(doorLockCloseAlert);
		alertTypeManager.save(doorLockOpenAlert);
		alertTypeManager.save(doorOpenAlert);
		alertTypeManager.save(doorCloseAlert);
		alertTypeManager.save(streamUploadSuccessAlert);
		alertTypeManager.save(streamUploadFailureAlert);
		alertTypeManager.save(imageUploadSuccessAlert);
		alertTypeManager.save(imageUploadFailureAlert);
		alertTypeManager.save(panicAlert);
		alertTypeManager.save(deviceStateChangedAlert);
		//vibhu start
		alertTypeManager.save(nUpdateFailAlert);
		alertTypeManager.save(nUpdateDoneAlert);
		alertTypeManager.save(nUpdateStartAlert);
		//vibhu end
//		installing different type alertActions
		ActionType captureImage = new ActionType();
		captureImage.setCommand(Constants.CAPTURE_IMAGE);
		captureImage.setDetails(Constants.CAPTURE_IMAGE);
		captureImage.setName(Constants.CAPTURE_IMAGE_NAME);

		ActionType captureVideo = new ActionType();
		captureVideo.setCommand(Constants.START_RECORDING);
		captureVideo.setDetails(Constants.START_RECORDING);
		captureVideo.setName(Constants.START_RECORDING_NAME);
		
		ActionType activateAlarm = new ActionType();
		activateAlarm.setCommand(Constants.ACTIVATE_ALARM);
		activateAlarm.setDetails(Constants.ACTIVATE_ALARM);
		activateAlarm.setName(Constants.ACTIVATE_ALARM_NAME);

		ActionType deactivateAlarm = new ActionType();
		deactivateAlarm.setCommand(Constants.DEACTIVATE_ALARM);
		deactivateAlarm.setDetails(Constants.DEACTIVATE_ALARM);
		deactivateAlarm.setName(Constants.DEACTIVATE_ALARM_NAME);
		
		ActionType switchOn = new ActionType();
		switchOn.setCommand(Constants.SWITCH_ON);
		switchOn.setDetails(Constants.SWITCH_ON);
		switchOn.setName(Constants.SWITCH_ON_NAME);

		ActionType switchOff = new ActionType();
		switchOff.setCommand(Constants.SWITCH_OFF);
		switchOff.setDetails(Constants.SWITCH_OFF);
		switchOff.setName(Constants.SWITCH_OFF_NAME);
		
		ActionType doorOpen = new ActionType();
		doorOpen.setCommand(Constants.DOOR_OPEN);
		doorOpen.setDetails(Constants.DOOR_OPEN);
		doorOpen.setName(Constants.DOOR_CLOSE_NAME);
		
		ActionType doorClose = new ActionType();
		doorClose.setCommand(Constants.DOOR_CLOSE);
		doorClose.setDetails(Constants.DOOR_CLOSE);
		doorClose.setName(Constants.DOOR_OPEN_NAME);
		
		ActionType dimmerChange = new ActionType();
		dimmerChange.setCommand(Constants.DIMMER_CHANGE);
		dimmerChange.setDetails(Constants.DIMMER_CHANGE);
		dimmerChange.setName(Constants.DIMMER_CHANGE_NAME);
		
		ActionType getTemperature = new ActionType();
		getTemperature.setCommand(Constants.GET_TEMPERATURE);
		getTemperature.setDetails(Constants.GET_TEMPERATURE);
		getTemperature.setName(Constants.GET_TEMPERATURE_NAME);
		
		ActionType acControl = new ActionType();
		acControl.setCommand(Constants.AC_CONTROL);
		acControl.setDetails(Constants.AC_CONTROL);
		acControl.setName(Constants.AC_CONTROL_NAME);
		
		ActionType acOn = new ActionType();
		acOn.setCommand(Constants.AC_ON);
		acOn.setDetails(Constants.AC_ON);
		acOn.setName(Constants.AC_ON_NAME);
		
		ActionType acOnOff = new ActionType();
		acOnOff.setCommand(Constants.AC_OFF);
		acOnOff.setDetails(Constants.AC_OFF);
		acOnOff.setName(Constants.AC_OFF_NAME);
		
		ActionType sendMail = new ActionType();
		sendMail.setCommand(Constants.SEND_EMAIL);
		sendMail.setDetails(Constants.SEND_EMAIL);
		sendMail.setName(Constants.SEND_EMAIL);
		
		ActionType sendSms = new ActionType();
		sendSms.setCommand(Constants.SEND_SMS);
		sendSms.setDetails(Constants.SEND_SMS);
		sendSms.setName(Constants.SEND_SMS);
		
		ActionType executeScenario = new ActionType();
		executeScenario.setCommand(Constants.EXECUTE_SCENARIO);
		executeScenario.setDetails(Constants.EXECUTE_SCENARIO);
		executeScenario.setName(Constants.EXECUTE_SCENARIO);
		
		ActionType armDevice = new ActionType();
		armDevice.setCommand(Constants.ARM_DEVICE);
		armDevice.setDetails(Constants.ARM_DEVICE);
		armDevice.setName(Constants.ARM_DEVICE);
		
		ActionType disarmDevice = new ActionType();
		disarmDevice.setCommand(Constants.DISARM_DEVICE);
		disarmDevice.setDetails(Constants.DISARM_DEVICE);
		disarmDevice.setName(Constants.DISARM_DEVICE);
		
		ActionType curtainopen = new ActionType();
		curtainopen.setCommand(Constants.CURTAIN_OPEN);
		curtainopen.setDetails(Constants.CURTAIN_OPEN);
		curtainopen.setName(Constants.CURTAIN_OPEN_NAME);
		
		ActionType curtainclose = new ActionType();
		curtainclose.setCommand(Constants.CURTAIN_CLOSE);
		curtainclose.setDetails(Constants.CURTAIN_CLOSE);
		curtainclose.setName(Constants.CURTAIN_CLOSE_NAME);
		
		ActionTypeManager actionTypeManager = new ActionTypeManager();
		actionTypeManager.save(switchOff);
		actionTypeManager.save(switchOn);
		actionTypeManager.save(doorOpen);
		actionTypeManager.save(doorClose);
		actionTypeManager.save(deactivateAlarm);
		actionTypeManager.save(activateAlarm);
		actionTypeManager.save(captureVideo);
		actionTypeManager.save(captureImage);
		actionTypeManager.save(dimmerChange);
		actionTypeManager.save(getTemperature);
		actionTypeManager.save(acControl);
		actionTypeManager.save(acOn);
		actionTypeManager.save(acOnOff);
		actionTypeManager.save(sendMail);
		actionTypeManager.save(sendSms);
		actionTypeManager.save(executeScenario);
		actionTypeManager.save(armDevice);
		actionTypeManager.save(disarmDevice);
		actionTypeManager.save(curtainopen);
		actionTypeManager.save(curtainclose);
		
		
//		Saving device types
		DeviceType ipCamera = new DeviceType();
		ipCamera.setName(Constants.IP_CAMERA);
		ipCamera.setDetails(Constants.IP_CAMERA);
		ipCamera.setIconFile("/imonitor/resources/images/device/ipcamera.png");
		ipCamera.setCategory(Constants.CAMERA_CONTROL);
		List<AlertType> camAlerts = new ArrayList<AlertType>();
		camAlerts.add(deviceUpAlert);
		camAlerts.add(deviceDownAlert);
		camAlerts.add(motionDetectedAlert);
		camAlerts.add(noMotionDetectedAlert);
		ipCamera.setAlertTypes(camAlerts);
		List<ActionType> camActions = new ArrayList<ActionType>();
		camActions.add(captureVideo);
		camActions.add(captureImage);
		ipCamera.setActionTypes(camActions);
		
		DeviceType switchs = new DeviceType();
		switchs.setName(Constants.Z_WAVE_SWITCH);
		switchs.setDetails(Constants.Z_WAVE_SWITCH);
		switchs.setIconFile("/imonitor/resources/images/device/zwaveswitch.png");
		switchs.setCategory(Constants.ON_OFF_CONTROL);
		List<AlertType> switchAlerts = new ArrayList<AlertType>();
		switchAlerts.add(deviceUpAlert);
		switchAlerts.add(deviceDownAlert);
		switchs.setAlertTypes(switchAlerts);
		List<ActionType> switchActions = new ArrayList<ActionType>();
		switchActions.add(switchOff);
		switchActions.add(switchOn);
		switchs.setActionTypes(switchActions);
		
		
		DeviceType motor = new DeviceType();
		motor.setName(Constants.Z_WAVE_MOTOR_CONTROLLER);
		motor.setDetails(Constants.Z_WAVE_MOTOR_CONTROLLER);
		motor.setIconFile("/imonitor/resources/images/device/curtain.png");
		motor.setCategory(Constants.ON_OFF_CONTROL);
		List<AlertType> Motoralert = new ArrayList<AlertType>();
		Motoralert.add(deviceUpAlert);
		Motoralert.add(deviceDownAlert);
		
		motor.setAlertTypes(Motoralert);
		List<ActionType> motorActions = new ArrayList<ActionType>();
		motorActions.add(curtainopen);
		motorActions.add(curtainclose);
		motor.setActionTypes(motorActions);
	
		 
		
		DeviceType dimmer = new DeviceType();
		dimmer.setName(Constants.Z_WAVE_DIMMER);
		dimmer.setDetails(Constants.Z_WAVE_DIMMER);
		dimmer.setIconFile("/imonitor/resources/images/device/dimmer.png");
		dimmer.setCategory(Constants.DIMMER_CONTROL);
		List<AlertType> dimmerAlerts = new ArrayList<AlertType>();
		dimmerAlerts.add(deviceUpAlert);
		dimmerAlerts.add(deviceDownAlert);
		dimmer.setAlertTypes(dimmerAlerts);
		List<ActionType> dimmerActions = new ArrayList<ActionType>();
		dimmerActions.add(dimmerChange);
		dimmer.setActionTypes(dimmerActions);
		
		DeviceType doorLock = new DeviceType();
		doorLock.setName(Constants.Z_WAVE_DOOR_LOCK);
		doorLock.setDetails(Constants.Z_WAVE_DOOR_LOCK);
		doorLock.setIconFile("/imonitor/resources/images/device/door-lock.png");
		doorLock.setCategory(Constants.ON_OFF_CONTROL);
		List<AlertType> doorlockAlerts = new ArrayList<AlertType>();
		doorlockAlerts.add(deviceUpAlert);
		doorlockAlerts.add(deviceDownAlert);
		doorlockAlerts.add(doorLockOpenAlert);
		doorlockAlerts.add(doorLockCloseAlert);
		doorlockAlerts.add(deviceDownAlert);
		doorlockAlerts.add(batteryStatusAlert);
		doorLock.setAlertTypes(doorlockAlerts);
		List<ActionType>doorlockActions = new ArrayList<ActionType>();
		doorlockActions.add(doorOpen);
		doorlockActions.add(doorClose);
		doorLock.setActionTypes(doorlockActions);
		
		DeviceType doorLockNumPad = new DeviceType();
		doorLockNumPad.setName(Constants.Z_WAVE_DOOR_LOCK_NUM_PAD);
		doorLockNumPad.setDetails(Constants.Z_WAVE_DOOR_LOCK_NUM_PAD);
		doorLockNumPad.setIconFile("/imonitor/resources/images/device/dorlocknumpad.png");
		doorLockNumPad.setCategory(Constants.ON_OFF_CONTROL);
		List<AlertType> doorLockNumPadAlerts = new ArrayList<AlertType>();
		doorLockNumPadAlerts.add(deviceUpAlert);
		doorLockNumPadAlerts.add(deviceDownAlert);
		doorLockNumPadAlerts.add(batteryStatusAlert);
		doorLockNumPad.setAlertTypes(doorLockNumPadAlerts);
		
		DeviceType doorSensor = new DeviceType();
		doorSensor.setName(Constants.Z_WAVE_DOOR_SENSOR);
		doorSensor.setDetails(Constants.Z_WAVE_DOOR_SENSOR);
		doorSensor.setIconFile("/imonitor/resources/images/device/doorsensor.png");
		doorSensor.setCategory(Constants.ON_OFF_CONTROL);
		List<AlertType> doorSensorAlerts = new ArrayList<AlertType>();
		doorSensorAlerts.add(deviceUpAlert);
		doorSensorAlerts.add(deviceDownAlert);
		doorSensorAlerts.add(doorOpenAlert);
		doorSensorAlerts.add(doorCloseAlert);
		doorSensorAlerts.add(batteryStatusAlert);
		doorSensor.setAlertTypes(doorSensorAlerts);
		
		DeviceType minimote = new DeviceType();
		minimote.setName(Constants.Z_WAVE_MINIMOTE);
		minimote.setDetails(Constants.Z_WAVE_MINIMOTE);
		minimote.setIconFile("/imonitor/resources/images/device/panicbutton.png");
		minimote.setCategory(Constants.ON_OFF_CONTROL);
		List<AlertType> minimoteAlerts = new ArrayList<AlertType>();
		minimoteAlerts.add(deviceUpAlert);
		minimoteAlerts.add(deviceDownAlert);
//		minimoteAlerts.add(batteryStatusAlert); // Later it may added !!!
		minimoteAlerts.add(panicAlert);
		minimote.setAlertTypes(minimoteAlerts);
		
//bhavya start
		DeviceType lcdRemote = new DeviceType();
		lcdRemote.setName(Constants.Z_WAVE_LCD_REMOTE);
		lcdRemote.setDetails(Constants.Z_WAVE_LCD_REMOTE);
		lcdRemote.setIconFile("/imonitor/resources/images/device/lcdremote.png");
		lcdRemote.setCategory(Constants.ON_OFF_CONTROL);
		List<AlertType> lcdAlerts = new ArrayList<AlertType>();
		lcdAlerts.add(deviceUpAlert);
		lcdAlerts.add(deviceDownAlert);
		lcdAlerts.add(panicAlert);
		lcdRemote.setAlertTypes(lcdAlerts);
		

//bhavya end		
		DeviceType pirSensor = new DeviceType();
		pirSensor.setName(Constants.Z_WAVE_PIR_SENSOR);
		pirSensor.setDetails(Constants.Z_WAVE_PIR_SENSOR);
		pirSensor.setIconFile("/imonitor/resources/images/device/pirsensor.png");
		pirSensor.setCategory(Constants.ON_OFF_CONTROL);
		List<AlertType> pirSensorAlerts = new ArrayList<AlertType>();
		pirSensorAlerts.add(deviceUpAlert);
		pirSensorAlerts.add(deviceDownAlert);
		pirSensorAlerts.add(batteryStatusAlert);
		pirSensorAlerts.add(motionDetectedAlert);
		pirSensorAlerts.add(noMotionDetectedAlert);
		pirSensor.setAlertTypes(pirSensorAlerts);
		List<ActionType> getTempActions = new ArrayList<ActionType>();
		getTempActions.add(getTemperature);
		pirSensor.setActionTypes(getTempActions);
		
		DeviceType repeater = new DeviceType();
		repeater.setName(Constants.Z_WAVE_REPEATER);
		repeater.setDetails(Constants.Z_WAVE_REPEATER);
		repeater.setIconFile("/imonitor/resources/images/device/repeater.png");
		repeater.setCategory(Constants.ON_OFF_CONTROL);
		List<AlertType> repeaterAlerts = new ArrayList<AlertType>();
		repeaterAlerts.add(deviceUpAlert);
		repeaterAlerts.add(deviceDownAlert);
		repeater.setAlertTypes(repeaterAlerts);
		
		DeviceType multiSensor = new DeviceType();
		multiSensor.setName(Constants.Z_WAVE_MULTI_SENSOR);
		multiSensor.setDetails(Constants.Z_WAVE_MULTI_SENSOR);
		multiSensor.setIconFile("/imonitor/resources/images/device/tempsensor.png");
		multiSensor.setCategory(Constants.READER_CONTROL);
		List<AlertType> multiSensorAlerts = new ArrayList<AlertType>();
		multiSensorAlerts.add(deviceUpAlert);
		multiSensorAlerts.add(deviceDownAlert);
		multiSensorAlerts.add(motionDetectedAlert);
		multiSensorAlerts.add(noMotionDetectedAlert);
		multiSensorAlerts.add(batteryStatusAlert);
		multiSensor.setAlertTypes(multiSensorAlerts);
		List<ActionType> multiSActionTypes = new ArrayList<ActionType>();
		multiSActionTypes.add(captureImage);
		multiSensor.setActionTypes(multiSActionTypes);
		List<ActionType> getMultiTempActions = new ArrayList<ActionType>();
		getMultiTempActions.add(getTemperature);
		multiSensor.setActionTypes(getMultiTempActions);
		
		DeviceType siren = new DeviceType();
		siren.setName(Constants.Z_WAVE_SIREN);
		siren.setDetails(Constants.Z_WAVE_SIREN);
		siren.setIconFile("/imonitor/resources/images/device/siren-1.png");
		List<AlertType> sirenAlerts = new ArrayList<AlertType>();
		sirenAlerts.add(deviceUpAlert);
		sirenAlerts.add(deviceDownAlert);
		siren.setAlertTypes(sirenAlerts);
		List<ActionType>sirenActions = new ArrayList<ActionType>();
		sirenActions.add(activateAlarm);
		sirenActions.add(deactivateAlarm);
		siren.setActionTypes(sirenActions);
		
//		Z_WAVE_AC_EXTENDER
		DeviceType acType = new DeviceType();
		acType.setName(Constants.Z_WAVE_AC_EXTENDER);
		acType.setDetails(Constants.Z_WAVE_AC_EXTENDER);
		acType.setIconFile("/imonitor/resources/images/device/airconditioner.png");
		List<AlertType> acAlerts = new ArrayList<AlertType>();
		acAlerts.add(deviceUpAlert);
		acAlerts.add(deviceDownAlert);
		acType.setAlertTypes(acAlerts);
		List<ActionType> acActions = new ArrayList<ActionType>();
		acActions.add(acControl);
		acActions.add(acOn);
		acActions.add(acOnOff);
		acType.setActionTypes(acActions);
		
		DeviceTypeManager deviceTypeManager = new DeviceTypeManager();
		deviceTypeManager.saveDeviceType(ipCamera);
		deviceTypeManager.saveDeviceType(switchs);
		deviceTypeManager.saveDeviceType(dimmer);
		deviceTypeManager.saveDeviceType(doorLock);
		deviceTypeManager.saveDeviceType(doorLockNumPad);
		deviceTypeManager.saveDeviceType(doorSensor);
		deviceTypeManager.saveDeviceType(pirSensor);
		deviceTypeManager.saveDeviceType(minimote);
		deviceTypeManager.saveDeviceType(repeater);
		deviceTypeManager.saveDeviceType(multiSensor);
		deviceTypeManager.saveDeviceType(siren);
		deviceTypeManager.saveDeviceType(acType);
	}
}