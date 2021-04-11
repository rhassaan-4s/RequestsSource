<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="dbUpdate.header.newQueryForm" /> <fmt:message
			key="dbUpdate.header.newQueryForm" /></td>
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
	</tr-->

	<tr>
		<td><c:if test="${not empty status.errorMessages}">
			<div><c:forEach var="error" items="${status.errorMessages}">
				<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
				</font>
			</c:forEach></div>
		</c:if></td>
	</tr>

	<tr>
		<td colspan="2">

		<form id="Query" name="Query" method="POST"
			action="<c:url value="/dbUpdate/newQueryForm.html"/>">
		<br /><br />
		<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="dbUpdate.header.newQueryForm" /> <fmt:message
					key="dbUpdate.header.newQueryForm" /></td>
			</tr>
			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="dbUpdate.caption.query" /><fmt:message
					key="dbUpdate.caption.query" /></td>
				<td class="formBodControl"><textarea dir="ltr" name="Query"  id="Query" cols="100" rows="20"></textarea></td>
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
