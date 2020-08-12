package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class CustomerType {
	
	private long id;
	private String name;
	private String remarks;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "CustomerType [id=" + id + ", name=" + name + ", remarks="
				+ remarks + "]";
	}
	

}
