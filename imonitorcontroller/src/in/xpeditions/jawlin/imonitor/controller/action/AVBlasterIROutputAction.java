package in.xpeditions.jawlin.imonitor.controller.action;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import in.xpeditions.jawlin.imonitor.controller.communication.RequestProcessor;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Agent;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.Device;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;
import in.xpeditions.jawlin.imonitor.util.Constants;
import in.xpeditions.jawlin.imonitor.util.IMonitorSession;
import in.xpeditions.jawlin.imonitor.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.util.KeyValuePair;

public class AVBlasterIROutputAction implements ImonitorControllerAction
{

	private ActionModel actionModel;
	private boolean resultExecuted;
	private boolean actionSuccess;
	private String channelCode;

	public AVBlasterIROutputAction(String channelCode) {
		this.channelCode = channelCode;
	}

	public AVBlasterIROutputAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeCommand(in.xpeditions.jawlin.imonitor.controller.action.ActionModel)
	 */
	public String executeCommand(ActionModel actionModel) {
		this.actionModel = actionModel;
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.SET_IR_OUTPUT_PORT));
		String trasactionId = IMonitorUtil.generateTransactionId();
		LogUtil.info("trasactionId"+trasactionId);
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		Device device = actionModel.getDevice();
		LogUtil.info("device"+device);
		queue.add(new KeyValuePair(Constants.IMVG_ID,device.getGateWay().getMacId()));
		String generatedDeviceId = device.getGeneratedDeviceId();
		LogUtil.info("generatedDeviceId"+generatedDeviceId);
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair("ZXT_AV_BLASTER_OUTPUT_PORT", this.channelCode));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		RequestProcessor requestProcessor = new RequestProcessor();
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		LogUtil.info("messageInXml"+messageInXml);
		Agent agent = device.getGateWay().getAgent();
		LogUtil.info("agent"+agent);
		String ip = agent.getIp();
		LogUtil.info("ip"+ip);
		int port = agent.getControllerReceiverPort();
		LogUtil.info("port"+port);
		requestProcessor.processRequest(messageInXml, ip , port);
		IMonitorSession.put(trasactionId, this);
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeFailureResults(java.util.Queue)
	 */
	@Override
	public String executeFailureResults(Queue<KeyValuePair> queue) {
		this.resultExecuted = true;
		this.actionSuccess = false;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#executeSuccessResults(java.util.Queue)
	 */
	@Override
	public String executeSuccessResults(Queue<KeyValuePair> queue) {
		
		String tId = IMonitorUtil.commandId(queue, Constants.TRANSACTION_ID);
		LogUtil.info("tId"+tId);
		IMonitorSession.remove(tId);
		this.resultExecuted = true;
		this.actionSuccess = true;
		return null;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#getActionModel()
	 */
	@Override
	public ActionModel getActionModel() {
		return this.actionModel;
	}

	/* (non-Javadoc)
	 * @see in.xpeditions.jawlin.imonitor.controller.action.ImonitorControllerAction#isResultExecuted()
	 */
	@Override
	public boolean isResultExecuted() {
		return this.resultExecuted;
	}

	@Override
	public List<KeyValuePair> createImvgConfigParams(ActionDataHolder actionDataHolder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActionSuccess() {
		return this.actionSuccess;
	}


	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	



}
