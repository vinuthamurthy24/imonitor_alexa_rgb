/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.util;


/**
 * @author Coladi
 *
 */
public class Constants {
	public static final String TRANSACTION_ID = "TRANSACTION_ID";
	public static final String CMD_ID = "CMD_ID";
	public static final String DISCOVER_DEVICES = "DISCOVER_DEVICES";
	public static final String DISCOVER_DEVICES_ACK = "DISCOVER_DEVICES_ACK";
	public static final String IMVG_ID = "IMVG_ID";
	public static final String SIGNATURE = "SIGNATURE";
	public static final String IMVG_KEEP_ALIVE = "IMVG_KEEP_ALIVE";
	public static final String DEVICE_DISCOVERED = "DEVICE_DISCOVERED";
	public static final String DEVICE_TYPE = "DEVICE_TYPE";
	public static final String DEVICE_ID = "DEVICE_ID";
	public static final String DEVICE_MODEL = "DEVICE_MODEL";
	public static final String IMVG_UP = "IMVG_UP";
	public static final String MOTION_DETECTED = "MOTION_DETECTED";
	public static final String DEVICE_DOWN = "DEVICE_DOWN";
	public static final String DEVICE_UP = "DEVICE_UP";
	public static final String BATTERY_LOW = "BATTERY_LOW";
	public static final String DOOR_LOCK_CLOSE = "DOOR_LOCK_CLOSE";
	public static final String DOOR_LOCK_OPEN = "DOOR_LOCK_OPEN";
	public static final String DOOR_OPEN = "DOOR_OPEN";
	public static final String HMD_NORMAL = "HMD_NORMAL";
	public static final String HMD_WARNING = "HMD_WARNING";
	public static final String HMD_FAILURE = "HMD_FAILURE";
	public static final String SCAN_HMD = "SCAN_HMD";
	public static final String DOOR_CLOSE = "DOOR_CLOSE";
	public static String SWITCH_ON = "SWITCH_ON";
	public static String SWITCH_OFF = "SWITCH_OFF";
	public static final String STREAM_FTP_SUCCESS = "STREAM_FTP_SUCCESS";
	public static final String STREAM_FTP_FAILED = "STREAM_FTP_FAILED";
	public static final String IMAGE_FTP_SUCCESS = "IMAGE_FTP_SUCCESS";
	public static final String IMAGE_FTP_FAILED = "IMAGE_FTP_FAILED";
	public static final String ALERT_TYPE = "ALERT_TYPE";
	public static final String ALERT_EVENT = "ALERT_EVENT";
	public static final String IMVG_TIME_STAMP = "IMVG_TIME_STAMP";
	public static final String DEVICE_ALERT = "DEVICE_ALERT";
	public static final String ACTIVE = "ACTIVE";
	public static final String PASSIVE = "PASSIVE";
	public static final String DEVICE_CONTROL = "DEVICE_CONTROL";
	public static final String ZW_DEVICE_CONTROL = "ZW_DEVICE_CONTROL";
	public static final String DEVICE_DISCOVERED_SUCCESS = "DEVICE_DISCOVERED_SUCCESS";
	public static final String DEVICE_CONTROL_SUCCESS = "DEVICE_CONTROL_SUCCESS";
	public static final String MONITOR_DEVICE_HEALTH_SUCCESS = "MONITOR_DEVICE_HEALTH_SUCCESS";
	
	
	public static String LIGHT_ON = "Switch Up";
	public static String LIGHT_OFF = "Switch Dwn";
	public static String DIMMER_HIGH = "Dimmer High";
	public static String DIMMER_MEDIUM = "Dimmer Medium";
	public static String DIMMER_LOW = "Dimmer Low";

	
//	Device types
	public static final String IP_CAMERA = "IP_CAMERA";
	public static final String Z_WAVE_SWITCH = "Z_WAVE_SWITCH";
	public static final String Z_WAVE_DIMMER = "Z_WAVE_DIMMER";
	public static final String Z_WAVE_DOOR_LOCK = "Z_WAVE_DOOR_LOCK";
	public static final String Z_WAVE_DOOR_LOCK_NUM_PAD = "Z_WAVE_DOOR_LOCK_NUM_PAD";
	public static final String Z_WAVE_DOOR_SENSOR = "Z_WAVE_DOOR_SENSOR";
	public static final String Z_WAVE_PIR_SENSOR = "Z_WAVE_PIR_SENSOR";
	public static final String Z_WAVE_REPEATER = "Z_WAVE_REPEATER";
	public static final String Z_WAVE_MULTI_SENSOR = "Z_WAVE_MULTI_SENSOR";
	public static final String Z_WAVE_SIREN = "Z_WAVE_SIREN";
	public static final String Z_WAVE_AC_EXTENDER = "Z_WAVE_AC_EXTENDER";
	
