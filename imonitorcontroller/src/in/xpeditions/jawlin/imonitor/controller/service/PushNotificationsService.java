package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.PushNotification;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.BasicConfigurator;
import org.codehaus.jettison.json.JSONObject;

import com.thoughtworks.xstream.XStream;


import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.SuperUserManager;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;
import in.xpeditions.jawlin.imonitor.util.ModbusConstants;
import in.xpeditions.jawlin.imonitor.util.PushNotificationConstants;
import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;
import net.sf.json.JSONSerializer;

public class PushNotificationsService implements Runnable
{
	
	private AlertType alertType;
	private Customer customer;
	private Device device;
	
	

	public PushNotificationsService(AlertType alertType, Customer customer , Device device) {
		
		this.alertType = alertType;
		this.customer = customer;
		this.device = device;
	}
	public PushNotificationsService(AlertType alertType, Customer customer ) {
		
		this.alertType = alertType;
		this.customer = customer;
	}


	@Override
	public void run()
	{
		String alert = alertType.getDetails();
		JSONObject notification = new JSONObject();
		JSONObject finalResponse = new JSONObject();
		PushNotificationConstants pushConstants = new PushNotificationConstants();
	
	
		//For Android Mrthod should be changed(Fetch object based on customerId and DeviceType)
		List<PushNotification> pushNotification=getDevicesListOfCustomer(this.customer.getId());
		
		
			
			String deviceName = this.device.getFriendlyName();
			
			try 
				{
				
				String pushMesssage=pushConstants.pushNoticationConstants.get(alert);
				
					for (PushNotification notify : pushNotification) 
					{
					
						if (notify.getDeviceType().equalsIgnoreCase("Android"))
						{
							
							//Android Block
							finalResponse.put("to", notify.getDeviceToken());
							
							notification.put("title",deviceName);
							notification.put("body",pushMesssage);
							notification.put("sound","default");
							notification.put("click_action","OPEN_ACTIVITY_1");
							finalResponse.put("notification", notification);
							
							boolean result=updateAlertType(finalResponse);
							
						}
						
						
						else if(notify.getDeviceType().equalsIgnoreCase("Daikin")){
							
							if (notify.getDeviceType().equalsIgnoreCase("Android"))
							{
								
								//Android Block
								finalResponse.put("to", notify.getDeviceToken());
								
								notification.put("title",deviceName);
								notification.put("body",pushMesssage);
								notification.put("sound","default");
								notification.put("click_action","OPEN_ACTIVITY_1");
								finalResponse.put("notification", notification);
								
								boolean result=updateAlertType(finalResponse);
								
							}
							else {
								
								//IOS Block
								
								try 
								{
									BasicConfigurator.configure();
									PushNotificationPayload payload = PushNotificationPayload.complex();
									payload.addCustomAlertTitle(deviceName);//Device Name
								    payload.addCustomAlertBody(pushMesssage); //Body
								//	payload.addBadge(1);
							        payload.addSound("default");
							        payload.addCustomDictionary("id", "1"); 
							          
							        boolean result =  sendNotificationToIOS(payload,notify);
							         
								}
								
								catch (Exception e)
								{
									e.printStackTrace();
									LogUtil.info("Error sending IOS notification " + e.getMessage());
								}
								
								
								
							}
							
							
						}
						else
						{
							//IOS Block
							
							try 
							{
								BasicConfigurator.configure();
								PushNotificationPayload payload = PushNotificationPayload.complex();
								payload.addCustomAlertTitle(deviceName);//Device Name
							    payload.addCustomAlertBody(pushMesssage); //Body
							//	payload.addBadge(1);
						        payload.addSound("default");
						        payload.addCustomDictionary("id", "1"); 
						          
						        boolean result =  sendNotificationToIOS(payload,notify);
						         
							}
							
							catch (Exception e)
							{
								e.printStackTrace();
								LogUtil.info("Error sending IOS notification " + e.getMessage());
							}
   
						}
					}
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
					LogUtil.info("Error : "+ e.getMessage());
				}
			//}
			////DSC alerts end
	}
	
	public PushNotification getPushNotificationObjbyCustomer(long customerId)
	{
		DeviceManager deviceManager=new DeviceManager();
		PushNotification notification=deviceManager.getPushNotificationObject(customerId);
		return notification;
	}
	
