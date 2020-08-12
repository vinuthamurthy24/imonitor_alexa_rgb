	<%-- Copyright © 2012 iMonitor Solutions India Private Limited --%>
	<%@ taglib uri="/struts-tags" prefix="s"%>
	<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
	<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
	<%@page import="com.opensymphony.xwork2.ActionContext"%><h4>  Select services to disable.  </h4>
	<div style="color: blue;display: none;"><p class="message"><s:property value="#session.message" /></p>
	<%ActionContext.getContext().getSession().remove("message"); %>
	</div>
	<script type="text/javascript" charset="utf-8">
				$(".datetime").datepicker({
				     showOn: 'button', 
				     buttonImage: '/imonitor/resources/images/calendar.gif', 
				     buttonImageOnly: true,
				     maxDate : new Date(),
				     dateFormat: "yy-mm-dd",
				     });

				$(".mydatepicker").datepicker({
					maxDate: '0', 
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
	<s:form action="updateCustomerService.action" method="POST" id="CustomerServiceform" cssClass="ajaxinlinepopupform">
		<s:hidden name="customer.id"></s:hidden>
		<s:hidden name="customer.customerId"></s:hidden>
		
		
	  		<tr>
				<td><s:text name="general.sms" /></td>
				<td><s:checkbox theme="simple" name="sms"/></td>
			</tr>
			<tr>
				<td><s:text name="general.email" /></td>
				<td><s:checkbox theme="simple" name="email"/></td>
				
			</tr>
			<tr>
				<td><s:text name="general.videostreaming" /></td>
				<td><s:checkbox theme="simple" name="videoStreaming"/></td>
			</tr>
			<tr>
				<td><s:text name="general.WhatsApp" /></td>
				<td><s:checkbox theme="simple" name="WhatsApp"/></td>
			</tr>
			
			<table>
	<tr>
	<p style="color:blue;">___________________________________________</p>
	</tr>
	
			<tr>
			<s:textarea name="customerServices.Description" id="Description" cssClass="required Description editdisplayStar" label="Disabled By" rows="1" cols="20" placeholder="Enter name"></s:textarea>
			</tr>
			<tr>
			<tr>
			<s:textfield name="customerServices.Date" label="Effective Date" id="Date" readonly="true" cssClass="mydatepicker"></s:textfield>
			</tr>
			<tr>
			<s:textarea name="customerServices.Reason" id="Reason" cssClass="required  Details" label="Reason" rows="3" cols="20" placeholder="Services disabled due to ..."></s:textarea>
			</tr>
			</table>
			<table>
			<tr>
	<p style="color:blue;">___________________________________________</p>
	</tr>
	<h4>  Notification  </h4>
				
	</table> 
	<div id="notify">
	<div>
	<s:checkbox name="support"/><span>support@imonitorsolutions.com</span>
	</div>
	<div>
	<s:checkbox name="customeremail"/><s:text name="customer.email" />
	</div>
	<div>
	<s:checkbox theme="simple"  name="otherMail"  id="intimate"/><s:textfield name="customerServices.IntimationMail" id="othermail" label="OthermailID" readonly="true"></s:textfield> 
	</div>
	</div>
	<%-- <table id="notify">
	    <tbody>
	    
	    
	         <tr>
	         <td> <s:checkbox name="support"/> 
	         support@imonitorsolution.com</td>
	              
	         </tr>
	        
	      
	         
	         <tr>
	         <s:checkbox name="customeremail"/>
	           <td><s:text name="customerServices.customer.email" /></td>
	           
	        </tr>
	        
	        <tr>
	          <td><s:checkbox theme="simple"  name="otherMail"  id="intimate"/>
	        OthermailID:</td>
	        <s:textfield name="customerServices.IntimationMail"  id="othermail" readonly="true"></s:textfield> 
	        </tr>
	        
	        
	    </tbody>
	</table> --%>
	<s:submit value="Save"></s:submit>
	</s:form>
	<div id="wait" style="display:none;width:69px;height:89px;border:1px solid black;position:absolute;top:50%;left:50%;padding:2px;"><img src='/imonitor/resources/images/loading.gif' width="64" height="64" /><br>Loading..</div>
	<script type="text/javascript">
	$(document).ready(function() {
	
		//$(".addnewrow").xpAddImageAfter({addimgsrc:'/imonitor/resources/images/addbutton.png',removeimgsrc:'/imonitor/resources/images/deletbutton.png',addimgclass:'duplicatemyrow',removeimgclass:'removemyrow'});
		var cForm = $("#CustomerServiceform");
		Xpeditions.validateForm(cForm);
	    
	$("#intimate").live('click', function(){
			
			if($(this).attr('checked')){
			
			$("#othermail").removeAttr("readOnly");
				$("#othermail").addClass("required");
				$("#othermail").addClass("email");
			
			}else{
				$("#othermail").attr("readOnly",true);
				$("#othermail").val("");
				$("#othermail").removeClass("required");
				$("#othermail").removeClass("email");
				
			}
		});
	$(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    });
	});
		</script>
		