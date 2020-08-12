<!-- Copyright © 2012 iMonitor Solutions India Private Limited -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.xpeditions.jawlin.imonitor.controller.dao.manager.GatewayManager"%>
<%@page import="in.xpeditions.jawlin.imonitor.controller.dao.entity.GateWay"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.KeyValuePair"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorUtil"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="in.xpeditions.jawlin.imonitor.controller.communication.RequestSender"%>


<%@page import="java.util.Queue"%>
<%@page import="java.util.LinkedList"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%
		String gateWayId = request.getParameter("gateway");
		GatewayManager gm = new GatewayManager();
		GateWay g = gm.getGateWayWithAgentByMacId(gateWayId);
		Queue<KeyValuePair> queue = new LinkedList<KeyValuePair>();
		queue.add(new KeyValuePair(Constants.CMD_ID,Constants.DEVICE_CONTROL));
		String trasactionId = IMonitorUtil.generateTransactionId();
		queue.add(new KeyValuePair(Constants.TRANSACTION_ID,trasactionId));
		queue.add(new KeyValuePair(Constants.IMVG_ID,gateWayId));
		String generatedDeviceId = gateWayId;
		queue.add(new KeyValuePair(Constants.DEVICE_ID,generatedDeviceId));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.START));
		queue.add(new KeyValuePair("PRIMARY_SERVER_IP_PORT","122.166.23.165:1920"));
		queue.add(new KeyValuePair("SECONDARY_SERVER_IP_PORT","122.166.23.165:1920"));
		queue.add(new KeyValuePair(Constants.CONFIG_PARAM,Constants.END));
		String messageInXml = IMonitorUtil.convertQueueIntoXml(queue);
		
		RequestSender requestSender = new RequestSender(g.getAgent().getIp(), g.getAgent().getControllerReceiverPort());
		requestSender.sendRequest(messageInXml);
		
		out.println("send the request to change the ip and port 122.166.23.165:1920 to gateway " + gateWayId);
	%>
</body>
</html>