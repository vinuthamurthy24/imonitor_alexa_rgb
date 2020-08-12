package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class CustomerAndSuperUser
{
	private long id;
	private Customer customer;
	private AdminSuperUser superUser;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public AdminSuperUser getSuperUser() {
		return superUser;
	}
	public void setSuperUser(AdminSuperUser superUser) {
		this.superUser = superUser;
	}
}
