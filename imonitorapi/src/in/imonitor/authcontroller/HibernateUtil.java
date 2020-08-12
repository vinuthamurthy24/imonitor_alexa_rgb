/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.imonitor.authcontroller;



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
			
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}
}
