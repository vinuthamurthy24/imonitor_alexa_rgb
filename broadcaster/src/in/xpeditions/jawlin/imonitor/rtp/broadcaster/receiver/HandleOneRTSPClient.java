/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.receiver;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.multicaster.ClientModel;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.RtspUrlIdDeviceIdMap;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Random;

/**
 * @author Coladi
 * 
 */
public class HandleOneRTSPClient implements Runnable {

	private Socket socket;
	private String sessionId = "375045500347029219";
	private String applicationUrl = null;
	private String deviceId;
	private PacketFlowManageListener packetFlowManageListener;
//	private boolean isPlaying = false;

	public HandleOneRTSPClient(Socket socket) {
		this.socket = socket;
		this.sessionId = Long.toHexString(System.currentTimeMillis()) + new Random().nextInt(1000);
	}

	@Override
	public void run() {

		// Running till STOP method.
		try {
			boolean isOK = true;
			while (true && isOK) {
//				if (!this.isPlaying) {
					IncomingRTSPMessage incomingRTSPMessage = readMessages();
					/*if(incomingRTSPMessage != null && incomingRTSPMessage.getMessage() != null && incomingRTSPMessage.getMessage().length() > 0){
						LogUtil.info(incomingRTSPMessage.getMessage());
					}*/
					LogUtil.info("Sent Command : "+incomingRTSPMessage.getMethodRep());
					
					switch (incomingRTSPMessage.getMethodRep()) 
					{
					case Constants.OPTIONS_REP:
						sendReplyForOptionRequest(incomingRTSPMessage);
						incomingRTSPMessage = null;
						break;
					case Constants.DESCRIBE_REP:
						this.applicationUrl = incomingRTSPMessage.getUrl();
						String rtspUrlId = IMonitorUtil.extractRtspUrlId(this.applicationUrl);
						this.deviceId = RtspUrlIdDeviceIdMap.get(rtspUrlId);
						if(!this.packetFlowManageListener.isStreamAvailable(this.deviceId)){
							this.packetFlowManageListener.clearMulticaster(this.deviceId);
						//	this.packetFlowManageListener.setsessionId(sessionId);
							sendBadReplyForDescribeRequest(incomingRTSPMessage);
						}else{
							sendReplyForDescribeRequest(incomingRTSPMessage);
						}
						incomingRTSPMessage = null;
						break;
					case Constants.SETUP_REP:
						sendReplyForSetupRequest(incomingRTSPMessage);
						incomingRTSPMessage = null;
						break;
					case Constants.PLAY_REP:
						sendReplyForPlayRequest(incomingRTSPMessage);
						ClientModel clientModel = new ClientModel(this.socket,
								this.sessionId);
						this.packetFlowManageListener.addRtspClient(this.deviceId,
								clientModel);
//						this.isPlaying = true;
						incomingRTSPMessage = null;
						break;
					case Constants.TEARDOWN_REP:
						sendReplyForTearDownRequest(incomingRTSPMessage);
						isOK = false;
						incomingRTSPMessage = null;
						break;
					default: 
//						LogUtil.error("We got an invalid RTSP request and the request method is : " + incomingRTSPMessage.getMethod());
//						isOK = false;
						break;
					}
//				} else {
//					break;
//				}
			}
			/*if (isPlaying) {
				ClientModel clientModel = new ClientModel(this.socket,
						this.sessionId);
				this.packetFlowManageListener.addRtspClient(this.deviceId,
						clientModel);
				// Listening for TEARDOWN method. :)' I'm getting the
				// TEARDOWN method ... If I reply properly it causes error  !!
				InputStream inputStream = this.socket.getInputStream();
				while (true) {
//					inputStream.read();
					int available = inputStream.available();
					if (available > 0) {
						byte[] b = new byte[available];
						inputStream.read(b, 0, available);
						String rtspMessage = new String(b);
						LogUtil.info(rtspMessage);
						LogUtil.info("\n");
						if (rtspMessage.contains("TEARDOWN")) {
							LogUtil.info("We got TEARDOWN request from session : " + this.sessionId + " for Device : " + this.deviceId);
							IncomingRTSPMessage incomingRTSPMessage = new IncomingRTSPMessage(
									rtspMessage);
							sendReplyForTearDownRequest(incomingRTSPMessage);
							if(!this.socket.isClosed()){
								this.socket.close();
							}
							break;
						}
						b = null;
					}
				}
			}*/
		} catch (IOException e) {
			LogUtil.error("Error from VLC client IOException" + e.getMessage());
			
			//LogUtil.info(this.sessionId);
			if(this.socket != null)
			{
				//LogUtil.info("socket is not null");
				try {
					this.socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		this.socket = null;
		/*if(this.socket != null)
		{
			LogUtil.info("socket is not null");
		}
		else
		{
			LogUtil.info("socket is null");
		}*/
		
		//LogUtil.info(this.sessionId);
		this.sessionId = null;
		//LogUtil.info(this.sessionId);
		this.applicationUrl = null;
		this.deviceId = null;
		this.packetFlowManageListener = null;
	}

	private void sendReplyForTearDownRequest(IncomingRTSPMessage incomingRTSPMessage)
			throws IOException {
		String message = "";
		message += "RTSP/1.0 200 OK" + Constants.CRLF;
		message += "Cseq: " + incomingRTSPMessage.getSeqNo() + Constants.CRLF;
		message += "Session: " + this.sessionId + Constants.CRLF;
		message += Constants.CRLF;
		this.socket.getOutputStream().write(message.getBytes());
		LogUtil.info("We have replied to TEARDOWN to VLC " + this.deviceId + " Session : " + this.sessionId);
	}

	private void sendReplyForPlayRequest(IncomingRTSPMessage incomingRTSPMessage)
			throws IOException {
		String message = "";
		message += "RTSP/1.0 200 OK" + Constants.CRLF;
		message += "Cseq: " + incomingRTSPMessage.getSeqNo() + Constants.CRLF;
		message += "Session: " + this.sessionId + Constants.CRLF;
		message += "RTP-Info: url=" + incomingRTSPMessage.getUrl() + "/trackID=1;seq=0;rtptime=0" + Constants.CRLF;
		message += "Range: npt=0-" + Constants.CRLF;
		
//		message += "Server: Imonitor/1.3b" + Constants.CRLF;
		message += Constants.CRLF;
		this.socket.getOutputStream().write(message.getBytes());
//		LogUtil.info("To client : \n" + message);
	}

	private void sendReplyForSetupRequest(
			IncomingRTSPMessage incomingRTSPMessage) throws IOException {
		String message = "";
		message += "RTSP/1.0 200 OK" + Constants.CRLF;
		message += "Cseq: " + incomingRTSPMessage.getSeqNo() + Constants.CRLF;
		message += "Session: " + this.sessionId + Constants.CRLF;
		message += "Transport: RTP/AVP/TCP;unicast;interleaved=0-1;ssrc=46216D58;mode=\"PLAY\"" + Constants.CRLF;
		
		/*message += "Server: Imonitor/1.3b"
				+ Constants.CRLF;
		message += "Last-Modified: Thu, 24 Feb 2011 17:31:36 GMT"
				+ Constants.CRLF;
		message += "Cache-Control: must-revalidate" + Constants.CRLF;
		message += "Date: Tue, 07 Jun 2011 12:47:25 GMT" + Constants.CRLF;
		message += "Expires: Tue, 07 Jun 2011 12:47:25 GMT" + Constants.CRLF;*/
		message += Constants.CRLF;
		this.socket.getOutputStream().write(message.getBytes());
//		LogUtil.info("To client : \n" + message);
	}

	private void sendBadReplyForDescribeRequest(
			IncomingRTSPMessage incomingRTSPMessage) throws IOException {

		String message = "";
		message += "RTSP/1.0 404 Not Found" + Constants.CRLF;
		message += "Cseq: " + incomingRTSPMessage.getSeqNo() + Constants.CRLF;
		message += Constants.CRLF;
		this.socket.getOutputStream().write(message.getBytes());
//		LogUtil.info("To client : \n" + message);
	}

	private void sendReplyForDescribeRequest(
			IncomingRTSPMessage incomingRTSPMessage) throws IOException {
		String coveringMesage = "";
		LogUtil.info("Covering Message is : -----------Start------");
		LogUtil.info(coveringMesage);
		LogUtil.info("Covering Message is : -----------End------");
		coveringMesage += this.packetFlowManageListener.getSDPSection();

		String message = "";
		message += "RTSP/1.0 200 OK" + Constants.CRLF;
		message += "Cseq: " + incomingRTSPMessage.getSeqNo() + Constants.CRLF;
		message += "Content-Base: " + incomingRTSPMessage.getUrl() + Constants.CRLF;
		message += "Content-Type: application/sdp" + Constants.CRLF;
		message += "Content-length: " + coveringMesage.length() + Constants.CRLF;
		
		/*message += "Server: Imonitor/1.3b"
				+ Constants.CRLF;
		message += "Last-Modified: Thu, 24 Feb 2011 17:31:36 GMT"
				+ Constants.CRLF;
		message += "Cache-Control: must-revalidate" + Constants.CRLF;
		message += "Date: Tue, 07 Jun 2011 12:47:25 GMT" + Constants.CRLF;
		message += "Expires: Tue, 07 Jun 2011 12:47:25 GMT" + Constants.CRLF;
		message += "x-Accept-Retransmit: our-retransmit" + Constants.CRLF;
		message += "x-Accept-Dynamic-Rate: 1" + Constants.CRLF;*/
		
		message += Constants.CRLF;
		message += coveringMesage;
		this.socket.getOutputStream().write(message.getBytes());
//		LogUtil.info("To client : \n" + message);
	}

	private void sendReplyForOptionRequest(
			IncomingRTSPMessage incomingRTSPMessage) throws IOException {
		String message = "";
		message += "RTSP/1.0 200 OK" + Constants.CRLF;
//		message += "Server: Imonitor/1.3b" + Constants.CRLF;
		message += "Cseq: " + incomingRTSPMessage.getSeqNo() + Constants.CRLF;
		message += "Public: DESCRIBE, SETUP, TEARDOWN, PLAY, OPTIONS" + Constants.CRLF;
		message += Constants.CRLF;
		this.socket.getOutputStream().write(message.getBytes());
//		LogUtil.info("To client : \n" + message);
	}

	private IncomingRTSPMessage readMessages() throws IOException {
//		I'm changing the following read method.
//		readLine may cause problem if there is not END_LINE character in the message.
//		Let's see whether the read will work in new way.
		InputStream inputStream = this.socket.getInputStream();
		String rtspMessage = "";
		/*BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String readLine = bufferedReader.readLine();
		while (readLine != null && !readLine.isEmpty()) {
			rtspMessage += readLine + Constants.CRLF;
			readLine = bufferedReader.readLine();
		}*/
		
		int read = inputStream.read();
		if(read < 0){
			LogUtil.info("Closing socket");
			throw new IOException("Exception occured while reading from VLC. Session is " + this.sessionId + " and Url is : " + this.applicationUrl);
			
		}
		char ch = (char) read;
		int available = inputStream.available();
		byte[] bArray = new byte[available];
		inputStream.read(bArray);
		rtspMessage = ch + new String(bArray);
		
		IncomingRTSPMessage incomingRTSPMessage = new IncomingRTSPMessage(
				rtspMessage);
		return incomingRTSPMessage;
	}

	public void addPacketFlowManageListener(
			PacketFlowManageListener packetFlowManageListener) {
		this.packetFlowManageListener = packetFlowManageListener;
	}

}
