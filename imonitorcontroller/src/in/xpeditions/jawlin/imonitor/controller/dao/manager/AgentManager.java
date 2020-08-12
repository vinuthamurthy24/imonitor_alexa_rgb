/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AgentManager {
	DaoManager daoManager = new DaoManager();

	public boolean save(Agent agent) {
		boolean result = false;
		try {
			daoManager.save(agent);
			result = true;
		} catch (Exception e) {
			LogUtil.error("Error when saving agent " + agent.getName()
					+ " message : " + e.getMessage());
		}
		return result;
	}

	public boolean update(Agent agent) {
		boolean result = false;
		try {
			daoManager.update(agent);
			result = true;
		} catch (Exception e) {
			LogUtil.error("Error when updating agent " + agent.getName()
					+ " message : " + e.getMessage());
		}
		return result;
	}

	public boolean delete(Agent agent) {
		boolean result = false;
		try {
			daoManager.delete(agent);
			result = true;
		} catch (Exception e) {
			LogUtil.error("Error when deleting agent " + agent.getName()
					+ " message : " + e.getMessage());
		}
		return result;

	}

	public boolean updateGatwayAfterAgentDeletion(long id) {
		String hqlG = "update gateway g set g.agent =NULL where agent=" + id;
		boolean result = false;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query qG = session.createSQLQuery(hqlG);
			Transaction tx = session.beginTransaction();
			qG.executeUpdate();
			result = true;
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Agent> listOfAgents() {
		List<Agent> agents = new ArrayList<Agent>();
		try {
			agents = (List<Agent>) daoManager.list(Agent.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return agents;

	}

	public int getTotalAgentCount() {
		int count = daoManager.getCount(Agent.class);
		return count;
	}

	@SuppressWarnings("unchecked")
	public List listAskedAgents(String sSearch, String sOrder, int start,
			int length) {
		String query = "";
		/*query += "select a.id,a.name,a.ip,a.receiverPort,a.controllerReceiverPort,a.controllerBroadCasterPort,a.streamingIp"
				+ ",a.streamingPort,a.ftpIp,a.ftpPort,s.name from "
				+ "Agent as a join a.status as s "
				+ "where a.status=s.id and (a.id like '%"
				+ sSearch
				+ "%' "
				+ "or a.ip like '%"
				+ sSearch
				+ "%'"
				+ " or a.streamingIp like '%"
				+ sSearch
				+ "%' "									//pari commented to not to list agents based on id
				+ " or a.ftpIp like '%"
				+ sSearch
				+ "%' "
				+ " or s.name like '%"
				+ sSearch
				+ "%' "
				+ " or a.name like '%"
				+ sSearch + "%' ) " + sOrder;*/
		query += "select a.id,a.name,a.ip,a.receiverPort,a.controllerReceiverPort,a.controllerBroadCasterPort,a.streamingIp"
				+ ",a.streamingPort,a.ftpIp,a.ftpPort,s.name from "
				+ "Agent as a join a.status as s "
				+ "where a.status=s.id and (a.ip like '%"
				+ sSearch
				+ "%'"
				+ " or a.streamingIp like '%"
				+ sSearch
				+ "%' "
				+ " or a.ftpIp like '%"
				+ sSearch
				+ "%' "
				+ " or s.name like '%"
				+ sSearch
				+ "%' "
				+ " or a.name like '%"
				+ sSearch + "%' ) " + sOrder;

		List list = daoManager.listAskedObjects(query, start, length,
				Agent.class);
		return list;
	}

	public int getTotalAgentCount(String sSearch) {
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(a)" + " from "
					+ "Agent as a join a.status as s "
					+ "where a.status=s.id and (a.id like '%" + sSearch + "%' "
					+ "or a.ip like '%" + sSearch + "%'"
					+ " or a.streamingIp like '%" + sSearch + "%' "
					+ " or a.ftpIp like '%" + sSearch + "%' "
					+ " or s.name like '%" + sSearch + "%' "
					+ " or a.name like '%" + sSearch + "%' )";
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

	public Agent getAgentById(long id) {
		return (Agent) daoManager.get(id, Agent.class);
	}

	@SuppressWarnings("unchecked")
	public Agent getLeastUsingGateWay() {
		Agent agent = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(Agent.class)
					.setFetchSize(1);
			List<Agent> users = criteria.list();
			if (users.size() > 0) {
				agent = users.get(0);
			}
			Hibernate.initialize(agent);
		} catch (Exception e) {
			LogUtil.error("Error when retreiving Agent : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return agent;
	}

	public Agent getReceiverIpAndPort(String macId) {
		Agent agent = null;
		String query = "select a.ip, a.controllerReceiverPort"
				+ " from GateWay g" + " left join g.agent a"
				+ " where g.macId='" + macId + "'";

		Object[] objects = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = (Object[]) q.uniqueResult();
			String ip = IMonitorUtil.convertToString(objects[0]);
			int port = (Integer) objects[1];
			agent = new Agent();
			agent.setIp(ip);
			agent.setControllerReceiverPort(port);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return agent;
	}

	public Agent getReceiverIpAndPortWithFtpDetails(String macId) {
		Agent agent = null;
		String query = "select a.ip, a.controllerReceiverPort, a.ftpIp, a.ftpPort, a.ftpUserName, a.ftpPassword"
				+ " from GateWay g"
				+ " left join g.agent a"
				+ " where g.macId='" + macId + "'";

		Object[] objects = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = (Object[]) q.uniqueResult();
			String ip = IMonitorUtil.convertToString(objects[0]);
			int port = (Integer) objects[1];
			String ftpIp = IMonitorUtil.convertToString(objects[2]);
			int ftpPort = (Integer) objects[3];
			String ftpUser = IMonitorUtil.convertToString(objects[4]);
			String ftpPassword = IMonitorUtil.convertToString(objects[5]);
			agent = new Agent();
			agent.setIp(ip);
			agent.setControllerReceiverPort(port);
			agent.setFtpIp(ftpIp);
			agent.setFtpPort(ftpPort);
			agent.setFtpUserName(ftpUser);
			agent.setFtpPassword(ftpPassword);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return agent;
	}

	public boolean deleteAgentIfNoGateWay(Agent agent) {
		String query = "select count(g) from GateWay as g"
				+ " left join g.agent as a" + " where a.id=" + agent.getId();

		Long count = null;
		DBConnection dbc = null;
		boolean result = false;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			count = (Long) q.uniqueResult();
			if (count > 0) {
				result = false;
			} else {
				String sql = "delete a from agent as a where a.id=" + agent.getId();
				q = session.createSQLQuery(sql);
				Transaction tx = session.beginTransaction();
				q.executeUpdate();
				tx.commit();
				result = true;
			}
		} catch (Exception ex) {
			LogUtil.error("Error is deleting agent with id " + agent.getId() + " and message : " + ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return result;
	}
	
	
	
	//Register Zing Gateway
	public Agent getAgentByName(String name) {
		String query = "select a.ftpIp,a.ftpPort,a.ftpUserName,a.ftpPassword from agent as a where a.name='" + name + "'";
		
		DBConnection dbc = null;
		Agent agent = new Agent();
		Object[] object = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			 object = (Object[]) q.uniqueResult();
			 String ftpip=(String) object[0];
			 int ftpPort=(Integer) object[1];
			 String ftpuser=(String) object[2];
			String ftpPassword=(String) object[3];
			agent.setFtpIp(ftpip);
			agent.setFtpPort(ftpPort);
			agent.setFtpUserName(ftpuser);
			agent.setFtpPassword(ftpPassword);
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.info("Exception message is "+ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return agent;
	}
	
	public Agent getAgentForGateway() {
		
		DBConnection dbc = null;
		Agent agent = null;
		String query = "select a from Agent as a where a.name='demo'";
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			agent = (Agent)q.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			dbc.closeConnection();
		}
		
		return agent;
	}
	
}
