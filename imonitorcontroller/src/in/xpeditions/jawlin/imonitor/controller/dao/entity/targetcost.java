/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author Coladi
 *
 */
public class targetcost {
	private long id; // Auto increment
	private String targetCost;
	private long customer;
	private long kwPerUnit;
	private long ReachedPowerlimit;
	private long WarningPowerlimit;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTargetCost() {
		return targetCost;
	}

	public void setTargetCost(String targetCost) {
		this.targetCost = targetCost;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public long getKwPerUnit() {
		return kwPerUnit;
	}

	public void setKwPerUnit(long kwPerUnit) {
		this.kwPerUnit = kwPerUnit;
	}

	public long getWarningPowerlimit() {
		return WarningPowerlimit;
	}

	public void setWarningPowerlimit(long warningPowerlimit) {
		WarningPowerlimit = warningPowerlimit;
	}

	public long getReachedPowerlimit() {
		return ReachedPowerlimit;
	}

	public void setReachedPowerlimit(long reachedPowerlimit) {
		ReachedPowerlimit = reachedPowerlimit;
	}

	


}
