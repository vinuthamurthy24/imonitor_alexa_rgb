/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.action;

import java.util.Map;

/**
 * @author Coladi
 *
 */
public class ShutdownAction extends BroadCasterDefaultAction{
	
	@Override
	public String execute(Map<String, Object> params) {
		System.exit(0);
		return "success";
	}
	
}
