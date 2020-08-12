/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class Event {
	private long id;
	private User user;
	private String actionPerformed;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getActionPerformed() {
		return actionPerformed;
	}
	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}
	
}
