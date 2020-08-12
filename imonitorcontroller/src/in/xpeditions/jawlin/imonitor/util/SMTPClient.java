/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.util;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
public class SMTPClient {

    public void sendMail(String recipient) throws Exception {
		try{
			String recipients[] ={recipient};
			boolean debug = false;
			String host = "smtp.gmail.com";
			// Get system properties
			Properties properties = System.getProperties();
			properties.put("mail.smtp.starttls.enable","true");
			// Setup mail server
			properties.setProperty("mail.smtp.host", host);
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			 Authenticator auth = new SMTPAuthenticator();
	    	 Session session = Session.getDefaultInstance(properties, auth);
	    	 session.setDebug(debug);
	    	 Message msg = new MimeMessage(session);
	    	 // set the from and to address
	    	 InternetAddress addressFrom = new InternetAddress("RED-LIFE-INDIA");
	    	 msg.setFrom(addressFrom);
	    	 
	    	 InternetAddress[] addressTo = new InternetAddress[recipients.length];
	    	 for (int i = 0; i < recipients.length; i++){
	    		 addressTo[i] = new InternetAddress(recipients[i]);
	    	 }
	    	 msg.setRecipients(Message.RecipientType.TO, addressTo);
	    	 // Setting the Subject and Content Type
	    	 msg.setSubject("hi");
	    	 msg.setContent("hellonz", "text/html");
	    	 Transport.send(msg);
		} catch(Exception e){
			e.printStackTrace();
		}
    }
}
