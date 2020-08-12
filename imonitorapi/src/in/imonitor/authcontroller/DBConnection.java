/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.imonitor.authcontroller;

import in.imonitorapi.util.LogUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @@author Naveen M S
 * 
 */

public class DBConnection {

	private static SessionFactory factory;
	@SuppressWarnings({ "rawtypes" })
	private static final ThreadLocal session = new ThreadLocal();
	private Transaction transaction;

	/**
    *
    */
	public DBConnection() {
	

	
		try {
			factory = new Configuration().configure("imonitorapi.hibernate.cfg.xml").buildSessionFactory();
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	
	}
	/**
	 * This method open a database session
	 * 
	 * @@return session
	 * @@throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public Session openConnection() throws HibernateException {
		Session s = (Session) session.get();
		if (s == null) {
			s = factory.openSession();
			session.set(s);
		} else if(!s.isOpen()){
			this.closeConnection();
			s = this.openConnection();
		} else if(!s.isConnected()){
			this.closeConnection();
			s = this.openConnection();
		}
		return s;
	}

	/**
	 * This method close the hibernate session
	 * 
	 * @@throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public void closeConnection() {
		Session s = (Session) session.get();
		session.set(null);
		if (s != null){
			s.close();
		}
	}

	/**
	 * This method begin a database trasnsaction
	 * 
	 * @@throws HibernateException
	 */
	public void beginTransaction() throws HibernateException {
		if (getTransaction() != null && getTransaction().isActive()) {
			throw new HibernateException("Transaction Already Active");
		} else if (getSession() == null && !(getSession().isOpen())) {
			throw new HibernateException("Session is not open");
		}
		transaction = getSession().beginTransaction();
	}

	/**
	 * This method is used to commit a database transaction
	 * 
	 * @@throws HibernateException
	 */
	public void commit() throws HibernateException {
		if (getTransaction() == null && !(getTransaction().isActive()))
			throw new HibernateException("Transaction is not active!");
		if (!getTransaction().wasCommitted())
			getTransaction().commit();
		transaction = null;
	}

	/**
	 * This method rollback all the operations in a transaction
	 * 
	 * @@throws HibernateException
	 */
	public void rollback() throws HibernateException {
		if (getTransaction() == null && !(getTransaction().isActive())) {
			throw new HibernateException("Transaction is not active!");
		} else {
			transaction.rollback();
			transaction = null;
		}
	}

	private Transaction getTransaction() throws HibernateException {
		return transaction;
	}

	/**
	 * This method will open current database session
	 * 
	 * @@return
	 * @@throws HibernateException
	 */
	public Session getSession() throws HibernateException {
		Session s = (Session) session.get();
		if (s == null)
			throw new HibernateException("Session closed");
		return s;
	}
}
