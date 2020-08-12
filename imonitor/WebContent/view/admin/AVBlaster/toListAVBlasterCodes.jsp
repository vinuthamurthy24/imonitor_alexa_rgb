<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>

<script type="text/javascript" charset="utf-8">
	var publicationgrouptable="";
	$(document).ready(function(){
		publicationgrouptable = $('#listAvBlasterTable').dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"bDestroy": true,
			"bSort": true,
			"sScrollY": 300,
			"sScrollX": "100%",
			"sScrollXInner": "100%",
			"sAjaxSource": "listAskedAVBlasterCodes.action"
		});		
	});

</script>
		

<div style="color: green;"><s:property value="#session.message" />
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<table>
		<tr>
			<td id="AddAVBlasterCodeInfo" >
				<a class='editlink' href="toAddAVBlasterCodeInfo.action" popupheight="350" popupwidth="950" popuptitle="Please fill form" 
				title="Add AV Blaster Code"><img src="/imonitor/resources/images/locationadd.png"></a>	</td>
				<td><a class='editlink' href="toAddAVBlasterCodeInfo.action" popupheight="350" popupwidth="950" popuptitle="Please fill form" 
				title="Add AV Blaster Code" style="border: none;  text-decoration: none;  text-align: left;  font-family: Verdana,Arial,Helvetica,sans-serif;  font-size: 16px;  font-weight: bold;  color: #0099ff;  position: relative;
				">
				<span>AV Blaster IR Code</span></a>
				</td>

		

		</tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listAvBlasterTable">
	<thead>
		<tr>
			<th>Region</th>
			<th>Category</th>
			<th>Brand</th>
			<th>Code</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>Region</th>
			<th>Category</th>
			<th>Brand</th>
			<th>Code</th>
			<th>Actions</th>
		</tr>
	</tfoot>
</table>
<div id="confirm"></div>