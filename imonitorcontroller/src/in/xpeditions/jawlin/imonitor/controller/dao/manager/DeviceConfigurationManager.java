/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

/**
 * @author Coladi
 *
 */
public class DeviceConfigurationManager {

	private DaoManager daoManager=new DaoManager();
	
	public boolean saveDeviceConfiguration(DeviceConfiguration deviceConfiguration){
		boolean result=false;
		try{
			daoManager.save(deviceConfiguration);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	public DeviceConfiguration getDeviceConfigurationById(long id){
		DeviceConfiguration deviceConfiguration = (DeviceConfiguration) daoManager.get(id, DeviceConfiguration.class);
		return deviceConfiguration;
	}
	
	
	public boolean updateIduConfiguration(IndoorUnitConfiguration indoorUnitConfiguration){
		boolean result = false;
		try {
			daoManager.update(indoorUnitConfiguration);
			result = true;
		} catch (Exception e) {
			LogUtil.info("Error while updating PIR Configuration. "+e.getMessage());
		}
		return result;
	}
}
