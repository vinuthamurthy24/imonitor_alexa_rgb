<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
				<div class="closebutton">
					<a href="#" onclick="closeoverlay()"><img src="/imonitor/resources/images/close_button.png" title="close"/></a>
				</div>
			<div class="imageshowingtimedetails">
				<ul>
					<li style="color:#047AA5;" id="timeStamp"><span></span></li>
					<li style="color:#2F2F2F;" id="alertdetails"><span></span></li>
				</ul>
			<div>
				<div class="sliderrightbutton">
<!--					<a href="#"><img src="/imonitor/resources/images/control_right.png"/></a>-->
					<a href="#" class="previousimagelink" currentimageId="<s:property value="#object[1]"/>"><img title="next" id="previousimagebutton" src="/imonitor/resources/images/control_right.png"></a>
				</div>
				<div class="sliderleftbutton">
<!--					<a href="#"><img src="/imonitor/resources/images/control_left.png"/></a>-->
					<a href="#" currentimageId="<s:property value="#object[1]"/>" class="nextimagelink"><img title="previous" id="nextimagebutton" src="/imonitor/resources/images/control_left.png"></a>
				</div>
				<div >				
					<img id="attachmentimageid" src="/imonitor<s:property value="#object[0]"/>" style="width:422px;height:245px;" class="imagedisplaying">
				</div>
				<div style="float: right;margin-right: 38px;">
					<a id="downloadLink" target="_blank" href="/imonitor<s:property value="#object[0]"/>">download</a>
				</div>
					
			</div>
			</div>
<!--	<div class="previousimage">-->
<!--	</div>-->
	<div class="displayimage">
	</div>
<s:iterator value="objects" var="object">
<script>

imvgDetails.addImageDetails (<s:property value="#object[1]"/>,"<s:property value="imvgUploadContextPath + #object[0]"/>","<s:property value="#object[3]" />","<s:property value="#object[4]"/>");
	<s:if test="#object[2] == alertFromImvgId ">
		 var showImageId = "<s:property value="#object[1]"/>" ;
		
		
	</s:if>
</script>
</s:iterator>
<script type="text/javascript">
imvgDetails.sortIds();
imvgDetails.showImage(1 * showImageId);
</script>
