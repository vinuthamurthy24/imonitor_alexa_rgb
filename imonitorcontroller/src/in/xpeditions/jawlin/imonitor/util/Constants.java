/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.util;

import java.util.Collection;
import java.util.Locale;

/**
 * @author Coladi
 *
 */
public class Constants {

	public static final String REGISTERED = "Registered";
	public static final String READY_TO_REGISTER = "Ready To Register";
	public static final String UN_REGISTERED = "Un Registered";
	public static final String DEVICE_DISCOVERY_MODE = "Device Discovery Mode";
	public static final String ONLINE = "Online";
	public static final String OFFLINE = "Offline";
	public static final String SUSPEND = "Suspend";
	public static final String LOGIN_FAIL_MESSAGE = "Invalid CustomerId or UserId or Password";
	public static final String GATEWAY_RECOVERY = "GateWay Recovery";

//	Device types
	public static final String IP_CAMERA = "IP_CAMERA";
	public static final String Z_WAVE_SWITCH = "Z_WAVE_SWITCH";
	public static final String Z_WAVE_DIMMER = "Z_WAVE_DIMMER";
	public static final String Z_WAVE_DOOR_LOCK = "Z_WAVE_DOOR_LOCK";
	public static final String Z_WAVE_DOOR_LOCK_NUM_PAD = "Z_WAVE_DOOR_LOCK_NUM_PAD";
	public static final String Z_WAVE_DOOR_SENSOR = "Z_WAVE_DOOR_SENSOR";
	public static final String Z_WAVE_PIR_SENSOR = "Z_WAVE_PIR_SENSOR";
	public static final String Z_WAVE_LUX_SENSOR = "Z_WAVE_LUX_SENSOR";
	public static final String Z_WAVE_PM_SENSOR="Z_WAVE_PM_SENSOR";
	public static final String Z_WAVE_REPEATER = "Z_WAVE_REPEATER";
	public static final String Z_WAVE_TEMP_SENSOR = "Z_WAVE_TEMP_SENSOR";
	public static final String Z_WAVE_SIREN = "Z_WAVE_SIREN";
	public static final String Z_WAVE_MOTOR_CONTROLLER = "Z_WAVE_MOTOR_CONTROLLER";
	public static final String Z_WAVE_AC_EXTENDER = "Z_WAVE_AC_EXTENDER";
	public static final String Z_WAVE_MINIMOTE = "Z_WAVE_MINIMOTE";
	public static final String Z_WAVE_SHOCK_DETECTOR = "Z_WAVE_SHOCK_DETECTOR";
	public static final String Z_WAVE_SMOKE_SENSOR = "Z_WAVE_SMOKE_SENSOR";
	public static final String Z_WAVE_AV_BLASTER = "Z_WAVE_AV_BLASTER";
//bhavya start
	public static final String Z_WAVE_LCD_REMOTE = "Z_WAVE_LCD_REMOTE";
	public static final String Z_WAVE_HEALTH_MONITOR = "Z_WAVE_HEALTH_MONITOR";
	
//bhavya end	
	public static final String ZWAVE_CLAMP_SWITCH = "ZWAVE_CLAMP_SWITCH";
	public static final String INVALID_IMVG_MAC = "INVALID_IMVG_MAC";
	public static final String ALREADY_REGISTERED = "ALREADY_REGISTERED";
	public static final String CUSTOMER_NOT_CONFIGURED = "CUSTOMER_NOT_CONFIGURED";
	public static final String SERVER_TEMPORARILY_BUSY = "SERVER_TEMPORARILY_BUSY";
	
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	public static final String MESSAGE_DELIMITER_1 = "|";
	public static final String MESSAGE_DELIMITER_2 = "^";
	public static final String REGEX_ESCAPE = "\\";
	
	public static final String IMVG_ID = "IMVG_ID";
	public static final String FILE_LOCATION = "FILE_LOCATION";
	
	public static final String HIBERNATE_CONFIG_FILE = "hibernate.config.file";
	public static final String DISCOVERY_DURATION = "DISCOVERY_DURATION";
	public static final String DISCOVERY_MODE = "DISCOVERY_MODE";
	public static final String DISCOVER_DEVICES = "DISCOVER_DEVICES";
	public static final String CMD_ID = "CMD_ID";
	public static final String DEVICE_ID = "DEVICE_ID";
	public static final String DEVICE_TYPE_NAME = "DEVICE_TYPE_NAME";
    public static final String LUX_VALUE = "LUX_VALUE";
	public static final String DISCOVER_DEVICES_ACK = "DISCOVER_DEVICES_ACK";
	public static final String TRANSACTION_ID = "TRANSACTION_ID";
	public static final String DEVICE_TYPE = "DEVICE_TYPE";
	public static final String DEVICE_DISCOVERED_SUCCESS = "DEVICE_DISCOVERED_SUCCESS";
	public static final String DEVICE_DISCOVERED_FAILED = "DEVICE_DISCOVERY_FAILED";
	public static final String FAILURE_REASON = "FAILURE_REASON";
	public static final String UNKNOWN_DEVICE = "UNKNOWN_DEVICE";
	public static final String DEVICE_ALREADY_EXISTS = "DEVICE_ALREADY_EXISTS";
	public static final String BED_ROOM_1 = "Bed Room 1";
	public static final String LIVING_ROOM = "Living Room";
	public static final String KITCHEN = "Kitchen";
	public static final String OFFICE_ROOM = "Office Room";
	public static final String BED_ROOM_2 = "Bed Room 2";
	public static final String BED_ROOM_3 = "Bed Room 3";
	public static final String MAIN_USER = "mainuser";
	public static final String NORMAL_USER = "normaluser";
	public static final String ADMIN_USER = "adminuser";
	public static final String LOG_IN ="systemalert.logged.in";
	public static final String LOG_OUT ="systemalert.logged.out";
	public static final String DEVICE_ON ="Device On";
	public static final String DEVICE_OFF ="Device Off";
	public static final String STREAMING_STARTED ="Streaming Started";
	public static final String STREAMING_STOPPED  ="Streaming Stopped";
	public static final String DIMMERCHANGE ="Dimmer Change";
	public static final String AC_CHANGE = "A/C Change";
	public static final String IMVG_ON = "systemalert.imvg.up";
	public static final String IMVG_OFF = "systemalert.imvg.down";
	public static final String PW_CHANGE = "systemalert.password.changed";
 	public static final String MEMORY_QUOTA_FULL = "Memory Quota Full";
	public static final String MEMORY_QUOTA_NOTFULL = "Memory Quota 90%";
	public static final String ADD_NEW_GATEWAY = "New Gateway added to user account";
	public static final String DELETE_GATEWAY = "Gateway deleted from the user account";
	public static final String ADD_USER = "systemalert.new.user.added.to.the.account";
	public static final String DELETE_USER = "systemalert.user.deleted.from.the.account";
	public static final String SUSPEND_USER = "systemalert.user.account.suspended";
	public static final String MAIL_SEND = "yes";
	public static final String SMS_SEND = "yes";
	public static final String MAIL_SEND_NO = "no";
	public static final String SMS_SEND_NO = "no";
	
