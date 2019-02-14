<%@ include file="/web/common/includes/taglibs.jsp"%>
<%@ include file="/web/common/includes/userPreferencesHeader.jsp"%>

<abc:security property="285" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right: 10px">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="i18n.header.editUserLanguage" /><fmt:message
			key="i18n.header.editUserLanguage" /></td>
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
			action="<c:url value="/i18n/changeUserLanguage.html"/>">

		<table rules="all" align="center" width="50%" class="sofT" >
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="2" nowrap><abc:i18n
					property="i18n.header.editUserLanguage" /> <fmt:message
					key="i18n.header.editUserLanguage" /></td>
			</tr>

			<tr>
				<td nowrap class="formReq"><abc:i18n
					property="commons.caption.language" /> <fmt:message
					key="commons.caption.language" /></td>
				<td class="formBodControl"><select name="selectLanguage">
					<c:forEach items="${languages}" var="lang" varStatus="loop">
						<option value="${lang.id}" ${lang.id==user.language.id? ' selected' : ''}>${lang.language}</option>
					</c:forEach>
				</select></td>
			</tr>
			
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>

			<tr id="btn">
				<td colspan="2" align=center><abc:i18n
					property="commons.button.save" /> <input type="submit" name="save"
					value="<fmt:message key="commons.button.save"/>" class="button" />
				&nbsp;&nbsp;&nbsp; <abc:i18n property="commons.button.cancel" /> <input
					type="reset" name="cancel"
					value="<fmt:message key="commons.button.cancel"/>" class="button" />
				</td>
			</tr>

		</table>
		</form>
		</td>
	</tr>
</table>

<%@ include file="/web/common/includes/footer.jsp"%>
