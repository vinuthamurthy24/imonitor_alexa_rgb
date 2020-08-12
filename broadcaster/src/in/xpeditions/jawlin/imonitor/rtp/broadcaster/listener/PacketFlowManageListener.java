/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener;

import in.xpeditions.jawlin.imonitor.rtp.broadcaster.multicaster.ClientModel;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;

/**
 * @author Coladi
 *
 */
public interface PacketFlowManageListener {

	public void addStreamProviderSocket(String deviceId, Socket socket);

	public void addRtspClient(String deviceId, ClientModel clientModel);

	public void stopStreaming(String deviceId, String sessionId);

	public Socket getStreamProviderSocket(String deviceId);

	public void setSDPSection(String contentSection);
	
	public String getSDPSection();

	public void addTearDownRequest(String deviceId, String string);
	
	public void clearMulticaster(String deviceId);
	
	public boolean isStreamAvailable(String deviceId);

	public HashMap<String, List<ClientModel>> getAllStreamsWithClients();

	public String getStreamUrlId(String deviceId);
	//naveen made changes
	public String setsessionId(String sessionId);
	
	public String getsessionId();
	//naveen end
}
