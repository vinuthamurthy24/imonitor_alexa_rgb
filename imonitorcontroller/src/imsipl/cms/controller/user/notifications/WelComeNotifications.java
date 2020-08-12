package imsipl.cms.controller.user.notifications;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.SmsNotifications;
import in.xpeditions.jawlin.imonitor.util.Constants;

public class WelComeNotifications implements Runnable{

	private Customer customer;
	
	public WelComeNotifications(Customer customer){
		this.customer = customer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		String message = "Welcome to your iMonitor Smart Home Solution. We are confident you will enjoy the experience of living in a smart home.\r\n" + 
				"\r\n" + 
				"You have now added intelligence to your home with a brain that ONLY  listens to you and your family (designated persons)\r\n" + 
				"\r\n" + 
				"We have sent an email confirmation also regarding your new account";
		
		try {
			//Send SMS
			SmsNotifications smsNotifications = new SmsNotifications();
			smsNotifications.notifywithOTPForSelfRegistration(message, customer.getMobile());
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.info("Couldn't send welcome sms ");
		}
	
		//Send Email
				
		
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
