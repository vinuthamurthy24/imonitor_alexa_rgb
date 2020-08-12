package in.monitor.authprovider;

import java.util.Date;

public class Clients {
	
	
	/**
	 * 
	 */
	private long id; // Auto increment
	private String Client_id;
	private String ClientSecret;
	private String AuthCode;
	private String AccessToken;
	private String RefreshToken;
	private Date  CreatedAT;
	private Date ExpireTime;
	
	
	
	/*public Clients(){
		
		
	}*/


	/*public Clients(String client_id, String ClientSecret, String AuthCode, String AccessToken, String RefreshToken, Date CreatedAT,Date ExpireTime) {
		super();
		this.Client_id = client_id;
		this.ClientSecret = ClientSecret;
		this.AuthCode = AuthCode;
		this.AccessToken = AccessToken;
		this.RefreshToken = RefreshToken;
		this.CreatedAT = CreatedAT;
		this.ExpireTime = ExpireTime;
		
		
	}*/


	public String getClient_id() {
		return Client_id;
	}

	public void setClient_id(String client_id) {
		this.Client_id = client_id;
	}

	public String getClientSecret() {
		return ClientSecret;
	}

	public void setClientSecret(String ClientSecret) {
		this.ClientSecret = ClientSecret;
	}
	
	public String getAuthCode() {
		return AuthCode;
	}

	public void setAuthCode(String AuthCode) {
		this.AuthCode = AuthCode;
	}
	public String getAccessToken() {
		return AccessToken;
	}

	public void setAccessToken(String AccessToken) {
		this.AccessToken = AccessToken;
	}

	public String getRefreshToken() {
		return RefreshToken;
	}

	public void setRefreshToken(String RefreshToken) {
		this.RefreshToken = RefreshToken;
	}

	public Date getCreatedAT() {
		return CreatedAT;
	}

	public void setCreatedAT(Date CreatedAT) {
		this.CreatedAT = CreatedAT;
	}

	public Date getExpireTime() {
		return ExpireTime;
	}

	public void setExpireTime(Date ExpireTime) {
		this.ExpireTime = ExpireTime;
	}
	/*@Override
	public String toString() {
		return "ClientVO [client_id=" + client_id + ", client_secret="
				+ client_secret + "]";
	}*/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	
	
	
}
