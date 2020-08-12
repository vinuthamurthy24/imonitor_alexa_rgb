/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotification;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ImvgAlertNotificationForScedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScheduleAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 * 
 */

public class ScheduleManager {

	public boolean saveScheduleWithDetails(Schedule schedule) {
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Transaction tx = session.beginTransaction();
			session.save(schedule);
			tx.commit();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	public boolean updateScheduleWithDetails(Schedule schedule) {
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Transaction tx = session.beginTransaction();
			session.update(schedule);
			tx.commit();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	public boolean updateScheduleActionWithDetails(ScheduleAction scheduleaction) {
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Transaction tx = session.beginTransaction();
			session.update(scheduleaction);
			tx.commit();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	public boolean updateScheduleWithAction(Schedule schedule, String action) {
		/*String[] actions = action.split(Constants.EXPRESSION_OPERATOR_PATTERN);
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			db.beginTransaction();
			session.update(schedule);
			if(!actions.equals("")){
				for (int i = 0; i < actions.length; i++) {
					if (!actions[i].trim().isEmpty()) {
						String[] details = actions[i].split("=");
						Device device = new Device();
						device.setId(Long.parseLong(details[0]));
						ActionType actionType = new ActionType();
						ScheduleAction scheduleAction =new ScheduleAction();
						if(details[1].contains("_")){
							String[] batteryRange = details[1].split("_");
							actionType.setId(Long.parseLong(batteryRange[0]));
							scheduleAction.setLevel(batteryRange[1]);
							}else{
								actionType.setId(Long.parseLong(details[1]));
							}
						scheduleAction.setDevice(device);
						scheduleAction.setActionType(actionType);
						scheduleAction.setSchedule(schedule);
						session.save(scheduleAction);
					}
				}
			}
			// Updating the device.
			session.flush();
			db.commit();
			result =true;
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
			try {
				db.rollback();
			} catch (Exception ex1) {
				LogUtil.error(ex1.getMessage());
			}
		} finally {
			db.closeConnection();
		}
		return result;*/
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> listAskedSchedule(String sSearch,String sOrder, int start, int length,long customerId) {
		//String query = "select s.id, s.name, s.description, g.macId" +
		String query = "select s.id, s.name, s.description, g.macId, s.activationStatus" +
		" from Schedule as s " +
		" left join s.gateWay as g" +
		" left join g.customer as c" +
		" where c.id = " + customerId + 
		" and  s.name not like 'HMD:%' and s.scheduleType = '0'"+
//vibhu start
		//" and (s.name like '%" + sSearch + "%' or s.description like '%" + sSearch + "%' or g.macId like '%" + sSearch + "%')" + sOrder;
		" and (s.name like '%" + sSearch + "%' or s.description like '%" + sSearch + "%' )" + sOrder;
//vibhu end		
		List<Object[]> detailsArray = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			q.setMaxResults(length);
			q.setFirstResult(start);
			detailsArray = q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return detailsArray;
	}
	
	public int getScheduleCountByCustomerId(String sSearch,long customerId){
		String query = "select count(s)" +
		" from Schedule as s " +
		" left join s.gateWay as g" +
		" left join g.customer as c" +
		" where c.id = " + customerId +
//vibhu start		
		//" and (s.name like '%" + sSearch + "%' or s.description like '%" + sSearch + "%' or g.macId like '%" + sSearch + "%')";
		" and (s.name like '%" + sSearch + "%' or s.description like '%" + sSearch + "%' )";
//vibhu end
		Long count = new Long(0);
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			count = (Long) q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return count.intValue();
	}

	public int getTotalScheduleCountByCustomerId(long id){
		String query = "select count(s)" +
		" from Schedule as s " +
		" left join s.gateWay as g" +
		" left join g.customer as c" +
		" where c.id = " + id;
		
		Long count = new Long(0);
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			count = (Long) q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return count.intValue();
	}
	
	public boolean deleteScheduleActionsAndScheduleTimeByScheduleId(long id) {
//		DBConnection dbc = null;
//		try {
//			dbc = new DBConnection();
//			String hql =  "delete from ScheduleAction where schedule ="+id;
//			Session session = dbc.openConnection();
//			dbc.beginTransaction();
//			Query query = session.createQuery(hql);
//		    int rowCount = query.executeUpdate();
//		    dbc.commit();
//		    return (rowCount>0)?true:false;
//		}catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			dbc.closeConnection();
//		}
		return false;
	}
	public boolean deleteSchedule(Schedule schedule) {
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Transaction tx = session.beginTransaction();
			session.delete(schedule);
			tx.commit();
			result = true;
			{
				DaoManager dm = new DaoManager();
				dm.executeHQLUpdateQuery("delete from ImvgAlertNotificationForScedule where schedule is NULL");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	
	public boolean deleteScheduleAction(ScheduleAction scheduleaction) {
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Transaction tx = session.beginTransaction();
			session.delete(scheduleaction);
			tx.commit();
			result = true;
			{
				DaoManager dm = new DaoManager();
				dm.executeHQLUpdateQuery("delete from ImvgAlertNotificationForSceduleaction where scheduleaction is NULL");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	
	public List<Object[]> listScheduleActionsByTime(int houre,int minute,int day) {
//		DBConnection dbc = null;
//		List<Object[]> iaObjects =null;
//		String	query = "select d.id,d.generatedDeviceId,g.macId,at.command,ag.ip, ag.controllerReceiverPort, ag.ftpIp, ag.ftpPort" +
//		" ,ag.ftpUserName, ag.ftpPassword, c.customerId, sa.level,sa.schedule.id" +
//		" from ScheduleAction as sa"+
//		" left join sa.actionType as at "+
//		" left join sa.device as d "+
//		" left join d.gateWay as g "+
//		" left join g.agent as ag"+
//		" left join g.customer as c"+
//		" where sa.schedule.id in (select s.id" +
//	" from ScheduleTime as st " +
//	" left join st.schedule s"+
//	" where st.houre="+houre+" and st.minute="+minute+" and st.scheduledDay ="+day+")";
//		try {
//			dbc = new DBConnection();
//			Session session = dbc.openConnection();
//			Query q = session.createQuery(query);
//			iaObjects = q.list();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			dbc.closeConnection();
//		}
//		return iaObjects;
		return null;
	}
	
	public static Schedule getScheduleById(long scheduleId) {
		DaoManager daoManager=new DaoManager();
		return (Schedule) daoManager.get(scheduleId,Schedule.class);
		
	}
	public static ScheduleAction getScheduleActionById(long scheduleId) {
		DaoManager daoManager=new DaoManager();
		return (ScheduleAction) daoManager.get(scheduleId,ScheduleAction.class);
		
	}
	
	
	public Schedule getScheduleWithCustomerByScheduleId(String scheduleId) {
//		1. Get the rule.
//		DBConnection dbc = null;
//		String query = "select s.id,s.name,s.description,s.scheduleTime from Schedule as s where s.id =" +scheduleId;
//		Schedule schedule = new Schedule();
//		try {
//			dbc = new DBConnection();
//			Session session = dbc.openConnection();
//			Query q = session.createQuery(query);
//			Object[] object = (Object[]) q.uniqueResult();
//			schedule.setId(Long.parseLong(object[0].toString()));
//			schedule.setName(object[1].toString());
//			schedule.setDescription(object[2].toString());
//			schedule.setScheduleTime(object[3].toString());
//			//schedule.setScheduleTimes(new HashSet<ScheduleTime>());
//			schedule.setScheduleActions(new HashSet<ScheduleAction>());
//			//schedule =  (Schedule) q.uniqueResult();
//			//scheduleId.setImvgAlertsActions(new ArrayList<ImvgAlertsAction>());
//			if(schedule != null){
//				query = "select ia.id,ia.level,d.id,d.friendlyName,at.id,at.command"+
//				" from ScheduleAction as ia left join ia.device as d "+
//				" left join ia.actionType as at where ia.schedule="+scheduleId;
//				q = session.createQuery(query);
//				List<Object[]> iaObjects = q.list();
//				for (Object[] objects : iaObjects) {
//					ScheduleAction scheduleAction =new ScheduleAction();
//					long id = (Long)objects[0];
//					scheduleAction.setId(id);
//					scheduleAction.setLevel((String) objects[1]);
//					Device device = new Device();
//					id = (Long) objects[2];
//					device.setId(id);
//					device.setFriendlyName(IMonitorUtil.convertToString(objects[3]));
//					scheduleAction.setDevice(device);
//					ActionType actionType = new ActionType();
//					id = (Long) objects[4];
//					actionType.setId(id);
//					actionType.setCommand(IMonitorUtil.convertToString(objects[5]));
//					scheduleAction.setActionType(actionType);
//					schedule.getScheduleActions().add(scheduleAction);
//				}
//				/*query = "select st.id,st.houre,st.minute,st.scheduledDay"+
//				" from ScheduleTime as st where st.schedule="+scheduleId;
//				q = session.createQuery(query);
//				//List<Object[]> timeObjects = q.list();
//				/*for (Object[] objects : timeObjects) {
//					ScheduleTime scheduleTime = new ScheduleTime();
//					scheduleTime.setHoure((String) objects[1]);
//					scheduleTime.setMinute((String) objects[2]);
//					scheduleTime.setScheduledDay((String) objects[3]);
//					schedule.getScheduleTimes().add(scheduleTime);
//				}*/
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			schedule = null;
//		} finally {
//			dbc.closeConnection();
//		}
//		return schedule;
		return null;
	}

	@SuppressWarnings("unchecked")
	public Schedule retrieveScheduleDetails(long id) {
		
		Schedule schedule = null;
		String query = "select s.id, s.name,s.description, s.scheduleTime," +
				" g.id, g.macId," +
				" sa.id, sa.level," +
				" d.id, d.deviceId, d.generatedDeviceId, d.friendlyName," +
				" at.id, at.name, at.command," +
				" a.id, a.ip, a.controllerReceiverPort, a.ftpIp, a.ftpPort, a.ftpNonSecurePort, a.ftpUserName, a.ftpPassword," +
				" c.customerId, s.activationStatus, s.endSchedule, s.scheduleType" +
				" from Schedule as s " +
				" left join s.gateWay as g" +
				" left join s.scheduleActions as sa" +
				" left join sa.device as d" +
				" left join sa.actionType as at" +
				" left join g.agent as a" +
				" left join g.customer as c" +
				" where s.id=" + id;

		
		List<Object[]> detailsArray = null;
		DBConnection dbc = null;
		try {
			XStream stream = new XStream();
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			detailsArray = q.list();
			
			
			schedule = new Schedule();
			
			boolean isFirst = true;
			for (Object[] details : detailsArray) {
				if (isFirst) {
					Long scheduleId = (Long) details[0];
					String scheduleName = (String) details[1];
					String scheduleDescription = (String) details[2];
					String scheduleTime = (String) details[3];
					Long gateWayId = (Long) details[4];
					String gateWayMacId = (String) details[5];
					Long agentId = (Long) details[15];
					String agentIp = (String) details[16];
					int aPort = (Integer) details[17];
					String ftpIp = (String) details[18];
					int ftpPort = (Integer) details[19];
					int ftpNonSecPort = (Integer) details[20];
					String ftpUserName = (String) details[21];
					String ftpPassword = (String) details[22];
					String customerId = (String) details[23];
					int activationStatus = (Integer) details[24];	//sumit added: Schedule Activation/De-Activation
					Long endSchedule = (Long) details[25];
					int type = (Integer) details[26];
					Agent agent = new Agent();
					agent.setId(agentId);
					agent.setIp(agentIp);
					agent.setControllerReceiverPort(aPort);
					agent.setFtpIp(ftpIp);
					agent.setFtpPort(ftpPort);
					agent.setFtpNonSecurePort(ftpNonSecPort);
					agent.setFtpUserName(ftpUserName);
					agent.setFtpPassword(ftpPassword);
					schedule.setId(scheduleId);
					schedule.setName(scheduleName);
					schedule.setDescription(scheduleDescription);
					schedule.setScheduleTime(scheduleTime);
					schedule.setActivationStatus(activationStatus);	//sumit added this line
					schedule.setEndSchedule(endSchedule);
					schedule.setScheduleType(type);
					
					
					
					GateWay gateWay = new GateWay();
					gateWay.setId(gateWayId);
					gateWay.setMacId(gateWayMacId);
					gateWay.setAgent(agent);
					Customer customer = new Customer();
					customer.setCustomerId(customerId);
					gateWay.setCustomer(customer);
					schedule.setGateWay(gateWay);
					Set<ScheduleAction> scheduleActions = new LinkedHashSet<ScheduleAction>();
					schedule.setScheduleActions(scheduleActions);
					
					
				}
				
				isFirst = false;
				Long scheduleActionId = (Long) details[6];
				if (scheduleActionId != null) {
					Set<ScheduleAction> scheduleActions = schedule.getScheduleActions();
					ScheduleAction scheduleAction = null;
					for (ScheduleAction scheduleAction1 : scheduleActions) {
						if (scheduleAction1.getId() == scheduleActionId) {
							scheduleAction = scheduleAction1;
							break;
						}
					}
					if (scheduleAction == null) {
						scheduleAction = new ScheduleAction();
						scheduleAction.setId(scheduleActionId);
						String imvgActionLevel = (String) details[7];
						scheduleAction.setLevel(imvgActionLevel);
						
						//******************************************* sumit start ****************************************
						//Long deviceId = (Long) details[8];
						Long dId = (Long) details[8];
						String deviceId = (String) details[9];
						String generatedDeviceId = (String) details[10];
						String friendlyName = (String) details[11];
						
						Long actionTypeId = (Long) details[12];
						String actionTypeName = (String) details[13];
						String actionTypeCommand = (String) details[14];
						
						Device device = new Device();
						device.setId(dId);
						device.setDeviceId(deviceId);
						// ******************************************** sumit end ****************************************
						device.setGeneratedDeviceId(generatedDeviceId);
						device.setFriendlyName(friendlyName);
						scheduleAction.setDevice(device);
						
						ActionType actionType = new ActionType();
						actionType.setId(actionTypeId);
						actionType.setName(actionTypeName);
						actionType.setCommand(actionTypeCommand);
						scheduleAction.setActionType(actionType);
						
						scheduleActions.add(scheduleAction);
						LogUtil.info("scheduleActions"+scheduleActions);
						
					}
				}
				
			}
			
			
			query = "select ian.id, ian.NotificationCheck, ian.WhatsApp, u.id, u.name, u.recipient," +
					" at.id, at.name, at.command, at.details" +
					" from ImvgAlertNotificationForScedule as ian" +
					" left join ian.userNotificationProfile as u" +
					" left join u.actionType as at" +
					" left join ian.schedule as s" +
					" where s.id=" + id;
					q = session.createQuery(query);
					List<Object[]> results = q.list();
					Set<ImvgAlertNotificationForScedule> imvgAlertNotificationsForSchedule = new HashSet<ImvgAlertNotificationForScedule>();
					for (Object[] objects : results) {
						Long ianId = (Long) objects[0];
						String notificationcheck = (String) objects[1];
						Long wsappCheck = (Long) objects[2];
						ImvgAlertNotificationForScedule imvgAlertNotification = new ImvgAlertNotificationForScedule();
						imvgAlertNotification.setId(ianId);
						imvgAlertNotification.setNotificationCheck(notificationcheck);
						imvgAlertNotification.setWhatsApp(wsappCheck);
						
						Long uId = (Long) objects[3];
						String uName = (String) objects[4];
						String uReceipient = (String) objects[5];
						UserNotificationProfile userNotificationProfile = new UserNotificationProfile();
						userNotificationProfile.setId(uId);
						userNotificationProfile.setName(uName);
						userNotificationProfile.setRecipient(uReceipient);
						imvgAlertNotification.setUserNotificationProfile(userNotificationProfile);
						
						Long atId = (Long) objects[6];
						String atName = (String) objects[7];
						String atCommand = (String) objects[8];
						String atDetails = (String) objects[9];
						ActionType actionType = new ActionType();
						actionType.setId(atId);
						actionType.setName(atName);
						actionType.setCommand(atCommand);
						actionType.setDetails(atDetails);
						userNotificationProfile.setActionType(actionType);
						imvgAlertNotification.setUserNotificationProfile(userNotificationProfile);
						imvgAlertNotificationsForSchedule.add(imvgAlertNotification);
					}
					
					schedule.setImvgAlertNotificationsForScedule(imvgAlertNotificationsForSchedule);
					
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		
		XStream stream = new XStream();
		//LogUtil.info("schedule"+stream.toXML(schedule));
		return schedule;
	}

	//parishod added to check device present in schedules
	public long getScheduleCountByGeneratedDeviceId(String generatedDeviceId){
		//Query for criteria device
		//Query for action device
		String query2 = "select count(sch.id)"
				+ " from scheduleaction sch, device d"
				+ " where  sch.schedule is not NULL and sch.device=d.id and d.generatedDeviceId='"+generatedDeviceId+"'";
		long scheduleCount = 0;
		
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			
			Query q2 = session.createSQLQuery(query2);
			String c2 = q2.uniqueResult().toString();
			scheduleCount = Long.parseLong(c2);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("Error while getting rule count for device.");
		}finally{
			dbc.closeConnection();
		}
		return scheduleCount;
	}
	//pari end
	//bhavya start
	@SuppressWarnings("unchecked")
	public Schedule retrieveScheduleDetailsfromgenerateddeviceid(String generateddeviceid) {
		Schedule schedule = null;
		String query = "select s.id, s.name,s.description, s.scheduleTime" +
				" from Schedule as s " +
				" where s.description='" + generateddeviceid +"'";

		List<Object[]> detailsArray = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			detailsArray = q.list();
			schedule = new Schedule();
			
			for (Object[] details : detailsArray) {
					Long scheduleId = (Long) details[0];
					String scheduleName = (String) details[1];
					String scheduleDescription = (String) details[2];
					String scheduleTime = (String) details[3];
					
					schedule.setId(scheduleId);
					schedule.setName(scheduleName);
					schedule.setDescription(scheduleDescription);
					schedule.setScheduleTime(scheduleTime);
					}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return schedule;
	}
	
	//Schedule API
		@SuppressWarnings("unchecked")
		public List<Object[]> listAllSchedulesByCustomerId(String customerId) {
			String query = "";
			/*query += "select s.id,s.name,s.description, d.generatedDeviceId, at.command, sa.level" +
					" from Scenario as s" +
					" left join s.gateWay as g" +
					" left join g.customer as c" +
			" join s.scenarioActions as sa" +
			" join sa.device as d" +
			" join sa.actionType at " +
			" where c.customerId='" + customerId + "'";*/
			
			query += "select s.id,s.name,s.description, d.generatedDeviceId, at.command, sa.level,s.activationStatus " +
					" from Schedule as s" +
					" left join s.gateWay as g" +
					" left join g.customer as c" +
			" join s.scheduleActions as sa" +
			" join sa.device as d" +
			" join sa.actionType at " +
			" where c.customerId='" + customerId + "'";
			
			DBConnection dbc = null;
			List<Object[]> objects =null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				objects= q.list();
			} catch (Exception ex) {
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return objects;
		}
	
	public long getscheduleidfromschedulename(String paramString1, String paramString2)
	  {
	    String str = "";
	    str = "select s.id from schedule as s left join gateway as g on s.gateWay=g.id left join customer as c on g.customer=c.id where s.name='" + paramString1 + "' and c.customerId='" + paramString2 + "'";
	    DBConnection localDBConnection = null;
	    long l = new Long(0L).longValue();
	    try
	    {
	      localDBConnection = new DBConnection();
	      Session localSession = localDBConnection.openConnection();
	      Query q = localSession.createSQLQuery(str);
	      BigInteger localBigInteger = (BigInteger)q.uniqueResult();
	      l = localBigInteger.longValue();
	    }
	    catch (Exception localException)
	    {
	      LogUtil.error(localException.getMessage());
	    }
	    finally
	    {
	      localDBConnection.closeConnection();
	    }
	    return l;
	  }
	
	
	//SuperUser changes for activating Schedule
	
	@SuppressWarnings("unchecked")
	public List<Long> getscheduleidsfromschedulenameForSuperUser(String paramString1, String paramString2)
	  {
	    String str = "";
	    str = "select s.id,s.name from schedule as s left join gateway as g on s.gateWay=g.id left join customer as c on g.customer=c.id where s.name='" + paramString1 + "' and c.customerId='" + paramString2 + "'";
	
	    DBConnection localDBConnection = null;
	    List<Object[]> objects =null;
	    List<Long> scheduleIdsList= new ArrayList<Long>();
	    try
	    {
	      localDBConnection = new DBConnection();
	      Session localSession = localDBConnection.openConnection();
	      Query q = localSession.createSQLQuery(str);
	      objects = q.list();	
	      if(!objects.isEmpty())
	      {
	    	  for (Object[] objFromDb : objects) 
	    	  {
	    		  BigInteger bigInteger=(BigInteger) objFromDb[0];
	  			long id=bigInteger.longValue();
	  			scheduleIdsList.add(id);
	    	  }
	      }
	    }
	    catch (Exception localException)
	    {
	      LogUtil.error(localException.getMessage());
	      localException.printStackTrace();
	    }
	    finally
	    {
	      localDBConnection.closeConnection();
	    }
	    return scheduleIdsList;
	  }
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listAllSchedulesByCustomerIdWithoutEndSchedule(String customerId) {
		XStream stream = new XStream();
		
		String query = "";
		/*query += "select s.id,s.name,s.description, d.generatedDeviceId, at.command, sa.level" +
				" from Scenario as s" +
				" left join s.gateWay as g" +
				" left join g.customer as c" +
		" join s.scenarioActions as sa" +
		" join sa.device as d" +
		" join sa.actionType at " +
		" where c.customerId='" + customerId + "'";*/
		
		/*query += "select s.id,s.name,s.description, d.generatedDeviceId, at.command, sa.level,s.activationStatus " +
				" from Schedule as s" +
				" left join s.gateWay as g" +
				" left join g.customer as c" +
		" join s.scheduleAction as sa" +
		" join sa.device as d" +
		" join sa.actionType at " +
		" where c.customerId='" + customerId + "' and s.endSchedule=0";*/
	query=	"select s.id,s.name,s.description, d.generatedDeviceId, at.command, sa.level,s.activationStatus from schedule as s left join gateway as g on s.gateWay=g.id left join customer as c on g.customer=c.id left join scheduleaction as sa on sa.schedule=s.id left join device as d on sa.device=d.id left join actionType as at on sa.actionType=at.id where c.customerId='" + customerId + "'";
	LogUtil.info("query"+query);
		DBConnection dbc = null;
		List<Object[]> objects =null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			objects= q.list();
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		LogUtil.info("objects"+stream.toXML(objects));
		return objects;
	}
	
	//bhavya end

	
	
	public boolean saveSchedule(Schedule schedule) {
		LogUtil.info("saveSchedule =");
		XStream stream =new XStream();
		boolean result = false;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Transaction tx = session.beginTransaction();
			session.save(schedule);
			tx.commit();
			session.flush();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
			try {
				dbc.rollback();
			} catch (Exception ex1) {
				LogUtil.error(ex1.getMessage());
			}
		} finally {
			dbc.closeConnection();
		}
		LogUtil.info("result ="+stream.toXML(result));
		return result;
	}
	
	
	
	public boolean saveScheduleAction(ScheduleAction scheduleaction) {
		LogUtil.info("saveScheduleaction =");
		XStream stream =new XStream();
		boolean result = false;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Transaction tx = session.beginTransaction();
			session.save(scheduleaction);
			tx.commit();
			session.flush();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
			try {
				dbc.rollback();
			} catch (Exception ex1) {
				LogUtil.error(ex1.getMessage());
			}
		} finally {
			dbc.closeConnection();
		}
		LogUtil.info("result ="+stream.toXML(result));
		return result;
	}
	
	
	
	
	public String getIdOfschedule(long gid,String schedulename){
		XStream stream = new XStream();
		DBConnection dbc = null;
		String result = null;
		try {
			String query = "select id" +
					" from Schedule as s" +
					" where s.gateWay='" + gid + "'and s.name='"+schedulename+"'" ;
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			LogUtil.info("result q="+stream.toXML(q));
			result = IMonitorUtil.convertToString(q.uniqueResult());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		LogUtil.info("result ="+stream.toXML(result));
		return result;
		
	}
	
	public ScheduleAction getIdOfscheduleAction(long sid){
		XStream stream = new XStream();
		DBConnection dbc = null;
		ScheduleAction scheduleAction = null;
		try {
			String query = "select id" +
					" from scheduleaction as s" +
					" where s.schedule='" + sid +" '" ;
			LogUtil.info("querry"+query);
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			LogUtil.info("result q="+stream.toXML(q));
			scheduleAction = (ScheduleAction) q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		LogUtil.info("result ="+stream.toXML(scheduleAction));
		return scheduleAction;
		
	}
	
	
	
	
	
	
}
