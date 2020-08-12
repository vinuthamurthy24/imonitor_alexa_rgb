/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.LCDRemoteConfiguration;

/**
 * @author Coladi
 * 
 */
public class LCDRemoteConfigurationManager {

	private DaoManager daoManager = new DaoManager();

	public boolean saveLCDRemoteConfiguration(
			LCDRemoteConfiguration LCDRemoteConfiguration) {
		boolean result = false;
		try {
			daoManager.save(LCDRemoteConfiguration);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//bhavya start: LCD remote feature Feature
	public LCDRemoteConfiguration getLCDRemoteConfiguration(long id) {
		LCDRemoteConfiguration LCDRemoteConfiguration = (LCDRemoteConfiguration) daoManager.get(id, LCDRemoteConfiguration.class);
		return LCDRemoteConfiguration;
	}
	public boolean updateLCDRemoteConfiguration(
			LCDRemoteConfiguration LCDRemoteConfiguration) {
		boolean result = false;
		try {
			daoManager.update(LCDRemoteConfiguration);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
