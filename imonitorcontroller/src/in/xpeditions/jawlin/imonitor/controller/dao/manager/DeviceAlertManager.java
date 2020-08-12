/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceAlert;

/**
 * @author computer
 *
 */
public class DeviceAlertManager {
	DaoManager daoManager=new DaoManager();
	public boolean save(DeviceAlert alertResponseExpression){
		boolean result=false;
		try{
			daoManager.save(alertResponseExpression);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	public long getTotalAlertResponseExpressionCount(){
		int count =  daoManager.getCount(DeviceAlert.class);
		return count;
	} 
}
