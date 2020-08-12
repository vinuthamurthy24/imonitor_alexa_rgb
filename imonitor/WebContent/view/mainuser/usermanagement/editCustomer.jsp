<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%><html>
<div style="color: blue;display:none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>
<div class="pageTitle">
	<!-- ************************************************************** sumit start ******************************************************** -->
	<table>
		<tr>
		<!-- <td id="ruleAdd" >
				<a class='' href="#" style="border: none;text-decoration: none;"><img src="/imonitor/resources/images/useradd.png"></a>
			</td><td>
				<span class='titlespangeneric'>Edit Profile</span>
			</td> -->
			<td></td>	
			<td></td>	
			<td id="changePassword">
				<a class='editlink rulenadd' href="toChangeUserPassword.action"  popuptitle="<s:text name='setup.user.password.change' />">
				<img style="width:35px; height:35px;" src="/imonitor/resources/images/CustomWidgets.png"></a>
			</td><td>
				<a class='editlink rulenadd' href="toChangeUserPassword.action"  popuptitle="<s:text name='setup.user.password.change' />"><span class='titlespangeneric'> <s:text name='setup.user.password.change' /> </span></a>
			</td>
			<!-- **************************************** sumit start: forget password user authentication ********************************* -->
			<td></td>	
			<td></td>
			<td id="resetPasswordInfo">
					<a class='rulenadd' id="toConfigureResetPasswordInfo" href="toConfigureResetPasswordInfo.action">
						<img style="width:35px; height:35px;" src="/imonitor/resources/images/CustomWidgets.png"/></a> 
			</td><td>
				<a class='editlink rulenadd' id="toConfigureResetPasswordInfo" href="toConfigureResetPasswordInfo.action"  popuptitle="<s:text name='configure' />"><span class='titlespangeneric'><s:text name='setup.user.password.configure' /></span></a>
			</td>
			
			<!-- ********************************* sumit end: forget password user authentication ****************************************** -->
		<td id="userConfiguration">
					<a  class='editlink' href="toUserConfigure.action">
						<img style="width:35px; height:35px;" src="/imonitor/resources/images/CustomWidgets.png"/></a> 
			</td><td>
				<a class='editlink rulenadd'  href="toUserConfigure.action"  popuptitle="System Configuration" popupwidth="500px"><span class='titlespangeneric'><s:text name='setup.user.configure' /></span></a>
			</td>
			
			<td id="tempPasswordConfig">
					<a  class='editlink' href="togeneratePassword.action">
						<img style="width:35px; height:35px;" src="/imonitor/resources/images/CustomWidgets.png"/></a> 
			</td><td>
				<a class='editlink rulenadd'  href="togeneratePassword.action"  popuptitle="Generate Temporary Password" popupwidth="500px"><span class='titlespangeneric'><s:text name='setup.user.Password' /></span></a>
			</td>
			
			
			<!-- **************************************** AdminSuperUser ********************************* -->
			<td></td>	
			<td></td>	
			<td id="changePasswordSuper1">
				<a class='editlink rulenadd' href="toChangeAdminSuperUserPassword.action"  popuptitle="<s:text name='setup.user.password.change' />">
				<img style="width:35px; height:35px;" src="/imonitor/resources/images/CustomWidgets.png"></a>
			</td ><td id="changePasswordSuper2">
				<a class='editlink rulenadd' href="toChangeAdminSuperUserPassword.action"  popuptitle="<s:text name='setup.user.password.change' />"><span class='titlespangeneric'> <s:text name='Change SuperUser Password' /> </span></a>
			</td>
			<!-- **************************************** AdminSuperUser end ********************************* -->
			
		</tr>
	</table>
	<div></div>

	<!-- **************************************************************** sumit end ******************************************************** -->
<!-- 	<a class='' href="#" style="border: none;text-decoration: none;"><img src="/imonitor/resources/images/useradd.png"></a> -->
<%-- 	<span class='titlespan'>&nbsp;Edit Profile</span> --%>
	<div class="pagetitleline"></div>
</div>
<br/>

<s:form action="updateCustomer.action" method="POST" id="editcustomerform" cssClass="ajaxinlineform" style="text-align: left;">
	<%-- <s:textfield name="customer.firstName" key="setup.user.name.first" cssClass="alphabets"></s:textfield>
	<s:textfield name="customer.middleName" key="setup.user.name.middle" cssClass="alphabets"></s:textfield>
	<s:textfield name="customer.lastName" key="setup.user.name.last" cssClass="alphabets"></s:textfield>
	<s:textfield name="customer.place" key="setup.user.address"></s:textfield>
	<s:textfield name="customer.post" key="setup.user.post"></s:textfield>
	<s:textfield name="customer.province" key="setup.user.state" cssClass="alphabets"></s:textfield>
	<s:textfield name="customer.pincode" cssClass="number" key="setup.user.post.code" minvalue="100000" maxvalue="999999"></s:textfield>
	<s:textfield name="customer.phoneNumber" cssClass="phonenumber" maxlength="12" key="general.phone"></s:textfield>
	<s:textfield name="customer.email" key="general.email" cssClass="required email"></s:textfield>
	<s:textfield name="customer.mobile" key="general.mobile" cssClass="required mobilenumber"></s:textfield> --%>
	<s:hidden name="user.id"></s:hidden>
	
	<s:textfield name="user.userId" label="User Name"  readonly="true"></s:textfield>
	<s:textfield name="user.email" key="general.email" cssClass="required email"></s:textfield>
	<s:textfield name="user.mobile" key="general.mobile" cssClass="required mobilenumber"></s:textfield>
	
	<tr></tr><tr></tr><tr></tr><tr><td></td><td><s:submit theme="simple" key="general.save"></s:submit></td></tr>
