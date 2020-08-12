<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>
Number Of Alerts:<s:property value="NumberofAlerts"/>
<s:set var="selectedOffer" value="NumberofAlerts"/>

<a  href='<s:property value="filepath"/>' target="_blank"><button type='button' class='bt bbtn'  title='click to Download'>Download</button></a>
<%-- <s:if test='#selectedOffer > "0" && #selectedOffer < "101" ' ><a  href='<s:property value="filepath"/>' target="_blank"><button type='button' class='bt bbtn'  title='click to Download'>Download</button></a>
</s:if>
<s:elseif test='#selectedOffer == "0" '><p>NO RECORDS</p>
</s:elseif>
<s:else></br>Please select any 100 records to download</s:else> --%>


