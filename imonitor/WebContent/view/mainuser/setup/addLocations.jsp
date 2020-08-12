<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<s:form action="addLocation.action" method="post" id="usersaveform" cssClass="ajaxfileuploadformpopup" datatableid="listlocationstable" datatableurl="listAskedLocations.action" enctype="multipart/form-data">
	 <s:hidden name="location.iconFile" id="iconFile"></s:hidden>
	 <s:hidden name="location.MobileIconFile" id="MobileIconFile"></s:hidden>
	<s:textfield name="location.name" key="general.name"  cssClass="required utf8alphanumeric" onkeydown="testForEnter();"></s:textfield>
	<s:textarea name="location.details" key="general.description"  id="Details" cssClass="Details required" rows="4" cols="17"></s:textarea>
	<!-- vibhuti we do not show the file <s:file name="file" label="IconFile"></s:file> -->
	
	
		<tr><td></td></tr>
               
                        <tr id="toggleIcon">
                        <td valign="top"><s:text name="setup.rooms.chooseicon" /></td>
                        <td>
                                <table>
                                	<tr>
                                    	<s:iterator value="listIcons"  status="abc" >
        									<s:if test="#abc.count == 7 || #abc.count == 13 ||  #abc.count == 19 ||  #abc.count == 25">
                					</tr>
                					<tr>
        									</s:if>
        									<td><input type='radio'  checked="checked" onClick="javascript: populateIcon('<s:property value="fileName" />')"  name='selectIcon' value='<s:property value="id" />'/>
        									<img class='listlocationicon' src='<s:property value="fileName" />'/></td>
        									<td>&nbsp;</td>
                                        </s:iterator>
                                    </tr>
                                        </table>
                                </td>
                        </tr>
 
   	<tr><td></td></tr>
	<tr>
	<td><s:checkbox style="float:right;" class="toggleUpload" theme="simple" id="toggleUpload" value="false" name="toggleUpload"></s:checkbox></td>
	<td><span class="actionheader"><s:text name="setup.rooms.uploadicon" /></span></td>
	</tr>
	<tr>
		<td style=" width: 50px; ">
			<span id="recommendations" class="errormessageclass">
				<s:text name="setup.rooms.recoicon" />
			</span>
		</td>
    		<td><s:file name="uploadedImage" id="uploadedImage" key="setup.rooms.selectfile" class="uploadedImage" onchange="return Checkfiles();"></s:file></td>
	</tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	<tr></tr>
	
	 <tr id="toggleIcon">
                        <td valign="top"><s:text name="Choose Mobile Icon:" /></td>
                        <td>
                                <table>
                                	<tr >
                                    	<s:iterator value="listMobileIcons"  status="abc" >
        									<s:if test="#abc.count == 7 || #abc.count == 13 ||  #abc.count == 19 ||  #abc.count == 25">
                					</tr>
                					<tr>
        									</s:if>
        									<td bgcolor="#1b1b1c"><input type='radio' onClick="javascript: populateMobileIcon('<s:property value="fileName" />')"  name='selectIcon' value='<s:property value="id" />'/>
        									<img class='listlocationicon' src='<s:property value="fileName" />'/></td>
        									<td>&nbsp;</td>
                                        </s:iterator>
                                    </tr>
                                        </table>
                                </td>
                        </tr>
	
	<tr></tr>
	<tr>
		<td></td>
		<td style=" float: right;"><input id="CancelButton"type="button" onClick="javascript:DestoryDialog()" value="Cancel" name="Cancel"/></td>
		<td><s:submit theme="simple" key="general.save" id="saveButton"></s:submit></td>
	</tr>
	<div id="confirm"></div>
	

</s:form>
	<script type="text/javascript">
		$(document).ready(function() {
			
			document.getElementById("iconFile").value="/imonitor/resources/images/systemicons/passage02.png";
			document.getElementById("MobileIconFile").value="/imonitor/resources/images/systemicons/guest_room.png";
			var cForm = $("#usersaveform");
			var inputs = cForm.find('input');
			inputs.each(function(index, inp){
				var input = $(inp);
				if(input.hasClass('required')){
					input.closest("tr").find("td:first-child").append("<span style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px;float: left;margin: -4px -2px 0px 0px;'>*</span>");
				}
			});
			inputs = cForm.find('textarea');
			inputs.each(function(index, inp){
				var input = $(inp);
				if(input.hasClass('required')){
					input.closest("tr").find("td:first-child").append("<span  class='detailss' style='color: rgb(218, 41, 62); font-weight: bold; font-size: 13px; float: right; margin: -5px 82px 0px -111px;'>*</span>");
				}
			});
			Xpeditions.validateForm(cForm);
			$("#uploadedImage").hide();
			$("#recommendations").hide();
			$(".detailss").css({margin:"-6px 81px 0px -111px"});
			$("#toggleUpload").live('click', function(){
				var uploadImage = $("#toggleUpload").attr('checked');
				if(uploadImage){
					$("#recommendations").show();
					$("#uploadedImage").addClass("required");
					$("#uploadedImage").show();
					$("#toggleIcon").hide();
					$(".detailss").css({margin:"-6px 106px 0px -111px"});
				}else{
					$(".detailss").css({margin:"-6px 81px 0px -111px"});
					$("#recommendations").hide();
					$("#uploadedImage").hide();
					$("#uploadedImage").removeClass("required");
					$("#toggleIcon").show();
				}
			});
		});

		
		
		$("#usersaveform").keypress(function(e) {
				  if (e.which == 13) {
					  e.preventDefault();
					  //alert("Enter key");
				    return false;
				  }
				});
			
		   
		 /*   $("#usersaveform").keypress(function(event) {
	            if (event.keyCode == 13) {
	                event.preventDefault();
	                return false;
	            }
	        }); */
			
			
			
			
			
		$("#saveButton").live("click", function(){
			var fup = document.getElementById('uploadedImage');
			
			var uploadImage = $("#toggleUpload").attr('checked');
			if(uploadImage){
				
			
			var fileName = fup.value;
			if(fileName.length == 0){
				showResultAlert('<s:text name="setup.rooms.uploadImage" />');
				return false;
			}else{
				var result = Checkfiles();
				if(!result){
					showResultAlert('<s:text name="setup.rooms.imagesOnly" />');
					return false;
				}
			}
			}
		});
       function Checkfiles(){
           var fup = document.getElementById('uploadedImage');
           var fileName = fup.value;
           var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
           if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" || ext == "png" || ext == "PNG")
           {
            return true;
           } 
           else
           {
           // $("#confirm").dialog("destroy");
			// $("#confirm").html("<s:text name='setup.msg.0003' />");
			// $("#confirm").dialog({
							// stackfix: true,
							// modal: false,
							// position: [350,100],
							// buttons: {
								// <s:text name='general.yes' />: function() 
								// {
									// $(this).dialog('close');
								// }
								     // }
							// });
           // fup.focus();
           return false;
           }
       }
		
        function populateIcon(iconId)
        {
                    document.getElementById("iconFile").value = iconId;
        }
        
        
        function populateMobileIcon(iconId)
        {
        	        alert(iconId);
                    document.getElementById("MobileIconFile").value = iconId;
        }
			
		function DestoryDialog()
        {
                   $("#editmodal").dialog('destroy'); 
        }	
		
		function testForEnter() 
    {    
	if (event.keyCode == 13) 
	{        
		event.cancelBubble = true;
		event.returnValue = false;
         }
    } 
  
	</script>	