	public static final String ON_OFF_CONTROL = "ON_OFF_CONTROL";
	public static final String DIMMER_CONTROL = "DIMMER_CONTROL";
	public static final String READER_CONTROL = "READER_CONTROL";
	public static final String DEVICE_CONTROL = "DEVICE_CONTROL";
	public static final String CAMERA_CONTROL = "CAMERA_CONTROL";
	public static final String ZW_DEVICE_CONTROL = "ZW_DEVICE_CONTROL";
	public static final String ZW_TIMEOUT = "ZW_TIMEOUT";
	public static final String SIGNATURE = "SIGNATURE";
	public static final String COMMAND_PARAM = "COMMAND_PARAM";
	public static final String IPC_GET_LIVE_STREAM = "IPC_GET_LIVE_STREAM";
	public static final String RTSP_CLIENT_IP_PORT = "RTSP_CLIENT_IP_PORT";
	public static final String IPC_GET_STORED_STREAM = "IPC_GET_STORED_STREAM";
	public static final String TIME_OFFSET = "TIME_OFFSET";
	public static final String STREAM_DURATION = "STREAM_DURATION";
	public static final String STREAM_FTP_IP_PORT = "STREAM_FTP_IP_PORT";
	public static final String STREAM_FTP_PATH_FILE_NAME = "STREAM_FTP_PATH_FILE_NAME";
	public static final String IPC_GET_CAPTURE_IMAGE = "IPC_GET_CAPTURE_IMAGE";
	public static final String IMAGE_FTP_IP_PORT = "IMAGE_FTP_IP_PORT";
	public static final String IMAGE_FTP_PATH_FILE_NAME = "IMAGE_FTP_PATH_FILE_NAME";
	public static final String REMOVE_DEVICE = "REMOVE_DEVICE";
	//vibhu start
	public static final String IMVG_DEFAULTSET = "IMVG_DEFAULTSET";
	public static final String NEIGHBOUR_UPDATE = "NEIGHBOUR_UPDATE";
	public static final String CONFIGURE_LCD_REMOTE = "CONFIGURE_LCD_REMOTE";
	//vibhu end
	public static final String CONFIG_PARAM = "CONFIG_PARAM";
	public static final String START = "START";
	public static final String END = "END";
	public static final String IMVG_RESET= "IMVG_RESET";
	public static final String IMONITOR_COTROL_STRING = "control";
	public static final String IMONITOR_COMMAND_ID = "id";
	public static final String IMONITOR_COMMAND_CLASS = "class";
	public static final String IMONITOR_COMMAND_METHOD = "method";

