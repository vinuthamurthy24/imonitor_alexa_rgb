<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%><div style="color: blue"><s:property value="#session.message" />
			<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<h4>Upload Firmware</h4>
<s:form action="uploadFirmWare" method="post" id="firmwaresaveform" enctype="multipart/form-data" cssClass="ajaxinlinefileuploadform">
<s:hidden id="firmwareName" name="firmWare.name" label="Name" value="iMVG_NEW.tar"></s:hidden>
<tr><td><label>Gateway Model:</label> </td>
 <td><select name="gatewaymodel" id="gatewaymodel" Class="requiredselect editdisplayStar" onchange="javascript: RowVisibility(this.options[this.selectedIndex])" >
    					<option value="0">--Select Gateway Model--</option>
    					<option value="NA900">NA900</option>
    					<option value="NA910">NA910</option>
    					<option value="IMSZING">IMSZING</option>
						</select></td></tr>
						<tr id="900"><td><label>Latest FirmwareVersion:</label></td><td><s:property value="latestVersionofFirmware900" /></td></tr>
						<tr id="910"><td><label>Latest FirmwareVersion:</label></td><td><s:property value="latestVersionofFirmware910" /></td></tr>
						
						<tr><td><label>Version:</label></td><td><input type="text" name="firmWare.version"  class="required editdisplayStar"  maxlength="2" size="2">.<input type="text" name="version1" class="required" maxlength="2" size="2">.<input type="text" name="version2" class="required" maxlength="2" size="2"></td></tr>

<s:textfield name="firmWare.description" label="Description"></s:textfield>
<s:textfield name="firmWare.date" label="Date" id="registrationDate" cssClass="datetime"></s:textfield>
<s:select list="agents" listKey="id" listValue="name" label="Agent" name="firmWare.agent.id"></s:select>
<s:file name="firmWareFile" cssClass="required editdisplayStar" label="Choose the file to upload"></s:file>
<s:submit value="Save" ></s:submit>
</s:form>
	<script>
		$(document).ready(function() {
		$("#900").hide();
				$("#910").hide(); 
			$("#registrationDate").datepicker({showOn: 'button', buttonImage: '/imonitor/resources/images/calendar.gif', buttonImageOnly: true,dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'});
			var cForm = $("#firmwaresaveform");
			Xpeditions.validateForm(cForm);
		});
		function RowVisibility(selVal)
        {

               if(selVal.label=="--Select Gateway Model--")        		{
        		$("#900").hide();
				$("#910").hide();
        		}
        	else if(selVal.label=="NA900") 
        		{
        		$("#900").show();
                $("#910").hide();
        		}
              else if(selVal.label=="NA910")
                     {
                     $("#910").show();
                     $("#900").hide();
                     }
               else {
            	   $("#firmwareName").val("IMSPRIME.bin");
            	   $("#900").hide();
   				$("#910").hide();
               }
        }

	</script>