/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.service;

import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.action.LocationUpdateAction;
import in.xpeditions.jawlin.imonitor.controller.action.RuleUpdateAction;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.LocationManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

/**
 * @author Asmodeus
 * 
 */
public class LocationServiceManager {
	
	//sumit start:
	public String getLocationIdByName(String locNameXml, String custIdXml){
		
		XStream stream = new XStream();
		String locName = (String) stream.fromXML(locNameXml);
		String customerId = (String) stream.fromXML(custIdXml);
		LocationManager locationManager = new LocationManager();
		long locId = locationManager.getLocationIdByName(locName, customerId);
	
		return stream.toXML(locId);
	}
	
	public String saveUploadedImageForLocation(String locationIdXml, String filePathXml){
		
		String result = "no";
		XStream stream = new XStream();
		Long locationId = (Long) stream.fromXML(locationIdXml);
		String filePath = (String) stream.fromXML(filePathXml);
		
		LocationManager locationManager = new LocationManager();
		if(locationManager.saveUploadedImageForLocation(locationId.longValue(),filePath)){
			result = "yes";
		}
		
		return result;
	}
	//sumit end:

	public String saveLocation(String xml) throws SecurityException,
			DOMException, IllegalArgumentException,
			ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		
		
		
		String result = "no";
		XStream xStream = new XStream();
		Location location = (Location) xStream.fromXML(xml);
		String mobIcon = location.getMobileIconFile();
		String iconnnnnn = mobIcon.substring(39, mobIcon.length() - 4);
		LogUtil.info("Mob iconnnnnn to save: " + iconnnnnn);
		location.setMobileIconFile(iconnnnnn);
		
		//sumit start: Check for duplicate entry for the room
		String isDuplicate = verifyRoomDetails(location);
		if(isDuplicate.equalsIgnoreCase("duplicateRoom")){
			result = "duplicate";
		}else if (isDuplicate.equalsIgnoreCase("validRoom")) {
			LocationManager locationManager = new LocationManager();
			if (locationManager.saveLocation(location)) {
				result = "yes";
			}
		}
		return result;
	}
	//sumit start
	@SuppressWarnings("unchecked")
	public String verifyRoomDetails(Location location){
		String result = "validRoom";
		try {
			List<Location> locations = new DaoManager().get("customer",location.getCustomer().getId(), Location.class);
			XStream stream = new XStream();
			for(Location locationFromDb : locations){
				if(location.getName().equals(locationFromDb.getName()) 
						&& (location.getCustomer().getId() == locationFromDb.getCustomer().getId()))
				{
					if(location.getId() != locationFromDb.getId())
					result = "duplicateRoom";
				} 
			}
		} catch (Exception e) {
			LogUtil.info("Got Exception while saving Room: ", e);
		}
		return result;
	}
	//sumit end

	public String deleteLocation(String xml) throws SecurityException,
			DOMException, IllegalArgumentException,
			ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		XStream xStream = new XStream();
		long id = (Long) xStream.fromXML(xml);
		LocationManager locationManager = new LocationManager();
		Location location = locationManager.deleteLocationIfNoOtherReferenceAndNotLast(id);
		return xStream.toXML(location);
	}

	public String updateLocation(String xml) throws SecurityException,
			DOMException, IllegalArgumentException,
			ParserConfigurationException, SAXException, IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		String result = "no";
		XStream xStream = new XStream();
		boolean resultFromImvg = false;
		ImonitorControllerAction roomUpdateacton = null;

		Location location = (Location) xStream.fromXML(xml);
		String mobIcon = location.getMobileIconFile();
		String iconnnnnn = mobIcon.substring(39, mobIcon.length() - 4);
		LogUtil.info("EDIT LOCATION iconnnnnn to save: " + iconnnnnn);
		location.setMobileIconFile(iconnnnnn);
		
		
		//sumit start: Check for duplicate entry for the room
		String isDuplicate = verifyRoomDetails(location);
		ActionModel actionModel = new ActionModel(null, location);
		LocationManager locationManager = new LocationManager();

		if(isDuplicate.equalsIgnoreCase("duplicateRoom")){
			result = "duplicate";
		}else if (isDuplicate.equalsIgnoreCase("validRoom")) {
		try
		{
			roomUpdateacton = new LocationUpdateAction();
			roomUpdateacton.executeCommand(actionModel);
			resultFromImvg = IMonitorUtil.waitForResult(roomUpdateacton);
		}
		catch (Exception e)
		{
			LogUtil.info("Some serious error while trying to modify room. Revert to origonal room.", e);
			actionModel.setMessage("Internal Error while updating room.");
			resultFromImvg = false;
		}
		
		}
		
		
		if(resultFromImvg){
//				If the update is a failure, then we should roll back the updated rule.
			if(!roomUpdateacton.isActionSuccess()){				
					result = "msg.imonitor.rooms.0004";				
			}else{
				locationManager.updateLocation(location);
				result = "yes";
			}
		}else{
			result = "msg.imonitor.rooms.0004"; //Space isuues
		}		
		return result;
	}

	public String listLocations() throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		XStream xStream = new XStream();
		LocationManager locationManager = new LocationManager();
		List<Location> locations = locationManager.listOfLocations();
		String xml = xStream.toXML(locations);
		return xml;
	}

	
	public String listLocations(String xml) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		XStream xStream = new XStream();
		Customer customer = (Customer) xStream.fromXML(xml);
		LocationManager locationManager = new LocationManager();
		List<Location> locations = locationManager.listOfLocations(customer);
		String result = xStream.toXML(locations);
		return result;
	}

	public String listAskedLocations(String xml) {
		XStream xStream = new XStream();
		String[] items = xml.split("-");
		DataTable dataTable = (DataTable) xStream.fromXML(items[0]);
		long id = (Long) xStream.fromXML(items[1]);
		LocationManager locationManager = new LocationManager();
		String sSearch = dataTable.getsSearch();
		String[] cols = { "Name", "Details"};
		String sOrder = "";
		int col = Integer.parseInt(dataTable.getiSortCol_0());
		  String colName = cols[col];
		  sOrder = "order by " + colName + " " + dataTable.getsSortDir_0();
		List<?> list = locationManager.listAskedLocations(sSearch,sOrder, dataTable
				.getiDisplayStart(), dataTable.getiDisplayLength(), id);
		dataTable.setTotalRows(locationManager.getTotalLocationCountByCustomerId(id));
		dataTable.setResults(list);
		int displayRow = locationManager.getTotalLocationCount(sSearch,id);
		dataTable.setTotalDispalyRows(displayRow);
		String valueInXml = xStream.toXML(dataTable);
		return valueInXml;
	}

	public String getLocationById(String xml) {
		XStream stream = new XStream();
		long id = (Long) stream.fromXML(xml);
		LocationManager locationManager = new LocationManager();
		Location location = locationManager.getLocationById(id);
		return stream.toXML(location);
		
	}
	public String listLocationOfCustomer(String xml)
	{
		XStream xStream = new XStream();
		DataTable dataTable = new DataTable();	
		Customer customer=(Customer) xStream.fromXML(xml);
		LocationManager locationManager = new LocationManager();
		List<Object[]> locations = locationManager.listOfLocations(customer.getId());
		
		dataTable.setTotalRows(locationManager.getTotalLocationCountByCustomerId(customer.getId()));
		dataTable.setResults(locations);
		return xStream.toXML(dataTable);
	}
	
	
	public String verifyLocation(Location location)
	{
		
		
		return null;
	}
	
}
