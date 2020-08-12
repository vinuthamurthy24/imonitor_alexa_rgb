/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.util;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * @author Coladi
 *
 */
public class MessageUtil {
	public static String macId = "aa:bb:cc:dd:ee:ff";
	private static String lineSeparator = "\r\n";
	public static JTextField inputParamText;

	public static String createRegisterMessage() {
//		String message = "";
		String message = "CMD_ID=IMVG_REGISTER";
		message += MessageUtil.lineSeparator;
		String tId = generateTransactionId();
		message += "TRANSACTION_ID=";
		message += tId;
		message += MessageUtil.lineSeparator;
		message += "IMVG_ID=";
		message += MessageUtil.macId;
		String checkSum = MessageUtil.createSignature(message);
		message += MessageUtil.lineSeparator;
		message += "SIGNATURE=0x";
		message += checkSum;
		return message;
	}
	
	public static String createKeepAliveMessage() {
		String message = "";
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.IMVG_KEEP_ALIVE;
		message += MessageUtil.lineSeparator;
		String tId = generateTransactionId();
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		message += Constants.IMVG_ID;
		message += "=";
		message += MessageUtil.macId;
		String checkSum = MessageUtil.createSignature(message);
		message += MessageUtil.lineSeparator;
		message += Constants.SIGNATURE;
		message += "=";
		message += checkSum;
		return message;
	}

	public static String createSignature(String message) {
		long length = 0;
    	for (int i = 0; i < message.length(); i++) {
			length += message.charAt(i);
		}
    	String signature = Long.toHexString(length);
    	signature += MessageUtil.lineSeparator;
		return signature;
	}

	private static String generateTransactionId() {
		return Long.toHexString(System.currentTimeMillis());
	}

	public static String createDeviceDiscoveryAckMessage(Properties properties) {
		String message = "";
		String tId = properties.getProperty(Constants.TRANSACTION_ID);
		String imvgId = properties.getProperty(Constants.IMVG_ID);
		
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.DISCOVER_DEVICES_ACK;
		message += MessageUtil.lineSeparator;
		
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += imvgId;
		message += MessageUtil.lineSeparator;
		
		String signature = MessageUtil.createSignature(message);
		
		message += Constants.SIGNATURE;
		message += "=";
		message += imvgId;
		message += signature;
		
		return message;
	}
	public static String createDeviceControlSuccessMessage(Properties properties) {
		String message = "";
		String tId = properties.getProperty(Constants.TRANSACTION_ID);
		String imvgId = properties.getProperty(Constants.IMVG_ID);
		String deviceId = properties.getProperty(Constants.DEVICE_ID);
		
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.DEVICE_CONTROL_SUCCESS;
		message += MessageUtil.lineSeparator;
		
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += imvgId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_ID;
		message += "=";
		message += deviceId;
		message += MessageUtil.lineSeparator;
		
		String signature = MessageUtil.createSignature(message);
		
		message += Constants.SIGNATURE;
		message += "=";
		message += imvgId;
		message += signature;
		
		return message;
	}
	
	public static String createGetLiveStreamSuccessMessage(Properties properties) {
		String message = "";
		String tId = properties.getProperty(Constants.TRANSACTION_ID);
		String imvgId = properties.getProperty(Constants.IMVG_ID);
		String deviceId = properties.getProperty(Constants.DEVICE_ID);
		
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.IPC_GET_LIVE_STREAM_SUCCESS;
		message += MessageUtil.lineSeparator;
		
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += imvgId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_ID;
		message += "=";
		message += deviceId;
		message += MessageUtil.lineSeparator;
		
		String signature = MessageUtil.createSignature(message);
		
		message += Constants.SIGNATURE;
		message += "=";
		message += imvgId;
		message += signature;
		
		return message;
	}

