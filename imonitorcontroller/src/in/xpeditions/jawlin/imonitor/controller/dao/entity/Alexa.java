package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class Alexa {
	
	private long id;
	private Customer customer;
	private String userId;
    private String password;
	private String accesstoken;
	private String code;
	private String refreshToken;
	private String skillToken;
	private String skillTokenType;
	private String skillRefreshToken;
	private String tokenExpires;
	private String alexaAuthCode;
	private String previousRefreshToken;
	private User user;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
		
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getSkillToken() {
		return skillToken;
	}
	public void setSkillToken(String skillToken) {
		this.skillToken = skillToken;
	}
	public String getSkillRefreshToken() {
		return skillRefreshToken;
	}
	public void setSkillRefreshToken(String skillRefreshToken) {
		this.skillRefreshToken = skillRefreshToken;
	}
	public String getSkillTokenType() {
		return skillTokenType;
	}
	public void setSkillTokenType(String skillTokenType) {
		this.skillTokenType = skillTokenType;
	}
	public String getTokenExpires() {
		return tokenExpires;
	}
	public void setTokenExpires(String tokenExpires) {
		this.tokenExpires = tokenExpires;
	}
	public String getAlexaAuthCode() {
		return alexaAuthCode;
	}
	public void setAlexaAuthCode(String alexaAuthCode) {
		this.alexaAuthCode = alexaAuthCode;
	}
	public String getPreviousRefreshToken() {
		return previousRefreshToken;
	}
	public void setPreviousRefreshToken(String previousRefreshToken) {
		this.previousRefreshToken = previousRefreshToken;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

}
