/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.PIRConfiguration;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

/**
 * 
 * @author sumit
 *
 */
public class PIRConfigurationManager {
	private DaoManager daoManager = new DaoManager();
	
	public PIRConfiguration getPIRConfigurationById(long id){
		PIRConfiguration pirConfiguration = (PIRConfiguration) daoManager.get(id, PIRConfiguration.class);
		return pirConfiguration;
	}
	public boolean updatePIRConfiguration(PIRConfiguration pirConfiguration){
		boolean result = false;
		try {
			daoManager.update(pirConfiguration);
			result = true;
		} catch (Exception e) {
			LogUtil.info("Error while updating PIR Configuration. "+e.getMessage());
		}
		return result;
	}
}
