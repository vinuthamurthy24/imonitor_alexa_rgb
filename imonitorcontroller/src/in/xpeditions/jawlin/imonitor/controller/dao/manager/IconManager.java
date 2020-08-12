/* Copyright © 2012 iMonitor Solutions India Private Limited */

package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemIcon;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class IconManager {
	
	DaoManager daoManager = new DaoManager();
	
	@SuppressWarnings("unchecked")
	public List<Icon> getListOfIconsByDeviceType(long deviceType) {
		List<Icon> iconURL = null; 
		
		try {
			iconURL = daoManager.list("deviceType", deviceType, Icon.class);
			Hibernate.initialize(iconURL);
			for(Icon icon : iconURL)
			{
				//no changes so far
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}
		return iconURL;
	}
	//pari start
	public List<SystemIcon> getListOfIconsForLocation() {
		List<SystemIcon> iconURLList = null; 

		try {
			iconURLList = daoManager.list("type", "LOCATION",SystemIcon.class);
			Hibernate.initialize(iconURLList);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}
		return iconURLList;
	}
	//pari end
	
	//pari start
		public List<SystemIcon> getListOfIconsForScenario() {
			List<SystemIcon> iconURLList = null; 

			try {
				iconURLList = daoManager.list("type", "SCENARIO",SystemIcon.class);
				Hibernate.initialize(iconURLList);
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.error(e.getMessage());
			}
			return iconURLList;
		}
		//pari end

	@SuppressWarnings("unchecked")
	public Icon getDefaultIcon(long deviceType) {
		String query = "select i " + " from Icon i"
				+ " where i.deviceType=" + deviceType ;

		
		DBConnection dbc = null;
		Icon icon = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			/*Criteria criteria = session.createCriteria(Icon.class);
			Criterion criterion = Restrictions.eq("deviceType",
					deviceType);
			criteria.add(criterion);
			icon = (Icon) criteria.uniqueResult();*/
			Query q = session.createQuery(query);
			q.setMaxResults(1);
			icon = (Icon) q.uniqueResult();
		} catch (Exception ex) {
			LogUtil.error("ERRORVVN"+ex.getMessage());
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return icon;
	}
	
	
	public Icon getIconById(long iconId) {
		return (Icon) daoManager.get(iconId, Icon.class);
	}
	
	////Mobile Icons
	public List<SystemIcon> getListOfMobileIconsForLocation() {
		List<SystemIcon> iconURLList = null; 

		try {
			iconURLList = daoManager.list("type", "MOBILELOCATION",SystemIcon.class);
			Hibernate.initialize(iconURLList);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}
		return iconURLList;
	}
	

}