	public static final String IPC_CAMARA_UPDATION = "IPC_CAMARA_UPDATION";
	public static final String IPC_JPEG_RESOLUTION = "IPC_JPEG_RESOLUTION";
	public static final String IPC_JPEG_QUALITY = "IPC_JPEG_QUALITY";
	public static final String IPC_VIDEO_COLOR_BALANCE = "IPC_VIDEO_COLOR_BALANCE";
	public static final String IPC_VIDEO_BRIGHTNESS = "IPC_VIDEO_BRIGHTNESS";
	public static final String IPC_VIDEO_SHARPNESS = "IPC_VIDEO_SHARPNESS";
	public static final String IPC_VIDEO_HUE = "IPC_VIDEO_HUE";
	public static final String IPC_VIDEO_SATURATION = "IPC_VIDEO_SATURATION";
	public static final String IPC_VIDEO_CONTRAST = "IPC_VIDEO_CONTRAST";
	public static final String IPC_VIDEO_AC_FREQUENCY = "IPC_VIDEO_AC_FREQUENCY";
	public static final String IPC_MPEG4_RESOLUTION = "IPC_MPEG4_RESOLUTION";
	public static final String IPC_MPEG4_QUALITY_CONTROL = "IPC_MPEG4_QUALITY_CONTROL";
	public static final String IPC_MPEG4_QUALITY_LEVEL = "IPC_MPEG4_QUALITY_LEVEL";
	public static final String IPC_MPEG4_BITRATE = "IPC_MPEG4_BITRATE";
	public static final String IPC_MPEG4_FRAMERATE = "IPC_MPEG4_FRAMERATE";
	public static final String IPC_MPEG4_BANDWIDTH = "IPC_MPEG4_BANDWIDTH";
	public static final String IPC_PANTILT_CONTROL = "IPC_PANTILT_CONTROL";
	public static final String IPC_PANTILT_VS_MOTION_ARBITRATION = "IPC_PANTILT_VS_MOTION_ARBITRATION";
	public static final String IPC_PANTILT_PATROL_STYLE = "IPC_PANTILT_PATROL_STYLE";
	public static final String IPC_PANTILT_PANSPEED = "IPC_PANTILT_PANSPEED";
	public static final String IPC_PANTILT_TILTSPEED = "IPC_PANTILT_TILTSPEED";
	public static final String IPC_CONTROL_PANTILT = "IPC_CONTROL_PANTILT";
	public static final String IPC_NETWORK_MODE = "IPC_NETWORK_MODE";
	public static final String IPC_USER_ADMIN_USERNAME = "IPC_USER_ADMIN_USERNAME";
	public static final String IPC_USER_ADMIN_PASSWORD = "IPC_USER_ADMIN_PASSWORD";
	public static final String IPC_SYSTEM_NIGHTVISION_CONTROL = "IPC_SYSTEM_NIGHTVISION_CONTROL";
	public static final String IPC_SYSTEM_PRIVACY_CONTROL = "IPC_SYSTEM_PRIVACY_CONTROL";
	public static final String IPC_SYSTEM_PIR_CONTROL = "IPC_SYSTEM_PIR_CONTROL";
	public static final String IPC_MOTIONDETECTION_WINDOW = "IPC_MOTIONDETECTION_WINDOW";
	public static final String IPC_CONTROL_NIGHTVISION = "IPC_CONTROL_NIGHTVISION";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	public static final String TIMOUT_DURATION = "TIMOUT_DURATION";
	public static final String AC_WAIT_DURATION = "AC_WAIT_DURATION";
	public static final String IMVG_UNREGISTER = "IMVG_UNREGISTER";
	public static final String REMOVE_FAILNODE = "REMOVE_FAILNODE";
	public static final String OBJ_APPENDER = "_OBJ_APPENDER";
	public static final String WAITING_FOR_LIVE_STREAM_RESPONCE = "WAITING_FOR_LIVE_STREAM_RESPONCE";
	public static final String Z_WAVE_MULTI_SENSOR = "Z_WAVE_MULTI_SENSOR";
	public static final String REF_TRANSACTION_ID = "REF_TRANSACTION_ID";
	public static final String ZW_GET_TEMPERATURE_VALUE = "ZW_GET_TEMPERATURE_VALUE";
	public static final String TEMPERATURE_VALUE = "TEMPERATURE_VALUE";
	public static final String FIRMWARE_UPGRADE = "FIRMWARE_UPGRADE";
	public static final String FTP_IP_PORT = "FTP_IP_PORT";
	public static final String FTP_PATH_FILE_NAME = "FTP_PATH_FILE_NAME";
//vibhuti start
	public static final String FILE_NAME = "FILE_NAME";
	public static final String FILE_PATH = "FILE_PATH";
//vibhuti end	
	public static final String FTP_USERNAME = "FTP_USERNAME";
	public static final String FTP_PASSWORD = "FTP_PASSWORD";
	public static final String IMVG_UP = "IMVG_UP";
	public static final String IMVG_UP_NAME = "Gateway Online";
	public static final String MOTION_DETECTED = "MOTION_DETECTED";
	public static final String MOTION_DETECTED_NAME = "Motion Detected";
	public static final String NOMOTION_DETECTED = "NOMOTION_DETECTED";
	public static final String NOMOTION_DETECTED_NAME = "No Motion Detected";
	public static final String DEVICE_DOWN = "DEVICE_DOWN";
	public static final String DEVICE_STATE_CHANGED = "DEVICE_STATE_CHANGED";
	public static final String DEVICE_STATE_CHANGED_NAME = "Device State Changed";
	public static final String DEVICE_DOWN_NAME = "Device Down";
	public static final String MONITOR_DEVICE_HEALTH_SUCCESS = "MONITOR_DEVICE_HEALTH_SUCCESS";
	public static final String MONITOR_DEVICE_HEALTH_FAIL = "MONITOR_DEVICE_HEALTH_FAIL";
	public static final String DEVICE_UP = "DEVICE_UP";
	public static final String DEVICE_UP_NAME = "Device Up";
	public static final String MONITOR_DEVICE_HEALTH_SUCCESS_NAME = "Monitor Device Health Success";
	public static final String PANIC_SITUATION = "PANIC_SITUATION";
	public static final String BATTERY_STATUS = "BATTERY_STATUS";
	public static final String BATTERY_STATUS_NAME = "Battery Status";
	public static final String DOOR_LOCK_CLOSE = "DOOR_LOCK_CLOSE";
	public static final String DOOR_LOCK_CLOSE_NAME = "Door Lock Close";
	public static final String DOOR_LOCK_OPEN = "DOOR_LOCK_OPEN";
	public static final String DOOR_LOCK_OPEN_NAME = "Door Lock Open";
	public static final String DOOR_OPEN = "DOOR_OPEN";
	public static final String DOOR_OPEN_NAME = "Door Open";
	public static final String DOOR_CLOSE = "DOOR_CLOSE";
	public static final String DOOR_CLOSE_NAME = "Door Close";
	public static final String STREAM_FTP_SUCCESS = "STREAM_FTP_SUCCESS";
	public static final String STREAM_FTP_SUCCESS_NAME = "Stream Upload Success";
	public static final String STREAM_FTP_FAILED = "STREAM_FTP_FAILED";
	public static final String STREAM_FTP_FAILED_NAME = "Stream Upload Failure";
	public static final String IMAGE_FTP_SUCCESS = "IMAGE_FTP_SUCCESS";
	public static final String IMAGE_FTP_SUCCESS_NAME = "Image Upload Success";
	public static final String IMAGE_FTP_FAILED = "IMAGE_FTP_FAILED";
	public static final String IMAGE_FTP_FAILED_NAME = "Image Upload Failure";
	public static final String ALERT_EVENT = "ALERT_EVENT";
	public static final String POWER_CONSUMED = "POWER_CONSUMED";
	public static final String BATTERY_LEVEL = "BATTERY_LEVEL";
	public static final String DEVICE_ALERT_ACK = "DEVICE_ALERT_ACK";
	public static final String CAPTURE_IMAGE = "CAPTURE_IMAGE";
	public static final String CAPTURE_IMAGE_NAME = "Capture Image";
	public static final String START_RECORDING = "START_RECORDING";
	public static final String START_RECORDING_NAME = "Start Recording";
	public static final String ACTIVATE_ALARM = "ACTIVATE_ALARM";
	public static final String ACTIVATE_ALARM_NAME = "Activate Alarm";
	public static final String DEACTIVATE_ALARM = "DEACTIVATE_ALARM";
	public static final String DEACTIVATE_ALARM_NAME = "Deactivate Alarm";
	public static final String SWITCH_ON = "SWITCH_ON";
	public static final String SWITCH_ON_NAME = "Switch On";
	public static final String SWITCH_OFF = "SWITCH_OFF";
	public static final String SWITCH_OFF_NAME = "Switch Off";
	public static final String DIMMER_CHANGE = "DIMMER_CHANGE";
	public static final String DIMMER_CHANGE_NAME = "Change Dimmer Level";
	public static final String GET_TEMPERATURE = "GET_TEMPERATURE";
	public static final String GET_TEMPERATURE_NAME = "Get Temperature";
	public static final String AC_ON = "AC_ON";
	public static final String AC_ON_NAME = "AC On";
	public static final String AC_CONTROL = "AC_CONTROL";
	public static final String AC_CONTROL_NAME = "AC Control";
	public static final String AC_OFF = "AC_OFF";
	public static final String AC_OFF_NAME = "AC Off";
	public static final String SEND_EMAIL = "E-Mail";
	public static final String SEND_SMS = "SMS";
	public static final String IMONITOR_NOTIFICATION_STRING = "notification";
	public static final String IMONITOR_ACTION_STRING = "action";
	public static final String EXPRESSION_OPERATOR_PATTERN = "[&]|[|]";
	public static final String EXPRESSION_OPERATOR_PATTERN_NOT = "[^&|^|]";
	// ************************************************************** sumit begin ***********************************************************
	public static final String ALERT_DEVICE = "ALERT_DEVICE";
	public static final String NO_DEVICE = "NO_DEVICE";
	public static final String DEVICE_NAME = "DEVICE_NAME";
	public static final String MID_TIMEOUT_DURATION = "MID_TIMEOUT_DURATION";
	public static final String STAY = "STAY";
	public static final String DELAY_HOME = "DELAY_HOME";
	public static final String DELAY_STAY = "DELAY_STAY";
	public static final String DELAY_AWAY = "DELAY_AWAY";
	public static final String UPDATE = "UPDATE";
	public static final String ALL_SET = "ALL_SET";
	public static final String ALL_CLEAR = "ALL_CLEAR";
	public static final String NO_CHANGE = "NO_CHANGE";
	public static final String SYNC = "SYNC";
	public static final String DEV = "DEV";
	public static final String MODE = "MODE";
	public static final String SET_MODE = "SET_MODE";
	public static final String EDIT_MODE = "EDIT_MODE";
	public static final String EDIT_MODE_SUCCESS = "EDIT_MODE_SUCCESS";
	public static final String EDIT_MODE_FAILURE = "EDIT_MODE_FAILURE";
	public static final String DEFAULT_DEVICE_MODE = "000";
	public static final String AWAY_SUCCESS = "AWAY_SUCCESS";
	public static final String HOME_SUCCESS = "HOME_SUCCESS";
	public static final String STAY_SUCCESS = "STAY_SUCCESS";
	public static final String DEFAULT_DEVICE_LISTING = "1";
	public static final String SHOW_DEVICE = "SHOW_DEVICE";
	public static final String NO_MOTION_DELAY = "NO_MOTION_DELAY";
	public static final String ATTACH_UPLOADED_FILE_INTERVAL = "ATTACH_UPLOADED_FILE_INTERVAL";	
	public static final String REF_IMVG_TIME_STAMP = "REF_IMVG_TIME_STAMP";
	public static final String UPLOADED_LOC_IMAGES_DIR = "UPLOADED_LOC_IMAGES_DIR";
	//Camera Orientaion Feature: start
	public static final String DEFAULT_CAMERA_ORIENTATION = "00";
	public static final String DEFAULT_H264_ORIENTATION = "4";
	public static final String RC80Series = "RC80";
	public static final String H264Series = "H264";
	public static final String IPC_VIDEO_ROTATION = "IPC_VIDEO_ROTATION";
	public static final String H264_VIDEO_ROTATION = "H264_VIDEO_ROTATION";
	//Camera Orientation Feature: end
	public static final String DEFAULT_ROOM = "Default Room";
	public static final String SCHEDULE_ACTIVATION = "SCHEDULE_ACTIVATION";
	public static final String ACTIVATION = "ACTIVATION";
	//Shock and Smoke Detecteor: start
	public static final String SHOCK_DETECTED = "SHOCK_DETECTED";
	public static final String SHOCK_STOPPED = "SHOCK_STOPPED";
	public static final String SMOKE_SENSED = "SMOKE_SENSED";
	public static final String SMOKE_CLEARED = "SMOKE_CLEARED";
	//Shock and Smoke Detecteor: end
	//bhavya start HMD device
	public static final String HMD_WARNING = "HMD_WARNING";
	public static final String HMD_NORMAL = "HMD_NORMAL";
	public static final String HMD_FAILURE = "HMD_FAILURE";
	public static final String POWER_INFORMATION="POWER_INFORMATION";
	//bhavya end HMD device
	public static final String DEVICE_MODEL = "DEVICE_MODEL";
	public static final String ZXT120 = "ZXT120";
	public static final String DEFAULT_POLLING_INTERVAL = "10";
	public static final String POLL_INTERVAL = "POLL_INTERVAL";
	public static final String TEMPERATURE_CHANGE = "TEMPERATURE_CHANGE";
	public static final String LUXLEVEL_CHANGE = "LUXLEVEL_CHANGE";
	public static final String PMLEVEL_CHANGE="PMLEVEL_CHANGE";
	
	
	public static final String DB_FAILURE = "DB_FAILURE";
	public static final String DB_SUCCESS = "DB_SUCCESS";
	public static final String iMVG_FAILURE = "iMVG_FAILURE";
	public static final String NO_RESPONSE_FROM_GATEWAY = "NO_RESPONSE_FROM_GATEWAY";
	//New Rule Specific Message
	public static final String RULE_SPECIFIC_RULE_ID = "RI";
	public static final String RULE_SPECIFIC_RULE_DELAY = "RD";
	public static final String RULE_SPECIFIC_ALERT_EVENT = "AE";
	public static final String RULE_SPECIFIC_START_TIME = "ST";
	public static final String RULE_SPECIFIC_END_TIME = "ET";
	public static final String RULE_SPECIFIC_COMFORT_SECURITY = "CS";
	public static final String RULE_SPECIFIC_ACTION_DEF = "AD";
	
