<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/userPreferencesHeader.jsp"%>
<abc:security property="287" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="security.header.editUserApplication" /> <fmt:message
			key="security.header.editUserApplication" /></td>
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
		<td colspan="2">

		<form id="defaults" name="defaults" method="POST"
			action="<c:url value="/security/changeUserApplication.html"/>">

		<table rules="all" align="center" width="50%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="security.header.editUserApplication" /> <fmt:message
					key="security.header.editUserApplication" /></td>
			</tr>
			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="security.caption.application" /> <fmt:message
					key="security.caption.application" /></td>
				<td class="formBodControl"><select name="selectApplication">
					<c:forEach items="${applications}" var="app" varStatus="loop">
						<option value="${app.id}" ${app.id==user.defaultApplication.id? ' selected' : ''}>${app.name}</option>
					</c:forEach>
				</select></td>
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
