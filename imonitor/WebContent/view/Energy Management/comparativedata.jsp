<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<ims>
	<s:iterator value="ListOfDevicewithPowerValues" var="DeviceDetails">
				<device>
					<name><s:property value="#DeviceDetails.locationName"/></name>
					
				
					<s:iterator value="#DeviceDetails.powerinformation" var="pwdetails">
				   		<pw>
				   		<alertTime><s:property value="#pwdetails.alertTime"/></alertTime>
				   		<alertValue><s:property value="#pwdetails.alertValue"/></alertValue>
				   		</pw>
					</s:iterator>
				
				
				</device>
	</s:iterator>
</ims>