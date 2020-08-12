/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import java.util.Date;
import java.util.Set;

/**
 * @author Coladi
 *
 */
public class Customer {
	private long id;
	private String customerId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String place;
	private String post;
	private String city;
	private String province;
	private String pincode;
	private String phoneNumber;
	private Date dateOfRegistration;
	private Date dateOfInstallation;
	private Date dateOfExpiry;
	private Status status;
	private String email;
	private String mobile;
	private int freeStorageMB;
	private int paidStorageMB;
	private Set<User> users;
	private Set<GateWay> gateWays;
	private Set<Location> locations;
	private Set<UserNotificationProfile> userNotificationProfiles;
	private TimeZone timeZone;
	//sumit start: forget password user authentication
	private String forgotPasswordAnswer;
	private long quesId;
	private int backup;
	private CustomerServices customerService;
	private int intimationCount;
	private long smsThreshold;
	private Date renewalDate;
	private int renewalDuration;
	
	
	
	//sumit end
	private Long featuresEnabled;
	private SystemIntegrator systemIntegrator;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}
	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}
	public Date getDateOfInstallation() {
		return dateOfInstallation;
	}
	public void setDateOfInstallation(Date dateOfInstallation) {
		this.dateOfInstallation = dateOfInstallation;
	}
	public Date getDateOfExpiry() {
		return dateOfExpiry;
	}
	public void setDateOfExpiry(Date dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getFreeStorageMB() {
		return freeStorageMB;
	}
	public void setFreeStorageMB(int freeStorageMB) {
		this.freeStorageMB = freeStorageMB;
	}
	public int getPaidStorageMB() {
		return paidStorageMB;
	}
	public void setPaidStorageMB(int paidStorageMB) {
		this.paidStorageMB = paidStorageMB;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<GateWay> getGateWays() {
		return gateWays;
	}
	public void setGateWays(Set<GateWay> gateWays) {
		this.gateWays = gateWays;
	}
	public Set<Location> getLocations() {
		return locations;
	}
	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public final Set<UserNotificationProfile> getUserNotificationProfiles() {
		return userNotificationProfiles;
	}
	public final void setUserNotificationProfiles(
			Set<UserNotificationProfile> userNotificationProfiles) {
		this.userNotificationProfiles = userNotificationProfiles;
	}
	public final TimeZone getTimeZone() {
		return timeZone;
	}
	public final void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
	public String getForgotPasswordAnswer() {
		return forgotPasswordAnswer;
	}
	public void setForgotPasswordAnswer(String forgotPasswordAnswer) {
		this.forgotPasswordAnswer = forgotPasswordAnswer;
	}
	public long getQuesId() {
		return quesId;
	}
	public void setQuesId(long quesId) {
		this.quesId = quesId;
	}
	public Long getFeaturesEnabled() {
		return featuresEnabled;
	}
	public void setFeaturesEnabled(Long featuresEnabled) {
		this.featuresEnabled = featuresEnabled;
	}
	public SystemIntegrator getSystemIntegrator() {
		return systemIntegrator;
	}
	public void setSystemIntegrator(SystemIntegrator systemIntegrator) {
		this.systemIntegrator = systemIntegrator;
	}
	public int getBackup() {
		return backup;
	}
	public void setBackup(int backup) {
		this.backup = backup;
	}
	public CustomerServices getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CustomerServices customerService) {
		this.customerService = customerService;
	}
	public int getIntimationCount() {
		return intimationCount;
	}
	public void setIntimationCount(int intimationCount) {
		this.intimationCount = intimationCount;
	}
	public long getSmsThreshold() {
		return smsThreshold;
	}
	public void setSmsThreshold(long smsThreshold) {
		this.smsThreshold = smsThreshold;
	}
	public Date getRenewalDate() {
		return renewalDate;
	}
	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}
	public int getRenewalDuration() {
		return renewalDuration;
	}
	public void setRenewalDuration(int renewalDuration) {
		this.renewalDuration = renewalDuration;
	}

	
}