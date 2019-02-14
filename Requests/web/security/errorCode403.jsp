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
		<td colspan="2" height="150" valign="top">
			<FONT color="red">
				<img src="<c:url value="/web/common/images/notallowed.gif"/>" style="margin-left:10px; margin-top:5px; ">
				<abc:i18n property="security.caption.permissionDenied"/>
				<fmt:message key="security.caption.permissionDenied"/>
				<img src="<c:url value="/web/common/images/notallowed.gif"/>" style="margin-left:10px; margin-top:5px; ">
			</FONT>
		</td>
	</tr>

	<tr>
		<td colspan="2" align="left">
			<FONT color="blue">
				<abc:i18n property="security.caption.forMoreInfoContactAdmin"/>
				<fmt:message key="security.caption.forMoreInfoContactAdmin"/>
			</FONT>
		</td>
	</tr>
	
</table>


<%@ include file="/web/common/includes/footer.jsp" %>