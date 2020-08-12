/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.util;

import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Coladi
 *
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static void buildSessionFactory(String hibernateConfigFileName) {
		try{
			Configuration configure = new Configuration().configure(hibernateConfigFileName);
			sessionFactory = configure.buildSessionFactory();
		}catch(Throwable ex){
			LogUtil.error(ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}
}