	public static final String ROOM1BULB = "Room1Bulb";
	public static final String ROOM2BULB = "Room2Bulb";
	public static final String ROOM3BULB = "Room3Bulb";
	public static final String ROOM4BULB = "Room4Bulb";
	public static final String ROOM3CAMERA = "ROOM3CAMERA";
	public static final String ROOM4CAMERA = "ROOM4CAMERA";
	public static final String ROOM1DIMMER = "Room1Dimmer";
	public static final String ROOM2DIMMER = "Room2Dimmer";
	public static final String ROOM3DIMMER = "Room3Dimmer";
	public static final String ROOM4DIMMER = "Room4Dimmer";
	public static final String IMVG_UNREGISTER = "IMVG_UNREGISTER";
	public static final String IMVG_UNREGISTER_ACK = "IMVG_UNREGISTER_ACK";
	public static final String REF_TRANSACTION_ID = "REF_TRANSACTION_ID";
	public static final String DEVICE_REMOVED = "DEVICE_REMOVED";
	public static final String TEMPERATURE_VALUE = "TEMPERATURE_VALUE";
	public static final String DOOR_1 = "door_1";
	public static final String ROOM1SIREN = "ROOM1SIREN";
	public static final String DOOR_2 = "Door_2";
	public static final String ROOM2PIR = "ROOM2PIR";
	public static final String ROOM3MUL = "ROOM3MUL";
	public static final String PIR_UP = "Pir Up";
	public static final String PIR_DOWN = "Pir Down";
	public static final String ZW_GET_TEMPERATURE_VALUE = "ZW_GET_TEMPERATURE_VALUE";
	public static final String BATTERY_STATUS = "BATTERY_STATUS";
	public static final String BATTERY_LEVEL = "BATTERY_LEVEL";
	public static final String IPC_GET_LIVE_STREAM = "IPC_GET_LIVE_STREAM";
	public static final String IPC_GET_LIVE_STREAM_SUCCESS = "IPC_GET_LIVE_STREAM_SUCCESS";
	public static final String IPC_GET_CAPTURE_IMAGE = "IPC_GET_CAPTURE_IMAGE";
	public static final String IPC_CAPTURE_IMAGE_ACK = "IPC_CAPTURE_IMAGE_ACK";
	public static final String STATUS = "STATUS";
	public static final String IMAGE_CAPTURE_INITIATED = "IMAGE_CAPTURE_INITIATED";
	public static final String FTP_PATH_FILE_NAME = "FTP_PATH_FILE_NAME";
	public static final String ROOM1AC = "ROOM1AC";
	public static final String PANIC_SITUATION = "PANIC_SITUATION";
	public static final String ROOM4MINIMOTE = "ROOM4MINIMOTE";
	public static final String Z_WAVE_MINIMOTE = "Z_WAVE_MINIMOTE";
	public static final String Z_WAVE_LCD_REMOTE = "Z_WAVE_LCD_REMOTE";
	public static final String Z_WAVE_HEALTH_MONITOR = "Z_WAVE_HEALTH_MONITOR";
}
