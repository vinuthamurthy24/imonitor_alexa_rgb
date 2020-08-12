<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<table>
	<s:iterator value="alertMechanisms" var="alertMechanism">
		<tr>
			<td>
				<table>		
					<s:property value="#alertMechanism.alertType"/>
				</table>
			</td>
		</tr>
	</s:iterator>
</table>
