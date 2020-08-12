package in.imsipl.cms.action.supportuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerReport;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IssueStatus;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.RootCause;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Severity;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorProperties;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;





public class SupportAction extends ActionSupport{

	/**
	 * Author Naveen M S
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Customer> customers;
	private List<Severity> severity;
	private CustomerReport customerReport;
	private String selectedCustomer;
	private DataTable dataTable;
	private List<IssueStatus> issueStatus;
	private String selectedReport;
	private String customerId;
	private String PassWord;
	private String filepath=IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH);
	private String uploadPath=IMonitorProperties.getPropertyMap().get(Constants.REPORT_UPLOAD_PATH);
	private String dateFormat;
	private List<RootCause> rootCauseList;
	
	@SuppressWarnings("unchecked")
	public String createnewreport() throws Exception{
		
		String serviceName = "customerService";
		String method = "listcustomerforreport";
		String valueInXml = IMonitorUtil.sendToController(serviceName, method);
		XStream stream = new XStream();
		setCustomers((List<Customer>) stream.fromXML(valueInXml));
		
		serviceName="customerService";
		method = "togetseveritylist";
		String levelInXml = IMonitorUtil.sendToController(serviceName, method);
		this.severity = (List<Severity>) stream.fromXML(levelInXml);
		
		serviceName="customerService";
		method = "togetstatuslist";
		String statusInXml = IMonitorUtil.sendToController(serviceName, method);
		this.issueStatus = (List<IssueStatus>) stream.fromXML(statusInXml);
		
		
		serviceName="customerService";
		method = "togetrootcauselist";
		String rootCauseInXml = IMonitorUtil.sendToController(serviceName, method);
		this.rootCauseList = (List<RootCause>) stream.fromXML(rootCauseInXml);
		
		
		return SUCCESS;
	}
	
@SuppressWarnings("unchecked")
public String saveCustomerReport() throws Exception{
	    
	    XStream stream = new XStream();
	    Customer customer = new Customer();
	    String selectedcustomer = stream.toXML(this.selectedCustomer);
	   
	   /* customer.setId(this.selectedCustomer);
	    this.customerReport.setCustomerId(customer);*/
	    String serviceName = "customerService";
		String method = "getCustomerByCustomerId";
		
		String xml = IMonitorUtil.sendToController(serviceName, method, selectedcustomer);
		
		Customer cust = (Customer) stream.fromXML(xml);
		this.customerReport.setCustomerId(cust);
	    Date reportedDate = this.customerReport.getReportedDate();
	    Calendar calobj = Calendar.getInstance();
	    DateFormat df = new SimpleDateFormat("HH:mm");
	    DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
	    String time = df.format(calobj.getTime());
	    
	   // String reportedDateTime = df1.format(reportedDate);
	    //reportedDateTime = reportedDateTime+" "+time;
	    StringBuilder datetime = new StringBuilder(df1.format(reportedDate)).append(" ").append(time);
	    String reportedDateTime = datetime.toString();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	    Date date = formatter.parse(reportedDateTime);
	    this.customerReport.setReportedDate(date);
	    
		 serviceName = "customerService";
		 method = "savereportforcustomer";
		String reportxml = stream.toXML(this.customerReport); 
		
		String result = IMonitorUtil.sendToController(serviceName, method, reportxml);
		
        if(result.equalsIgnoreCase("success")){
    		
    		ActionContext.getContext().getSession().put(Constants.MESSAGE, "Customer report saved successfully.");
    	}else{
    		
    		ActionContext.getContext().getSession().put(Constants.MESSAGE, "Error while saving customer report. Please contact iMonitor support");
    	}
		return SUCCESS;
	}

