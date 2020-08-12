<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<s:iterator value="objects" var="object">
<!--<script>-->
imvgDetails.addImageDetails (<s:property value="#object[1]"/>,"<s:property value="imvgUploadContextPath + #object[0]"/>","<s:property value="#object[3]"/>","<s:property value="#object[4]"/>","<s:property value="#object[5]"/>");
	<s:if test="#object[2] == alertFromImvgId ">
		 var showImageId = "<s:property value="#object[1]"/>" ;
	</s:if>
</s:iterator>
<!--</script>-->
<script type="text/javascript">
</script>
imvgDetails.sortIds();
imvgDetails.showImage(1 * showImageId);
