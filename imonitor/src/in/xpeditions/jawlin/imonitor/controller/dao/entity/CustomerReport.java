package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Date;

public class CustomerReport {
	
	private long id;
	private Customer customerId;
	private String reportPerson;
	private Date reportedDate;
	private Severity severity;
	private String issueDescription;
	private String resolution;
	private Date resolutionDate;
	private String allocatedPerson;
	private String action;
	private Date actionDate;
	private IssueStatus finalState;
	private Date finalDate;
	private RootCause rootCause;
	private String otherCause;
	/*private int type;*/
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Customer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}
	public String getReportPerson() {
		return reportPerson;
	}
	public void setReportPerson(String reportPerson) {
		this.reportPerson = reportPerson;
	}
	
	
	public String getIssueDescription() {
		return issueDescription;
	}
	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	public String getAllocatedPerson() {
		return allocatedPerson;
	}
	public void setAllocatedPerson(String allocatedPerson) {
		this.allocatedPerson = allocatedPerson;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
	
	public Severity getSeverity() {
		return severity;
	}
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	public Date getReportedDate() {
		return reportedDate;
	}
	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}
	public Date getResolutionDate() {
		return resolutionDate;
	}
	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public Date getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}
	public IssueStatus getFinalState() {
		return finalState;
	}
	public void setFinalState(IssueStatus finalState) {
		this.finalState = finalState;
	}
	/*public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	*/
	public RootCause getRootCause() {
		return rootCause;
	}
	public void setRootCause(RootCause rootCause) {
		this.rootCause = rootCause;
	}
	public String getOtherCause() {
		return otherCause;
	}
	public void setOtherCause(String otherCause) {
		this.otherCause = otherCause;
	}

}
