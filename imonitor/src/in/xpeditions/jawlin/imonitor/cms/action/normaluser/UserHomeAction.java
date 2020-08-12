/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.cms.action.normaluser;

import java.util.List;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.CustomerPasswordHintQuestionAnswer;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.ForgotPasswordHintQuestion;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.User;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorProperties;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

/**
 * @author Coladi
 *
 */
public class UserHomeAction extends ActionSupport {

	
	private User user;
	private CustomerPasswordHintQuestionAnswer hintQuestionAnswer;
	private long selectedQuestionId;
	private String hintAnswer;
	private String hintAnswerFromDB;
	private String hintQuestionFromDB;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3541762494538070573L;
	private String imvgUploadContextPath=IMonitorProperties.getPropertyMap().get(Constants.IMVG_UPLOAD_CONTEXT_PATH);
	// **************************************************** sumit start: ReOrdering Of Devices *************************************************
	private String position;
	
	@Override
	public String execute() throws Exception {
		XStream stream = new XStream();
		user = (User) ActionContext.getContext().getSession().get(Constants.USER);
		long cId = user.getCustomer().getId();
		String cIdXml = stream.toXML(cId);
		String hintQuestionAnswerXml = IMonitorUtil.sendToController("customerService", "getForgotPasswordQuestionsWithAnswer", cIdXml);
		//LogUtil.info("user details: "+ stream.toXML(user));
		hintQuestionAnswer = (CustomerPasswordHintQuestionAnswer) stream.fromXML(hintQuestionAnswerXml);
		setSelectedQuestionId(hintQuestionAnswer.getQuesId());
		setHintAnswerFromDB(hintQuestionAnswer.getHintAnswer());
		setHintQuestionFromDB(hintQuestionAnswer.getHintQuestion());
		return SUCCESS;
	}
	
	public String reOrderDevicesList() throws Exception {
		XStream stream = new XStream();
		String devicesOrder = this.position;
		String xmlString = stream.toXML(devicesOrder);
		String serviceName = "deviceService";
		String method = "savePositionIndexForDevices";
		IMonitorUtil.sendToController(serviceName, method, xmlString);
		return SUCCESS;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String positionIndexes) {
		this.position = positionIndexes;
	}
	// ********************************************************* sumit end: ReOrdering Of Devices *********************************************
		public String getImvgUploadContextPath() {
		return imvgUploadContextPath;
	}
	public void setImvgUploadContextPath(String imvgUploadContextPath) {
		this.imvgUploadContextPath = imvgUploadContextPath;
	}

	public long getSelectedQuestionId() {
		return selectedQuestionId;
	}

	public void setSelectedQuestionId(long selectedQuestionId) {
		this.selectedQuestionId = selectedQuestionId;
	}

	public String getHintQuestionFromDB() {
		return hintQuestionFromDB;
	}

	public void setHintQuestionFromDB(String hintQuestionFromDB) {
		this.hintQuestionFromDB = hintQuestionFromDB;
	}

	public String getHintAnswerFromDB() {
		return hintAnswerFromDB;
	}

	public void setHintAnswerFromDB(String hintAnswerFromDB) {
		this.hintAnswerFromDB = hintAnswerFromDB;
	}

	public String getHintAnswer() {
		return hintAnswer;
	}

	public void setHintAnswer(String hintAnswer) {
		this.hintAnswer = hintAnswer;
	}

}
