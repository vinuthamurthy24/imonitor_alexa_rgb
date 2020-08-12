/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Role;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.RoleManager;

import java.util.List;

import com.thoughtworks.xstream.XStream;

public class RoleServiceManager {
	public String listRoles(){
		String xml="";
		XStream xStream=new XStream();
		RoleManager roleManager=new RoleManager();
		List<Role>roles=roleManager.listOfRoles();
		xml=xStream.toXML(roles);
		return xml;
	}
	public String getRoleByRoleName(String xml){
		
		XStream xStream=new XStream();
		String roleName = (String) xStream.fromXML(xml);
		RoleManager roleManager = new RoleManager();
		Role role = roleManager.getRoleByRoleName(roleName);
		return xStream.toXML(role);
	}
}
