<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/userPreferencesHeader.jsp"%>
<abc:security property="299" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="security.header.changeUserPassword" /> <fmt:message
			key="security.header.changeUserPassword" /></td>
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
		<td><spring:bind path="changePasswordCommand.*">
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				</c:forEach></div>
			</c:if>
		</spring:bind></td>
	</tr>

	<tr>
		<td colspan="2">

		<form id="defaults" name="defaults" method="POST"
			action="<c:url value="/security/changeUserPassword.html"/>">
		<table rules="all" align="center" width="70%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="security.header.changeUserPassword" /> <fmt:message
					key="security.header.changeUserPassword" /></td>
			</tr>
			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="security.caption.oldPassword" /> <fmt:message
					key="security.caption.oldPassword" /></td>
				<td class="formBodControl"><spring:bind path="changePasswordCommand.oldPassword">
					<input type="password" name="${status.expression}"
						value="${status.value}" />
				</spring:bind></td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="security.caption.newPassword" /> <fmt:message
					key="security.caption.newPassword" /></td>
				<td class="formBodControl"><spring:bind
					path="changePasswordCommand.newPassword">
					<input type="password" name="${status.expression}"
						value="${status.value}" />
				</spring:bind></td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="security.caption.confirmPassword" /> <fmt:message
					key="security.caption.confirmPassword" /></td>
				<td class="formBodControl"><spring:bind
					path="changePasswordCommand.confirmPassword">
					<input type="password" name="${status.expression}"
						value="${status.value}" />
				</spring:bind></td>
			</tr>

			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>

			<tr id="btn">
				<td colspan="2" align="center">
				<abc:i18n property="commons.button.save" /> <input type="submit"
					name="save" value="<fmt:message key="commons.button.save"/>"
					class="button" /> &nbsp;&nbsp;&nbsp; <abc:i18n
					property="commons.button.cancel" /> <input type="reset"
					name="cancel" value="<fmt:message key="commons.button.cancel"/>"
					class="button" /></td>
			</tr>

		</table>
		</form>
		</td>
	</tr>
</table>
<%@ include file="/web/common/includes/footer.jsp"%>
