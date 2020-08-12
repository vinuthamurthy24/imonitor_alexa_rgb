/* Copyright  2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.notifications;


import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerServices;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsReport;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsService;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ServerConfigurationManger;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.controller.service.ServerConfigurationSevice;
import in.xpeditions.jawlin.imonitor.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class SmsNotifications {

	// ********************************************* sumit start ***********************************************
	
	private SmsService smsservie;
	
	public SmsNotifications(){
		
		
		ServerConfigurationSevice serverconfiguration=new ServerConfigurationSevice();
		String Result = null;
		try {
			Result = serverconfiguration.getSelectedSMSServiceProvider();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(Result != null)
		this.smsservie=(SmsService) new XStream().fromXML(Result);
		else
		LogUtil.info(Result+"IS NULL");
	}
	
	/**
	 * 
	 * Calls appropriate API for sending SMS[s] based on smsNotificationVendor Name.
	 * @param message java.lang.String
	 * @param recipients java.lang.String[]
	 * @param date1 java.util.Date
	 * @return java.lang.String
	 */
	public String notify(String message, String[] recipients, Date date1,Customer customer){
		ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
		
		
		CustomerManager customerManager = new CustomerManager();
	     CustomerServices customerservices = customerManager.getCustomerServicesByCustomerId(customer.getId());
	     int serviceEnabled = customerservices.getServiceEnabled();
	     
	     
			for (int i = 0; i < recipients.length; i++) {
			try {
				if(date1 == null)
				{
					date1 = new Date();
					
					
				}
				
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a");
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-yyyy");
				String time = timeFormat.format(date1);
				String date = dateFormat.format(date1);
				Calendar cal = Calendar.getInstance();
			    String month = new SimpleDateFormat("MMM").format(cal.getTime());
			    //LogUtil.info("timeeee:"+ time);
				
			   
				if(smsservie != null)
				{
					
					if(this.smsservie.getOperatorcode().equals("efl"))
					{
						String requestUrl = ControllerProperties.getProperties().get(Constants.SMS_ALERT_NOTIFIER_ADDRESS)
								+ "__key=" + URLEncoder.encode(smsservie.getUsername(), "UTF-8")
								+ "&__pass="+ URLEncoder.encode(smsservie.getPassword(), "UTF-8")
								+ "&mobile="+ URLEncoder.encode(recipients[i], "UTF-8")
								+ "&message="+ URLEncoder.encode(message+" at "+time+" IST on "+ month+" "+ date,"UTF-8");
					
						//LogUtil.info("request Url :"+ requestUrl);
						URL url = new URL(requestUrl);
						URLConnection urlConnection = url.openConnection();
						urlConnection.setDoInput(true);
						InputStream inStream = urlConnection.getInputStream();
			            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
			            String line = "";
			            while ((line = input.readLine()) != null){
			            	LogUtil.info("Response:"+line);
			            }
			            
			            input.close(); // Naveen added to avoid resource leak
		            }
						
					else if(this.smsservie.getOperatorcode().equalsIgnoreCase("ims"))
					{
						
						 if((serviceEnabled & 1) != 1)
								
							{
							
						String requestUrl = ControllerProperties.getProperties().get(Constants.SMS_ALERT_NOTIFIER_ADDRESS_1)
							+ "uname=" + URLEncoder.encode(smsservie.getUsername(), "UTF-8")
							+ "&pass="+ URLEncoder.encode(smsservie.getPassword(), "UTF-8")
							+ "&send="+ URLEncoder.encode("homeqi", "UTF-8")
							+ "&dest="+ URLEncoder.encode(recipients[i], "UTF-8")
							+ "&msg="+ URLEncoder.encode(message+" at "+time+ " IST on "+month+" "+ date,"UTF-8");
					
					//	LogUtil.info(requestUrl);
					
						URL url = new URL(requestUrl);
						URLConnection urlConnection = url.openConnection();
						urlConnection.setDoInput(true);
						InputStream inStream = urlConnection.getInputStream();
			            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
			            String line = "";
			            SmsReport smsreport=new SmsReport();
			            while ((line = input.readLine()) != null)
			            {
			            	LogUtil.info("Response:"+line);
			            	smsreport.setSessionId(line);
			            	
			            	
			            }
			            
		            	input.close(); // naveen added to avoid potential leak
		            	smsreport.setMobileNumber(recipients[i]);
		            	smsreport.setSubmittedTime(new Date());
		            	smsreport.setCustomer(customer);
		            	serverconfigurationmanger.saveSessionId(smsreport);
		            	
		            	// To get the sms count per customer and mobile number
		               	long count = serverconfigurationmanger.getTotalSmsCountPerCustomer(recipients[i],customer);
		               	customer = customerManager.getCustomerById(customer.getId());
		               	long threshold=customer.getSmsThreshold();
		              
		               
		               	
		            	if((count > threshold) && ((count == 51) || (count == 70) || (count==90) || (count==110) || (count==130) || (count==150)||(count==200)||(count==250)||(count==300)||(count==350)||(count==400)||(count==450)||(count==500)) ){
		            		
		            		
		            		
		            		
		            		String alertMessage = "You have exceeded alloted SMS quota for this week. "+ "<br><br>"+"Customer Name:  "+customer.getCustomerId()+ "<br><br>"+ "MobileNumer:  "+recipients[i]+ "<br><br>" +"Maximum SMS Limit: " + threshold + "<br><br>" +"Used SMS: " + count + 
		            			                   "  ";
		            		
		            		String[] recipiantsBYmail={"support@imonitorsolutions.com"};
		        
		            		EMailNotifications email=new EMailNotifications();
		            		//email.notify2(alertMessage,recipiantsBYmail,null,customer.getCustomerId());	
		            		
		            	}// end if statement
					
					}
						/***************Orginal code*************/
		            }
					/* ******************************************** ORIGINAL CODE **********************************************
					 * String urlS = "http://enterprise.smsgupshup.com/GatewayAPI/rest?" +
					"method=sendMessage&auth_scheme=PLAIN&userid=2000033167&password=eureka&msg_type=TEXT&v=1.1&mask=Eurosmil" +
					"&send_to=" + recipients[i] +
					"&msg=" + URLEncoder.encode(message+"</br></br>"+date, "UTF-8");
					URL url;
					url = new URL(urlS);
					URLConnection connection = url.openConnection();
					connection.setDoInput(true);
		            InputStream inStream = connection.getInputStream();
		            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
		    
		            String line = "";
		            while ((line = input.readLine()) != null){
		            	LogUtil.info(line);
		            }
		            ************************************************** sumit end **********************************************
		            */
										
				}
				
			 }catch (MalformedURLException e) {
					e.printStackTrace();
					LogUtil.info("1.MalformedURLException Caught: ", e);
				} catch (IOException e) {
					e.printStackTrace();
					LogUtil.info("2.IOException Caught: ", e);
				} catch (Exception e) {
					LogUtil.info("3.Exception Caught", e);
				}
			} 
			
			
			
	
				
				
		return null;
	
	}

	public SmsService getSmsservie() {
		return smsservie;
	}

	public void setSmsservie(SmsService smsservie) {
		this.smsservie = smsservie;
	}
	
	
	public String notifyforschedule(String message, String[] sms,  Date dateOfAlert,Customer customer) {
		
		CustomerManager customerManager = new CustomerManager();
	    CustomerServices customerservices = customerManager.getCustomerServicesByCustomerId(customer.getId());
	     int serviceEnabled = customerservices.getServiceEnabled();
		ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
		
		for (int i = 0; i < sms.length; i++) {
		try {
			if(dateOfAlert == null)
			{
				dateOfAlert = new Date();
			}
			
			if((serviceEnabled & 1) != 1)
				
			{
			if(smsservie != null)
			{
				if(this.smsservie.getOperatorcode().equals("efl"))
				{
					String requestUrl = ControllerProperties.getProperties().get(Constants.SMS_ALERT_NOTIFIER_ADDRESS)
							+ "__key=" + URLEncoder.encode(smsservie.getUsername(), "UTF-8")
							+ "&__pass="+ URLEncoder.encode(smsservie.getPassword(), "UTF-8")
							+ "&mobile="+ URLEncoder.encode(sms[i], "UTF-8")
							+ "&message="+ URLEncoder.encode(message+" at "+dateOfAlert,"UTF-8");
					
					
					URL url = new URL(requestUrl);
					URLConnection urlConnection = url.openConnection();
					urlConnection.setDoInput(true);
					InputStream inStream = urlConnection.getInputStream();
		            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
		            String line = "";
		            while ((line = input.readLine()) != null){
		            	LogUtil.info("Response:"+line);
		            }
		            input.close(); // Naveen added 
	            }
				else if(this.smsservie.getOperatorcode().equalsIgnoreCase("ims"))
				{
					String requestUrl = ControllerProperties.getProperties().get(Constants.SMS_ALERT_NOTIFIER_ADDRESS_1)
						+ "uname=" + URLEncoder.encode(smsservie.getUsername(), "UTF-8")
						+ "&pass="+ URLEncoder.encode(smsservie.getPassword(), "UTF-8")
						+ "&send="+ URLEncoder.encode("homeqi", "UTF-8")
						+ "&dest="+ URLEncoder.encode(sms[i], "UTF-8")
						+ "&msg="+ URLEncoder.encode(message+" at "+dateOfAlert,"UTF-8");
				
					//LogUtil.info(requestUrl);
				
					URL url = new URL(requestUrl);
					URLConnection urlConnection = url.openConnection();
					urlConnection.setDoInput(true);
					InputStream inStream = urlConnection.getInputStream();
		            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
		            String line = "";
		            SmsReport smsreport=new SmsReport();
		            while ((line = input.readLine()) != null)
		            {
		            	
		            	smsreport.setSessionId(line);
		            	
		            	
		            }
		           
	            	smsreport.setCustomer(customer);
	            	smsreport.setMobileNumber(sms[i]);
	            	smsreport.setSubmittedTime(new Date());
	            	serverconfigurationmanger.saveSessionId(smsreport);
	            	// To get the sms count per customer and mobile number
	            	
	            	long count = serverconfigurationmanger.getTotalSmsCountPerCustomer(sms[i],customer);
	             	customer = customerManager.getCustomerById(customer.getId());
	            	long threshold=customer.getSmsThreshold();
	            	
	            		if((count > threshold) && ((count == 51) || (count == 70) || (count==90) || (count==110) || (count==130) || (count==150)||(count==200)||(count==250)||(count==300)||(count==350)||(count==400)||(count==450)||(count==500)) ){
	            		         		
	            		
	            		
	            		String alertMessage = "You have exceeded alloted SMS quota for this week. "+ "<br><br>"+"Customer Name:  "+customer.getCustomerId()+ "<br><br>"+ "MobileNumer:  "+sms[i]+ "<br><br>" +"Maximum SMS Limit: " + threshold + "<br><br>" +"Used SMS: " + count + 
 			                   "  ";
	            		
	            		String[] recipiantsBYmail={"support@imonitorsolutions.com"};
	    		        
	            		EMailNotifications email=new EMailNotifications();
	            		//email.notify2(alertMessage,recipiantsBYmail,null,customer.getCustomerId());
	            	}
	            	
	            }
			
			}
			
		} }catch (MalformedURLException e) {
				e.printStackTrace();
				LogUtil.info("1.MalformedURLException Caught: ", e);
			} catch (IOException e) {
				e.printStackTrace();
				LogUtil.info("2.IOException Caught: ", e);
			} catch (Exception e) {
				LogUtil.info("3.Exception Caught", e);
			}
		} 
		
		
		return null;
	}
	
	
	/*Function for sending message to whatsapp number..
	 * Implemented on April 2014
	 * Author: Naveen M S
	 */
