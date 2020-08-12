/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;


import in.xpeditions.jawlin.imonitor.controller.dao.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleManager {
	DaoManager daoManager=new DaoManager();
	@SuppressWarnings("unchecked")
	public List<Role> listOfRoles() {
		List<Role>roles=new ArrayList<Role>();
		try{
			roles=(List<Role>)daoManager.list(Role.class);
		}catch(Exception e){
			e.printStackTrace();
		}return roles;
		
	}
	public boolean save(Role role) {
		boolean result=false;
		try{
			daoManager.save(role);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	public Role getRoleByRoleName(String roleName) {
		return (Role) daoManager.getOne("name", roleName, Role.class);
	}

}