	public static String createDeviceDiscoveredMessage(String deviceId, String deviceType, String model) {
		String message = "";
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.DEVICE_DISCOVERED;
		message += MessageUtil.lineSeparator;
		String tId = generateTransactionId();

		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += MessageUtil.macId;
		message += MessageUtil.lineSeparator;

		message += Constants.DEVICE_TYPE;
		message += "=";
		message += deviceType;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_ID;
		message += "=";
		message += deviceId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_MODEL;
		message += "=";
		message += model;
		message += MessageUtil.lineSeparator;
		
		String checkSum = MessageUtil.createSignature(message);
		message += Constants.SIGNATURE;
		message += "=";
		message += checkSum;
		
		Util.transactionIdDevice.put(tId, deviceId);
		
		return message;
	}
	
	public static String createDeviceAlertMessage(String deviceId, String alertEvent) {
		String message = "";
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.DEVICE_ALERT;
		message += MessageUtil.lineSeparator;
		String tId = generateTransactionId();
		
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += MessageUtil.macId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_ID;
		message += "=";
		message += deviceId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_TYPE;
		message += "=";
		message += Constants.ACTIVE;
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_EVENT;
		message += "=";
		message += alertEvent;
		message += MessageUtil.lineSeparator;
		
		String checkSum = MessageUtil.createSignature(message);
		message += Constants.SIGNATURE;
		message += "=";
		message += checkSum;
		return message;
	}
	
	public static String createDoorOpenAlertMessage(String deviceId, String alertEvent) {
		String message = "";
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.DEVICE_ALERT;
		message += MessageUtil.lineSeparator;
		String tId = generateTransactionId();
		
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += MessageUtil.macId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_ID;
		message += "=";
		message += Util.deviceGenerateDevieId.get(deviceId);
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_TYPE;
		message += "=";
		message += Constants.ACTIVE;
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_EVENT;
		message += "=";
		message += alertEvent;
		message += MessageUtil.lineSeparator;
		
		String checkSum = MessageUtil.createSignature(message);
		message += Constants.SIGNATURE;
		message += "=";
		message += checkSum;
		return message;
	}

	public static String createImvgUnRegisterMessage(Properties properties) {
		String message = "";
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.IMVG_UNREGISTER_ACK;
		message += MessageUtil.lineSeparator;

		String tId = generateTransactionId();
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += MessageUtil.macId;
		message += MessageUtil.lineSeparator;
		
		String checkSum = MessageUtil.createSignature(message);
		message += Constants.SIGNATURE;
		message += "=";
		message += checkSum;
		return message;
	}

	public static String createRemoveDeviceReturnMessage(
			String generatedDeviceId, String transactionId) {
		String message = "";
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.DEVICE_ALERT;
		message += MessageUtil.lineSeparator;
		String tId = generateTransactionId();
		
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += MessageUtil.macId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_ID;
		message += "=";
		message += generatedDeviceId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_TYPE;
		message += "=";
		message += Constants.ACTIVE;
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_EVENT;
		message += "=";
		message += Constants.DEVICE_REMOVED;
		message += MessageUtil.lineSeparator;
		
		message += Constants.REF_TRANSACTION_ID;
		message += "=";
		message += transactionId;
		message += MessageUtil.lineSeparator;
		
		String checkSum = MessageUtil.createSignature(message);
		message += Constants.SIGNATURE;
		message += "=";
		message += checkSum;
		
		return message;
	}

	public static String createGetTemperatureReturnMessage(
			String generatedDeviceId, String transactionId) {
		String message = "";
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.DEVICE_CONTROL_SUCCESS;
		message += MessageUtil.lineSeparator;
		String tId = transactionId;
		
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += MessageUtil.macId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_ID;
		message += "=";
		message += generatedDeviceId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_TYPE;
		message += "=";
		message += Constants.ACTIVE;
		message += MessageUtil.lineSeparator;
		
		message += Constants.TEMPERATURE_VALUE;
		message += "=";
		message += MessageUtil.inputParamText.getText();
		message += MessageUtil.lineSeparator;
		
		String checkSum = MessageUtil.createSignature(message);
		message += Constants.SIGNATURE;
		message += "=";
		message += checkSum;
		
		return message;
	}