	//vibhu added
	public static final int MAX_RULE_PER_DEVICE_ALERT = 5;
	
	//New Schedule Specific Message
	public static final String SCHEDULE_SPECIFIC_SCHEDULE_ID = "SI";
	public static final String SCHEDULE_SPECIFIC_SCHEDULE_TIME = "SCT";
	//New Scenario Specific Message
	public static final String CREATE_SCENARIO = "CREATE_SCENARIO";
	public static final String SCENARIO_ID = "SCI";
	public static final String MODIFY_SCENARIO = "MODIFY_SCENARIO";
	public static final String DELETE_SCENARIO = "DELETE_SCENARIO";
	public static final String SCENARIO_NAME = "SCNM";
	public static final String SCENARIO_DES = "SCDS";
	//Password Change Syatem Alert Notification to Main User
	public static final String PASSWORD_CHANGED = "Password Changed";
	//Device Discovery Stop from iMVG
	public static final String DEVICE_DISCOVERY_STOP_SUCCESS = "DEVICE_DISCOVERY_STOP_SUCCESS";
	public static final String DEVICE_DISCOVERY_STOP_FAILURE = "DEVICE_DISCOVERY_STOP_FAILURE";
	public static final String STOP_REASON = "STOP_REASON";
	public static final String DEVICE_DISCOVERY_FAILED = "DEVICE_DISCOVERY_FAILED";
	public static final String DEVICE_DISCOVERY_DONE = "DEVICE_DISCOVERY_DONE";
	public static final String DEVICE_DISCOVERY_TIMED_OUT = "DEVICE_DISCOVERY_TIMED_OUT";
	public static final String DEVICE_ALREADY_ADDED = "DEVICE_ALREADY_ADDED";
	//Device Removal Stop from iMVG
	public static final String DEVICE_REMOVE_DONE = "DEVICE_REMOVE_DONE";
	public static final String DEVICE_REMOVE_FAILED = "DEVICE_REMOVE_FAILED";
	public static final String DEVICE_REMOVE_TIMED_OUT = "DEVICE_REMOVE_TIMED_OUT";
	public static final String DEVICE_REMOVE_STOP_SUCCESS = "DEVICE_REMOVE_STOP_SUCCESS";
	public static final String DEVICE_REMOVE_TIMEOUT_DURATION = "DEVICE_REMOVE_TIMEOUT_DURATION";
	//Notification Feature Modification [SMS/EMAIL/VOICE_CALL]
	public static final String SMS_ALERT_NOTIFIER = "SMS_ALERT_NOTIFIER";
	public static final String EMAIL_ALERT_NOTIFIER = "EMAIL_ALERT_NOTIFIER";
	public static final String VOICE_ALERT_NOTIFIER = "VOICE_ALERT_NOTIFIER";
	//public static final String EFL_SMS_NOTIFIER = "adoefl";			//ADOEFL Sms API
	
	public static final String IMS_SMS_NOTIFIER = "ims";		//IMSIPL Sms API
	public static final String IMS_EMAIL_NOTIFIER = "ims";		//IMSIPL Email API
	public static final String IMS_VOICE_NOTIFIER = "ims";		//IMSIPL Voice Alert API
	public static final String EFL_SMS_NOTIFIER = "efl";		// Sms API
	public static final String EFL_EMAIL_NOTIFIER = "efl";		// Email API
	public static final String EFL_VOICE_NOTIFIER = "efl";		// Voice Alert API
	//Unsupported Device Type
	public static final String UNSUPPORTED = "UNSUPPORTED";
	public static final String BASIC_DEV_CLASS = "BASIC_DEV_CLASS";
	public static final String GENERIC_CLASS = "GENERIC_CLASS";
	public static final String SPECIFIC_CLASS = "SPECIFIC_CLASS";
	public static final String SUPPORTED_CLASSES = "SUPPORTED_CLASSES";
	public static final String MANUFACTURER_ID = "MANUFACTURER_ID";
	public static final String PRODUCT_ID = "PRODUCT_ID";
	public static final String UNSUPPORTED_DEVICE_TYPE = "UNSUPPORTED_DEVICE_TYPE";
	//Configure Switch Type to Rocker or Tact
	public static final String CONFIGURE_SWITCH = "CONFIGURE_SWITCH";
	public static final String SWITCH_TYPE = "SWITCH_TYPE";
	public static final String ROCKER = "ROCKER";
	public static final String TACT = "TACT";
	//************************************************************ sumit end *****************************************************************
	public static final String ARM = "ARM";
	public static final String DISARM = "DISARM";
	public static final String MODEL_NO = "DEVICE_MODEL";
	public static final String IMAGES_DIR = "IMAGES_DIR";
	public static final String STREAMS_DIR = "STREAMS_DIR";
	public static final String STATUS = "STATUS";
	public static final String IMAGE_CAPTURE_INITIATED = "IMAGE_CAPTURE_INITIATED";
	public static final String ZXT_AC_MODE = "ZXT_AC_MODE";
	public static final String ZXT_AC_CODE_NO = "ZXT_AC_CODE_NO";
	public static final String ZXT_AC_SET_POINT_TYPE = "ZXT_AC_SET_POINT_TYPE";
	public static final String ZXT_AC_SET_POINT_VALUE = "ZXT_AC_SET_POINT_VALUE";
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";
	public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	public static final String MAIL_SMTP_PORT = "mail.smtp.port";
	public static final String INTERNET_ADDRESS = "internetaddress";
	public static final String MAIL_USERNAME = "mail.username";
	public static final String MAIL_PASSWORD = "mail.password";
	//vibhu start
	public static final String REQUEST_NEIGHBOR_UPDATE_STARTED = "REQUEST_NEIGHBOR_UPDATE_STARTED";
	public static final String REQUEST_NEIGHBOR_UPDATE_STARTED_NAME = "N Update Started";
	public static final String REQUEST_NEIGHBOR_UPDATE_DONE = "REQUEST_NEIGHBOR_UPDATE_DONE";
	public static final String REQUEST_NEIGHBOR_UPDATE_DONE_NAME = "N Update Successfull";
	public static final String REQUEST_NEIGHBOR_UPDATE_FAILED = "REQUEST_NEIGHBOR_UPDATE_FAILED";
	public static final String REQUEST_NEIGHBOR_UPDATE_FAILED_NAME = "N Update Failed";
	//vibhu end
	
	
	
