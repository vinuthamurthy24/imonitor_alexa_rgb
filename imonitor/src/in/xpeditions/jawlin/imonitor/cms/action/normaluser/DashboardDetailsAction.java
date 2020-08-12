/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.TarrifConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.targetcost;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class DashboardDetailsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1507401089613192289L;
	private List<DeviceDetails> gateWays;
	private User user;
	private targetcost targetcost;
	private DeviceDetails gateWay;
	private String uptodateusage;
	private long uptodatecost;
	private String uptotimeusage;
	private Date uptotime;
	private String uptotimecost;
	private Object setUptodateusage;
	private List<TarrifConfig> tariffConfigg;
	private String lastMonthUsage;
	private String type;
	private String fromDate;
	private String toDate;
	private String Name;
	private String labelroom;
	private List<DeviceDetails> ListOfDevicewithPowerValues;
	private List<Object[]> wattageroom;
	private String totalwattage;
	private List<Object[]> toppowerdev;
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString = stream.toXML(customer);
		String serviceName = "DashboardService";
		String method = "listDashboarddetails";
		String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		//LogUtil.info("result--"+result);
		//LogUtil.info("result xml--"+stream.fromXML(result));
		this.setGateWays((List<DeviceDetails>) stream.fromXML(result));

		String valueInXml = stream.toXML(user.getId());
    	serviceName = "userService";
		method = "getUserById";
		result = IMonitorUtil.sendToController(serviceName, method, valueInXml);
		this.user=((User)stream.fromXML(result));
		
		serviceName = "DashboardService";
		method = "listDashboardtargetcost";
		result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		//LogUtil.info("targetcost--"+result);
		this.targetcost=(((targetcost)stream.fromXML(result)));
		String tt=targetcost.getTargetCost();
		
		serviceName = "DashboardService";
		method = "listDashboardUptodateUsage";
		result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		//LogUtil.info("uptodateusage--"+result);
		this.uptodateusage=(String) stream.fromXML(result);
		
		serviceName = "DashboardService";
		method = "getLastMonthUsage";
		result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		//LogUtil.info("lastMonthUsage--"+result);
		this.lastMonthUsage=(String) stream.fromXML(result);

		serviceName = "DashboardService";
		method = "listDashboardUptodatecost";
		result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		//LogUtil.info("uptodatecost--"+result);
		this.uptodatecost=(Long) stream.fromXML(result);
		
		serviceName = "DashboardService";
		method = "listDashboardwattageroom";
		result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		//LogUtil.info("room wattage:"+result);
		this.setWattageroom((List<Object[]>)stream.fromXML(result));
		
		serviceName = "DashboardService";
		method = "listDashboardtotalwattage";
		result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		//LogUtil.info("total  wattage:"+result);
		this.setTotalwattage((String) stream.fromXML(result));
		
		serviceName="DashboardService";
		method="listtoppowerconsumeddevices";
		result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.setToppowerdev((List<Object[]>)stream.fromXML(result));
		
		serviceName = "DashboardService";
		method = "listDashboardUptotimecost";
		result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		//LogUtil.info("timecost--"+result);
		float timecost =(Float) stream.fromXML(result);
		
		
		DecimalFormat df = new DecimalFormat("#.##");
		this.uptotimecost=df.format(timecost);
		
		serviceName = "DashboardService";
		method = "listDashboardUptotimeUsage";
		result = IMonitorUtil.sendToController(serviceName, method, xmlString);
		
		
		
		//LogUtil.info("uptotimeusage--"+result);
		Object[] data=(Object[]) stream.fromXML(result);
		if(data[0] != null && data[1] != null)
		{
			uptotimeusage=data[0].toString();
			uptotime=(Date) data[1];
		}
		
		
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		if(tt!=null)
		{
		String[] split = tt.split(",");
		String jan =split[0];
		String feb =split[1];
		String march =split[2];
		String april =split[3];
		String may =split[4];
		String june =split[5];
		String july =split[6];
		String aug =split[7];
		String sep =split[8];
		String oct =split[9];
		String nov =split[10];
		String dec =split[11];
		
		
		switch (month) {
		  case 0: 
			  targetcost.setTargetCost(jan);
		    break;
		  case 1: 
			  targetcost.setTargetCost(feb);
		    break;
		  case 2: 
			  targetcost.setTargetCost(march);
		    break;
		  case 3: 
			  targetcost.setTargetCost(april);
		    break;
		  case 4: 
			  targetcost.setTargetCost(may);
		    break;
		  case 5: 
			  targetcost.setTargetCost(june);
		    break;
		  case 6: 
			  targetcost.setTargetCost(july);
		    break;
		  case 7: 
			  targetcost.setTargetCost(aug);
		    break;
		  case 8: 
			  targetcost.setTargetCost(sep);
		    break;
		  case 9: 
			  targetcost.setTargetCost(oct);
		    break;
		  case 10: 
			  targetcost.setTargetCost(nov);
		    break;
		  case 11: 
			  targetcost.setTargetCost(dec);
		    break;
		}	
		}
		else
		{
			targetcost.setTargetCost("0");
		}
		
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String togetTotalpowerInfoofdates() throws Exception{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString = stream.toXML(customer);
		String FromDatexmlString = stream.toXML(this.fromDate);
		String ToDatexmlString = stream.toXML(this.toDate);
		//LogUtil.info("FromDatexmlString "+FromDatexmlString+"ToDatexmlString "+ToDatexmlString+"customer "+xmlString);
		String serviceName = "DashboardService";
		String method = "GetPowerinfoOfTwoDateUsingCustomerID";
		String result = IMonitorUtil.sendToController(serviceName, method, xmlString,FromDatexmlString,ToDatexmlString);
		//LogUtil.info(result);
		this.setListOfDevicewithPowerValues((List<DeviceDetails>) stream.fromXML(result));
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String getpowerInfoofdatesfordevice() throws Exception{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString = stream.toXML(customer);
		String FromDatexmlString = stream.toXML(this.fromDate);
		String ToDatexmlString = stream.toXML(this.toDate);
		String SelectedType = stream.toXML(this.type);
		String deviceXml = stream.toXML(this.Name);
	//	LogUtil.info("FromDatexmlString "+FromDatexmlString+"ToDatexmlString "+ToDatexmlString+"SelectedTypexmlString "+SelectedType+"device "+deviceXml);
		String serviceName = "DashboardService";
		String method = "GetPowerinfoOfTwoDateforDeviceWise";
		String result = IMonitorUtil.sendToController(serviceName, method, xmlString,deviceXml,FromDatexmlString,ToDatexmlString,SelectedType);
		//LogUtil.info(result);
		this.setListOfDevicewithPowerValues((List<DeviceDetails>) stream.fromXML(result));
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String getpowerInfoofdatesforroom() throws Exception{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString = stream.toXML(customer);
		String FromDatexmlString = stream.toXML(this.fromDate);
		String ToDatexmlString = stream.toXML(this.toDate);
		String SelectedType = stream.toXML(this.type);
		String roomXml = stream.toXML(this.labelroom);
		//LogUtil.info("FromDatexmlString "+FromDatexmlString+"ToDatexmlString "+ToDatexmlString+"SelectedTypexmlString "+SelectedType+"room "+roomXml);
		String serviceName = "DashboardService";
		String method = "GetPowerinfoOfTwoDateforRoomWise";
		String resultroom = IMonitorUtil.sendToController(serviceName, method, xmlString,roomXml,FromDatexmlString,ToDatexmlString,SelectedType);
	//	LogUtil.info(resultroom);
		this.setListOfDevicewithPowerValues((List<DeviceDetails>) stream.fromXML(resultroom));
		return SUCCESS;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	public List<DeviceDetails> getGateWays() {
		return gateWays;
	}



	public void setGateWays(List<DeviceDetails> gateWays) {
		this.gateWays = gateWays;
	}



	public DeviceDetails getGateWay() {
		return gateWay;
	}



	public void setGateWay(DeviceDetails gateWay) {
		this.gateWay = gateWay;
	}


	public targetcost getTargetcost() {
		return targetcost;
	}


	public void setTargetcost(targetcost targetcost) {
		this.targetcost = targetcost;
	}


	public String getUptodateusage() {
		return uptodateusage;
	}


	public void setUptodateusage(String uptodateusage) {
		this.uptodateusage = uptodateusage;
	}


	public List<TarrifConfig> getTariffConfigg() {
		return tariffConfigg;
	}


	public void setTariffConfigg(List<TarrifConfig> tariffConfigg) {
		this.tariffConfigg = tariffConfigg;
	}


	public long getUptodatecost() {
		return uptodatecost;
	}


	public void setUptodatecost(long uptodatecost) {
		this.uptodatecost = uptodatecost;
	}


	public String getUptotimeusage() {
		return uptotimeusage;
	}


	public void setUptotimeusage(String uptotimeusage) {
		this.uptotimeusage = uptotimeusage;
	}


	public String getUptotimecost() {
		return uptotimecost;
	}


	public void setUptotimecost(String uptotimecost) {
		this.uptotimecost = uptotimecost;
	}


	public Date getUptotime() {
		return uptotime;
	}


	public void setUptotime(Date uptotime) {
		this.uptotime = uptotime;
	}


	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public List<DeviceDetails> getListOfDevicewithPowerValues() {
		return ListOfDevicewithPowerValues;
	}

	public void setListOfDevicewithPowerValues(
			List<DeviceDetails> listOfDevicewithPowerValues) {
		ListOfDevicewithPowerValues = listOfDevicewithPowerValues;
	}

	public String getType() {
		return type;
	}

	public String getLastMonthUsage() {
		return lastMonthUsage;
	}
public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLabelroom() {
		return labelroom;
	}

	public void setLabelroom(String labelroom) {
		this.labelroom = labelroom;
	}

	public String getTotalwattage() {
		return totalwattage;
	}

	public void setTotalwattage(String totalwattage) {
		this.totalwattage = totalwattage;
	}

	public List<Object[]> getWattageroom() {
		return wattageroom;
	}

	public void setWattageroom(List<Object[]> wattageroom) {
		this.wattageroom = wattageroom;
	}

	public List<Object[]> getToppowerdev() {
		return toppowerdev;
	}

	public void setToppowerdev(List<Object[]> toppowerdev) {
		this.toppowerdev = toppowerdev;
	}
	
}