public String notifyForWhatsApp(String message, String[] sms,  Date dateOfAlert) {
	CustomerServices customerservices=new CustomerServices();
    int serviceEnabled = customerservices.getServiceEnabled();
		
		for (int i = 0; i < sms.length; i++) {
		try {
			if(dateOfAlert == null)
			{
				dateOfAlert = new Date();
			}
			
			if((serviceEnabled & 8) != 8)
				
			{
						
			if(smsservie != null)
			{
				if(this.smsservie.getOperatorcode().equals("efl"))
				{
					
					String phoneNumber = sms[i];
				   
					SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
					SimpleDateFormat sdfDay = new SimpleDateFormat("MM-dd-yyyy");
				    Date now = new Date();
				    String strDate = sdfTime.format(now);
					String strDay = sdfDay.format(now);
		   	  	ProcessBuilder pb = new ProcessBuilder("./directory.sh",sms[i],message,strDate, strDay);
	   	    	pb.start();
	   	    
				
	            }
				else if(this.smsservie.getOperatorcode().equalsIgnoreCase("ims"))
				{		   	    	
		   	       	// Runtime.getRuntime().exec("./directory.sh "+sms[i]+"" +"\"" + message + "\"");
					/*if(sms[i].contains("+")){
						
						sms[i].replaceFirst("+", null);
						sms[i]= "91"+sms[i];
					}*/
					String phoneNumber = sms[i];
				   
				//	String executedDate = String.valueOf(dateOfAlert);
					SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
					SimpleDateFormat sdfDay = new SimpleDateFormat("MM-dd-yyyy");
				    Date now = new Date();
				    String strDate = sdfTime.format(now);
					String strDay = sdfDay.format(now);
		   	  	ProcessBuilder pb = new ProcessBuilder("./directory.sh",sms[i],message,strDate, strDay);
	   	    	pb.start();
	   	    
		   	    	 //end
	            }
			
			}
			
		} }catch (MalformedURLException e) {
				e.printStackTrace();
				LogUtil.info("1.MalformedURLException Caught: ", e);
			} catch (IOException e) {
				e.printStackTrace();
				LogUtil.info("2.IOException Caught: ", e);
			} catch (Exception e) {
				LogUtil.info("3.Exception Caught", e);
			}
		} 
		
		
		return null;
	}



