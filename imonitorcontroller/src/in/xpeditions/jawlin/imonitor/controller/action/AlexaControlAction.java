package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;

public class AlexaControlAction implements Runnable{

	private String command;
	private Device device;
	private String value;
	private volatile boolean exit = false;
	
	public AlexaControlAction(Device device,String command) {
		
		this.device = device;
		this.command = command;
	}
	
public AlexaControlAction(Device device,String command,String value) {
	LogUtil.info("AlexaControlAction  "+device+""+command+""+value);
		this.device = device;
		this.command = command;
		this.value = value;
	}
	
	@SuppressWarnings("null")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!exit) {
			
			try {
				
				if(this.device.getDeviceType().getName().equals(Constants.Z_WAVE_DIMMER)) {
					
					if(this.command.equals(Constants.SetZWDim)){
						this.device.setCommandParam(this.value);
						            
			           
					}else if(this.command.equals(Constants.ZWDevDimUp)){
						int previous=Integer.parseInt(this.device.getCommandParam());
						int current=Integer.parseInt(this.value);
						int m=previous+current;
						 if (m > 100) {
				             m = 100;
				           }
						 this.device.setCommandParam(String.valueOf(m));
						
						 
					}else if(this.command.equals(Constants.ZWDevDimDown)){
						int previous=Integer.parseInt(this.device.getCommandParam());
						int current=Integer.parseInt(this.value);
						int m=previous-current;
						 if (m < 0) {
				             m = 0;
				           }
						 this.device.setCommandParam(String.valueOf(m));
						
						
					}
					
					ActionModel actionModel = new ActionModel(this.device, null);
		            ImonitorControllerAction controllerAction = new DimmerLevelChangeAction();
		            controllerAction.executeCommand(actionModel);
		            IMonitorUtil.waitForResult(controllerAction);
		            if(IMonitorUtil.waitForResult(controllerAction)==false){
						exit = true;
					}else{
						
						exit = true;
					}
				}else if(this.device.getDeviceType().getName().equals(Constants.Z_WAVE_SWITCH)) {
					
					ActionModel actionModel = new ActionModel(device, null);
					ImonitorControllerAction controllerAction = null;
					if(device.getCommandParam().equalsIgnoreCase("1")){
						controllerAction = new SwitchOnAction();
					} else{
						controllerAction = new SwitchOffAction();
					}
					controllerAction.executeCommand(actionModel);
					IMonitorUtil.waitForResult(controllerAction);
					
					if(IMonitorUtil.waitForResult(controllerAction)==false){
						exit = true;
					}else{
						//device = controllerAction.getActionModel().getDevice();
						exit = true;
					}
					
					
				}else if(this.device.getDeviceType().getName().equals(Constants.Z_WAVE_AC_EXTENDER)) {
					
					if((command.equals(Constants.ZwaveAcOn)) || (command.equals(Constants.ZwaveAcOff))){
						device.setCommandParam(value);					
						ActionModel actionModel = new ActionModel(device, null);
						ImonitorControllerAction controllerAction = new AcControlAction();
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg= IMonitorUtil.waitForResult(controllerAction);
						if(resultFromImvg){
							exit = true;
						}else {
							exit = true;
						}
					}else if(command.equals(Constants.SetAcTemp)){
						
						device.setCommandParam(value);
						ActionModel actionModel = new ActionModel(device, null);
						ImonitorControllerAction controllerAction = new AcTempeartureControlAction();
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
						if(resultFromImvg){
							exit = true;
						}else {
							exit = true;
						}

					}else if(command.equals(Constants.SetAcMode)){
						
						
						device.setCommandParam(value);
						ActionModel actionModel = new ActionModel(device, null);
					
						ImonitorControllerAction controllerAction = new AcControlAction();
						
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
						if(resultFromImvg){
							exit = true;
						}else {
							exit = true;
						}
						
					}else if(command.equalsIgnoreCase(Constants.SetAcOff)){
						device.setCommandParam(value);
						ImonitorControllerAction controllerAction = new AcControlOffAction();
						ActionModel actionModel = new ActionModel(device, null);
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
						if(resultFromImvg){
							exit = true;
						}else {
							exit = true;
						}
					}else if (command.equalsIgnoreCase(Constants.AdjustAcTemp)){
						int previous=Integer.parseInt(device.getCommandParam());
						int current=Integer.parseInt(value);
						
						int m=previous+current;
						if(m > 28){
							m = 28;
						}else if(m < 16){
							m = 16;
						}
						device.setCommandParam(String.valueOf(m));
						ActionModel actionModel = new ActionModel(device, null);
						ImonitorControllerAction controllerAction = new AcTempeartureControlAction();
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
						if(resultFromImvg){
							exit = true;
						}else {
							exit = true;
						}
					}
				}else if(this.device.getDeviceType().getName().equals(Constants.VIA_UNIT)) {
					
					LogUtil.info("command received" + value + " " + command);
					if((command.equals(Constants.IduOn)) || (command.equals(Constants.IduOff))){
						
						device.setCommandParam(value);
						ActionModel actionModel = new ActionModel(device, null);
						ImonitorControllerAction controllerAction = new IndoorUnitActionControl();
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg= IMonitorUtil.waitForResult(controllerAction);
						if(resultFromImvg){
							exit = true;
						}else {
							exit = true;
						}
					}else if(command.equals(Constants.SetIduTemp)){
						
						device.setCommandParam(value);
						ActionModel actionModel = new ActionModel(device, null);
						ImonitorControllerAction controllerAction = new IduTemperatureControlAction();
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
						if(resultFromImvg){
							exit = true;
						}else {
							exit = true;
						}

					}else if(command.equals(Constants.SetIduMode)){
						
						device.setCommandParam(value);
						ActionModel actionModel = new ActionModel(device, null);
					
						ImonitorControllerAction controllerAction = new IndoorUnitActionControl();
						
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
						if(resultFromImvg){
							exit = true;
						}else {
							exit = true;
						}

						
					}else if(command.equalsIgnoreCase(Constants.SetIduOff)){
						
						device.setCommandParam(value);
						ImonitorControllerAction controllerAction = new IndoorUnitActionControl();
						ActionModel actionModel = new ActionModel(device, null);
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
						
						if(resultFromImvg){
							exit = true;
						}else {
							exit = true;
						}
					}else if (command.equalsIgnoreCase(Constants.AdjustIduTemp)){
						
						int previous=Integer.parseInt(device.getCommandParam());
						
						int current=Integer.parseInt(value);
						
						int m=previous+current;
						if(m > 32){
							m = 32;
						}else if(m < 16){
							m = 16;
						}
						
						device.setCommandParam(String.valueOf(m));
						ActionModel actionModel = new ActionModel(device, null);
						ImonitorControllerAction controllerAction = new IduTemperatureControlAction();
						controllerAction.executeCommand(actionModel);
						boolean resultFromImvg=IMonitorUtil.waitForResult(controllerAction);
						if(resultFromImvg){
							exit = true;
						}else {
							exit = true;
						}
					}
					
				}
				else if(this.device.getDeviceType().getName().equals(Constants.DEV_ZWAVE_RGB)) {
					
					
					LogUtil.info("start");
					
					ActionModel actionModel = new ActionModel(this.device, this.value);
					ImonitorControllerAction configureSwitchType = new RGBColorPickerAction();
					configureSwitchType.executeCommand(actionModel);
					boolean resultFromImvg = IMonitorUtil.waitForResult(configureSwitchType);
					
					LogUtil.info("start 1");
		           
		           
		          
		            if(resultFromImvg){
						exit = true;
					}else{
						
						exit = true;
					}
				}
				
				else if(this.device.getDeviceType().getName().equals(Constants.Z_WAVE_AV_BLASTER)) {
					
					
					LogUtil.info("start av blaster");
					
					ActionModel actionModel = new ActionModel(this.device, this.value);
					
			           
					ImonitorControllerAction configureSwitchType = new AVBlasterControlAction();
					configureSwitchType.executeCommand(actionModel);
					boolean resultFromImvg = IMonitorUtil.waitForResult(configureSwitchType);
					
					LogUtil.info("resultFromImvg"+resultFromImvg);
					
		            if(resultFromImvg){
						exit = true;
					}else{
						
						exit = true;
					}
				}
				
				
				LogUtil.info("exit"+exit);
			} catch (Exception e) {
				exit = true;
				LogUtil.info("Exception occured while controling through alexa : " + e.getMessage());
			}
		
		
		
		
		
		}//While loop
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getValue() {
		return value;
	}
	

	public void setValue(String value) {
		this.value = value;
	}

}
