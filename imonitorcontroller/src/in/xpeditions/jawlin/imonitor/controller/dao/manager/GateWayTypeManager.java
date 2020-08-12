/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import java.util.ArrayList;
import java.util.List;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWayType;

public class GateWayTypeManager {
	DaoManager daoManager=new DaoManager();
	public boolean saveGateWayType(GateWayType gateWayType){
		boolean result=false;
		try{
			daoManager.save(gateWayType);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	public boolean deleteGateWayType(GateWayType gateWayType){
		boolean result=false;
		try{
			daoManager.delete(gateWayType);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	public boolean updateGateWayType(GateWayType gateWayType){
		boolean result=false;
		try{
			daoManager.update(gateWayType);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}return result;
	}
	@SuppressWarnings("unchecked")
	public List<GateWayType> listGateWayTypes(){
		List<GateWayType>gateWayTypes=new ArrayList<GateWayType>();
		try{
			gateWayTypes=(List<GateWayType>)daoManager.list(GateWayType.class);
		}catch(Exception e){
			e.printStackTrace();
		}return gateWayTypes;
	}
}	
