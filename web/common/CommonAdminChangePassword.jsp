<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<abc:security property="279" />
<br>
<table width="90%" border=cellspacing= "0" cellpadding="0"
	style="padding-right: 10px">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>

	<tr>
		<td class="tableHeader"><abc:i18n
				property="commons.pageHeading.changePassword" />
			<fmt:message key="commons.pageHeading.changePassword" /></td>
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
		<td colspan="2"><form:form method="POST"
				modelAttribute="changePassword"
				action="/Requests/communication/commonAdminChangePassword.html">
				<spring:bind path="changePassword.*">
					<c:if test="${not empty status.errorMessages}">
						<div>
							<c:forEach var="error" items="${status.errorMessages}">
								<font color="red"> <c:out value="${error}"
										escapeXml="false" /><br />
								</font>
							</c:forEach>
						</div>
					</c:if>
				</spring:bind>
				<table border=0 cellspacing=1 cellpadding=0 id="ep"
					style="margin-right: 40px">

					<tr>
						<td colspan=5>&nbsp;</td>
					</tr>

					<tr id="head_1_ep">
						<td class="bodyBold" colspan=4 nowrap><abc:i18n
								property="commons.pageHeading.changePassword" />
							<fmt:message key="commons.pageHeading.changePassword" /></td>
						<td nowrap colspan=1 align=left></td>
					</tr>

					<TR>
						<TD CLASS="blackLine" COLSPAN=5><img
							src="/Requests/web/common/images/s.gif"></TD>
					</TR>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.oldPassword" />
							<fmt:message key="commons.oldPassword" /></td>
						<td><spring:bind path="changePassword.oldPassword">
								<input type="password" size="40" maxlength=40
									name="${status.expression}" value="${status.value}" />
							</spring:bind></td>
					</tr>


					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.newPassword" />
							<fmt:message key="commons.newPassword" /></td>
						<td><spring:bind path="changePassword.newPassword">
								<input type="password" size="40" maxlength=40
									name="${status.expression}" value="${status.value}" />
							</spring:bind></td>
					</tr>


					<tr>
						<td colspan=5>&nbsp;</td>
					</tr>


					<tr id="btn">
						<td colspan=5 align=center><abc:i18n
								property="commons.button.submit" /><input type="submit"
							name="save" value=" <fmt:message key="commons.button.submit"/>"
							class="button">&nbsp;&nbsp;&nbsp;</td>
					</tr>


				</table>
			</form:form></td>

	</tr>

</table>


<%@ include file="/web/common/includes/footer.jsp"%>