/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;



public class DeviceControlServiceManager {
	
	public String controlDevice(String xml)throws Exception {
		RequestProcessor requestProcessor = new RequestProcessor();
		requestProcessor.processRequest(xml);
		return null;
	}

}
