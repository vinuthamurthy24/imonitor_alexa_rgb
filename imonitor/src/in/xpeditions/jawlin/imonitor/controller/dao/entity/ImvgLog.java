/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class ImvgLog {
	private long id; 
	private String fileName;
	private String filePath;
	private String uploadedDate;
	private GateWay gateWay;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public GateWay getGateWay() {
		return gateWay;
	}
	public void setGateWay(GateWay gateWay) {
		this.gateWay = gateWay;
	}

}
