<%-- Copyright Ãƒâ€šÃ‚Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page pageEncoding="UTF-8"%>

<s:form action="getExeldata.action" method="post"  cssClass="downloadpopupform" id="usersaveform">
	<s:radio label="Select Energy Report" name="energyreport" list="#{'1':'Hour','2':'Week','3':'Month','4':'Year','5':'15Min Data'}"></s:radio>
	<s:submit id="submit" key="Submit"/>
</s:form>

<script type="text/javascript">

	 function DestoryDialog()
        {
                   $("#editmodal").dialog('destroy'); 
        }	

</script>