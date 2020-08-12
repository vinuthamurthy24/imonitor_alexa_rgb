package in.imonitor.authcontroller;



import org.hibernate.Session;
import in.imonitor.authcontroller.DBConnection;
import in.monitor.authprovider.Clients;



public class StoreAccessToken {
	
	
	public static String saveClient(Clients clientID) {
	
		System.out.println("Hibernate save image into database");
		Session session = HibernateUtil.getSessionfactory().openSession();
		session.beginTransaction();
		DBConnection db = null;
		String result = "";
		try {
			db = new DBConnection();
			db.openConnection();
			Session session1 = db.getSession();
			db.beginTransaction();
			session1.save(clientID);
			session1.flush();
			db.commit();
			result ="true";
			
		}catch (Exception ex){
			ex.printStackTrace();
		}finally {
			db.closeConnection();
		}
		return result;
		
	}
	
	
	
	

}
