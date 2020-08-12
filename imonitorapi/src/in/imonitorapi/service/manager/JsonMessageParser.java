package in.imonitorapi.service.manager;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class JsonMessageParser {
	
	private String event;
	private String eMail;
	private String transid;
	private String success;
	private String source;
    private String url;
	public JsonMessageParser(){
		
	}
	
	public JsonMessageParser(String Event, String email, String source, String transaction, String status, String url) {
		
		        this.event = Event;
		        this.eMail = email;
		        this.transid = transaction;
		        this.success = status;
		        this.source = source;
		        this.url = url;
		
		    }
	
		 

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

		public String getSuccess() {
			return success;
		}

		public void setSuccess(String success) {
			this.success = success;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}
		
		
		
		public String getTransid() {
			return transid;
		}

		public void setTransid(String transid) {
			this.transid = transid;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
 
		
		/*@Override
	     public String toString() {
	
	         return new StringBuffer(" event :").append(this.event)
                                    .append(" email :").append(this.eMail)
	                                 .append(" source :").append(this.source)
	                                 .append(" transid : ")
	               	                 .append(this.txnId)
	               	                 .append("status :").append(this.success).toString();
	 
	     }*/

		
}