public String tpListCustomerReports() throws Exception {
	XStream stream = new XStream();
	String xmlString = stream.toXML(this.dataTable);
	String resultXml="";
	
	//String DisplayStart = stream.toXML(new Integer(displayStart).toString());

	//String XMLdisplayLengthAlarm = stream.toXML(new Integer(displayLengthAlarm).toString());
	
	resultXml =IMonitorUtil.sendToController("customerService", "listAskedCustomersReports",xmlString);	
	
	dataTable = ((DataTable) stream.fromXML(resultXml));
	
	
	return SUCCESS;
}

 public String toListResolvedReports() throws Exception {
	XStream stream = new XStream();
	String xmlString = stream.toXML(this.dataTable);
	String resultXml="";
	
	//String DisplayStart = stream.toXML(new Integer(displayStart).toString());

	//String XMLdisplayLengthAlarm = stream.toXML(new Integer(displayLengthAlarm).toString());
	
	resultXml =IMonitorUtil.sendToController("customerService", "listAskedResolvedReports",xmlString);	
	
	dataTable = ((DataTable) stream.fromXML(resultXml));
	
	
	return SUCCESS;
}
    public String toredirect() throws Exception{
    	
    	
    	return SUCCESS;
    }
    
    
    @SuppressWarnings("unchecked")
	public String toViewSelectedReport() throws Exception{
    	
    	XStream stream = new XStream();
    	String reportID= this.selectedReport;
        String serviceName="customerService";
		String method = "togetseveritylist";
		String levelInXml = IMonitorUtil.sendToController(serviceName, method);
		this.severity = (List<Severity>) stream.fromXML(levelInXml);
		
		serviceName="customerService";
		method = "togetstatuslist";
		String statusInXml = IMonitorUtil.sendToController(serviceName, method);
		this.issueStatus = (List<IssueStatus>) stream.fromXML(statusInXml);
		
		serviceName="customerService";
		method = "togetrootcauselist";
		String rootCauseInXml = IMonitorUtil.sendToController(serviceName, method);
		this.rootCauseList = (List<RootCause>) stream.fromXML(rootCauseInXml); 
		
		this.dateFormat = IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER_DISPLAY);
		serviceName="customerService";
		method = "togetselectedCustomerReport";
		reportID = stream.toXML(reportID);
		String reportxml = IMonitorUtil.sendToController(serviceName, method, reportID);
		this.customerReport = (CustomerReport) stream.fromXML(reportxml);
		
    	return SUCCESS;
    }
    
    
    @SuppressWarnings("unchecked")
	public String todownloadcustomerreports() throws Exception{
    	    	
    	XStream stream = new XStream();
    	String path = uploadPath+"customerReports/";
    	File file = new File(path);
    	File[] files = file.listFiles(); 
    	 for (File f:files) 
         {
         	if (f.isFile() && f.exists()) 
             { 
         		f.delete();
         		
             }else{
             	LogUtil.info("cant delete a file due to open or error");
             
            } 
         }  
    	 
    	 String resultXml =IMonitorUtil.sendToController("customerService", "objectsforexcelreport");
    	   
    	 
    	 List<Object[]> objects = (List<Object[]>) stream.fromXML(resultXml);
    	 long NumberofReports=objects.size();
    	 
    	 
    	 
    	 if(NumberofReports != 0){
    		 
    		 /*Calendar cal = Calendar.getInstance();
    		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		 String Date= sdf.format(cal.getTime());*/
    		 String Filepath = null;
    		 Filepath = uploadPath+"customerReports/customerReports.xls";
    		 Workbook workbook = new HSSFWorkbook();
    		 CellStyle style=workbook.createCellStyle();;
  			 style.setAlignment(CellStyle.ALIGN_CENTER);
  	         style.setWrapText(true);
  	         int RowCount=1;
  	         filepath+="customerReports/customerReports.xls";
  	         HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Customer Repots");
  	         Row header = sheet.createRow(0);
  	         header.setRowStyle(style);
  	         header.createCell(0).setCellValue("Customer Name");
  	         header.createCell(1).setCellValue("Report Person");
  	         header.createCell(2).setCellValue("Reporting Date");
  	         header.createCell(3).setCellValue("Severity");
  	         header.createCell(4).setCellValue("Issue Description");
  	         header.createCell(5).setCellValue("Resolution Provided");
             header.createCell(6).setCellValue("Resolution Date");
             header.createCell(7).setCellValue("Allocated Person");
             header.createCell(8).setCellValue("Action Taken");
             header.createCell(9).setCellValue("Action Date");
             header.createCell(10).setCellValue("Final status");
             header.createCell(11).setCellValue("Final Date");
             
             
             
             for(Object[] reportObjects : objects){
            	 
            	 String customerId = (String) reportObjects[1];
            	 String ReportPerson = (String) reportObjects[2];
            	 String ReportDate = (String) reportObjects[3];
            	 String severity = (String) reportObjects[4];
            	 String Issue = (String) reportObjects[5];
            	 String ResolutionProvided = (String) reportObjects[6];
            	 String resolutionDate = (String) reportObjects[7];
            	 String allocatedPerson = (String) reportObjects[8];
            	 String actionTaken = (String) reportObjects[9];
            	 String actionDate = (String) reportObjects[10];
            	 String finalStatus = (String) reportObjects[11];
            	 String finalDate = (String) reportObjects[12];
            	 
            	 Row dataRow = sheet.createRow(RowCount);
            	 dataRow.setRowStyle(style);
            	 
            	 dataRow.createCell(0).setCellValue(customerId);
            	 dataRow.createCell(1).setCellValue(ReportPerson);
            	 dataRow.createCell(2).setCellValue(ReportDate);
            	 dataRow.createCell(3).setCellValue(severity);
            	 dataRow.createCell(4).setCellValue(Issue);
            	 dataRow.createCell(5).setCellValue(ResolutionProvided);
            	 dataRow.createCell(6).setCellValue(resolutionDate);
            	 dataRow.createCell(7).setCellValue(allocatedPerson);
            	 dataRow.createCell(8).setCellValue(actionTaken);
            	 dataRow.createCell(9).setCellValue(actionDate);
            	 dataRow.createCell(10).setCellValue(finalStatus);
            	 dataRow.createCell(11).setCellValue(finalDate);
            	 
            	 RowCount++;
            	 
             }
             FileOutputStream out = new FileOutputStream(new File(Filepath));
             workbook.write(out);
     		 out.close();
    	     }
    	 
    	 
    	return SUCCESS;
    }
    
    public String toUpdateCustomerReport() throws Exception{
    	
    	XStream stream = new XStream();
    	String reportXml = stream.toXML(this.customerReport);
    	String xml = IMonitorUtil.sendToController("customerService", "updatecustomerreport", reportXml);
    	String result = (String) stream.fromXML(xml);
    	if(result.equalsIgnoreCase("success")){
    		
    		ActionContext.getContext().getSession().put(Constants.MESSAGE, "Success~Customer report updated successfully.");
    	}else{
    		
    		ActionContext.getContext().getSession().put(Constants.MESSAGE, "Failure~Error while updating customer report. Please contact iMonitor support");
    	}
    	return SUCCESS;
    }
    
    public String toDeleteReports() throws Exception{
    	
    	
    	return SUCCESS;
    }
    
   public String deleteCustomerReports() throws Exception{
    	
	    XStream stream = new XStream();
    	
    	String adminPassword = IMonitorProperties.getPropertyMap().get(Constants.REPORTPASS);
    	String reportId = this.selectedReport;
    	
    	reportId = (String) stream.toXML(reportId);
    	
    	if(!(PassWord.equals(adminPassword)))
		{
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Failure~Not Autorized.");
			return ERROR;
			
		}else{
			
			IMonitorUtil.sendToController("customerService", "deleteCustomerReport", reportId);
			ActionContext.getContext().getSession().put(Constants.MESSAGE, "Success~Customer report deleted successfully.");
	    	return SUCCESS;
		}

    }

