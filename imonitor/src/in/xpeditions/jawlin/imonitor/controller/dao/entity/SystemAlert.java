/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class SystemAlert {
	
	private long id;
	private String name;
	private String utf8name;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUtf8name() {
		return utf8name;
	}
	public void setUtf8name(String utf8name) {
		this.utf8name = utf8name;
	}
	
}