	//Get list of devices per customer from db
	public List<PushNotification> getDevicesListOfCustomer(long customerId)
	{
		DeviceManager deviceManager=new DeviceManager();
		List<PushNotification> list = deviceManager.getDevicesListOfCustomer(customerId);
		return list;
	}
	
	
	
	public boolean updateAlertType(JSONObject finalResponse) throws Exception
	{
		boolean result= false;
		
				try {
					
					 // Create a trust manager that does not validate certificate chains
			        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
			                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			                    return null;
			                }
			              
							@Override
							public void checkClientTrusted(X509Certificate[] certs, String authType)
									throws CertificateException {
								// TODO Auto-generated method stub
								
							}
							@Override
							public void checkServerTrusted(X509Certificate[] certs, String authType)
									throws CertificateException {
								// TODO Auto-generated method stub
								
							}
			            }
			        };
			 
			        // Install the all-trusting trust manager
			        SSLContext sc = SSLContext.getInstance("SSL");
			        sc.init(null, trustAllCerts, new java.security.SecureRandom());
			        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			       
			        // Create all-trusting host name verifier
			        HostnameVerifier allHostsValid = new HostnameVerifier() {
			          
						@Override
						public boolean verify(String hostname, SSLSession session) {
							// TODO Auto-generated method stub
							return false;
						}
			        };
			 
			        // Install the all-trusting host verifier
			        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

					
					//String url = ControllerProperties.getProperties().get(Constants.ANDROID_URL);
					URL obj = new URL(ControllerProperties.getProperties().get(Constants.ANDROID_URL));
					//URL obj = new URL("https://fcm.googleapis.com/fcm/send");
				
					HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "application/json");
					String androidAuthkey =ControllerProperties.getProperties().get(Constants.ANDROID_AUTH_KEY);
					//LogUtil.info("androidAuthkey : "+androidAuthkey);
					//con.setRequestProperty("Authorization","key=AIzaSyCMisULV9NBhU8Z5K4RjsHNf6KWNrPulro");
					con.setRequestProperty("Authorization",androidAuthkey);
					con.setDoOutput(true);
					
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					wr.writeBytes(finalResponse.toString());
					wr.flush();
					wr.close();
					int responseCode = con.getResponseCode();
				//	LogUtil.info("Response Code : " + responseCode);
					result = true;
				}
				catch (Exception e) 
				{
					e.printStackTrace();
					LogUtil.info("Could not send message exception occuered: " + e.getMessage());
				}
		return result;
	}
	
	public boolean sendNotificationToIOS(PushNotificationPayload payload,PushNotification pushNotification)
	{
		boolean result = false;
		String APNCerificate = ControllerProperties.getProperties().get(Constants.APN_CERTIFICATE_NAME);
		String APNPassword = ControllerProperties.getProperties().get(Constants.APN_PASSWORD);
		try
		{
			List < PushedNotification > NOTIFICATIONS = Push.payload(payload, APNCerificate, APNPassword, true, pushNotification.getDeviceToken());
			 for (PushedNotification NOTIFICATION: NOTIFICATIONS) {
	             if (NOTIFICATION.isSuccessful())
	             {
	                
	                 result=true;
	             } else
	             {
	                 String INVALIDTOKEN = NOTIFICATION.getDevice().getToken();
	                 Exception THEPROBLEM = NOTIFICATION.getException();
	                 THEPROBLEM.printStackTrace();
	                 ResponsePacket THEERRORRESPONSE = NOTIFICATION.getResponse();
	                 if (THEERRORRESPONSE != null) {
	                	 LogUtil.info("IOS notification sending error "+THEERRORRESPONSE.getMessage());
	                	 if (THEERRORRESPONSE.getMessage().equals("APNS: [1] Invalid token"))
	                	 {
	                		 SuperUserManager superUserManager = new SuperUserManager();
			            	 boolean res = superUserManager.disablePushNotificationForIOS(pushNotification.getDeviceToken());
			            	 LogUtil.info("Sending Failed , Device Token delete result = " + res);
						}
	                	
	                	 
	                 }
	             }
	         }
		} 
		catch (CommunicationException e) {
            e.printStackTrace();
            LogUtil.info("Errorrrr msg : " + e.getMessage());
        }
        catch (KeystoreException e) {
            e.printStackTrace();
            LogUtil.info("Errorrrr msg : " + e.getMessage());
        }
		catch (Exception e)
		{
			 e.printStackTrace();
			 LogUtil.info("Errorrrr msg : " + e.getMessage());
		}
		
		
		
		return result;
	}
	
	public boolean updateAlertTypeToDaikin(JSONObject finalResponse) throws Exception
	{
		boolean result= false;
		
				try {
					
					 // Create a trust manager that does not validate certificate chains
			        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
			                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			                    return null;
			                }
			              
							@Override
							public void checkClientTrusted(X509Certificate[] certs, String authType)
									throws CertificateException {
								// TODO Auto-generated method stub
								
							}
							@Override
							public void checkServerTrusted(X509Certificate[] certs, String authType)
									throws CertificateException {
								// TODO Auto-generated method stub
								
							}
			            }
			        };
			 
			        // Install the all-trusting trust manager
			        SSLContext sc = SSLContext.getInstance("SSL");
			        sc.init(null, trustAllCerts, new java.security.SecureRandom());
			        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			       
			        // Create all-trusting host name verifier
			        HostnameVerifier allHostsValid = new HostnameVerifier() {
			          
						@Override
						public boolean verify(String hostname, SSLSession session) {
							// TODO Auto-generated method stub
							return false;
						}
			        };
			 
			        // Install the all-trusting host verifier
			        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

					
					//String url = ControllerProperties.getProperties().get(Constants.ANDROID_URL);
					URL obj = new URL(ControllerProperties.getProperties().get(Constants.ANDROID_URL));
					//URL obj = new URL("https://fcm.googleapis.com/fcm/send");
				
					HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "application/json");
					String androidAuthkey =ControllerProperties.getProperties().get(Constants.ANDROID_DAIKIN_AUTH_KEY);
					//LogUtil.info("androidAuthkey : "+androidAuthkey);
					//con.setRequestProperty("Authorization","key=AIzaSyCMisULV9NBhU8Z5K4RjsHNf6KWNrPulro");
					con.setRequestProperty("Authorization",androidAuthkey);
					con.setDoOutput(true);
					
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					wr.writeBytes(finalResponse.toString());
					wr.flush();
					wr.close();
					int responseCode = con.getResponseCode();
				//	LogUtil.info("Response Code : " + responseCode);
					result = true;
				}
				catch (Exception e) 
				{
					e.printStackTrace();
					LogUtil.info("Could not send message exception occuered: " + e.getMessage());
				}
		return result;
	}
	
	public boolean sendNotificationToDaikinIOS(PushNotificationPayload payload,PushNotification pushNotification)
	{
		boolean result = false;
		String APNCerificate = ControllerProperties.getProperties().get(Constants.APN_CERTIFICATE_NAME_DAIKIN);
		String APNPassword = ControllerProperties.getProperties().get(Constants.APN_PASSWORD);
		try
		{
			List < PushedNotification > NOTIFICATIONS = Push.payload(payload, APNCerificate, APNPassword, true, pushNotification.getDeviceToken());
			 for (PushedNotification NOTIFICATION: NOTIFICATIONS) {
	             if (NOTIFICATION.isSuccessful())
	             {
	                
	                 result=true;
	             } else
	             {
	                 String INVALIDTOKEN = NOTIFICATION.getDevice().getToken();
	                 Exception THEPROBLEM = NOTIFICATION.getException();
	                 THEPROBLEM.printStackTrace();
	                 ResponsePacket THEERRORRESPONSE = NOTIFICATION.getResponse();
	                 if (THEERRORRESPONSE != null) {
	                	 LogUtil.info("IOS notification sending error "+THEERRORRESPONSE.getMessage());
	                	 if (THEERRORRESPONSE.getMessage().equals("APNS: [1] Invalid token"))
	                	 {
	                		 SuperUserManager superUserManager = new SuperUserManager();
			            	 boolean res = superUserManager.disablePushNotificationForIOS(pushNotification.getDeviceToken());
			            	 LogUtil.info("Sending Failed , Device Token delete result = " + res);
						}
	                	
	                	 
	                 }
	             }
	         }
		} 
		catch (CommunicationException e) {
            e.printStackTrace();
            LogUtil.info("Errorrrr msg : " + e.getMessage());
        }
        catch (KeystoreException e) {
            e.printStackTrace();
            LogUtil.info("Errorrrr msg : " + e.getMessage());
        }
		catch (Exception e)
		{
			 e.printStackTrace();
			 LogUtil.info("Errorrrr msg : " + e.getMessage());
		}
		
		
		
		return result;
	}
	
		
}
