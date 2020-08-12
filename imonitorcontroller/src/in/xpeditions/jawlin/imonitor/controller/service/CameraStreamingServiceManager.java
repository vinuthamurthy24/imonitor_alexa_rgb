/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @author Coladi
 *
 */
public class CameraStreamingServiceManager {

	public String getCameraDetailsByUrl(String url){
//		Object idObj = ImonitorCameraUrl.getDeviceIdByUrl(url);
//		if(null == idObj){
//			return Constants.FAILURE;
//		}
//		return idObj.toString();
		return null;
	}
	
	public String liveStreamStopped(String deviceId) throws NoSuchAlgorithmException, ParserConfigurationException, SAXException, IOException{
//		ImonitorCameraUrl.remove(deviceId);
		return "success";
	}
}
