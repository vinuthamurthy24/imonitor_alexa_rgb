/* Copyright  2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.listener;


import java.io.IOException;
import java.sql.Date;

import in.xpeditions.jawlin.imonitor.controller.action.sampleaction;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DashboardManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Coladi
 *
 */
 
public class QuartzSchedulerListener implements ServletContextListener {
 
	public void contextDestroyed(ServletContextEvent arg0) {
		//
	}
 
	public void contextInitialized(ServletContextEvent arg0) {
// LogUtil.info("hhhhhhhhhhhhhhhhhhhhhhh");

 
 
 /*try {
	 JobDetail job = new JobDetail();
	 	job.setName("dummyJobName");
		job.setJobClass(sampleaction.class);
		
		//configure the scheduler time
		SimpleTrigger trigger = new SimpleTrigger();
		trigger.setName("dummyTriggerName");
		trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		trigger.setRepeatInterval(10000);
		//schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
		
	 
	} catch (SchedulerException se) {
	se.printStackTrace();
	}
	}*/
}
}