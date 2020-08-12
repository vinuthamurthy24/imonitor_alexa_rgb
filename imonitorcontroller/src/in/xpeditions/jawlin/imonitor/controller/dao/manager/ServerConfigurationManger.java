/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsReport;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsService;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServerConfigurationManger 
{

	DaoManager daoManager=new DaoManager();
	public boolean saveServiceProvider(SmsService smsoperator) {
		
		boolean result=false;
		try{
			daoManager.save(smsoperator);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List listAskedSmsProvider(String sSearch,String sOrder, int start, int length) {
		String query = "";
        query += "select sm.id,sm.operatorcode,sm.status from " + " SmsService as sm " +
                "where (sm.operatorcode like '%"+sSearch+"%') "+sOrder;   
        List list = daoManager.listAskedObjects(query,start,length,SmsService.class);
		return list;
	}

	public int getTotalSMSSeviceCount(String sSearch) {
		
			int count =  daoManager.getCount(SmsService.class);
			return count;
		
	}

	public SmsService GetsmsServiceById(SmsService smsoperator) {
		SmsService smsserrvice=new SmsService();
		try{
			smsserrvice=(SmsService) daoManager.getOne("operatorcode", smsoperator.getOperatorcode(),SmsService.class);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return smsserrvice;
	}

	public SmsService DeletesmsServiceById(SmsService smsoperator) 
	{
		 daoManager.delete(smsoperator.getId(),SmsService.class);
		return null;
	}

	public void UpdateServiceProvider(SmsService smsoperator) 
	{
		
		 daoManager.update(smsoperator);
	}

	@SuppressWarnings("unchecked")
	public List<SmsService> listOfSMSService() {
		
			List<SmsService> SmsService = new ArrayList<SmsService>();
			try {
				SmsService = (List<SmsService>) daoManager.list(SmsService.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SmsService;

		
	}

	public boolean UpdateStatusServiceProvider(SmsService smsoperator) {
		DBConnection dbc = null;
		String query ="";
		boolean result = false;
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q ;
		Transaction tx = session.beginTransaction();
		
		if(smsoperator.getStatus().equals("0"))
		{
			query = "update smsservices set status="+smsoperator.getStatus()+" where id="+smsoperator.getId()+"";
		}
		else
		{
			query = "update smsservices set status="+"0"+"";
			try {
				q = session.createSQLQuery(query);
				q.executeUpdate();
				tx.commit();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			} 
			query = "update smsservices set status="+smsoperator.getStatus()+" where id="+smsoperator.getId()+"";
		}
		try {
			q = session.createSQLQuery(query);
			q.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
		return result;
	}

	public SmsService getSelectedSMSProvider() {
		SmsService smsservice= (SmsService) daoManager.getOne("status", "1",SmsService.class);
			
		return smsservice;
		
	}

	public boolean saveSessionId(SmsReport smsreport) {
		boolean result=false;
		try{
			daoManager.save(smsreport);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}
	
	public SmsReport getSmsReportBuSessionId(String Value,String MoblieNumber) {
		
		String query = "select sm from SmsReport sm where sm.sessionId = '" +Value+ "' and sm.mobileNumber = '"+MoblieNumber+" ' ";

	
		DBConnection dbc = null;
		SmsReport result=new SmsReport();
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Transaction tx = session.beginTransaction();
			
			Query q = session.createQuery(query);
			result = (SmsReport) q.uniqueResult();
			
			
			tx.commit();
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return result;
		
	}

	public void UpdateSMSReport(SmsReport smsReportDb) {
	
		daoManager.update(smsReportDb);
	}

	@SuppressWarnings("rawtypes")
	public List listAskedSmsReportCounts(String sSearch,String sOrder, int start, int length,String fromDate, String toDate ) {
		String query = "";
        /*query += "select c.customerId,sr.mobileNumber,sm.time,sm.subscribtionStatus from " + " SmsSubscibers as sm " +
                "where (sm.mobile like '%"+sSearch+"%') "+sOrder;*/
		
		
		if((fromDate != null) || (toDate != null)){
		
		
			 query += "select sr.id,c.customerId,sr.mobileNumber,count(sr)" + " from "
						+ "SmsReport as sr join sr.customer as c "
						+ "where (c.customerId like '%" + sSearch + "%' "
						+ " or sr.mobileNumber like '%" + sSearch + "%') and DATE(sr.submittedTime)  between '"+fromDate+"' and '"+toDate+"' "
						+ "group by c.customerId,sr.mobileNumber ";
		
	}else{
        query += "select sr.id,c.customerId,sr.mobileNumber,count(sr)" + " from "
				+ "SmsReport as sr join sr.customer as c "
				+ "where  (c.customerId like '%" + sSearch + "%' "
				+ " or sr.mobileNumber like '%" + sSearch + "%') and week(sr.submittedTime)=week(CURDATE()) and year(sr.submittedTime)=year(CURDATE()) "
				+ "group by c.customerId,sr.mobileNumber ";
        
		}
				
        List list = daoManager.listAskedObjects(query,start,length,SmsReport.class);
		return list;
	}
public long getTotalSmsCountPerCustomer(String number, Customer customer){
	
	long count = 0;
	DBConnection dbc = null;
	String query = "select count(*) from smsReport where mobileNumber='"+number+"' and customer="+customer.getId()+" and WEEK(submittedTime)=(WEEK(CURDATE())) and YEAR(submittedTime) = (YEAR(CURDATE()))";
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createSQLQuery(query);
		BigInteger result = (BigInteger) q.uniqueResult();
		count = result.longValue();
	} catch (Exception e) {
        LogUtil.info("fail to get SMS count : "+ e.getMessage());
	}finally {
		dbc.closeConnection();
	}
	return count;
	
	
}

@SuppressWarnings("rawtypes")
public int getSmsReportCounts(String sSearch) {
	
    /*query += "select c.customerId,sr.mobileNumber,sm.time,sm.subscribtionStatus from " + " SmsSubscibers as sm " +
            "where (sm.mobile like '%"+sSearch+"%') "+sOrder;*/

    DBConnection dbc = null;
	int count = 0;
	try{
		String query = "";
		 query += "select count(*)" + " from "
					+ "smsReport as sr left join customer as c on c.id=sr.customer "
					+ "where  (c.customerId like '%" + sSearch + "%' "
					+ " or sr.mobileNumber like '%" + sSearch + "%') and week(sr.submittedTime)=week(CURDATE()) and year(sr.submittedTime)=year(CURDATE()) "
					+ "group by c.customerId,sr.mobileNumber ";
	
    dbc = new DBConnection();
	Session session = dbc.openConnection();
	Query q = session.createSQLQuery(query);
	BigInteger lCount = (BigInteger) q.uniqueResult();
	count = lCount.intValue();
}catch (Exception ex) {
	ex.printStackTrace();
} finally {
	dbc.closeConnection();
}
	return count;}

}
