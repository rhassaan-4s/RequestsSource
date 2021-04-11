<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>



<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="commons.caption.clientsForm" /> <fmt:message
			key="commons.caption.clientsForm" /></td>
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

		<form id="client" name="client" method="POST"
			action="<c:url value="/common/commonAdminClientsForm.html"/>"><spring:bind
			path="client.*">
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				</c:forEach></div>
			</c:if>
		</spring:bind> <spring:bind path="client.id">
			<input type="hidden" size=20 maxlength=40 name="clientId"
				value="${status.value}" />
		</spring:bind> <input type="hidden" size=20 maxlength=40
			name="clientId" value="${clientId}" />

		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right:40px">

			<tr>
				<td colspan=5>&nbsp;</td>
			</tr>
			<tr id="head_1_ep">
				<td class="bodyBold" colspan=4 nowrap><abc:i18n
					property="commons.caption.clientsForm" /> <fmt:message
					key="commons.caption.clientsForm" /></td>
				<td nowrap colspan=1 align=left><img
					src="<c:url value="/web/common/images/required_icon.gif"/>"
					border="0" alt="Required Information" title="Required Ination"
					width=18 height=18 align="texttop"><span class="bodySmallBold"> = <abc:i18n
					property="commons.caption.requiredInformation" /><fmt:message
					key="commons.caption.requiredInformation" /></span></td>
			</tr>
			<TR>
				<TD CLASS="blackLine" COLSPAN=5><img
					src="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>

			<tr>
				<td nowrap class="requiredInput"><abc:i18n
					property="commons.caption.clientName" /> <fmt:message
					key="commons.caption.clientName" /></td>
				<td><spring:bind path="client.clientName">
					<input type="text" size="40" name="${status.expression}"
						value="${status.value}"></input>
				</spring:bind></td>
			</tr>
			
			<tr>
				<td nowrap class="requiredInput"><abc:i18n
					property="commons.caption.clientPort" /> <fmt:message
					key="commons.caption.clientPort" /></td>
				<td><spring:bind path="client.portNo">
					<input type="text" size="40" name="${status.expression}"
						value="${status.value}"></input>
				</spring:bind></td>
			</tr>
			
			
			<tr>
				<td colspan=5>&nbsp;</td>
			</tr>


			<tr id="btn">
				<td colspan=5 align=center><abc:i18n property="commons.button.save" />
				<input type="submit" name="save"
					value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;

				<abc:i18n property="commons.button.cancel" /> <input type="reset"
					name="cancel" value="<fmt:message key="commons.button.cancel"/>"
					class="button" /></td>
			</tr>
		</td>
	</tr>
</table>
</td>
</tr>
</table>



<%@ include file="/web/common/includes/footer.jsp"%>
