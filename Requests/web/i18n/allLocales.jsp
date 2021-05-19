<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<abc:security property="298" />
<br>
<table rules="all" align="center" width="70%" class="sofT">
	<tr id="head_1_ep">
		<td class="helpTitle" colspan="2" nowrap><abc:i18n
			property="i18n.header.locale" /><fmt:message key="i18n.header.locale" /></td>
	</tr>
	<tr>
		<td nowrap class="helpHed">&nbsp;<abc:i18n property="commons.caption.language" /><fmt:message
			key="commons.caption.language" />&nbsp;</td>
		<td nowrap class="helpHed">&nbsp;<abc:i18n property="commons.caption.country" /><fmt:message
			key="commons.caption.country" />&nbsp;</td>
	</tr>
	<c:forEach var="result" items="${locales}">
		<tr>
			<td class="helpBod">${result.language}</td>
			<td class="helpBod">${result.country}</td>
		</tr>
	</c:forEach>
</table>
<br />
<!--table>
	<tr>
		<td align="center"><br>
		<abc:i18n property="commons.button.add" /> <input type="button"
			name="add" value=" <fmt:message key="commons.button.add"/> "
			class="button"
			onclick="javascript: createWindow('addNewLocale.html', 250, 700);"></td>
	</tr>
</table-->

<%@ include file="/web/common/includes/footer.jsp"%>