	public static final String GET_ALL_STREAMS_WITH_CLIENTS = "getallstreamswithclients";
	public static final String SEND_TEAR_DOWN_TO_PROVIDER = "SEND_TEAR_DOWN_TO_PROVIDER";
	public static final String GET_RTSP_URL = "GET_RTSP_URL";
	public static final String WAIT_GET_RTSP_URL = "WAIT_GET_RTSP_URL";
	public static final String SWITCH_DIMMER_STATE = "SWITCH_DIMMER_STATE";
	public static final String IMAGE_FILE_EXTENSION = "IMAGE_FILE_EXTENSION";
	public static final String EXECUTE_SCENARIO = "EXECUTE_SCENARIO";
	public static final String ARM_DEVICE = "ARM_DEVICE";
	public static final String DISARM_DEVICE = "DISARM_DEVICE";
	public static final String CREATE_RULE = "CREATE_RULE";
	public static final String THRESHOLD_REACHED = "THRESHOLD_REACHED";
	public static final String THRESHOLD_VALUE = "THRESHOLD_VALUE";
	public static final String RULE_ID = "RULE_ID";
	public static final String ACTION_DEF = "ACTION_DEF";
	public static final String ACT_DEVICE_ID = "ACT_DEVICE_ID";
	public static final String ZW_DIMMER_LEVEL = "ZW_DIMMER_LEVEL";
	public static final String MODIFY_RULE = "MODIFY_RULE";
	public static final String DELETE_RULE = "DELETE_RULE";
	public static final String DEVICE_ARM = "DEVICE_ARM";
	public static final String DEVICE_DISARM = "DEVICE_DISARM";
	public static final String CREATE_SCHEDULE = "CREATE_SCHEDULE";
	public static final String SCHEDULE_ID = "SCHEDULE_ID";
	public static final String SCHEDULE_TIME = "SCHEDULE_TIME";
	public static final String MODIFY_SCHEDULE = "MODIFY_SCHEDULE";
	public static final String DELETE_SCHEDULE = "DELETE_SCHEDULE";
	public static final String ARM_DEVICES = "ARM_DEVICES";
	public static final String DISARM_DEVICES = "DISARM_DEVICES";
	//sumit begin
	public static final String STAY_DEVICES = "STAY_DEVICES";
	//sumit end
	public static final String INVALID_RULE_ID = "RULE_ID_DOESNOT_EXIST";
	public static final String INVALID_SCHEDULE_ID = "SCHEDULE_ID_DOESNOT_EXIST";
	public static final String INVALID_SCENARIO_ID = "SCENARIO_ID_DOESNOT_EXIST";
	public static final String IMVG_WAN_IP = "IMVG_WAN_IP";
	public static final String UPDATE_IMVG_WAN_IP_ACK = "UPDATE_IMVG_WAN_IP_ACK";
	public static final String H264_VIDEO_BRIGHTNESS = "H264_VIDEO_BRIGHTNESS";
	public static final String H264_VIDEO_CONTRAST = "H264_VIDEO_CONTRAST";
	public static final String H264_VIDEO_AC_FREQUENCY = "H264_VIDEO_AC_FREQUENCY";
	public static final String H264_VIDEO_FRAMERATE = "H264_VIDEO_FRAMERATE";
	public static final String H264_VIDEO_RESOLUTION = "H264_VIDEO_RESOLUTION";
	public static final String H264_IMAGE_QUALITY = "H264_IMAGE_QUALITY";
	public static final String H264_VIDEO_BITRATE = "H264_VIDEO_BITRATE";
	public static final String H264_VIDEO_BITRATE_MODE = "H264_VIDEO_BITRATE_MODE";
	public static final String H264_VIDEO_KEYFRAME = "H264_VIDEO_KEYFRAME";
	public static final String H264_LED_MODE = "H264_LED_MODE";
	public static final String H264_PTZ_PATROL_RATE = "H264_PTZ_PATROL_RATE";
	public static final String H264_PTZ_PATROL_UP_RATE = "H264_PTZ_PATROL_UP_RATE";
	public static final String H264_PTZ_PATROL_DOWN_RATE = "H264_PTZ_PATROL_DOWN_RATE";
	public static final String H264_PTZ_PATROL_LEFT_RATE = "H264_PTZ_PATROL_LEFT_RATE";
	public static final String H264_PTZ_PATROL_RIGHT_RATE = "H264_PTZ_PATROL_RIGHT_RATE";
	public static final String H264_USER_ADMIN_USERNAME = "H264_USER_ADMIN_USERNAME";
	public static final String H264_USER_ADMIN_PASSWORD = "H264_USER_ADMIN_PASSWORD";

	public static final String IPC_ENABLE_LIVE_STREAM="IPC_ENABLE_LIVE_STREAM";
	public static final String ENABLE = "ENABLE";
	public static final String DISABLE = "DISABLE";
	public static final String DEVICE_ENBLE="DEVICE_ENBLE";
	public static final String DEVICE_DISABLE="DEVICE_DISABLE";
	public static final String DEVICE_ENBLE_NAME = "Device enable";
	public static final String DEVICE_DISABLE_NAME = "Device disable";
	public static final String CURTAIN_OPEN = "CURTAIN_OPEN";
	public static final String CURTAIN_CLOSE = "CURTAIN_CLOSE";
	public static final String CURTAIN_OPEN_NAME = "Curtain Open";
	public static final String CURTAIN_CLOSE_NAME = "Curtain Close";
	public static final String DEVICE_STATUS_UPDATE = "DEVICE_STATUS_UPDATE";
	public static final String FRIENDLY_DEVICE_NAME = "FRIENDLY_DEVICE_NAME";
	public static final String DEVICE_LOCATION = "DEVICE_LOCATION";
	public static final String DEVICE_LOCATION_ID = "DEVICE_LOCATION_ID";
	//sumit start
	public static final String FILENAME = "FILENAME";
	//sumit end
	public static final String RULE_DELAY = "RULE_DELAY";
	public static final String COMFORT_SECURITY = "COMFORT_SECURITY"; //vibhu added
	public static final String DEVICE_CONFIG = "DEVICE_CONFIG";
	public static final String CAMERA_STATE_CHANGED = "CAMERA_STATE_CHANGED";
	public static final String RESPONSE_TYPE = "RESPONSE_TYPE";
	public static final String RESPONSE_TIME = "RESPONSE_TIME";
	public static final String POLLING_TIME = "POLLING_TIME";
	public static final String UPDATED_DEVICE_STATUS_ACK = "UPDATED_DEVICE_STATUS_ACK";
	public static final String DEVICE_STATUS = "DEVICE_STATUS";
	public static final String DEVICE_STATUS_UPDATED = "DEVICE_STATUS_UPDATED";
	public static final String LOG_DIR = "LOG_DIR";
	public static final String GET_IMVGLOGS = "GET_IMVGLOGS";
	public static final String LOGIN_FAIL_MESSAGE_ADMIN = "Invalid User Name or Password";
	public static final String EXCUTE_USER_COMMAND = "EXCUTE_USER_COMMAND";
	public static final String COMMAND = "COMMAND";
	public static final String COMMAND_NAME = "COMMAND_NAME";
	public static final String DATA = "DATA";
	public static final String TIME_OUT = "TIME_OUT";
	public static final String STORE_ALARM = "STORE_ALARM";
	public static final String EXECUTE_DELAY = "EXECUTE_DELAY";
    //bhavya added
	public static final String F1 = "F1";
	public static final String F2 = "F2";
	public static final String F3 = "F3";
	//bhavya end
	//vibhu added
	public static final long MILLISECONDS_IN_A_DAY = 1 * 24 * 60 * 60 * 1000;
	public static final int MAX_IMVG_UNICODE_STRING_LEN = 6 * 5;
	public static final String PRESET_CAMERA = "PRESET_CAMERA";
	//public static final String PRESET_VALUE ="PRESET_VALUE";
	public static final String PRESET_MOVE = "PRESET_MOVE";
	public static final String PRESET_SET = "PRESET_SET";
	public static final Locale LOCALE_EN_US = new Locale("en", "US");
	public static final Locale LOCALE_KA_IN = new Locale("ka", "IN");	
	public static final String LOCALE_KEY_EN_US = "en_US";
	public static final String LOCALE_KEY_KA_IN = "ka_IN";
	public static final String UI_MSG_RESOURCE = "imsUIMsgs";
	public static final String SMS_ALERT_NOTIFIER_ADDRESS = "SMS_ALERT_NOTIFIER_ADDRESS";
	public static final String EMAIL_ALERT_NOTIFIER_ADDRESS = "EMAIL_ALERT_NOTIFIER_ADDRESS";
	public static final String imvg_upload_context_path = "imvg_upload_context_path";
	public static final String SMS_ALERT_NOTIFIER_ADDRESS_1 = "SMS_ALERT_NOTIFIER_ADDRESS_1";
	public static final String DOOR_LOCK_STATE = "DOOR_LOCK_STATE";
	public static final String IMVG_MIGRATION = "IMVG_MIGRATION";
	public static final String SERVER_IP = "SERVER_IP";
	public static final String SEVER_PORT = "SEVER_PORT";
	
