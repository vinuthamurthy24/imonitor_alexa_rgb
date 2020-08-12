/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.notifications;


//import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.SMTPAuthenticator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


//import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeMultipart;
/**
 * @author Coladi
 * 
 */
public class EMailNotifications {
	/*
	 * public String notify(String message, String[] recipients) {
		return notifyWithAttchment(message, recipients, null);
	}*/

	private String emailNotificationVendor = null;
	
	public EMailNotifications(){
		if(emailNotificationVendor == null){
			emailNotificationVendor = ControllerProperties.getProperties().get(Constants.EMAIL_ALERT_NOTIFIER);
			
		}
	}
	
	/**
	 * 
	 * Calls appropriate API[s] for sending out email notifications based on emailNotificationVendor Name.
	 * @param message java.lang.String
	 * @param recipients java.lang.String[]
	 * @param dateOfAlert java.util.Date
	 * @return java.lang.String
	 */
	public String notify(String message, String[] recipients, Date dateOfAlert,String userName) throws MessagingException {
		 boolean debug = false;
		 Date date = null;
	     //Set the host smtp address
	     Properties props = new Properties();
	     /*
	     props.put("mail.smtp.host",ControllerProperties.getProperties().get(Constants.MAIL_SMTP_HOST));
	     props.put("mail.smtp.starttls.enable", ControllerProperties.getProperties().get(Constants.MAIL_SMTP_STARTTLS_ENABLE));
	     props.put("mail.smtp.auth", ControllerProperties.getProperties().get(Constants.MAIL_SMTP_AUTH));
	     props.put("mail.smtp.port",ControllerProperties.getProperties().get(Constants.MAIL_SMTP_PORT));
	     */

	   /*  CustomerManager customerManager = new CustomerManager();
	     UserManager userManager=new UserManager();
	     User user = userManager.getUserByUserId(userName);
	     CustomerServices customerservices = customerManager.getCustomerServicesByCustomerId(user.getCustomer().getId());
	     
	     int serviceEnabled = customerservices.getServiceEnabled();*/
	         
	     
	     if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER))
	     {
	    	 /*if(((serviceEnabled & 2) != 2) || (customerservices == null) || (customerservices.equals(null)))
				{*/
					
	    		
	     String strProtocol = "smtp", strHost = "smtp.gmail.com", strPort = "587", strFrom = "myimonitor@gmail.com", strPassword ="imsipl@india";
	     
	     props.put("mail.smtp.host", strHost);
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtps.auth", "true");
	     props.put("mail.smtp.port", strPort);
	     props.put("mail.transport.protocol", strProtocol);	     
		 props.put("mail.smtp.ssl.trust","*");
	     try{
	    	 if(dateOfAlert == null){
	    		 date = new Date();
	    		
	    	 }else{
	    		 date = dateOfAlert;
	    		
	    	 }
	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
	    	 String sDate = sdf.format(date);
	    	
	    	 Authenticator auth = new SMTPAuthenticator();
	    	 Session session = Session.getDefaultInstance(props, auth);
	    	 session.setDebug(debug);
	    	 // create a message
	    	 Message msg = new MimeMessage(session);
	    	 // set the from and to address
	    	 //InternetAddress addressFrom = new InternetAddress(ControllerProperties.getProperties().get(Constants.INTERNET_ADDRESS));
	    	 InternetAddress addressFrom = new InternetAddress("my_imonitor");
	    	 msg.setFrom(addressFrom);
	    	
	    	 InternetAddress[] addressTo = new InternetAddress[recipients.length];
	    	 for (int i = 0; i < recipients.length; i++){
	    		 addressTo[i] = new InternetAddress(recipients[i]);
	    		
	    	
	    	 }
	    	 msg.setRecipients(Message.RecipientType.TO, addressTo);
	    	 // Setting the Subject and Content Type
	    	 msg.setSubject("From Imonitor");
	    	 msg.setContent("Dear "+userName+",<br><br>"+message+" ", "text/html");
	    	 msg.setHeader("X-Mailer","msgsend");
	    	 Transport tr = session.getTransport(strProtocol);
	    	 tr.connect(strHost, Integer.valueOf(strPort), strFrom, strPassword);
	    	 tr.sendMessage(msg, msg.getAllRecipients());
	    
	     }catch (Exception e) {
	    	 e.printStackTrace();
		    	 LogUtil.info("Error while sending email notification.", e);
		}
	     }
	    else if(emailNotificationVendor.equalsIgnoreCase(Constants.EFL_EMAIL_NOTIFIER))
	    {
	    	String key = "efl";
			String password = "efl321";
			for (int i = 0; i < recipients.length; i++) 
			{
				
				String requestUrl;
				try {
					requestUrl = ControllerProperties.getProperties().get(Constants.EMAIL_ALERT_NOTIFIER_ADDRESS)
							+ "__key=" + URLEncoder.encode(key, "UTF-8")
							+ "&__pass="+ URLEncoder.encode(password, "UTF-8")
							+ "&mail="+ URLEncoder.encode(recipients[i], "UTF-8")
							+ "&subject="+ URLEncoder.encode("Alert from your EuroVigil account", "UTF-8")
							+ "&cust_name="+ URLEncoder.encode(userName, "UTF-8")
							+ "&alertmsg="+URLEncoder.encode(message,"UTF-8");
							 if(dateOfAlert == null){
					    		 date = new Date();
					    	 }else{
					    		 date = dateOfAlert;
					    	 }
							requestUrl+="&time_stamp="+ URLEncoder.encode(""+date, "UTF-8");
					
					URL url = new URL(requestUrl);
					URLConnection urlConnection = url.openConnection();
					//urlConnection.setDoInput(true);
					InputStream inStream = urlConnection.getInputStream();
		            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
		            String line = "";
		            while ((line = input.readLine()) != null){
		            	LogUtil.info(line);
		            }
		            
		            input.close(); // Naveen added to avoid potential resource leak
				
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				} catch (MalformedURLException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
	    }
			}
	     
	     //for different email vendor append else block to check with the emailNotificationVendor Name.
	     return null;
	} 
	
	
	//sumit satrt: send image attachment
	/**
	 * 
	 * Calls appropriate API[s] for sending out email notifications with attachments based on emailNotificationVendor Name.
	 * @param message java.lang.String
	 * @param recipients java.lang.String[]
	 * @param filePath java.lang.String
	 * @param dateForAttachment java.util.Date
	 * @param agent 
	 * @return java.lang.String
	 */
	public String notifyWithAttchment(String message, String[] recipients, String filePath, Date dateForAttachment,String userName, Agent agent) {
		boolean debug = false;
		Date date = null;
	     //Set the host smtp address
	     Properties props = new Properties();
	     
	     if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER)){
	     String strProtocol = "smtp", strHost = "smtp.gmail.com", strPort = "587", strFrom = "myimonitor@gmail.com", strPassword ="imsipl@india";
	     
	     props.put("mail.smtp.host", strHost);
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtps.auth", "true");
	     props.put("mail.smtp.port", strPort);
	     props.put("mail.transport.protocol", strProtocol);	     
		 props.put("mail.smtp.ssl.trust","*");
	     try{
	    	 if(dateForAttachment == null){
	    		 date = new Date();
	    	 }else{
	    		 date = dateForAttachment;
	    	 }
	    	 //Date date = new Date();
	    	 Authenticator auth = new SMTPAuthenticator();
	    	 Session session = Session.getDefaultInstance(props, auth);
	    	 session.setDebug(debug);
	    	 // create a message
	    	 MimeMessage msg = new MimeMessage(session);
	    	 // set the from and to address
	    	 InternetAddress addressFrom = new InternetAddress("my_imonitor");
	    	 msg.setFrom(addressFrom);
	    	 
	    	 InternetAddress[] addressTo = new InternetAddress[recipients.length];
	    	 for (int i = 0; i < recipients.length; i++){
	    		 
	    		 addressTo[i] = new InternetAddress(recipients[i]);
	    		 LogUtil.info("Sending attachment email to  " + recipients[i] );
	    	 }
	    	 msg.setRecipients(Message.RecipientType.TO, addressTo);
	    	 // Setting the Subject and Content Type
	    	 msg.setSubject("From Imonitor with Attachment.");
	    	 
	    	 Multipart multipart = new MimeMultipart();
	    	 BodyPart mssgBodyPart = new MimeBodyPart();
	    	 //String textAndImage = "<img src=\"cid:image\">";	/*this would attach image to the mail body.*/
	    	 
	    	 //1.Create the text part.
	    	 mssgBodyPart.setContent(message+"Time Stamp:"+date+". ","text/html");
	    	 multipart.addBodyPart(mssgBodyPart);
	    	 
	    	 if(filePath!= null)
	    	 {
	    		
	    		filePath = "/home/"+agent.getFtpUserName()+"/"+filePath;
	    		
		    	 //2.Create bodyPart to add the image attachment
		    	 mssgBodyPart = new MimeBodyPart();
		    	 //DataSource source = new FileDataSource("/imonitor/resources/images/device/ipcamera.png");
		    	 FileDataSource source = new FileDataSource(filePath);
		    	 mssgBodyPart.setDataHandler(new DataHandler(source));
		    	 mssgBodyPart.setHeader("Content-ID","<image>");
		    	 mssgBodyPart.setFileName(source.getFile().getName());
		    	 
		    	 //3.Add image attachment to multiPart
		    	 multipart.addBodyPart(mssgBodyPart);
	    	 }
	    	 
	    	 msg.setContent(multipart);
	    	 
	    	 Transport tr = session.getTransport(strProtocol);
	    	 tr.connect(strHost, Integer.valueOf(strPort), strFrom, strPassword);
	    	 tr.sendMessage(msg, msg.getAllRecipients());
	    	
	     }catch (Exception e) {
	    	 LogUtil.error("Error while sending Mail: "+e.getMessage());
	    	 e.printStackTrace();
		}
		}//for different email vendor append else block to check with the emailNotificationVendor Name.
	    else if(emailNotificationVendor.equalsIgnoreCase(Constants.EFL_EMAIL_NOTIFIER))
	    {
	    	String key = "efl";
			String password = "efl321";
			for (int i = 0; i < recipients.length; i++) 
			{
				
				String requestUrl;
				try {
					requestUrl = ControllerProperties.getProperties().get(Constants.EMAIL_ALERT_NOTIFIER_ADDRESS)
							+ "__key=" + URLEncoder.encode(key, "UTF-8")
							+ "&__pass="+ URLEncoder.encode(password, "UTF-8")
							+ "&mail="+ URLEncoder.encode(recipients[i], "UTF-8")
							+ "&subject="+ URLEncoder.encode("Alert from your EuroVigil account", "UTF-8")
							+ "&cust_name="+ URLEncoder.encode(userName, "UTF-8")
							+ "&alertmsg="+URLEncoder.encode(message,"UTF-8")
							+ "&image_url="+ URLEncoder.encode(ControllerProperties.getProperties().get(Constants.imvg_upload_context_path)+filePath, "UTF-8");
							 if(dateForAttachment == null){
					    		 date = new Date();
					    	 }else{
					    		 date = dateForAttachment;
					    	 }
							requestUrl+="&time_stamp="+ URLEncoder.encode(""+date, "UTF-8");		
					URL url = new URL(requestUrl);
					URLConnection urlConnection = url.openConnection();
					urlConnection.setDoInput(true);
					InputStream inStream = urlConnection.getInputStream();
		            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
		            String line = "";
		            while ((line = input.readLine()) != null){
		            	LogUtil.info(line);
		            }
				input.close(); // naveen added to avoid potential resource
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			
	    }
	    	
		return null;
	}
	
	public static void main(String[] args) {
		EMailNotifications eMailNotifications = new EMailNotifications();
		//eMailNotifications.notify("Test", new String[]{"princekfrancis@gmail.com"});
		//eMailNotifications.notify("Test1", new String[]{"sumit.kumar@imonitorsolutions.com"}, "pass the filePath");
		//sumit end
	}
	
	
	public String notifyforschedule(String message, String[] emails, Date dateOfAlert, String userName) throws MessagingException{
		 
		 boolean debug = false;
		 Date date = null;
	     Properties props = new Properties();
	     

	    
	    
	     if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER))
	     {
	     String strProtocol = "smtp", strHost = "smtp.gmail.com", strPort = "587", strFrom = "myimonitor@gmail.com", strPassword ="imsipl@india";
	     
	     props.put("mail.smtp.host", strHost);
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtps.auth", "true");
	     props.put("mail.smtp.port", strPort);
	     props.put("mail.transport.protocol", strProtocol);	     
		 props.put("mail.smtp.ssl.trust","*");
	     try{
	    	 if(dateOfAlert == null){
	    		 date = new Date();
	    	 }else{
	    		 date = dateOfAlert;
	    	 }
	    	 //Date date = new Date();
	    	 Authenticator auth = new SMTPAuthenticator();
	    	 Session session = Session.getDefaultInstance(props, auth);
	    	 session.setDebug(debug);
	    	 // create a message
	    	 Message msg = new MimeMessage(session);
	    	 // set the from and to address
	    	 //InternetAddress addressFrom = new InternetAddress(ControllerProperties.getProperties().get(Constants.INTERNET_ADDRESS));
	    	 InternetAddress addressFrom = new InternetAddress("my_imonitor");
	    	 msg.setFrom(addressFrom);
	    	 InternetAddress[] addressTo = new InternetAddress[emails.length];
	    	// if(notifierName.equalsIgnoreCase("E-Mail")){
	    	 for (int i = 0; i < emails.length; i++){
	    		 addressTo[i] = new InternetAddress(emails[i]);
	    		 LogUtil.info("Sending schedule notify email to  " + emails[i] );
	    	 }
	    	 msg.setRecipients(Message.RecipientType.TO, addressTo);
	    	 // Setting the Subject and Content Type
	    	 msg.setSubject("From Imonitor");
	    	 msg.setContent("Dear "+userName+",<br><br>"+message+"Time Stamp:"+date, "text/html");//KanthaRaj is changed to add user name in Email alert.
	    	 msg.setHeader("X-Mailer","msgsend");
	    	 Transport tr = session.getTransport(strProtocol);
	    	 tr.connect(strHost, Integer.valueOf(strPort), strFrom, strPassword);
	    	 tr.sendMessage(msg, msg.getAllRecipients());
	     }catch(MessagingException e) {
			throw new RuntimeException(e);
		}
	    }
	    else if(emailNotificationVendor.equalsIgnoreCase(Constants.EFL_EMAIL_NOTIFIER))
	    {
	    	
	    	/*SmsNotifications smsnotify= new SmsNotifications();
	    	smsnotify.notify(message, recipients, dateOfAlert);*/
	    	String key = "efl";
			String password = "efl321";
			for (int i = 0; i < emails.length; i++) 
			{
				
				String requestUrl;
				try {
					requestUrl = ControllerProperties.getProperties().get(Constants.EMAIL_ALERT_NOTIFIER_ADDRESS)
							+ "__key=" + URLEncoder.encode(key, "UTF-8")
							+ "&__pass="+ URLEncoder.encode(password, "UTF-8")
							+ "&mail="+ URLEncoder.encode(emails[i], "UTF-8")
							+ "&subject="+ URLEncoder.encode("Alert from your EuroVigil account", "UTF-8")
							+ "&cust_name="+ URLEncoder.encode(userName, "UTF-8")
							+ "&alertmsg="+URLEncoder.encode(message,"UTF-8");
							 if(dateOfAlert == null){
					    		 date = new Date();
					    	 }else{
					    		 date = dateOfAlert;
					    	 }
							requestUrl+="&time_stamp="+ URLEncoder.encode(""+date, "UTF-8");
					
					URL url = new URL(requestUrl);
					URLConnection urlConnection = url.openConnection();
					//urlConnection.setDoInput(true);
					InputStream inStream = urlConnection.getInputStream();
		            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
		            String line = "";
		            while ((line = input.readLine()) != null){
		            	LogUtil.info(line);
		            }
				input.close(); // naveen added to avoid potential resource leak
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				} catch (MalformedURLException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
	    }
			}
	    
	     
		
		return null;
		
	}
	
	
	public String SendXlsSheet(String[] recipients, String filePath){
		boolean debug = false;
		 Date date = new Date();
	     //Set the host smtp address
	     Properties props = new Properties();
	     
	    
	     String strProtocol = "smtp", strHost = "smtp.gmail.com", strPort = "587", strFrom = "myimonitor@gmail.com", strPassword ="imsipl@india";
	     
	     props.put("mail.smtp.host", strHost);
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtps.auth", "true");
	     props.put("mail.smtp.port", strPort);
	     props.put("mail.transport.protocol", strProtocol);	     
		 props.put("mail.smtp.ssl.trust","*");
	     try{
	    	 
	    	 //Date date = new Date();
	    	 Authenticator auth = new SMTPAuthenticator();
	    	 Session session = Session.getDefaultInstance(props, auth);
	    	 session.setDebug(debug);
	    	 // create a message
	    	 MimeMessage msg = new MimeMessage(session);
	    	 // set the from and to address
	    	 InternetAddress addressFrom = new InternetAddress("my_imonitor");
	    	 msg.setFrom(addressFrom);
	    	 
	    	
	    	 
	    	 InternetAddress[] addressTo = new InternetAddress[recipients.length];
	    	 for (int i = 0; i < recipients.length; i++){
	    		
	    		 addressTo[i] = new InternetAddress(recipients[i]);
	    		
	    	 }
	    	 msg.setRecipients(Message.RecipientType.TO, addressTo);

	    	 
	    	
	    	
	    	 
	    	 msg.setSubject("Myhomeqi gateway status report!");
	    	 msg.setContent("Hello iMonitor team, <br><br> This is a automated mail from myhomeqi server. Do not reply to this mail <br><br> Please find the attachment of "+
	    	 "gateway status report for myhomeqi server for the date: "+ date ,"text/html");
	    	 Multipart multipart = new MimeMultipart();
	    	 BodyPart mssgBodyPart = new MimeBodyPart();
	    	 //String textAndImage = "<img src=\"cid:image\">";	/*this would attach image to the mail body.*/
	    	 
	    	 //1.Create the text part.
	    	 mssgBodyPart.setContent("","text/html");
	    	 multipart.addBodyPart(mssgBodyPart);
	    	 
	    	 if(filePath!= null)
	    	 {
	    		
	    		
	    		
		    	 //2.Create bodyPart to add the image attachment
		    	 mssgBodyPart = new MimeBodyPart();
		    	
		    	 FileDataSource source = new FileDataSource(filePath);
		    	 mssgBodyPart.setDataHandler(new DataHandler(source));
		    	 mssgBodyPart.setFileName(source.getFile().getName());
		    	 
		    	 //3.Add image attachment to multiPart
		    	 multipart.addBodyPart(mssgBodyPart);
	    	 }
	    	 
	    	 msg.setContent(multipart);
	    	 
	    	 Transport tr = session.getTransport(strProtocol);
	    	 tr.connect(strHost, Integer.valueOf(strPort), strFrom, strPassword);
	    	 tr.sendMessage(msg, msg.getAllRecipients());
	    	 
	    	
	    	 
	    	 
	     }catch (Exception e) {
	    	 LogUtil.error("Error while sending Mail: "+e.getMessage());
	    	 e.printStackTrace();
		}
		
	     
	     
	     try{
	    		File file = new File(filePath);
	    		if(file.delete()){
	    			LogUtil.info(file.getName() + " is deleted!");
	    		}else{
	    			LogUtil.info("Delete operation is failed.");
	    		}
	 
	    	}catch(Exception e){
	 
	    		e.printStackTrace();
	 
	    	}
	     
		return null;
	}
	
	public String notify1(String message, String[] recipients, Date dateOfAlert,String userName) {
		 boolean debug = false;
		 Date date = null;
	     //Set the host smtp address
	     Properties props = new Properties();
	     	    
	     	     
	     if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER))
	     {	
	     String strProtocol = "smtp", strHost = "smtp.gmail.com", strPort = "587", strFrom = "myimonitor@gmail.com", strPassword ="imsipl@india";
	     
	     props.put("mail.smtp.host", strHost);
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtps.auth", "true");
	     props.put("mail.smtp.port", strPort);
	     props.put("mail.transport.protocol", strProtocol);	     
		 props.put("mail.smtp.ssl.trust","*");
	     try{
	    	 if(dateOfAlert == null){
	    		 date = new Date();
	    		
	    	 }else{
	    		 date = dateOfAlert;
	    		
	    	 }
	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
	    	 String sDate = sdf.format(date);
	    	
	    	 Authenticator auth = new SMTPAuthenticator();
	    	 Session session = Session.getDefaultInstance(props, auth);
	    	 session.setDebug(debug);
	    	 // create a message
	    	 Message msg = new MimeMessage(session);
	    	 // set the from and to address
	    	 //InternetAddress addressFrom = new InternetAddress(ControllerProperties.getProperties().get(Constants.INTERNET_ADDRESS));
	    	 InternetAddress addressFrom = new InternetAddress("my_imonitor");
	    	 msg.setFrom(addressFrom);
	    	 
	    	 InternetAddress[] addressTo = new InternetAddress[recipients.length];
	    	 for (int i = 0; i < recipients.length; i++){
	    		 addressTo[i] = new InternetAddress(recipients[i]);
	    	 }
	    	 msg.setRecipients(Message.RecipientType.TO, addressTo);
	    	 // Setting the Subject and Content Type
	    	 msg.setSubject("From Imonitor");
	    	 msg.setContent("Dear "+userName+",<br><br>"+message+ " ", "text/html");
	    	 msg.setHeader("X-Mailer","msgsend");
	    	 Transport tr = session.getTransport(strProtocol);
	    	 tr.connect(strHost, Integer.valueOf(strPort), strFrom, strPassword);
	    	 tr.sendMessage(msg, msg.getAllRecipients());
	    	
	    
	     }catch (Exception e) {
	    	 e.printStackTrace();
		    	 LogUtil.info("Error while sending email notification.", e);
		}
				
	     }
	     return null;
	
}

	
	/*****abhi changed the code for smsthreshold count*********/
	public String notify2(String message, String[] recipients, Date dateOfAlert,String userName) {
		 boolean debug = false;
		 Date date = null;
	     //Set the host smtp address
	     Properties props = new Properties();
	     	    
	     	     
	     if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER))
	     {	
	     String strProtocol = "smtp", strHost = "smtp.gmail.com", strPort = "587", strFrom = "myimonitor@gmail.com", strPassword ="imsipl@india";
	     
	     props.put("mail.smtp.host", strHost);
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtps.auth", "true");
	     props.put("mail.smtp.port", strPort);
	     props.put("mail.transport.protocol", strProtocol);	     
		 props.put("mail.smtp.ssl.trust","*");
	     try{
	    	 if(dateOfAlert == null){
	    		 date = new Date();
	    		
	    	 }else{
	    		 date = dateOfAlert;
	    		
	    	 }
	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
	    	 String sDate = sdf.format(date);
	    	
	    	 Calendar c = Calendar.getInstance();
	    	 c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	    	 c.set(Calendar.HOUR_OF_DAY, 0);
	    	 c.set(Calendar.MINUTE,0);
	    	 c.set(Calendar.SECOND,0);
	    	 String startDate = "", endDate = "";
	    	 startDate=sdf.format(c.getTime());
	    	 Date date1=new Date();
	    	 c.add(Calendar.DATE, 6);
	    	 endDate=sdf.format(date1);
	    	
	    	 
	    	 Authenticator auth = new SMTPAuthenticator();
	    	 Session session = Session.getDefaultInstance(props, auth);
	    	 session.setDebug(debug);
	    	 // create a message
	    	 Message msg = new MimeMessage(session);
	    	 // set the from and to address
	    	 //InternetAddress addressFrom = new InternetAddress(ControllerProperties.getProperties().get(Constants.INTERNET_ADDRESS));
	    	 InternetAddress addressFrom = new InternetAddress("my_imonitor");
	    	 msg.setFrom(addressFrom);
	    	 
	    	 InternetAddress[] addressTo = new InternetAddress[recipients.length];
	    	 for (int i = 0; i < recipients.length; i++){
	    		 addressTo[i] = new InternetAddress(recipients[i]);
	    	 }
	    	 msg.setRecipients(Message.RecipientType.TO, addressTo);
	    	 // Setting the Subject and Content Type
	    	 msg.setSubject(userName+"-SMS Report");
	    	 msg.setContent("Dear Imonitor "+",<br><br>"+message+"<br><br>"+" From:  "+startDate+"<br><br>"+" To:  "+ endDate +"<br><br>"+"<br><br>"+"<br><br>"+"Thanks & Regards"+",<br><br>"+ "	iMonitor Solutions" +" ", "text/html");
	    	 msg.setHeader("X-Mailer","msgsend");
	    	 Transport tr = session.getTransport(strProtocol);
	    	 tr.connect(strHost, Integer.valueOf(strPort), strFrom, strPassword);
	    	 tr.sendMessage(msg, msg.getAllRecipients());
	    	
	    
	     }catch (Exception e) {
	    	 e.printStackTrace();
		    	 LogUtil.info("Error while sending email notification.", e);
		}
				
	     }
	     return null;
	
}	
	/*Auhor: Naveen
	 * To intimate customer after successful self registration from login page
	 * 18th June 2018
	 */
	public String notifyRegistrationSuccessToCustomer(String message, String[] recipients, Date dateOfAlert,String userName) {
		 boolean debug = false;
		 Date date = null;
	     Properties props = new Properties();
	    
	         
	     
	     if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER))
	     {
					
	    		
	     String strProtocol = "smtp", strHost = "smtp.gmail.com", strPort = "587", strFrom = "myimonitor@gmail.com", strPassword ="imsipl@india";
	     
	     props.put("mail.smtp.host", strHost);
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtps.auth", "true");
	     props.put("mail.smtp.port", strPort);
	     props.put("mail.transport.protocol", strProtocol);	     
		 props.put("mail.smtp.ssl.trust","*");
	     try{
	    	 if(dateOfAlert == null){
	    		 date = new Date();
	    		
	    	 }else{
	    		 date = dateOfAlert;
	    		
	    	 }
	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
	    	 String sDate = sdf.format(date);
	    	
	    	 Authenticator auth = new SMTPAuthenticator();
	    	 Session session = Session.getDefaultInstance(props, auth);
	    	 session.setDebug(debug);
	    	 // create a message
	    	 Message msg = new MimeMessage(session);
	    	 // set the from and to address
	    	 //InternetAddress addressFrom = new InternetAddress(ControllerProperties.getProperties().get(Constants.INTERNET_ADDRESS));
	    	 InternetAddress addressFrom = new InternetAddress("my_imonitor");
	    	 msg.setFrom(addressFrom);
	    	
	    	 InternetAddress[] addressTo = new InternetAddress[recipients.length];
	    	 for (int i = 0; i < recipients.length; i++){
	    		 addressTo[i] = new InternetAddress(recipients[i]);
	    	
	    	 }
	    	 msg.setRecipients(Message.RecipientType.TO, addressTo);
	    	 // Setting the Subject and Content Type
	    	 msg.setSubject("From Imonitor");
	    	 msg.setContent(message+" ", "text/html");
	    	 msg.setHeader("X-Mailer","msgsend");
	    	 Transport tr = session.getTransport(strProtocol);
	    	 tr.connect(strHost, Integer.valueOf(strPort), strFrom, strPassword);
	    	 tr.sendMessage(msg, msg.getAllRecipients());
	    
	     }catch (Exception e) {
	    	 e.printStackTrace();
		    	 LogUtil.info("Error while sending email notification.", e);
		}
				
	     }
	    else if(emailNotificationVendor.equalsIgnoreCase(Constants.EFL_EMAIL_NOTIFIER))
	    {
	    	String key = "efl";
			String password = "efl321";
			for (int i = 0; i < recipients.length; i++) 
			{
				
				String requestUrl;
				try {
					requestUrl = ControllerProperties.getProperties().get(Constants.EMAIL_ALERT_NOTIFIER_ADDRESS)
							+ "__key=" + URLEncoder.encode(key, "UTF-8")
							+ "&__pass="+ URLEncoder.encode(password, "UTF-8")
							+ "&mail="+ URLEncoder.encode(recipients[i], "UTF-8")
							+ "&subject="+ URLEncoder.encode("Alert from your EuroVigil account", "UTF-8")
							+ "&cust_name="+ URLEncoder.encode(userName, "UTF-8")
							+ "&alertmsg="+URLEncoder.encode(message,"UTF-8");
							 if(dateOfAlert == null){
					    		 date = new Date();
					    	 }else{
					    		 date = dateOfAlert;
					    	 }
							requestUrl+="&time_stamp="+ URLEncoder.encode(""+date, "UTF-8");
					
					URL url = new URL(requestUrl);
					URLConnection urlConnection = url.openConnection();
					//urlConnection.setDoInput(true);
					InputStream inStream = urlConnection.getInputStream();
		            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
		            String line = "";
		            while ((line = input.readLine()) != null){
		            	LogUtil.info(line);
		            }
		            
		            input.close(); // Naveen added to avoid potential resource leak
				
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				} catch (MalformedURLException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
	    }
			}
	     
	     //for different email vendor append else block to check with the emailNotificationVendor Name.
	     return null;
	} 
	
	
	
}


