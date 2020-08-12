/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.cms.action.mainuser;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.AdminSuperUser;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Customer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerPasswordHintQuestionAnswer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ForgotPasswordHintQuestion;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Role;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Scenario;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Status;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserDeviceAccess;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.subUserScenarioAccess;
import in.xpeditions.jawlin.imonitor.general.DataTable;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class UserManagementAction extends ActionSupport {
	
	/**
	 * @author King Asmodeus
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private String password;
	private String oldPassword;
	private String confirmPassword;
	private List<Role> roles;
	private List<Status> statuses;
	private DataTable dataTable;
	private CustomerPasswordHintQuestionAnswer hintQuestionAnswer;
	private List<ForgotPasswordHintQuestion> hintQuestionList;
	private String email;
	private String mobile;
	private String emailFromDB;
	private long selectedQuestionId;
	private String hintAnswer;
	private String hintAnswerFromDB;
	private String hintQuestionFromDB;
	private List<Device> deviceList;
	private List<Scenario> scenarioList;
	private List<subUserScenarioAccess> subuserScenarioList;
	private List<subUserDeviceAccess> subuserDeviceList;
	private String devicesChecked;
	private String scenariosChecked;
	private int deviceCount;
	private int scenarioCount;
	private GateWay gateway;
    private String macID;
    private String deviceToDelete;
    private String zWaveVersion;
    private String homeId;
    private String nodeId;
    private String chipVersion;
    private String chipType;
    private String chipFlag;
    private String numbOfNodes;
    private String zWaveSeries;

    private Date expiredate;
    private String expiretime;
    private String tempPassword;
    private String smsToSend;
    private String emailToSend;
    private String type;
    private String midPort ;
    private String midLoginPort;
    private String lftpDownload;
    private List<String> userTips;
   
    //3gp
    private String gateId;

    //Admin SuperUser
    private AdminSuperUser superuser;
	
	
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String toChangeUserPassword(){
		return SUCCESS;
	}
	public String changePasswordByAdmin() throws Exception{
		XStream stream = new XStream();
		String result = ERROR;
		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String hashPassword = IMonitorUtil.getHashPassword(oldPassword);
		if(user.getPassword().equals(hashPassword)){
			if(password.equals(confirmPassword)){
				String confirmPasswordxml = stream.toXML(confirmPassword);
				 String xml = stream.toXML(user);
				 String resultXml = IMonitorUtil.sendToController("userService", "changePasswordByAdmin",xml,confirmPasswordxml);
				 String resultString = (String) stream.fromXML(resultXml);
				 if(resultString.equals("yes")){
					 ActionContext.getContext().getSession().put("message", "password changed successfully");
					 result = SUCCESS;
				 }
			}
			else{
				ActionContext.getContext().getSession().put("message", "Passwords are not matching.");
				result= ERROR;
			}
		}
		else{
			ActionContext.getContext().getSession().put("message", "Invalid password. Please try again.");
			result= ERROR;
		}
		return result;
	}
	
	public String changePassword() throws Exception{
		XStream stream = new XStream();
		String message="";
		String result = ERROR;
		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String hashPassword = IMonitorUtil.getHashPassword(oldPassword);
		try {
			if(user.getPassword().equals(hashPassword)){
				if(password.equals(confirmPassword)){
					String confirmPasswordxml = stream.toXML(confirmPassword);
					 String xml = stream.toXML(user);
					 String resultXml = IMonitorUtil.sendToController("userService", "changePassword",xml,confirmPasswordxml);
					 String resultString = (String) stream.fromXML(resultXml);
                   
					 if(resultString.equals("yes")){
						
						 message = IMonitorUtil.formatMessage(this, "msg.imonitor.changepwd.0001");
						
					 }else if(resultString.equals("Actionfailure")){
						 message = IMonitorUtil.formatMessage(this, "msg.imonitor.changepwd.0004");
						
					 }else if(resultString.equals("imvg_failure")){
						 message = IMonitorUtil.formatMessage(this, "msg.imonitor.changepwd.0005");
						
					 }
				}
				else{
					 message = IMonitorUtil.formatMessage(this, "msg.imonitor.changepwd.0002");
					result= ERROR;
				}
			}
			else{
				 message = IMonitorUtil.formatMessage(this, "msg.imonitor.changepwd.0003");
				result= ERROR;
			}
		} catch (Exception e) {
			LogUtil.info("Caught Exception while changing password : ", e);
		}
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	
	// **************************************** sumit start: forgot password user authentication **************************************************
//	//CALLED FROM editCustomer.JSP
//	public String toValidatePassword(){
//		LogUtil.info("toValidatePassword: IN");
//		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
//		//password = (String) ActionContext.getContext().getSession().get(password);
//		LogUtil.info("test1");
//		String hashPassword = IMonitorUtil.getHashPassword(password);
//		LogUtil.info("test2");
//		String oldPass = user.getPassword();
//		LogUtil.info("hashPassword: "+hashPassword+", oldpass: "+oldPass);	
//		if(oldPass.equals(hashPassword)){
//			LogUtil.info("toValidatePassword: OUT");
//			return SUCCESS;
//		}
//		LogUtil.info("toValidatePassword: OUT");
//		ActionContext.getContext().getSession().put("message", "Failure:Invalid Password. You cannot proceed without a valid password.");
//		return ERROR;
//	}
	
	//CALLED FROM editCustomer.JSP
	@SuppressWarnings("unchecked")
	public String toConfigureResetPasswordInfo() throws Exception {
		XStream stream = new XStream();
		String message ="";
		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String hashPassword = IMonitorUtil.getHashPassword(password);
		String oldPass = user.getPassword();
		if(!oldPass.equals(hashPassword)){
			 message = IMonitorUtil.formatMessage(this, "msg.imonitor.configpwd.0001");
				ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:Invalid Password. You cannot proceed without a valid password.");
			return ERROR;
		}
		
		//1.Get the previously selected questionId along with the Answer for the customer.
		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		long cId = user.getCustomer().getId();
		String cIdXml = stream.toXML(cId);
		String hintQuestionAnswerXml = IMonitorUtil.sendToController("customerService", "getForgotPasswordQuestionsWithAnswer", cIdXml);
		
		hintQuestionAnswer = (CustomerPasswordHintQuestionAnswer) stream.fromXML(hintQuestionAnswerXml);
		selectedQuestionId = hintQuestionAnswer.getQuesId();
		hintAnswerFromDB = hintQuestionAnswer.getHintAnswer();
		hintQuestionFromDB = hintQuestionAnswer.getHintQuestion();
		
		//2.Get the List of Reset Password Hint Questions from the DB.
		String serviceName = "userService";
		String method = "listResetPasswordHintQuestions";
		String hintQuestionListFromDB = IMonitorUtil.sendToController(serviceName, method);
		hintQuestionList = (List<ForgotPasswordHintQuestion>) stream.fromXML(hintQuestionListFromDB);
		return SUCCESS;
	}
	
	//CALLED FROM ToConfigureResetPasswordInfo.JSP
	@SuppressWarnings("unused")
	public String saveResetPasswordInfo() throws Exception {
		XStream stream = new XStream(); 
		String message ="";
		String result = ERROR;
		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		long cId = user.getCustomer().getId();
		String customerId = user.getCustomer().getCustomerId();
		String cIdXml = stream.toXML(cId);
		String custIdXml = stream.toXML(customerId);
		
		//1.Validate the oldPassword of the customer.
		
		String hashPassword = IMonitorUtil.getHashPassword(oldPassword);
		if(user.getPassword().equals(hashPassword)){
			
			//2.Get the email for the main user for user validation during configuring of Forgot Password Reset Info.
			//String emailXml = IMonitorUtil.sendToController("customerService", "getEmailOfCustomer", cIdXml, custIdXml);
			emailFromDB = user.getEmail();//(String) stream.fromXML(emailXml);
					
			//3.Validate the email for the main user.
			if(email.equalsIgnoreCase(emailFromDB)){
				
				//4.Get the Question ID and the Answer set by the customer.
				String quesIdXml = stream.toXML(selectedQuestionId);
				String answerXml = stream.toXML(hintAnswer.toLowerCase());
				String serviceName = "customerService";
				String method ="saveResetPasswordInfo";
				
				//5.Save the answer corresponding to quesId in the DB.
				String resultXml = IMonitorUtil.sendToController(serviceName, method, cIdXml, quesIdXml, answerXml);
				String result1 = (String) stream.fromXML(resultXml);
				if(result1.equalsIgnoreCase("success")){
					message = IMonitorUtil.formatMessage(this, "msg.imonitor.configResetpwd.0001");
					ActionContext.getContext().getSession().put("message", message);
					//ActionContext.getContext().getSession().put("message", "Sucess~Password rest Info successfully configured.");
					result = SUCCESS;
				}else if(result1.equalsIgnoreCase("failure")){
					message = IMonitorUtil.formatMessage(this, "msg.imonitor.configResetpwd.0002");
					ActionContext.getContext().getSession().put("message", message);
					//ActionContext.getContext().getSession().put("message", "Failure~Password Reset Info could not be configured due to some internal error.");
					result = ERROR;
				}
				
			}else{
				
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.configResetpwd.0003");
				ActionContext.getContext().getSession().put("message", message);
				//ActionContext.getContext().getSession().put("message", "Failure~Invalid email, Please try again !!!");
				result= ERROR;
			}
		}else{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.configResetpwd.0004");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure~Invalid Password, Please try again !!!");
			result= ERROR;
		}		
		return result;
	}
	
	//CALLED FROM LOGIN PAGE
	public String forgotPasswordAssistance() throws Exception{
		XStream stream = new XStream();
		String message ="";
		String result = ERROR;
		String userXml = stream.toXML(user);
		String userId = user.getUserId();
		
		//1.Check if the user exists for the specified userId.
		String resultXml1 = IMonitorUtil.sendToController("userService", "toValidateUser", userXml);
		if(resultXml1 == null){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.forgotpwdAssistance.0001");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:User with the given name does not exist.");
			return result;
		}else if(resultXml1.equalsIgnoreCase("invalidcustomerid")){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.configResetpwd.0002");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:Invalid customer ID. Please enter a valid customer ID and try again.");
			return result;
		}else if(resultXml1.equalsIgnoreCase("yes")){
		
			//2.Get the details of the user(User exists with specified userId).
			String userFromDBXml = IMonitorUtil.sendToController("userService", "getUserForPasswordChange", userXml);
			User userFromDB = (User) stream.fromXML(userFromDBXml);
			
			//3.Check the role name of the user.Only main user can proceed further to change his/her password.
			String roleName = userFromDB.getRole().getName();
			if(roleName.equalsIgnoreCase(Constants.NORMAL_USER)){
				//message = IMonitorUtil.formatMessage(this, "msg.imonitor.configResetpwd.0004");
				//ActionContext.getContext().getSession().put("message", message);
				ActionContext.getContext().getSession().put("message", "Failure:You are not authorized to change your password."
															+" Please contact the main user.");
				result = ERROR;
			}else if (roleName.equalsIgnoreCase(Constants.MAIN_USER)) {
				this.user = userFromDB;
				this.user.setUserId(userId);
			
				//4.Get the Password HINT question-answer from the DB based on the customer. (At most 1 question with its answer)
				String customerIdXml = stream.toXML(userFromDB.getCustomer().getId());
				String resultXml= IMonitorUtil.sendToController("customerService", "getForgotPasswordQuestionsWithAnswer", customerIdXml);
				hintQuestionAnswer = (CustomerPasswordHintQuestionAnswer) stream.fromXML(resultXml); 
				this.hintQuestionFromDB = hintQuestionAnswer.getHintQuestion();
				this.hintAnswerFromDB = hintQuestionAnswer.getHintAnswer();
				result = SUCCESS;
			}
		}
		return result;
	}
	
	public String resetPassword() throws Exception{
		String result = ERROR;
		XStream stream = new XStream();
		String message ="";
		String userXml = stream.toXML(user);

		//1.Get User details to cross check mobile & email
		String userFromDBXml = IMonitorUtil.sendToController("userService", "getUserById", stream.toXML(user.getId()));
		User userFromDB = (User) stream.fromXML(userFromDBXml);
		String emailOfUser = userFromDB.getEmail();
		String mobileOfUser = userFromDB.getMobile();
				
		//2.Get customer details to cross check hint answer
		String customerWithHintAnswer = IMonitorUtil.sendToController("customerService", "getForgotPasswordQuestionsWithAnswer", 
				stream.toXML(user.getCustomer().getId()));
		hintQuestionAnswer = (CustomerPasswordHintQuestionAnswer) stream.fromXML(customerWithHintAnswer);
		hintAnswerFromDB = hintQuestionAnswer.getHintAnswer();
		if((email.equalsIgnoreCase(emailOfUser)) && (mobile.equalsIgnoreCase(mobileOfUser))
				&& (hintAnswerFromDB == null || hintAnswer.equalsIgnoreCase(hintAnswerFromDB))){
			user.setPassword(confirmPassword);
			String resultXml1 = IMonitorUtil.sendToController("userService", "resetPassword",userXml);
			User user = (User) stream.fromXML(resultXml1);
			Device device = null;
			String serviceName2 = "userService";
			String method2= "getMainUserAndSendSmsAndEmailing";
			
			String customerIdXml = stream.toXML(user.getCustomer().getId());
			String messageXml = stream.toXML("Password is reseted for ");
			String actionXml = stream.toXML(Constants.LOG_IN);
			String deviceXml = stream.toXML(device);
			String resultXml2 =  IMonitorUtil.sendToController(serviceName2, method2, customerIdXml, messageXml, actionXml, deviceXml);
			String isPasswordSet = (String) stream.fromXML(resultXml2);
			if(isPasswordSet.equalsIgnoreCase("yes")){
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.Resetpwd.0001");
				ActionContext.getContext().getSession().put("message", message);
				//ActionContext.getContext().getSession().put("message", "Success:New password successfully send to your mail and mobile");
				result = SUCCESS;
			}
			
		}else{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.Resetpwd.0002");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:Credentials provided were incorrect. Please try again with correct inputs.");
			result = ERROR;
		}
		return result;
	}
	

	public String listAskedUsers() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.dataTable);
		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		long id = user.getCustomer().getId();
		xmlString += "-" + stream.toXML(id);
		String serviceName = "userService";
		String method = "listAskedUsers";
		String resultXml = IMonitorUtil.sendToController(serviceName, method, xmlString);
		this.dataTable = (DataTable) stream.fromXML(resultXml);
		return SUCCESS;
	}
	
	public String toAddUser() throws Exception {
		return SUCCESS;
	}
	
	public String addUser() throws Exception {
		//LogUtil.info("start add user");
			XStream stream = new XStream();
			String message ="";
			String roleXml = stream.toXML(Constants.NORMAL_USER);
			String valueInXml = IMonitorUtil.sendToController("roleService", "getRoleByRoleName", roleXml);
			Role role = (Role) stream.fromXML(valueInXml);
			user.setRole(role);
			
			String statusXml = stream.toXML(Constants.OFFLINE);
			valueInXml = IMonitorUtil.sendToController("statusService", "getStatusByName", statusXml);
			Status status = (Status) stream.fromXML(valueInXml);
			user.setStatus(status);
			
			User superUser = (User) ActionContext.getContext().getSession().get(Constants.USER);
			user.setCustomer(superUser.getCustomer());
			//bhavya bug 543
			long id = user.getCustomer().getId();
			String xmlString =""+stream.toXML(id);
			String countuserXml = IMonitorUtil.sendToController("userService","CountUserByCustomer",xmlString);
			long count=(Long) stream.fromXML(countuserXml);
			//bhavya bug 543
			if(count>=5)
			{
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.addusercount.0001");
				ActionContext.getContext().getSession().put("message", message);	
			}
			else
			{
			String hashPassword = IMonitorUtil.getHashPassword(this.user.getPassword());
			this.user.setPassword(hashPassword);
			String xml = stream.toXML(this.user);
			//LogUtil.info("xml---hhh-"+xml);
			String serviceName = "userService";
			String method = "attatchCustomerAndSaveUser";
			String resultXml = IMonitorUtil.sendToController(serviceName, method, xml);
			String result = (String) stream.fromXML(resultXml);
			//LogUtil.info("result----"+result);
			if(result.equals("no")){
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.adduser.0001");
				ActionContext.getContext().getSession().put("message", message);
				//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
				return ERROR;
			}
			else if(result.equals("yes")){
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.addusersuccess.0001"+Constants.MESSAGE_DELIMITER_1+user.getUserId());
				ActionContext.getContext().getSession().put("message", message);
				String xmlMessage = stream.toXML("User ID: "+user.getUserId()+" is Added ");
				user = (User) ActionContext.getContext().getSession().get(Constants.USER);
				Device device = null;
				//sendEmailAndSms(stream.toXML(Constants.ADD_USER),xmlMessage,stream.toXML(device),stream.toXML(user.getCustomer().getId()));
			//	ActionContext.getContext().getSession().put("message", "Successfully saved");
			}
			else if(result.equals("imvg_failure"))
			{
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.adduser.0002");
				ActionContext.getContext().getSession().put("message", message);
				//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
				return ERROR;
			}
			else if(result.equals("Actionfailure"))
			{
				message = IMonitorUtil.formatMessage(this, "msg.imonitor.adduser.0003");
				ActionContext.getContext().getSession().put("message", message);
				//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
				return ERROR;
			}
			
			}
		return SUCCESS;
	}
	
	// ************************************* sumit start : Sub-User Restriction *************************************
	/**
	 * @author sumit kumar
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String toCustomizeSubUserAccess() throws Exception{
		
		XStream stream = new XStream();
		String serviceName = "";
		String methodName = "";
		User mainUser = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String customerIdXml = stream.toXML(mainUser.getCustomer().getCustomerId());
		long subUserId = user.getId();
		String subUserIdXml = stream.toXML(subUserId);
		
		
		//1. Get Customer for the Main User.
		serviceName = "customerService";
		methodName = "getCustomerByCustomerId";
		String customerXml = IMonitorUtil.sendToController(serviceName, methodName, customerIdXml);
		Customer customer = (Customer) stream.fromXML(customerXml);
		
		//2. Get List of Devices
		serviceName = "customerService";
		methodName = "listAllDevicesForAllGateWaysOfCustomerByCustomerId";
		String devicesXml = IMonitorUtil.sendToController(serviceName, methodName, customerIdXml);
		this.deviceList = (List<Device>) stream.fromXML(devicesXml);
		this.deviceCount = this.deviceList.size();
		
		
		//3. Get List of Scenarios.
		serviceName = "scenarioService";
		methodName = "listAllScnarios";
		String scenariosXml = IMonitorUtil.sendToController(serviceName, methodName, stream.toXML(customer.getId()));
		this.scenarioList = (List<Scenario>) stream.fromXML(scenariosXml);
		this.scenarioCount = this.scenarioList.size();
		
		//4. Get the Sub User Device Access Configuration details.
		serviceName = "userService";
		methodName = "listDevicesForSubuser";
		String subuserDevicesXml = IMonitorUtil.sendToController(serviceName, methodName, subUserIdXml);
		this.subuserDeviceList = (List<subUserDeviceAccess>) stream.fromXML(subuserDevicesXml);
		
		//5. Get the Sub User Scenario Access Configuration details.
		serviceName = "userService";
		methodName = "listScenariosForSubuser";
		String subuserScenariosXml = IMonitorUtil.sendToController(serviceName, methodName, subUserIdXml);
		this.subuserScenarioList = (List<subUserScenarioAccess>) stream.fromXML(subuserScenariosXml);
				
		return SUCCESS;
	}
	
	public String customizeSubUserAccess() throws Exception{
		XStream stream = new XStream();
		String message = "";
		String serviceName = "";
		String methodName = "";
		long subUserId = user.getId();
		String subuserIdXml = stream.toXML(subUserId);
		
		//1. Get Sub User Details.
		serviceName = "userService";
		methodName = "getUserById";
		String userXML = IMonitorUtil.sendToController(serviceName, methodName, subuserIdXml);
		User userFromDB = (User) stream.fromXML(userXML);
		
		//2. Save accessible Devices and Scenarios List for Sub User.
		String subUserDevicesXml = stream.toXML(this.devicesChecked);
		String subUserScenariosXml = stream.toXML(this.scenariosChecked);
		serviceName = "userService";
		methodName = "customizeSubUserAccess";
		IMonitorUtil.sendToController(serviceName, methodName, subuserIdXml, subUserDevicesXml, subUserScenariosXml);
		message = IMonitorUtil.formatMessage(this, "msg.imonitor.updatedevicesandscenariosforsubusersuccess.0001"+Constants.MESSAGE_DELIMITER_1+userFromDB.getUserId());
		ActionContext.getContext().getSession().put("message", message);
		return SUCCESS;
	}
	//************************************* sumit start : Sub-User Restriction *************************************

	public String toEditUser() throws Exception{
		long id = user.getId();
		XStream stream = new XStream();
		String xml = stream.toXML(id);
		String valueInXml = IMonitorUtil.sendToController("userService", "getUserById", xml);
		this.user = (User) stream.fromXML(valueInXml);
		return SUCCESS;
	}
	
	
	public String editUser() throws Exception{
		
		XStream stream = new XStream();
		String message ="";
		
		if(this.user.getPassword() != null && !this.user.getPassword().equals(""))
		{
			String hashPassword = IMonitorUtil.getHashPassword(this.user.getPassword());
			this.user.setPassword(hashPassword);
		}
		String xml = stream.toXML(user);
		String resultXml = IMonitorUtil.sendToController("userService", "updateUser", xml);
		String result = (String) stream.fromXML(resultXml);
		if(result.equals("no")){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.editUser.0001");
			ActionContext.getContext().getSession().put("message", message);
			return ERROR;
		}
		else if(result.equals("yes")){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.updateusersuccess.0001"+Constants.MESSAGE_DELIMITER_1+this.user.getUserId());
			ActionContext.getContext().getSession().put("message", message);
			return SUCCESS;
		}
		else if(result.equals("imvg_failure"))
		{
			//LogUtil.info("imvg_failure inside");
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.editUser.0002");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return SUCCESS;
		}
		else if(result.equals("Actionfailure"))
		{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.editUser.0003");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String toEditAdminUser() throws Exception{
		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		long id = user.getId();
		XStream stream = new XStream();
		String xml = stream.toXML(id);
		String valueInXml = IMonitorUtil.sendToController("userService", "getUserById", xml);
		this.user = (User) stream.fromXML(valueInXml);
		return SUCCESS;
	}
	
	public String editAdminUser() throws Exception{
		XStream stream = new XStream();
		String hashPassword = IMonitorUtil.getHashPassword(this.user.getPassword());
		this.user.setPassword(hashPassword);
		String xml = stream.toXML(user);
		IMonitorUtil.sendToController("userService", "updateUser", xml);
		ActionContext.getContext().getSession().put("message","Sucess~Sucessfully saved ");
		return SUCCESS;
	}
	public String suspendUser() throws Exception{
		long id = user.getId();
		XStream stream = new XStream();
		String message ="";
		String xml = stream.toXML(id);
		String valueInXml = IMonitorUtil.sendToController("userService", "getUserById", xml);
		this.user = (User) stream.fromXML(valueInXml);
		
		String result = IMonitorUtil.sendToController("userService", "suspendUser", xml);
		String xmlMessage = stream.toXML("User ID: "+user.getUserId()+" is Suspended ");
		if(result.equals("yes")){
			user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Device device = null;
			//sendEmailAndSms(stream.toXML(Constants.SUSPEND_USER),xmlMessage,stream.toXML(device),stream.toXML(user.getCustomer().getId()));
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.suspenduser.0001");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "successfully suspended");
			return SUCCESS;
		}
		else if(result.equals("imvg_failure"))
		{
			//LogUtil.info("imvg_failure inside");
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.suspendUser.0002");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return SUCCESS;
		}
		else if(result.equals("Actionfailure"))
		{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.suspendUser.0003");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return ERROR;
		}
		else if(result.equals("no")){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.suspendUser.0004");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return ERROR;
		}
		return "failure";
	}
	public String revokeUser() throws Exception{
		long id = user.getId();
		XStream stream = new XStream();
		String message ="";
		String xml = stream.toXML(id);
		String valueInXml = IMonitorUtil.sendToController("userService", "getUserById", xml);
		this.user = (User) stream.fromXML(valueInXml);
		String result = IMonitorUtil.sendToController("userService", "revokeUser", xml);
		@SuppressWarnings("unused")
		String xmlMessage = stream.toXML("User ID: "+user.getUserId()+" is Revoked ");
		if(result.equals("yes")){
			user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			@SuppressWarnings("unused")
			Device device = null;
			//sendEmailAndSms(stream.toXML(Constants.SUSPEND_USER),xmlMessage,stream.toXML(device),stream.toXML(user.getCustomer().getId()));
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.Revokeuser.0001");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "successfully revoked");
			return SUCCESS;
		}
		else if(result.equals("imvg_failure"))
		{
			//LogUtil.info("imvg_failure inside");
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.revokeUser.0002");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return SUCCESS;
		}
		else if(result.equals("Actionfailure"))
		{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.revokeUser.0003");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return ERROR;
		}
		else if(result.equals("no")){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.revokeUser.0004");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return ERROR;
		}
		return "failure";
	}
	public String deleteUser() throws Exception{
		XStream stream = new XStream();
		String message ="";
		long id = user.getId();
		String xml = stream.toXML(id);
		String valueInXml = IMonitorUtil.sendToController("userService", "getUserById", xml);
		this.user = (User) stream.fromXML(valueInXml);
		valueInXml = IMonitorUtil.sendToController("userService", "deleteUser", xml);
//		valueInXml - contains yes or no according as the success
		String result = (String) stream.fromXML(valueInXml);
		String xmlMessage = stream.toXML("User ID: "+user.getUserId()+" is Deleted ");
		if(result.compareTo("yes") == 0){
			user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			Device device = null;
			//sendEmailAndSms(stream.toXML(Constants.DELETE_USER),xmlMessage,stream.toXML(device),stream.toXML(user.getCustomer().getId()));
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.Deleteuser.0001");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "User has been deleted successfully.");
			return SUCCESS;
		}
		else if(result.equals("imvg_failure"))
		{
			//LogUtil.info("imvg_failure inside");
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.deleteUser.0002");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return SUCCESS;
		}
		else if(result.equals("Actionfailure"))
		{
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.deleteUser.0003");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return ERROR;
		}
		else if(result.equals("no")){
			message = IMonitorUtil.formatMessage(this, "msg.imonitor.deleteUser.0004");
			ActionContext.getContext().getSession().put("message", message);
			//ActionContext.getContext().getSession().put("message", "Failure:A user by the specified User Id already exist,please choose another one");
			return ERROR;
		}
		return "failure";
	}
	
	public void sendEmailAndSms(String actionXml,String xmlMessage, String devceXmlString,String xmlString2){
		
		String method2 = "getMainUserAndSendSmsAndEmailing";
		String serviceName2 = "userService";
		try {
			IMonitorUtil.sendToController(serviceName2, method2, xmlString2,xmlMessage,actionXml,devceXmlString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public String toUserConfigure() throws Exception {
		XStream stream = new XStream();
		ArrayList<String> response = new ArrayList<String>();
		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		Customer customer = user.getCustomer();
		String xmlString = stream.toXML(customer);
		String result = IMonitorUtil.sendToController("userService", "getGatewayDetails", xmlString);
		
		this.gateway = ((GateWay) stream.fromXML(result));
		if(this.gateway != null){
		String xmlGateway = stream.toXML(this.gateway.getMacId());
		String xml = IMonitorUtil.sendToController("deviceService", "togetZwaveVersion", xmlGateway);
		response = (ArrayList<String>) stream.fromXML(xml);
		this.type = response.get(0);
		this.zWaveVersion = response.get(1);
		this.homeId = response.get(2);
		this.nodeId = response.get(3);
		this.chipType = response.get(4);
		this.numbOfNodes = response.get(5);
		this.chipVersion = response.get(6);
		this.chipFlag = response.get(7);
		this.zWaveSeries = response.get(8);
		
		
		
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String togetDeviceListWithDeadNodes() throws Exception {
		XStream stream = new XStream();
		String xmlString = stream.toXML(this.macID);
		String valueInXml = IMonitorUtil.sendToController("deviceService", "getComaparedDeviceList", xmlString);
		this.deviceList = (List<Device>) stream.fromXML(valueInXml);
		this.deviceCount = this.deviceList.size();
	    return SUCCESS;
	   
	}
	
	@SuppressWarnings("unchecked")
	public String deleteDeadNode() throws Exception {
		XStream stream = new XStream();
		String deviceID = stream.toXML(this.deviceToDelete);
				
		
		String Id =  IMonitorUtil.sendToController("deviceService", "getIdByGeneratedDeviceId", deviceID);
		long count =0;
		String countResult = IMonitorUtil.sendToController("ruleService", "getRuleCountForDevice", Id, deviceID);
		String message = "msg.imonitor.deletedeadnode.0000";
		
	
		String countResult1 = IMonitorUtil.sendToController("scheduleService", "getScheduleCountForDevice",deviceID);
		String countResult2 = IMonitorUtil.sendToController("scenarioService", "getScenarioCountForDevice",deviceID);
		
		String isAlertDevice = IMonitorUtil.sendToController("deviceService", "checkAlertDeviceOfGateway", deviceID);
		
		boolean res = (Boolean) stream.fromXML(isAlertDevice);
		
		//2.if rules exists for the device prompt the user about it and proceed further
		if(countResult.equalsIgnoreCase("RULE_EXISTS")){
			count += 4;
			
		}
		if(countResult1.equalsIgnoreCase("SCHEDULE_EXISTS")){
			count += 2;
			
		}if(countResult2.equalsIgnoreCase("SCENARIO_EXISTS")){
			count += 1;
			
		}
		if((count > 0 || count == 0) && res){
			
			message = "msg.imonitor.devicedelete.0008";
			
			message = IMonitorUtil.formatFailureMessage(this, message);
		}else if(count > 0 && !res) {
			
            message = "msg.imonitor.devicedelete.000"+count;
			
			message = IMonitorUtil.formatFailureMessage(this, message);
		}
		else
		{
			
		message = IMonitorUtil.formatMessage(this, "msg.imonitor.deletedeadnode.0000");
		
		IMonitorUtil.sendToController("deviceService", "deleteSelectedDeadNode", deviceID);
		
		}
		
		ActionContext.getContext().getSession().put("message", message);
		
		
		return SUCCESS;
	}

	
	@SuppressWarnings("unchecked")
	public String togeneratePassword() throws Exception {
		
		
		
		return SUCCESS;
	}

	

public String saveTempPassword() throws Exception {
		
		XStream stream = new XStream();
		String [] time= null;
		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		String passXml = stream.toXML(this.tempPassword);
		if(this.expiretime != null){
		 time = this.expiretime.split(",");
		 this.expiredate.setHours(Integer.parseInt(time[0]));
		 this.expiredate.setMinutes(Integer.parseInt(time[1]));
		}
		
		
		String smsxml = stream.toXML(this.smsToSend);
		String emailxml = stream.toXML(this.emailToSend);
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
        String datexml = stream.toXML(DATE_FORMAT.format(this.expiredate));
        this.user.setTempPassword(IMonitorUtil.getHashPassword(this.tempPassword));
        String userXml = stream.toXML(this.user);
        
        try {
        	IMonitorUtil.sendToController("userService", "saveTemporaryPassword",userXml,datexml,passXml,smsxml,emailxml);
		} catch (Exception e) {
			LogUtil.info("could send message "+ e.getMessage());
		}
       		
		return SUCCESS;
	}


@SuppressWarnings("unchecked")
public String togetDiagnosticInfo() throws Exception {
	XStream stream = new XStream();
	ArrayList<String> response = new ArrayList<String>();
	user = (User) ActionContext.getContext().getSession().get(Constants.USER);
	String customerXml = stream.toXML(user.getCustomer().getCustomerId());
	String valueInXml = IMonitorUtil.sendToController("gateWayService", "getDiagnosticInfoOfGateway", customerXml);
	response = (ArrayList<String>) stream.fromXML(valueInXml);
	this.midPort = response.get(0);
	this.midLoginPort = response.get(1);
	this.lftpDownload = response.get(2);
	

	
    return SUCCESS;

}

@SuppressWarnings("unchecked")
public String showTipsToUser () throws Exception{
	
	XStream stream = new XStream();
	String valueInXml = IMonitorUtil.sendToController("userService", "getListOfUserTips");
	

	
	this.userTips = (List<String>) stream.fromXML(valueInXml);
	
	return SUCCESS;
}

public String updateUserTipsStatus() throws Exception{
	
	XStream stream = new XStream();
	user = (User) ActionContext.getContext().getSession().get(Constants.USER);
	String userXml = stream.toXML(user);
	IMonitorUtil.sendToController("userService", "updateUserTipsForUser", userXml);
	
	return SUCCESS;
}
	
public String toConfigAlexa() throws Exception{
	
	user = (User) ActionContext.getContext().getSession().get(Constants.USER);
	return SUCCESS;
}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailFromDB() {
		return emailFromDB;
	}

	public void setEmailFromDB(String emailFromDB) {
		this.emailFromDB = emailFromDB;
	}

	public String getHintAnswer() {
		return hintAnswer;
	}

	public void setHintAnswer(String hintAnswer) {
		this.hintAnswer = hintAnswer;
	}

	public long getSelectedQuestionId() {
		return selectedQuestionId;
	}

	public void setSelectedQuestionId(long selectedQuestionId) {
		this.selectedQuestionId = selectedQuestionId;
	}

	public String getHintAnswerFromDB() {
		return hintAnswerFromDB;
	}

	public void setHintAnswerFromDB(String hintAnswerFromDB) {
		this.hintAnswerFromDB = hintAnswerFromDB;
	}

	public String getHintQuestionFromDB() {
		return hintQuestionFromDB;
	}

	public void setHintQuestionFromDB(String hintQuestionFromDB) {
		this.hintQuestionFromDB = hintQuestionFromDB;
	}

	public List<ForgotPasswordHintQuestion> getHintQuestionList() {
		return hintQuestionList;
	}

	public void setHintQuestionList(List<ForgotPasswordHintQuestion> hintQuestionList) {
		this.hintQuestionList = hintQuestionList;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}

	public List<Scenario> getScenarioList() {
		return scenarioList;
	}

	public void setScenarioList(List<Scenario> scenarioList) {
		this.scenarioList = scenarioList;
	}

	public List<subUserScenarioAccess> getSubuserScenarioList() {
		return subuserScenarioList;
	}

	public void setSubuserScenarioList(List<subUserScenarioAccess> subuserScenarioList) {
		this.subuserScenarioList = subuserScenarioList;
	}

	public List<subUserDeviceAccess> getSubuserDeviceList() {
		return subuserDeviceList;
	}

	public void setSubuserDeviceList(List<subUserDeviceAccess> subuserDeviceList) {
		this.subuserDeviceList = subuserDeviceList;
	}

	public String getDevicesChecked() {
		return devicesChecked;
	}

	public void setDevicesChecked(String devicesChecked) {
		this.devicesChecked = devicesChecked;
	}

	public String getScenariosChecked() {
		return scenariosChecked;
	}

	public void setScenariosChecked(String scenariosChecked) {
		this.scenariosChecked = scenariosChecked;
	}

	public int getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(int deviceCount) {
		this.deviceCount = deviceCount;
	}

	public int getScenarioCount() {
		return scenarioCount;
	}

	public void setScenarioCount(int scenarioCount) {
		this.scenarioCount = scenarioCount;
	}

	public GateWay getGateway() {
		return gateway;
	}

	public void setGateway(GateWay gateway) {
		this.gateway = gateway;
	}

	public String getMacID() {
		return macID;
	}

	public void setMacID(String macID) {
		this.macID = macID;
	}

	public String getDeviceToDelete() {
		return deviceToDelete;
	}

	public void setDeviceToDelete(String deviceToDelete) {
		this.deviceToDelete = deviceToDelete;
	}

	public String getzWaveVersion() {
		return zWaveVersion;
	}

	public void setzWaveVersion(String zWaveVersion) {
		this.zWaveVersion = zWaveVersion;
	}

	public String getHomeId() {
		return homeId;
	}

	public void setHomeId(String homeId) {
		this.homeId = homeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getChipVersion() {
		return chipVersion;
	}

	public void setChipVersion(String chipVersion) {
		this.chipVersion = chipVersion;
	}

	public String getChipType() {
		return chipType;
	}

	public void setChipType(String chipType) {
		this.chipType = chipType;
	}

	public String getChipFlag() {
		return chipFlag;
	}

	public void setChipFlag(String chipFlag) {
		this.chipFlag = chipFlag;
	}

	public String getNumbOfNodes() {
		return numbOfNodes;
	}

	public void setNumbOfNodes(String numbOfNodes) {
		this.numbOfNodes = numbOfNodes;
	}

	public String getzWaveSeries() {
		return zWaveSeries;
	}

	public void setzWaveSeries(String zWaveSeries) {
		this.zWaveSeries = zWaveSeries;
	}

	public Date getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}


	public String getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public String getSmsToSend() {
		return smsToSend;
	}

	public void setSmsToSend(String smsToSend) {
		this.smsToSend = smsToSend;
	}

	public String getEmailToSend() {
		return emailToSend;
	}

	public void setEmailToSend(String emailToSend) {
		this.emailToSend = emailToSend;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMidPort() {
		return midPort;
	}

	public void setMidPort(String midPort) {
		this.midPort = midPort;
	}

	public String getMidLoginPort() {
		return midLoginPort;
	}

	public void setMidLoginPort(String midLoginPort) {
		this.midLoginPort = midLoginPort;
	}

	public String getLftpDownload() {
		return lftpDownload;
	}

	public void setLftpDownload(String lftpDownload) {
		this.lftpDownload = lftpDownload;
	}

	public List<String> getUserTips() {
		return userTips;
	}

	public void setUserTips(List<String> userTips) {
		this.userTips = userTips;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	
	public AdminSuperUser getSuperuser() {
		return superuser;
	}

	public void setSuperuser(AdminSuperUser superuser) {
		this.superuser = superuser;
	}
	
	public String toChangeAdminSuperUserPassword(){
		return SUCCESS;
	}
	
	public String saveNewPassword(){
		
	  	try 
	  	{
	  		String message = "";
	  		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
			XStream stream = new XStream();
			Customer customer = user.getCustomer();
			
			//Verify if customer is mapped to superUser
			String serviceName = "superUserService";
		  	String	method = "superUserMappingVerification";
		  	String customerXml = stream.toXML(customer);
		  	String superUserxml = stream.toXML(this.superuser);
			String result = IMonitorUtil.sendToController(serviceName,method,customerXml,superUserxml);
			
	  		 if(result.equals("success"))
	  		{
	  			 message = IMonitorUtil.formatMessage(this, "Success:Password updated successfully");
	  			ActionContext.getContext().getSession().put("message", message);
	  		}
	  		else if(result.equals("updateFailed"))
	  		{
	  			 message = IMonitorUtil.formatMessage(this, "Failure:Password updated failed.Please try again");
	  			ActionContext.getContext().getSession().put("message", message);
	  		}
	  		else
	  		{
	  			 message = IMonitorUtil.formatMessage(this, "Failure:Old password of SuperUser is wrong");
	  			ActionContext.getContext().getSession().put("message", message);
	  		}
		} catch (Exception e) 
	  	{
			e.printStackTrace();
		}
		
		
		return SUCCESS;
	}
	
	

	

	






}
