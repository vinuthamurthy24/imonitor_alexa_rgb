/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.action;

import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

import java.util.List;
import java.util.Queue;

/**
 * @author Coladi
 *
 */
public interface ImonitorControllerAction {

	public String executeCommand(ActionModel actionModel);
	
	public String executeSuccessResults(Queue<KeyValuePair> queue);
	
	public String executeFailureResults(Queue<KeyValuePair> queue);
	
	public boolean isResultExecuted();
	
	public ActionModel getActionModel();

	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder);
	
	public boolean isActionSuccess();
	
	
}
