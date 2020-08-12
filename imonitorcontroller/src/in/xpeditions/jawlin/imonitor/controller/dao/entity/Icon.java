/* Copyright © 2012 iMonitor Solutions India Private Limited */

package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class Icon {
	
	private long id;  //id auto-increment
	private long deviceType;
	private String fileName;  //url for icon
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id=id;
	}
	
	public long getDeviceType(){
		return deviceType;
	}
	
	public void setDeviceType(long deviceType){
		this.deviceType=deviceType;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName=fileName;
	}

}
