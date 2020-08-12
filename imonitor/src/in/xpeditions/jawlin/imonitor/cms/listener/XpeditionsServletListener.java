/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.listener;

import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorProperties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * @author Coladi
 *
 */
public class XpeditionsServletListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
//		Initializing properties.
		Properties properties = new Properties();
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("imonitor.properties"); 
			properties.load(inputStream);
			Map<String, String> propertiesMap = new HashMap<String, String>();
			propertiesMap.put(Constants.CONTROLLER_IP, properties.getProperty(Constants.CONTROLLER_IP));
			propertiesMap.put(Constants.CONTROLLER_PORT, properties.getProperty(Constants.CONTROLLER_PORT));
			propertiesMap.put(Constants.CONTROLLER_PROTOCOL, properties.getProperty(Constants.CONTROLLER_PROTOCOL));
			propertiesMap.put(Constants.DATE_FORMAT_SERVER, properties.getProperty(Constants.DATE_FORMAT_SERVER));
			propertiesMap.put(Constants.DATE_FORMAT_SERVER_DISPLAY, properties.getProperty(Constants.DATE_FORMAT_SERVER_DISPLAY));
			propertiesMap.put(Constants.IMVG_UPLOAD_CONTEXT_PATH,properties.getProperty(Constants.IMVG_UPLOAD_CONTEXT_PATH));
			propertiesMap.put(Constants.ADMINPASS,properties.getProperty("superadminpass"));
			propertiesMap.put(Constants.SMS_ALERT_NOTIFIER, properties.getProperty("sms_notifier"));
			//Naveen Added on 13Feb 2015 for deleting customer report
			propertiesMap.put(Constants.REPORTPASS, properties.getProperty("reportpassword"));
			propertiesMap.put(Constants.REPORT_UPLOAD_PATH, properties.getProperty("report_upload_path"));
			IMonitorProperties.setPropertyMap(propertiesMap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
