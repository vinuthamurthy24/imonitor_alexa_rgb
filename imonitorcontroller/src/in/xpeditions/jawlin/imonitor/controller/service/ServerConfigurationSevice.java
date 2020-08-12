/* Copyright © 2012 iMonitor Solutions India Private Limited */

package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsReport;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SmsService;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ServerConfigurationManger;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.general.DataTable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.thoughtworks.xstream.XStream;



public class ServerConfigurationSevice   
{

	
	 private static Cipher cipher = null;
	 private static final String keyValue = "IMS1356";
	 private static final String ALG = "Blowfish";

	
     
	public String SaveServiceProvider(String paramXml) throws UnsupportedEncodingException {
		String result="no";
		XStream xStream = new XStream();
		SmsService smsoperator = (SmsService) xStream.fromXML(paramXml);
		ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
		
		List<SmsService> SmsService = serverconfigurationmanger.listOfSMSService();
		//check if name already exists.
		for(SmsService s: SmsService)
		{
			if(!s.getOperatorcode().equalsIgnoreCase(smsoperator.getOperatorcode())){
				continue;
			}else{
				return result;
			}
		}
		
		
		try
		{
	
		SecretKeySpec key = new SecretKeySpec(keyValue.getBytes(), ALG);

		cipher=Cipher.getInstance(ALG);

	    // Initialize the cipher for encryption
		cipher.init(Cipher.ENCRYPT_MODE, key);

		
	    //sensitive information
	    byte[] username = smsoperator.getUsername().getBytes("UTF8");
	    byte[] password = smsoperator.getPassword().getBytes("UTF8");
	   

	    // Encrypt the username
	    byte[] UserNameEncrypted = cipher.doFinal(username);
	    
	    BASE64Encoder encoder = new BASE64Encoder();
	    
		String UsernameBase =encoder.encode(UserNameEncrypted);
		smsoperator.setUsername(new String(UsernameBase));
		
		
		
		
	    
	    
  		
  		
  		// Encrypt the password
	    byte[] PasswordEncrypted = cipher.doFinal(password);
	    String PassBase = encoder.encode(PasswordEncrypted);
		smsoperator.setPassword(new String(PassBase));

	    
	

	}catch(NoSuchAlgorithmException e){
		e.printStackTrace();
	}catch(NoSuchPaddingException e){
		e.printStackTrace();
	}catch(InvalidKeyException e){
		e.printStackTrace();
	}catch(IllegalBlockSizeException e){
		e.printStackTrace();
	}catch(BadPaddingException e){
		e.printStackTrace();
	} 


		
		
		serverconfigurationmanger.saveServiceProvider(smsoperator);
		
		result="yes";
		
		return result;
	}
	
	
	public String UpdateServiceProvider(String paramXml) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
		
		String xml = "";
		XStream xStream = new XStream();
		SmsService smsoperator = (SmsService) xStream.fromXML(paramXml);
		try
		{
		

	    BASE64Encoder encoder = new BASE64Encoder();
	    // Create the cipher 
	   

	    // Initialize the cipher for encryption
	    SecretKeySpec key = new SecretKeySpec(keyValue.getBytes(), ALG);

	    cipher=Cipher.getInstance(ALG);

	    // Initialize the cipher for encryption
		cipher.init(Cipher.ENCRYPT_MODE, key);

	    //sensitive information
	    byte[] username = smsoperator.getUsername().getBytes("UTF8");
	    byte[] password = smsoperator.getPassword().getBytes("UTF8");
	   

	    // Encrypt the username
	    byte[] UserNameEncrypted = cipher.doFinal(username);
	    String UsernameBase = encoder.encode(UserNameEncrypted);
		smsoperator.setUsername(new String(UsernameBase));
	    
	    
		
		// Encrypt the password
	    byte[] PasswordEncrypted = cipher.doFinal(password);
	    String PassBase = encoder.encode(PasswordEncrypted);
		smsoperator.setPassword(new String(PassBase));

	    
	
	   

	}catch(InvalidKeyException e){
		e.printStackTrace();
	}catch(IllegalBlockSizeException e){
		e.printStackTrace();
	}catch(BadPaddingException e){
		e.printStackTrace();
	} 


		
		ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
		serverconfigurationmanger.UpdateServiceProvider(smsoperator);
		
		
		
