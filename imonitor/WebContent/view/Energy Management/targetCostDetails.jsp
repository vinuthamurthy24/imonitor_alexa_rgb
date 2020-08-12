<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<imonitor>
	<s:iterator value="costdetails" var="object" status="status">
				
				<costdetails>
					<id><s:property value="#object[0]"/></id>
					<customer><s:property value="#object[1]"/></customer>
					<cost><s:property value="#object[2]"/></cost>
					<alertTime><s:property value="#object[3]"/></alertTime>
					
				</costdetails>
					
				
				
	</s:iterator>
</imonitor>
	<%-- <s:iterator value="acAutoMake" var="acb" status="status">
		<acBrand>
			<brandName><s:property value="acb"/></brandName>
		</acBrand>
	</s:iterator>	 --%>
