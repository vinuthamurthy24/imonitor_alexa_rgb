package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.KeyConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SceneControllerMake;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class SceneControllerManager {
	DaoManager daoManager=new DaoManager();

	public List<SceneControllerMake> getSceneMakeByModel(String modelNumber) 
	{
		DBConnection dbc = null;
		List<SceneControllerMake> sceneMake = null;
		try 
		{
			String query = "";
			query +="select s.id,s.keyName,s.noOfKeys,s.pressType,s.keyCode from SceneControllerMake as s where s.ModelName='" + modelNumber + "' order by s.id";
			
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			List<Object[]> list = q.list();
			sceneMake=new ArrayList<SceneControllerMake>();
			for (Object[] object : list) {
				SceneControllerMake scenemake = new SceneControllerMake();
				scenemake.setId((Long)object[0]);
				scenemake.setKeyName(IMonitorUtil.convertToString(object[1]));
				scenemake.setNoOfKeys(IMonitorUtil.convertToString(object[2]));
				scenemake.setPressType(IMonitorUtil.convertToString(object[3]));
				scenemake.setKeyCode(IMonitorUtil.convertToString(object[4]));
				sceneMake.add(scenemake);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally{
			dbc.closeConnection();
		}
		return sceneMake;
	}
	
	public boolean saveKeyConfiguration(KeyConfiguration keyConfiguration) {
		boolean result=false;
		try{
			result=daoManager.save(keyConfiguration);
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	
	
	

}