		return xml;
	}
	
	
	public String listAskedService(String xml) {
		XStream xStream = new XStream();
		DataTable dataTable = (DataTable) xStream.fromXML(xml);
		ServerConfigurationManger serverConfigurationManger = new ServerConfigurationManger();
		
		String sSearch = dataTable.getsSearch();
		String[] cols = {"sm.id","sm.operatorcode","sm.status"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		String colName = cols[col];
		sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		
		List<?> list = serverConfigurationManger.listAskedSmsProvider(sSearch,sOrder, dataTable
				.getiDisplayStart(), dataTable.getiDisplayLength());
		 
		dataTable.setResults(list);
		int displayRow = serverConfigurationManger.getTotalSMSSeviceCount(sSearch);
		
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	

	
	public String GetsmsServiceById(String paramXml) throws UnsupportedEncodingException {
		
		String xml = "";
		XStream xStream = new XStream();
		
		SmsService smsoperator = (SmsService) xStream.fromXML(paramXml);
		
		if(smsoperator != null)
		{
			ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
			smsoperator=serverconfigurationmanger.GetsmsServiceById(smsoperator);
			
			try
			{
				
				SecretKeySpec key = new SecretKeySpec(keyValue.getBytes(), ALG);
				cipher=Cipher.getInstance(ALG);
				cipher.init(Cipher.DECRYPT_MODE, key);
				BASE64Decoder decoder = new BASE64Decoder();
	  		
			    byte[] UserName = null;
			    byte[] Password = null;
			    try 
			    {
			    	UserName = decoder.decodeBuffer(smsoperator.getUsername());
			    	Password = decoder.decodeBuffer(smsoperator.getPassword());
				
			    } catch (IOException e) {
				e.printStackTrace();
			    }
	   
			    //decode the message
			    byte[] UserNamestringBytes = cipher.doFinal(UserName);
			    
			    byte[] PasswordstringBytes = cipher.doFinal(Password);
	   
			    //converts the decoded message to a String
			    String DecodedUsername = new String(UserNamestringBytes, "UTF8");
			    smsoperator.setUsername(DecodedUsername);
	       
			    String DecodedPassword = new String(PasswordstringBytes, "UTF8");
			    smsoperator.setPassword(DecodedPassword);
		   

		}catch(IllegalBlockSizeException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			
			e1.printStackTrace();
		} catch (InvalidKeyException e1) {
		
			e1.printStackTrace();
		} 
			xml=xStream.toXML(smsoperator);
		}
		else
		{
			LogUtil.info("NO SMS OPERATOR!!!!");
		}
		
		return xml;
	}
	
	public String DeletesmsServiceById(String paramXml) throws UnsupportedEncodingException {
		
		
		XStream xStream = new XStream();
		SmsService smsoperator = (SmsService) xStream.fromXML(paramXml);
		
		
		ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
		smsoperator=serverconfigurationmanger.DeletesmsServiceById(smsoperator);
		
		return "YES";
	}


	public String UpdateStatusServiceProvider (String paramXml) throws Exception {
		
		
		XStream xStream = new XStream();
		SmsService smsoperator = (SmsService) xStream.fromXML(paramXml);
		
		ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
		serverconfigurationmanger.UpdateStatusServiceProvider(smsoperator);
		
		return "YES";
	}
	

	public String getSelectedSMSServiceProvider()throws Exception
	{
		XStream xStream = new XStream();
		ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
		SmsService smsoperator=serverconfigurationmanger.getSelectedSMSProvider();
		
		String xml = null;
		if(smsoperator != null)
		{
			xml = GetsmsServiceById(xStream.toXML(smsoperator));
		}
		else
		{
			LogUtil.info("NO SMS OPERATOR");
		}
		 
		
		return xml;
	}
	
	public String SaveSMSReport(String xmlsid) throws Exception
	{
		
		/*Document document = XmlUtil.getDocument(xmlsid);
		
		NodeList sidNodes = document.getElementsByTagName("sid");
		Element sidNode = (Element) sidNodes.item(0);
		String sid = XmlUtil.getCharacterDataFromElement(sidNode);
		
		NodeList destNodes = document.getElementsByTagName("dest");
		Element destNode = (Element) destNodes.item(0);
		String destId = XmlUtil.getCharacterDataFromElement(destNode);
		
		NodeList stimeNodes = document.getElementsByTagName("stime");
		Element stimeNode = (Element) stimeNodes.item(0);
		String stime = XmlUtil.getCharacterDataFromElement(stimeNode);
		
		NodeList dtimeNodes = document.getElementsByTagName("dtime");
		Element dtimeNode = (Element) dtimeNodes.item(0);
		String dtime = XmlUtil.getCharacterDataFromElement(dtimeNode);
		
		NodeList statusNodes = document.getElementsByTagName("status");
		Element statusNode = (Element) statusNodes.item(0);
		String status = XmlUtil.getCharacterDataFromElement(statusNode);*/
		
		SmsReport sm=(SmsReport) new XStream().fromXML(xmlsid);
		
		ServerConfigurationManger serverconfigurationmanger=new ServerConfigurationManger();
		SmsReport SmsReportDb=serverconfigurationmanger.getSmsReportBuSessionId(sm.getSessionId(),sm.getMobileNumber());
		
		if(SmsReportDb!= null)
		{
			SmsReportDb.setSentTime(sm.getSentTime());
			SmsReportDb.setDeliveryTime(sm.getDeliveryTime());
			SmsReportDb.setStatus(sm.getStatus());
			serverconfigurationmanger.UpdateSMSReport(SmsReportDb);
		}
		
		
		
		
		return "<status>Sucess</status>";
		
	}
	
public String listAskedSmsReportsWithCount(String xml) {
		XStream xStream = new XStream();
		DataTable dataTable = (DataTable) xStream.fromXML(xml);
		ServerConfigurationManger serverConfigurationManger = new ServerConfigurationManger();
		String SentDate=dataTable.getsSearch_1();
		String[] SpliteDate = null;
		String XmlFromdate = null;
		String XmlTodate = null;
		
		
		SpliteDate=SentDate.split(":");
		if(SpliteDate.length>1)
		{
			XmlFromdate=SpliteDate[0];
			XmlTodate=SpliteDate[1];
		}
		
		LogUtil.info("from date : "+ XmlFromdate + " to date: "+ XmlTodate);
		String sSearch = dataTable.getsSearch();
		String[] cols = {"c.customerId","sm.mobileNumber"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		String colName = cols[col];
		sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		
		List<?> list = serverConfigurationManger.listAskedSmsReportCounts(sSearch,sOrder, dataTable
				.getiDisplayStart(), dataTable.getiDisplayLength(),XmlFromdate,XmlTodate);
		LogUtil.info("sms count list: "+ xStream.toXML(list));
		dataTable.setResults(list);
		int displayRow = serverConfigurationManger.getSmsReportCounts(sSearch);
		
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	

	public static Cipher getCipher() {
		return cipher;
	}


	public static void setCipher(Cipher cipher) {
		ServerConfigurationSevice.cipher = cipher;
	}


	public static String getKeyvalue() {
		return keyValue;
	}


	public static String getAlg() {
		return ALG;
	}
	
	
}
