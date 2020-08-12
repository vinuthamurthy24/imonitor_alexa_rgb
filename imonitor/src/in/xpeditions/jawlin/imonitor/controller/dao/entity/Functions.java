package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class Functions 
{
	private long id;
	private String name;
	private String category;
	private long functionId;
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(long functionId) {
		this.functionId = functionId;
	}
}
