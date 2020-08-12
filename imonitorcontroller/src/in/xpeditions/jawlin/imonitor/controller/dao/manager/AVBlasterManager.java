/* Copyright  2012 iMonitor Solutions India Private Limited */

package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AVBlaster;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AVCategory;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * 
 * @author sumit kumar
 *
 */
public class AVBlasterManager {
	
	private DaoManager daoManager = new DaoManager();

	@SuppressWarnings("unchecked")
	public List<AVCategory> getListOfAVCategories() {
		List<AVCategory> avCategoryList = new ArrayList<AVCategory>();
		try {
			avCategoryList = (List<AVCategory>) daoManager.list(AVCategory.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return avCategoryList;
	}
	
	@SuppressWarnings("unchecked")
	public List<AVBlaster> getListOfAVBlasterCodes() {
		List<AVBlaster> avBlasterList = new ArrayList<AVBlaster>();
		try {
			avBlasterList = (List<AVBlaster>) daoManager.list(AVBlaster.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return avBlasterList;
	}

	@SuppressWarnings("unchecked")
	public List<AVBlaster> getAVBlasterCodeList(){
		List<AVBlaster> avCodeList = new ArrayList<AVBlaster>();
		try {
			avCodeList = (List<AVBlaster>) daoManager.list(AVBlaster.class);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("Caught Exception :\n", e);
		}
		return avCodeList;
	}

	public boolean saveAVBlasterCodeInfo(AVBlaster avBlaster) {
		boolean result = false;
		try {
			daoManager.save(avBlaster);
			result = true;
		} catch (Exception e) {
			LogUtil.error("Error when saving avBlaster ~ message : " + e.getMessage());
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public List<?> listAskedAVBlasterCodes(String sSearch, String sOrder,
			int start, int length) {
		String query = "";
		query += "select avb.id, avb.region, avc.name, "
				+ "avb.brand, avb.code from "
				+ "AVBlaster as avb join avb.category as avc "
				+ "where avb.category = avc.id and (" + "avb.region like '%"
				+ sSearch + "%' or avb.brand like '%" + sSearch + "%' "
				+ " or avb.code like '%" + sSearch + "%' "
				+ "or avc.name like '%" + sSearch + "%' ) " + sOrder;
		
		List list = null;
		try {
			list = daoManager.listAskedObjects(query, start, length, AVBlaster.class);
		} catch (Exception e) {
			LogUtil.info("Exception while list of AV Blaster Codes : ", e);
		}
		return list;
	}
	
	public int getTotalIRCodesCount(){
		return daoManager.getCount(AVBlaster.class);	
	}
	
	public int getTotalAVBlasterCodesCount(String sSearch) {
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(avb)" + " from "
					+ "AVBlaster as avb join avb.category as avc "
					+ "where avb.category = avc.id and (" + "avb.region like '%"
					+ sSearch + "%' or avb.brand like '%" + sSearch + "%' "
					+ " or avb.code like '%" + sSearch + "%' "
					+ "or avc.name like '%" + sSearch + "%' )";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			count = lCount.intValue();
		} catch (Exception ex) {
			LogUtil.info("Caught Exception  while getting IR Codes Count : ", ex);
		} finally {
			dbc.closeConnection();
		}
		return count;
	}

	public AVBlaster getAVBlasterCodeById(long id) {
		return (AVBlaster) daoManager.get(id, AVBlaster.class);
	}

	public boolean deleteAVBlasterCodeInfo(AVBlaster avBlasterFromDB) {
		boolean result = false;
		try {
			daoManager.delete(avBlasterFromDB);
			result = true;
		} catch (Exception e) {
			LogUtil.error("Error when deleting AV Blaster Code entry.");
		}
		return result;
	}

	public boolean updateAVBlasterCodeInfo(AVBlaster avBlaster) {
		boolean result = false;
		try {
			daoManager.update(avBlaster);
			result = true;
		} catch (Exception e) {
			LogUtil.error("Error when updating AV Blaster Code Info.");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<String> getBrandListByCategory(long categoryId) {
		DBConnection dbc = null;
		List<String> avBrandList = new ArrayList<String>();
		try {
			String query = "";
			query += " select avb.brand from avBlaster as avb join avCategory as avc where"
					+ " avb.category = avc.id and"
					+ " avc.id = " + categoryId
					+ " group by avb.brand ";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			avBrandList = q.list();
		} catch (Exception ex) {
			LogUtil.info("Caught Exception  while getting IR Codes Count : ", ex);
		} finally {
			dbc.closeConnection();
		}
		return avBrandList;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getCodeListByCategoryAndBrand(long categoryId, String bName){
		
		DBConnection dbc = null;
		List<String> avCodeList = new ArrayList<String>();
		try {
			String query = "";
			query += " select avb.code from avBlaster as avb join avCategory as avc where"
					+ " avb.category = avc.id and"
					+ " avc.id = " + categoryId + " and"
					+ " avb.brand = '" + bName +"' "
					+ " group by avb.code ";
		
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createSQLQuery(query);
			avCodeList = q.list();
		} catch (Exception ex) {
			LogUtil.info("Caught Exception  while getting IR Codes Count : ", ex);
		} finally {
			dbc.closeConnection();
		}
		return avCodeList;
		
	}
}
