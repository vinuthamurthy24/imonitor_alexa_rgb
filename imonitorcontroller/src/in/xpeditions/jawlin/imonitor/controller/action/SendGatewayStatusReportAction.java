package in.xpeditions.jawlin.imonitor.controller.action;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;

import in.xpeditions.jawlin.imonitor.controller.notifications.EMailNotifications;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.util.Constants;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendGatewayStatusReportAction implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		String query= "select c.customerId,s.name,c.mobile,c.email,g.macId from gateway as g left join customer as c on g.customer=c.id left join status as s on g.status=s.id where g.customer=c.id and s.name in ('Online','Offline') order by s.name";
		DBConnection dbc = null;
		String emailNotificationVendor = ControllerProperties.getProperties().get(Constants.EMAIL_ALERT_NOTIFIER);
		String FilePath = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
	    String Date= sdf.format(cal.getTime());
	     
		if(emailNotificationVendor.equalsIgnoreCase(Constants.IMS_EMAIL_NOTIFIER))
	     {
			
			FilePath = "/tmp/Myhomeqi_GatewayReport-"+Date+".xls";
			File file = new File(FilePath);
			List<Object[]> list = new ArrayList<Object[]>();
			
			try {
				
				dbc = new DBConnection();
				Session session = dbc.openConnection();
				Query q = session.createSQLQuery(query);
				list = q.list();
				
				int totalGateways = list.size();
				
				
				query = "select count(*) from gateway as g left join status as s on g.status=s.id where s.name='Online'";
				Session session1 = dbc.openConnection();
				Query q1 = session1.createSQLQuery(query);
				BigInteger lCount = (BigInteger) q1.uniqueResult();
				int onlineGateways = lCount.intValue();
				int offlineGateways = totalGateways - onlineGateways;
				
				
				Workbook workbook = new HSSFWorkbook();
				HSSFCellStyle style=(HSSFCellStyle) workbook.createCellStyle();
				style.setAlignment(CellStyle.ALIGN_CENTER);
				style.setWrapText(true);
				int RowCount=4;
				HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Gateway status");
				sheet.autoSizeColumn(0);
				
				Row totalInfo = sheet.createRow(0);
				totalInfo.createCell(0).setCellValue("Total gateways");
				totalInfo.createCell(1).setCellValue(String.valueOf(totalGateways));
				
				Row offlineInfo = sheet.createRow(1);
				offlineInfo.createCell(0).setCellValue("Offline gateways");
				offlineInfo.createCell(1).setCellValue(String.valueOf(offlineGateways));
				
				Row onlineInfo = sheet.createRow(2);
				onlineInfo.createCell(0).setCellValue("Online gateways");
				onlineInfo.createCell(1).setCellValue(String.valueOf(onlineGateways));
				
				Row header = sheet.createRow(3);
				
                header.createCell(0).setCellValue("customerId");
	  	        header.createCell(1).setCellValue("Gateway status");
	  	        header.createCell(2).setCellValue("Mobile");
	  	        header.createCell(3).setCellValue("E-mail");
	  	        header.createCell(4).setCellValue("Gateway MacId");
	  	        sheet.autoSizeColumn(0); sheet.autoSizeColumn(1); sheet.autoSizeColumn(2); sheet.autoSizeColumn(3); sheet.autoSizeColumn(4);
			
	  	      for(Object[] statusObjects : list){
	  	     String customerId = (String) statusObjects[0];
           	 String status = (String) statusObjects[1];
           	 String mobile = (String) statusObjects[2];
           	 String email = (String) statusObjects[3];
           	 String macId = (String) statusObjects[4];
           	 
           	 Row dataRow = sheet.createRow(RowCount);
        	 dataRow.setRowStyle(style);
        	 
        	 dataRow.createCell(0).setCellValue(customerId);
        	 dataRow.createCell(1).setCellValue(status);
        	 dataRow.createCell(2).setCellValue(mobile);
        	 dataRow.createCell(3).setCellValue(email);
        	 dataRow.createCell(4).setCellValue(macId);
        	 RowCount++;
	  	      }	//end for loop	
	  	      
	  	      
	  	    FileOutputStream out = null;
	  	    out = new FileOutputStream(new File(FilePath));
		    workbook.write(out);
		    out.close();
		    EMailNotifications eMailNotifications = new EMailNotifications();
		    
		 //   String []Recepits={"naveen.ms@imonitorsolutions.com"};
		    String []Recepits={"girish.gaur@imonitorsolutions.com","vishal.hasure@imonitorsolutions.com","naveen.ms@imonitorsolutions.com","ganesh.joshi@imonitorsolutions.com"};
		    eMailNotifications.SendXlsSheet(Recepits, FilePath);
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				dbc.closeConnection();
			}
			
			
			
	     }//end if condition
		
		
	}
	
	
	

}
