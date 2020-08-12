package in.xpeditions.jawlin.imonitor.controller.dao.manager;


/*
 * 
 * Author: Naveen M S
 * Created on Feb 2015
 */

import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerReport;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IssueStatus;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.RootCause;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Severity;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerSupportManager {
	
	DaoManager daoManager = new DaoManager();
	
	@SuppressWarnings("unchecked")
	public List<Severity> listOfSeverityLevels() {
		List<Severity> severity = new ArrayList<Severity>();
		try {
			severity = (List<Severity>) daoManager.list(Severity.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return severity;

	}
	
	@SuppressWarnings("unchecked")
	public List<IssueStatus> listOfIssueStatus() {
		List<IssueStatus> severity = new ArrayList<IssueStatus>();
		try {
			severity = (List<IssueStatus>) daoManager.list(IssueStatus.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return severity;

	}
	
	@SuppressWarnings("unchecked")
	public List<RootCause> listOfRootCause() {
		List<RootCause> rcList = new ArrayList<RootCause>();
		try {
			rcList = (List<RootCause>) daoManager.list(RootCause.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rcList;

	}
	
	@SuppressWarnings("unchecked")
	public List<RootCause> listOfRoorCause() {
		List<RootCause> rootcauselist = new ArrayList<RootCause>();
		try {
			rootcauselist = (List<RootCause>) daoManager.list(RootCause.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootcauselist;

	}
	
	public boolean saveReportForCustomer(CustomerReport customerReport){
		
		boolean result = false;
		try {
			daoManager.save(customerReport);
			result = true;
		} catch (Exception e) {
			LogUtil.error("Error when saving customer Report " 
					+ " message : " + e.getMessage());
		}
		
		
		return result;
	}
	
	public int getTotalReportsCount() {
		
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(c)" + " from "
					+ "CustomerReport as c join c.severity as s join c.finalState as fs join c.rootCause as rc "
					+ "where c.severity=s.id and c.finalState=fs.id and c.rootCause=rc.id";
		
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			count = lCount.intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
		return count;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listAskedResolvedIssueList(String sSearch, String sOrder, int start,
			int length, String status, String severity) {
		String query = "";
		
		
		
		if( (status.equals("") || status == null) && (severity.equals("") || severity == null)){
		query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate from customerReports" +
	    		" as cr " +
				" left join customer as c on cr.customerId=c.id "+
	    		" left join severity as s on cr.severity=s.id "+
				" left join issuestatus as fs on fs.id=cr.finalState"+
	            " where cr.type=1 and ( c.customerId like '%"+sSearch+"%' " +
	            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%'  )" + sOrder;   
		}
		else if( (!status.equals("") || status != null) && (severity.equals("") || severity == null)){
			query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate from customerReports" +
		    		" as cr " +
					" left join customer as c on cr.customerId=c.id "+
		    		" left join severity as s on cr.severity=s.id "+
					" left join issuestatus as fs on fs.id=cr.finalState"+
		            " where fs.name='"+status+"' and cr.type=1 and ( c.customerId like '%"+sSearch+"%' " +
		            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%'  )" + sOrder;   
			}
		else if( (status.equals("") || status == null) && (!severity.equals("") || severity != null)){
			query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate from customerReports" +
		    		" as cr " +
					" left join customer as c on cr.customerId=c.id "+
		    		" left join severity as s on cr.severity=s.id "+
					" left join issuestatus as fs on fs.id=cr.finalState"+
		            " where s.severityLevel='"+severity+"' and cr.type=1 and ( c.customerId like '%"+sSearch+"%' " +
		            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%'  )" + sOrder;   
			}
		else{
			query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate from customerReports" +
		    		" as cr " +
					" left join customer as c on cr.customerId=c.id "+
		    		" left join severity as s on cr.severity=s.id "+
					" left join issuestatus as fs on fs.id=cr.finalState"+
		            " where fs.name='"+status+"' and s.severityLevel='"+severity+"' and  cr.type=1 and ( c.customerId like '%"+sSearch+"%' " +
		            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%'  )" + sOrder;   
			}
		
		DBConnection dbc = null;
		List<Object[]> objects = null;
		
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			q.setMaxResults(length);
			q.setFirstResult(start);
			objects = q.list();
						
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		
		
		return objects;
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listAskedCustomerIssueList(String sSearch, String sOrder, int start,
			int length, String status, String severity, String rootCause) {
		String query = "";
		
		
		if( (status.equals("") || status == null) && (severity.equals("") || severity == null) && (rootCause.equals("") || rootCause == null)){		
		query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate,rc.cause,cr.otherCause from customerReports" +
	    		" as cr " +
				" left join customer as c on cr.customerId=c.id "+
	    		" left join severity as s on cr.severity=s.id "+
				" left join issuestatus as fs on fs.id=cr.finalState"+
	    		" left join rootcause as rc on rc.id=cr.rootCause"+
	            " where ( c.customerId like '%"+sSearch+"%' " +
	            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%' or rc.cause like '%"+sSearch+"%'  )" + sOrder;   
		}
		
		else if((!status.equals("") || status != null) && (severity.equals("") || severity == null) && (rootCause.equals("") || rootCause == null)){
			
			query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate,rc.cause,cr.otherCause from customerReports" +
		    		" as cr " +
					" left join customer as c on cr.customerId=c.id "+
		    		" left join severity as s on cr.severity=s.id "+
					" left join issuestatus as fs on fs.id=cr.finalState"+
					" left join rootcause as rc on rc.id=cr.rootCause"+
		            " where fs.name='"+status+"' and ( c.customerId like '%"+sSearch+"%' " +
		            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%' or rc.cause like '%"+sSearch+"%'  )" + sOrder;
		}
     else if((status.equals("") || status == null) && (!severity.equals("") || severity != null) && (rootCause.equals("") || rootCause == null)){
			
			query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate,rc.cause,cr.otherCause from customerReports" +
		    		" as cr " +
					" left join customer as c on cr.customerId=c.id "+
		    		" left join severity as s on cr.severity=s.id "+
					" left join issuestatus as fs on fs.id=cr.finalState"+
					" left join rootcause as rc on rc.id=cr.rootCause"+
		            " where s.severityLevel='"+severity+"' and ( c.customerId like '%"+sSearch+"%' " +
		            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%' or rc.cause like '%"+sSearch+"%'  )" + sOrder;
		}
     else if((status.equals("") || status == null) && (severity.equals("") || severity == null) && (!rootCause.equals("") || rootCause != null)){
			
			query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate,rc.cause,cr.otherCause from customerReports" +
		    		" as cr " +
					" left join customer as c on cr.customerId=c.id "+
		    		" left join severity as s on cr.severity=s.id "+
					" left join issuestatus as fs on fs.id=cr.finalState"+
					" left join rootcause as rc on rc.id=cr.rootCause"+
		            " where rc.cause='"+rootCause+"' and ( c.customerId like '%"+sSearch+"%' " +
		            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%' or rc.cause like '%"+sSearch+"%'  )" + sOrder;
		}
     else if((!status.equals("") || status != null) && (severity.equals("") || severity == null) && (!rootCause.equals("") || rootCause != null)){
			
			query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate,rc.cause,cr.otherCause from customerReports" +
		    		" as cr " +
					" left join customer as c on cr.customerId=c.id "+
		    		" left join severity as s on cr.severity=s.id "+
					" left join issuestatus as fs on fs.id=cr.finalState"+
					" left join rootcause as rc on rc.id=cr.rootCause"+
		            " where rc.cause='"+rootCause+"' and fs.name='"+status+"' and ( c.customerId like '%"+sSearch+"%' " +
		            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%' or rc.cause like '%"+sSearch+"%'  )" + sOrder;
		}
     else if((!status.equals("") || status != null) && (!severity.equals("") || severity != null) && (rootCause.equals("") || rootCause == null)){
			
			query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate,rc.cause,cr.otherCause from customerReports" +
		    		" as cr " +
					" left join customer as c on cr.customerId=c.id "+
		    		" left join severity as s on cr.severity=s.id "+
					" left join issuestatus as fs on fs.id=cr.finalState"+
					" left join rootcause as rc on rc.id=cr.rootCause"+
		            " where s.severityLevel='"+severity+"' and fs.name='"+status+"' and ( c.customerId like '%"+sSearch+"%' " +
		            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%' or rc.cause like '%"+sSearch+"%'  )" + sOrder;
		}
     else if((status.equals("") || status == null) && (!severity.equals("") || severity != null) && (!rootCause.equals("") || rootCause != null)){
			
			query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate,rc.cause,cr.otherCause from customerReports" +
		    		" as cr " +
					" left join customer as c on cr.customerId=c.id "+
		    		" left join severity as s on cr.severity=s.id "+
					" left join issuestatus as fs on fs.id=cr.finalState"+
					" left join rootcause as rc on rc.id=cr.rootCause"+
		            " where s.severityLevel='"+severity+"' and rc.cause='"+rootCause+"' and ( c.customerId like '%"+sSearch+"%' " +
		            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%' or rc.cause like '%"+sSearch+"%'  )" + sOrder;
		}
     else{
    	 
    	 query = "select cr.id,c.customerId,cr.reportedDate,s.severityLevel,fs.name,cr.issueDescription,cr.resolution,cr.resolutionDate,rc.cause,cr.otherCause from customerReports" +
		    		" as cr " +
					" left join customer as c on cr.customerId=c.id "+
		    		" left join severity as s on cr.severity=s.id "+
					" left join issuestatus as fs on fs.id=cr.finalState"+
					" left join rootcause as rc on rc.id=cr.rootCause"+
		            " where s.severityLevel='"+severity+"' and fs.name='"+status+"' and rc.cause='"+rootCause+"' and ( c.customerId like '%"+sSearch+"%' " +
		            		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch +"%' or fs.name like '%" + sSearch + "%' or rc.cause like '%"+sSearch+"%'  )" + sOrder;
    	 
     }
		
		
		DBConnection dbc = null;
		List<Object[]> objects = null;
		
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			q.setMaxResults(length);
			q.setFirstResult(start);
			objects = q.list();
			
			for (Object[] strings : objects) 
			{
				
				if(strings[8]== "Other" || strings[8].equals("Other")){
					
					strings[8] = strings[9];
				}
				
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		
		
		return objects;
		
		
	}
	
	public int getTotalDisplayReportCount(String sSearch) {
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(*)" + " from "
					+ "customerReports as cr "+
					"left join customer as c on cr.customerId=c.id "
					+" left join severity as s on s.id=cr.severity "
					+" left join issuestatus as fs on fs.id=cr.finalState "
					+ " where  (c.customerId like '%"+sSearch+"%' " +
	         		"or s.severityLevel like '%" + sSearch + "%' or cr.resolutionDate like '%" + sSearch + "%' or fs.name like '%" + sSearch + "%'  )" ;   
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			BigInteger lCount = (BigInteger) q.uniqueResult();
			count = lCount.intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return count;
	}
	
	public CustomerReport getSelectedCustomerReport (String reportId){
		
		    long id = Long.parseLong(reportId);
			return (CustomerReport) daoManager.get(id, CustomerReport.class);
		
		
	}
	
	public boolean saveUpdatedCustomerReport(CustomerReport report){
		
		boolean result = false;
		DBConnection dbc = null;
		String query = "select cr from CustomerReport as cr where cr.id="+report.getId()+"";
		
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createQuery(query);
			CustomerReport objects = (CustomerReport) q.uniqueResult();
			
			if(objects != null){
				
				objects.setIssueDescription(report.getIssueDescription());
				objects.setAction(report.getAction());
				objects.setActionDate(report.getActionDate());
				objects.setAllocatedPerson(report.getAllocatedPerson());
				objects.setFinalDate(report.getFinalDate());
				objects.setFinalState(report.getFinalState());
				objects.setReportedDate(report.getReportedDate());
				objects.setReportPerson(report.getReportPerson());
				objects.setResolution(report.getResolution());
				objects.setResolutionDate(report.getResolutionDate());
				objects.setRootCause(report.getRootCause());
				objects.setOtherCause(report.getOtherCause());
				objects.setSeverity(report.getSeverity());
			   
				
			}
			
			Transaction tx = session.beginTransaction();
			session.update(objects);
			tx.commit();
			result = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.info("error while updating customer report for id: "+ report.getId());
		}finally{
			
			dbc.closeConnection();
		}
		
		
		
		return result;
	}
	
	
	public boolean deleteSelectedCustomerReport(String id){
		
		boolean isDeleted = false;
		
		long reportid = Long.parseLong(id);
		DBConnection dbc = null;
		String query="delete from customerReports where id="+reportid+"";
		
		
		
		try {
			
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(query);
			q.executeUpdate();
			isDeleted= true;
			
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.info("Error while deleting customer Report and id is : "+ id);
		}
		
		return isDeleted;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getCustomerReportObjects() {
		
		List<Object[]> objects = null;
		DBConnection dbc = null;
		String query="select cr.id,c.customerId,cr.reportPerson,DATE_FORMAT(cr.reportedDate,'%Y-%m-%d'),s.severityLevel,cr.issueDescription,cr.resolution,DATE_FORMAT(cr.resolutionDate,'%Y-%m-%d'),cr.allocatedPerson,cr.action,"+
		              " DATE_FORMAT(cr.actionDate,'%Y-%m-%d'),fs.name,DATE_FORMAT(cr.finalDate,'%Y-%m-%d') from customerReports as cr left join severity as s"+
				      " on s.id=cr.severity left join issuestatus as fs on fs.id=cr.finalState left join"+
		              " customer as c on c.id=cr.customerId order by fs.name";
						
		try {
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			objects = q.list();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return objects;
	}
	
@SuppressWarnings("unchecked")
public List<Object[]> getCustomerWithDeviceCount(String customerId) {
		
		List<Object[]> objects = null;
		DBConnection dbc = null;
		String query="select c.customerId,dt.name ,count(d.deviceType) from device as d left join gateway "+
		              "as g on d.gateWay=g.id left join customer as c on g.customer=c.id "+
				      "left join devicetype as dt on d.deviceType=dt.id where c.customerId='"+customerId+"' and dt.name NOT IN ('MODE_AWAY','MODE_STAY','MODE_HOME','Z_WAVE_ENERGY_MONITOR') group by d.deviceType";
		
		
		
		try {
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			objects = q.list();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			
			dbc.closeConnection();
		}
		
		
		return objects;
	}
	
	public String getIssueStatusNameByid(long id){
		
		DBConnection dbc = null;
		String query = "select fs.name from issuestatus as fs where fs.id="+id+"";
		String name = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			name = (String) q.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			
			dbc.closeConnection();
		}
		
		return name;
	}
	
public int getTotalAlertsCount() {
		
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(c)" + " from "
					+ "AlertMonitor as am join am.areaCode as ac join am.customer as c join am.rule as r "
					+ "where am.areaCode=ac.id and am.customer=c.id and am.rule=r.id";
		
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			count = lCount.intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
		return count;
	}

@SuppressWarnings("unchecked")
public List<Object[]> getListOfCustomerAlerts(String sSearch, String sOrder, int start,
		int length) {
	String query = "";
	
	
		
	query = "select am.id,am.alertTime,'dummy',ac.code,c.customerId,r.description,c.phoneNumber from alertMonitor" +
    		" as am " +
			" left join customer as c on am.customer=c.id "+
    		" left join areaCode as ac on am.areaCode=ac.id "+
			" left join rules as r on r.id=am.rule"+
    		" where ( c.customerId like '%"+sSearch+"%' " +
            		"or ac.code like '%" + sSearch + "%')" + sOrder;   
	

	
	DBConnection dbc = null;
	List<Object[]> objects = null;
	
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createSQLQuery(query);
		q.setMaxResults(length);
		q.setFirstResult(start);
		objects = q.list();
		
		
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.error(ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	
	
	return objects;
	
	
}

public int getTotalCustomerAlertCount(String sSearch) {
	DBConnection dbc = null;
	int count = 0;
	try {
		String query = "";
		query += "select count(am)" + " from "
				+ "AlertMonitor as am join am.customer as c "
				+ "join am.areaCode as ac "
				+ "where am.customer=c.id and am.areaCode=ac.id and (" + "c.customerId like '%"
				+ sSearch + "%' or ac.code like '%" + sSearch + "%' )";
		
		LogUtil.info("total count query: "+ query);
				
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		Long lCount = (Long) q.uniqueResult();
		count = lCount.intValue();
		
		LogUtil.info("count: "+ count);
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return count;
}


@SuppressWarnings("unchecked")
public List<?> getListOfCustomerAlertsWithStatus(String sSearch, String sOrder, int start,
		int length) {
	String query = "";
	
	
		
	query = "select am.id,am.alertTime,c.customerId,s.name,r.description,c.mobile,am.attended from alertMonitor" +
    		" as am " +
			" left join customer as c on am.customer=c.id "+
    		
			" left join rules as r on r.id=am.rule"+
    		" left join alertstatus as s on s.id=am.alertStatus "+
    		" where ( c.customerId like '%"+sSearch+"%' " +
            		"or am.attended like '%" + sSearch + "%' or s.name like '%"+sSearch+"%') order by am.id desc";   
	
    LogUtil.info("query: "+ query);
	
	DBConnection dbc = null;
	List<Object[]> objects = null;
	List objectss = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createSQLQuery(query);
		q.setMaxResults(length);
		q.setFirstResult(start);
		objects = q.list();
		
		
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.error(ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	
	
	return objects;
	
	
}
	
}