	public static final String REQUEST_CONFIGURATION_STARTED = "REQUEST_CONFIGURATION_STARTED";
	public static final String REQUEST_CONFIGURATION_STARTED_NAME = "switch configure Started";
	public static final String REQUEST_CONFIGURATION_DONE = "REQUEST_CONFIGURATION_DONE";
	public static final String REQUEST_CONFIGURATION_DONE_NAME = "switch configure success";
	public static final String REQUEST_CONFIGURATION_FAILED = "REQUEST_CONFIGURATION_FAILED";
	public static final String REQUEST_CONFIGURATION_FAILED_NAME = "switch configure failed";
	public static final String REQUEST_ASSOCIATION_STARTED = "REQUEST_ASSOCIATION_STARTED";
	public static final String REQUEST_ASSOCIATION_STARTED_NAME = "associate started";
	public static final String REQUEST_ASSOCIATION_DONE = "REQUEST_ASSOCIATION_DONE";
	public static final String REQUEST_ASSOCIATION_DONE_NAME = "associate success";
	public static final String REQUEST_ASSOCIATION_FAILED = "REQUEST_ASSOCIATION_FAILED";
	public static final String REQUEST_ASSOCIATION_FAILED_NAME = "associate failed";
	public static final String PARAM_VAL = "PARAM_VAL";
	
	public static final String ZXT_AC_FAN_MODE = "ZXT_AC_FAN_MODE";
	public static final String ZXT110 = "ZXT110";
	public static final String DEFAULT_FAN_MODES = "1";
	public static final String AC_MODE = "AC_MODE";
	public static final String AC_MODE_NAME = "AC Mode";
	public static final String AC_MODE_CHANGED="AC_MODE_CHANGED";
	public static final String AC_THERMOSTATSETPOINTTYPE="AC_THERMOSTATSETPOINTTYPE";
	public static final String AC_TEMPERATURE_STATE="AC_TEMPERATURE_STATE";
	public static final String AC_MODE_TEMP="AC_MODE_TEMP";
	public static final String ZXT_AC_SWING_CONTROL = "ZXT_AC_SWING_CONTROL";
	public static final String AC_SWING_CONTROL="AC_SWING_CONTROL";
	public static final String AC_FAN_MODE_CHANGED="AC_FAN_MODE_CHANGED";
	public static final String SI_DIR="SI_DIR";
	public static final String SWITCH_ALL = "SWITCH_ALL";
	

	
	public static final String DEVICE_UPDATE_MODEL="DEVICE_UPDATE_MODEL";
	public static final String CONFIG_SIREN="CONFIG_SIREN";
	public static final String SIREN_STROBE="SIREN_STROBE";
	public static final String CONFIG_VAL="CONFIG_VAL";
	public static final String CONFIG_VAL1="CONFIG_VAL1";
	public static final String SIREN_TIMEOUT="SIREN_TIMEOUT";

	public static final String PWR_LMT_WARNING = "PWR_LMT_WARNING";
	public static final String PWR_LMT_REACHED = "PWR_LMT_REACHED";
	public static final String PWR_LMT_REACHED_SUCCESS = "PWR_LMT_REACHED_SUCCESS";
	public static final String PWR_LMT_REACHED_FAIL = "PWR_LMT_REACHED_FAIL";
	public static final String CREATE_USR = "CREATE_USR";
	public static final String MODIFY_USR = "MODIFY_USR";
	public static final String DELETE_USR = "DELETE_USR";
	public static final String CSTR_ID = "CSTR_ID";
	public static final String USR_ID = "USR_ID";
	public static final String USR_NAME = "USR_NAME";
	public static final String PASSWD = "PASSWD";
	public static final String REVOKE_USR = "REVOKE_USR";
	public static final String SUSPEND_USR = "SUSPEND_USR";
	public static final String MODIFY_MAINUSR = "MODIFY_MAINUSR";
	public static final String MAIN_ID = "MAIN_ID";
	public static final String SCHEDULE_EXEC_SUCCESS ="SCHEDULE_EXEC_SUCCESS";
	public static final String SCHEDULE_EXEC_FAIL ="SCHEDULE_EXEC_FAIL";
	public static final String NEW_ALARM = "NEW_ALARM";
	public static final String SET_USER_CODE = "SET_USER_CODE";
	public static final String USER_CODE = "USER_CODE";
	
	public static final String ASSIGN_RETURN_ROUTE_FAIL = "ASSIGN_RETURN_ROUTE_FAIL";
	public static final String ASSIGN_RETURN_NO_ROUTE = "ASSIGN_RETURN_NO_ROUTE";
	public static final String ASSIGN_RETURN_ROUTE_SUCCESS = "ASSIGN_RETURN_ROUTE_SUCCESS";
	public static final String PERCENTAGE = "PERCENTAGE";
	public static final String AUTO_SEARCH_AC = "AUTO_SEARCH_AC";
	public static final String CODE_NO = "CODE_NO";
	public static final String AUTO_SEARCH_AV = "AUTO_SEARCH_AV";
	public static final String ZXT_AV_BLASTER_CODE_NO = "ZXT_AV_BLASTER_CODE_NO";
	public static final String SEND_SMS_EMAIL = "EmailAndSMS";
	public static final String IMVG_KEEP_ALIVE_ACK = "IMVG_KEEP_ALIVE_ACK";
	public static final String AC_LEARN_IR_KEY = "AC_LEARN_IR_KEY";
	public static final String NORMAL_SWITCH_CONFIG_FAIL = "NORMAL_SWITCH_CONFIG_FAIL";
	public static final String NORMAL_SWITCH_CONFIG_SUCCESS = "NORMAL_SWITCH_CONFIG_SUCCESS";
	public static final String RMV_DEADNODE = "RMV_DEADNODE";
	public static final String GET_DEVICELIST = "GET_DEVICELIST";
	public static final String LIST = "LIST";
	public static final String REMOVEDEVICE = "REMOVEDEVICE";
	public static final String GETZWAVE_VERSION = "GETZWAVE_VERSION";
	public static final String VERSION = "VERSION";
	public static final String B1 = "B1";
	public static final String B2 = "B2";
	public static final String B3 = "B3";
	public static final String B4 = "B4";
	public static final String ZW_CONTROL_LEVEL = "ZW_CONTROL_LEVEL";
	public static final String SUPPORT_USER = "supportuser";
	public static final String ALERT_SERVICE = "alertservice";
	
