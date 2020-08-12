/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.sms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsReport;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;
//import in.xpeditions.jawlin.imonitor.util.LogUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;




public class SmsReportAction extends ActionSupport {

	private String data;
	private String sid;
	private String dest;
	private String stime;
	private String dtime;
	private String status;
	
	private static final long serialVersionUID = 1L;

	
	public String SaveSMSReport() throws Exception
	{
		
		String serviceName = "serverConfiguration";
		String method = "SaveSMSReport";
		
		
		XStream strem= new XStream();
		
		
		SmsReport sr=new SmsReport();
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sdate=null;
		Date ddate=null;
		
		try {
			sdate = time.parse(stime);
			ddate = time.parse(dtime);
		} catch (ParseException e) {
			LogUtil.info("ParseException", e);
		} catch (Exception e){
			LogUtil.info("Got Exception", e);
		}
		
		sr.setDeliveryTime(ddate);
		sr.setMobileNumber(dest.substring(2));
		sr.setSessionId(sid);
		sr.setSentTime(sdate);
		sr.setStatus(status);
		
		this.data = IMonitorUtil.sendToController(serviceName, method,strem.toXML(sr));
		
		return SUCCESS;
		
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public String getSid() {
		return sid;
	}


	public void setSid(String sid) {
		this.sid = sid;
	}


	public String getDest() {
		return dest;
	}


	public void setDest(String dest) {
		this.dest = dest;
	}


	public String getStime() {
		return stime;
	}


	public void setStime(String stime) {
		this.stime = stime;
	}


	public String getDtime() {
		return dtime;
	}


	public void setDtime(String ddate) {
		this.dtime = ddate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
}