public String notifywithTempPassword(String message, String recipient,Customer customer){
	ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
	CustomerManager customerManager = new CustomerManager();
	CustomerServices customerservices=new CustomerServices();
     int serviceEnabled = customerservices.getServiceEnabled();
	try {
		if((serviceEnabled & 1) != 1)
			
		{
		
		if(smsservie != null)
		{
			if(this.smsservie.getOperatorcode().equals("efl"))
			{
				String requestUrl = ControllerProperties.getProperties().get(Constants.SMS_ALERT_NOTIFIER_ADDRESS)
						+ "__key=" + URLEncoder.encode(smsservie.getUsername(), "UTF-8")
						+ "&__pass="+ URLEncoder.encode(smsservie.getPassword(), "UTF-8")
						+ "&mobile="+ URLEncoder.encode(recipient, "UTF-8")
						+ "&message="+ URLEncoder.encode(message,"UTF-8");
				
			
				URL url = new URL(requestUrl);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setDoInput(true);
				InputStream inStream = urlConnection.getInputStream();
	            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
	            String line = "";
	            while ((line = input.readLine()) != null){
	            	LogUtil.info("Response:"+line);
	            }
	            input.close(); //Naveen added
            }
			else if(this.smsservie.getOperatorcode().equalsIgnoreCase("ims"))
			{
				String requestUrl = ControllerProperties.getProperties().get(Constants.SMS_ALERT_NOTIFIER_ADDRESS_1)
					+ "uname=" + URLEncoder.encode(smsservie.getUsername(), "UTF-8")
					+ "&pass="+ URLEncoder.encode(smsservie.getPassword(), "UTF-8")
					+ "&send="+ URLEncoder.encode("homeqi", "UTF-8")
					+ "&dest="+ URLEncoder.encode(recipient, "UTF-8")
					+ "&msg="+ URLEncoder.encode(message,"UTF-8");
			
			
			
				URL url = new URL(requestUrl);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setDoInput(true);
				InputStream inStream = urlConnection.getInputStream();
	            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
	            String line = "";
	            SmsReport smsreport=new SmsReport();
	            while ((line = input.readLine()) != null)
	            {
	            	LogUtil.info("Response:"+line);
	            	smsreport.setSessionId(line);
	            	
	            	
	            }
	            
            	input.close();
            	smsreport.setMobileNumber(recipient);
            	smsreport.setSubmittedTime(new Date());
            	serverconfigurationmanger.saveSessionId(smsreport);
            	
            	// To get the sms count per customer and mobile number
               	long count = serverconfigurationmanger.getTotalSmsCountPerCustomer(recipient,customer);
               	customer = customerManager.getCustomerById(customer.getId());
               	long threshold=customer.getSmsThreshold();
               	
               		if((count > threshold) && ((count == 51) || (count == 70) || (count==90) || (count==110) || (count==130) || (count==150)||(count==200)||(count==250)||(count==300)||(count==350)||(count==400)||(count==450)||(count==500)) ){
            		
            		
            		
            		String alertMessage = "You have exceeded alloted SMS quota for this week. "+ "<br><br>"+"Customer Name:  "+customer.getCustomerId()+ "<br><br>"+ "MobileNumer:  "+recipient+ "<br><br>" +"Maximum SMS Limit: " + threshold + "<br><br>" +"Used SMS: " + count + 
			                   "  ";
            		
            		String[] recipiantsBYmail={"support@imonitorsolutions.com"};
        
            		EMailNotifications email=new EMailNotifications();
            		//email.notify2(alertMessage,recipiantsBYmail,null,customer.getCustomerId());
            		
            	}
		
		
	} }}}catch (MalformedURLException e) {
		e.printStackTrace();
		LogUtil.info("1.MalformedURLException Caught: ", e);
	}	catch (IOException e) {
		e.printStackTrace();
		LogUtil.info("2.IOException Caught: ", e);
	}	
			
			
	return null;
}

