/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.util;

public class Constants {
	public static final String NORMAL_USER = "normaluser";
	public static final String MAIN_USER = "mainuser";
	public static final String SUSPEND = "Suspend";
	public static final String ADMIN_USER = "adminuser";
	public static final String USER = "user";
	public static final String LOCALE = "locale";
	public static final String LOGIN_FAIL_MESSAGE = "Invalid CustomerId or UserId or Password";
	public static final String LOGIN_FAIL_MESSAGE_ADMIN = "Invalid User Name or Password";
	public static final String LOGIN_FAIL_MESSAGE_SUSPEND = "You are suspended";
	public static final String MESSAGE = "message";
	public static final String MSG_FAILURE = "Failure: ";
	
	
	public static final String REGEX_ESCAPE = "\\";
	public static final String MESSAGE_DELIMITER_1 = "|";
	public static final String MESSAGE_DELIMITER_2 = "^";
	public static String NEW_LINE = System.getProperty("line.separator");
	public static final String CMD_ID = "CMD_ID";
	public static final String DEVICE_ID = "DEVICE_ID";
	public static final String TRANSACTION_ID = "TRANSACTION_ID";
	public static final String IMVG_ID = "IMVG_ID";
	public static final String SIGNATURE = "SIGNATURE";
	
	public static final String ON_OFF_CONTROL = "ON_OFF_CONTROL";
	public static final String DIMMER_CONTROL = "DIMMER_CONTROL";
	public static final String READER_CONTROL = "READER_CONTROL";
	public static final String CAMERA_CONTROL = "CAMERA_CONTROL";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	public static final String SESSION_EXPIRED = "sessionexpired";
	public static final String FORBIDDEN = "forbidden";
	public static final String CONTROLLER_IP = "controllerip";
	public static final String CONTROLLER_PORT = "controllerport";
	public static final String CONTROLLER_PROTOCOL = "serviceprotocol";
	public static final String DATE_FORMAT_SERVER = "date_format_server";
	public static final String IMVG_UPLOAD_CONTEXT_PATH ="imvg_upload_context_path";
	
	public static final String LOG_IN ="systemalert.logged.in";
	public static final String LOG_OUT ="systemalert.logged.out";
	public static final String DEVICE_ON ="Device On";
	public static final String DEVICE_OFF ="Device Off";
	public static final String STREAMING_STARTED ="Streaming Started";
	public static final String STREAMING_STOPPED  ="Streaming Stopped";
	public static final String DIMMERCHANGE ="Dimmer Change";
	public static final String AC_CHANGE ="A/C Change";
	public static final String IMVG_ON = "systemalert.imvg.up";
	public static final String IMVG_OFF = "systemalert.imvg.down";
	public static final String PW_CHANGE = "systemalert.password.changed";
 	public static final String MEMORY_QUOTA_FULL = "Memory Quota Full";
	public static final String MEMORY_QUOTA_NOTFULL = "Memory Quota 90%";
	public static final String ADD_NEW_GATEWAY = "New Gateway added to use account";
	public static final String DELETE_GATEWAY = "Gateway deleted from the user account";
	public static final String ADD_USER = "systemalert.new.user.added.to.the.account";
	public static final String DELETE_USER = "systemalert.user.deleted.from.the.account";
	public static final String SUSPEND_USER = "systemalert.user.account.suspended";
	public static final String REVOKED_USER = "User Account Revoked";
	
	public static final String ONLINE = "Online";
	public static final String OFFLINE = "Offline";

	public static final String SEND_EMAIL = "E-Mail";
	public static final String SEND_SMS = "SMS";
	public static final String EXTERNAL_FOLDER = "extenral";
	public static final String FILE_SEPARATOR = "/";
	public static final String LOCATION_FOLDER = "location";
	
	public static final int ALERTSHOWING_SIZE =5;
	public static final String DATE_FORMAT_SERVER_DISPLAY = "date_format_server_display";
	public static final String ADMINPASS = "ADMINPASS";
	public static final String NO_DEVICE = "NO_DEVICE";
//sumit start: Camera Orientation Feature
	public static final String RC80Series = "RC80";
	public static final String H264Series = "H264";
	//sumit end: Camera Orientation Feature
	public static final String Z_WAVE_AC_EXTENDER = "Z_WAVE_AC_EXTENDER";
	public static final String ZXT120 = "ZXT120";
	public static final String DB_FAILURE = "DB_FAILURE";
	public static final String DB_SUCCESS = "DB_SUCCESS";
	public static final String iMVG_FAILURE = "iMVG_FAILURE";
	public static final String NO_RESPONSE_FROM_GATEWAY = "NO_RESPONSE_FROM_GATEWAY";
	public static final String PASSWORD_CHANGED = "Password Changed";
	
	//sumit start: Internationalization for Alerts PDF
	public static final String en_US = "en_US";
	public static final int english_US = 0;
	public static final String ka_IN = "ka_IN";
	public static final int kannada_IN = 1;
	//sumit end: Internationalization for Alerts PDF
	public static final String SMS_ALERT_NOTIFIER = "SMS_ALERT_NOTIFIER";
	public static final String EMAIL_ALERT_NOTIFIER = "EMAIL_ALERT_NOTIFIER";
	public static final String SEND_SMS_EMAIL = "EmailAndSMS";
	public static final String SUPPORT_USER = "supportuser";
	public static final String REPORTPASS = "REPORTPASS";
	public static final String REPORT_UPLOAD_PATH = "report_upload_path";
	public static final String PASSWORD_EXPIRED = "Your temporary password is expired!! Please contact Customer";
	public static final String Z_WAVE_SIREN = "Z_WAVE_SIREN";
	public static final String UN_SUBSCRIBED = "Un-subscribed";
	public static final String SMS_SUBSCRIBED = "Subscribed";
	public static final String WHATSAPP = "WhatsApp";
	public static final String NOT_ENABLED = "Not enabled";
	
	public static final String CANT_ACCESS_DEVICE="CANT_ACCESS_DEVICE";
	public static final String ALERT_SERVICE = "alertservice";
	
	//ADmin superUsr
	public static final String SUPER_USER = "superuser";
}
