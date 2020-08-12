/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Set;

/**
 * @author Coladi
 *
 */

public class Panic {
	private long id;
	private String name;
	private String description;
	private Set<PanicAction> panicActions;
	private Set<PanicAlertNotification> panicAlertNotifications;
	private Customer customer;
	
	public final long getId() {
		return id;
	}
	public final void setId(long id) {
		this.id = id;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getDescription() {
		return description;
	}
	public final void setDescription(String description) {
		this.description = description;
	}
	public final Customer getCustomer() {
		return customer;
	}
	public final void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Set<PanicAction> getPanicActions() {
		return panicActions;
	}
	public void setPanicActions(Set<PanicAction> panicActions) {
		this.panicActions = panicActions;
	}
	public Set<PanicAlertNotification> getPanicAlertNotifications() {
		return panicAlertNotifications;
	}
	public void setPanicAlertNotifications(
			Set<PanicAlertNotification> panicAlertNotifications) {
		this.panicAlertNotifications = panicAlertNotifications;
	}

	
}
