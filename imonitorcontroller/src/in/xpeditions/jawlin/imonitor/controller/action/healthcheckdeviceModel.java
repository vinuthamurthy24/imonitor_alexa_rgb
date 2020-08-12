/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.action;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.jawlin.imonitor.controller.action.ActionDataHolder;
import in.xpeditions.jawlin.imonitor.controller.action.ActionModel;
import in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction;
import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Make;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Schedule;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DaoManager;
import in.xpeditions.jawlin.imonitor.controller.dao.manager.DeviceManager;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

public class healthcheckdeviceModel implements ImonitorControllerAction {
	private ActionModel actionModel;
	private boolean resultExecuted = false;
	private boolean actionSuccess;
	@Override
	public String executeCommand(ActionModel actionModel) {
		LogUtil.info("ececute command for health check device model");
		this.actionModel = actionModel;
		XStream stream = new XStream();
		DeviceManager deviceManager = new DeviceManager();
		int healthcheckmodelno = 0;
		int HMDTypes = 0;
		/*int curtainLength = 0;
		int curtainMounting = 0;*/
		int HMDRating=0;
		int acOnTime=0;
		int Scale=0;
		int NRL=0;
		int NRH=0;
		int WRL=0;
		int WRH=0;
	    int FRL=0;
		int FRH=0;
		int stabilizationPeriod=0;
		int totalTestRunTime=0;
		int intervalBwnReports=0;
		
		Device device=actionModel.getDevice();
		String acdeviceid=(String) actionModel.getData();
//		List<Object> listforupdatehealth= (List<Object>)actionModel.getData();
//		
//		LogUtil.info(device.getDeviceConfiguration());
//		LogUtil.info("listof health  ==========="+listforupdatehealth);
//		LogUtil.info("listof health acdeviceid ==========="+listforupdatehealth.get(1));
//		LogUtil.info("listof health  sheduleid==========="+listforupdatehealth.get(2));
//		LogUtil.info("listof health  sheduletime==========="+listforupdatehealth.get(0));
		LogUtil.info(stream.toXML(device));
		//Schedule schedule = (Schedule) this.actionModel.getData();
		/*Device deviceFromDB=deviceManager.getDeviceWithConfigurationAndMake(device.getGeneratedDeviceId());
	
		//Kantha: Please DO NOT remove this line even though it seems redundant.
		deviceFromDB = (Device)stream.fromXML(stream.toXML(deviceFromDB));*/
		long id=device.getMake().getId();
		Make make=deviceManager.getMake(id);
		
		
		healthcheckmodelno=Integer.parseInt(make.getNumber());
		LogUtil.info("healthcheckmodelno=="+healthcheckmodelno);
		String temp=make.getDetails();
		LogUtil.info("details=="+temp);
		String temp1[]=temp.split(", ");
		if(temp1[0].isEmpty())
		{
			HMDTypes=0;
		}
		else
		{
		HMDTypes=Integer.parseInt(temp1[0]);
		}
		LogUtil.info("HMDTypes=="+HMDTypes);
		if(temp1[1].isEmpty())
		{
			acOnTime=0;	
		}
		else
		{
			acOnTime=Integer.parseInt(temp1[1]);
		}
		LogUtil.info("acOnTime=="+acOnTime);
		if(temp1[2].isEmpty())
		{
			HMDRating=0;
		}
		else
		{
		HMDRating = Integer.parseInt(temp1[2]);
		}
		LogUtil.info("HMDRating="+HMDRating);
		if(temp1[3].isEmpty())
		{
			Scale=0;
		}
		else
		{
		Scale = Integer.parseInt(temp1[3]);
		}
		LogUtil.info("Scale="+Scale);
		if(temp1[4].isEmpty())
		{
			NRL=0;
		}
		else
		{
			NRL = Integer.parseInt(temp1[4]);
		}
		LogUtil.info("NRL"+NRL);
		if(temp1[5].isEmpty())
		{
			NRH=0;
		}
		else
		{
			NRH = Integer.parseInt(temp1[5]);
		}
		LogUtil.info("NRH"+NRH);
		if(temp1[6].isEmpty())
		{
			WRL=0;
		}
		else
		{
			WRL = Integer.parseInt(temp1[6]);
		}
		LogUtil.info("WRL"+WRL);
		if(temp1[7].isEmpty())
		{
			WRH=0;
		}
		else
		{
			WRH = Integer.parseInt(temp1[7]);
		}
		LogUtil.info("WRH"+WRH);
		if(temp1[8].isEmpty())
		{
			FRL=0;
		}
		else
		{
			FRL = Integer.parseInt(temp1[8]);
		}
		LogUtil.info("FRL"+FRL);
		if(temp1[9].isEmpty())
		{
			FRH=0;
		}
		else
		{
			FRH = Integer.parseInt(temp1[9]);
		}
		LogUtil.info("FRH"+FRH);
		
		if(temp1[10].isEmpty())
		{
			stabilizationPeriod=0;	
		}
		else
		{
		stabilizationPeriod = Integer.parseInt(temp1[10]);
		}
		LogUtil.info("stabilizationPeriod"+stabilizationPeriod);
		if(temp1[11].isEmpty())
		{
			totalTestRunTime=0;	
		}
		else
		{
		totalTestRunTime = Integer.parseInt(temp1[11]);
		}
		LogUtil.info("totalTestRunTime"+totalTestRunTime);
		if(temp1[12].isEmpty())
		{
			intervalBwnReports=0;	
		}
		else
		{
			intervalBwnReports = Integer.parseInt(temp1[12]);
		}
		LogUtil.info("intervalBwnReports"+intervalBwnReports);
		
		String readinglimits="";
		readinglimits="NRL:"+NRL+";NRH:"+NRH+";WRL:"+WRL+";WRH:"+WRH+";FRL:"+FRL+";FRH:"+FRH+"";
		//MotorConfiguration m = (MotorConfiguration)device.getDeviceConfiguration();
		
		/*curtainLength=(int) m.getLength();
		LogUtil.info("curtainLength"+curtainLength);
		curtainMounting=((MotorConfiguration)device.getDeviceConfiguration()).getMountingtype();
		LogUtil.info("curtainMounting"+curtainMounting);
		if(curtainMounting == 3)curtainMounting = 1;*/
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,"DEVICE_UPDATE_MODEL"));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
 		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		String generatedDeviceId = device.getGeneratedDeviceId();
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		if(HMDTypes==1)
		{
			queue.add(new KeyValuePair("HMD_TYPE",""+"AC"));
			queue.add(new KeyValuePair("AC_DEV_ID",""+acdeviceid));
			queue.add(new KeyValuePair("AC_ON_TIME",""+acOnTime));
		}
		else if(HMDTypes==2)
		{
			queue.add(new KeyValuePair("HMD_TYPE",""+"PUMP"));
			queue.add(new KeyValuePair("AC_DEV_ID",""+0));
			queue.add(new KeyValuePair("AC_ON_TIME",""+0));
		}
		else
		{
			queue.add(new KeyValuePair("HMD_TYPE",""+HMDTypes));
			queue.add(new KeyValuePair("AC_DEV_ID",""+0));
			queue.add(new KeyValuePair("AC_ON_TIME",""+0));
		}
		
		//queue.add(new KeyValuePair("AC_DEV_ID",""+acdeviceid));
		queue.add(new KeyValuePair("HMD_RATING",""+HMDRating));
		if(Scale==1)
		{
			queue.add(new KeyValuePair("SCALE",""+"W"));
		}
		else if(Scale==2)
		{
			queue.add(new KeyValuePair("SCALE",""+"KWH"));
		}
		else if(Scale==3)
		{
			queue.add(new KeyValuePair("SCALE",""+"AMP"));
		}
		else
		{
			queue.add(new KeyValuePair("SCALE",""+Scale));
		}
		
		queue.add(new KeyValuePair("READING_LIMITS",""+readinglimits));
		queue.add(new KeyValuePair("STABILIZATION_PERIOD",""+stabilizationPeriod));
		queue.add(new KeyValuePair("TOTAL_TEST_RUN_TIME",""+totalTestRunTime));
		queue.add(new KeyValuePair("INTERVAL_BWN_REPORTS",""+intervalBwnReports));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		LogUtil.info(messageInXml);
		Agent agent = device.getGateWay().getAgent();
		String ip = agent.getIp();
		int port = agent.getControllerReceiverPort();
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
		return null;
	
	}


	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		LogUtil.error("I AM INSIDE health check device model executeSuccessResults COMMAND");
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = true;
		
		return null;// TODO Auto-generated method stub
	}

	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		LogUtil.error("I AM INSIDE health check device model executefailureResults COMMAND");
		this.resultExecuted = true;
		this.actionSuccess = false;
		return null;
	
	}

	@Override
	public boolean isResultExecuted() {
		return this.resultExecuted;

	}

	@Override
	public ActionModel getActionModel() {
		return this.actionModel;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(
			ActionDataHolder actionDataHolder) {
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}
	private String formatScheduleTime(String time)
	{
		String result = "";
		if( time == null)return result;
		
		String arr1[] = time.split(" ");
		if(arr1.length == 5)
		{
			result += arr1[0]+" "+arr1[1];
			
			int lowerLimit = 1;
			for(int i=2;i<5;i++)
			{
				long val = 0;
				if(i==3)lowerLimit = 0;
				if(arr1[i].equals("*"))
				{
					if(i==2)val = 2147483647L;
					else if (i==3)val = 4095L;
					else if (i==4)val = 127L;
				}
				else
				{
					String arr2[] = arr1[i].split(",");
					for (String s : arr2)
					{
						int j = Integer.parseInt(s);
						val ^= 1<<(j-lowerLimit); 
					}
				}
				result += " "+val;
			}
			
		}
		return result;
	}

}
