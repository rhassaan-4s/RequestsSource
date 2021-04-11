<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/header.jsp"%>

<br> <br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="banks.caption.objForSafeForm" /> <fmt:message
			key="banks.caption.objForSafeForm" /></td>
		<td align="left"></td>
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
		<td colspan="2">

		<form id="obj" name="obj" method="POST"
			action="<c:url value="/banks/error403.html"/>">
		<table cellpadding="0" cellspacing="1" border="0">


			<input type="hidden" size="40" name="safeId" value="${safeId}" />
			<tr>
				<td colspan=5>&nbsp;</td>
			</tr>
			<tr id="head_1_ep">
				<td class="bodyBold" colspan=4 nowrap><abc:i18n
					property="banks.caption.objForSafeForm" /> <fmt:message
					key="banks.caption.objForSafeForm" /></td>
				<td nowrap colspan=1 align=left><img
					src="<c:url value="/web/common/images/required_icon.gif"/>"
					border="0" alt="Required Information" title="Required Ination"
					width=18 height=18 align="texttop"><span class="bodySmallBold"> =<abc:i18n
					property="commons.caption.requiredInformation" /> <fmt:message
					key="commons.caption.requiredInformation" /></span></td>
			</tr>
			<TR>
				<TD CLASS="blackLine" COLSPAN=5><img
					src="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>



		</table>
		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/footer.jsp"%>