@SuppressWarnings("unchecked")
public String togetrfslist() throws Exception{
	   
	   XStream stream = new XStream();
	   String serviceName="customerService";
		String method = "togetseveritylist";
		String levelInXml = IMonitorUtil.sendToController(serviceName, method);
		this.severity = (List<Severity>) stream.fromXML(levelInXml);
		
		serviceName="customerService";
		method = "togetstatuslist";
		String statusInXml = IMonitorUtil.sendToController(serviceName, method);
		this.issueStatus = (List<IssueStatus>) stream.fromXML(statusInXml);
		
		
		serviceName="customerService";
		method = "togetrootcauselist";
		String rootCauseInXml = IMonitorUtil.sendToController(serviceName, method);
		this.rootCauseList = (List<RootCause>) stream.fromXML(rootCauseInXml);
	   
	   return SUCCESS;
   }
	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Severity> getSeverity() {
		return severity;
	}

	public void setSeverity(List<Severity> severity) {
		this.severity = severity;
	}

	public CustomerReport getCustomerReport() {
		return customerReport;
	}

	public void setCustomerReport(CustomerReport customerReport) {
		this.customerReport = customerReport;
	}


	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	

	public List<IssueStatus> getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(List<IssueStatus> issueStatus) {
		this.issueStatus = issueStatus;
	}

	public String getSelectedReport() {
		return selectedReport;
	}

	public void setSelectedReport(String selectedReport) {
		this.selectedReport = selectedReport;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public List<RootCause> getRootCauseList() {
		return rootCauseList;
	}

	public void setRootCauseList(List<RootCause> rootCauseList) {
		this.rootCauseList = rootCauseList;
	}

	public String getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(String selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	

}
