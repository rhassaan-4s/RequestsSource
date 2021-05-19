<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true"/>
<abc:security property="117" />
<br>
<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<!--tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="commons.caption.companyView" /> <fmt:message
			key="commons.caption.companyView" /></td>
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
		<table rules="all" align="center" width="70%" class="sofT">
			<tr id="head_1_ep">
				<td class="helpTitle" colspan="6" nowrap><abc:i18n
					property="commons.caption.companyView" /> <fmt:message
					key="commons.caption.companyView" /></td>
			</tr>
			<tr>
				<td nowrap class="helpHed">&nbsp; <abc:i18n property="commons.caption.description" />
				<fmt:message key="commons.caption.description" />&nbsp;</td>
				<td nowrap class="helpHed">&nbsp;&nbsp;</td>
			</tr>
			<c:forEach var="result" items="${results}">
				<tr>
					<td class="helpBod">${result.description}</td>
					<td class="helpBod"><NOBR>&nbsp; <abc:i18n
						property="commons.button.branches" /> <a
						href="/Requests/stores/storesAdminBranches.html?companyId=${result.id}" class="actionLink">
					<fmt:message key="commons.button.branches" /></a>&nbsp;</NOBR></td>
				</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
</table>
<br />
<!--table>
	<tr>
		<td align="center"><abc:i18n property="commons.button.add" /> <input
			type="button" name="add"
			value=" <fmt:message key="commons.button.add"/> " class="button"
			onclick="window.location='commonAdminCompanyForm.html'"></td>
	</tr>
</table-->

<%@ include file="/web/common/includes/footer.jsp"%>