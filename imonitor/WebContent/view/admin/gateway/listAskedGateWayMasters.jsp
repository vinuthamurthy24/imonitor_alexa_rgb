<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
{
"sEcho" : <s:property value="dataTable.sEcho"/>,
"iTotalRecords" : <s:property value="dataTable.totalRows"/>,
"iTotalDisplayRecords" : <s:property value="dataTable.totalDispalyRows"/>, 
"aaData": [
<s:iterator value="gateWayMasters" var="gateWayMaster">
["<s:property value="#gateWayMaster.make"/>","<s:property value="#gateWayMaster.model"/>","<s:property value="#gateWayMaster.techDescription"/>"],
</s:iterator>
] }