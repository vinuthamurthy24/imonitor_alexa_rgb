package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import org.hibernate.Query;
import org.hibernate.Session;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

public class ModbusDeviceManager {
	DaoManager daoManager = new DaoManager();
	
	public int getSlaveCountByViaDevice (Device device) {
		DBConnection dbc = null;
		int count = 0;
		String query = "select count(m) from ModbusSlave as m join m.deviceId as d where d.id="+device.getId();
		
	
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			count = lCount.intValue();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
				
		return count;
	}

}
