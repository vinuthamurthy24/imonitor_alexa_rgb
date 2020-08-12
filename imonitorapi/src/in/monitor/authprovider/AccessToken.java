package in.monitor.authprovider;

public class AccessToken {
	
	
	private String access_token;
	private String token_type;
	private long expires_in;
	private String refresh_token;
	//private String example_parameter;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	/*public String getExample_parameter() {
		return example_parameter;
	}
	public void setExample_parameter(String example_parameter) {
		this.example_parameter = example_parameter;
	}*/
	public long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

}
