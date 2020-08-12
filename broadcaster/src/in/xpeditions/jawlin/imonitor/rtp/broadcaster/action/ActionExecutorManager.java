/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.action;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.BroadCasterActionMap;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.UpdatorModel;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.XmlUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLSocket;

import org.w3c.dom.Node;

/**
 * @author Coladi
 *
 */
public class ActionExecutorManager implements Runnable {

	private String actionName;
	private List<Node> paramsNodes;
	private SSLSocket socket;
	private PacketFlowManageListener packetFlowManageListener;
	
	public ActionExecutorManager(String actionName, List<Node> paramsNodes, SSLSocket socket, PacketFlowManageListener packetFlowManageListener) {
		this.actionName = actionName;
		this.paramsNodes = paramsNodes;
		this.socket = socket;
		this.packetFlowManageListener = packetFlowManageListener;
	}

	@Override
	public void run() {
		String result = Constants.FAILURE;
		UpdatorModel<?> actionModel = BroadCasterActionMap.get(this.actionName);
		if(actionModel == null){
			LogUtil.info("Action (" + this.actionName + ") asked by the client is not implemented");
			return;
		}
		Class<?> className = actionModel.getClassName();
		Map<String, Object> params = XmlUtil.convertNodesIntoMap(paramsNodes);
		params.put(Constants.PACKET_FLOW_MANAGE_LISTENER, this.packetFlowManageListener);
		Method method = actionModel.getMethod();
		if(className == null || method == null){
			return ;
		}
		try {
			result = (String) method.invoke(className.newInstance(), params);
		} catch (IllegalArgumentException e) {
			LogUtil.error("Error when processing request " + this.actionName + " IllegalArgumentException: " + e.getMessage());
		} catch (IllegalAccessException e) {
			LogUtil.error("Error when processing request " + this.actionName + " IllegalAccessException: " + e.getMessage());
		} catch (InvocationTargetException e) {
			LogUtil.error("Error when processing request " + this.actionName + " InvocationTargetException: " + e.getMessage());
		} catch (InstantiationException e) {
			LogUtil.error("Error when processing request " + this.actionName + " InstantiationException: " + e.getMessage());
		}
		DataOutputStream dataOutputStream;
		try {
			dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
			dataOutputStream.writeUTF(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
