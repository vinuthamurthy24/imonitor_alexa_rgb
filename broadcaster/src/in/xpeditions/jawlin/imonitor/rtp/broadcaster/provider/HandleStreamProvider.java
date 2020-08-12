/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.provider;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.listener.PacketFlowManageListener;
import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;

/**
 * @author Coladi
 *
 */
public class HandleStreamProvider implements Runnable {

	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
//	Sequence number of RTSP messages within the session
	private int rtspSequenceNumber = 1;
	private String imvgId;
	private String deviceId;
	private String contentSection;
	private String sdpUrl;
	private String trackIDSection;
	private String rtspSessionId;
	private PacketFlowManageListener packetFlowManageListener;
	
	public HandleStreamProvider(Socket socket) {
		this.socket = socket;
		try {
			this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			
		} catch (IOException e) {
			LogUtil.error("Error while creating reader/writer" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
//		Here we start sending RTSP request to IMVG.
		try {
//			1. Send Describe message
			sendDescribeMessage();
			int status = parseServerResponseForImvgDetails();

			LogUtil.info("Content Section is : -----------Start------");
			LogUtil.info(this.contentSection);
			LogUtil.info("Content Section is : -----------End------");
			this.packetFlowManageListener.setSDPSection(this.contentSection);
		//	LogUtil.info("this content section bytes: "+ this.contentSection.getBytes());
			this.rtspSequenceNumber++;
//			2. Send SETUP request.
			sendSetUpRequest();
			status = parseServerResponse();
			if(status != 200){
//				Clear all the connections and objects and return
				LogUtil.info("Invalid status : Status is " + status);
				return;
			}
			this.rtspSequenceNumber++;
//			2. Send Play Request.
			sendPlayRequest();
			status = parseServerResponse();
			if(status != 200){
//				Clear all the connections and objects and return
				LogUtil.info("Invalid status : Status is " + status + " From device " + this.deviceId);
				return;
			}
			this.rtspSequenceNumber++;
			receiveRtpPackets();
			//sendTearDownRequest();
		} catch (SocketTimeoutException e) {
			LogUtil.error("Time out occured in the communication from iMVG(" + this.imvgId + ") : " + e.getMessage());
			this.packetFlowManageListener.clearMulticaster(this.deviceId);
		} catch (IOException e) {
			LogUtil.error("Exception (IOException) occured in the communication from iMVG(" + this.imvgId + ") and device (" + this.deviceId + "): " + e.getMessage());
			this.packetFlowManageListener.clearMulticaster(this.deviceId);
		}catch (Exception e) {
			LogUtil.error("Exception occured in the communication from iMVG(" + this.imvgId + ") and device (" + this.deviceId + "): " + e.getMessage());
			this.packetFlowManageListener.clearMulticaster(this.deviceId);
		}
	}

	private void sendDescribeMessage() throws IOException {
		String request = "DESCRIBE rtsp://server.address:port/object.sdp RTSP/1.0" + Constants.CRLF;
		request += ("CSeq: " + this.rtspSequenceNumber + Constants.CRLF);
		request += Constants.CRLF;
//		LogUtil.info("Sending Describe : \n" + request);
		this.bufferedWriter.write(request);
		this.bufferedWriter.flush();
	}

	private void sendSetUpRequest() throws IOException {
		
		String request = "SETUP " + this.sdpUrl + "/" + this.trackIDSection + " RTSP/1.0" + Constants.CRLF;
		request += ("CSeq: " + this.rtspSequenceNumber + Constants.CRLF);
		request += ("Transport:RTP/AVP/TCP;unicast;interleaved=0-1;client_port=" + ImonitorControllerCommunicator.get(Constants.STREAMING_PORT) + Constants.CRLF);
		request += Constants.CRLF;
//		LogUtil.info("Sending Setup : \n" + request);
		this.bufferedWriter.write(request);
		this.bufferedWriter.flush();
	}

	private void sendPlayRequest() throws IOException {
		String request = "PLAY " + this.sdpUrl + "/" + this.trackIDSection + " RTSP/1.0" + Constants.CRLF;
		request += ("CSeq: " + this.rtspSequenceNumber + Constants.CRLF);
		request += ("Session: " + this.rtspSessionId + Constants.CRLF);
		request += Constants.CRLF;
//		LogUtil.info("Sending Play : \n" + request);
		this.bufferedWriter.write(request);
		this.bufferedWriter.flush();
	}
	
	private int parseServerResponseForImvgDetails() throws IOException {
		int status = 0;
		
//		1. parse status line and extract the reply_code:
		String statusLine = this.bufferedReader.readLine();
//		LogUtil.info("Message From Client : \n" + statusLine);
		StringTokenizer tokens = new StringTokenizer(statusLine);
	    tokens.nextToken(); //skip over the RTSP version
	    status = Integer.parseInt(tokens.nextToken());
//		2. if reply code is OK get and print the other lines
	    if(status == 200){
	    	int contentLenght = 0;
	    	String currentLine = this.bufferedReader.readLine();
	    	while(!currentLine.isEmpty()){
//	    		Extracting the iMVG and Camera Id.
	    		if(currentLine.startsWith("Content-Base:")){
	    			tokens = new StringTokenizer(currentLine);
	    			tokens.nextToken(); //skip over the Content-Type:
	    			String urlSection = tokens.nextToken();
	    			this.sdpUrl = urlSection;
	    			String[] split = urlSection.split("/");
	    			this.imvgId = split[split.length - 2];
	    			String deviceIdSection = split[split.length - 1];
	    			this.deviceId = deviceIdSection.substring(0, deviceIdSection.indexOf(".live"));
//	    			Create one mutlicaster and put it in multicasters.
	    			LogUtil.info("Connected from imvg: "+ imvgId + " and Device : " + deviceId);
	    		}
//	    		Extracting the length of the descriptor message.
	    		if(currentLine.startsWith("Content-length:")){
	    			tokens = new StringTokenizer(currentLine);
	    			tokens.nextToken(); //skip over the Content-length:
	    			contentLenght = Integer.parseInt(tokens.nextToken());
	    		}
	    		currentLine = this.bufferedReader.readLine();
	    	}
//	    	Extracting the content part.
	    	char[] cbuf = new char[contentLenght];
			this.bufferedReader.read(cbuf, 0, contentLenght);
			this.contentSection = new String(cbuf);
			String trackIdStart = "trackID=";
			String afterTrackId = this.contentSection.substring(this.contentSection.indexOf(trackIdStart) + "trackID=".length());
			this.trackIDSection = trackIdStart + afterTrackId.substring(0, afterTrackId.indexOf(Constants.CRLF));
	    }else{
//	    	Empty the Reader ...
	    	String currentLine = this.bufferedReader.readLine();
	    	while(!currentLine.isEmpty()){
	    		currentLine = this.bufferedReader.readLine();
	    	}
	    }
		return status;
	}
	private int parseServerResponse() throws IOException {
		int status = 0;
		
//		1. parse status line and extract the reply_code:
		String statusLine = this.bufferedReader.readLine();
		LogUtil.info("Message From Client1 : \n" + statusLine);
		StringTokenizer tokens = new StringTokenizer(statusLine);
	    tokens.nextToken(); //skip over the RTSP version
	    try{
	    status = Integer.parseInt(tokens.nextToken());
	    }catch(Exception e){
	    	LogUtil.info("Error in the status line : " + e.getMessage());
	    	String currentLine = this.bufferedReader.readLine();
	    	while(!currentLine.isEmpty()){
	    		LogUtil.info("Message From Client2 : " + currentLine);
	    		currentLine = this.bufferedReader.readLine();
	    	}
	    }
//		2. if reply code is OK get and print the other lines
	    if(status == 200){
	    	String currentLine = this.bufferedReader.readLine();
	    	while(!currentLine.isEmpty()){
	    		LogUtil.info("Message From Client3 : " + currentLine);
//	    		We bothered only about the Session.
	    		if(currentLine.startsWith("Session")){
	    			tokens = new StringTokenizer(currentLine);
	    			tokens.nextToken(); //skip over the Session:
	    			String sessionIdSection = tokens.nextToken();
	    			this.rtspSessionId = sessionIdSection;
	    			
	    			if(sessionIdSection.contains(";")){
	    				this.rtspSessionId = sessionIdSection.substring(0, sessionIdSection.indexOf(';'));
	    			}
	    		}
	    		currentLine = this.bufferedReader.readLine();
	    	}
	    }
		return status;
	}

	private void receiveRtpPackets() throws IOException {
		this.packetFlowManageListener.addStreamProviderSocket(this.deviceId, this.socket);
		
//		No dude... The listener will do the necessary actions when a new stream provider came.
		String tearDownRequest = createTearDownRequest();
		this.packetFlowManageListener.addTearDownRequest(this.deviceId, tearDownRequest);
	}

	private String createTearDownRequest() {
		String request = "";
		request += "TEARDOWN " + this.sdpUrl + " RTSP/1.0" + Constants.CRLF;
		request += ("CSeq: " + this.rtspSequenceNumber + Constants.CRLF);
		request += ("Session: " + this.rtspSessionId + Constants.CRLF);
		request += Constants.CRLF;
		return request;
	}

	public void addPacketFlowManageListener(
			PacketFlowManageListener packetFlowManageListener) {
		this.packetFlowManageListener = packetFlowManageListener;
	}
}
