/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.imonitor.cms.listener;

import in.imonitorapi.util.Constants;
import in.imonitorapi.util.IMonitorProperties;

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
			propertiesMap.put(Constants.EflCONTROLLER_PORT, properties.getProperty(Constants.EflCONTROLLER_PORT));
			
			
			IMonitorProperties.setPropertyMap(propertiesMap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
