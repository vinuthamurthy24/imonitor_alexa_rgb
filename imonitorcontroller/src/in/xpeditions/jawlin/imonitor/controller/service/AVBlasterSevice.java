/* Copyright  2012 iMonitor Solutions India Private Limited */

package in.xpeditions.jawlin.imonitor.controller.service;

import java.util.ArrayList;
import java.util.List;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AVBlaster;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AVCategory;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.AVBlasterManager;
import in.xpeditions.jawlin.imonitor.general.DataTable;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author sumit kumar
 *
 */
public class AVBlasterSevice {
	

	private AVBlasterManager avBlasterManager = new AVBlasterManager();
	XStream stream = new XStream();
	
	public String getListOfAVCategories(){
		List<AVCategory> avCategoryList = new ArrayList<AVCategory>();
		avCategoryList = avBlasterManager.getListOfAVCategories();
		String avCategoryXml  = stream.toXML(avCategoryList);	
		return avCategoryXml;

	}
	
	public String getListOfAVBlasterCodes() {
		List<AVBlaster> avBlasterList = new ArrayList<AVBlaster>();
		avBlasterList = avBlasterManager.getListOfAVBlasterCodes();
		String avBlasterXml  = stream.toXML(avBlasterList);		
		return avBlasterXml;
	}
	
	public String getBrandListByCategory(String categoryIdXml){
		long categoryId = (Long) stream.fromXML(categoryIdXml);
		List<String> avBrandList = new ArrayList<String>();
		avBrandList = avBlasterManager.getBrandListByCategory(categoryId);
		String avBrandListXml  = stream.toXML(avBrandList);		
		return avBrandListXml;
	}
	
	public String getCodeListByCategoryAndBrand(String categoryIdXml, String brandNameXml){
		long categoryId = (Long) stream.fromXML(categoryIdXml);
		String brandName = (String) stream.fromXML(brandNameXml);
		List<String> avCodeList = new ArrayList<String>();
		avCodeList = avBlasterManager.getCodeListByCategoryAndBrand(categoryId, brandName);
		String avCodeListXml  = stream.toXML(avCodeList);		
		return avCodeListXml;
	}
	
	public String addAVBlasterCodeInfo(String avBlasterCodeInfoXml){
		AVBlaster avBlaster = (AVBlaster) stream.fromXML(avBlasterCodeInfoXml);
		String result = "success";
		List<AVBlaster> avBlasters = avBlasterManager.getAVBlasterCodeList();
		//check if the combination of [Category - Brand - Code] already exists.
		for(AVBlaster av: avBlasters){
			if(!(	(av.getCategory().getId() == avBlaster.getCategory().getId()) && 
					(av.getBrand().equalsIgnoreCase(avBlaster.getBrand())) && 
					(av.getCode().equalsIgnoreCase(avBlaster.getCode())))){
				continue;
			}else{
				result = "alreadyExists";
				return stream.toXML(result);
			}
		}
		if(!avBlasterManager.saveAVBlasterCodeInfo(avBlaster)){
			result = "dbError";
		}
		return stream.toXML(result);
	}
	
	public String getAVBlasterCodeById(String idXml){
		long id = (Long) stream.fromXML(idXml);
		AVBlaster avBlaster = avBlasterManager.getAVBlasterCodeById(id);
		String valueInXML = stream.toXML(avBlaster);
		return valueInXML;
	}
	
	public String updateAVBlasterCodeInfo(String avBlasterXml){
		String result="no";
		AVBlaster avBlaster = (AVBlaster) stream.fromXML(avBlasterXml);
		if(avBlasterManager.updateAVBlasterCodeInfo(avBlaster)){
			result="yes";
		}
		return result;
	}
	
	public String listAskedAVBlasterCodes(String dataTableXml){
		XStream xStream=new XStream();
		DataTable dataTable =  (DataTable) xStream.fromXML(dataTableXml);
		int count = avBlasterManager.getTotalIRCodesCount();
		dataTable.setTotalRows(count);
		String sSearch = dataTable.getsSearch();
		String[] cols = {"avb.region", "avb.category", "avb.brand", "avb.code"};
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		String colName = cols[col];
		String sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = avBlasterManager.listAskedAVBlasterCodes(sSearch, sOrder, dataTable.getiDisplayStart(), dataTable.getiDisplayLength());
		dataTable.setResults(list);
		int displayRow = avBlasterManager.getTotalAVBlasterCodesCount(sSearch);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}
	
	public String deleteAVBlasterCodeInfoById(String avBlasterXml){
		AVBlaster avBlaster = (AVBlaster) stream.fromXML(avBlasterXml);
		String result = "no";
		AVBlaster avBlasterFromDB = avBlasterManager.getAVBlasterCodeById(avBlaster.getId());
		if(avBlasterManager.deleteAVBlasterCodeInfo(avBlasterFromDB)){
			result = "yes";
		}
		return result;
	}
}
