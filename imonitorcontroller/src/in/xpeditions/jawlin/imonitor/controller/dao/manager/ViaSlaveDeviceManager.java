package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import java.math.BigInteger;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ViaSlaveDeviceManager
{
DaoManager daoManager = new DaoManager();
	
	public int getIDUCountBySlaveDevice (Device device) {
		DBConnection dbc = null;
		int count = 0;
		/*String query = "select count(i) from IndoorUnit as i join i.SlaveId as m where m.id="+device.getId();*/
		/*String query="select count(generatedDeviceId) from device left join devicetype on device.deviceType=devicetype.id where gateway="+device.getGateWay().getId()+
				" and devicetype.name='VIA_UNIT'";*/
		/*String query="select count(generatedDeviceId) from device left join devicetype on device.deviceType=devicetype.id where gateway="+device.getGateWay().getId()+
				" and length(generatedDeviceId)>26 ";*/
		
		String query="select count(generatedDeviceId) from device left join devicetype on device.deviceType=devicetype.id left join Indoor_Unit_Configuration as idu  on device.deviceConfiguration=idu.id " +
				"where gateway="+device.getGateWay().getId()+" and length(generatedDeviceId)>26 and idu.ConnectStatus=1";
		
		
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			BigInteger lCount = (BigInteger) q.uniqueResult();
		     count = lCount.intValue();
		
			if (count==0) 
			{
				deleteAllIDUsFromDB(device);
			}
			
		} catch (Exception e)
		{
			LogUtil.info("Exception counttttt"+e.getMessage());
			e.printStackTrace();
		}finally {
			dbc.closeConnection();
		}
				
		return count;
	}
	
	//Method to delete IDUs if count is 0 and connect status is 0
	public void deleteAllIDUsFromDB (Device device) 
	{
		String genaratedDeviceId=device.getGeneratedDeviceId();
		DBConnection dbc = null;					
		String query="delete from device where generatedDeviceId like '%"+genaratedDeviceId+"%'";
		
		try {
			dbc = new DBConnection();		
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(query);
			Transaction tx = session.beginTransaction();
			q.executeUpdate();
			tx.commit();
			
		} catch (Exception e)
		{
			LogUtil.info("Exception for deleting all IDUS"+e.getMessage());
			e.printStackTrace();
		}finally {
			dbc.closeConnection();
		}
	}
	
	
}
