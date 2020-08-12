package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class AdminSuperUser
{
	private long id; 
	private String superUserId;
	private String password;
	private String oldPassword;

	
	public String getSuperUserId() 
	{
		return superUserId;
	}
	public void setSuperUserId(String superUserId) {
		this.superUserId = superUserId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
