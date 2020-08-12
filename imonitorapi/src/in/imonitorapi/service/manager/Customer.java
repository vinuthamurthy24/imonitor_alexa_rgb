package in.imonitorapi.service.manager;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name="customer")

public class Customer {

		private String customerId;
		private String userId;
		private String password;
		private String macId;
		private String status;
		
		public Customer(String customerId,String userId,String password,String macId,String status){
			super();
			this.customerId=customerId;
			this.userId=userId;
			this.password=password;
			this.macId=macId;
			this.status=status;
		}
		
		
		@XmlElement
		public String getCustomerId() {return customerId;}
		public void setCustomerId(String customerId) {this.customerId = customerId;}
		
		@XmlElement
		public String getUserId() {return userId;}
		public void setUserId(String userId) {this.userId = userId;}
		
		@XmlElement
		public String getPassword() {return password;}
		public void setPassword(String password) {this.password = password;}
		
	
		@XmlElement
		public String getMacId() {return macId;}
		public void setMacId(String macId) {this.macId = macId;}
		@XmlElement
		public String getStatus() {return status;}
		public void setStatus(String status) {this.status = status;}
	
}
