/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UploadsByImvg;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorProperties;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;


import java.io.FileOutputStream;
//import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class ImvgAlertAction extends ActionSupport {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2378825267657311326L;
	private List<Object[]> objects;
	private int displayStart;
	private int rowSize = Constants.ALERTSHOWING_SIZE;
	private int rowSizeAlarm = 10;
	private int displayLength=rowSize;
	private int displayLengthAlarm=rowSizeAlarm;
	private String alertFromImvgId;
	private String deviceId;
	private String imvgUploadContextPath=IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH); 
	private UploadsByImvg uploadsByImvg= new UploadsByImvg();
	private DataTable dataTable;
	
	private DataTable dataTableAlert;
	private List<Device> devices;
	private String gateWayId;
	
	private String FromDate;
	private String ToDate;
	private long selctedDevice;
	private String wisetype;
	private int id;
	private String selectedAlarms;
	private String filepath=IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH);
	private long NumberofAlerts=0;
	private String alertId;
	private Object object;
	private ArrayList<Object[]> deviceNodeInfo;

	public String listImvgAlert() throws Exception{
		XStream stream = new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString1 = stream.toXML(customer);
		String xmlString2 = stream.toXML(new Integer(displayStart).toString());
		String xmlString3 = stream.toXML(new Integer(displayLength).toString());
		String resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlert", xmlString1,xmlString2,xmlString3);
		dataTable = ((DataTable) stream.fromXML(resultXml));		
		
		return SUCCESS;
	}
	
	public String listImvgAlertForAlarm() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		
		
		/*String FromDate=this.FromDate;
		String ToDate=this.ToDate;
		long SelectedDevice=this.selctedDevice;*/
		String resultXml="";
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String Customer = stream.toXML(customer);
		/*String XmlSelectedDevice=stream.toXML(SelectedDevice);
		String XmlFromdate=stream.toXML(FromDate);
		String XmlTodate=stream.toXML(ToDate);*/
		String DisplayStart = stream.toXML(new Integer(displayStart).toString());
		String XMLdisplayLengthAlarm = stream.toXML(new Integer(displayLengthAlarm).toString());
		
		/*if(SelectedDevice!=0)
		{
			
			resultXml=IMonitorUtil.sendToController("alertService", "listImvgAlertForFiltering", Customer,DisplayStart,XmlSelectedDevice,XmlFromdate,XmlTodate);		
		}
		else
		{
			
			resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlertForAlarm", Customer,DisplayStart,XMLdisplayLengthAlarm,xmlString);		
		}*/
		resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlertForAlarm",Customer,DisplayStart,XMLdisplayLengthAlarm,xmlString);		
		dataTable = ((DataTable) stream.fromXML(resultXml));
		
		return SUCCESS;
	}
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public String uploadedImvgAlertAttachment() throws Exception{
		XStream xStream = new XStream();
		String xml = xStream.toXML(alertFromImvgId);
	
		String resultXml = IMonitorUtil.sendToController("alertService", "getImvgAlertAttachment", xml);
		 objects  = (List<Object[]>) xStream.fromXML(resultXml);
		
		 imvgUploadContextPath = IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String uploadedImvgAlertStreamAttachment() throws Exception{
		XStream xStream = new XStream();
		String xml = xStream.toXML(alertFromImvgId);
		String resultXml = IMonitorUtil.sendToController("alertService", "getUploadedImvgAlertStreamAttachment", xml);
		 objects  = (List<Object[]>) xStream.fromXML(resultXml);
		 imvgUploadContextPath = IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String getdeviceforalerts() throws Exception
	{
		XStream stream=new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String customerXmlString = stream.toXML(customer);
		String xmlOfDeviceList = IMonitorUtil.sendToController("customerService", "getAllDevicesOfCustomer", customerXmlString);
		devices = (List<Device>) stream.fromXML(xmlOfDeviceList);
		return SUCCESS;
		
	}
		
	
	public String listAlarmsByDateAndDevice() throws Exception
	{
		XStream stream=new XStream();
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString1 = stream.toXML(customer);
	
		DataTable datatable=this.dataTable;
		String xmlstring4=stream.toXML(datatable);
		String resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlertForAlarm", xmlString1,xmlstring4);
		
		dataTable = ((DataTable) stream.fromXML(resultXml));
		return SUCCESS;
	}
		
	
	public String GetAlarmsByDevice() throws Exception
	{
		XStream stream = new XStream();
		String SelectedType=this.wisetype;
		String FromDate=this.FromDate;
		String ToDate=this.ToDate;
		long SelectedDevice=this.selctedDevice;
		
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String Customer = stream.toXML(customer);
		
		String XMLdisplayLengthAlarm = stream.toXML(new Integer(displayLengthAlarm).toString());
		String XmlSelectedDevice=stream.toXML(SelectedDevice);
		String XmlFromdate=stream.toXML(FromDate);
		String XmlTodate=stream.toXML(ToDate);
		String DisplayStart = stream.toXML(new Integer(displayStart).toString());
		
		
		if(SelectedType.equals("Alarm"))
		{
			if((FromDate.isEmpty()) && (ToDate.isEmpty()) && (SelectedDevice==1))
			{
						
				String resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlert", Customer,DisplayStart,XMLdisplayLengthAlarm);
				dataTable = ((DataTable) stream.fromXML(resultXml));		
			}	
			else
			{		
				
				String resultXml =IMonitorUtil.sendToController("alertService","listImvgAlertForAlarmPerDevice",XmlSelectedDevice,XmlFromdate,XmlTodate,DisplayStart);
				dataTable = ((DataTable) stream.fromXML(resultXml));	
			}
		}
		else
		{
			String resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlertForPerdeviceAndDate", Customer,DisplayStart,XmlSelectedDevice,XmlFromdate,XmlTodate);		
			dataTable = ((DataTable) stream.fromXML(resultXml));	
		}
		
		
		return SUCCESS;
	}
	
	public String deleteAlaram() throws Exception 
	{
		/*String SelectedType=this.wisetype;*/
		XStream stream=new XStream();
		
		String SelectedAlaram=this.selectedAlarms;
		String xmlString=stream.toXML(SelectedAlaram);
		
		IMonitorUtil.sendToController("alertService", "DeleteSelectedAlarms", xmlString);
		
		/*String FromDate=this.FromDate;
		String ToDate=this.ToDate;
		long SelectedDevice=this.selctedDevice;
		
		String resultXml="";
		if(SelectedType.equals("Alarm"))
		{
			
			if((FromDate.isEmpty()) && (ToDate.isEmpty()) && (SelectedDevice==1))
			{
				
				User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
				Customer customer = user.getCustomer();
				String xmlString1 = stream.toXML(customer);
				String xmlString2 = stream.toXML(new Integer(displayStart).toString());
				String xmlString3 = stream.toXML(new Integer(displayLengthAlarm).toString());		
				resultXml=IMonitorUtil.sendToController("alertService", "listImvgAlert", xmlString1,xmlString2,xmlString3);
				//LogUtil.info(resultXml);
				dataTable = ((DataTable) stream.fromXML(resultXml));		
			}	
			else
			{		
				
				String XmlSelectedDevice=stream.toXML(SelectedDevice);
				String XmlFromdate=stream.toXML(FromDate);
				String XmlTodate=stream.toXML(ToDate);
				String DisplayStart = stream.toXML(new Integer(displayStart).toString());
				resultXml =IMonitorUtil.sendToController("alertService","listImvgAlertForAlarmPerDevice",XmlSelectedDevice,XmlFromdate,XmlTodate,DisplayStart);
				dataTable = ((DataTable) stream.fromXML(resultXml));	
			}
			
		}
		else
		{
			User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Customer customer = user.getCustomer();
			String Customer = stream.toXML(customer);
			String XmlSelectedDevice=stream.toXML(SelectedDevice);
			String XmlFromdate=stream.toXML(FromDate);
			String XmlTodate=stream.toXML(ToDate);
			String DisplayStart = stream.toXML(new Integer(displayStart).toString());
			String XMLdisplayLengthAlarm = stream.toXML(new Integer(displayLengthAlarm).toString());
			
			if(SelectedDevice!=0)
			{
				resultXml=IMonitorUtil.sendToController("alertService", "listImvgAlertForFiltering", Customer,DisplayStart,XmlSelectedDevice,XmlFromdate,XmlTodate);		
			}
			else
			{
				resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlertForAlarm", Customer,DisplayStart,XMLdisplayLengthAlarm);		
			}
			resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlertForAlarm",Customer,DisplayStart,XMLdisplayLengthAlarm,xmlString);		
			dataTable = ((DataTable) stream.fromXML(resultXml));	
		}*/
		return SUCCESS;
	
	}
	// ******************************************************** sumit start: Internationalization ********************************************
	@SuppressWarnings({ "unchecked" })
	public String requetForPdf() throws Exception 
	{
		
		XStream stream=new XStream();
		String SelectedType=this.wisetype;
		String FromDate=this.FromDate;
		String ToDate=this.ToDate;
		long SelectedDevice=this.selctedDevice;
		
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString1 = stream.toXML(customer);
		String XmlSelectedDevice=stream.toXML(SelectedDevice);
		String XmlFromdate=stream.toXML(FromDate);
		String XmlTodate=stream.toXML(ToDate);
		String XmlSelectedType=stream.toXML(SelectedType);
		String DisplayStart = stream.toXML(new Integer(displayStart).toString());
		String DisplayLength = stream.toXML(new Integer(displayLengthAlarm).toString());	
		String resultXml =IMonitorUtil.sendToController("alertService", "requestForPdf", xmlString1,XmlSelectedDevice,XmlFromdate,XmlTodate,XmlSelectedType,DisplayStart,DisplayLength);
		
		List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
		Device device=new Device();
		if(selctedDevice!=1)
		{
			resultXml =IMonitorUtil.sendToController("deviceService", "getDeviceById", XmlSelectedDevice);
			device=(Device) stream.fromXML(resultXml);
		}
		NumberofAlerts=objects.size();
	
		if(NumberofAlerts!=0)
		{
			
			String customerIdXml = stream.toXML(customer.getCustomerId());
			String serviceName = "agentService";
			String method = "getAgentByCustomerId";
			String result = IMonitorUtil.sendToController(serviceName, method, customerIdXml);
			Agent agent = (Agent) stream.fromXML(result);
			String ftpUserName = agent.getFtpUserName();
			
			
			
		String FILE = "/home/"+ftpUserName+"/"+customer.getCustomerId()+"/"+"Alerts.pdf";
		Document document=new Document();
		try {
			PdfWriter.getInstance(document,new FileOutputStream(FILE));
			document.open();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
			
			// ********************************************************** sumit start: *****************************************************
			/*
			String language = ActionContext.getContext().getLocale().getLanguage();
			String country = ActionContext.getContext().getLocale().getCountry();
			LogUtil.info("language: "+language);
			LogUtil.info("country: "+country);
			String languageCountry = language +"_"+ country;
			LogUtil.info("languageCountry: "+languageCountry);
			int languageCountryCode = 0;											//default language Country Code refers to [english_UnitedStates]
			if(languageCountry.equalsIgnoreCase(Constants.ka_IN)){
				languageCountryCode = Constants.kannada_IN;
			}else if(languageCountry.equalsIgnoreCase(Constants.en_US)){
				languageCountryCode = Constants.english_US;
			}
			LogUtil.info("languageCountryCode: "+languageCountryCode);
			String fontName = "";
			URL url = ActionSupport.class.getResource("/WEB-INF/fontName/times.ttf");				//default
			InputStream is = ActionSupport.class.getResourceAsStream("/WEB-INF/fontName/times.ttf");
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			url = cl.getResource("times.ttf");
			LogUtil.info("URL: "+url.getFile());
			switch (languageCountryCode) {
			case 0:		try {
							LogUtil.info("case 0");
							is = ActionSupport.class.getResourceAsStream("times.ttf");
							//url = ActionSupport.class.getResource("times.ttf");
							//LogUtil.info(" url path: "+url.getPath());
							//LogUtil.info("url: "+url);
							LogUtil.info("is: "+is.toString());
						} catch (Exception e1) {
							LogUtil.info("English ttf Exception", e1);
						}
						break;//"resources/fontName/times.ttf";	break;				//[English]
			
			case 1: 	try {
							LogUtil.info("case 1");
							is = ActionSupport.class.getResourceAsStream("kannada.ttf");
							//url = ActionSupport.class.getResource("kannada.ttf");	
							 LogUtil.info("is: "+is.toString());
							//LogUtil.info("url path: "+url.getFile());
						} catch (Exception e2) {
							LogUtil.info("Kannada ttf Exception", e2);
						}
						break;				//[Kannada]

			default:	fontName = "resources/fontName/times.ttf";	
						LogUtil.info("fontName: "+fontName);
						break;
			}
			
			
			
			String encoding = BaseFont.IDENTITY_H;
			
			boolean embedded = false;
			LogUtil.info("sk0");
			*/
			Font font = null;
			try {
				
				//BaseFont baseFont = BaseFont.createFont(url.getPath(), encoding, embedded);
				//BaseFont baseFont = BaseFont.createFont(is.toString(), encoding, embedded);
				//font = new Font(baseFont, 12F);
				font = new Font();
				
			} /*catch (DocumentException e) {
				LogUtil.info("Document Exception", e);
			} catch (IOException e) {
				LogUtil.info("IOException", e);
			} */catch (NullPointerException e) {
				LogUtil.info("NullPointerException", e);
			} catch (Exception e) {
				LogUtil.info("Got Exception", e);
			}
					
	    Paragraph preface = new Paragraph();
	    int alignment=10;
		preface.setAlignment(alignment);
			
			//1.Generate the header section
			String formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0001"			//Document generated by [customer id]
					+ Constants.MESSAGE_DELIMITER_1 + customer.getCustomerId());		
			document.add(new Paragraph(formatText, font));
			
			formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0002"					//On [current date]
					+ Constants.MESSAGE_DELIMITER_1 + new Date());					
			document.add(new Paragraph(formatText, font));
			
			if(SelectedType.equals("Alarm")){
				
				//2.Generate Alarms Details.
				if((FromDate.isEmpty()) && (ToDate.isEmpty()) && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0001");	//Listing all alarms
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());	
					document.add(new Paragraph(formatText, font));	    		
				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0003"	//Listing alarms between date [arg1] and date [arg2]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());	
					document.add(new Paragraph(formatText, font));

				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice!=0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0003"	//Listing alarms between date [arg1] and date [arg2]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0003"
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());			//For Selected Device [deviceName]
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));

				}
				if(FromDate.isEmpty() && ToDate.isEmpty() && SelectedDevice!=0){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0004"	//Listing Alarms Of Selected Device [device Name]
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());
					
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));
					
				}
			}else{
			
				//3.Generate Alerts Details.
				if((FromDate.isEmpty()) && (ToDate.isEmpty()) && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0001");	//Listing All Alerts
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));
				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0003"	//Listing Alerts Between Date [arg0] And To Date [arg1]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));

				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice!=0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0003"	//Listing Alerts Between Date [arg0] And To Date [arg1]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0003"			//For Selected Device [deviceName]
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());			
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));

				}
				if(FromDate.isEmpty() && ToDate.isEmpty() && SelectedDevice!=0){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0004"	//Listing alerts of selected device [device name]
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));
				}
			}
			
			
			/*
			preface.add(new Paragraph("Documented Generated By:"+customer.getCustomerId()));		//0001
			preface.add(new Paragraph("On:"+new Date()));											//0002
		addEmptyLine(preface,1);
		if(SelectedType.equals("Alarm"))
	    {
	    	if((FromDate.isEmpty()) && (ToDate.isEmpty()) && (SelectedDevice==1))
			{
					preface.add(new Paragraph("Listing All Alarm"));								//alarms.0001
					preface.add(new Phrase("Total Number of Alarms:"+objects.size()));				//alarms.0002
			}
	    	
	    	if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice==1))
			{
					preface.add(new Paragraph("Listing Alarm Between Selected From Date: "+FromDate+" And To Date: "+ToDate));	//alarms.0003a alarms.0003b
	    		
					preface.add(new Phrase("Total Number of Alarms:"+objects.size()));				//alarms.0002

			}
	    	if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice!=1))
			{
					//preface.add(new Paragraph("Listing Alarm Between Date: "+FromDate+" And "+ToDate+" For Selected Device: "+device.getFriendlyName()));
					preface.add(new Paragraph("Listing Alarm Between Date: "+FromDate+" And "+ToDate));	//alarms.0003a alarms.0003b
					preface.add(new Paragraph("For Selected Device: "+device.getFriendlyName()));		//0003
					preface.add(new Phrase("Total Number of Alarms:"+objects.size()));					//alarms.0002

			}
	    	if(FromDate.isEmpty() && ToDate.isEmpty() && SelectedDevice!=1)
			{
					preface.add(new Paragraph("Listing Alarm Of Selected Device:  "+device.getFriendlyName()));	//alarms.0004
					preface.add(new Phrase("Total Number of Alarms:"+objects.size()));					//alarms.0002
	    		
			}
	    }
	    else
	    {
	    	
	    	if((FromDate.isEmpty()) && (ToDate.isEmpty()) && (SelectedDevice==1))
			{
					preface.add(new Paragraph(" Listing All Alerts"));						//alerts.0001
					preface.add(new Phrase("Total Number of Alerts:"+objects.size()));		//alerts.0002
			}
	    	
	    	if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice==1))
			{
					preface.add(new Paragraph("Listing Alerts Between Selected From Date: "+FromDate+" And To Date: "+ToDate));	//alerts.0003a alerts.0003b
	    		
					preface.add(new Phrase("Total Number of Alerts:"+objects.size()));		//alerts.0002

			}
	    	if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice!=1))
			{
					//preface.add(new Paragraph("Listing Alerts Between Date: "+FromDate+" And "+ToDate+" For Selected Device: "+device.getFriendlyName()));
					preface.add(new Paragraph("Listing Alerts Between Date: "+FromDate+" And "+ToDate));	//alerts.0003a alerts.0003b
					preface.add(new Paragraph("For Selected Device: "+device.getFriendlyName()));	//0003
					preface.add(new Phrase("Total Number of Alerts:"+objects.size()));		//alerts.0002

			}
	    	if(FromDate.isEmpty() && ToDate.isEmpty() && SelectedDevice!=1)
			{
					preface.add(new Paragraph(" Listing Alerts Of Selected Device:  "+device.getFriendlyName()));	//alerts.0004
					preface.add(new Phrase("Total Number of Alerts:"+objects.size()));		//alerts.0002
			}
	    }
			*/
	    addEmptyLine(preface,1);
			
			//document.add(preface); sumit commented
			//4.Generate the Table Contents.
			
	    PdfPTable table = new PdfPTable(3);
	    BaseColor backgroundColor = new BaseColor(176,196,222);
	    float border=(float) 0.8;
	    
			String timeStampHeader = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0004");
			//PdfPCell c1 = new PdfPCell(new Phrase("TIME STAMP"));
			PdfPCell c1 = new PdfPCell(new Phrase(timeStampHeader));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);	
		c1.setBackgroundColor(backgroundColor);
		table.addCell(c1);
		
			String deviceHeader = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0005");
			//c1 = new PdfPCell(new Phrase("DEVICE NAME"));
			c1 = new PdfPCell(new Phrase(deviceHeader));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(backgroundColor);
		table.addCell(c1);

			String alertHeader = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0006");
			//c1 = new PdfPCell(new Phrase("ALERTS"));
			c1 = new PdfPCell(new Phrase(alertHeader));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(backgroundColor);
		table.addCell(c1);
		table.setHeaderRows(1);
		
		for(Object[] strings : objects)
		{
			java.sql.Timestamp TimeStamp=(Timestamp)strings[0];
			String TimeStampString = new SimpleDateFormat("dd/MM/yy 'at' hh:mm a").format(TimeStamp);
			backgroundColor=new BaseColor(131,139,131);
			PdfPCell c= new PdfPCell(new Phrase(TimeStampString));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBackgroundColor(backgroundColor);
			c.setBorderWidth(border);
			c.setBottom((float) 3.6);
			table.addCell(c);
			c = new PdfPCell(new Phrase((String)strings[1]));
			c.setBackgroundColor(backgroundColor);
			c.setBorderWidth(border);
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c);
			c = new PdfPCell(new Phrase((String)strings[2]));
			c.setBackgroundColor(backgroundColor);
			c.setBorderWidth(border);
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c);
		}
		document.add(table);
	    document.close();      
		filepath+=customer.getCustomerId()+"/Alerts.pdf";
		
		}

		return SUCCESS;
	}
	
	public String listImvgAlarm() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		
		String resultXml="";
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String Customer = stream.toXML(customer);
		resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlarm",Customer,xmlString);		
		dataTable = ((DataTable) stream.fromXML(resultXml));
		return SUCCESS;
	}
	
	public String updatealert() throws Exception{
		XStream stream = new XStream();
		
		String alert = this.alertId;
	
		String alertIdXml = stream.toXML(this.alertId);
		
		//String message = "";
		
		//	message = IMonitorUtil.sendToController("alertService", "updateAlert", alertIdXml);
		IMonitorUtil.sendToController("alertService", "updateAlert", alertIdXml);
			
		
		return SUCCESS;
	}
	
	
	// ******************************************************** sumit start: Internationalization ********************************************
	
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	
	public void setDisplayStart(int displayStart) {
		this.displayStart = displayStart;
	}


	public int getDisplayStart() {
		return displayStart;
	}


	public void setDisplayLength(int displayLength) {
		this.displayLength = displayLength;
	}


	public int getDisplayLength() {
		return displayLength;
	}


	public UploadsByImvg getUploadsByImvg() {
		return uploadsByImvg;
	}
	public void setUploadsByImvg(UploadsByImvg uploadsByImvg) {
		this.uploadsByImvg = uploadsByImvg;
	}


