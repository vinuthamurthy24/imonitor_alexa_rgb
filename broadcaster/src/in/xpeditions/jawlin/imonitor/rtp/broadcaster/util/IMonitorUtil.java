/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.util;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Random;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.communicator.ImonitorControllerCommunicator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

/**
 * @author Coladi
 *
 */
public class IMonitorUtil {

	
	public static String sendToController(String xmlString, String serviceName,String method) throws ServiceException, MalformedURLException, RemoteException {
		String address = ImonitorControllerCommunicator.get(Constants.CONTROLLER_IP);
		String port = ImonitorControllerCommunicator.get(Constants.CONTROLLER_PORT);
		String protocol = ImonitorControllerCommunicator.get(Constants.CONTROLLER_PROTOCOL);
		String endpoint = protocol + "://" + address + ":" + port
		+ "/imonitorcontroller/services/"+serviceName;
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName(method));
		String result = (String) call.invoke(new Object[] {xmlString});
		return result;
	}
	public static String sendToController(String serviceName,String method) throws ServiceException, MalformedURLException, RemoteException {
		String address = ImonitorControllerCommunicator.get(Constants.CONTROLLER_IP);
		String port = ImonitorControllerCommunicator.get(Constants.CONTROLLER_PORT);
		String protocol = ImonitorControllerCommunicator.get(Constants.CONTROLLER_PROTOCOL);
		String endpoint = protocol + "://" + address + ":" + port
		+ "/imonitorcontroller/services/"+serviceName;
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName(method));
		String deviceXml = (String) call.invoke(new Object[] {});
		return deviceXml;
	}
	public static String createRtspUrlStreamId() {
		Random generator = new Random();
		int random = generator.nextInt(10000);
		String rtspUrlSessionId = Long.toHexString(System.currentTimeMillis()) + Integer.toHexString(random);
		return rtspUrlSessionId+".mp4";//vibhu temp change for kritnu
	}
	public static String createRtspUrl(String host, String port,
			String streamUrlId) {
		if(streamUrlId == null){
			return null;
		}
		String rtspUrl = "rtsp://" + host + ":" + port + "/" + streamUrlId;
		LogUtil.info("rtspUrl : "+rtspUrl);
		return rtspUrl;
	}
	public static String extractRtspUrlId(String applicationUrl) {
		int lastIndexOf = applicationUrl.lastIndexOf("/");
		String rtspUrlId = applicationUrl.substring(lastIndexOf + 1);
		return rtspUrlId;
	}
}
