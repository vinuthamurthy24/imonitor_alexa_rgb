/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.mainuser;

import in.xpeditions.jawlin.imonitor.cms.action.adminuser.Logcapture;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemIcon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.FTPUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorProperties;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
//import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class LocationManagementAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3644797778331641134L;
	private DataTable dataTable;
	private User user;
	private Location location;
	private String currentIcon;



	private String imvgUploadContextPath=IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH);
	//sumit start
	private File uploadedImage;
	private String uploadedImageFileName;
	private String uploadedImageContentType;
	
	
	//=IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH);
	//private HttpServletRequest servletRequest;
	//sumit end
	
	
	
	//private File file;
	//pari start
	private List<SystemIcon> listIcons;
	
	//Added by apoorva
	private List<SystemIcon> listMobileIcons;
	private String MobileIconFile;
	
	public String getMobileIconFile() {
		return MobileIconFile;
	}
	public void setMobileIconFile(String mobileIconFile) {
		MobileIconFile = mobileIconFile;
	}
	public List<SystemIcon> getListMobileIcons() {
		return listMobileIcons;
	}
	public void setListMobileIcons(List<SystemIcon> listMobileIcons) {
		this.listMobileIcons = listMobileIcons;
	}
	//pari end
	public String execute() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		user = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		long id = user.getCustomer().getId();
		xmlString += "-" + stream.toXML(id);
		String serviceName = "locationService";
		String method = "listAskedLocations";
		String resultXml = IMonitorUtil.sendToController(serviceName, method,
				xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
//pari start
	@SuppressWarnings("unchecked")
	public String toAddLocation() throws Exception{
		XStream stream = new XStream();
		String xmlOfIconList = null;
		//Mobile Icons
		String xmlOfMobileIconList = null;
		try 
		{
			xmlOfIconList = IMonitorUtil.sendToController("iconService", "listIconsForLocation");
			this.listIcons = (List<SystemIcon>)stream.fromXML(xmlOfIconList);	
			
			//Mobile Icons
			xmlOfIconList = IMonitorUtil.sendToController("iconService", "listMobileIconsForLocation");
			this.listMobileIcons = (List<SystemIcon>)stream.fromXML(xmlOfIconList);	
			
			LogUtil.info("Mobile Icons :"+xmlOfIconList);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
//pari end
	// ************************************************ sumit start: To Upload Image for Location by User *****************************************
	public String addLocation() throws Exception {
		String message = IMonitorUtil.formatMessage(this, "msg.imonitor.rooms.0001");
		try {
			User user;
			/*
			String filePath = createLocationFilePath(location.getName());
			
			if (file != null) {
				location.setIconFile(filePath);
				File fileToCreate = new File(filePath);
				FileUtils.copyFile(file, fileToCreate);
			}
			*/
			XStream stream = new XStream();
			user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			this.location.setCustomer(user.getCustomer());
			String xmlString = stream.toXML(this.location);
			String serviceName = "locationService";
			String method = "saveLocation";
			String result = IMonitorUtil.sendToController(serviceName,method, xmlString);
			if(result.equalsIgnoreCase("yes")){
				//sumit start: save image if uploaded by the user.
				if((this.uploadedImage != null)){// && this.uploadedImage.exists()){
					String userXml = stream.toXML(user);
					String customerId = user.getCustomer().getCustomerId();

					//1.Get Location Id based on the location name to rename the image.
					String locationNameXml = stream.toXML(this.location.getName());
					String customerIdXml = stream.toXML(this.location.getCustomer().getCustomerId());
					method = "getLocationIdByName";
					result = IMonitorUtil.sendToController(serviceName, method, locationNameXml, customerIdXml);
					Long locId = (Long) stream.fromXML(result);
					
					//2.Get the extension for the uploaded image.
					String fName = this.uploadedImageFileName;
					int index = fName.lastIndexOf(".");
					String ext = fName.substring(index);
					//LogUtil.info("extension: "+ext);
					
					//3.Set locationId + extension as the name of the uploaded file.
					String filePath = customerId+"/images/locations/location"+locId.longValue()+ext;	
					//LogUtil.info("filePath: "+filePath);
					InputStream inputFile = new FileInputStream(this.uploadedImage);
					
					//4.Get the agent for FTP UPLOAD based on customer Id.
					serviceName = "agentService";
					method = "getAgentByCustomerId";
					result = IMonitorUtil.sendToController(serviceName, method, customerIdXml);
					//LogUtil.info("Agent:"+result);
					Agent agent = (Agent) stream.fromXML(result);
					String ftpIp = agent.getFtpIp();
					int ftpPort = agent.getFtpPort();
					String ftpUserName = agent.getFtpUserName();
					String ftpPassword = agent.getFtpPassword();
					String locationsDir = customerId+"/images/locations";
					
					//5.Check if locations folder exists and upload the image.
					FTPUtil.createFolders(ftpIp, ftpPort, ftpUserName, ftpPassword, locationsDir);
					FTPUtil.uploadFile(ftpIp, ftpPort, ftpUserName, ftpPassword, inputFile,filePath);		
					
					//6.Set the image as iconFile for the location and update.
					String locationIdXml = stream.toXML(locId);
					String filePathXml = stream.toXML(filePath);
					serviceName = "locationService";
					method = "saveUploadedImageForLocation";
					result = IMonitorUtil.sendToController(serviceName, method, locationIdXml, filePathXml);
				}//in else part handle: what if the user has not uploaded any image for location.
				
				
				//sumit end: save image if uploaded by the user.
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.rooms.0002");;
			}else if(result.equalsIgnoreCase("duplicate")){
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.rooms.0003");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			LogUtil.error("ABCDEFG"+exception.getMessage());
		}
		
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	// ********************************************* sumit end: To Upload Image for Location by User **************************************
	private String createLocationFilePath(String fileName) {
		String filePath = ServletActionContext.getServletContext().getRealPath("/");
		filePath += Constants.EXTERNAL_FOLDER + Constants.FILE_SEPARATOR;
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		filePath += user.getCustomer().getCustomerId() + Constants.FILE_SEPARATOR;
		filePath += Constants.LOCATION_FOLDER + Constants.FILE_SEPARATOR;
		new File(filePath).mkdirs();
		filePath += fileName;
		return filePath;
	}

	public String toEditLocation() throws Exception {
		long id = location.getId();
		XStream stream = new XStream();
		String xml = stream.toXML(id);
		String valueInXml = IMonitorUtil.sendToController("locationService",
				"getLocationById", xml);
		this.location = (Location) stream.fromXML(valueInXml);
		//pari start
		
		String iconPath = this.location.getIconFile();
		//Mid Icons change for displaying Icon For editing location
		String mobileIconPath = "/imonitor/resources/images/systemicons/";
		String mobileIcon = this.location.getMobileIconFile();
		String finalMobileIconUrl = mobileIconPath + mobileIcon + ".png";
		LogUtil.info("Final Mobile Icon : " + finalMobileIconUrl);
		
		this.location.setMobileIconFile(finalMobileIconUrl);
		
		if(!iconPath.contains("systemicons")){
			String realIconPath = this.imvgUploadContextPath+iconPath;
			this.currentIcon = realIconPath;
			
			String realMobileIconPath = this.imvgUploadContextPath+finalMobileIconUrl;
			this.MobileIconFile = realMobileIconPath;
			
		}
		else
		{
			this.currentIcon = iconPath;
			this.MobileIconFile = finalMobileIconUrl;
		}
		
		
		String xmlOfIconList = null;
		try {

			xmlOfIconList = IMonitorUtil.sendToController("iconService", "listIconsForLocation");
			this.listIcons = (List<SystemIcon>) stream.fromXML(xmlOfIconList);	
			
			//Mobile Icons
			xmlOfIconList = IMonitorUtil.sendToController("iconService", "listMobileIconsForLocation");
			this.listMobileIcons = (List<SystemIcon>)stream.fromXML(xmlOfIconList);	
			
			
			} catch (Exception e) {
			e.printStackTrace();
		}
		//pari end
		ActionContext.getContext().getSession().put("locatonFilePath", location.getIconFile());
		return SUCCESS;
	}

	public String deleteLocation() throws Exception {
		String message = IMonitorUtil.formatMessage(this, "msg.imonitor.rooms.0006");
		long id = location.getId();
		XStream stream = new XStream();
		String xml = stream.toXML(id);
		String valueInXml = IMonitorUtil.sendToController("locationService","deleteLocation", xml);
		// valueInXml - contains yes or no according as the success
		this.location = (Location) stream.fromXML(valueInXml);
		String filePath=this.location.getIconFile();
		long LocationId=this.location.getId();
		//Location Id Will Be NUll If This Location Allocated For Some Device
		if(LocationId!=0)
		{
			if(((!filePath.isEmpty())||(filePath!="")))
			{
				String Temp[]=filePath.split("/");
				if(!(Temp[1].equals("imonitor")))
				{
					//REMOVING USER HAS Uploaded IMAGE
					User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
					String customerIdXml = stream.toXML(user.getCustomer().getCustomerId());
					String serviceName = "agentService";
					String method = "getAgentByCustomerId";
					String result = IMonitorUtil.sendToController(serviceName, method, customerIdXml);
					Agent agent = (Agent) stream.fromXML(result);
					String ftpIp = agent.getFtpIp();
					int ftpPort = agent.getFtpPort();
					String ftpUserName = agent.getFtpUserName();
					String ftpPassword = agent.getFtpPassword();
					FTPUtil.removeFile(ftpIp, ftpPort, ftpUserName, ftpPassword, filePath);
				}
			}
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.rooms.0007");
		}
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}

	public String editLocation() throws Exception {
		String message = IMonitorUtil.formatMessage(this, "msg.imonitor.rooms.0003");
		try {
			/*
			String locatonFilePath = (String) ActionContext.getContext().getSession()
					.get("locatonFilePath");
			ActionContext.getContext().getSession().remove("locatonFilePath");
			if(locatonFilePath == null){
				locatonFilePath = createLocationFilePath(location.getName());
			}
			if (file != null) {
				File f = new File(locatonFilePath);
				f.delete();
				locatonFilePath = createLocationFilePath(this.location.getName());
				location.setIconFile(locatonFilePath);
				File fileToCreate = new File(locatonFilePath);
				FileUtils.copyFile(file, fileToCreate);
			}*/
			XStream stream = new XStream();
			user = (User) ActionContext.getContext().getSession().get(
					Constants.USER);
			this.location.setCustomer(user.getCustomer());
			String xmlString = stream.toXML(this.location);
			
			String serviceName = "locationService";
			String method = "updateLocation";
			String result = IMonitorUtil.sendToController(serviceName, method, xmlString);
			if(result.equalsIgnoreCase("yes")){
				//sumit start: save image if uploaded by user.
				if((this.uploadedImage != null)){// && this.uploadedImage.exists()){
					String userXml = stream.toXML(user);
					String customerId = user.getCustomer().getCustomerId();

					//1.Get Location Id based on the location name to rename the image.
					String locationNameXml = stream.toXML(this.location.getName());
					String customerIdXml = stream.toXML(this.location.getCustomer().getCustomerId());
					method = "getLocationIdByName";
					result = IMonitorUtil.sendToController(serviceName, method, locationNameXml, customerIdXml);
					Long locId = (Long) stream.fromXML(result);
					
					//2.Get the extension for the uploaded image.
					String fName = this.uploadedImageFileName;
					int index = fName.lastIndexOf(".");
					String ext = fName.substring(index);
					//LogUtil.info("extension: "+ext);
					
					//3.Set locationId + extension as the name of the uploaded file.
					String filePath = customerId+"/images/locations/location"+locId.longValue()+ext;	
					//LogUtil.info("filePath: "+filePath);
					InputStream inputFile = new FileInputStream(this.uploadedImage);
					
					//4.Get the agent for FTP UPLOAD based on customer Id.
					serviceName = "agentService";
					method = "getAgentByCustomerId";
					result = IMonitorUtil.sendToController(serviceName, method, customerIdXml);
					//LogUtil.info("Agent:"+result);
					Agent agent = (Agent) stream.fromXML(result);
					String ftpIp = agent.getFtpIp();
					int ftpPort = agent.getFtpPort();
					String ftpUserName = agent.getFtpUserName();
					String ftpPassword = agent.getFtpPassword();
					String locationsDir = customerId+"/images/locations";
					
					//5.Check if locations folder exists and upload the image.
					FTPUtil.createFolders(ftpIp, ftpPort, ftpUserName, ftpPassword, locationsDir);
					FTPUtil.uploadFile(ftpIp, ftpPort, ftpUserName, ftpPassword, inputFile,filePath);		
					
					//6.Set the image as iconFile for the location and update.
					String locationIdXml = stream.toXML(locId);
					String filePathXml = stream.toXML(filePath);
					serviceName = "locationService";
					method = "saveUploadedImageForLocation";
					result = IMonitorUtil.sendToController(serviceName, method, locationIdXml, filePathXml);
				}//in else part handle: what if the user has not uploaded any image for location.
				
				
				//sumit end: save image if uploaded by user.
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.rooms.0005");
			}else if(result.equalsIgnoreCase("duplicate")){
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.rooms.0003");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}

	public String getlistofLocation(){
		return SUCCESS;
	}
	
	public String listlocationsForMap ()throws Exception
	{
		XStream stream = new XStream();
		user = (User) ActionContext.getContext().getSession().get(
				Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString = stream.toXML(customer);
		String serviceName = "locationService";
		String method = "listLocationOfCustomer";
		String result = IMonitorUtil.sendToController(serviceName,method, xmlString);
		
		this.dataTable=(DataTable)stream.fromXML(result);
		
		return SUCCESS;
	}
	
	
	
	public String getlistofLocationforEnergyMg(){
		return SUCCESS;
	}
	
	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
/*
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}*/
//pari start
	public List<SystemIcon> getListIcons() {
		return listIcons;
	}
	public void setListIcons(List<SystemIcon> listIcons) {
		this.listIcons = listIcons;
	}	
//pari end	
	//sumit start:
	public File getUploadedImage() {
		
		return this.uploadedImage;
	}
	public void setUploadedImage(File uploadedImage) {
		this.uploadedImage = uploadedImage;
	
	}
	public String getUploadedImageFileName(){
		//LogUtil.info("GET FileName: "+this.uploadedImageFileName);
		return this.uploadedImageFileName;
	}
	public void setUploadedImageFileName(String fileName){
		this.uploadedImageFileName = fileName;
		//LogUtil.info("SET FileName"+this.uploadedImageFileName);
	}
	public String getUploadedImageContentType(){
		return this.uploadedImageContentType;
	}
	public void setUploadedImageContentType(String contentType){
		this.uploadedImageContentType = contentType;
	}
	public String getImvgUploadContextPath() {
		return imvgUploadContextPath;
	}


	public void setImvgUploadContextPath(String imvgUploadContextPath) {
		this.imvgUploadContextPath = imvgUploadContextPath;
	}

	/*
	public void setServletRequest(HttpServletRequest servletRequest){
		this.servletRequest = servletRequest;
	}*/
	//sumit end
	public String getCurrentIcon() {
		return currentIcon;
	}
	public void setCurrentIcon(String currentIcon) {
		this.currentIcon = currentIcon;
	}
}