public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}


	public DataTable getDataTable() {
		return dataTable;
	}


	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}


	public int getRowSize() {
		return rowSize;
	}

	public List<Object[]> getObjects() {
		return objects;
	}

	public void setObjects(List<Object[]> objects) {
		this.objects = objects;
	}

	public String getAlertFromImvgId() {
		return alertFromImvgId;
	}

	public void setAlertFromImvgId(String alertFromImvgId) {
		this.alertFromImvgId = alertFromImvgId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	public void setImvgUploadContextPath(String imvgUploadContextPath) {
		this.imvgUploadContextPath = imvgUploadContextPath;
	}

	public String getImvgUploadContextPath() {
		return imvgUploadContextPath;
	}

	public DataTable getDataTableAlert() {
		return dataTableAlert;
	}

	public void setDataTableAlert(DataTable dataTableAlert) {
		this.dataTableAlert = dataTableAlert;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public String getGateWayId() {
		return gateWayId;
	}

	public void setGateWayId(String gateWayId) {
		this.gateWayId = gateWayId;
	}

	

	public String getFromDate() {
		return FromDate;
	}

	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}

	public String getToDate() {
		return ToDate;
	}

	public void setToDate(String toDate) {
		ToDate = toDate;
	}

	public long getSelctedDevice() {
		return selctedDevice;
	}

	public void setSelctedDevice(long selctedDevice) {
		this.selctedDevice = selctedDevice;
	}

	
	public String getWisetype() {
		return wisetype;
	}

	public void setWisetype(String wisetype) {
		this.wisetype = wisetype;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSelectedAlarms() {
		return selectedAlarms;
	}

	public void setSelectedAlarms(String selectedAlarms) {
		this.selectedAlarms = selectedAlarms;
	}

	public int getDisplayLengthAlarm() {
		return displayLengthAlarm;
	}

	public void setDisplayLengthAlarm(int displayLengthAlarm) {
		this.displayLengthAlarm = displayLengthAlarm;
	}

	public int getRowSizeAlarm() {
		return rowSizeAlarm;
	}

	public void setRowSizeAlarm(int rowSizeAlarm) {
		this.rowSizeAlarm = rowSizeAlarm;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public long getNumberofAlerts() {
		return NumberofAlerts;
	}

	public void setNumberofAlerts(long numberofAlerts) {
		NumberofAlerts = numberofAlerts;
	}
	
	public String getAlertId() {
		return alertId;
	}
	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}
	
	
	public String listImvgOldAlertForAlarm() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		
		
		/*String FromDate=this.FromDate;
		String ToDate=this.ToDate;
		long SelectedDevice=this.selctedDevice;*/
		String resultXml="";
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String Customer = stream.toXML(customer);
		/*String XmlSelectedDevice=stream.toXML(SelectedDevice);
		String XmlFromdate=stream.toXML(FromDate);
		String XmlTodate=stream.toXML(ToDate);*/
		String DisplayStart = stream.toXML(new Integer(displayStart).toString());
		String XMLdisplayLengthAlarm = stream.toXML(new Integer(displayLengthAlarm).toString());
		
		
		/*if(SelectedDevice!=0)
		{
			
			resultXml=IMonitorUtil.sendToController("alertService", "listImvgAlertForFiltering", Customer,DisplayStart,XmlSelectedDevice,XmlFromdate,XmlTodate);		
		}
		else
		{
			
			resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlertForAlarm", Customer,DisplayStart,XMLdisplayLengthAlarm,xmlString);		
		}*/
		resultXml =IMonitorUtil.sendToController("alertService", "listImvgOldAlertForAlarm",Customer,DisplayStart,XMLdisplayLengthAlarm,xmlString);		
		dataTable = ((DataTable) stream.fromXML(resultXml));
		
		return SUCCESS;
	}
	
	public String listImvgOldAlarm() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		
		String resultXml="";
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String Customer = stream.toXML(customer);
		resultXml =IMonitorUtil.sendToController("alertService", "listImvgOldAlarm",Customer,xmlString);		
		dataTable = ((DataTable) stream.fromXML(resultXml));
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked" })
	public String requetForOldPdf() throws Exception 
	{
		
		XStream stream=new XStream();
		String SelectedType=this.wisetype;
		
		String FromDate=this.FromDate;
		
		String ToDate=this.ToDate;
		
		long SelectedDevice=this.selctedDevice;
		
		
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
	
		Customer customer = user.getCustomer();
		
		String xmlString1 = stream.toXML(customer);
		
		
		String XmlSelectedDevice=stream.toXML(SelectedDevice);
		
		String XmlFromdate=stream.toXML(FromDate);
		
		String XmlTodate=stream.toXML(ToDate);
		
		String XmlSelectedType=stream.toXML(SelectedType);
		
		String DisplayStart = stream.toXML(new Integer(displayStart).toString());
		
		String DisplayLength = stream.toXML(new Integer(displayLengthAlarm).toString());	
		
		String resultXml =IMonitorUtil.sendToController("alertService", "requestForOldPdf", xmlString1,XmlSelectedDevice,XmlFromdate,XmlTodate,XmlSelectedType,DisplayStart,DisplayLength);
		
		List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
		
		Device device=new Device();
		if(selctedDevice!=1)
		{
			resultXml =IMonitorUtil.sendToController("deviceService", "getDeviceById", XmlSelectedDevice);
			device=(Device) stream.fromXML(resultXml);
		}
		NumberofAlerts=objects.size();
	
		if(NumberofAlerts!=0)
		{
			
			String customerIdXml = stream.toXML(customer.getCustomerId());
			String serviceName = "agentService";
			String method = "getAgentByCustomerId";
			String result = IMonitorUtil.sendToController(serviceName, method, customerIdXml);
			Agent agent = (Agent) stream.fromXML(result);
			String ftpUserName = agent.getFtpUserName();
			
			
			
		String FILE = "/home/"+ftpUserName+"/"+customer.getCustomerId()+"/"+"OldAlerts.pdf";
		Document document=new Document();
		try {
			PdfWriter.getInstance(document,new FileOutputStream(FILE));
			document.open();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
			
			
			Font font = null;
			try {
				
			
				font = new Font();
				
			} 
			catch (NullPointerException e) {
				LogUtil.info("NullPointerException", e);
			} catch (Exception e) {
				LogUtil.info("Got Exception", e);
			}
					
	    Paragraph preface = new Paragraph();
	    int alignment=10;
		preface.setAlignment(alignment);
			
			
			String formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0001"			//Document generated by [customer id]
					+ Constants.MESSAGE_DELIMITER_1 + customer.getCustomerId());		
			document.add(new Paragraph(formatText, font));
			
			formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0002"					//On [current date]
					+ Constants.MESSAGE_DELIMITER_1 + new Date());					
			document.add(new Paragraph(formatText, font));
			
			if(SelectedType.equals("Alarm")){
				
				
				if((FromDate.isEmpty()) && (ToDate.isEmpty()) && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0001");	//Listing all alarms
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());	
					document.add(new Paragraph(formatText, font));	    		
				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0003"	//Listing alarms between date [arg1] and date [arg2]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());	
					document.add(new Paragraph(formatText, font));

				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice!=0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0003"	//Listing alarms between date [arg1] and date [arg2]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0003"
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());			//For Selected Device [deviceName]
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));

				}
				if(FromDate.isEmpty() && ToDate.isEmpty() && SelectedDevice!=0){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0004"	//Listing Alarms Of Selected Device [device Name]
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());
					
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));
					
				}
			}else{
			
				//3.Generate Alerts Details.
				if((FromDate.isEmpty()) && (ToDate.isEmpty()) && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0001");	//Listing All Alerts
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));
				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0003"	//Listing Alerts Between Date [arg0] And To Date [arg1]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));

				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice!=0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0003"	//Listing Alerts Between Date [arg0] And To Date [arg1]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0003"			//For Selected Device [deviceName]
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());			
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));

				}
				if(FromDate.isEmpty() && ToDate.isEmpty() && SelectedDevice!=0){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0004"	//Listing alerts of selected device [device name]
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));
				}
			}
			
			
		
	    addEmptyLine(preface,1);
			
			//document.add(preface); sumit commented
			//4.Generate the Table Contents.
			
	    PdfPTable table = new PdfPTable(3);
	    BaseColor backgroundColor = new BaseColor(176,196,222);
	    float border=(float) 0.8;
	    
			String timeStampHeader = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0004");
			
			PdfPCell c1 = new PdfPCell(new Phrase(timeStampHeader));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);	
		c1.setBackgroundColor(backgroundColor);
		table.addCell(c1);
		
			String deviceHeader = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0005");
			
		c1 = new PdfPCell(new Phrase(deviceHeader));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(backgroundColor);
		table.addCell(c1);

			String alertHeader = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0006");
			
		c1 = new PdfPCell(new Phrase(alertHeader));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(backgroundColor);
		table.addCell(c1);
		table.setHeaderRows(1);
		
		for(Object[] strings : objects)
		{
			java.sql.Timestamp TimeStamp=(Timestamp)strings[0];
			String TimeStampString = new SimpleDateFormat("dd/MM/yy 'at' hh:mm a").format(TimeStamp);
			backgroundColor=new BaseColor(131,139,131);
			PdfPCell c= new PdfPCell(new Phrase(TimeStampString));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBackgroundColor(backgroundColor);
			c.setBorderWidth(border);
			c.setBottom((float) 3.6);
			table.addCell(c);
			c = new PdfPCell(new Phrase((String)strings[1]));
			c.setBackgroundColor(backgroundColor);
			c.setBorderWidth(border);
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c);
			c = new PdfPCell(new Phrase((String)strings[2]));
			c.setBackgroundColor(backgroundColor);
			c.setBorderWidth(border);
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c);
		}
		document.add(table);
	    document.close();     
	    
		filepath+=customer.getCustomerId()+"/OldAlerts.pdf";
		
		}

		return SUCCESS;
	}
	
	
	public String deleteOldAlaram() throws Exception 
	{
		/*String SelectedType=this.wisetype;*/
		XStream stream=new XStream();
		
		String SelectedAlaram=this.selectedAlarms;
		String xmlString=stream.toXML(SelectedAlaram);
		
		IMonitorUtil.sendToController("alertService", "DeleteSelectedOldAlarms", xmlString);
		
		
		return SUCCESS;
	
	}
	
	
	@SuppressWarnings("unchecked")
	public String uploadedImvgAlertAttachmentfrombackup() throws Exception{
		XStream xStream = new XStream();
		String xml = xStream.toXML(alertFromImvgId);
	
		String resultXml = IMonitorUtil.sendToController("alertService", "getImvgAlertAttachmentforbackup", xml);
		object  = (Object) xStream.fromXML(resultXml);
		
		 imvgUploadContextPath = IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH);
		 return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String getDeviceRountingInfo() throws Exception{
		XStream xStream = new XStream();
		String xml = xStream.toXML(alertFromImvgId);
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString1 = xStream.toXML(customer);
		String resultXml = IMonitorUtil.sendToController("alertService", "getRoutingInfo", xml,xmlString1);
		deviceNodeInfo  =  (ArrayList<Object[]>) xStream.fromXML(resultXml);
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String getSelectedDeviceNodeInfo() throws Exception{
	
		return SUCCESS;
	}
	
	public String requestForAlertsInXls() throws Exception{
		
		
		return SUCCESS;
	}
	
	public String requestForOldAlertsInXls() throws Exception {
		
		
		return SUCCESS;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public ArrayList<Object[]> getDeviceNodeInfo() {
		return deviceNodeInfo;
	}

	public void setDeviceNodeInfo(ArrayList<Object[]> deviceNodeInfo) {
		this.deviceNodeInfo = deviceNodeInfo;
	}
	
	
	//3 gp by apoorva
	public String listImvgAlertForAlarmofMultipleGateway() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		
		
		/*String FromDate=this.FromDate;
		String ToDate=this.ToDate;
		long SelectedDevice=this.selctedDevice;*/
		String resultXml="";
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String Customer = stream.toXML(customer);
		/*String XmlSelectedDevice=stream.toXML(SelectedDevice);
		String XmlFromdate=stream.toXML(FromDate);
		String XmlTodate=stream.toXML(ToDate);*/
		String DisplayStart = stream.toXML(new Integer(displayStart).toString());
		String XMLdisplayLengthAlarm = stream.toXML(new Integer(displayLengthAlarm).toString());
		
		/*if(SelectedDevice!=0)
		{
			
			resultXml=IMonitorUtil.sendToController("alertService", "listImvgAlertForFiltering", Customer,DisplayStart,XmlSelectedDevice,XmlFromdate,XmlTodate);		
		}
		else
		{
			
			resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlertForAlarm", Customer,DisplayStart,XMLdisplayLengthAlarm,xmlString);		
		}*/
		resultXml =IMonitorUtil.sendToController("alertService", "listImvgAlertForAlarmofMultipleGateway",Customer,DisplayStart,XMLdisplayLengthAlarm,xmlString);		
		dataTable = ((DataTable) stream.fromXML(resultXml));
		
		return SUCCESS;
	}
	
	//3gp
	@SuppressWarnings({ "unchecked" })
	public String requetForPdfForMultipleGateways() throws Exception 
	{
		LogUtil.info("requetForPdfForMultipleGateways start");
		XStream stream=new XStream();
		String SelectedType=this.wisetype;
		String FromDate=this.FromDate;
		String ToDate=this.ToDate;
		long SelectedDevice=this.selctedDevice;
		
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString1 = stream.toXML(customer);
		String XmlSelectedDevice=stream.toXML(SelectedDevice);
		String XmlFromdate=stream.toXML(FromDate);
		String XmlTodate=stream.toXML(ToDate);
		String XmlSelectedType=stream.toXML(SelectedType);
		String DisplayStart = stream.toXML(new Integer(displayStart).toString());
		String DisplayLength = stream.toXML(new Integer(displayLengthAlarm).toString());	
		String resultXml =IMonitorUtil.sendToController("alertService", "requestForPdfForMultipleGateways", xmlString1,XmlSelectedDevice,XmlFromdate,XmlTodate,XmlSelectedType,DisplayStart,DisplayLength);
		List<Object[]> objects=(List<Object[]>) stream.fromXML(resultXml);
		
		Device device=new Device();
		if(selctedDevice!=1)
		{
			resultXml =IMonitorUtil.sendToController("deviceService", "getDeviceById", XmlSelectedDevice);
			device=(Device) stream.fromXML(resultXml);
		}
		NumberofAlerts=objects.size();
		
		if(NumberofAlerts!=0)
		{
			
			String customerIdXml = stream.toXML(customer.getCustomerId());
			String serviceName = "agentService";
			String method = "getAgentByCustomerId";
			String result = IMonitorUtil.sendToController(serviceName, method, customerIdXml);
			Agent agent = (Agent) stream.fromXML(result);
			String ftpUserName = agent.getFtpUserName();
			
			
			
		String FILE = "/home/"+ftpUserName+"/"+customer.getCustomerId()+"/"+"Alerts.pdf";
		
		Document document=new Document();
		try {
			
			PdfWriter.getInstance(document,new FileOutputStream(FILE));
			document.open();
			
		} catch (Exception e)
		{
			
			LogUtil.info("Excepppp :"+e.getMessage());
		}
		
			Font font = null;
			try {
				font = new Font();
				
			} catch (NullPointerException e) {
				LogUtil.info("NullPointerException", e);
			} catch (Exception e) {
				LogUtil.info("Got Exception", e);
			}
					
	    Paragraph preface = new Paragraph();
	    int alignment=10;
		preface.setAlignment(alignment);
			
			//1.Generate the header section
			String formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0001"			//Document generated by [customer id]
					+ Constants.MESSAGE_DELIMITER_1 + customer.getCustomerId());		
			document.add(new Paragraph(formatText, font));
			
			formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0002"					//On [current date]
					+ Constants.MESSAGE_DELIMITER_1 + new Date());					
			document.add(new Paragraph(formatText, font));
			
			if(SelectedType.equals("Alarm")){
				
				//2.Generate Alarms Details.
				if((FromDate.isEmpty()) && (ToDate.isEmpty()) && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0001");	//Listing all alarms
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());	
					document.add(new Paragraph(formatText, font));	    		
				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0003"	//Listing alarms between date [arg1] and date [arg2]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());	
					document.add(new Paragraph(formatText, font));

				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice!=0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0003"	//Listing alarms between date [arg1] and date [arg2]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0003"
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());			//For Selected Device [deviceName]
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));

				}
				if(FromDate.isEmpty() && ToDate.isEmpty() && SelectedDevice!=0){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0004"	//Listing Alarms Of Selected Device [device Name]
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());
					
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alarms.0002"	//Total number of alarms [alarm count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));
					
				}
			}else{
			
				//3.Generate Alerts Details.
				if((FromDate.isEmpty()) && (ToDate.isEmpty()) && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0001");	//Listing All Alerts
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));
				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice==0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0003"	//Listing Alerts Between Date [arg0] And To Date [arg1]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));

				}
				if(!FromDate.isEmpty() && !ToDate.isEmpty() && (SelectedDevice!=0)){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0003"	//Listing Alerts Between Date [arg0] And To Date [arg1]
							+ Constants.MESSAGE_DELIMITER_1 + FromDate
							+ Constants.MESSAGE_DELIMITER_2 + ToDate);
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0003"			//For Selected Device [deviceName]
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());			
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));

				}
				if(FromDate.isEmpty() && ToDate.isEmpty() && SelectedDevice!=0){
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0004"	//Listing alerts of selected device [device name]
							+ Constants.MESSAGE_DELIMITER_1 + device.getFriendlyName());
					document.add(new Paragraph(formatText, font));
					
					formatText = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.alerts.0002"	//Total Number of Alerts [alerts Count]
							+ Constants.MESSAGE_DELIMITER_1 + objects.size());
					document.add(new Paragraph(formatText, font));
				}
			}
			
	    addEmptyLine(preface,1);
	    PdfPTable table = new PdfPTable(3);
	    BaseColor backgroundColor = new BaseColor(176,196,222);
	    float border=(float) 0.8;
	    
			String timeStampHeader = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0004");
			//PdfPCell c1 = new PdfPCell(new Phrase("TIME STAMP"));
			PdfPCell c1 = new PdfPCell(new Phrase(timeStampHeader));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);	
		c1.setBackgroundColor(backgroundColor);
		table.addCell(c1);
		
			String deviceHeader = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0005");
			//c1 = new PdfPCell(new Phrase("DEVICE NAME"));
			c1 = new PdfPCell(new Phrase(deviceHeader));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(backgroundColor);
		table.addCell(c1);

			String alertHeader = IMonitorUtil.formatMessage(this, "msg.imonitor.pdf.0006");
			//c1 = new PdfPCell(new Phrase("ALERTS"));
			c1 = new PdfPCell(new Phrase(alertHeader));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(backgroundColor);
		table.addCell(c1);
		table.setHeaderRows(1);
		
		for(Object[] strings : objects)
		{
			java.sql.Timestamp TimeStamp=(Timestamp)strings[0];
			String TimeStampString = new SimpleDateFormat("dd/MM/yy 'at' hh:mm a").format(TimeStamp);
			backgroundColor=new BaseColor(131,139,131);
			PdfPCell c= new PdfPCell(new Phrase(TimeStampString));
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			c.setBackgroundColor(backgroundColor);
			c.setBorderWidth(border);
			c.setBottom((float) 3.6);
			table.addCell(c);
			c = new PdfPCell(new Phrase((String)strings[1]));
			c.setBackgroundColor(backgroundColor);
			c.setBorderWidth(border);
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c);
			c = new PdfPCell(new Phrase((String)strings[2]));
			c.setBackgroundColor(backgroundColor);
			c.setBorderWidth(border);
			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c);
		}
		document.add(table);
	    document.close();      
		filepath+=customer.getCustomerId()+"/Alerts.pdf";
		
		}
		return SUCCESS;
	}
	
	public String listAlarmsForMultipleGateway() throws Exception{
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		
		String resultXml="";
		User user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String Customer = stream.toXML(customer);
		resultXml =IMonitorUtil.sendToController("alertService", "listAlarmsForMultipleGateway",Customer,xmlString);	
		dataTable = ((DataTable) stream.fromXML(resultXml));
		return SUCCESS;
	}
	

	
}
