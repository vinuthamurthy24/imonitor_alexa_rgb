/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.UpdatorModel;

/**
 * @author Coladi
 *
 */
public class ActionExecutor implements Runnable {

	private String command;
	private ActionModel actionModel;

	public ActionExecutor(String command, ActionModel actionModel) {
		this.command = command;
		this.actionModel = actionModel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		UpdatorModel<?> updatorModel = IMonitorUtil.getActionUpdators().get(this.command);
		Class<?> className = updatorModel.getClassName();
		
		try {
			ImonitorControllerAction controllerAction = (ImonitorControllerAction) className.newInstance();
			controllerAction.executeCommand(actionModel);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}
