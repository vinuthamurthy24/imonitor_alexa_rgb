package in.xpeditions.jawlin.imonitor.controller.service;

import java.io.DataOutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.BasicConfigurator;
import org.codehaus.jettison.json.JSONObject;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.PushNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.SuperUserManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;


public class PanicRingService implements Runnable{

	
	private Customer customer;
	private String alertType;
	
	public PanicRingService(Customer customer){
		
		this.customer = customer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		JSONObject notification = new JSONObject();
		JSONObject finalResponse = new JSONObject();
		
		
		//For Android Mrthod should be changed(Fetch object based on customerId and DeviceType)
		List<PushNotification> pushNotification=getDevicesListOfCustomer(this.customer.getId());
		
		try {
			for (PushNotification notify : pushNotification) 
			{
				
				if (notify.getDeviceType().equalsIgnoreCase("Android"))
				{
					
					finalResponse.put("to", notify.getDeviceToken());
					notification.put("title","Panic");
					notification.put("body","Panic Situation!!!!");
					notification.put("sound","panic.mp3");
					notification.put("channel_id","channel_id");
					notification.put("click_action","OPEN_ACTIVITY_1");
					
				//	finalResponse.put("notification", notification);
					finalResponse.put("data", notification);
					LogUtil.info("finalResponse to mobile: "+ finalResponse);
					//Differnt application Android key feature changes start
					boolean result=updateAlertType(notify , finalResponse );
					LogUtil.info("Update result after sending :->"+result);
				}else
				{
					//IOS Block
					
					try 
					{
						BasicConfigurator.configure();
						PushNotificationPayload payload = PushNotificationPayload.complex();
						payload.addCustomAlertTitle("ALERT!!!");//Device Name
					    payload.addCustomAlertBody("PANIC SITUATION"); //Body
						payload.addBadge(1);
				        payload.addSound("panic.caf");
				        payload.addCustomDictionary("id", "1"); 
				        LogUtil.info("Payload : " + payload);
				        boolean result =  sendNotificationToIOS(payload,notify);
				         
					}
					
					catch (Exception e)
					{
						e.printStackTrace();
						LogUtil.info("Error sending IOS notification " + e.getMessage());
					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	//Get list of devices per customer from db
		public List<PushNotification> getDevicesListOfCustomer(long customerId)
		{
			DeviceManager deviceManager=new DeviceManager();
			List<PushNotification> list = deviceManager.getDevicesListOfCustomer(customerId);
			return list;
		}

		//Differnt application Android key feature changes start(3rd june)
		public boolean updateAlertType(PushNotification notify , JSONObject finalResponse) throws Exception
		{
			boolean result= false;
			XStream stream=new XStream();
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
						//LogUtil.info("Sending 'POST' request to URL : " + obj);
						HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
						con.setRequestMethod("POST");
						con.setRequestProperty("Content-Type", "application/json");
						String androidAuthkey = "";
						
						
							 androidAuthkey =ControllerProperties.getProperties().get(Constants.ANDROID_AUTH_KEY);
						
						
						con.setRequestProperty("Authorization",androidAuthkey);
						con.setDoOutput(true);
						DataOutputStream wr = new DataOutputStream(con.getOutputStream());
						wr.writeBytes(finalResponse.toString());
						wr.flush();
						wr.close();
						int responseCode = con.getResponseCode();
					//	LogUtil.info("Response Message : " + con.getResponseMessage());
						LogUtil.info("Response Code : " + responseCode);
						result = true;
						String devicetoken   = (String) finalResponse.get("to");
						
					}
					catch (Exception e) 
					{
						e.printStackTrace();
						LogUtil.info("Could not send Android Notification exception occuered: " + e.getMessage());
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
			//	LogUtil.info("sending iOS push notificatios " + payload);
			//	LogUtil.info("sending iOS push notificatios" + APNCerificate);
			//	LogUtil.info("sending iOS push notificatios" + APNPassword);
			//	LogUtil.info("sending iOS push notificatios" + pushNotification.getDeviceToken());
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