</s:form>

<table>

</table>

<table style= "margin: -108px 0px 0px 840px;">
<tr>
<td>

<%--  <button style="width: 130px;"  type='button' id="getDiagnosticInfo" href="toGetDiagnosticInfo.action"  class='bt bbtn editlink' title='click to get diagnostic info'><s:text name="Diagnostic Info" /></button>  --%>
<%-- <button style="width: 130px;"  type='button' id="getDevice" href="toaddvirtualdevice.action"  class='bt bbtn updategatewaylink' title='click to add virtual device'><s:text name="Add virtual device" /></button> --%>

</td>
</tr>

</table>

<a class='gatewayList editlink'  href="getGatewaysForRename.action"  popuptitle="Edit Gateway Name" popupwidth="500px"><span class='titlespangeneric'><s:text name='Edit Gateway Name' /></span></a>

<s:hidden id="feature" name="#session.user.customer.featuresEnabled " ></s:hidden>
<div id ="discoverydialog"></div>
<script>
	$(document).ready(function() {
		
		// (TO display superUser password change button)
		 var mappingResult = '<s:property value="mappingResult"/>';
		if (mappingResult == "present")
		{
			$("#changePasswordSuper1").show();
			$("#changePasswordSuper2").show();
			
		} else 
		{
			$("#changePasswordSuper1").hide();
			$("#changePasswordSuper2").hide();
		}
		// (TO display superUser password change button) end
		
		$(".gatewayList").hide();
		//3gp start
		
		var handleSuccess = function(xml){
			//arrangeGateWaySectionForSetup(xml);
			// 1. Handling the gatewy.
			userDetails.setGateWayCount($(xml).find("gateway").size());
			 gateWayCount = $(xml).find("gateway").size();
			 if (gateWayCount > 1)
			 {
				 $(".gatewayList").show();
			}
		};
		
		var showUserDetails = function(){
			$.ajax({
				url: "userdetails.action",
				cache: false,
				dataType: 'xml',
				success: handleSuccess
			});
			setTimeout(showUserDetails,10000);
		};
		showUserDetails();
		
		//3gp end
		
		
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
			},
			dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'
			});
		$(".addnewrow").xpAddImageAfter({addimgsrc:'/imonitor/resources/images/addbutton.png',removeimgsrc:'/imonitor/resources/images/deletbutton.png',addimgclass:'duplicatemyrow',removeimgclass:'removemyrow'});
		var cForm = $("#editcustomerform");
		var inputs = cForm.find('input');
		
		inputs.each(function(index, inp)
		{
		var input = $(inp);
		if(input.hasClass('required'))
		{
			input.closest("tr").find("td:first-child").append("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -4px -2px 0px 29px;'>*</span>");

		}
		
		});
		inputs = cForm.find('textarea');
		inputs.each(function(index, inp)
		{
		var input = $(inp);
		if(input.hasClass('required'))
		{
			input.closest("tr").find("td:first-child").append("<span  class='detailss' style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px; float: right; margin: -5px 82px 0px -111px;'>*</span>");

		}
		
		});
		Xpeditions.validateForm(cForm);
		
	});
	
	//naveen start
	$(".updategatewaylink").live('click', function(){
		
		
		var url = $(this).attr('href');
		var para = '<div><p style="color: black;">'+"<s:text name='msg.ui.myprofile.01'/>"+'</p>';
		$("#discoverydialog").html(para);
		$("#discoverydialog").dialog({
			stackfix: true,
			position: [500,250],
			modal: true,
			buttons: {
				<s:text name='Yes' />: function() {
					$(this).dialog('close');
					
				 
				$.ajax({
					url: url,
					dataType: 'xml',
					success: function(xml){
						
							
					}
				});
			},
				<s:text name='No' />: function(){
					$(this).dialog('close');
					$("#discoverydialog").dialog("destroy");
				}
			},
		close: function(ev, ui) { 
			
			$("#discoverydialog").dialog("destroy");
		}
		});
	});
	
	//sumit start
	$("#toConfigureResetPasswordInfo").live("click", function(){
		//var pass = prompt("This is a secured area. Please enter your password to proceed.","your password");
		var targeturl = $(this).attr('href');
		var toDisplay = '<div><p style="color: red;">'+ '<s:text name="setup.user.password.secure"/>' +'</p>';
		toDisplay += "<br><input class='confirmPasssword' type='password'  placeholder='"+'<s:text name="setup.user.password.secure"/>'+"'></input></div>";
		$("#confirm").dialog("destroy");
		$("#confirm").html(toDisplay);
		$("#confirm").dialog({
					stackfix: true,
					modal: true,
					buttons: {
						<s:text name="general.ok" />: function() {
							$(this).dialog('close');
							var pass = $('.confirmPasssword').val();
							var params='password='+pass;
							//var targeturl = "toConfigureResetPasswordInfo.action";
							$.ajax({
								url: targeturl,
								type: "POST",
								data: params,
								success: function(data){
									var a= $(data).find("p.message").text();
									var aa = a.split(":");
									if(aa[0] =='Failure'){
										showResultAlert(aa[1]);
									}
									else
									{
										$('#editmodal').html(data);
										$('#editmodal').dialog('destroy');
										$('#editmodal').dialog({height: 400, width: 550, title: "<s:text name='setup.user.password.configure' />", modal:true});

									}
								}
							});
						},
						<s:text name="general.cancel" />: function() {
							$(this).dialog('close');
						}

					}
				}); 
		return false;
 	});
	</script>