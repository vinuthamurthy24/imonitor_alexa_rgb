package in.xpeditions.jawlin.imonitor.controller.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.EMailNotifications;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.thoughtworks.xstream.XStream;

public class IntimateCustomerAction implements Job {

	String emailNotificationVendor = ControllerProperties.getProperties().get(Constants.EMAIL_ALERT_NOTIFIER);
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		
		XStream stream = new XStream();
		
		String query="select c.id,c.dateOfExpiry,c.dateOfInstallation,c.customerId,c.intimationCount,c.email from customer as c" +
				     " where DATE(c.dateOfExpiry) BETWEEN NOW() AND DATE_ADD(NOW(),INTERVAL 1 MONTH) and c.intimationCount = 0";
	
		
		
		List<Object[]> objects = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			objects = q.list();
			if(objects != null){
				
			for(Object[] strings : objects){
				
				String customerId = IMonitorUtil.convertToString(strings[3]);
				String expireDate = IMonitorUtil.convertToString(strings[1]);
				String installationDate =  IMonitorUtil.convertToString(strings[2]);
			    String eMail = IMonitorUtil.convertToString(strings[5]);
			    int intimation=Integer.parseInt(IMonitorUtil.convertToString(strings[4]));
			    long id=Integer.parseInt(IMonitorUtil.convertToString(strings[0]));
			  //  LogUtil.info("customer ID : "+ customerId + " with expiry date: "+ expireDate+"id"+id);
			    
			    String messageMail = "Imonitor smart home solution will expire on "+expireDate+". Please contact your supplier for maintainance.";
			   
			    if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER)){
			    EMailNotifications emailNotifications = new EMailNotifications();
			    String[] Recipient ={eMail};
			   
			    emailNotifications.notify(messageMail,Recipient, null,customerId);
			    
			   
			    query="update customer set intimationCount='1'"+"where id="+id+"";
			   
			    
			    Session session1 = dbc.openConnection();
			    Query q1 = session1.createSQLQuery(query);
			   
			    Transaction tx = session1.beginTransaction();
				q1.executeUpdate();
				tx.commit();
				
			    }
		
			}
			
			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		List<Object[]> customerObjects = null;
		query = "select c.id,c.dateOfExpiry,c.dateOfInstallation,c.customerId,c.intimationCount,c.email from customer as c" +
				     " where DATE(c.dateOfExpiry) BETWEEN NOW() AND DATE_ADD(NOW(),INTERVAL 10 DAY) and intimationCount = 1";
		
		try {
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q1 = session.createSQLQuery(query);
			customerObjects = q1.list();
			
			if(customerObjects != null){
				for(Object[] object : customerObjects){
					
					String customerId = IMonitorUtil.convertToString(object[3]);
					String expireDate = IMonitorUtil.convertToString(object[1]);
					String instalationDate = IMonitorUtil.convertToString(object[2]);
					String eMail = IMonitorUtil.convertToString(object[5]);
					long id=Integer.parseInt(IMonitorUtil.convertToString(object[0]));
						
						
						String messageMail = "Imonitor smart home solution will expire on "+expireDate+". Please contact your supplier for maintainance.";
						  if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER)){
						EMailNotifications emailNotifications = new EMailNotifications();
					    String[] Recipient ={eMail};
					    emailNotifications.notify(messageMail,Recipient, null,customerId);
						
					    
					    query="update customer set intimationCount='2'"+"where id="+id+"";
					    
					   
					    Session session2 = dbc.openConnection();
					    Query q2 = session2.createSQLQuery(query);
					   
					    Transaction tx = session2.beginTransaction();
						q2.executeUpdate();
						tx.commit();
						
					    
						  }
				
					
					
				}
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	
	
	
	
}
