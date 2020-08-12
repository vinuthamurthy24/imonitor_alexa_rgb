/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;


import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

/**
 * 
 * @author sumit
 *
 */
public class acConfigurationManager {
	private DaoManager daoManager = new DaoManager();
	
	public acConfiguration getacConfigurationById(long id){
		acConfiguration acConfiguration = (acConfiguration) daoManager.get(id, acConfiguration.class);
		return acConfiguration;
	}
	public boolean updateacConfiguration(acConfiguration acConfiguration){
		boolean result = false;
		try {
			daoManager.update(acConfiguration);
			result = true;
		} catch (Exception e) {
			LogUtil.info("Error while updating PIR Configuration. "+e.getMessage());
		}
		return result;
	}
}