	//Navee Added on 30th March 2015 for Master slave, Node Info Protocol and Routing Info
	public static final String Z_WAVE_SLAVE = "Z_WAVE_SLAVE";
	public static final String MASTER = "MASTER";
	public static final String SLAVE = "Node is a Slave";
	public static final String UNIQ_ID = "UNIQ_ID";
	public static final String SLV_STATUS = "SLV_STATUS";
	public static final String ROUTING_INFO_SUCCESS = "ROUTING_INFO_SUCCESS";
	public static final String NODE_PROTOCOL_INFO_SUCCESS = "NODE_PROTOCOL_INFO_SUCCESS";
	public static final String HOME_ID = "HOME_ID";
	public static final String NODE_ID = "NODE_ID";
	public static final String ZWSER_VER = "ZWSER_VER";
	public static final String CAP_FLAG = "CAP_FLAG";
	public static final String CHIP_TYPE = "CHIP_TYPE";
	public static final String CHIP_VER = "CHIP_VER";
	public static final String NUM_NODES = "NUM_NODES";
	public static final String BASIC = "BASIC";
	public static final String GENERIC = "GENERIC";
	public static final String SPECIFIC = "SPECIFIC";
	public static final String NEIGHBOURS = "NEIGHBOURS";
	public static final String ROUTING_SLAVE = "Node is a Slave with routing capabilities";
	public static final String CONTROLLER = "Node is a Portable controller";
	public static final String STATIC_CONTROLLER = "Node is static controller";
	public static final String SENSOR_BINARY = "Binary Sensor";
	public static final String NOT_USED = "Specific Device Class not used";
	public static final String ROUTING_SENSOR_BINARY = "Routing Binary Sensor";
	public static final String MULTI_SENSOR = "Multi Sensor";
	public static final String SENSOR_MULTILEVEL = "Multilevel Sensor";
	public static final String CHIMNEY_FAN = "Chimney Fan";
	public static final String ROUTING_SENSOR_MULTILEVEL = "Routing Multilevel Sensor";
	public static final String SWITCH_BINARY = "Binary Switch";
	public static final String POWER_SWITCH_BINARY = "Binary Power Switch";
	public static final String SCENE_SWITCH_BINARY = "Binary Scene Switch";
	public static final String SWITCH_MULTILEVEL = "Multilevel Switch";
	public static final String CLASS_A_MOTOR_CONTROL = "Class A Motor Controller";
	public static final String CLASS_B_MOTOR_CONTROL = "Class B Motor Controller";
	public static final String CLASS_C_MOTOR_CONTROL = "Class C Motor Controller";
	public static final String MOTOR_MULTIPOSITION = "Multiposition Motor";
	public static final String POWER_SWITCH_MULTILEVEL = "Multilevel Power Switch";
	public static final String SCENE_SWITCH_MULTILEVEL = "Multilevel Scene Switch";
	public static final String REPEATER_SLAVE = "Repeater Slave";
	public static final String ENTRY_CONTROL = "Entry Control";
	public static final String ADVANCED_DOOR_LOCK = "Advanced Door Lock";
	public static final String DOOR_LOCK = "Door Lock";
	public static final String SECURE_KEYPAD_DOOR_LOCK = "Secure Keypad Door Lock";
	public static final String THERMOSTAT  = "Thermostat";
	public static final String SETBACK_SCHEDULE_THERMOSTAT  = "Setback Schedule Thermostat";
	public static final String SETBACK_THERMOSTAT = "Setback Thermostat";
	public static final String SETPOINT_THERMOSTAT = "Setpoint Thermostat";
	public static final String THERMOSTAT_GENERAL = "Thermostat General";
	public static final String THERMOSTAT_GENERAL_V2 = "Thermostat General V2";
	public static final String THERMOSTAT_HEATING = "Thermostat Heating";
	public static final String GENERIC_CONTROLLER = "Remote Controller";
	public static final String PORTABLE_INSTALLER_TOOL = "Portable Installer Tool";
	public static final String PORTABLE_REMOTE_CONTROLLER = "Portable Remote Controller";
	public static final String PORTABLE_SCENE_CONTROLLER = "Portable Scene Controller";
	public static final String PULSE = "PULSE";
	public static final String SLAVE_ID = "SLAVE_ID";
	public static final String NO_NEIGHBOURS = "NO_NEIGHBOURS";
	public static final String TYPE = "TYPE";
	public static final String SLAVECOUNT = "SLAVECOUNT";
	public static final String DOWN_TIME = "DOWN_TIME";
	public static final String DOWN_TIME_INFO = "DOWN_TIME_INFO";
	public static final String GET_BACKUP_DATA = "GET_BACKUP_DATA";
	public static final String MOTION_DETECT_TIME = "MOTION_DETECT_TIME";
	public static final String YALE_LOCK_DISCOVERED = "YALE_LOCK_DISCOVERED";
	public static final String YALELOCK = "YALELOCK";
	public static final String MODE_YALE_LOCK = "MODE_YALE_LOCK";
	public static final String FINGER_PRINT1 = "FINGER_PRINT1";
	public static final String FINGER_PRINT2 = "FINGER_PRINT2";
	public static final String FINGER_PRINT3 = "FINGER_PRINT3";
	public static final String FINGER_PRINT4 = "FINGER_PRINT4";
	public static final String FINGER_PRINT5 = "FINGER_PRINT5";
	public static final String FINGER_PRINT6 = "FINGER_PRINT6";
	public static final String FINGER_PRINT7 = "FINGER_PRINT7";
	public static final String FINGER_PRINT8 = "FINGER_PRINT8";
	public static final String FINGER_PRINT9 = "FINGER_PRINT9";
	public static final String FINGER_PRINT10 = "FINGER_PRINT10";
	public static final String GET_RESTORE_DATA =  "GET_RESTORE_DATA";
	public static final String IMVG_TIME_STAMP = "IMVG_TIME_STAMP";
	public static final String MIDBROADCAST_PORT = "MIDBROADCAST_PORT";
	public static final String MIDL_LOGIN_PORT = "MIDL_LOGIN_PORT";
	public static final String LFTPDOWNLOAD = "LFTPDOWNLOAD";
	public static final String GET_DIAGNOSTIC_DETAIL = "GET_DIAGNOSTIC_DETAIL";
	public static final String SEND_WHATSAPP = "WhatsApp";
	public static final String UN_SUBSCRIBED = "Un-subscribed";
	public static final String SMS_SUBSCRIBED = "Subscribed";
	public static final String NOT_ENABLED = "Not enabled";
	public static final String PM_VALUE="PM_VALUE";
	public static final String ENERGY_INFORMATION="ENERGY_INFORMATION";
	public static final String ENERGY_CONSUMED="ENERGY_CONSUMED";
//control commands for alexa service
	
	public static final String ZwaveAcOn="ZwaveAcOn";
	public static final String ZwaveAcOff="ZwaveAcOff";
	public static final String ENDPOINT_UNREACHABLE="ENDPOINT_UNREACHABLE";
	public static final String SetAcTemp="SetAcTemp";
	public static final String SetAcMode="SetAcMode";
	public static final String SetAcOff="SetAcOff";
	public static final String AdjustAcTemp="AdjustAcTemp";
	public static final String SetZWDim="SetZWDim";
	public static final String ZWDevDimDown="ZWDevDimDown";
	public static final String ZWDevDimUp="ZWDevDimUp";
	public static final String UNREACHABLE="UNREACHABLE";
//Action
	public static final String ADD_ACTIONS="ADD_ACTIONS";
	public static final String ACT_PARAM="ACT_PARAM";
	public static final String DEV_ZWAVE_SCENE_CONTROLLER= "DEV_ZWAVE_SCENE_CONTROLLER";
	
