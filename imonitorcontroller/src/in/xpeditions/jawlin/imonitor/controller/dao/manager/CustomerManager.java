/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.ActionType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AdminSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.AlertType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerServices;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.DeviceType;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.FirmWare;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.H264CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.IndoorUnitConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LCDRemoteConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Location;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.LocationMap;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MinimoteConfig;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.MotorConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.OTPForSelfRegistration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Powerinformation;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Rule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Icon;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.SystemIntegrator;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.UserNotificationProfile;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerPasswordHintQuestionAnswer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.acConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserDeviceAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.util.DBConnection;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.controller.properties.ControllerProperties;
import in.xpeditions.jawlin.imonitor.controller.service.DeviceConfigurationServiceManager;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.FTPUtil;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.xml.Log4jEntityResolver;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.thoughtworks.xstream.XStream;


import java.util.Calendar;

public class CustomerManager {
	DaoManager daoManager = new DaoManager();
	
	// *************************************** sumit added: for forgot password user authentication feature. *************************************
	public boolean checkCustomerExistence(String customerId) {
		Customer customer = (Customer) daoManager.getOne("customerId", customerId, Customer.class);
		//If customer = null then return false to specify customer does not exist.
		if (customer == null) {
			return false;	
		}
		return true;
	}

	public boolean saveResetPasswordInfo(long cId, long quesId, String answer){
		DBConnection dbc = null;
		boolean isDBUpdate = false;
		String query = "update customer c set c.quesId="+quesId+", c.forgotPasswordAnswer='"+answer+"' where c.id="+cId+" ";
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(query);
			Transaction tx = session.beginTransaction();
			q.executeUpdate();
			tx.commit();
			isDBUpdate = true;
		} catch (Exception e) {
			LogUtil.error("Error while saving Reset Password Info: "+e.getMessage());
			e.printStackTrace();
		}finally {
			dbc.closeConnection();
			
			
		}
		return isDBUpdate;
	}
	
	public CustomerPasswordHintQuestionAnswer getForgotPasswordQuestionsWithAnswersForCustomerById(long id){
		DBConnection dbc = null;
		CustomerPasswordHintQuestionAnswer hintQuestionAnswer = null;
		String query = "select c.quesId, f.question, c.forgotPasswordAnswer"
					+ " from customer as c,"
					+ " forgotpasswordquestion as f"
					+ " where c.id = "+id+" and c.quesId=f.id;";
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(query);
			Object[] objects = (Object[]) q.uniqueResult();
			if(objects != null){
				long quesId = Long.parseLong(IMonitorUtil.convertToString(objects[0]));
				String hintQuestion = IMonitorUtil.convertToString(objects[1]);
				String hintAnswer = IMonitorUtil.convertToString(objects[2]);
				hintQuestionAnswer =  new CustomerPasswordHintQuestionAnswer();
				hintQuestionAnswer.setQuesId(quesId);
				hintQuestionAnswer.setHintQuestion(hintQuestion);
				hintQuestionAnswer.setHintAnswer(hintAnswer);
			}
		} catch (Exception e) {
			LogUtil.error("Error while retieving hint Question and Answer for the customer."+ e.getMessage());
			e.printStackTrace();
		} finally {
			if(dbc != null){
				dbc.closeConnection();
			}
		}
		return hintQuestionAnswer;
		
	}
	
	public String getEmailOfCustomer(long cId, String customerId){
		DBConnection dbc = null;
		String email = null;
		String query ="select c.email from customer as c where c.id="+cId+" and c.customerId='"+customerId+"' ";
		
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(query);
			email = (String) q.uniqueResult();
		} catch (Exception e) {
			LogUtil.error("Error while getting the email for the customer: "+e.getMessage());
			e.printStackTrace();
		}finally {
			dbc.closeConnection();
		}
		return email;
	}
	// *************************************************************** sumit end *****************************************************************
	public boolean saveCustomer(Customer customer) {
		boolean result = false;
		try {
			daoManager.save(customer);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteCustomer(Customer customer) {
	
		boolean result = false;
		try {
			daoManager.delete(customer);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean updateCustomer(Customer customer) {
		boolean result = false;
		try {
			daoManager.update(customer);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Customer updateCustomerAndSetGatewayNull(Customer customer) {
		DBConnection db = new DBConnection();
		db.openConnection();
		Session session = db.getSession();
		db.beginTransaction();
		session.update(customer);
		db.commit();
		GatewayManager gatewayManager = new GatewayManager();
		Set<GateWay> gateWays = gatewayManager.getGateWaysOfCustomer(customer);
		for (GateWay gateWay : gateWays) {
			gateWay.setCustomer(null);
			gatewayManager.updateCustomerOfGateWay(gateWay);
		}
		try {
			db.closeConnection();
		} catch (Exception e) {
			LogUtil.info("could not close connection:  " +e.getMessage());
		}
		
		return customer;
	}

	public Customer getCustomerByMacId(String macId) {
		String query = "select c from GateWay g left join g.customer c where g.macId='"
				+ macId + "'";
		DBConnection dbc = null;
		Customer customer = null;

		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			customer = (Customer) q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			LogUtil.info("Error fetchinggg : " + ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return customer;
	}

	@SuppressWarnings("unchecked")
	public List<Customer> listOfCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		try {
			customers = (List<Customer>) daoManager.list(Customer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}                                                                                                                   
		return customers;

	}

	public Customer getCustomerByCustomerId(String customerId) {
		Customer customer = new Customer();
		try {
			customer = (Customer) daoManager.getOne("customerId", customerId,
					Customer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	
	public Customer getCustomerBycustomerId(String customerId) {
		Customer customer = new Customer();
		//LogUtil.info(customerId);
		try {
			customer = (Customer) daoManager.get( customerId,Customer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	
	
	@SuppressWarnings("unchecked")
	public List listAskedCustomer(String sSearch, String sOrder, int start,
			int length) {
		String query = "";
		query += "select c.id,c.customerId,c.firstName"
				+ ",c.lastName,c.phoneNumber,s.name from "
				+ "Customer as c join c.status as s "
				+ "where c.status=s.id and (" + "c.customerId like '%"
				+ sSearch + "%' or c.firstName like '%" + sSearch + "%' "
				+ " or c.lastName like '%" + sSearch + "%' "
				+ "or c.phoneNumber like '%" + sSearch + "%' "
				+ "or s.name like '%" + sSearch + "%' ) " + sOrder;
		List list = daoManager.listAskedObjects(query, start, length,
				Customer.class);
		return list;
	}

	public int getTotalCustomerCount(String sSearch) {
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(c)" + " from "
					+ "Customer as c join c.status as s "
					+ "where c.status=s.id and (" + "c.customerId like '%"
					+ sSearch + "%' or c.firstName like '%" + sSearch + "%' "
					+ " or c.lastName like '%" + sSearch + "%' "
					+ "or c.phoneNumber like '%" + sSearch + "%' "
					+ "or s.name like '%" + sSearch + "%' )";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			count = lCount.intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return count;
	}

	public int getTotalGateWayCount() {
		DBConnection dbc = null;
		int count = 0;
		try {
			String query = "";
			query += "select count(c)" + " from "
					+ "Customer as c join c.status as s "
					+ "where c.status=s.id";
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			Long lCount = (Long) q.uniqueResult();
			count = lCount.intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return count;
	}

	public Customer getCustomerById(long id) {
		
		return (Customer) daoManager.get(id, Customer.class);
	}

	public Customer getCustomerWithGateWaysByCustomerId(long id) {
		Customer customer = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			customer = (Customer) session.get(Customer.class, id);
			if (null != customer) {
				Hibernate.initialize(customer);
				GatewayManager gatewayManager = new GatewayManager();
				Set<GateWay> gateWays = gatewayManager
						.getGateWaysOfCustomer(customer);
				customer.setGateWays(gateWays);
				customer.setStatus(customer.getStatus());
				
			}
		} catch (Exception e) {
			LogUtil.error("Error when retreiving customer by id : "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return customer;
	}
	
	/* *************************************** sumit sumit: SUB USER RESTRICTION ****************************************** */
	/**
	 * @author sumit kumar
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GateWay> getGateWaysAndDeviceOfCustomerForSubUser(User user){
		DBConnection dbc = null;
		List<GateWay> gateWays = new ArrayList<GateWay>();
		List<Status> statusList = new ArrayList<Status>();
		Status status1 = IMonitorUtil.getStatuses().get(Constants.UN_REGISTERED);
		statusList.add(status1);
		Customer customer = user.getCustomer();
		UserManager userManager = new UserManager();
		
		String query = "select "
				+ " g.id, g.macId, s.name, d.generatedDeviceId, d.friendlyName,"
				+ " d.batteryStatus, d.commandParam, la.alertCommand, dt.details, dt.iconFile,"
				+ " l.name, l.iconFile, arm.name, count(r), d.id,d.modelNumber, i.fileName, "
				+ " dc.id, dc.controlNightVision, d.enableStatus, m.id, g.currentMode,"
				+ " d.enableList, d.posIdx, lm.top, lm.leftMap, lm.id, l.id, dc.pantiltControl,"
				+ " c.featuresEnabled, g.gateWayVersion, dc.fanModeValue, dc.acSwing, g.allOnOffStatus"/*Aditya changed for all ON OFF,Last Index is 33 for allOnOffStatus*/  
				+ " from Device as d " + " left join d.deviceType as dt "
				+ " left join d.lastAlert as la " + " left join d.rules as r "
				+ " left join d.make as m " + " left join d.armStatus as arm "
				+ " left join d.location as l " + " left join d.gateWay as g "
				+ " left join d.gateWay.status as s"
				+ " left join d.icon as i "					//added "left join d.icon as i" in from clause
				+ " left join d.deviceConfiguration as dc" 
				+ " left join d.locationMap as lm"
				+ " left join g.customer as c" + " where c.customerId='"
				+ customer.getCustomerId() + "'" + " and s not in(:statusList)"
				+ " group by d" + " order by d.friendlyName";
		
		List<Object[]> objects = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			q.setParameterList("statusList", statusList);
			objects = q.list();
			
			Map<String, GateWay> gateWayMap = new HashMap<String, GateWay>();
			for (Object[] strings : objects) {
				String key = IMonitorUtil.convertToString(strings[0]);
				GateWay gateWay = gateWayMap.get(key);
				if (gateWay == null) {
					gateWay = new GateWay();
					gateWay.setId(Long.parseLong(key));
					gateWay.setMacId(IMonitorUtil.convertToString(strings[1]));
					//sumit start
					gateWay.setCurrentMode(IMonitorUtil.convertToString(strings[21]));
					//sumit end
					Status status = new Status();
					status.setName(IMonitorUtil.convertToString(strings[2]));
					gateWay.setStatus(status);
					gateWay.setGateWayVersion(IMonitorUtil.convertToString(strings[30]));
					Customer Customer=new Customer();
					long featuresEnabled = (Long)strings[29];
					Customer.setFeaturesEnabled(featuresEnabled);
					gateWay.setCustomer(Customer);
					
					/***
					 * Aditya added for all on off feature
					 * */
					
					gateWay.setAllOnOffStatus((Long)strings[33]);
					
					String macid=gateWay.getMacId();
					//bhavya start
					FirmWareManager FirmWareManager=new FirmWareManager();
					Object[] objects1 = (Object[]) FirmWareManager.listLatesestFirmWare(macid);
					
					if(objects1 != null)
					{
						
					gateWay.setFirmwareId(Long.parseLong(IMonitorUtil
							.convertToString(objects1[0])));
					gateWay.setLatestversion(IMonitorUtil.convertToString(objects1[1]));
					}
					

					gateWay.setDevices(new HashSet<Device>());
					gateWayMap.put(key, gateWay);
					gateWays.add(gateWay);
				}
				Device device = new Device();
				long dId = (Long) strings[14];
				device.setId(dId);
				device.setGeneratedDeviceId(IMonitorUtil
						.convertToString(strings[3]));
				device
						.setFriendlyName(IMonitorUtil
								.convertToString(strings[4]));
				device.setBatteryStatus(IMonitorUtil
						.convertToString(strings[5]));
				device
						.setCommandParam(IMonitorUtil
								.convertToString(strings[6]));
				device
						.setModelNumber(IMonitorUtil
								.convertToString(strings[15]));
				
				if(device.getModelNumber().equals("HMD"))
				{
					String gendeviceid=device.getGeneratedDeviceId();
					String alerttype1="HMD_WARNING";
					String alerttype2="HMD_NORMAL";
					String alerttype3="HMD_FAILURE";
				
					ImvgAlertsActionsManager ImvgAlertsActionsManager=new ImvgAlertsActionsManager();
					Object[] objects1 = (Object[]) ImvgAlertsActionsManager.listLatesestHMDAlerts(gendeviceid, alerttype1, alerttype2, alerttype3);
					if(objects1 != null && objects1[0]!= null)
					{
						device.setHMDalertTime(""+(Timestamp)objects1[0]);
						device.setHMDalertstatus(""+(String)objects1[2]);
					}
					else
					{
						device.setHMDalertTime("");
						device.setHMDalertstatus("");
					}
					if(device.getModelNumber().equals("HMD"))
					{
					Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getId());
				
					if(objects2 != null && objects2[2]!= null)
					{
					
						device.setHMDalertValue((""+(String)objects2[2]));
						device.setHMDpowerinfo(""+(String)objects2[4]);
					
					}
					else
					{
						device.setHMDalertValue("");
						device.setHMDpowerinfo("");
					
					}
				}
				}
//				for the icon feature	
				Icon icon = new Icon();
				icon.setFileName(IMonitorUtil.convertToString(strings[16]));
				device.setIcon(icon);
								
				device.setEnableStatus(IMonitorUtil.convertToString(strings[19]));
				device.setEnableList(IMonitorUtil.convertToString(strings[22]));	//for enabling listing of device on home screen.
				device.setPosIdx(Integer.parseInt((IMonitorUtil.convertToString(strings[23]))));	//Re-Ordering Of Device
				LocationMap map=new LocationMap();
				String Id = IMonitorUtil.convertToString(strings[26]);
				if(Id != null && !Id.equals(""))
				{
				map.setId(Integer.parseInt(Id));
				map.setTop(IMonitorUtil.convertToString(strings[24]));
				map.setLeftMap(IMonitorUtil.convertToString(strings[25]));
				}
				device.setLocationMap(map);
				Make make = new Make();
				String makeId = IMonitorUtil.convertToString(strings[20]);
				long makeIdLong = 0;
				if(makeId != null && !makeId.equals(""))
				{
					makeIdLong = Integer.parseInt(makeId);
				}	
				make.setId(makeIdLong);
				device.setMake(make);
					
				int count = Integer.parseInt(IMonitorUtil
						.convertToString(strings[13]));
				if (count > 0) {
					device.setRules(new ArrayList<Rule>());

				}
				AlertType lastAlert = new AlertType();
				lastAlert.setAlertCommand(IMonitorUtil
						.convertToString(strings[7]));
				device.setLastAlert(lastAlert);
				DeviceType deviceType = new DeviceType();
				deviceType.setDetails(IMonitorUtil.convertToString(strings[8]));
				deviceType
						.setIconFile(IMonitorUtil.convertToString(strings[9]));
				device.setDeviceType(deviceType);
				Location location = new Location();
				location.setName(IMonitorUtil.convertToString(strings[10]));
				location.setIconFile(IMonitorUtil.convertToString(strings[11]));
				device.setLocation(location);

				Status armStatus = new Status();
				armStatus.setName(IMonitorUtil.convertToString(strings[12]));
				device.setArmStatus(armStatus);
				
//				Camera Configuration.
				Long dcId =  (Long) strings[17];
				
				if(dcId != null)
				{
					DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
					
					if(device.getModelNumber().contains(Constants.H264Series)){
						H264CameraConfiguration h264CameraConfiguration = (H264CameraConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(h264CameraConfiguration);
					}else if (device.getModelNumber().contains(Constants.RC80Series)){
						CameraConfiguration cameraConfiguration = (CameraConfiguration) Dvconf.getDeviceConfigurationById(dcId);;
						device.setDeviceConfiguration(cameraConfiguration);
					}
					else if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
					{
						acConfiguration acConfiguration = (acConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(acConfiguration);
					}
					else{
						DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
						deviceConfiguration.setId(dcId);
						device.setDeviceConfiguration(deviceConfiguration);
					}
					
//					//bhavya start acthermostat
//					XStream stream = new XStream();
//						
//						if(device.getModelNumber().contains(Constants.ZXT110)){
//							
//						LogUtil.info("device-----------"+stream.toXML(device));
//						}
//						else{
//							DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
//							deviceConfiguration.setId(dcId);
//							device.setDeviceConfiguration(deviceConfiguration);
//							LogUtil.info("device-----------"+stream.toXML(device));
//						}
//						
					
					
					
					/*String fanModeValue = (String) strings[31];
					if(fanModeValue != null){
						acConfiguration acConfiguration = new acConfiguration();
						acConfiguration.setId(dcId);
						acConfiguration.setFanModeValue(fanModeValue);
						device.setDeviceConfiguration(acConfiguration);
					}else{
						DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
						deviceConfiguration.setId(dcId);
						device.setDeviceConfiguration(deviceConfiguration);
					}*/
					
					//bhavya end 
					//Kantharaj Commeted To fixes bug om H264 PT Control option Visibility
					/*String controlNightVision = (String) strings[18];
					String pantiltControl = (String) strings[28];
					if(controlNightVision != null || pantiltControl != null){
						CameraConfiguration cameraConfiguration = new CameraConfiguration();
						cameraConfiguration.setId(dcId);
						cameraConfiguration.setControlNightVision(controlNightVision);
						cameraConfiguration.setPantiltControl(pantiltControl);
						device.setDeviceConfiguration(cameraConfiguration);
					}else{
						DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
						deviceConfiguration.setId(dcId);
						device.setDeviceConfiguration(deviceConfiguration);
					}*/
				}
				
				//Filter devices as per the subUserDeviceAccess entry
				List<subUserDeviceAccess> accessibleDeviceList = userManager.listDevicesForSubuser(user.getId());
				for(subUserDeviceAccess su : accessibleDeviceList){
					long did = su.getDevice().getId();
					//LogUtil.info("Accessible Device ID : "+did+", Actual Device ID : "+device.getId());
					if(did == device.getId()){
						gateWay.getDevices().add(device);
					}
				}
				
				
				//gateWay.getDevices().add(device);
			}
			String excludeGateWays = "";
			if (gateWays.size() > 0) {
				excludeGateWays = " and g not in (:varList)";
			}
			query = "select g.id, g.macId, s.name from "
					+ " GateWay as g join g.status as s"
					+ " where g.status=s.id and s.id not in(" + status1.getId()
					+ ")" + "and g.customer=" + customer.getId()
					+ excludeGateWays + "";
			
			dbc = new DBConnection();
			Session session1 = dbc.openConnection();
			q = session1.createQuery(query);
			if (gateWays.size() > 0) {
				// q.setParameterList("statusList",statusList);
				q.setParameterList("varList", gateWays);
			}
			List<Object[]> newImvgs = q.list();
			for (Object[] objects2 : newImvgs) {
				GateWay gateWay = new GateWay();
				gateWay.setId(Long.parseLong(IMonitorUtil
						.convertToString(objects2[0])));
				gateWay.setMacId(IMonitorUtil.convertToString(objects2[1]));
				Status status = new Status();
				status.setName(IMonitorUtil.convertToString(objects2[2]));
				gateWay.setStatus(status);
				gateWays.add(gateWay);
			}
		} catch (Exception ex) {
			LogUtil.info("Caught Exception while retrieving devices for Sub User: ", ex);
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return gateWays;
	}
	/* *************************************** sumit end: SUB USER RESTRICTION ****************************************** */
	
	@SuppressWarnings("unchecked")
	public List<GateWay> getGateWaysAndDeviceOfCustomer(Customer customer) {

		DBConnection dbc = null;
		List<GateWay> gateWays = new ArrayList<GateWay>();
		// Using HQL for performance Optimization.
		List<Status> statusList = new ArrayList<Status>();
		Status status1 = IMonitorUtil.getStatuses()
				.get(Constants.UN_REGISTERED);
		statusList.add(status1);
		String query = "select "
				+ " g.id, g.macId "
				+ " , s.name "
				+ " , d.generatedDeviceId, d.friendlyName, d.batteryStatus, d.commandParam, la.alertCommand"
				+ " , dt.details, dt.iconFile "
				+ " , l.name, l.iconFile, arm.name, count(r), d.id,d.modelNumber, i.fileName, "	//added a "i.fileName" in the select clause

//				+ " dc.id, dc.controlNightVision, d.enableStatus, m.id " 
//sumit start: Re-Ordering Of Devices.
//				+ " dc.id, dc.controlNightVision, d.enableStatus, m.id, g.currentMode, d.enableList "/*17-21 + 22*/  //sumit
//	parishod commented:	+ " dc.id, dc.controlNightVision, d.enableStatus, m.id, g.currentMode, d.enableList, d.posIdx ,lm.top,lm.leftMap,lm.id,l.id"/*posIdx = 23*/  
				+ " dc.id, dc.controlNightVision, d.enableStatus, m.id, g.currentMode, d.enableList, d.posIdx ,lm.top,lm.leftMap,lm.id,l.id,dc.pantiltControl,c.featuresEnabled,g.gateWayVersion,dc.fanModeValue,dc.acSwing,g.allOnOffStatus,d.switchType,d.deviceId,g.name"/*Aditya changed for all ON OFF,Last Index is 33 for allOnOffStatus*/  
//sumit end: Re-Ordering Of Devices.				
				+ " from Device as d " + " left join d.deviceType as dt "
				+ " left join d.lastAlert as la " + " left join d.rules as r "
				+ " left join d.make as m " + " left join d.armStatus as arm "
				+ " left join d.location as l " + " left join d.gateWay as g "
				+ " left join d.gateWay.status as s"
				+ " left join d.icon as i "					//added "left join d.icon as i" in from clause
				+ " left join d.deviceConfiguration as dc" 
				+ " left join d.locationMap as lm"
				+ " left join g.customer as c" + " where c.customerId='"
				+ customer.getCustomerId() + "'" + " and s not in(:statusList)"
				+ " group by d" + " order by d.friendlyName";
		
		
		List<Object[]> objects = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			q.setParameterList("statusList", statusList);
			objects = q.list();
			
			Map<String, GateWay> gateWayMap = new HashMap<String, GateWay>();
			for (Object[] strings : objects) {
				String key = IMonitorUtil.convertToString(strings[0]);
				
				GateWay gateWay = gateWayMap.get(key);
				if (gateWay == null) {
					gateWay = new GateWay();
					gateWay.setId(Long.parseLong(key));
					gateWay.setMacId(IMonitorUtil.convertToString(strings[1]));
					//sumit start
					gateWay.setCurrentMode(IMonitorUtil.convertToString(strings[21]));
					//sumit end
					Status status = new Status();
					status.setName(IMonitorUtil.convertToString(strings[2]));
					gateWay.setStatus(status);
					gateWay.setGateWayVersion(IMonitorUtil.convertToString(strings[30]));
					Customer Customer=new Customer();
					long featuresEnabled = (Long)strings[29];
					Customer.setFeaturesEnabled(featuresEnabled);
					gateWay.setCustomer(Customer);
					
					/***
					 * Aditya added for all on off feature
					 * */
					
					gateWay.setAllOnOffStatus((Long)strings[33]);
					//3gp
					gateWay.setName(IMonitorUtil.convertToString(strings[36]));
					
					
					String macid=gateWay.getMacId();
					//bhavya start
					FirmWareManager FirmWareManager=new FirmWareManager();
					Object[] objects1 = (Object[]) FirmWareManager.listLatesestFirmWare(macid);
					
					if(objects1 != null)
					{
						
					gateWay.setFirmwareId(Long.parseLong(IMonitorUtil
							.convertToString(objects1[0])));
					gateWay.setLatestversion(IMonitorUtil.convertToString(objects1[1]));
					}
					

					gateWay.setDevices(new HashSet<Device>());
					gateWayMap.put(key, gateWay);
					gateWays.add(gateWay);
				}
				Device device = new Device();
				long dId = (Long) strings[14];
				device.setId(dId);
				device.setGeneratedDeviceId(IMonitorUtil
						.convertToString(strings[3]));
				device
						.setFriendlyName(IMonitorUtil
								.convertToString(strings[4]));
				device.setBatteryStatus(IMonitorUtil
						.convertToString(strings[5]));
				device
						.setCommandParam(IMonitorUtil
								.convertToString(strings[6]));
				device
						.setModelNumber(IMonitorUtil
								.convertToString(strings[15]));
				
				if(strings[34] != null){
					
					device.setSwitchType((Long)strings[34]);
				}
				device.setDeviceId(IMonitorUtil.convertToString(strings[35]));
				if(device.getModelNumber().equals("HMD"))
				{
					String gendeviceid=device.getGeneratedDeviceId();
					String alerttype1="HMD_WARNING";
					String alerttype2="HMD_NORMAL";
					String alerttype3="HMD_FAILURE";
				
					ImvgAlertsActionsManager ImvgAlertsActionsManager=new ImvgAlertsActionsManager();
					Object[] objects1 = (Object[]) ImvgAlertsActionsManager.listLatesestHMDAlerts(gendeviceid, alerttype1, alerttype2, alerttype3);
					if(objects1 != null && objects1[0]!= null)
					{
						device.setHMDalertTime(""+(Timestamp)objects1[0]);
						device.setHMDalertstatus(""+(String)objects1[2]);
					}
					else
					{
						device.setHMDalertTime("");
						device.setHMDalertstatus("");
					}
					if(device.getModelNumber().equals("HMD"))
					{
					Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getId());
				
					if(objects2 != null && objects2[2]!= null)
					{
					
						device.setHMDalertValue((""+(String)objects2[2]));
						device.setHMDpowerinfo(""+(String)objects2[4]);
					
					}
					else
					{
						device.setHMDalertValue("");
						device.setHMDpowerinfo("");
					
					}
				}
				}
//				for the icon feature	
				Icon icon = new Icon();
				icon.setFileName(IMonitorUtil.convertToString(strings[16]));
				device.setIcon(icon);
								
				device.setEnableStatus(IMonitorUtil.convertToString(strings[19]));
				device.setEnableList(IMonitorUtil.convertToString(strings[22]));	//for enabling listing of device on home screen.
				device.setPosIdx(Integer.parseInt((IMonitorUtil.convertToString(strings[23]))));	//Re-Ordering Of Device
				LocationMap map=new LocationMap();
				String Id = IMonitorUtil.convertToString(strings[26]);
				if(Id != null && !Id.equals(""))
				{
				map.setId(Integer.parseInt(Id));
				map.setTop(IMonitorUtil.convertToString(strings[24]));
				map.setLeftMap(IMonitorUtil.convertToString(strings[25]));
				}
				device.setLocationMap(map);
				Make make = new Make();
				String makeId = IMonitorUtil.convertToString(strings[20]);
				long makeIdLong = 0;
				if(makeId != null && !makeId.equals(""))
				{
					makeIdLong = Integer.parseInt(makeId);
				}	
				make.setId(makeIdLong);
				device.setMake(make);
					
				int count = Integer.parseInt(IMonitorUtil
						.convertToString(strings[13]));
				if (count > 0) {
					device.setRules(new ArrayList<Rule>());

				}
				AlertType lastAlert = new AlertType();
				lastAlert.setAlertCommand(IMonitorUtil
						.convertToString(strings[7]));
				device.setLastAlert(lastAlert);
				DeviceType deviceType = new DeviceType();
				deviceType.setDetails(IMonitorUtil.convertToString(strings[8]));
				deviceType
						.setIconFile(IMonitorUtil.convertToString(strings[9]));
				device.setDeviceType(deviceType);
				Location location = new Location();
				location.setName(IMonitorUtil.convertToString(strings[10]));
				location.setIconFile(IMonitorUtil.convertToString(strings[11]));
				device.setLocation(location);

				Status armStatus = new Status();
				armStatus.setName(IMonitorUtil.convertToString(strings[12]));
				device.setArmStatus(armStatus);
				
				
				//Diaken VIA DeviceType
				Device device2=null;
				Device device3=null;
				if(deviceType.getDetails().equals("VIA"))
				{
					//LogUtil.info("Devicetype is VIA");
					String query1="select m.id,m.deviceId,m.HAIF_Id,m.FriendlyName,m.Location,m.Connected_Units,l.name,l.iconFile,dt.details,dt.iconFile,m.enableList from Modbus_Slave as m " +
							"left join location as l on m.Location=l.id left join devicetype as dt on m.deviceType=dt.id where m.deviceId="+device.getId();
					//LogUtil.info("Query :-"+query1);
					Session session1 = dbc.openConnection();
					Query q1 = session1.createSQLQuery(query1);
					objects = q1.list();
					//LogUtil.info("Modbus Slave list "+new XStream().toXML(objects));
					if (!objects.isEmpty()) 
					{
						//LogUtil.info("Modbus list not empty");
					for (Object[] slaves : objects)
					{
						//LogUtil.info("Modbus Slave object "+new XStream().toXML(slaves));
						BigInteger BigId= (BigInteger) slaves[0];
						long SlaveId=BigId.longValue();
						//LogUtil.info("Slave Id is "+SlaveId);
						
						device2=new Device();
						device2.setId(SlaveId);
						//device2.setGeneratedDeviceId(IMonitorUtil.convertToString(slaves[3]));
						device2.setFriendlyName(IMonitorUtil.convertToString(slaves[3]));
						device2.setGeneratedDeviceId(IMonitorUtil.convertToString(slaves[2]));
						//device2.setBatteryStatus(IMonitorUtil.convertToString(slaves[5]));
						//device2.setCommandParam(IMonitorUtil.convertToString(slaves[6]));
						//device2.setModelNumber(IMonitorUtil.convertToString(slaves[15]));
						device2.setDeviceId(IMonitorUtil.convertToString(slaves[1]));
						Icon icon1 = new Icon();
						icon1.setFileName("/imonitor/resources/images/device/pm.png");
						//icon.setFileName(IMonitorUtil.convertToString(slaves[16]));
						device2.setIcon(icon1);			
						//device2.setEnableStatus(IMonitorUtil.convertToString(slaves[19]));
						//device2.setEnableList(IMonitorUtil.convertToString(slaves[22]));	//for enabling listing of device on home screen.
						//device2.setPosIdx(Integer.parseInt((IMonitorUtil.convertToString(slaves[23]))));	//Re-Ordering Of Device
						AlertType lastAlert1 = new AlertType();
						//lastAlert.setAlertCommand(IMonitorUtil
							//	.convertToString(slaves[7]));
						device2.setLastAlert(lastAlert1);
						DeviceType deviceType1 = new DeviceType();
						deviceType1.setDetails(IMonitorUtil.convertToString(slaves[8]));
						deviceType1.setIconFile(IMonitorUtil.convertToString(IMonitorUtil.convertToString(slaves[9])));
						device2.setDeviceType(deviceType1);
						Location location1 = new Location();
						location1.setName(IMonitorUtil.convertToString(slaves[6]));
						location1.setIconFile(IMonitorUtil.convertToString(slaves[7]));
						device2.setLocation(location1);

						Status armStatus1 = new Status();
						//armStatus.setName(IMonitorUtil.convertToString(strings[12]));
						device2.setArmStatus(armStatus1);
						device2.setEnableList(IMonitorUtil.convertToString(slaves[10]));
						gateWay.getDevices().add(device2);
						//LogUtil.info("Step 1 ");
						
						/*String query2="select iud.id,iud.SlaveId,iud.Unit_Id,iud.FriendlyName,iud.Location,l.name,l.iconFile,dt.details,dt.iconFile,iud.enableStatus,iud.enableList,iud.DeviceConfiguration from Indoor_Unit_Devices as iud " +
								" left join location as l on iud.Location=l.id left join devicetype as dt on iud.deviceType=dt.id where iud.SlaveId="+SlaveId;
						//LogUtil.info("Query  to fetch indoor units :-"+query2);
						Session session2 = dbc.openConnection();
						Query q2 = session2.createSQLQuery(query2);
						objects = q2.list();
						//LogUtil.info("Indoor Units List "+new XStream().toXML(objects));
						
						List<Device> devices=new ArrayList<Device>();
						for (Object[] units : objects)
						{
							//LogUtil.info("Loop Start");
							//LogUtil.info("Indoor unit -->"+new XStream().toXML(units));
							device3=new Device();
							BigInteger BigId1= (BigInteger) units[0];
							long unitId=BigId1.longValue();
							device3.setId(unitId);
							device3.setFriendlyName(IMonitorUtil.convertToString(units[3]));
							device3.setDeviceId(IMonitorUtil.convertToString(units[1]));
							device3.setGeneratedDeviceId(IMonitorUtil.convertToString(units[2]));
							device3.setEnableStatus(IMonitorUtil.convertToString(units[9]));
							Icon icon2 = new Icon();
							icon2.setFileName("/imonitor/resources/images/device/airconditioner.png");
							device3.setIcon(icon2);			
							AlertType lastAlert2 = new AlertType();
							device3.setLastAlert(lastAlert2);
							DeviceType deviceType2 = new DeviceType();
							deviceType2.setDetails(IMonitorUtil.convertToString(units[7]));
							deviceType2.setIconFile(IMonitorUtil.convertToString(IMonitorUtil.convertToString(units[8])));
							device3.setDeviceType(deviceType2);
							Location location2 = new Location();
							location2.setName(IMonitorUtil.convertToString(units[5]));
							location2.setIconFile(IMonitorUtil.convertToString(units[6]));
							device3.setLocation(location2);
							Status armStatus2 = new Status();
							device3.setArmStatus(armStatus2);
							device3.setEnableList(IMonitorUtil.convertToString(units[10]));
							//device3.setDeviceConfiguration(null);
							BigInteger BigId2= (BigInteger) units[11];
							//LogUtil.info("Big int "+new XStream().toXML(BigId2));
							try 
							{
								if (BigId2 != null) 
								{
									long deviceConfigId=BigId2.longValue();
									//LogUtil.info("Device Config Id =="+deviceConfigId);
									
									//IndoorUnitConfiguration unitConfiguration=deviceManager.getIndoorUnitConfigurationById(deviceConfigId);
									DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
									IndoorUnitConfiguration unitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(deviceConfigId);
									//LogUtil.info("Indoor Unit Configureation -->"+new XStream().toXML(unitConfiguration));
									//device3.setDeviceConfiguration(unitConfiguration);
									//LogUtil.info("Device with Config "+new XStream().toXML(device3));
								}
							} catch (Exception e) 
							{
								e.printStackTrace();
								LogUtil.info("Config exception "+e.getMessage());
							}
							
							//Adding device3 to gateway list
							
							gateWay.getDevices().add(device3);
							
							
							
							
							//LogUtil.info("Loop End");
						}*/
						//LogUtil.info("Gateway devices to display -->"+new XStream().toXML(gateWay.getDevices()));
						//gateWay.getDevices().addAll(devices);
					}
					}
				}
				
				
				
				
//				Camera Configuration.
				Long dcId =  (Long) strings[17];
				
				if(dcId != null)
				{
					DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
					
					if(device.getModelNumber().contains(Constants.H264Series)){
						H264CameraConfiguration h264CameraConfiguration = (H264CameraConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(h264CameraConfiguration);
					}else if (device.getModelNumber().contains(Constants.RC80Series)){
						CameraConfiguration cameraConfiguration = (CameraConfiguration) Dvconf.getDeviceConfigurationById(dcId);;
						device.setDeviceConfiguration(cameraConfiguration);
					}
					else if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
					{
						acConfiguration acConfiguration = (acConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(acConfiguration);
					}
					else if(device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
					{
						IndoorUnitConfiguration indoorUnitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(indoorUnitConfiguration);
						if(indoorUnitConfiguration.getConnectStatus() == 0){
							continue;
						}
					}
					else{
						DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
						deviceConfiguration.setId(dcId);
						device.setDeviceConfiguration(deviceConfiguration);
					}
	
				}
				
				gateWay.getDevices().add(device);
			}
			
			String excludeGateWays = "";
			if (gateWays.size() > 0) {
				excludeGateWays = " and g not in (:varList)";
				
			}
			query = "select g.id, g.macId, s.name from "
					+ " GateWay as g join g.status as s"
					+ " where g.status=s.id and s.id not in(" + status1.getId()
					+ ")" + "and g.customer=" + customer.getId()
					+ excludeGateWays + "";
			
			try {
				dbc = new DBConnection();
				session = dbc.openConnection();
			q = session.createQuery(query);
			if (gateWays.size() > 0) {
				// q.setParameterList("statusList",statusList);
				q.setParameterList("varList", gateWays);
			}
			List<Object[]> newImvgs = q.list();
			for (Object[] objects2 : newImvgs) {
				GateWay gateWay = new GateWay();
				gateWay.setId(Long.parseLong(IMonitorUtil
						.convertToString(objects2[0])));
				gateWay.setMacId(IMonitorUtil.convertToString(objects2[1]));
				Status status = new Status();
				status.setName(IMonitorUtil.convertToString(objects2[2]));
				gateWay.setStatus(status);
				gateWays.add(gateWay);
			}
			}catch (Exception ex) {
				ex.printStackTrace();
			}finally {
				dbc.closeConnection();
			}
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}

		return gateWays;
	}

	@SuppressWarnings("unchecked")
	public Customer getCustomerByGateWay(GateWay gateWay) {
		List<Customer> customers = daoManager.list("gateWays", "macId", gateWay
				.getMacId(), Customer.class);
		Customer customer = customers.get(0);
		return customer;
	}

	public Customer getCustomerWithUsersById(long id) {
		Customer customer = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			customer = (Customer) session.get(Customer.class, id);
		} catch (Exception e) {
			LogUtil.error("Error when retreiving customers with users : "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return customer;
	}

	@SuppressWarnings("unchecked")
	public String saveCustomerWithDefaultLocation(Customer customer) {
		String result = "success";
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Transaction tx = session.beginTransaction();
			// 1. List all the gateways.
			DBConnection dbConnection = new DBConnection();
			Session sess = dbConnection.getSession();
			Criteria crt = sess.createCriteria(GateWay.class);
			Set<GateWay> incomingGateWays = customer.getGateWays();
			String[] macIds = new String[incomingGateWays.size()];
			List<GateWay> gateWays = new ArrayList<GateWay>(incomingGateWays);
			for (int i = 0; i < gateWays.size(); i++) {
				macIds[i] = gateWays.get(i).getMacId();
			}
			Criterion restriction = Restrictions.in("macId", macIds);
			crt.add(restriction);
			// Reusing the variable
			gateWays = crt.list();
			if (gateWays.size() < 1) {
				result = "Failure~None of the macId provided by you is correct !!!";
				throw new Exception(
						"Gateways provided to create customer are all invalid.");
			}
			Agent agent = gateWays.get(0).getAgent();
			if (agent != null) {
				// 2. Save the customer
				// Reusing the variable.
				incomingGateWays = new HashSet<GateWay>(gateWays);
				customer.setGateWays(incomingGateWays);
				session.save(customer);
				// Getting an agent.
				// Creating the folder in the ftp location.
				/*
				 * FTPClient ftpClient = new FTPClient();
				 * ftpClient.connect(agent.getFtpIp(), agent.getFtpPort());
				 * boolean login = ftpClient.login(agent.getFtpUserName(),
				 * agent.getFtpPassword()); if(login){
				 * ftpClient.makeDirectory(customer.getCustomerId()); }
				 */
//Naveen commneted this to work with imsserver
			/*	String customerId = customer.getCustomerId();
				String imagesDir = customerId
						+ "/"
						+ ControllerProperties.getProperties().get(
								Constants.IMAGES_DIR);
				String streamsDir = customerId
						+ "/"
						+ ControllerProperties.getProperties().get(
								Constants.STREAMS_DIR);
				String logsDir = customerId
						+ "/"
						+ ControllerProperties.getProperties().get(
								Constants.LOG_DIR);
				// ********************** sumit start: Create Locations Folder for user to upload images for his location **********************
				String locationsDir = imagesDir
						+ "/"
						//+ "/locations";
						+ ControllerProperties.getProperties().get(
								Constants.UPLOADED_LOC_IMAGES_DIR);
				// ************************* sumit end: Create Locations Folder for user to upload images for his location *********************
				FTPUtil.createFolders(agent.getFtpIp(), agent.getFtpPort(),
						agent.getFtpUserName(), agent.getFtpPassword(),
						customerId, imagesDir, streamsDir,logsDir,locationsDir);*/
				
			}
			// 3. Create Default Room.
			//LogUtil.info("DIY feature add pre defined rooms start");
			Location location = new Location();
			location.setName("Default Room");
			location.setDetails("Default Room");
			location.setCustomer(customer);
			session.save(location);
			
			//DIY feature add pre defined rooms start
			Location location1 = new Location();
			location1.setName("Living Room");
			location1.setDetails("Living Room");
			location1.setCustomer(customer);
			session.save(location1);
			
			Location location2 = new Location();
			location2.setName("Bedroom");
			location2.setDetails("Bedroom");
			location2.setCustomer(customer);
			session.save(location2);
			
			Location location3 = new Location();
			location3.setName("Kitchen");
			location3.setDetails("Kitchen");
			location3.setCustomer(customer);
			session.save(location3);
		//	LogUtil.info("DIY feature add pre defined rooms end");
			//DIY feature add pre defined rooms end
			
			for (GateWay gateWay : gateWays) {
				gateWay.setCustomer(customer);
				session.update(gateWay);
			}
			tx.commit();
			
			//create dummy device ENERGY_MONITOR
			DeviceManager devicemanager=new DeviceManager();
			Boolean createdevice=false;
			
			if(!(((customer.getFeaturesEnabled())&4)==0))
			{
				
			for (GateWay gateWay : gateWays) {
				
				createdevice = devicemanager.createSystemDevicesFordashboard(gateWay);
			}
			
			}
			else
			{
				
					
				for (GateWay gateWay : gateWays) {
					
					createdevice = devicemanager.deleteSystemDevicesFordashboard(gateWay);
				}
			}
			
			
			
			
		} catch (Exception e) {
			LogUtil.error("Error when saving customer : " + e.getMessage());
			e.printStackTrace();
			result = "Failure ~" + e.getMessage();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
		return result;
	}
	
	// ***************************************************************** sumit begin **********************************************************
	public List<Device> getAllDevicesOfCustomer(Customer customer) {
		
		DBConnection dbc = null;
		List<Device> devices = new ArrayList<Device>();
		
		String query = "select"
				+ " d.id, d.generatedDeviceId, d.friendlyName, d.mode"
				+ " , i.fileName"
				//sumit edited to avoid listing Virtual devices on EDIT MODE page
				//+ " , g.id, g.macId, g.delayHome, g.delayStay, g.delayAway,"
				//+ " , g.id, g.macId, g.delayHome, g.delayStay, g.delayAway, d.deviceId" //sumit#10
				//+ " , g.id, g.macId, g.delayHome, g.delayStay, g.delayAway, d.deviceId, dt.id" //sumit#11
				+ " , g.id, g.macId, g.delayHome, g.delayStay, g.delayAway, d.deviceId, dt.id, l.name, dt.name" //sumit#13
				+ " from Device as d" 
				+ " left join d.gateWay as g"
				+ " left join d.icon as i"
				+ " left join g.customer as c" 
				+ " left join d.deviceType as dt"
				+ " left join d.location as l"
				+ " where c.customerId='"+ customer.getCustomerId() + "'" 
				+ " group by d" + " order by d.id";
		List<Object[]> objects = null;
		try{
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
			for (Object[] strings : objects) {
				Device device = new Device();
				device.setId(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
				device.setGeneratedDeviceId(IMonitorUtil.convertToString(strings[1]));
				device.setFriendlyName(IMonitorUtil.convertToString(strings[2]));
				device.setMode (IMonitorUtil.convertToString(strings[3]));
				//sumit start
				device.setDeviceId(IMonitorUtil.convertToString(strings[10]));
				//device.setSubUserAccess(IMonitorUtil.convertToString(strings[12]));//sumit added: Sub-User Restriction
				
				Location location = new Location();
				location.setName(IMonitorUtil.convertToString(strings[12]));
				device.setLocation(location);
				
				DeviceType deviceType = new DeviceType();
				deviceType.setId(Long.parseLong(IMonitorUtil.convertToString(strings[11])));
				deviceType.setName(IMonitorUtil.convertToString(strings[13]));
				device.setDeviceType(deviceType);
				//sumit end
				Icon icon = new Icon();
				icon.setFileName(IMonitorUtil.convertToString(strings[4]));
				device.setIcon(icon);
				
				GateWay gateway = new GateWay();
				gateway.setId(Long.parseLong(IMonitorUtil.convertToString(strings[5])));
				gateway.setMacId(IMonitorUtil.convertToString(strings[6]));
				gateway.setDelayHome(IMonitorUtil.convertToString(strings[7]));
				gateway.setDelayStay(IMonitorUtil.convertToString(strings[8]));
				gateway.setDelayAway(IMonitorUtil.convertToString(strings[9]));
				device.setGateWay(gateway);
				
				devices.add(device);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return devices;
	}

	// ****************************************************************** sumit end ***********************************************************

	/***************************************************************************
	 * Project Name : CMS
	 * Project Code : CMS
	 * Function : Retrieve Gateway details based on customer id
	 * Desc: Below code retrieves Gateway details based on customer id
	 * Developed by: Kantharaj
	 * Change History : Altered query and appended corresponding member variable
	 * 					to hold device position.
	 * Changes done by : Sumit Kumar Date: June 28, 2013.
	 * Date: July 01, 2013
	 * ************************************************************************/
	@SuppressWarnings("unchecked")
	public List<GateWay> getGateWaysAndDeviceOfCustomerByCustomerId(
			String customerId) {

		
		DBConnection dbc = null;
		List<GateWay> gateWays = new ArrayList<GateWay>();
		// Using HQL for performance Optimization.

		String query = "select "
				+ " g.id, g.macId, g.localIp "
				+ " , s.name "
				+ " , d.generatedDeviceId, d.friendlyName, d.batteryStatus, d.commandParam, la.alertCommand"
				+ " , dt.details, dt.iconFile " 
				+ " , l.name, l.iconFile"
				//sumit begin
				//+ " ,ast.id, ast.name, d.enableStatus"
				//+ " ,ast.id, ast.name, d.enableStatus, i.fileName, d.modelNumber " 
				+ " ,ast.id, ast.name, d.enableStatus, i.fileName, d.modelNumber, d.enableList, g.currentMode, lm.top, lm.leftMap, lm.id ,dc.id "
				//for enable/disable listing of devices on MID.
				+ " , d.posIdx ,g.allOnOffStatus" //for device position in the list for a particular location
				+ " , d.id,g.gateWayVersion"	//#26 sumit added : required for filtering device for MID for sub users 
				//sumit end
				+ " from Device as d " + " left join d.deviceType as dt "
				+ " left join d.lastAlert as la "
				+ " left join d.location as l " + " left join d.gateWay as g "
				+ " left join d.gateWay.status as s "
				+ " left join d.armStatus as ast"
				+ " left join d.locationMap as lm"
				//sumit begin
				+ " left join d.icon as i"
				+ " left join d.deviceConfiguration as dc"
				//sumit end
				+ " left join g.customer as c" + " where c.customerId='"
				+ customerId + "'" + "";

		List<Object[]> objects = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
			Map<String, GateWay> gateWayMap = new HashMap<String, GateWay>();
			for (Object[] strings : objects) {
				String key = IMonitorUtil.convertToString(strings[0]);
				GateWay gateWay = gateWayMap.get(key);
				if (gateWay == null) {
					gateWay = new GateWay();
					gateWay.setId(Long.parseLong(key));
					gateWay.setMacId(IMonitorUtil.convertToString(strings[1]));
					String localIp = (String) strings[2];
					gateWay.setLocalIp(localIp);
					gateWay.setCurrentMode(IMonitorUtil.convertToString(strings[19]));
					Status status = new Status();
					status.setName(IMonitorUtil.convertToString(strings[3]));
					gateWay.setStatus(status);
					gateWay.setDevices(new HashSet<Device>());
					/**Aditya Added for get status of All on OFF of gateway**/
					gateWay.setAllOnOffStatus((Long) strings[25]);
					gateWay.setGateWayVersion(IMonitorUtil.convertToString(strings[27]));
					gateWayMap.put(key, gateWay);
					gateWays.add(gateWay);
					
				}
				Device device = new Device();
				// sumit start : Jan 24, 2014 for Sub User restriction on MID
				device.setId(Long.parseLong(IMonitorUtil.convertToString(strings[26])));
				//sumit end
				device.setGeneratedDeviceId(IMonitorUtil
						.convertToString(strings[4]));
				device
						.setFriendlyName(IMonitorUtil
								.convertToString(strings[5]));
				device.setBatteryStatus(IMonitorUtil
						.convertToString(strings[6]));
				device
						.setCommandParam(IMonitorUtil
								.convertToString(strings[7]));
				AlertType lastAlert = new AlertType();
				lastAlert.setAlertCommand(IMonitorUtil
						.convertToString(strings[8]));
				device.setLastAlert(lastAlert);
				DeviceType deviceType = new DeviceType();
				deviceType.setDetails(IMonitorUtil.convertToString(strings[9]));
				deviceType.setIconFile(IMonitorUtil
						.convertToString(strings[10]));
				device.setDeviceType(deviceType);
				Location location = new Location();
				location.setName(IMonitorUtil.convertToString(strings[11]));
				location.setIconFile(IMonitorUtil.convertToString(strings[12]));
				device.setLocation(location);

				Status armStatus = null;
				Long armStatusId = (Long) strings[13];
				if (armStatusId != null) {
					armStatus = new Status();
					String armStatusName = (String) strings[14];
					armStatus.setId(armStatusId);
					armStatus.setName(armStatusName);
				} else {
					armStatus = IMonitorUtil.getStatuses()
							.get(Constants.DISARM);
				}
				device.setArmStatus(armStatus);

				String enableS = (String) strings[15];
				device.setEnableStatus(enableS);
				
//sumit begin	
				Icon icon = new Icon();
				icon.setFileName(IMonitorUtil.convertToString(strings[16]));
				device.setIcon(icon);
				String modelNumber = (String) strings[17];
				device.setModelNumber(modelNumber);
				
				device.setEnableList(IMonitorUtil.convertToString(strings[18]));
				//LogUtil.info("SK1");
				device.setPosIdx(Long.parseLong(IMonitorUtil.convertToString(strings[24])));
				//LogUtil.info("SK2");
//sumit end			
				LocationMap map=new LocationMap();
				String Id = IMonitorUtil.convertToString(strings[22]);
				if(Id != null && !Id.equals(""))
				{
				map.setId(Integer.parseInt(Id));
				//String top=IMonitorUtil.convertToString(strings[20]);
				//String left=IMonitorUtil.convertToString(strings[21]);
				
				map.setTop(IMonitorUtil.convertToString(strings[20]));
				map.setLeftMap(IMonitorUtil.convertToString(strings[21]));
				}
				
				Long dcId =  (Long) strings[23];
				
				if(dcId != null)
				{
					DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
					if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
					{
						
						acConfiguration acConfiguration = (acConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						
						device.setDeviceConfiguration(acConfiguration);
					}	
					else if(device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
					{
						IndoorUnitConfiguration indoorUnitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(indoorUnitConfiguration);
						if(indoorUnitConfiguration.getConnectStatus() == 0){
							continue;
						}
					}
					else{
						DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
						deviceConfiguration.setId(dcId);
						device.setDeviceConfiguration(deviceConfiguration);
					}
				}

				device.setLocationMap(map);
				
				gateWay.getDevices().add(device);
			}
			String excludeGateWays = "";
			if (gateWays.size() > 0) {
				excludeGateWays = " and g not in (:varList)";
			}
			query = "select g.id, g.macId, s.name from "
					+ " GateWay as g join g.status as s"
					+ " join g.customer as c" + " where g.status=s.id "
					+ " and g.customer=c.id" + " and c.customerId='"
					+ customerId + "'" + excludeGateWays + "";
			q = session.createQuery(query);
			if (gateWays.size() > 0) {
				q.setParameterList("varList", gateWays);
			}
			List<Object[]> newImvgs = q.list();
			for (Object[] objects2 : newImvgs) {
				GateWay gateWay = new GateWay();
				gateWay.setId(Long.parseLong(IMonitorUtil
						.convertToString(objects2[0])));
				gateWay.setMacId(IMonitorUtil.convertToString(objects2[1]));
				Status status = new Status();
				status.setName(IMonitorUtil.convertToString(objects2[2]));
				gateWay.setStatus(status);
				gateWays.add(gateWay);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}

		return gateWays;
	}

	@SuppressWarnings("unchecked")
	public Customer retrieveCustomerDeviceAlertAndActionsAndNotificationProfiles(
			String customerId) {
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		String query = "select g.id,g.macId," 
				+ " d.id, d.friendlyName,"
				//+ " act.id, act.name," + " at.id, at.name from Customer as c"
				//+ " act.id, act.name," + " at.id, at.name, d.modelNumber from Customer as c"
				+ " act.id, act.name," + " at.id, at.name, d.modelNumber,dt.name, at.details, act.details, d.generatedDeviceId ,c.featuresEnabled ,m.id,g.name from Customer as c" //vibhu added dt.name, at.details, act.details(9,10,11)
				+ " left join c.gateWays as g" + " left join g.devices as d"
				+ " left join d.deviceType as dt"
				+ " left join d.make as m "
				+ " left join dt.alertTypes as at"
				+ " left join dt.actionTypes as act" + " where c.customerId='"
				+ customerId + "'";
		List<Object[]> detailsArray = null;
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			detailsArray = q.list();
			
			Set<GateWay> gateWays = new HashSet<GateWay>();
			for (Object[] details : detailsArray) {
				Long gateWayId = (Long) details[0];
				String macId = IMonitorUtil.convertToString(details[1]);
				//3gp
				String gatewayName = IMonitorUtil.convertToString(details[15]);
				Long deviceId = (Long) details[2];
				if (deviceId == null) {
					continue;
				}
				String friendlyName = IMonitorUtil.convertToString(details[3]);
				Long actionTypeId = (Long) details[4];
				String actionTypeName = IMonitorUtil
						.convertToString(details[5]);
				Long alertTypeId = (Long) details[6];
				String alertTypeName = IMonitorUtil.convertToString(details[7]);
				String modelNumber =  IMonitorUtil.convertToString(details[8]);		//sumit: getting modelNumber based for ZXT120
				String deviceTypeName = IMonitorUtil.convertToString(details[9]); //vibhu added
				String alertTypeDetails = IMonitorUtil.convertToString(details[10]); //vibhu added
				String actionTypeDetails = IMonitorUtil.convertToString(details[11]); //vibhu added
                                String generatedDeviceId = (String)details[12];//pari added to get device type name
               long FeatureEnabled = (Long)details[13];
              // long makes = (Long)details[14];
			//parishod getting make id for the device	
               Make make = new Make();
				String makeId = IMonitorUtil.convertToString(details[14]);
				long makeIdLong = 0;
				if(makeId != null && !makeId.equals(""))
				{
					makeIdLong = Integer.parseInt(makeId);
				}	
				make.setId(makeIdLong);
               //parishod end
               //LogUtil.info("make value of device="+make);
               
				CameraConfiguration cameraConfig = null;
				H264CameraConfiguration cameraConfigH264 = null;
				if(deviceTypeName.equals(Constants.IP_CAMERA) && generatedDeviceId != null)
				{
					
					String query1 = "select d.deviceConfiguration from Device as d where d.id="+deviceId; 
					Query q1 = session.createQuery(query1);
					//Object[] object = (Object[]) q1.uniqueResult();
				
					
					if(modelNumber.contains(Constants.H264Series)) //Kantharaj Changed for Fixes Bug-924
					cameraConfigH264 = (H264CameraConfiguration) q1.uniqueResult();
					else
					cameraConfig = (CameraConfiguration) q1.uniqueResult();
				}else if(deviceTypeName.equals(Constants.VIA_UNIT))
				{
					IndoorUnitConfiguration indoorUnitConfiguration = null;
					String query1 = "select d.deviceConfiguration from Device as d where d.id="+deviceId; 
					Query q1 = session.createQuery(query1);
					indoorUnitConfiguration = (IndoorUnitConfiguration) q1.uniqueResult();
					if(indoorUnitConfiguration.getConnectStatus() == 0)
					{
						continue;
					}
				}				
// creating/finding gateway.
				GateWay gateWay = null;
				for (GateWay gateWay1 : gateWays) {
					if (gateWay1.getId() == gateWayId) {
						gateWay = gateWay1;
						break;
					}
				}
				if (gateWay == null) {
					gateWay = new GateWay();
					gateWay.setId(gateWayId);
					gateWay.setMacId(macId);
					gateWay.setName(gatewayName);
					
					gateWays.add(gateWay);
				}
				Set<Device> devices = gateWay.getDevices();
				if (devices == null) {
					devices = new HashSet<Device>();
					gateWay.setDevices(devices);
				}
				
				// Finding/Creating the device.
				Device device = null;
				for (Device device1 : devices) {
					if (device1.getId() == deviceId) {
						device = device1;
						break;
					}
				}
				if (device == null) {
					device = new Device();
					device.setId(deviceId);
					device.setFriendlyName(friendlyName);
					device.setModelNumber(modelNumber);//sumit: setting modelNumber for ZXT120
					device.setMake(make);		//parishod setting make

					if(modelNumber.contains(Constants.H264Series))//Kantharaj added for Fixes Bug-924
					device.setDeviceConfiguration(cameraConfigH264);
					else
					device.setDeviceConfiguration(cameraConfig);
					devices.add(device);
					
				}
				
				DeviceType deviceType = device.getDeviceType();
				
				if (deviceType == null) {
					deviceType = new DeviceType();
					List<ActionType> actionTypes = new ArrayList<ActionType>();
					deviceType.setActionTypes(actionTypes);
					List<AlertType> alertTypes = new ArrayList<AlertType>();
					deviceType.setAlertTypes(alertTypes);
					deviceType.setName(deviceTypeName); //vibhu added
					device.setDeviceType(deviceType);
				}
				deviceType.setName(deviceTypeName);	//pari added to get the name of device
				
				// creating/finding actions.
				if (actionTypeId != null) {

					List<ActionType> actionTypes = deviceType.getActionTypes();
					ActionType actionType = null;
					for (ActionType actionType1 : actionTypes) {
						if (actionType1.getId() == actionTypeId) {
							actionType = actionType1;
							break;
						}
					}
					if (actionType == null) {
						actionType = new ActionType();
						actionType.setId(actionTypeId);
						actionType.setName(actionTypeName);
						actionType.setDetails(actionTypeDetails); //vibhu added
						actionTypes.add(actionType);
					}
				}
				// creating/finding alerts.
				if (alertTypeId != null) 
				{

					List<AlertType> alertTypes = deviceType.getAlertTypes();
					AlertType alertType = null;
					for (AlertType alertType1 : alertTypes) {
						if (alertType1.getId() == alertTypeId) {
							alertType = alertType1;
							break;
						}
					}
					if (alertType == null) 
					{
						alertType = new AlertType();
						alertType.setId(alertTypeId);
						alertType.setName(alertTypeName);
						alertType.setDetails(alertTypeDetails); //vibhu added
						if((FeatureEnabled & 1)== 1)
						{
							if(alertTypeName.equals("HMD_WARNING") || alertTypeName.equals("HMD_NORMAL") || alertTypeName.equals("HMD_FAILURE") || alertTypeName.equals("POWER_INFORMATION"))
							{
								if(modelNumber.equals("HMD"))
								alertTypes.add(alertType);
							}
							else
							{
						alertTypes.add(alertType);
					}
				}
						else
						{
							if(!alertTypeName.contains("HMD_WARNING") && !alertTypeName.contains("HMD_NORMAL") && !alertTypeName.contains("HMD_FAILURE") && !alertTypeName.contains("POWER_INFORMATION"))
							{
								alertTypes.add(alertType);
			}
						}
						
					}
					
					
					
					 
					
				
			}}
			customer.setGateWays(gateWays);
			
			
			
			// Now filling the user notification profiles.
			query = "select u.id, u.name, u.recipient, "
					+ " at.id, at.name, at.command, at.details,s.id,s.name,u.whatsApp,u.whatsAppStatus"
					+ " from UserNotificationProfile as u"
					+ " left join u.actionType as at"
					+ " left join u.status as s"
					+ " left join u.customer as c" + " where c.customerId='"
					+ customerId + "'";
			List<Object[]> results = null;
			q = session.createQuery(query);
			results = q.list();
			Set<UserNotificationProfile> userNotificationProfiles = new HashSet<UserNotificationProfile>();
			for (Object[] objects : results) {
				Long uId = (Long) objects[0];
				String uName = (String) objects[1];
				String uReceipient = (String) objects[2];

				Long atId = (Long) objects[3];
				String atName = (String) objects[4];
				String atCommand = (String) objects[5];
				String atDetails = (String) objects[6];
				// Naveen added on 22 dec 2015, For new SMS notification integration
				Long sId = (Long) objects[7];
				String sName = (String)objects[8];
				String whatsApp = IMonitorUtil.convertToString(objects[9]);
				int wStatus = Integer.parseInt(IMonitorUtil.convertToString(objects[10]));
                Status status = new Status();
                status.setId(sId);
                status.setName(sName);
				UserNotificationProfile notificationProfile = new UserNotificationProfile();
				notificationProfile.setId(uId);
				notificationProfile.setName(uName);
				notificationProfile.setRecipient(uReceipient);

				ActionType actionType = new ActionType();
				actionType.setId(atId);
				actionType.setName(atName);
				actionType.setCommand(atCommand);
				actionType.setDetails(atDetails);
				notificationProfile.setActionType(actionType);
				notificationProfile.setWhatsApp(whatsApp);
				notificationProfile.setStatus(status);
				notificationProfile.setWhatsAppStatus(wStatus);
				userNotificationProfiles.add(notificationProfile);
			}
			customer.setUserNotificationProfiles(userNotificationProfiles);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}

		return customer;
	}
	
	public Customer GetUserChoosenAlerts(long GateWayId) {
		Customer customer = new Customer();
		String query = " select u.device,u.alerts from  UserChoosenAlerts as u where u.gateway='"+ GateWayId + "'";
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}

		return customer;
	}

	public void UpdateCustomerSIINformation(SystemIntegrator systemIntegrator) {
		
		DBConnection dbc = null;
		

		try {
			dbc = new DBConnection();
			
			dbc.openConnection();
			Session session = dbc.getSession();
			Criteria criteria = session.createCriteria(Customer.class);
			Criterion criterion = Restrictions.eq("systemIntegrator", systemIntegrator);
			criteria.add(criterion);
			@SuppressWarnings("unchecked")
			List<Customer> CustomerList = criteria.list();
			
			for (Customer cus : CustomerList) {
				Hibernate.initialize(cus);
				cus.setSystemIntegrator(null);
				dbc.beginTransaction();
				session.update(cus);
				dbc.commit();
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return ;
		
	}
	
	public Customer getCustometidByCustomerName(String customerId) {
		
		Customer customer = new Customer();
		String query = " select c from  Customer as c where c.customerId='"+ customerId + "'";
		DBConnection dbc = null;
		
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			
			Query q = session.createQuery(query);
			customer = (Customer) q.uniqueResult();
			
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dbc) {
				dbc.closeConnection();
			}
		}
	
		return customer;
	}
	
// naveen start: to set value in customer table after backup is done	
public void setbackupdone(String customerid){
		
		/*String query = " update Customer as c set c.backup=1 where c.customerId='"+ customerid + "'";*/
		String query = "update customer c set c.backup=1 where c.customerId='"+customerid+"' ";
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(query);
			Transaction tx = session.beginTransaction();
			q.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			LogUtil.error("Error while saving backup Info in customer: "+e.getMessage());
			e.printStackTrace();
		}finally {
			dbc.closeConnection();
		}

	}
	
public void resetbackupdone(String customerid){
		
		
		String query = "update customer c set c.backup=0 where c.customerId='"+customerid+"' ";
		DBConnection dbc = null;
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Session session = dbc.getSession();
			Query q = session.createSQLQuery(query);
			Transaction tx = session.beginTransaction();
			q.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			LogUtil.error("Error while saving backup Info in customer: "+e.getMessage());
			e.printStackTrace();
		}finally {
			dbc.closeConnection();
		}

	}

@SuppressWarnings("unchecked")
public List<GateWay> getGateWaysAndDeviceOfCustomerForLocationView(Customer customer) {
	DBConnection dbc = null;
	List<GateWay> gateWays = new ArrayList<GateWay>();
	
	List<Status> statusList = new ArrayList<Status>();
	
	Status status1 = IMonitorUtil.getStatuses()
			.get(Constants.UN_REGISTERED);
	statusList.add(status1);
	String query = "select g.id, g.macId,s.name, "
			+ " d.id,d.generatedDeviceId, d.friendlyName, d.commandParam, la.alertCommand"
			+ " , dt.details, dt.iconFile "
			+ " , l.name, l.iconFile, arm.name,d.modelNumber, i.fileName, "	
			+ " dc.id,d.enableStatus, m.id, g.currentMode, d.enableList, d.posIdx ,l.id,c.featuresEnabled,g.gateWayVersion,g.allOnOffStatus,d.switchType"
			+ " from Device as d " + " left join d.deviceType as dt "
			+ " left join d.lastAlert as la " 
			+ " left join d.make as m " + " left join d.armStatus as arm "
			+ " left join d.location as l " + " left join d.gateWay as g "
			+ " left join d.gateWay.status as s"
			+ " left join d.icon as i "					
			+ " left join d.deviceConfiguration as dc" 
			+ " left join d.locationMap as lm" 
			+ " left join g.customer as c" + " where c.customerId='"
			+ customer.getCustomerId() + "'" //+ " and s not in(:statusList)"
			+ " group by d" + " order by d.id";
	
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		
		objects = q.list();
				
		Map<String, GateWay> gateWayMap = new HashMap<String, GateWay>();
		
		for (Object[] strings : objects) {
			String key = IMonitorUtil.convertToString(strings[0]);
			GateWay gateWay = gateWayMap.get(key);
			if (gateWay == null) {
				gateWay = new GateWay();
				gateWay.setId(Long.parseLong(key));
				gateWay.setMacId(IMonitorUtil.convertToString(strings[1]));
				gateWay.setCurrentMode(IMonitorUtil.convertToString(strings[18]));
				Status status = new Status();
				status.setName(IMonitorUtil.convertToString(strings[2]));
				gateWay.setStatus(status);
				gateWay.setGateWayVersion(IMonitorUtil.convertToString(strings[23]));
				Customer Customer=new Customer();
				long featuresEnabled = (Long)strings[22];
				Customer.setFeaturesEnabled(featuresEnabled);
				gateWay.setCustomer(Customer);
				gateWay.setAllOnOffStatus((Long)strings[24]);
				
				
				String macid=gateWay.getMacId();
				FirmWareManager FirmWareManager=new FirmWareManager();
				Object[] objects1 = (Object[]) FirmWareManager.listLatesestFirmWare(macid);
				
				if(objects1 != null)
				{
					
				gateWay.setFirmwareId(Long.parseLong(IMonitorUtil
						.convertToString(objects1[0])));
				gateWay.setLatestversion(IMonitorUtil.convertToString(objects1[1]));
				}
				gateWay.setDevices(new HashSet<Device>());
				gateWayMap.put(key, gateWay);
				gateWays.add(gateWay);
			}
			
			Device device = new Device();
			long dId = (Long) strings[3];
			device.setId(dId);
			device.setGeneratedDeviceId(IMonitorUtil.convertToString(strings[4]));
			device.setFriendlyName(IMonitorUtil.convertToString(strings[5]));
			device.setCommandParam(IMonitorUtil.convertToString(strings[6]));
			device.setModelNumber(IMonitorUtil.convertToString(strings[13]));
			
			if(device.getModelNumber().equals("HMD"))
			{
				String gendeviceid=device.getGeneratedDeviceId();
				String alerttype1="HMD_WARNING";
				String alerttype2="HMD_NORMAL";
				String alerttype3="HMD_FAILURE";
			
				ImvgAlertsActionsManager ImvgAlertsActionsManager=new ImvgAlertsActionsManager();
				Object[] objects1 = (Object[]) ImvgAlertsActionsManager.listLatesestHMDAlerts(gendeviceid, alerttype1, alerttype2, alerttype3);
				if(objects1 != null && objects1[0]!= null)
				{
					device.setHMDalertTime(""+(Timestamp)objects1[0]);
					device.setHMDalertstatus(""+(String)objects1[2]);
				}
				else
				{
					device.setHMDalertTime("");
					device.setHMDalertstatus("");
				}
				if(device.getModelNumber().equals("HMD"))
				{
				Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getId());
			
				if(objects2 != null && objects2[2]!= null)
				{
				
					device.setHMDalertValue((""+(String)objects2[2]));
					device.setHMDpowerinfo(""+(String)objects2[4]);
				
				}
				else
				{
					device.setHMDalertValue("");
					device.setHMDpowerinfo("");
				
				}
			}
			}
			
			Icon icon = new Icon();
			icon.setFileName(IMonitorUtil.convertToString(strings[14]));
			device.setIcon(icon);
			device.setEnableStatus(IMonitorUtil.convertToString(strings[16]));
			device.setEnableList(IMonitorUtil.convertToString(strings[19]));
			Make make = new Make();
			String makeId = IMonitorUtil.convertToString(strings[17]);
			long makeIdLong = 0;
			if(makeId != null && !makeId.equals(""))
			{
				makeIdLong = Integer.parseInt(makeId);
			}
			make.setId(makeIdLong);
			device.setMake(make);
			AlertType lastAlert = new AlertType();
			lastAlert.setAlertCommand(IMonitorUtil.convertToString(strings[7]));
			device.setLastAlert(lastAlert);
			DeviceType deviceType = new DeviceType();
			deviceType.setDetails(IMonitorUtil.convertToString(strings[8]));
			deviceType.setIconFile(IMonitorUtil.convertToString(strings[9]));
			device.setDeviceType(deviceType);
			Location location = new Location();
			location.setName(IMonitorUtil.convertToString(strings[10]));
			location.setIconFile(IMonitorUtil.convertToString(strings[11]));
			device.setLocation(location);
			
			Status armStatus = new Status();
			armStatus.setName(IMonitorUtil.convertToString(strings[12]));
			device.setArmStatus(armStatus);
			
			gateWay.getDevices().add(device);
			List sortedList = new ArrayList(gateWay.getDevices());
		    Collections.sort(sortedList);
		    //gateWay.setDevices(Collections.sort(sortedList));
		}
		
		String excludeGateWays = "";
		if (gateWays.size() > 0) {
			excludeGateWays = " and g not in (:varList)";
		}
		query = "select g.id, g.macId, s.name from "
				+ " GateWay as g join g.status as s"
				+ " where g.status=s.id and s.id not in(" + status1.getId()
				+ ")" + "and g.customer=" + customer.getId()
				+ excludeGateWays + "";
		q = session.createQuery(query);
		if (gateWays.size() > 0) {
			
			q.setParameterList("varList", gateWays);
		}
		List<Object[]> newImvgs = q.list();
		for (Object[] objects2 : newImvgs) {
			GateWay gateWay = new GateWay();
			gateWay.setId(Long.parseLong(IMonitorUtil
					.convertToString(objects2[0])));
			gateWay.setMacId(IMonitorUtil.convertToString(objects2[1]));
			Status status = new Status();
			status.setName(IMonitorUtil.convertToString(objects2[2]));
			gateWay.setStatus(status);
			gateWays.add(gateWay);
		}
	}catch (Exception e){
		LogUtil.info("error while executing query");
	}finally {
		dbc.closeConnection();
	}
		
 //   LogUtil.info("gatewayssss: "+ new XStream().toXML(gateWays));
    
    
    
	return gateWays;
}

@SuppressWarnings("unchecked")
public List<Customer> listAskedCustomerToList() {
	String query = "";
	query += "select c.id,c.customerId,c.firstName"
			+ ",c.lastName,c.phoneNumber,s.name from "
			+ "Customer as c join c.status as s "
			+ "where c.status=s.id and c.customerId != 'admin' order by c.customerId";
	List<Customer> CustomerList = new ArrayList<Customer>();
	DBConnection dbc = null;
	List<Object[]> objects = null;
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Query q = dbc.getSession().createQuery(query);
		objects = (List<Object[]>)q.list();
		
		for (Object[] strings : objects)
		{
			Customer customer=new Customer();
			customer.setId(Integer.parseInt(IMonitorUtil.convertToString(strings[0])));
			customer.setCustomerId(IMonitorUtil.convertToString(strings[1]));
			CustomerList.add(customer);
		}
	} catch (Exception e) {
		e.printStackTrace();
		LogUtil.error(e.getMessage());
	}finally {
		dbc.closeConnection();
	}
	
	return CustomerList;
}

public Customer getCustomerByMainUserId(long userId) {
	String query = "select c from User u left join u.customer c where u.id='"
			+ userId + "'";
	DBConnection dbc = null;
	Customer customer = null;
    
   
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		customer = (Customer) q.uniqueResult();
	
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	return customer;
}
	// naveen end

public boolean updateCustomerServices(CustomerServices customerServices){
	
	boolean result = false;
	XStream xStream = new XStream();
	DBConnection dbc = null;
	
	String query = "select * from customerServices where customer="+customerServices.getCustomer().getId()+"";
	
		
	
		
try {
	
	dbc = new DBConnection();
	dbc.openConnection();
	Session session = dbc.getSession();
	Query q = session.createSQLQuery(query);
	Object[] objects = (Object[]) q.uniqueResult();
	if(objects != null){
		long id=Long.parseLong(IMonitorUtil.convertToString(objects[0]));
		
		CustomerServices services = new CustomerServices();
		services.setId(id);
		services.setCustomer(customerServices.getCustomer());
		services.setIntimation(customerServices.getIntimation());
		services.setDate(customerServices.getDate());
		services.setReason(customerServices.getReason());
		services.setDescription(customerServices.getDescription());
		services.setServiceEnabled(customerServices.getServiceEnabled());
		services.setIntimationMail(customerServices.getIntimationMail());
		Transaction  tx = session.beginTransaction();
		session.update(services);
		tx.commit();
		result = true;
		
	}else{
		Transaction  tx = session.beginTransaction();
		tx = session.beginTransaction();
		session.save(customerServices);
		 result = true;
	}
	
} catch (Exception e) {
	// TODO: handle exception
}finally {
	dbc.closeConnection();
}

			
		
	return result;
	
}


public CustomerServices getCustomerServicesByCustomerId(long id){
	

	XStream xStream = new XStream();
	DBConnection dbc = null;
	
	String query = "select cs.id,cs.serviceEnabled,cs.Intimation,cs.Date,cs.Reason,cs.Description,c.id,c.email,cs.IntimationMail from customerServices as cs left join customer as c on cs.customer=c.id " +
			        " where c.id="+id+"";
	
	
	CustomerServices customerService = new CustomerServices();
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createSQLQuery(query);
		Object[] objects = (Object[]) q.uniqueResult();
				
		Calendar javaCalendar = null;
		String currentDate = "";

		javaCalendar = Calendar.getInstance();

		currentDate = javaCalendar.get(Calendar.DATE) + "/" + (javaCalendar.get(Calendar.MONTH) + 1) + "/" + javaCalendar.get(Calendar.YEAR);
		
		if(objects != null){
			id=Long.parseLong(IMonitorUtil.convertToString(objects[0]));
			int serviceEnabled = Integer.parseInt (IMonitorUtil.convertToString(objects[1]));
			int Intimation = Integer.parseInt(IMonitorUtil.convertToString(objects[2]));
			String dateString;
			if (IMonitorUtil.convertToString(objects[3]) != null ){
				
				dateString = IMonitorUtil.convertToString(objects[3]);
				
				}
			else{
			
				Date date = new Date();
				DateFormat  formatter = new SimpleDateFormat("dd-MMM-yyyy");
				dateString = formatter.format(date);
				}
			//String dateString = currentDate;//IMonitorUtil.convertToString(objects[3]);
			//String verifydate = IMonitorUtil.convertToString(objects[3]);
			String Reason = IMonitorUtil.convertToString(objects[4]);
			String Description = IMonitorUtil.convertToString(objects[5]);
			long customerid = Long.parseLong(IMonitorUtil.convertToString(objects[6]));
			String email = IMonitorUtil.convertToString(objects[7]);
			String IntimationMail = IMonitorUtil.convertToString(objects[8]);
			Customer customer = new Customer();
			customer.setId(customerid);
			customerService.setCustomer(customer);
			customerService.setIntimation(Intimation);
			customerService.setDate(dateString);
			customerService.setReason(Reason);
			customerService.setDescription(Description);
			customer.setEmail(email);
			customerService.setCustomer(customer);
			customerService.setServiceEnabled(serviceEnabled);
			customerService.setIntimationMail(IntimationMail);
		}
						
	}catch(Exception e){
		
		LogUtil.info("could not fetch customer services :"+ e.getMessage());
		
	}finally {
		dbc.closeConnection();
	}
	
	
    return customerService;
}

public List<Object[]> listofcustomerapi(){
	XStream stream = new XStream();
	DBConnection dbc = null;
	String query = "select c.customerId,u.userId,u.password,c.firstName,c.mobile,c.email,g.macId,s.name from user as u left join customer as c on c.id=u.customer left join gateway as g on c.id=g.customer left join status as s on s.id=g.status where g.remarks like '%TPDDL%'";
	List<Object[]> list=null;
	try {
		
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createSQLQuery(query);
		list=q.list();
		
	}catch (Exception e) {
		// TODO: handle exception
	}finally {
		dbc.closeConnection();
	}
	
	return list;
}

public boolean loginpasscheckapi(String userId,String password,String custid){
	//LogUtil.info("loginpasscheckapi");
	XStream stream = new XStream();
	DBConnection dbc = null;
	boolean result = false;
	String query = "select c.customerId,u.userId,u.password from user as u left join customer as c on c.id=u.customer where c.customerId='"+custid+"' and u.userId='"+userId+"' and u.password='"+password+"'";
	LogUtil.info("loginpasscheckapi query="+query);
	try {
		
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createSQLQuery(query);
		Object[] object=(Object[]) q.uniqueResult();
		if(object!=null){
			result=true;
		}else{
			result=false;
			}
	}catch (Exception e) {
		// TODO: handle exception
	}finally {
		dbc.closeConnection();
	}
	LogUtil.info("result="+result);
	return result;
}

public List<Object[]> getcustomeraccDetails(String customerId,String userId){
	XStream stream = new XStream();
	List<GateWay> gateWays = new ArrayList<GateWay>();
	DBConnection dbc = null;
	String query = "select d.generatedDeviceId,d.friendlyName,d.batteryStatus,d.enableList,d.modelNumber,dt.details,l.name,d.commandParam,d.switchType,mi.buttonone,mi.buttontwo,mi.buttonthree,mi.buttonfour,lc.f1,lc.f2,lc.f3,ac.pollingInterval,ac.fanModeValue,ac.fanMode,ac.AcSwing,ac.acLearnValue,ac.sensedTemperature,m.length,m.mountingtype,ca.pantiltControl,ca.controlPantilt,ca.controlNightVision,ca.presetvalue,ca.cameraOrientation,dc.id from device as d left join gateway as g on d.gateway=g.id left join devicetype as dt on d.deviceType=dt.id left join location as l on d.location=l.id left join deviceconfiguration as dc on d.deviceConfiguration=dc.id left join minimoteconfiguration as mi on dc.id=mi.id left join lcdremoteconfiguration as lc on dc.id=lc.id left join acConfiguration as ac on dc.id=ac.id left join motorconfiguration as m on dc.id=m.id left join cameraconfiguration as ca on dc.id=ca.id left join customer as c on g.customer=c.id left join user as u on u.customer=c.id where c.customerId='"+customerId+"'"+" and u.userId='"+userId+"'";
	
	List<Object[]> list=null;
	try {
		
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createSQLQuery(query);
		list=q.list();
		
	}catch (Exception e) {
		LogUtil.info("exception :"+e.getMessage());
	}finally {
		dbc.closeConnection();
	}
	
	return list;
	
	
	
	
}

@SuppressWarnings("unchecked")
public List<Object[]> getCustomerdevicePowerDetails(String customerId){
	XStream stream = new XStream();
	DBConnection dbc = null;
	String query="select d.id from device as d left join gateway as g on d.gateway=g.id left join customer as c on g.customer=c.id left join devicetype as dd on dd.id=d.deviceType where c.customerId='"+customerId+"'"+"and dd.name='Z_WAVE_SWITCH' order by d.id";
	List<Object[]> values=new ArrayList<Object[]>();
	List<Object[]> list=new ArrayList<Object[]>();
	try {
		
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		
		Query q = session.createSQLQuery(query);
		List<Object> objects=q.list();
		for(Object obj:objects){
		String query1 = "select g.macId,d.generatedDeviceId,d.friendlyName,dd.name,p.alertValue,p.alertTime,d.modelNumber from powerInformationFromImvg as p  left join device as d on d.id=p.device left join devicetype as dd on dd.id=d.deviceType left join gateway as g on g.id=d.gateway left join customer as c on c.id=g.customer where p.device='"+obj+"'"+" and p.alertTime >= date_sub(NOW(), interval 20 SECOND) and dd.name='Z_WAVE_SWITCH' order by p.alertTime desc limit 2";
		
		q = session.createSQLQuery(query1);
		values=q.list();
		for(Object[] obj1:values){
			list.add(obj1);
		}
		}
		
		
	}catch (Exception e) {
		// TODO: handle exception
	}finally {
		dbc.closeConnection();
	}
	
	return list;
	
	
	
	
}

@SuppressWarnings("unchecked")
public List<Object[]> getCustomerdeviceAcDetails(String customerId){
	XStream stream = new XStream();
	DBConnection dbc = null;
	String query="select d.id from device as d left join gateway as g on d.gateway=g.id left join customer as c on g.customer=c.id left join devicetype as dd on dd.id=d.deviceType where c.customerId='"+customerId+"'"+"and dd.name='Z_WAVE_AC_EXTENDER' order by d.id";
	List<Object[]> values=new ArrayList<Object[]>();
	List<Object[]> list=new ArrayList<Object[]>();
	try {
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		
		Query q = session.createSQLQuery(query);
		List<Object> objects=q.list();
		
		
		for(Object obj:objects){
		//LogUtil.info(obj);
		String query1= "select g.macId,d.generatedDeviceId,d.friendlyName,dd.name,p.alertValue,p.alertTime,d.modelNumber from alertsfromimvg as p  left join device as d on d.id=p.device left join devicetype as dd on dd.id=d.deviceType left join gateway as g on g.id=d.gateway left join customer as c on c.id=g.customer where p.device='"+obj+"'"+"and dd.name='Z_WAVE_AC_EXTENDER' order by p.alertTime desc limit 1";
		
		q = session.createSQLQuery(query1);
		values=q.list();
		for(Object[] obj1:values){
			list.add(obj1);
		}
		}
		
	}catch (Exception e) {
		// TODO: handle exception
	}finally {
		dbc.closeConnection();
	}
	
	return list;	
}


//getting Devices list
@SuppressWarnings("unchecked")
public long getGateWay(long id) {
	
	DBConnection dbc = null;
	String gateway = null;
	try {
		
		String query = "";
		query += "select g.id from gateway as g where customer="+id;
		
		dbc = new DBConnection();
		Session session = dbc.openConnection();
	
		
		Query q = session.createSQLQuery(query);
		
		gateway = IMonitorUtil.convertToString(q.uniqueResult());
		
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	
	long gty=Long.parseLong(gateway);
	return gty;
}


	@SuppressWarnings("unchecked")
	public List<Object[]> listAllDevices(long Id) {
		
		String query = "select d.id,d.deviceId,d.gateWay,d.generatedDeviceId,d.friendlyName,d.batteryStatus,d.modelNumber,d.deviceType," +
				"d.commandParam,d.lastAlert, d.armStatus,d.make,d.location,d.deviceConfiguration,d.enableStatus,d.icon,d.mode," +
				"d.enableList,d.posIdx,d.locationMap,d.switchType,d.devicetimeout,d.pulseTimeOut,dt.name " +
				"from device as d join devicetype as dt on dt.id=d.deviceType where d.gateWay="+Id+" and dt.name in ('Z_WAVE_SWITCH','Z_WAVE_DIMMER','Z_WAVE_AC_EXTENDER') group by d.deviceId";
      query += "";
    
      
              DBConnection dbc = null;
              List<Object[]> devices =null;
             
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			devices= q.list();
			
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		
		return devices;
	}
	
	

	@SuppressWarnings("unchecked")
	public List<Device> listAlexaDevicesOfCustomer(String customerId) {
		
		/*String query = "select d.id,d.deviceId,d.gateWay,d.generatedDeviceId,d.friendlyName,d.batteryStatus,d.modelNumber,d.deviceType," +
				"d.commandParam,d.lastAlert, d.armStatus,d.make,d.location,d.deviceConfiguration,d.enableStatus,d.icon,d.mode," +
				"d.enableList,d.posIdx,d.locationMap,d.switchType,d.devicetimeout,d.pulseTimeOut,dt.name " +
				"from device as d join devicetype as dt on dt.id=d.deviceType where d.gateWay="+Id+" and dt.name in ('Z_WAVE_SWITCH','Z_WAVE_DIMMER','Z_WAVE_AC_EXTENDER') group by d.deviceId";
      query += "";
      LogUtil.info(query);
      
              DBConnection dbc = null;
              List<Object[]> devices =null;
             
		try {
			dbc = new DBConnection();
			dbc.openConnection();
			Query q = dbc.getSession().createSQLQuery(query);
			devices= q.list();
			
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		
		return devices;*/
		
		
		DBConnection dbc = null;
		List<Device> devices = new ArrayList<Device>();
		
		/*String query = "select"
				+ " d.id, d.generatedDeviceId, d.friendlyName, d.mode"
				+ " , i.fileName"
				+ " , g.id, g.macId, g.delayHome, g.delayStay, g.delayAway, d.deviceId, dt.id, l.name, dt.name, d.commandParam" //sumit#13
				+ " from Device as d" 
				+ " left join d.gateWay as g"
				+ " left join d.icon as i"
				+ " left join g.customer as c" 
				+ " left join d.deviceType as dt"
				+ " left join d.location as l"
				+ " where c.customerId='"+ customerId + "' and dt.name in ('Z_WAVE_SWITCH','Z_WAVE_DIMMER','Z_WAVE_AC_EXTENDER')" 
				+ " order by d.id";*/
		String query = "select"
				+ " d.id, d.generatedDeviceId, d.friendlyName, d.mode"
				+ " , i.fileName"
				+ " , g.id, g.macId, g.delayHome, g.delayStay, g.delayAway, d.deviceId, dt.id, l.name, dt.name, d.commandParam" //sumit#13
				+ " from Device as d" 
				+ " left join d.gateWay as g"
				+ " left join d.icon as i"
				+ " left join g.customer as c" 
				+ " left join d.deviceType as dt"
				+ " left join d.location as l"
				+ " where c.customerId='"+ customerId + "' and dt.name in ('Z_WAVE_SWITCH','Z_WAVE_DIMMER')" 
				+ " order by d.id";
		List<Object[]> objects = null;
		try{
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
			for (Object[] strings : objects) {
				Device device = new Device();
				device.setId(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
				device.setGeneratedDeviceId(IMonitorUtil.convertToString(strings[1]));
				device.setFriendlyName(IMonitorUtil.convertToString(strings[2]));
				device.setMode (IMonitorUtil.convertToString(strings[3]));
				//sumit start
				device.setDeviceId(IMonitorUtil.convertToString(strings[10]));
				//device.setSubUserAccess(IMonitorUtil.convertToString(strings[12]));//sumit added: Sub-User Restriction
				
				Location location = new Location();
				location.setName(IMonitorUtil.convertToString(strings[12]));
				device.setLocation(location);
				
				DeviceType deviceType = new DeviceType();
				deviceType.setId(Long.parseLong(IMonitorUtil.convertToString(strings[11])));
				deviceType.setName(IMonitorUtil.convertToString(strings[13]));
				device.setDeviceType(deviceType);
				//sumit end
				Icon icon = new Icon();
				icon.setFileName(IMonitorUtil.convertToString(strings[4]));
				device.setIcon(icon);
				
				GateWay gateway = new GateWay();
				gateway.setId(Long.parseLong(IMonitorUtil.convertToString(strings[5])));
				gateway.setMacId(IMonitorUtil.convertToString(strings[6]));
				gateway.setDelayHome(IMonitorUtil.convertToString(strings[7]));
				gateway.setDelayStay(IMonitorUtil.convertToString(strings[8]));
				gateway.setDelayAway(IMonitorUtil.convertToString(strings[9]));
				device.setGateWay(gateway);
				device.setCommandParam(IMonitorUtil.convertToString(strings[14]));
				devices.add(device);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return devices;
	}
	
			/***AlexaVideo devices of Cusromer****/
	@SuppressWarnings("unchecked")
	public List<Device> listAlexaVideoDevicesOfCustomer(String customerId) {
		DBConnection dbc = null;
		List<Device> devices = new ArrayList<Device>();
		String query = "select"
				+ " d.id, d.generatedDeviceId, d.friendlyName, d.mode"
				+ " , i.fileName"
				+ " , g.id, g.macId, g.delayHome, g.delayStay, g.delayAway, d.deviceId, dt.id, l.name, dt.name, d.commandParam" //sumit#13
				+ " from Device as d" 
				+ " left join d.gateWay as g"
				+ " left join d.icon as i"
				+ " left join g.customer as c" 
				+ " left join d.deviceType as dt"
				+ " left join d.location as l"
				+ " where c.customerId='"+ customerId + "' and dt.name ='Z_WAVE_AV_BLASTER'" 
				+ " order by d.id";
		List<Object[]> objects = null;
		try{
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
			for (Object[] strings : objects) {
				Device device = new Device();
				device.setId(Long.parseLong(IMonitorUtil.convertToString(strings[0])));
				device.setGeneratedDeviceId(IMonitorUtil.convertToString(strings[1]));
				device.setFriendlyName(IMonitorUtil.convertToString(strings[2]));
				device.setMode (IMonitorUtil.convertToString(strings[3]));
				//sumit start
				device.setDeviceId(IMonitorUtil.convertToString(strings[10]));
				//device.setSubUserAccess(IMonitorUtil.convertToString(strings[12]));//sumit added: Sub-User Restriction
				
				Location location = new Location();
				location.setName(IMonitorUtil.convertToString(strings[12]));
				device.setLocation(location);
				
				DeviceType deviceType = new DeviceType();
				deviceType.setId(Long.parseLong(IMonitorUtil.convertToString(strings[11])));
				deviceType.setName(IMonitorUtil.convertToString(strings[13]));
				device.setDeviceType(deviceType);
				//sumit end
				Icon icon = new Icon();
				icon.setFileName(IMonitorUtil.convertToString(strings[4]));
				device.setIcon(icon);
				
				GateWay gateway = new GateWay();
				gateway.setId(Long.parseLong(IMonitorUtil.convertToString(strings[5])));
				gateway.setMacId(IMonitorUtil.convertToString(strings[6]));
				gateway.setDelayHome(IMonitorUtil.convertToString(strings[7]));
				gateway.setDelayStay(IMonitorUtil.convertToString(strings[8]));
				gateway.setDelayAway(IMonitorUtil.convertToString(strings[9]));
				device.setGateWay(gateway);
				device.setCommandParam(IMonitorUtil.convertToString(strings[14]));
				devices.add(device);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return devices;
	}

			
	public String getCustomerIdByMacId(String macId) {
		String query = "select c from GateWay g left join g.customer c where g.macId='"
				+ macId + "'";
		DBConnection dbc = null;
		Customer customer = null;

		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			customer = (Customer) q.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}
		return customer.getCustomerId();
	}		

//3 gateways proj
	
	@SuppressWarnings("unchecked")
	public List<GateWay> getGateWaysAndDeviceOfMultipleGateway(Customer customer) {

		DBConnection dbc = null;
		List<GateWay> gateWays = new ArrayList<GateWay>();
		// Using HQL for performance Optimization.
		List<Status> statusList = new ArrayList<Status>();
		Status status1 = IMonitorUtil.getStatuses()
				.get(Constants.UN_REGISTERED);
		statusList.add(status1);
		String query = "select "
				+ " g.id, g.macId "
				+ " , s.name "
				+ " , d.generatedDeviceId, d.friendlyName, d.batteryStatus, d.commandParam, la.alertCommand"
				+ " , dt.details, dt.iconFile "
//				+ " , l.name, l.iconFile, arm.name, count(r), d.id,d.modelNumber"
				+ " , l.name, l.iconFile, arm.name, count(r), d.id,d.modelNumber, i.fileName, "	//added a "i.fileName" in the select clause

//				+ " dc.id, dc.controlNightVision, d.enableStatus, m.id " 
//sumit start: Re-Ordering Of Devices.
//				+ " dc.id, dc.controlNightVision, d.enableStatus, m.id, g.currentMode, d.enableList "/*17-21 + 22*/  //sumit
//	parishod commented:	+ " dc.id, dc.controlNightVision, d.enableStatus, m.id, g.currentMode, d.enableList, d.posIdx ,lm.top,lm.leftMap,lm.id,l.id"/*posIdx = 23*/  
				+ " dc.id, dc.controlNightVision, d.enableStatus, m.id, g.currentMode, d.enableList, d.posIdx ,lm.top,lm.leftMap,lm.id,l.id,dc.pantiltControl,c.featuresEnabled,g.gateWayVersion,dc.fanModeValue,dc.acSwing,g.allOnOffStatus,d.switchType,d.deviceId"/*Aditya changed for all ON OFF,Last Index is 33 for allOnOffStatus*/  
//sumit end: Re-Ordering Of Devices.				
				+ " from Device as d " + " left join d.deviceType as dt "
				+ " left join d.lastAlert as la " + " left join d.rules as r "
				+ " left join d.make as m " + " left join d.armStatus as arm "
				+ " left join d.location as l " + " left join d.gateWay as g "
				+ " left join d.gateWay.status as s"
				+ " left join d.icon as i "					//added "left join d.icon as i" in from clause
				+ " left join d.deviceConfiguration as dc" 
				+ " left join d.locationMap as lm"
				+ " left join g.customer as c" + " where c.customerId='"
				+ customer.getCustomerId() + "'" + " and s not in(:statusList)"
				//+ " group by d" + " order by d.id";
				+ " group by d" + " order by d.friendlyName";
		
		
		List<Object[]> objects = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			q.setParameterList("statusList", statusList);
			objects = q.list();
			XStream stream=new XStream();
			Map<String, GateWay> gateWayMap = new HashMap<String, GateWay>();
			for (Object[] strings : objects) {
				String key = IMonitorUtil.convertToString(strings[0]);
				GateWay gateWay = gateWayMap.get(key);
				if (gateWay == null) {
					gateWay = new GateWay();
					gateWay.setId(Long.parseLong(key));
					gateWay.setMacId(IMonitorUtil.convertToString(strings[1]));
					//sumit start
					gateWay.setCurrentMode(IMonitorUtil.convertToString(strings[21]));
					//sumit end
					Status status = new Status();
					status.setName(IMonitorUtil.convertToString(strings[2]));
					gateWay.setStatus(status);
					gateWay.setGateWayVersion(IMonitorUtil.convertToString(strings[30]));
					Customer Customer=new Customer();
					long featuresEnabled = (Long)strings[29];
					Customer.setFeaturesEnabled(featuresEnabled);
					gateWay.setCustomer(Customer);
					
					/***
					 * Aditya added for all on off feature
					 * */
					
					gateWay.setAllOnOffStatus((Long)strings[33]);
					
					String macid=gateWay.getMacId();
					//bhavya start
					FirmWareManager FirmWareManager=new FirmWareManager();
					Object[] objects1 = (Object[]) FirmWareManager.listLatesestFirmWare(macid);
					
					if(objects1 != null)
					{
						
					gateWay.setFirmwareId(Long.parseLong(IMonitorUtil
							.convertToString(objects1[0])));
					gateWay.setLatestversion(IMonitorUtil.convertToString(objects1[1]));
					}
					

					gateWay.setDevices(new HashSet<Device>());
					gateWayMap.put(key, gateWay);
					gateWays.add(gateWay);
				}
				Device device = new Device();
				long dId = (Long) strings[14];
				device.setId(dId);
				device.setGeneratedDeviceId(IMonitorUtil
						.convertToString(strings[3]));
				device
						.setFriendlyName(IMonitorUtil
								.convertToString(strings[4]));
				device.setBatteryStatus(IMonitorUtil
						.convertToString(strings[5]));
				device
						.setCommandParam(IMonitorUtil
								.convertToString(strings[6]));
				device
						.setModelNumber(IMonitorUtil
								.convertToString(strings[15]));
				
				if(strings[34] != null){
					
					device.setSwitchType((Long)strings[34]);
				}
				device.setDeviceId(IMonitorUtil.convertToString(strings[35]));
				if(device.getModelNumber().equals("HMD"))
				{
					String gendeviceid=device.getGeneratedDeviceId();
					String alerttype1="HMD_WARNING";
					String alerttype2="HMD_NORMAL";
					String alerttype3="HMD_FAILURE";
				
					ImvgAlertsActionsManager ImvgAlertsActionsManager=new ImvgAlertsActionsManager();
					Object[] objects1 = (Object[]) ImvgAlertsActionsManager.listLatesestHMDAlerts(gendeviceid, alerttype1, alerttype2, alerttype3);
					if(objects1 != null && objects1[0]!= null)
					{
						device.setHMDalertTime(""+(Timestamp)objects1[0]);
						device.setHMDalertstatus(""+(String)objects1[2]);
					}
					else
					{
						device.setHMDalertTime("");
						device.setHMDalertstatus("");
					}
					if(device.getModelNumber().equals("HMD"))
					{
					Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getId());
				
					if(objects2 != null && objects2[2]!= null)
					{
					
						device.setHMDalertValue((""+(String)objects2[2]));
						device.setHMDpowerinfo(""+(String)objects2[4]);
					
					}
					else
					{
						device.setHMDalertValue("");
						device.setHMDpowerinfo("");
					
					}
				}
				}
//				for the icon feature	
				Icon icon = new Icon();
				icon.setFileName(IMonitorUtil.convertToString(strings[16]));
				device.setIcon(icon);
								
				device.setEnableStatus(IMonitorUtil.convertToString(strings[19]));
				device.setEnableList(IMonitorUtil.convertToString(strings[22]));	//for enabling listing of device on home screen.
				device.setPosIdx(Integer.parseInt((IMonitorUtil.convertToString(strings[23]))));	//Re-Ordering Of Device
				LocationMap map=new LocationMap();
				String Id = IMonitorUtil.convertToString(strings[26]);
				if(Id != null && !Id.equals(""))
				{
				map.setId(Integer.parseInt(Id));
				map.setTop(IMonitorUtil.convertToString(strings[24]));
				map.setLeftMap(IMonitorUtil.convertToString(strings[25]));
				}
				device.setLocationMap(map);
				Make make = new Make();
				String makeId = IMonitorUtil.convertToString(strings[20]);
				long makeIdLong = 0;
				if(makeId != null && !makeId.equals(""))
				{
					makeIdLong = Integer.parseInt(makeId);
				}	
				make.setId(makeIdLong);
				device.setMake(make);
					
				int count = Integer.parseInt(IMonitorUtil
						.convertToString(strings[13]));
				if (count > 0) {
					device.setRules(new ArrayList<Rule>());

				}
				AlertType lastAlert = new AlertType();
				lastAlert.setAlertCommand(IMonitorUtil
						.convertToString(strings[7]));
				device.setLastAlert(lastAlert);
				DeviceType deviceType = new DeviceType();
				deviceType.setDetails(IMonitorUtil.convertToString(strings[8]));
				deviceType
						.setIconFile(IMonitorUtil.convertToString(strings[9]));
				device.setDeviceType(deviceType);
				Location location = new Location();
				location.setName(IMonitorUtil.convertToString(strings[10]));
				location.setIconFile(IMonitorUtil.convertToString(strings[11]));
				device.setLocation(location);
				Status armStatus = new Status();
				armStatus.setName(IMonitorUtil.convertToString(strings[12]));
				device.setArmStatus(armStatus);
				
//				Camera Configuration.
				Long dcId =  (Long) strings[17];
				
				if(dcId != null)
				{
					DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
					
					if(device.getModelNumber().contains(Constants.H264Series)){
						H264CameraConfiguration h264CameraConfiguration = (H264CameraConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(h264CameraConfiguration);
					}else if (device.getModelNumber().contains(Constants.RC80Series)){
						CameraConfiguration cameraConfiguration = (CameraConfiguration) Dvconf.getDeviceConfigurationById(dcId);;
						device.setDeviceConfiguration(cameraConfiguration);
					}
					else if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
					{
						acConfiguration acConfiguration = (acConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(acConfiguration);
					}
					else{
						DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
						deviceConfiguration.setId(dcId);
						device.setDeviceConfiguration(deviceConfiguration);
					}
	
				}
				
				gateWay.getDevices().add(device);
			}
			/*for (GateWay gateWay : gateWays)
			{
				LogUtil.info("Gateway  --- > "+stream.toXML(gateWay));
			}*/
			String excludeGateWays = "";
			if (gateWays.size() > 0) {
				excludeGateWays = " and g not in (:varList)";
				
			}
			query = "select g.id, g.macId, s.name from "
					+ " GateWay as g join g.status as s"
					+ " where g.status=s.id and s.id not in(" + status1.getId()
					+ ")" + "and g.customer=" + customer.getId()
					+ excludeGateWays + "";
			
			try {
				dbc = new DBConnection();
				session = dbc.openConnection();
			q = session.createQuery(query);
			if (gateWays.size() > 0) {
				// q.setParameterList("statusList",statusList);
				q.setParameterList("varList", gateWays);
			}
			List<Object[]> newImvgs = q.list();
			for (Object[] objects2 : newImvgs) {
				GateWay gateWay = new GateWay();
				gateWay.setId(Long.parseLong(IMonitorUtil
						.convertToString(objects2[0])));
				gateWay.setMacId(IMonitorUtil.convertToString(objects2[1]));
				Status status = new Status();
				status.setName(IMonitorUtil.convertToString(objects2[2]));
				gateWay.setStatus(status);
				gateWays.add(gateWay);
			}
			}catch (Exception ex) {
				ex.printStackTrace();
			}finally {
				dbc.closeConnection();
			}
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}

		return gateWays;
	}

	//3Gateways project
	@SuppressWarnings("unchecked")
	public List<GateWay> listGateWaysAndNonHmdDevicesForMultipleGateway(Customer customer) {

		DBConnection dbc = null;
		List<GateWay> gateWays = new ArrayList<GateWay>();
		// Using HQL for performance Optimization.
		List<Status> statusList = new ArrayList<Status>();
		Status status1 = IMonitorUtil.getStatuses()
				.get(Constants.UN_REGISTERED);
		statusList.add(status1);
		String query = "select "
				+ " g.id, g.macId "
				+ " , s.name "
				+ " , d.generatedDeviceId, d.friendlyName, d.batteryStatus, d.commandParam, la.alertCommand"
				+ " , dt.details, dt.iconFile "
//				+ " , l.name, l.iconFile, arm.name, count(r), d.id,d.modelNumber"
				+ " , l.name, l.iconFile, arm.name, count(r), d.id,d.modelNumber, i.fileName, "	//added a "i.fileName" in the select clause

//				+ " dc.id, dc.controlNightVision, d.enableStatus, m.id " 
//sumit start: Re-Ordering Of Devices.
//				+ " dc.id, dc.controlNightVision, d.enableStatus, m.id, g.currentMode, d.enableList "/*17-21 + 22*/  //sumit
//	parishod commented:	+ " dc.id, dc.controlNightVision, d.enableStatus, m.id, g.currentMode, d.enableList, d.posIdx ,lm.top,lm.leftMap,lm.id,l.id"/*posIdx = 23*/  
				+ " dc.id, dc.controlNightVision, d.enableStatus, m.id, g.currentMode, d.enableList, d.posIdx ,lm.top,lm.leftMap,lm.id,l.id,dc.pantiltControl,c.featuresEnabled,g.gateWayVersion,dc.fanModeValue,dc.acSwing,g.allOnOffStatus,d.switchType,d.deviceId"/*Aditya changed for all ON OFF,Last Index is 33 for allOnOffStatus*/  
//sumit end: Re-Ordering Of Devices.				
				+ " from Device as d " + " left join d.deviceType as dt "
				+ " left join d.lastAlert as la " + " left join d.rules as r "
				+ " left join d.make as m " + " left join d.armStatus as arm "
				+ " left join d.location as l " + " left join d.gateWay as g "
				+ " left join d.gateWay.status as s"
				+ " left join d.icon as i "					//added "left join d.icon as i" in from clause
				+ " left join d.deviceConfiguration as dc" 
				+ " left join d.locationMap as lm"
				+ " left join g.customer as c" + " where c.customerId='"
				+ customer.getCustomerId() + "'" + " and s not in(:statusList)"
				//+ " group by d" + " order by d.id";
				+ " group by d" + " order by d.friendlyName";
		
		
		List<Object[]> objects = null;
		try {
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			q.setParameterList("statusList", statusList);
			objects = q.list();
			XStream stream=new XStream();
			Map<String, GateWay> gateWayMap = new HashMap<String, GateWay>();
			for (Object[] strings : objects) {
				String key = IMonitorUtil.convertToString(strings[0]);
				GateWay gateWay = gateWayMap.get(key);
				if (gateWay == null) {
					gateWay = new GateWay();
					gateWay.setId(Long.parseLong(key));
					gateWay.setMacId(IMonitorUtil.convertToString(strings[1]));
					//sumit start
					gateWay.setCurrentMode(IMonitorUtil.convertToString(strings[21]));
					//sumit end
					Status status = new Status();
					status.setName(IMonitorUtil.convertToString(strings[2]));
					gateWay.setStatus(status);
					gateWay.setGateWayVersion(IMonitorUtil.convertToString(strings[30]));
					Customer Customer=new Customer();
					long featuresEnabled = (Long)strings[29];
					Customer.setFeaturesEnabled(featuresEnabled);
					gateWay.setCustomer(Customer);
					
					/***
					 * Aditya added for all on off feature
					 * */
					
					gateWay.setAllOnOffStatus((Long)strings[33]);
					
					String macid=gateWay.getMacId();
					//bhavya start
					FirmWareManager FirmWareManager=new FirmWareManager();
					Object[] objects1 = (Object[]) FirmWareManager.listLatesestFirmWare(macid);
					
					if(objects1 != null)
					{
						
					gateWay.setFirmwareId(Long.parseLong(IMonitorUtil
							.convertToString(objects1[0])));
					gateWay.setLatestversion(IMonitorUtil.convertToString(objects1[1]));
					}
					

					gateWay.setDevices(new HashSet<Device>());
					gateWayMap.put(key, gateWay);
					gateWays.add(gateWay);
				}
				Device device = new Device();
				long dId = (Long) strings[14];
				device.setId(dId);
				device.setGeneratedDeviceId(IMonitorUtil
						.convertToString(strings[3]));
				device
						.setFriendlyName(IMonitorUtil
								.convertToString(strings[4]));
				device.setBatteryStatus(IMonitorUtil
						.convertToString(strings[5]));
				device
						.setCommandParam(IMonitorUtil
								.convertToString(strings[6]));
				device
						.setModelNumber(IMonitorUtil
								.convertToString(strings[15]));
				
				if(strings[34] != null){
					
					device.setSwitchType((Long)strings[34]);
				}
				device.setDeviceId(IMonitorUtil.convertToString(strings[35]));
				if(device.getModelNumber().equals("HMD"))
				{
					String gendeviceid=device.getGeneratedDeviceId();
					String alerttype1="HMD_WARNING";
					String alerttype2="HMD_NORMAL";
					String alerttype3="HMD_FAILURE";
				
					ImvgAlertsActionsManager ImvgAlertsActionsManager=new ImvgAlertsActionsManager();
					Object[] objects1 = (Object[]) ImvgAlertsActionsManager.listLatesestHMDAlerts(gendeviceid, alerttype1, alerttype2, alerttype3);
					if(objects1 != null && objects1[0]!= null)
					{
						device.setHMDalertTime(""+(Timestamp)objects1[0]);
						device.setHMDalertstatus(""+(String)objects1[2]);
					}
					else
					{
						device.setHMDalertTime("");
						device.setHMDalertstatus("");
					}
					if(device.getModelNumber().equals("HMD"))
					{
					Object[] objects2 = (Object[]) ImvgAlertsActionsManager.listpowerinfo(device.getId());
				
					if(objects2 != null && objects2[2]!= null)
					{
					
						device.setHMDalertValue((""+(String)objects2[2]));
						device.setHMDpowerinfo(""+(String)objects2[4]);
					
					}
					else
					{
						device.setHMDalertValue("");
						device.setHMDpowerinfo("");
					
					}
				}
				}
//				for the icon feature	
				Icon icon = new Icon();
				icon.setFileName(IMonitorUtil.convertToString(strings[16]));
				device.setIcon(icon);
								
				device.setEnableStatus(IMonitorUtil.convertToString(strings[19]));
				device.setEnableList(IMonitorUtil.convertToString(strings[22]));	//for enabling listing of device on home screen.
				device.setPosIdx(Integer.parseInt((IMonitorUtil.convertToString(strings[23]))));	//Re-Ordering Of Device
				LocationMap map=new LocationMap();
				String Id = IMonitorUtil.convertToString(strings[26]);
				if(Id != null && !Id.equals(""))
				{
				map.setId(Integer.parseInt(Id));
				map.setTop(IMonitorUtil.convertToString(strings[24]));
				map.setLeftMap(IMonitorUtil.convertToString(strings[25]));
				}
				device.setLocationMap(map);
				Make make = new Make();
				String makeId = IMonitorUtil.convertToString(strings[20]);
				long makeIdLong = 0;
				if(makeId != null && !makeId.equals(""))
				{
					makeIdLong = Integer.parseInt(makeId);
				}	
				make.setId(makeIdLong);
				device.setMake(make);
					
				int count = Integer.parseInt(IMonitorUtil
						.convertToString(strings[13]));
				if (count > 0) {
					device.setRules(new ArrayList<Rule>());

				}
				AlertType lastAlert = new AlertType();
				lastAlert.setAlertCommand(IMonitorUtil
						.convertToString(strings[7]));
				device.setLastAlert(lastAlert);
				DeviceType deviceType = new DeviceType();
				deviceType.setDetails(IMonitorUtil.convertToString(strings[8]));
				deviceType
						.setIconFile(IMonitorUtil.convertToString(strings[9]));
				device.setDeviceType(deviceType);
				Location location = new Location();
				location.setName(IMonitorUtil.convertToString(strings[10]));
				location.setIconFile(IMonitorUtil.convertToString(strings[11]));
				device.setLocation(location);
				Status armStatus = new Status();
				armStatus.setName(IMonitorUtil.convertToString(strings[12]));
				device.setArmStatus(armStatus);
				
//				Camera Configuration.
				Long dcId =  (Long) strings[17];
				
				if(dcId != null)
				{
					DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
					
					if(device.getModelNumber().contains(Constants.H264Series)){
						H264CameraConfiguration h264CameraConfiguration = (H264CameraConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(h264CameraConfiguration);
					}else if (device.getModelNumber().contains(Constants.RC80Series)){
						CameraConfiguration cameraConfiguration = (CameraConfiguration) Dvconf.getDeviceConfigurationById(dcId);;
						device.setDeviceConfiguration(cameraConfiguration);
					}
					else if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
					{
						acConfiguration acConfiguration = (acConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(acConfiguration);
					}
					else{
						DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
						deviceConfiguration.setId(dcId);
						device.setDeviceConfiguration(deviceConfiguration);
					}
	
				}
				
				if (device.getModelNumber().equals("HMD"))
				{
					LogUtil.info("Model HMD not added to device List");
				}
				else 
				{
					gateWay.getDevices().add(device);
				}
				
			}
			/*for (GateWay gateWay : gateWays)
			{
				LogUtil.info("Gateway  --- > "+stream.toXML(gateWay));
			}*/
			String excludeGateWays = "";
			if (gateWays.size() > 0) {
				excludeGateWays = " and g not in (:varList)";
				
			}
			query = "select g.id, g.macId, s.name from "
					+ " GateWay as g join g.status as s"
					+ " where g.status=s.id and s.id not in(" + status1.getId()
					+ ")" + "and g.customer=" + customer.getId()
					+ excludeGateWays + "";
			
			try {
				dbc = new DBConnection();
				session = dbc.openConnection();
			q = session.createQuery(query);
			if (gateWays.size() > 0) {
				// q.setParameterList("statusList",statusList);
				q.setParameterList("varList", gateWays);
			}
			List<Object[]> newImvgs = q.list();
			for (Object[] objects2 : newImvgs) {
				GateWay gateWay = new GateWay();
				gateWay.setId(Long.parseLong(IMonitorUtil
						.convertToString(objects2[0])));
				gateWay.setMacId(IMonitorUtil.convertToString(objects2[1]));
				Status status = new Status();
				status.setName(IMonitorUtil.convertToString(objects2[2]));
				gateWay.setStatus(status);
				gateWays.add(gateWay);
			}
			}catch (Exception ex) {
				ex.printStackTrace();
			}finally {
				dbc.closeConnection();
			}
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbc.closeConnection();
		}

		return gateWays;
	}
	
	//SuperUserAdmin
public List<Device> singleCustomerDeviceDetailsForSuperUser(Customer customer) {
		
		DBConnection dbc = null;
		List<Device> devices = new ArrayList<Device>();
		List<GateWay> gateWays = new ArrayList<GateWay>();
		String query = "select "
				+ " g.id, g.macId, g.localIp "
				+ " , s.name "
				+ " , d.generatedDeviceId, d.friendlyName, d.batteryStatus, d.commandParam, la.alertCommand"
				+ " , dt.details, dt.iconFile " 
				+ " , l.name, l.iconFile"
				+ " ,ast.id, ast.name, d.enableStatus, i.fileName, d.modelNumber, d.enableList, g.currentMode, lm.top, lm.leftMap, lm.id ,dc.id "
				+ " , d.posIdx ,g.allOnOffStatus" 
				+ " , d.id,g.gateWayVersion"	
				+ " from Device as d " + " left join d.deviceType as dt "
				+ " left join d.lastAlert as la "
				+ " left join d.location as l " + " left join d.gateWay as g "
				+ " left join d.gateWay.status as s "
				+ " left join d.armStatus as ast"
				+ " left join d.locationMap as lm"
				+ " left join d.icon as i"
				+ " left join d.deviceConfiguration as dc"
				+ " left join g.customer as c" + " where c.customerId='"
				+ customer.getCustomerId() + "'" + "";
		List<Object[]> objects = null;
		try{
			dbc = new DBConnection();
			Session session = dbc.openConnection();
			Query q = session.createQuery(query);
			objects = q.list();
			Map<String, GateWay> gateWayMap = new HashMap<String, GateWay>();
			for (Object[] strings : objects) {
				
				
				
				String key = IMonitorUtil.convertToString(strings[0]);
				GateWay gateWay = gateWayMap.get(key);
				if (gateWay == null) {
					gateWay = new GateWay();
					gateWay.setId(Long.parseLong(key));
					gateWay.setMacId(IMonitorUtil.convertToString(strings[1]));
					String localIp = (String) strings[2];
					gateWay.setLocalIp(localIp);
					gateWay.setCurrentMode(IMonitorUtil.convertToString(strings[19]));
					Status status = new Status();
					status.setName(IMonitorUtil.convertToString(strings[3]));
					gateWay.setStatus(status);
					gateWay.setDevices(new HashSet<Device>());
					gateWay.setAllOnOffStatus((Long) strings[25]);
					gateWay.setGateWayVersion(IMonitorUtil.convertToString(strings[27]));
					gateWayMap.put(key, gateWay);
					gateWays.add(gateWay);
					
				}
				
				Device device = new Device();
				// sumit start : Jan 24, 2014 for Sub User restriction on MID
				device.setId(Long.parseLong(IMonitorUtil.convertToString(strings[26])));
				//sumit end
				device.setGeneratedDeviceId(IMonitorUtil
						.convertToString(strings[4]));
				device
						.setFriendlyName(IMonitorUtil
								.convertToString(strings[5]));
				device.setBatteryStatus(IMonitorUtil
						.convertToString(strings[6]));
				device
						.setCommandParam(IMonitorUtil
								.convertToString(strings[7]));
				AlertType lastAlert = new AlertType();
				lastAlert.setAlertCommand(IMonitorUtil
						.convertToString(strings[8]));
				device.setLastAlert(lastAlert);
				DeviceType deviceType = new DeviceType();
				deviceType.setDetails(IMonitorUtil.convertToString(strings[9]));
				deviceType.setIconFile(IMonitorUtil
						.convertToString(strings[10]));
				device.setDeviceType(deviceType);
				Location location = new Location();
				location.setName(IMonitorUtil.convertToString(strings[11]));
				location.setIconFile(IMonitorUtil.convertToString(strings[12]));
				device.setLocation(location);

				Status armStatus = null;
				Long armStatusId = (Long) strings[13];
				if (armStatusId != null) {
					armStatus = new Status();
					String armStatusName = (String) strings[14];
					armStatus.setId(armStatusId);
					armStatus.setName(armStatusName);
				} else {
					armStatus = IMonitorUtil.getStatuses()
							.get(Constants.DISARM);
				}
				device.setArmStatus(armStatus);

				String enableS = (String) strings[15];
				device.setEnableStatus(enableS);
				
//sumit begin	
				Icon icon = new Icon();
				icon.setFileName(IMonitorUtil.convertToString(strings[16]));
				device.setIcon(icon);
				String modelNumber = (String) strings[17];
				device.setModelNumber(modelNumber);
				
				device.setEnableList(IMonitorUtil.convertToString(strings[18]));
				//LogUtil.info("SK1");
				device.setPosIdx(Long.parseLong(IMonitorUtil.convertToString(strings[24])));
				//LogUtil.info("SK2");
//sumit end			
				LocationMap map=new LocationMap();
				String Id = IMonitorUtil.convertToString(strings[22]);
				if(Id != null && !Id.equals(""))
				{
				map.setId(Integer.parseInt(Id));
				//String top=IMonitorUtil.convertToString(strings[20]);
				//String left=IMonitorUtil.convertToString(strings[21]);
				
				map.setTop(IMonitorUtil.convertToString(strings[20]));
				map.setLeftMap(IMonitorUtil.convertToString(strings[21]));
				}
				
				Long dcId =  (Long) strings[23];
				
				if(dcId != null)
				{
					DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
					if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
					{
						
						acConfiguration acConfiguration = (acConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						
						device.setDeviceConfiguration(acConfiguration);
					}	
					else if(device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
					{
						IndoorUnitConfiguration indoorUnitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(dcId);
						device.setDeviceConfiguration(indoorUnitConfiguration);
						if(indoorUnitConfiguration.getConnectStatus() == 0){
							continue;
						}
					}
					else{
						DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
						deviceConfiguration.setId(dcId);
						device.setDeviceConfiguration(deviceConfiguration);
					}
				}

				device.setLocationMap(map);
				
				//devices.add(device);
				gateWay.getDevices().add(device);
	
			}
			String excludeGateWays = "";
			if (gateWays.size() > 0) {
				excludeGateWays = " and g not in (:varList)";
			}
			query = "select g.id, g.macId, s.name from "
					+ " GateWay as g join g.status as s"
					+ " join g.customer as c" + " where g.status=s.id "
					+ " and g.customer=c.id" + " and c.customerId='"
					+ customer.getCustomerId() + "'" + excludeGateWays + "";
			q = session.createQuery(query);
			if (gateWays.size() > 0) {
				q.setParameterList("varList", gateWays);
			}
			List<Object[]> newImvgs = q.list();
			for (Object[] objects2 : newImvgs) {
				GateWay gateWay = new GateWay();
				gateWay.setId(Long.parseLong(IMonitorUtil
						.convertToString(objects2[0])));
				gateWay.setMacId(IMonitorUtil.convertToString(objects2[1]));
				Status status = new Status();
				status.setName(IMonitorUtil.convertToString(objects2[2]));
				gateWay.setStatus(status);
				gateWays.add(gateWay);
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			LogUtil.info("Error : "+ ex.getMessage());
		} finally {
			dbc.closeConnection();
		}
		return devices;
	}


// Get Devices list with Mobile Icons
@SuppressWarnings("unchecked")
public List<GateWay> getGateWaysAndDeviceOfCustomerByCustomerIdWithMobileIcons(
		String customerId) {

	
	XStream stream = new XStream();
	DBConnection dbc = null;
	List<GateWay> gateWays = new ArrayList<GateWay>();
	// Using HQL for performance Optimization.

	String query = "select "
			+ " g.id, g.macId, g.localIp "
			+ " , s.name "
			+ " , d.generatedDeviceId, d.friendlyName, d.batteryStatus, d.commandParam, la.alertCommand"
			+ " , dt.details, dt.iconFile " 
			+ " , l.name, l.iconFile"
			//sumit begin
			//+ " ,ast.id, ast.name, d.enableStatus"
			//+ " ,ast.id, ast.name, d.enableStatus, i.fileName, d.modelNumber " 
			+ " ,ast.id, ast.name, d.enableStatus, i.fileName, d.modelNumber, d.enableList, g.currentMode, lm.top, lm.leftMap, lm.id ,dc.id "
			//for enable/disable listing of devices on MID.
			+ " , d.posIdx ,g.allOnOffStatus" //for device position in the list for a particular location
			+ " , d.id,g.gateWayVersion,l.MobileIconFile,l.id"	//#26 sumit added : required for filtering device for MID for sub users 
			//sumit end
			+ " from Device as d " + " left join d.deviceType as dt "
			+ " left join d.lastAlert as la "
			+ " left join d.location as l " + " left join d.gateWay as g "
			+ " left join d.gateWay.status as s "
			+ " left join d.armStatus as ast"
			+ " left join d.locationMap as lm"
			//sumit begin
			+ " left join d.icon as i"
			+ " left join d.deviceConfiguration as dc"
			//sumit end
			+ " left join g.customer as c" + " where c.customerId='"
			+ customerId + "'" + "";

	List<Object[]> objects = null;
	try {
		
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		objects = q.list();
		//LogUtil.info("objects " + stream.toXML(objects));
		Map<String, GateWay> gateWayMap = new HashMap<String, GateWay>();
		for (Object[] strings : objects) {
			String key = IMonitorUtil.convertToString(strings[0]);
			GateWay gateWay = gateWayMap.get(key);
			if (gateWay == null) {
				gateWay = new GateWay();
				gateWay.setId(Long.parseLong(key));
				gateWay.setMacId(IMonitorUtil.convertToString(strings[1]));
				String localIp = (String) strings[2];
				gateWay.setLocalIp(localIp);
				//LogUtil.info("current mode" + IMonitorUtil.convertToString(strings[19]));
				gateWay.setCurrentMode(IMonitorUtil.convertToString(strings[19]));
				Status status = new Status();
				status.setName(IMonitorUtil.convertToString(strings[3]));
				gateWay.setStatus(status);
				gateWay.setDevices(new HashSet<Device>());
				/**Aditya Added for get status of All on OFF of gateway**/
				gateWay.setAllOnOffStatus((Long) strings[25]);
				gateWay.setGateWayVersion(IMonitorUtil.convertToString(strings[27]));
				gateWayMap.put(key, gateWay);
				gateWays.add(gateWay);
				
			}
			Device device = new Device();
			// sumit start : Jan 24, 2014 for Sub User restriction on MID
			device.setId(Long.parseLong(IMonitorUtil.convertToString(strings[26])));
			//sumit end
			device.setGeneratedDeviceId(IMonitorUtil
					.convertToString(strings[4]));
			device
					.setFriendlyName(IMonitorUtil
							.convertToString(strings[5]));
			device.setBatteryStatus(IMonitorUtil
					.convertToString(strings[6]));
			device
					.setCommandParam(IMonitorUtil
							.convertToString(strings[7]));
			AlertType lastAlert = new AlertType();
			lastAlert.setAlertCommand(IMonitorUtil
					.convertToString(strings[8]));
			device.setLastAlert(lastAlert);
			DeviceType deviceType = new DeviceType();
			deviceType.setDetails(IMonitorUtil.convertToString(strings[9]));
			deviceType.setIconFile(IMonitorUtil
					.convertToString(strings[10]));
			device.setDeviceType(deviceType);
			Location location = new Location();
			location.setName(IMonitorUtil.convertToString(strings[11]));
			location.setIconFile(IMonitorUtil.convertToString(strings[12]));
			
			//Added code for changing deviceName from mobile
			location.setId(Long.parseLong(IMonitorUtil.convertToString(strings[29])));
			//Added code for changing deviceName from mobile end
			String licon = IMonitorUtil.convertToString(strings[28]);
			
			location.setMobileIconFile(licon);
			
			device.setLocation(location);
			Status armStatus = null;
			Long armStatusId = (Long) strings[13];
			if (armStatusId != null) {
				armStatus = new Status();
				String armStatusName = (String) strings[14];
				armStatus.setId(armStatusId);
				armStatus.setName(armStatusName);
			} else {
				armStatus = IMonitorUtil.getStatuses()
						.get(Constants.DISARM);
			}
			device.setArmStatus(armStatus);
			String enableS = (String) strings[15];
			device.setEnableStatus(enableS);
//sumit begin	
			Icon icon = new Icon();
			icon.setFileName(IMonitorUtil.convertToString(strings[16]));
			device.setIcon(icon);
			String modelNumber = (String) strings[17];
			device.setModelNumber(modelNumber);
			
			device.setEnableList(IMonitorUtil.convertToString(strings[18]));
			//LogUtil.info("SK1");
			device.setPosIdx(Long.parseLong(IMonitorUtil.convertToString(strings[24])));
			//LogUtil.info("SK2");
//sumit end			
			LocationMap map=new LocationMap();
			String Id = IMonitorUtil.convertToString(strings[22]);
			if(Id != null && !Id.equals(""))
			{
			map.setId(Integer.parseInt(Id));
			//String top=IMonitorUtil.convertToString(strings[20]);
			//String left=IMonitorUtil.convertToString(strings[21]);
			
			map.setTop(IMonitorUtil.convertToString(strings[20]));
			map.setLeftMap(IMonitorUtil.convertToString(strings[21]));
			}
			
			Long dcId =  (Long) strings[23];
			
			if(dcId != null)
			{
				DeviceConfigurationManager Dvconf=new DeviceConfigurationManager();
				if(device.getDeviceType().getDetails().equals(Constants.Z_WAVE_AC_EXTENDER))
				{
					
					acConfiguration acConfiguration = (acConfiguration) Dvconf.getDeviceConfigurationById(dcId);
					
					device.setDeviceConfiguration(acConfiguration);
				}	
				else if(device.getDeviceType().getDetails().equals(Constants.VIA_UNIT))
				{
					IndoorUnitConfiguration indoorUnitConfiguration=(IndoorUnitConfiguration) Dvconf.getDeviceConfigurationById(dcId);
					device.setDeviceConfiguration(indoorUnitConfiguration);
					if(indoorUnitConfiguration.getConnectStatus() == 0){
						continue;
					}
				}
				else{
					DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
					deviceConfiguration.setId(dcId);
					device.setDeviceConfiguration(deviceConfiguration);
				}
			}

			device.setLocationMap(map);
			gateWay.getDevices().add(device);
		}
		String excludeGateWays = "";
		if (gateWays.size() > 0) {
			excludeGateWays = " and g not in (:varList)";
		}
		query = "select g.id, g.macId, s.name from "
				+ " GateWay as g join g.status as s"
				+ " join g.customer as c" + " where g.status=s.id "
				+ " and g.customer=c.id" + " and c.customerId='"
				+ customerId + "'" + excludeGateWays + "";
		q = session.createQuery(query);
		if (gateWays.size() > 0) {
			q.setParameterList("varList", gateWays);
		}
		List<Object[]> newImvgs = q.list();
		for (Object[] objects2 : newImvgs) {
			GateWay gateWay = new GateWay();
			gateWay.setId(Long.parseLong(IMonitorUtil
					.convertToString(objects2[0])));
			gateWay.setMacId(IMonitorUtil.convertToString(objects2[1]));
			Status status = new Status();
			status.setName(IMonitorUtil.convertToString(objects2[2]));
			gateWay.setStatus(status);
			gateWays.add(gateWay);
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		dbc.closeConnection();
	}
	
	
	return gateWays;
	
}

//OTP Self Registration
public OTPForSelfRegistration getOTPObjectByMacId(String macId) {
	String query = "select o from OTPForSelfRegistration o where o.macId='"
			+ macId + "'";
	DBConnection dbc = null;
	OTPForSelfRegistration otpForSelfRegistration = null;

	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		otpForSelfRegistration = (OTPForSelfRegistration) q.uniqueResult();
	} catch (Exception ex) {
		ex.printStackTrace();
		LogUtil.info("Error fetchinggg : " + ex.getMessage());
	} finally {
		dbc.closeConnection();
	}
	return otpForSelfRegistration;
}
@SuppressWarnings("unchecked")
public Customer retrieveCustomerDeviceAlertAndActionsAndNoticiationProfiles(String customerId) {
	Customer customer = new Customer();
	customer.setCustomerId(customerId);
	String query = "select g.id,g.macId," + " d.id, d.friendlyName," + " act.id, act.name,"
			+ " at.id, at.name, d.modelNumber,dt.name, at.details, act.details, d.generatedDeviceId ,c.featuresEnabled ,m.id,g.name from Customer as c" // vibhu
																																						// added
																																						// dt.name,
																																						// at.details,
																																						// act.details(9,10,11)
			+ " left join c.gateWays as g" + " left join g.devices as d" + " left join d.deviceType as dt"
			+ " left join d.make as m " + " left join dt.alertTypes as at" + " left join dt.actionTypes as act"
			+ " where c.customerId='" + customerId + "'";
	List<Object[]> detailsArray = null;
	DBConnection dbc = null;
	try {
		dbc = new DBConnection();
		Session session = dbc.openConnection();
		Query q = session.createQuery(query);
		detailsArray = q.list();

		Set<GateWay> gateWays = new HashSet<GateWay>();
		for (Object[] details : detailsArray) {
			Long gateWayId = (Long) details[0];
			String macId = IMonitorUtil.convertToString(details[1]);
			// 3gp
			String gatewayName = IMonitorUtil.convertToString(details[15]);
			Long deviceId = (Long) details[2];
			if (deviceId == null) {
				continue;
			}
			String friendlyName = IMonitorUtil.convertToString(details[3]);
			Long actionTypeId = (Long) details[4];
			String actionTypeName = IMonitorUtil.convertToString(details[5]);
			Long alertTypeId = (Long) details[6];
			String alertTypeName = IMonitorUtil.convertToString(details[7]);
			String modelNumber = IMonitorUtil.convertToString(details[8]); // sumit: getting modelNumber based for
																			// ZXT120
			String deviceTypeName = IMonitorUtil.convertToString(details[9]); // vibhu added
			String alertTypeDetails = IMonitorUtil.convertToString(details[10]); // vibhu added
			String actionTypeDetails = IMonitorUtil.convertToString(details[11]); // vibhu added
			String generatedDeviceId = (String) details[12];// pari added to get device type name
			long FeatureEnabled = (Long) details[13];

			GateWay gateWay = null;
			for (GateWay gateWay1 : gateWays) {
				if (gateWay1.getId() == gateWayId) {
					gateWay = gateWay1;
					break;
				}
			}
			if (gateWay == null) {
				gateWay = new GateWay();
				gateWay.setId(gateWayId);
				gateWay.setMacId(macId);
				gateWay.setName(gatewayName);

				gateWays.add(gateWay);
			}
			Set<Device> devices = gateWay.getDevices();
			if (devices == null) {
				devices = new HashSet<Device>();
				gateWay.setDevices(devices);
			}

			// Finding/Creating the device.
			Device device = null;
			for (Device device1 : devices) {
				if (device1.getId() == deviceId) {
					device = device1;
					break;
				}
			}
			if (device == null) {
				device = new Device();
				device.setId(deviceId);
				device.setFriendlyName(friendlyName);
				device.setModelNumber(modelNumber);// sumit: setting modelNumber for ZXT120

				devices.add(device);

			}

			DeviceType deviceType = device.getDeviceType();

			if (deviceType == null) {
				deviceType = new DeviceType();
				List<ActionType> actionTypes = new ArrayList<ActionType>();
				deviceType.setActionTypes(actionTypes);
				List<AlertType> alertTypes = new ArrayList<AlertType>();
				deviceType.setAlertTypes(alertTypes);
				deviceType.setName(deviceTypeName); // vibhu added
				device.setDeviceType(deviceType);
			}
			deviceType.setName(deviceTypeName); // pari added to get the name of device

			// creating/finding actions.
			if (actionTypeId != null) {

				List<ActionType> actionTypes = deviceType.getActionTypes();
				ActionType actionType = null;
				for (ActionType actionType1 : actionTypes) {
					if (actionType1.getId() == actionTypeId) {
						actionType = actionType1;
						break;
					}
				}
				if (actionType == null) {
					actionType = new ActionType();
					actionType.setId(actionTypeId);
					actionType.setName(actionTypeName);
					actionType.setDetails(actionTypeDetails); // vibhu added
					actionTypes.add(actionType);
				}
			}
			// creating/finding alerts.
			if (alertTypeId != null) {

				List<AlertType> alertTypes = deviceType.getAlertTypes();
				AlertType alertType = null;
				for (AlertType alertType1 : alertTypes) {
					if (alertType1.getId() == alertTypeId) {
						alertType = alertType1;
						break;
					}
				}
				if (alertType == null) {
					alertType = new AlertType();
					alertType.setId(alertTypeId);
					alertType.setName(alertTypeName);
					alertType.setDetails(alertTypeDetails); // vibhu added
					if ((FeatureEnabled & 1) == 1) {
						if (alertTypeName.equals("HMD_WARNING") || alertTypeName.equals("HMD_NORMAL")
								|| alertTypeName.equals("HMD_FAILURE")
								|| alertTypeName.equals("POWER_INFORMATION")) {
							if (modelNumber.equals("HMD"))
								alertTypes.add(alertType);
						} else {
							alertTypes.add(alertType);
						}
					} else {
						if (!alertTypeName.contains("HMD_WARNING") && !alertTypeName.contains("HMD_NORMAL")
								&& !alertTypeName.contains("HMD_FAILURE")
								&& !alertTypeName.contains("POWER_INFORMATION")) {
							alertTypes.add(alertType);
						}
					}

				}

			}
		}
		customer.setGateWays(gateWays);

		// Now filling the user notification profiles.
		query = "select u.id, u.name, u.recipient, "
				+ " at.id, at.name, at.command, at.details,s.id,s.name,u.whatsApp,u.whatsAppStatus"
				+ " from UserNotificationProfile as u" + " left join u.actionType as at"
				+ " left join u.status as s" + " left join u.customer as c" + " where c.customerId='" + customerId
				+ "'";
		List<Object[]> results = null;
		q = session.createQuery(query);
		results = q.list();
		Set<UserNotificationProfile> userNotificationProfiles = new HashSet<UserNotificationProfile>();
		for (Object[] objects : results) {
			Long uId = (Long) objects[0];
			String uName = (String) objects[1];
			String uReceipient = (String) objects[2];

			Long atId = (Long) objects[3];
			String atName = (String) objects[4];
			String atCommand = (String) objects[5];
			String atDetails = (String) objects[6];
			// Naveen added on 22 dec 2015, For new SMS notification integration
			Long sId = (Long) objects[7];
			String sName = (String) objects[8];
			String whatsApp = IMonitorUtil.convertToString(objects[9]);
			int wStatus = Integer.parseInt(IMonitorUtil.convertToString(objects[10]));
			Status status = new Status();
			status.setId(sId);
			status.setName(sName);
			UserNotificationProfile notificationProfile = new UserNotificationProfile();
			notificationProfile.setId(uId);
			notificationProfile.setName(uName);
			notificationProfile.setRecipient(uReceipient);

			ActionType actionType = new ActionType();
			actionType.setId(atId);
			actionType.setName(atName);
			actionType.setCommand(atCommand);
			actionType.setDetails(atDetails);
			notificationProfile.setActionType(actionType);
			notificationProfile.setWhatsApp(whatsApp);
			notificationProfile.setStatus(status);
			notificationProfile.setWhatsAppStatus(wStatus);
			userNotificationProfiles.add(notificationProfile);
		}
		customer.setUserNotificationProfiles(userNotificationProfiles);
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != dbc) {
			dbc.closeConnection();
		}
	}

	return customer;
}


/*public boolean loginsuperUserpasscheckapi(String password,String custid){
	LogUtil.info("loginsuperUserpasscheckapi =="+password+" "+custid);
	XStream stream = new XStream();
	DBConnection dbc = null;
	boolean result = false;
	String query = "select c.customerId,u.password from user as u left join customer as c on c.id=u.customer where c.customerId='"+custid+"' and u.userId='"+userId+"' and u.password='"+password+"'";
	LogUtil.info("loginpasscheckapi query="+query);
	try {
		
		dbc = new DBConnection();
		dbc.openConnection();
		Session session = dbc.getSession();
		Query q = session.createSQLQuery(query);
		Object[] object=(Object[]) q.uniqueResult();
		if(object!=null){
			result=true;
		}else{
			result=false;
			}
	}catch (Exception e) {
		// TODO: handle exception
	}finally {
		dbc.closeConnection();
	}
	LogUtil.info("result="+result);
	return result;
}*/

}