public String notifywithOTP(String message, String recipient, Customer customer){
	ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
	CustomerManager customerManager = new CustomerManager();
	 CustomerServices customerservices = customerManager.getCustomerServicesByCustomerId(customer.getId());
    int serviceEnabled = customerservices.getServiceEnabled();
	try {
		if((serviceEnabled & 1) != 1)
			
		{
		
		if(smsservie != null)
		{
			if(this.smsservie.getOperatorcode().equals("efl"))
			{
				String requestUrl = ControllerProperties.getProperties().get(Constants.SMS_ALERT_NOTIFIER_ADDRESS)
						+ "__key=" + URLEncoder.encode(smsservie.getUsername(), "UTF-8")
						+ "&__pass="+ URLEncoder.encode(smsservie.getPassword(), "UTF-8")
						+ "&mobile="+ URLEncoder.encode(recipient, "UTF-8")
						+ "&message="+ URLEncoder.encode(message,"UTF-8");
				
			
				URL url = new URL(requestUrl);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setDoInput(true);
				InputStream inStream = urlConnection.getInputStream();
	            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
	            String line = "";
	            while ((line = input.readLine()) != null){
	            	LogUtil.info("Response:"+line);
	            }
	            input.close(); //Naveen added
            }
			else if(this.smsservie.getOperatorcode().equalsIgnoreCase("ims"))
			{
				String requestUrl = ControllerProperties.getProperties().get(Constants.SMS_ALERT_NOTIFIER_ADDRESS_1)
					+ "uname=" + URLEncoder.encode(smsservie.getUsername(), "UTF-8")
					+ "&pass="+ URLEncoder.encode(smsservie.getPassword(), "UTF-8")
					+ "&send="+ URLEncoder.encode("homeqi", "UTF-8")
					+ "&dest="+ URLEncoder.encode(recipient, "UTF-8")
					+ "&msg="+ URLEncoder.encode(message,"UTF-8");
			
			
			
				URL url = new URL(requestUrl);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setDoInput(true);
				InputStream inStream = urlConnection.getInputStream();
	            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
	            String line = "";
	            SmsReport smsreport=new SmsReport();
	            while ((line = input.readLine()) != null)
	            {
	            	
	            	smsreport.setSessionId(line);
	            	smsreport.setCustomer(customer);
	            	
	            }
	            
            	input.close();
            	smsreport.setMobileNumber(recipient);
            	smsreport.setSubmittedTime(new Date());
            	serverconfigurationmanger.saveSessionId(smsreport);
            	// To get the sms count per customer and mobile number
               	long count = serverconfigurationmanger.getTotalSmsCountPerCustomer(recipient,customer);
               	customer = customerManager.getCustomerById(customer.getId());
               	
               	long threshold=customer.getSmsThreshold();
               	
               		if((count > threshold) && ((count == 51) || (count == 70) || (count==90) || (count==110) || (count==130) || (count==150)||(count==200)||(count==250)||(count==300)||(count==350)||(count==400)||(count==450)||(count==500)) ){
            		
            		
            		
            		
            		
            		String alertMessage = "You have exceeded alloted SMS quota for this week. "+ "<br><br>"+"Customer Name:  "+customer.getCustomerId()+ "<br><br>"+ "MobileNumer:  "+recipient+ "<br><br>" +"Maximum SMS Limit: " + threshold + "<br><br>" +"Used SMS: " + count + 
			                   "  ";
            		
            		String[] recipiantsBYmail={"support@imonitorsolutions.com"};    		
            		EMailNotifications email=new EMailNotifications();
            	//	email.notify2(alertMessage,recipiantsBYmail,null,customer.getCustomerId());
            		
            	}
            	
            	
	}}}}catch (MalformedURLException e) {
		e.printStackTrace();
		LogUtil.info("1.MalformedURLException Caught: ", e);
	}	catch (IOException e) {
		e.printStackTrace();
		LogUtil.info("2.IOException Caught: ", e);
	}	
	
	
			
			
		
		
		
		
		

			
			
	return null;

}


