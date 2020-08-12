/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

/**
 * @author Coladi
 *
 */
public class Agent {
	private long id; // Auto increment
	private String name;
	private String ip; // ip of the m/c
	private int balancerPort; // port  - listening for iMVG registration.
	private int receiverPort; // port  - listening for iMVG events.
	private int controllerReceiverPort; // port  - listening for message from controller and process to receiver.
	private int controllerBroadCasterPort; // port  - listening for message from controller and process to broadcaster.
	private String streamingIp; // ip  - listening for streaming. 
	private int streamingPort; // port  - listening for streaming.
	private int rtspPort; // port  - listening for streaming.
	private String ftpIp;
	private int ftpPort;
	private int ftpNonSecurePort;
	private String ftpUserName;
	private String ftpPassword;
	private Status status; // Status of the m/c
	private String location; // Physical location of the m/c
	private String details; // other details

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getStreamingPort() {
		return streamingPort;
	}

	public void setStreamingPort(int streamingPort) {
		this.streamingPort = streamingPort;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStreamingIp() {
		return streamingIp;
	}

	public void setStreamingIp(String streamingIp) {
		this.streamingIp = streamingIp;
	}

	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public int getBalancerPort() {
		return balancerPort;
	}

	public void setBalancerPort(int balancerPort) {
		this.balancerPort = balancerPort;
	}

	public int getReceiverPort() {
		return receiverPort;
	}

	public void setReceiverPort(int receiverPort) {
		this.receiverPort = receiverPort;
	}

	public int getControllerReceiverPort() {
		return controllerReceiverPort;
	}

	public void setControllerReceiverPort(int controllerReceiverPort) {
		this.controllerReceiverPort = controllerReceiverPort;
	}

	public int getControllerBroadCasterPort() {
		return controllerBroadCasterPort;
	}

	public void setControllerBroadCasterPort(int controllerBroadCasterPort) {
		this.controllerBroadCasterPort = controllerBroadCasterPort;
	}

	public int getRtspPort() {
		return rtspPort;
	}

	public void setRtspPort(int rtspPort) {
		this.rtspPort = rtspPort;
	}

	public int getFtpNonSecurePort() {
		return ftpNonSecurePort;
	}

	public void setFtpNonSecurePort(int ftpNonSecurePort) {
		this.ftpNonSecurePort = ftpNonSecurePort;
	}
}
