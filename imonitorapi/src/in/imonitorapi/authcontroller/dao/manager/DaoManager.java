/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.imonitorapi.authcontroller.dao.manager;


import in.imonitor.authcontroller.DBConnection;
import in.imonitorapi.util.LogUtil;


import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class DaoManager {
	
	// ******************************************************************** sumit begin **************************************************************
		public boolean executeHQLUpdateQuery(String HQLQuery) {
			String hqlG = HQLQuery;
			boolean result = false;
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Session session = dbc.getSession();
//				Query query = session.createSQLQuery(hqlG);
				Query query = session.createQuery(hqlG);
				Transaction tx = session.beginTransaction();
				query.executeUpdate();
				result = true;
				tx.commit();
			} catch (Exception ex) {
				LogUtil.error("excuteUpdateQuery Failed.");
				LogUtil.info(hqlG, ex);
			} finally {
				dbc.closeConnection();
			}
			return result;
		}

		public boolean executeSQLQuery(String SQLQuery) {
			boolean result = false;
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Session session = dbc.getSession();
				Query query = session.createSQLQuery(SQLQuery);
				Transaction tx = session.beginTransaction();
				query.executeUpdate();
				result = true;
				tx.commit();
			} catch (Exception ex) {
				LogUtil.error("executeSQLQuery Failed.");
				LogUtil.info(SQLQuery, ex);
			} finally {
				dbc.closeConnection();
			}
			return result;
		}	
		
		public boolean executeUpdateQuery(String HQLQuery) {
			String hqlG = HQLQuery;
			boolean result = false;
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Session session = dbc.getSession();
				Query query = session.createSQLQuery(hqlG);
//				Query query = session.createQuery(hqlG);
				Transaction tx = session.beginTransaction();
				query.executeUpdate();
				result = true;
				tx.commit();
			} catch (Exception ex) {
				LogUtil.error("excuteUpdateQuery Failed.");
				LogUtil.info(hqlG, ex);
			} finally {
				dbc.closeConnection();
			}
			return result;
		}
	// ************************************************************** sumit end ********************************************************


		public void delete(Object object) {
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				dbc.beginTransaction();
				dbc.getSession().delete(object);
				dbc.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
				try {
					dbc.rollback();
				} catch (Exception ex1) {
					LogUtil.error(ex.getMessage());
				}
			} finally {
				dbc.closeConnection();
			}
		}	
		
		public boolean deleteAndReturnBoolean(Object object) {
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				dbc.beginTransaction();
				dbc.getSession().delete(object);
				dbc.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
				dbc.rollback();
				return false;
			} finally {
				dbc.closeConnection();
			}
			return true;

		}

		@SuppressWarnings( { "unchecked", "null" })
		public Object get(Serializable id, Class type) {
			DBConnection dbc = null;
			dbc.openConnection();
			Object obj = null;
			try {
				obj = dbc.getSession().get(type, id);
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return obj;
		}

		@SuppressWarnings("unchecked")
		public void delete(long id, Class className) {
			Object object = this.get(id, className);
			this.delete(object);
		}

		@SuppressWarnings("unchecked")
		public Object get(long id, Class className) {
			DBConnection dbc = null;
			Object object = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				object = (Object) dbc.getSession().get(className, id);
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return object;
		}

		@SuppressWarnings("unchecked")
		public Object getInt(int id, Class className) {
			DBConnection dbc = null;
			Object object = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				object = (Object) dbc.getSession().get(className, id);
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return object;
		}

		@SuppressWarnings("unchecked")
		public Object get(String id, Class className) {
			DBConnection dbc = null;
			Object object = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				dbc.beginTransaction();
				object = (Object) dbc.getSession().get(className, id);
				dbc.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				dbc.rollback();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return object;
		}

		@SuppressWarnings("unchecked")
		public Object get(Object object1, Class className) {
			DBConnection dbc = null;
			Object object = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				object = (Object) dbc.getSession().get(className,
						(Serializable) object1);
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return object;
		}

		@SuppressWarnings("unchecked")
		public List list(Class className) {
			DBConnection dbc = null;
			List objects = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Criteria cr = dbc.getSession().createCriteria(className);
				objects = cr.list();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return objects;
		}
		
		public List listFirstTenEvents(String columnName,Object value,Class className) {
			DBConnection dbc = null;
			List<Object> lstObj = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Criteria cr = dbc.getSession().createCriteria(className);
				Criterion criterion = Restrictions.eq(columnName, value);
				cr.add(criterion).setMaxResults(10);
				lstObj = cr.add(criterion).list();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return lstObj;
		}

		@SuppressWarnings("unchecked")
		public List getProjectedList(Class className, String[] fields) {
			DBConnection dbc = null;
			List objects = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				// dbc.getSession().(object);
				Criteria cr = dbc.getSession().createCriteria(className);
				ProjectionList proList = Projections.projectionList();
				for (int i = 0; i < fields.length; i++) {
					proList.add(Projections.property(fields[i]));
				}
				cr.setProjection(proList);
				objects = cr.list();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return objects;
		}

		@SuppressWarnings("unchecked")
		public int getCount(Class className) {
			DBConnection dbc = null;
			int count = 0;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Criteria cr = dbc.getSession().createCriteria(className);
				cr.setProjection(Projections.rowCount());
				List objects = cr.list();
				count = (Integer) objects.get(0);
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return count;
		}

		
		
		
		@SuppressWarnings("unchecked")
		public boolean updateByQuerry(String query,Class className) {
			DBConnection dbc = null;
			boolean result = true;
			try {
				LogUtil.info("executingggggggggggggggggggggggg"+query);
				dbc = new DBConnection();
				dbc.openConnection();
				dbc.beginTransaction();
				dbc.getSession().createQuery(query);
				dbc.commit();
			} catch (Exception ex) {
				result = false;
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
				try {
					dbc.rollback();
				} catch (Exception ex1) {
					LogUtil.error(ex1.getMessage());
				}			
			} finally {
					dbc.closeConnection();
			}
			return result;
		}

		

		public boolean save(Object object) {
			DBConnection dbc = null;
			boolean result = false;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				dbc.beginTransaction();
				dbc.getSession().save(object);
				dbc.commit();
				dbc.getSession().flush();
				result = true;
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
				try {
					dbc.rollback();
				} catch (Exception ex1) {
					LogUtil.error(ex1.getMessage());
				}
			} finally {
				dbc.closeConnection();
				return result;
			}
		}

		public void saveOrUpdate(Object object) {
			DBConnection dbc = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				dbc.beginTransaction();
				dbc.getSession().saveOrUpdate(object);
				dbc.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
				try {
					dbc.rollback();
				} catch (Exception ex1) {
					LogUtil.error(ex1.getMessage());
				}
			} finally {
				dbc.getSession().flush();
				dbc.closeConnection();
			}
		}

		public boolean update(Object object) {
			DBConnection dbc = null;
			boolean result = false;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				dbc.beginTransaction();
				dbc.getSession().update(object);
				dbc.commit();
				result = true;
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
				try {
					dbc.rollback();
				} catch (Exception ex1) {
					LogUtil.error(ex1.getMessage());
				}
			} finally {
				dbc.closeConnection();
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		public List get(SimpleExpression[] simpleExpressions, Class type) {
			DBConnection dbc = null;
			List<Object> obj = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();

				Criteria criteria = dbc.getSession().createCriteria(type);
				for (int i = 0; i < simpleExpressions.length; i++) {
					criteria.add(simpleExpressions[i]);
				}
				obj = (List<Object>) criteria.list();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return obj;
		}

		@SuppressWarnings("unchecked")
		public List list(String columnName, Object value, Class className) {
			DBConnection dbc = null;
			List<Object> lstObj = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Criteria cr = dbc.getSession().createCriteria(className);
				Criterion criterion = Restrictions.eq(columnName, value);
				lstObj = cr.add(criterion).list();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return lstObj;
		}
		
		@SuppressWarnings("unchecked")
		public List list(String joinProperty, String criteria, Object value, Class className) {
			DBConnection dbc = null;
	        List<Object> objects = null;
	        try {
	        	dbc = new DBConnection();
				dbc.openConnection();
	            Criteria cr = dbc.getSession().createCriteria(className);
	            cr.createAlias(joinProperty, "join");
	            cr.add(Restrictions.eq("join."+criteria, value));
	            objects = cr.list();
	        } catch (Exception ex) {
	          ex.printStackTrace();
				LogUtil.error(ex.getMessage());
	        } finally {
	        	dbc.closeConnection();
	        }
	        return objects;
	    }

		/**
		 * 
		 * @param string
		 *            ->variable
		 * @param value
		 *            object.value
		 * @param type
		 *            Class
		 * @return return list
		 */
		@SuppressWarnings("unchecked")
		public List get(String string, Object value, Class type) {
			DBConnection dbc = null;
			List<Object> obj = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				String queryString = "from " + type.getName()
						+ " as type where type." + string + "=" + value;
				Query criteria = dbc.getSession().createQuery(queryString);
				obj = (List<Object>) criteria.list();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return obj;
		}


		/**
		 * The method will get the list of objects according to the criteria and
		 * return the first element from the list
		 * 
		 * @param columnName
		 * @param value
		 * @param classname
		 * @return = null if the list is empty Single object for the given column
		 *         name, value and class type if the list is not empty
		 */
		@SuppressWarnings("unchecked")
		public Object getOne(String columnName, Object value, Class classname) {
			List<Object> lstObj = this.list(columnName, value, classname);
			Object object = null;
			try {
				if (lstObj != null && lstObj.size() > 0) {
					object = lstObj.get(0);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			}
			return object;

		}
		
		@SuppressWarnings("unchecked")
		public List listWithFilterColum(String columnName, Class className) {
			DBConnection dbc = null;
			List<Object> lstObj = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Criteria cr = dbc.getSession().createCriteria(className);
				//Criterion criterion = Restrictions.eq(columnName,"MODE_HOME");
				cr.add(Restrictions.ne(columnName,"MODE_HOME"));
				cr.add(Restrictions.ne(columnName,"MODE_STAY"));
				cr.add(Restrictions.ne(columnName,"MODE_AWAY"));
				lstObj = cr.list();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return lstObj;
		}
		
		@SuppressWarnings("unchecked")
		public List<Object[]> listAskedObjects(String query, Class className) {
			DBConnection dbc = null;
			List<Object[]> objects = null;
			try {
				dbc = new DBConnection();
				dbc.openConnection();
				Query q = dbc.getSession().createQuery(query);
				objects = q.list();
			} catch (Exception ex) {
				ex.printStackTrace();
				LogUtil.error(ex.getMessage());
			} finally {
				dbc.closeConnection();
			}
			return objects;
		}
}