//Self Registration changes
public String notifywithOTPForSelfRegistration(String message, String recipient){

	try {
				/*String requestUrl = ControllerProperties.getProperties().get(Constants.SMS_ALERT_NOTIFIER_ADDRESS)
						+ "__key=" + URLEncoder.encode(smsservie.getUsername(), "UTF-8")
						+ "&__pass="+ URLEncoder.encode(smsservie.getPassword(), "UTF-8")
						+ "&mobile="+ URLEncoder.encode(recipient, "UTF-8")
						+ "&message="+ URLEncoder.encode(message,"UTF-8");*/
				
				String requestUrl = ControllerProperties.getProperties().get(Constants.SMS_ALERT_NOTIFIER_ADDRESS_1)
						+ "uname=" + URLEncoder.encode(smsservie.getUsername(), "UTF-8")
						+ "&pass="+ URLEncoder.encode(smsservie.getPassword(), "UTF-8")
						+ "&send="+ URLEncoder.encode("homeqi", "UTF-8")
						+ "&dest="+ URLEncoder.encode(recipient, "UTF-8")
						+ "&msg="+ URLEncoder.encode(message,"UTF-8");
				
			
				URL url = new URL(requestUrl);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setDoInput(true);
				InputStream inStream = urlConnection.getInputStream();
	            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
	            String line = "";
	            while ((line = input.readLine()) != null){
	            	LogUtil.info("Response:"+line);
	            }
	            input.close(); //Naveen added
 
		}
	catch (MalformedURLException e) 
	{
		e.printStackTrace();
		LogUtil.info("1.MalformedURLException Caught: ", e);
	}	catch (IOException e) {
		e.printStackTrace();
		LogUtil.info("2.IOException Caught: ", e);
	}	
	
	return null;

}
	
}
