<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>



<script type="text/javascript" charset="utf-8">
			var publicationgrouptable="";
			
			function fnFilterColumn ()
			{
				
				
					var Date=$("#FromDate").val();
					Date += ":"+$("#ToDate").val();
					$('#listSmsSevicetable').dataTable().fnFilter( 
						Date,1,true,true
					);
				
				

			}

			 $(document).ready(function() 
			{
				publicationgrouptable = $('#listSmsSevicetable').dataTable({
					"bProcessing": true,
					"bServerSide": true,
					"bDestroy": true,
					"bSort": true,
					"sScrollY": 300,
					"sScrollX": "100%",
					"sScrollXInner": "100%",
					"sAjaxSource": "listSMSReports.action",
					"aoColumns": [
					
{"bSortable": true },
{"bSortable": false },
{"bSortable": false,"bVisible": true},
{"bSortable": false,"bVisible": false},
					 
					 ],
					
				
				});
			});
			    $("#FromDate").change(fnFilterColumn);
		    	$("#ToDate").change(fnFilterColumn);
				$(".datetime").datepicker({
				     showOn: 'button', 
				     buttonImage: '/imonitor/resources/images/calendar.gif', 
				     buttonImageOnly: true,
				     maxDate : new Date(),
				     dateFormat: "yy-mm-dd",
				     });

				$(".mydatepicker").datepicker({
					showOn: 'button',
					buttonImage: '/imonitor/resources/images/calendar.gif',
					buttonImageOnly: true
					,
					beforeShow: function(input, inst) {
						$(".ui-datepicker").css('z-index', 5000);
					},
					onClose: function(dateText, inst) { 
						$(".ui-datepicker").css('z-index', 1);
						var testToDate = new Date(dateText);
						var testFromDate = new Date($('#FromDate').val());
						if (testFromDate > testToDate){
							showResultAlert("To date should be greater than From date");
			        }
					},
				
					dateFormat: "yy-mm-dd"});
				$(".mydatepicker" ).datepicker( "option", "showButtonPanel", true );
				$("#ui-datepicker-div").hide();
</script>
		

<div style="color: green;"><s:property value="#session.message" />
	<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<s:form>
<table id="mainform">
<tr>
<td><s:textfield key="alerts.select.from"  id="FromDate" name="FromDate" cssClass="datetime"  readonly="true" ></s:textfield></td>
<td><s:textfield key="alerts.select.to" id="ToDate" name="ToDate" readonly="true" cssClass="mydatepicker"></s:textfield></td>
</tr>
</table>
</s:form>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="listSmsSevicetable">
	<thead>
		<tr>
			
			<th>Customer Id</th>
			<th>Mobile</th>
			<th>SMS Count</th>
		<th></th>
			
			
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="3">Data Loading ...</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			
			<th>Customer Id</th>
			<th>Mobile</th>
			<th>SMS Count</th>
			<th></th>
			
		
		</tr>
	</tfoot>
</table>
<div id="confirm"></div>