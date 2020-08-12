/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.alert;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 * 
 */
public class ImvgUpAlert extends AlertUpdateHelper {

	public Queue<KeyValuePair> updateUpAlert(Queue<KeyValuePair> alerts)
			throws Exception {
		String imvgId = IMonitorUtil.commandId(alerts, Constants.IMVG_ID);
		XStream stream = new XStream();
		String xml = stream.toXML(imvgId);
		String serviceName = "gateWayService";
		String methodName = "handleImvgUp";
		//SimpleDateFormat sdf = null;
		IMonitorUtil.sendToController(serviceName, methodName, xml);
	
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue
				.add(new KeyValuePair(Constants.CMD_ID,
						Constants.DEVICE_ALERT_ACK));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil
				.commandId(alerts, Constants.TRANSACTION_ID)));
		queue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil.commandId(
				alerts, Constants.IMVG_ID)));
		//LogUtil.info("return queue: " + queue);
		return queue;
	}

	public Queue<KeyValuePair> updateIMVGUpAlert(Queue<KeyValuePair> alerts)
			throws Exception {
		
		String imvgId = IMonitorUtil.commandId(alerts, Constants.IMVG_ID);
		String imvgversion = IMonitorUtil.commandId(alerts, Constants.IMVG_VERSION);
		String userdetails = IMonitorUtil.commandId(alerts, Constants.USR_DTLS);
		//LogUtil.info("userdetails----"+userdetails);
		XStream stream = new XStream();
		
		String versionxml = stream.toXML(imvgversion);
		String xml = stream.toXML(imvgId);
		
		
		String serviceName = "gateWayService";
		String methodName = "handleImvgUp";
		
		SimpleDateFormat sdf = null;
		try {
			
			String  resultFromController = IMonitorUtil.sendToController(serviceName, methodName, xml, versionxml);
			
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.info("eeror:  "  + e.getMessage());
		}
		
		
		// the above method returns success of failure.
		// send the results according as the above result.
		
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		
		if(userdetails!=null)
		{
        	serviceName = "gateWayService";
			methodName = "GetUserDatails";
		//	LogUtil.info("Sending request to controller to get user details for gateway: " + imvgId);
			String result=IMonitorUtil.sendToController(serviceName, methodName, xml);
		//	LogUtil.info("received response for user details for the gateway: "+ imvgId +" and the response is "+ result);
			Object[] objects=(Object[]) stream.fromXML(result);
			
//			        CSTR_ID=1234
//					CSTR_NAME=surya
//					MAIN_ID=1234
//					MAIN_USR=SURYA1
//					PASSWD=Md5 generatedstring
			// by bhavya to change the IMVG_UP ack 
			
		
		queue
				.add(new KeyValuePair(Constants.CMD_ID,
						Constants.DEVICE_ALERT_ACK));
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil
				.commandId(alerts, Constants.TRANSACTION_ID)));
		queue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil.commandId(
				alerts, Constants.IMVG_ID)));
		queue.add(new KeyValuePair(Constants.CSTR_ID, ""+objects[2]));
		queue.add(new KeyValuePair(Constants.CSTR_NAME,""+objects[3]));
		queue.add(new KeyValuePair(Constants.MAIN_ID,""+objects[0]));
		queue.add(new KeyValuePair(Constants.MAIN_USR,""+objects[1]));
		queue.add(new KeyValuePair(Constants.PASSWD,""+objects[4]));
		
		}else
		{
			
			queue
					.add(new KeyValuePair(Constants.CMD_ID,
							Constants.DEVICE_ALERT_ACK));
			queue.add(new KeyValuePair(Constants.TRANSACTION_ID, IMonitorUtil
					.commandId(alerts, Constants.TRANSACTION_ID)));
			queue.add(new KeyValuePair(Constants.IMVG_ID, IMonitorUtil.commandId(
					alerts, Constants.IMVG_ID)));
		}
		
		String imvgDateString = IMonitorUtil.commandId(alerts, Constants.IMVG_TIME_STAMP);
      //  LogUtil.info("times atmp : " +imvgDateString );
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date cmsDate = new Date();
		Date imvgDate = cmsDate;
		try
		{
			imvgDate = (Date) sdf.parse(imvgDateString);
		}
		catch (Exception pe)
		{
			LogUtil.error("Could not parse the date sent by IMVG:<"+imvgDateString +">. Ignoring imvg time sync. " );
		}
		
		long maxTimeDiff = 60000;
//		try
//		{
//			String maxTimeDiffString = ImonitorControllerCommunicator.getPropertyMap().get(Constants.CONNECTION_TIMEOUT);
//			maxTimeDiff = Long.parseLong(maxTimeDiffString);
//		}catch(Exception e)
//		{
//			LogUtil.info("Could not get max allowed time difference between CMS and iMVG. Defaulting to 60000");
//		}
		
		if((cmsDate.getTime() - imvgDate.getTime()) > maxTimeDiff || (cmsDate.getTime() - imvgDate.getTime()) < -(0))
		{
			//Constants.CMS_TIME_STAMP = d.getTime();
		    queue.add(new KeyValuePair(Constants.CMS_TIME_STAMP, sdf.format(cmsDate) ));
		   // queue.add(new KeyValuePair(Constants.TIME_ZONE, "IST-5:30")); Commented on Sept 03 2014 by Naveen
		    queue.add(new KeyValuePair(Constants.TIME_ZONE, "IST"));
		}
		return queue;
	}
	
	public Queue<KeyValuePair> updateDownAlert(Queue<KeyValuePair> alerts)
			throws Exception {
		String imvgId = IMonitorUtil.commandId(alerts, Constants.IMVG_ID);
		XStream stream = new XStream();
		String xml = stream.toXML(imvgId);
		String serviceName = "gateWayService";
		String methodName = "handleImvgDown";
		IMonitorUtil.sendToController(serviceName, methodName, xml);
		return null;
	}
}