	public static String createBatteryStatusMessage(String deviceId) {
		String message = "";
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.DEVICE_ALERT;
		message += MessageUtil.lineSeparator;
		String tId = generateTransactionId();
		
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += MessageUtil.macId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_ID;
		message += "=";
		message += Util.deviceGenerateDevieId.get(deviceId);
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_TYPE;
		message += "=";
		message += Constants.ACTIVE;
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_EVENT;
		message += "=";
		message += Constants.BATTERY_STATUS;
		message += MessageUtil.lineSeparator;
		
		message += Constants.BATTERY_LEVEL;
		message += "=";
		message += MessageUtil.inputParamText.getText();
		message += MessageUtil.lineSeparator;
		
		String checkSum = MessageUtil.createSignature(message);
		message += Constants.SIGNATURE;
		message += "=";
		message += checkSum;
		return message;
	}

	public static String createCaptureImageAckMessage(Properties properties) {
		String message = "";
		String tId = properties.getProperty(Constants.TRANSACTION_ID);
		String imvgId = properties.getProperty(Constants.IMVG_ID);
		String deviceId = properties.getProperty(Constants.DEVICE_ID);
		
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.IPC_CAPTURE_IMAGE_ACK;
		message += MessageUtil.lineSeparator;
		
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += imvgId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_ID;
		message += "=";
		message += deviceId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.STATUS;
		message += "=";
		message += Constants.IMAGE_CAPTURE_INITIATED;
		message += MessageUtil.lineSeparator;
		
		String signature = MessageUtil.createSignature(message);
		
		message += Constants.SIGNATURE;
		message += "=";
		message += imvgId;
		message += signature;
		
		return message;
	}

	public static String uploadImageAndCreateFtpImageSuccess(
			Properties properties) throws AWTException, IOException {
		Robot robot = new Robot();
		Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	    BufferedImage bufferedImage = robot.createScreenCapture(captureSize);
	    String lFileName = "C:\\delete\\" + System.currentTimeMillis() + ".jpeg";
		File lFile = new File(lFileName);
		ImageIO.write(bufferedImage, "jpeg", lFile);
	    
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("192.168.2.26", 21);
		
		boolean login = ftpClient.login("walker", "rose@123");
		
		if(login){
			InputStream local = new FileInputStream(lFile);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			String rFileName = properties.getProperty(Constants.FTP_PATH_FILE_NAME);
			rFileName += ".jpeg";
			ftpClient.storeFile(rFileName , local );
			ftpClient.disconnect();
		}
//		Create the message.
		String message = "";
		message += Constants.CMD_ID;
		message += "=";
		message += Constants.DEVICE_ALERT;
		message += MessageUtil.lineSeparator;
		String tId = generateTransactionId();
		
		message += Constants.TRANSACTION_ID;
		message += "=";
		message += tId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.IMVG_ID;
		message += "=";
		message += MessageUtil.macId;
		message += MessageUtil.lineSeparator;
		
		message += Constants.DEVICE_ID;
		message += "=";
		String deviceId = properties.getProperty(Constants.DEVICE_ID);
		message += deviceId ;
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_TYPE;
		message += "=";
		message += Constants.ACTIVE;
		message += MessageUtil.lineSeparator;
		
		message += Constants.ALERT_EVENT;
		message += "=";
		message += Constants.IMAGE_FTP_SUCCESS;
		message += MessageUtil.lineSeparator;
		
		message += Constants.REF_TRANSACTION_ID;
		message += "=";
		String refTransactionId = properties.getProperty(Constants.TRANSACTION_ID);
		message += refTransactionId ;
		message += MessageUtil.lineSeparator;
		
		String checkSum = MessageUtil.createSignature(message);
		message += Constants.SIGNATURE;
		message += "=";
		message += checkSum;
		return message;
	}

}