	public static final String DELETE_ACTIONS="DELETE_ACTIONS";
	public static final String MODIFY_ACTIONS="MODIFY_ACTIONS";
	//DeviceTypes New Format
	
	public static final String KEY_CONFIGURATION="KEY_CONFIGURATION";
	public static final String KN = "KN";
	public static final String ZW_CONTROL_TYPE="ZW_CONTROL_TYPE";
	public static final String HTTP_PATH="HTTP_PATH";	
    public static final String NW_STATS_REQ = "NW_STATS_REQ";
	public static final String FAN_CONTROL = "FAN_CONTROL";
	public static final String T_TIME ="T_TIME";
	public static final String RSSI ="RSSI";
	public static final String R_RETRIES ="R_RETRIES";
	public static final String R_SPEED ="R_SPEED";
	public static final String NC ="NC";
	public static final String RC ="RC";
	public static final String CANT_ACCESS_DEVICE="CANT_ACCESS_DEVICE";
	public static final String APP_INTERACTION="APP_INTERACTION";
	public static final String ChangeReport="ChangeReport";
	
	//Daiken VIA
	public static final String VIA="VIA";
	public static final String VIA_UNIT="VIA_UNIT";
	public static final String DISCOVER_VRV_UNITS="DISCOVER_VRV_UNITS";
	public static final String DISCOVER_SLAVE="DISCOVER_SLAVE";
	public static final String ADD_VIASLAVE="ADD_VIASLAVE";
	public static final String SLAVEID="SLAVEID";
	public static final String DISCOVER_INDOOR_UNIT="DISCOVER_INDOOR_UNIT";
	public static final String REMOVE_VIADEVICE="REMOVE_VIADEVICE";
	public static final String REMOVE_VIASLAVE="REMOVE_VIASLAVE";
	public static final String GET_VRVINDOOR_CAPABILITY="GET_VRVINDOOR_CAPABILITY";
	public static final String VRV1="VRV1";
	public static final String VRV2="VRV2";
	public static final String VRV3="VRV3";
	public static final String VRV4="VRV4";
	public static final String FANMODE_CAPABILITY="FANMODE_CAPABILITY";
	public static final String COOLMODE_CAPABILITY="COOLMODE_CAPABILITY";
	public static final String HEATMODE_CAPABILITY="HEATMODE_CAPABILITY";
	public static final String AUTOMODE_CAPABILITY="AUTOMODE_CAPABILITY";
	public static final String DRYMODE_CAPABILITY="DRYMODE_CAPABILITY";
	public static final String FANDIRECTION_LEVEL_CAPABILITY="FANDIRECTION_LEVEL_CAPABILITY";
	public static final String FANDIRECTION_CAPABILITY="FANDIRECTION_CAPABILITY";
	public static final String FANVOLUME_LEVEL_CAPABILITY="FANVOLUME_LEVEL_CAPABILITY";
	public static final String FANVOLUME_CAPABILITY="FANVOLUME_CAPABILITY";
	public static final String COOLING_UPPERLIMIT="COOLING_UPPERLIMIT";
	public static final String COOLING_LOWERLIMIT="COOLING_LOWERLIMIT";
	public static final String HEATING_UPPERLIMIT="HEATING_UPPERLIMIT";
	public static final String HEATING_LOWERLIMIT="HEATING_LOWERLIMIT";
	public static final String SLAVEMODEL="SLAVEMODEL";
	public static final String REMOVE_IDU="REMOVE_IDU";
	public static final String ERROR_CODE_VALUE="ERROR_CODE_VALUE";
	public static final String RESET_FILTER_SIGN="RESET_FILTER_SIGN";
	public static final String FILTER_SIGN_STATUS="FILTER_SIGN_STATUS";
	public static final String HA_IF="HA_IF";
	public static final String ROOMTEMP="ROOMTEMP";
	public static final String AC_MODE_CONTROL="AC_MODE_CONTROL";
	public static final String AC_FAN_MODE="AC_FAN_MODE";
	
	
	
	//Naveen
	public static final String ALEXA_CLIENT_ID = "ALEXA_CLIENT_ID";
	public static final String ALEXA_SECRET = "ALEXA_SECRET";
	public static final String ALEXA_EVENT_POINT="ALEXA_EVENT_POINT";
	public static final String ALEXA_TOKEN_END_POINT = "ALEXA_TOKEN_END_POINT";
	
	//Apoorva for alexa IDu
	public static final String SetIduTemp = "SetIduTemp";
	public static final String SetIduMode = "SetIduMode";
	public static final String SetIduOff = "SetIduOff";
	public static final String AdjustIduTemp = "AdjustIduTemp";
	public static final String IduOn ="IduOn";
	public static final String IduOff = "IduOff";
	
	
	//For 911 service
	public static final String AlertService_OPEN = "Open";

	
	//PushNotification
	public static final String ANDROID_URL = "ANDROID_URL";
	public static final String ANDROID_AUTH_KEY = "ANDROID_AUTH_KEY";
	public static final String HOME_FAILURE = "HOME_FAILURE";
	public static final String STAY_FAILURE = "STAY_FAILURE";
	public static final String AWAY_FAILURE = "AWAY_FAILURE";
	public static final String SENSED_LIGHT_INTENSITY = "SENSED_LIGHT_INTENSITY";	
	public static final String NODE_PROTOCOL_INFO_FAILED = "NODE_PROTOCOL_INFO_FAILED";
	public static final String ROUTING_INFO_FAILED = "ROUTING_INFO_FAILED";
	public static final String SENSED_PM_LEVEL = "SENSED_PM_LEVEL";
	
	public static final String DEV_ZWAVE_WALL_MOTE_QUAD = "DEV_ZWAVE_WALL_MOTE_QUAD";
	public static final String DEV_ZWAVE_FIB_KEYFOBE = "DEV_ZWAVE_FIB_KEYFOBE";
	public static final String SCHEDULE_NAME = "SHNM";
	
	//Push notification IOS
	public static final String APN_CERTIFICATE_NAME = "APN_CERTIFICATE_NAME";
	public static final String APN_PASSWORD = "APN_PASSWORD";
	
	public static final String ZXT600 = "ZXT600";
	
	//RGB
	public static final String DEV_ZWAVE_RGB = "DEV_ZWAVE_RGB";
	public static final String ZW_SWITCH_COLOR = "ZW_SWITCH_COLOR";
	
	
	
	//IR Output Code
	public static final String SET_IR_OUTPUT_PORT =  "SET_IR_OUTPUT_PORT";
	
	//ZW Virtual Switch
	public static final String DEV_ZWAVE_VIRTUAL_SW = "DEV_ZWAVE_VIRTUAL_SW";
	public static final String TWO_WAY_SWITCH = "TWO_WAY_SWITCH";
	public static final String SCENARIO_CONTROL = "SCENARIO_CONTROL";
	public static final String SCI = "SCI";
	
	//Added by naveen
	public static final String DOOR_IS_OPENED = "DOOR_IS_OPENED";
	
	public static final String ZW_DEVICE_CONTRO = "ZW_DEVICE_CONTRO";
	
	public static final String SET_ZWAVE_DEV_CONFIG = "SET_ZWAVE_DEV_CONFIG";
	public static final String PARAM_VALUE = "PARAM_VALUE";
	public static final String PARAM_ID = "PARAM_ID";
	public static final String PHYSICAL_INTERACTION = "PHYSICAL_INTERACTION";
	public static final Object ANDROID_DAIKIN_AUTH_KEY = "ANDROID_DAIKIN_AUTH_KEY";
	public static final Object APN_CERTIFICATE_NAME_DAIKIN = "APN_CERTIFICATE_NAME_DAIKIN";
	
	
	
	
}	
