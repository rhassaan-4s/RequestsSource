<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<table width="90%"  border="0" cellspacing="0" cellpadding="0" style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader">
		</td><td align="left"></td>
	</tr>
	<tr>
		<td colspan="2" bgcolor="#5A718B" height="2"></td>
	</tr>
	<tr>
		<td colspan="2" height="1"></td>
	</tr>

	<tr>
		<td colspan="2" height="20"></td>
	</tr>
	
	<tr>
		<td colspan="2" height="200" valign="top">
			<img src="<c:url value="/web/common/images/alert_icon.gif"/>" style="margin-left:10px; margin-top:5px; ">
			<font color="red">
			<abc:i18n property="security.caption.pageNotFound"/>
			<fmt:message key="security.caption.pageNotFound"/>
			</font>
		</td>
	</tr>

</table>


<%@ include file="/web/common/includes/footer.jsp" %>