/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.action.ActionExecutor;
import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScenarioAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScheduleAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserDeviceAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserScenarioAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


/**
 * @author Coladi
 * 
 */

public class ScenarioManager {

	public boolean saveScheduleWithAction(Scenario scenario, String action) {
		String[] actions = action.split(Constants.EXPRESSION_OPERATOR_PATTERN);
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			db.beginTransaction();
			session.save(scenario);
			if(!actions.equals("")){
				for (int i = 0; i < actions.length; i++) {
					if (!actions[i].trim().isEmpty()) {
						String[] details = actions[i].split("=");
						Device device = new Device();
						device.setId(Long.parseLong(details[0]));
						ActionType actionType = new ActionType();
						ScenarioAction scenarioAction = new ScenarioAction();
						if(details[1].contains("_")){
							String[] batteryRange = details[1].split("_");
							actionType.setId(Long.parseLong(batteryRange[0]));
							scenarioAction.setLevel(batteryRange[1]);
							}else{
								actionType.setId(Long.parseLong(details[1]));
							}
						scenarioAction.setDevice(device);
						scenarioAction.setActionType(actionType);
						scenarioAction.setScenario(scenario);
						session.save(scenarioAction);
					}
				}
			}
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
		return result;
	}
	
	public boolean updateScenarioWithAction(Scenario scenario, String action) {
		String[] actions = action.split(Constants.EXPRESSION_OPERATOR_PATTERN);
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			db.beginTransaction();
			session.update(scenario);
			if(!actions.equals("")){
				for (int i = 0; i < actions.length; i++) {
					if (!actions[i].trim().isEmpty()) {
						String[] details = actions[i].split("=");
						Device device = new Device();
						device.setId(Long.parseLong(details[0]));
						ActionType actionType = new ActionType();
						ScenarioAction scenarioAction = new ScenarioAction();
						if(details[1].contains("_")){
							String[] batteryRange = details[1].split("_");
							actionType.setId(Long.parseLong(batteryRange[0]));
							scenarioAction.setLevel(batteryRange[1]);
							}else{
								actionType.setId(Long.parseLong(details[1]));
							}
						scenarioAction.setDevice(device);
						scenarioAction.setActionType(actionType);
						scenarioAction.setScenario(scenario);
						session.save(scenarioAction);
					}
				}
			}
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
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listAllScenariosByCustomerId(long id) {
		String query = "";
        query += "select s.id,s.name,s.description,s.iconFile " 
    			+ " from Scenario as s" 
        		+ " left join s.gateWay as g" 
				+ " left join g.customer as c" 
                + " where c.id="+id
                + " order by s.name ";
                DBConnection dbc = null;
                List<Object[]> scenarios =null;
                
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			scenarios= q.list();
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return scenarios;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listAllScenariosByCustomerId(String customerId) {
		String query = "";
		query += "select s.id,s.name,s.description, d.generatedDeviceId, at.command, sa.level" +
				" from Scenario as s" +
				" left join s.gateWay as g" +
				" left join g.customer as c" +
		" join s.scenarioActions as sa" +
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
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listScenariosByScheduleId(long id) {
		String query = "";
        query += "select s.scenarioId,s.scheduledId from " +
                "ScheduledScenarios as s " +
                "where s.scheduledId="+id;
                DBConnection dbc = null;
                List<Object[]> scenarios =null;
                
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			scenarios= q.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return scenarios;
	}
	
	
	public List<?> listAskedScnarios(String sSearch,String sOrder, int start, int length,long id) {
		DaoManager daoManager=new DaoManager();
		String query = "";
        query += "select s.id,s.name,s.description, g.macId from " +
                "Scenario as s " +
                " left join s.gateWay as g " +
                " left join g.customer as c" +
//vibhu start
				//" where c.id=" + id + " and (s.name like '%"+sSearch+"%' or s.description like '%"+sSearch+"%' or g.macId like '%"+sSearch+"%') "+sOrder;
        		" where c.id=" + id + " and (s.name like '%"+sSearch+"%' or s.description like '%"+sSearch+"%' ) "+sOrder;
//vibhu end        
		List<?> list = daoManager.listAskedObjects(query,start,length,Scenario.class);
		return list;
	}
	
	public int getScenarioCountByCustomerId(String sSearch,long id){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(s)" +
        		" from  Scenario as s " +
        		" left join s.gateWay as g " +
        		" left join g.customer as c" +
                " where c.id=" + id + " and (s.name like '%"+sSearch+"%' or s.description like '%"+sSearch+"%' or g.macId like '%"+sSearch+"%')";
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
	public int getTotalScenarioCountByCustomerId(long id){
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(s) from Scenario as s " +
					" left join s.gateWay as g " +
					" left join g.customer as c" +
                " where c.id=" + id ;
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

	
	public boolean deleteScenarioActionsAndScheduleTimeByScheduleId(long id) {
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			String hql =  "delete from ScenarioAction where scenario ="+id;
			Session session = dbc.openConnection();
			dbc.beginTransaction();
			Query query = session.createQuery(hql);
		    int rowCount = query.executeUpdate();
		    dbc.commit();
		    return (rowCount>0)?true:false;
		}catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return false;
	}

	public boolean deleteScenario(Scenario scenario) {
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Transaction tx = session.beginTransaction();
			session.delete(scenario);
			tx.commit();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Deprecated
	public boolean executeScenarioActionsBySenarioId(long id) {
		boolean result = false;
		DBConnection dbc = null;
		List<Object[]> iaObjects =null;
		String	query = "select d.id,d.generatedDeviceId,g.macId,at.command,ag.ip, ag.controllerReceiverPort, ag.ftpIp, ag.ftpPort" +
	" ,ag.ftpUserName, ag.ftpPassword, c.customerId, sa.level, ag.ftpNonSecurePort" +
	" from ScenarioAction as sa"+
	" left join sa.actionType as at "+
	" left join sa.device as d "+
	" left join d.gateWay as g "+
	" left join g.agent as ag"+
	" left join g.customer as c"+
	" where sa.scenario ="+id;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			iaObjects = q.list();
			for (Object[] objects : iaObjects) {
				String deviceId = IMonitorUtil.convertToString(objects[1]);
				String macId = IMonitorUtil.convertToString(objects[2]);
				String command = IMonitorUtil.convertToString(objects[3]);
				String ip = IMonitorUtil.convertToString(objects[4]);
				String portS = IMonitorUtil.convertToString(objects[5]);
				String ftpIp = IMonitorUtil.convertToString(objects[6]);
				int ftpPort = (Integer)objects[7];
				int ftpNonSecurePort = (Integer)objects[12];
				String ftpUser = IMonitorUtil.convertToString(objects[8]);
				String ftpPass = IMonitorUtil.convertToString(objects[9]);
				String customerId = IMonitorUtil.convertToString(objects[10]);
				int port = Integer.parseInt(portS);
				Device cDevice = new Device();
				GateWay cGateWay = new GateWay();
				Agent cAgent = new Agent();
				cDevice.setGeneratedDeviceId(deviceId);
				cGateWay.setMacId(macId);
				cAgent.setIp(ip);
				cAgent.setControllerReceiverPort(port);
				cAgent.setFtpIp(ftpIp);
				cAgent.setFtpPort(ftpPort);
				cAgent.setFtpNonSecurePort(ftpNonSecurePort);
				cAgent.setFtpUserName(ftpUser);
				cAgent.setFtpPassword(ftpPass);
				cGateWay.setAgent(cAgent);
				Customer customer = new Customer();
				customer.setCustomerId(customerId);
				cGateWay.setCustomer(customer);
				cDevice.setGateWay(cGateWay);
				if(objects[11] != null){
					String level = IMonitorUtil.convertToString(objects[11]);
					cDevice.setCommandParam(level);
				}
				ActionModel actionModel = new ActionModel(cDevice, null);
				ActionExecutor actionExecutor = new ActionExecutor(command, actionModel);
				Thread t = new Thread(actionExecutor);
				t.start();
			}
			result = true;
		} catch (Exception ex) {
			LogUtil.error("Error when executing scenario by id: "+ex.getMessage());
			LogUtil.info("EXCEPTION", ex);
		} finally {
			dbc.closeConnection();
		}
		return result;
	}
	
	public Scenario getScenarioWithGatewayById(long scenarioId) 
	{
		Scenario sc = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			sc = (Scenario) session.get(Scenario.class, scenarioId);
			if (null != sc) {
				Hibernate.initialize(sc);
				Hibernate.initialize(sc.getGateWay());
				Hibernate.initialize(sc.getGateWay().getAgent());
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving scenario by id : "
					+ e.getMessage());
			LogUtil.info("", e);
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return sc;
	}
	
	public Scenario getScenarioWithGatewayByNameAndCustId(String CustomerId,String name) 
	{
		Scenario sc = null;
		DBConnection dbc = null;
		String	query = "select s " +
						" from Scenario as s"+
						" left join s.gateWay as g"+
						" left join g.customer as c"+
						" where s.name ='"+name+"'"+
						" and c.customerId ='"+CustomerId+"'";
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			sc = (Scenario)q.uniqueResult();
			if (null != sc) {
				Hibernate.initialize(sc);
				Hibernate.initialize(sc.getGateWay());
				Hibernate.initialize(sc.getGateWay().getAgent());
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving scenario by name and customerId : "
					+ e.getMessage());
			LogUtil.info("", e);
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return sc;
	}
	
	@SuppressWarnings("unchecked")
	public boolean executeScenarioActionsBySenarioName(String customerId, String scenarioName) {
		boolean result = false;
		DBConnection dbc = null;
		List<Object[]> iaObjects =null;
		String	query = "select d.id,d.generatedDeviceId,g.macId,at.command,ag.ip, ag.controllerReceiverPort, ag.ftpIp, ag.ftpPort" +
		" ,ag.ftpUserName, ag.ftpPassword, c.customerId, sa.level, ag.ftpNonSecurePort" +
		" from ScenarioAction as sa"+
		" left join sa.actionType as at "+
		" left join sa.device as d "+
		" left join d.gateWay as g "+
		" left join g.agent as ag"+
		" left join g.customer as c"+
		" left join sa.scenario as scn"+
		" where scn.name = '" + scenarioName + "'" +
		" and c.customerId='" + customerId + "'";
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			iaObjects = q.list();
			if(iaObjects == null || iaObjects.size() == 0){
				result = false;
			}else{
				result = true;
			}
			for (Object[] objects : iaObjects) {
				String deviceId = IMonitorUtil.convertToString(objects[1]);
				String macId = IMonitorUtil.convertToString(objects[2]);
				String command = IMonitorUtil.convertToString(objects[3]);
				String ip = IMonitorUtil.convertToString(objects[4]);
				String portS = IMonitorUtil.convertToString(objects[5]);
				String ftpIp = IMonitorUtil.convertToString(objects[6]);
				int ftpPort = (Integer)objects[7];
				int ftpNonSecurePort = (Integer)objects[12];
				String ftpUser = IMonitorUtil.convertToString(objects[8]);
				String ftpPass = IMonitorUtil.convertToString(objects[9]);
				int port = Integer.parseInt(portS);
				Device cDevice = new Device();
				GateWay cGateWay = new GateWay();
				Agent cAgent = new Agent();
				cDevice.setGeneratedDeviceId(deviceId);
				cGateWay.setMacId(macId);
				cAgent.setIp(ip);
				cAgent.setControllerReceiverPort(port);
				cAgent.setFtpIp(ftpIp);
				cAgent.setFtpPort(ftpPort);
				cAgent.setFtpNonSecurePort(ftpNonSecurePort);
				cAgent.setFtpUserName(ftpUser);
				cAgent.setFtpPassword(ftpPass);
				cGateWay.setAgent(cAgent);
				Customer customer = new Customer();
				customer.setCustomerId(customerId);
				cGateWay.setCustomer(customer);
				cDevice.setGateWay(cGateWay);
				if(objects[11] != null){
					String level = IMonitorUtil.convertToString(objects[11]);
					cDevice.setCommandParam(level);
				}
				ActionModel actionModel = new ActionModel(cDevice, null);
				ActionExecutor actionExecutor = new ActionExecutor(command, actionModel);
				Thread t = new Thread(actionExecutor);
				t.start();
			}
		} catch (Exception ex) {
			LogUtil.error("Error when executing scenario : " + scenarioName + " of customer " + customerId + " " + ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return result ;
	}
	
	public Scenario getScenarioById(long scenarioId) {
		DaoManager daoManager=new DaoManager();
		return (Scenario) daoManager.get(scenarioId,Scenario.class);
	}
	
	@SuppressWarnings("unchecked")
	public Scenario getScenarioWithCustomerByScenarioId(String scenarioId) {
//		1. Get the Scenario.
		DBConnection dbc = null;
		String query = "select s.id,s.name,s.description,s.id from Scenario as s where s.id =" +scenarioId;
		Scenario scenario = new Scenario();
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Object[] object = (Object[]) q.uniqueResult();
			scenario.setId(Long.parseLong(object[0].toString()));
			scenario.setName(object[1].toString());
			scenario.setDescription(object[2].toString());
			scenario.setScenarioActions(new HashSet<ScenarioAction>());
			//schedule =  (Schedule) q.uniqueResult();
			//scheduleId.setImvgAlertsActions(new ArrayList<ImvgAlertsAction>());
			if(scenario != null){
				query = "select ia.id,ia.level,d.id,d.friendlyName,at.id,at.command"+
				" from ScenarioAction as ia left join ia.device as d "+
				" left join ia.actionType as at where ia.scenario="+scenarioId;
				q = session.createQuery(query);
				List<Object[]> iaObjects = q.list();
				for (Object[] objects : iaObjects) {
					ScenarioAction scenarioAction = new ScenarioAction();
					long id = (Long)objects[0];
					scenarioAction.setId(id);
					scenarioAction.setLevel((String) objects[1]);
					Device device = new Device();
					id = (Long) objects[2];
					device.setId(id);
					device.setFriendlyName(IMonitorUtil.convertToString(objects[3]));
					scenarioAction.setDevice(device);
					ActionType actionType = new ActionType();
					id = (Long) objects[4];
					actionType.setId(id);
					actionType.setCommand(IMonitorUtil.convertToString(objects[5]));
					scenarioAction.setActionType(actionType);
					scenario.getScenarioActions().add(scenarioAction);
				}
			}
		} catch (Exception ex) {
			LogUtil.error("Error when listing scenario for id " + scenarioId);
			scenario = null;
		} finally {
			dbc.closeConnection();
		}
		return scenario;
	}

	@SuppressWarnings("unchecked")
	public Scenario getScenario(String customerId, String scenarioName) {
		DBConnection dbc = null;
		String query = "select s.id,s.name,s.description,s.id from Scenario as s " +
		" left join s.customer as c" +
		" where s.name ='Scenario1'" +
		" and c.customerId='p1'";
		Scenario scenario = new Scenario();
        try {
        	dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Object[] results = (Object[]) q.uniqueResult();
			if(results == null){
				dbc.closeConnection();
				return null;
			}
			long scenarioId = (Long)results[0];
			scenario.setId(scenarioId);
			scenario.setName(results[1].toString());
			scenario.setDescription(results[2].toString());
			scenario.setScenarioActions(new HashSet<ScenarioAction>());
			query = "select ia.id,ia.level,d.id,d.friendlyName,at.id,at.command"+
			" from ScenarioAction as ia left join ia.device as d "+
			" left join ia.actionType as at where ia.scenario="+scenarioId;
			q = session.createQuery(query);
			List<Object[]> iaObjects = q.list();
			for (Object[] objects : iaObjects) {
				ScenarioAction scenarioAction = new ScenarioAction();
				long id = (Long)objects[0];
				scenarioAction.setId(id);
				scenarioAction.setLevel((String) objects[1]);
				Device device = new Device();
				id = (Long) objects[2];
				device.setId(id);
				device.setFriendlyName(IMonitorUtil.convertToString(objects[3]));
				scenarioAction.setDevice(device);
				ActionType actionType = new ActionType();
				id = (Long) objects[4];
				actionType.setId(id);
				actionType.setCommand(IMonitorUtil.convertToString(objects[5]));
				scenarioAction.setActionType(actionType);
				scenario.getScenarioActions().add(scenarioAction);
			}

        } catch (Exception e) {
        	LogUtil.error("Error when listing scenario " + scenarioName + " for customer " + customerId);
			scenario = null;
        } finally {
            if (null != dbc) {
                dbc.closeConnection();
            }
        }
		return scenario;
	}

	public boolean saveScenarioWithDetails(Scenario scenario) {
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Transaction tx = session.beginTransaction();
			session.save(scenario);
			tx.commit();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Scenario retrieveScenarioDetails(long id) {

		Scenario scenario = null;
		String query = "select s.id, s.name,s.description, g.localIp," + /*0-3*/
				" g.id, g.macId," + 
				" sa.id, sa.level," +
				" d.id, d.deviceId, d.generatedDeviceId, d.friendlyName," + /*8-11*/
				" at.id, at.name, at.command," +
				" a.id, a.ip, a.controllerReceiverPort, a.ftpIp, a.ftpPort, a.ftpNonSecurePort, a.ftpUserName, a.ftpPassword," + /*15-22*/
				" c.customerId" +
				" ,s.iconFile" + /*24*/
				" from Scenario as s " +
				" left join s.gateWay as g" +
				" left join s.scenarioActions as sa" +
				" left join sa.device as d" +
				" left join sa.actionType as at" +
				" left join g.agent as a" +
				" left join g.customer as c" +
				" where s.id=" + id;

		List<Object[]> detailsArray = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			detailsArray = q.list();
			scenario = new Scenario();
			boolean isFirst = true;
			for (Object[] details : detailsArray) {
				if (isFirst) {
					Long scenarioId = (Long) details[0];
					String scenarioName = (String) details[1];
					String scenarioDescription = (String) details[2];
					
					
					Long gateWayId = (Long) details[4];
					String gateWayMacId = (String) details[5];
					String gateWayLocaIp = (String) details[3];
					
					Long agentId = (Long) details[15];
					String agentIp = (String) details[16];
					int aPort = (Integer) details[17];
					String ftpIp = (String) details[18];
					int ftpPort = (Integer) details[19];
					int ftpNonSecPort = (Integer) details[20];
					String ftpUserName = (String) details[21];
					String ftpPassword = (String) details[22];
					String customerId = (String) details[23];

					Agent agent = new Agent();
					agent.setId(agentId);
					agent.setIp(agentIp);
					agent.setControllerReceiverPort(aPort);
					agent.setFtpIp(ftpIp);
					agent.setFtpPort(ftpPort);
					agent.setFtpNonSecurePort(ftpNonSecPort);
					agent.setFtpUserName(ftpUserName);
					agent.setFtpPassword(ftpPassword);
					scenario.setId(scenarioId);
					scenario.setName(scenarioName);
					scenario.setDescription(scenarioDescription);
					scenario.setIconFile(IMonitorUtil.convertToString(details[24]));
					GateWay gateWay = new GateWay();
					gateWay.setId(gateWayId);
					gateWay.setMacId(gateWayMacId);
					gateWay.setLocalIp(gateWayLocaIp);
					gateWay.setAgent(agent);
					Customer customer = new Customer();
					customer.setCustomerId(customerId);
					gateWay.setCustomer(customer);
					scenario.setGateWay(gateWay);
					Set<ScenarioAction> scenarionActions = new HashSet<ScenarioAction>();
					scenario.setScenarioActions(scenarionActions);
				}
				
				isFirst = false;
				Long scheduleActionId = (Long) details[6];
				if (scheduleActionId != null) {
					Set<ScenarioAction> scenarioActions = scenario.getScenarioActions();
					ScenarioAction scenarioAction = null;
					for (ScenarioAction scheduleAction1 : scenarioActions) {
						if (scheduleAction1.getId() == scheduleActionId) {
							scenarioAction = scheduleAction1;
							break;
						}
					}
					if (scenarioAction == null) {
						scenarioAction = new ScenarioAction();
						scenarioAction.setId(scheduleActionId);
						String imvgActionLevel = (String) details[7];
						scenarioAction.setLevel(imvgActionLevel);
						
						Long deviceId = (Long) details[8];
						String generatedDeviceId = (String) details[10];
						String friendlyName = (String) details[11];
						
						Long actionTypeId = (Long) details[12];
						String actionTypeName = (String) details[13];
						String actionTypeCommand = (String) details[14];

						Device device = new Device();
						device.setId(deviceId);
						device.setGeneratedDeviceId(generatedDeviceId);
						device.setFriendlyName(friendlyName);
						scenarioAction.setDevice(device);

						ActionType actionType = new ActionType();
						actionType.setId(actionTypeId);
						actionType.setName(actionTypeName);
						actionType.setCommand(actionTypeCommand);
						scenarioAction.setActionType(actionType);

						scenarioActions.add(scenarioAction);
					}
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return scenario;
	}

	public boolean updateScenarioWithDetails(Scenario scenario) {
		DBConnection db = null;
		boolean result = false;
		try {
			db = new DBConnection();
			db.openConnection();
			Session session = db.getSession();
			Transaction tx = session.beginTransaction();
			session.update(scenario);
			tx.commit();
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	//parishod added to check device in scenario
	public long getScenarioCountByGeneratedDeviceId(String generatedDeviceId){
		//Query for trigger/action device
		String query2 = "select count(sch.id)"
				+ " from scenarioaction sch, device d"
				+ " where sch.scenario is not NULL and sch.device=d.id and d.generatedDeviceId='"+generatedDeviceId+"'";
		long scenarioCount = 0;
		
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q2 = session.createSQLQuery(query2);
			String c2 = q2.uniqueResult().toString();
			scenarioCount = Long.parseLong(c2);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("Error while getting rule count for device.");
		}finally{
			dbc.closeConnection();
		}
		return scenarioCount;
		
	}
	//parishod end
	
	/**
	 * @author sumit kumar
	 * @param user
	 * @param scenarioIdList
	 * @return
	 */
	public boolean updateAccessibleScenariosForSubUser(User user, String scenarioIdList){
		//LogUtil.info("updateAccessibleScenariosForSubUser : IN");
		boolean result = false;
		String scenarioIds[] = scenarioIdList.split(",");
		DaoManager daoManager = new DaoManager();
		boolean deleteStatus = false;
		boolean insertStatus = false;
		
		//1. Delete entries in subUserScenarioAccess table with [user = subuserId].
		String deleteQuery  = "delete from subUserScenarioAccess where user="+user.getId()+" ";
		deleteStatus = daoManager.executeSQLQuery(deleteQuery);
		//LogUtil.info("deleteStatus : "+deleteStatus);
		
		//2. Update Scenarios to update subUserScenarioAccess table.
		for(String scenarioID : scenarioIds){
			try {
				if(scenarioID != null && scenarioID != ""){
					String query = "insert into subUserScenarioAccess(user,scenario) values("+user.getId()+","+Long.parseLong(scenarioID)+") ";
					insertStatus = daoManager.executeSQLQuery(query);
					//LogUtil.info("insertStatus : "+insertStatus);
				}
			} catch (NumberFormatException e) {
				LogUtil.info("Got Exception while updating subUserScenarioAccess : ", e);
			}
		}
		
		if(deleteStatus && insertStatus) result =true;
		//LogUtil.info("updateAccessibleScenariosForSubUser : IN");
		return result;
	}
	
	public Scenario getScenarioApiByIdAndCustId(String CustomerId,String Id) 
	{
		Scenario sc = null;
		DBConnection dbc = null;
		String	query = "select s " +
						" from Scenario as s"+
						" left join s.gateWay as g"+
						" left join g.customer as c"+
						" where s.id ='"+Id+"'"+
						" and c.customerId ='"+CustomerId+"'";
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			sc = (Scenario)q.uniqueResult();
			if (null != sc) {
				Hibernate.initialize(sc);
				Hibernate.initialize(sc.getGateWay());
				Hibernate.initialize(sc.getGateWay().getAgent());
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving scenario by name and customerId : "
					+ e.getMessage());
			LogUtil.info("", e);
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return sc;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listSubUserScenariosByCustomerIdForApi(String customerId,User user) {
		String query = "";
		query += "select s.id,s.name,s.description,s.iconFile" +
				" from Scenario as s" +
				" left join s.gateWay as g" +
				" left join g.customer as c" +
		" where c.customerId='" + customerId + "'";
		LogUtil.info("query"+query);
		DBConnection dbc = null;
		UserManager userManager=new UserManager();
		List<subUserDeviceAccess> accessibleDeviceList = userManager.listDevicesForSubuser(user.getId());
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
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listAllScenariosByCustomerIdForApi(String customerId) {
		String query = "";
		query += "select s.id,s.name,s.description,s.iconFile" +
				" from Scenario as s" +
				" left join s.gateWay as g" +
				" left join g.customer as c" +
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
	
	public boolean checkAlexaUserByCustomerId(Customer customer) {
		
		boolean result = false;
		String query = "";
		query += "select a  from Alexa as a" +
				" left join a.customer as c" +
				" where c.customerId='" + customer.getCustomerId() + "'";
		DBConnection dbc = null;
		Object objects =null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createQuery(query);
			objects= q.uniqueResult();
			
			if(objects != null) {
				result = true;
			}
			
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		
		return result;
		
	}
	
	/*public static ScenarioAction getScenarioActionById(long scenarioId) {
		DaoManager daoManager=new DaoManager();
		return (ScenarioAction) daoManager.get(scenarioId,ScenarioAction.class);
		
	}*/
	
	
}
