/* Copyright © 2012 iMonitor Solutions India Private Limited */

package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class SystemIcon {
	
	private long id;  //id auto-increment
	private String Type;
	private String fileName;  //url for icon
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id=id;
	}
		
	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName=fileName;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		this.Type = type;
	}

}
