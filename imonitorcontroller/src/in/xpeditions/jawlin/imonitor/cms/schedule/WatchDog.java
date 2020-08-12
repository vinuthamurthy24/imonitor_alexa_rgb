/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.schedule;

import in.xpeditions.jawlin.imonitor.controller.action.ActionExecutor;
import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ScheduledScenarios;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScenarioManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.ScheduleManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WatchDog implements Runnable {

	@Override
	public void run() {

		int delay = 60000; // delay for 5 sec.
		int period = 20000; // repeat every sec.
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				try {
					ScheduleManager scheduleManager = new ScheduleManager();
					Date scheduledDate = new Date();
					Calendar c = new GregorianCalendar();
					c.setTime(scheduledDate);
					int houre = c.get(Calendar.HOUR_OF_DAY);
					int minute = c.get(Calendar.MINUTE);
					int day = c.get(Calendar.DAY_OF_WEEK) - 1;
					String scheduleId ="";

					List<Object[]> scheduleActionObjects = scheduleManager
							.listScheduleActionsByTime(houre, minute, day);
//					We may move the below statements into seperate threads.
					for (Object[] objects : scheduleActionObjects) {
						String deviceId = IMonitorUtil
								.convertToString(objects[1]);
						String macId = IMonitorUtil.convertToString(objects[2]);
						String command = IMonitorUtil
								.convertToString(objects[3]);
						String ip = IMonitorUtil.convertToString(objects[4]);
						String portS = IMonitorUtil.convertToString(objects[5]);
						String ftpIp = IMonitorUtil.convertToString(objects[6]);
						int ftpPort = (Integer) objects[7];
						String ftpUser = IMonitorUtil
								.convertToString(objects[8]);
						String ftpPass = IMonitorUtil
								.convertToString(objects[9]);
						String customerId = IMonitorUtil
								.convertToString(objects[10]);
						int port = Integer.parseInt(portS);
						scheduleId = IMonitorUtil.convertToString(objects[12]);
						Device cDevice = new Device();
						GateWay cGateWay = new GateWay();
						Agent cAgent = new Agent();
						cDevice.setGeneratedDeviceId(deviceId);
						cGateWay.setMacId(macId);
						cAgent.setIp(ip);
						cAgent.setControllerReceiverPort(port);
						cAgent.setFtpIp(ftpIp);
						cAgent.setFtpPort(ftpPort);
						cAgent.setFtpUserName(ftpUser);
						cAgent.setFtpPassword(ftpPass);
						cGateWay.setAgent(cAgent);
						Customer customer = new Customer();
						customer.setCustomerId(customerId);
						cGateWay.setCustomer(customer);
						cDevice.setGateWay(cGateWay);
						if(objects[11] != null){
							String level = IMonitorUtil.convertToString(objects[11]);
							cDevice.setCommandParam(level);
						}
						ActionModel actionModel = new ActionModel(cDevice, null);
						ActionExecutor actionExecutor = new ActionExecutor(
								command, actionModel);
						Thread t = new Thread(actionExecutor);
						t.start();
					}
					ScenarioManager scenarioManager = new ScenarioManager();
					if(scheduleId !=null ){
						List<Object[]> scenariosObjects = scenarioManager.listScenariosByScheduleId(Long.parseLong(scheduleId));
						for(Object[] scenario:scenariosObjects){
							scenarioManager.executeScenarioActionsBySenarioId(Long.parseLong(scenario[0].toString()));
						}
					}
				} catch (Exception e) {
					LogUtil.error("Exception when running the scheduler. "
							+ e.getMessage());
				}
			}
		}, delay, period);
	}

}
