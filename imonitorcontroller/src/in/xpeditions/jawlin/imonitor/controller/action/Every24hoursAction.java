package in.xpeditions.jawlin.imonitor.controller.action;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.CostDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceDetails;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Powerinformation;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.TarrifConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.powerinfo;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.CustomerSupportManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DashboardManager;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.dao.util.dashboardDBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.notifications.EMailNotifications;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Every24hoursAction implements Job {
	
	private String emailNotificationVendor = null;
  @SuppressWarnings({ "unchecked", "unused" })
public void execute(JobExecutionContext arg0) throws JobExecutionException{
 
	
	String query= "select pw.did,sum(pw.alertValue),date(pw.alertTime)" +
			" from averagepowerinformation as pw " +
			"where date(pw.alertTime) = date(date_sub(NOW(), interval 1 day)) " +
			"group by pw.did";
	//LogUtil.info("query-----"+query);

	dashboardDBConnection DashBoard = null;
	DBConnection dbc = null;
	List<Object[]> objects = null;
	
	try {
		DashBoard = new dashboardDBConnection();
		Session session = DashBoard.openConnection();
		Query q = session.createSQLQuery(query);
		objects = q.list();
		

		XStream xStream=new XStream();
		for (Object[] strings : objects) 
		{
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -1);    
	      	        
			
			powerinfo Powerinformation=new powerinfo();
			Powerinformation.setAlertValue(IMonitorUtil.convertToString(strings[1]));
			Powerinformation.setDid(IMonitorUtil.convertToString(strings[0]));
			
			Powerinformation.setAlertTime(dateFormat.format(cal.getTime()));
			session.save(Powerinformation);
	
			
			}
		
		Transaction tx = session.beginTransaction();
		tx.commit();
		DashBoard.closeConnection();
				
	} catch (Exception e) {
		e.printStackTrace();
		LogUtil.info("Catch clause caught : {0} \n"+ e.getMessage());
	} finally {
		if (null != DashBoard) {
			DashBoard.closeConnection();
		}
	}
	
	//Naveen Added for target graph on Nov 06th 2014
	
	query= "select * from customer where customerId !='admin'";
	long totalcost=0;
	try {
		dbc = new DBConnection();
		Session sess = dbc.openConnection();
		Query q = sess.createSQLQuery(query);
		objects = q.list();
		dbc.closeConnection();
		for (Object[] strings : objects) 
		{
			long custId=(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
			 query = "select sum(pw.alertValue) as alertValue,customer from" +
					" (select did,customer from devicedetails) as d," +
					"(select alertValue,did,alertTime from powerinfo) as pw " +
					"where d.did=pw.did and d.customer="+custId+" and " +
					"date(pw.alertTime) = date(date_sub(NOW(), interval 1 day))";
			 XStream xStream=new XStream();
			 double uptodateusage=0;
			 List<Object[]> slabrange = null;
			try {
				DashBoard = new dashboardDBConnection();
				Session session = DashBoard.openConnection();
				 q = session.createSQLQuery(query);
				 Object[] powerinfoobjects = (Object[])q.uniqueResult();
					if(powerinfoobjects != null)
					{
						
						if(powerinfoobjects[0] != null)
						{
							uptodateusage=((Number) powerinfoobjects[0]).doubleValue();
							uptodateusage=uptodateusage/1000;
						}
						
					
					
					query = "select * from tariffconfiguration where customer="+custId +"";
					q = session.createSQLQuery(query);
					slabrange= q.list();
					
					query = "select kwPerUnit from targetcost where customer="+custId +"";
					q = session.createSQLQuery(query);
					Object kwPerUnit = q.uniqueResult();
					
					long Unitvalue=100;
					if(kwPerUnit != null){
						 Unitvalue= ((Number) kwPerUnit).longValue();
					}
					else
					{
						Unitvalue=100;
					}
					float totalUnit=(float) (uptodateusage/Unitvalue);
					
					//LogUtil.info("totalUnit===="+totalUnit);
					
					long totaluptodatecost=0;
					for (Object[] dataSet : slabrange)
					{	
					TarrifConfig tariffconfig=new TarrifConfig();
					tariffconfig.setId(Long.parseLong(IMonitorUtil.convertToString(dataSet[0])));
					tariffconfig.setCustomer(Long.parseLong(IMonitorUtil.convertToString(dataSet[1])));
					tariffconfig.setStartHour(Long.parseLong(IMonitorUtil.convertToString(dataSet[2])));
					tariffconfig.setEndHour(Long.parseLong(IMonitorUtil.convertToString(dataSet[3])));
					tariffconfig.setTarrifRate(IMonitorUtil.convertToString(dataSet[4]));
					tariffconfig.setStartSlabRange(Long.parseLong(IMonitorUtil.convertToString(dataSet[5])));
					tariffconfig.setEndSlabRange(Long.parseLong(IMonitorUtil.convertToString(dataSet[6])));
					

					long endslab=tariffconfig.getEndSlabRange();

					long startslab=tariffconfig.getStartSlabRange();
			
					long tarrifRate=(Long.parseLong(IMonitorUtil.convertToString(dataSet[4])));
				
					
					if(startslab==0)
					{
						if(totalUnit > startslab && totalUnit > endslab)
						{
							totaluptodatecost=totaluptodatecost+((endslab-(startslab))*tarrifRate);
						}
						else if(totalUnit > startslab && totalUnit < endslab)
						{
							totaluptodatecost=(long) (totaluptodatecost+((totalUnit-(startslab))*tarrifRate));
						}
						else if(totalUnit < startslab)
						{
							totaluptodatecost=totaluptodatecost+0;
						}
						
					}
					
					else
						{
			
						if(totalUnit > startslab && totalUnit > endslab)
						
					{
						totaluptodatecost=totaluptodatecost+((endslab-(startslab-1))*tarrifRate);
					}
					else if(totalUnit > startslab && totalUnit < endslab)
					{
						totaluptodatecost=(long) (totaluptodatecost+((totalUnit-(startslab-1))*tarrifRate));
					}
					else if(totalUnit < startslab)
					{
						totaluptodatecost=totaluptodatecost+0;
					}
					}
					}
					totalcost=totaluptodatecost;
					//LogUtil.info("totalcost===="+totalcost);
					}
					
					query="";
					
					CostDetails costdetails = new CostDetails();
					costdetails.setCustomer(custId);
					costdetails.setCost(""+totalcost);
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					
			        Calendar cal = Calendar.getInstance();
			        cal.add(Calendar.DATE, -1);  
			        costdetails.setAlertTime(dateFormat.format(cal.getTime()));
			        session.save(costdetails);
			       // LogUtil.info("cost details: "+xStream.toXML(costdetails));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	//Naveen Added on 16 Feb 2015 to send autogenerated customerreports to selected email id's 
	
	       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar cal = Calendar.getInstance();
	        String Date= sdf.format(cal.getTime());
	        emailNotificationVendor = ControllerProperties.getProperties().get(Constants.EMAIL_ALERT_NOTIFIER);
	        String FilePath = null;
	        if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER))
		     {
	        	FilePath = "/tmp/Myhomeqi_CustomerIssueReport-"+Date+".xls";
	        	
		     }else{
		    	 
		    	 FilePath = "/tmp/Eurovigil_CustomerIssueReport-"+Date+".xls";
		     }
	        
	    	File file = new File(FilePath);
	    	List<Object[]> list = new ArrayList<Object[]>();
	    	List<Object[]> customerDeviceList = new ArrayList<Object[]>();
	    	CustomerSupportManager supportManager = new CustomerSupportManager();
	    	list = supportManager.getCustomerReportObjects();
	    	
	    	
	    	
	    	 long NumberofReports=list.size();
	    	 if(NumberofReports != 0){
	    		 
	    		 String Filepath = null;
	    		 String path = null;
	    		 if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER))
			     {
	    		 path = "/tmp/Myhomeqi_CustomerIssueReport-"+Date+".xls";
			     }else{
			    	 
			     path = "/tmp/Eurovigil_CustomerIssueReport-"+Date+".xls";
			     }
			     Workbook workbook = new HSSFWorkbook();
	    		 HSSFCellStyle style=(HSSFCellStyle) workbook.createCellStyle();
	    		// HSSFFont font = (HSSFFont) workbook.createFont();
	    		// font.setFontName(HSSFFont.FONT_ARIAL);
	    		// font.setFontHeightInPoints((short)10);
	    		// font.setBoldweight((short) 10);
	    		 style.setAlignment(CellStyle.ALIGN_CENTER);
	    		// style.setFont(font);
	    		 
	    		 style.setWrapText(true);
	    		 int RowCount=1;
	    		 int deviceRowCount = 1;
	    		 HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Customer Repots");
	    		 sheet.autoSizeColumn(0);
	    		 
	    		 Row header = sheet.createRow(0);
	    		 
	  	        
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
	             
	             header.createCell(12).setCellValue("IP_CAMERA");
	             header.createCell(13).setCellValue("Z_WAVE_SWITCH");
	             header.createCell(14).setCellValue("Z_WAVE_DIMMER");
	             header.createCell(15).setCellValue("Z_WAVE_DOOR_LOCK");
	             header.createCell(16).setCellValue("Z_WAVE_DOOR_SENSOR");
	             header.createCell(17).setCellValue("Z_WAVE_PIR_SENSOR");
	             header.createCell(18).setCellValue("Z_WAVE_MINIMOTE");
	             header.createCell(19).setCellValue("Z_WAVE_SIREN");
	             header.createCell(20).setCellValue("Z_WAVE_AC_EXTENDER");
	             header.createCell(21).setCellValue("Z_WAVE_MOTOR_CONTROLLER");
	             header.createCell(22).setCellValue("Z_WAVE_SHOCK_DETECTOR");
	             header.createCell(23).setCellValue("Z_WAVE_SMOKE_SENSOR");
	             header.createCell(24).setCellValue("Z_WAVE_LCD_REMOTE");
	             header.createCell(25).setCellValue("Z_WAVE_LUX_SENSOR");
	             header.createCell(26).setCellValue("Z_WAVE_AV_BLASTER");
	             header.createCell(27).setCellValue("ZWAVE_CLAMP_SWITCH");
	             header.createCell(28).setCellValue("Z_WAVE_HEALTH_MONITOR");
	             header.createCell(29).setCellValue("UNSUPPORTED");
	             
	             
	             sheet.autoSizeColumn(0);sheet.autoSizeColumn(1);sheet.autoSizeColumn(2);sheet.autoSizeColumn(3);sheet.autoSizeColumn(4);
	             sheet.autoSizeColumn(5);sheet.autoSizeColumn(6);sheet.autoSizeColumn(7);sheet.autoSizeColumn(8);sheet.autoSizeColumn(9);
	             sheet.autoSizeColumn(10);sheet.autoSizeColumn(11);sheet.autoSizeColumn(12);sheet.autoSizeColumn(13);sheet.autoSizeColumn(14);
	             sheet.autoSizeColumn(15);sheet.autoSizeColumn(16);sheet.autoSizeColumn(17);sheet.autoSizeColumn(18);sheet.autoSizeColumn(19);
	             sheet.autoSizeColumn(20);sheet.autoSizeColumn(21);sheet.autoSizeColumn(22);sheet.autoSizeColumn(23);sheet.autoSizeColumn(24);
	             sheet.autoSizeColumn(25);sheet.autoSizeColumn(26);sheet.autoSizeColumn(27);sheet.autoSizeColumn(28);sheet.autoSizeColumn(29);
	             
	            
	             
	             
	           	             
	             for(Object[] reportObjects : list){
	            	 
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
	            	 
	            	 customerDeviceList = supportManager.getCustomerWithDeviceCount(customerId);
	            	 
	            	 for(Object[] deviceObjects : customerDeviceList){
	            		 
	            		 BigInteger count = (BigInteger) deviceObjects[2];
	            		 String deviceCount = count.toString();
	            		 String device = (String) deviceObjects[1];
	            		 
	            		 if(device.equals(Constants.IP_CAMERA)){
	            			 dataRow.createCell(12).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_SWITCH)){
	            			 dataRow.createCell(13).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_DIMMER)){
	            			 dataRow.createCell(14).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_DOOR_LOCK)){
	            			 dataRow.createCell(15).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_DOOR_SENSOR)){
	            			 dataRow.createCell(16).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_PIR_SENSOR)){
	            			 dataRow.createCell(17).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_MINIMOTE)){
	            			 dataRow.createCell(18).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_SIREN)){
	            			 dataRow.createCell(19).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_AC_EXTENDER)){
	            			 dataRow.createCell(20).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_MOTOR_CONTROLLER)){
	            			 dataRow.createCell(21).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_SHOCK_DETECTOR)){
	            			 dataRow.createCell(22).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_SMOKE_SENSOR)){
	            			 dataRow.createCell(23).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_LCD_REMOTE)){
	            			 dataRow.createCell(24).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_LUX_SENSOR)){
	            			 dataRow.createCell(25).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_AV_BLASTER)){
	            			 dataRow.createCell(26).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.ZWAVE_CLAMP_SWITCH)){
	            			 dataRow.createCell(27).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.Z_WAVE_HEALTH_MONITOR)){
	            			 dataRow.createCell(28).setCellValue(deviceCount);
	            		 }else if(device.equals(Constants.UNSUPPORTED)){
	            			 dataRow.createCell(29).setCellValue(deviceCount);
	            		 }
	            		 
	            		 
	            		 
	            		 
	            		 
	            		 
	            		 
	            	 }
	            	 
	            	 RowCount++;
	            	 
	             }
	             FileOutputStream out = null;
					     		 
	     		 //To send mail
	     		 
	             try {
	     			
	     		    out = new FileOutputStream(new File(FilePath));
	     		    workbook.write(out);
	     		    out.close();
	     		    EMailNotifications eMailNotifications = new EMailNotifications();
	     		    
	     		   // String []Recepits={"b.shashidhar@imonitorsolutions.com","girish.gaur@imonitorsolutions.com","sajith.kumar@imonitorsolutions.com"};
	     		    String []Recepits={"b.shashidhar@imonitorsolutions.com"};
	     		    eMailNotifications.SendXlsSheet(Recepits, FilePath);
	     		    
	     		     
	     		} catch (FileNotFoundException e) {
	     		    e.printStackTrace();
	     		} catch (IOException e) {
	     		    e.printStackTrace();
	     		}
	     		 
	    	 }*/
	    	 
	    	 
	    	 
	
  }
}


//insert into averagepowerinformation(deviceId,deviceName,deviceIcon,gateway,locationName,enableList,deviceType,alertParam,customer,avgalertvalue)select d.deviceId,d.friendlyName,i.fileName,d.gateWay,l.name,d.enableList,dt.name,d.lastAlert,g.customer,avg(a.alertValue) from imonitor3.alertsfromimvg as a  join imonitor3.device as d on a.device=d.id join icon as i on d.icon=i.id join imonitor3.location as l on d.location=l.id  join imonitor3.gateway as g on d.gateWay=g.id join imonitor3.devicetype as dt on d.deviceType=dt.id group by a.device";

